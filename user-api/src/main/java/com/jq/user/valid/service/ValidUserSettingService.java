package com.jq.user.valid.service;

import com.jq.user.valid.dto.ValidUserSettingDTO;
import com.jq.user.valid.fallbackfactory.ValidUserSettingServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user", path = "/inner/user/valid", url = "${feign-url.user:}", fallbackFactory = ValidUserSettingServiceFallbackFactory.class)
public interface ValidUserSettingService {

    @PutMapping(value = "updateSettingApi")
    ApiResult updateSettingApi(@RequestBody ValidUserSettingDTO dto);

    @GetMapping(value = "findBySiteIdApi")
    ApiResult<ValidUserSettingDTO> findBySiteIdApi(@RequestParam Long siteId);

    @PostMapping(value = "saveSettingApi")
    ApiResult saveSettingApi(@RequestBody ValidUserSettingDTO dto);
}
