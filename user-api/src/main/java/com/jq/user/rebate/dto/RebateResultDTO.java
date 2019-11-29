package com.jq.user.rebate.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RebateResultDTO implements Serializable{

    private Long id;
    private String eventName;//返水事件名
    private String ruleName;//返水规则名称
    private String groupName;//现金分组名
    private Integer groupId;//现金分组id

    private Long siteId;
    private String siteCode;
    private Long ruleId;
    private String beginTime;
    private String endTime;
    private Date doTime;//执行时间
    private Integer status;//0已返水 1已冲销
    private Long finalRebateNum;//实际返水人数
    private Long rebateNum;//返水总人数
    private Long allRebates;//返水总金额
    private Long allBets;//真人视讯返水总投注额

    private List<Long> reqIds;//勾选查询的用户id（批量返水操作用）
    private String userName;
    private String createBy;

    private String orderField="result.create_time";
    private String orderDirection = "desc";
    private Integer page = 1;
    private Integer limit = 10;

    //前后端分离接口分页参数
    private int size = 10;
    private int current=1;
    private int total;
    private int pages;



    public Long getAllBets() {
        return allBets;
    }

    public void setAllBets(Long allBets) {
        this.allBets = allBets;
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


    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Long> getReqIds() {
        return reqIds;
    }

    public void setReqIds(List<Long> reqIds) {
        this.reqIds = reqIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public Date getDoTime() {
        return doTime;
    }

    public void setDoTime(Date doTime) {
        this.doTime = doTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getFinalRebateNum() {
        return finalRebateNum;
    }

    public void setFinalRebateNum(Long finalRebateNum) {
        this.finalRebateNum = finalRebateNum;
    }

    public Long getRebateNum() {
        return rebateNum;
    }

    public void setRebateNum(Long rebateNum) {
        this.rebateNum = rebateNum;
    }

    public Long getAllRebates() {
        return allRebates;
    }

    public void setAllRebates(Long allRebates) {
        this.allRebates = allRebates;
    }

    @Override
    public String toString() {
        return "RebateResultDTO{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", groupId=" + groupId +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", ruleId=" + ruleId +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", doTime=" + doTime +
                ", status=" + status +
                ", finalRebateNum=" + finalRebateNum +
                ", rebateNum=" + rebateNum +
                ", allRebates=" + allRebates +
                ", reqIds=" + reqIds +
                ", userName='" + userName + '\'' +
                ", createBy='" + createBy + '\'' +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", size=" + size +
                ", current=" + current +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}
