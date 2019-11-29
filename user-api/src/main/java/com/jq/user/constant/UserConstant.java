package com.jq.user.constant;

/**
 * @author Created by ZhangCong on 2018/4/24.
 */
public interface UserConstant {

    String SCORE_TYPE = "SCORE_TYPE";
    String SCORE_TYPE_URL="SCORE_TYPE_URL";
    String USER_STATUS = "USER_STATUS";
    Integer IS_T = 1;
    Integer IS_F = 0;
    Integer IS_TEST =2;
    Integer IS_ONE = 1;
    Integer IS_ZERO = 0;
    Integer IS_TWO = 2;
    Integer IS_THREE=3;
    Integer IS_FOUR=4;
    Integer IS_FIVE=5;

    //修改密码错误次数对应的key
    String CHANGE_PASS_WORD_ERROR_NUM = "CHANGE_PASS_WORD_ERROR_NUM";

    //日工资相关
    String BONUS_CYCLE = "BONUS_CYCLE";
    Integer MONTH_ONE = 0;
    Integer MONTH_TWO = 1;
    Integer MONTH_THREE = 2;
    Integer DAY_ONE = 3;
    String BONUS_SETTING_TYPE= "bonus_setting_type";
    Integer BONUS = 0;
    Integer CONTRACT_BONUS = 1;
    Integer DAILY = 2;
    Integer CONTRACT_DAILY = 3;
    String GPC = "gpc";
    String  LHC = "lhc";
    String DPC = "dpc";
    String FLC = "flc";
    String LHC_REBATE = "LHC_REBATE";
    String REBATE0 = "REBATE0";
    String REBATE1 = "REBATE1";
    String REBATE2 = "REBATE2";
    String REBATE3 = "REBATE3";
    String EXAMINE_STATE = "examine_state";
    Integer INIT = 0;
    Integer LOCK = 1;
    Integer APPLYED = 2;
    String BONUS_MAIN = "BONUS_MAIN";
    String BONUS_SON = "BONUS_SON";
    String BONUS_SETTING = "BONUS_SETTING";


    //登录类型
    Integer PC = 1;
    Integer APP = 2;
    String APP_STR = "手机";
    String PC_STR = "电脑";

    String LOGIN_PLATFORM = "LOGIN_PLATFORM";
    //H5 : H5
    String H5 = "H5";
    //WEB : 网页
    String WEB_NAME = "WEB";
    //PP : APP
    String APP_NAME = "APP";

    //默认生成的等级名
    String INIT_RANKNAME = "默认等级";

    //管理员修改密码错误最大次数
    String SYS_LOGIN_USER_ERROR_PAS_NUM = "SYS_LOGIN_USER_ERROR_PAS_NUM";
    //管理员修改密码错误最大次数
    Integer PAY_PASSWORD_OUT_PUT_ERROR_COUNT = 6;

    //棋牌返点
    String USER_FLC_REBATE = "FLC_REBATE";

    //高频彩返点
    String USER_GPC_REBATE = "GPC_REBATE";

    //体育彩票返点
    String USER_TYCP_REBATE = "TYCP_REBATE";

    //其他彩票返点
    String USER_QT_REBATE = "QT_REBATE";

    //体育返点
    String USER_TY_REBATE = "TC_REBATE";

    //六合彩组3返点，单位：万
    String USER_DPC_REBATE = "DPC_REBATE";

    //六合彩组2返点，单位：万
    String USER_LHC_REBATE0 = "LHC_REBATE0";

    //六合彩组1返点，单位：万
    String USER_LHC_REBATE1 = "LHC_REBATE1";

    //六合彩组0返点，单位：万
    String USER_LHC_REBATE2 = "LHC_REBATE2";

    //低频彩返点
    String USER_LHC_REBATE3 = "LHC_REBATE3";

    //返点信息字典key
    String USER_REBATE_CODE = "USER_REBATE";

    //本站链接
    String LINK_TYPE_INNER = "0";
    //外部链接
    String LINK_TYPE_OUTTER = "1";

    //域名请求 会员端标识
    Integer USER_POINT =1;
    //商家端
    Integer SITE_POINT=2;
    //平台端
    Integer ADMIN_POINT=3;

    //厅主返点
    Long DEFAULT_REBATE=1000l;

    String SINGLE_QUOTES = "\'";
    String SLASH = "/";
    String COMMA = ",";

