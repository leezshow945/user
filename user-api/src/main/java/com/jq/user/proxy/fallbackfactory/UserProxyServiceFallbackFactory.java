package com.jq.user.proxy.fallbackfactory;

import com.jq.user.proxy.dto.UserProxyDTO;
import com.jq.user.proxy.service.UserProxyService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserProxyServiceFallbackFactory implements FallbackFactory<UserProxyService> {
    private static final Logger logger = LoggerFactory.getLogger(UserProxyServiceFallbackFactory.class);

    @Override
    public UserProxyService create(Throwable throwable) {
        return new UserProxyService() {
            @Override
            public ApiResult<List<Long>> getAllHighUserIdListApi(List<Long> idList, Long siteId, Boolean isContain) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserProxyDTO>> getAllSubUserDirHighUserApi(Long userId, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<Map<String, Integer>>> getAllLevelBySiteIdApi(Long siteId, Integer isDemo) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<Long, List<Long>>> getAllProxyIdApi(UserProxyDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<String, Map<Long, Integer>>> getTeamNumberByListApi(List<Long> highUserIdList, String startDate,
                                                                                     String endDate, String siteCode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<String, Integer>> getTeamNewNumberGroupByDateApi(Long userId, String startDate, String endDate, String siteCode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
