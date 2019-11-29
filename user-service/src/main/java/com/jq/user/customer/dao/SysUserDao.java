package com.jq.user.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.customer.entity.SysUserEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @author Created by ZhangCong on 2018/4/4.
 */
public interface SysUserDao extends BaseMapper<SysUserEntity> {

    SysUserEntity selectByUserName(@Param("userName") String userName, @Param("siteId") Long siteId);

    String findUserNameById(Long id);

    String confirmExistUserName(@Param("userName") String userName);

    Integer updateSysUser(SysUserEntity sysUser);

    Integer deleteSysUserByUserId(@Param("userId") Long userId, @Param("siteId") Long siteId);

    Long getDefaultSysManId(Long siteId);
}
