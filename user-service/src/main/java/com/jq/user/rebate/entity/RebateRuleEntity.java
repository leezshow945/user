package com.jq.user.rebate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jq.user.support.BaseEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户返水规则组
 *
 * @author lee
 * Descript:
 * Date: 2018/5/3
 */
@TableName("rebate_rule")
@Data
public class RebateRuleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private Long siteId;
    private String siteCode;
    private Integer sort;
}
