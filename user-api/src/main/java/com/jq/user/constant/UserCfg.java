package com.jq.user.constant;

public interface UserCfg {
    //用户日志分类
    String USER_LOG_FLAG_TYPE ="USER_LOG_FLAG_TYPE";
    //登录日志
    String USER_LOG_FLAG_TYPE_LOGIN = "FLAG_TYPE_LOGIN";
    //操作日志
    String USER_LOG_FLAG_TYPE_OPER = "FLAG_TYPE_OPER";
    /*** 用于验证用户使用预付卡支付 支付密码错误次数 */
    String  PAY_CARD_USER_PAY_PASSWORD_ERROR = "pay.card.user.pay.password.error.userId:";
    /*** 用于验证用户出款 支付密码错误次数 */
    String  PAY_WITHDAW_USER_PAY_PASSWORD_ERROR = "pay.withdraw.user.pay.password.error.userId:";
    /*** 用于验证用户使用转点 支付密码错误次数 */
    String  PAY_TRANSFER_USER_PAY_PASSWORD_ERROR = "pay.transfer.user.pay.password.error.userId:";
    /** 支付密码错误最大次数*/
    Integer PAY_PASSWORD_OUT_PUT_ERROR_COUNT = 5;
    //默认站点管理员名
    String SITE_USER = "siteUser";
    //默认最大错误次数
    Integer MAX_ERROR_COUNT = 5;
    //密保相关默认错误次数
    Integer MAX_ENCRYPTED_ERROR_COUNT = 6;
    //默认系统用户密码
    String SYS_USER_PWD = "cp123456";
    //默认试玩用户密码
    String USER_DEMO_PWD = "cp123456";
    //管理员默认密码
    String SYS_USER_DEFAULT_PWD = "cp123456";
    //厅主默认密码
    String DEFAULT_PWD = "cp123456";
    //默认支付密码
    String DEFAULT_PAY_PWD = "1234";
    //默认厅主名
    String DEFAULT_SYS_USER_NAME = "default";
    //默认厅主真实名
    String DEFAULT_SYS_USER_REAL_NAME ="厅主";
    //默认管理员部门名
    String DEFAULT_SYS_DEFT_NAME="默认";
    //默认试玩账户真实姓名
    String DEFAULT_DEMO_REAL_NAME ="试玩用户";

    //用户日志类型
    String USER_LOG_TYPE ="USER_LOG_TYPE";
    //修改
    String UPDATE = "UPDATE";
    //删除
    String DELETE ="DELETE";
    //增加
    String ADD ="ADD";
    //登出成功
    String LOGOUT_SUCCESS = "LOGOUT_SUCCESS";
    //登出失败
    String LOGIN_FAILURE = "LOGIN_FAILURE";
    //下单
    String ORDER = "ORDER";
    //注册成功
    String REGISTER_SUCCESS="REGISTER_SUCCESS";
    //注册失败
    String REGISTER_FAILURE="REGISTER_FAILURE";
    //登录成功
    String LOGIN_SUCCESS = "LOGIN_SUCCESS";
    //登录失败
    String LOGOUT_FAILURE = "LOGOUT_FAILURE";
    //存款
    String DEPOSIT = "DEPOSIT";
    //退款
    String WITHDRAW = "WITHDRAW";
    //修改密码
    String UPDATE_PWD = "UPDATE_PWD";
    //修改支付密码
    String UPDATE_PAY_PWD = "UPDATE_PAY_PWD";
    //分组
    String USER_GROUP = "USER_GROUP";
    //收款账户
    String COLLECTING_ACCOUNT = "COLLECTING_ACCOUNT";
    //入款模板
    String DEPOSIT_MODE = "DEPOSIT_MODE";
    //出款模板
    String WITHDRAW_MODE = "WITHDRAW_MODE";
    //领取每日加奖
    String RANK_BONUS ="RANK_BONUS";
    //领取晋级奖励
    String UPGRADE_BONUS ="UPGRADE_BONUS";


    //用户类型
    String USER_TYPE = "USER_TYPE";
    //系统用户
    String SYS = "SYS";
    //站点用户
    String SITE = "SITE";
    //会员
    String USER = "USER";
    //试玩账户
    String DEMO = "DEMO";
    //测试帐户
    String TEST = "TEST";

    //图片验证码的宽
    Integer CAPTCHA_WIDE = 200;
    //图片验证码的高
    Integer CAPTCHA_HIGH = 80;
    //验证码的位数
    Integer CAPTCHA_COUNT = 4;
    //干扰线的个数
    Integer CAPTCHA_LINE_COUNT = 2;
    Integer PC = 1;
    // 试玩帐户初始资金
    Long DEMO_AMOUNT = 200000L;

}
