package com.jq.user.rebate.service;

import com.jq.user.rebate.entity.RebateRuleInfoEntity;

public interface RebateRuleInfoInnerService {

    /**
     * 获取一个规则组下最小的投注金额
     *
     * @param siteId
     * @param ruleId
     * @return
     */
    Long getMinTotalEffect(Long siteId, Long ruleId);

    /**
     * 根据投注金额获取返水规则
     *
     * @param siteId
     * @param ruleId
     * @param bet
     * @return
     */
    RebateRuleInfoEntity getRuleInfoByBet(Long siteId, Long ruleId, Long bet);
}
