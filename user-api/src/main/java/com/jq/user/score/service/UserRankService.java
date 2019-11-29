package com.jq.user.score.service;

import com.jq.user.log.dto.LogUserDTO;
import com.jq.user.score.dto.RankBonusConfigDTO;
import com.jq.user.score.dto.UserRankBonusDTO;
import com.jq.user.score.dto.UserRankDTO;
import com.jq.user.score.dto.UserRankScoreDTO;
import com.jq.user.score.fallbackfactory.UserRankServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "user",path = "/inner/user/rank",url = "${feign-url.user:}",fallbackFactory = UserRankServiceFallbackFactory.class)
public interface UserRankService {


    /**
     * 根据站点id获取默认等级id
     *
     * @param siteId
     * @return rankId
     */
    @GetMapping(value = "getDefaultIdBySiteId")
    ApiResult<Long> getDefaultIdBySiteId(@RequestParam Long siteId);

    /**
     * 根据站点id与page参数获取等级列表数据
     *
     * @param userRankDTO
     * @return
     */
    @GetMapping(value = "queryRankListAPI")
    ApiResult<PageInfo<UserRankDTO>> queryRankListAPI(@RequestBody UserRankDTO userRankDTO);

    /**
     * 根据站点id获取站点等级列表数据
     * @param
     * @return
     */
    @GetMapping(value = "queryRankBySiteIdAPI")
    ApiResult<List<UserRankDTO>> queryRankBySiteIdAPI(@RequestParam Long siteId);

    /**
     * 根据siteId获取站点等级详细配置
     *
     * @param siteId
     * @return UserRankDTO
     */
    @GetMapping(value = "initRankInfoAPI")
    ApiResult<UserRankDTO> initRankInfoAPI(@RequestParam Long siteId);


    /**
     * 根据等级id获取等级详细信息
     *
     * @param rankId
     * @return UserRankDTO
     */
    @GetMapping(value = "getRankInfoByIdAPI")
    ApiResult<UserRankDTO> getRankInfoByIdAPI(@RequestParam Long rankId);

    /**
     * 根据站点id获取最大等级阶级
     * @param siteId
     * @return
     */
    @GetMapping(value = "getMaxRankLevelAPI")
    ApiResult<Integer> getMaxRankLevelAPI(@RequestParam Long siteId);

    /**
     * 新增等级
     * 此接口入参非固定值 为等级积分详情动态入参 故使用纯map格式严格控制
     *
     * @param:rankMap 参数为等级积分数据{
     *            "ORDER_check": "1",
     *            "ORDER_val": 50,
     *            "SIGN_check": "1",
     *            "SIGN_val": 60
     *            }
     * @return
     */
    @PostMapping(value = "addRankInfoAPI")
    ApiResult addRankInfoAPI(@RequestBody UserRankDTO userRankDTO);


    /**
     * 修改等级详情
     * 此接口入参非固定值 为等级积分详情动态入参 故使用纯map格式严格控制
     *
     * @param:rankMap 参数为等级积分数据{
     *            "ORDER_check": "1",
     *            "ORDER_val": 50,
     *            "SIGN_check": "1",
     *            "SIGN_val": 60
     *            }
     * @return
     */
    @PutMapping(value = "updateRankInfoAPI")
    ApiResult updateRankInfoAPI(@RequestBody UserRankDTO userRankDTO);

    /**
     * @Author: levi
     * @Descript: 根据站点id查询所有等级
     * @Date: 2018/6/19
     */
    @GetMapping(value = "getAllRankBySiteIdApi")
    ApiResult<List<String>> getAllRankBySiteIdApi(@RequestParam Long siteId);

    /**
     * 获取用户等级积分获取详情
     *
     * @param siteId
     * @param userId
     * @return
     */
    @GetMapping(value = "getRankScoreInfo")
    ApiResult<List<UserRankScoreDTO>> getRankScoreInfo(@RequestParam Long siteId, @RequestParam Long userId);

    /**
     * 根据用户id与站点获取用户等级与代理上下级关系
     * @param list
     * @return
     */
    @PostMapping(value = "getRankAndRebateLevel")
    ApiResult<List<UserRankDTO>> getRankAndRebateLevel(@RequestBody List<UserRankDTO> list);

    /**
     *获取等级晋级加奖金额
     */
    @GetMapping(value = "getRankUpgradeBonus")
    ApiResult<UserRankBonusDTO> getRankUpgradeBonus(@RequestParam Long userId);

    /**
     *领取等级晋级加奖
     */
    @GetMapping(value = "addRankUpgradeBonus")
    ApiResult addRankUpgradeBonus(@RequestBody LogUserDTO logUserDTO);
    /**
     * 获取站点的等级跳级信息与每日加奖信息
     */
    @GetMapping(value = "getSiteRankInfoBonus")
    ApiResult<RankBonusConfigDTO> getSiteRankInfoBonus(@RequestParam Long siteId);
}
