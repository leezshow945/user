package com.jq.user.oldbonus.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.code.PlatformCodeEnum;
import com.jq.platform.constants.PlatformConstants;
import com.jq.platform.operating.dto.InComepayQtotaDTO;
import com.jq.platform.operating.dto.OperatingLinesInfoDTO;
import com.jq.platform.operating.service.OperatingLinesInfoService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.service.UserBonusService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.BonusConstant;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserRebateDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.exception.UserException;
import com.jq.user.oldbonus.dao.UserBonusDao;
import com.jq.user.oldbonus.entity.UserBonusEntity;
import com.jq.user.oldbonus.service.UserBonusInnerService;
import com.jq.user.oldbonus.support.Utils;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.support.DateUtil;
import com.jq.user.support.PageUtil;
import com.liying.common.constants.QueueConstants;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.common.util.Results;
import com.liying.game.api.GameCategoryService;
import com.liying.game.api.req.QueryGameCategoryReq;
import com.liying.game.api.resp.GameCategoryResp;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.IncomePayReq;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/6/19
 */
@Service
@Transactional
public class UserBonusServiceImpl implements UserBonusInnerService {

    private static final Logger logger = LoggerFactory.getLogger(UserBonusServiceImpl.class);

    @Autowired
    private UserBonusDao userBonusDao;
    @Autowired
    private UserRebateDao userRebateDao;
    @Autowired
    private GameCategoryService rpcGameCategoryService;

    @Autowired
    private OrderSubtotalService rpcOrderSubtotalService;

    @Autowired
    private KeyValueService rpcKeyValueService;

    @Autowired
    private UserFundService rpcUserFundService;

    @Autowired
    private OperatingLinesInfoService rpcOperatingLinesInfoService;

    @Resource
    private UserProxyInnerService userProxyInnerService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public ApiResult<List<UserBonusDTO>> queryUserBonusByUserIdApi(Long userId, Long siteId, boolean flag, Integer dataType) {
        QueryWrapper<UserBonusEntity> ew = new QueryWrapper<>();
        ew.eq("site_id", siteId);
        ew.eq("data_type", dataType);
        if (flag) {
            ew.eq("user_id", userId);
        } else {
            ew.eq("to_user_id", userId);
        }
        List<UserBonusDTO> list = new ArrayList<>();
        List<UserBonusEntity> userBonusEntities = userBonusDao.selectList(ew);
        if (CollectionUtil.isEmpty(userBonusEntities))
            return RPCResult.success(null);
        for (UserBonusEntity userBonusEntity : userBonusEntities) {
            UserBonusDTO dto = new UserBonusDTO();
            BeanUtil.copyProperties(userBonusEntity, dto);
            list.add(dto);
        }
        return RPCResult.success(list);
    }

