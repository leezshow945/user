package com.jq.user.rebate.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.rebate.entity.RebateVideoResultInfoEntity;

/**
 * @Auther: Lee
 * @Date: 2018/12/5 13:43
 */
public interface RebateVideoResultInfoDao extends BaseMapper<RebateVideoResultInfoEntity> {
    void updateAllColumnById(RebateVideoResultInfoEntity infoEntity);
}
