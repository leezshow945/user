package com.jq.user.oldbonus.constant;

public enum ExamineState {
    TEMP(0,"临时"),
    LOCK(1,"锁定"),
    REVIEWED(2,"已审核");

    private Integer code;
    private String message;

    ExamineState(Integer code, String message) {
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
