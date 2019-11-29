package com.jq.user.customer.controller;

import com.jq.user.customer.dto.*;
import com.jq.user.customer.service.UserRebateInnerService;
import com.jq.user.customer.service.UserRebateService;
import com.jq.user.customer.service.UserRebateTransService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/inner/user/rebate")
public class UserRebateController implements UserRebateService {

    @Resource
    private UserRebateInnerService userRebateInnerService;
    @Resource
    private UserRebateTransService userRebateTransService;


    @Override
    public ApiResult<UserRebateDTO> getRebateByUserId(@RequestParam Long userId) {
        return userRebateInnerService.getRebateByUserId(userId);
    }

    @Override
    public ApiResult<List<Long>> getProxyByUserId(@RequestParam Long userId, @RequestParam Integer type) {
        return userRebateInnerService.getProxyByUserId(userId, type);
    }

    @Override
    public ApiResult<PageInfo<UserRebateDTO>> qryUserByPageApi(@RequestBody QueryParamDTO dto) {
        return userRebateInnerService.qryUserByPageApi(dto);
    }

    @Override
    public ApiResult<UserDTO> addUserApi(@RequestBody AddUserDTO dto) {
        return userRebateTransService.addUserApi(dto);
    }

    @Override
    public ApiResult addUserApi(@RequestBody TestUserDTO dto, @RequestParam Long siteId, @RequestParam String updateUserName, @RequestParam String ip) {
        return userRebateTransService.addUserApi(dto, siteId, updateUserName, ip);
    }

    @Override
    public ApiResult<Boolean> updateBaseInfoApi(@RequestBody UserRebateUpdateDTO dto) {
        return userRebateTransService.updateBaseInfoApi(dto);
    }

    @Override
    public ApiResult<Boolean> updateBaseInfoApi(@RequestBody UpdateTestUserDTO dto, @RequestParam Long siteId) {
        return userRebateTransService.updateBaseInfoApi(dto, siteId);
    }

    @Override
    public ApiResult<PageInfo<UserRebateDTO>> getSubUserListApi(@RequestBody QueryParamDTO dto) {
        return userRebateInnerService.getSubUserListApi(dto);
    }

    @Override
    public ApiResult<UserDTO> getLevelAndRankApi(@RequestParam Long siteId) {
        return userRebateInnerService.getLevelAndRankApi(siteId);
    }

    @Override
    public ApiResult<String> getPathByApi(@RequestParam Long userId) {
        return userRebateInnerService.getPathByApi(userId);
    }

    @Override
    public ApiResult<List<UserDTO>> getUserLevelPathApi(@RequestParam String userName, @RequestParam Long siteId) {
        return userRebateInnerService.getUserLevelPathApi(userName, siteId);
    }

    @Override
    public ApiResult<Map<String, Object>> getRebateByUserNameAndSiteIdApi(@RequestParam String userName, @RequestParam Long siteId) {
        return userRebateInnerService.getRebateByUserNameAndSiteIdApi(userName, siteId);
    }

    @Override
    public ApiResult<Map<String, Object>> getUserRebateByUserNameAndSiteIdApi(@RequestParam String userName, @RequestParam Long siteId) {
        return userRebateInnerService.getUserRebateByUserNameAndSiteIdApi(userName, siteId);
    }

