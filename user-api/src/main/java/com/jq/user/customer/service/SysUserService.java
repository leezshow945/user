package com.jq.user.customer.service;

import com.jq.user.customer.dto.*;
import com.jq.user.customer.fallbackfactory.SysUserServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: Brady
 * Description: 管理员操作相关service
 * Date: 2018/4/26
 */
@FeignClient(value = "user",path = "/inner/user/sysUser",url = "${feign-url.user:}",fallbackFactory = SysUserServiceFallbackFactory.class)
public interface SysUserService {
    /**
     * Author: Brady
     * Description:根据userId获得userName
     * Return: String userName
     * Date: 2018/5/2
     */
    @GetMapping(value = "getUserNameById")
    ApiResult<String> getUserNameById(@RequestParam  Long id);

    /**
     * Author: Brady
     * Description: 根据deptId获得部门员工列表
     * Param:deptId,分页参数（可无）
     * Return:PageInfo<SysUserDTO>
     * Date: 2018/5/9
     */
    @PostMapping(value = "getSysUsersByDeptId")
    ApiResult<PageInfo<SysUserDTO>> getSysUsersByDeptId(@RequestBody SysUserDTO sysUserDTO);

    /**
     * Author: Brady
     * Description: 根据userId判断是会员还是管理员
     * Return: ResultMap{userRole::1-会员 0-管理员  siteId：siteId}
     * Date: 2018/5/9
     */
    @GetMapping(value = "judgeUserRole")
    ApiResult<Map<String, Long>> judgeUserRole(@RequestParam  Long userId);

    /**
     * Author: Brady
     * Description: RPC管理员登录
     * Param:userModelDTO {userName,password,siteId,verifyCode 验证码,platformType平台类型}
     * Return:UserDTO
     * Date: 2018/5/18
     */
    @PutMapping(value = "loginSubmitApi")
    ApiResult<SysUserDTO> loginSubmitApi(@RequestBody UserModelDTO userModelDTO, @RequestParam String ip,
                                         @RequestParam String url, @RequestParam String ipAttribution);

