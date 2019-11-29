package com.jq.user.valid.controller;

import com.jq.user.valid.service.ValidUserStatisticsInnerService;
import com.jq.user.valid.service.ValidUserStatisticsService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "inner/user/valid")
public class ValidUserStatisticsController implements ValidUserStatisticsService {

    @Resource
    private ValidUserStatisticsInnerService validUserStatisticsInnerService;


    @Override
    public ApiResult<Integer> countValidUserBySiteIdApi(@RequestParam Long siteId) {
        return validUserStatisticsInnerService.countValidUserBySiteIdApi(siteId);
    }
}
