package com.jq.user.oldbonus.controller;

import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.dto.UserBonusMainSonDTO;
import com.jq.user.bonus.service.UserBonusMainService;
import com.jq.user.oldbonus.service.UserBonusMainInnerService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "inner/user/bonusMain")
public class UserBonusMainController implements UserBonusMainService {

    @Resource
    private UserBonusMainInnerService userBonusMainInnerService;


    @Override
    public ApiResult<List<Map<String, Object>>> queryUserBonusSetByLevelApi(@RequestParam Integer rebateLevel, @RequestParam Integer settingType,
                                                                            @RequestParam boolean flag, @RequestParam Long siteId) {
        return userBonusMainInnerService.queryUserBonusSetByLevelApi(rebateLevel, settingType, flag, siteId);
    }

    @Override
    public ApiResult<UserBonusMainDTO> getByIdApi(@RequestParam Long id) {
        return userBonusMainInnerService.getByIdApi(id);
    }

    @Override
    public ApiResult updateApi(@RequestBody UserBonusMainDTO dto) {
        return userBonusMainInnerService.updateApi(dto);
    }

    @Override
    public ApiResult saveApi(@RequestBody UserBonusMainDTO dto) {
        return userBonusMainInnerService.saveApi(dto);
    }

    @Override
    public ApiResult editOrSaveApi(@RequestBody UserBonusMainSonDTO mainSonDTO) {
        return userBonusMainInnerService.editOrSaveApi(mainSonDTO);
    }

    @Override
    public ApiResult updateStatusApi(@RequestBody UserBonusMainSonDTO mainSonDTO) {
        return userBonusMainInnerService.updateStatusApi(mainSonDTO);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryContractBonusInfoApi(@RequestBody UserBonusMainDTO dto) {
        return userBonusMainInnerService.queryContractBonusInfoApi(dto);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryContractBonusApi(@RequestBody UserBonusMainDTO dto) {
        return userBonusMainInnerService.queryContractBonusApi(dto);
    }

    @Override
    public ApiResult<List<Map<String, Object>>> queryContractMainSonByIdApi(@RequestParam Long mainId, @RequestParam Long siteId) {
        return userBonusMainInnerService.queryContractMainSonByIdApi(mainId, siteId);
    }

    @Override
    public ApiResult<PageInfo<Map<String, Object>>> queryContractWageInfoApi(@RequestParam Long siteId, @RequestParam int pageNum,
                                                                             @RequestParam int pageSize, @RequestParam String gameCategoryId,
                                                                             @RequestParam String playType, @RequestParam String toUserName,
                                                                             @RequestParam String userName) {
        return userBonusMainInnerService.queryContractWageInfoApi(siteId, pageNum, pageSize, gameCategoryId, playType, toUserName, userName);
    }

    @Override
    public ApiResult<PageInfo<UserBonusMainDTO>> queryContractRecordByUserIdApi(@RequestBody UserBonusMainDTO dto) {
        return userBonusMainInnerService.queryContractRecordByUserIdApi(dto);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> queryContractRecordApi(@RequestBody UserBonusMainDTO dto) {
        return userBonusMainInnerService.queryContractRecordApi(dto);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> queryMain(@RequestParam Long siteId, @RequestParam Integer settingType, @RequestParam Integer signStatus) {
        return userBonusMainInnerService.queryMain(siteId, settingType, signStatus);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> queryBonusMainSonApi(@RequestParam Long siteId, @RequestParam Integer settingType, @RequestParam Integer signStatus) {
        return userBonusMainInnerService.queryBonusMainSonApi(siteId, settingType, signStatus);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> getBonusMainApi(@RequestParam Integer settingType, @RequestParam List<Integer> levelList, @RequestParam List<Long> toUserIdList, @RequestParam Long siteId) {
        return userBonusMainInnerService.getBonusMainApi(settingType, levelList, toUserIdList, siteId);
    }

    @Override
    public ApiResult<Boolean> updateSettleTime(@RequestParam Long id, @RequestParam Date date) {
        return userBonusMainInnerService.updateSettleTime(id, date);
    }

    @Override
    public ApiResult<List<UserBonusMainDTO>> batchQueryUserBonusMain(@RequestParam List<Long> ids, @RequestParam Integer settingType) {
        return userBonusMainInnerService.batchQueryUserBonusMain(ids, settingType);
    }
}
