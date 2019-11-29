package com.jq.user.bankcard.fallbackfactory;

import com.jq.user.bankcard.dto.BankCardDTO;
import com.jq.user.bankcard.dto.BankCardQryDTO;
import com.jq.user.bankcard.service.UserBankService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class UserBankServiceFallbackFactory implements FallbackFactory<UserBankService> {
    private static final Logger logger = LoggerFactory.getLogger(UserBankServiceFallbackFactory.class);

    @Override
    public UserBankService create(Throwable throwable) {
        return new UserBankService() {
            @Override
            public ApiResult<BankCardDTO> getBankCardByIdApi(Long bankCardId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<BankCardDTO>> getListBy(BankCardQryDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<String> addBankCardApi(BankCardDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult deleteApi(Long bankCardId, String manUserName, Long manUserId, Long siteId, Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult setDefaultApi(Long bankCardId, String manUserName, Long manUserId, Long siteId, Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }


            @Override
            public ApiResult<Long> addBankCardApi(BankCardDTO dto, Long siteId, String manUserName,
                                                  Long manUserId, Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult enableApi(Long bankCardId, Integer status, String manUserName,
                                       Long manUserId, Long siteId, Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult updateApi(BankCardDTO dto, String manUserName, Long manUserId,
                                       Long siteId, Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<BankCardDTO> getDefaultBankCardApi(Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<BankCardDTO> findByIdApi(Long bankCardId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Boolean> updateBankStatus(Integer type, Long bankId, Integer status,
                                                       String manUserName, Long manUserId, Long siteId,
                                                       Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Boolean> deleteBankApi(Integer type, Long bankCardId, String manUserName, Long manUserId, Long siteId,
                                                    Integer platformType) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
