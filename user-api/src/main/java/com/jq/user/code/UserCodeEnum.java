package com.jq.user.code;

/**
 * 自定义返回码API
 */
public enum UserCodeEnum {

    //01用户 登录操作相关
    /**
     * 40100
     * 帐号或密码错误
     */
    USER_NAMEORPWD_ERR(40100, "帐号或密码错误"),
    /**
     * 40101
     * 用户不存在
     */
    USER_NOT_EXIST(40101, "用户不存在"),
    /**
     * 40102
     * 验证码错误
     */
    ERR_VERIFYCODE(40102, "验证码错误"),
    /**
     * 40103
     * 用户状态异常
     */
    USER_UNNORMAL_STATUS(40103, "用户状态异常"),
    /**
     * 40104
     * 验证码生成失败
     */
    ERR_CREATE_VERIFYCODE(40104, "验证码已失效"),
    /**
     * 40105
     * 用户站点ID匹配错误
     */
    SITEID_ERR(40105, "用户站点ID匹配错误"),
    /**
     * 40106
     * 用户权限异常
     */
    USER_NO_AUTHORITY(40106, "用户权限异常"),
    /**
     * 40107
     * 新旧密码一致
     */
    SAME_PWD(40107, "新旧密码一致"),
    /**
     * 40108
     * 资料已存在
     */
    USER_INFORMATION_EXIST(40108, "资料已存在"),
    /**
     * 40109
     * 解析密码失败
     */
    WRONG_PARSE_PWD(40109, "密码错误"),
    /**
     * 40110
     * 用户已存在
     */
    USER_EXIST(40110, "用户已存在"),
    /**
     * 40111
     * 交易密码不合法，请重新输入4位数字
     */
    PAYPASSWORD_ILLEGAL(40111, "交易密码不合法，请重新输入4位数字"),
    /**
     * 40112
     * 管理员首次登录
     */
    SYS_USER_FIRST_LOGIN(40112, "管理员首次登录"),
    /**
     * 40113
     * 用户密码达到最大错误次数
     */
    USER_PWD_MAX_ERR_NUM(40113, "密码达到最大错误次数"),
    /**
     * 40114
     * 站点信息不存在
     */
    SITE_NOT_EXIST(40114, "站点信息不存在"),
    /**
     * 40115
     * 站点不可用
     */
    SITE_DISTBLED(40115, "站点不可用"),
    /**
     * 40116
     * 用户注册失败
     */
    USER_REGISTER_FAIL(40116, "用户注册失败"),
    /**
     * 40117
     * 站点未开启试玩帐户
     */
    SITE_DEMO_DISABLED(40117, "站点未开启试玩帐户"),
    /**
     * 40118
     * 初始化部门失败
     */
    SYS_INIT_DEPT_FAIL(40118, "初始化部门失败"),
    /**
     * 40119
     * 站点禁止注册
     */
    SITE_REGISTER_DISABLED(40119, "站点禁止注册"),
    /**
     * 40120
     * 站点已存在
     */
    SITE_EXIST(40120, "站点已存在"),
    /**
     * 40121
     * 缺少登录信息
     */
    LACK_USER_REGISTER_INFO(40121, "缺少用户信息"),
    /**
     * 40122
     * 平台类型错误
     */
    WRONG_PLATFORMTYPE_INFO(40122, "平台类型错误"),
    /**
     * 40123
     * 同一会话密码错误达到3次，需要输入验证码
     */
    NEED_SECURITYCODE(40123, "同一会话密码错误达到3次，需要输入验证码"),
    /**
     * 40124
     * 会员无权访问
     */
    MEMBERS_NO_AUTHORITY(40124, "会员无权访问"),
    /**
     * 40125
     * 验证码失效
     */
    VERIFYCODE_FAILURE(40125, "验证码失效"),
    /**
     * 40126
     * 用户名为空
     */
    EMPTY_VALUE_USERNAME(40126, "用户名为空"),
    /**
     * 40127
     * 密码为空
     */
    EMPTY_VALUE_PASSWORD(40127, "密码为空"),
    /**
     * 40128
     * 平台类型为空
     */
    EMPTY_VALUE_PLATFORMTYPE(40128, "平台类型为空"),
    /**
     * 40129
     * 管理员部门不存在
     */
    SYS_DEPT_NOT_EXIST(40129, "管理员部门不存在"),
    /**
     * 40130
     * RSA生成失败
     */
    CREATE_RSA_FAILURE(40130, "RSA生成失败"),
    /**
     * 40131
     * 用户角色绑定失败
     */
    BINDING_ROLE_FAILURE(40131,"用户角色绑定失败"),
    /**
     * 40132
     * 用户状态码不存在
     */
    USER_STATUS_CODE_NOT_EXIST(40132,"用户状态码不存在"),
    /**
     * 40133
     * 用户支付密码为空
     */
    EMPTY_PAY_PWD(40133,"用户支付密码为空"),
    /**
     * 40134
     * 验证码为空
     */
    EMPTY_VERIFYCODE(10134,"验证码为空"),
    /**
     * 40135
     * 系统当前时间为空
     */
    EMPTY_CURRTIME(40135,"系统当前时间为空"),
    /**
     * 40136
     * 手机号码不合法
     */
    MOBILE_ILLEGAL(40136, "手机号码不合法"),
    /**
     * 40137
     * 缺少信息
     */
    LACK_INFORMATION(40137,"缺少信息"),
    /**
     * 40138
     * 登录IP不在允许范围内
     */
    IP_NOT_MATCH(40138,"登录IP不在允许范围内"),
    /**
     * 40139
     * 参数为空
     */
    PARAM_IS_NULL(40139, "参数为空"),
    /**
     * 40140
     * 参数为空
     */
    SAVE_USER_INFO_FAIL(40140, "保存用户信息失败"),

