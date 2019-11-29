package com.jq.user.rebate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

/**
 * 真人游戏返水配置表
 * @Auther: Lee
 * @Date: 2019/2/11 15:09
 */
@TableName("rebate_video_rule")
@Data
public class RebateVideoRuleEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Long ruleInfoId;//返水规则详情表Id
    private String gameCode;//游戏code
    private String gameName;//游戏名称
    private Long gameRebate;//真人游戏返点
}
