package com.jq.user.rebate.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jq.user.code.UserCodeEnum;
import com.jq.user.constant.UserConstant;
import com.jq.user.exception.UserException;
import com.jq.user.rebate.dao.RebateRuleInfoDao;
import com.jq.user.rebate.entity.RebateRuleInfoEntity;
import com.jq.user.rebate.service.RebateRuleInfoInnerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RebateRuleInfoServiceImpl extends ServiceImpl<RebateRuleInfoDao, RebateRuleInfoEntity> implements RebateRuleInfoInnerService {


    @Override
    public Long getMinTotalEffect(Long siteId, Long ruleId) {
        QueryWrapper<RebateRuleInfoEntity> ew = new QueryWrapper<RebateRuleInfoEntity>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("rule_id", ruleId);
//        ew.orderBy("effective_bets asc limit 1");
        ew.orderByAsc("effective_bets");
        ew.last("limit 1");
        List<RebateRuleInfoEntity> list = list(ew);
        if (list == null || list.size() == 0) {
            return 0L;
        }
        return list.get(0).getEffectiveBets();
    }

    @Override
    public RebateRuleInfoEntity getRuleInfoByBet(Long siteId, Long ruleId, Long bet) {
        QueryWrapper<RebateRuleInfoEntity> ew = new QueryWrapper<RebateRuleInfoEntity>();
        ew.eq("is_del", UserConstant.IS_F);
        ew.eq("rule_id", ruleId);
//        ew.and("effective_bets<=" + bet);
//        ew.orderBy("effective_bets desc limit 1");
        ew.le("effective_bets",bet);
        ew.orderByDesc("effective_bets");
        ew.last("limit 1");
        List<RebateRuleInfoEntity> list = list(ew);
        if (list == null || list.size() == 0) {
            throw new UserException(UserCodeEnum.RULEINFO_NOT_EXIST.getCode(), UserCodeEnum.RULEINFO_NOT_EXIST.getMessage());
        }
        return list.get(0);
    }


}
