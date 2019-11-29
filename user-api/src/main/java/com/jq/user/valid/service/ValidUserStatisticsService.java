package com.jq.user.valid.service;

import com.jq.user.valid.fallbackfactory.ValidUserStatServiceFallFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user", path = "/inner/user/valid",url = "${feign-url.user:}",fallbackFactory = ValidUserStatServiceFallFactory.class)
public interface ValidUserStatisticsService {

    /**
     * @param siteId 站点id
     * @Author: levi
     * @Descript: 获取站点有效会员数
     * @Date: 2019/1/3
     */
    @GetMapping(value = "countValidUserBySiteIdApi")
    ApiResult<Integer> countValidUserBySiteIdApi(@RequestParam Long siteId);
}
