package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.dto.SiteRegisterConfigDTO;
import com.jq.platform.site.service.SiteRegisterConfigService;
import com.jq.platform.site.service.SiteService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.constant.UserStatus;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserTokenDao;
import com.jq.user.customer.dto.UserDTO;
import com.jq.user.customer.dto.UserModelDTO;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserInfoEntity;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.entity.UserTokenEntity;
import com.jq.user.customer.service.*;
import com.jq.user.exception.UserException;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.refer.dao.UserReferDao;
import com.jq.user.refer.entity.UserReferEntity;
import com.jq.user.refer.service.UserReferInnerService;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.support.RedisUtil;
import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.vo.UserBindingDefaultGroupReq;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import com.liying.common.util.GlobalCacheKey;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.AddUserFundReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.jq.user.support.SpringBeanUtil.getActiveProfile;

/**
 * CopyUserServiceImpl
 *
 * @author lxh
 * @date 2018/9/7
 */
@Service
@Transactional
public class UserRelieveServiceImpl implements UserRelieveService {

    private final static Logger logger = LoggerFactory.getLogger(UserRelieveServiceImpl.class);

    @Resource
    private UserInfoInnerService userInfoInnerService;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Resource
    private UserReferInnerService userReferInnerService;
    @Resource
    private UserReferDao userReferDao;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private SiteService siteService;
    @Resource
    private UserFundService userFundService;
    @Resource
    private GroupService groupService;
    @Resource
    private SiteRegisterConfigService siteRegisterConfigService;
    @Value("${RSATime}")
    private Integer rsaTime;
    @Value("${JWTTime}")
    private Integer ttlMillis;
    @Value("${SecurityCode}")
    private Integer securityCodeTime;
    @Value("${SessionUuidTime}")
    private Integer sessionUuidTime;
    @Resource
    private UserRebateTransService userRebateTransService;
    @Resource
    private UserDao userDao;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;

