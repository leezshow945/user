package com.jq.user.score.dto;

import java.io.Serializable;

/**
 * @Auther: Lee
 * @Date: 2018/11/16 10:14
 */
public class UserRankBonusDTO implements Serializable {

    private Double dailyBet=0.00;//昨日投注金额
    private String rankName;//等级名称
    private Integer rankLevel;//等级
    private String bonusRatio="0.00%";//加奖比例
    private Double dailyBonus=0.00;//可得加奖||可得晋级奖励
    private int bonusType=0;//领奖状态 0不可领奖1领奖-1已领取

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Double getDailyBet() {
        return dailyBet;
    }

    public void setDailyBet(Double dailyBet) {
        this.dailyBet = dailyBet;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getBonusRatio() {
        return bonusRatio;
    }

    public void setBonusRatio(String bonusRatio) {
        this.bonusRatio = bonusRatio;
    }

    public Double getDailyBonus() {
        return dailyBonus;
    }

    public void setDailyBonus(Double dailyBonus) {
        this.dailyBonus = dailyBonus;
    }

    public int getBonusType() {
        return bonusType;
    }

    public void setBonusType(int bonusType) {
        this.bonusType = bonusType;
    }

    @Override
    public String toString() {
        return "UserRankBonusDTO{" +
                "dailyBet=" + dailyBet +
                ", rankName='" + rankName + '\'' +
                ", bonusRatio='" + bonusRatio + '\'' +
                ", dailyBonus=" + dailyBonus +
                ", bonusType=" + bonusType +
                '}';
    }
}
