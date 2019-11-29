package com.jq.user.favorite.fallbackfactory;

import com.jq.user.favorite.entity.FavoriteDTO;
import com.jq.user.favorite.service.FavoriteService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.RPCResult;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FavoriteServiceFallbackFactory implements FallbackFactory<FavoriteService> {
    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceFallbackFactory.class);

    @Override
    public FavoriteService create(Throwable throwable) {
        return new FavoriteService() {
            @Override
            public ApiResult addFavoriteApi(List<FavoriteDTO> dtoList) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult cancelFavoriteApi(List<String> gameCodeList, Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }

            @Override
            public ApiResult<List<FavoriteDTO>> searchFavoriteListApi(Long userId) {
                logger.error("fallback method was: {} ,reason : ", Thread.currentThread().getStackTrace()[1].getMethodName(), throwable);
                return RPCResult.fail();
            }
        };
    }
}
