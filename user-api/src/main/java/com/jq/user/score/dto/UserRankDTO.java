package com.jq.user.score.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class UserRankDTO implements Serializable {

    private Long id;
    private Long siteId;
    private String siteCode;
    private Long userId;
    private Long highLevelId;
    private String highLevelAccount;
    private String path;
    private String userName;

    /**
     * 等级名称
     **/
    private String rankName;
    /**
     * 等级值
     **/
    private Integer rankLevel;
    /**
     * 等级缩略图
     **/
    private String rankImg;
    /**
     * 等级最大积分
     **/
    private Integer maxScore;

    private Date createTime;
    private String createBy;
    private String updateBy;
    private Date updateTime;
    private String remark;
    private Integer isDel = 0;
    private Map rankMap;

    private String orderField = "rank_level";
    /**排序方向,默认降序**/
    private String orderDirection = "asc";
    private Integer page = 1;
    private Integer limit = 20;

    private String timeStr;//查询字段 时间关键词 格式：yyyy-MM-dd
    private List<UserRankScoreDTO> userRankScoreList;//等级模板下的积分模板
    private RankBonusConfigDTO rankBonusConfigDTO;//站点加奖配置
    private Double rechargeRatio;//用户充值兑换积分比例
    private Long upgradeReward;//晋级奖励金
    //一二三级加奖比例
    private Integer level1Ratio;
    private Integer level2Ratio;
    private Integer level3Ratio;
    private Integer bonusType;//加奖比例开关标识
    private String upgradeRankBonus;//此等级跳级奖励金

    public String getUpgradeRankBonus() {
        return upgradeRankBonus;
    }

    public void setUpgradeRankBonus(String upgradeRankBonus) {
        this.upgradeRankBonus = upgradeRankBonus;
    }

    public Integer getBonusType() {
        return bonusType;
    }

    public void setBonusType(Integer bonusType) {
        this.bonusType = bonusType;
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

    public Long getUpgradeReward() {
        return upgradeReward;
    }

    public void setUpgradeReward(Long upgradeReward) {
        this.upgradeReward = upgradeReward;
    }

    public RankBonusConfigDTO getRankBonusConfigDTO() {
        return rankBonusConfigDTO;
    }

    public void setRankBonusConfigDTO(RankBonusConfigDTO rankBonusConfigDTO) {
        this.rankBonusConfigDTO = rankBonusConfigDTO;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
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

    public String getRankImg() {
        return rankImg;
    }

    public void setRankImg(String rankImg) {
        this.rankImg = rankImg;
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

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public List<UserRankScoreDTO> getUserRankScoreList() {
        return userRankScoreList;
    }

    public void setUserRankScoreList(List<UserRankScoreDTO> userRankScoreList) {
        this.userRankScoreList = userRankScoreList;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map getRankMap() {
        return rankMap;
    }

    public void setRankMap(Map rankMap) {
        this.rankMap = rankMap;
    }

    @Override
    public String toString() {
        return "UserRankDTO{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", userId=" + userId +
                ", highLevelId=" + highLevelId +
                ", highLevelAccount='" + highLevelAccount + '\'' +
                ", path='" + path + '\'' +
                ", userName='" + userName + '\'' +
                ", rankName='" + rankName + '\'' +
                ", rankLevel=" + rankLevel +
                ", rankImg='" + rankImg + '\'' +
                ", maxScore=" + maxScore +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", isDel=" + isDel +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", timeStr='" + timeStr + '\'' +
                ", userRankScoreList=" + userRankScoreList +
                ", rankBonusConfigDTO=" + rankBonusConfigDTO +
                ", rechargeRatio=" + rechargeRatio +
                ", upgradeReward=" + upgradeReward +
                ", level1Ratio=" + level1Ratio +
                ", level2Ratio=" + level2Ratio +
                ", level3Ratio=" + level3Ratio +
                '}';
    }
}
