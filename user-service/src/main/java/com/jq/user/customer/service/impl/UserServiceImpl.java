package com.jq.user.customer.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.JQException;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteRegisterConfigService;
import com.jq.platform.site.service.SiteService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.common.InitParameters;
import com.jq.user.constant.RedisKey;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.constant.UserStatus;
import com.jq.user.customer.dao.*;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.*;
import com.jq.user.customer.service.*;
import com.jq.user.exception.UserException;
import com.jq.user.log.dao.LogUserDao;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.entity.LogUserEntity;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.proxy.dao.UserProxyDao;
import com.jq.user.proxy.entity.UserProxyEntity;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.refer.dao.UserReferDao;
import com.jq.user.refer.entity.UserReferEntity;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.dto.UserRankScoreDTO;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.service.UserRankInnerService;
import com.jq.user.score.service.UserSignInnerService;
import com.jq.user.support.AsyncTask;
import com.jq.user.support.PageUtil;
import com.jq.user.support.RSAUtil;
import com.jq.user.support.SupportUtil;
import com.liying.cash.group.api.GroupService;
import com.liying.cash.group.vo.UserBindingDefaultGroupReq;
import com.liying.common.code.GlobalCode;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.common.util.Results;
import com.liying.trade.report.resp.EntryAndExitTotalResp;
import com.liying.trade.report.vo.EntryAndExitTotalReq;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.AddUserFundReq;
import com.liying.trade.user.vo.GetUserFundReq;
import com.liying.trade.user.vo.UserFundResp;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.KeyPair;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.jq.user.support.SpringBeanUtil.getActiveProfile;

