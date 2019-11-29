package com.jq.user.bonus.fallbackfactory;

import com.jq.user.bonus.dto.UserBonusMainSettingDTO;
import com.jq.user.bonus.dto.UserBonusSettingDTO;
import com.jq.user.bonus.service.UserBonusSettingService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class UserBonusSettingServiceFallbackFactory implements FallbackFactory<UserBonusSettingService> {
    private static final Logger logger = LoggerFactory.getLogger(UserBonusSettingServiceFallbackFactory.class);

    @Override
    public UserBonusSettingService create(Throwable throwable) {
        return new UserBonusSettingService() {
            @Override
            public ApiResult<Map<String, Object>> queryBonusSetByLevelApi(Long siteId, Integer level, Integer settingType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusSettingDTO>> getBonusSettingApi(Integer settingType, Long mainId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<UserBonusSettingDTO>> getBonusSettingByIdApi(Integer settingType, Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<Map<String, Object>>> getBonusMapApi(Integer settingType, Long mainId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Map<String, Object>> queryBonusSetByLevelApi(Long siteId, String pcode,
                                                                          Integer rebateLevel, int settingType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult deleteApi(Long siteId, Long id, Long mainId,
                                       Long updateByUserId, String ip, String url) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult saveApi(UserBonusSettingDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateApi(UserBonusSettingDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<Map<String, Object>>> queryUserBonusSettingApi(Long mainId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<Map<String, Object>>> querySettingByQy(String teamDailyAmount, Integer validMember,
                                                                         Integer bonus, Integer daysPer,
                                                                         Long mainId, Long id, Integer bonusMode) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<Map<String, Object>>> querySettingByQyApi(String teamDailyAmount,
                                                                            Long mainId, Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateUserBonusSetting(UserBonusMainSettingDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
