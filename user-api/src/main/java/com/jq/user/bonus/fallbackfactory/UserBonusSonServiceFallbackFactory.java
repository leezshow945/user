package com.jq.user.bonus.fallbackfactory;

import com.jq.user.bonus.dto.UserBonusSonDTO;
import com.jq.user.bonus.service.UserBonusSonService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBonusSonServiceFallbackFactory implements FallbackFactory<UserBonusSonService> {
    private static final Logger logger = LoggerFactory.getLogger(UserBonusSonServiceFallbackFactory.class);

    @Override
    public UserBonusSonService create(Throwable throwable) {
        return new UserBonusSonService() {
            @Override
            public ApiResult<List<UserBonusSonDTO>> queryUserBonusSonApi(Long mainId, String rebate,
                                                                         String amount, String sort) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateApi(UserBonusSonDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusSonDTO>> querySonByMainIdApi(Long mainId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserBonusSonDTO> findByIdApi(Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult saveApi(UserBonusSonDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusSonDTO>> getUserBonusSonApi(Integer settingType, Long mainId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