    @Override
    public ApiResult<UserDTO> registerUserApi(UserModelDTO dto) {
        if (null == dto || null == dto.getSiteId() || null == dto.getUserName() || null == dto.getPassword() || null == dto.getCurrTime() || null == dto.getVerifyCode()) {
            return RPCResult.custom(UserCodeEnum.LACK_USER_REGISTER_INFO.getCode(), UserCodeEnum.LACK_USER_REGISTER_INFO.getMessage());
        }
        //分布式锁操作用户
        String cacheKey = RedisKey.Lock.REGISTER_USER + dto.getUserName();
        RedisUtil redisUtil = new RedisUtil(template, cacheKey);
        //参数空判断验证
        try {
            if (redisUtil.lock()) {
                Long time = System.currentTimeMillis();
                ValueOperations<String, String> ops = template.opsForValue();

                UserEntity userEntity = new UserEntity();
                String currTime = dto.getCurrTime();
                String verifyCode = dto.getVerifyCode();
                Integer platformType = dto.getPlatformType();
                BeanUtil.copyProperties(dto, userEntity);
                // 判断是否需要验证码，若同个ip5分钟内有注册过则需要进行验证，否则绕过验证码验证
                String verifyCodeKey = GlobalCacheKey.User.VERIFY_CODE + dto.getIp();
                String verifyCodeCache = ops.get(verifyCodeKey);
                if (StrUtil.isNotEmpty(verifyCodeCache)) {
                    if (getActiveProfile().equals("dev") || getActiveProfile().equals("test")) {
                        if (ObjectUtil.notEqual("@Ly!", verifyCode)) {
                            String cacheVerifyCode = ops.get(RedisKey.SECURITY_CODE + currTime);
                            if (StrUtil.isEmpty(cacheVerifyCode)) {
                                return RPCResult.custom(UserCodeEnum.ERR_CREATE_VERIFYCODE.getCode(), "验证码已过期，请重新生成");
                            }
                            if (!verifyCode.equalsIgnoreCase(cacheVerifyCode)) {
                                return RPCResult.custom(UserCodeEnum.ERR_VERIFYCODE.getCode(), UserCodeEnum.ERR_VERIFYCODE.getMessage());
                            }
                        }
                    } else {
                        String cacheVerifyCode = ops.get(RedisKey.SECURITY_CODE + currTime);
                        if (StrUtil.isEmpty(cacheVerifyCode)) {
                            return RPCResult.custom(UserCodeEnum.ERR_CREATE_VERIFYCODE.getCode(), UserCodeEnum.ERR_CREATE_VERIFYCODE.getMessage());
                        }
                        if (!verifyCode.equalsIgnoreCase(cacheVerifyCode)) {
                            return RPCResult.custom(UserCodeEnum.ERR_VERIFYCODE.getCode(), UserCodeEnum.ERR_VERIFYCODE.getMessage());
                        }
                    }
                }
                //2、注册配置参数检验
                ApiResult verifyFiled = verifyField(dto, userEntity.getSiteId());
                if (!RPCResult.checkApiResult(verifyFiled)) {
                    return verifyFiled;
                }
                template.delete(RedisKey.SECURITY_CODE + currTime + ":");
                //获取站点信息，判断站点状态
                ApiResult<SiteDTO> siteDTOApi = siteService.findSiteDTOById(userEntity.getSiteId());
                if (!RPCResult.checkApiResult(siteDTOApi)) {
                    return RPCResult.custom(siteDTOApi.getCode(), siteDTOApi.getMessage());
                }
                SiteDTO siteDTO = siteDTOApi.getData();
                if (ObjectUtil.equal(UserConstant.IS_T, siteDTO.getIsDel())) {
                    return RPCResult.custom(UserCodeEnum.SITE_DISTBLED.getCode(), UserCodeEnum.SITE_DISTBLED.getMessage());
                }
                if (siteDTO.getIsRegister() == 0) {
                    return RPCResult.custom(UserCodeEnum.SITE_REGISTER_DISABLED.getCode(), UserCodeEnum.SITE_REGISTER_DISABLED.getMessage());
                }
                //判断用户是否存在
                UserEntity user = userDao.confirmExistUserName(userEntity.getSiteId(), userEntity.getUserName());
                if (null != user) {
                    return RPCResult.custom(UserCodeEnum.USER_EXIST.getCode(), UserCodeEnum.USER_EXIST.getMessage());
                }

                //上级代理账户
                UserEntity highUser = null;
                UserReferEntity userRefer = null;
                if (StrUtil.isNotEmpty(dto.getReferCode())) {
                    //查找推广码及推广
                    userRefer = userReferInnerService.findBySiteIdAndReferCode(siteDTO.getId(), dto.getReferCode());
                } else {
                    userRefer = userReferDao.selectById(siteDTO.getDefaultProxyId());
                }
                if (null == userRefer || ObjectUtil.equal(UserConstant.IS_T, userRefer.getIsDel())) {
                    throw new UserException(UserCodeEnum.REFERCODE_NOT_EXIST.getCode());
                }
                if (ObjectUtil.equal(UserConstant.IS_F, userRefer.getIsEnable())) {
                    throw new UserException(UserCodeEnum.REFERCODE_DISABLED.getCode());
                }
                // 更新推广链接使用次数
                userReferInnerService.updateReferUseCount(userRefer.getId());

                //推广码所属代理
                highUser = userDao.findById(userRefer.getUserId());
                if (null == highUser || ObjectUtil.equal(UserConstant.IS_T, highUser.getIsDel())) {
                    throw new UserException(UserCodeEnum.HIGH_USER_IS_NULL.getCode());
                }
                //7、查询用户等级
                Long userRankId = userRankDao.findDefaultRank(userEntity.getSiteId());
                if (ObjectUtil.isNull(userRankId)) {
                    throw new UserException(UserCodeEnum.RANK_MISS_CODE.getCode(), UserCodeEnum.RANK_MISS_CODE.getMessage());
                }
                userEntity.setUserRankId(userRankId);
                //获取私钥，解密
                String MD5Pwd = userInnerService.getMD5PwdByRSA(userEntity.getPassword());
                String MD5PayPwd;
                if (StrUtil.isNotEmpty(userEntity.getPayPwd())) {
                    MD5PayPwd = userInnerService.getMD5PwdByRSA(userEntity.getPayPwd());
                    userEntity.setPayPwd(MD5PayPwd);
                }
                userEntity.setPassword(MD5Pwd);
                //保存对应表信息
                userEntity.setChangePayPwdErrNum(0);
                userEntity.setChangePwdErrNum(0);
                userEntity.setRandom(RandomUtil.randomInt(6));
                userEntity.setUpdateBy(dto.getCreateByUserName());
                userEntity.setCreateBy(dto.getCreateByUserName());
                userEntity.setCreateTime(new Date());
                userEntity.setUpdateTime(new Date());
                userEntity.setId(IdWorker.getId());
                userEntity.setSiteCode(siteDTO.getSiteCode());
                UserInfoEntity userInfoEntity = new UserInfoEntity();
                BeanUtil.copyProperties(dto, userInfoEntity);
                if (StrUtil.isNotBlank(dto.getBirthDate())) {
                    DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    userInfoEntity.setBirthday(fmt.parse(dto.getBirthDate()));
                }
                userInfoEntity.setRegSource(platformType);
                userInfoEntity.setRegIp(dto.getIp());
                userInfoEntity.setRegUrl(dto.getUrl());
                userInfoEntity.setNickName(dto.getNickName());
                UserEntity finalUser = saveUser(userEntity, userInfoEntity, userEntity.getSiteId(), highUser.getIsDemo(), dto.getIp());
                // 保存有效会员数据
                validUserStatisticsInnerService.initValidUserInfo(finalUser);
                // 保存返点信息
                logger.info("注册用户：" + finalUser.getUserName() + "，站点：" + siteDTO.getId() + "保存返点信息，耗时：" + (System.currentTimeMillis() - time) + "ms");
                UserRebateEntity userRebateEntity = new UserRebateEntity();
                BeanUtil.copyProperties(userRefer, userRebateEntity);
                userRebateEntity.setId(finalUser.getId());
                if (!userRebateTransService.saveUserRebate(userRebateEntity, userRefer.getUserId())) {
                    throw new UserException(ErrorCode.DEFAULT_CODE, ErrorCode.DEFAULT_MSG);
                }
                // 保存代理关系
                Integer result = userProxyInnerService.saveUserProxy(userRefer.getUserId(), finalUser.getId(), dto.getUserName());
                if (result == 0) {
                    throw new UserException(UserCodeEnum.SAVE_PROXY_FAIL.getCode(), UserCodeEnum.SAVE_PROXY_FAIL.getMessage());
                }
                //初始化资金记录
                logger.info("注册用户：" + finalUser.getUserName() + "，站点：" + siteDTO.getId() + "初始化资金记录，耗时：" + (System.currentTimeMillis() - time) + "ms");
                AddUserFundReq addUserFundReq = new AddUserFundReq();
                addUserFundReq.setSiteCode(siteDTO.getSiteCode());
                addUserFundReq.setUserId(Convert.toStr(userEntity.getId()));
                addUserFundReq.setUsername(userEntity.getUserName());
                addUserFundReq.setSuperiorUserId(highUser.getId().toString());  //顶级用户
                ApiResult apiResultUserFund = userFundService.addUserFund(addUserFundReq);
                if (!RPCResult.checkApiResult(apiResultUserFund)) {
                    throw new UserException(apiResultUserFund.getCode(), apiResultUserFund.getMessage());
                }
                // 保存分组
                logger.info("注册用户：" + finalUser.getUserName() + "，站点：" + siteDTO.getId() + "保存分组，耗时：" + (System.currentTimeMillis() - time) + "ms");
                UserBindingDefaultGroupReq userBindingDefaultGroupReq = new UserBindingDefaultGroupReq();
                userBindingDefaultGroupReq.setUserId(finalUser.getId());
                userBindingDefaultGroupReq.setSiteCode(finalUser.getSiteCode());
                userBindingDefaultGroupReq.setIsDemo(finalUser.getIsDemo().toString());
                userBindingDefaultGroupReq.setUserName(finalUser.getUserName());
                ApiResult apiResult = groupService.userBindingDefaultGroup(userBindingDefaultGroupReq);
                if (!RPCResult.checkApiResult(apiResult)) {
                    throw new UserException(apiResult.getCode(), apiResult.getMessage());
                }
                // 设置用户头像（针对老用户设置）
                userInfoService.updateUserPicture(finalUser.getId(), "");
                //保存注册成功日志
                logUserInnerService.addUserLog(finalUser.getId(), finalUser.getUserName(), platformType, "代理会员：" + finalUser.getUserName(), UserCfg.REGISTER_SUCCESS, UserCfg.USER,
                        finalUser.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, dto.getIp(), dto.getUrl());
                String token = this.getTokenJson(finalUser, ttlMillis, platformType);
                UserDTO finalUserDTO = new UserDTO();
                BeanUtil.copyProperties(finalUser, finalUserDTO);
                finalUserDTO.setToken(token);
                logger.info("注册用户：" + finalUser.getUserName() + "，站点：" + siteDTO.getId() + "注册成功，耗时：" + (System.currentTimeMillis() - time) + "ms");
                return RPCResult.success(finalUserDTO);
            } else {
                logger.error("用户:" + dto.getUserName() + "注册时，分布式锁获取失败");
                return RPCResult.custom(UserCodeEnum.USER_REGISTER_FAIL.getCode(), "用户注册失败 ，请重新注册");
            }
        } catch (Exception e) {
            logger.error("代理玩家注册失败!!!", e);
            throw new UserException(ErrorCode.DEFAULT_CODE, ErrorCode.DEFAULT_MSG);
        } finally {
            redisUtil.unlock();
        }
    }

