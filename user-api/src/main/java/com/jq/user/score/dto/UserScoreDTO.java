package com.jq.user.score.dto;


import java.io.Serializable;
import java.util.Date;

public class UserScoreDTO implements Serializable {

    //page参数

    /*是否代理*/
    private Integer isProxy;
    /*账户状态*/
    private String status;
    /*用户层级*/
    private Integer level;
    /*等级阶级*/
    private Integer rankLevel;
    private String scoreStart;//最小积分查询
    private String scoreEnd;//最大积分查询
    private Long siteId;//站点id
    private String userName;//用户名
    private Integer scoreVal;//积分变动值；人工操作积分值
    private Integer finalScore;//积分最终值
    private String scoreName;//积分动作
    private String scoreCode;//积分动作code值
    private String realName;//真实姓名
    private String createBy;//操作用户
    private String timeStr;//B端指定日期查询

    //C端查询积分记录时间筛选
    private String beginTime;
    private String endTime;


    //返回值参数
    private Long userId;//用户id
    private Integer score;//积分值
    private String rankName;//等级称号
    private Integer subLevelCount;//下级数量
    private String highLevelAccount;//上级账号
    private Date createTime;//交易时间
    private String remark;//备注
    private Integer type;//人工操作标识 0-存入 1-取出



    /**
     * 分页参数
     */
    private String orderField;
    private String orderDirection = "desc";
    private Integer page = 1;
    private Integer limit = 20;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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


    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }


    public String getScoreStart() {
        return scoreStart;
    }

    public void setScoreStart(String scoreStart) {
        this.scoreStart = scoreStart;
    }

    public String getScoreEnd() {
        return scoreEnd;
    }

    public void setScoreEnd(String scoreEnd) {
        this.scoreEnd = scoreEnd;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getScoreVal() {
        return scoreVal;
    }

    public void setScoreVal(Integer scoreVal) {
        this.scoreVal = scoreVal;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Integer getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Integer finalScore) {
        this.finalScore = finalScore;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getSubLevelCount() {
        return subLevelCount;
    }

    public void setSubLevelCount(Integer subLevelCount) {
        this.subLevelCount = subLevelCount;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    @Override
    public String toString() {
        return "UserScoreDTO{" +
         "isProxy=" + isProxy +
         ", status='" + status + '\'' +
         ", level=" + level +
         ", rankLevel=" + rankLevel +
         ", scoreStart='" + scoreStart + '\'' +
         ", scoreEnd='" + scoreEnd + '\'' +
         ", siteId=" + siteId +
         ", userName='" + userName + '\'' +
         ", scoreVal='" + scoreVal + '\'' +
         ", finalScore=" + finalScore +
         ", scoreName='" + scoreName + '\'' +
         ", userId=" + userId +
         ", score=" + score +
         ", rankName='" + rankName + '\'' +
         ", subLevelCount=" + subLevelCount +
         ", highLevelAccount=" + highLevelAccount +
         ", createTime=" + createTime +
         ", orderField='" + orderField + '\'' +
         ", orderDirection='" + orderDirection + '\'' +
         ", page=" + page +
         ", limit=" + limit +
         '}';
    }
}
