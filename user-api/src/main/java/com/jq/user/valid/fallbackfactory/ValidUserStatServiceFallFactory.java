package com.jq.user.valid.fallbackfactory;

import com.jq.user.valid.service.ValidUserStatisticsService;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidUserStatServiceFallFactory implements FallbackFactory<ValidUserStatisticsService> {
    private static final Logger logger = LoggerFactory.getLogger(ValidUserStatServiceFallFactory.class);

    @Override
    public ValidUserStatisticsService create(Throwable throwable) {

        return siteId -> {
            logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
            return RPCResult.fail();
        };
    }
}