    @Override
    public ApiResult<UserRebateDTO> getUserInfoAndPathApi(@RequestParam String userName, @RequestParam Long siteId) {
        return userRebateInnerService.getUserInfoAndPathApi(userName, siteId);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> getAllLevelBySiteIdApi(@RequestParam Long siteId) {
        return userRebateInnerService.getAllLevelBySiteIdApi(siteId);
    }

    @Override
    public ApiResult<List<UserDTO>> getUserType(@RequestParam List<Long> ids, @RequestParam Long siteId) {
        return userRebateInnerService.getUserType(ids, siteId);
    }

    @Override
    public ApiResult<List<UserRebateDTO>> queryUserInfoAndPathByIdApi(@RequestParam List<Long> idList, @RequestParam Long siteId) {
        return userRebateInnerService.queryUserInfoAndPathByIdApi(idList, siteId);
    }

    @Override
    public ApiResult<List<UserRebateDTO>> queryUserInfoAndPathByIdApi(@RequestParam List<Long> idList, @RequestParam Long siteId, @RequestParam(value = "isProxy",required = false) Integer isProxy) {
        return userRebateInnerService.queryUserInfoAndPathByIdApi(idList, siteId, isProxy);
    }

    @Override
    public ApiResult<Map<String, Object>> initUpdateApi(@RequestParam Long userId, @RequestParam Long siteId) {
        return userRebateInnerService.initUpdateApi(userId, siteId);
    }

    @Override
    public ApiResult<UserDTO> getUserFundByUserIdAndSiteIdApi(@RequestParam Long userId, @RequestParam Long siteId) {
        return userRebateInnerService.getUserFundByUserIdAndSiteIdApi(userId, siteId);
    }

    @Override
    public ApiResult<Boolean> transferBatchApi(@RequestParam Long destId, @RequestParam String destName, @RequestParam String userIds, @RequestParam String managerUserName) {
        return userRebateTransService.transferBatchApi(destId, destName, userIds, managerUserName);
    }

    @Override
    public ApiResult<Boolean> transferApi(@RequestParam Long destId, @RequestParam Long sourceId,
                                          @RequestParam String destName, @RequestParam String sourceName,
                                          @RequestParam Integer isTransfer, @RequestParam String managerUserName) {
        return userRebateTransService.transfer(destId, sourceId, destName, sourceName, isTransfer, managerUserName);
    }

    @Override
    public ApiResult<Map<String, Object>> getRelateApi(@RequestParam Long userId) {
        return userRebateInnerService.getRelateApi(userId);
    }

    @Override
    public ApiResult updateRebate(@RequestParam Long destId, @RequestParam List<String> userNameList, @RequestParam Long siteId) {
        return userRebateTransService.updateRebate(destId, userNameList, siteId);
    }

    @Override
    public ApiResult<UserRebateDTO> getUserDetailApi(@RequestParam Long id) {
        return userRebateInnerService.getUserDetailApi(id);
    }

    @Override
    public ApiResult<String> getProxyLine(@RequestParam Long userId) {
        return userRebateInnerService.getProxyLine(userId);
    }

    @Override
    public ApiResult<List<Long>> getEffectUserId(@RequestParam List<Long> userIds, @RequestParam Long siteId) {
        return userRebateInnerService.getEffectUserId(userIds, siteId);
    }

    @Override
    public ApiResult<List<Long>> getDefaultAndDirectUserIdBySiteCode(@RequestParam String siteCode) {
        return userRebateInnerService.getDefaultAndDirectUserIdBySiteCode(siteCode);
    }

    @Override
    public ApiResult<List<UserRebateDTO>> getHighLevelIdAndRank(@RequestParam List<Long> ids) {
        return userRebateInnerService.getHighLevelIdAndRank(ids);
    }

    @Override
    public ApiResult<Map<Long, List<Long>>> getHighLevelProxyByIdList(@RequestParam List<Long> idList) {
        return userRebateInnerService.getHighLevelProxyByIdList(idList);
    }

    @Override
    public ApiResult<Map<Long, Boolean>> getUserIsSubLevel(@RequestParam List<Long> idList) {
        return userRebateInnerService.getUserIsSubLevel(idList);
    }

    @Override
    public ApiResult<Map<Long, List<Long>>> getSubIdListByIdListApi(@RequestParam List<Long> idList) {
        return userRebateInnerService.getSubIdListByIdListApi(idList);
    }

    @Override
    public ApiResult<PageInfo<UserRebateDTO>> getBanRebateUserListApi(@RequestBody QueryParamDTO dto) {
        return userRebateInnerService.getBanRebateUserListApi(dto);
    }

    @Override
    public ApiResult setBanRebateUserApi(@RequestParam String userName, @RequestParam Long siteId) {
        return userRebateInnerService.setBanRebateUserApi(userName, siteId);
    }

    @Override
    public ApiResult deleteBanRebateUserApi(@RequestParam List<Long> idList, @RequestParam Long siteId) {
        return userRebateInnerService.deleteBanRebateUserApi(idList, siteId);
    }

    @Override
    public ApiResult<UserRebateDTO> getUserRebateBy(@RequestParam String userName, @RequestParam Long siteId, @RequestParam Integer isDemo) {
        return userRebateInnerService.getUserRebateBy(userName, siteId, isDemo);
    }

    @Override
    public ApiResult<?> updateUserType(@RequestParam Long userId, @RequestParam String siteCode) {
        return userRebateInnerService.updateUserType(userId, siteCode);
    }

    @Override
    public ApiResult<UserRebateDTO> findByUserIdApi(@RequestParam Long userId) {
        return userRebateInnerService.findByUserIdApi(userId);
    }


}
