package com.jq.user.customer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.user.customer.dao.UserTokenDao;
import com.jq.user.customer.entity.UserTokenEntity;
import com.jq.user.customer.service.UserTokenInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/7/12
 */

@Transactional
@Service
public class UserTokenServiceImpl extends ServiceImpl<UserTokenDao, UserTokenEntity> implements UserTokenInnerService {

}
