package com.jq.user.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.framework.core.exception.Assert;
import com.jq.framework.core.exception.ErrorCode;
import com.jq.framework.core.exception.JQException;
import com.jq.platform.auth.dto.UserRoleDTO;
import com.jq.platform.auth.service.UserRoleService;
import com.jq.platform.site.dto.SiteDTO;
import com.jq.platform.site.service.SiteService;
import com.jq.platform.sysmanage.service.KeyValueService;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserCfg;
import com.jq.user.constant.UserConstant;
import com.jq.user.constant.UserStatus;
import com.jq.user.customer.dao.SysDeptDao;
import com.jq.user.customer.dao.SysUserDao;
import com.jq.user.customer.dao.UserDao;
import com.jq.user.customer.dao.UserInfoDao;
import com.jq.user.customer.dao.UserRebateDao;
import com.jq.user.customer.dto.QuerySysUserDTO;
import com.jq.user.customer.dto.QueryTestUserDTO;
import com.jq.user.customer.dto.RegisterUserDTO;
import com.jq.user.customer.dto.SysUserDTO;
import com.jq.user.customer.dto.SysUserUpdateInfoDTO;
import com.jq.user.customer.dto.TestUserDTO;
import com.jq.user.customer.dto.UserDTO;
import com.jq.user.customer.dto.UserDemoReqDTO;
import com.jq.user.customer.dto.UserModelDTO;
import com.jq.user.customer.entity.SysDeptEntity;
import com.jq.user.customer.entity.SysUserEntity;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserInfoEntity;
import com.jq.user.customer.entity.UserRebateEntity;
import com.jq.user.customer.service.SysUserInnerService;
import com.jq.user.customer.service.UserInnerService;
import com.jq.user.customer.support.SysUserName;
import com.jq.user.exception.UserException;
import com.jq.user.log.entity.LogUserEntity;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.refer.dao.UserReferDao;
import com.jq.user.refer.entity.UserReferEntity;
import com.jq.user.score.dao.UserRankDao;
import com.jq.user.score.dao.UserRankScoreDao;
import com.jq.user.score.entity.UserRankEntity;
import com.jq.user.score.entity.UserRankScoreEntity;
import com.jq.user.support.PageUtil;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import com.liying.trade.user.api.UserFundService;
import com.liying.trade.user.vo.UserFundResp;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Author: Brady
 * Description:
 * Date: 2018/4/26
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserDao,SysUserEntity> implements SysUserInnerService {

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private StringRedisTemplate template;
    @Resource
    private UserInfoDao userInfoDao;
    @Resource
    private UserDao userDao;
    @Resource
    private UserInnerService userInnerService;
    @Resource
    private LogUserInnerService logUserInnerService;
    @Resource
    private KeyValueService keyValueService;
    @Resource
    private SiteService siteService;
    @Resource
    private UserFundService userFundService;
    @Value("${JWTTime}")
    private Integer ttlMillis;
    @Value("${SessionUuidTime}")
    private Integer sessionUuidTime;
    @Resource
    private SysDeptDao sysDeptDao;
    @Resource
    private UserRankDao userRankDao;
    @Resource
    private UserRankScoreDao userRankScoreDao;
    @Resource
    private UserReferDao userReferDao;
    @Resource
    private LogUserInnerService logService;
    @Resource
    private UserRebateDao userRebateDao;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private UserProxyInnerService userProxyInnerService;

    @Override
    public Map<String, Object> querySysUserList(Page page, String userName, Long siteId, Long deptId) {
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        if (StrUtil.isNotBlank(userName)) {
            ew.eq("user_name", userName);
        }
        if (ObjectUtil.isNotNull(siteId)) {
            ew.eq("site_id", siteId);
        }
        if (ObjectUtil.isNotNull(deptId)) {
            ew.eq("dept_id", deptId);
        }
        IPage<SysUserEntity> iPage = sysUserDao.selectPage(page, ew);
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (SysUserEntity sysUserEntity : iPage.getRecords()) {
            ApiResult<List<UserRoleDTO>> api = userRoleService.findByRoleIdApi(sysUserEntity.getId());
            if (!RPCResult.checkApiResult(api)) {
                throw new UserException(api.getCode());
            }
            List<UserRoleDTO> list = api.getData();
            Map<String, Object> map = new HashMap<>();
            if (list.size() != 0) {
                map.put("roleId", api.getData().get(0).getRoleId().toString());
            }
            map.put("id", sysUserEntity.getId().toString());
            map.put("deptId", sysUserEntity.getDeptId().toString());
            map.put("siteId", sysUserEntity.getSiteId().toString());
            map.put("userName", sysUserEntity.getUserName());
            map.put("createTime", DateUtil.formatDate(sysUserEntity.getCreateTime()));
            map.put("allowIp", sysUserEntity.getAllowIp());
            map.put("isEnable", sysUserEntity.getIsEnable());
            resultList.add(map);
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("resultList", resultList);
        resultMap.put("total", page.getTotal());
        return resultMap;
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean confirmExistUserName(String userName) {
        return StrUtil.isNotBlank(sysUserDao.confirmExistUserName(userName));
    }

    @Override
    public Boolean updateSysUser(SysUserEntity sysUser) {
        return sysUserDao.updateSysUser(sysUser) == 1;
    }

    @Override
    public Boolean deleteSysUserByUserId(Long userId, Long siteId) {
        return 0 < sysUserDao.deleteSysUserByUserId(userId, siteId);
    }

    @Override
    public Boolean sysUserDisabled(Long userId, String userName) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (sysUserEntity == null || sysUserEntity.getIsDel() == 1) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        sysUserEntity.setIsEnable(UserConstant.IS_F);
        sysUserEntity.setUpdateTime(new Date());
        sysUserEntity.setUpdateBy(userName);
        return 0 < sysUserDao.updateById(sysUserEntity);
    }

    @Override
    public Boolean resetSysUserPwd(String userName, Long userId, String ip, String url) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (null == sysUserEntity) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        //重置密码，采用MD5加密
        String md5Pwd = SecureUtil.md5(UserCfg.SYS_USER_DEFAULT_PWD);
        sysUserEntity.setPassword(md5Pwd);
        sysUserEntity.setUpdateBy(userName);
        sysUserEntity.setUpdateTime(new Date());
        sysUserEntity.setLoginPwdErrCount(UserConstant.IS_ZERO);
        //加入日志
        logUserInnerService.addUserLog(sysUserEntity.getId(), sysUserEntity.getUserName(), 1,
                "管理员重置密码", UserCfg.UPDATE_PWD,
                (ObjectUtil.equal(UserConstant.IS_ZERO, sysUserEntity.getSiteId()) ? UserCfg.SYS : UserCfg.SITE), sysUserEntity.getSiteId(),
                UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
        return 1 == sysUserDao.updateById(sysUserEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public SysUserEntity findSysUser(Long userId, Long siteId) {
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("id",userId);
        ew.eq("site_id",siteId);
        return getOne(ew);
    }

    @Override
    public Boolean updateSysPwd(String userName, Long userId, String oldPwd, String newPwd, Long siteId) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (!ObjectUtil.equal(siteId, sysUserEntity.getSiteId())) {
            throw new UserException(UserCodeEnum.SITEID_ERR.getCode());
        }
        if (ObjectUtil.equal(UserConstant.IS_ONE, sysUserEntity.getIsDel()) || ObjectUtil.equal(UserConstant.IS_ZERO, sysUserEntity.getIsEnable())) {
            throw new UserException(UserCodeEnum.USER_UNNORMAL_STATUS.getCode());
        }
        // 获取私钥对，解密
        String MD5OldPwd;
        String MD5NewPwd;
        try {
            MD5OldPwd = userInnerService.getMD5PwdByRSA(oldPwd);
            MD5NewPwd = userInnerService.getMD5PwdByRSA(newPwd);
        } catch (Exception e) {
            logger.error("异常信息:", e);
            throw new UserException(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
        }
        Assert.isNull(sysUserEntity.getPassword(), "用户密码为空");
        if (!sysUserEntity.getPassword().equals(MD5OldPwd)) {
            throw new UserException(UserCodeEnum.USER_NAMEORPWD_ERR.getCode());
        }
        if (MD5OldPwd.equals(MD5NewPwd)) {
            throw new UserException(UserCodeEnum.SAME_PWD.getCode());
        }
        //修改用戶密碼
        if (Objects.equals(sysUserEntity.getLoginCount(), UserConstant.IS_ZERO)) {
            sysUserEntity.setLoginCount(UserConstant.IS_ONE);
        }
        sysUserEntity.setPassword(MD5NewPwd);
        sysUserEntity.setUpdateBy(userName);
        sysUserEntity.setUpdateTime(new Date());
        Integer num = sysUserDao.updateById(sysUserEntity);
        return num == 1;
    }

    @Override
    public Boolean addSysUser(SysUserEntity sysUserEntity) {
        this.confirmExistUserName(sysUserEntity.getUserName());
        Date date = new Date();
        sysUserEntity.setCreateTime(date);
        sysUserEntity.setUpdateTime(date);
        sysUserEntity.setPassword(userInnerService.getMD5PwdByRSA(sysUserEntity.getPassword()));
        return sysUserDao.insert(sysUserEntity) == 1;
    }

    @Override
    public Map<String, Object> getUserProfile(Long userId) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("userName", sysUserEntity.getUserName());
        resultMap.put("loginCount", sysUserEntity.getLoginCount());
        resultMap.put("id", sysUserEntity.getId().toString());
        resultMap.put("isEnable", sysUserEntity.getIsEnable());
        resultMap.put("siteId", sysUserEntity.getSiteId().toString());
        resultMap.put("deptId", sysUserEntity.getDeptId().toString());
        return resultMap;
    }

    @Override
    public Boolean updateUserProfile(SysUserEntity sysUser) {
        sysUser.setUpdateBy(sysUser.getUserName());
        sysUser.setUpdateTime(new Date());
        return sysUserDao.updateById(sysUser) == 1;
    }

    @Override
    public ApiResult<String> getUserNameById(Long id) {
            String userName = sysUserDao.findUserNameById(id);
            if (StrUtil.isEmpty(userName)) {
                return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }
            return RPCResult.success(userName);
    }

    @Override
    public ApiResult<PageInfo<SysUserDTO>> getSysUsersByDeptId(SysUserDTO sysUserDTO) {
        try {
            QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
            ew.eq("is_del", UserConstant.IS_F);
            ew.eq("dept_id", sysUserDTO.getDeptId());
            Page page = PageUtil.buildPage(sysUserDTO.getPage(), sysUserDTO.getLimit(), sysUserDTO.getOrderDirection(), sysUserDTO.getOrderField());
            IPage<SysUserEntity> iPage = sysUserDao.selectPage(page, ew);
            List<SysUserDTO> sysUserDTOS = new ArrayList<>();
            for (SysUserEntity sysUserEntity : iPage.getRecords()) {
                SysUserDTO finalSysUserDTO = new SysUserDTO();
                BeanUtil.copyProperties(sysUserEntity, finalSysUserDTO);
                sysUserDTOS.add(finalSysUserDTO);
            }
            PageInfo<SysUserDTO> pageInfo = new PageInfo<>(sysUserDTOS, sysUserDTO.getPage(), sysUserDTO.getLimit(), page.getTotal());
            return RPCResult.success(pageInfo);
        } catch (Exception e) {
            logger.error("异常信息:", e);
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "根据管理员部门查询用户失败");
    }

    @Override
    public ApiResult<Map<String, Long>> judgeUserRole(Long userId) {
        Map<String, Object> resultMap = new HashMap<>();
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("id",userId);
        ew.eq("is_del",UserConstant.IS_F);
        SysUserEntity sysUser = getOne(ew);
        if (null != sysUser) {
            resultMap.put("userRole", 0L);
            resultMap.put("siteId", sysUser.getSiteId());
            return RPCResult.success(resultMap);
        } else {
            UserEntity selectUserEntity = new UserEntity();
            selectUserEntity.setId(userId);
            selectUserEntity.setIsDel(UserConstant.IS_F);
            UserEntity user = userDao.selectOne(new QueryWrapper<>(selectUserEntity));
            if (null != user) {
                resultMap.put("userRole", 1L);
                resultMap.put("siteId", user.getSiteId());
                return RPCResult.success(resultMap);
            } else {
                return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
            }
        }
    }

    @Override
    @Transactional(noRollbackFor = JQException.class)
    public ApiResult<SysUserDTO> loginSubmitApi(UserModelDTO userModelDTO, String ip, String url, String ipAttribution) {
        //判断参数
        if (StrUtil.isEmpty(userModelDTO.getUserName())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_USERNAME.getCode(), UserCodeEnum.EMPTY_VALUE_USERNAME.getMessage());
        }
        if (StrUtil.isEmpty(userModelDTO.getPassword())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_PASSWORD.getCode(), UserCodeEnum.EMPTY_VALUE_PASSWORD.getMessage());
        }
        if (ObjectUtil.isNull(userModelDTO.getPlatformType())) {
            return RPCResult.custom(UserCodeEnum.EMPTY_VALUE_PLATFORMTYPE.getCode(), UserCodeEnum.EMPTY_VALUE_PLATFORMTYPE.getMessage());
        }
        //参数转换
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtil.copyProperties(userModelDTO, sysUserEntity);
        SysUserEntity selectUser = new SysUserEntity();
        selectUser.setUserName(sysUserEntity.getUserName());
        selectUser.setIsDel(UserConstant.IS_F);
        SysUserEntity sysUser = sysUserDao.selectOne(new QueryWrapper<>(selectUser));
        if (sysUser == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        String passwordMD5 = userInnerService.getMD5PwdByRSA(sysUserEntity.getPassword());
        int maxErrorCount;
        ApiResult<Map<String, String>> map = keyValueService.findKeyValueMapByDictCode(UserConstant.SYS_LOGIN_USER_ERROR_PAS_NUM);
        if (!RPCResult.checkApiResult(map)) {
            maxErrorCount = UserCfg.MAX_ERROR_COUNT;
        } else {
            maxErrorCount = NumberUtils.toInt(map.getData().get(UserConstant.SYS_LOGIN_USER_ERROR_PAS_NUM));
        }
        if (!passwordMD5.equalsIgnoreCase(sysUser.getPassword())) {
            Integer errorCount = sysUser.getLoginPwdErrCount() + 1;
            sysUser.setLoginPwdErrCount(errorCount);
            sysUser.setUpdateTime(new Date());
            if (sysUser.getLoginPwdErrCount() >= maxErrorCount - 1) {
                sysUser.setIsEnable(UserConstant.IS_F);
                sysUser.setUpdateTime(new Date());
                sysUserDao.updateById(sysUser);
                return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), UserCodeEnum.USER_NAMEORPWD_ERR.getMessage());
            }
            if (Objects.equals(sysUser.getIsEnable(), UserConstant.IS_F)) {
                return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "当前账号已被禁用，请联系管理员！");
            }
            //判断站点是否存在
            ApiResult<SiteDTO> siteDTOApi = siteService.findSiteDTOById(sysUser.getSiteId());
            if (!RPCResult.checkApiResult(siteDTOApi)) {
                return RPCResult.custom(siteDTOApi.getCode(), siteDTOApi.getMessage());
            }
            SiteDTO siteDTO = siteDTOApi.getData();
            if (ObjectUtil.equal(siteDTO.getIsEnable(), UserConstant.IS_F)) {
                return RPCResult.custom(UserCodeEnum.SITE_DISTBLED.getCode(), UserCodeEnum.SITE_DISTBLED.getMessage());
            }
            String content = "登录：管理员" + sysUser.getUserName() + "在登陆时，登录密码错误[" + sysUser.getLoginPwdErrCount() + "]次,累计[5]次后账号将禁用";
            //添加登录失败日志
            logUserInnerService.addUserLog(sysUser.getId(), sysUser.getUserName(), UserConstant.PC, content, UserCfg.LOGIN_FAILURE,
                    UserCfg.SITE, sysUser.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_LOGIN, ip, url, ipAttribution, sysUser.getSiteCode());
            sysUserDao.updateById(sysUser);
            return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), UserCodeEnum.USER_NAMEORPWD_ERR.getMessage());
        }
        if (Objects.equals(sysUser.getIsEnable(), UserConstant.IS_F)) {
            return RPCResult.custom(UserCodeEnum.USER_UNNORMAL_STATUS.getCode(), "当前账号已被禁用,请联系管理员");
        }
        //判断站点是否存在
        ApiResult<SiteDTO> siteDTOApi = siteService.findSiteDTOById(sysUser.getSiteId());
        if (!RPCResult.checkApiResult(siteDTOApi)) {
            return RPCResult.custom(siteDTOApi.getCode(), siteDTOApi.getMessage());
        }
        SiteDTO siteDTO = siteDTOApi.getData();
        if (ObjectUtil.equal(siteDTO.getIsEnable(), UserConstant.IS_F)) {
            return RPCResult.custom(UserCodeEnum.SITE_DISTBLED.getCode(), UserCodeEnum.SITE_DISTBLED.getMessage());
        }
        SysUserEntity finalSysUser = sysUserDao.selectById(sysUser.getId());
        SysUserDTO finalSysUserDTO = new SysUserDTO();
        BeanUtil.copyProperties(finalSysUser, finalSysUserDTO);
        // 判断用户是否是第一次登录，是则跳转至修改登录密码界面
        if (sysUser.getSiteId() != 0L && sysUser.getLoginCount() == -1) {
            return RPCResult.custom(UserCodeEnum.SYS_USER_FIRST_LOGIN.getCode(), UserCodeEnum.SYS_USER_FIRST_LOGIN.getMessage(),finalSysUserDTO);
        }
        //添加登录次数，重置密码错误次数
        sysUser.setLoginCount(sysUser.getLoginCount() + 1);
        sysUser.setLoginPwdErrCount(UserConstant.IS_ZERO);
        sysUserDao.updateById(sysUser);
        return RPCResult.success(finalSysUserDTO);
    }

    @Override
    public ApiResult<PageInfo<UserDemoReqDTO>> queryDemoUserPageApi(UserDemoReqDTO userDemoReqDTO, Long siteId) {

        //参数转换
        String loginBeginTime = userDemoReqDTO.getLoginBeginTime();
        String loginEndTime = userDemoReqDTO.getLoginEndTime();
        String orderBeginTime = userDemoReqDTO.getOrderBeginTime();
        String orderEndTime = userDemoReqDTO.getOrderEndTime();
        String gameCode = userDemoReqDTO.getGameCode();
        Page page = PageUtil.buildPage(userDemoReqDTO.getPage(), userDemoReqDTO.getLimit(), userDemoReqDTO.getOrderDirection(), userDemoReqDTO.getOrderField());
        Map<String, Object> resultMap = userInnerService.queryDemoUserPage(siteId, page, loginBeginTime, loginEndTime, orderBeginTime, orderEndTime, gameCode);
        List<UserDemoReqDTO> finalList = new ArrayList<>();
        List<Map<String, Object>> lists = (List<Map<String, Object>>) resultMap.get("resultList");
        for (Map<String, Object> map : lists) {
            UserDemoReqDTO userDemoReq = new UserDemoReqDTO();
            userDemoReq = BeanUtil.mapToBean(map, userDemoReq.getClass(), true);
            ApiResult<Long> totalAmountResult = userFundService.getTotalAmount(map.get("id").toString(), map.get("siteCode").toString());
            Long totalAmount = totalAmountResult.getData();
            userDemoReq.setTotalCount(totalAmount);
            finalList.add(userDemoReq);
        }
        PageInfo<UserDemoReqDTO> pageInfo = new PageInfo<>(finalList, userDemoReqDTO.getPage(), userDemoReqDTO.getLimit(), (long)resultMap.get("total"));
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<PageInfo<QueryTestUserDTO>> queryTestUserPageApi(TestUserDTO testUserDTO, Long siteId) {

        ApiResult<SiteDTO> siteByIdApi = siteService.findSiteByIdApi(siteId);
        if (!RPCResult.checkApiResult(siteByIdApi)) {
            throw new UserException(UserCodeEnum.SITE_NOT_EXIST.getCode(), "站点不存在");
        }
        Page page = PageUtil.buildPage(testUserDTO.getPage(), testUserDTO.getLimit(), testUserDTO.getOrderDirection(), testUserDTO.getOrderField());
        SiteDTO siteDTO = siteByIdApi.getData();
        Map<String, Object> map = BeanUtil.beanToMap(testUserDTO);
        map.put("siteId", siteId);
        Map<String, Object> resultMap = userInnerService.queryTestUserPage(page, map);
        List<Map<String, Object>> lists = (List<Map<String, Object>>) resultMap.get("resultList");
        List<QueryTestUserDTO> finalList = new ArrayList<>();
        if (CollectionUtil.isEmpty(lists)){
            return RPCResult.success(new PageInfo<>(finalList, testUserDTO.getPage(), testUserDTO.getLimit(), (long)resultMap.get("total")));
        }
        StringBuilder ids = new StringBuilder();
        for (Map<String, Object> maps : lists) {
            QueryTestUserDTO queryTestUserDTO = new QueryTestUserDTO();
            queryTestUserDTO = BeanUtil.mapToBean(maps, queryTestUserDTO.getClass(), true);
            queryTestUserDTO.setRealName(Base64.decodeStr(queryTestUserDTO.getRealName()));
            Integer status = queryTestUserDTO.getStatus();
            String controlStatus = "0|0|0|0";
            if (status.equals(UserStatus.USER_STATUS_11.getCode())) {
                controlStatus = "1|0|0|0";
            } else if (status.equals(UserStatus.USER_STATUS_21.getCode())) {
                controlStatus = "0|1|0|0";
            } else if (status.equals(UserStatus.USER_STATUS_31.getCode())) {
                controlStatus = "0|0|1|0";
            } else if (status.equals(UserStatus.USER_STATUS_41.getCode())) {
                controlStatus = "0|0|0|1";
            }
            queryTestUserDTO.setControlStatus(controlStatus);
            ids.append(queryTestUserDTO.getId()).append(",");
            finalList.add(queryTestUserDTO);
        }
        ids.deleteCharAt(ids.length()-1);
        ApiResult<List<UserFundResp>> apiResult = userFundService.queryUserFund(ids.toString(), siteDTO.getSiteCode());
        List<UserFundResp> data = apiResult.getData();
        Map<Long,UserFundResp> userFundMap = new HashMap<>();
        for (UserFundResp datum : data) {
            userFundMap.put(datum.getId(),datum);
        }
        for (QueryTestUserDTO queryTestUserDTO : finalList) {
            UserFundResp userFundResp = userFundMap.get(queryTestUserDTO.getId());
            if (userFundResp!=null) {
                queryTestUserDTO.setTotalAmount(userFundResp.getTotalAmount());
            }
        }
        PageInfo<QueryTestUserDTO> pageInfo = new PageInfo<>(finalList, testUserDTO.getPage(), testUserDTO.getLimit(), (long)resultMap.get("total"));
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult deleteUserByIdApi(Long userId) {
        Boolean flag = userInnerService.deleteUserByUserId(userId);
        return flag ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "删除用户失败");
    }

    @Override
    public ApiResult<PageInfo<RegisterUserDTO>> queryRegisterUserApi(UserDTO userDTO) {

        Page page = PageUtil.buildPage(userDTO.getPage(), userDTO.getLimit(), userDTO.getOrderDirection(), userDTO.getOrderField());
        Long siteId = userDTO.getSiteId();
        if (ObjectUtil.isNull(siteId)) {
            throw new UserException(UserCodeEnum.LACK_USER_REGISTER_INFO.getCode(), "站点数据缺失");
        }
        String date = DateUtil.formatDate(new Date());
        String beginTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        List<RegisterUserDTO> userDTOList = userDao.queryRegisterUserApi(page, beginTime, endTime, siteId);
        if (CollectionUtils.isNotEmpty(userDTOList)) {
            for (RegisterUserDTO registerUserDTO : userDTOList) {
                String realName = registerUserDTO.getRealName();
                if (StrUtil.isNotEmpty(realName)) {
                    realName = Base64.decodeStr(realName);
                    registerUserDTO.setRealName(realName);
                }

                ApiResult<Long> totalAmountResult = userFundService.getTotalAmount(registerUserDTO.getId().toString(), registerUserDTO.getSiteCode());
                registerUserDTO.setTotalAmount(totalAmountResult.getData()==null?0L:totalAmountResult.getData());
            }
        }
        PageInfo<RegisterUserDTO> pageInfo = new PageInfo<>(userDTOList, userDTO.getPage(), userDTO.getLimit(), page.getTotal());
        return RPCResult.success(pageInfo);
    }

    @Override
    public ApiResult<PageInfo<SysUserDTO>> querySysUserListApi(QuerySysUserDTO querySysUserDTO) {
        PageInfo pageInfo = querySysUser(querySysUserDTO);
        return RPCResult.success(pageInfo);
    }

    private PageInfo querySysUser(QuerySysUserDTO querySysUserDTO) {
        Page page = PageUtil.buildPage(querySysUserDTO.getPage(), querySysUserDTO.getLimit(), querySysUserDTO.getOrderDirection(), querySysUserDTO.getOrderField());
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("is_del", UserConstant.IS_F);
        if (StrUtil.isNotBlank(querySysUserDTO.getUserName())) {
            ew.eq("user_name", querySysUserDTO.getUserName());
        }
        if (querySysUserDTO.getSiteId() != null) {
            ew.eq("site_id", querySysUserDTO.getSiteId());
        }
        if (querySysUserDTO.getDeptId() != null) {
            ew.eq("dept_id", querySysUserDTO.getDeptId());
        }
        if (querySysUserDTO.getIsEnable() != null) {
            ew.eq("is_enable", querySysUserDTO.getIsEnable());
        }
        if (querySysUserDTO.getSex() != null) {
            ew.eq("sex", querySysUserDTO.getSex());
        }
        if (StrUtil.isNotBlank(querySysUserDTO.getRealName())) {
            ew.eq("real_name", querySysUserDTO.getRealName());
        }
        IPage<SysUserEntity> iPage = sysUserDao.selectPage(page, ew);
        List<SysUserDTO> finalList = new ArrayList<>();
        for (SysUserEntity sysUserEntity : iPage.getRecords()) {
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtil.copyProperties(sysUserEntity, sysUserDTO);
            if (!StrUtil.equalsIgnoreCase(sysUserDTO.getKey(), "F")) {
                sysUserDTO.setKey("T");
            }
            finalList.add(sysUserDTO);
        }
        return new PageInfo<>(finalList, querySysUserDTO.getPage(), querySysUserDTO.getLimit(), page.getTotal());
    }

    @Override
    public ApiResult confirmExistUserNameApi(String userName) {
        Boolean flag = this.confirmExistUserName(userName);
        return flag ? RPCResult.success(true) : RPCResult.success(false);
    }

    @Override
    public ApiResult updateSysUserApi(SysUserUpdateInfoDTO sysUserUpdateInfoDTO, String updateUserName) {
        SysUserEntity sysUser = new SysUserEntity();
        BeanUtil.copyProperties(sysUserUpdateInfoDTO, sysUser);
        sysUser.setUpdateBy(updateUserName);
        sysUser.setUpdateTime(new Date());
        Integer num = sysUserDao.updateById(sysUser);
        ApiResult<SiteDTO> siteDTOApi = siteService.findSiteDTOById(sysUser.getSiteId());
        if (!RPCResult.checkApiResult(siteDTOApi)) {
            return RPCResult.custom(UserCodeEnum.SITE_NOT_EXIST.getCode(), UserCodeEnum.SITE_NOT_EXIST.getMessage());
        }
        return num == 1 ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "更新管理员失败");
    }

    @Override
    public ApiResult deleteSysUserApi(Long userId, Long siteId) {
        SysUserEntity sysUserEntity = new SysUserEntity();
        sysUserEntity.setIsDel(UserConstant.IS_F);
        sysUserEntity.setId(userId);
        sysUserEntity.setSiteId(siteId);
        SysUserEntity sysUser = sysUserDao.selectOne(new QueryWrapper<>(sysUserEntity));
        if (sysUser == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        Boolean flag = this.deleteSysUserByUserId(userId, siteId);
        return flag ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "删除管理员失败");
    }

    @Override
    public ApiResult sysUserDisabledApi(Long userId, String updateUserName) {
        Boolean flag = this.sysUserDisabled(userId, updateUserName);
        return flag ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "禁用管理员失败");
    }

    @Override
    public ApiResult resetSysUserPwdApi(Long userId, String updateUserName, String ip, String url) {
        Boolean flag = this.resetSysUserPwd(updateUserName, userId, ip, url);
        return flag ? RPCResult.success() : RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "重置管理员密码失败");
    }

    @Override
    public ApiResult addSysUserApi(SysUserUpdateInfoDTO sysUserInfo, String updateName, String ip, String url) {
        if (StrUtil.isBlank(updateName)) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "操作账户信息为空");
        }
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("user_name",updateName);
        ew.eq("is_del",UserConstant.IS_F);
        SysUserEntity updateUser = getOne(ew);
        if (null == updateUser) {
            return RPCResult.custom(UserCodeEnum.LACK_INFORMATION.getCode(), "操作账户信息为空");
        }
        Assert.isNull(sysUserInfo.getUserName(), "登录名参数缺失");
        Assert.isNull(sysUserInfo.getDeptId(), "部门参数缺失");
        Assert.isNull(sysUserInfo.getSiteId(), "站点参数缺失");
        if (sysUserDao.confirmExistUserName(sysUserInfo.getUserName()) != null) {
            return RPCResult.custom(UserCodeEnum.USER_EXIST.getCode(), UserCodeEnum.USER_EXIST.getMessage());
        }
        String userType = UserCfg.SYS;
        if (sysUserInfo.getSiteId() != 0L) {
            userType = UserCfg.SITE;
        }
        SysUserEntity sysUserEntity = new SysUserEntity();
        BeanUtil.copyProperties(sysUserInfo, sysUserEntity);
        Long sysId = IdWorker.getId();
        sysUserEntity.setId(sysId);
        if (sysUserEntity.getPassword() == null) {
            sysUserEntity.setPassword(UserCfg.SYS_USER_DEFAULT_PWD);
        }
        ApiResult<SiteDTO> siteDTOById = siteService.findSiteDTOById(sysUserInfo.getSiteId());
        if (!RPCResult.checkApiResult(siteDTOById)) {
            return RPCResult.custom(siteDTOById.getCode(), siteDTOById.getMessage());
        } else {
            SiteDTO siteDTO = siteDTOById.getData();
            sysUserEntity.setSiteCode(siteDTO.getSiteCode());
            sysUserEntity.setLoginCount(-1);
            Integer flag = sysUserDao.insert(sysUserEntity);
            if (flag > 0) {
                String content = "新增管理员成功";
                logUserInnerService.addUserLog(updateUser.getId(), updateUser.getUserName(), UserCfg.PC, content, UserCfg.REGISTER_SUCCESS, userType, siteDTO.getId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
                return RPCResult.success(sysId);
            }
            return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "新增管理员失败");
        }
    }

    /**
     * 初始化站点时初始化参数
     *
     * @param siteId
     * @param siteCode
     * @param createBy
     * @return
     */
    @Override
    public ApiResult initSiteParam(Long siteId, String siteCode, String siteTitle, Long userId, String createBy) {
        Date nowDate = new Date();
        //初始化站点部门
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        Long deptId = IdWorker.getId();
        sysDeptEntity.setId(deptId);
        sysDeptEntity.setDeptName(UserCfg.DEFAULT_SYS_DEFT_NAME);
        sysDeptEntity.setSiteId(siteId);
        sysDeptEntity.setCreateBy(createBy);
        sysDeptEntity.setCreateTime(nowDate);
        sysDeptEntity.setUpdateBy(createBy);
        sysDeptEntity.setUpdateTime(nowDate);
        sysDeptEntity.setIsDel(UserConstant.IS_F);
        sysDeptEntity.setSiteCode(siteCode);
        int deptInsert = sysDeptDao.insert(sysDeptEntity);
        //初始化站点管理员
        String sysUserName = SysUserName.generatorName(UserCfg.SITE_USER);
        String existUserName = sysUserDao.confirmExistUserName(sysUserName);
        if (StrUtil.isNotBlank(existUserName)) {
            throw new UserException(UserCodeEnum.USER_INFORMATION_EXIST.getCode(), UserCodeEnum.USER_INFORMATION_EXIST.getMessage());
        }
        SysUserEntity sysUser = new SysUserEntity();
        Long sysUserId = IdWorker.getId();
        sysUser.setSiteId(siteId);
        sysUser.setDeptId(deptId);
        sysUser.setRemark(siteTitle);
        sysUser.setId(sysUserId);
        sysUser.setLoginCount(UserConstant.IS_ZERO);
        sysUser.setIsEnable(UserConstant.IS_T);
        sysUser.setUserName(sysUserName);
        sysUser.setCreateTime(nowDate);
        sysUser.setCreateBy(createBy);
        sysUser.setUpdateBy(createBy);
        sysUser.setUpdateTime(nowDate);
        sysUser.setPassword(SecureUtil.md5(UserCfg.SYS_USER_PWD));
        sysUser.setIsDel(UserConstant.IS_F);
        sysUser.setKey("F");
        sysUser.setSiteCode(siteCode);
        int sysUserInsert = sysUserDao.insert(sysUser);
        //初始化会员等级积分
        if (userRankDao.findDefaultRank(siteId) != null) {
            throw new UserException(UserCodeEnum.RANK_EXIST_CODE.getCode(), UserCodeEnum.RANK_EXIST_CODE.getMessage());
        }
        UserRankEntity userRankEntity = new UserRankEntity();
        userRankEntity.setId(IdWorker.getId());
        userRankEntity.setSiteCode(siteCode);
        userRankEntity.setIsDel(UserConstant.IS_F);
        userRankEntity.setSiteId(siteId);
        userRankEntity.setCreateBy(createBy);
        userRankEntity.setUpdateBy(createBy);
        userRankEntity.setCreateTime(nowDate);
        userRankEntity.setUpdateTime(nowDate);
        userRankEntity.setRankLevel(0);
        userRankEntity.setMaxScore(0);
        userRankEntity.setRankName(UserConstant.INIT_RANKNAME);

        List<UserRankScoreEntity> scoreEntityList = new ArrayList<>();
        ApiResult<Map<String, String>> keyValueMapByDictCode = keyValueService.findKeyValueMapByDictCode(UserConstant.SCORE_TYPE);
        if (!RPCResult.checkApiResult(keyValueMapByDictCode)) {
            return RPCResult.custom(keyValueMapByDictCode.getCode(), keyValueMapByDictCode.getMessage());
        }
        for (Map.Entry<String, String> entry : keyValueMapByDictCode.getData().entrySet()) {
            UserRankScoreEntity entity = new UserRankScoreEntity();
            entity.setId(IdWorker.getId());
            entity.setRankId(userRankEntity.getId());
            entity.setScoreName(entry.getValue());
            entity.setScoreCode(entry.getKey());
            entity.setScoreVal(0);
            entity.setIsEnable(UserConstant.IS_F);
            entity.setCreateTime(nowDate);
            entity.setUpdateTime(nowDate);
            entity.setCreateBy(userRankEntity.getCreateBy());
            entity.setUpdateBy(userRankEntity.getCreateBy());
            entity.setIsDel(UserConstant.IS_F);
            scoreEntityList.add(entity);
        }
        int rankInsert = userRankDao.insert(userRankEntity);
        int scoreInsert = userRankScoreDao.addList(scoreEntityList);
        //新增厅主
        String userName = UserCfg.DEFAULT_SYS_USER_NAME;
        if (ObjectUtil.isNotNull(userDao.confirmExistUserName(siteId, userName))) {
            throw new UserException(UserCodeEnum.USER_EXIST.getCode(), UserCodeEnum.USER_EXIST.getMessage());
        }
        UserEntity userEntity = new UserEntity();
        Long id = userId;
        String MD5pwd = SecureUtil.md5(UserCfg.DEFAULT_PWD);
        userEntity.setId(id);
        userEntity.setIsDel(UserConstant.IS_F);
        userEntity.setSiteId(siteId);
        userEntity.setUserName(UserCfg.DEFAULT_SYS_USER_NAME);
        userEntity.setPassword(MD5pwd);
        userEntity.setCreateTime(nowDate);
        userEntity.setUpdateTime(nowDate);
        userEntity.setCreateBy(createBy);
        userEntity.setUpdateBy(createBy);
        userEntity.setIsDemo(UserConstant.IS_F);
        userEntity.setStatus(UserStatus.USER_STATUS_10.getCode());
        userEntity.setUserRankId(userRankEntity.getId());
        userEntity.setRandom(RandomUtil.randomInt(6));
        userEntity.setSiteCode(siteCode);
        //保存用户
        userDao.insert(userEntity);
        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setRealName(Base64.encode(UserCfg.DEFAULT_SYS_USER_REAL_NAME));
        userInfoEntity.setId(id);
        userInfoEntity.setCreateTime(nowDate);
        userInfoEntity.setUpdateTime(nowDate);
        Integer userInsert = userInfoDao.insert(userInfoEntity);
        // 新增厅主代理关系
        Integer integer = userProxyInnerService.initDefault(userId, siteId, siteCode);
        if (integer == 0) {
            throw new UserException(UserCodeEnum.SAVE_PROXY_FAIL.getCode(), UserCodeEnum.SAVE_PROXY_FAIL.getMessage());
        }
        //新增厅主返点数据
        if (userRebateDao.selectById(userEntity.getId()) != null) {
            throw new UserException(UserCodeEnum.USER_EXIST.getCode(), UserCodeEnum.USER_EXIST.getMessage());
        }
        UserRebateEntity userRebateEntity = new UserRebateEntity();
        userRebateEntity.setId(userEntity.getId());
        userRebateEntity.setIsProxy(UserConstant.IS_T);
        // 单位万(百分比) 返点
        userRebateEntity.setGpcRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setFlcRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setTycpRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setTyRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setQtRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setDpcRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setLhcRebate0(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setLhcRebate1(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setLhcRebate2(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setLhcRebate3(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userRebateEntity.setCreateTime(nowDate);
        userRebateEntity.setUpdateTime(nowDate);
        userRebateEntity.setSiteId(siteId);
        userRebateEntity.setSiteCode(siteCode);
        int rebateInsert = userRebateDao.insert(userRebateEntity);

        //初始化站点推广链接
        UserReferEntity userReferEntity = new UserReferEntity();
        userReferEntity.setId(IdWorker.getId());
        userReferEntity.setCreateBy(createBy);
        userReferEntity.setUpdateBy(createBy);
        userReferEntity.setUserId(userEntity.getId());
        userReferEntity.setCreateTime(new Date());
        userReferEntity.setDomainUrl("#");
        String referCode = RandomUtil.randomString(6);//生成六位随机码
        userReferEntity.setReferCode(referCode);
        userReferEntity.setReferUrl("#");//TODO 需要定义
        userReferEntity.setIsProxy(UserConstant.IS_T);
        userReferEntity.setFlcRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setGpcRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setTycpRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setQtRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setTyRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setDpcRebate(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setLhcRebate0(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setLhcRebate1(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setLhcRebate2(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setLhcRebate3(UserConstant.Proxy_Rebate.DEFAULT_REBATE);
        userReferEntity.setUpdateTime(new Date());
        userReferEntity.setSiteId(siteId);
        userReferEntity.setSiteCode(siteCode);
        userReferEntity.setIsDel(UserConstant.IS_F);
        userReferEntity.setIsApp(UserConstant.IS_F);//是否是手机端，1:是，0:否。默认0
        userReferEntity.setIsEnable(UserConstant.IS_T);// 是否是启用状态，1：是，0：否。默认1
        int referInsert = userReferDao.insert(userReferEntity);

        LogUserEntity logUserEntity = new LogUserEntity();
        logUserEntity.setUserId(userReferEntity.getUserId());
        logUserEntity.setSiteId(userReferEntity.getSiteId());
        logUserEntity.setContent("初始化：推广链接，推广码" + userReferEntity.getReferCode() + "。链接：" + userReferEntity.getReferUrl());
        logUserEntity.setType(UserCfg.ADD);
        logUserEntity.setAccountType(UserCfg.SITE);
        logUserEntity.setFlagType(UserCfg.USER_LOG_FLAG_TYPE_OPER);

        if (logService.save(logUserEntity) && deptInsert > 0 && sysUserInsert > 0 && rankInsert > 0 && scoreInsert > 0 && userInsert > 0 && referInsert > 0 && rebateInsert > 0) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("sysUserId", sysUser.getId());
            resultMap.put("userId", sysUser.getId());
            resultMap.put("userName", userEntity.getUserName());
            resultMap.put("userReferId", userReferEntity.getId());
            return RPCResult.success(resultMap);
        } else {
            return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "初始化站点失败");
        }
    }

    @Override
    public ApiResult<SysUserDTO> getSysUserDTOApi(Long siteId, Long userId) {
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("id",userId);
        ew.eq("site_id",siteId);
        SysUserEntity sysUserEntity = getOne(ew);
        if (sysUserEntity == null || UserConstant.IS_T.equals(sysUserEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        SysUserDTO dto = new SysUserDTO();
        BeanUtil.copyProperties(sysUserEntity, dto);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult<SysUserDTO> bindGoogleAuthApi(String userName, Long userId, String googleSecret) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (sysUserEntity == null || UserConstant.IS_T.equals(sysUserEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.OBJECT_NOT_EXIST.getCode());
        }
        if (!"F".equals(sysUserEntity.getKey())) {
            throw new UserException(UserCodeEnum.KEY_EXIST.getCode());
        }
        sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(userId);
        sysUserEntity.setKey(googleSecret);
        sysUserEntity.setUpdateBy(userName);
        sysUserEntity.setUpdateTime(new Date());
        int result = sysUserDao.updateById(sysUserEntity);
        if (result == 0) {
            return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "绑定google验证失败");
        }
        SysUserEntity sysUserEntity1 = sysUserDao.selectById(userId);
        SysUserDTO dto = new SysUserDTO();
        BeanUtil.copyProperties(sysUserEntity1, dto);
        return RPCResult.success(dto);
    }

    @Override
    public ApiResult verifyPwdApi(Long userId, String password) {
        SysUserEntity sysUser = sysUserDao.selectById(userId);
        if (sysUser == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        String md5Pwd = userInnerService.getMD5PwdByRSA(password);
        if (!md5Pwd.equals(sysUser.getPassword())) {
            return RPCResult.custom(UserCodeEnum.USER_NAMEORPWD_ERR.getCode(), "密码错误");
        }
        return RPCResult.success();
    }

    @Override
    public ApiResult<SysUserDTO> getSysUserByNameApi(String userName,String siteCode) {
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("is_del",UserConstant.IS_F);
        ew.eq("user_name",userName);
        ew.eq("site_code", siteCode);
        SysUserEntity sysUser = getOne(ew);
        if (sysUser == null) {
            return RPCResult.custom(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtil.copyProperties(sysUser, sysUserDTO);
        return RPCResult.success(sysUserDTO);
    }

    @Override
    public ApiResult updatePwdApi(UserModelDTO userModelDTO, String ip, String url) {
        String userName = userModelDTO.getUserName();
        Long userId = userModelDTO.getId();
        String oldPwd = userModelDTO.getPassword();
        String newPwd = userModelDTO.getNewPwd();
        Long siteId = userModelDTO.getSiteId();
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (!ObjectUtil.equal(siteId, sysUserEntity.getSiteId())) {
            throw new UserException(UserCodeEnum.SITEID_ERR.getCode());
        }
        if (ObjectUtil.equal(UserConstant.IS_ONE, sysUserEntity.getIsDel()) || ObjectUtil.equal(UserConstant.IS_ZERO, sysUserEntity.getIsEnable())) {
            throw new UserException(UserCodeEnum.USER_UNNORMAL_STATUS.getCode());
        }
        // 获取私钥对，解密
        String MD5OldPwd;
        String MD5NewPwd;
        try {
            MD5OldPwd = userInnerService.getMD5PwdByRSA(oldPwd);
            MD5NewPwd = userInnerService.getMD5PwdByRSA(newPwd);
        } catch (Exception e) {
            logger.error("异常信息:", e);
            throw new UserException(UserCodeEnum.WRONG_PARSE_PWD.getCode(), UserCodeEnum.WRONG_PARSE_PWD.getMessage());
        }
        Assert.isNull(sysUserEntity.getPassword(), "用户密码为空");
        if (!sysUserEntity.getPassword().equals(MD5OldPwd)) {
            throw new UserException(UserCodeEnum.USER_NAMEORPWD_ERR.getCode());
        }
        if (MD5OldPwd.equals(MD5NewPwd)) {
            throw new UserException(UserCodeEnum.SAME_PWD.getCode());
        }
        //修改用戶密碼
        if (Objects.equals(sysUserEntity.getLoginCount(), -1)) {
            sysUserEntity.setLoginCount(UserConstant.IS_ZERO);
        }
        sysUserEntity.setPassword(MD5NewPwd);
        sysUserEntity.setUpdateBy(userName);
        sysUserEntity.setUpdateTime(new Date());
        sysUserDao.updateById(sysUserEntity);
        String content = "更改密码成功";
        logUserInnerService.addUserLog(sysUserEntity.getId(), sysUserEntity.getUserName(), UserCfg.PC, content, UserCfg.UPDATE_PWD, UserCfg.SYS, sysUserEntity.getSiteId(), UserCfg.USER_LOG_FLAG_TYPE_OPER, ip, url);
        return RPCResult.custom(ErrorCode.SUCCESS, "密码修改成功");
    }

    @Override
    public ApiResult enableSysUserApi(Long userId, String updateUserName) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (sysUserEntity == null || sysUserEntity.getIsDel() == 1) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode());
        }
        sysUserEntity.setIsEnable(UserConstant.IS_T);
        sysUserEntity.setUpdateTime(new Date());
        sysUserEntity.setUpdateBy(updateUserName);
        if (sysUserDao.updateById(sysUserEntity) > 0) {
            return RPCResult.success();
        }
        return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "启用管理员失败");
    }

    @Override
    public ApiResult<List<Long>> queryUserIdByUserNameApi(String userName) {
        return RPCResult.success(userDao.queryUserIdByUserNameApi(userName));
    }

    @Override
    public ApiResult<List<SysUserDTO>> querySysUserByIdApi(List<Long> listId, Long siteId) {
        List<SysUserDTO> resultList = new ArrayList<>();
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.in("id", listId);
        ew.eq("site_id", siteId);
        ew.eq("is_del", UserConstant.IS_F);
        List<SysUserEntity> sysUserEntities = sysUserDao.selectList(ew);
        for (SysUserEntity sysUser : sysUserEntities) {
            SysUserDTO sysUserDTO = new SysUserDTO();
            BeanUtil.copyProperties(sysUser, sysUserDTO);
            resultList.add(sysUserDTO);
        }
        return RPCResult.success(resultList);
    }

    @Override
    public ApiResult<SysUserDTO> getSysUserByUserNameApi(String userName) {
        QueryWrapper<SysUserEntity> ew = new QueryWrapper<>();
        ew.eq("user_name",userName);
        ew.eq("is_del",UserConstant.IS_F);
        SysUserEntity sysUser = getOne(ew);
        if (sysUser == null) {
            throw new UserException(UserCodeEnum.USER_NOT_EXIST.getCode(), UserCodeEnum.USER_NOT_EXIST.getMessage());
        }
        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtil.copyProperties(sysUser, sysUserDTO);
        return RPCResult.success(sysUserDTO);
    }


    @Override
    public ApiResult<SysUserDTO> removeGoogleAuthApi(Long userId) {
        SysUserEntity sysUserEntity = sysUserDao.selectById(userId);
        if (sysUserEntity == null || UserConstant.IS_T.equals(sysUserEntity.getIsDel())) {
            throw new UserException(UserCodeEnum.OBJECT_NOT_EXIST.getCode());
        }
        if ("F".equals(sysUserEntity.getKey())) {
            throw new UserException(UserCodeEnum.KEY_NOT_EXIST.getCode());
        }
        String userName = sysUserEntity.getUserName();
        sysUserEntity = new SysUserEntity();
        sysUserEntity.setId(userId);
        sysUserEntity.setKey("F");
        sysUserEntity.setUpdateBy(userName);
        sysUserEntity.setUpdateTime(new Date());
        Integer result = sysUserDao.updateById(sysUserEntity);
        if (result == 0) {
            return RPCResult.custom(UserCodeEnum.METHOD_FAIL.getCode(), "删除google验证失败");
        }
        SysUserEntity sysUserEntity1 = sysUserDao.selectById(userId);
        SysUserDTO dto = new SysUserDTO();
        BeanUtil.copyProperties(sysUserEntity1, dto);
        return RPCResult.success(dto);
    }
}
