package com.jq.user.customer.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.entity.SysDeptEntity;

import java.util.Map;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/14
 */
public interface DepartmentInnerService extends DepartmentService {
    /**
     * Author: Brady
     * Description: 新增管理员部门
     * Date: 2018/5/14
     */
    Boolean addSysDept(SysDeptEntity sysDeptEntity);

    /**
     * Author: Brady
     * Description: 修改管理员部门
     * Date: 2018/5/14
     */
    Boolean updateSysDept(SysDeptEntity sysDeptEntity);

    /**
     * Author: Brady
     * Description: 禁用管理员部门
     * Date: 2018/5/14
     */
    Boolean disableSysDept(SysDeptEntity sysDeptEntity);

    /**
     * Author: Brady
     * Description: 删除管理员部门
     * Date: 2018/5/15
     */
    Boolean deleteSysDept(SysDeptEntity sysDeptEntity);

    /**
     * Author: Brady
     * Description: 启用管理员部门
     * Date: 2018/5/31
     */
    Boolean enableSysDept(SysDeptEntity sysDeptEntity);

    /**
     * Author: Brady
     * Description: 查询管理员部门
     * Date: 2018/6/4
     */
    Map<String,Object> querySysDept(Page page, Long siteId);

    /**
     * Author: Brady
     * Description: 获取管理员部门
     * Date: 2018/6/5
     */
    SysDeptEntity getSysDept(Long deptId, Long siteId);
}