    /**
     * 10140
     * token值不存在
     */
    USER_TOKEN(10140,"token值不存在"),
    GAME_ID_IS_NULL(40141, "游戏id为空"),
    SITE_ID_IS_NULL(40142, "站点id为空"),
    USER_ID_IS_NULL(40143, "用户id为空"),
    GAME_CODE_IS_NULL(40144, "游戏code为空"),
    SITE_CODE_IS_NULL(40145, "站点code为空"),
    GAME_CODE_DUPLICATE(40146, "游戏收藏重复"),
    USER_NAME_IS_NULL(40147, "用户名为空"),
    NOT_FAVORITE(40148, "无对应收藏信息"),
    USER_INFO_NOT_EXIST(40149,"用户基本信息不存在" ),
    /**
     * 40150
     * 服务不可用
     */
    SERVICE_CANNOT_USE(40150,"服务不可用"),
    /**
     * 40151
     * 方法运行失败
     */
    METHOD_FAIL(40151,"方法运行失败"),
    /**
     * 40139
     * 站点下用户为空
     */
    SITE_USER_IS_NULL(40139, "站点下用户为空"),


    //02等级 层级返回码
    /**
     * 40200
     * 等级不存在
     */
    RANK_MISS_CODE(40200, "等级不存在"),
    /**
     * 40201
     * 等级名称不合法
     */
    RANK_NAME_CODE(40201, "等级名称重复"),
    /**
     * 40202
     * 等级不合法
     */
    RANK_LEVEL_CODE(40202, "等级不合法"),
    /**
     * 40203
     * 最大积分比已有等级小
     */
    MAXSCORE_ISNOT_MAX(40203, "最大积分比已有等级小"),
    /**
     * 40204
     * 等级已存在
     */
    RANK_EXIST_CODE(40204, "等级已存在"),

