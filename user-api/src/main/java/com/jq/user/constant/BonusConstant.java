package com.jq.user.constant;

/**
 * 〈代理分红常量〉
 *
 * @author Json
 * @create 2018/6/26
 */
public class BonusConstant {

    /**
     * 分红规则(0:盈亏，1:日均量，2:总量)
     *
     * @author jfChen
     * @date 2017年10月11日 下午7:05:48
     *
     */
    public static class BonusRuleDetail{
        public static final String CODE_TYPE = "BONUS_RULE";
        /** 0:盈亏 **/
        public static final Integer WIN_LOSE = 0;
        /** 1:日均量 **/
        public static final Integer DAILY_AVERAGE = 1;
        /** 2:总量 **/
        public static final Integer TOTAL_BET = 2;
    }



    /**
     * 设置类型(0:分红设置,1:契约分红设置,2日工资设置,3:契约日工资设置)
     * @author XC
     * **/
    public static class BonusSettingType{
        public static final String CODE_TYPE = "bonus_setting_type";
        /** 0:分红设置**/
        public static final Integer BONUS = 0;
        /** 1:契约分红设置**/
        public static final Integer CONTRACT_BONUS = 1;
        /** 2:日工资设置**/
        public static final Integer DAILY = 2;
        /** 3:契约日工资设置**/
        public static final Integer CONTRACT_DAILY = 3;
    }


    /**
     * 契约分红redis前缀
     * @author XC
     * **/
    public static class BonusRedis{
        /** 主单redis前缀 **/
        public static final String BONUS_MAIN = "BONUS_MAIN";
        /** 细单redis前缀**/
        public static final String BONUS_SON = "BONUS_SON";
        /** 日工资细单redis前缀 **/
        public static final String BONUS_SETTING = "BONUS_SETTING";

        public static final String BONUS_SETTINGTYPE = "bonus:settingtype:";
    }


    /**六合彩返点类型
     * @author XC
     * **/
    public static class LhcRebateType{
        public static final String CODE_TYPE = "LHC_REBATE";
        /** REBATE0 : 返点组0 特码，连码**/
        public static final String REBATE0 = "REBATE0";
        /** REBATE1 : 返点组1 半波，正码特，全不中**/
        public static final String REBATE1 = "REBATE1";
        /** REBATE2 : 返点组2 特肖，正码，连肖，尾数连**/
        public static final String REBATE2 = "REBATE2";
        /** REBATE3 : 返点组3 合肖，正码1-6，一肖/尾**/
        public static final String REBATE3 = "REBATE3";
    }



    /**游戏分类代码*/
    public static class GameCategoryCode{
        /**高频彩*/
        public final static String GPC = "gpc";
        /**六合彩*/
        public final static String LHC = "lhc";
        /**低频彩*/
        public final static String DPC = "dpc";
        /**棋牌*/
        public final static String FLC = "flc";
        /**体彩*/
        public final static String TY = "ty";
        /**体育彩票*/
        public final static String TYCP = "tycp";
        /**其他彩票*/
        public final static String QT = "qt";
        /**
         * 游戏大类 ： 彩票游戏
         */
        public final static String CP = "CP";
        /**
         * 游戏大类 ： 真人视讯
         */
        public final static String ZR = "ZR";

    }


    /**
     * 契约签订状态(0:未签订，1:同意，2:拒绝)
     * @author XC
     * **/
    public static class SignStatus{
        public static final String CODE_TYPE = "sign_status";
        /** 0:未签订 **/
        public static final Integer NOT_SIGN = 0;
        /** 1:同意**/
        public static final Integer AGREE = 1;
        /** 2:拒绝**/
        public static final Integer REFUSE= 2;
    }


    /**
     * 平台，1->pc，2->app
     *
     * @author jqlin
     *
     */
    public static class PlatformType {
        public static final Integer PC = 1;
        public static final Integer APP = 2;
        public static final String APP_STR = "手机";
        public static final String PC_STR = "电脑";
    }


    /** codeType : 账号类型**/
    public static class  AccountType{
        public static final String CODE_TYPE = "ACCOUNT_TYPE";
        /** SYS : 系统用户-平台**/
        public static final String SYS = "SYS";
        /** SITE : 站点用户-管理员**/
        public static final String SITE = "SITE";
        /** USER : 用户**/
        public static final String USER = "USER";
        /** DEMO : 试玩账户**/
        public static final String DEMO = "DEMO";
        /** TEST 测试账号**/
        public static final String TEST = "TEST";
    }



    /**
     * 分红周期 (0:每月1期，1:每月2期，2:每月3期，3:每天一期,4:每周一期)
     *
     * @author jfChen
     * @date 2017年10月11日 下午7:05:48
     *
     */
    public static class BonusCycle{
        public static final String CODE_TYPE = "BONUS_CYCLE";
        /** 0:每月1期 **/
        public static final Integer MONTH_ONE = 0;
        /** 1:每月2期 **/
        public static final Integer MONTH_TWO = 1;
        /** 2:每月3期 **/
        public static final Integer MONTH_THREE = 2;
        /** 3:每天1期 **/
        public static final Integer DAY_ONE = 3;
        /** 4:每周1期 **/
        public static final Integer WEEK_ONE = 4;
    }



    /**
     * 代理分红审核状态(0:临时，1:锁定，2:已审核)
     * @author XC
     * **/
    public static class ExamineState{
        public static final String CODE_TYPE = "examine_state";
        /** 0:临时 初始化 **/
        public static final Integer INIT = 0;
        /** 1:锁定 待审核**/
        public static final Integer LOCK = 1;
        /** 2:已经审核**/
        public static final Integer APPLYED = 2;

    }


    /**
     * 交易订单状态
     *
     * @author ASUS
     *
     */
    public static class OrderNoStartsWith {
        /** 注单号 */
        public static final String ORDER_NO = "NO";
        /** 入账 **/
        public static final String IN = "IN";
        /** 出账 **/
        public static final String OUT = "OUT";
        /** 追号 */
        public static final String CHASE = "CH";
        /** 批次号 */
        public static final String BATCH = "BT";
        /** 合买方案编号 **/
        public static final String JOINTBUY = "JB";
    }



    //分红周期
    public static final String BONUSCYCLE_MONTH_ONE = "0";
    public static final String BONUSCYCLE_MONTH_TWO = "1";
    public static final String BONUSCYCLE_MONTH_THREE = "2";
    public static final String BONUSCYCLE_DAY_ONE = "3";
    public static final String BONUSCYCLE_WEEK_ONE = "4";
}
