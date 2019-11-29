package com.jq.user.customer.service;

import com.jq.user.customer.dto.UserDTO;
import com.jq.user.customer.dto.UserModelDTO;
import com.liying.common.service.ApiResult;

/**
 * UserRelieveService
 * 解除userService类RPC层与业务层
 * @author lxh
 * @date 2018/9/7
 */

public interface UserRelieveService {

    ApiResult<UserDTO> registerUserApi(UserModelDTO userModelDTO) ;

    }
