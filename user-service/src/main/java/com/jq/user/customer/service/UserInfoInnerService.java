package com.jq.user.customer.service;

import com.jq.user.customer.entity.UserInfoEntity;
import com.jq.user.customer.vo.UserInfoVO;

import java.util.Map;

public interface UserInfoInnerService extends UserInfoService {
    Map<String, Object> findById(Long id);

    boolean updateUserInfo(UserInfoVO vo);

    Map<String, String> findUserDetail(Long userId);

    boolean saveUserInfo(UserInfoEntity userInfoEntity);

}