    //返水状态
    int REBATE_STATUS=0;
    int CANCEL_REBATE_STATUS=1;

    //付款方式
    class PayType{
        /** 预付卡--0 **/
        public static final int PRE_CARD = 0;
        /** 余额支付--1 **/
        public static final int PAY_BALANCE = 1;
        /** 手机银行--2 **/
        public static final int M_BANK = 2;
        /** 公司银行--3 **/
        public static final int COMPANY = 3;
        /** 网上银行--4 **/
        public static final int NET_BANK = 4;
        /**其他银联付款方式--5**/
        public static final int OTHER_YL = 5;
        /**扫码支付--6**/
        public static final int SCAN = 6;
        /**QQ支付--7**/
        public static final int QQ = 7;
        /**QQ支付H5--8**/
        public static final int QQ_H5 = 8;
        /**微信--9**/
        public static final int WEIXIN = 9;
        /**微信H5支付--10**/
        public static final int WEIXIN_H5 = 10;
        /**其他付款方式--11**/
        public static final int OTHER = 11;
        /**其他线下付款方式--12**/
        public static final int OTHERSCANCODE = 12;
        /** 支付宝--13**/
        public static final int ALIPAY = 13;
        /** 支付宝H5支付--14 **/
        public static final int ALIPAY_H5 = 14;
        /** 系统支付--15 **/
        public static final int PAY_SYSTEM = 15;
        /**点卡支付--16**/
        public static final int CARD_PAY = 16;
        /** 取款模式--17 **/
        public static final int WITHDRAW = 17;
    }

    //交易大类
    class TradeType{
        /** 入款--1 **/
        public static final int INCOME = 1;
        /** 出款--2 **/
        public static final int EXPENSE = 2;
        /** 冻结--3 **/
        public static final int FREEZE = 3;
        /** 解冻--4 **/
        public static final int UNFREEZE = 4;

        /**
         * 交易小类--入款
         *
         * @author wsy
         *
         */
        public static class Income {
            /** 入款--101 **/
            public static final int IN = 101;
            /** 推广费--102 **/
            public static final int PROMOTION = 102;
            /** 入款优惠--103 **/
            public static final int DISCOUNT = 103;
            /** 入款额外优惠--104 **/
            public static final int EX_DISCOUNT = 104;
            /** 人工存入--105 **/
            public static final int STAFF_IN = 105;
            /** 派彩--106 **/
            public static final int PRIZE = 106;
            /** 取消投注--107 **/
            public static final int CANCEL_ORDER = 107;
            /** 返点--108 **/
            public static final int REBATE = 108;
            /** 其他--109 **/
            public static final int OTHER = 109;
            /** 人工存入-存款优惠--110 **/
            public static final int IN_DISCOUNT = 110;
            /** 人工出入-活动优惠--111 **/
            public static final int ACTIVITY_DISCOUNT = 111;
            /** 人工存入_返点优惠--112 **/
            public static final int REBATE_DISCOUNT = 112;
            /** 人工存入_负数归零--113 **/
            public static final int RETURN_ZERO = 113;
            /** 人工存入_取消出款--114 **/
            public static final int CANCEL_WITHDRAW = 114;
            /** 人工存入_其他--115 **/
            public static final int STAFF_IN_OTHER = 115;
            /** 合买佣金--116 **/
            public static final int JOINTBUY_COMMISSION = 116;
            /** 撤销合买--117 **/
            public static final int CANCEL_JOINTBUY = 117;
            /** 撤销合买返还保底金额--118 **/
            public static final int CANCEL_JOINTBUY_GUARANTY = 118;
            /** 人工存入-存款其他优惠 **/
            public static final int IN_EXDISCOUNT = 119;
            /** IN_BONUS ：代理分红--120*/
            public static final int IN_BONUS = 120;
            /** IN_CONTRACT_BONUS ：代理分红-契约分红--121*/
            public static final int IN_CONTRACT_BONUS = 121;
            /** IN_DAILY ：日工资--122*/
            public static final int IN_DAILY = 122;
            /** IN_CONTRACT_DAILY ：日工资 - 契约日工资--123*/
            public static final int IN_CONTRACT_DAILY = 123;
            /** IN_MAKE_WATER 团队赚水--124*/
            public static final int IN_MAKE_WATER = 124;
            /**REBATE_WATER : 返水**/
            public static final int REBATE_WATER = 125;
        }

