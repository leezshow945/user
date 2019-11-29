package com.jq.user.bonus.service;

import com.jq.user.bonus.dto.UserBonusSonDTO;
import com.jq.user.bonus.fallbackfactory.UserBonusSonServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 〈分红设置〉
 *
 * @author Json
 * @create 2018/6/19
 */
@FeignClient(value = "user" ,path = "/inner/user/bonusSon",url = "${feign-url.user:}",fallbackFactory = UserBonusSonServiceFallbackFactory.class)
public interface UserBonusSonService {

    /**
     *  根据设置主表id 查询设置子表数据
     * @param mainId 主表id rebate 返点要求  amount 金额 sort 排序
     * @return
     */
    @GetMapping(value = "queryUserBonusSonApi")
    ApiResult<List<UserBonusSonDTO>> queryUserBonusSonApi(@RequestParam Long mainId,@RequestParam String rebate,
                                                          @RequestParam String amount,@RequestParam String sort);

    /**
     * 修改奖金设置字表
     * @return
     */
    @PutMapping(value = "updateApi")
    ApiResult updateApi(@RequestBody UserBonusSonDTO dto);

    /**
     * 根据设置主表id查询子表数据
     * mainId 为空时查询所有细单
     * @param mainId
     * @return
     */
    @GetMapping(value = "querySonByMainIdApi")
    ApiResult<List<UserBonusSonDTO>> querySonByMainIdApi(@RequestParam Long mainId);



    /**
     * 根据id查询 设置子单
     * @param id
     * @return
     */
    @GetMapping(value = "findByIdApi")
    ApiResult<UserBonusSonDTO> findByIdApi(@RequestParam Long id);

    /**
     * 新增 分红设置子单
     * @param dto
     * @return
     */
    @PostMapping(value = "saveApi")
    ApiResult saveApi(@RequestBody UserBonusSonDTO dto);

    /**
     * @Author: levi
     * @Descript: 获取分红细单规则
     * @param settingType  0-分红,1-契约分红
     * @param mainId 主单id
     * @Date: 2018/6/28
     */
    @GetMapping(value = "getUserBonusSonApi")
    ApiResult<List<UserBonusSonDTO>> getUserBonusSonApi(@RequestParam Integer settingType, @RequestParam Long mainId);

}