/**
 * Author: Brady
 * Date: 2018/4/26
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserInnerService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserDao userDao;
    @Resource
    private UserInfoInnerService userInfoInnerService;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Resource
    private UserRebateInnerService userRebateInnerService;
    @Resource
    private UserReferDao userReferDao;
    @Resource
    private UserRankInnerService userRankInnerService;
    @Resource
    private UserTokenDao userTokenDao;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private KeyValueService keyValueService;
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
    private BankCardDao bankCardDao;
    @Resource
    private UserRebateDao userRebateDao;
    @Resource
    private UserTokenInnerService userTokenInnerService;
    @Resource
    private UserSignInnerService userSignInnerService;
    @Resource
    private com.liying.trade.report.api.UserReportService tradeUserReportService;
    @Resource
    private LogUserDao logUserDao;
    @Resource
    private UserRebateTransService userRebateTransService;
    @Resource
    private UserRelieveService userRelieveService;
    @Resource
    private UserRebateService userRebateService;
    @Resource
    private UserProxyInnerService userProxyInnerService;
    @Resource
    private UserProxyDao userProxyDao;
    @Resource
    private UserEncryptedDao userEncryptedDao;
    @Resource
    private AsyncTask asyncTask;


    @Override
    public ApiResult<Boolean> enableOrDisEnableUser(Long id, Integer status) {
        //启用或停用改变下级状态
        if (status.equals(UserStatus.USER_STATUS_11.getCode())
                || status.equals(UserStatus.USER_STATUS_10.getCode())) {
            Date now = new Date();
            UserEntity userEntity1 = userDao.selectById(id);
            if (userEntity1 == null) {
                return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }
            List<UserProxyEntity> allSubUserProxy = userProxyInnerService.getAllSubUserProxy(id, userEntity1.getSiteId());
            List<Long> allSubUserProxyIdList = userProxyInnerService.userIdToList(allSubUserProxy);
            if (CollectionUtil.isNotEmpty(allSubUserProxyIdList)) {
                List<UserEntity> userEntities = userDao.selectBatchIds(allSubUserProxyIdList);
                for (UserEntity userEntity : userEntities) {
                    if (userEntity != null && UserConstant.IS_F.equals(userEntity.getIsDel())) {
                        userEntity.setStatus(status);
                        userEntity.setUpdateTime(now);
                        userDao.updateById(userEntity);
                    }
                }
            }
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(id);
        if (status.equals(UserStatus.USER_STATUS_11.getCode())
                || status.equals(UserStatus.USER_STATUS_31.getCode())) {
            userEntity.setStatus(status);
        }
        if (status.equals(UserStatus.USER_STATUS_10.getCode())) {
            userEntity.setLoginErrCount(UserConstant.IS_ZERO);
            userEntity.setChangePwdErrNum(UserConstant.IS_ZERO);
            userEntity.setChangePayPwdErrNum(UserConstant.IS_ZERO);
            userEntity.setStatus(UserStatus.USER_STATUS_10.getCode());
            List<String> keys = new ArrayList<String>();
            keys.add(RedisKey.PAY_WITHDAW_USER_PAY_PASSWORD_ERROR + id);
            keys.add(String.format("%s%s", RedisKey.FIND_LOGIN_PASSWORD_VERIFY_ENCRYPTED_ERROR_NUM, id));
            keys.add(String.format("%s%s", RedisKey.FIND_pay_PASSWORD_VERIFY_ENCRYPTED_ERROR_NUM, id));
            keys.add(String.format("%s%s", RedisKey.UPDATE_ENCRYPTED_VERIFY_ERROR_NUM, id));
            template.delete(keys);
        } else {
            userEntity.setStatus(status);
        }
        this.kickOutUserApi(id);
        Integer result = userDao.updateById(userEntity);
        return result > 0 ? RPCResult.success(true) : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "更改下级用户状态失败");
    }

    @Override
    public ApiResult cleanUserApi(Long siteId) {
        UpdateWrapper<UserEntity> userEw = new UpdateWrapper<>();
        userEw.eq("site_id", siteId);
        userEw.eq("is_del", UserConstant.IS_F);
        userEw.notIn(true, "user_name", "default");
        UserEntity userEntity = new UserEntity();
        userEntity.setIsDel(UserConstant.IS_T);
        Integer userResult = userDao.update(userEntity, userEw);
        if (userResult == 0) {
            throw new UserException(UserCodeEnum.SITE_USER_IS_NULL.getCode());
        }
        Long defaultSysManId = sysUserDao.getDefaultSysManId(siteId);
        UpdateWrapper<SysUserEntity> sysUserEW = new UpdateWrapper<>();
        sysUserEW.eq("site_id", siteId);
        sysUserEW.eq("is_del", UserConstant.IS_F);
        sysUserEW.notIn(defaultSysManId != null, "id", defaultSysManId);
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setIsDel(UserConstant.IS_T);
        sysUserDao.update(sysUserEntity, sysUserEW);
        UpdateWrapper<LogUserEntity> logUserEw = new UpdateWrapper<>();
        logUserEw.eq("site_id", siteId);
        logUserEw.eq("is_del", UserConstant.IS_F);
        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setIsDel(UserConstant.IS_T);
        logUserDao.update(logUserEntity, logUserEw);
        return userResult > 0 ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "清理日志失败");
    }

    @Override
    public ApiResult logOutFrontUser(Long id, Integer status) {
        UserEntity userEntity = userDao.selectById(id);
        if (userEntity == null) {
            return RPCResult.fail(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        //保存pc端或app端token数据
        List<Long> userTokenIdList = new ArrayList<>();
        List<String> userTokenKeyList = new ArrayList<>();
        //注销用户登录信息(冻结，停用状态)
        if (status.equals(UserStatus.USER_STATUS_11.getCode())
                || status.equals(UserStatus.USER_STATUS_31.getCode())) {
            //pc端
            UserTokenEntity pcUserTokenPO = userTokenDao.selectById(id);
            if (null != pcUserTokenPO) {
                userTokenIdList.add(pcUserTokenPO.getId());
                userTokenKeyList.add(RedisKey.USER_TOKEN + pcUserTokenPO.getSecretToken());
            }
        }
        //停用，注销下级
        if (status.equals(UserStatus.USER_STATUS_11.getCode())) {
            List<UserProxyEntity> allSubUserProxy = userProxyInnerService.getAllSubUserProxy(id, userEntity.getSiteId());
            List<Long> list = userProxyInnerService.userIdToList(allSubUserProxy);
            QueryWrapper<UserTokenEntity> ew = new QueryWrapper<>();
            ew.in(true, "id", list);
            List<UserTokenEntity> userTokenEntities = userTokenDao.selectList(ew);
            for (UserTokenEntity tokenEntity : userTokenEntities) {
                userTokenIdList.add(tokenEntity.getId());
                userTokenKeyList.add(RedisKey.USER_TOKEN + tokenEntity.getSecretToken());
            }
        }
        if (userTokenIdList.size() != 0) {
            QueryWrapper<UserTokenEntity> ew = new QueryWrapper<>();
            ew.in(true, "id", userTokenIdList);
            userTokenInnerService.remove(ew);
        }
        if (userTokenKeyList.size() != 0) {
            template.delete(userTokenKeyList);
            logger.info("logOutFrontUser中批量删除token：" + userTokenKeyList.toString());
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult<String> getTokenApi(String token) {
        if (StrUtil.isBlank(token)) {
            return RPCResult.custom(UserCodeEnum.USER_TOKEN.getCode(), "token缺失");
        }
        ValueOperations<String, String> ops = template.opsForValue();
        String redisInfo = ops.get(RedisKey.USER_TOKEN + token);
        String msg = "getTokenApi 入参:" + token + ",redis查询结果为" + (StrUtil.isNotBlank(redisInfo) ? "存在" : "不存在");

        //进入redis存在业务逻辑
        if (StrUtil.isNotBlank(redisInfo)) {
            long expire = template.getExpire(RedisKey.USER_TOKEN + token);
            msg += ",剩余有效时限为" + expire;
            //1分钟内防刷
            if (expire < ((ttlMillis - 5) * 60)) {
                //异步处理token刷新业务逻辑
                msg += ",防刷鉴定通过,进行异步刷新";
                JSONObject userInfoJson = JSONUtil.parseObj(redisInfo);
                asyncTask.refreshTask(userInfoJson.getLong("userId"));
            }
            logger.info(msg);
            return RPCResult.success(redisInfo);
        } else {
            //进入redis不存在业务逻辑
            UserTokenEntity userTokenParam = new UserTokenEntity();
            userTokenParam.setSecretToken(token);
            UserTokenEntity userTokenDB = userTokenDao.selectOne(new QueryWrapper<>(userTokenParam));
            msg += ",db 查询为" + (ObjectUtil.isNotNull(userTokenDB) ? userTokenDB.toString() : "不存在");

            if (ObjectUtil.isNotNull(userTokenDB)) {
                Calendar nowCalendar = Calendar.getInstance();
                boolean flag = (nowCalendar.getTime().getTime() < userTokenDB.getAccessExpire().getTime());
                msg += ",过期时间判断为" + (flag ? "未过期" : "已过期");
                if (flag) {
                    //db未过期 进行防刷鉴定 异步方法刷新 redis db有效时间
                    Calendar tokenCalendar = Calendar.getInstance();
                    tokenCalendar.setTime(userTokenDB.getAccessExpire());
                    tokenCalendar.add(Calendar.MINUTE, -ttlMillis + 1);
                    msg += ",当前时间:" + nowCalendar.getTime().toString() + ",db过期时间计算后:" + tokenCalendar.getTime().toString();
                    if (nowCalendar.after(tokenCalendar)) {
                        msg += ",防刷鉴定通过,进行异步刷新";
                        asyncTask.refreshTask(userTokenDB);
                    }
                    logger.info(msg);
                    return RPCResult.success(userTokenDB.getAccessToken());
                }
            }

            //redis db为空,鉴权过期
            //redis为空,db已过期,鉴权过期
            logger.info(msg);
            return RPCResult.custom(UserCodeEnum.USER_TOKEN.getCode(), "token已过期");
        }
    }


    @Override
    public ApiResult<List<UserDTO>> queryUserDTOBySiteCodeApi(String siteCode) {
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        ew.eq("site_code", siteCode);
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("is_demo", UserConstant.IS_F);
        List<UserEntity> userEntities = userDao.selectList(ew);
        List<UserDTO> resultList = new ArrayList<>();
        for (UserEntity user : userEntities) {
            UserDTO userDTO = new UserDTO();
            BeanUtil.copyProperties(user, userDTO);
            resultList.add(userDTO);
        }
        return RPCResult.success(resultList);
    }

    @Override
    public ApiResult batchDeleteUserApi(Long siteId, String ids) {
        String idStr = "(" + ids + ")";
        userDao.batchDeleteUserApi(siteId, idStr);
        return RPCResult.success();
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserLikeUserNameApi(String userName, String siteCode) {
        if (StrUtil.isBlank(userName)) {
            throw new UserException(UserCodeEnum.LACK_INFORMATION.getCode(), "查询的userName为空");
        }
        List<UserDTO> lists = userDao.queryUserLikeUserNameApi(userName, siteCode);
        return RPCResult.success(lists);
    }

    @Override
    public ApiResult verifyPayPwdApi(String logType, Long siteId, Long userId, String updateUserName, String payPwd, Integer platform, String ip, String url) {
        //校验参数
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        if (ObjectUtil.isNull(payPwd)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "支付密码为空");
        }
        if (ObjectUtil.isNull(siteId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "站点信息为空");
        }
        //获取siteDTO
        ApiResult<SiteDTO> siteDTOById = siteService.findSiteDTOById(siteId);
        if (!RPCResult.checkApiResult(siteDTOById)) {
            return RPCResult.custom(siteDTOById.getCode(), siteDTOById.getMessage());
        }
        SiteDTO siteDTO = siteDTOById.getData();
        //判断用户,是否存在，用户状态
        UserEntity user = userDao.findById(userId);
        if (null == user) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (null == user.getSiteId() || !Objects.equals(siteDTO.getId(), user.getSiteId())) {
            return RPCResult.custom(UserCodeEnum.SITEID_ERR.getCode(), UserCodeEnum.SITEID_ERR.getMessage());
        }
        if (Objects.equals(user.getIsDel(), UserConstant.IS_ONE)) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        if (user.getStatus() == null) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        } else {
            if (Objects.equals(UserStatus.USER_STATUS_41.getCode(), user.getStatus())) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "用户被拉黑");
            } else if (Objects.equals(UserStatus.USER_STATUS_11.getCode(), user.getStatus())) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "用户被停用");
            } else if (Objects.equals(UserStatus.USER_STATUS_21.getCode(), user.getStatus())) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "用户被暂停投注");
            } else if (Objects.equals(UserStatus.USER_STATUS_31.getCode(), user.getStatus())) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "用户被冻结");
            }
        }

        int errorNum = user.getChangePayPwdErrNum();
        String passwordMD5 = this.getMD5PwdByRSA(payPwd);
        //解析判断支付密码是否正确
        if (!passwordMD5.equalsIgnoreCase(user.getPayPwd())) {
            //判断支付密码错误次数
            int errorCount = errorNum + 1;
            user.setChangePayPwdErrNum(errorCount);
            String appendString = "";
            if (UserCfg.PAY_CARD_USER_PAY_PASSWORD_ERROR.equals(logType)) {
                appendString = "在使用预付卡支付时,";
            } else if (UserCfg.PAY_WITHDAW_USER_PAY_PASSWORD_ERROR.equals(logType)) {
                appendString = "在提款时,";
            } else if (UserCfg.PAY_TRANSFER_USER_PAY_PASSWORD_ERROR.equals(logType)) {
                appendString = "在转点时,";
            }
            user.setUpdateTime(new Date());
            user.setUpdateBy(updateUserName);
            final String content = String.format("%s:%s支付密码错误[%s]次,累计[%s]次后账号将冻结", user.getUserName(), appendString, errorCount, UserCfg.PAY_PASSWORD_OUT_PUT_ERROR_COUNT);
            if (errorCount >= UserCfg.PAY_PASSWORD_OUT_PUT_ERROR_COUNT) {
                user.setStatus(UserStatus.USER_STATUS_31.getCode());
                userDao.updateById(user);
                kickOutUserApi(user.getId());
                // 保存支付密码错误日志
                logUserInnerService.addUserLog(user.getId(), user.getUserName(), platform, "代理会员：" + user.getUserName() + "。支付密码密码错误次数超过最大限制，账户被冻结！",
                        UserCfg.USER_LOG_FLAG_TYPE_OPER, UserCfg.USER, user.getSiteId(), UserCfg.UPDATE_PAY_PWD, ip, url);
                return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), UserCodeEnum.USER_NAMEORPWD_ERR.getMessage());
            } else {
                logUserInnerService.addUserLog(user.getId(), user.getUserName(), platform, content,
                        UserCfg.USER_LOG_FLAG_TYPE_OPER, UserCfg.USER, user.getSiteId(), UserCfg.UPDATE_PAY_PWD, ip, url);
            }
            userDao.updateById(user);
            return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), UserCodeEnum.USER_NAMEORPWD_ERR.getMessage());
        }
        //密码验证成功
        user.setChangePwdErrNum(UserConstant.IS_ZERO);
        userDao.updateById(user);
        return RPCResult.success();
    }

    @Override
    public ApiResult<Map<String, Integer>> getCountOfRegisterByTimeAndRegSourceApi(String startTime, String endTime, String siteCode, List<Long> idList) {
        if (StrUtil.isBlank(startTime)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "开始时间参数为空");
        }
        if (StrUtil.isBlank(endTime)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "结束时间参数为空");
        }
        if (StrUtil.isBlank(siteCode)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "siteCode参数为空");
        }
        if (CollectionUtil.isEmpty(idList)){
            idList = new ArrayList<>();
        }
        Integer PCCount = userDao.getCountOfRegisterByTimeAndRegSourceApi(UserConstant.PC, startTime, endTime, siteCode, idList);
        Integer APPCount = userDao.getCountOfRegisterByTimeAndRegSourceApi(UserConstant.APP, startTime, endTime, siteCode, idList);
        Map<String, Integer> map = new HashMap<>();
        map.put("PC", PCCount);
        map.put("APP", APPCount);
        return RPCResult.success(map);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserByListIdApi(List<Long> idList) {
        return RPCResult.success(userDao.queryUserByListIdApi(idList));
    }

    @Override
    public ApiResult<List<Long>> queryFilterIds(Long siteId, List<Long> idList) {
        Assert.isNull(siteId);
        Assert.isNull(idList);
        return RPCResult.success(userDao.queryFilterIds(siteId, idList));
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserByIpApi(String ip, String siteCode) {
        return RPCResult.success(userDao.queryUserByIpApi(ip, siteCode));
    }

    @Override
    public ApiResult<String> getUserNameById(Long id) {
        String userName = userDao.findUserNameById(id);
        if (StrUtil.isEmpty(userName)) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        return RPCResult.success(userName);
    }

    @Override
    public ApiResult<Long> getIdByUserName(String userName, Long siteId) {
        UserEntity userEntity = userDao.findByUserName(userName, siteId);
        if (ObjectUtil.isNull(userEntity)) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        return RPCResult.success(userEntity.getId());
    }


    @Override
    public void createSecurityCode(String sessionUuid) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //创建验证码模型，指定图片的宽和高
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(UserCfg.CAPTCHA_WIDE, UserCfg.CAPTCHA_HIGH, UserCfg.CAPTCHA_COUNT, UserCfg.CAPTCHA_LINE_COUNT);
        //将验证码以<key,value>形式缓存到redis
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(RedisKey.SECURITY_CODE + sessionUuid, captcha.getCode(), securityCodeTime, TimeUnit.MINUTES);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            //设置响应头通知浏览器以图片的形式打开
            response.setContentType("image/jpeg");//等同于response.setHeader("Content-Type", "image/jpeg");
            //设置响应头控制浏览器不要缓存
            response.setDateHeader("expries", -1);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            ImageIO.write(captcha.getImage(), "jpg", out);
        } catch (IOException e) {
            logger.error("生成验证码失败" + e);
            throw new UserException(UserCodeEnum.ERR_CREATE_VERIFYCODE.getCode());
        } finally {
            try {
                assert out != null;
                out.close();
            } catch (IOException e) {
                logger.error("异常信息:", e);
            }
        }
    }

    @Override
    public String getRSAPublicKey() {
        ValueOperations<String, String> ops = template.opsForValue();
        String publicKey = ops.get(RedisKey.RSA_PUBLIC_KEY);
        String privateKey = ops.get(RedisKey.RSA_PRIVATE_KEY);
        if (StringUtils.isNotEmpty(publicKey) && StringUtils.isNotEmpty(privateKey)) {
            setRSAKeyExpTime(publicKey, privateKey);
            return publicKey;
        } else {
            try {
                KeyPair keyPair = RSAUtil.getKeyPair();
                publicKey = RSAUtil.getPublicKey(keyPair);
                privateKey = RSAUtil.getPrivateKey(keyPair);
                setRSAKeyExpTime(publicKey, privateKey);
                return publicKey;
            } catch (Exception e) {
                logger.error("异常信息:", e);
            }
            throw new UserException(UserCodeEnum.CREATE_RSA_FAILURE.getCode(), UserCodeEnum.CREATE_RSA_FAILURE.getMessage());
        }
    }

    private void setRSAKeyExpTime(String publicKey, String privateKey) {
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(RedisKey.RSA_PUBLIC_KEY, publicKey, rsaTime, TimeUnit.MINUTES);
        ops.set(RedisKey.RSA_PRIVATE_KEY, privateKey, rsaTime, TimeUnit.MINUTES);
    }

    @Override
    public String getRSAPrivateKey() {
        ValueOperations<String, String> ops = template.opsForValue();
        String publicKey = ops.get(RedisKey.RSA_PUBLIC_KEY);
        String privateKey = ops.get(RedisKey.RSA_PRIVATE_KEY);
        if (StringUtils.isNotEmpty(publicKey) && StringUtils.isNotEmpty(privateKey)) {
            setRSAKeyExpTime(publicKey, privateKey);
            return privateKey;
        } else {
            return getPrivateKey();
        }
    }

    /**
     * Author: Brady
     * Description: 获取RSA私钥
     * Date: 2018/5/7
     */
    private String getPrivateKey() {
        try {
            KeyPair keyPair = RSAUtil.getKeyPair();
            String publicKey = RSAUtil.getPublicKey(keyPair);
            String privateKey = RSAUtil.getPrivateKey(keyPair);
            setRSAKeyExpTime(publicKey, privateKey);
            return privateKey;
        } catch (Exception e) {
            logger.error("异常信息:", e);
        }
        throw new UserException(UserCodeEnum.CREATE_RSA_FAILURE.getCode(), UserCodeEnum.CREATE_RSA_FAILURE.getMessage());
    }

    @Override
    @Transactional(noRollbackFor = UserException.class)
    public String getMD5PwdByRSA(String password) {
        //获取私钥，解密
        String privateRSAKey = this.getRSAPrivateKey();
        Assert.isEmpty(privateRSAKey, "RAS私钥获取失败");
        String MD5Pwd;
        try {
            MD5Pwd = new String(RSAUtil.privateDecrypt(RSAUtil.base642Byte(password), RSAUtil.string2PrivateKey(privateRSAKey)));
        } catch (Exception e) {
            throw new UserException(UserCodeEnum.WRONG_PARSE_PWD.getCode());
        }
        return MD5Pwd;
    }

    @Override
    public Map<String, Object> getUserProfile(Long userId) {
        UserEntity user = userDao.findById(userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userName", user.getUserName());
        resultMap.put("lastLoginTime", user.getLastLoginTime());
        resultMap.put("loginCount", user.getLoginCount());
        resultMap.put("id", user.getId().toString());
        resultMap.put("status", user.getStatus());
        resultMap.put("isDemo", user.getIsDemo());
        resultMap.put("siteId", user.getSiteId().toString());
        return resultMap;
    }

    @Override
    public Boolean updateUserProfile(UserEntity user) {
        user.setUpdateBy(user.getUserName());
        user.setUpdateTime(new Date());
        return userDao.updateById(user) == 1;
    }

    @Override
    public String createPwd(String pwd) {
        try {
            String rsaPublicKey = getRSAPublicKey();
            String MD5Pwd = SecureUtil.md5(pwd);
            return RSAUtil.byte2Base64(RSAUtil.publicEncrypt(MD5Pwd.getBytes(), RSAUtil.string2PublicKey(rsaPublicKey)));
        } catch (Exception e) {
            logger.error("异常信息:", e);
        }
        throw new UserException(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
    }

    @Override
    public String loginTest(Long id) {
        UserEntity user = userDao.findById(id);
        ValueOperations<String, String> ops = template.opsForValue();
        if (ObjectUtil.isNull(user)) {
            SysUserEntity sysUser = sysUserDao.selectById(id);
            if (ObjectUtil.isNotNull(sysUser)) {
                if (ObjectUtil.equal(0, sysUser.getIsDel())) {
                    JSONObject userJson = new JSONObject();
                    userJson.put("userId", sysUser.getId().toString());
                    userJson.put("siteId", sysUser.getSiteId().toString());
                    userJson.put("userName", sysUser.getUserName());
                    userJson.put("siteCode", sysUser.getSiteCode());
                    String uuid = RandomUtil.simpleUUID();
                    ops.set(RedisKey.USER_TOKEN + uuid, userJson.toString(), ttlMillis, TimeUnit.MINUTES);//测试数据token有效期7天
                    return uuid;
                }
            }
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        if (Objects.equals(user.getIsDel(), UserConstant.IS_ONE)) {
            throw new UserException(UserCodeEnum.USER_UNNORMAL_STATUS.getCode());
        }
        JSONObject userJson = new JSONObject();
        userJson.put("userId", user.getId().toString());
        userJson.put("siteId", user.getSiteId().toString());
        userJson.put("userName", user.getUserName());
        userJson.put("isDemo", user.getIsDemo().toString());
        userJson.put("siteCode", user.getSiteCode());
        String uuid = RandomUtil.simpleUUID();
        ops.set(RedisKey.USER_TOKEN + uuid, userJson.toString(), ttlMillis, TimeUnit.MINUTES);
        return uuid;
    }

    @Override
    public Map<String, Object> queryDemoUserPage(Long siteId, Page page, String loginBeginTime, String loginEndTime, String orderBeginTime, String orderEndTime, String gameCode) {
        if (StrUtil.isNotBlank(loginBeginTime)) {
            loginBeginTime += " 00:00:00";
        }
        if (StrUtil.isNotBlank(loginEndTime)) {
            loginEndTime += " 23:59:59";
        }
        if (StrUtil.isNotBlank(orderBeginTime)) {
            orderBeginTime += " 00:00:00";
        }
        if (StrUtil.isNotBlank(orderEndTime)) {
            orderEndTime += " 23:59:59";
        }
        List<Map<String, Object>> resultList = userDao.queryDemoUserPage(siteId, page, loginBeginTime, loginEndTime, orderBeginTime, orderEndTime, gameCode);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultList", resultList);
        resultMap.put("total", page.getTotal());
        return resultMap;
    }

    @Override
    public Map<String, Object> queryTestUserPage(Page page, Map map) {
        List<Map<String, Object>> resultList = userDao.queryTestUserPage(page, map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultList", resultList);
        resultMap.put("total", page.getTotal());
        return resultMap;
    }

    @Override
    public Boolean deleteUserByUserId(Long userId) {
        return userDao.deleteByUserId(userId) > 0;
    }

    @Override
    public Boolean initUserPayPwd(Long userId, Long siteId, String payPwd) {
        //1、用户状态判断
        UserEntity userEntity = userDao.findById(userId);
        if (ObjectUtil.isNull(userEntity)) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        if (Objects.equals(UserConstant.IS_T, userEntity.getIsDel()) || !Objects.equals(UserStatus.USER_STATUS_10.getCode(), userEntity.getStatus())) {
            throw new UserException(UserCodeEnum.USER_UNNORMAL_STATUS.getCode());
        }
        if (!StrUtil.isEmpty(userEntity.getPayPwd())) {
            throw new UserException(UserCodeEnum.USER_INFORMATION_EXIST.getCode());
        }
        //2、获取私钥对，解密
        String password = userInnerService.getMD5PwdByRSA(payPwd);
        userEntity.setPayPwd(password);
        return userDao.updateById(userEntity) == 1;
    }

    @Override
    public Boolean confirmExistUserName(Long siteId, String userName) {
        UserEntity userEntity = userDao.confirmExistUserName(siteId, userName);
        if (ObjectUtil.isNotNull(userEntity)) {
            throw new UserException(UserCodeEnum.USER_INFORMATION_EXIST.getCode());
        }
        return true;
    }

    @Override
    public boolean changeUserStatus(Long userId, Integer status) {
        UserEntity userTempEntity = userDao.findById(userId);
        Assert.isNull(userTempEntity, "用户不存在");
        userTempEntity = new UserEntity();
        userTempEntity.setId(userId);
        userTempEntity.setStatus(status);
        userTempEntity.setUpdateTime(new Date());
        return userDao.updateById(userTempEntity) == 1;
    }

    @Override
    public ApiResult<Boolean> updateGameCategoryAndTimeApi(Long userId, String category) {
        Date date = new Date();
        return RPCResult.success(userDao.updateGameCategoryAndTime(userId, category, date) == 1);
    }

    @Override
    public ApiResult<UserDTO> getUserDTOById(Long userId, Long siteId) {
        UserEntity user = new UserEntity();
        user.setId(userId);
        if (siteId != null && siteId != 0L) {
            user.setSiteId(siteId);
        }
        user.setIsDel(UserConstant.IS_F);
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<>(user));
        if (ObjectUtil.isNull(userEntity)) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(userEntity, userDTO);
        return RPCResult.success(userDTO);
    }

    @Override
    @Transactional(noRollbackFor = JQException.class)
    public ApiResult<?> loginSubmitApi(UserModelDTO userModelDTO, String ip, String url, String ipAttribution) {
        Map<String, Object> validMap = new HashMap<>();
        ApiResult<?> apiResult = verifLoginParam(userModelDTO, ip, url, ipAttribution, validMap);
        if (!RPCResult.checkApiResult(apiResult)) {
            return RPCResult.custom(apiResult.getCode(), apiResult.getMessage(), JSONUtil.parse(validMap));
        }
        UserEntity user = (UserEntity) apiResult.getData();
        //密码验证成功，处理用户登录信息
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        user.setLoginCount(user.getLoginCount() + 1);
        user.setLoginErrCount(UserConstant.IS_ZERO);
        userDao.updateById(user);
        // 设置用户头像（针对老用户设置）
        userInfoService.updateUserPicture(user.getId(), "");
        // 保存登录成功日志
        logUserInnerService.addUserLog(user.getId(), user.getUserName(), userModelDTO.getPlatformType(), "代理会员：" + user.getUserName(),
                UserCfg.LOGIN_SUCCESS, UserCfg.USER, user.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_LOGIN, ip, url, ipAttribution, user.getSiteCode());
        String loginVerifyKey = RedisKey.USER_LOGIN_VERIFY + ip;
        String value = template.opsForValue().get(loginVerifyKey);
        if (StrUtil.isNotEmpty(value)) {
            template.delete(loginVerifyKey);
        }
        return RPCResult.success(this.getTokenJson(user, ttlMillis, userModelDTO.getPlatformType()));
    }

    /**
     * 登录参数校验
     *
     * @param userModelDTO
     * @param ip
     * @param url
     * @param ipAttribution
     * @param validMap
     * @return
     */
    private ApiResult<?> verifLoginParam(UserModelDTO userModelDTO, String ip, String url, String ipAttribution, Map<String, Object> validMap) {
        validMap.put("needValid", false);
        //校验参数
        if (ObjectUtil.isNull(userModelDTO.getUserName())) {
            return RPCResult.custom(UserCodeEnum.LACK_USER_REGISTER_INFO.getCode(), UserCodeEnum.LACK_USER_REGISTER_INFO.getMessage());
        }
        if (ObjectUtil.isNull(userModelDTO.getPassword())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_PASSWORD.getCode(), UserCodeEnum.EMPTY_VALUE_PASSWORD.getMessage());
        }
        if (ObjectUtil.isNull(userModelDTO.getPlatformType())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_PLATFORMTYPE.getCode(), UserCodeEnum.EMPTY_VALUE_PLATFORMTYPE.getMessage());
        }

        //参数转换
        Integer platformType = userModelDTO.getPlatformType();
        //获取siteDTO
        ApiResult<SiteDTO> siteDTOById = siteService.findSiteDTOById(userModelDTO.getSiteId());
        if (!RPCResult.checkApiResult(siteDTOById)) {
            return RPCResult.custom(siteDTOById.getCode(), siteDTOById.getMessage());
        }
        SiteDTO siteDTO = siteDTOById.getData();
        //判断用户,是否存在，用户状态
        UserEntity user = userDao.findByUserName(userModelDTO.getUserName(), userModelDTO.getSiteId());
        if (null == user) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (null == user.getSiteId() || !Objects.equals(siteDTO.getId(), user.getSiteId())) {
            return RPCResult.custom(UserCodeEnum.SITEID_ERR.getCode(), UserCodeEnum.SITEID_ERR.getMessage());
        }
        if (Objects.equals(user.getIsDel(), UserConstant.IS_ONE)) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        if (user.getStatus() == null) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        } else {
            if (Objects.equals(UserStatus.USER_STATUS_11.getCode(), user.getStatus())) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "用户被停用");
            } else if (Objects.equals(UserStatus.USER_STATUS_31.getCode(), user.getStatus())) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "用户被冻结");
            }
        }

        validMap.put("needValid", true);
        int errorNum = user.getLoginErrCount();
        // 密码错误1次，需要验证码
        if (StringUtils.isNotEmpty(userModelDTO.getVerifyCode())) {
            validMap.put("ErrorTimes", errorNum);
            ApiResult<?> verifyCodeResult = verifyCodeResult(userModelDTO, validMap, errorNum);
            if (!RPCResult.checkApiResult(verifyCodeResult)) {
                return verifyCodeResult;
            }
        }

        String passwordMD5 = this.getMD5PwdByRSA(userModelDTO.getPassword());
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(passwordMD5);
        }
        //解析判断密码是否正确
        if (!passwordMD5.equalsIgnoreCase(user.getPassword())) {
            //判断密码错误次数
            int errorCount = errorNum + 1;
            user.setLoginErrCount(errorCount);
            if (errorCount >= 6) {
                user.setStatus(UserStatus.USER_STATUS_31.getCode());
                user.setUpdateTime(new Date());
                userDao.updateById(user);
                // 保存账户冻结日志
                logUserInnerService.addUserLog(user.getId(), user.getUserName(), platformType, "代理会员：" + user.getUserName() + "。密码输入错误次数超过最大限制，账户被冻结！",
                        UserCfg.LOGIN_FAILURE, UserCfg.USER, user.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_LOGIN, ip, url, ipAttribution, siteDTO.getSiteCode());
                return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), "密码验证达到最大错误次数,账号已被冻结");
            }
            logUserInnerService.addUserLog(user.getId(), user.getUserName(), platformType, "代理会员：" + user.getUserName() + "在登陆时，登录密码错误[" + errorCount + "]次,累计[5]次后账号将禁用",
                    UserCfg.LOGIN_FAILURE, UserCfg.USER, user.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_LOGIN, ip, url, ipAttribution, siteDTO.getSiteCode());
            String logVerifyKey = RedisKey.USER_LOGIN_VERIFY + ip;
            //同一ip 24小时内 若上次密码错误，则需输入验证码
            template.opsForValue().set(logVerifyKey, ip, 24, TimeUnit.HOURS);
            userDao.updateById(user);
            validMap.put("ErrorTimes", errorCount);
            return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), UserCodeEnum.USER_NAMEORPWD_ERR.getMessage());
        }
        return RPCResult.success(user);
    }

    /**
     * 验证码判断
     *
     * @param userModelDTO
     * @param validMap
     * @param errorNum
     * @return
     */
    private ApiResult<?> verifyCodeResult(UserModelDTO userModelDTO, Map<String, Object> validMap, int errorNum) {
        if (ObjectUtil.isNull(userModelDTO.getCurrTime())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_CURRTIME.getCode(), UserCodeEnum.EMPTY_CURRTIME.getMessage());
        }
        String verifyCode = userModelDTO.getVerifyCode();
        if (ObjectUtil.isNull(verifyCode)) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VERIFYCODE.getCode(), UserCodeEnum.EMPTY_VERIFYCODE.getMessage());
        }
        // 按测试要求增加登录时超级验证码
        //根据环境变量在dev与test环境生效
        String redisCode = template.opsForValue().get(RedisKey.SECURITY_CODE + userModelDTO.getCurrTime());
        if (getActiveProfile().equals("dev") || getActiveProfile().equals("test")) {
            if (ObjectUtil.notEqual("@Ly!", verifyCode)) {
                if (ObjectUtil.isNull(redisCode)) {
                    return RPCResult.custom(UserCodeEnum.EMPTY_VERIFYCODE.getCode(), "验证码失效，请刷新验证码");
                }
                if (!redisCode.equalsIgnoreCase(verifyCode)) {
                    return RPCResult.custom(UserCodeEnum.ERR_VERIFYCODE.getCode(), UserCodeEnum.ERR_VERIFYCODE.getMessage());
                }
            }
        } else {
            if (ObjectUtil.isNull(redisCode)) {
                return RPCResult.custom(UserCodeEnum.EMPTY_VERIFYCODE.getCode(), "验证码失效，请刷新验证码");
            }
            if (!redisCode.equalsIgnoreCase(verifyCode)) {
                return RPCResult.custom(UserCodeEnum.ERR_VERIFYCODE.getCode(), UserCodeEnum.ERR_VERIFYCODE.getMessage());
            }
        }
        return RPCResult.success();
    }


    @Override
    public ApiResult<UserDTO> registerUserApi(UserModelDTO userModelDTO) {
        return userRelieveService.registerUserApi(userModelDTO);
    }

    @Override
    public ApiResult<UserDTO> registerDemoUserApi(String ip, Long siteId, Integer platformType) {
        if (ObjectUtil.isNull(siteId) || ObjectUtil.isNull(platformType)) {
            return RPCResult.custom(UserCodeEnum.LACK_USER_REGISTER_INFO.getCode(), UserCodeEnum.LACK_USER_REGISTER_INFO.getMessage());
        }
        //判断站点是否开启免费试玩功能
        ApiResult<SiteDTO> siteDTOApi = siteService.findSiteDTOById(siteId);
        if (!RPCResult.checkApiResult(siteDTOApi)) {
            return RPCResult.custom(siteDTOApi.getCode(), siteDTOApi.getMessage());
        }
        SiteDTO siteDTO = siteDTOApi.getData();
        if (ObjectUtil.equal(UserConstant.IS_F, siteDTO.getIsDemo())) {
            return RPCResult.custom(UserCodeEnum.SITE_DEMO_DISABLED.getCode(), UserCodeEnum.SITE_DEMO_DISABLED.getMessage());
        }
        QueryWrapper<UserEntity> selectParam = new QueryWrapper<>();
        selectParam.eq("is_del", UserConstant.IS_F);
        selectParam.eq("site_id", siteId);
        selectParam.eq("last_login_ip", ip);
        selectParam.eq("is_demo", UserConstant.IS_T);
        List<UserEntity> userEntityList = userDao.selectList(selectParam);
        UserEntity highUserModel = userDao.findByUserName(UserCfg.DEFAULT_SYS_USER_NAME, siteId);
        UserReferEntity defaultUserReferEntity;
        //根据站点id查询默认代理
        Date date = new Date();
        if (CollectionUtil.isEmpty(userEntityList)) {
            defaultUserReferEntity = userReferDao.selectById(siteDTO.getDemoDefaultProxyId());
            if (null == defaultUserReferEntity || ObjectUtil.equal(UserConstant.IS_T, defaultUserReferEntity.getIsDel())) {
                throw new UserException(UserCodeEnum.REFERCODE_NOT_EXIST.getCode());
            }
            if (ObjectUtil.equal(UserConstant.IS_F, defaultUserReferEntity.getIsEnable())) {
                throw new UserException(UserCodeEnum.REFERCODE_DISABLED.getCode());
            }
            //查询用户等级
            Long userRankId = userRankDao.findDefaultRank(siteId);
            if (null == userRankId) {
                return RPCResult.custom(UserCodeEnum.SITEID_ERR.getCode(), UserCodeEnum.SITEID_ERR.getMessage());
            }
            // 生成试玩账户登录名称
            String userName;
            while (true) {
                userName = createDemoUser();
                QueryWrapper<UserEntity> selectDemoUser = new QueryWrapper<>();
                selectDemoUser.eq("user_name", userName);
                selectDemoUser.eq("is_del", UserConstant.IS_F);
                selectDemoUser.eq("site_id", siteId);
                selectDemoUser.eq("is_demo", UserConstant.IS_T);
                UserEntity demoUser = userDao.selectOne(selectDemoUser);
                if (demoUser == null) {
                    break;
                }
            }
            UserEntity userEntity = new UserEntity();
            userEntity.setId(IdWorker.getId());
            String MD5Pwd = SecureUtil.md5(UserCfg.USER_DEMO_PWD);
            userEntity.setPassword(MD5Pwd);
            userEntity.setUserName(userName);
            userEntity.setIsDel(UserConstant.IS_F);
            userEntity.setLastLoginIp(ip);
            userEntity.setSiteId(siteId);
            userEntity.setIsDemo(UserConstant.IS_T);
            userEntity.setUserRankId(userRankId);
            userEntity.setCreateTime(date);
            userEntity.setUpdateTime(date);
            userEntity.setRandom(RandomUtil.randomInt(6));
            userEntity.setSiteCode(siteDTO.getSiteCode());
            userEntity.setLastLoginTime(date);
            userEntity.setLoginCount(1);
            userEntity.setCreateBy(userEntity.getUserName());
            userEntity.setUpdateBy(userEntity.getUserName());
            userEntity.setStatus(10);
            UserInfoEntity userInfoEntity = new UserInfoEntity();
            userInfoEntity.setId(userEntity.getId());
            userInfoEntity.setRealName(UserCfg.DEFAULT_DEMO_REAL_NAME);
            userInfoEntity.setUpdateTime(date);
            userInfoEntity.setCreateTime(date);
            userInfoEntity.setRegSource(platformType);
            userInfoEntity.setRegIp(ip);
            //9、保存对应表信息
            UserEntity finalUser = saveUser(userEntity, userInfoEntity, siteId, UserConstant.IS_ONE, "");
            logger.debug(String.format("试玩用户: %s ,站点code: %s,保存用户信息成功", finalUser.getUserName(), finalUser.getSiteCode()));
            //保存用户分组信息
            UserBindingDefaultGroupReq userBindingDefaultGroupReq = new UserBindingDefaultGroupReq();
            userBindingDefaultGroupReq.setUserId(finalUser.getId());
            userBindingDefaultGroupReq.setIsDemo(finalUser.getIsDemo().toString());
            userBindingDefaultGroupReq.setSiteCode(finalUser.getSiteCode());
            userBindingDefaultGroupReq.setUserName(finalUser.getUserName());
            ApiResult apiResult = groupService.userBindingDefaultGroup(userBindingDefaultGroupReq);
            if (!RPCResult.checkApiResult(apiResult)) {
                throw new UserException(apiResult.getCode(), apiResult.getMessage());
            }
            logger.debug(String.format("试玩用户: %s ,站点code: %s,保存用户分组成功", finalUser.getUserName(), finalUser.getSiteCode()));
            // 保存代理关系
            Integer integer = userProxyInnerService.saveUserProxy(defaultUserReferEntity.getUserId(), finalUser.getId(), finalUser.getUserName());
            if (integer == 0) {
                throw new UserException(UserCodeEnum.SAVE_PROXY_FAIL.getCode(), UserCodeEnum.SAVE_PROXY_FAIL.getMessage());
            }
            //保存用户返点
            UserRebateEntity userRebateEntity = new UserRebateEntity();
            BeanUtil.copyProperties(defaultUserReferEntity, userRebateEntity);
            userRebateEntity.setId(finalUser.getId());
            userRebateEntity.setIsProxy(UserConstant.IS_F);
            userRebateTransService.saveUserRebate(userRebateEntity, defaultUserReferEntity.getUserId());
            logger.debug(String.format("试玩用户: %s ,站点code: %s,保存用户返点成功", finalUser.getUserName(), finalUser.getSiteCode()));
            //初始化资金记录
            AddUserFundReq addUserFundReq = new AddUserFundReq();
            addUserFundReq.setSiteCode(finalUser.getSiteCode());
            addUserFundReq.setUserId(Convert.toStr(finalUser.getId()));
            addUserFundReq.setUsername(finalUser.getUserName());
            addUserFundReq.setTotalAmount(UserCfg.DEMO_AMOUNT);
            addUserFundReq.setCanAmount(UserCfg.DEMO_AMOUNT);
            addUserFundReq.setSuperiorUserId(highUserModel.getId().toString());  //顶级用户
            ApiResult apiResultUserFund = userFundService.addUserFund(addUserFundReq);
            if (!RPCResult.checkApiResult(apiResultUserFund)) {
                throw new UserException(apiResultUserFund.getCode(), apiResultUserFund.getMessage());
            }
            logger.debug(String.format("试玩用户: %s ,站点code: %s,初始化资金记录成功", userEntity.getUserName(), userEntity.getSiteCode()));
            userDao.updateById(userEntity);
            UserDTO userDTO = new UserDTO();
            String token = getTokenJson(userEntity, ttlMillis, platformType);
            BeanUtil.copyProperties(userEntity, userDTO);
            userDTO.setToken(token);
//            ApiResult<?> updateUserPicture = userInfoService.updateUserPicture(userDTO.getId(), "");
//            if (!RPCResult.checkApiResult(updateUserPicture)) {
//                throw new UserException(UserCodeEnum.USER_REGISTER_FAIL.getCode(), "保存用户图片异常");
//            }
            logger.info(String.format("试玩用户: %s ,站点code: %s ,注册成功", userDTO.getUserName(), userDTO.getSiteCode()));
            return RPCResult.success(userDTO);
        } else {
            UserEntity selectUser = userEntityList.get(0);
            selectUser.setLastLoginIp(ip);
            selectUser.setLastLoginTime(date);
            selectUser.setLoginCount(selectUser.getLoginCount() + 1);
            selectUser.setUpdateBy(selectUser.getUserName());
            selectUser.setCreateTime(date);
            selectUser.setUpdateTime(date);
            userDao.updateById(selectUser);
            UserDTO userDTO = new UserDTO();
            String token = getTokenJson(selectUser, ttlMillis, platformType);
            BeanUtil.copyProperties(selectUser, userDTO);
            userDTO.setToken(token);
//            ApiResult<?> updateUserPicture = userInfoService.updateUserPicture(userDTO.getId(), "");
//            if (!RPCResult.checkApiResult(updateUserPicture)) {
//                throw new UserException(UserCodeEnum.USER_REGISTER_FAIL.getCode(), "保存用户图片异常");
//            }
            logger.info(String.format("试玩用户: %s ,站点code: %s ,注册成功", userDTO.getUserName(), userDTO.getSiteCode()));
            return RPCResult.success(userDTO);
        }
    }

    @Override
    public ApiResult<String> createSecurityCodeApi(String sessionUuid) {
        Integer s1 = RandomUtil.randomInt(10);
        Integer s2 = RandomUtil.randomInt(10);
        Integer s3 = RandomUtil.randomInt(10);
        Integer s4 = RandomUtil.randomInt(10);
        StringBuffer result = new StringBuffer();
        StringBuffer result1 = new StringBuffer();
        result.append(s1).append(" ").append(s2).append(" ").append(s3).append(" ").append(s4);
        result1.append(s1).append(s2).append(s3).append(s4);
        ValueOperations<String, String> ops = template.opsForValue();
        ops.set(RedisKey.SECURITY_CODE + sessionUuid, String.valueOf(result1), 5, TimeUnit.MINUTES);
        return RPCResult.success(result.toString());
    }

    @Override
    public ApiResult<String> getRsaPublicKeyApi() {
        String rsaPublicKey = userInnerService.getRSAPublicKey();
        return RPCResult.success(rsaPublicKey);
    }

    @Override
    public ApiResult<List<UserLevelDTO>> getUserLevelBySiteCodeApi(String siteCode) {
        List<UserWrapper> list = userDao.getUserLevelBySiteCode(siteCode);
        if (CollectionUtil.isEmpty(list)) {
            return RPCResult.success(list);
        }
        Long siteId = list.get(0).getSiteId();
        List<Long> idList = new ArrayList<>();
        for (UserWrapper userWrapper : list) {
            idList.add(userWrapper.getUserId());
        }
        Map<Long, UserProxyEntity> dirHighUserProxyMap = userProxyInnerService.getDirHighUserProxyMapByIdList(idList, siteId);
        List<UserLevelDTO> dotList = new ArrayList<>();
        for (UserWrapper userWrapper : list) {
            UserLevelDTO dto = new UserLevelDTO();
            dto.setUserId(userWrapper.getUserId());
            dto.setUserName(userWrapper.getUserName());
            dto.setRankId(userWrapper.getRankId());
            dto.setRankName(userWrapper.getRankName());
            UserProxyEntity userProxyEntity = dirHighUserProxyMap.get(userWrapper.getUserId());
            if (userProxyEntity != null) {
                dto.setHighLevelId(userProxyEntity.getHighUserId());
                dto.setHighLevelName(userProxyEntity.getHighUserName());
            }
            dotList.add(dto);
        }
        return RPCResult.success(dotList);
    }

    @Override
    public ApiResult<Map<Integer, String>> getUserStatusMapApi() {
        return RPCResult.success(UserStatus.getNameByCodeType(UserStatus.USER_STATUS_10.getCodeType()));
    }

    @Override
    public ApiResult<InitParameters> getUserListParamApi(Long siteId) {
        InitParameters initParameters = new InitParameters();
        ApiResult<Map<Integer, String>> userStatusApi = this.getUserStatusMapApi();
        if (RPCResult.checkApiResult(userStatusApi)) {
            initParameters.setUserStatusMap(userStatusApi.getData());
        }
        Map<Long, Integer> allRankMapBySiteId = userRankInnerService.findAllRankMapBySiteId(siteId);
        Map<Long, Integer> resultMap = new TreeMap<>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return o1.compareTo(o2);
            }
        });
        resultMap.putAll(allRankMapBySiteId);
        initParameters.setUserRankMap(resultMap);
        return RPCResult.success(initParameters);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserDTOByListIdApi(List<Long> listId, Long siteId, Integer isDemo) {
        if (CollectionUtil.isEmpty(listId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "集合数据缺失");
        }
        List<UserDTO> resultList = userDao.queryUserDTOByListIdApi(listId, siteId, isDemo);
        return RPCResult.success(resultList);
    }

    @Override
    public ApiResult<UserDTO> getUserByUserNameApi(String userName, Long siteId) {
        if (StrUtil.isEmpty(userName)) {
            throw new UserException(UserCodeEnum.USER_NAME_IS_NULL.getCode());
        }
        if (siteId == null) {
            throw new UserException(UserCodeEnum.SITE_ID_IS_NULL.getCode());
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setSiteId(siteId);
        userEntity.setIsDel(UserConstant.IS_F);
        UserEntity user = userDao.selectOne(new QueryWrapper<>(userEntity));
        if (user == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        UserDTO userDTO = new UserDTO();
        BeanUtil.copyProperties(user, userDTO);
        //新增代理标识属性
        UserRebateEntity userRebateEntity = userRebateDao.selectById(user.getId());
        if (userRebateEntity == null) {
            return RPCResult.custom(UserCodeEnum.USER_REBATE_NOT_EXIST.getCode(), UserCodeEnum.USER_REBATE_NOT_EXIST.getMessage());
        }
        userDTO.setIsProxy(userRebateEntity.getIsProxy());
        return RPCResult.success(userDTO);
    }

    @Override
    public ApiResult updateUserPwdApi(Long userId, String oldPwd, String newPwd, Long siteId, Integer
            platformType, String token, String ip, String url) {
        //1.用户状态判断
        UserEntity customer = userDao.findById(userId);
        if (customer == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (siteId == null || !Objects.equals(customer.getSiteId(), siteId)) {
            return RPCResult.custom(UserCodeEnum.SITEID_ERR.getCode(), UserCodeEnum.SITEID_ERR.getMessage());
        }
        if (ObjectUtil.equal(UserConstant.IS_ONE, customer.getIsDel())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        if (ObjectUtil.equal(UserConstant.IS_ONE, customer.getIsDemo())) {
            return RPCResult.custom(UserCodeEnum.USER_NO_AUTHORITY.getCode(), UserCodeEnum.USER_NO_AUTHORITY.getMessage());
        }
        if (ObjectUtil.isNull(customer.getStatus()) || !ObjectUtil.equal(UserStatus.USER_STATUS_10.getCode(), customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        //2.判断原密码输错次数是否超过允许的最大次数
        int maxPwdErrNum; //修改密码，原密码允许输错的最大次数
        ApiResult<Map<String, String>> sysKeyValueMap = keyValueService.findKeyValueMapByDictCode(UserConstant.CHANGE_PASS_WORD_ERROR_NUM);
        if (RPCResult.checkApiResult(sysKeyValueMap)) {
            maxPwdErrNum = NumberUtils.toInt(sysKeyValueMap.getData().get(UserConstant.CHANGE_PASS_WORD_ERROR_NUM));
        } else {
            maxPwdErrNum = UserCfg.MAX_ERROR_COUNT;
        }
        Map<String, Object> response = new HashMap<>();
        response.put("pwdErrNum", customer.getChangePwdErrNum()); //当前用户原密码输错次数
        response.put("maxPwdErrNum", maxPwdErrNum); //原密码允许输错的最大次数
        //删除token，保存日志
        if (customer.getChangePwdErrNum() >= maxPwdErrNum) {
            customer.setStatus(UserStatus.USER_STATUS_31.getCode());
            userDao.updateById(customer);
            //删除用户token
            template.delete(RedisKey.USER_TOKEN + token);
            userTokenDao.deleteById(customer.getId());
            logger.info("用户原密码输错次数已超允许的最大次数，账号已被冻结");
            logger.info("用户密码错误达到最大，清除token :'" + token + "'");
            logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。密码输入错误次数超过最大限制，账户被冻结！",
                    UserCfg.UPDATE_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "原密码输错次数已超允许的最大次数，账号已被冻结");
        }
        // 4.获取私钥对，解密
        String rsaPrivateKey = getRSAPrivateKey();
        if (StrUtil.isBlank(rsaPrivateKey)) {
            return RPCResult.custom(UserCodeEnum.CREATE_RSA_FAILURE.getCode(), UserCodeEnum.CREATE_RSA_FAILURE.getMessage());
        }
        String MD5OldPwd;
        String MD5NewPwd;
        try {
            MD5OldPwd = new String(RSAUtil.privateDecrypt(RSAUtil.base642Byte(oldPwd), RSAUtil.string2PrivateKey(rsaPrivateKey)));
            MD5NewPwd = new String(RSAUtil.privateDecrypt(RSAUtil.base642Byte(newPwd), RSAUtil.string2PrivateKey(rsaPrivateKey)));
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return RPCResult.custom(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
        }
        if (StrUtil.isBlank(customer.getPassword())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_PASSWORD.getCode(), UserCodeEnum.EMPTY_VALUE_PASSWORD.getMessage());
        }
        if (!customer.getPassword().equals(MD5OldPwd)) {
            customer.setChangePwdErrNum(customer.getChangePwdErrNum() + 1);
            customer.setUpdateTime(new Date());
            userDao.updateById(customer);
            response.put("pwdErrNum", customer.getChangePwdErrNum()); //当前用户原密码输错次数
            //写入日志
            logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。密码错误！", UserCfg.UPDATE_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), "原密码输入错误");
        }
        if (MD5OldPwd.equals(MD5NewPwd)) {
            throw new UserException(UserCodeEnum.SAME_PWD.getCode());
        }
        //6.修改用戶密碼
        customer.setPassword(MD5NewPwd);
        customer.setUpdateTime(new Date());
        customer.setLastChangePwdTime(new Date());
        customer.setChangePwdErrNum(UserConstant.IS_ZERO);
        userDao.updateById(customer);
        //写入日志
        logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。修改密码成功！", UserCfg.UPDATE_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
        return RPCResult.success(customer);
    }

    @Override
    public ApiResult updateUserPayPwdApi(Long userId, String oldPwd, String newPwd, Long siteId, Integer
            platformType, String ip, String url) {
        //用户状态判断
        UserEntity customer = userDao.findById(userId);
        if (customer == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (ObjectUtil.isNull(customer.getSiteId()) || !siteId.equals(customer.getSiteId())) {
            return RPCResult.custom(UserCodeEnum.SITEID_ERR.getCode(), UserCodeEnum.SITEID_ERR.getMessage());
        }
        if (Objects.equals(UserConstant.IS_ONE, customer.getIsDel())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        if (Objects.equals(UserConstant.IS_ONE, customer.getIsDemo())) {
            return RPCResult.custom(UserCodeEnum.USER_NO_AUTHORITY.getCode(), UserCodeEnum.USER_NO_AUTHORITY.getMessage());
        }
        if (ObjectUtil.isNull(customer.getStatus()) || !Objects.equals(UserStatus.USER_STATUS_10.getCode(), customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        //判断密码输入错误次数
        int maxPwdErrNum; //修改密码，原密码允许输错的最大次数
        ApiResult<Map<String, String>> map = keyValueService.findKeyValueMapByDictCode(UserConstant.CHANGE_PASS_WORD_ERROR_NUM);
        if (RPCResult.checkApiResult(map)) {
            maxPwdErrNum = Integer.parseInt(map.getData().get(UserConstant.CHANGE_PASS_WORD_ERROR_NUM));
        } else {
            maxPwdErrNum = UserCfg.MAX_ERROR_COUNT;
        }
        if (customer.getChangePayPwdErrNum() >= maxPwdErrNum) {
            customer.setStatus(UserStatus.USER_STATUS_31.getCode());
            userDao.updateById(customer);
            // 增加日志的ip和url
            logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。密码输入错误次数超过最大限制，账户被冻结！",
                    UserCfg.UPDATE_PAY_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        // 获取私钥对，解密
        String MD5OldPwd = userInnerService.getMD5PwdByRSA(oldPwd);
        String MD5NewPwd = userInnerService.getMD5PwdByRSA(newPwd);
        if (StrUtil.isBlank(customer.getPayPwd())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_PAY_PWD.getCode(), UserCodeEnum.EMPTY_PAY_PWD.getMessage());
        }
        if (!MD5OldPwd.equals(customer.getPayPwd())) {
            customer.setChangePayPwdErrNum(customer.getChangePayPwdErrNum() + 1);
            customer.setUpdateTime(new Date());
            if (customer.getChangePayPwdErrNum() > UserCfg.MAX_ERROR_COUNT) {
                customer.setStatus(UserStatus.USER_STATUS_31.getCode());
                userDao.updateById(customer);
                //保存原密码输入有误日志
                logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。密码错误达到最大次数。",
                        UserCfg.UPDATE_PAY_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
                return RPCResult.custom(UserCodeEnum.USER_PWD_MAX_ERR_NUM.getCode(), UserCodeEnum.USER_PWD_MAX_ERR_NUM.getMessage());
            }
            userDao.updateById(customer);
            //保存原密码输入有误日志
            logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。密码错误，错误次数：" + customer.getChangePayPwdErrNum(),
                    UserCfg.UPDATE_PAY_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
            return RPCResult.custom(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
        }
        if (MD5OldPwd.equals(MD5NewPwd)) {
            return RPCResult.custom(UserCodeEnum.SAME_PWD.getCode(), UserCodeEnum.SAME_PWD.getMessage());
        }
        //修改用戶支付密码
        customer.setPayPwd(MD5NewPwd);
        customer.setUpdateTime(new Date());
        customer.setChangePayPwdErrNum(UserConstant.IS_ZERO);
        //添加修改密码日志
        logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), platformType, "代理会员：" + customer.getUserName() + "。修改支付密码成功",
                UserCfg.UPDATE_PAY_PWD, UserCfg.USER, siteId, UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
        userDao.updateById(customer);
        return RPCResult.success(customer);
    }

    @Override
    public ApiResult<UserDTO> getUserBaseInfoApi(Long siteId, Long userId) {
        UserEntity userParam = new UserEntity();
        userParam.setSiteId(siteId);
        userParam.setId(userId);
        userParam.setIsDel(UserConstant.IS_F);
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<>(userParam));
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserRebateEntity userRebateEntity = userRebateDao.selectById(userId);
        if (userRebateEntity == null) {
            throw new UserException(UserCodeEnum.USER_REBATE_NOT_EXIST.getCode());
        }
        boolean isBanRebate = userRebateInnerService.isBanRebate(userRebateEntity);
        UserInfoEntity userInfoEntity = userInfoDao.selectById(userId);
        if (userInfoEntity == null) {
            throw new UserException(UserCodeEnum.USERINFO_LACK.getCode());
        }
        UserRankEntity userRankParam = new UserRankEntity();
        userRankParam.setId(userEntity.getUserRankId());
        userRankParam.setSiteId(userEntity.getSiteId());
        UserDTO userDTO = new UserDTO();
        UserRankEntity userRankEntity = userRankDao.selectOne(new QueryWrapper<>(userRankParam));
        if (userRankEntity != null) {
            userDTO.setRankImage(SupportUtil.getRankImgByLevel(userRankEntity.getRankLevel()));
            userDTO.setRankName(userRankEntity.getRankName());
            userDTO.setRankLevel(userRankEntity.getRankLevel());
            //获取用户下一级等级
            QueryWrapper ew = new QueryWrapper<UserRankEntity>();
            ew.eq("is_del", UserConstant.IS_F);
            ew.eq("site_id", userEntity.getSiteId());
            ew.gt("max_score", userEntity.getScore());
            ew.orderBy(true, false, "max_score");
            ew.last("limit 1");
            List<UserRankEntity> nextRanks = userRankDao.selectList(ew);

            userDTO.setNextRankScore(CollectionUtil.isNotEmpty(nextRanks) ? nextRanks.get(0).getMaxScore() : 0);
            //用户每充值1元兑换的积分值
            userDTO.setRankRechargeRatio(userRankEntity.getRechargeRatio().toString());
        }
        QueryWrapper<BankCardEntity> ew = new QueryWrapper<>();
        ew.eq("user_id", userId);
        ew.eq("is_del", UserConstant.IS_F);
        List<BankCardEntity> bankCardEntities = bankCardDao.selectList(ew);
        QueryWrapper<UserEncryptedEntity> etQueryWrapper = new QueryWrapper<UserEncryptedEntity>();
        etQueryWrapper.eq("user_id", userId);
        Boolean flag = userEncryptedDao.selectCount(etQueryWrapper) > 0;
        userDTO.setBindEncrypted(flag);
        userDTO.setBindBankCard(CollectionUtil.isNotEmpty(bankCardEntities));
        userDTO.setBindEmail(StrUtil.isNotEmpty(userInfoEntity.getEmail()));
        userDTO.setBindMobile(StrUtil.isNotEmpty(userInfoEntity.getMobile()));
        userDTO.setBindRealName(StrUtil.isNotEmpty(userInfoEntity.getRealName()));
        userDTO.setPhoto(userInfoEntity.getPhoto());
        userDTO.setUserRankId(userEntity.getUserRankId());
        userDTO.setIsDemo(userEntity.getIsDemo());
        boolean isSign = userSignInnerService.existSign(siteId, userId);
        userDTO.setSignIn(isSign);
        userDTO.setHasPayPwd(StrUtil.isNotEmpty(userEntity.getPayPwd()));
        userDTO.setInDemo(UserConstant.IS_T.equals(userEntity.getIsDel()));
        userDTO.setCreateTime(userEntity.getCreateTime());
        userDTO.setLastLoginIp(userEntity.getLastLoginIp());
        userDTO.setLastLoginTime(userEntity.getLastLoginTime());
        userDTO.setScore(userEntity.getScore());
        String realName = Base64.decodeStr(userInfoEntity.getRealName());
        userDTO.setRealName(realName);
        userDTO.setUserId(userId);
        userDTO.setUserName(userEntity.getUserName());
        userDTO.setSiteCode(userEntity.getSiteCode());
        userDTO.setSiteId(siteId);
        userDTO.setTransferTime(userEntity.getTransferTime());
        userDTO.setBanRebate(isBanRebate);
        userDTO.setIsProxy(userRebateEntity.getIsProxy());
        userDTO.setGpcRebate(userRebateEntity.getGpcRebate());
        userDTO.setFlcRebate(userRebateEntity.getFlcRebate());
        userDTO.setTycpRebate(userRebateEntity.getTycpRebate());
        userDTO.setTyRebate(userRebateEntity.getTyRebate());
        userDTO.setQtRebate(userRebateEntity.getQtRebate());
        userDTO.setDpcRebate(userRebateEntity.getDpcRebate());
        userDTO.setLhcRebate0(userRebateEntity.getLhcRebate0());
        userDTO.setLhcRebate1(userRebateEntity.getLhcRebate1());
        userDTO.setLhcRebate2(userRebateEntity.getLhcRebate2());
        userDTO.setLhcRebate3(userRebateEntity.getLhcRebate3());
        userDTO.setNickName(userInfoEntity.getNickName());

        //获取显示积分商城标识 0:签到和积分商城不显示 ; 1:全显示 ; 2:积分商城显示 ,签到不显示
        int integralMall = UserConstant.IS_ZERO;
        UserRankDTO userRankDTO = userRankInnerService.getRankInfoById(userEntity.getUserRankId());
        if (userRankDTO != null) {
            List<UserRankScoreDTO> userRankScoreDTOs = userRankDTO.getUserRankScoreList();
            for (UserRankScoreDTO userRankScoreDTO : userRankScoreDTOs) {
                if (Objects.equals(userRankScoreDTO.getIsEnable(), UserConstant.IS_ONE)) {
                    integralMall = UserConstant.IS_TWO;
                }
                if ("SIGN".equals(userRankScoreDTO.getScoreCode()) && userRankScoreDTO.getIsEnable() == UserConstant.IS_T) {
                    integralMall = UserConstant.IS_ONE;
                    break;
                }
            }
        }
        userDTO.setIntegralMall(integralMall);
        return RPCResult.success(userDTO);
    }

    @Override
    public ApiResult<UserDTO> getUserByUserName(Long userId, String userName) {
        QueryWrapper<UserProxyEntity> ew = new QueryWrapper<>();
        ew.eq("high_user_id", userId);
        ew.eq("user_name", userName);
        List<UserProxyEntity> userProxyEntities = userProxyDao.selectList(ew);
        if (CollectionUtil.isEmpty(userProxyEntities)) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        UserProxyEntity userProxyEntity = userProxyEntities.get(0);
        String path = userProxyInnerService.getPath(userProxyEntity.getUserId(), userProxyEntity.getSiteId());
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userProxyEntity.getUserId());
        userDTO.setSiteCode(userProxyEntity.getSiteCode());
        userDTO.setUserName(userProxyEntity.getUserName());
        userDTO.setPath(path);
        GetUserFundReq rep = new GetUserFundReq();
        rep.setUserId(userDTO.getUserId().toString());
        rep.setSiteCode(userDTO.getSiteCode());
        ApiResult<UserFundResp> userFund = userFundService.getUserFund(rep);
        if (!RPCResult.checkApiResult(userFund)) {
            userDTO.setAmount(0L);
        } else {
            UserFundResp data = userFund.getData();
            userDTO.setAmount(data.getCanAmount());
        }
        return RPCResult.success(userDTO);
    }

    @Override
    public ApiResult<Long> getSubUserIdApi(@RequestParam Long userId, @RequestParam String userName) {
        return RPCResult.success(userProxyInnerService.getSubUserId(userId, userName));
    }

    @Override
    public ApiResult<List<Map<String, Object>>> getHighAccountNameToLevelPathApi(Long siteId, String
            searchName, String highLevelName) {
        logger.info(String.format("查找代理会员的上级用户名称，searchName:[{%s}], highUserName:[{%s}]", searchName, highLevelName));
        List<Map<String, Object>> listMap = new ArrayList<>();
        if (StrUtil.isBlank(highLevelName)) {
            highLevelName = searchName;
        }
        if (StrUtil.isBlank(highLevelName)) {
            return RPCResult.success(null);
        }
        UserEntity queryUser = new UserEntity();
        queryUser.setUserName(highLevelName);
        queryUser.setSiteId(siteId);
        queryUser.setIsDel(UserConstant.IS_F);
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<>(queryUser));
        if (userEntity == null) {
            logger.info(String.format("用户[{%s}]不存在。", highLevelName));
            return RPCResult.success(null);
        }
        List<UserProxyEntity> allHighUserProxy = userProxyInnerService.getAllHighUserProxy(userEntity.getId(), userEntity.getSiteId());
        for (UserProxyEntity userProxyEntity : allHighUserProxy) {
            Map<String, Object> map = new HashMap<>();
            map.put("userName", userProxyEntity.getHighUserName());
            listMap.add(map);
        }
        return RPCResult.success(listMap);
    }

    @Override
    public ApiResult<Map<Integer, Object>> getSiteUserPathLengthApi(Long siteId) {
        List<Integer> siteIdAllLevel = userProxyInnerService.getSiteIdAllLevel(siteId);
        if (CollectionUtil.isEmpty(siteIdAllLevel)) {
            logger.info(String.format("未查询到相关用户信息：resultList：[{%s}]", siteIdAllLevel));
            return null;
        }
        int level = siteIdAllLevel.size();
        logger.info(String.format("站点:[{%s}]共有[{%s}]个层级", siteId, level));
        Map<Integer, Object> levels = new HashMap<Integer, Object>();
        for (int i = 1; i <= level; i++) {
            levels.put(i, i);
        }
        return RPCResult.success(levels);
    }


    @Override
    public ApiResult<List<Long>> getSubUserIdApi(Long userId, String userName, boolean isLike) {
        List<Long> idList =  userProxyInnerService.getSubUserId(userId, userName,isLike);
        return RPCResult.success(idList);
    }

    @Override
    public ApiResult<UserDTO> getRelateApi(Long userId) {
        Map<String, Object> relate = userRebateInnerService.getRelate(userId);
        UserDTO dto = BeanUtil.mapToBean(relate, UserDTO.class, true);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<Integer> getTodayRegisterUserCountApi(Long siteId) {
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        String date = DateUtil.formatDate(new Date());
        String beginTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("site_id", siteId);
        ew.between("create_time", beginTime, endTime);
        ew.eq("is_demo", UserConstant.IS_F);
        Integer count = userDao.selectCount(ew);
        return RPCResult.success(count);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryUserOnlineApi(String userName, Long siteId, int pageNo,
                                                                       int pageSize, List<Long> idList, String type) {
        Page page = PageUtil.buildPage(pageNo, pageSize);
        List<Map<String, Object>> resultList = userDao.queryUserOnlineApi(page, userName, siteId, idList, type);
        List<Map<String, Object>> finalList = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            if (map.containsKey("realName")) {
                map.put("realName", Base64.decodeStr(map.get("realName").toString()));
            } else {
                map.put("realName", "");
            }
            ApiResult<Long> totalAmountResult = userFundService.getTotalAmount(map.get("id").toString(), map.get("siteCode").toString());
            Long totalAmount = totalAmountResult.getData();
            totalAmount = totalAmount == null ? 0 : totalAmount / 100;
            map.put("totalAmount", totalAmount);
            finalList.add(map);
        }
        PageInfo<Map<String, Object>> resultPage = new PageInfo<>(finalList, pageNo, pageSize, page.getTotal());
        return RPCResult.success(resultPage);
    }

    @Override
    public ApiResult<Integer> getOnlineCountApi(Long siteId, Long highUserId) {
        if (highUserId != null) {
            ApiResult<List<Long>> proxyByUserId = userRebateService.getProxyByUserId(highUserId, UserConstant.IS_THREE);
            List<Long> idList = proxyByUserId.getData();
            if (CollectionUtil.isEmpty(idList)) {
                return RPCResult.success(0);
            }
            Integer count = userDao.getOnlineCountApi(siteId, idList);
            return RPCResult.success(count);
        } else {
            return RPCResult.success(userDao.getOnlineCountApi(siteId, null));
        }
    }

    @Override
    public ApiResult kickOutUserApi(Long userId) {
        UserTokenEntity userTokenEntity = userTokenDao.selectById(userId);
        String msg = "kickOutUserApi 用户" + userId + "登出,查询token:" + (ObjectUtil.isNotNull(userTokenEntity) ? "存在" : "不存在");
        if (ObjectUtil.isNotNull(userTokenEntity)) {
            msg += " 清除token值 :'" + userTokenEntity.getSecretToken() + "'";
            template.delete(RedisKey.USER_TOKEN + userTokenEntity.getSecretToken());
            userTokenDao.deleteById(userTokenEntity.getId());
        }
        logger.info(msg);
        return RPCResult.success();
    }

    @Override
    public ApiResult changeStatusApi(SysUserDTO sysUserDTO, Long userId, Integer s, Integer task, String ip, String
            url) {
        Integer status = 0;
        //根据status改变状态值
        switch (task) {
            case 0:
                status = s == 0 ? UserStatus.USER_STATUS_10.getCode() : UserStatus.USER_STATUS_11.getCode();
                break;
            case 1:
                status = s == 0 ? UserStatus.USER_STATUS_10.getCode() : UserStatus.USER_STATUS_21.getCode();
                break;
            case 2:
                status = s == 0 ? UserStatus.USER_STATUS_10.getCode() : UserStatus.USER_STATUS_31.getCode();
                break;
            case 3:
                status = s == 0 ? UserStatus.USER_STATUS_10.getCode() : UserStatus.USER_STATUS_41.getCode();
                break;
            default:
                break;
        }
        if (ObjectUtil.isNull(status)) {
            return RPCResult.custom(UserCodeEnum.USER_STATUS_CODE_NOT_EXIST.getCode(), "用户状态值错误");
        }
        UserEntity userModel = userDao.findById(userId);
        if (userModel == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        // 旧状态
        ApiResult<Map<String, String>> keyValueMapByDictCode = keyValueService.findKeyValueMapByDictCode(UserConstant.USER_STATUS);
        if (!RPCResult.checkApiResult(keyValueMapByDictCode)) {
            throw new UserException(keyValueMapByDictCode.getCode(), keyValueMapByDictCode.getMessage());
        }
        Map<String, String> resultMap = keyValueMapByDictCode.getData();
        String before = resultMap.get(userModel.getStatus().toString());
        String after = resultMap.get(status.toString());
        userModel.setStatus(status);
        if (status.equals(UserStatus.USER_STATUS_10.getCode())) {
            userModel.setLoginErrCount(UserConstant.IS_ZERO);
            userModel.setChangePwdErrNum(UserConstant.IS_ZERO);
            userModel.setChangePayPwdErrNum(UserConstant.IS_ZERO);
            template.delete(RedisKey.PAY_WITHDAW_USER_PAY_PASSWORD_ERROR + userModel.getId());
        }
        userDao.updateById(userModel);
        //停用或冻结将用户踢下线 删除token
        if (
            // 停用
                status.equals(UserStatus.USER_STATUS_11.getCode())
                        // 冻结
                        || status.equals(UserStatus.USER_STATUS_31.getCode())
                        // 暂停投注
                        || status.equals(UserStatus.USER_STATUS_21.getCode())
                        // 启用投注
                        || status.equals(UserStatus.USER_STATUS_10.getCode())
                ) {
            UserTokenEntity userTokenEntity = userTokenDao.selectById(userModel.getId());
            if (ObjectUtil.isNotNull(userTokenEntity)) {
                userTokenDao.deleteById(userTokenEntity.getId());
                template.delete(RedisKey.USER_TOKEN + userTokenEntity.getSecretToken());
                logger.info("changeStatusApi接口清除token :'" + userTokenEntity.getSecretToken() + "'");
            }
        }
        //若是停用或启用，则连级停用或启用
        if (task == 0 && (status.equals(UserStatus.USER_STATUS_10.getCode()) || status.equals(UserStatus.USER_STATUS_11.getCode()))) {
            //查询下级用户
            List<UserProxyEntity> allSubUserProxy = userProxyInnerService.getAllSubUserProxy(userModel.getId(), userModel.getSiteId());
            List<Long> idList = new ArrayList<>();
            if (CollectionUtil.isNotEmpty(allSubUserProxy)) {
                for (UserProxyEntity subUserProxy : allSubUserProxy) {
                    idList.add(subUserProxy.getUserId());
                }
                //更新下级状态
                userDao.updateBatchUserStatus(idList, status);
                //删除下级token
                for (UserProxyEntity subUserProxy : allSubUserProxy) {
                    UserTokenEntity userTokenEntity = userTokenDao.selectById(subUserProxy.getUserId());
                    if (ObjectUtil.isNotNull(userTokenEntity)) {
                        userTokenDao.deleteById(userTokenEntity.getId());
                        template.delete(RedisKey.USER_TOKEN + userTokenEntity.getSecretToken());
                        logger.info("changeStatusApi接口清除token :'" + userTokenEntity.getSecretToken() + "'");
                    }
                }
            }
        }
        // 添加操作日志
        String content = "修改：用户" + userModel.getUserName() + "状态：修改前：" + before + "，修改后：" + after;
        logUserInnerService.addUserLog(sysUserDTO.getId(), sysUserDTO.getUserName(), 1, content,
                UserCfg.UPDATE, UserCfg.USER, userModel.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
        return RPCResult.success();
    }

    @Override
    public ApiResult<List<UserDTO>> checkOutUserExcel(QueryParamDTO dto) {
        long start = System.currentTimeMillis();
        logger.debug(String.format("导出代理会员列表开始,站点code: %s", dto.getSiteCode()));
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("siteId", dto.getSiteId());
        paramMap.put("isProxy", dto.getIsProxy());
        paramMap.put("level", dto.getLevel());
        paramMap.put("rankLevel", dto.getRankLevel());
        paramMap.put("status", dto.getStatus());
        paramMap.put("highLevelId", dto.getHighLevelId());
        paramMap.put("highLevelName", dto.getHighLevelName());
        paramMap.put("regSource", dto.getRegSource());
        paramMap.put("regIp", dto.getRegIp());
        paramMap.put("lastLoginIp", dto.getLastLoginIp());
        paramMap.put("beginTime", dto.getBeginTime());
        paramMap.put("endTime", dto.getEndTime());
        paramMap.put("orderBy", dto.getOrderBy());
        paramMap.put("searchType", dto.getSearchType());
        paramMap.put("dateType", dto.getDateType());
        paramMap.put("sort", dto.getSort());
        paramMap.put("keyword", dto.getLoginName());
        paramMap.put("rebateType", dto.getRebateType());
        paramMap.put("minRebate", dto.getMinRebate());
        paramMap.put("maxRebate", dto.getMaxRebate());
        List<UserDTO> userDTOList = userDao.checkOutUserExcel(paramMap);
        List<Long> idList = new ArrayList<>();
        StringBuilder ids = new StringBuilder();
        long start1 = System.currentTimeMillis();
        logger.debug(String.format("循环遍历组装用户基本信息开始,站点code: %s", dto.getSiteCode()));
        for (UserDTO userDTO : userDTOList) {
            String realName = Base64.decodeStr(userDTO.getRealName());
            String weChat = Base64.decodeStr(userDTO.getWeChat());
            String qq = Base64.decodeStr(userDTO.getQq());
            String mobile = Base64.decodeStr(userDTO.getMobile());
            String email = Base64.decodeStr(userDTO.getEmail());
            userDTO.setRealName(realName);
            userDTO.setWeChat(weChat);
            userDTO.setQq(qq);
            userDTO.setMobile(mobile);
            userDTO.setEmail(email);
            idList.add(userDTO.getId());
            ids.append(userDTO.getId()).append(",");
        }
        long end1 = System.currentTimeMillis();
        logger.debug(String.format("循环遍历组装用户基本信息结束,站点code: %s,耗时: %s", dto.getSiteCode(), end1 - start1));
        ids.deleteCharAt(ids.length() - 1);
        ApiResult<List<UserFundResp>> apiResult = userFundService.queryUserFund(ids.toString(), dto.getSiteCode());
        List<UserFundResp> data = apiResult.getData();
        Map<Long, UserFundResp> userFundMap = new HashMap<>();
        for (UserFundResp datum : data) {
            userFundMap.put(datum.getId(), datum);
        }
        EntryAndExitTotalReq req = new EntryAndExitTotalReq();
        req.setSiteCode(dto.getSiteCode());
        if (StrUtil.isNotEmpty(dto.getStartTime())) {
            req.setBeginDate(dto.getStartTime());
        }
        if (StrUtil.isNotEmpty(dto.getEndTime())) {
            req.setEndDate(dto.getEndTime());
        }
        req.setUserIdList(idList);
        ApiResult<List<EntryAndExitTotalResp>> listApiResult = tradeUserReportService.queryEntryAndExitTotal(req);
        List<EntryAndExitTotalResp> entryAndExitTotalRespData = listApiResult.getData();
        if (!RPCResult.checkApiResult(listApiResult)){
            logger.error("查询出、入款总计查询失败:{}",listApiResult);
        }
        Map<Long, EntryAndExitTotalResp> entryAndExitTotalMap = new HashMap<>();
        if (CollectionUtil.isNotEmpty(entryAndExitTotalRespData)){
            for (EntryAndExitTotalResp entryAndExitTotalData : entryAndExitTotalRespData) {
                entryAndExitTotalMap.put(entryAndExitTotalData.getUserId(),entryAndExitTotalData);
            }
        }
        for (UserDTO userDTO : userDTOList) {
            UserFundResp userFundResp = userFundMap.get(userDTO.getId());
            if (userFundResp != null) {
                userDTO.setCanAmount(userFundResp.getCanAmount());
            }
            EntryAndExitTotalResp entryAndExitTotalResp = entryAndExitTotalMap.get(userDTO.getId());
            if (entryAndExitTotalResp != null) {
                userDTO.setDepositNum(entryAndExitTotalResp.getDepositNum() != null ? entryAndExitTotalResp.getDepositNum() : 0);
                userDTO.setDepositAmount(entryAndExitTotalResp.getDepositAmount() != null ? entryAndExitTotalResp.getDepositAmount() : 0);
                userDTO.setWithdrawNum(entryAndExitTotalResp.getWithdrawNum() != null ? entryAndExitTotalResp.getWithdrawNum() : 0);
                userDTO.setWithdrawAmount(entryAndExitTotalResp.getWithdrawAmount() != null ? entryAndExitTotalResp.getWithdrawAmount() : 0);
            }
        }
        long end = System.currentTimeMillis();
        logger.debug(String.format("导出代理会员列表结束,站点code: %s,耗时: %s", dto.getSiteCode(), end - start));
        return RPCResult.success(userDTOList);
    }

    @Override
    public ApiResult<Boolean> setPayPwd(Long userId, String payPwd) {
        UserEntity userEntity = userDao.selectById(userId);
        if (userEntity == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        if (StrUtil.isNotEmpty(payPwd)) {
            String md5PwdByRSA = userInnerService.getMD5PwdByRSA(payPwd);
            userEntity.setPayPwd(md5PwdByRSA);
        }
        userEntity.setUpdateTime(new Date());
        userEntity.setChangePayPwdErrNum(UserConstant.IS_ZERO);
        Integer result = userDao.updateById(userEntity);
        if (result > 0) {
            return RPCResult.success(true);
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "设置支付密码失败");
    }

    @Override
    public ApiResult<Boolean> verifyUserIsDemoByIdApi(Long userId) {
        return RPCResult.success(2 == userDao.selectById(userId).getIsDemo());
    }

    @Override
    public ApiResult<Boolean> verifyUserIsDemoByIdApi(Long siteId, String userName) {
        QueryWrapper<UserEntity> ew = new QueryWrapper<>();
        ew.eq("user_name", userName);
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("is_demo", UserConstant.IS_TEST);
        return RPCResult.success(null == userDao.selectOne(ew));
    }

    @Override
    public ApiResult<Boolean> verifyUserNameApi(Long siteId, String userName) {
        if (StrUtil.isBlank(userName)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "姓名信息 为空");
        }
        if (ObjectUtil.isNull(siteId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "站点信息为空");
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setSiteId(siteId);
        userEntity.setUserName(userName);
        userEntity.setIsDel(UserConstant.IS_F);
        return RPCResult.success(ObjectUtil.isNull(userDao.selectOne(new QueryWrapper<>(userEntity))));

    }


    @Override
    public UserEntity saveUser(UserEntity userEntity, UserInfoEntity userInfoEntity, Long siteId, Integer
            isDemo, String ip) {
        UserEntity user = new UserEntity();
        user.setId(userEntity.getId());
        user.setPassword(userEntity.getPassword());
        user.setPayPwd(userEntity.getPayPwd());
        if (StringUtils.isNotEmpty(ip)) {
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
        return user;
    }

    @Override
    public UserEntity getUserByUserName(String userName, Long siteId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setSiteId(siteId);
        userEntity.setIsDel(UserConstant.IS_F);
        return userDao.selectOne(new QueryWrapper<>(userEntity));
    }

    /**
     * 生成试玩账号名称
     * T开头12位随字符串，
     *
     * @return T开头12位随机数
     */
    private String createDemoUser() {
        StringBuilder sb = new StringBuilder("T");
        String str = UUID.randomUUID().toString().replace("-", "").substring(0, 11);
        sb.append(str);
        return sb.toString().toUpperCase();
    }

    /**
     * 站点是否允许多端登录
     * 是：true
     * 否：false
     *
     * @param siteId 站点id
     * @return
     */
    private boolean isMutilportLogin(Long siteId) {
        SiteDTO siteDTO = null;
        String siteCachkey = String.format("site:%s", siteId);
        String siteJson = template.opsForValue().get(siteCachkey);
        if (StringUtils.isNotEmpty(siteJson)) {
            siteDTO = JSONUtil.toBean(siteJson, SiteDTO.class);
        }
        if (null != siteDTO) {
            // 缓存字段如果为空，默认设置0
            String multiportLogin = String.valueOf(siteDTO.getMultiportLogin());
            if (StringUtils.isEmpty(multiportLogin) || "null".equals(multiportLogin)) {
                siteDTO.setMultiportLogin(UserConstant.IS_ZERO);
            }
            return siteDTO.getMultiportLogin() == 1;
        }
        ApiResult<SiteDTO> siteDTOApiResult = siteService.findSiteDTOById(siteId);
        if (RPCResult.checkApiResult(siteDTOApiResult)) {
            siteDTO = siteDTOApiResult.getData();
            if (null != siteDTO) {
                return siteDTO.getMultiportLogin() == 1;
            }
        }
        return false;
    }

    public String getTokenJson(UserEntity user, Integer ttlMillis, Integer platformType) {
        JSONObject userJson = new JSONObject();
        userJson.put("userId", user.getId().toString());
        userJson.put("siteId", user.getSiteId().toString());
        userJson.put("siteCode", user.getSiteCode());
        userJson.put("userName", user.getUserName());
        userJson.put("platformType", platformType);
        userJson.put("isDemo", user.getIsDemo().toString());
        userJson.put("status", user.getStatus().toString());

        UserTokenEntity userToken = userTokenDao.selectById(user.getId());
        String msg = "getTokenJson 用户:" + user.getUserName() + " 获取当前token:" + (null != userToken ? userToken.getSecretToken() : "未查询到数据");
        String uuid = RandomUtil.simpleUUID();
        if (null != userToken) {
            // 判断如果站点开启多端登录，则直接返回token
            Boolean flag = isMutilportLogin(user.getSiteId());
            msg += ",站点多端登录状态" + (flag ? "开启" : "未开启");
            if (flag) {
                msg += ",token保持不变";
                uuid = userToken.getSecretToken();
            } else {
                msg += ",token被替换，由:" + userToken.getSecretToken() + ",替换为:" + uuid;
                template.delete(RedisKey.USER_TOKEN + userToken.getSecretToken());
            }
        }
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
            int flat = userTokenDao.updateById(userTokenEntity);
            logger.info(msg + ",执行updateById,结果" + flat);
        } else {
            int flat = userTokenDao.insert(userTokenEntity);
            logger.info(msg + ",生成新token:" + uuid + ",执行insert,结果" + flat);
        }
        return uuid;
    }


    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryRegisterUserByProxyLineApi(Long id, String startTime, String endTime, int pageNo, int pageSize) {
        if (ObjectUtil.isNull(id)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "用户id信息为空");
        }
        if (StrUtil.isBlank(startTime)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "startTime为空");
        }
        if (StrUtil.isBlank(endTime)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "endTime为空");
        }
        if (ObjectUtil.isNull(pageNo)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "pageNo为空");
        }
        if (ObjectUtil.isNull(pageSize)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "pageSize为空");
        }
        Page page = PageUtil.buildPage(pageNo, pageSize, "desc", "u.create_time");
        List<Map<String, Object>> resultList = userProxyDao.queryRegisterUserByProxyLineApi(page, id, startTime, endTime);
        PageInfo<List<Map<String, Object>>> pageInfo = new PageInfo(resultList, pageNo, pageSize, page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<List<Long>> getIdListBySiteCodeAndIsDemoApi(String siteCode, Integer isDemo) {
        if (StrUtil.isBlank(siteCode)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "siteCode为空");
        }
        if (ObjectUtil.isNull(isDemo)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "isDemo为空");
        }
        List<Long> resultList = userDao.getIdListBySiteCodeAndIsDemoApi(siteCode, isDemo);
        return RPCResult.success(resultList);

    }

    @Override
    public ApiResult<?> setEncryptedApiResult(List<UserEncryptedDTO> encryptedDTOs) {
        ApiResult<List<UserEncryptedEntity>> encryptApiResult = copyEncryptedEntities(encryptedDTOs);
        if (encryptApiResult.getCode() != GlobalCode.SUCCESS.getCode()) {
            return RPCResult.custom(encryptApiResult.getCode(), encryptApiResult.getMessage());
        }
        List<UserEncryptedEntity> encryptedEntities = encryptApiResult.getData();
        for (UserEncryptedEntity entity : encryptedEntities) {
            entity.setId(IdWorker.getId());
            entity.setResult(Base64.encode(entity.getResult()));
            userEncryptedDao.insert(entity);
        }
        return RPCResult.success(true);
    }

    @Override
    public ApiResult<?> verifyEncryptedApiResult(List<UserEncryptedDTO> encryptedDTOs, Integer type,
                                                 Long userId, Integer plat, Long siteId,
                                                 String loginIp, String loginUrl) {
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        ApiResult<?> verifyNum = verifyEncryptedNum(encryptedDTOs, type);
        if (verifyNum.getCode() != GlobalCode.SUCCESS.getCode()) {
            return RPCResult.custom(verifyNum.getCode(), verifyNum.getMessage());
        }
        //验证用户是否存在
        UserEntity customer = userDao.findById(userId);
        if (ObjectUtil.isNull(customer)) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (UserStatus.USER_STATUS_31.getCode().equals(customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "该账号已被冻结");
        }
        //验证是否验证密保
        QueryWrapper<UserEncryptedEntity> ew = new QueryWrapper<UserEncryptedEntity>();
        ew.eq("user_id", userId);
        List<UserEncryptedEntity> searchEntityList = userEncryptedDao.selectList(ew);
        if (CollectionUtil.isEmpty(searchEntityList)) {
            return RPCResult.custom(UserCodeEnum.ENCRYPTED_UN_SET.getCode(), UserCodeEnum.ENCRYPTED_UN_SET.getMessage());
        }
        String redisKey = type == UserConstant.IS_ONE ? String.format("%s%s", RedisKey.UPDATE_ENCRYPTED_VERIFY_ERROR_NUM, userId) : String.format("%s%s", RedisKey.FIND_LOGIN_PASSWORD_VERIFY_ENCRYPTED_ERROR_NUM, userId);
        //取出问题id对应答案map
        Map<Long, String> resultMap = searchEntityList.stream().collect(Collectors.toMap(UserEncryptedEntity::getId, UserEncryptedEntity::getResult));
        String errorCountStr = template.opsForValue().get(redisKey);
        for (UserEncryptedDTO dto : encryptedDTOs) {
            Long id = dto.getId();
            String answer = dto.getResult();
            String result = resultMap.get(id);
            String decodeResult = null;
            if (StringUtils.isNotEmpty(result)) {
                decodeResult = Base64.decodeStr(result);
            }
            if (StringUtils.isEmpty(decodeResult) || ObjectUtil.isNull(id) || !decodeResult.equals(answer)) {
                Integer errorCount = NumberUtils.toInt(errorCountStr, 0) + 1;
                String newErrorCountStr = String.valueOf(errorCount);
                template.opsForValue().set(redisKey, newErrorCountStr);
                Integer remainNum = UserCfg.MAX_ENCRYPTED_ERROR_COUNT - errorCount;
                if (errorCount >= UserCfg.MAX_ENCRYPTED_ERROR_COUNT) {
                    customer.setStatus(UserStatus.USER_STATUS_31.getCode());
                    userDao.updateById(customer);
                    kickOutUserApi(userId);
                    logUserInnerService.addUserLog(userId, customer.getUserName(), plat, "代理会员：" + customer.getUserName() + "。验证密保问题错误次数超过最大限制，账户被冻结！",
                            UserCfg.USER_LOG_FLAG_TYPE_OPER, UserCfg.USER, siteId, UserCfg.UPDATE, loginIp, loginUrl);
                    return RPCResult.custom(UserCodeEnum.ENCRYPTED_ANSWER_ERROR.getCode(), "验证密保问题错误次数达到最大错误次数,已被冻结");
                }
                logUserInnerService.addUserLog(userId, customer.getUserName(), plat, String.format("代理会员:%s验证密保问题错误次数:%s次", customer.getUserName(), errorCount),
                        UserCfg.USER_LOG_FLAG_TYPE_OPER, UserCfg.USER, siteId, UserCfg.UPDATE, loginIp, loginUrl);
                return RPCResult.custom(UserCodeEnum.ENCRYPTED_ANSWER_ERROR.getCode(), String.format(UserCodeEnum.ENCRYPTED_ANSWER_ERROR.getMessage(), remainNum));
            }
        }
        //设置验证成功标志 有效时间:5分钟
        template.delete(redisKey);
        String successKey = String.format("%s%s", RedisKey.ENCRYPTED_VERIFY_SUCCESS, userId);
        template.opsForValue().set(successKey, "0", 5 * 60, TimeUnit.SECONDS);
        return RPCResult.success();
    }

    @Override
    public ApiResult<?> verifyUserName(String userName, Long siteId, String verifyCode, String verId) {
        //验证码是否正确
        if (ObjectUtil.isNull(verifyCode)) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VERIFYCODE.getCode(), UserCodeEnum.EMPTY_VERIFYCODE.getMessage());
        }
        String redisCode = template.opsForValue().get(RedisKey.SECURITY_CODE + verId);
        if (ObjectUtil.isNull(redisCode)) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VERIFYCODE.getCode(), "验证码失效，请刷新验证码");
        }
        if (!redisCode.equalsIgnoreCase(verifyCode)) {
            return RPCResult.custom(UserCodeEnum.ERR_VERIFYCODE.getCode(), UserCodeEnum.ERR_VERIFYCODE.getMessage());
        }
        //验证用户是否存在
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(userName);
        userEntity.setSiteId(siteId);
        userEntity.setIsDel(UserConstant.IS_ZERO);
        UserEntity exitUser = userDao.selectOne(new QueryWrapper<>(userEntity));
        if (ObjectUtil.isNull(exitUser)) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (exitUser.getStatus() == UserStatus.USER_STATUS_31.getCode()) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "该账号已被冻结");
        }
        Map<String, Object> verifyResult = new HashMap<String, Object>();
        Long userId = exitUser.getId();
        verifyResult.put("userId", userId);
        ApiResult<Boolean> isSetPayPwdResult = isSetPayPwdByUserId(userId, siteId);
        verifyResult.put("isSetPayPwd", isSetPayPwdResult.getData());
        ApiResult<Boolean> isBindEncrytedResult = isBandEncrypted(userId);
        verifyResult.put("isBindEncryted", isBindEncrytedResult.getData());
        return RPCResult.success(JSONUtil.parse(verifyResult));
    }

    @Override
    public ApiResult<?> updatePwdByVerify(Long userId, String password) {
        //参数验证
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        if (ObjectUtil.isNull(password)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "密码为空");
        }
        //获取redis 判断是否验证成功才通过修改密码
        String encryptedKey = String.format("%s%s", RedisKey.ENCRYPTED_VERIFY_SUCCESS, userId);
        String payPswKey = String.format("%s%s", RedisKey.FIND_LOGIN_PASSWORD_VERIFY_PAY_PASSWORD_SUCCESS, userId);
        ValueOperations<String, String> ops = template.opsForValue();
        String isPassEncrypted = ops.get(encryptedKey);
        String isPassPayPwd = ops.get(payPswKey);
        if (StrUtil.isEmpty(isPassEncrypted) && StrUtil.isEmpty(isPassPayPwd)) {
            return RPCResult.custom(UserCodeEnum.USER_UNVERIFIED_ERROR.getCode(), UserCodeEnum.USER_UNVERIFIED_ERROR.getMessage());
        }
        UserEntity customer = userDao.findById(userId);
        String rsaPrivateKey = getRSAPrivateKey();
        String MD5NewPwd = null;
        try {
            MD5NewPwd = new String(RSAUtil.privateDecrypt(RSAUtil.base642Byte(password), RSAUtil.string2PrivateKey(rsaPrivateKey)));
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return RPCResult.custom(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
        }
        customer.setPassword(MD5NewPwd);
        customer.setUpdateTime(new Date());
        customer.setLastChangePwdTime(new Date());
        customer.setChangePwdErrNum(UserConstant.IS_ZERO);
        userDao.updateById(customer);
        template.delete(payPswKey);
        template.delete(encryptedKey);
        return RPCResult.success();
    }

    @Override
    public ApiResult<?> updateEncrypted(List<UserEncryptedDTO> encryptedDTOs, Long userId) {
        ApiResult<List<UserEncryptedEntity>> encryptApiResult = copyEncryptedEntities(encryptedDTOs);
        if (encryptApiResult.getCode() != GlobalCode.SUCCESS.getCode()) {
            return RPCResult.custom(encryptApiResult.getCode(), encryptApiResult.getMessage());
        }
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        UserEntity customer = userDao.findById(userId);
        if (UserStatus.USER_STATUS_31.getCode().equals(customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "该账号已被冻结");
        }
        String encryptedKey = String.format("%s%s", RedisKey.ENCRYPTED_VERIFY_SUCCESS, userId);
        ValueOperations<String, String> ops = template.opsForValue();
        String isPassEncrypted = ops.get(encryptedKey);
        if (StrUtil.isEmpty(isPassEncrypted)) {
            return RPCResult.custom(UserCodeEnum.USER_UNVERIFIED_ERROR.getCode(), UserCodeEnum.USER_UNVERIFIED_ERROR.getMessage());
        }
        QueryWrapper<UserEncryptedEntity> ew = new QueryWrapper<UserEncryptedEntity>();
        ew.eq("user_id", userId);
        userEncryptedDao.delete(ew);
        for (UserEncryptedDTO dto : encryptedDTOs) {
            dto.setUserId(userId);
        }
        ApiResult<?> result = setEncryptedApiResult(encryptedDTOs);
        if (result.getCode() != GlobalCode.SUCCESS.getCode()) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        template.delete(encryptedKey);
        return result;
    }

    /**
     * 转换密保Entity并验证
     *
     * @param encryptedDTOs
     * @return
     * @author Solming
     */
    private ApiResult<List<UserEncryptedEntity>> copyEncryptedEntities(List<UserEncryptedDTO> encryptedDTOs) {
        ApiResult<?> verifyNum = verifyEncryptedNum(encryptedDTOs, UserConstant.IS_ZERO);
        if (verifyNum.getCode() != GlobalCode.SUCCESS.getCode()) {
            return RPCResult.custom(verifyNum.getCode(), verifyNum.getMessage());
        }
        List<UserEncryptedEntity> entityList = new ArrayList<UserEncryptedEntity>();
        Map<String, String> questionMap = new HashMap<String, String>();
        for (UserEncryptedDTO encryptedDTO : encryptedDTOs) {
            //验证问题是否重复
            if (StrUtil.isNotEmpty(questionMap.get(encryptedDTO.getQuestion()))) {
                return RPCResult.custom(UserCodeEnum.ENCRYPTED_REPEAT.getCode(), UserCodeEnum.ENCRYPTED_REPEAT.getMessage());
            }
            questionMap.put(encryptedDTO.getQuestion(), encryptedDTO.getQuestion());
            UserEncryptedEntity encryptedEntity = new UserEncryptedEntity();
            BeanUtil.copyProperties(encryptedDTO, encryptedEntity);
            entityList.add(encryptedEntity);
        }
        return Results.success(entityList);
    }


    /**
     * 验证密保数量
     *
     * @param encryptedDTOs
     * @param type          0:需要满足3个,1满足两个
     * @return
     * @author Solming
     */
    private ApiResult<?> verifyEncryptedNum(List<UserEncryptedDTO> encryptedDTOs, Integer type) {
        Integer verifyNum = type == UserConstant.IS_ZERO ? UserConstant.IS_THREE : UserConstant.IS_TWO;
        if (CollectionUtil.isEmpty(encryptedDTOs)) {
            return RPCResult.custom(UserCodeEnum.ENCRYPTED_LIST_IS_NULL.getCode(), UserCodeEnum.ENCRYPTED_LIST_IS_NULL.getMessage());
        }
        if (encryptedDTOs.size() != verifyNum) {
            return RPCResult.custom(UserCodeEnum.ENCRYPTED_LIST_UNMEET.getCode(), UserCodeEnum.ENCRYPTED_LIST_UNMEET.getMessage());
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult<?> verifyPayPwdApi(String password, LogUserDTO logDTO) {
        Long userId = logDTO.getUserId();
        Long siteId = logDTO.getSiteId();
        //校验参数
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        if (ObjectUtil.isNull(password)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "支付密码为空");
        }
        if (ObjectUtil.isNull(siteId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "站点信息为空");
        }
        //判断用户,是否存在，用户状态
        UserEntity userEntity = new UserEntity();
        userEntity.setSiteId(siteId);
        userEntity.setId(userId);
        userEntity.setIsDel(UserConstant.IS_ZERO);
        UserEntity user = userDao.selectOne(new QueryWrapper<>(userEntity));
        if (null == user) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (UserStatus.USER_STATUS_31.getCode().equals(user.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "该账号已被冻结");
        }
        int errorNum = user.getChangePayPwdErrNum();
        String passwordMD5 = this.getMD5PwdByRSA(password);
        //解析判断支付密码是否正确
        if (!passwordMD5.equalsIgnoreCase(user.getPayPwd())) {
            //判断支付密码错误次数
            int errorCount = errorNum + 1;
            user.setChangePayPwdErrNum(errorCount);

            user.setUpdateTime(new Date());
            user.setUpdateBy(user.getUserName());
            Integer remainNum = UserCfg.MAX_ENCRYPTED_ERROR_COUNT - errorCount;
            if (errorCount >= UserCfg.MAX_ENCRYPTED_ERROR_COUNT) {
                user.setStatus(UserStatus.USER_STATUS_31.getCode());
                userDao.updateById(user);
                kickOutUserApi(user.getId());
                // 保存支付密码错误日志
                logUserInnerService.addUserLog(logDTO.getUserId(), user.getUserName(), logDTO.getPlat(), String.format("代理会员:%s验证支付密码达到最大错误次数,已被冻结!", user.getUserName()),
                        UserCfg.USER_LOG_FLAG_TYPE_OPER, UserCfg.USER, logDTO.getSiteId(), UserCfg.UPDATE, logDTO.getLoginIp(), logDTO.getLoginUrl(), logDTO.getLoginArea(), logDTO.getSiteCode());
                return RPCResult.custom(UserCodeEnum.ENCRYPTED_PAY_PASSWORD_ERROR.getCode(), String.format("账号验证支付密码错误达到%s次,已被冻结", UserCfg.MAX_ENCRYPTED_ERROR_COUNT));
            }
            logUserInnerService.addUserLog(logDTO.getUserId(), user.getUserName(), logDTO.getPlat(), String.format("代理会员:%s验证支付密码错误次数:%s次", user.getUserName(), errorCount),
                    UserCfg.USER_LOG_FLAG_TYPE_OPER, UserCfg.USER, logDTO.getSiteId(), UserCfg.UPDATE, logDTO.getLoginIp(), logDTO.getLoginUrl(), logDTO.getLoginArea(), logDTO.getSiteCode());
            userDao.updateById(user);
            return RPCResult.custom(UserCodeEnum.ENCRYPTED_PAY_PASSWORD_ERROR.getCode(), String.format(UserCodeEnum.ENCRYPTED_PAY_PASSWORD_ERROR.getMessage(), remainNum));
        }
        //密码验证成功
        user.setChangePwdErrNum(UserConstant.IS_ZERO);
        userDao.updateById(user);
        //设置找回密码,支付密码验证标志,时间5分钟
        template.opsForValue().set(String.format("%s%s", RedisKey.FIND_LOGIN_PASSWORD_VERIFY_PAY_PASSWORD_SUCCESS, userId), "0", 5 * 60, TimeUnit.SECONDS);
        return RPCResult.success();

    }

    @Override
    public ApiResult<Boolean> isBandEncrypted(Long userId) {
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        QueryWrapper<UserEncryptedEntity> ew = new QueryWrapper<UserEncryptedEntity>();
        ew.eq("user_id", userId);
        Boolean flag = userEncryptedDao.selectCount(ew) > 0;
        return RPCResult.success(flag);
    }

    @Override
    public ApiResult<Boolean> updatePayPwd(Long userId, String payPassword) {
        //参数验证
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        if (StrUtil.isBlank(payPassword)) {
            return RPCResult.custom(UserCodeEnum.EMPTY_PAY_PWD.getCode(), UserCodeEnum.EMPTY_PAY_PWD.getMessage());
        }
        //获取redis 判断是否验证成功才通过修改密码
        String encryptedKey = String.format("%s%s", RedisKey.ENCRYPTED_VERIFY_SUCCESS, userId);
        ValueOperations<String, String> ops = template.opsForValue();
        String isPassEncrypted = ops.get(encryptedKey);
        if (StringUtils.isEmpty(isPassEncrypted)) {
            return RPCResult.custom(UserCodeEnum.USER_UNVERIFIED_ERROR.getCode(), UserCodeEnum.USER_UNVERIFIED_ERROR.getMessage());
        }
        UserEntity customer = userDao.findById(userId);
        if (UserStatus.USER_STATUS_31.getCode().equals(customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "该账号已被冻结");
        }
        String MD5NewPwd = userInnerService.getMD5PwdByRSA(payPassword);
        customer.setPayPwd(MD5NewPwd);
        customer.setUpdateTime(new Date());
        customer.setLastChangePwdTime(new Date());
        customer.setChangePayPwdErrNum(UserConstant.IS_ZERO);
        userDao.updateById(customer);
        template.delete(encryptedKey);
        return RPCResult.success();
    }

    @Override
    public ApiResult<List<UserEncryptedDTO>> queryEncryptedById(Long userId) {

        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        QueryWrapper<UserEncryptedEntity> ew = new QueryWrapper<UserEncryptedEntity>();
        ew.eq("user_id", userId);
        List<UserEncryptedEntity> userEncryptedDTOs = userEncryptedDao.selectList(ew);
        List<UserEncryptedDTO> resultList = new ArrayList<UserEncryptedDTO>();
        if (CollectionUtil.isEmpty(userEncryptedDTOs)) {
            return RPCResult.success(resultList);
        }
        for (UserEncryptedEntity entity : userEncryptedDTOs) {
            UserEncryptedDTO dto = new UserEncryptedDTO();
            BeanUtil.copyProperties(entity, dto);
            dto.setResult(null);
            resultList.add(dto);
        }
        return RPCResult.success(resultList);
    }

    @Override
    public ApiResult<Boolean> isSetPayPwdByUserId(Long userId, Long siteId) {
        if (ObjectUtil.isNull(userId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询用户id为空");
        }
        if (ObjectUtil.isNull(siteId)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "查询站点id为空");
        }
        UserEntity userParam = new UserEntity();
        userParam.setId(userId);
        userParam.setSiteId(siteId);
        userParam.setIsDel(UserConstant.IS_F);
        UserEntity userEntity = userDao.selectOne(new QueryWrapper<>(userParam));
        Boolean flag = ObjectUtil.isNotNull(userEntity) && StrUtil.isNotEmpty(userEntity.getPayPwd());
        return RPCResult.success(flag);

    }

    @Override
    public ApiResult verifyLoginPwd(String password, LogUserDTO logDTO) {
        //1.用户状态判断
        Long userId = logDTO.getUserId();
        UserEntity customer = userDao.findById(userId);
        if (customer == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        if (ObjectUtil.equal(UserConstant.IS_ONE, customer.getIsDel())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        if (ObjectUtil.equal(UserConstant.IS_ONE, customer.getIsDemo())) {
            return RPCResult.custom(UserCodeEnum.USER_NO_AUTHORITY.getCode(), UserCodeEnum.USER_NO_AUTHORITY.getMessage());
        }
        if (ObjectUtil.isNull(customer.getStatus()) || ObjectUtil.equal(UserStatus.USER_STATUS_31.getCode(), customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "账户已被冻结");
        }
        if (ObjectUtil.isNull(customer.getStatus()) || !ObjectUtil.equal(UserStatus.USER_STATUS_10.getCode(), customer.getStatus())) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), UserCodeEnum.USER_UNNORMAL_STATUS.getMessage());
        }
        //2.判断原密码输错次数是否超过允许的最大次数
        int maxPwdErrNum; //修改密码，原密码允许输错的最大次数
        ApiResult<Map<String, String>> sysKeyValueMap = keyValueService.findKeyValueMapByDictCode(UserConstant.CHANGE_PASS_WORD_ERROR_NUM);
        if (RPCResult.checkApiResult(sysKeyValueMap)) {
            maxPwdErrNum = NumberUtils.toInt(sysKeyValueMap.getData().get(UserConstant.CHANGE_PASS_WORD_ERROR_NUM));
        } else {
            maxPwdErrNum = UserCfg.MAX_ENCRYPTED_ERROR_COUNT;
        }
        Map<String, Object> response = new HashMap<>();
        response.put("pwdErrNum", customer.getChangePwdErrNum()); //当前用户原密码输错次数
        response.put("maxPwdErrNum", maxPwdErrNum); //原密码允许输错的最大次数
        // 4.获取私钥对，解密
        String rsaPrivateKey = getRSAPrivateKey();
        if (StrUtil.isBlank(rsaPrivateKey)) {
            return RPCResult.custom(UserCodeEnum.CREATE_RSA_FAILURE.getCode(), UserCodeEnum.CREATE_RSA_FAILURE.getMessage());
        }
        String MD5OldPwd;
        try {
            MD5OldPwd = new String(RSAUtil.privateDecrypt(RSAUtil.base642Byte(password), RSAUtil.string2PrivateKey(rsaPrivateKey)));
        } catch (Exception e) {
            logger.error("异常信息:", e);
            return RPCResult.custom(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
        }
        if (StrUtil.isBlank(customer.getPassword())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_PASSWORD.getCode(), UserCodeEnum.EMPTY_VALUE_PASSWORD.getMessage());
        }
        if (!customer.getPassword().equals(MD5OldPwd)) {
            int errorNum = customer.getChangePwdErrNum();
            int nowErrorNum = errorNum + 1;
            customer.setChangePwdErrNum(nowErrorNum);
            customer.setUpdateTime(new Date());
            response.put("pwdErrNum", customer.getChangePwdErrNum()); //当前用户原密码输错次数
            //下线并冻结
            if (nowErrorNum >= maxPwdErrNum) {
                customer.setStatus(UserStatus.USER_STATUS_31.getCode());
                userDao.updateById(customer);
                kickOutUserApi(userId);
                userTokenDao.deleteById(customer.getId());
                logger.info("用户验证登录密码输错次数已超允许的最大次数，账号已被冻结");
                logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), logDTO.getPlat(), "代理会员：" + customer.getUserName() + "。验证密码输入错误次数超过最大限制，账户被冻结！",
                        UserCfg.UPDATE_PWD, UserCfg.USER, logDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, logDTO.getLoginIp(), logDTO.getLoginUrl(), logDTO.getLoginArea(), logDTO.getSiteCode());
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "账号验证密码错误次数已达到最大次数,已被冻结!");
            }
            userDao.updateById(customer);
            logUserInnerService.addUserLog(customer.getId(), customer.getUserName(), logDTO.getPlat(), String.format("代理会员：%s。验证登录密码错误！当前错误次数:%s", customer.getUserName(), customer.getChangePwdErrNum()),
                    UserCfg.UPDATE_PWD, UserCfg.USER, logDTO.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, logDTO.getLoginIp(), logDTO.getLoginUrl(), logDTO.getLoginArea(), logDTO.getSiteCode());
            return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), String.format("验证密码错误,%s次错误后账号将被冻结", maxPwdErrNum - nowErrorNum));
        }
        customer.setChangePwdErrNum(UserConstant.IS_ZERO);
        customer.setUpdateTime(new Date());
        userDao.updateById(customer);
        return RPCResult.success(true);
    }

    @Override
    public ApiResult updateConvertType(Long userId, Long siteId, Integer type) {
        UserEntity userParam = new UserEntity();
        userParam.setId(userId);
        userParam.setSiteId(siteId);
        userParam.setIsDel(UserConstant.IS_F);
        userParam.setIsConvert(type);
        return userDao.updateById(userParam) > 0 ? RPCResult.success() : RPCResult.fail();

    }
    @Override
    public ApiResult<String> getTextByRSA(String text) {
        return RPCResult.success(getMD5PwdByRSA(text));
    }

}

