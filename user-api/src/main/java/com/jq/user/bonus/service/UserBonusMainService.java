package com.jq.user.bonus.service;

import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.dto.UserBonusMainSonDTO;
import com.jq.user.bonus.fallbackfactory.UserBonusMainServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 〈奖金设置〉
 *
 * @author Json
 * @create 2018/6/19
 */
@FeignClient(value = "user", path = "/inner/user/bonusMain",url = "${feign-url.user:}",fallbackFactory = UserBonusMainServiceFallbackFactory.class)
public interface UserBonusMainService {


    /**
     * 根据玩法id 代理层级 奖金类型查询奖金设置列表
     * @param rebateLevel
     * @param settingType
     * @param flag
     * @param siteId
     * @return
     */
    @GetMapping(value = "queryUserBonusSetByLevelApi")
    ApiResult<List<Map<String,Object>>> queryUserBonusSetByLevelApi(@RequestParam Integer rebateLevel ,
                                                                    @RequestParam Integer settingType,
                                                                    @RequestParam boolean flag,@RequestParam Long siteId);

    /**
     * 根据id查询奖金设置主表
     * @param id
     * @return
     */
    @GetMapping(value = "getByIdApi")
    ApiResult<UserBonusMainDTO> getByIdApi(@RequestParam Long id);

    /**
     * 修改奖金设置主表
     * @param dto
     * @return
     */
    @PutMapping(value = "updateApi")
    ApiResult updateApi(@RequestBody UserBonusMainDTO dto);

    /**
     * 新增奖金设置主表
     * @param dto
     * @return
     */
    @PostMapping(value = "saveApi")
    ApiResult saveApi(@RequestBody UserBonusMainDTO dto);

    /**
     * 修改或者新增分红设置
     * @param mainSonDTO
     * @return
     */
    @PutMapping(value = "editOrSaveApi")
    ApiResult editOrSaveApi(@RequestBody UserBonusMainSonDTO mainSonDTO);

    /**
     * 修改代理分红设置子单删除状态
     * @param mainSonDTO
     * @return
     */
    @PutMapping(value = "updateStatusApi")
    ApiResult updateStatusApi(@RequestBody UserBonusMainSonDTO mainSonDTO);

    /**
     * 分页查询 契约分红设置
     * @param siteId 站点id
     * @param pageNum
     * @param pageSize
     * @param gameCategoryId 游戏类型
     * @param playType lhc玩法组合
     * @param toUserName 接收人名称
     * @param userName 发起人名称
     * @param settingType 设置类型
     * @return  create_time, 创建时间
    update_time, 修改时间
    id, 主表id
    to_user_id, 接收用户id
    user_id, 发起用户id
    game_category_id, 游戏分类
    sign_status, 签订状态
    user_name AS to_user_name, 接收用户名
    user_name AS user_name 发起用户名
    is_del 0-未失效,1-已失效
     *
     */
    @PostMapping(value = "queryContractBonusInfoApi")
    ApiResult<PageInfo<Map<String,Object>>> queryContractBonusInfoApi(@RequestBody UserBonusMainDTO dto);

    /**
     * 分页查询 契约工资设置
     * @param siteId 站点id
     * @param pageNum
     * @param pageSize
     * @param gameCategoryId 游戏类型
     * @param playType lhc玩法组合
     * @param toUserName 接收人名称
     * @param userName 发起人名称
     * @param settingType 设置类型
     * @return  create_time, 创建时间
    update_time, 修改时间
    id, 主表id
    to_user_id, 接收用户id
    user_id, 发起用户id
    game_category_id, 游戏分类
    sign_status, 签订状态
    user_name AS to_user_name, 接收用户.queryContractRecordByUserIdApi名
    user_name AS user_name 发起用户名
    sign_time  签订时间
    is_del  1-已失效,0-未失效
     *
     */
    @PostMapping(value = "queryContractBonusApi")
    ApiResult<PageInfo<Map<String,Object>>> queryContractBonusApi(@RequestBody UserBonusMainDTO dto);

