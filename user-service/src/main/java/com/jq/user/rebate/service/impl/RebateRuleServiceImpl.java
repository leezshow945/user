package com.jq.user.rebate.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.report.member.dto.MemberDTO;
import com.jq.report.member.service.MemberService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.rebate.dao.RebateRuleDao;
import com.jq.user.rebate.dao.RebateRuleInfoDao;
import com.jq.user.rebate.dao.RebateVideoRuleDao;
import com.jq.user.rebate.dto.*;
import com.jq.user.rebate.entity.RebateRuleEntity;
import com.jq.user.rebate.entity.RebateRuleInfoEntity;
import com.jq.user.rebate.entity.RebateVideoRuleEntity;
import com.jq.user.rebate.service.RebateRuleInfoInnerService;
import com.jq.user.rebate.service.RebateRuleInnerService;
import com.jq.user.support.PageUtil;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.resp.GroupUsersResp;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.resp.CountTotalEffectiveBetResp;
import com.liying.trade.user.resp.EffectiveBetUserResp;
import com.liying.trade.user.resp.VideoBetUserResp;
import com.liying.trade.user.vo.CountTotalEffectiveBetReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;


@Transactional
@Service
public class RebateRuleServiceImpl extends ServiceImpl<RebateRuleDao, RebateRuleEntity> implements  RebateRuleInnerService {

    private final static Logger logger = LoggerFactory.getLogger(RebateRuleServiceImpl.class);

    @Resource
    private RebateRuleDao userRebateRuleDao;
    @Resource
    private RebateRuleInfoInnerService rebateRuleInfoInnerService;
    @Resource
    private RebateRuleInfoDao userRebateRuleInfoDao;
    @Resource
    private OrderSubtotalService orderSubtotalService;
    @Resource
    private GroupService groupService;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private RebateVideoRuleDao rebateVideoRuleDao;
    @Resource
    private UserDao userDao;
    @Resource
    private MemberService memberService;


    @Override
    public ApiResult<PageInfo<RebateRuleDTO>> queryRuleListAPI(RebateRuleDTO dto) {
            Assert.isNull(dto.getSiteId(), "站点数据为空");
            Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
            QueryWrapper<RebateRuleEntity> ew = new QueryWrapper<>();
            ew.eq("is_del", UserConstant.IS_F);
            ew.eq("site_id", dto.getSiteId());
            ew.like(StrUtil.isNotEmpty(dto.getName()), "name", dto.getName());
            ew.ge(StrUtil.isNotBlank(dto.getBeginTime()), "date_format(create_time, '%Y-%m-%d')", dto.getBeginTime());
            ew.le(StrUtil.isNotBlank(dto.getEndTime()), "date_format(create_time, '%Y-%m-%d')", dto.getEndTime());

            IPage<RebateRuleEntity> ruleEntityIPage = userRebateRuleDao.selectPage(page, ew);
            List<RebateRuleDTO> dtoList = new ArrayList<>();

            for (RebateRuleEntity entity : ruleEntityIPage.getRecords()) {
                RebateRuleDTO newDto = new RebateRuleDTO();
                BeanUtil.copyProperties(entity, newDto);
                dtoList.add(newDto);
            }
            return RPCResult.success(new PageInfo<>(dtoList, dto.getPage(), dto.getLimit(), page.getTotal()));
    }

    public boolean existRuleName(Long siteId, String name, Long id) {
        QueryWrapper<RebateRuleEntity> ew = new QueryWrapper<RebateRuleEntity>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("site_id", siteId);
        ew.eq("name", name);
        ew.ne(id != null, "id", id);
        return count(ew) > 0;
    }

    public boolean existRuleInfo(Long ruleId, Long id, Long total) {
        QueryWrapper<RebateRuleInfoEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("rule_id", ruleId);
        ew.eq("effective_bets", total);
        ew.ne(id != null, "id", id);
        return userRebateRuleInfoDao.selectCount(ew) > 0;
    }


