package com.jq.user.rebate.fallbackfactory;

import com.jq.user.rebate.dto.RebateResultDTO;
import com.jq.user.rebate.dto.RebateResultInfoDTO;
import com.jq.user.rebate.service.RebateResultService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RebateResultServiceFallbackFactory implements FallbackFactory<RebateResultService> {
    private static final Logger logger = LoggerFactory.getLogger(RebateResultServiceFallbackFactory.class);

    @Override
    public RebateResultService create(Throwable throwable) {
        return new RebateResultService() {
            @Override
            public ApiResult executeRebate(RebateResultDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<RebateResultDTO>> queryRebateResultAPI(RebateResultDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<RebateResultInfoDTO>> queryRebateResultInfoAPI(RebateResultInfoDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult executeCancelRebate(RebateResultDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<RebateResultInfoDTO> sumRebateResultsByIds(RebateResultDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<Long, Long>> getUserEventMoneyMap(RebateResultDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
