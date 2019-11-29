package com.jq.user.constant;

/**
 * Created by ZhangCong on 2018/4/17.
 */
public interface RedisKey {

    /** 密钥对，redis：key+私钥 */
    String RSA_PRIVATE_KEY = "us:rsa:private:key";
    /** 密钥对，redis：key+公钥 */
    String RSA_PUBLIC_KEY = "us:rsa:public:key";
    /** 生成验证码文件夹名 */
    String SECURITY_CODE ="us:securityCode:";
    /** token */
    String USER_TOKEN ="us:token:";
    String CACHE_USER_TOKEN="us:token";//去除最后分层符号,spring cache data自动拼接
    /** 登录时uuid */
    String USER_LOGIN="us:login:";
    /** */
    String USER_LOGIN_VERIFY="us:login:verify:";
    /** 资金相关 */
    String BONUS_MAIN = "us:bonus:main:";
    String BONUS_SON = "us:bonus:son:";
    String BONUS_SETTING = "us:bonus:setting:";
    String BONUS_SETTINGTYPE = "us:bonus:settingtype:";
    String USER_HOURLY_ONLINES ="us:hourlyonlines:";
    /** 首页用户分布图防刷缓存 */
    String USER_INDEX_MAP ="us:index:map:";
    /** 首页访问量防刷缓存 */
    String USER_INDEX_PAGE_VIEW ="us:index:pageview:";
    /*** 用于验证用户出款 支付密码错误次数 */
    String PAY_WITHDAW_USER_PAY_PASSWORD_ERROR = "pay.withdraw.user.pay.password.error.userId:";
    /** 找回登入密码--验证密保问题错误次数 **/
    String FIND_LOGIN_PASSWORD_VERIFY_ENCRYPTED_ERROR_NUM = "find.login.password.verify.encrypted.error.num:";
    /** 找回支付密码--验证密保问题错误次数 **/
    String FIND_pay_PASSWORD_VERIFY_ENCRYPTED_ERROR_NUM = "find.pay.password.verify.encrypted.error.num:";
    /** 修改密保问题--验证密保问题错误次数 **/
    String UPDATE_ENCRYPTED_VERIFY_ERROR_NUM = "update.encrypted.verify.error.num:";
    /** 修改密保问题--验证密保问题是否验证 **/
    String ENCRYPTED_VERIFY_SUCCESS = "encrypted.verify.success:";
    /** 找回登录密码--验证支付密码是否成功  **/
    String FIND_LOGIN_PASSWORD_VERIFY_PAY_PASSWORD_SUCCESS = "find.login.password.verify.pay_password.success:";
    /** 找回登入密码--验证支付密码错误次数 **/
    String FIND_LOGIN_PASSWORD_VERIFY_PAY_PASSWORD_ERROR_NUM = "find.login.password.verify.pay.password.error.num:%s";
    /** redis 分布式锁lock */
    class Lock {
        /** 人工操作积分 分布式锁 */
        public static final String INCOME_PAY = "lock:incomepay:";

        /**推广链接 分布式锁**/
        public static final String LOCK_USER_SIGN  ="lock:userSign:";

        /** 增加积分 分布式锁 */
        public static final String ADD_SCORE = "lock:addscore:";

        /** 充值增加积分 分布式锁 */
        public static final String ADD_SCORE_RECHARGE = "lock:addscorerecharge:";

        /** 每日加奖 分布式锁 */
        public static final String ADD_RANK_BONUS = "lock:addrankbonus:";

        /** 晋级奖励 分布式锁 */
        public static final String ADD_UPGRADE_BONUS = "lock:addupgradebonus:";

        /** 用户注册 分布式锁 */
        public static final String REGISTER_USER = "lock:registeruser:";

        public Lock() {
        }
    }
}
