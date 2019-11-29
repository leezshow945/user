package com.jq.user.oldbonus.constant;

public enum BonusCycle {
    MONTH(1, "每月一期"),
    TWO_OF_MONTH(2, "每月两期"),
    THREE_OF_MONTH(3, "每月三期"),
    DAILY(4, "每天一期"),;


    private Integer code;
    private String message;

    BonusCycle(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
    public static boolean hasCode(Integer code) {
        for (BonusCycle bonusCycle : BonusCycle.values()) {
            if (bonusCycle.getCode().equals(code)) {
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
