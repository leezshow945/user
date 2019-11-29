package com.jq.user.customer.controller;

import com.jq.user.common.InitParameters;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.service.UserInnerService;
import com.jq.user.customer.service.UserService;
import com.jq.user.log.dto.LogUserDTO;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Author: Brady
 * Description: user操作相关controller
 * Date: 2018/4/26
 */
@RestController
@RequestMapping(value = "/inner/user")
public class UserController implements UserService {

    @Resource
    private UserInnerService userInnerService;


    @Override
    public ApiResult<String> getUserNameById(@RequestParam Long id) {
        return userInnerService.getUserNameById(id);
    }

    @Override
    public ApiResult<Long> getIdByUserName(@RequestParam String userName, @RequestParam Long siteId) {
        return userInnerService.getIdByUserName(userName, siteId);
    }

    @Override
    public ApiResult<Boolean> updateGameCategoryAndTimeApi(@RequestParam Long userId, @RequestParam String category) {
        return userInnerService.updateGameCategoryAndTimeApi(userId, category);
    }

    @Override
    public ApiResult<UserDTO> getUserDTOById(@RequestParam Long userId, @RequestParam(value = "siteId",required = false) Long siteId) {
        return userInnerService.getUserDTOById(userId, siteId);
    }

    @Override
    public ApiResult<?> loginSubmitApi(@RequestBody UserModelDTO userModelDTO, @RequestParam String ip, @RequestParam String url,
                                       @RequestParam String ipAttribution) {
        return userInnerService.loginSubmitApi(userModelDTO, ip, url, ipAttribution);
    }

    @Override
    public ApiResult<UserDTO> registerUserApi(@RequestBody UserModelDTO userModelDTO) {
        return userInnerService.registerUserApi(userModelDTO);
    }

    @Override
    public ApiResult<UserDTO> registerDemoUserApi(@RequestParam String ip, @RequestParam Long siteId, @RequestParam Integer platformType) {
        return userInnerService.registerDemoUserApi(ip, siteId, platformType);
    }

    @Override
    public ApiResult<String> createSecurityCodeApi(@RequestParam String sessionUuid) {
        return userInnerService.createSecurityCodeApi(sessionUuid);
    }

    @Override
    public ApiResult<String> getRsaPublicKeyApi() {
        return userInnerService.getRsaPublicKeyApi();
    }

    @Override
    public ApiResult<List<UserLevelDTO>> getUserLevelBySiteCodeApi(@RequestParam String siteCode) {
        return userInnerService.getUserLevelBySiteCodeApi(siteCode);
    }

    @Override
    public ApiResult<Map<Integer, String>> getUserStatusMapApi() {
        return userInnerService.getUserStatusMapApi();
    }

