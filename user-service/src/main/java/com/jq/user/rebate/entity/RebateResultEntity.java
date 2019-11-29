package com.jq.user.rebate.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jq.user.support.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户返水规则结果
 *
 * @author lee
 * Descript:
 * Date: 2018/5/3
 */
@TableName("rebate_result")
@Data
public class RebateResultEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**主键*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String groupName;//返水现金分组名
    private String eventName;//返水事件名
    private Long siteId;
    private String siteCode;
    private Long ruleId;
    private String beginTime;
    private String endTime;
    private Date doTime;//执行时间
    private Integer status=0;//0已返水 1已冲销
    private Integer finalRebateNum;//实际返水人数
    private Integer rebateNum;//返水总人数
    private Long allRebates;//返水总金额
    private Long allBets;//总投注额

}
