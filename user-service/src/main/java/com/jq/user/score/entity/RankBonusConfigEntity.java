package com.jq.user.score.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @Auther: Lee
 * @Date: 2018/11/8 15:50
 */
@TableName("rank_bonus_config")
public class RankBonusConfigEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteId;
    private String siteCode;
    private Integer upgradeRatio;//跳级加奖比例
    private String bonusStartTime;//每日加奖开始时间 格式 mm:ss
    private String bonusEndTime;//每日加奖逾期时间 格式 mm:ss
    private Long level1Bet;//第一级金额设置
    private Long level2Bet;//第二级金额设置
    private Long level3Bet;//第三级金额设置
    private String updateBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;


    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getUpgradeRatio() {
        return upgradeRatio;
    }

    public void setUpgradeRatio(Integer upgradeRatio) {
        this.upgradeRatio = upgradeRatio;
    }

    public String getBonusStartTime() {
        return bonusStartTime;
    }

    public void setBonusStartTime(String bonusStartTime) {
        this.bonusStartTime = bonusStartTime;
    }

    public String getBonusEndTime() {
        return bonusEndTime;
    }

    public void setBonusEndTime(String bonusEndTime) {
        this.bonusEndTime = bonusEndTime;
    }

    public Long getLevel1Bet() {
        return level1Bet;
    }

    public void setLevel1Bet(Long level1Bet) {
        this.level1Bet = level1Bet;
    }

    public Long getLevel2Bet() {
        return level2Bet;
    }

    public void setLevel2Bet(Long level2Bet) {
        this.level2Bet = level2Bet;
    }

    public Long getLevel3Bet() {
        return level3Bet;
    }

    public void setLevel3Bet(Long level3Bet) {
        this.level3Bet = level3Bet;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "RankBonusEntity{" +
                "siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", upgradeRatio=" + upgradeRatio +
                ", bonusStartTime='" + bonusStartTime + '\'' +
                ", bonusEndTime='" + bonusEndTime + '\'' +
                ", level1Bet=" + level1Bet +
                ", level2Bet=" + level2Bet +
                ", level3Bet=" + level3Bet +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                '}';
    }
}


