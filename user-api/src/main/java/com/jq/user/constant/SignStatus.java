package com.jq.user.constant;

/**
 * @Author: levi
 * @Descript: 契约签订状态
 * @Date: 2018/5/29
 */
public enum SignStatus {
    UN_SIGN(0, "未签订"),
    AGREE(1, "已签订"),
    REJECT(2, "已拒绝");
    private Integer code;
    private String message;

    SignStatus(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
