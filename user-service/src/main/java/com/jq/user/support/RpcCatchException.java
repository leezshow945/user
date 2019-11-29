//package com.jq.user.support;
//
//import com.jq.user.exception.ExceptionUtil;
//import com.liying.common.service.ApiResult;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class RpcCatchException {
//
//    @Pointcut("execution(* com.jq.user.*.service.impl..*.*(..))")
//    public void scan(){
//
//    }
//
//    @Around("scan()")
//    public ApiResult doAround(ProceedingJoinPoint pjp) throws  Throwable{
//        try {
//            return (ApiResult)pjp.proceed();
//        } catch (Exception e) {
//            return ExceptionUtil.catchException(e);
//        }
//    }
//}
