package com.jq.user.report.fallbackfactory;

import com.liying.common.service.RPCResult;
import com.jq.user.report.service.UserReportService;
import com.liying.common.service.ApiResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserReportServiceFallbackFactory implements FallbackFactory<UserReportService> {
    private static final Logger logger = LoggerFactory.getLogger(UserReportServiceFallbackFactory.class);

    @Override
    public UserReportService create(Throwable throwable) {
        return userIdList -> {
            logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
            return RPCResult.fail();
        };
    }
}
