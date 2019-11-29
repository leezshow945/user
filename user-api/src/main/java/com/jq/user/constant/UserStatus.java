package com.jq.user.constant;

import java.util.HashMap;
import java.util.Map;

public enum UserStatus {
    /**
     * <pre>
     * codeType : USER_STATUS
     * codeName : 用户状态
     * code : 10
     * value : 启用
     * </pre>
     **/
    USER_STATUS_10("USER_STATUS", "用户状态", 10, "启用"),
    /**
     * <pre>
     * codeType : USER_STATUS
     * codeName : 用户状态
     * code : 11
     * value : 停用
     * </pre>
     **/
    USER_STATUS_11("USER_STATUS", "用户状态", 11, "停用"),
    /** <pre>
     * codeType : USER_STATUS
     * codeName : 用户状态
     * code : 21
     * value : 停用投注
     * </pre>
     **/
    USER_STATUS_21("USER_STATUS", "用户状态", 21, "暂停投注"),

    /**
     * <pre>
     * codeType : USER_STATUS
     * codeName : 用户状态
     * code : 31
     * value : 冻结
     * </pre>
     **/
    USER_STATUS_31("USER_STATUS", "用户状态", 31, "冻结"),

    /**
     * <pre>
     * codeType : USER_STATUS
     * codeName : 用户状态
     * code : 41
     * value : 拉黑
     * </pre>
     **/
    USER_STATUS_41("USER_STATUS", "用户状态", 41, "拉黑");



    private String codeType;
    private String codeName;
    private Integer code;
    private String value;

    UserStatus(String codeType, String codeName, Integer code, String value) {
        this.codeType = codeType;
        this.codeName = codeName;
        this.code = code;
        this.value = value;
    }

    public static boolean hasCode(Integer code) {
        for (UserStatus status : UserStatus.values()) {
            if (status.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获得以参数codeType 为codeType的所有枚举 组装成map<code,value>
     *
     * @param codeType
     * @return
     */
    public static Map<Integer, String> getNameByCodeType(String codeType) {
        Map<Integer, String> map = new HashMap<>();
        for (UserStatus e : UserStatus.values()) {
            if (e.getCodeType().equalsIgnoreCase(codeType)) {
                map.put(e.getCode(), e.getValue());
            }
        }
        if (map != null && !map.isEmpty()) {
            return map;
        } else {
            return null;
        }
    }

    public static String getStatusByCode(Integer code){
        if (code==null){
            return null;
        }
        for (UserStatus userStatus : UserStatus.values()) {
            if (userStatus.getCode().equals(code)) {
                return userStatus.getValue();
            }
        }
        return null;
    }
}
