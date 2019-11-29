package com.jq.user.oldbonus.constant;

public enum BonusModel {
    AMOUNT(1, "奖金金额"),
    RATIO(2, "奖金比例");

    private Integer code;
    private String message;

    BonusModel(Integer code, String message) {
        this.code = code;
        this.message = message;
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
