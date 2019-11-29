package com.jq.user.rebate.fallbackfactory;

import com.jq.user.rebate.dto.RebateBetsDTO;
import com.jq.user.rebate.dto.RebateRuleDTO;
import com.jq.user.rebate.dto.RebateRuleInfoDTO;
import com.jq.user.rebate.service.RebateRuleService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RebateRuleServiceFallbackFactory implements FallbackFactory<RebateRuleService> {
    private static final Logger logger = LoggerFactory.getLogger(RebateRuleServiceFallbackFactory.class);

    @Override
    public RebateRuleService create(Throwable throwable) {
        return new RebateRuleService() {
            @Override
            public ApiResult<PageInfo<RebateRuleDTO>> queryRuleListAPI(RebateRuleDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult saveRuleAPI(RebateRuleDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult delRuleAPI(RebateRuleDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<RebateRuleInfoDTO>> queryRuleInfoListAPI(RebateRuleInfoDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult saveRuleInfoAPI(RebateRuleInfoDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult delRuleInfoAPI(RebateRuleInfoDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<RebateRuleInfoDTO> getRuleInfoById(Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<RebateBetsDTO> queryRebateBets(RebateBetsDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
