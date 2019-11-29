package com.jq.user.oldbonus.controller;

import com.jq.user.bonus.dto.UserBonusMainSettingDTO;
import com.jq.user.bonus.dto.UserBonusSettingDTO;
import com.jq.user.bonus.service.UserBonusSettingService;
import com.jq.user.oldbonus.service.UserBonusSettingInnerService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/inner/user/bonusSetting")
public class UserBonusSettingController implements UserBonusSettingService {

    @Resource
    private UserBonusSettingInnerService userBonusSettingInnerService;

    @Override
    public ApiResult<Map<String, Object>> queryBonusSetByLevelApi(@RequestParam Long siteId, @RequestParam Integer level, @RequestParam Integer settingType) {
        return userBonusSettingInnerService.queryBonusSetByLevelApi(siteId, level, settingType);
    }

    @Override
    public ApiResult<List<UserBonusSettingDTO>> getBonusSettingApi(@RequestParam Integer settingType, @RequestParam Long mainId) {
        return userBonusSettingInnerService.getBonusSettingApi(settingType, mainId);
    }

    @Override
    public ApiResult<List<UserBonusSettingDTO>> getBonusSettingByIdApi(@RequestParam Integer settingType, @RequestParam Long id) {
        return userBonusSettingInnerService.getBonusSettingByIdApi(settingType, id);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> getBonusMapApi(@RequestParam Integer settingType, @RequestParam Long mainId) {
        return userBonusSettingInnerService.getBonusMapApi(settingType, mainId);
    }

    @Override
    public ApiResult<Map<String, Object>> queryBonusSetByLevelApi(@RequestParam Long siteId, @RequestParam String pcode,
                                                                  @RequestParam Integer rebateLevel, @RequestParam int settingType) {
        return userBonusSettingInnerService.queryBonusSetByLevelApi(siteId, pcode, rebateLevel, settingType);
    }

    @Override
    public ApiResult deleteApi(@RequestParam Long siteId, @RequestParam Long id,
                               @RequestParam Long mainId, @RequestParam Long updateByUserId,
                               @RequestParam String ip, @RequestParam String url) {
        return userBonusSettingInnerService.deleteApi(siteId, id, mainId, updateByUserId, ip, url);
    }

    @Override
    public ApiResult saveApi(@RequestBody UserBonusSettingDTO dto) {
        return userBonusSettingInnerService.saveApi(dto);
    }

    @Override
    public ApiResult updateApi(@RequestBody UserBonusSettingDTO dto) {
        return userBonusSettingInnerService.updateApi(dto);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> queryUserBonusSettingApi(@RequestParam Long mainId) {
        return userBonusSettingInnerService.queryUserBonusSettingApi(mainId);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> querySettingByQy(@RequestParam String teamDailyAmount,
                                                                 @RequestParam Integer validMember, @RequestParam Integer bonus,
                                                                 @RequestParam Integer daysPer, @RequestParam Long mainId,
                                                                 @RequestParam Long id, @RequestParam Integer bonusMode) {
        return userBonusSettingInnerService.querySettingByQy(teamDailyAmount, validMember, bonus, daysPer, mainId, id, bonusMode);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> querySettingByQyApi(@RequestParam String teamDailyAmount, @RequestParam Long mainId, @RequestParam Long id) {
        return userBonusSettingInnerService.querySettingByQyApi(teamDailyAmount, mainId, id);
    }

    @Override
    public ApiResult updateUserBonusSetting(@RequestBody UserBonusMainSettingDTO dto) {
        return userBonusSettingInnerService.updateUserBonusSetting(dto);
    }
}
