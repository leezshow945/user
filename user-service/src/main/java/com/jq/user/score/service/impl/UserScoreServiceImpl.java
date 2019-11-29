package com.jq.user.score.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.site.service.SiteService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.common.InitParameters;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.ScoreTypeEnum;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserRebateDao;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.service.UserRebateInnerService;
import com.jq.user.exception.UserException;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.score.dao.UserScoreRecordDao;
import com.jq.user.score.dto.UserScoreDTO;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.entity.UserScoreRecordEntity;
import com.jq.user.score.service.*;
import com.jq.user.support.PageUtil;
import com.jq.user.support.RedisUtil;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class UserScoreServiceImpl implements UserScoreInnerService {
    @Resource
    private UserScoreRecordDao userScoreRecordDao;
    @Resource
    private UserDao userDao;
    @Resource
    private UserRebateDao userRebateDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private UserRankInnerService userRankInnerService;
    @Resource
    private UserRankScoreInnerService userRankScoreInnerService;
    @Resource
    private UserRebateInnerService userRebateInnerService;
    @Resource
    private KeyValueService keyValueService;
    @Resource
    private SiteService siteService;
    @Resource
    private UserSignInnerService userSignInnerService;
    @Resource
    private UserProxyInnerService userProxyInnerService;

    private static final Logger logger = LoggerFactory.getLogger(UserScoreServiceImpl.class);


    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findAll(Long siteId, Map map, Page page) {
        if (siteId != null && siteId != 0L) {
            map.put("siteId", siteId);
        }
        return userScoreRecordDao.findAll(map, page);
    }


    /**
     * 判断是否存在流水记录
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isExistRecord(Long siteId, Long userId, String scoreType) {

        QueryWrapper<UserScoreRecordEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        wrapper.eq("site_id", siteId);
        wrapper.eq("score_code", scoreType);
        wrapper.eq("is_del", UserConstant.IS_F);
        //判断当天流水类型
        if (scoreType.equals(ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode()) ||
                scoreType.equals(ScoreTypeEnum.SCORE_TYPE_ORDER.getCode())) {

            return userScoreRecordDao.selectCount(wrapper.apply("to_days(create_time)=to_days(now())")) > 0;
        }
        //判断签到记录是否存在
        if (scoreType.equals(ScoreTypeEnum.SCORE_TYPE_SIGN.getCode())) {
            return userSignInnerService.existSign(siteId, userId);
        }
        return false;
    }

    //人工操作积分封装方法
    @Override
    public boolean manualUpdate(Map map) {
        UserEntity userEntity = userDao.selectById(Convert.toLong(map.get("userId")));
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        if (Convert.toInt(map.get("type")) == 1) {
            if (userEntity.getScore() < Convert.toInt(map.get("scoreVal"))) {
                throw new UserException(UserCodeEnum.SCORE_LACK_CODE.getCode());
            }
        }

        //分布式锁操作用户积分
        String cacheKey = RedisKey.Lock.INCOME_PAY + userEntity.getId();
        RedisUtil redisUtil = new RedisUtil(redisTemplate, cacheKey);
        try {
            if (redisUtil.lock()) {
                Date nowDate = new Date();
                //积分流水入参
                UserScoreRecordEntity userScoreRecordEntity = new UserScoreRecordEntity();
                userScoreRecordEntity.setSiteId(userEntity.getSiteId());
                userScoreRecordEntity.setSiteCode(userEntity.getSiteCode());
                userScoreRecordEntity.setUserId(userEntity.getId());
                userScoreRecordEntity.setUserName(userEntity.getUserName());
                userScoreRecordEntity.setScoreVal(Convert.toInt(map.get("scoreVal")));
                userScoreRecordEntity.setCreateTime(nowDate);
                if (map.get("remark") != null) {
                    userScoreRecordEntity.setRemark(Convert.toStr(map.get("remark")));
                }
                //用户积分修改
                if (Convert.toInt(map.get("type")) == 1) {
                    userEntity.setScore(userEntity.getScore() - Convert.toInt(map.get("scoreVal")));
                    userScoreRecordEntity.setScoreCode(ScoreTypeEnum.SCORE_TYPE_TACK_OUT.getCode());
                    userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_TACK_OUT.getValue());
                } else {
                    userEntity.setScore(scoreAdd(userEntity.getScore(), Convert.toInt(map.get("scoreVal"))));
                    userScoreRecordEntity.setScoreCode(ScoreTypeEnum.SCORE_TYPE_DEPOSIT.getCode());
                    userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_DEPOSIT.getValue());
                }
                userEntity.setUpdateBy(Convert.toStr(map.get("createBy")));
                userScoreRecordEntity.setFinalScore(userEntity.getScore());

                userScoreRecordDao.insert(userScoreRecordEntity);
                if (!userRankInnerService.checkUserScore(userEntity)) {
                    throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                }
                return true;
            }
        } catch (Exception e) {
            throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
        } finally {
            redisUtil.unlock();
        }
        return false;
    }

    //更新用户积分（排除用户充值途径）
    @Override
    public boolean updateUserScore(UserEntity userEntity, String scoreType) {
        String cacheKey = RedisKey.Lock.ADD_SCORE + scoreType + userEntity.getId();
        RedisUtil redisUtil = new RedisUtil(redisTemplate, cacheKey);
        try {
            //根据用户id获取等级
            UserRankEntity userRankEntity = userRankInnerService.findById(userEntity.getUserRankId());
            if (userRankEntity == null) {
                throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode(), UserCodeEnum.RANK_MISS_CODE.getMessage());
            }
            Date nowDate = new Date();

            //检查等级配置的积分值,如果有积分值则开始进行积分更新
            Integer scoreVal = userRankScoreInnerService.checkScoreType(userRankEntity.getId(), scoreType);
            if (scoreVal == 0) {
                return true;//未开启或积分配置值为0 跳出方法
            }

            //判断是否已存在该积分类型流水(首次签到 首次下注 首次充值)
            if (!scoreType.equals(ScoreTypeEnum.SCORE_TYPE_AGENT_RECHARGE.getCode())
                    && isExistRecord(userEntity.getSiteId(), userEntity.getId(), scoreType)) {
                logger.info("检查出---" + userEntity.getId() + "---->" + scoreType + "的积分流水已存在");
                return true;
            }

            //开始增加等级积分
            //首先结算个人充值积分
            userEntity.setScore(scoreAdd(userEntity.getScore(), scoreVal));
            //生成积分流水记录
            UserScoreRecordEntity userScoreRecordEntity = new UserScoreRecordEntity();
            userScoreRecordEntity.setSiteId(userEntity.getSiteId());
            userScoreRecordEntity.setSiteCode(userEntity.getSiteCode());
            userScoreRecordEntity.setUserId(userEntity.getId());
            userScoreRecordEntity.setUserName(userEntity.getUserName());
            userScoreRecordEntity.setScoreVal(scoreVal);
            userScoreRecordEntity.setFinalScore(userEntity.getScore());
            userScoreRecordEntity.setCreateTime(nowDate);
            userScoreRecordEntity.setScoreCode(scoreType);

            if (redisUtil.lock()) {

                //代理首次充值 AGENT_RECHARGE
                if (scoreType.equals(ScoreTypeEnum.SCORE_TYPE_AGENT_RECHARGE.getCode())) {
                    userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_AGENT_RECHARGE.getValue());
                    if (!userRankInnerService.checkUserScore(userEntity) || userScoreRecordDao.insert(userScoreRecordEntity) <= 0) {
                        throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                    }
                }
                //每天首次下注 ORDER
                if (scoreType.equals(ScoreTypeEnum.SCORE_TYPE_ORDER.getCode())) {
                    userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_ORDER.getValue());
                    if (!userRankInnerService.checkUserScore(userEntity) || userScoreRecordDao.insert(userScoreRecordEntity) <= 0) {
                        throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                    }

                }
                //每日签到 SIGN
                if (scoreType.equals(ScoreTypeEnum.SCORE_TYPE_SIGN.getCode())) {
                    userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_SIGN.getValue());
                    if (!userRankInnerService.checkUserScore(userEntity) || userScoreRecordDao.insert(userScoreRecordEntity) <= 0) {
                        throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                    }

                }
                return true;
            }
        } catch (Exception e) {
            throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
        } finally {
            redisUtil.unlock();
        }
        return false;
    }


    //充值更新用户积分方法(只做充值方式处理)
    public boolean updateUserScoreByRecharge(UserEntity userEntity, String money) {
        //根据用户id获取等级
        UserRankEntity userRankEntity = userRankInnerService.findById(userEntity.getUserRankId());
        if (userRankEntity == null) {
            throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode(), UserCodeEnum.RANK_MISS_CODE.getMessage());
        }
        Date nowDate = new Date();

        //设置了充值积分奖励比例(此处只做充值兑换积分处理)
        if (userRankEntity.getRechargeRatio() > 0) {
            //计算出每次充值的积分比例
            int scoreValue = new BigDecimal(Double.parseDouble(money) * userRankEntity.getRechargeRatio() / 100).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
            logger.info(userEntity.getId() + "用户充值金额" + money + ",兑换积分比例" + userRankEntity.getRechargeRatio() + ",奖励积分" + scoreValue);
            userEntity.setScore(scoreAdd(userEntity.getScore(), scoreValue));
            //生成每次充值积分流水
            UserScoreRecordEntity userScoreRecordEntity = new UserScoreRecordEntity();
            userScoreRecordEntity.setSiteId(userEntity.getSiteId());
            userScoreRecordEntity.setSiteCode(userEntity.getSiteCode());
            userScoreRecordEntity.setUserId(userEntity.getId());
            userScoreRecordEntity.setUserName(userEntity.getUserName());
            userScoreRecordEntity.setScoreVal(scoreValue);
            userScoreRecordEntity.setFinalScore(userEntity.getScore());
            userScoreRecordEntity.setCreateTime(nowDate);
            userScoreRecordEntity.setScoreCode(ScoreTypeEnum.SCORE_TYPE_RECHARGE_RATIO.getCode());
            userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_RECHARGE_RATIO.getValue());
            //每次充值生成分布式锁进行积分增加
            String rechargeCacheKey = RedisKey.Lock.ADD_SCORE_RECHARGE + userEntity.getId();
            RedisUtil rechargeRedisUtil = new RedisUtil(redisTemplate, rechargeCacheKey);
            try {
                if (!userRankInnerService.checkUserScore(userEntity) || userScoreRecordDao.insert(userScoreRecordEntity) <= 0) {
                    throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                }
            } catch (Exception e) {
                throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
            } finally {
                rechargeRedisUtil.unlock();
            }
        }
        //进行每日首充的判断
        //获取等级配置的充值金额积分
        Integer scoreVal = userRankScoreInnerService.checkScoreType(userRankEntity.getId(), ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode());
        //如果配置了首充金额并且未存在首充记录
        if(scoreVal>0&&!isExistRecord(userEntity.getSiteId(), userEntity.getId(), ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode())){
            //进行个人首充逻辑
            userEntity.setScore(scoreAdd(userEntity.getScore(), scoreVal));
            //生成每日首充积分流水
            UserScoreRecordEntity userScoreRecordEntity = new UserScoreRecordEntity();
            userScoreRecordEntity.setSiteId(userEntity.getSiteId());
            userScoreRecordEntity.setSiteCode(userEntity.getSiteCode());
            userScoreRecordEntity.setUserId(userEntity.getId());
            userScoreRecordEntity.setUserName(userEntity.getUserName());
            userScoreRecordEntity.setScoreVal(scoreVal);
            userScoreRecordEntity.setFinalScore(userEntity.getScore());
            userScoreRecordEntity.setCreateTime(nowDate);
            userScoreRecordEntity.setScoreCode(ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode());
            userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_RECHARGE.getValue());

            String cacheKey = RedisKey.Lock.ADD_SCORE + ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode() + userEntity.getId();
            RedisUtil redisUtil = new RedisUtil(redisTemplate, cacheKey);
            try {
                if (redisUtil.lock()) {
                    userScoreRecordEntity.setScoreName(ScoreTypeEnum.SCORE_TYPE_RECHARGE.getValue());
                    if (!userRankInnerService.checkUserScore(userEntity) || userScoreRecordDao.insert(userScoreRecordEntity) <= 0) {
                        throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                    }
                }
            } catch (Exception e) {
                throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
            } finally {
                redisUtil.unlock();
            }
        }

        //进入直属上级增加积分业务逻辑
        UserRebateEntity userRebateEntity = userRebateDao.selectById(userEntity.getId());
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userEntity.getId(), userEntity.getSiteId());
        if (userRebateEntity != null && dirHighUserProxy != null && userRebateEntity.getIsProxy().equals(UserConstant.IS_T) && !dirHighUserProxy.getHighUserName().equals(UserCfg.DEFAULT_SYS_USER_NAME)) {
            //为上级增加积分
            UserEntity levelUser = userDao.selectById(dirHighUserProxy.getHighUserId());
            if (levelUser != null) {
                if (!updateUserScore(levelUser, ScoreTypeEnum.SCORE_TYPE_AGENT_RECHARGE.getCode())) {
                    throw new UserException(UserCodeEnum.ADD_SCORE_ERROR.getCode(), UserCodeEnum.ADD_SCORE_ERROR.getMessage());
                }
            }
        }
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findAllRecord(Long siteId, Map map, Page page) {
        if (siteId != null && siteId != 0L) {
            map.put("siteId", siteId);
        }
        return userScoreRecordDao.findAllRecord(map, page);
    }

    @Override
    public ApiResult<PageInfo<UserScoreDTO>> queryScoreListAPI(UserScoreDTO userScoreDTO) {
            Page page = PageUtil.buildPage(userScoreDTO.getPage(), userScoreDTO.getLimit(), userScoreDTO.getOrderDirection(), userScoreDTO.getOrderField());
            Map<String, Object> map = BeanUtil.beanToMap(userScoreDTO, false, true);
            if (ObjectUtil.isNotNull(userScoreDTO.getSiteId()) && userScoreDTO.getSiteId().equals(0)) {
                map.remove("siteId");
            }
            List<Map<String, Object>> allMap = userScoreRecordDao.findAll(map, page);
            List<UserScoreDTO> userScoreDTOS = new ArrayList<>();
            for (Map<String, Object> thisMap : allMap) {
                UserScoreDTO thisDTO = new UserScoreDTO();
                thisDTO = BeanUtil.fillBeanWithMap(thisMap, thisDTO, true);
                userScoreDTOS.add(thisDTO);
            }
            PageInfo<UserScoreDTO> listPageInfo = new PageInfo<>(userScoreDTOS, userScoreDTO.getPage(), userScoreDTO.getLimit(), page.getTotal());
            return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordListAPI(UserScoreDTO userScoreDTO) {
            if (null == userScoreDTO.getOrderField()) {
                userScoreDTO.setOrderField("usr.create_time");
            }
            Page page = PageUtil.buildPage(userScoreDTO.getPage(), userScoreDTO.getLimit(), userScoreDTO.getOrderDirection(), userScoreDTO.getOrderField());
            Map<String, Object> map = BeanUtil.beanToMap(userScoreDTO, false, true);
            if (ObjectUtil.isNotNull(userScoreDTO.getSiteId()) && userScoreDTO.getSiteId().equals(0)) {
                map.remove("siteId");
            }
            List<Map<String, Object>> allMap = userScoreRecordDao.findAllRecord(map, page);
            List<UserScoreDTO> userScoreDTOS = new ArrayList<>();
            for (Map<String, Object> thisMap : allMap) {
                UserScoreDTO thisDTO = new UserScoreDTO();
                thisDTO = BeanUtil.fillBeanWithMap(thisMap, thisDTO, true);
                userScoreDTOS.add(thisDTO);
            }
            PageInfo<UserScoreDTO> listPageInfo = new PageInfo<>(userScoreDTOS, userScoreDTO.getPage(), userScoreDTO.getLimit(), page.getTotal());
            return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordAPI(UserScoreDTO userScoreDTO) {
            Assert.isNull(userScoreDTO.getUserId(), "用户id缺失");
            Assert.isNull(userScoreDTO.getSiteId(), "站点id缺失");
            if (null == userScoreDTO.getOrderField()) {
                userScoreDTO.setOrderField("create_time");
            }
            Page page = PageUtil.buildPage(userScoreDTO.getPage(), userScoreDTO.getLimit(), userScoreDTO.getOrderDirection(), userScoreDTO.getOrderField());
            QueryWrapper<UserScoreRecordEntity> ew = new QueryWrapper<>();
            ew.eq("site_id", userScoreDTO.getSiteId());
            ew.eq("user_id", userScoreDTO.getUserId());
            if (StrUtil.isNotEmpty(userScoreDTO.getScoreCode())) {
                ew.eq("score_code", userScoreDTO.getScoreCode());
            }
            if (StrUtil.isNotEmpty(userScoreDTO.getBeginTime())) {
                ew.ge("date_format(create_time, '%Y-%m-%d')",userScoreDTO.getBeginTime());
            }
            if (StrUtil.isNotEmpty(userScoreDTO.getEndTime())) {
                ew.le("date_format(create_time, '%Y-%m-%d')",userScoreDTO.getEndTime());
            }

            List<UserScoreRecordEntity> userScoreRecordEntities = userScoreRecordDao.selectPage(page, ew).getRecords();

            List<UserScoreDTO> userScoreDTOS = new ArrayList<>();

            for (UserScoreRecordEntity entity : userScoreRecordEntities) {
                UserScoreDTO thisDTO = new UserScoreDTO();
                BeanUtil.copyProperties(entity, thisDTO);
                userScoreDTOS.add(thisDTO);
            }
            PageInfo<UserScoreDTO> listPageInfo = new PageInfo<>(userScoreDTOS, userScoreDTO.getPage(), userScoreDTO.getLimit(), page.getTotal());
            return RPCResult.success(listPageInfo);
    }

    @Override
    public ApiResult<InitParameters> getScoreListParamsAPI(Long siteId) {
            InitParameters params = new InitParameters();
            ApiResult<Map<Long, String>> siteMap = siteService.findSiteMapApi();
            if (RPCResult.checkApiResult(siteMap)) {
                params.setSiteMap(siteMap.getData());
            }
            ApiResult<Map<String, String>> userStatusMap = keyValueService.findKeyValueMapByDictCode(UserConstant.USER_STATUS);
            if (RPCResult.checkApiResult(userStatusMap)) {
                params.setUserStatusMap(userStatusMap.getData());
            }
            params.setRankLevelList(userRankInnerService.getRankLevelList(siteId));
            params.setRebateLevelList(userRebateInnerService.getRebateLevelList(siteId));
            return RPCResult.success(params);
    }

    @Override
    public ApiResult<InitParameters> getScoreRecordListParamsAPI(Long siteId) {
            InitParameters params = new InitParameters();
            ApiResult<Map<Long, String>> siteMap = siteService.findSiteMapApi();
            if (RPCResult.checkApiResult(siteMap)) {
                params.setSiteMap(siteMap.getData());
            }
            ApiResult<Map<String, String>> scoreTypeMap = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE);
            if (RPCResult.checkApiResult(scoreTypeMap)) {
                params.setScoreTypeMap(scoreTypeMap.getData());
            }
            return RPCResult.success(params);
    }

    @Override
    public ApiResult manualUpdateAPI(UserScoreDTO userScoreDTO) {
            Map<String, Object> map = BeanUtil.beanToMap(userScoreDTO, false, true);
            return this.manualUpdate(map) == true ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult updateUserScore(String userId, String scoreType) {
            if (!ScoreTypeEnum.isExist(scoreType)) {
                throw new UserException(UserCodeEnum.SCORETYPE_NOT_EXIST.getCode(), UserCodeEnum.SCORETYPE_NOT_EXIST.getMessage());
            }
            //排除首次充值途径
            if (scoreType.equals(ScoreTypeEnum.SCORE_TYPE_RECHARGE.getCode())) {
                throw new UserException(ErrorCode.DEFAULT_CODE,"更新用户积分接口调用有误");
            }
            UserEntity userEntity = userDao.findById(Long.valueOf(userId));
            if (userEntity == null) {
                throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }

            return this.updateUserScore(userEntity, scoreType) ? RPCResult.success() : RPCResult.fail();
    }

    //充值专用更新用户积分接口
    @Override
    public ApiResult updateUserScoreByRecharge(String userId, String money) {
            Assert.isNull(userId);
            Assert.isNull(money);
            UserEntity userEntity = userDao.findById(Long.valueOf(userId));
            if (userEntity == null) {
                throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }
            return this.updateUserScoreByRecharge(userEntity, money) ? RPCResult.success() : RPCResult.fail();
    }

    /**
     * 增加积分时增加值溢出判断过滤
     *
     * @param
     * @return
     */
    public static Integer scoreAdd(int oldScore, int score) {
        if (score > Integer.MAX_VALUE - oldScore) {
            return Integer.MAX_VALUE;
        }
        return oldScore + score;
    }
}