        /**
         * 交易小类--出款
         *
         * @author wsy
         *
         */
        public static class Expense {
            /** 下注--201 **/
            public static final int ORDER = 201;
            /** 人工提出--202 **/
            public static final int STAFF_OUT = 202;
            /** 取消优惠返点--203 **/
            public static final int CANCEL_REBATE = 203;
            /** 实际出款--204 **/
            public static final int FACT_OUT = 204;
            /** 出款扣除的手续费--205 **/
            public static final int OUT_FEE = 205;
            /** 出款扣除的优惠--206 **/
            public static final int OUT_DISCOUNT = 206;
            /** 其他--207 **/
            public static final int OTHER = 207;
            /** 人工提现-重复出款--208 **/
            public static final int REPEAT_OUT = 208;
            /** 人工提现-公司入款误存--209 **/
            public static final int IN_WRONG = 209;
            /** 人工提现-会员负数回充--210 **/
            public static final int RETURN_ZERO = 210;
            /** 人工提现-扣除非法下注非法派彩--211 **/
            public static final int WRONG_PRIZE = 211;
            /** 人工提现-放弃存款优惠--212 **/
            public static final int GIVE_UP = 212;
            /** 人工提现-其他--213 **/
            public static final int STAFF_OUT_OTHER = 213;
            /** 撤单扣除返点--214 **/
            public static final int CANCEL_ORDER_REBATE = 214;
            /** 撤销合买扣除返点--215 **/
            public static final int CANCEL_JOINTBUY_REBATE = 215;
            /** 拒绝出款 **/
            public static final int REFUSE_OUT = 216;
            /** PAY_CONTRACT_BONUS ：代理分红-契约分红--217*/
            public static final int PAY_CONTRACT_BONUS = 217;
            /** PAY_CONTRACT_DAILY ：日工资 - 契约日工资--218*/
            public static final int PAY_CONTRACT_DAILY = 218;
            /***WRITE_OFF:冲销返水*/
            public static final int WRITE_OFF = 219;
        }

            /**
             * 交易小类--冻结
             */
            public static class Freeze {
                /** 冻结出款--301 **/
                public static final int FREEZE_OUT = 301;
                /** 出款冻结手续费--302 **/
                public static final int FREEZE_FEE = 302;
                /** 出款冻结优惠--303 **/
                public static final int FREEZE_DISCOUNT = 303;
                /** 其他--304 **/
                public static final int OTHER = 304;
            }

            /**
             * 交易小类--解冻
             */
            public static class Unfreeze {
                /** 解冻--401 **/
                public static final int THAW = 401;
            }

    }

    //订单交易编号
    class OrderNoStartsWith {
        public static final String ORDER_NO = "NO";
        public static final String IN = "IN";
        public static final String OUT = "OUT";
        public static final String CHASE = "CH";
        public static final String BATCH = "BT";
        public static final String JOINTBUY = "JB";

        public OrderNoStartsWith() {
        }
    }

    //消息区域
//    dev  孟买  ap-south-1
//    test 东京  ap-northeast-1
//    pre  新加坡 ap-southeast-1
//    uat  首尔   ap-northeast-2
    class SQSRegin{
        public static final String dev="ap-south-1";
        public static final String test="ap-northeast-1";
        public static final String pre="ap-southeast-1";
        public static final String uat="ap-northeast-2";
        public static final String prod="ap-southeast-1";
    }

    //消息队列名
    class QueueUrl{
        public static final String INCOME_PAY_QUEUE = "income-pay-queue";//资金出入款消息队列
        public QueueUrl() {
        }
    }

    //积分任务类型
    class ScoreType{
        public static final int UNLIMITED=0;//没有限制
        public static final int ONETIME=1;//一次性
        public static final int ONTODAY=2;//今日

    }

    /**
     * 代理返点
     */
    class Proxy_Rebate{
        //初始化站点时 默认代理返点
        public static final Long DEFAULT_REBATE=0L;
    }

    /**
     * 团队人员总数、新增人员、7天未登录人员常量
     */
     class ProxyNumberFiled{
        public final static String TEAM_NUMBER = "teamNumber";
        public final static String TEAM_NEW_NUMBER = "teamNewNumber";
        public final static String TEAM_NOT_LOGIN = "teamNotLogin";
    }

}
