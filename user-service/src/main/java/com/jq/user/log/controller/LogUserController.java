package com.jq.user.log.controller;

import com.jq.user.log.dto.HourlyOnLinesDTO;
import com.jq.user.log.dto.LogQryParamDTO;
import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.log.service.LogUserInnerService;
import com.jq.user.log.service.LogUserService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: levi
 * @Descript: 用户日志
 * @Date: 2018/4/4
 */
@RestController
@RequestMapping(value = "/inner/user/log")
public class LogUserController implements LogUserService{

    @Resource
    private LogUserInnerService logUserInnerService;

    @Override
    public ApiResult addUserLogApi(@RequestBody LogUserDTO dto) {
        return logUserInnerService.addUserLogApi(dto);
    }

    @Override
    public ApiResult<PageInfo<LogUserDTO>> qryUserLoginLogApi(@RequestBody LogQryParamDTO dto) {
        return logUserInnerService.qryUserLoginLogApi(dto);
    }

    @Override
    public ApiResult<PageInfo<LogUserDTO>> qryUserLogByRecordApi(@RequestBody LogQryParamDTO dto) {
        return logUserInnerService.qryUserLogByRecordApi(dto);
    }

    @Override
    public ApiResult deleteBatchApi(@RequestParam String ids) {
        return logUserInnerService.deleteBatchApi(ids);
    }

    @Override
    public ApiResult deleteApi(@RequestParam Long id) {
        return logUserInnerService.deleteApi(id);
    }

    @Override
    public ApiResult deleteByDateApi(@RequestParam String type, @RequestParam String startTime, @RequestParam String endTime) {
        return logUserInnerService.deleteByDateApi(type, startTime, endTime);
    }

    @Override
    public ApiResult<List<HourlyOnLinesDTO>> getHourlyOnLines(@RequestParam Long siteId) {
        return logUserInnerService.getHourlyOnLines(siteId);
    }

    @Override
    public ApiResult<Integer> getPageView(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Long siteId) {
        return logUserInnerService.getPageView(startDate, endDate, siteId);
    }
}
