package com.jq.user.customer.controller;


import com.jq.user.customer.dto.*;
import com.jq.user.customer.service.SysUserInnerService;
import com.jq.user.customer.service.SysUserService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author: Brady
 * Description: 管理员操作相关controller
 * Date: 2018/4/26
 */
@RestController
@RequestMapping(value = "/inner/user/sysUser")
public class SysUserController implements SysUserService {
    @Resource
    private SysUserInnerService sysUserInnerService;

    @Override
    public ApiResult<String> getUserNameById(@RequestParam  Long id) {
        return sysUserInnerService.getUserNameById(id);
    }

    @Override
    public ApiResult<PageInfo<SysUserDTO>> getSysUsersByDeptId(@RequestBody SysUserDTO sysUserDTO) {
        return sysUserInnerService.getSysUsersByDeptId(sysUserDTO);
    }

    @Override
    public ApiResult<Map<String, Long>> judgeUserRole(@RequestParam  Long userId) {
        return sysUserInnerService.judgeUserRole(userId);
    }

    @Override
    public ApiResult<SysUserDTO> loginSubmitApi(@RequestBody UserModelDTO userModelDTO, @RequestParam  String ip, @RequestParam  String url,
                                                @RequestParam  String ipAttribution) {
        return sysUserInnerService.loginSubmitApi(userModelDTO, ip, url, ipAttribution);
    }

    @Override
    public ApiResult<PageInfo<UserDemoReqDTO>> queryDemoUserPageApi(@RequestBody UserDemoReqDTO userDemoReqDTO, @RequestParam  Long siteId) {
        return sysUserInnerService.queryDemoUserPageApi(userDemoReqDTO, siteId);
    }

    @Override
    public ApiResult<PageInfo<QueryTestUserDTO>> queryTestUserPageApi(@RequestBody TestUserDTO testUserDTO, @RequestParam  Long siteId) {
        return sysUserInnerService.queryTestUserPageApi(testUserDTO, siteId);
    }

    @Override
    public ApiResult deleteUserByIdApi(@RequestParam Long userId) {
        return sysUserInnerService.deleteUserByIdApi(userId);
    }

    @Override
    public ApiResult<PageInfo<RegisterUserDTO>> queryRegisterUserApi(@RequestBody UserDTO userDTO) {
        return sysUserInnerService.queryRegisterUserApi(userDTO);
    }

    @Override
    public ApiResult<PageInfo<SysUserDTO>> querySysUserListApi(@RequestBody QuerySysUserDTO querySysUserDTO) {
        return sysUserInnerService.querySysUserListApi(querySysUserDTO);
    }

    @Override
    public ApiResult<Boolean> confirmExistUserNameApi(@RequestParam String userName) {
        return sysUserInnerService.confirmExistUserNameApi(userName);
    }

    @Override
    public ApiResult updateSysUserApi(@RequestBody SysUserUpdateInfoDTO sysUserUpdateInfoDTO, @RequestParam String updateUserName) {
        return sysUserInnerService.updateSysUserApi(sysUserUpdateInfoDTO, updateUserName);
    }

    @Override
    public ApiResult deleteSysUserApi(@RequestParam Long userId, @RequestParam Long siteId) {
        return sysUserInnerService.deleteSysUserApi(userId, siteId);
    }

    @Override
    public ApiResult sysUserDisabledApi(@RequestParam Long userId, @RequestParam String updateUserName) {
        return sysUserInnerService.sysUserDisabledApi(userId, updateUserName);
    }

    @Override
    public ApiResult resetSysUserPwdApi(@RequestParam Long userId, @RequestParam String updateUserName,
                                        @RequestParam String ip, @RequestParam String url) {
        return sysUserInnerService.resetSysUserPwdApi(userId, updateUserName, ip, url);
    }

    @Override
    public ApiResult addSysUserApi(@RequestBody SysUserUpdateInfoDTO sysUserInfo, @RequestParam  String updateName,
                                   @RequestParam String ip, @RequestParam String url) {
        return sysUserInnerService.addSysUserApi(sysUserInfo, updateName, ip, url);
    }

    @Override
    public ApiResult<Map<String, Object>> initSiteParam(@RequestParam Long siteId, @RequestParam String siteCode,
                                                        @RequestParam String siteTitle, @RequestParam Long userId,
                                                        @RequestParam String createBy) {
        return sysUserInnerService.initSiteParam(siteId, siteCode, siteTitle, userId, createBy);
    }

    @Override
    public ApiResult<SysUserDTO> getSysUserDTOApi(@RequestParam Long siteId, @RequestParam Long userId) {
        return sysUserInnerService.getSysUserDTOApi(siteId, userId);
    }

    @Override
    public ApiResult<SysUserDTO> bindGoogleAuthApi(@RequestParam String userName, @RequestParam Long userId,
                                                   @RequestParam String googleSecret) {
        return sysUserInnerService.bindGoogleAuthApi(userName, userId, googleSecret);
    }

    @Override
    public ApiResult<SysUserDTO> removeGoogleAuthApi(@RequestParam Long userId) {
        return sysUserInnerService.removeGoogleAuthApi(userId);
    }

    @Override
    public ApiResult verifyPwdApi(@RequestParam Long userId, @RequestParam String password) {
        return sysUserInnerService.verifyPwdApi(userId, password);
    }

    @Override
    public ApiResult<SysUserDTO> getSysUserByNameApi(@RequestParam String userName,@RequestParam String siteCode) {
        return sysUserInnerService.getSysUserByNameApi(userName,siteCode);
    }

    @Override
    public ApiResult updatePwdApi(@RequestBody UserModelDTO userModelDTO, @RequestParam String ip, @RequestParam String url) {
        return sysUserInnerService.updatePwdApi(userModelDTO, ip, url);
    }

    @Override
    public ApiResult enableSysUserApi(@RequestParam Long userId, @RequestParam String updateUserName) {
        return sysUserInnerService.enableSysUserApi(userId, updateUserName);
    }

    @Override
    public ApiResult<List<Long>> queryUserIdByUserNameApi(@RequestParam String userName) {
        return sysUserInnerService.queryUserIdByUserNameApi(userName);
    }

    @Override
    public ApiResult<List<SysUserDTO>> querySysUserByIdApi(@RequestParam List<Long> listId, @RequestParam Long siteId) {
        return sysUserInnerService.querySysUserByIdApi(listId, siteId);
    }

    @Override
    public ApiResult<SysUserDTO> getSysUserByUserNameApi(@RequestParam String userName) {
        return sysUserInnerService.getSysUserByUserNameApi(userName);
    }

    /**
     * 更新管理员秘钥（临时方法.....)
     *
     * @return
     */
//    @PostMapping(value = "batchSysUser")
//    public ApiResult<?> batchSysUser() {
//
//       return new ApiResult<>(sysUserInnerService.batchUpdateSysUser());
//    }
}

