package com.jq.user.rebate.controller;

import com.jq.user.rebate.dto.RebateResultDTO;
import com.jq.user.rebate.dto.RebateResultInfoDTO;
import com.jq.user.rebate.service.RebateResultInnerService;
import com.jq.user.rebate.service.RebateResultService;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/inner/user/rebateResult")
public class RebateResultController implements RebateResultService {

    @Resource
    private RebateResultInnerService rebateResultInnerService;

    @Override
    public ApiResult executeRebate(@RequestBody RebateResultDTO dto) {
        return rebateResultInnerService.executeRebate(dto);
    }

    @Override
    public ApiResult<PageInfo<RebateResultDTO>> queryRebateResultAPI(@RequestBody RebateResultDTO dto) {
        return rebateResultInnerService.queryRebateResultAPI(dto);
    }

    @Override
    public ApiResult<PageInfo<RebateResultInfoDTO>> queryRebateResultInfoAPI(@RequestBody RebateResultInfoDTO dto) {
        return rebateResultInnerService.queryRebateResultInfoAPI(dto);
    }

    @Override
    public ApiResult executeCancelRebate(@RequestBody RebateResultDTO dto) {
        return rebateResultInnerService.executeCancelRebate(dto);
    }

    @Override
    public ApiResult<RebateResultInfoDTO> sumRebateResultsByIds(@RequestBody RebateResultDTO dto) {
        return rebateResultInnerService.sumRebateResultsByIds(dto);
    }

    @Override
    public ApiResult<Map<Long, Long>> getUserEventMoneyMap(@RequestBody RebateResultDTO dto) {
        return rebateResultInnerService.getUserEventMoneyMap(dto);
    }
}
