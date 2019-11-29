package com.jq.user.customer.service;


import com.jq.user.common.InitParameters;
import com.jq.user.customer.dto.*;
import com.jq.user.customer.fallbackfactory.UserServiceFallbackFactory;
import com.jq.user.log.dto.LogUserDTO;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: Brady
 * Description: user相关操作service
 * Date: 2018/4/26
 */
@FeignClient(value = "user", path = "/inner/user", url = "${feign-url.user:}", fallbackFactory = UserServiceFallbackFactory.class)
public interface UserService {
    /**
     * 根据用户id获取用登录名
     *
     * @return String:userName
     */
    @GetMapping(value = "getUserNameById")
    ApiResult<String> getUserNameById(@RequestParam Long id);

    /**
     * 根据用户名获取用用户id
     *
     * @return String:userName
     */
    @GetMapping(value = "getIdByUserName")
    ApiResult<Long> getIdByUserName(@RequestParam String userName, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 更新最后使用游戏的种类和时间
     * Return: true of false
     * Date: 2018/5/9
     */
    @GetMapping(value = "updateGameCategoryAndTimeApi")
    ApiResult<Boolean> updateGameCategoryAndTimeApi(@RequestParam Long userId, @RequestParam String category);

    /**
     * Author: Brady
     * Description: 根据userId获得用户基本信息
     * Return: UserStatusDTO
     * Date: 2018/5/10
     */
    @GetMapping(value = "getUserDTOById")
    ApiResult<UserDTO> getUserDTOById(@RequestParam Long userId, @RequestParam(value = "siteId",required = false) Long siteId);

    /**
     * Author: Brady
     * Description: 会员登录
     * Param:userModelDTO {userName,password,siteId,verifyCode 验证码,platformType平台类型}
     * Return:UserDTO
     * Date: 2018/5/18
     */
    @PostMapping(value = "loginSubmitApi")
    ApiResult<?> loginSubmitApi(@RequestBody UserModelDTO userModelDTO, @RequestParam String ip,
                                @RequestParam String url, @RequestParam String ipAttribution);

    /**
     * Author: Brady
     * Description: 会员注册
     *
     * @Param:UserModelDTO{ referCode 推广码(非必需)，userName，password，verifyCode 验证码，platformType，siteId，siteCode，payPwd(非必需)}
     * String createByUserName 修改者登录名
     * Return:UserDTO
     * Date: 2018/5/18
     */
    @PostMapping(value = "registerUserApi")
    ApiResult<UserDTO> registerUserApi(@RequestBody UserModelDTO userModelDTO);

    /**
     * Author: Brady
     * Description: 试玩帐户注册
     * Return UserDTO
     * Date: 2018/5/18
     */
    @PostMapping(value = "registerDemoUserApi")
    ApiResult<UserDTO> registerDemoUserApi(@RequestParam String ip, @RequestParam Long siteId, @RequestParam Integer platformType);

    /**
     * Author: Brady
     * Description: 生成验证码
     * Return:verifyCode
     * Date: 2018/5/21
     */
    @GetMapping(value = "createSecurityCodeApi")
    ApiResult<String> createSecurityCodeApi(@RequestParam String sessionUuid);

    /**
     * Author: Brady
     * Description: 获得rsa公钥
     * Date: 2018/5/30
     */
    @GetMapping(value = "getRsaPublicKeyApi")
    ApiResult<String> getRsaPublicKeyApi();

    /**
     * @Author: levi
     * @Descript: 通过站点找出站点内的用户，以及用户相关的等级和上级信息
     * @Date: 2018/6/4
     */
    @GetMapping(value = "getUserLevelBySiteCodeApi")
    ApiResult<List<UserLevelDTO>> getUserLevelBySiteCodeApi(@RequestParam String siteCode);

    /**
     * Author: Brady
     * Description: 账户状态map key:状态值，value:状态名称
     *
     * @return Map <String,String>
     * Date: 2018/6/15
     */
    @GetMapping(value = "getUserStatusMapApi")
    ApiResult<Map<Integer, String>> getUserStatusMapApi();

    /**
     * Author: Brady
     * Description: 获得用户初始化状态
     * Date: 2018/6/15
     */
    @GetMapping(value = "getUserListParamApi")
    ApiResult<InitParameters> getUserListParamApi(@RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 根据userId集合查询用户 isDemo:0 正常会员，1 试玩用户，2 测试用户
     * Date: 2018/6/19
     */
    @GetMapping(value = "queryUserDTOByListIdApi")
    ApiResult<List<UserDTO>> queryUserDTOByListIdApi(@RequestParam List<Long> listId, @RequestParam Long siteId, @RequestParam Integer isDemo);

    /**
     * Author: Brady
     * Description: 根据用户名查询用户
     * Date: 2018/6/19
     */
    @GetMapping(value = "getUserByUserNameApi")
    ApiResult<UserDTO> getUserByUserNameApi(@RequestParam String userName, @RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 修改用户密码
     * Date: 2018/6/20
     */
    @PutMapping(value = "updateUserPwdApi")
    ApiResult updateUserPwdApi(@RequestParam Long userId, @RequestParam String oldPwd, @RequestParam String newPwd,
                               @RequestParam Long siteId, @RequestParam Integer platformType, @RequestParam String token,
                               @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description: 修改支付密码
     * Date: 2018/6/20
     */
    @PutMapping(value = "updateUserPayPwdApi")
    ApiResult updateUserPayPwdApi(@RequestParam Long userId, @RequestParam String oldPwd, @RequestParam String newPwd,
                                  @RequestParam Long siteId, @RequestParam Integer platformType, @RequestParam String ip,
                                  @RequestParam String url);

    /**
     * @Author: levi
     * @Descript: 获取用户基本信息
     * @Date: 2018/6/20
     */
    @GetMapping(value = "getUserBaseInfoApi")
    ApiResult<UserDTO> getUserBaseInfoApi(@RequestParam Long siteId, @RequestParam Long userId);

    /**
     * @Author: levi
     * @Descript:根 据用户名搜索用户（搜索当前登录代理下级用户）
     * 入参：当前登录用户id，用户名
     * 返回：用户名，用户余额，所在层级
     * @Date: 2018/6/21
     */
    @GetMapping(value = "getUserByUserName")
    ApiResult<UserDTO> getUserByUserName(@RequestParam Long userId, @RequestParam String userName);


    /**
     * @Author: levi
     * @Descript: 获取当前登录用户直属下级id
     * @Date: 2018/6/22
     */
    @Deprecated
    @GetMapping(value = "getSubUserIdApi")
    ApiResult<Long> getSubUserIdApi(@RequestParam Long userId, @RequestParam String userName);
    /**
     * Author: Brady
     * Description: 查找代理会员的上级用户名称和切割path参数
     * Param: searchName 搜索账户名称，highLevelName 上级用户名称（当上级用户名称为Null是，默认使用搜索账户名称查询）
     * Return：List<Map<String,Object>> Map<"userName"></>
     * Date: 2018/6/22
     */
    @GetMapping(value = "getHighAccountNameToLevelPathApi")
    ApiResult<List<Map<String, Object>>> getHighAccountNameToLevelPathApi(@RequestParam Long siteId, @RequestParam String searchName,
                                                                          @RequestParam String highLevelName);

    /**
     * Author: Brady
     * Description: 获得站点用户path长度
     */
    @GetMapping(value = "getSiteUserPathLengthApi")
    ApiResult<Map<Integer, Object>> getSiteUserPathLengthApi(@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 获取当前登录用户直属下级id
     * @param userId 当前登录用户id
     * @param userName 用户名
     * @param isLike 是否开启模糊搜索
     * @Date: 2019/4/15
     */
    @GetMapping(value = "getSubUserIdApi1")
    ApiResult<List<Long>> getSubUserIdApi(@RequestParam Long userId, @RequestParam String userName, @RequestParam boolean isLike);
    /**
     * Author: Brady
     * Description: 根据id判断用户是否为测试账户
     * Date: 2018/6/25
     */
    @GetMapping(value = "verifyUserIsDemoByIdApi")
    ApiResult<Boolean> verifyUserIsDemoByIdApi(@RequestParam Long userId);

    /**
     * Author: Brady
     * Description: 根据userName和siteId判断是否为测试账户
     * Date: 2018/6/25
     */
    @GetMapping(value = "verifyUserIsDemoBySiteIdAndUserNameApi")
    ApiResult<Boolean> verifyUserIsDemoByIdApi(@RequestParam Long siteId, @RequestParam String userName);

    /**
     * Author: Brady
     * Description: 判断用户名是否可用
     * Date: 2018/6/25
     */
    @GetMapping(value = "verifyUserNameApi")
    ApiResult<Boolean> verifyUserNameApi(@RequestParam Long siteId, @RequestParam String userName);

    /**
     * @Author: levi
     * @Descript: 获取用户相关资料
     * @Date: 2018/6/25
     */
    @GetMapping(value = "userName")
    ApiResult<UserDTO> getRelateApi(@RequestParam Long userId);

    /**
     * Author: Brady
     * Description: 获得今日注册人数
     * Date: 2018/6/27
     */
    @GetMapping(value = "getTodayRegisterUserCountApi")
    ApiResult<Integer> getTodayRegisterUserCountApi(@RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 查询在线会员的资料
     * Date: 2018/6/28
     */
    @GetMapping(value = "queryUserOnlineApi")
    ApiResult<PageInfo<Map<String, Object>>> queryUserOnlineApi(@RequestParam(value = "userName", required = false) String userName,
                                                                @RequestParam(value = "siteId", required = false) Long siteId,
                                                                @RequestParam int pageNo, @RequestParam int pageSize,
                                                                @RequestParam(value = "idList", required = false) List<Long> idList,
                                                                @RequestParam(value = "type", required = false) String type);

    /**
     * Author: Brady
     * Description:
     * Date: 2018/6/28
     */
    @GetMapping(value = "getOnlineCountApi")
    ApiResult<Integer> getOnlineCountApi(@RequestParam Long siteId, @RequestParam(required = false) Long highUserId);

    /**
     * Author: Brady
     * Description: 踢出在线人员
     * Date: 2018/6/29
     */
    @DeleteMapping(value = "kickOutUserApi")
    ApiResult kickOutUserApi(@RequestParam Long userId);

    /**
     * Author: Brady
     * Description: 修改用户状态
     * Date: 2018/6/29
     */
    @GetMapping(value = "changeStatusApi")
    ApiResult changeStatusApi(@RequestBody SysUserDTO sysUserDTO, @RequestParam Long userId, @RequestParam Integer status,
                              @RequestParam Integer task, @RequestParam String ip, @RequestParam String url);

    /**
     * @Author: levi
     * @Descript: 导出excel表格
     * "注册时间", "账号", "上级代理", "真实姓名", "微信号", "QQ号", "电话号码",
     * "电子邮箱", "余额", "存款次数", "存款总额",
     * "提现次数", "提现总额", "注册IP", "账户状态", "账号类型"
     * @Date: 2018/7/2
     */
    @PostMapping(value = "checkOutUserExcel")
    ApiResult<List<UserDTO>> checkOutUserExcel(@RequestBody QueryParamDTO dto);

    /**
     * @Author: levi
     * @Descript: 设置用户交易密码
     * @Date: 2018/7/4
     */
    @GetMapping(value = "setPayPwd")
    ApiResult<Boolean> setPayPwd(@RequestParam Long userId, @RequestParam String payPwd);

    /**
     * @Author: levi
     * @Descript: 总后台控制用户状态
     * @Date: 2018/7/5
     */
    @GetMapping(value = "enableOrDisEnableUser")
    ApiResult<Boolean> enableOrDisEnableUser(@RequestParam Long userId, @RequestParam Integer status);

    /**
     * @Author: levi
     * @Descript: 总后台清除站点:删除站点所有用户、日志
     * @Date: 2018/7/11
     */
    @DeleteMapping(value = "cleanUserApi")
    ApiResult cleanUserApi(@RequestParam Long siteId);

    /**
     * Author: Brady
     * Description: 停用和冻结某用户时，同时停用和冻结下级用户
     * Date: 2018/7/12
     */
    @GetMapping(value = "logOutFrontUser")
    ApiResult logOutFrontUser(@RequestParam Long id, @RequestParam Integer status);

    /**
     * Author: Brady
     * Description：获得token信息
     * Date: 2018/7/17
     */
    @GetMapping(value = "getTokenApi")
    ApiResult<String> getTokenApi(@RequestParam String token);

    /**
     * Author: Brady
     * Description：根据siteCode获得会员列表
     * Date: 2018/7/23
     */
    @GetMapping(value = "queryUserDTOBySiteCodeApi")
    ApiResult<List<UserDTO>> queryUserDTOBySiteCodeApi(@RequestParam String siteCode);

    /**
     * Author: Brady
     * Description：批量删除用户
     * Date: 2018/7/23
     */
    @DeleteMapping(value = "batchDeleteUserApi")
    ApiResult batchDeleteUserApi(@RequestParam Long siteId, @RequestParam String ids);

    /**
     * Author: Brady
     * Description：根据用户名模糊查询用户信息（siteCode可为空）
     * Date: 2018/7/26
     */
    @GetMapping(value = "queryUserLikeUserNameApi")
    ApiResult<List<UserDTO>> queryUserLikeUserNameApi(@RequestParam String userName, @RequestParam String siteCode);

    /**
     * Author: Brady
     * Description：验证用户支付密码是否正确
     * Param：logType日志类型
     * Date: 2018/7/26
     */
    @GetMapping(value = "verifyPayPwdApi")
    ApiResult verifyPayPwdApi(@RequestParam String logType, @RequestParam Long siteId, @RequestParam Long userId,
                              @RequestParam String updateUserName, @RequestParam String payPwd, @RequestParam Integer platform,
                              @RequestParam String ip, @RequestParam String url);

    /**
     * Author: Brady
     * Description：根据注册时间和注册方式获得注册人数
     * Param：startTime 开始时间，endTime 结束时间 ，siteCode
     * Return：
     * Date: 2018/7/26
     */
    @GetMapping(value = "getCountOfRegisterByTimeAndRegSourceApi")
    ApiResult<Map<String, Integer>> getCountOfRegisterByTimeAndRegSourceApi(@RequestParam String startTime, @RequestParam String endTime,
                                                                            @RequestParam String siteCode, @RequestParam(required = false, value = "idList") List<Long> idList);

    /**
     * Author: Brady
     * Description：根据List<Long>查询会员列表
     * Return：
     * Date: 2018/8/7
     */
    @GetMapping(value = "queryUserByListIdApi")
    ApiResult<List<UserDTO>> queryUserByListIdApi(@RequestParam List<Long> idList);

    /**
     * 获取站点过滤掉idList包含的其他会员id
     *
     * @param idList
     * @return
     */
    @GetMapping(value = "queryFilterIds")
    ApiResult<List<Long>> queryFilterIds(@RequestParam Long siteId, @RequestParam List<Long> idList);

    /**
     * @Author: Brady
     * @Descript: 根据最后登录Ip获取该ip登录的用户信息
     * @Date: 2018/11/6
     */
    @GetMapping(value = "queryUserByIpApi")
    ApiResult<List<UserDTO>> queryUserByIpApi(@RequestParam String ip, @RequestParam String siteCode);

    /**
     * @Author: Brady
     * @Descript:根据用户获得整条代理线的注册情况
     * @param: 只接受id（必需）和分页参数
     * @Date: 2019/1/2
     */
    @GetMapping(value = "queryRegisterUserByProxyLineApi")
    ApiResult<PageInfo<Map<String, Object>>> queryRegisterUserByProxyLineApi(@RequestParam Long id, @RequestParam String startTime,
                                                                             @RequestParam String endTime, @RequestParam int pageNo, @RequestParam int pageSize);

    /**
     * @Author: Brady
     * @Descript: 根据用户状态（会员，试玩，测试）和siteCode获得所有用户id
     * @Param： siteCode, int isDemo(0 会员，1试玩，2测试)
     * @Date: 2019/1/3
     */
    @GetMapping(value = "getIdListBySiteCodeAndIsDemoApi")
    ApiResult<List<Long>> getIdListBySiteCodeAndIsDemoApi(@RequestParam String siteCode, @RequestParam Integer isDemo);

    /**
     * 设置密保问题
     *
     * @param encryptedDTOs: 数量3个
     * @return
     * @author Solming
     */
    @PostMapping(value = "setEncryptedApiResult")
    ApiResult<?> setEncryptedApiResult(@RequestBody List<UserEncryptedDTO> encryptedDTOs);

    /**
     * 验证密保问题
     *
     * @param encryptedDTOs
     * @param type          0 验证全部密保   1验证两个密保
     * @return
     * @author Solming
     */
    @PostMapping(value = "verifyEncryptedApiResult")
    ApiResult<?> verifyEncryptedApiResult(@RequestBody List<UserEncryptedDTO> encryptedDTOs, @RequestParam Integer type,
                                          @RequestParam Long userId, @RequestParam Integer plat, @RequestParam Long siteId,
                                          @RequestParam String loginIp, @RequestParam String loginUrl);

    /**
     * 找回密码时验证用户名是否存在
     *
     * @param userName   用户名
     * @param siteId     站点id
     * @param verifyCode 验证码
     * @param verId      验证码对应
     * @return json 的map: {"isSetPayPwd":true,"isBindEncryted":true,"userId":1024144145814}
     * @author Solming
     */
    @GetMapping(value = "verifyUserName")
    ApiResult<?> verifyUserName(@RequestParam String userName, @RequestParam Long siteId,
                                @RequestParam String verifyCode, @RequestParam String verId);

    /**
     * 经验证之后修改用户的密码
     *
     * @param userId
     * @param password
     * @return
     * @author Solming
     */
    @PutMapping(value = "updatePwdByVerify")
    ApiResult<?> updatePwdByVerify(@RequestParam Long userId, @RequestParam String password);

    /**
     * 修改密保
     *
     * @param encryptedDTOs
     * @param userId
     * @return
     * @author Solming
     */
    @PutMapping(value = "updateEncrypted")
    ApiResult<?> updateEncrypted(@RequestBody List<UserEncryptedDTO> encryptedDTOs, @RequestParam Long userId);

    /**
     * 找回密码-验证支付密码
     *
     * @param password
     * @param dto      日志所需信息
     * @return
     * @author Solming
     */
    @PostMapping(value = "verifyPayPasswordApi")
    ApiResult<?> verifyPayPwdApi(@RequestParam String password, @RequestBody LogUserDTO dto);

    /**
     * 是否绑定密保问题
     *
     * @param userId
     * @return
     * @author Solming
     */
    @GetMapping(value = "isBandEncrypted")
    ApiResult<Boolean> isBandEncrypted(@RequestParam Long userId);

    /**
     * 找回支付密码-修改支付密码
     *
     * @param userId
     * @param payPassword
     * @return
     * @author Solming
     */
    @PutMapping(value = "updatePayPwd")
    ApiResult<Boolean> updatePayPwd(@RequestParam Long userId, @RequestParam String payPassword);

    /**
     * 查询用户的密保问题
     *
     * @param userId
     * @return
     * @author Solming
     */
    @GetMapping(value = "queryEncryptedById")
    ApiResult<List<UserEncryptedDTO>> queryEncryptedById(@RequestParam Long userId);

    /**
     * 是否设置支付密码
     *
     * @param userId
     * @return true  : 已设置
     * @author Solming
     */
    @GetMapping(value = "isSetPayPwdByUserId")
    ApiResult<Boolean> isSetPayPwdByUserId(@RequestParam Long userId, @RequestParam Long siteId);

    /**
     * 验证登录密码是否正确
     *
     * @param password
     * @return
     * @author Solming
     */
    @PostMapping(value = "verifyLoginPwd")
    ApiResult<Boolean> verifyLoginPwd(@RequestParam String password, @RequestBody LogUserDTO dto);


    /**
     * 设置用户是否自动余额转换
     *
     * @param userId
     * @param siteId
     * @param type   0=否 1=是
     * @return
     */
    @PutMapping(value = "updateConvertType")
    ApiResult updateConvertType(@RequestParam Long userId, @RequestParam Long siteId, @RequestParam Integer type);

    /**
     * 解密rsa后的字符串
     *
     * @param text
     * @return
     */
    @GetMapping(value = "getTextByRSA")
    ApiResult<String> getTextByRSA(@RequestParam String text);
}
