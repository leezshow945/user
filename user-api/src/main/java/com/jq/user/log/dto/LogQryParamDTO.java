package com.jq.user.log.dto;

import java.io.Serializable;

public class LogQryParamDTO implements Serializable{
    private Long siteId;
    private String siteCode;
    private Integer searchType;
    private String keyword;
    private Integer sort;
    private String startTime;
    private String endTime;
    private Integer isDiffArea;
    private String userName;
    private String ip;
    private String accountType;
    private String type;

    private String orderField = "create_time";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    private Integer page = 1;
    private Integer limit = 20;

    public String getSiteCode() {
        return siteCode;

    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getIsDiffArea() {
        return isDiffArea;
    }

    public void setIsDiffArea(Integer isDiffArea) {
        this.isDiffArea = isDiffArea;
    }

    @Override
    public String toString() {
        return "LogQryParamDTO{" +
                "siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", searchType=" + searchType +
                ", keyword='" + keyword + '\'' +
                ", sort=" + sort +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isDiffArea=" + isDiffArea +
                ", userName='" + userName + '\'' +
                ", ip='" + ip + '\'' +
                ", accountType='" + accountType + '\'' +
                ", type='" + type + '\'' +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
