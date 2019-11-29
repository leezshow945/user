package com.jq.user.score.fallbackfactory;

import com.jq.user.score.dto.UserSignDTO;
import com.jq.user.score.service.UserSignService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserSignServiceFallbackFactory implements FallbackFactory<UserSignService> {
    private static final Logger logger = LoggerFactory.getLogger(UserSignServiceFallbackFactory.class);

    @Override
    public UserSignService create(Throwable throwable) {
        return new UserSignService() {
            @Override
            public ApiResult userSignAPI(Long siteId, Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserSignDTO>> getSignRecordAPI(Long siteId, Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
