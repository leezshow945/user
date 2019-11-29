package com.jq.user.score.service;

import com.jq.user.score.dto.UserSignDTO;
import com.jq.user.score.fallbackfactory.UserSignServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "user",path = "/inner/user/sign",url = "${feign-url.user:}",fallbackFactory = UserSignServiceFallbackFactory.class)
public interface UserSignService {

    /**
     * 会员签到
     * @return
     */
    @PostMapping(value = "userSignAPI")
    ApiResult userSignAPI(@RequestParam Long siteId,@RequestParam Long userId);

    /**
     * 获取会员签到记录
     * @param siteId
     * @param userId
     * @return
     */
    @GetMapping(value = "getSignRecordAPI")
    ApiResult<List<UserSignDTO>> getSignRecordAPI(@RequestParam Long siteId, @RequestParam Long userId);


}
