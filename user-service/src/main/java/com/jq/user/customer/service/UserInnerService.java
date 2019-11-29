package com.jq.user.customer.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jq.user.customer.entity.UserEntity;
import com.jq.user.customer.entity.UserInfoEntity;

import java.util.Map;

public interface UserInnerService extends UserService {

    boolean changeUserStatus(Long id, Integer status);

    /**
     * Author: Brady
     * Description: 生成验证码
     * Date: 2018/4/28
     */
    void createSecurityCode(String currTime);

    /**
     * Author: Brady
     * Description: 获得rsa公钥
     * Date: 2018/4/28
     */
    String getRSAPublicKey();

    /**
     * Author: Brady
     * Description: 加密密码生成，通过MD5\RSA\BASE64后的加密
     * Date: 2018/4/28
     */
    String createPwd(String pwd);

    /**
     * Author: Brady
     * Description: 生成测试用的token（后台测试使用）
     * Date: 2018/4/28
     */
    String loginTest(Long id);

    /**
     * Author: Brady
     * Description: 分页查询试玩帐户
     * Date: 2018/4/28
     */
    Map<String, Object> queryDemoUserPage(Long siteId, Page page, String loginBeginTime, String loginEndTime, String orderBeginTime, String orderEndTime, String gameCode);

    /**
     * Author: Brady
     * Description: 分页查询测试用户
     * Date: 2018/4/28
     */
    Map<String, Object> queryTestUserPage(Page page, Map map);

    /**
     * Author: Brady
     * Description: 根据userId删除帐户
     * Date: 2018/4/28
     */
    Boolean deleteUserByUserId(Long userId);

    /**
     * Author: Brady
     * Description: 初始化支付密码
     * Date: 2018/5/7
     */
    Boolean initUserPayPwd(Long userId, Long siteId, String payPwd);

    /**
     * Author: Brady
     * Description: 确认帐号是否被占用
     * Date: 2018/5/7
     */
    Boolean confirmExistUserName(Long siteId, String userName);

    /**
     * 获取rsa私钥
     * @return rsa私钥
     */
    String getRSAPrivateKey();

    /**
     * Author: Brady
     * Description: 通过RSA解密，获得MD5密码
     * Date: 2018/5/3
     */
    String getMD5PwdByRSA(String password);

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
    Boolean updateUserProfile(UserEntity user);

    /**
     * Author: Brady
     * Description: 保存用户基本信息
     * Date: 2018/6/26
     */
    UserEntity saveUser(UserEntity userEntity, UserInfoEntity userInfoEntity, Long siteId, Integer isDemo, String ip);

    /**
     * Author: Brady
     * Description: 根据用户名获得用户信息
     * Date: 2018/6/27
     */
    UserEntity getUserByUserName(String userName, Long siteId);


}
