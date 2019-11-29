package com.jq.user.oldbonus.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jq.user.support.BaseEntity;

import java.io.Serializable;


@TableName("user_bonus_son")
public class UserBonusSonEntity extends BaseEntity implements Serializable{



    private Long id;

    /**
     * 主表id
     **/
    private Long mainId;

    /**
     * 金额
     **/
    private Long amount;

    /**
     * 返点要求
     **/
    private Long rebate;

    /**
     * 有效会员
     **/
    private Integer validMember;

    /**
     * 奖金模式
     */
    private Integer bonusMode;

    /**
     * 奖金金额
     **/
    private Long bonusAmount;

    /**
     * 上限金额
     **/
    private Long limitAmount;

    /**
     * 奖金比例
     **/
    private Long bonusPer;

    /**
     * 实际盈亏金额
     */
    private Long actualWinLose;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }


    public Long getRebate() {
        return rebate;
    }

    public void setRebate(Long rebate) {
        this.rebate = rebate;
    }


    public Integer getValidMember() {
        return validMember;
    }

    public void setValidMember(Integer validMember) {
        this.validMember = validMember;
    }


    public Integer getBonusMode() {
        return bonusMode;
    }

    public void setBonusMode(Integer bonusMode) {
        this.bonusMode = bonusMode;
    }


    public Long getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(Long bonusAmount) {
        this.bonusAmount = bonusAmount;
    }


    public Long getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(Long limitAmount) {
        this.limitAmount = limitAmount;
    }


    public Long getBonusPer() {
        return bonusPer;
    }

    public void setBonusPer(Long bonusPer) {
        this.bonusPer = bonusPer;
    }

    public Long getActualWinLose() {
        return actualWinLose;
    }

    public void setActualWinLose(Long actualWinLose) {
        this.actualWinLose = actualWinLose;
    }

    @Override
    public String toString() {
        return "UserBonusSonEntity{" +
                "id=" + id +
                ", mainId=" + mainId +
                ", amount=" + amount +
                ", rebate=" + rebate +
                ", validMember=" + validMember +
                ", bonusMode=" + bonusMode +
                ", bonusAmount=" + bonusAmount +
                ", limitAmount=" + limitAmount +
                ", bonusPer=" + bonusPer +
                ", actualWinLose=" + actualWinLose +
                '}';
    }
}