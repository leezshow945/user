package com.jq.user.customer.service;

import com.jq.user.customer.dto.SysDeptDTO;
import com.jq.user.customer.dto.SysUserDTO;
import com.jq.user.customer.fallbackfactory.DepartmentServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/14
 */
@FeignClient(value = "user",path = "/inner/user/department",url = "${feign-url.user:}",fallbackFactory = DepartmentServiceFallbackFactory.class)
public interface DepartmentService {

    /**
     * Author: Brady
     * Description: RPC新增管理员部门
     * Param:SysDeptDTO
     * Return:success,fail
     * Date: 2018/5/21
     */
    @PostMapping(value = "addSysDeptApi")
    ApiResult addSysDeptApi(@RequestBody SysDeptDTO sysDeptDTO, @RequestParam String updateUserName,
                            @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description: RPC修改管理员部门
     * Date: 2018/5/21
     */
    @PutMapping(value = "updateSysDeptApi")
    ApiResult updateSysDeptApi(@RequestBody SysDeptDTO sysDeptDTO,@RequestParam String updateUserName,
                               @RequestParam Long siteId, @RequestParam String ip,  @RequestParam String url);

    /**
     * Author: Brady
     * Description: RPC禁用管理员部门
     * Date: 2018/5/22
     */
    @PutMapping(value = "disabledSysDeptApi")
    ApiResult disabledSysDeptApi(@RequestParam Long deptId, @RequestParam String updateUserName,
                                 @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description:PRC删除管理员部门
     * Date: 2018/5/22
     */
    @DeleteMapping(value = "deleteSysDeptApi")
    ApiResult deleteSysDeptApi(@RequestParam Long deptId, @RequestParam String updateUserName,
                               @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description:RPC启用管理员部门
     * Date: 2018/5/31
     */
    @PutMapping(value = "enableSysDeptApi")
    ApiResult enableSysDeptApi(@RequestParam Long deptId, @RequestParam String updateUserName,
                               @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description: RPC查询管理员部门
     * Param：SysDeptDTO（siteId(非必需),deptName(非必需),contact(非必需),分页参数（非必需））
     * Return:PageInfo<SysDeptDTO>
     * Date: 2018/6/4
     */
    @PostMapping(value = "querySysDeptApi")
    ApiResult<PageInfo<SysDeptDTO>> querySysDeptApi(@RequestBody SysDeptDTO sysDeptDTO);

    /**
     * Author: Brady
     * Description: 根据siteId获得管理员部门列表
     * Date: 2018/6/22
     */
    @GetMapping(value = "querySysDeptListApi")
    ApiResult<List<SysDeptDTO>> querySysDeptListApi(@RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 根据id查询管理员部门信息
     * Param:Long deptId
     * Return:SysDeptDTO
     * Date: 2018/6/8
     */
    @GetMapping(value = "getSysDeptApi")
    ApiResult<SysDeptDTO> getSysDeptApi(@RequestParam Long deptId, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 判断部门名是否可用
     * Retuen:success可用，fail不可用
     * Date: 2018/6/22
     */
    @GetMapping(value = "verifySysDeptNameApi")
    ApiResult verifySysDeptNameApi(@RequestParam Long siteId, @RequestParam String sysDeptName);

    /**
     * Author: Brady
     * Description: 根据部门id获取管理员信息
     * Date: 2018/6/23
     */
    @GetMapping(value = "querySysUserByDeptIdApi")
    ApiResult<List<SysUserDTO>> querySysUserByDeptIdApi(@RequestParam Long deptId);

}
