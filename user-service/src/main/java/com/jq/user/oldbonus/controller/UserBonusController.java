package com.jq.user.oldbonus.controller;

import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.service.UserBonusService;
import com.jq.user.oldbonus.service.UserBonusInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/inner/user/bonus")
public class UserBonusController implements UserBonusService {

    @Resource
    private UserBonusInnerService userBonusInnerService;

    @Override
    public ApiResult<List<UserBonusDTO>> queryUserBonusByUserIdApi(@RequestParam Long userId, @RequestParam Long siteId,
                                                                   @RequestParam boolean flag, @RequestParam Integer dataType) {
        return userBonusInnerService.queryUserBonusByUserIdApi(userId, siteId, flag, dataType);
    }

    @Override
    public ApiResult updateApi(@RequestBody UserBonusDTO dto) {
        return userBonusInnerService.updateApi(dto);
    }

    @Override
    public ApiResult<PageInfo<UserBonusDTO>> queryUserBonusDividByPageApi(@RequestBody UserBonusDTO dto) {
        return userBonusInnerService.queryUserBonusDividByPageApi(dto);
    }

    @Override
    public ApiResult<UserBonusDTO> findById(@RequestParam Long id) {
        return userBonusInnerService.findById(id);
    }

    @Override
    public ApiResult<List<UserBonusDTO>> queryUserBonusByIds(@RequestParam List ids, @RequestParam Integer examineState) {
        return userBonusInnerService.queryUserBonusByIds(ids, examineState);
    }

    @Override
    public ApiResult<PageInfo<UserBonusDTO>> queryUserWagesByPageApi(@RequestBody UserBonusDTO dto) {
        return userBonusInnerService.queryUserWagesByPageApi(dto);
    }

    @Override
    public ApiResult updateStateApi(@RequestParam Long id, @RequestParam Long userId,
                                    @RequestParam String userName, @RequestParam String siteCode) {
        return userBonusInnerService.updateStateApi(id, userId, userName, siteCode);
    }

    @Override
    public ApiResult updateAllStateApi(@RequestParam String ids, @RequestParam  Long userId,
                                       @RequestParam  String userName, @RequestParam  String siteCode) {
        return userBonusInnerService.updateAllStateApi(ids, userId, userName, siteCode);
    }

    @Override
    public ApiResult insertBonusBatch(@RequestParam  List<UserBonusDTO> list) {
        return userBonusInnerService.insertBonusBatch(list);
    }

    @Override
    public ApiResult<UserBonusDTO> getPastPeriodBonusApi(@RequestParam  Long mainId, @RequestParam  String gameCategory, @RequestParam  String playType,
                                                         @RequestParam  Long toUserId, @RequestParam  String periods, @RequestParam  Integer dataType, @RequestParam Long siteId,@RequestParam Integer bonusCycle) {
        return userBonusInnerService.getPastPeriodBonusApi(mainId, gameCategory, playType, toUserId, periods, dataType, siteId,bonusCycle);
    }

    @Override
    public ApiResult<Long> getBonusTotalByTypeApi(@RequestBody UserBonusDTO userBonusDTO, @RequestParam int dataType, @RequestParam List<Long> ids) {
        return userBonusInnerService.getBonusTotalByTypeApi(userBonusDTO, dataType, ids);
    }

    @Override
    public ApiResult<Boolean> checkUnDistributeRecord(@RequestBody UserBonusMainDTO dto) {
        return userBonusInnerService.checkUnDistributeRecord(dto);
    }
}