    @Override
    public ApiResult saveRuleAPI(RebateRuleDTO dto) {
            Assert.isNull(dto.getName(), "规则名缺失");
            Assert.isNull(dto.getSiteId(), "站点信息缺失");
            if (dto.getName().length() > 10) {
                return RPCResult.custom(UserCodeEnum.RULENAME_TOOLONG.getCode(), UserCodeEnum.RULENAME_TOOLONG.getMessage());
            }
            if (dto.getSort() == null) {
                dto.setSort(1);
            }
            if (dto.getSort() < 1 || dto.getSort() > 99) {
                return RPCResult.custom(UserCodeEnum.RULE_SORT_CODE.getCode(), UserCodeEnum.RULE_SORT_CODE.getMessage());
            }
            if (existRuleName(dto.getSiteId(), dto.getName(), dto.getId())) {
                return RPCResult.custom(UserCodeEnum.RULENAME_EXIST.getCode(), UserCodeEnum.RULENAME_EXIST.getMessage());
            }
            RebateRuleEntity entity = new RebateRuleEntity();
            BeanUtil.copyProperties(dto, entity);
            Date date = new Date();
            entity.setUpdateTime(date);
            if (dto.getId() == null) {
                entity.setCreateTime(date);
                entity.setCreateBy(entity.getUpdateBy());
            }
            return saveOrUpdate(entity) ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult delRuleAPI(RebateRuleDTO dto) {
            Assert.isNull(dto.getId(), "缺少id");
            Assert.isNull(dto.getSiteId(), "缺少站点信息");
            Assert.isNull(dto.getUpdateBy(), "缺少操作人信息");

            RebateRuleEntity entity = getOne(new QueryWrapper<RebateRuleEntity>()
                    .eq("id", dto.getId())
                    .eq("is_del", UserConstant.IS_F)
                    .eq("site_id", dto.getSiteId()));
            if (entity != null) {
                entity.setIsDel(UserConstant.IS_T);
                entity.setUpdateTime(new Date());
                entity.setUpdateBy(dto.getUpdateBy());

                //逻辑删除返水规则组详情
                RebateRuleInfoEntity infoEntity = new RebateRuleInfoEntity();
                infoEntity.setIsDel(UserConstant.IS_T);
                infoEntity.setUpdateTime(new Date());
                infoEntity.setUpdateBy(dto.getUpdateBy());
                userRebateRuleInfoDao.update(infoEntity, new UpdateWrapper<RebateRuleInfoEntity>()
                        .eq("is_del", UserConstant.IS_F)
                        .eq("rule_id", dto.getId()));

                //物理删除真人返水规则详情
                rebateVideoRuleDao.deleteByRuleId(dto.getId());

                return saveOrUpdate(entity) ? RPCResult.success() : RPCResult.fail();
            }
            return RPCResult.success();
    }

    @Override
    public ApiResult<RebateBetsDTO> queryRebateBets(RebateBetsDTO dto) {
            Assert.isNull(dto.getBeginTime());
            Assert.isNull(dto.getEndTime());
            Assert.isNull(dto.getRuleId());
            Assert.isNull(dto.getSiteId());
            Assert.isNull(dto.getSiteCode());
            Assert.isNull(dto.getGroupId());
            List<Long> userIds = new ArrayList<>();
            String groupName = "";

            //GroupId =0 代表全部分组
            if (!dto.getGroupId().equals(0)) {
                //获取指定现金分组下用户id
                ApiResult<List<GroupUsersResp>> groupApiResult = groupService.queryUsersByGroup(dto.getSiteCode(), dto.getGroupId().toString(), 0);
                logger.info("queryRebateBets 查询用户分组--->分组id：" + dto.getGroupId() + ",出参：" + groupApiResult.toString());
                if (!RPCResult.checkApiResult(groupApiResult)) {
                    return RPCResult.custom(groupApiResult.getCode(), groupApiResult.getMessage());
                }
                if (CollectionUtil.isEmpty(groupApiResult.getData())) {
                    //筛选的现金组内无用户信息 直接返回结果
                    return RPCResult.success(new RebateBetsDTO());
                }
                groupName = groupApiResult.getData().get(0).getGroupName();
                for (GroupUsersResp resp : groupApiResult.getData()) {
                    userIds.add(Long.valueOf(resp.getUserId()));
                }
            } else if (StrUtil.isNotEmpty(dto.getUserName())) {
                //模糊搜索获取用户id
                QueryWrapper<UserEntity> ew = new QueryWrapper<>();
                ew.select("id as userId");
                ew.eq("is_demo", UserConstant.IS_ZERO);
                ew.eq("is_del", UserConstant.IS_ZERO);
                ew.eq("site_id", dto.getSiteId());
                ew.like("user_name", dto.getUserName());
                List<Map<String, Object>> objects = userDao.selectMaps(ew);
                if (CollectionUtil.isEmpty(objects)) {
                    //用户名模糊搜索无结果 直接返回结果
                    return RPCResult.success(new RebateBetsDTO());
                }
                for (Map<String, Object> map : objects) {
                    userIds.add((Long) map.get("userId"));
                }
            }

            //获取规则组下最小投注用来过滤用户
            Long minTotalEffect = rebateRuleInfoInnerService.getMinTotalEffect(dto.getSiteId(), dto.getRuleId());

            //筛选 融合真人彩票投注记录的 用户id
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setUserIdList(userIds);
            memberDTO.setSiteCode(dto.getSiteCode());
            memberDTO.setStartTime(dto.getBeginTime());
            memberDTO.setEndTime(dto.getEndTime());
            memberDTO.setNumber(dto.getPage());
            memberDTO.setSize(dto.getLimit());
            memberDTO.setBetMoney(minTotalEffect);

            ApiResult<PageInfo<Long>> betUserPageApi = memberService.findBetUserPageApi(memberDTO);
            logger.info("queryRebateBets 筛选已投注用户ID--->入参：" + memberDTO.toString() + ",出参：" + betUserPageApi.toString());
            if (!RPCResult.checkApiResult(betUserPageApi)) {
                return RPCResult.custom(betUserPageApi.getCode(), betUserPageApi.getMessage());
            }

            if (CollectionUtil.isEmpty(betUserPageApi.getData().getContent())) {
                //未筛选出 投注用户数据 直接返回结果
                return RPCResult.success(new RebateBetsDTO());
            }
            RebateBetsDTO rebateBetsDTO = new RebateBetsDTO();
            rebateBetsDTO.setPage(dto.getPage());
            rebateBetsDTO.setLimit(dto.getLimit());
            rebateBetsDTO.setTotalElements(betUserPageApi.getData().getTotalElements());
            rebateBetsDTO.setTotalPages(betUserPageApi.getData().getTotalPages());

            //通过筛选的id获取具体游戏类别投注金额
            CountTotalEffectiveBetReq req = new CountTotalEffectiveBetReq();
            req.setUserIds(betUserPageApi.getData().getContent());
            req.setBeginDate(dto.getBeginTime());
            req.setEndDate(dto.getEndTime());
            req.setMinEffectiveBetMoney(minTotalEffect);
            req.setSiteCode(dto.getSiteCode());

            //取得用户彩票真人各游戏类别有效投注数据
            ApiResult<CountTotalEffectiveBetResp> betRespApiResult = orderSubtotalService.countTotalEffectiveBetWithUserIds(req);
            logger.info("queryRebateBets 统计用户有效投注值--->入参：" + req.toString() + ",出参：" + betRespApiResult.toString());
            if (!RPCResult.checkApiResult(betRespApiResult)) {
                return RPCResult.custom(betRespApiResult.getCode(), betRespApiResult.getMessage());
            }

            rebateBetsDTO.setGroupName(groupName);
            rebateBetsDTO.setGroupId(dto.getGroupId());
            rebateBetsDTO.setUserNum(betRespApiResult.getData().getTotalMembers());//统计总人数
            rebateBetsDTO.setBetSum(betRespApiResult.getData().getTotalEffectiveBet());//统计总有效投注
            long rebateSum = 0l;
            List<RebateResultInfoDTO> rebateResultInfos = new ArrayList<>();
            List<EffectiveBetUserResp> betUserResps = betRespApiResult.getData().getEffectiveBetUserList();
            List<Long> userIdList = betUserResps.stream().map(EffectiveBetUserResp::getUserId).collect(Collectors.toList());
            Map<Long, UserProxyEntity> userProxyEntityMap = userProxyInnerService.getDirHighUserProxyMapByIdList(userIdList, dto.getSiteId());
            //遍历用户投注数据,封装到dto类
            for (EffectiveBetUserResp betResp : betRespApiResult.getData().getEffectiveBetUserList()) {
                RebateResultInfoDTO infoDTO = new RebateResultInfoDTO();
                BeanUtil.copyProperties(betResp, infoDTO);
                infoDTO.setUserName(betResp.getUsername());
                UserProxyEntity dirHighUserProxy = userProxyEntityMap.get(betResp.getUserId());
                if (dirHighUserProxy != null) {
                    infoDTO.setHighLevelAccount(dirHighUserProxy.getHighUserName());
                }
                //根据用户投注值筛选出合适的规则组数据
                double thisRebate;
                RebateRuleInfoEntity thisRule = rebateRuleInfoInnerService.getRuleInfoByBet(dto.getSiteId(), dto.getRuleId(), betResp.getEffectiveBets());
                if (thisRule.getGpcRebate() > 0 && betResp.getGpcBets() > 0) {
                    thisRebate = (thisRule.getGpcRebate() / (double) 100) * (betResp.getGpcBets() / (double) 100);
                    infoDTO.setGpcRebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getDpcRebate() > 0 && betResp.getDpcBets() > 0) {
                    thisRebate = (Math.round((thisRule.getDpcRebate() / (double) 100) * (betResp.getDpcBets() / (double) 100)));
                    infoDTO.setDpcRebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getFlcRebate() > 0 && betResp.getFlcBets() > 0) {
                    thisRebate = (Math.round((thisRule.getFlcRebate() / (double) 100) * (betResp.getFlcBets() / (double) 100)));
                    infoDTO.setFlcRebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getTycpRebate() > 0 && betResp.getTycpBets() > 0) {
                    thisRebate = (Math.round((thisRule.getTycpRebate() / (double) 100) * (betResp.getTycpBets() / (double) 100)));
                    infoDTO.setTycpRebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getTyRebate() > 0 && betResp.getTyBets() > 0) {
                    thisRebate = (Math.round((thisRule.getTyRebate() / (double) 100) * (betResp.getTyBets() / (double) 100)));
                    infoDTO.setTyRebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getQtRebate() > 0 && betResp.getQtBets() > 0) {
                    thisRebate = (Math.round((thisRule.getQtRebate() / (double) 100) * (betResp.getQtBets() / (double) 100)));
                    infoDTO.setQtRebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getLhcRebate0() > 0 && betResp.getLhc0Bets() > 0) {
                    thisRebate = (Math.round((thisRule.getLhcRebate0() / (double) 100) * (betResp.getLhc0Bets() / (double) 100)));
                    infoDTO.setLhc0Rebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getLhcRebate1() > 0 && betResp.getLhc1Bets() > 0) {
                    thisRebate = (Math.round((thisRule.getLhcRebate1() / (double) 100) * (betResp.getLhc1Bets() / (double) 100)));
                    infoDTO.setLhc1Rebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getLhcRebate2() > 0 && betResp.getLhc2Bets() > 0) {
                    thisRebate = (Math.round((thisRule.getLhcRebate2() / (double) 100) * (betResp.getLhc2Bets() / (double) 100)));
                    infoDTO.setLhc2Rebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }
                if (thisRule.getLhcRebate3() > 0 && betResp.getLhc3Bets() > 0) {
                    thisRebate = (Math.round((thisRule.getLhcRebate3() / (double) 100) * (betResp.getLhc3Bets() / (double) 100)));
                    infoDTO.setLhc3Rebate(thisRebate > 0 ? Math.round(thisRebate) : 0);
                }

                //总返水金额
                //计算彩票总返水金额
                long sumRebate = infoDTO.getGpcRebate() + infoDTO.getDpcRebate() + infoDTO.getFlcRebate() + infoDTO.getTyRebate()
                        + infoDTO.getTycpRebate() + infoDTO.getLhc0Rebate() + infoDTO.getLhc1Rebate() + infoDTO.getLhc2Rebate() + infoDTO.getLhc3Rebate();

                //封装统计真人游戏返水值
                //返回的真人有效投注已做非0投注筛选，规则组Map已做非0返水值筛选
                List<RebateVideoResultInfoDTO> videoResultDTOS = new ArrayList<>();
                Map<String, Long> videoMap = getVideoRebateMap(thisRule.getId());

                for (VideoBetUserResp videoBet : betResp.getVideoBets()) {
                    Long ruleRebate = videoMap.get(videoBet.getGameCode());
                    //根据真人游戏code从返水规则videoMap取得该游戏返水值
                    if (ObjectUtil.isNotNull(ruleRebate)) {
                        RebateVideoResultInfoDTO videoResultInfoDTO = new RebateVideoResultInfoDTO();
                        BeanUtil.copyProperties(videoBet, videoResultInfoDTO);

                        double videoRebate = (Math.round((ruleRebate / (double) 100) * (videoBet.getGameBet() / (double) 100)));
                        videoResultInfoDTO.setGameRebate(videoRebate > 0 ? Math.round(videoRebate) : 0);
                        sumRebate += videoResultInfoDTO.getGameRebate();
                        videoResultDTOS.add(videoResultInfoDTO);
                    }
                }

                infoDTO.setVideoResultInfoDTOS(videoResultDTOS);

                infoDTO.setAllRebates(sumRebate < thisRule.getRebateMost() ? sumRebate : thisRule.getRebateMost());
                rebateSum += infoDTO.getAllRebates();
                rebateResultInfos.add(infoDTO);
            }
            rebateBetsDTO.setRebateResultInfoDTOList(rebateResultInfos);
            rebateBetsDTO.setRebateSum(rebateSum);
            return RPCResult.success(rebateBetsDTO);


    }

    @Override
    public ApiResult<PageInfo<RebateRuleInfoDTO>> queryRuleInfoListAPI(RebateRuleInfoDTO dto) {
            Assert.isNull(dto.getRuleId(), "缺少规则组id");

            Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());
            QueryWrapper<RebateRuleInfoEntity> ew = new QueryWrapper<>();
            ew.eq("is_del", UserConstant.IS_F);
            ew.eq("rule_id", dto.getRuleId());

            IPage<RebateRuleInfoEntity> ruleInfoEntityIPage = userRebateRuleInfoDao.selectPage(page, ew);
            List<RebateRuleInfoDTO> dtoList = new ArrayList<>();

            for (RebateRuleInfoEntity entity : ruleInfoEntityIPage.getRecords()) {
                RebateRuleInfoDTO ruleInfoDTO = new RebateRuleInfoDTO();
                BeanUtil.copyProperties(entity, ruleInfoDTO);

                //获取所属规则真人游戏返水列表
                List<RebateVideoRuleEntity> videoRuleList = rebateVideoRuleDao.selectList(new QueryWrapper<RebateVideoRuleEntity>()
                        .eq("rule_info_id", ruleInfoDTO.getId()));
                List<RebateVideoRuleDTO> videoRuleDTOS = new ArrayList<>();
                videoRuleList.forEach(videoRuleEntity -> {
                    RebateVideoRuleDTO videoRuleDTO = new RebateVideoRuleDTO();
                    BeanUtil.copyProperties(videoRuleEntity, videoRuleDTO);
                    videoRuleDTOS.add(videoRuleDTO);
                });
                ruleInfoDTO.setVideoRuleDTOList(videoRuleDTOS);
                dtoList.add(ruleInfoDTO);
            }
            return RPCResult.success(new PageInfo<>(dtoList, dto.getPage(), dto.getLimit(), page.getTotal()));
    }

