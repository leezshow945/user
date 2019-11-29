package com.jq.user.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.customer.entity.UserTokenEntity;

import java.util.List;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/28
 */
public interface UserTokenDao extends BaseMapper<UserTokenEntity> {


    void deleteByIdList(List<Long> list);
}
