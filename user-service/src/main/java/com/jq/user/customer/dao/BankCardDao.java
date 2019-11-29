package com.jq.user.customer.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jq.user.customer.entity.BankCardEntity;
import org.apache.ibatis.annotations.Param;

public interface BankCardDao extends BaseMapper<BankCardEntity> {
    Integer  countBySiteIdAndCardNo(@Param("siteId") Long siteId, @Param("cardNo") String cardNo);
}