    @Override
    public ApiResult updateApi(UserBonusDTO dto) {
        UserBonusEntity entity = new UserBonusEntity();
        BeanUtil.copyProperties(dto, entity);

        return userBonusDao.updateById(entity) > 0 ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<PageInfo<UserBonusDTO>> queryUserBonusDividByPageApi(UserBonusDTO dto) {
        if (null == dto) {
            return RPCResult.fail(UserCodeEnum.PARAM_IS_NULL.getCode(), "入参不合法");
        }
        Page page = PageUtil.buildPage(dto.getPage(), dto.getLimit(), dto.getOrderDirection(), dto.getOrderField());

        // 获取游戏大类集合，组装map
        QueryGameCategoryReq req = new QueryGameCategoryReq();
        req.setPid(UserConstant.IS_ZERO.longValue());// 游戏父类
        req.setLimit(Integer.MAX_VALUE);
        ApiResult<com.liying.common.PageInfo<GameCategoryResp>> apiResult = rpcGameCategoryService.queryGameCategory(req);

        if (!RPCResult.checkApiResult(apiResult)) {
            return RPCResult.fail(ErrorCode.DEFAULT_CODE, apiResult.getMessage());
        }
        Map<String, String> gameCategoryMap = new HashMap<>();
        com.liying.common.PageInfo<GameCategoryResp> data = apiResult.getData();
        if (data != null) {
            List<GameCategoryResp> content = data.getContent();
            for (GameCategoryResp gameCategoryResp : content) {
                gameCategoryMap.put(gameCategoryResp.getCategoryCode(), gameCategoryResp.getCategoryName());
            }
        }
        List<UserBonusDTO> list = userBonusDao.queryUserBonusDividByPage(dto, page);
        if (CollectionUtil.isNotEmpty(list)) {
            List<Long> toUserIdList = new ArrayList<>();
            for (UserBonusDTO userBonusDTO : list) {
                toUserIdList.add(userBonusDTO.getToUserId());
            }
            Long siteId = list.get(0).getSiteId();
            Map<Long, List<Long>> allSubUserIdMap = userProxyInnerService.getAllSubUserIdMap(toUserIdList, siteId);
            for (UserBonusDTO userBonusDTO : list) {
                // 获取代理所有下级
                List<Long> ids = allSubUserIdMap.get(userBonusDTO.getToUserId());
                if (ids == null) {
                    ids = new ArrayList<>();
                }
                ids.add(userBonusDTO.getToUserId());
                if (BonusConstant.BonusSettingType.BONUS == userBonusDTO.getDataType()) {
                    //计算当前周期用户代理线下所有日工资总计
                    Long dailyBonus = getDailyBonus(userBonusDTO, BonusConstant.BonusSettingType.DAILY, ids);
                    userBonusDTO.setDailyWage(dailyBonus);
                } else if (BonusConstant.BonusSettingType.CONTRACT_BONUS == userBonusDTO.getDataType()) {
                    //计算当前周期用户契约日工资总计
                    Long contractDailyBonus = getDailyBonus(userBonusDTO, BonusConstant.BonusSettingType.CONTRACT_DAILY, null);
                    userBonusDTO.setDailyWage(contractDailyBonus);
                }
                userBonusDTO.setCategoryName("");
                String categoryName = gameCategoryMap.get(userBonusDTO.getCategoryId());
                if (StringUtils.isNotEmpty(categoryName)) {
                    userBonusDTO.setCategoryName(categoryName);
                }
            }
        }
        PageInfo<UserBonusDTO> listPageInfo = new PageInfo<>(list, (int) page.getCurrent(), (int) page.getSize(), page.getTotal());
        return RPCResult.success(listPageInfo);
    }


    @Override
    public ApiResult<UserBonusDTO> findById(Long id) {
        UserBonusEntity userBonusEntity = userBonusDao.selectById(id);
        UserBonusDTO dto = new UserBonusDTO();
        if (userBonusEntity != null)
            BeanUtil.copyProperties(userBonusEntity, dto);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<List<UserBonusDTO>> queryUserBonusByIds(List ids, Integer examineState) {
        Assert.isNull(ids, "id集合为空");
        List<UserBonusDTO> list = userBonusDao.selectBatchIdsAndStatus(ids, examineState);
        return RPCResult.success(list);
    }

    @Override
    public ApiResult<PageInfo<UserBonusDTO>> queryUserWagesByPageApi(UserBonusDTO dto) {
        dto.setDataType(BonusConstant.BonusSettingType.DAILY);
        ApiResult<PageInfo<UserBonusDTO>> pageInfoApiResult = queryUserBonusDividByPageApi(dto);
        return pageInfoApiResult;
    }

    @Override
    public ApiResult updateStateApi(Long id, Long userId, String userName, String siteCode) {
        if (id == null) {
            logger.info("id不存在!");
            return RPCResult.fail(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode(), "id不存在!");
        }
        //根据id查询分红记录
        ApiResult<UserBonusDTO> byId = findById(id);
        UserBonusDTO data = byId.getData();
        if (data == null) {
            logger.info("分红、日工资不存在!");
            return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "分红、日工资不存在!");
        }
        if (null == data.getBonus() || data.getBonus() <= 0) {
            logger.info("没有可分发的分红、日工资，无需更改状态!");
            return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "没有可分发的分红、日工资，无需更改状态!");
        }
        if (data.getExamineState() == BonusConstant.ExamineState.INIT) {
            logger.info("契约分红、契约日工资是临时状态!");
            return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "契约分红、契约日工资是临时状态!");
        }
        if (data.getExamineState() == BonusConstant.ExamineState.APPLYED) {
            logger.info("契约分红、契约日工资已是审核状态!");
            return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "契约分红、契约日工资已是审核状态!");
        }
        data.setAuditorId(userId);
        data.setUpdateBy(userName);
        // 派发分红,日工资
        ApiResult apiResult = handBonus(data, siteCode);

        logger.info(String.format("结果:%S", apiResult));
        return apiResult;
    }

    @Override
    public ApiResult updateAllStateApi(String ids, Long userId, String userName, String siteCode) {
        Assert.isNull(ids, "id不存在!");
        String[] id = null;
        if (ids.contains(",")) {
            id = ids.split(",");
        }
        StringBuffer sb = new StringBuffer();
        if (id != null && id.length > 0) {
            for (String str : id) {
                sb.append(UserConstant.SINGLE_QUOTES).append(str).append(UserConstant.SINGLE_QUOTES).append(UserConstant.COMMA);
            }
        }
        if (sb.toString().endsWith(UserConstant.COMMA)) {
            sb = Utils.deleteLastChar(sb);
        }

        List<String> idList = null;
        if (id != null && id.length > 0)
            idList = Arrays.asList(id);
        ApiResult<List<UserBonusDTO>> listApiResult = queryUserBonusByIds(idList, BonusConstant.ExamineState.LOCK);
        List<UserBonusDTO> data = listApiResult.getData();
        if (CollectionUtil.isEmpty(data)) {
            logger.info("分红、日工资不存在!");
            return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "分红、日工资不存在!");
        }
        int count = 0;
        for (UserBonusDTO bonus : data) {
            if (bonus.getBonus() <= 0) {
                continue;
            }
            ApiResult apiResult = handBonus(bonus, siteCode);
            if (ErrorCode.SUCCESS == apiResult.getCode()) {
                count++;
            }
        }
        return RPCResult.success("共审核成功:" + count + "条!");
    }


    @Override
    public ApiResult insertBonusBatch(List<UserBonusDTO> list) {
        Assert.isNull(list, "数据为空");
        for (UserBonusDTO userBonusDTO : list) {
            userBonusDTO.setId(IdWorker.getId());
        }
        userBonusDao.insertBatch(list);
        return RPCResult.success();
    }

    @Override
    public ApiResult<UserBonusDTO> getPastPeriodBonusApi(Long mainId, String gameCategory,
                                                         String playType, Long toUserId,
                                                         String periods, Integer dataType,
                                                         Long siteId, Integer bonusCycle) {
        if (StrUtil.isEmpty(gameCategory)) {
            throw new UserException(UserCodeEnum.GAME_ID_IS_NULL.getCode());
        }
        if (toUserId == null) {
            throw new UserException(UserCodeEnum.USER_ID_IS_NULL.getCode());
        }
        if (dataType == null) {
            throw new UserException(UserCodeEnum.BONUS_TYPE_IS_NULL.getCode());
        }
        if (siteId == null) {
            throw new UserException(UserCodeEnum.SITE_ID_IS_NULL.getCode());
        }
        QueryWrapper<UserBonusEntity> ew = new QueryWrapper<>();
        ew.eq(mainId != null, "main_id", mainId);
        ew.eq("game_category_id", gameCategory);
//            ew.eq(gameCategory.equals("lhc"),"play_type",playType);
        ew.eq("to_user_id", toUserId);
        ew.eq(StrUtil.isNotEmpty(periods), "periods", periods);
        ew.eq("data_type", dataType);
        ew.eq("site_id", siteId);
        ew.eq("bonus_cycle", bonusCycle);
        ew.eq("is_del", UserConstant.IS_F);
        ew.in("examine_state", new Integer[]{1, 2, 3});
//            ew.orderBy("periods",false);
        ew.orderByDesc(true, "periods");
        ew.last("limit 1");
        List<UserBonusEntity> userBonusEntities = userBonusDao.selectList(ew);
        UserBonusDTO dto = new UserBonusDTO();
        if (CollectionUtil.isNotEmpty(userBonusEntities)) {
            UserBonusEntity userBonusEntity = userBonusEntities.get(0);
            BeanUtil.copyProperties(userBonusEntity, dto);
        } else {
            return RPCResult.success(null);
        }
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<Long> getBonusTotalByTypeApi(UserBonusDTO userBonusDTO, int dataType, List<Long> ids) {

        Long dailBonus = this.getDailyBonus(userBonusDTO, dataType, ids);
//            QueryWrapper<UserBonusEntity> ew = new QueryWrapper<>();
//            ew.setSqlSelect("select sum(bonus)");
//            ew.eq("game_category_id",gameCategoryId);
//            ew.eq( StrUtil.isNotEmpty(gameCategoryId),"play_type",playType);
//            ew.eq("data_type",dataType);
//            ew.eq("site_id",siteId);
//            ew.ge("periods",startPeriods);
//            ew.le("periods",endPeriods);
//            ew.in("to_user_id",toUserIdList);
//            List<UserBonusEntity> userBonusEntities = userBonusDao.selectList(ew);
//            if (CollectionUtil.isNotEmpty(userBonusEntities)){
//                Long bonus = userBonusEntities.get(0).getBonus();
//                return RPCResult.success(bonus);
//            }
        return RPCResult.success(dailBonus);
    }

    @Override
    public ApiResult<Boolean> checkUnDistributeRecord(UserBonusMainDTO dto) {
        Assert.isNull(dto.getUserId(), "userId为空");
        Assert.isNull(dto.getSiteId(), "siteId为空");
        QueryWrapper<UserBonusEntity> ew = new QueryWrapper<>();
        ew.select("count(*)");
        ew.eq("user_id", dto.getUserId());
        ew.eq("site_id", dto.getSiteId());
        ew.eq("examine_state", UserConstant.IS_ONE);
        ew.gt("bonus", 0);
        ew.eq("is_del", UserConstant.IS_F);
        if (dto.getSettingType() != null) {
            ew.eq("data_type", dto.getSettingType());
        } else {
            ew.in("data_type", new Integer[]{1, 3});
        }
        Integer num = userBonusDao.selectCount(ew);
        return RPCResult.success(num > 0);
    }

    /**
     * 判断额度是否满足
     *
     * @param siteCode
     * @param money
     * @return
     */
    private boolean isGreaterThanMoney(String siteCode, Long money) {
        ApiResult<OperatingLinesInfoDTO> apiResult = rpcOperatingLinesInfoService.queryLineInfoBySiteCode(siteCode);
        if (!Results.isSuccess(apiResult)) {
            logger.warn(String.format("获取站点额度失败，结果：%s", apiResult));
            return false;
        }
        OperatingLinesInfoDTO infoDTO = apiResult.getData();
        if (null == infoDTO) {
            return false;
        }
        return infoDTO.getCanAmount().longValue() >= money.longValue();
    }

    /**
     * 额度交易
     *
     * @param inComepayQtotaDTO
     */
    private void incomePayQuota(InComepayQtotaDTO inComepayQtotaDTO) {
        logger.info(String.format("更新站点额度，参数：%s", inComepayQtotaDTO));
        ApiResult<?> apiResult = rpcOperatingLinesInfoService.inComepayQuotaApi(inComepayQtotaDTO);
        // 额度不足的情况，本地派发失败
        if (apiResult.getCode() == PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode()) {
            return;
        }

        // 额度扣除失败把要扣除的额度对象放入队列
        if (!Results.isSuccess(apiResult)) {
            logger.warn(String.format("站点额度更新失败，结果：%s", apiResult));
            try {
                amqpTemplate.convertAndSend(QueueConstants.RabbitMessageQueue.SITE_QUOTA_AMOUNT_QUEUE,
                        JSONUtil.toJsonStr(inComepayQtotaDTO));
                logger.info(String.format("额度信息放入队列成功"));

            } catch (Exception e) {
                // 如果是发送异常情况，可手动发送消息
                logger.error(String.format("额度信息放入队列异常 queue_name={%s} msg={%s}",
                        QueueConstants.RabbitMessageQueue.SITE_QUOTA_AMOUNT_QUEUE,
                        JSONUtil.toJsonStr(inComepayQtotaDTO)), e);
            }
        }
    }


    /**
     * 派发分红,日工资,契约分红,契约日工资
     *
     * @param bonusModel
     * @return
     * @author cjf
     */
    private ApiResult handBonus(UserBonusDTO bonusModel, String siteCode) {

        IncomePayReq incomePayReq = new IncomePayReq();
        String str = "";
        String income = "";
        if (BonusConstant.BonusSettingType.BONUS == bonusModel.getDataType()) {
            str = "手动派发分红:";
            income = UserConstant.TradeType.Income.IN_BONUS + "";
        } else if (BonusConstant.BonusSettingType.CONTRACT_BONUS == bonusModel.getDataType()) {
            str = "手动派发契约分红:";
            income = UserConstant.TradeType.Income.IN_CONTRACT_BONUS + "";
        } else if (BonusConstant.BonusSettingType.CONTRACT_DAILY == bonusModel.getDataType()) {
            str = "手动派发契约日工资:";
            income = UserConstant.TradeType.Income.IN_CONTRACT_DAILY + "";
        } else {
            str = "手动派发日工资:";
            income = UserConstant.TradeType.Income.IN_DAILY + "";
        }

        UserEntity userEntity = userDao.findById(bonusModel.getToUserId());
        // 如果是契约分红或契约日工资,从上级用户扣除
        if (BonusConstant.BonusSettingType.CONTRACT_BONUS == bonusModel.getDataType()
                || BonusConstant.BonusSettingType.CONTRACT_DAILY == bonusModel.getDataType()) {
            // 批次号
            String payBatchNo = Utils.genSerialNo(BonusConstant.OrderNoStartsWith.BATCH);
            IncomePayReq outcome = new IncomePayReq();
            outcome.setPayType(UserConstant.PayType.PAY_BALANCE + "");
            outcome.setMoney(String.valueOf(bonusModel.getBonus()));
            outcome.setPayNo(Utils.genSerialNo(BonusConstant.OrderNoStartsWith.OUT));
            outcome.setTradeType(UserConstant.TradeType.EXPENSE + "");
            outcome.setPlatformType(BonusConstant.PlatformType.PC + "");

            if (BonusConstant.BonusSettingType.CONTRACT_BONUS == bonusModel.getDataType()) {
                str = "手动派发下级[" + userEntity.getUserName() + "]契约分红:";
                income = UserConstant.TradeType.Expense.PAY_CONTRACT_BONUS + "";
            } else {
                str = "手动派发下级[" + userEntity.getUserName() + "]契约工资:";
                income = UserConstant.TradeType.Expense.PAY_CONTRACT_BONUS + "";
            }
            outcome.setSubTradeType(income);
            outcome.setRemark(str + (bonusModel.getBonus()) / 100);
            outcome.setUserId(bonusModel.getUserId() + "");
            outcome.setBatchNo(payBatchNo);
            outcome.setIsDemo(UserConstant.IS_ZERO + "");
            outcome.setSiteCode(siteCode);
            outcome.setOperTime(new Date());
            ApiResult<?> apiResult = rpcUserFundService.incomePay(outcome);

            logger.info(String.format("给用户userid=[%s]派发分红[%s]成功，结果：%s", bonusModel.getUserId(), String.valueOf(bonusModel.getBonus()), apiResult.getData()));
            if (ErrorCode.SUCCESS.intValue() != apiResult.getCode()) {
                return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), apiResult.getMessage());
            }
            incomePayReq.setPayType(UserConstant.PayType.PAY_BALANCE + "");
        } else {
            incomePayReq.setPayType(UserConstant.PayType.PAY_SYSTEM + "");

            // 判断站点额度情况
            boolean isGreater = this.isGreaterThanMoney(siteCode, bonusModel.getBonus());
            if (!isGreater) {
                logger.warn(String.format("站点额度不足，代理契约工资记录已锁定。userId:{%s}", bonusModel.getToUserId()));
                return RPCResult.fail(PlatformCodeEnum.QUOTA_NOE_ENOUGH.getCode(), PlatformCodeEnum.QUOTA_NOE_ENOUGH.getMessage());
            }

            // 系统分红扣除站点额度
            InComepayQtotaDTO inComepayQtotaDTO = new InComepayQtotaDTO();
            inComepayQtotaDTO.setSiteCode(siteCode);
            inComepayQtotaDTO.setUserId(String.valueOf(bonusModel.getToUserId()));
            inComepayQtotaDTO.setUserName(bonusModel.getCreateName());
            inComepayQtotaDTO.setAmonut(String.valueOf(bonusModel.getBonus()));
            inComepayQtotaDTO.setType(String.valueOf(PlatformConstants.SITE_QUOTA_STATUS.PAY_QUOTA));
            inComepayQtotaDTO.setRemark(str);
            incomePayQuota(inComepayQtotaDTO);
        }
        // 批次号
        String inBatchNo = Utils.genSerialNo(BonusConstant.OrderNoStartsWith.BATCH);
        //资金接口对接RPC
        incomePayReq.setTradeType(UserConstant.TradeType.INCOME + "");
        incomePayReq.setMoney(String.valueOf(bonusModel.getBonus()));
        incomePayReq.setPayNo(Utils.genSerialNo(BonusConstant.OrderNoStartsWith.IN));
        incomePayReq.setPlatformType(BonusConstant.PlatformType.PC + "");

        incomePayReq.setRemark(str + (bonusModel.getBonus()) / 100);
        incomePayReq.setSubTradeType(income);
        incomePayReq.setUserId(bonusModel.getToUserId().toString());
        incomePayReq.setBatchNo(inBatchNo);
        incomePayReq.setIsDemo(UserConstant.IS_ZERO + "");
        incomePayReq.setSiteCode(siteCode);
        incomePayReq.setOperTime(new Date()); // 手动审核默认当前时间
        ApiResult<?> apiResult = rpcUserFundService.incomePay(incomePayReq);
        logger.info(String.format("给用户userid=[%s]派发分红[%s]成功，结果：%s", bonusModel.getToUserId(),
                String.valueOf(bonusModel.getBonus()), apiResult.getData()));

        if (Objects.equals(ErrorCode.SUCCESS, apiResult.getCode())) {
            bonusModel.setExamineState(BonusConstant.ExamineState.APPLYED);
            SimpleDateFormat fomart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            bonusModel.setUpdateTime(fomart.format(new Date()));
            logger.info("修改" + bonusModel + "...");
            // 修改分红记录
            ApiResult updateResult = updateApi(bonusModel);
            if (ErrorCode.SUCCESS.intValue() != updateResult.getCode()) {
                //失败之后数据回滚
                throw new UserException(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), "审核失败");
            }
            logger.info("修改" + bonusModel + "成功");
            return RPCResult.success("审核成功");
        } else {
            return RPCResult.fail(UserCodeEnum.BONUS_BUSINESS_EXCEPTION.getCode(), apiResult.getMessage());

        }
    }

    @Override
    public Long getTotalBonus(Long userId, Integer dataType) {
        Assert.isNull(userId, "用户id不能为空");
        logger.info("查询用户有效分红累计 用户id : " + userId);
        return userBonusDao.getTotalBonus(userId, dataType);
    }

    @Override
    public Long getTotalMakeWater(Long userId) {
        logger.info(String.format("查询用户累计赚水,userId: %s", userId));
        return userBonusDao.getTotalMakeWater(userId);
    }


    /**
     * 计算当前周期内当前代理线下所有用户的日工资/契约日工资总计
     *
     * @param userBonusDTO
     * @param daily        2 日工资  3 契约日工资
     * @param ids          用户id集合
     * @return
     */
    private Long getDailyBonus(UserBonusDTO userBonusDTO, int dataType, List<Long> ids) {
        if (userBonusDTO.getBonusCycle() == null || StringUtils.isEmpty(userBonusDTO.getPeriods()) ||
                userBonusDTO.getToUserId() == null || StringUtils.isEmpty(userBonusDTO.getCategoryId())) {
            logger.info(String.format("统计用户指定期数的日工资失败参数缺失 {%s}", JSONUtil.toJsonStr(userBonusDTO)));
            throw new UserException(UserCodeEnum.BONUS_PARAM_NOT_AVAILABLE.getCode());
        }
        //获取需要结算的期数
        List<String> countPeriods = getPeriodsByBonusCycle(userBonusDTO.getBonusCycle(), userBonusDTO.getPeriods());

        if (CollectionUtil.isEmpty(countPeriods)) {
            return 0L;
        }
        Long dailyBonus = userBonusDao.selectDailyBonus(dataType, countPeriods, userBonusDTO.getToUserId(), userBonusDTO.getCategoryId(), null, ids);
        return dailyBonus == null ? 0L : dailyBonus;
    }

    /**
     * 获取需要结算的期数
     *
     * @param bonusCycle 分红周期 (0:每月1期，1:每月2期，2:每月3期，3:每天一期,4:每周一期)
     * @return
     */
    private List<String> getPeriodsByBonusCycle(String bonusCycle, String periods) {
        //天
        String day = periods.substring(periods.length() - 2, periods.length());
        //年月
        String yearmonth = periods.substring(0, 6);
        // 获取每月最后一天的天数
        int maxDay = DateUtil.getDaysOfMonth(DateUtil.strToDate(periods, "yyyyMMdd"));

        List<String> dayOne = new ArrayList<>();
        for (int i = 1; i <= maxDay; i++) {

            if (i < 10) {
                dayOne.add(yearmonth + "0" + i);
            } else {
                dayOne.add(yearmonth + i);
            }
        }

        List<String> countPeriods = new ArrayList<>();
        switch (bonusCycle) {
            case BonusConstant.BONUSCYCLE_MONTH_ONE:
                if ("01".equals(day)) {
                    countPeriods = dayOne;
                }
                break;

            case BonusConstant.BONUSCYCLE_MONTH_TWO:
                if ("01".equals(day)) {
                    countPeriods = dayOne.subList(0, 15);
                } else if ("02".equals(day)) {
                    countPeriods = dayOne.subList(15, maxDay);
                }
                break;

            case BonusConstant.BONUSCYCLE_MONTH_THREE:
                if ("01".equals(day)) {
                    countPeriods = dayOne.subList(0, 10);
                } else if ("02".equals(day)) {
                    countPeriods = dayOne.subList(10, 20);
                } else if ("03".equals(day)) {
                    countPeriods = dayOne.subList(20, maxDay);
                }
                break;

            case BonusConstant.BONUSCYCLE_WEEK_ONE:

                // 获取当月每一周开始结束日期
                Date firstDay = DateUtil.strToDate(String.format(yearmonth + "01"), "yyyyMMdd");
                String monthDay = DateUtil.dateToYYYYMMDDString(firstDay);
                List<String> monthWeekList = DateUtil.getWeekByMonth(monthDay);
                for (int week = 0; week < monthWeekList.size(); week++) {
                    if (week + 1 == NumberUtils.toInt(day)) {
                        String beginDate = monthWeekList.get(week).split("/")[0];
                        countPeriods = DateUtil.getWholeWeekYYYYMMDDStr(DateUtil.strToYYMMDDDate(beginDate));
                        break;
                    }
                }
//                //获取当前月第一周开始时间
//                DateTime firstWeekOfMonth = cn.hutool.core.date.DateUtil.beginOfWeek(DateUtil.strToDate(String.format(yearmonth + "01"), "yyyyMMdd"));
//                //获取当前月第二周开始时间
//                Date secondWeekOfMonth = DateUtil.dateAddDay(firstWeekOfMonth, 7);
//                //获取当前月第三周开始时间
//                Date threeWeekOfMonth = DateUtil.dateAddDay(secondWeekOfMonth, 7);
//                //获取当前月第四周开始时间
//                Date fourWeekOfMonth = DateUtil.dateAddDay(threeWeekOfMonth, 7);
//
//                if("1".equals(day)){
//                    countPeriods=DateUtil.getWholeWeekYYYYMMDDStr(firstWeekOfMonth);
//                }else if("2".equals(day)){
//                    countPeriods=DateUtil.getWholeWeekYYYYMMDDStr(secondWeekOfMonth);
//                }else if("3".equals(day)){
//                    countPeriods=DateUtil.getWholeWeekYYYYMMDDStr(threeWeekOfMonth);
//                }else if("4".equals(day)){
//                    countPeriods=DateUtil.getWholeWeekYYYYMMDDStr(fourWeekOfMonth);
//                }
                break;

            case BonusConstant.BONUSCYCLE_DAY_ONE:
                countPeriods.add(periods);
                break;

            default:
                break;
        }
        // 周期除了每天一期的情况，需要累计当前期
        countPeriods.add(periods);
        return countPeriods;
    }
}
