package com.jq.user.score.dto;

import java.io.Serializable;
import java.util.Date;

public class UserRankScoreDTO implements Serializable {

    private Long id;
    private Long rankId;
    /**
     * 积分获取动作名
     **/
    private String scoreName;
    /**
     * 积分获取CODE
     **/
    private String scoreCode;
    /**
     * 积分获取值
     **/
    private Integer scoreVal;
    /**
     * 是否启用
     **/
    private Integer isEnable;
    private Date createTime;
    private String createBy;
    private String updateBy;
    private Date updateTime;
    private String remark;
    private Integer isDel = 0;

    private Integer scoreType;//0-无限1-一次性2-今天
    private String scoreTypeUrl;
    private Boolean isDone;

    public String getScoreTypeUrl() {
        return scoreTypeUrl;
    }

    public void setScoreTypeUrl(String scoreTypeUrl) {
        this.scoreTypeUrl = scoreTypeUrl;
    }

    public Boolean getIsDone() {
        return isDone;
    }

    public void setIsDone(Boolean done) {
        isDone = done;
    }

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public Integer getScoreVal() {
        return scoreVal;
    }

    public void setScoreVal(Integer scoreVal) {
        this.scoreVal = scoreVal;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    @Override
    public String toString() {
        return "UserRankScoreDTO{" +
         "id=" + id +
         ", rankId=" + rankId +
         ", scoreName='" + scoreName + '\'' +
         ", scoreCode='" + scoreCode + '\'' +
         ", scoreVal=" + scoreVal +
         ", isEnable=" + isEnable +
         ", createTime=" + createTime +
         ", createBy='" + createBy + '\'' +
         ", updateBy='" + updateBy + '\'' +
         ", updateTime=" + updateTime +
         ", remark='" + remark + '\'' +
         ", isDel=" + isDel +
         '}';
    }
}
