package com.jq.user.rebate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.Date;

/**
 * 真人游戏返水结果详情表
 * @Auther: Lee
 * @Date: 2018/12/4 17:35
 */
@TableName("rebate_video_result_info")
@Data
public class RebateVideoResultInfoEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;//返水结果详情Id
    private Long resultInfoId;//返水结果详情Id
    private String gameCode;//游戏code
    private String gameName;//游戏名称
    private Long gameBet=0l;//游戏投注
    private Long gameRebate=0l;//游戏返水

}