    /**
     * 40205
     * 站点未初始化用户等级信息
     */
    RANK_NOT_INIT(40205, "站点未初始化用户等级信息"),
    /**
     * 40206
     * 小于低等级最大积分
     */
    MAXSCORE_TOO_SMALL(40206, "小于低等级最大积分"),
    /**
     * 40207
     * 大于高等级最大积分
     */
    MAXSCORE_TOO_LARGE(40207, "大于高等级最大积分"),
    /**
     * 40208
     * 开始时间应小于逾期时间
     */
    RANKBONUS_TIME_ERROR(40208, "开始时间应小于逾期时间"),
    /**
     * 40209
     * 暂无可领取的每日加奖
     */
    RANKBONUS_IS_ZERO(40209, "暂无可领取的每日加奖"),
    /**
     * 40210
     * 暂无可领取的晋级奖励
     */
    RANK_UPGRADE_BONUS_IS_ZERO(40210, "暂无可领取的晋级奖励"),
    /**
     * 40211
     * 每日加奖领取异常
     */
    ADD_RANKBONUS_ERROR(40211, "每日加奖领取异常"),
    /**
     * 40212
     * 晋级奖励领取异常
     */
    ADD_UPGRADE_BONUS_ERROR(40212, "晋级奖励领取异常"),
    /**
     * 40213
     * 每日加奖投注金额设置有误
     */
    RANKBONUS_LEVELBET_ERROR(40213, "每日加奖投注金额设置有误"),

    //03积分 层级返回码
    /**
     * 40300
     * 积分不足
     */
    SCORE_LACK_CODE(40300, "积分不足"),
    /**
     * 40301
     * 积分类型不存在
     */
    SCORETYPE_NOT_EXIST(40301, "积分类型不存在"),
    /**
     * 40302
     * 积分计算失败
     */
    ADD_SCORE_ERROR(40302, "积分计算失败"),
    /**
     * 40303
     * 今日已签到
     */
    EXISTE_SIGN(40303, "今日已签到"),


    //04返水错误码
    /**
     * 40401
     * 返水规则组不存在
     */
    RULE_NOT_EXIST(40401, "返水规则组不存在"),

    /**
     * 40402
     * 返水规则不存在
     */
    RULEINFO_NOT_EXIST(40402, "规则组下规则不存在"),

    /**
     * 40403
     * 返水规则组名已存在
     */
    RULENAME_EXIST(40403, "返水规则组名已存在"),

    /**
     * 40404
     * 返水规则组名过长
     */
    RULENAME_TOOLONG(40404, "返水规则组名过长"),

    /**
     * 40405
     * 排序值不合法
     */
    RULE_SORT_CODE(40405, "排序值不合法"),
    /**
     * 40406
     * 存在相同有效投注
     */
    EFFECTIVE_EXIST(40406, "存在相同有效投注"),
    /**
     * 40407
     * 有效投注不合法
     */
    EFFECTIVE_CODE(40407, "有效投注不合法"),
    /**
     * 40408
     * 返水上限不合法
     */
    REBATEMOST_CODE(40408, "返水上限不合法"),
    /**
     * 40409
     * 返点范围不合法
     */
    REBATE_RANGE_ERROR(40409, "返点范围不合法"),
    /**
     * 40410
     * 已存在返水事件名
     */
    REBATE_EVENT_EXIST(40410, "已存在返水事件名"),
    /**
     * 40411
     * 已存在返水区间
     */
    REBATE_TIME_EXIST(40411, "已存在返水区间"),
    /**
     * 40412
     * 返水结果不存在
     */
    RESULT_NOT_EXIST(40412, "返水结果不存在"),
    /**
     * 40413
     * 没有符合返水规则条件的用户
     */
    NO_REBATE_RESULT(40413, "没有符合返水规则条件的用户"),



    //05用户 层级错误码

