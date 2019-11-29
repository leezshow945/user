package com.jq.user.exception;

import com.jq.framework.core.exception.Code;
import com.jq.user.code.UserCodeEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CodeImpl implements Code {

    public static Map<Integer, String> errorCode = new HashMap<Integer, String>() {
        {
            for(UserCodeEnum codeEnum:UserCodeEnum.values()){
                put(codeEnum.getCode(),codeEnum.getMessage());
            }
        }
    };

    @Override
    public Map<Integer, String> getErrorCode() {
            return errorCode;
    }

}

