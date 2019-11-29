package com.jq.user.score.controller;

import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.dto.UserRankScoreDTO;
import com.jq.user.score.service.UserRankInnerService;
import com.jq.user.score.service.UserRankService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;


/**
 * 用户等级Controller
 */
@RestController
@RequestMapping(value = "/inner/user/rank")
public class UserRankController implements UserRankService {
    @Resource
    private UserRankInnerService userRankInnerService;

    @Override
    public ApiResult<Long> getDefaultIdBySiteId(@RequestParam Long siteId) {
        return userRankInnerService.getDefaultIdBySiteId(siteId);
    }

    @Override
    public ApiResult<PageInfo<UserRankDTO>> queryRankListAPI(@RequestBody UserRankDTO userRankDTO) {
        return userRankInnerService.queryRankListAPI(userRankDTO);
    }

    @Override
    public ApiResult<List<UserRankDTO>> queryRankBySiteIdAPI(@RequestParam Long siteId) {
        return userRankInnerService.queryRankBySiteIdAPI(siteId);
    }

    @Override
    public ApiResult<UserRankDTO> initRankInfoAPI(@RequestParam Long siteId) {
        return userRankInnerService.initRankInfoAPI(siteId);
    }

    @Override
    public ApiResult<UserRankDTO> getRankInfoByIdAPI(@RequestParam Long rankId) {
        return userRankInnerService.getRankInfoByIdAPI(rankId);
    }

    @Override
    public ApiResult<Integer> getMaxRankLevelAPI(@RequestParam Long siteId) {
        return userRankInnerService.getMaxRankLevelAPI(siteId);
    }

    @Override
    public ApiResult addRankInfoAPI(@RequestBody UserRankDTO userRankDTO) {
        return userRankInnerService.addRankInfoAPI(userRankDTO);
    }

    @Override
    public ApiResult updateRankInfoAPI(@RequestBody UserRankDTO userRankDTO) {
        return userRankInnerService.updateRankInfoAPI(userRankDTO);
    }

    @Override
    public ApiResult<List<String>> getAllRankBySiteIdApi(@RequestParam Long siteId) {
        return userRankInnerService.getAllRankBySiteIdApi(siteId);
    }

    @Override
    public ApiResult<List<UserRankScoreDTO>> getRankScoreInfo(@RequestParam Long siteId, @RequestParam Long userId) {
        return userRankInnerService.getRankScoreInfo(siteId, userId);
    }

    @Override
    public ApiResult<List<UserRankDTO>> getRankAndRebateLevel(@RequestBody List<UserRankDTO> list) {
        return userRankInnerService.getRankAndRebateLevel(list);
    }

    @Override
    public ApiResult<UserRankBonusDTO> getRankUpgradeBonus(@RequestParam Long userId) {
        return userRankInnerService.getRankUpgradeBonus(userId);
    }

    @Override
    public ApiResult addRankUpgradeBonus(@RequestBody LogUserDTO logUserDTO) {
        return userRankInnerService.addRankUpgradeBonus(logUserDTO);
    }

    @Override
    public ApiResult<RankBonusConfigDTO> getSiteRankInfoBonus(@RequestParam Long siteId) {
        return userRankInnerService.getSiteRankInfoBonus(siteId);
    }
}