    /**
     * 40501
     * 银行卡被禁用
     */
    BANKCARD_FORBIDDEN(40501, "银行卡被禁用"),
    /**
     * 40502
     * 银行卡已使用
     */
    BANKCARD_ENABLED(40502, "银行卡已使用"),
    /**
     * 40503
     * 银行卡禁用失败
     */
    BANKCARD_FORBIDDEN_FAILED(40503, "银行卡禁用失败"),
    /**
     * 40504
     * 银行卡被启用
     */
    BANKCARD_UN_ENABLED(40504, "银行卡未被启用"),
    /**
     * 40505
     * 银行卡被删除
     */
    BANKCARD_DELETED(40505, "银行卡已被删除"),
    /**
     * 40506
     * 无法删除银行卡
     */
    BANKCARD_ORDER_UNTREATED(40506, "无法删除银行卡，存在未处理的出款订单，请先处理！"),
    /**
     * 40507
     * 银行卡信息已存在
     */
    BANKCARD_EXIST(40507, "银行卡信息已存在"),

    /**
     * 40508
     * 银行卡不存在
     */
    BANKCARD_NOT_EXIST(40508, "银行卡不存在"),

    /**
     * 40509
     * 银行网点不存在
     */
    BANK_BRANCH_NOT_EXIST(40509, "银行网点不存在"),

    /**
     * 40601
     * 返点级别不能为空
     */
    REBATE_IS_NULL(40601, "返点级别不能为空"),
    /**
     * 40602
     * 登录账号不合法
     */
    USERNAME_ILLEGAL(40602, "登录账号不合法"),
    /**
     * 40603
     * 用户密码不合法
     */
    PASSWORD_ILLEGAL(40603, "用户密码不合法"),
    /**
     * 40604
     * 真实姓名不合法
     */
    REALNAME_ILLEGAL(40604, "真实姓名不合法"),
    /**
     * 40605
     * 状态无效
     */
    STATUS_INVALID(40605, "状态无效"),
    /**
     * 40606
     * 目标代理或上级与源代理一致，不能迁移
     */
    GOAL_IDENTICAL(40606, "目标代理或上级与源代理一致，不能迁移"),
    /**
     * 440607
     * 目标代理账号类型不能为会员
     */
    GOAL_NOT_PROXY(40607, "目标代理账号类型不能为会员"),
    /**
     * 40608
     * 源代理账号类型为会员，没有下级可迁移
     */
    SOURCE_NOT_PROXY(40608, "源代理账号类型为会员，没有下级可迁移"),
    /**
     * 40609
     * 源代理没有下级可迁移
     */
    SOURCE_NOT_SUB(40609, "源代理没有下级可迁移"),
    /**
     * 40610
     * 上级代理不能迁移到自己的下级代理
     */
    TRANSFER_LEVEL_INVALID(40610, "上级代理不能迁移到自己的下级代理"),
    /**
     * 40611
     * 目标代理线的返点比例不能低于源代理
     */
    DEST_REBATE_LOW(40611, "目标代理线的返点比例不能低于源代理"),
    /**
     * 40612
     * 对象不存在
     */
    OBJECT_NOT_EXIST(40612, "对象不存在"),
    /**
     * 40613
     * 对象已被删除
     */
    OBJECT_DELETED(40613, "对象已被删除"),
    /**
     * 40616
     * 会员返点大于上级返点
     */
    USER_REBATE_ILLEGAL(40616, "会员返点大于上级返点"),

    /**
     * 40617
     * 用户还未完善真实姓名，无法添加银行卡
     */
    USERINFO_LACK(40617, "用户还未完善真实姓名，无法添加银行卡"),//预防代码冲突

    /**
     * 40618
     * 会员返点信息不存在
     */
    USER_REBATE_NOT_EXIST(40618, "会员返点信息不存在"),

    /**
     * 40619
     * 该站点未初始化厅主
     */
    DEFAULT_NOT_EXIST(40619, "该站点未初始化厅主"),

