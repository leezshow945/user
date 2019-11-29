package com.jq.user.customer.controller;

import cn.hutool.json.JSONUtil;
import com.liying.common.service.ApiResult;
import com.jq.user.customer.entity.SysDeptEntity;
import com.jq.user.customer.service.DepartmentInnerService;
import com.liying.common.service.RPCResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/14
 */

@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Resource
    private DepartmentInnerService departmentInnerService;

    /**
     * Author: Brady
     * Description: 禁用管理员部门
     * Date: 2018/5/14
     */
    @PutMapping("disable/{id}")
    public ApiResult<?> disableSysDept(@PathVariable("id") Long id) {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(id);
        Boolean flag = departmentInnerService.disableSysDept(sysDeptEntity);
        return flag ? RPCResult.success():RPCResult.fail();
    }

    /**
     * Author: Brady
     * Description: 启用管理员部门
     * Date: 2018/5/31
     */
    @PutMapping("enable/{id}")
    public ApiResult<?> enableSysDept(@PathVariable("id") Long id,
                                      @RequestHeader String tokeninfo) {
        String userName = JSONUtil.parseObj(tokeninfo).getStr("userName");
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(id);
        sysDeptEntity.setUpdateBy(userName);
        Boolean flag = departmentInnerService.enableSysDept(sysDeptEntity);
        return flag ? RPCResult.success():RPCResult.fail();
    }
}
