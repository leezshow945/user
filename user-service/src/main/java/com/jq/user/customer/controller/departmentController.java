package com.jq.user.customer.controller;

import com.jq.user.customer.dto.SysDeptDTO;
import com.jq.user.customer.dto.SysUserDTO;
import com.jq.user.customer.service.DepartmentInnerService;
import com.jq.user.customer.service.DepartmentService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/6
 */
@RestController
@RequestMapping(value = "/inner/user/department")
public class departmentController implements DepartmentService {

    @Resource
    private DepartmentInnerService departmentInnerService;

    @Override
    public ApiResult addSysDeptApi(@RequestBody SysDeptDTO sysDeptDTO, @RequestParam String updateUserName,
                                   @RequestParam String ip, @RequestParam String url) {
        return departmentInnerService.addSysDeptApi(sysDeptDTO, updateUserName, ip, url);
    }

    @Override
    public ApiResult updateSysDeptApi(@RequestBody SysDeptDTO sysDeptDTO, @RequestParam String updateUserName,
                                      @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url) {
        return departmentInnerService.updateSysDeptApi(sysDeptDTO, updateUserName, siteId, ip, url);
    }

    @Override
    public ApiResult disabledSysDeptApi(@RequestParam Long deptId, @RequestParam String updateUserName,
                                        @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url) {
        return departmentInnerService.disabledSysDeptApi(deptId, updateUserName, siteId, ip, url);
    }

    @Override
    public ApiResult deleteSysDeptApi(@RequestParam Long deptId, @RequestParam String updateUserName,
                                      @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url) {
        return departmentInnerService.deleteSysDeptApi(deptId, updateUserName, siteId, ip, url);
    }

    @Override
    public ApiResult enableSysDeptApi(@RequestParam Long deptId, @RequestParam String updateUserName,
                                      @RequestParam Long siteId, @RequestParam String ip, @RequestParam String url) {
        return departmentInnerService.enableSysDeptApi(deptId, updateUserName, siteId, ip, url);
    }

    @Override
    public ApiResult<PageInfo<SysDeptDTO>> querySysDeptApi(@RequestBody SysDeptDTO sysDeptDTO) {
        return departmentInnerService.querySysDeptApi(sysDeptDTO);
    }

    @Override
    public ApiResult<List<SysDeptDTO>> querySysDeptListApi(@RequestParam Long siteId) {
        return departmentInnerService.querySysDeptListApi(siteId);
    }

    @Override
    public ApiResult<SysDeptDTO> getSysDeptApi(@RequestParam Long deptId, @RequestParam Long siteId) {
        return departmentInnerService.getSysDeptApi(deptId,siteId);
    }

    @Override
    public ApiResult verifySysDeptNameApi(@RequestParam Long siteId, @RequestParam String sysDeptName) {
        return departmentInnerService.verifySysDeptNameApi(siteId, sysDeptName);
    }

    @Override
    public ApiResult<List<SysUserDTO>> querySysUserByDeptIdApi(@RequestParam Long deptId) {
        return departmentInnerService.querySysUserByDeptIdApi(deptId);
    }
}
