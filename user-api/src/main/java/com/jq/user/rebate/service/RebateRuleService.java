package com.jq.user.rebate.service;

import com.jq.user.rebate.dto.RebateBetsDTO;
import com.jq.user.rebate.dto.RebateRuleDTO;
import com.jq.user.rebate.dto.RebateRuleInfoDTO;
import com.jq.user.rebate.fallbackfactory.RebateRuleServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user",path = "/inner/user/rebateRule",url = "${feign-url.user:}",fallbackFactory = RebateRuleServiceFallbackFactory.class)
public interface RebateRuleService {

    /**
     * 查询返水规则组
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "queryRuleListAPI")
    ApiResult<PageInfo<RebateRuleDTO>> queryRuleListAPI(@RequestBody RebateRuleDTO dto);

    /**
     * 新增/修改返水规则组
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "saveRuleAPI")
    ApiResult saveRuleAPI(@RequestBody RebateRuleDTO dto);

    /**
     * 删除返水规则组
     *
     * @param
     * @return
     */
    @PostMapping(value = "delRuleAPI")
    ApiResult delRuleAPI(@RequestBody RebateRuleDTO dto);


    /**
     * 查询返水规则详情
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "queryRuleInfoListAPI")
    ApiResult<PageInfo<RebateRuleInfoDTO>> queryRuleInfoListAPI(@RequestBody RebateRuleInfoDTO dto);

    /**
     * 新增返水规则详情
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "saveRuleInfoAPI")
    ApiResult saveRuleInfoAPI(@RequestBody RebateRuleInfoDTO dto);

    /**
     * 删除返水规则详情
     *
     * @param
     * @return
     */
    @PostMapping(value = "delRuleInfoAPI")
    ApiResult delRuleInfoAPI(@RequestBody RebateRuleInfoDTO dto);

    /**
     * 查看返水规则详情
     *
     * @param id
     * @return
     */
    @GetMapping(value = "getRuleInfoById")
    ApiResult<RebateRuleInfoDTO> getRuleInfoById(@RequestParam Long id);


    /**
     * 根据查询参数统计用户投注与返水数据
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "queryRebateBets")
    ApiResult<RebateBetsDTO> queryRebateBets(@RequestBody RebateBetsDTO dto);


}
