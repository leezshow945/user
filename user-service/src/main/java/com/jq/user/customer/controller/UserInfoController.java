package com.jq.user.customer.controller;

import com.jq.user.customer.dto.UserInfoDTO;
import com.jq.user.customer.service.UserInfoInnerService;
import com.jq.user.customer.service.UserInfoService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/inner/user/info")
public class UserInfoController implements UserInfoService {

    @Resource
    private UserInfoInnerService userInfoInnerService;

    @Override
    public ApiResult<UserInfoDTO> getUserInfoByIdApi(@RequestParam Long userId) {
        return userInfoInnerService.getUserInfoByIdApi(userId);
    }

    @Override
    public ApiResult updateUserInfoApi(@RequestBody UserInfoDTO dto) {
        return userInfoInnerService.updateUserInfoApi(dto);
    }

    @Override
    public ApiResult<UserInfoDTO> findUserDetailApi(@RequestParam Long userId) {
        return userInfoInnerService.findUserDetailApi(userId);
    }

    @Override
    public ApiResult<UserInfoDTO> getUserInfoApi(@RequestParam Long userId) {
        return userInfoInnerService.getUserInfoApi(userId);
    }

    @Override
    public ApiResult<?> updateUserPicture(@RequestParam Long id, @RequestParam String pictureUrl) {
        return userInfoInnerService.updateUserPicture(id,pictureUrl);
    }

    @Override
    public ApiResult<List<UserInfoDTO>> findUserInfoByUserNamesApi(@RequestParam List<String> userNameList, @RequestParam String siteCode) {
        return userInfoInnerService.findUserInfoByUserNamesApi(userNameList, siteCode);
    }

    @Override
    public ApiResult<List<UserInfoDTO>> getUserInfoByIdListApi(@RequestParam List<Long> idList,@RequestParam Long siteId) {
        return userInfoInnerService.getUserInfoByIdListApi(idList,siteId);
    }
}
