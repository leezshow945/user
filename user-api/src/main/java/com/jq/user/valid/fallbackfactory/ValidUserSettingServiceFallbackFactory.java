package com.jq.user.valid.fallbackfactory;

import com.jq.user.valid.dto.ValidUserSettingDTO;
import com.jq.user.valid.service.ValidUserSettingService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: Lee
 * @Date: 2019/2/27 15:16
 */
@Component
public class ValidUserSettingServiceFallbackFactory implements FallbackFactory<ValidUserSettingService> {
    private static final Logger logger = LoggerFactory.getLogger(ValidUserSettingServiceFallbackFactory.class);

    @Override
    public ValidUserSettingService create(Throwable throwable) {

        return new ValidUserSettingService() {
            @Override
            public ApiResult updateSettingApi(ValidUserSettingDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<ValidUserSettingDTO> findBySiteIdApi(Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult saveSettingApi(ValidUserSettingDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

        };
    }

}
