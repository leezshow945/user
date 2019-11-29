package com.jq.user.rebate.service;


import com.jq.user.rebate.dao.RebateVideoRuleDao;
import com.jq.user.rebate.dto.RebateRuleDTO;
import com.jq.user.rebate.dto.RebateRuleInfoDTO;
import com.liying.common.service.ApiResult;
import com.liying.common.service.PageInfo;
import com.liying.trade.order.api.OrderSubtotalService;
import com.liying.trade.user.resp.CountTotalEffectiveBetResp;
import com.liying.trade.user.vo.CountTotalEffectiveBetReq;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lee
 * Descript:
 * Date: 2018/5/3
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class RebateRuleServiceTest {
    @Resource
    private RebateRuleService rebateRuleService;
    @Resource
    private RebateRuleInfoInnerService rebateRuleInfoInnerService;

    private OrderSubtotalService orderSubtotalService;
    @Resource
    private RebateVideoRuleDao rebateVideoRuleDao;

    @Test
    public void queryTest() {

        RebateRuleDTO ruleDTO = new RebateRuleDTO();
        ruleDTO.setSiteId(1040499894897405953l);
        ruleDTO.setBeginTime("2019-01-14");
        ApiResult<PageInfo<RebateRuleDTO>> ruleListAPI = rebateRuleService.queryRuleListAPI(ruleDTO);
        assert ruleListAPI.getCode().equals(10000);

        RebateRuleInfoDTO infoDTO = new RebateRuleInfoDTO();
        infoDTO.setRuleId(1010139380951576578l);
        ApiResult<PageInfo<RebateRuleInfoDTO>> infoApiResult = rebateRuleService.queryRuleInfoListAPI(infoDTO);
        assert infoApiResult.getCode().equals(10000);

    }

    @Test
    public void saveRuleTest() {
        RebateRuleDTO ruleDTO = new RebateRuleDTO();
        ruleDTO.setName("弟子规1");
        ruleDTO.setSort(0);
        ruleDTO.setSiteId(8888888888888888l);
        ruleDTO.setSiteCode("ceshi");
        ruleDTO.setCreateBy("鹿丸");
        ApiResult apiResult = rebateRuleService.saveRuleAPI(ruleDTO);
        assert apiResult.getCode().equals(10000);

        RebateRuleInfoDTO userRebateRuleInfoDTO = new RebateRuleInfoDTO();
        userRebateRuleInfoDTO.setRebateMost(100l);
        userRebateRuleInfoDTO.setEffectiveBets(20l);
        userRebateRuleInfoDTO.setCreateBy("鹿丸");
        ApiResult apiResult3 = rebateRuleService.saveRuleInfoAPI(userRebateRuleInfoDTO);
        assert apiResult3.getCode().equals(10000);

    }

    @Test
    public void delTest() {

        rebateVideoRuleDao.deleteByRuleId(1094818489589305346l);

        RebateRuleDTO dto = new RebateRuleDTO();
        dto.setUpdateBy("Zoro");
        dto.setSiteId(8888888888888888l);
        dto.setId(1010416927564218370l);
        ApiResult apiResult = rebateRuleService.delRuleAPI(dto);
        assert apiResult.getCode().equals(10000);

    }

    @Test
    public void minEffective() {
        Long minTotalEffect = rebateRuleInfoInnerService.getMinTotalEffect(1007161902619004929l, 1010469221574479874l);
        assert minTotalEffect != null;
    }

    @Test
    public void countTotalEffectiveBetTest() {
        CountTotalEffectiveBetReq req = new CountTotalEffectiveBetReq();
        req.setBeginDate("2018-06-01");
        req.setEndDate("2018-06-26");
        req.setSiteCode("jq-0");
        req.setMinEffectiveBetMoney(0l);

        List<Long> users = new ArrayList<>();
        users.add(1l);
        users.add(2l);

        req.setUserIds(users);
        ApiResult<CountTotalEffectiveBetResp> betRespApiResult = orderSubtotalService.countTotalEffectiveBet(req);
        assert betRespApiResult.getCode() == 10000;

    }



}
