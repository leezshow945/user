package com.jq.user.oldbonus.support;

import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/7/2
 */
public class Utils {

    private static String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};


    public static StringBuffer delEndComma(StringBuffer sb){
        if(null==sb|| StringUtils.isEmpty(sb.toString())){
            return new StringBuffer();
        }
        if(sb.toString().endsWith(",")){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb;
    }

    public static synchronized String genSerialNo(String head) {
        String str = (head == null ? "" : head) + getUUID();
        return str.toUpperCase();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String randomUUID() {
        StringBuffer stringBuffer = new StringBuffer();
        String uuid = getUUID();

        for(int i = 0; i < 8; ++i) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int strInteger = Integer.parseInt(str, 16);
            stringBuffer.append(chars[strInteger % chars.length]);
        }

        return stringBuffer.toString();
    }

    public static StringBuffer deleteLastChar(StringBuffer sb) {
        return sb != null && sb.length() > 0 ? sb.deleteCharAt(sb.length() - 1) : new StringBuffer("");
    }
}
