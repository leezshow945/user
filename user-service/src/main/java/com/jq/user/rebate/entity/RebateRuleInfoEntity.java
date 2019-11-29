package com.jq.user.rebate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jq.user.support.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 返水规则组详情
 */

@TableName("rebate_rule_info")
@Data
public class RebateRuleInfoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**主键*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long ruleId;//规则组id
    private Long rebateMost;//返水金额上限
    private Long effectiveBets;//总有效投注金额

    /** 棋牌返点  **/
    private Long  flcRebate;

    /** 高频彩返点  **/
    private Long  gpcRebate;

    /** 体育彩票返点  **/
    private Long  tycpRebate;

    /** 其他返点  **/
    private Long  qtRebate;

    /** 体育返点  **/
    private Long  tyRebate;

    /** 六合彩返点0，单位：万  **/
    private Long  lhcRebate0;

    /** 六合彩返点1，单位：万  **/
    private Long  lhcRebate1;

    /** 六合彩返点2，单位：万  **/
    private Long  lhcRebate2;

    /** 六合彩返点3，单位：万  **/
    private Long  lhcRebate3;

    /** 低频彩返点，单位：万  **/
    private Long  dpcRebate;

}
