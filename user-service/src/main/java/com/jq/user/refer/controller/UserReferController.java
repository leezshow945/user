package com.jq.user.refer.controller;


import com.jq.user.refer.dto.UserReferDTO;
import com.jq.user.refer.service.UserReferInnerService;
import com.jq.user.refer.service.UserReferService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 〈推广链接Controller〉
 *
 * @author Json
 * @create 2018/4/25
 */
@RestController
@RequestMapping(value = "/inner/user/refer")
public class UserReferController implements UserReferService {

    @Resource
    private UserReferInnerService userReferInnerService;


    @Override
    public ApiResult<PageInfo<UserReferDTO>> getListByConditionApi(@RequestBody UserReferDTO dto) {
        return userReferInnerService.getListByConditionApi(dto);
    }

    @Override
    public ApiResult saveApi(@RequestBody UserReferDTO dto) {
        return userReferInnerService.saveApi(dto);
    }

    @Override
    public ApiResult<UserReferDTO> getByIdApi(@RequestParam Long referId) {
        return userReferInnerService.getByIdApi(referId);
    }

    @Override
    public ApiResult updateApi(@RequestBody UserReferDTO dto) {
        return userReferInnerService.updateApi(dto);
    }

    @Override
    public ApiResult updateStatusApi(@RequestBody UserReferDTO dto) {
        return userReferInnerService.updateStatusApi(dto);
    }

    @Override
    public ApiResult deleteApi(@RequestBody UserReferDTO dto) {
        return userReferInnerService.deleteApi(dto);
    }

    @Override
    public ApiResult<List<UserReferDTO>> getByDomainUrlApi(@RequestParam Long siteId, @RequestParam Integer linkType,
                                                           @RequestParam String domainUrl) {
        return userReferInnerService.getByDomainUrlApi(siteId, linkType, domainUrl);
    }

    @Override
    public ApiResult<Map<Long, String>> getUserNameById(@RequestParam List<Long> idList) {
        return userReferInnerService.getUserNameById(idList);
    }
}
