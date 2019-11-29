package com.jq.user.log.fallbackfactory;

import com.jq.user.log.dto.HourlyOnLinesDTO;
import com.jq.user.log.dto.LogQryParamDTO;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.service.LogUserService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LogUserServiceFallbackFactory implements FallbackFactory<LogUserService> {
    private static final Logger logger = LoggerFactory.getLogger(LogUserServiceFallbackFactory.class);

    @Override
    public LogUserService create(Throwable throwable) {
        return new LogUserService() {
            @Override
            public ApiResult addUserLogApi(LogUserDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<LogUserDTO>> qryUserLoginLogApi(LogQryParamDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<PageInfo<LogUserDTO>> qryUserLogByRecordApi(LogQryParamDTO dto) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult deleteBatchApi(String ids) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult deleteApi(Long id) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult deleteByDateApi(String type, String startTime, String endTime) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<HourlyOnLinesDTO>> getHourlyOnLines(Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<Integer> getPageView(String startDate, String endDate, Long siteId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