    /**
     * 40620
     */
    USER_NOT_PROXY(40620, "上级为会员，不能添加下级代理会员"),
    /**
     * 40621
     * 新返点信息不能大于就返点信息
     */
    NEWREBATE_LESS_OLDREBATE(40621, "新返点信息不能大于就返点信息"),
    GPC_REBATE_OVER_LIMIT(40622, "高频彩票超出最高返点限制"),
    DPC_REBATE_OVER_LIMIT(40623, "低频彩票超出最高返点限制"),
    TYCP_REBATE_OVER_LIMIT(40624, "体育彩票超出最高返点限制"),
    TY_REBATE_OVER_LIMIT(40625, "体育超出最高返点限制"),
    QT_REBATE_OVER_LIMIT(40626, "其他超出最高返点限制"),
    FLC_REBATE_OVER_LIMIT(40627, "棋牌返点超出最高返点限制"),
    LHC0_REBATE_OVER_LIMIT(40628, "六合彩0超出最高返点限制"),
    LHC1_REBATE_OVER_LIMIT(40629, "六合彩1超出最高返点限制"),
    LHC2_REBATE_OVER_LIMIT(40630, "六合彩2超出最高返点限制"),
    LHC3_REBATE_OVER_LIMIT(40631, "六合彩3超出最高返点限制"),
    REBATE_OVER_100(40632, "返点组合不能超过100%"),
    HIGH_NOT_EXIST(40633,"查无上级用户"),
    RPC_SERVER_UNAVAILABLE(40634, "RPC服务异常,请联系管理员"),
    BONUS_TYPE_IS_NULL(40635, "奖金类型为空"),
    BAN_USER_REBATE_FAIL(40636, "代理不存在,或已被禁止返点"),
    BAN_REBATE_USER_DELETED(40637, "已删除禁止返点代理"),
    TRANSFER_EXCEPTION(40638, "代理迁异常"),
    SAVE_PROXY_FAIL(40639, "代理关系保存失败"),
    USER_ID_LIST_EMPTY(40640, "用户列表为空"),
    // 07 代理推广

    /**
     * 40701
     * 目标代理信息不存在或已被删除
     */
    USERREBATE_IS_DEL(40701, "目标代理线不存在或已被删除"),

    /**
     * 40702
     * 低频彩返点不得高于目标代理
     */
    DPC_REBATE_OVER_TARGET(40702, "低频彩返点不得高于目标代理"),
    /**
     * 40703
     * 棋牌返点不得高于目标代理
     */
    FLC_REBATE_OVER_TARGET(40703, "棋牌返点不得高于目标代理"),
    /**
     * 40704
     * 体育彩票返点不得高于目标代理
     */
    TYCP_REBATE_OVER_TARGET(40704, "体育彩票返点不得高于目标代理"),
    /**
     * 40705
     * 体育彩票返点不得高于目标代理
     */
    TY_REBATE_OVER_TARGET(40705, "体育返点不得高于目标代理"),
    /**
     * 40706
     * 高频彩返点不得高于目标代理
     */
    GPC_REBATE_OVER_TARGET(40706, "高频彩返点不得高于目标代理"),
    /**
     * 40707
     * 其它彩票返点不得高于目标代理
     */
    QT_REBATE_OVER_TARGET(40707, "其它彩票返点不得高于目标代理"),
    /**
     * 40708
     * 六合彩组0返点不得高于目标代理
     */
    LHC0_REBATE_OVER_TARGET(40708, "六合彩组0返点不得高于目标代理"),
    /**
     * 40709
     * 六合彩组3返点不得高于目标代理
     */
    LHC1_REBATE_OVER_TARGET(40709, "六合彩组1返点不得高于目标代理"),
    /**
     * 40710
     * 六合彩组3返点不得高于目标代理
     */
    LHC2_REBATE_OVER_TARGET(40710, "六合彩组2返点不得高于目标代理"),
    /**
     * 40711
     * 六合彩组3返点不得高于目标代理
     */
    LHC3_REBATE_OVER_TARGET(40711, "六合彩组3返点不得高于目标代理"),

    REBATE_ONLY_ONE_DECIMAL(40713, "返点只能保留一位小数"),
    /**
     * 40714
     * 推广码不存在或者已删除
     */
    REFERCODE_NOT_EXIST(40714, "推广码不存在或者已删除"),
    /**
     * 40715
     * 推广码被禁用
     */
    REFERCODE_DISABLED(40715, "推广码被禁用"),
    /**
     * 40716
     * 代理类型不能为空
     */
    ACCOUNT_TYPE_IS_NULL(40716, "代理类型不能为空"),

