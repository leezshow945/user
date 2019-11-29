package com.jq.user.rebate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.code.PlatformCodeEnum;
import com.jq.platform.operating.dto.OperatingLinesInfoDTO;
import com.jq.platform.operating.service.OperatingLinesInfoService;
import com.jq.report.member.dto.MemberDTO;
import com.jq.report.member.service.MemberService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.BonusConstant;
import com.jq.user.constant.UserConstant;
import com.jq.user.exception.UserException;
import com.jq.user.oldbonus.dao.UserBonusDao;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.rebate.dao.RebateResultDao;
import com.jq.user.rebate.dao.RebateResultInfoDao;
import com.jq.user.rebate.dao.RebateRuleDao;
import com.jq.user.rebate.dao.RebateVideoResultInfoDao;
import com.jq.user.rebate.dto.RebateBetsDTO;
import com.jq.user.rebate.dto.RebateResultDTO;
import com.jq.user.rebate.dto.RebateResultInfoDTO;
import com.jq.user.rebate.dto.RebateVideoResultInfoDTO;
import com.jq.user.rebate.entity.RebateResultEntity;
import com.jq.user.rebate.entity.RebateResultInfoEntity;
import com.jq.user.rebate.entity.RebateVideoResultInfoEntity;
import com.jq.user.rebate.service.RebateResultInnerService;
import com.jq.user.rebate.service.RebateRuleInfoInnerService;
import com.jq.user.rebate.support.CancelRebateCallable;
import com.jq.user.rebate.support.RebateResultCallable;
import com.jq.user.support.AmazonSQSUtil;
import com.jq.user.support.PageUtil;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.resp.GroupUsersResp;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.resp.CountTotalEffectiveBetResp;
import com.liying.trade.user.resp.EffectiveBetUserResp;
import com.liying.trade.user.vo.CountTotalEffectiveBetReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Transactional
@Service
public class RebateResultServiceImpl extends ServiceImpl<RebateResultDao, RebateResultEntity> implements RebateResultInnerService {
    @Resource
    private RebateResultDao rebateResultDao;
    @Resource
    private RebateRuleInfoInnerService rebateRuleInfoInnerService;
    @Resource
    private RebateResultInfoDao rebateResultInfoDao;
    @Resource
    private OrderSubtotalService orderSubtotalService;
    @Resource
    private UserFundService userFundService;
    @Resource
    private GroupService groupService;
    @Resource
    private UserBonusDao userBonusDao;
    @Resource
    private RebateRuleDao rebateRuleDao;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private OperatingLinesInfoService operatingLinesInfoService;
    @Resource
    private RebateVideoResultInfoDao rebateVideoResultInfoDao;
    @Resource
    private MemberService memberService;

    private final static Logger logger = LoggerFactory.getLogger(RebateResultServiceImpl.class);
    private static ExecutorService exec = Executors.newCachedThreadPool();

