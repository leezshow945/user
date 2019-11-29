package com.jq.user.bonus.fallbackfactory;

import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.service.UserBonusService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserBonusServiceFallbackFactory implements FallbackFactory<UserBonusService> {
    private static final Logger logger = LoggerFactory.getLogger(UserBonusServiceFallbackFactory.class);

    @Override
    public UserBonusService create(Throwable throwable) {
        return new UserBonusService() {
            @Override
            public ApiResult<List<UserBonusDTO>> queryUserBonusByUserIdApi(Long userId, Long siteId, boolean flag, Integer dataType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateApi(UserBonusDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<UserBonusDTO>> queryUserBonusDividByPageApi(UserBonusDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserBonusDTO> findById(Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusDTO>> queryUserBonusByIds(List ids, Integer examineState) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<UserBonusDTO>> queryUserWagesByPageApi(UserBonusDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateStateApi(Long id, Long userId, String userName, String siteCode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateAllStateApi(String ids, Long userId, String userName, String siteCode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult insertBonusBatch(List<UserBonusDTO> list) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserBonusDTO> getPastPeriodBonusApi(Long mainId, String gameCategory,
                                                                 String playType, Long toUserId,
                                                                 String periods, Integer dataType,
                                                                 Long siteId, Integer bonusCycle) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Long> getBonusTotalByTypeApi(UserBonusDTO userBonusDTO, int dataType, List<Long> ids) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Boolean> checkUnDistributeRecord(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
