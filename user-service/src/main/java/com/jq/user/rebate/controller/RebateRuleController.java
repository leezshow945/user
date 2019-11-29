package com.jq.user.rebate.controller;

import com.jq.user.rebate.dto.RebateBetsDTO;
import com.jq.user.rebate.dto.RebateRuleDTO;
import com.jq.user.rebate.dto.RebateRuleInfoDTO;
import com.jq.user.rebate.service.RebateRuleInnerService;
import com.jq.user.rebate.service.RebateRuleService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/inner/user/rebateRule")
public class RebateRuleController  implements RebateRuleService{

    @Resource
    private RebateRuleInnerService rebateRuleInnerService;

    @Override
    public ApiResult<PageInfo<RebateRuleDTO>> queryRuleListAPI(@RequestBody RebateRuleDTO dto) {
        return rebateRuleInnerService.queryRuleListAPI(dto);
    }

    @Override
    public ApiResult saveRuleAPI(@RequestBody RebateRuleDTO dto) {
        return rebateRuleInnerService.saveRuleAPI(dto);
    }

    @Override
    public ApiResult delRuleAPI(@RequestBody RebateRuleDTO dto) {
        return rebateRuleInnerService.delRuleAPI(dto);
    }

    @Override
    public ApiResult<PageInfo<RebateRuleInfoDTO>> queryRuleInfoListAPI(@RequestBody RebateRuleInfoDTO dto) {
        return rebateRuleInnerService.queryRuleInfoListAPI(dto);
    }

    @Override
    public ApiResult saveRuleInfoAPI(@RequestBody RebateRuleInfoDTO dto) {
        return rebateRuleInnerService.saveRuleInfoAPI(dto);
    }

    @Override
    public ApiResult delRuleInfoAPI(@RequestBody RebateRuleInfoDTO dto) {
        return rebateRuleInnerService.delRuleInfoAPI(dto);
    }

    @Override
    public ApiResult<RebateRuleInfoDTO> getRuleInfoById(@RequestParam Long id) {
        return rebateRuleInnerService.getRuleInfoById(id);
    }

    @Override
    public ApiResult<RebateBetsDTO> queryRebateBets(@RequestBody RebateBetsDTO dto) {
        return rebateRuleInnerService.queryRebateBets(dto);
    }


}