    @Override
    public ApiResult executeRebate(RebateResultDTO dto) {
        Assert.isNull(dto.getBeginTime(), "缺少开始时间");
        Assert.isNull(dto.getEndTime(), "缺少结束时间");
        Assert.isNull(dto.getEventName(), "缺少返水事件名");
        Assert.isNull(dto.getRuleId(), "缺少返水规则数据");
        Assert.isNull(dto.getGroupId(), "缺少分组ID数据");
        Assert.isNull(dto.getGroupName(), "缺少分组名数据");

        //验证事件名
        QueryWrapper<RebateResultEntity> ew = new QueryWrapper<RebateResultEntity>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("rule_id", dto.getRuleId());
        ew.eq("site_id", dto.getSiteId());
        ew.eq("event_name", dto.getEventName());
        if (count(ew) > 0) {
            return RPCResult.custom(UserCodeEnum.REBATE_EVENT_EXIST.getCode(), UserCodeEnum.REBATE_EVENT_EXIST.getMessage());
        }

        //判断站点运营额度是否欠费
        ApiResult<OperatingLinesInfoDTO> linesInfoDTOApiResult = operatingLinesInfoService.queryLineInfoBySiteCode(dto.getSiteCode());
        logger.info("executeRebate 判断运营额度--->siteCode：" + dto.getSiteCode() + ",出参：" + linesInfoDTOApiResult.toString());
        if (!RPCResult.checkApiResult(linesInfoDTOApiResult)) {
            return linesInfoDTOApiResult;
        }
        if (linesInfoDTOApiResult.getData().getCanAmount() <= 0) {
            return RPCResult.custom(PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode(), PlatformCodeEnum.QUOTA_NOE_ENOUGH.getMessage());
        }

        //GroupId == -1 表示指定会员返水
        if (dto.getGroupId() < 0) {
            if (CollectionUtil.isEmpty(dto.getReqIds())) {
                logger.info("executeRebate groupId is" + dto.getGroupId() + ";reqIds is Empty;入参异常");
                return RPCResult.success();
            }
            // GroupId >=0 表示全部返水 需要进行入参分组id筛选
        } else if (dto.getGroupId() > 0) {
            //选择全部返水并指定会员分组时，进行封装筛选所有指定分组会员
            List<Long> userIds = new ArrayList<>();
            ApiResult<List<GroupUsersResp>> groupApiResult = groupService.queryUsersByGroup(dto.getSiteCode(), dto.getGroupId().toString(), 0);
            logger.info("executeRebate 查询用户分组--->分组id：" + dto.getGroupId() + ",出参：" + groupApiResult.toString());
            if (!RPCResult.checkApiResult(groupApiResult)) {
                return RPCResult.custom(groupApiResult.getCode(), groupApiResult.getMessage());
            }
            if (CollectionUtil.isEmpty(groupApiResult.getData())) {
                logger.info(dto.getGroupId().toString() + "用户分组内未找到用户,返水操作终止" + dto.getSiteCode());
                return RPCResult.success();
            }
            for (GroupUsersResp resp : groupApiResult.getData()) {
                userIds.add(Long.valueOf(resp.getUserId()));
            }
            dto.setReqIds(userIds);//将筛选出的用户id list赋值到dto内
        }

        //获取规则组下最小投注用来过滤用户
        Long minTotalEffect = rebateRuleInfoInnerService.getMinTotalEffect(dto.getSiteId(), dto.getRuleId());

        //筛选 融合真人彩票投注记录的 用户id
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setUserIdList(dto.getReqIds());
        memberDTO.setSiteCode(dto.getSiteCode());
        memberDTO.setStartTime(dto.getBeginTime());
        memberDTO.setEndTime(dto.getEndTime());
        memberDTO.setBetMoney(minTotalEffect);

        ApiResult<List<Long>> userListApiResult = memberService.findBetUserListApi(memberDTO);
        logger.info("executeRebate 筛选已投注用户ID--->入参：" + memberDTO.toString() + ",出参：" + userListApiResult.toString());
        if (!RPCResult.checkApiResult(userListApiResult)) {
            return RPCResult.custom(userListApiResult.getCode(), userListApiResult.getMessage());
        }
        if (CollectionUtil.isEmpty(userListApiResult.getData())) {
            //未筛选出 投注用户数据 直接返回结果
            return RPCResult.success(new RebateBetsDTO());
        }

        //筛选出的用户id列表进行 已返水验证判断
        dto.setReqIds(userListApiResult.getData());
        RebateResultDTO checkDTO = rebateResultDao.getResultByTime(dto);
        if (checkDTO != null) {
            String message = checkDTO.getUserName() + "已在" + checkDTO.getEventName() + "事件的" + checkDTO.getBeginTime() + "--" + checkDTO.getEndTime() + "时间段进行返水";
            return RPCResult.custom(UserCodeEnum.REBATE_TIME_EXIST.getCode(), message);
        }

        //通过筛选的id获取具体游戏类别投注金额
        CountTotalEffectiveBetReq req = new CountTotalEffectiveBetReq();
        req.setUserIds(dto.getReqIds());
        req.setBeginDate(dto.getBeginTime());
        req.setEndDate(dto.getEndTime());
        req.setMinEffectiveBetMoney(minTotalEffect);
        req.setSiteCode(dto.getSiteCode());

        //取得用户彩票真人各游戏类别有效投注数据
        ApiResult<CountTotalEffectiveBetResp> betRespApiResult = orderSubtotalService.countTotalEffectiveBetWithUserIds(req);
        logger.info("executeRebate 统计用户有效投注值--->入参：" + req.toString() + ",出参：" + betRespApiResult.toString());
        if (!RPCResult.checkApiResult(betRespApiResult)) {
            return RPCResult.custom(betRespApiResult.getCode(), betRespApiResult.getMessage());
        }

        //执行返水程序
        Date now = new Date();
        Long resultId = IdWorker.getId();
        RebateResultEntity result = new RebateResultEntity();
        BeanUtil.copyProperties(dto, result);
        result.setId(resultId);
        result.setDoTime(now);
        result.setCreateTime(now);
        result.setUpdateTime(now);
        result.setUpdateBy(dto.getCreateBy());
        result.setRebateNum(betRespApiResult.getData().getTotalMembers());
        result.setFinalRebateNum(betRespApiResult.getData().getTotalMembers());
        result.setAllBets(betRespApiResult.getData().getTotalEffectiveBet());

        Long allRebate = 0l;
        long timeTest = 0;

        try {
            //启用多线程循环遍历用户投注数据,计算返水值,进行结算
            String batchNO = UserConstant.OrderNoStartsWith.BATCH + UUID.randomUUID().toString().replace("-", "").toUpperCase();
            String queueUrl = AmazonSQSUtil.getQueueUrl(UserConstant.QueueUrl.INCOME_PAY_QUEUE);
            timeTest = System.currentTimeMillis();

            List<Future<Long>> futureList = new ArrayList<Future<Long>>();
            for (EffectiveBetUserResp betResp : betRespApiResult.getData().getEffectiveBetUserList()) {
                futureList.add(exec.submit(new RebateResultCallable(result, betResp, batchNO, queueUrl)));
            }
            for (Future<Long> future : futureList) {
                allRebate += future.get();
            }
        } catch (Exception e) {
            logger.error("error",e);
        }
        logger.info(dto.getEventName() + "事件返水结束，总执行时间-----》" + (System.currentTimeMillis() - timeTest) + "ms");
        result.setAllRebates(allRebate);

        /**
         * 出入款队列已同步运营额度
         */
//                //开始扣除站点运营额度
//                InComepayQtotaDTO qtotaDTO = new InComepayQtotaDTO();
//                qtotaDTO.setSiteCode(dto.getSiteCode());
//                qtotaDTO.setType(PlatformConstants.SITE_QUOTA_STATUS.PAY_QUOTA.toString());
//                //验证过站点运营额度为正数,返水不受运营额度影响
//                qtotaDTO.setInfluence(PlatformConstants.INFLUENCE_QUOTA.UN_INFLUENCE.toString());
//                qtotaDTO.setUserName(dto.getCreateBy());
//                qtotaDTO.setAmonut(allRebate.toString());
//                qtotaDTO.setRemark(result.getEventName() + "事件返水");
//                ApiResult<?> quotaResult = operatingLinesInfoService.inComepayQuotaApi(qtotaDTO);
//                if (!RPCResult.checkApiResult(quotaResult)) {
//                    logger.info("返水调用站点运营额度接口异常,入参" + qtotaDTO.toString() + ",出参" + quotaResult.toString());
//                    throw new UserException(quotaResult.getCode(), quotaResult.getMessage());
//                }
        if (!save(result)) {
            throw new UserException(ErrorCode.DEFAULT_CODE);
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult<PageInfo<RebateResultDTO>> queryRebateResultAPI(RebateResultDTO dto) {
        Assert.isNull(dto.getSiteId(), "站点数据为空");
        Assert.isNull(dto.getBeginTime());
        Assert.isNull(dto.getEndTime());
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        List<RebateResultDTO> dtoList = rebateResultDao.queryList(dto, page);
        return RPCResult.success(new PageInfo<>(dtoList, dto.getPage(), dto.getLimit(), page.getTotal()));
    }

    @Override
    public ApiResult<PageInfo<RebateResultInfoDTO>> queryRebateResultInfoAPI(RebateResultInfoDTO dto) {
        Assert.isNull(dto.getResultId(), "返水结果数据为空");
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
        QueryWrapper<RebateResultInfoEntity> entityWrapper = new QueryWrapper<RebateResultInfoEntity>()
                .eq("result_id", dto.getResultId())
                .eq("is_del", UserConstant.IS_F);
        IPage<RebateResultInfoEntity> resultInfoEntityIPage = rebateResultInfoDao.selectPage(page, entityWrapper);
        List<RebateResultInfoDTO> dtoList = new ArrayList<>();
        for (RebateResultInfoEntity resultInfoEntity : resultInfoEntityIPage.getRecords()) {
            RebateResultInfoDTO thisDto = new RebateResultInfoDTO();
            BeanUtil.copyProperties(resultInfoEntity, thisDto);


            //获取所属真人游戏返水结果数据
            List<RebateVideoResultInfoEntity> videoResultList = rebateVideoResultInfoDao.selectList(
                    new QueryWrapper<RebateVideoResultInfoEntity>()
                            .eq("result_info_id", resultInfoEntity.getId()));

            List<RebateVideoResultInfoDTO> videoResultDTOS = new ArrayList<>();
            videoResultList.forEach(videoResultEntity -> {
                RebateVideoResultInfoDTO videoResultDTO = new RebateVideoResultInfoDTO();
                BeanUtil.copyProperties(videoResultEntity, videoResultDTO);
                videoResultDTOS.add(videoResultDTO);
            });
            thisDto.setVideoResultInfoDTOS(videoResultDTOS);
            dtoList.add(thisDto);
        }
        return RPCResult.success(new PageInfo<>(dtoList, dto.getPage(), dto.getLimit(), page.getTotal()));
    }

    @Override
    public ApiResult executeCancelRebate(RebateResultDTO dto) {
        Assert.isNull(dto.getId(), "返水结果数据为空");
        Assert.isNull(dto.getCreateBy(), "用户数据为空");

        RebateResultEntity resultEntity = getOne(new QueryWrapper<RebateResultEntity>()
                .eq("id", dto.getId())
                .eq("is_del", UserConstant.IS_F));

        if (resultEntity == null) {
            throw new UserException(UserCodeEnum.RESULT_NOT_EXIST.getCode(), UserCodeEnum.RESULT_NOT_EXIST.getMessage());
        }

        QueryWrapper<RebateResultInfoEntity> ew = new QueryWrapper<>();
        ew.eq("result_id", dto.getId());
        ew.eq("is_del", UserConstant.IS_F);
        if (dto.getReqIds() != null && dto.getReqIds().size() > 0) {
            ew.in("id", dto.getReqIds());
        }
        List<RebateResultInfoEntity> rebateResultInfoEntities = rebateResultInfoDao.selectList(ew);
        //启用多线程循环遍历用户返水数据,进行冲销结算
        String batchNO = UserConstant.OrderNoStartsWith.BATCH + UUID.randomUUID().toString().replace("-", "").toUpperCase();
        String queueUrl = AmazonSQSUtil.getQueueUrl(UserConstant.QueueUrl.INCOME_PAY_QUEUE);
        long timeTest = System.currentTimeMillis();
        long cancelRebate = 0l;

        try {
            //新增统计最后冲销返水金额
            List<Future<Long>> futureList = new ArrayList<Future<Long>>();
            for (RebateResultInfoEntity entity : rebateResultInfoEntities) {
                if (ObjectUtil.equal(entity.getStatus(), UserConstant.REBATE_STATUS)) {
                    futureList.add(exec.submit(new CancelRebateCallable(entity, resultEntity, batchNO, queueUrl, dto.getCreateBy())));
                    resultEntity.setFinalRebateNum(resultEntity.getFinalRebateNum() - 1);
                }
            }

            for (Future<Long> future : futureList) {
                cancelRebate += future.get();
            }
        } catch (Exception e) {
            logger.error("error",e);
        }
        resultEntity.setUpdateTime(new Date());
        resultEntity.setUpdateBy(dto.getCreateBy());
        if (resultEntity.getFinalRebateNum() == 0) {
            resultEntity.setStatus(UserConstant.CANCEL_REBATE_STATUS);
        }
        logger.info(dto.getId() + "返水数据冲销时间-----》" + (System.currentTimeMillis() - timeTest) + "ms");

        /**
         * 出入款队列已同步运营额度
         */
//            //开始返还站点运营额度
//            InComepayQtotaDTO qtotaDTO = new InComepayQtotaDTO();
//            qtotaDTO.setSiteCode(resultEntity.getSiteCode());
//            qtotaDTO.setType(PlatformConstants.SITE_QUOTA_STATUS.IN_QUOTA.toString());
//            qtotaDTO.setUserName(dto.getCreateBy());
//            qtotaDTO.setAmonut(cancelRebate.toString());
//            qtotaDTO.setRemark(resultEntity.getEventName() + "事件返水冲销");
//            ApiResult<?> quotaResult = operatingLinesInfoService.inComepayQuotaApi(qtotaDTO);
//            if (!RPCResult.checkApiResult(quotaResult)) {
//                logger.info("返水冲销调用站点运营额度接口异常,入参" + qtotaDTO.toString() + ",出参" + quotaResult.toString());
//                throw new UserException(quotaResult.getCode(), quotaResult.getMessage());
//            }
        if (!updateById(resultEntity)) {
            throw new UserException(ErrorCode.DEFAULT_CODE);
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult<RebateResultInfoDTO> sumRebateResultsByIds(RebateResultDTO dto) {
        Assert.isNull(dto.getBeginTime(), "缺少开始时间");
        Assert.isNull(dto.getEndTime(), "缺少结束时间");
        Assert.isNull(dto.getSiteCode(), "缺少站点信息");
        if (dto.getReqIds() == null || dto.getReqIds().size() == 0) {
            Assert.isNull("缺少id集合");
        }
        RebateResultInfoDTO resultDTO = rebateResultDao.sumRebateResultsByIds(dto);
        return RPCResult.success(ObjectUtil.isNull(resultDTO) ? new RebateResultInfoDTO() : resultDTO);
    }

    @Override
    public ApiResult<Map<Long, Long>> getUserEventMoneyMap(RebateResultDTO dto) {
        Assert.isNull(dto);
        Assert.isNull(dto.getSiteCode(), "缺少站点信息");
        if (CollectionUtil.isEmpty(dto.getReqIds())) {
            Assert.isNull(null, "缺少用户id信息");
        }
        Map<Long, Long> moneyMap = new HashMap<>();

        //获取id集合内各用户返水金额
        List<Map<String, Long>> rebateMap = rebateResultDao.getUserAllRebateMap(dto);
        if (CollectionUtil.isNotEmpty(rebateMap)) {
            for (Map<String, Long> map : rebateMap) {
                moneyMap.put(map.get("userId"), map.get("allRebate"));
            }
        }
        //获取日工资
        List<Map<String, Long>> dailyBonusList = userBonusDao.selectUsersDailyBonus(dto.getReqIds(), BonusConstant.BonusSettingType.DAILY, dto.getBeginTime(), dto.getEndTime());
        if (CollectionUtil.isNotEmpty(dailyBonusList)) {
            for (Map<String, Long> longLongMap : dailyBonusList) {
                if (moneyMap.get(longLongMap.get("toUserId")) != null) {
                    moneyMap.put(longLongMap.get("toUserId"), moneyMap.get(longLongMap.get("toUserId")) + longLongMap.get("dailyBonus"));
                } else {
                    moneyMap.put(longLongMap.get("toUserId"), longLongMap.get("dailyBonus"));
                }
            }
        }
        return RPCResult.success(moneyMap);
    }

}
