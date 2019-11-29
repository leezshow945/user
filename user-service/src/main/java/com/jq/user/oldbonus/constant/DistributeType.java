package com.jq.user.oldbonus.constant;

public enum DistributeType {
    AUTO_DISTRIBUTE(1,"自动派发"),
    UN_AUTO(0,"非自动派发"),;

    private Integer code;
    private String message;

    DistributeType(Integer code, String message) {
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
