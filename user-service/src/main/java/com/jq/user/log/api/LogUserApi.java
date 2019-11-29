package com.jq.user.log.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.jq.framework.core.result.ApiResult;
import com.jq.user.api.log.dto.LogUser;
import com.jq.user.log.service.LogUserInnerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class LogUserApi {

    @Resource
    private LogUserInnerService logUserService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Integer save(@RequestBody LogUser logUser) {
        return logUserService.save(logUser);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ApiResult<List> get(@RequestParam(value = "pageNum", defaultValue = "1", required = false) int pageNum,
                               @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        ApiResult<List> result = new ApiResult<>();
        logUserService.get(new Page(pageNum, pageSize), result);

        return result;
    }
}
