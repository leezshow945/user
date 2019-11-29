package com.jq.user.oldbonus.constant;

public enum DataType {
    CONTRACT_SALARY(1, "契约日工资"),
    CONTRACT_BONUS(2, "契约奖金"),
    DAILY_SALARY(3, "日工资"),
    BONUS(4, "分红");

    private Integer code;
    private String message;

    DataType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static boolean hasCode(Integer code) {
        for (DataType bonusType : DataType.values()) {
            if (bonusType.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