    @Override
    public ApiResult<InitParameters> getUserListParamApi(@RequestParam Long siteId) {
        return userInnerService.getUserListParamApi(siteId);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserDTOByListIdApi(@RequestParam List<Long> listId, @RequestParam Long siteId, @RequestParam Integer isDemo) {
        return userInnerService.queryUserDTOByListIdApi(listId, siteId, isDemo);
    }

    @Override
    public ApiResult<UserDTO> getUserByUserNameApi(@RequestParam String userName, @RequestParam Long siteId) {
        return userInnerService.getUserByUserNameApi(userName, siteId);
    }

    @Override
    public ApiResult updateUserPwdApi(@RequestParam Long userId, @RequestParam String oldPwd, @RequestParam String newPwd,
                                      @RequestParam Long siteId, @RequestParam Integer platformType, @RequestParam String token,
                                      @RequestParam String ip, @RequestParam String url) {
        return userInnerService.updateUserPwdApi(userId, oldPwd, newPwd, siteId, platformType, token, ip, url);
    }

    @Override
    public ApiResult updateUserPayPwdApi(@RequestParam Long userId, @RequestParam String oldPwd, @RequestParam String newPwd,
                                         @RequestParam Long siteId, @RequestParam Integer platformType, @RequestParam String ip, @RequestParam String url) {
        return userInnerService.updateUserPayPwdApi(userId, oldPwd, newPwd, siteId, platformType, ip, url);
    }

    @Override
    public ApiResult<UserDTO> getUserBaseInfoApi(@RequestParam Long siteId, @RequestParam Long userId) {
        return userInnerService.getUserBaseInfoApi(siteId, userId);
    }

    @Override
    public ApiResult<UserDTO> getUserByUserName(@RequestParam Long userId, @RequestParam String userName) {
        return userInnerService.getUserByUserName(userId, userName);
    }

    @Override
    public ApiResult<Long> getSubUserIdApi(Long userId, String userName) {
        return userInnerService.getSubUserIdApi(userId,userName);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> getHighAccountNameToLevelPathApi(@RequestParam Long siteId, @RequestParam String searchName, @RequestParam String highLevelName) {
        return userInnerService.getHighAccountNameToLevelPathApi(siteId, searchName, highLevelName);
    }

    @Override
    public ApiResult<Map<Integer, Object>> getSiteUserPathLengthApi(@RequestParam Long siteId) {
        return userInnerService.getSiteUserPathLengthApi(siteId);
    }

    @Override
    public ApiResult<List<Long>> getSubUserIdApi(@RequestParam Long userId, @RequestParam String userName, @RequestParam boolean isLike) {
        return userInnerService.getSubUserIdApi(userId, userName, isLike);
    }

    @Override
    public ApiResult<Boolean> verifyUserIsDemoByIdApi(@RequestParam Long userId) {
        return userInnerService.verifyUserIsDemoByIdApi(userId);
    }

    @Override
    public ApiResult<Boolean> verifyUserIsDemoByIdApi(@RequestParam Long siteId, @RequestParam String userName) {
        return userInnerService.verifyUserIsDemoByIdApi(siteId, userName);
    }

    @Override
    public ApiResult<Boolean> verifyUserNameApi(@RequestParam Long siteId, @RequestParam String userName) {
        return userInnerService.verifyUserNameApi(siteId, userName);
    }

    @Override
    public ApiResult<UserDTO> getRelateApi(@RequestParam Long userId) {
        return userInnerService.getRelateApi(userId);
    }

    @Override
    public ApiResult<Integer> getTodayRegisterUserCountApi(@RequestParam Long siteId) {
        return userInnerService.getTodayRegisterUserCountApi(siteId);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryUserOnlineApi(@RequestParam(value = "userName", required = false) String userName,
                                                                       @RequestParam(value = "siteId", required = false) Long siteId,
                                                                       @RequestParam int pageNo, @RequestParam int pageSize,
                                                                       @RequestParam(value = "idList", required = false) List<Long> idList,
                                                                       @RequestParam(value = "type", required = false) String type) {
        return userInnerService.queryUserOnlineApi(userName, siteId, pageNo, pageSize, idList, type);
    }

    @Override
    public ApiResult<Integer> getOnlineCountApi(@RequestParam Long siteId, @RequestParam(required = false) Long highUserId) {
        return userInnerService.getOnlineCountApi(siteId, highUserId);
    }

    @Override
    public ApiResult kickOutUserApi(@RequestParam Long userId) {
        return userInnerService.kickOutUserApi(userId);
    }

    @Override
    public ApiResult changeStatusApi(@RequestBody SysUserDTO sysUserDTO, @RequestParam Long userId, @RequestParam Integer status,
                                     @RequestParam Integer task, @RequestParam String ip, @RequestParam String url) {
        return userInnerService.changeStatusApi(sysUserDTO, userId, status, task, ip, url);
    }

    @Override
    public ApiResult<List<UserDTO>> checkOutUserExcel(@RequestBody QueryParamDTO dto) {
        return userInnerService.checkOutUserExcel(dto);
    }

    @Override
    public ApiResult<Boolean> setPayPwd(@RequestParam Long userId, @RequestParam String payPwd) {
        return userInnerService.setPayPwd(userId, payPwd);
    }

    @Override
    public ApiResult<Boolean> enableOrDisEnableUser(@RequestParam Long userId, @RequestParam Integer status) {
        return userInnerService.enableOrDisEnableUser(userId, status);
    }

    @Override
    public ApiResult cleanUserApi(@RequestParam Long siteId) {
        return userInnerService.cleanUserApi(siteId);
    }

    @Override
    public ApiResult logOutFrontUser(@RequestParam Long id, @RequestParam Integer status) {
        return userInnerService.logOutFrontUser(id, status);
    }

    @Override
    public ApiResult<String> getTokenApi(@RequestParam String token) {
        return userInnerService.getTokenApi(token);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserDTOBySiteCodeApi(@RequestParam String siteCode) {
        return userInnerService.queryUserDTOBySiteCodeApi(siteCode);
    }

    @Override
    public ApiResult batchDeleteUserApi(@RequestParam Long siteId, @RequestParam String ids) {
        return userInnerService.batchDeleteUserApi(siteId, ids);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserLikeUserNameApi(@RequestParam String userName, @RequestParam String siteCode) {
        return userInnerService.queryUserLikeUserNameApi(userName, siteCode);
    }

    @Override
    public ApiResult verifyPayPwdApi(@RequestParam String logType, @RequestParam Long siteId, @RequestParam Long userId,
                                     @RequestParam String updateUserName, @RequestParam String payPwd, @RequestParam Integer platform,
                                     @RequestParam String ip, @RequestParam String url) {
        return userInnerService.verifyPayPwdApi(logType, siteId, userId, updateUserName, payPwd, platform, ip, url);
    }

    @Override
    public ApiResult<Map<String, Integer>> getCountOfRegisterByTimeAndRegSourceApi(@RequestParam String startTime, @RequestParam String endTime,
                                                                                   @RequestParam String siteCode, @RequestParam(required = false, value = "idList") List<Long> idList) {
        return userInnerService.getCountOfRegisterByTimeAndRegSourceApi(startTime, endTime, siteCode, idList);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserByListIdApi(@RequestParam List<Long> idList) {
        return userInnerService.queryUserByListIdApi(idList);
    }

    @Override
    public ApiResult<List<Long>> queryFilterIds(@RequestParam Long siteId, @RequestParam List<Long> idList) {
        return userInnerService.queryFilterIds(siteId, idList);
    }

    @Override
    public ApiResult<List<UserDTO>> queryUserByIpApi(@RequestParam String ip, @RequestParam String siteCode) {
        return userInnerService.queryUserByIpApi(ip, siteCode);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryRegisterUserByProxyLineApi(@RequestParam Long id, @RequestParam String startTime, @RequestParam String endTime,
                                                                                    @RequestParam int pageNo, @RequestParam int pageSize) {
        return userInnerService.queryRegisterUserByProxyLineApi(id, startTime, endTime, pageNo, pageSize);
    }

    @Override
    public ApiResult<List<Long>> getIdListBySiteCodeAndIsDemoApi(@RequestParam String siteCode, @RequestParam Integer isDemo) {
        return userInnerService.getIdListBySiteCodeAndIsDemoApi(siteCode, isDemo);
    }

    @Override
    public ApiResult<?> setEncryptedApiResult(@RequestBody List<UserEncryptedDTO> encryptedDTOs) {
        return userInnerService.setEncryptedApiResult(encryptedDTOs);
    }

    @Override
    public ApiResult<?> verifyEncryptedApiResult(@RequestBody List<UserEncryptedDTO> encryptedDTOs, @RequestParam Integer type, @RequestParam Long userId,
                                                 @RequestParam Integer plat, @RequestParam Long siteId, @RequestParam String loginIp, @RequestParam String loginUrl) {
        return userInnerService.verifyEncryptedApiResult(encryptedDTOs, type, userId, plat, siteId, loginIp, loginUrl);
    }

    @Override
    public ApiResult<?> verifyUserName(@RequestParam String userName, @RequestParam Long siteId, @RequestParam String verifyCode, @RequestParam String verId) {
        return userInnerService.verifyUserName(userName, siteId, verifyCode, verId);
    }

    @Override
    public ApiResult<?> updatePwdByVerify(@RequestParam Long userId, @RequestParam String password) {
        return userInnerService.updatePwdByVerify(userId, password);
    }

    @Override
    public ApiResult<?> updateEncrypted(@RequestBody List<UserEncryptedDTO> encryptedDTOs, @RequestParam Long userId) {
        return userInnerService.updateEncrypted(encryptedDTOs, userId);
    }

    @Override
    public ApiResult<?> verifyPayPwdApi(@RequestParam String password, @RequestBody LogUserDTO dto) {
        return userInnerService.verifyPayPwdApi(password, dto);
    }

    @Override
    public ApiResult<Boolean> isBandEncrypted(@RequestParam Long userId) {
        return userInnerService.isBandEncrypted(userId);
    }

    @Override
    public ApiResult<Boolean> updatePayPwd(@RequestParam Long userId, @RequestParam String payPassword) {
        return userInnerService.updatePayPwd(userId, payPassword);
    }

    @Override
    public ApiResult<List<UserEncryptedDTO>> queryEncryptedById(@RequestParam Long userId) {
        return userInnerService.queryEncryptedById(userId);
    }

    @Override
    public ApiResult<Boolean> isSetPayPwdByUserId(@RequestParam Long userId, @RequestParam Long siteId) {
        return userInnerService.isSetPayPwdByUserId(userId, siteId);
    }

    @Override
    public ApiResult<Boolean> verifyLoginPwd(@RequestParam String password, @RequestBody LogUserDTO dto) {
        return userInnerService.verifyLoginPwd(password, dto);
    }

    @Override
    public ApiResult updateConvertType(@RequestParam Long userId, @RequestParam Long siteId, @RequestParam Integer type) {
        return userInnerService.updateConvertType(userId, siteId, type);
    }

    @Override
    public ApiResult<String> getTextByRSA(@RequestParam String text) {
        return userInnerService.getTextByRSA(text);
    }
}