    HIGH_USER_IS_NULL(40717, "上级代理不存在"),
    DISTRIBUTE_STRATEGY_ILLEGAL(40718, "分发策略非法"),
    BONUS_RULE_ILLEGAL(40719, "奖金规则非法"),
    BONUS_STRATEGY_ILLEGAL(40720, "奖金策略非法"),
    BONUS_CYCLE_ILLEGAL(40721, "奖金周期非法"),
    BONUS_MODE_ILLEGAL(40722, "奖金模式非法"),
    BONUS_TYPE_ILLEGAL(40723, "奖金类型非法"),
    UN_LOCK(40724, "分红记录未锁定"),
    KEY_EXIST(40725, "用户已绑定谷歌验证器"),
    KEY_NOT_EXIST(40726, "用户未绑定谷歌验证器"),


    /**
     * 40728
     * 自定义地址不合法
     */
    DOMAINURL_IS_ILLEGAL(40728,"自定义地址不合法"),
    /**
     * 40729
     * 自定义地址已存在
     */
    DOMAINURL_IS_REPEAT(40729,"自定义地址已存在"),
    SOURCE_NOT_EXIST(40730, "源代理不存在"),
    DEST_NOT_EXIST(40731, "目标代理不存在"),
    USER_ID_DEFICIENCY(40732, "用户id缺失"),
    HIGH_REBATE_NOT_EXIST(40733, "上级返点信息不存在"),


    /**
     * 40734
     * 推广码重复
     */
    REFER_CODE_IS_EXIST(40734,"推广码已被使用"),

    /**
     * 40735
     * 推广码格式错误
     */
    REFER_CODE_VALID_FALSE(40735,"推广码格式错误!"),

    /**
     * 40736
     * 默认代理线不能操作
     */
    REFER_CODE_IS_DEFAULT_PROXY(40736,"该推广链接为默认代理线,无法操作"),
    PROXY_ID_IDENTICAL(40737, "源代理与目标代理不能为同一用户"),
    DEST_USER_TYPE_ERROR(40738, "目标代理账号类型有误，请输入正式账号！"),
    FREQUENT_OPERATION(40739,"操作频繁，请稍后再试"),

    //8 代理分红 相关
    BONUS_PARAM_NOT_AVAILABLE(40801,"分红模块参数校验异常"),

    BONUS_BUSINESS_EXCEPTION(40802,"分红模块业务异常"),

    //9
    FAVORITE_LIST_IS_NULL(40901, "选择收藏列表为空"),
    AREA_NOT_EXIST(40902, "区域不存在"),
    /**
     * 41001
     * 密保相关操作
     */
    ENCRYPTED_ERROR(41001,"找回密码操作失败"),
    ENCRYPTED_LIST_IS_NULL(41002,"密保内容为空"),
    ENCRYPTED_LIST_UNMEET(41003,"密保内容数量不满足"),
    ENCRYPTED_UN_SET(41004,"未设置密保,请先设置密保"),
    ENCRYPTED_ANSWER_ERROR(41005,"密保回答错误,%s次错误后账号将被冻结"),
    ENCRYPTED_IS_NULL(41006,"密保不存在"),
    ENCRYPTED_PAY_PASSWORD_ERROR(41007,"验证支付密码错误,%s次错误后账号将被冻结"),
    USER_UNVERIFIED_ERROR(41008,"未通过身份验证"),
    ENCRYPTED_REPEAT(41009,"密保问题重复"),
    ;

    private Integer code;
    private String message;

    UserCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    /**
     * 通过code获得message
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        String message = "";
        for (UserCodeEnum e : UserCodeEnum.values()) {
            if (e.getCode().equals(code)) {
                message = e.getMessage();
            }
        }
        return message;
    }




}