    /**
     * Author: Brady
     * Description: RPC查询试玩账户分页信息
     * Param：UserDemoReqDTO(字段均可为空), siteId
     * Return:PageInfo<UserDemoReqDTO>
     * Date: 2018/5/22
     */
    @PostMapping(value = "queryDemoUserPageApi")
    ApiResult<PageInfo<UserDemoReqDTO>> queryDemoUserPageApi(@RequestBody UserDemoReqDTO userDemoReqDTO, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: PRC查询测试账户分页信息
     * Param：PapeParam, UserDemoReqDTO(字段均可为空), siteId
     * Return:PageInfo<UserDemoReqDTO>
     * Date: 2018/5/22
     */
    @PostMapping(value = "queryTestUserPageApi")
    ApiResult<PageInfo<QueryTestUserDTO>> queryTestUserPageApi(@RequestBody TestUserDTO testUserDTO, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: RPC删除会员
     * Return:success,fail
     * Date: 2018/5/22
     */
    @DeleteMapping(value = "deleteUserByIdApi")
    ApiResult deleteUserByIdApi(@RequestParam Long userId);

    /**
     * Author: Brady
     * Description: RPC查询当日注册会员
     * Param:仅需分业参数，可无
     * Return:PageInfo<UserDTO>
     * Date: 2018/5/22
     */
    @PostMapping(value = "queryRegisterUserApi")
    ApiResult<PageInfo<RegisterUserDTO>> queryRegisterUserApi(@RequestBody UserDTO userDTO);

    /**
     * Author: Brady
     * Description: RPC查询管理员列表
     * Param:QuerySysUserDTO(email，mobile，birthday，siteId;字段无查询为空，其他字段可无), Long siteId
     * return:PageInfo<SysUserDTO>
     * Date: 2018/5/23
     */
    @PostMapping(value = "querySysUserListApi")
    ApiResult<PageInfo<SysUserDTO>> querySysUserListApi(@RequestBody QuerySysUserDTO querySysUserDTO);

    /**
     * Author: Brady
     * Description: RPC确认用户名是否存在
     * Return: success,fail
     * Date: 2018/5/23
     */
    @GetMapping(value = "confirmExistUserNameApi")
    ApiResult<Boolean> confirmExistUserNameApi(@RequestParam String userName);

    /**
     * Author: Brady
     * Description: RPC修改用户基本信息
     * Param: SysUserUpdateInfoDTO{id必须，其它可为空} String updateUserName 修改人的登录名
     * Return:success,fail
     * Date: 2018/5/23
     */
    @PutMapping(value = "updateSysUserApi")
    ApiResult updateSysUserApi(@RequestBody SysUserUpdateInfoDTO sysUserUpdateInfoDTO, @RequestParam String updateUserName);

    /**
     * Author: Brady
     * Description: RPC删除管理员
     * Return: success,fail
     * Date: 2018/5/23
     */
    @DeleteMapping(value = "deleteSysUserApi")
    ApiResult deleteSysUserApi(@RequestParam Long userId, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: RPC禁用管理员
     * Param:String updateUserName 修改人的登录名
     * Return: success,fail
     * Date: 2018/5/23
     */
    @PutMapping(value = "sysUserDisabledApi")
    ApiResult sysUserDisabledApi(@RequestParam Long userId, @RequestParam String updateUserName);

    /**
     * Author: Brady
     * Description: RPC重置管理员密码
     * Return： success,fail
     * Date: 2018/5/23
     */
    @GetMapping(value = "resetSysUserPwdApi")
    ApiResult resetSysUserPwdApi(@RequestParam Long userId, @RequestParam String updateUserName, @RequestParam String ip, @RequestParam String url);

    /**
     * Author: lee
     * Description: 新增管理员
     * Return： success,fail
     * Date: 2018/5/23
     */
    @PostMapping(value = "addSysUserApi")
    ApiResult addSysUserApi(@RequestBody SysUserUpdateInfoDTO sysUserInfo, @RequestParam String updateName,
                            @RequestParam String ip,@RequestParam String url);

    /**
     * Author: lee
     * Description: 初始化站点时初始化参数(等级 默认管理员 厅主 推广链接 部门)
     * Param: CreatBy操作用户名 SiteId站点id SiteCode站点code SiteTitle站点标题
     * Return: sysUserId,userName,userReferId,userId
     * Date: 2018/5/23
     */
    @GetMapping(value = "initSiteParam")
    ApiResult<Map<String, Object>> initSiteParam(@RequestParam Long siteId, @RequestParam String siteCode,
                                                 @RequestParam String siteTitle,@RequestParam Long userId,
                                                 @RequestParam String createBy);

    /**
     * @Author: levi
     * @Descript: 根据用户id获取管理员对象
     * @Date: 2018/6/1
     */
    @GetMapping(value = "getSysUserDTOApi")
    ApiResult<SysUserDTO> getSysUserDTOApi(@RequestParam Long siteId, @RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript: 管理员绑定谷歌验证器
     * @Date: 2018/6/5
     */
    @PutMapping(value = "bindGoogleAuthApi")
    ApiResult<SysUserDTO> bindGoogleAuthApi(@RequestParam String userName, @RequestParam Long userId, @RequestParam String googleSecret);

    /**
     * @Author: levi
     * @Descript: 管理员解绑谷歌验证器
     * @Date: 2018/6/5
     */
    @DeleteMapping(value = "removeGoogleAuthApi")
    ApiResult<SysUserDTO> removeGoogleAuthApi(@RequestParam Long userId);

    /**
     * Author: Brady
     * Description: 验证密码
     * Date: 2018/6/5
     */
    @GetMapping(value = "verifyPwdApi")
    ApiResult verifyPwdApi(@RequestParam Long userId, @RequestParam String password);

    /**
     * Author: Brady
     * Description: 根据管理员名称获得管理员对象
     * Return:SysUserDTO
     * Date: 2018/6/5
     */
    @GetMapping(value = "getSysUserByNameApi")
    ApiResult<SysUserDTO> getSysUserByNameApi(@RequestParam String userName,@RequestParam String siteCode);

    /**
     * Author: Brady
     * Description: 修改管理员密码
     * Param: userName 操作人姓名, userId 管理员userId, oldPwd 旧密码, newPwd 新密码, siteId
     * Date: 2018/6/5
     */
    @PutMapping(value = "updatePwdApi")
    ApiResult updatePwdApi(@RequestBody UserModelDTO userModelDTO, @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description: RPC启用管理员
     * Date: 2018/6/6
     */
    @PutMapping(value = "enableSysUserApi")
    ApiResult enableSysUserApi(@RequestParam Long userId, @RequestParam String updateUserName);

    /**
     * Author: Brady
     * Description: 根据用户名获取用户id
     * Return: List<Long>
     * Date: 2018/6/11
     */
    @GetMapping(value = "queryUserIdByUserNameApi")
    ApiResult<List<Long>> queryUserIdByUserNameApi(@RequestParam String userName);

    /**
     * Author: Brady
     * Description: 根据id集合查询用户
     * Date: 2018/6/19
     */
    @GetMapping(value = "querySysUserByIdApi")
    ApiResult<List<SysUserDTO>> querySysUserByIdApi(@RequestParam List<Long> listId, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 根据用户名查询管理员信息
     * Date: 2018/7/7
     */
    @GetMapping(value = "getSysUserByUserNameApi")
    ApiResult<SysUserDTO> getSysUserByUserNameApi(@RequestParam String userName);
}
