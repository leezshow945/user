package com.jq.user.rebate.service;

import com.jq.user.rebate.dto.RebateResultDTO;
import com.jq.user.rebate.dto.RebateResultInfoDTO;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RebateResultServiceTest {
    @Resource
    private RebateResultService rebateResultService;

    @Test
    public void executeRebateTest() {
        RebateResultDTO dto = new RebateResultDTO();
        dto.setSiteId(1011868438377877506l);
        dto.setSiteCode("jq-0");
        dto.setEventName("测试事件650");
        dto.setBeginTime("2018-06-30");
        dto.setEndTime("2018-07-29");
        dto.setRuleId(1011870489572380673l);
        dto.setCreateBy("jay");

        List<Long> userId = new ArrayList<>();
        for (Long i = 1001l; i < 1010l; i++) {
            userId.add(i);
        }
        dto.setReqIds(userId);
        ApiResult apiResult = rebateResultService.executeRebate(dto);
        assert apiResult.getCode() == 10000;
    }

    @Test
    public void queryRebateResultTest() {
        RebateResultDTO dto = new RebateResultDTO();
        dto.setStatus(0);
        dto.setSiteId(1011868438377877506l);
        dto.setBeginTime("2018-06-02");
        dto.setEndTime("2018-07-02");
        ApiResult<PageInfo<RebateResultDTO>> pageInfoApiResult = rebateResultService.queryRebateResultAPI(dto);
        assert pageInfoApiResult.getCode() == 10000;

    }

    @Test
    public void queryRebateResultInfoTest() {
        RebateResultInfoDTO dto = new RebateResultInfoDTO();
        dto.setResultId(1013609836258045953l);
        ApiResult<PageInfo<RebateResultInfoDTO>> pageInfoApiResult = rebateResultService.queryRebateResultInfoAPI(dto);
        assert pageInfoApiResult.getCode() == 10000;
    }

    @Test
    public void cancelRebateTest() {
        RebateResultDTO dto = new RebateResultDTO();
        dto.setId(1013634296742690818l);
        dto.setCreateBy("lee");

        List<Long> ids = new ArrayList<>();
        ids.add(1013634303759761410l);

        dto.setReqIds(ids);
        ApiResult apiResult = rebateResultService.executeCancelRebate(dto);
        assert apiResult.getCode() == 10000;
    }

    @Test
    public void sumRebateResultsByIdsTest() {
        RebateResultDTO dto = new RebateResultDTO();
        dto.setSiteCode("jq-0");
        dto.setBeginTime("2018-06-25");
        dto.setEndTime("2018-07-25");
        List ids = new ArrayList();
        ids.add(1071676767l);
        ids.add(107677777l);
        dto.setReqIds(ids);
        ApiResult<RebateResultInfoDTO> apiResult = rebateResultService.sumRebateResultsByIds(dto);
        System.out.println(apiResult.toString());
        assert apiResult.getCode() == 10000;
    }

    @Test
    public void getUserEventMoneyMapTest() {

        RebateResultDTO dto = new RebateResultDTO();
        dto.setSiteCode("jq-0");
        dto.setBeginTime("2017-06-25");
        dto.setEndTime("2018-07-25");
        List ids = new ArrayList();
        ids.add(1019785602918277122l);
        ids.add(1019788818317860865l);
        dto.setReqIds(ids);

        ApiResult<Map<Long, Long>> apiResult = rebateResultService.getUserEventMoneyMap(dto);
        System.out.println(apiResult.toString());
        assert apiResult.getCode() == 10000;

    }

}
