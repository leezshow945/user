package com.jq.user.score.fallbackfactory;

import com.jq.user.common.InitParameters;
import com.jq.user.score.dto.UserScoreDTO;
import com.jq.user.score.service.UserScoreService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserScoreServiceFallbackFactory implements FallbackFactory<UserScoreService> {
    private static final Logger logger = LoggerFactory.getLogger(UserScoreServiceFallbackFactory.class);

    @Override
    public UserScoreService create(Throwable throwable) {
        return new UserScoreService() {
            @Override
            public ApiResult<PageInfo<UserScoreDTO>> queryScoreListAPI(UserScoreDTO userScoreDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordListAPI(UserScoreDTO userScoreDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<UserScoreDTO>> queryScoreRecordAPI(UserScoreDTO userScoreDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<InitParameters> getScoreListParamsAPI(Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<InitParameters> getScoreRecordListParamsAPI(Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult manualUpdateAPI(UserScoreDTO userScoreDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateUserScore(String userId, String scoreType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateUserScoreByRecharge(String userId, String money) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
