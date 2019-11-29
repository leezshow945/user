package com.jq.user.score.fallbackfactory;

import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.service.RankBonusService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RankBonusServiceFallbackFactory implements FallbackFactory<RankBonusService> {
    private static final Logger logger = LoggerFactory.getLogger(RankBonusServiceFallbackFactory.class);

    @Override
    public RankBonusService create(Throwable throwable) {
        return new RankBonusService() {
            @Override
            public ApiResult<RankBonusConfigDTO> getRankBonus(Long siteId, String siteCode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateRankBonus(RankBonusConfigDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserRankBonusDTO> getUserRankBonus(Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult addRankBonus(LogUserDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
