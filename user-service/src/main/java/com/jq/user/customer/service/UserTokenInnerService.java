package com.jq.user.customer.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.jq.user.customer.entity.UserTokenEntity;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/7/12
 */
public interface UserTokenInnerService {

    boolean remove(Wrapper<UserTokenEntity> wrapper);
}