    /**
     * Author: Brady
     * Description: 验证字段
     * Date: 2018/6/21
     */
    private ApiResult verifyField(Object obj, Long siteId) {
        logger.info("verifyField:" + obj + ",siteId:" + siteId);
        if (null == obj) {
            logger.info("没有注册参数配置信息");
            return RPCResult.success();
        }
        //根据站点id获取注册配置参数
        ApiResult<List<SiteRegisterConfigDTO>> apiResult = siteRegisterConfigService.findSiteRegisterConfigBySiteIdApi(siteId);
        if (!RPCResult.checkApiResult(apiResult)) {
            throw new UserException(apiResult.getCode());
        }
        List<SiteRegisterConfigDTO> siteRegConfigList = apiResult.getData();
        for (SiteRegisterConfigDTO siteRegisterConfigDTO : siteRegConfigList) {
            //判断是否显示
            if (UserConstant.IS_T.equals(siteRegisterConfigDTO.getIsDisplay())) {
                Field[] fields = obj.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    String name = fields[i].getName();
                    String firstLetter = name.substring(0, 1).toUpperCase();
                    String getter = "get" + firstLetter + name.substring(1);
                    Method method;
                    try {
                        method = obj.getClass().getMethod(getter, new Class[]{});
                        String value = String.valueOf(method.invoke(obj, new Object[]{}));
                        if (name.equals(siteRegisterConfigDTO.getFieldCode())) {
                            //判断是否必填
                            if (UserConstant.IS_T.equals(siteRegisterConfigDTO.getIsRequired())) {
                                //判断是否为空
                                if (StrUtil.isBlank(value)) {
                                    return RPCResult.custom(UserCodeEnum.LACK_USER_REGISTER_INFO.getCode(), siteRegisterConfigDTO.getFieldName() + "不允许为空！");
                                }
                            }
                        }
                    } catch (Exception e) {
                        logger.error("异常信息:", e);
                    }
                }
            }

        }

