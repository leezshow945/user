package com.jq.user.score.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@TableName("user_rank")
public class UserRankEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteId;
    private String siteCode;
    /**等级名称**/
    private String rankName;
    /**等级值**/
    private Integer rankLevel;
    /**等级最大积分**/
    private Integer maxScore;
    //用户充值兑换积分比例
    private Double rechargeRatio;
    //晋级奖励金
    private Long upgradeReward;
    //一二三级加奖比例
    private Integer level1Ratio;
    private Integer level2Ratio;
    private Integer level3Ratio;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    private String createBy;
    private String updateBy;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;
    private String remark;
    private Integer isDel = 0;

    @TableField(exist = false)
    private String timeStr;//查询字段 时间关键词 格式：yyyy-MM-dd
    @TableField(exist = false)
    public List<UserRankScoreEntity> userRankScoreEntityList;//等级模板下的积分模板

    private Integer bonusType;//加奖标识0为关闭1为开启

    public Integer getBonusType() {
        return bonusType;
    }

    public void setBonusType(Integer bonusType) {
        this.bonusType = bonusType;
    }

    public Long getUpgradeReward() {
        return upgradeReward;
    }

    public void setUpgradeReward(Long upgradeReward) {
        this.upgradeReward = upgradeReward;
    }

    public Integer getLevel1Ratio() {
        return level1Ratio;
    }

    public void setLevel1Ratio(Integer level1Ratio) {
        this.level1Ratio = level1Ratio;
    }

    public Integer getLevel2Ratio() {
        return level2Ratio;
    }

    public void setLevel2Ratio(Integer level2Ratio) {
        this.level2Ratio = level2Ratio;
    }

    public Integer getLevel3Ratio() {
        return level3Ratio;
    }

    public void setLevel3Ratio(Integer level3Ratio) {
        this.level3Ratio = level3Ratio;
    }

    public Double getRechargeRatio() {
        return rechargeRatio;
    }

    public void setRechargeRatio(Double rechargeRatio) {
        this.rechargeRatio = rechargeRatio;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public List<UserRankScoreEntity> getUserRankScoreEntityList() {
        return userRankScoreEntityList;
    }

    public void setUserRankScoreEntityList(List<UserRankScoreEntity> userRankScoreEntityList) {
        this.userRankScoreEntityList = userRankScoreEntityList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }
}
