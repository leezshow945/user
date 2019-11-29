package com.jq.user.rebate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.rebate.entity.RebateVideoRuleEntity;

/**
 * @Auther: Lee
 * @Date: 2019/2/11 17:17
 */
public interface RebateVideoRuleDao extends BaseMapper<RebateVideoRuleEntity> {

    //根据规则组id删除真人返水规则组数据
    void deleteByRuleId(Long ruleId);
}
