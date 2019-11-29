package com.jq.user.rebate.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 返水统计DTO类
 */
public class RebateBetsDTO implements Serializable {

    private Long siteId;
    private String siteCode;
    private Long ruleId;
    private String beginTime;
    private String endTime;
    private String UserName;
    private Integer groupId;//现金分组id
    private String groupName;//现金分组name
    private String eventName;//返水事件名

    private Integer userNum;//总人数
    private Long betSum;//总有效投注
    private Long rebateSum;//返水总计
    private String updateBy;//操作人
    private Long resultId;//返水事件id

    private List<RebateResultInfoDTO> rebateResultInfoDTOList;//彩票游戏 用户投注与返水

    //mango端分页参数
    private int page = 1;
    private int limit = 10;
    private long totalElements;
    private int totalPages;

    //前后端分离接口分页参数
    private int size = 10;
    private int current=1;
    private int total;
    private int pages;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Long getBetSum() {
        return betSum;
    }

    public void setBetSum(Long betSum) {
        this.betSum = betSum;
    }

    public Long getRebateSum() {
        return rebateSum;
    }

    public void setRebateSum(Long rebateSum) {
        this.rebateSum = rebateSum;
    }

    public List<RebateResultInfoDTO> getRebateResultInfoDTOList() {
        return rebateResultInfoDTOList;
    }

    public void setRebateResultInfoDTOList(List<RebateResultInfoDTO> rebateResultInfoDTOList) {
        this.rebateResultInfoDTOList = rebateResultInfoDTOList;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    @Override
    public String toString() {
        return "RebateBetsDTO{" +
                "siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", ruleId=" + ruleId +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", UserName='" + UserName + '\'' +
                ", groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", userNum=" + userNum +
                ", betSum=" + betSum +
                ", rebateSum=" + rebateSum +
                ", rebateResultInfoDTOList=" + rebateResultInfoDTOList +
                ", page=" + page +
                ", limit=" + limit +
                ", totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                '}';
    }
}
