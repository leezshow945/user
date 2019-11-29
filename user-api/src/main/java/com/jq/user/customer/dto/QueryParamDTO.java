package com.jq.user.customer.dto;

import java.io.Serializable;

public class QueryParamDTO implements Serializable {
    private Integer isProxy;
    private Integer level;
    private Integer rankLevel;
    private Integer status;
    private String regSource;
    /** 上级代理id */
    private Long highLevelId;
    private String highLevelName;
    private String loginName;
    private Integer searchType;
    private Integer dateType;
    private String beginTime;
    private String endTime;
    private String regIp;
    private String lastLoginIp;
    private Integer orderBy;
    private Integer sort;
    private Long userId;
    private String userName;
    private String startTime;
    private String siteCode;
    private Integer isOnLine;
    // 是否开启查看所有下级代理会员:1-是,0-否
    private Integer isSearchAll;
    // 返点查询类型
    private String rebateType;
    // 范围最小值
    private Long minRebate;
    // 范围最大值
    private Long maxRebate;
    private String token;
    // 站点名称
    private String siteTitle;

    private Long siteId;
    // page参数
    private String orderField = "create_time";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    private Integer page = 1;
    private Integer limit = 20;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIsSearchAll() {
        return isSearchAll;
    }

    public void setIsSearchAll(Integer isSearchAll) {
        this.isSearchAll = isSearchAll;
    }

    public String getRebateType() {
        return rebateType;
    }

    public void setRebateType(String rebateType) {
        this.rebateType = rebateType;
    }

    public Long getMinRebate() {
        return minRebate;
    }

    public void setMinRebate(Long minRebate) {
        this.minRebate = minRebate;
    }

    public Long getMaxRebate() {
        return maxRebate;
    }

    public void setMaxRebate(Long maxRebate) {
        this.maxRebate = maxRebate;
    }

    public Integer getIsOnLine() {
        return isOnLine;
    }

    public void setIsOnLine(Integer isOnLine) {
        this.isOnLine = isOnLine;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    public String getHighLevelName() {
        return highLevelName;
    }

    public void setHighLevelName(String highLevelName) {
        this.highLevelName = highLevelName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public Integer getDateType() {
        return dateType;
    }

    public void setDateType(Integer dateType) {
        this.dateType = dateType;
    }

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

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
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

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    @Override
    public String toString() {
        return "QueryParamDTO{" +
                "isProxy=" + isProxy +
                ", level=" + level +
                ", rankLevel=" + rankLevel +
                ", status=" + status +
                ", regSource='" + regSource + '\'' +
                ", highLevelId=" + highLevelId +
                ", highLevelName='" + highLevelName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", searchType=" + searchType +
                ", dateType=" + dateType +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", regIp='" + regIp + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", orderBy=" + orderBy +
                ", sort=" + sort +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", startTime='" + startTime + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", isOnLine=" + isOnLine +
                ", isSearchAll=" + isSearchAll +
                ", rebateType='" + rebateType + '\'' +
                ", minRebate=" + minRebate +
                ", maxRebate=" + maxRebate +
                ", token='" + token + '\'' +
                ", siteTitle='" + siteTitle + '\'' +
                ", siteId=" + siteId +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
