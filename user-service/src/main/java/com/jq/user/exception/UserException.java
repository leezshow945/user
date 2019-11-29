package com.jq.user.exception;

import com.jq.framework.core.exception.JQException;

public class UserException extends JQException {
    private static final long serialVersionUID = -4037855875276048223L;
    private Integer code;
    private String message;
    public UserException() {
    }

    public UserException(Integer code,String message) {
        this.code=code;
        this.message=message;
    }
    public UserException(Integer code) {
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}
