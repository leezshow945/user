package com.jq.user.oldbonus.controller;

import com.jq.user.bonus.dto.UserBonusSonDTO;
import com.jq.user.bonus.service.UserBonusSonService;
import com.jq.user.oldbonus.service.UserBonusSonInnerService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/inner/user/bonusSon")
public class UserBonusSonController implements UserBonusSonService {

    @Resource
    private UserBonusSonInnerService userBonusSonInnerService;

    @Override
    public ApiResult<List<UserBonusSonDTO>> queryUserBonusSonApi(@RequestParam Long mainId, @RequestParam String rebate,
                                                                 @RequestParam String amount, @RequestParam String sort) {
        return userBonusSonInnerService.queryUserBonusSonApi(mainId, rebate, amount, sort);
    }

    @Override
    public ApiResult updateApi(@RequestBody UserBonusSonDTO dto) {
        return userBonusSonInnerService.updateApi(dto);
    }

    @Override
    public ApiResult<List<UserBonusSonDTO>> querySonByMainIdApi(@RequestParam Long mainId) {
        return userBonusSonInnerService.querySonByMainIdApi(mainId);
    }

    @Override
    public ApiResult<UserBonusSonDTO> findByIdApi(@RequestParam Long id) {
        return userBonusSonInnerService.findByIdApi(id);
    }

    @Override
    public ApiResult saveApi(@RequestBody UserBonusSonDTO dto) {
        return userBonusSonInnerService.saveApi(dto);
    }

    @Override
    public ApiResult<List<UserBonusSonDTO>> getUserBonusSonApi(@RequestParam Integer settingType, @RequestParam Long mainId) {
        return userBonusSonInnerService.getUserBonusSonApi(settingType, mainId);
    }
}
