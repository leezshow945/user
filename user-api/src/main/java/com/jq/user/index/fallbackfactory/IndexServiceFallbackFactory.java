package com.jq.user.index.fallbackfactory;

import com.jq.user.index.dto.IndexDTO;
import com.jq.user.index.service.IndexService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component("UserIndexServiceFallbackFactory")
public class IndexServiceFallbackFactory implements FallbackFactory<IndexService> {
    private static final Logger logger = LoggerFactory.getLogger(IndexServiceFallbackFactory.class);

    @Override
    public IndexService create(Throwable throwable) {
        return new IndexService() {
            @Override
            public ApiResult<List<Long>> findNewUserIdList(String startDate, String endDate, Object siteSign) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<Long, String>> findNewUserMapApi(String startDate, String endDate, Object siteSign) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<String, Integer>> countNewUserByDateGroup(String startDate, String endDate,
                                                                           Long siteId, Integer isProxy) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Integer> countNewUserByDate(String startDate, String endDate, Long siteId, Integer isProxy) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<String, Integer>> getUserDisc(IndexDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
