package com.jq.user.interceptor;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
     throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2, ModelAndView arg3)
     throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2) throws Exception {


        return true;
    }

}
