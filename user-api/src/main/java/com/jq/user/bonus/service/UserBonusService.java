package com.jq.user.bonus.service;

import com.jq.user.bonus.dto.UserBonusDTO;
import com.jq.user.bonus.dto.UserBonusMainDTO;
import com.jq.user.bonus.fallbackfactory.UserBonusServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈奖金记录〉
 *
 * @author Json
 * @create 2018/6/19
 */
@FeignClient(value = "user", path = "/inner/user/bonus",url = "${feign-url.user:}",fallbackFactory = UserBonusServiceFallbackFactory.class)
public interface UserBonusService {

    /**
     * 查询奖金记录
     * @param  userId 用户id siteId 站点id isOwner 是否是发起用户 dataType 奖金类型
     * @return
     */
    @GetMapping(value = "queryUserBonusByUserIdApi")
    ApiResult<List<UserBonusDTO>> queryUserBonusByUserIdApi(@RequestParam Long userId,@RequestParam Long siteId,
                                                            @RequestParam boolean flag,@RequestParam Integer dataType);

    /**
     * 修改奖金记录
     * @param dto
     * @return
     */
    @PutMapping(value = "updateApi")
    ApiResult updateApi(@RequestBody UserBonusDTO dto);

    /**
     * 分页查询分红记录
     * @param dto
     * @return
     */
    @PostMapping(value = "queryUserBonusDividByPageApi")
    ApiResult<PageInfo<UserBonusDTO>> queryUserBonusDividByPageApi(@RequestBody UserBonusDTO dto);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    @GetMapping(value = "findById")
    ApiResult<UserBonusDTO> findById(@RequestParam Long id);

    /**
     * 根据id 审核状态 批量查询
     * @param ids id集合
     * @param examineState 审核状态
     * @return
     */
    @GetMapping(value = "queryUserBonusByIds")
    ApiResult<List<UserBonusDTO>> queryUserBonusByIds(@RequestParam List ids,@RequestParam Integer examineState);

    /**
     * @Author: levi
     * @Descript: 分页查询日工资列表
     * @Date: 2018/6/27
     */
    @PostMapping(value = "queryUserWagesByPageApi")
    ApiResult<PageInfo<UserBonusDTO>> queryUserWagesByPageApi(@RequestBody UserBonusDTO dto);

    /**
     * 手动修改审核状态 并分发分红、日工资
     * @param id
     * @param userId
     * @param userName
     * @return
     */
    @PutMapping(value = "updateStateApi")
    ApiResult updateStateApi(@RequestParam Long id, @RequestParam Long userId,
                             @RequestParam String userName,@RequestParam String siteCode);

    /**
     * 批量审核 并分发分红、日工资
     * @param ids id 串
     * @param userId
     * @param userName
     * @return
     */
    @PutMapping(value = "updateAllStateApi")
    ApiResult updateAllStateApi(@RequestParam String ids,@RequestParam Long userId,
                                @RequestParam  String userName,@RequestParam  String siteCode);


    /**
     * 批量保存分红记录
     * @param list
     * @return
     */
    @PostMapping(value = "insertBonusBatch")
    ApiResult insertBonusBatch(@RequestParam  List<UserBonusDTO> list);

    /**
     * @Author: levi
     * @Descript: 分红结算-查询往期分红记录
     * @param mainId 主单id
     * @param gameCategory  游戏类型 (gpc,lhc...）
     * @param playType 六合分组（rebate0,1..类型为lhc时有值）
     * @param toUserId 用户id（接收者）
     * @param periods 期数（允许为空，为空即查所有往期记录。）
     * @param dataType 奖金设置类型 0:分红,1:契约分红,2:日工资,3:契约日工资
     * @param siteId 站点id
     * @return 总计（投注，输赢，返点）
     * @Date: 2018/8/1
     */
    @GetMapping(value = "getPastPeriodBonusApi")
    ApiResult<UserBonusDTO> getPastPeriodBonusApi(@RequestParam  Long mainId,@RequestParam  String gameCategory,
                                                  @RequestParam  String playType,@RequestParam  Long toUserId,
                                                  @RequestParam  String periods,@RequestParam  Integer dataType,
                                                  @RequestParam  Long siteId,@RequestParam Integer bonusCycle);

    /**
     * @Author: levi
     * @Descript: 根据类型统计用户日工资总计
     * @param userBonusDTO 戏类型 (gpc,lhc...）
     * @param dataType 奖金设置类型 2:日工资,3:契约日工资
     * @param ids 用户id集合
     * @return  设置类型对应奖金总计
     * @Date: 2018/9/14
     */
    @PostMapping(value = "getBonusTotalByTypeApi")
    ApiResult<Long> getBonusTotalByTypeApi(@RequestBody UserBonusDTO userBonusDTO, @RequestParam  int dataType,
                                           @RequestParam List<Long> ids);

    /**
     * @Author: levi
     * @Descript: 查询代理是否存在未给下级派发的契约的记录
     * @param userId 用户id
     * @param siteId 站点id
     * @param dataType 数据类型 (1-契约分红,3-契约日工资)
     * @return boolean
     * @Date: 2018/10/25
     */
    @PostMapping(value = "checkUnDistributeRecord")
    ApiResult<Boolean> checkUnDistributeRecord(@RequestBody UserBonusMainDTO dto);
}
