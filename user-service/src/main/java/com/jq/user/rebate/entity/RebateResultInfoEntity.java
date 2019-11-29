package com.jq.user.rebate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jq.user.support.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户返水规则结果详情
 *
 * @author lee
 * Descript:
 * Date: 2018/5/3
 */
@TableName("rebate_result_info")
@Data
public class RebateResultInfoEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**主键*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long userId;
    private String userName;
    private String highLevelAccount;//上级用户名
    private Long resultId;//返水结果表id
    private Long effectiveBets;//总有效投注
    private Integer status=0;//状态0已返水1已冲销
    private Long allRebates;//返水总金额

    //投注金额
    private Long gpcBets;
    private Long dpcBets;
    private Long flcBets;
    private Long tycpBets;
    private Long qtBets;
    private Long tyBets;
    private Long lhc0Bets;
    private Long lhc1Bets;
    private Long lhc2Bets;
    private Long lhc3Bets;

    //返水金额
    private Long gpcRebate=0l;
    private Long dpcRebate=0l;
    private Long flcRebate=0l;
    private Long tycpRebate=0l;
    private Long qtRebate=0l;
    private Long tyRebate=0l;
    private Long lhc0Rebate=0l;
    private Long lhc1Rebate=0l;
    private Long lhc2Rebate=0l;
    private Long lhc3Rebate=0l;

}