    /**
     * 根据主表id 查询契约分红设置详情
     * @param mainId
     * @param siteId
     * @return UserBonusMainDTO UserBonusSonDTO
     */
    @GetMapping(value = "queryContractMainSonByIdApi")
    ApiResult<List<Map<String,Object>>> queryContractMainSonByIdApi(@RequestParam Long mainId,@RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 分页查询契约日工资设置
     * @Date: 2018/6/27
     */
    @GetMapping(value = "queryContractWageInfoApi")
    ApiResult<PageInfo<Map<String,Object>>> queryContractWageInfoApi(@RequestParam Long siteId,@RequestParam int pageNum,
                                                                     @RequestParam int pageSize,@RequestParam String gameCategoryId,
                                                                     @RequestParam String playType,@RequestParam String toUserName,
                                                                     @RequestParam String userName);

    /**
     *获取用户的契约签订记录 包含子单
     * @param userId 接收用户id
     * @param settingType 1 契约分红  3 契约日工资
     * @param siteId 站点id
     * @param signStatus 签约状态
     * @param gameCategoryId 游戏类型
     * @param startTime 时间段
     * @param endTime
     * @return
     */
    @PostMapping(value = "queryContractRecordByUserIdApi")
    ApiResult<PageInfo<UserBonusMainDTO>> queryContractRecordByUserIdApi(@RequestBody UserBonusMainDTO dto);

    /**
     * 条件查询 发起用户 契约记录 不包含子单
     * @param dto siteId 不为空 userId不为空 settingType 不为空
     * @return
     */
    @PostMapping(value = "queryContractRecordApi")
    ApiResult<List<UserBonusMainDTO>> queryContractRecordApi(@RequestBody UserBonusMainDTO dto);

    /**
     * @Author: levi
     * @Descript: 查询主单记录
     * @param siteId 站点id
     * @param settingType 设置类型(0:分红设置,1:契约分红设置,2日工资设置,3:契约日工资设置)
     * @param signStatus 契约签订状态(0:未签订，1:同意，2:拒绝)
     * @Date: 2018/6/28
     */
    @GetMapping(value = "queryMain")
    ApiResult<List<UserBonusMainDTO>> queryMain(@RequestParam Long siteId, @RequestParam Integer settingType,
                                                @RequestParam Integer signStatus);


    /**
     * 查询奖金设置 包含子单
     * @param siteId
     * @param settingType 设置类型(0:分红设置,1:契约分红设置,2日工资设置,3:契约日工资设置)
     * @param signStatus 契约签订状态(0:未签订，1:同意，2:拒绝)
     * 注意：不同数据类型，排序优先级不同
    分红：返点要求>团队日量（团队总量）>有效会员
    契约分红：金额>有效会有
    日工资：返点要求>团队实际亏损>团队日量>有效会员
    契约日工资：日量>有效会员
     * @return
     */
    @GetMapping(value = "queryBonusMainSonApi")
    ApiResult<List<UserBonusMainDTO>> queryBonusMainSonApi(@RequestParam Long siteId,@RequestParam Integer settingType,
                                                           @RequestParam Integer signStatus);

    /**
     * @Author: levi
     * @Descript: 查询分红设置主单，代理契约分红主单记录
     * @param settingType 设置类型(0:分红设置,1:契约分红设置)
     * @param levelList 层级集合
     * @param toUserIdList 接收契约用户id集合
     * @param siteId 站点Id
     * @Date: 2018/7/24
     */
    @GetMapping(value = "getBonusMainApi")
    ApiResult<List<UserBonusMainDTO>> getBonusMainApi(@RequestParam Integer settingType,@RequestParam List<Integer> levelList,
                                                      @RequestParam List<Long> toUserIdList, @RequestParam Long siteId);

    /**
     * @Author: levi
     * @Descript: 更新结算时间
     * @param id 主单id
     * @param date 日期
     * @Date: 2018/11/6
     */
    @PutMapping(value = "updateSettleTime")
    ApiResult<Boolean> updateSettleTime(@RequestParam Long id,@RequestParam Date date);

    /**
     * 根据主单id批量查询设置 包含子单
     *
     * @param ids 主单id集合
     * @param settingType 设置类型
     * @return
     */
    @GetMapping(value = "batchQueryUserBonusMain")
    ApiResult<List<UserBonusMainDTO>> batchQueryUserBonusMain(@RequestParam List<Long> ids,@RequestParam Integer settingType);

}
