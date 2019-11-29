package com.jq.user.customer.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.entity.SysUserEntity;

import java.util.Map;

/**
 * @author Created by on 2018/4/2.
 */

public interface SysUserInnerService extends SysUserService {

    /**
     * Author: Brady
     * Description: 查询管理员列表
     * Date: 2018/5/7
     */
    Map<String, Object> querySysUserList(Page page, String userName, Long siteId, Long deptId);

    /**
     * Author: Brady
     * Description: 确认用户名是否存在
     * Date: 2018/5/7
     */
    Boolean confirmExistUserName(String userName);

    /**
     * Author: Brady
     * Description: 更新用户信息
     * Date: 2018/5/8
     */
    Boolean updateSysUser(SysUserEntity sysUser);

    /**
     * Author: Brady
     * Description: 根据userId查询管理员用户
     * Date: 2018/5/11
     */
    Boolean deleteSysUserByUserId(Long userId,Long siteId);

    /**
     * Author: Brady
     * Description: 禁用管理员用户
     * Date: 2018/5/11
     */
    Boolean sysUserDisabled(Long userId,String userName);

    /**
     * Author: Brady
     * Description: 重置管理员密码
     * Date: 2018/5/11
     */
    Boolean resetSysUserPwd(String userName,Long userId,String ip,String url);

    /**
     * Author: Brady
     * Description: 查询管理员
     * Date: 2018/5/28
     */
    SysUserEntity findSysUser(Long userId, Long siteId);

    /**
     * Author: Brady
     * Description: 修改管理员密码
     * Date: 2018/6/4
     */
    Boolean updateSysPwd(String userName, Long userId, String oldPwd, String newPwd,Long siteId);

    /**
     * Author: Brady
     * Description: 新增管理员
     * Date: 2018/6/5
     */
    Boolean addSysUser(SysUserEntity sysUserEntity);

    /**
     * Author: Brady
     * Description: 获得登录用户的基本信息
     * Date: 2018/6/6
     */
    Map<String,Object> getUserProfile(Long userId);

    /**
     * Author: Brady
     * Description: 修改登录用户的基本信息
     * Date: 2018/6/6
     */
    Boolean updateUserProfile(SysUserEntity sysUser);
}
