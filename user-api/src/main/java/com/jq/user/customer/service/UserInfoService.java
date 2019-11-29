package com.jq.user.customer.service;

import com.jq.user.customer.dto.UserInfoDTO;
import com.jq.user.customer.fallbackfactory.UserInfoServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user",path = "/inner/user/info",url = "${feign-url.user:}",fallbackFactory = UserInfoServiceFallbackFactory.class)
public interface UserInfoService {
    /**
     * @param userId 用户id
     * @return UserInfoDTO
     * @Author: levi
     * @Descript: 查询用户完整资料
     * @Date: 2018/5/18
     */
    @GetMapping(value = "getUserInfoByIdApi")
    ApiResult<UserInfoDTO> getUserInfoByIdApi(@RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 更新会员完整资料
     * @param dto 用户信息
     * @return success or fail
     * @Date: 2018/5/18
     */
    @PutMapping(value = "updateUserInfoApi")
    ApiResult updateUserInfoApi(@RequestBody UserInfoDTO dto);

    /**
     * @Author: levi
     * @Descript: 查看下级会员详情
     * @param userId 用户id
     * @return UserInfoDTO userName=用户名,realName=真实姓名,birthday=生日,qq=qq,
     *             weChat=微信,mobile=手机号,email=电子邮箱,lastLoginIp=最后登录ip
     * @Date: 2018/5/22
     */
    @GetMapping(value = "findUserDetailApi")
    ApiResult<UserInfoDTO> findUserDetailApi(@RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 获取用户基本信息
     * @Date: 2018/6/20
     */
    @GetMapping(value = "getUserInfoApi")
    ApiResult<UserInfoDTO> getUserInfoApi(@RequestParam Long userId);


    /**
     * 更新用户头像
     *
     * @param id
     * @param pictureUrl
     * @return
     */
    @PutMapping(value = "updateUserPicture")
    ApiResult<?> updateUserPicture(@RequestParam Long id,@RequestParam String pictureUrl);

    /**
     * 批量获取用户基本信息
     *
     * @param userNameList
     * @param siteCode
     * @return
     */
    @GetMapping(value = "findUserInfoByUserNamesApi")
    ApiResult<List<UserInfoDTO>> findUserInfoByUserNamesApi(@RequestParam(value = "userNameList",required = false) List<String> userNameList,@RequestParam String siteCode);

    /**
     * @Author: levi
     * @Descript: 根据userIdList，siteId 批量获取玩家相关信息(玩家等级 rankLevel,玩家名 userName,玩家昵称 nickName,玩家头像 photo,玩家 id)
     * @Date: 2019/2/28
     */
    @GetMapping(value = "getUserInfoByIdListApi")
    ApiResult<List<UserInfoDTO>> getUserInfoByIdListApi(@RequestParam List<Long> idList,@RequestParam Long siteId);
}
