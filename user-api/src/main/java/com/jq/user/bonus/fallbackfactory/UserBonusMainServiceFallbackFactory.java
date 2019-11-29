package com.jq.user.bonus.fallbackfactory;

import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.dto.UserBonusMainSonDTO;
import com.jq.user.bonus.service.UserBonusMainService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class UserBonusMainServiceFallbackFactory implements FallbackFactory<UserBonusMainService> {
    private static final Logger logger = LoggerFactory.getLogger(UserBonusMainServiceFallbackFactory.class);

    @Override
    public UserBonusMainService create(Throwable throwable) {
        return new UserBonusMainService() {
            @Override
            public ApiResult<List<Map<String, Object>>> queryUserBonusSetByLevelApi(Integer rebateLevel, Integer settingType,
                                                                                    boolean flag, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<UserBonusMainDTO> getByIdApi(Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateApi(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult saveApi(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult editOrSaveApi(UserBonusMainSonDTO mainSonDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateStatusApi(UserBonusMainSonDTO mainSonDTO) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<Map<String, Object>>> queryContractBonusInfoApi(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<Map<String, Object>>> queryContractBonusApi(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<Map<String, Object>>> queryContractMainSonByIdApi(Long mainId, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<Map<String, Object>>> queryContractWageInfoApi(Long siteId, int pageNum, int pageSize,
                                                                                     String gameCategoryId, String playType,
                                                                                     String toUserName, String userName) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<UserBonusMainDTO>> queryContractRecordByUserIdApi(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusMainDTO>> queryContractRecordApi(UserBonusMainDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusMainDTO>> queryMain(Long siteId, Integer settingType, Integer signStatus) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusMainDTO>> queryBonusMainSonApi(Long siteId, Integer settingType,
                                                                          Integer signStatus) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusMainDTO>> getBonusMainApi(Integer settingType, List<Integer> levelList,
                                                                     List<Long> toUserIdList, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Boolean> updateSettleTime(Long id, Date date) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusMainDTO>> batchQueryUserBonusMain(List<Long> ids, Integer settingType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
