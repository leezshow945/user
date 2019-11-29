package com.jq.user.rebate.service;

import java.util.Map;

/**
 * @author Brady
 * Descript:
 * Date: 2018/5/3
 */
public interface RebateRuleInnerService extends RebateRuleService {

    Map getVideoRebateMap(long ruleInfoId);

}
