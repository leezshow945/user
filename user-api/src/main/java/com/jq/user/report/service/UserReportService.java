package com.jq.user.report.service;

import com.jq.user.report.fallbackfactory.UserReportServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user", path = "/inner/user/report", url = "${feign-url.user:}", fallbackFactory = UserReportServiceFallbackFactory.class)
public interface UserReportService {

    /**
     * @param userIdList 用户id集合
     * @return map key=用户id, value=未登录天数
     * @Author: levi
     * @Descript: 获取用户未登录天数
     * @Date: 2018/12/24
     */
    @GetMapping(value = "getUserUnLoginNumOfDaysApi")
    ApiResult<Map<Long, Integer>> getUserUnLoginNumOfDaysApi(@RequestParam List<Long> userIdList);
}
