package com.jq.user.valid.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.valid.dao.ValidUserSettingDao;
import com.jq.user.valid.dao.ValidUserStatisticsDao;
import com.jq.user.valid.entity.ValidUserSettingEntity;
import com.jq.user.valid.entity.ValidUserStatisticsEntity;
import com.jq.user.valid.service.ValidUserSettingInnerService;
import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.trade.report.api.MemberReportService;
import com.liying.trade.report.resp.CountCapitalAndBetsResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class ValidUserStatisticsServiceImpl extends ServiceImpl<ValidUserStatisticsDao, ValidUserStatisticsEntity>
        implements  ValidUserStatisticsInnerService {

    private final static Logger logger = LoggerFactory.getLogger(ValidUserStatisticsServiceImpl.class);
    @Resource
    private ValidUserSettingInnerService validUserSettingInnerService;
    @Resource
    private ValidUserStatisticsDao validUserStatisticsDao;
    @Resource
    private ValidUserSettingDao validUserSettingDao;
    @Resource
    private MemberReportService rpcMemberReportService;
    private static ExecutorService exec = Executors.newCachedThreadPool();

    private List<ValidUserStatisticsEntity> findAll() {
        return list(new QueryWrapper<ValidUserStatisticsEntity>().eq("is_del", UserConstant.IS_F));
    }

    private List<ValidUserStatisticsEntity> findBySiteId(Long siteId) {
        return list(new QueryWrapper<ValidUserStatisticsEntity>().eq("is_del", UserConstant.IS_F).eq("site_id", siteId));
    }


    @Override
    public void statisticsAllSite() {
        try {
            long start = System.currentTimeMillis();
            List<ValidUserSettingEntity> settingList = validUserSettingInnerService.findAll();
            logger.info(">>>>>>>>>>开始统计有效会员,待统计站点数量{}个<<<<<<<<<<", settingList.size());
            if (CollectionUtil.isEmpty(settingList)) {
                return;
            }
            final CountDownLatch latch = new CountDownLatch(settingList.size());
            for (ValidUserSettingEntity setting : settingList) {
                exec.execute(() -> {
                    this.statisticsBySiteId(setting.getSiteId());
                    latch.countDown();
                });
            }
            try {
                latch.await();
            } catch (InterruptedException e) {
                logger.error("有效会员统计异常发生异常!!!!!");
            }
            long end = System.currentTimeMillis();
            logger.info(">>>>>>>>>>全平台有效会员统计结束,完成统计站点{}个,耗时:{}ms<<<<<<<<<<", settingList.size(), end - start);
        } catch (Exception e) {
            logger.error("有效会员统计异常发生异常",e);
        }
    }


    @Override
    public void validUserDateSaveAndStatistics() {
        try {
            List<ValidUserSettingEntity> all = validUserSettingInnerService.findAll();
            if (CollectionUtil.isEmpty(all)) {
                return;
            }
            long start = System.currentTimeMillis();
            logger.info(">>>>>>>>>>开始所有站点有效会员统计数据入库和统计,待统计站点数{}个<<<<<<<<<<", all.size());
            final CountDownLatch latch = new CountDownLatch(all.size());
            for (ValidUserSettingEntity setting : all) {
                exec.execute(() -> {
                    this.updateValidUserDateBySiteId(setting.getSiteId());
                    latch.countDown();
                });
            }
            latch.await();
            long end = System.currentTimeMillis();
            logger.info(">>>>>>>>>>结束所有站点有效会员统计数据入库和统计,完成站点统计{}个,耗时:{}ms<<<<<<<<<<", all.size(), end - start);
        } catch (Exception e) {
            logger.error("有效会员统计异常发生异常!!!!!",e);
        }
    }

    @Override
    @Transactional
    public boolean updateValidUserDateBySiteId(Long siteId) {
        try {
            ValidUserSettingEntity setting = validUserSettingInnerService.findBySiteId(siteId);
            if (setting == null) {
                return true;
            }
            logger.info(">>>>>>>>>>开始获取站点{}有效会员统计参数<<<<<<<<<<", setting.getSiteCode());
            long start = System.currentTimeMillis();
            ApiResult<List<CountCapitalAndBetsResp>> listApiResult = rpcMemberReportService.countCapitalAndBets(setting.getSiteCode());
            if (!RPCResult.checkApiResult(listApiResult)) {
                logger.error("站点{}有效会员数据查询失败!!!,message:{}", setting.getSiteCode(), listApiResult.getMessage());
                return false;
            }
            List<CountCapitalAndBetsResp> data = listApiResult.getData();
            Map<Long, CountCapitalAndBetsResp> countMap = this.listToMap(data);
            Map<Long, Map<String, Integer>> userInfoMap = this.assembleValidUserInfo(setting.getSiteId());
            logger.info(">>>>>>>>>>站点{}有效会员参数获取{}个<<<<<<<<<<", setting.getSiteCode(), userInfoMap.size());
            List<ValidUserStatisticsEntity> entityList = new ArrayList<>(userInfoMap.size());
            Date now = new Date();
            for (Long userId : userInfoMap.keySet()) {
                ValidUserStatisticsEntity entity = new ValidUserStatisticsEntity();
                Map<String, Integer> userMap = userInfoMap.get(userId);
                CountCapitalAndBetsResp resp = countMap.get(userId);
                if (resp != null) {
                    entity.setRechargeAmount(resp.getIncomeMoney());
                    entity.setWithdrawAmount(resp.getPayMoney());
                    entity.setValidBetAmount(resp.getOrderMoney());
                    entity.setValidOrderNum(Integer.parseInt(String.valueOf(resp.getBetNum())));
                } else {
                    entity.setRechargeAmount(0L);
                    entity.setWithdrawAmount(0L);
                    entity.setValidBetAmount(0L);
                    entity.setValidOrderNum(0);
                }
                entity.setUserId(userId);
                entity.setSiteId(setting.getSiteId());
                entity.setSiteCode(setting.getSiteCode());
                entity.setRegisterDays(Integer.parseInt(String.valueOf(userMap.get("registerDays"))));
                entity.setLoginDays(Integer.parseInt(String.valueOf(userMap.get("loginDays"))));
                entity.setLoginCountNum(userMap.get("loginCount"));
                entity.setHasRealName(Integer.parseInt(String.valueOf(userMap.get("hasRealName"))));
                entity.setIsRepeat(Integer.parseInt(String.valueOf(userMap.get("isRepeat"))));
                entity.setCreateTime(now);
                entity.setUpdateTime(now);
                boolean isValid = userIsValid(setting, entity);
                entity.setIsValid(isValid ? UserConstant.IS_T : UserConstant.IS_F);
                entityList.add(entity);
            }
            long saveOrUpdateStart = System.currentTimeMillis();
            Integer num = validUserStatisticsDao.updateBatch(entityList);
            long saveOrUpdateEnd = System.currentTimeMillis();
            logger.info(">>>>>>>>>>站点{}有效会员参数保存完成,保存数量{}个，耗时：{}ms<<<<<<<<<<", setting.getSiteCode(), userInfoMap.size(), saveOrUpdateEnd - saveOrUpdateStart);
            if (num > 0) {
                long end = System.currentTimeMillis();
                logger.info(">>>>>>>>>>站点{}有效会员参数获取完成,耗时：{}ms<<<<<<<<<<", setting.getSiteCode(), end - start);
            }
            return num > 0;
        } catch (Exception e) {
            logger.error("站点{}有效会员参数获取失败!!!!!", siteId,e);
            return false;
        }
    }

    @Override
    public ApiResult<Integer> countValidUserBySiteIdApi(Long siteId) {
            Assert.isNull(siteId, "siteId为空");
            Integer num = this.countValidUserBySiteId(siteId);
            return RPCResult.success(num);
    }

    private boolean userIsValid(ValidUserSettingEntity setting, ValidUserStatisticsEntity userStatistics) {
        if (setting.getRechargeAmount() != null) {
            if (userStatistics.getRechargeAmount() < setting.getRechargeAmount()) {
                return false;
            }
        }
        if (setting.getWithdrawAmount() != null) {
            if (userStatistics.getWithdrawAmount() < setting.getWithdrawAmount()) {
                return false;
            }
        }
        if (setting.getValidBetAmount() != null) {
            if (userStatistics.getValidBetAmount() < setting.getValidBetAmount()) {
                return false;
            }
        }
        if (setting.getValidOrderNum() != null) {
            if (userStatistics.getValidOrderNum() < setting.getValidOrderNum()) {
                return false;
            }
        }
        if (setting.getLoginDays() != null) {
            if (userStatistics.getLoginDays() < setting.getLoginDays()) {
                return false;
            }
        }
        if (setting.getLoginCountNum() != null) {
            if (userStatistics.getLoginCountNum() < setting.getLoginCountNum()) {
                return false;
            }
        }
        if (UserConstant.IS_T.equals(setting.getHasRealName())) {
            if (UserConstant.IS_F.equals(userStatistics.getHasRealName())) {
                return false;
            }
            if (UserConstant.IS_T.equals(setting.getIsRepeat())) {
                if (UserConstant.IS_F.equals(userStatistics.getIsRepeat())) {
                    return false;
                }
            }
        }
        if (setting.getRegisterDays() != null) {
            if (userStatistics.getRegisterDays() < setting.getRegisterDays()) {
                return false;
            }
        }
        return true;
    }

    @Override
    @Transactional
    public boolean statisticsBySiteId(Long siteId) {
        try {
            Assert.isNull(siteId, "siteId为空");
            ValidUserSettingEntity setting = validUserSettingInnerService.findBySiteId(siteId);
            List<ValidUserStatisticsEntity> statisticsList = findBySiteId(setting.getSiteId());
            logger.info(">>>>>>>>>>开始统计站点{}的有效会员,待统计的会员有{}个<<<<<<<<<<", setting.getSiteCode(), statisticsList.size());
            long siteValidUserStatisticsStart = System.currentTimeMillis();
            List<ValidUserStatisticsEntity> resultValidUser = new ArrayList<>(statisticsList.size());
            Date now = new Date();
            for (ValidUserStatisticsEntity userStatistics : statisticsList) {
                boolean isChange = userValidStatusIsChange(setting, userStatistics);
                if (isChange) {
                    ValidUserStatisticsEntity validUserEntity = new ValidUserStatisticsEntity();
                    validUserEntity.setUserId(userStatistics.getUserId());
                    validUserEntity.setIsValid(UserConstant.IS_T.equals(userStatistics.getIsValid()) ? UserConstant.IS_F : UserConstant.IS_T);
                    validUserEntity.setUpdateTime(now);
                    resultValidUser.add(validUserEntity);
                }
            }
            boolean isSuccess = updateBatchById(resultValidUser);
            long siteValidUserStatisticsEnd = System.currentTimeMillis();
            if (isSuccess) {
                logger.info(">>>>>>>>>>完成站点{}有效会员统计,{}个会员状态发生改变,耗时:{}ms<<<<<<<<<<", setting.getSiteCode(),
                        resultValidUser.size(), siteValidUserStatisticsEnd - siteValidUserStatisticsStart);
            }
            return isSuccess;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Map<Long, Map<String, Integer>> assembleValidUserInfo(Long siteId) {
        Assert.isNull(siteId, "siteId为空");
        List<Map<String, Integer>> list = validUserStatisticsDao.getValidUserInfo(siteId);
        Map<Long, Map<String, Integer>> dataMap = new HashMap<>();
        for (Map<String, Integer> map : list) {
            Long id = Long.parseLong(String.valueOf(map.get("id")));
            dataMap.put(id, map);
        }
        return dataMap;
    }

    @Override
    @Transactional
    public boolean initValidUserData(Long siteId, String siteCode) {
        Map<Long, Map<String, Integer>> userInfoMap = assembleValidUserInfo(siteId);
        List<ValidUserStatisticsEntity> list = new ArrayList<>();
        Date now = new Date();
        for (Long userId : userInfoMap.keySet()) {
            Map<String, Integer> userMap = userInfoMap.get(userId);
            ValidUserStatisticsEntity entity = new ValidUserStatisticsEntity();
            entity.setUserId(userId);
            entity.setSiteId(siteId);
            entity.setSiteCode(siteCode);
            entity.setRechargeAmount(0L);
            entity.setWithdrawAmount(0L);
            entity.setValidBetAmount(0L);
            entity.setValidOrderNum(0);
            entity.setRegisterDays(Integer.parseInt(String.valueOf(userMap.get("registerDays"))));
            entity.setLoginDays(Integer.parseInt(String.valueOf(userMap.get("loginDays"))));
            entity.setLoginCountNum(userMap.get("loginCount"));
            entity.setHasRealName(Integer.parseInt(String.valueOf(userMap.get("hasRealName"))));
            entity.setIsRepeat(Integer.parseInt(String.valueOf(userMap.get("isRepeat"))));
            // 默认为有效
            entity.setIsValid(UserConstant.IS_T);
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            list.add(entity);
        }
        Integer result = validUserStatisticsDao.insertBatch(list);
        return result > 0;
    }

    @Override
    @Transactional
    public boolean initValidUserInfo(UserEntity userEntity) {
        ValidUserSettingEntity settingEntity = validUserSettingDao.selectById(userEntity.getSiteId());
        if (settingEntity == null) {
            return true;
        }
        ValidUserStatisticsEntity entity = new ValidUserStatisticsEntity();
        entity.setUserId(userEntity.getId());
        entity.setSiteId(userEntity.getSiteId());
        entity.setSiteCode(userEntity.getSiteCode());
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        return save(entity);
    }

    @Override
    public Integer countValidUserBySiteId(Long siteId) {
        QueryWrapper<ValidUserStatisticsEntity> ew = new QueryWrapper<>();
        ew.select("count(*)");
        ew.eq("site_id", siteId);
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("is_valid", UserConstant.IS_T);
        return count(ew);
    }

    @Override
    public List<Long> getValidUserIdList(List<Long> idList, Long siteId) {
        ValidUserSettingEntity entity = validUserSettingDao.selectById(siteId);
        if (entity==null){
            return idList;
        }
        return validUserStatisticsDao.getValidUserIdList(idList);
    }


    private boolean userValidStatusIsChange(ValidUserSettingEntity setting, ValidUserStatisticsEntity userStatistics) {
        Integer isValid = userStatistics.getIsValid();
        boolean isChange = UserConstant.IS_T.equals(isValid);
        if (setting.getRechargeAmount() != null) {
            if (userStatistics.getRechargeAmount() < setting.getRechargeAmount()) {
                return isChange;
            }
        }
        if (setting.getWithdrawAmount() != null) {
            if (userStatistics.getWithdrawAmount() < setting.getWithdrawAmount()) {
                return isChange;
            }
        }
        if (setting.getValidBetAmount() != null) {
            if (userStatistics.getValidBetAmount() < setting.getValidBetAmount()) {
                return isChange;
            }
        }
        if (setting.getValidOrderNum() != null) {
            if (userStatistics.getValidOrderNum() < setting.getValidOrderNum()) {
                return isChange;
            }
        }
        if (setting.getLoginDays() != null) {
            if (userStatistics.getLoginDays() < setting.getLoginDays()) {
                return isChange;
            }
        }
        if (setting.getLoginCountNum() != null) {
            if (userStatistics.getLoginCountNum() < setting.getLoginCountNum()) {
                return isChange;
            }
        }
        if (UserConstant.IS_T.equals(setting.getHasRealName())) {
            if (UserConstant.IS_F.equals(userStatistics.getHasRealName())) {
                return isChange;
            }
            if (UserConstant.IS_T.equals(setting.getIsRepeat())) {
                if (UserConstant.IS_F.equals(userStatistics.getIsRepeat())) {
                    return isChange;
                }
            }
        }
        if (setting.getRegisterDays() != null) {
            if (userStatistics.getRegisterDays() < setting.getRegisterDays()) {
                return isChange;
            }
        }
        return !isChange;
    }

    private Map<Long, CountCapitalAndBetsResp> listToMap(List<CountCapitalAndBetsResp> dataList) {
        Map<Long, CountCapitalAndBetsResp> countMap = new HashMap<>();
        for (CountCapitalAndBetsResp resp : dataList) {
            countMap.put(resp.getUserId(), resp);
        }
        return countMap;
    }
}
