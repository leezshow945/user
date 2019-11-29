package com.jq.user.proxy.controller;

import com.jq.user.proxy.dto.UserProxyDTO;
import com.jq.user.proxy.service.UserProxyInnerService;
import com.jq.user.proxy.service.UserProxyService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/inner/user/proxy")
public class UserProxyController implements UserProxyService {

    @Resource
    private UserProxyInnerService userProxyInnerService;

    @Override
    public ApiResult<List<Long>> getAllHighUserIdListApi(@RequestParam List<Long> idList, @RequestParam Long siteId, @RequestParam Boolean isContain) {
        return userProxyInnerService.getAllHighUserIdListApi(idList, siteId, isContain);
    }

    @Override
    public ApiResult<List<UserProxyDTO>> getAllSubUserDirHighUserApi(@RequestParam Long userId, @RequestParam Long siteId) {
        return userProxyInnerService.getAllSubUserDirHighUserApi(userId, siteId);
    }

    @Override
    public ApiResult<List<Map<String, Integer>>> getAllLevelBySiteIdApi(@RequestParam Long siteId, @RequestParam Integer isDemo) {
        return userProxyInnerService.getAllLevelBySiteIdApi(siteId, isDemo);
    }

    @Override
    public ApiResult<Map<Long, List<Long>>> getAllProxyIdApi(@RequestBody UserProxyDTO dto) {
        return userProxyInnerService.getAllProxyIdApi(dto);
    }

    @Override
    public ApiResult<Map<String, Map<Long, Integer>>> getTeamNumberByListApi(@RequestParam List<Long> highUserIdList, @RequestParam String startDate, @RequestParam String endDate,
                                                                             @RequestParam String siteCode) {
        return userProxyInnerService.getTeamNumberByListApi(highUserIdList, startDate, endDate, siteCode);
    }

    @Override
    public ApiResult<Map<String, Integer>> getTeamNewNumberGroupByDateApi(@RequestParam Long userId, @RequestParam String startDate, @RequestParam String endDate,
                                                                          @RequestParam String siteCode) {
        return userProxyInnerService.getTeamNewNumberGroupByDateApi(userId, startDate, endDate, siteCode);
    }
}