        return RPCResult.success();
    }

    private String getTokenJson(UserEntity user, Integer ttlMillis, Integer platformType) {
        JSONObject userJson = new JSONObject();
        userJson.put("userId", user.getId().toString());
        userJson.put("siteId", user.getSiteId().toString());
        userJson.put("siteCode", user.getSiteCode());
        userJson.put("userName", user.getUserName());
        userJson.put("platformType", platformType);
        userJson.put("isDemo", user.getIsDemo().toString());
        userJson.put("status", user.getStatus().toString());
        UserTokenEntity userToken = userTokenDao.selectById(user.getId());
        if (null != userToken) {
            template.delete(RedisKey.USER_TOKEN + userToken.getSecretToken());
        }
        String uuid = RandomUtil.simpleUUID();
        template.opsForValue().set(RedisKey.USER_TOKEN + uuid, userJson.toString(), ttlMillis, TimeUnit.MINUTES);
        UserTokenEntity userTokenEntity = new UserTokenEntity();
        Date date = new Date();
        userTokenEntity.setId(user.getId());
        userTokenEntity.setPlatformType(platformType);
        userTokenEntity.setSecretToken(uuid);
        userTokenEntity.setAccessToken(userJson.toString());
        userTokenEntity.setCreateTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, ttlMillis);
        Date newDate = calendar.getTime();
        userTokenEntity.setAccessExpire(newDate);
        if (null != userToken) {
            userTokenDao.updateById(userTokenEntity);
        } else {
            try {
                userTokenDao.insert(userTokenEntity);
            } catch (DuplicateKeyException e) {
                logger.error("userId" + user.getId() + "出现主键重复");
            }
        }
        return uuid;
    }

    public UserEntity saveUser(UserEntity userEntity, UserInfoEntity userInfoEntity, Long siteId, Integer isDemo, String ip) {
        UserEntity user = new UserEntity();
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        user.setPayPwd(userEntity.getPayPwd());
        if (StrUtil.isNotEmpty(ip)) {
            user.setLastLoginIp(ip);
            user.setLastLoginTime(new Date());
        }
        user.setLoginCount(UserConstant.IS_ZERO);
        user.setUserName(userEntity.getUserName());
        user.setSiteId(siteId);
        user.setStatus(UserStatus.USER_STATUS_10.getCode());
        user.setCreateTime(userEntity.getCreateTime());
        user.setIsDel(UserConstant.IS_ZERO);
        user.setUserRankId(userEntity.getUserRankId());
        user.setIsDemo(isDemo);
        user.setUpdateBy(userEntity.getUpdateBy());
        user.setUpdateTime(userEntity.getUpdateTime());
        user.setRandom(userEntity.getRandom());
        user.setCreateBy(userEntity.getCreateBy());
        user.setUpdateTime(userEntity.getUpdateTime());
        user.setSiteCode(userEntity.getSiteCode());
        userDao.insert(user);
        if (userInfoEntity != null) {
            userInfoEntity.setId(user.getId());
            userInfoInnerService.saveUserInfo(userInfoEntity);
        }
        UserEntity finalUser = userDao.selectById(user.getId());
        return finalUser;
    }
}
