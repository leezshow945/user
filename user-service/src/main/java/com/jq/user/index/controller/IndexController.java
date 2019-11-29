package com.jq.user.index.controller;

import com.jq.user.index.dto.IndexDTO;
import com.jq.user.index.service.IndexInnerService;
import com.jq.user.index.service.IndexService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/inner/user/index")
public class IndexController implements IndexService {

    @Resource
    private IndexInnerService indexInnerService;

    @Override
    public ApiResult<List<Long>> findNewUserIdList(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Object siteSign) {
        return indexInnerService.findNewUserIdList(startDate, endDate, siteSign);
    }

    @Override
    public ApiResult<Map<Long, String>> findNewUserMapApi(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Object siteSign) {
        return indexInnerService.findNewUserMapApi(startDate, endDate, siteSign);
    }

    @Override
    public ApiResult<Map<String, Integer>> countNewUserByDateGroup(@RequestParam String startDate, @RequestParam String endDate,
                                                                   @RequestParam Long siteId, @RequestParam(value ="isProxy",required = false) Integer isProxy) {
        return indexInnerService.countNewUserByDateGroup(startDate, endDate, siteId, isProxy);
    }

    @Override
    public ApiResult<Integer> countNewUserByDate(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Long siteId, @RequestParam Integer isProxy) {
        return indexInnerService.countNewUserByDate(startDate, endDate, siteId, isProxy);
    }

    @Override
    public ApiResult<Map<String, Integer>> getUserDisc(@RequestBody IndexDTO dto) {
        return indexInnerService.getUserDisc(dto);
    }
}
