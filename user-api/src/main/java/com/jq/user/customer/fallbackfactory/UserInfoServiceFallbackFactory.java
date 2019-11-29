package com.jq.user.customer.fallbackfactory;

import com.jq.user.customer.dto.UserInfoDTO;
import com.jq.user.customer.service.UserInfoService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInfoServiceFallbackFactory implements FallbackFactory<UserInfoService> {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceFallbackFactory.class);

    @Override
    public UserInfoService create(Throwable throwable) {
        return new UserInfoService() {
            @Override
            public ApiResult<UserInfoDTO> getUserInfoByIdApi(Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateUserInfoApi(UserInfoDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserInfoDTO> findUserDetailApi(Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserInfoDTO> getUserInfoApi(Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<?> updateUserPicture(Long id, String pictureUrl) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserInfoDTO>> findUserInfoByUserNamesApi(List<String> userNameList, String siteCode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserInfoDTO>> getUserInfoByIdListApi(List<Long> idList, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return null;
            }
        };
    }
}
