package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.domain.service.DomainService;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.constant.UserStatus;
import com.jq.user.customer.dao.BankCardDao;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserInfoDao;
import com.jq.user.customer.dao.UserRebateDao;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.BankCardEntity;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserInfoEntity;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.service.UserInnerService;
import com.jq.user.customer.service.UserRebateInnerService;
import com.jq.user.customer.service.UserRebateTransService;
import com.jq.user.customer.service.UserService;
import com.jq.user.customer.support.CheckUtil;
import com.jq.user.exception.UserException;
import com.jq.user.oldbonus.dao.UserBonusMainDao;
import com.jq.user.oldbonus.entity.UserBonusMainEntity;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.service.UserRankService;
import com.jq.user.score.service.UserScoreInnerService;
import com.jq.user.support.RedisUtil;
import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.resp.SiteDefaultGroupResp;
import com.liying.cash.group.vo.UserBindingDefaultGroupReq;
import com.liying.cash.group.vo.UserGroupRelaReq;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.api.UserBonusService;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.AddUserFundReq;
import com.liying.trade.user.vo.UserBonusReq;
import com.liying.tradehis.order.api.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserRebateTransServiceImpl implements UserRebateTransService {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(UserRebateTransServiceImpl.class);
    @Resource
    private UserRebateDao userRebateDao;
    @Resource
    private UserDao userDao;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private SiteService siteService;
    @Resource
    private com.liying.trade.report.api.UserReportService userReportService;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private UserFundService userFundService;
    @Resource
    private OrderService orderHisService;
    @Resource
    private OrderSubtotalService orderSubtotalService;
    @Resource
    private com.liying.trade.order.api.OrderService orderService;
    @Resource
    private DomainService domainService;
    @Resource
    private UserRankService userRankService;
    @Resource
    private UserBonusMainDao userBonusMainDao;
    @Resource
    private UserService userService;
    @Resource
    private GroupService groupService;
    @Resource
    private UserRebateInnerService userRebateInnerService;
    @Resource
    private UserScoreInnerService userScoreInnerService;
    @Resource
    private BankCardDao bankCardDao;
    @Resource
    private UserBonusService userBonusService;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;
    @Resource
    private StringRedisTemplate template;

    public ApiResult transfer(Long destId, Long sourceId, String destName, String sourceName
            , Integer isTransfer, String managerUserName) {
        long start = System.currentTimeMillis();
        logger.info(String.format("代理迁移开始,源代理: %s,目标代理: %s,迁移是否包含源代理: %s", sourceName, destName, UserConstant.IS_T.equals(isTransfer) ? "是" : "否"));
        Assert.isNull(destId,"目标代理id为空");
        Assert.isNull(sourceId,"源代理id为空");
        if (sourceId.equals(destId)){
            return RPCResult.fail(UserCodeEnum.PROXY_ID_IDENTICAL.getCode(), UserCodeEnum.PROXY_ID_IDENTICAL.getMessage());
        }
        UserRebateEntity sourceUserRebate = userRebateDao.selectById(sourceId);
        if (sourceUserRebate == null) {
            throw new UserException(UserCodeEnum.SOURCE_NOT_EXIST.getCode());
        }
        UserRebateEntity destUserRebate = userRebateDao.selectById(destId);
        if (destUserRebate == null) {
            throw new UserException(UserCodeEnum.DEST_NOT_EXIST.getCode());
        }
        Long siteId = sourceUserRebate.getSiteId();
        String siteCode = sourceUserRebate.getSiteCode();

        // 判断目标代理账号类型（过滤试玩，测试账号）
        UserEntity userDTO = userDao.selectById(destId);
        if (userDTO == null) {
            throw new UserException(UserCodeEnum.OBJECT_NOT_EXIST.getCode());
        }
        // 目标代理不是正式账号
        if (userDTO.getIsDemo() != UserConstant.IS_ZERO) {
            throw new UserException(UserCodeEnum.DEST_USER_TYPE_ERROR.getCode());
        }

        //判断目标代理账号类型是否是代理
        if (destUserRebate.getIsProxy().equals(UserConstant.IS_F)) {
            throw new UserException(UserCodeEnum.GOAL_NOT_PROXY.getCode());
        }
        UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(sourceId, siteId);
        // 迁移是否包含源代理
        boolean bonusFlag = isTransfer.equals(UserConstant.IS_T);
        if (bonusFlag) {
            //判断目标代理是否与源代理的上级一致
            if (destUserRebate.getId().equals(dirHighUserProxy.getUserId())) {
                throw new UserException(UserCodeEnum.GOAL_IDENTICAL.getCode());
            }
        } else {
            //判断源代理有没有下级
            if (sourceUserRebate.getIsProxy().equals(UserConstant.IS_F)) {
                throw new UserException(UserCodeEnum.SOURCE_NOT_PROXY.getCode());
            }
            Integer num = userProxyInnerService.countAllSubUserLine(sourceId, siteId);
            if (num==0) {
                throw new UserException(UserCodeEnum.SOURCE_NOT_SUB.getCode());
            }
        }
        //判断目标代理和源代理是否同一体系，并且目标代理层级小于源代理
        boolean flag =  userProxyInnerService.checkSourceLevelIsNoHighDest(sourceId,destId,siteId);
        if (flag) {
            throw new UserException(UserCodeEnum.TRANSFER_LEVEL_INVALID.getCode());
        }
        //判断源代理返点是否大于目标代理返点，若有一个大于，则不成功
        if (sourceUserRebate.getDpcRebate() > destUserRebate.getDpcRebate()
                || sourceUserRebate.getFlcRebate() > destUserRebate.getFlcRebate()
                || sourceUserRebate.getTycpRebate() > destUserRebate.getTycpRebate()
                || sourceUserRebate.getQtRebate() > destUserRebate.getQtRebate()
                || sourceUserRebate.getTyRebate() > destUserRebate.getTyRebate()
                || sourceUserRebate.getLhcRebate0() > destUserRebate.getLhcRebate0()
                || sourceUserRebate.getLhcRebate1() > destUserRebate.getLhcRebate1()
                || sourceUserRebate.getLhcRebate2() > destUserRebate.getLhcRebate2()
                || sourceUserRebate.getLhcRebate3() > destUserRebate.getLhcRebate3()
                || sourceUserRebate.getGpcRebate() > destUserRebate.getGpcRebate()) {
            throw new UserException(UserCodeEnum.DEST_REBATE_LOW.getCode());
        }
        // 拷贝源代理直属下级,用作分红结算
        List<UserProxyEntity> dirSubUserProxyToSettleList = userProxyInnerService.getDirSubUserProxy(sourceId, siteId);
        // 拷贝源代理所有下级,用作分红结算
        List<UserProxyEntity> allSubUserProxyToSettleList = userProxyInnerService.getAllSubUserProxy(sourceId, siteId);
        // 源代理直属下级id
        List<Long> dirSubUserIdList = userProxyInnerService.userIdToList(dirSubUserProxyToSettleList);
        // 源代理已经所有下级id
        List<Long> allSourceUserId = userProxyInnerService.userIdToList(allSubUserProxyToSettleList);
        allSourceUserId.add(sourceId);
        boolean isSuccess = false;
        if(UserConstant.IS_T.equals(isTransfer)){
            // 迁移包含源代理
             isSuccess = userProxyInnerService.transferUserProxy(sourceId, destId, siteId);
        }else {
            // 迁移不包含源代理
             isSuccess = userProxyInnerService.transferBatch(dirSubUserIdList, destId, siteId);
        }
        if (!isSuccess){
            logger.error(String.format("代理迁移异常: siteCode: %s,源代理: %s,目标代理: %s",siteCode,sourceName,destName));
            throw new UserException(UserCodeEnum.TRANSFER_LEVEL_INVALID.getCode());
        }
        // 更新用户迁移时间
        UserEntity userEntity = new UserEntity();
        userEntity.setTransferTime(new Date());
        userEntity.setUpdateBy(managerUserName);
        userEntity.setUpdateTime(new Date());
        if (UserConstant.IS_T.equals(isTransfer)){
            userEntity.setId(sourceId);
            userDao.updateById(userEntity);
        }else{
            QueryWrapper<UserEntity> ew  =new QueryWrapper<>();
            ew.in("id",dirSubUserIdList);
            userDao.update(userEntity,ew);
        }
        Map<Long, Integer> allSourceUserMap = userProxyInnerService.getUserLevelMap(allSourceUserId, siteId);
        // 获取用户类型
        Map<Long ,Integer> proxyTypeMap = userRebateInnerService.getUserProxyTypeMap(allSourceUserId);
        // 5.迁移玩要处理user_bonus代理分红、契约分红待结算的数据
        // 5.1处理分红待结算数据 源代理所有下级层级有变,则需结算代理分红
        List<UserBonusReq> reqList = new ArrayList<>();
        for (UserProxyEntity userProxyEntity : allSubUserProxyToSettleList) {
            if (UserConstant.IS_F.equals(proxyTypeMap.get(userProxyEntity.getUserId()))) {
                continue;
            }
            UserBonusReq req = new UserBonusReq();
            req.setUserId(userProxyEntity.getUserId());
            req.setSiteId(userProxyEntity.getSiteId());
            req.setSiteCode(userProxyEntity.getSiteCode());
            req.setLevel(allSourceUserMap.get(userProxyEntity.getUserId()));
            req.setSettingType(UserConstant.IS_ZERO);
            reqList.add(req);
        }
        // 如果迁移包含源代理,源代理要结算代理分红和契约分红
        if (bonusFlag) {
            UserBonusReq req1 = new UserBonusReq();
            req1.setUserId(sourceId);
            req1.setSiteId(sourceUserRebate.getSiteId());
            req1.setSiteCode(sourceUserRebate.getSiteCode());
            req1.setLevel(allSourceUserMap.get(sourceId));
            req1.setSettingType(UserConstant.IS_ZERO);
            UserBonusReq req2 = new UserBonusReq();
            BeanUtil.copyProperties(req1, req2);
            req2.setSettingType(UserConstant.IS_ONE);
            reqList.add(req1);
            reqList.add(req2);
        } else {
            // 若迁移不包含源代理,则直属下级的上级有变,则需结算所有下直属级代理的契约分红
            for (UserProxyEntity userProxyEntity : dirSubUserProxyToSettleList) {
                if (UserConstant.IS_F.equals(proxyTypeMap.get(userProxyEntity.getUserId()))) {
                    continue;
                }
                UserBonusReq req = new UserBonusReq();
                req.setUserId(userProxyEntity.getUserId());
                req.setSiteId(userProxyEntity.getSiteId());
                req.setSiteCode(userProxyEntity.getSiteCode());
                req.setLevel(allSourceUserMap.get(userProxyEntity.getUserId()));
                req.setSettingType(UserConstant.IS_ONE);
                reqList.add(req);
            }
        }
        // 5.2处理契约分红待结算数据
        long settleStart = System.currentTimeMillis();
        logger.info(String.format("正在调用代理迁移分红结算服务....,源代理: %s,目标代理: %s,待结算信息: %s", sourceName, destName, reqList.toString()));
        ApiResult<?> settleResult = userBonusService.settle(reqList);
        long settleEnd = System.currentTimeMillis();
        logger.info(String.format("调用代理迁移分红结算结束,源代理: %s,目标代理: %s耗时: %sms", sourceName, destName, settleEnd - settleStart));
        if (!RPCResult.checkApiResult(settleResult)) {
            throw new UserException(settleResult.getCode(), settleResult.getMessage());
        }
        // 6.自动解除源代理与直属下级的契约
        QueryWrapper<UserBonusMainEntity> ew = new QueryWrapper<>();
        ew.eq("site_id",siteId);
        ew.eq("is_del",UserConstant.IS_F);
        ew.eq(bonusFlag,"to_user_id",sourceId);
        ew.eq(!bonusFlag,"user_id",sourceId);
        UserBonusMainEntity main = new UserBonusMainEntity();
        main.setIsDel(UserConstant.IS_T);
        main.setUpdateBy(managerUserName);
        main.setUpdateTime(new Date());
        userBonusMainDao.update(main,ew);
        // 7.修改user_fund表数据
        StringBuilder sb = new StringBuilder();
        if (bonusFlag) {
            sb.append(sourceId);
        } else {
            for (Long dirSubUserId : dirSubUserIdList) {
                sb.append(dirSubUserId).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        long userFundStart = System.currentTimeMillis();
        logger.info(String.format("正在调用资金服务....,源代理: %s,目标代理: %s", sourceName, destName));
        ApiResult<?> apiResult = userFundService.migrationAgent(destId, sb.toString(), siteCode);
        long userFundEnd = System.currentTimeMillis();
        logger.info(String.format("调用资金服务结束,源代理: %s,目标代理: %s耗时: %sms", sourceName, destName, userFundEnd - userFundStart));
        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode(), apiResult.getMessage());
        }
        long end = System.currentTimeMillis();
        logger.info(String.format("代理迁移结束,源代理: %s,目标代理: %s,耗时: %sms", sourceName, destName, end - start));
        return isSuccess ? RPCResult.success() : RPCResult.fail();
    }

    @Override
    public ApiResult<UserDTO> addUserApi(AddUserDTO dto) {
        String cacheKey = RedisKey.Lock.REGISTER_USER + dto.getUserName();
        RedisUtil redisUtil = new RedisUtil(template, cacheKey);
        try {
            long start = System.currentTimeMillis();
            logger.info(String.format("添加代理会员开始,会员信息: %s", dto.getUserName()));
            if (dto.getIsProxy() == null) {
                throw new UserException(UserCodeEnum.ACCOUNT_TYPE_IS_NULL.getCode());
            }
            if (!checkRebate(dto)) {
                throw new UserException(UserCodeEnum.REBATE_ONLY_ONE_DECIMAL.getCode());
            }
            if (!CheckUtil.isUserName(dto.getUserName())) {
                throw new UserException(UserCodeEnum.USERNAME_ILLEGAL.getCode());
            }
            if (redisUtil.lock()) {
                UserEntity user = new UserEntity();
                user.setUserName(dto.getUserName());
                user.setSiteId(dto.getSiteId());
                user.setIsDel(UserConstant.IS_F);
                UserEntity entity = userDao.selectOne(new QueryWrapper<>(user));
                if (entity != null) {
                    throw new UserException(UserCodeEnum.USER_EXIST.getCode());
                }
                // 查询等级
                Long defaultRankId = userRankDao.findDefaultRank(dto.getSiteId());
                if (defaultRankId == null) {
                    throw new UserException(UserCodeEnum.RANK_NOT_INIT.getCode());
                }
                // 查询分组
                ApiResult<SiteDefaultGroupResp> defaultGroup = groupService.getDefaultGroup(dto.getSiteCode());
                if (!RPCResult.checkApiResult(defaultGroup)) {
                    logger.error("查询用户分组失败，message：{}",defaultGroup);
                    throw new UserException(defaultGroup.getCode(), defaultGroup.getMessage());
                }
                long groupId = defaultGroup.getData().getId();
                long userId = IdWorker.getId();
                Date date = new Date();
                Long highUserRebateId;
                if (StrUtil.isEmpty(dto.getHighLevelName())) {
                    // highLevelName为空时上级为厅主
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserName("default");
                    userEntity.setSiteId(dto.getSiteId());
                    UserEntity defaultUser = userDao.selectOne(new QueryWrapper<>(userEntity));
                    if (defaultUser == null) {
                        throw new UserException(UserCodeEnum.DEFAULT_NOT_EXIST.getCode());
                    }
                    highUserRebateId = defaultUser.getId();
                } else {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setUserName(dto.getHighLevelName());
                    userEntity.setSiteId(dto.getSiteId());
                    userEntity.setIsDel(UserConstant.IS_F);
                    userEntity.setIsDemo(dto.getIsDemo());
                    UserEntity highUser = userDao.selectOne(new QueryWrapper<>(userEntity));
                    if (highUser == null) {
                        throw new UserException(UserCodeEnum.HIGH_USER_IS_NULL.getCode());
                    }
                    highUserRebateId = highUser.getId();
                }
                UserRebateEntity highUserRebate = userRebateDao.selectById(highUserRebateId);
                if (UserConstant.IS_F.equals(highUserRebate.getIsProxy())) {
                    throw new UserException(UserCodeEnum.USER_NOT_PROXY.getCode());
                }
                UserEntity highUser = userDao.selectById(highUserRebateId);
                // 验证返点是否小于上级返点
                boolean isLegalRebate = checkRebateRPC(dto, highUserRebate);
                if (!isLegalRebate) {
                    throw new UserException(UserCodeEnum.USER_REBATE_ILLEGAL.getCode());
                }
                // 保存代理关系
                Integer integer = userProxyInnerService.saveUserProxy(highUser.getId(), userId, dto.getUserName());
                if (integer == 0) {
                    throw new UserException(UserCodeEnum.SAVE_PROXY_FAIL.getCode(), UserCodeEnum.SAVE_PROXY_FAIL.getMessage());
                }
                // 保存返点信息
                UserRebateEntity userRebate = new UserRebateEntity();
                userRebate.setId(userId);
                userRebate.setIsProxy(dto.getIsProxy());
                userRebate.setGpcRebate(dto.getGpcRebate().longValue());
                userRebate.setFlcRebate(dto.getFlcRebate().longValue());
                userRebate.setTycpRebate(dto.getTycpRebate().longValue());
                userRebate.setQtRebate(dto.getQtRebate().longValue());
                userRebate.setTyRebate(dto.getTyRebate().longValue());
                userRebate.setLhcRebate0(dto.getLhcRebate0().longValue());
                userRebate.setLhcRebate1(dto.getLhcRebate1().longValue());
                userRebate.setLhcRebate2(dto.getLhcRebate2().longValue());
                userRebate.setLhcRebate3(dto.getLhcRebate3().longValue());
                userRebate.setDpcRebate(dto.getDpcRebate().longValue());
                userRebate.setSiteId(dto.getSiteId());
                userRebate.setSiteCode(dto.getSiteCode());
                userRebate.setCreateTime(date);
                userRebate.setUpdateTime(date);
                userRebateDao.insert(userRebate);
                // 保存用户信息
                UserEntity userEntity = new UserEntity();
                userEntity.setId(userId);
                userEntity.setSiteId(dto.getSiteId());
                userEntity.setUserRankId(defaultRankId);
                userEntity.setUserName(dto.getUserName());
                String md5PwdByRSA = userInnerService.getMD5PwdByRSA(dto.getPassword());
                userEntity.setPassword(md5PwdByRSA);
                if (StrUtil.isNotEmpty(dto.getPayPwd())) {
                    String md5PayPwd = userInnerService.getMD5PwdByRSA(dto.getPayPwd());
                    userEntity.setPayPwd(md5PayPwd);
                }
                int random = RandomUtil.randomInt(6);
                userEntity.setRandom(random);
                userEntity.setSiteId(userRebate.getSiteId());
                userEntity.setSiteCode(dto.getSiteCode());
                userEntity.setCreateTime(date);
                userEntity.setUpdateTime(date);
                userEntity.setIsDemo(dto.getIsDemo());
                userEntity.setCreateBy(dto.getSysUserName());
                userEntity.setStatus(UserStatus.USER_STATUS_10.getCode());
                userEntity.setUpdateBy(dto.getSysUserName());
                userDao.insert(userEntity);
                validUserStatisticsInnerService.initValidUserInfo(userEntity);
                //保存用户详情
                UserInfoEntity info = new UserInfoEntity();
                info.setId(userId);
                String encodeRealName = Base64.encode(dto.getRealName());
                info.setRealName(encodeRealName);
                info.setCreateTime(date);
                info.setUpdateTime(date);
                info.setRegIp(dto.getRegIP());
                info.setRegSource(dto.getPlatformType());
                info.setRegUrl(StrUtil.isEmpty(dto.getRegUrl()) ? "系统来源" : dto.getRegUrl());
                info.setCreateTime(date);
                userInfoDao.insert(info);
                // 保存用户分组
                UserGroupRelaReq req = new UserGroupRelaReq();
                req.setUserId(userId);
                req.setUserName(dto.getUserName());
                req.setGroupId(groupId);
                req.setIsDel(UserConstant.IS_F);
                req.setSiteCode(dto.getSiteCode());
                req.setIsLock(UserConstant.IS_F);
                req.setIsDemo(UserConstant.IS_F);
                req.setCreateTime(new Date());
                ApiResult<String> groupApiResult = groupService.saveGroupRela(req);
                if (!RPCResult.checkApiResult(groupApiResult)) {
                    logger.error("保存用户分组失败,message: {}",groupApiResult);
                    throw new UserException(groupApiResult.getCode(), groupApiResult.getMessage());
                }
                AddUserFundReq addUserFundReq = new AddUserFundReq();
                addUserFundReq.setSiteCode(dto.getSiteCode());
                addUserFundReq.setUserId(userId + "");
                addUserFundReq.setUsername(dto.getUserName());
                addUserFundReq.setSuperiorUserId(highUserRebateId.toString());  //顶级用户
                ApiResult<?> apiResult = userFundService.addUserFund(addUserFundReq);
                if (!RPCResult.checkApiResult(apiResult)) {
                    logger.error("保存用户资金失败,message: {}",apiResult.getMessage());
                    throw new UserException(apiResult.getCode(), apiResult.getMessage());
                }
                UserDTO respDto = new UserDTO();
                respDto.setId(userId);
                respDto.setUserName(dto.getUserName());
                long end = System.currentTimeMillis();
                logger.info(String.format("添加代理会员结束,耗时: %sms, 代理会员信息: %s", end - start, dto.getUserName()));
                return RPCResult.success(respDto);
            }else {
                logger.error("用户:"+dto.getUserName()+"注册时，分布式锁获取失败");
                return RPCResult.custom(UserCodeEnum.USER_REGISTER_FAIL.getCode(),"用户注册失败 ，请重新注册");
            }
        } catch (Exception e) {
            logger.error("保存代理玩家失败",e);
            throw new UserException(ErrorCode.DEFAULT_CODE, ErrorCode.DEFAULT_MSG);
        }finally {
            redisUtil.unlock();
        }
    }

    @Override
    public ApiResult addUserApi(TestUserDTO dto, Long siteId, String updateUserName, String ip) {
        long start = System.currentTimeMillis();
        logger.info(String.format("添加测试账号开始,测试账号信息: %s", dto.getUserName()));

        UserEntity user = new UserEntity();
        user.setUserName(dto.getUserName());
        user.setSiteId(siteId);
        user.setIsDel(UserConstant.IS_F);
        UserEntity entity = userDao.selectOne(new QueryWrapper<>(user));
        if (entity != null) {
            throw new UserException(UserCodeEnum.USER_EXIST.getCode());
        }
        ApiResult<SiteDTO> siteDTOApi = siteService.findSiteDTOById(siteId);
        if (!RPCResult.checkApiResult(siteDTOApi)) {
            return RPCResult.custom(siteDTOApi.getCode(), siteDTOApi.getMessage());
        }
        SiteDTO siteDTO = siteDTOApi.getData();
        if (ObjectUtil.equal(UserConstant.IS_T, siteDTO.getIsDel())) {
            return RPCResult.custom(UserCodeEnum.SITE_DISTBLED.getCode(), UserCodeEnum.SITE_DISTBLED.getMessage());
        }
        if (StrUtil.isBlank(dto.getUserName())) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "用户名为空");
        }
        if (StrUtil.isBlank(dto.getPassword())) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "密码为空");
        }
        UserEntity um = userInnerService.getUserByUserName(dto.getUserName(), siteDTO.getId());
        if (um != null) {
            return RPCResult.custom(UserCodeEnum.USER_INFORMATION_EXIST.getCode(), "用户名已经存在");
        }
        //如果上级用户名为空，则默认为default
        UserEntity highUserModel = new UserEntity();
        if (dto.getHighLevelName().isEmpty()) {
            ApiResult<UserDTO> userByUserNameApi1 = userService.getUserByUserNameApi(UserCfg.DEFAULT_SYS_USER_NAME, siteId);
            if (!RPCResult.checkApiResult(userByUserNameApi1)) {
                return RPCResult.custom(userByUserNameApi1.getCode(), userByUserNameApi1.getMessage());
            }
            UserDTO defaultModel = userByUserNameApi1.getData();
            dto.setHighLevelId(defaultModel.getId());
            dto.setHighLevelName(defaultModel.getUserName());
        }
        highUserModel = userDao.findByUserName(dto.getHighLevelName(), siteId);
        if (null == highUserModel) {
            return RPCResult.custom(UserCodeEnum.HIGH_USER_IS_NULL.getCode(), UserCodeEnum.HIGH_USER_IS_NULL.getMessage());
        }
        dto.setHighLevelId(highUserModel.getId());
        //查找上级返点
        Map<String, Object> highUser = userRebateDao.findByUserNameAndSiteId(highUserModel.getUserName(), siteId);
        //与上级比较返点
        if (NumberUtils.toLong(dto.getRebate0()) > NumberUtils.toLong(highUser.get("gpcRebate").toString())
                || NumberUtils.toLong(dto.getRebate0()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "高频彩种" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate1()) > NumberUtils.toLong(highUser.get("flcRebate").toString())
                || NumberUtils.toLong(dto.getRebate1()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "棋牌" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate2()) > NumberUtils.toLong(highUser.get("tycpRebate").toString())
                || NumberUtils.toLong(dto.getRebate2()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "体育彩票" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate3()) > NumberUtils.toLong(highUser.get("qtRebate").toString())
                || NumberUtils.toLong(dto.getRebate3()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "其他彩种" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate4()) > NumberUtils.toLong(highUser.get("tyRebate").toString())
                || NumberUtils.toLong(dto.getRebate4()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "体育返点" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate5()) > NumberUtils.toLong(highUser.get("dpcRebate").toString())
                || NumberUtils.toLong(dto.getRebate5()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "低频彩返点" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate6()) > NumberUtils.toLong(highUser.get("lhcRebate0").toString())
                || NumberUtils.toLong(dto.getRebate6()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "六合彩组0返点" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate7()) > NumberUtils.toLong(highUser.get("lhcRebate1").toString())
                || NumberUtils.toLong(dto.getRebate7()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "六合彩组1返点" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate8()) > NumberUtils.toLong(highUser.get("lhcRebate2").toString())
                || NumberUtils.toLong(dto.getRebate8()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "六合彩组2返点" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        if (NumberUtils.toLong(dto.getRebate9()) > NumberUtils.toLong(highUser.get("lhcRebate3").toString())
                || NumberUtils.toLong(dto.getRebate9()) < 0) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), "六合彩组3返点" + UserCodeEnum.USER_REBATE_ILLEGAL.getMessage());
        }
        UserEntity userModel = new UserEntity();
        //查询等级
        ApiResult<List<UserRankDTO>> listApiResultApi = userRankService.queryRankBySiteIdAPI(siteId);
        if (!RPCResult.checkApiResult(listApiResultApi)) {
            return RPCResult.custom(listApiResultApi.getCode(), listApiResultApi.getMessage());
        }
        List<UserRankDTO> userRankList = listApiResultApi.getData();
        if (ObjectUtil.isNull(userRankList)) {
            return RPCResult.custom(UserCodeEnum.RANK_MISS_CODE.getCode(), UserCodeEnum.RANK_MISS_CODE.getMessage());
        }
        UserRankDTO userRankModel = userRankList.get(0);
        userModel.setUserRankId(userRankModel.getId());
        //登录密码md5加密
        String md5Pwd = SecureUtil.md5(dto.getPassword());
        userModel.setPassword(md5Pwd);
        // 交易密码，默认1234
        userModel.setPayPwd(SecureUtil.md5(UserCfg.DEFAULT_PAY_PWD));
        if (StringUtils.isNotBlank(dto.getPayPassword())) {
            userModel.setPayPwd(SecureUtil.md5(dto.getPayPassword()));
        }
        userModel.setUserName(dto.getUserName());
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setRealName(dto.getRealName());
        Long id = IdWorker.getId();
        userInfoEntity.setId(id);
        userInfoEntity.setCreateTime(new Date());
        userInfoEntity.setUpdateTime(new Date());
        userInfoEntity.setRegIp("系统来源");
        userInfoEntity.setRegUrl("系统来源");
        userModel.setId(id);
        userModel.setSiteId(siteDTO.getId());
        userModel.setSiteCode(siteDTO.getSiteCode());
        userModel.setStatus(UserStatus.USER_STATUS_10.getCode());
        userModel.setCreateBy(updateUserName);
        userModel.setCreateTime(new Date());
        userModel.setUpdateBy(updateUserName);
        userModel.setRandom(RandomUtil.randomInt(6));
        userModel.setUpdateTime(new Date());
        userModel.setIsDel(UserConstant.IS_F);
        userModel.setIsDemo(UserConstant.IS_F);
        if (ObjectUtil.equal(UserConstant.IS_TEST, dto.getIsDemo())) {// 测试账户
            userModel.setIsDemo(UserConstant.IS_TEST);
        }
        userModel.setRemark("");
        UserEntity userEntity = userInnerService.saveUser(userModel, userInfoEntity, siteId, UserConstant.IS_TEST, null);
        Long userId = userEntity.getId();
        logger.info(String.format("保存成功：userId:[{%s}]", userId));
        //保存用户分组关系
        UserBindingDefaultGroupReq userBindingDefaultGroupReq = new UserBindingDefaultGroupReq();
        userBindingDefaultGroupReq.setUserId(userEntity.getId());
        userBindingDefaultGroupReq.setIsDemo(userEntity.getIsDemo().toString());
        userBindingDefaultGroupReq.setSiteCode(userEntity.getSiteCode());
        userBindingDefaultGroupReq.setUserName(userEntity.getUserName());
        ApiResult apiResult = groupService.userBindingDefaultGroup(userBindingDefaultGroupReq);
        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode(), apiResult.getMessage());
        }
        //初始化资金记录
        AddUserFundReq addUserFundReq = new AddUserFundReq();
        addUserFundReq.setSiteCode(userEntity.getSiteCode());
        addUserFundReq.setUserId(Convert.toStr(userEntity.getId()));
        addUserFundReq.setUsername(userEntity.getUserName());
        addUserFundReq.setSuperiorUserId(dto.getHighLevelId().toString());  //顶级用户
        ApiResult apiResultUserFund = userFundService.addUserFund(addUserFundReq);
        if (!RPCResult.checkApiResult(apiResultUserFund)) {
            throw new UserException(apiResultUserFund.getCode(), apiResultUserFund.getMessage());
        }
        logger.info("开始保存代理关系");
        Integer integer = userProxyInnerService.saveUserProxy(dto.getHighLevelId(), id, dto.getUserName());
        if (integer==0){
            throw new UserException(UserCodeEnum.SAVE_PROXY_FAIL.getCode(),UserCodeEnum.SAVE_PROXY_FAIL.getMessage());
        }
        //保存用户返点
        logger.info("开始保存用户返点信息");
        UserRebateEntity userRebateModel = new UserRebateEntity();
        userRebateModel.setCreateTime(new Date());
        userRebateModel.setId(userEntity.getId());
        userRebateModel.setIsProxy(dto.getIsProxy());
        userRebateModel.setGpcRebate(NumberUtils.toLong(dto.getRebate0()));
        userRebateModel.setFlcRebate(NumberUtils.toLong(dto.getRebate1()));
        userRebateModel.setTycpRebate(NumberUtils.toLong(dto.getRebate2()));
        userRebateModel.setQtRebate(NumberUtils.toLong(dto.getRebate3()));
        userRebateModel.setTyRebate(NumberUtils.toLong(dto.getRebate4()));
        userRebateModel.setDpcRebate(NumberUtils.toLong(dto.getRebate5()));
        userRebateModel.setLhcRebate0(NumberUtils.toLong(dto.getRebate6()));
        userRebateModel.setLhcRebate1(NumberUtils.toLong(dto.getRebate7()));
        userRebateModel.setLhcRebate2(NumberUtils.toLong(dto.getRebate8()));
        userRebateModel.setLhcRebate3(NumberUtils.toLong(dto.getRebate9()));
        userRebateModel.setUpdateTime(new Date());
        userRebateModel.setSiteId(siteDTO.getId());
        userRebateModel.setSiteCode(siteDTO.getSiteCode());
        this.saveUserRebate(userRebateModel,dto.getHighLevelId());
        long end = System.currentTimeMillis();
        logger.info(String.format("添加测试账号结束,耗时: %sms, 测试账号信息: %s", end - start, dto.getUserName()));
        return RPCResult.success();
    }

    @Override
    public ApiResult updateBaseInfoApi(UserRebateUpdateDTO dto) {
        long start = System.currentTimeMillis();
        logger.info(String.format("更新用户基本信息开始,用户信息: %s", dto.getUserName()));
        Long userId = dto.getUserId();
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity == null || UserConstant.IS_T.equals(userEntity.getIsDel())) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (!"default".equals(userEntity.getUserName())) {
            UserRebateEntity oldUserRebate = userRebateDao.selectById(userId);
            UserProxyEntity dirHighUserProxy = userProxyInnerService.getDirHighUserProxy(userId, userEntity.getSiteId());
            if (dirHighUserProxy == null) {
                return RPCResult.custom(UserCodeEnum.HIGH_USER_IS_NULL.getCode(), UserCodeEnum.HIGH_USER_IS_NULL.getMessage());
            }
            UserRebateEntity highUserRebate = userRebateDao.selectById(dirHighUserProxy.getHighUserId());
            if (!checkSelfRebate(dto, userEntity, oldUserRebate, highUserRebate)) {
                throw new UserException(UserCodeEnum.USER_REBATE_ILLEGAL.getCode());
            }
        }
        if (StrUtil.isNotEmpty(dto.getPassword()) || StrUtil.isNotEmpty(dto.getPayPwd())) {
            // 更新密码
            UserEntity user = new UserEntity();
            user.setId(userId);
            if (StrUtil.isNotEmpty(dto.getPassword())) {
                String md5Password = userInnerService.getMD5PwdByRSA(dto.getPassword());
                user.setPassword(md5Password);
                user.setLastChangePwdTime(new Date());
                userService.kickOutUserApi(userId);
            }
            if (StrUtil.isNotEmpty(dto.getPayPwd())) {
                String payPwd = userInnerService.getMD5PwdByRSA(dto.getPayPwd());
                user.setPayPwd(payPwd);
            }
            user.setUpdateTime(new Date());
            userDao.updateById(user);
        }
        if (StrUtil.isNotEmpty(dto.getRealName()) || StrUtil.isNotEmpty(dto.getMobile())) {
            if (!CheckUtil.isRealName(dto.getRealName())) {
                return RPCResult.custom(UserCodeEnum.REALNAME_ILLEGAL.getCode(), UserCodeEnum.REALNAME_ILLEGAL.getMessage());
            }
            //更新真实姓名
            UserInfoEntity userInfo = new UserInfoEntity();
            userInfo.setId(userId);
            if (StrUtil.isNotEmpty(dto.getRealName())) {
                String realName = Base64.encode(dto.getRealName());
                userInfo.setRealName(realName);
                // 更新银行卡的持卡人信息
                QueryWrapper<BankCardEntity> ew = new QueryWrapper<>();
                ew.eq("is_del", UserConstant.IS_F);
                ew.eq("user_id", userId);
                List<BankCardEntity> bankCardEntities = bankCardDao.selectList(ew);
                for (BankCardEntity bankCardEntity : bankCardEntities) {
                    bankCardEntity.setCardUserName(realName);
                    bankCardEntity.setUpdateTime(new Date());
                    bankCardDao.updateById(bankCardEntity);
                }
            }
            if (StrUtil.isNotEmpty(dto.getMobile())) {
                if (!CheckUtil.isMobile(dto.getMobile())) {
                    throw new UserException(UserCodeEnum.MOBILE_ILLEGAL.getCode());
                }
                String mobile = Base64.encode(dto.getMobile());
                userInfo.setMobile(mobile);
            }
            userInfo.setUpdateTime(new Date());
            userInfoDao.updateById(userInfo);
        }
        // 保存返点信息
        UserRebateEntity rebate = new UserRebateEntity();
        rebate.setId(userId);
        rebate.setGpcRebate(new BigDecimal(dto.getGpcRebate()).longValue());
        rebate.setFlcRebate(new BigDecimal(dto.getFlcRebate()).longValue());
        rebate.setTycpRebate(new BigDecimal(dto.getTycpRebate()).longValue());
        rebate.setQtRebate(new BigDecimal(dto.getQtRebate()).longValue());
        rebate.setTyRebate(new BigDecimal(dto.getTyRebate()).longValue());
        rebate.setDpcRebate(new BigDecimal(dto.getDpcRebate()).longValue());
        rebate.setLhcRebate0(new BigDecimal(dto.getLhcRebate0()).longValue());
        rebate.setLhcRebate1(new BigDecimal(dto.getLhcRebate1()).longValue());
        rebate.setLhcRebate2(new BigDecimal(dto.getLhcRebate2()).longValue());
        rebate.setLhcRebate3(new BigDecimal(dto.getLhcRebate3()).longValue());
        rebate.setUpdateTime(new Date());
        Integer result = userRebateDao.updateById(rebate);
        long end = System.currentTimeMillis();
        logger.info(String.format("更新用户信息成功,耗时: %s ,信息: %s", end - start, dto.getUserName()));
        return result > 0 ? RPCResult.success(true) : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "更新用户信息失败");
    }

    @Override
    public ApiResult updateBaseInfoApi(UpdateTestUserDTO dto, Long siteId) {
        long start = System.currentTimeMillis();
        String userName = dto.getLoginName();
        UserEntity user = new UserEntity();
        user.setIsDel(UserConstant.IS_F);
        user.setUserName(userName);
        user.setSiteId(siteId);
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<>(user));
        if (userEntity == null || UserConstant.IS_T.equals(userEntity.getIsDel())) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (StrUtil.isNotEmpty(dto.getLoginPwd())) {
            String md5Password = SecureUtil.md5(dto.getLoginPwd());
            // 更新密码
            userEntity.setPassword(md5Password);
            userEntity.setUpdateTime(new Date());
            userEntity.setLastChangePwdTime(new Date());
            userDao.updateById(userEntity);
        }
        if (StrUtil.isNotEmpty(dto.getNickName()) || StrUtil.isNotEmpty(dto.getMobile())) {
            if (!CheckUtil.isRealName(dto.getNickName())) {
                return RPCResult.custom(UserCodeEnum.REALNAME_ILLEGAL.getCode(), UserCodeEnum.REALNAME_ILLEGAL.getMessage());
            }
            //更新真实姓名
            UserInfoEntity userInfo = new UserInfoEntity();
            userInfo.setId(userEntity.getId());
            if (StrUtil.isNotEmpty(dto.getNickName())) {
                String realName = Base64.encode(dto.getNickName());
                userInfo.setRealName(realName);
            }
            if (StrUtil.isNotEmpty(dto.getMobile())) {
                String mobile = Base64.encode(dto.getMobile());
                userInfo.setMobile(mobile);
            }
            userInfo.setUpdateTime(new Date());
            userInfoDao.updateById(userInfo);
        }
        // 保存返点信息
        UserRebateEntity rebate = new UserRebateEntity();
        rebate.setId(userEntity.getId());
        rebate.setGpcRebate(NumberUtils.toLong(dto.getRebate0()));
        rebate.setFlcRebate(NumberUtils.toLong(dto.getRebate1()));
        rebate.setTycpRebate(NumberUtils.toLong(dto.getRebate2()));
        rebate.setQtRebate(NumberUtils.toLong(dto.getRebate3()));
        rebate.setTyRebate(NumberUtils.toLong(dto.getRebate4()));
        rebate.setDpcRebate(NumberUtils.toLong(dto.getRebate5()));
        rebate.setLhcRebate0(NumberUtils.toLong(dto.getRebate6()));
        rebate.setLhcRebate1(NumberUtils.toLong(dto.getRebate7()));
        rebate.setLhcRebate2(NumberUtils.toLong(dto.getRebate8()));
        rebate.setLhcRebate3(NumberUtils.toLong(dto.getRebate9()));
        rebate.setUpdateTime(new Date());
        Integer result = userRebateDao.updateById(rebate);
        long end = System.currentTimeMillis();
        logger.info(String.format("更新测试账户信息成功,耗时: %s ,信息: %s", end - start, dto.getLoginName()));
        return result > 0 ? RPCResult.success(true) : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "更新测试账户信息失败");
    }

    @Override
    public ApiResult transferBatchApi(Long destId, String destName, String userIds, String managerUserName) {
        long start = System.currentTimeMillis();
        logger.info(String.format("会员批量迁移开始, 目标代理: %s,会员id: %s", destName, userIds));
        if (destId == null || StrUtil.isEmpty(userIds) || StringUtils.isEmpty(destName)) {
            logger.error("参数为空");
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode());
        }
        UserRebateEntity destUserRebate = userRebateDao.selectById(destId);
        if (destUserRebate == null) {
            logger.error("不存在该代理");
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        // 判断目标代理账号类型是否是代理
        if (UserConstant.IS_F.equals(destUserRebate.getIsProxy())) {
            logger.error("目标代理账号类型不能为会员");
            throw new UserException(UserCodeEnum.GOAL_NOT_PROXY.getCode());
        }

        // 判断目标代理账号类型（过滤试玩，测试账号）
        UserEntity userDTO = userDao.selectById(destId);
        if (userDTO == null) {
            throw new UserException(UserCodeEnum.OBJECT_NOT_EXIST.getCode());
        }
        // 目标代理不是正式账号
        if (userDTO.getIsDemo() != UserConstant.IS_ZERO) {
            throw new UserException(UserCodeEnum.DEST_USER_TYPE_ERROR.getCode());
        }

        List<Long> longList = Arrays.asList(userIds.toString().split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
        //查询会员的返点信息
        List<Map<String, Object>> userList = userRebateDao.queryUserInfoByUserId(longList);
        StringBuffer userBuffer = new StringBuffer();
        Map<String, Object> userMap = new HashMap<String, Object>();
        StringBuffer userIdArrs = new StringBuffer();
        String siteCode = destUserRebate.getSiteCode();
        if (0 >= userList.size()) {
            logger.error("会员信息返点信息不存在");
            throw new UserException(UserCodeEnum.USER_REBATE_NOT_EXIST.getCode());
        } else {
            for (Map<String, Object> userRebate : userList) {
                userIdArrs.append(userRebate.get("id") + ",");
                if (NumberUtils.toInt(userRebate.get("dpc_rebate").toString()) > destUserRebate.getDpcRebate()
                        || NumberUtils.toInt(userRebate.get("flc_rebate").toString()) > destUserRebate.getFlcRebate()
                        || NumberUtils.toInt(userRebate.get("tycp_rebate").toString()) > destUserRebate.getTycpRebate()
                        || NumberUtils.toInt(userRebate.get("qt_rebate").toString()) > destUserRebate.getQtRebate()
                        || NumberUtils.toInt(userRebate.get("ty_rebate").toString()) > destUserRebate.getTyRebate()
                        || NumberUtils.toInt(userRebate.get("lhc_rebate0").toString()) > destUserRebate.getLhcRebate0()
                        || NumberUtils.toInt(userRebate.get("lhc_rebate1").toString()) > destUserRebate.getLhcRebate1()
                        || NumberUtils.toInt(userRebate.get("lhc_rebate2").toString()) > destUserRebate.getLhcRebate2()
                        || NumberUtils.toInt(userRebate.get("lhc_rebate3").toString()) > destUserRebate.getLhcRebate3()
                        || NumberUtils.toInt(userRebate.get("gpc_rebate").toString()) > destUserRebate.getGpcRebate()) {
                    userBuffer.append(userRebate.get("user_name") + ",");
                    userMap.put(userRebate.get("id").toString(), userRebate.get("user_name"));
                }
            }
            String userString = userBuffer.toString().indexOf(",") == -1 ? userBuffer.toString() : userBuffer.toString().substring(0, userBuffer.toString().length() - 1);
            if (0 < userMap.size()) {
                logger.error(userString + "返点高于目标代理返点");
                throw new UserException(UserCodeEnum.USER_REBATE_ILLEGAL.getCode(), userString + "返点高于目标代理返点，是否将这些会员返点调整与代理目标一致？是，则迁移成功；否,则返回");
            }
            boolean isSuccess = userProxyInnerService.transferBatch(longList, destId, destUserRebate.getSiteId());
            if (!isSuccess){
                logger.error(String.format("代理迁移异常: siteCode: %s,会员: %s,目标代理: %s",siteCode,longList,destName));
                throw new UserException(UserCodeEnum.TRANSFER_LEVEL_INVALID.getCode());
            }
            Date now = new Date();
            //更新会员迁移时间
            UserEntity userEntity = new UserEntity();
            userEntity.setTransferTime(now);
            userEntity.setUpdateBy(managerUserName);
            userEntity.setUpdateTime(now);
            QueryWrapper<UserEntity> ew = new QueryWrapper<>();
            ew.in("id",longList);
            userDao.update(userEntity,ew);
            //添加操作日志
            //修改lot_user_fund表数据
            ApiResult<?> apiResult = userFundService.migrationAgent(destId, userIds, siteCode);
            if (!RPCResult.checkApiResult(apiResult)) {
                throw new UserException(apiResult.getCode(), apiResult.getMessage());
            }
            long end = System.currentTimeMillis();
            logger.info(String.format("用户批量迁移结束,目标代理: %s,耗时: %s", destName, end - start));
            return isSuccess ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "批量迁移失败");
        }
    }

    @Override
    public ApiResult updateRebate(Long destId, List<String> userNameList, Long siteId) {
        long start = System.currentTimeMillis();
        logger.info(String.format("批量更新会员返点开始,会员信息: %s", userNameList));
        if (destId == null || CollectionUtil.isEmpty(userNameList)) {
            logger.error("参数为空");
            throw new UserException(UserCodeEnum.PARAM_IS_NULL.getCode());
        }
        UserRebateEntity destUserRebate = userRebateDao.selectById(destId);
        if (destUserRebate == null) {
            logger.error("不存在该代理");
            throw new UserException(UserCodeEnum.HIGH_USER_IS_NULL.getCode());
        }
        // 判断目标代理账号类型是否是代理
        if (UserConstant.IS_F.equals(destUserRebate.getIsProxy())) {
            logger.error("目标代理账号类型不能为会员");
            throw new UserException(UserCodeEnum.GOAL_NOT_PROXY.getCode());
        }
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        ew.in("user_name", userNameList);
        ew.eq("site_id", siteId);
        ew.eq("is_del", UserConstant.IS_F);
        List<Long> idList = new ArrayList<>();
        List<UserEntity> userEntities = userDao.selectList(ew);
        if (CollectionUtil.isEmpty(userEntities)) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        for (UserEntity userEntity : userEntities) {
            idList.add(userEntity.getId());
        }
        QueryWrapper<UserRebateEntity> updateParam = new QueryWrapper<>();
        UserRebateEntity userRebateParam = new UserRebateEntity();
        userRebateParam.setDpcRebate(destUserRebate.getDpcRebate());
        userRebateParam.setFlcRebate(destUserRebate.getFlcRebate());
        userRebateParam.setGpcRebate(destUserRebate.getGpcRebate());
        userRebateParam.setLhcRebate0(destUserRebate.getLhcRebate0());
        userRebateParam.setLhcRebate1(destUserRebate.getLhcRebate1());
        userRebateParam.setLhcRebate2(destUserRebate.getLhcRebate2());
        userRebateParam.setLhcRebate3(destUserRebate.getLhcRebate3());
        userRebateParam.setQtRebate(destUserRebate.getQtRebate());
        userRebateParam.setTycpRebate(destUserRebate.getTycpRebate());
        userRebateParam.setTyRebate(destUserRebate.getTyRebate());
        updateParam.in("id", idList);
        updateParam.eq("site_id", siteId);
        int update = userRebateDao.update(userRebateParam, updateParam);
        long end = System.currentTimeMillis();
        logger.info(String.format("批量更新会员返点结束,耗时: %s,会员信息: %s", end - start, userNameList));
        return update > 0 ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "批量更新返点信息失败");
    }

    @Override
    public boolean saveUserRebate(UserRebateEntity userRebateVO,Long highUserId) {
        UserEntity highUser = userDao.selectById(highUserId);
        if (highUser==null){
            throw new UserException(UserCodeEnum.HIGH_NOT_EXIST.getCode());
        }
        UserRebateEntity highUserRebate = userRebateDao.selectById(highUserId);
        if (highUserRebate==null){
            throw new UserException(UserCodeEnum.HIGH_REBATE_NOT_EXIST.getCode());
        }
        boolean isLegalRebate = checkRebateInner(userRebateVO, highUserRebate);
        if (!isLegalRebate) {
            throw new UserException(UserCodeEnum.USER_REBATE_ILLEGAL.getCode());
        }
        Long userId = userRebateVO.getId();
        // 保存返点信息
        UserRebateEntity userRebate = new UserRebateEntity();
        userRebate.setId(userId);
        userRebate.setIsProxy(userRebateVO.getIsProxy());
        userRebate.setGpcRebate(userRebateVO.getGpcRebate());
        userRebate.setFlcRebate(userRebateVO.getFlcRebate());
        userRebate.setTycpRebate(userRebateVO.getTycpRebate());
        userRebate.setQtRebate(userRebateVO.getQtRebate());
        userRebate.setTyRebate(userRebateVO.getTyRebate());
        userRebate.setLhcRebate0(userRebateVO.getLhcRebate0());
        userRebate.setLhcRebate1(userRebateVO.getLhcRebate1());
        userRebate.setLhcRebate2(userRebateVO.getLhcRebate2());
        userRebate.setLhcRebate3(userRebateVO.getLhcRebate3());
        userRebate.setDpcRebate(userRebateVO.getDpcRebate());
        userRebate.setCreateTime(new Date());
        userRebate.setUpdateTime(new Date());
        userRebate.setSiteId(userRebateVO.getSiteId());
        userRebate.setSiteCode(userRebateVO.getSiteCode());
        Integer result = userRebateDao.insert(userRebate);
        return result > 0;
    }

    private boolean checkRebateInner(UserRebateEntity user, UserRebateEntity highUser) {
        return user.getGpcRebate() <= highUser.getGpcRebate() && user.getFlcRebate() <= highUser.getFlcRebate()
                && user.getTycpRebate() <= highUser.getTycpRebate() && user.getQtRebate() <= highUser.getQtRebate()
                && user.getTyRebate() <= highUser.getTyRebate() && user.getLhcRebate3() <= highUser.getLhcRebate3()
                && user.getLhcRebate2() <= highUser.getLhcRebate2() && user.getLhcRebate1() <= highUser.getLhcRebate1()
                && user.getLhcRebate0() <= highUser.getLhcRebate0();
    }


    private boolean checkRebate(AddUserDTO dto) {
        return CheckUtil.isOneDecimal(dto.getGpcRebate())
                && CheckUtil.isOneDecimal(dto.getFlcRebate()) && CheckUtil.isOneDecimal(dto.getQtRebate())
                && CheckUtil.isOneDecimal(dto.getTyRebate()) && CheckUtil.isOneDecimal(dto.getDpcRebate())
                && CheckUtil.isOneDecimal(dto.getLhcRebate0()) && CheckUtil.isOneDecimal(dto.getLhcRebate1())
                && CheckUtil.isOneDecimal(dto.getLhcRebate2()) && CheckUtil.isOneDecimal(dto.getLhcRebate3());
    }

    private boolean checkRebateRPC(AddUserDTO dto, UserRebateEntity highUser) {
        return dto.getGpcRebate() <= highUser.getGpcRebate() && dto.getFlcRebate() <= highUser.getFlcRebate()
                && dto.getTycpRebate() <= highUser.getTycpRebate() && dto.getQtRebate() <= highUser.getQtRebate()
                && dto.getTyRebate() <= highUser.getTyRebate() && dto.getLhcRebate3() <= highUser.getLhcRebate3()
                && dto.getLhcRebate2() <= highUser.getLhcRebate2() && dto.getLhcRebate1() <= highUser.getLhcRebate1()
                && dto.getLhcRebate0() <= highUser.getLhcRebate0();
    }

    private boolean checkSelfRebate(UserRebateUpdateDTO dto, UserEntity userEntity,
                                    UserRebateEntity oldUserRebate, UserRebateEntity highUserRebate) {
        // 判断是否更新了用户返点
        boolean flag = false;
        if (oldUserRebate.getGpcRebate() != NumberUtils.toLong(dto.getGpcRebate())
                || oldUserRebate.getFlcRebate() != NumberUtils.toLong(dto.getFlcRebate())
                || oldUserRebate.getTycpRebate() != NumberUtils.toLong(dto.getTycpRebate())
                || oldUserRebate.getQtRebate() != NumberUtils.toLong(dto.getQtRebate())
                || oldUserRebate.getTyRebate() != NumberUtils.toLong(dto.getTyRebate())
                || oldUserRebate.getDpcRebate() != NumberUtils.toLong(dto.getDpcRebate())
                || oldUserRebate.getLhcRebate0() != NumberUtils.toLong(dto.getLhcRebate0())
                || oldUserRebate.getLhcRebate1() != NumberUtils.toLong(dto.getLhcRebate1())
                || oldUserRebate.getLhcRebate2() != NumberUtils.toLong(dto.getLhcRebate2())
                || oldUserRebate.getLhcRebate3() != NumberUtils.toLong(dto.getLhcRebate3())) {
            flag = true;
        }

        if (flag) {
            //查找上级返点
            if (highUserRebate != null && !"default".equals(userEntity.getUserName())) {//非默认厅主
                //与上级比较返点
                if (NumberUtils.toLong(dto.getGpcRebate()) > highUserRebate.getGpcRebate()
                        || NumberUtils.toLong(dto.getGpcRebate()) < 0) {
                    logger.info(String.format("上级用户高频彩返点：[{%s}]，下级用户高频彩返点：[{%s}]", highUserRebate.getGpcRebate(), dto.getGpcRebate()));
                    throw new UserException(UserCodeEnum.GPC_REBATE_OVER_LIMIT.getCode());
                }

                if (NumberUtils.toLong(dto.getFlcRebate()) > highUserRebate.getFlcRebate()
                        || NumberUtils.toLong(dto.getFlcRebate()) < 0) {
                    logger.info(String.format("上级用户棋牌返点：[{%s}]，下级用户棋牌返点：[{%s}]", highUserRebate.getFlcRebate(), dto.getFlcRebate()));
                    throw new UserException(UserCodeEnum.FLC_REBATE_OVER_LIMIT.getCode());
                }

                if (NumberUtils.toLong(dto.getTycpRebate()) > highUserRebate.getTycpRebate()
                        || NumberUtils.toLong(dto.getTycpRebate()) < 0) {
                    logger.info(String.format("上级用户体育彩票返点：[{%s}]，下级用户体育彩票返点：[{%s}]", highUserRebate.getTycpRebate(), dto.getTycpRebate()));
                    throw new UserException(UserCodeEnum.TYCP_REBATE_OVER_LIMIT.getCode());
                }

                if (NumberUtils.toLong(dto.getQtRebate()) > highUserRebate.getQtRebate()
                        || NumberUtils.toLong(dto.getQtRebate()) < 0) {
                    logger.info(String.format("上级用户其他返点：[{%s}]，下级用户其他彩返点：[{%s}]", highUserRebate.getQtRebate(), dto.getQtRebate()));
                    throw new UserException(UserCodeEnum.QT_REBATE_OVER_LIMIT.getCode());
                }

                if (NumberUtils.toLong(dto.getTyRebate()) > highUserRebate.getTyRebate()
                        || NumberUtils.toLong(dto.getTyRebate()) < 0) {
                    logger.info(String.format("上级用户体育返点：[{%s}]，下级用户体育彩返点：[{%s}]", highUserRebate.getTyRebate(), dto.getTyRebate()));
                    throw new UserException(UserCodeEnum.TY_REBATE_OVER_LIMIT.getCode());
                }
                long dpcRebate = NumberUtils.toLong(dto.getDpcRebate());
                if (dpcRebate > highUserRebate.getDpcRebate()
                        || NumberUtils.toLong(dto.getDpcRebate()) < 0) {
                    logger.info(String.format("上级用户低频彩返点：[{%s}]，下级用户低频彩彩返点：[{%s}]",
                            highUserRebate.getDpcRebate(), dto.getDpcRebate()));
                    throw new UserException(UserCodeEnum.DPC_REBATE_OVER_LIMIT.getCode());
                }
                long lhcRebate0 = NumberUtils.toLong(dto.getLhcRebate0());
                if (lhcRebate0 > highUserRebate.getLhcRebate0()
                        || NumberUtils.toLong(dto.getLhcRebate0()) < 0) {
                    logger.info(String.format("上级用户六合彩组0返点：[{%s}]，下级用户六合彩组0返点：[{%s}]",
                            highUserRebate.getLhcRebate0(), dto.getLhcRebate0()));
                    throw new UserException(UserCodeEnum.LHC0_REBATE_OVER_LIMIT.getCode());
                }

                long lhcRebate1 = NumberUtils.toLong(dto.getLhcRebate1());
                if (lhcRebate1 > highUserRebate.getLhcRebate1()
                        || NumberUtils.toLong(dto.getLhcRebate1()) < 0) {
                    logger.info(String.format("上级用户六合彩组1返点：[{%s}]，下级用户六合彩组1返点：[{%s}]",
                            highUserRebate.getLhcRebate1(), dto.getLhcRebate1()));
                    throw new UserException(UserCodeEnum.LHC1_REBATE_OVER_LIMIT.getCode());
                }

                long lhcRebate2 = NumberUtils.toLong(dto.getLhcRebate2());
                if (lhcRebate2 > highUserRebate.getLhcRebate2()
                        || NumberUtils.toLong(dto.getLhcRebate2()) < 0) {
                    logger.info(String.format("上级用户六合彩组2返点：[{%s}]，下级用户六合彩组2返点：[{%s}]",
                            highUserRebate.getLhcRebate2(), dto.getLhcRebate2()));
                    throw new UserException(UserCodeEnum.LHC2_REBATE_OVER_LIMIT.getCode());
                }

                long lhcRebate3 = NumberUtils.toLong(dto.getLhcRebate3());
                if (lhcRebate3 > highUserRebate.getLhcRebate3()
                        || NumberUtils.toLong(dto.getLhcRebate3()) < 0) {
                    logger.info(String.format("上级用户六合彩组3返点：[{%s}]，下级用户六合彩组3返点：[{%s}]",
                            highUserRebate.getLhcRebate3(), dto.getLhcRebate3()));
                    throw new UserException(UserCodeEnum.LHC3_REBATE_OVER_LIMIT.getCode());
                }

            } else if (highUserRebate == null && "default".equals(userEntity.getUserName())) {
                if (NumberUtils.toLong(dto.getGpcRebate()) > 10000l || NumberUtils.toLong(dto.getDpcRebate()) > 10000l
                        || NumberUtils.toLong(dto.getTycpRebate()) > 10000l || NumberUtils.toLong(dto.getTyRebate()) > 10000l
                        || NumberUtils.toLong(dto.getFlcRebate()) > 10000l || NumberUtils.toLong(dto.getQtRebate()) > 10000l
                        || NumberUtils.toLong(dto.getLhcRebate0()) > 10000l || NumberUtils.toLong(dto.getLhcRebate1()) > 10000l
                        || NumberUtils.toLong(dto.getLhcRebate2()) > 10000l || NumberUtils.toLong(dto.getLhcRebate3()) > 10000l) {
                    logger.info("修改默认厅主返点");
                    throw new UserException(UserCodeEnum.REBATE_OVER_100.getCode());
                }
            } else {
                throw new UserException(UserCodeEnum.HIGH_NOT_EXIST.getCode());
            }
        }
        return true;
    }
}
