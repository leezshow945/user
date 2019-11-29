package com.jq.user.valid.controller;

import com.jq.user.valid.dto.ValidUserSettingDTO;
import com.jq.user.valid.service.ValidUserSettingInnerService;
import com.jq.user.valid.service.ValidUserSettingService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/inner/user/valid")
public class ValidUserSettingController implements ValidUserSettingService {

    @Resource
    private ValidUserSettingInnerService validUserSettingInnerService;

    @Override
    public ApiResult updateSettingApi(@RequestBody ValidUserSettingDTO dto) {
        return validUserSettingInnerService.updateSettingApi(dto);
    }

    @Override
    public ApiResult<ValidUserSettingDTO> findBySiteIdApi(@RequestParam Long siteId) {
        return validUserSettingInnerService.findBySiteIdApi(siteId);
    }

    @Override
    public ApiResult saveSettingApi(@RequestBody ValidUserSettingDTO dto) {
        return validUserSettingInnerService.saveSettingApi(dto);
    }
}
