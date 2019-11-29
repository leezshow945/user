package com.jq.user.refer.support;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈工具类〉
 *
 * @author Json
 * @create 2018/6/20
 */
public class Utils {
    /**
     * URL检查<br>
     * <br>
     * @param pInput     要检查的字符串<br>
     * @return boolean   返回检查结果<br>
     */
    public static boolean isUrl (String pInput) {
        if(pInput == null){
            return false;
        }
        //转换为小写
        pInput = pInput.toLowerCase();
//        String regex = "^((https|http|ftp|rtsp|mms)?://)"
//                + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
//                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
//                + "|" // 允许IP和DOMAIN（域名）
//                + "([0-9a-z_!~*'()-]+\\.)*" // 域名- www.
//                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." // 二级域名
//                + "[a-z]{2,6})" // first level domain- .com or .museum
//                + "(:[0-9]{1,4})?" // 端口- :80
//                + "((/?)|" // a slash isn't required if there is no file name
//                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$";
        String regex="(https|http)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();
    /*    String regEx = "^(http|https|ftp)//://([a-zA-Z0-9//.//-]+(//:[a-zA-"
            + "Z0-9//.&%//$//-]+)*@)?((25[0-5]|2[0-4][0-9]|[0-1]{1}[0-9]{"
            + "2}|[1-9]{1}[0-9]{1}|[1-9])//.(25[0-5]|2[0-4][0-9]|[0-1]{1}"
            + "[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-4][0-9]|"
            + "[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[1-9]|0)//.(25[0-5]|2[0-"
            + "4][0-9]|[0-1]{1}[0-9]{2}|[1-9]{1}[0-9]{1}|[0-9])|([a-zA-Z0"
            + "-9//-]+//.)*[a-zA-Z0-9//-]+//.[a-zA-Z]{2,4})(//:[0-9]+)?(/"
            + "[^/][a-zA-Z0-9//.//,//?//'///////+&%//$//=~_//-@]*)*$";
        Pattern p = Pattern.compile(regEx);
        Matcher matcher = p.matcher(pInput);
        return matcher.matches();*/
    }
}
