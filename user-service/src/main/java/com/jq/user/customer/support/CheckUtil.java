package com.jq.user.customer.support;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;

import java.util.regex.Pattern;

public class CheckUtil {
    // 6-14位字母及数字，必须英文字母开头，区分大小写
//    private static final String RE_PWD = "[a-zA-Z][a-zA-Z0-9]{5,13}";
    private static final String RE_PWD = "^(?![\\\\d]+$)(?![a-zA-Z]+$)(?![^\\\\da-zA-Z]+$).{6,20}$";
    // 6-12个字元, 仅可输入英文小写字母或数字或下划线
//    private static final String RE_USERNAME = "[a-z0-9_]{6,12}";
    private static final String RE_USERNAME = "^[a-zA-Z0-9][a-zA-Z0-9_]{5,15}$";
    // 2-12位字母或汉字长度，不区分大小写
    private static final String RE_REAL_NAME = "[\u4E00-\u9FFFa-zA-Z·]{2,20}";
//    private static final String RE_REAL_NAME = "/^[\\u4e00-\\u9fa5a-zA-Z·]{2,20}/";
    // 4位数字
    private static final String RE_NUMBER = "[0-9]{4}";

    private static final String RE_ONE_DECIMAL = "^([0-9][0-9]*)+(.[0-9]{1})?$";

    public static boolean isPwd(String password) {
        if (StrUtil.isEmpty(password)) {
            return false;
        }
        return ReUtil.isMatch(RE_PWD, password);
    }

    public static boolean isUserName(String userName) {
        if (StrUtil.isEmpty(userName)) {
            return false;
        }
        return ReUtil.isMatch(RE_USERNAME, userName);
    }

    public static boolean isRealName(String realName) {
        if (StrUtil.isEmpty(realName)) {
            return false;
        }
        return ReUtil.isMatch(RE_REAL_NAME, realName);
    }

    public static boolean isOneDecimal(Double num) {
        if (num == null) {
            return false;
        }
        return ReUtil.isMatch(RE_ONE_DECIMAL, num + "");
    }

    public static boolean isPayPwd(String code) {
        return ReUtil.isMatch(RE_NUMBER, code);
    }

    public static boolean isMobile(String text) {
        return text.length() != 11 ? false : match(text, "^(((13[0-9]{1})|(14[0-9]{1})|(15[0-9]{1})|(17[0-9]{1})|(18[0-9]{1}))+\\d{8})$");
    }

    private static boolean match(String text, String reg) {
        return !StrUtil.isBlank(text) && !StrUtil.isBlank(reg) ? Pattern.compile(reg).matcher(text).matches() : false;
    }

    public static void main(String[] args) {
//        String s = "asdAfghhjk";
//        System.out.println(isPwd(s));
//        String userName = "rwerAqwer";
//        System.out.println(isUserName(userName));
//        String realName = "aa";
//        System.out.println(isRealName(realName));
//        String code = "999l";
//        System.out.println(isPayPwd(code));
//        String userName = "A2sAdfasd_";
//        boolean general = Validator.isGeneral(userName, 6, 12);
//        System.out.println(general);
//        Double d = 1.4;
//        boolean oneDecimal1 = isOneDecimal(d);
//        System.out.println(oneDecimal1);
        boolean tmac123 = isRealName("亚麻跌·干巴爹");
        System.out.println(tmac123);
    }
}
