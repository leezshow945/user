package com.jq.user.customer.service;

import com.jq.user.customer.dto.*;
import com.jq.user.customer.entity.UserRebateEntity;
import com.liying.common.service.ApiResult;

import java.util.List;

public interface UserRebateTransService {

    /**
     * @Author: levi
     * @Descript: 代理迁移
     * @param destId 目标代理id
     * @param sourceId 源代理id
     * @param destName 目标代理账号
     * @param sourceName 源代理账号
     * @param isTransfer 是否包含源代理
     * @param managerUserName 源代理账号
     * @Date: 2018/6/30
     */
     ApiResult transfer(Long destId, Long sourceId, String destName, String sourceName
            , Integer isTransfer, String managerUserName);

    ApiResult<UserDTO> addUserApi(AddUserDTO dto);

    ApiResult addUserApi(TestUserDTO dto, Long siteId, String updateUserName, String ip);

    ApiResult updateBaseInfoApi(UserRebateUpdateDTO dto);

    ApiResult updateBaseInfoApi(UpdateTestUserDTO dto, Long siteId);

    ApiResult transferBatchApi(Long destId, String destName, String userIds, String managerUserName);

    ApiResult updateRebate(Long destId, List<String> userNameList, Long siteId);

    boolean saveUserRebate(UserRebateEntity vo,Long HighUserId);
}