    @Override
    public ApiResult saveRuleInfoAPI(RebateRuleInfoDTO dto) {
            Assert.isNull(dto.getRuleId(), "规则id缺失");
            Assert.isNull(dto.getEffectiveBets(), "有效投注缺失");
            Assert.isNull(dto.getRebateMost(), "返水上限缺失");

            if (dto.getEffectiveBets() <= 0 || dto.getEffectiveBets().toString().length() > 14) {
                return RPCResult.custom(UserCodeEnum.EFFECTIVE_CODE.getCode(), UserCodeEnum.EFFECTIVE_CODE.getMessage());
            }
            if (dto.getRebateMost() <= 0 || dto.getRebateMost().toString().length() > 14) {
                return RPCResult.custom(UserCodeEnum.REBATEMOST_CODE.getCode(), UserCodeEnum.REBATEMOST_CODE.getMessage());
            }
            if (existRuleInfo(dto.getRuleId(), dto.getId(), dto.getEffectiveBets())) {
                return RPCResult.custom(UserCodeEnum.EFFECTIVE_EXIST.getCode(), UserCodeEnum.EFFECTIVE_EXIST.getMessage());
            }
            if (dto.getGpcRebate() < 0 || dto.getGpcRebate() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "高频彩返点范围不合法");
            }
            if (dto.getDpcRebate() < 0 || dto.getDpcRebate() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "低频彩返点范围不合法");
            }
            if (dto.getQtRebate() < 0 || dto.getQtRebate() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "其他返点范围不合法");
            }
            if (dto.getTyRebate() < 0 || dto.getTyRebate() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "体育返点范围不合法");
            }
            if (dto.getTycpRebate() < 0 || dto.getTycpRebate() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "体育彩票返点范围不合法");
            }
            if (dto.getFlcRebate() < 0 || dto.getFlcRebate() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "棋牌返点范围不合法");
            }
            if (dto.getLhcRebate0() < 0 || dto.getLhcRebate0() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "六合彩组0返点范围不合法");
            }
            if (dto.getLhcRebate1() < 0 || dto.getLhcRebate1() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "六合彩组1返点范围不合法");
            }
            if (dto.getLhcRebate2() < 0 || dto.getLhcRebate2() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "六合彩组2返点范围不合法");
            }
            if (dto.getLhcRebate3() < 0 || dto.getLhcRebate3() > 10000) {
                return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), "六合彩组3返点范围不合法");
            }
            for (RebateVideoRuleDTO videoRuleDTO : dto.getVideoRuleDTOList()) {
                if (videoRuleDTO.getGameRebate() < 0 || videoRuleDTO.getGameRebate() > 10000) {
                    return RPCResult.custom(UserCodeEnum.REBATE_RANGE_ERROR.getCode(), videoRuleDTO.getGameName() + "返点范围不合法");
                }
            }

            RebateRuleInfoEntity entity = new RebateRuleInfoEntity();
            BeanUtil.copyProperties(dto, entity);
            Date date = new Date();
            entity.setUpdateTime(date);

            if (dto.getId() == null) {
                entity.setCreateTime(date);
                entity.setCreateBy(entity.getUpdateBy());
                userRebateRuleInfoDao.insert(entity);
            } else {
                //删除旧的真人返水规则
                rebateVideoRuleDao.delete(new QueryWrapper<RebateVideoRuleEntity>()
                        .eq("rule_info_id", dto.getId()));

                userRebateRuleInfoDao.updateById(entity);
            }
            //遍历保存规则组下真人返水规则
            dto.getVideoRuleDTOList().forEach(videoRuleDTO -> {
                RebateVideoRuleEntity videoRuleEntity = new RebateVideoRuleEntity();
                BeanUtil.copyProperties(videoRuleDTO, videoRuleEntity);
                videoRuleEntity.setRuleInfoId(entity.getId());
                rebateVideoRuleDao.insert(videoRuleEntity);
            });

            return RPCResult.success();
    }

    @Override
    public ApiResult delRuleInfoAPI(RebateRuleInfoDTO dto) {
            Assert.isNull(dto.getId(), "缺少id");
            Assert.isNull(dto.getUpdateBy(), "缺少操作人信息");

            RebateRuleInfoEntity param = new RebateRuleInfoEntity();
            param.setIsDel(UserConstant.IS_F);
            param.setId(dto.getId());

            RebateRuleInfoEntity entity = userRebateRuleInfoDao.selectOne(new QueryWrapper<>(param));
            if (entity != null) {
                entity.setIsDel(UserConstant.IS_T);
                entity.setUpdateTime(new Date());
                entity.setUpdateBy(dto.getUpdateBy());
                userRebateRuleInfoDao.updateById(entity);
            }

            rebateVideoRuleDao.delete(new QueryWrapper<RebateVideoRuleEntity>()
                    .eq("rule_info_id", dto.getId()));

            return RPCResult.success();
    }


    @Override
    public ApiResult<RebateRuleInfoDTO> getRuleInfoById(Long id) {
            RebateRuleInfoEntity entity = userRebateRuleInfoDao.selectById(id);
            if (entity == null) {
                throw new UserException(UserCodeEnum.RULEINFO_NOT_EXIST.getCode(), UserCodeEnum.RULEINFO_NOT_EXIST.getMessage());
            }
            RebateRuleInfoDTO ruleInfoDTO = new RebateRuleInfoDTO();
            BeanUtil.copyProperties(entity, ruleInfoDTO);

            //获取所属规则真人游戏返水列表
            List<RebateVideoRuleEntity> videoRuleList = rebateVideoRuleDao.selectList(new QueryWrapper<RebateVideoRuleEntity>()
                    .eq("rule_info_id", id));
            List<RebateVideoRuleDTO> videoRuleDTOS = new ArrayList<>();
            videoRuleList.forEach(videoRuleEntity -> {
                RebateVideoRuleDTO videoRuleDTO = new RebateVideoRuleDTO();
                BeanUtil.copyProperties(videoRuleEntity, videoRuleDTO);
                videoRuleDTOS.add(videoRuleDTO);
            });

            ruleInfoDTO.setVideoRuleDTOList(videoRuleDTOS);
            return RPCResult.success(ruleInfoDTO);
    }

    /**
     * 根据规则组详情id获取真人游戏返水值
     * Map封装  过滤掉0返水值的游戏配置
     *
     * @param ruleInfoId
     * @return
     */
    @Override
    public Map<String, Long> getVideoRebateMap(long ruleInfoId) {
        List<RebateVideoRuleEntity> videoRuleList = rebateVideoRuleDao.selectList(
                new QueryWrapper<RebateVideoRuleEntity>()
                        .gt("game_rebate", 0)
                        .eq("rule_info_id", ruleInfoId));
        Map<String, Long> map = new HashMap<>();
        videoRuleList.forEach(entity -> map.put(entity.getGameCode(), entity.getGameRebate()));
        return map;
    }
}
