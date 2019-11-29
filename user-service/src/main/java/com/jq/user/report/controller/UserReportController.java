package com.jq.user.report.controller;

import com.jq.user.report.service.UserReportInnerService;
import com.jq.user.report.service.UserReportService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/inner/user/report")
public class UserReportController implements UserReportService {

    @Resource
    private UserReportInnerService userReportInnerService;


    @Override
    public ApiResult<Map<Long, Integer>> getUserUnLoginNumOfDaysApi(@RequestParam List<Long> userIdList) {
        return userReportInnerService.getUserUnLoginNumOfDaysApi(userIdList);
    }
}
