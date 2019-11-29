package com.jq.user.rebate.service;

import com.jq.user.rebate.dto.RebateResultDTO;
import com.jq.user.rebate.dto.RebateResultInfoDTO;
import com.jq.user.rebate.fallbackfactory.RebateResultServiceFallbackFactory;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(value = "user",path = "/inner/user/rebateResult",url = "${feign-url.user:}",fallbackFactory = RebateResultServiceFallbackFactory.class)
public interface RebateResultService {
    /**
     * 执行返水操作
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "executeRebate")
    ApiResult executeRebate(@RequestBody RebateResultDTO dto);

    /**
     * 查询返水结果记录
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "queryRebateResultAPI")
    ApiResult<PageInfo<RebateResultDTO>> queryRebateResultAPI(@RequestBody RebateResultDTO dto);

    /**
     * 查询返水结果详细记录
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "queryRebateResultInfoAPI")
    ApiResult<PageInfo<RebateResultInfoDTO>> queryRebateResultInfoAPI(@RequestBody RebateResultInfoDTO dto);

    /**
     * 执行冲销操作
     *
     * @param dto
     * @return
     */
    @PostMapping(value = "executeCancelRebate")
    ApiResult executeCancelRebate(@RequestBody RebateResultDTO dto);

    /**
     * 统计用户id返水统计
     * beginTime-endTime :yyyy-MM-dd
     * reqIds idList
     * siteCode 站点信息
     */
    @PostMapping(value = "sumRebateResultsByIds")
    ApiResult<RebateResultInfoDTO> sumRebateResultsByIds(@RequestBody RebateResultDTO dto);


    /**
     * 统计用户idList活动金额统计
     * beginTime-endTime :yyyy-MM-dd
     * reqIds idList
     * siteCode 站点信息
     */
    @PostMapping(value = "getUserEventMoneyMap")
    ApiResult<Map<Long, Long>> getUserEventMoneyMap(@RequestBody RebateResultDTO dto);




}
