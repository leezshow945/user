package com.jq.user.score.controller;

import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.service.RankBonusInnerService;
import com.jq.user.score.service.RankBonusService;
import com.liying.common.service.ApiResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/inner/user/rankBonus")
public class RankBonusController  implements RankBonusService{

    @Resource
    private RankBonusInnerService rankBonusInnerService;

    @Override
    public ApiResult<RankBonusConfigDTO> getRankBonus(@RequestParam(required = false) Long siteId, @RequestParam(required = false) String siteCode) {
        return rankBonusInnerService.getRankBonus(siteId, siteCode);
    }

    @Override
    public ApiResult updateRankBonus(@RequestBody RankBonusConfigDTO dto) {
        return rankBonusInnerService.updateRankBonus(dto);
    }

    @Override
    public ApiResult<UserRankBonusDTO> getUserRankBonus(@RequestParam Long userId) {
        return rankBonusInnerService.getUserRankBonus(userId);
    }

    @Override
    public ApiResult addRankBonus(@RequestBody LogUserDTO dto) {
        return rankBonusInnerService.addRankBonus(dto);
    }
}
