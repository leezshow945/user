package com.jq.user.score.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Lee
 * @Date: 2018/11/12 14:37
 */
public class RankBonusConfigDTO implements Serializable {

    private Long siteId;
    private String siteCode;
    private Integer upgradeRatio;//跳级加奖比例
    private String bonusStartTime;//每日加奖开始时间 格式 mm:ss
    private String bonusEndTime;//每日加奖逾期时间 格式 mm:ss
    private Long level1Bet;//第一级金额设置
    private Long level2Bet;//第二级金额设置
    private Long level3Bet;//第三级金额设置
    private String updateBy;
    private Date updateTime;

    private List<UserRankDTO> userRankDTOList;

    public List<UserRankDTO> getUserRankDTOList() {
        return userRankDTOList;
    }

    public void setUserRankDTOList(List<UserRankDTO> userRankDTOList) {
        this.userRankDTOList = userRankDTOList;
    }

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
        return "RankBonusConfigDTO{" +
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
                ", userRankDTOList=" + userRankDTOList +
                '}';
    }
}
