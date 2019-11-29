package com.jq.user.customer.service;



import com.jq.user.customer.dto.SysDeptDTO;
import com.jq.user.customer.entity.SysDeptEntity;
import com.liying.common.service.ApiResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/14
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DepartmentServiceTest {
    @Resource
    private DepartmentInnerService departmentInnerService;
    @Resource
    private DepartmentService departmentService;

    /**
     * Author: Brady
     * Description: 测试新增管理员部门
     * Date: 2018/5/14
     */
    @Test
    public void addSysDeptTest() {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        Boolean flag = departmentInnerService.addSysDept(sysDeptEntity);
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 测试新增里员部门
     * Date: 2018/5/14
     */
    @Test
    public void updateSysDeptTest() {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(995945290281119746L);
        sysDeptEntity.setCreateBy("zhangcong");
        Boolean flag = departmentInnerService.updateSysDept(sysDeptEntity);
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 测试禁用管理员部门
     * Date: 2018/5/14
     */
    @Test
    public void disableSysDeptTest() {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(995945290281119746L);
        Boolean flag = departmentInnerService.disableSysDept(sysDeptEntity);
        System.out.println(flag);
    }

    /**
     * Author: Brady
     * Description: 测试删除管理员部门
     * Date: 2018/5/15
     */
    @Test
    public void deleteSysDeptTest() {
        SysDeptEntity sysDeptEntity = new SysDeptEntity();
        sysDeptEntity.setId(995957399844880386L);
        sysDeptEntity.setUpdateBy("zhangcong");
        Boolean flag = departmentInnerService.deleteSysDept(sysDeptEntity);
        System.out.println(flag);
    }


    /**
     * Author: Brady
     * Description: 测试RPC新增管理员部门
     * Date: 2018/5/21
     */
    @Test
    public void addSysDeptApiTest() {
        SysDeptDTO sysDeptDTO = new SysDeptDTO();
        String updateUserName = "brady";
//        ApiResult api = departmentService.addSysDeptApi(sysDeptDTO, updateUserName);
//        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试RPC修改管理员部门
     * Date: 2018/5/22
     */
    @Test
    public void updateSysDeptApiTest() {
        SysDeptDTO sysDeptDTO = new SysDeptDTO();
        sysDeptDTO.setId(995957399844880386L);
//        ApiResult api = departmentService.updateSysDeptApi(sysDeptDTO, "zhangcong");
//        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试RPC禁用管理员部门
     * Date: 2018/5/22
     */
    @Test
    public void disableSysDeptApiTest() {
        Long deptId = 995957399844880386L;
//        ApiResult api = departmentService.disabledSysDeptApi(deptId);
//        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试RPC删除管理员部门
     * Date: 2018/5/22
     */
    @Test
    public void deleteSysDeptApi() {
        Long deptId = 995957399844880386L;
        String userName = "brady";
//        ApiResult api = departmentService.deleteSysDeptApi(deptId, userName);
//        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 启用管理员部门
     * Date: 2018/6/4
     */
    @Test
    public void enableSysDeptApiTest() {
        Long deptId = 995957399844880386L;
        String updateName = "cong001";
//        ApiResult api = departmentService.enableSysDeptApi(deptId, updateName);
//        System.out.println(api.getMessage());
//        System.out.println(api.getData());
    }

    /**
     * Author: Brady
     * Description:查询管理员接口
     * Date: 2018/6/4
     */
    @Test
    public void querySydDeptApiTest() {
        SysDeptDTO sysDeptDTO = new SysDeptDTO();
        sysDeptDTO.setContact("47537638");
        ApiResult api = departmentService.querySysDeptApi(sysDeptDTO);
        System.out.println(api.getData());
    }





    /**
     * Author: Brady
     * Description: 测试根据siteId获得管理员部门列表
     * Date: 2018/6/22
     */
    @Test
    public void querySysDeptListApiTest(){
        ApiResult api = departmentService.querySysDeptListApi(1007161902619004929L);
        System.out.println(api.getData().toString());
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 测试判断管理员部门名称是否存在
     * Date: 2018/6/23
     */
    @Test
    public void verifySysDeptNameApiTest(){

        ApiResult api = departmentService.verifySysDeptNameApi(1006467073941540866L,"默认");
        System.out.println(api.getMessage());
    }

    /**
     * Author: Brady
     * Description: 根据管理员部门id查询管理员信息
     * Date: 2018/6/23
     */
    @Test
    public void querySysUserByDeptIdApiTest(){
        ApiResult api = departmentService.querySysUserByDeptIdApi(1005987901163016193L);
        System.out.println(api.getData().toString());
    }
}
