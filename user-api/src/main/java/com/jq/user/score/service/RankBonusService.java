package com.jq.user.score.service;

import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.fallbackfactory.RankBonusServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @Auther: Lee
 * @Date: 2018/11/12 14:36
 */
@FeignClient(value = "user",path = "/inner/user/rankBonus",url = "${feign-url.user:}",fallbackFactory = RankBonusServiceFallbackFactory.class)
public interface RankBonusService {


    /**
     * 根据站点信息获取站点VIP奖励参数信息
     * 站点id与站点code任传一个即可
     */
    @GetMapping(value = "getRankBonus")
    ApiResult<RankBonusConfigDTO> getRankBonus(@RequestParam(required = false) Long siteId, @RequestParam(required = false) String siteCode);


    /**
     * 更新站点信息获取站点VIP奖励参数信息
     */
    @PutMapping(value = "updateRankBonus")
    ApiResult updateRankBonus(@RequestBody RankBonusConfigDTO dto);

    /**
     * 获取用户每日加奖数据
     */
    @GetMapping(value = "getUserRankBonus")
    ApiResult<UserRankBonusDTO> getUserRankBonus(@RequestParam Long userId);


    /**
     * 领取用户每日加奖
     */
    @PostMapping(value = "addRankBonus")
    ApiResult addRankBonus(@RequestBody LogUserDTO dto);


}
