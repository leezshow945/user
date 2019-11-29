package com.jq.user.customer.dto;

import java.io.Serializable;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/22
 */
public class TestUserDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private Integer status;
    private String searchType;
    private String searchName;
    private Integer level;
    private Long userRankId;
    private String beginTotalAmount;
    private String endTotalAmount;
    private String highLevelName;
    private Long highLevelId;
    private Integer isProxy;
    private String ip;
    private String regResource;
    private String dateType;
    private String beginTime;
    private String endTime;
    private String orderBy;
    private String sort;
    private String doMain;
    private String userName;
    private String password;
    private String realName;
    private String rebate0;
    private String rebate1;
    private String rebate2;
    private String rebate3;
    private String rebate4;
    private String rebate5;
    private String rebate6;
    private String rebate7;
    private String rebate8;
    private String rebate9;
    private String controlStatus;
    private Integer isDemo;
    private String sex;
    private String weChat;
    private String qq;
    private String mobile;
    private String email;
    private String canAmount;
    private String incomeNum;
    private String incomeMoney;
    private String payNum;
    private String payMoney;
    private Long id;
    private String createTime;
    private String payPassword;
    private Long groupId;

    /**排序字段,默认sort_id**/
    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    /** 当前页 */
    private Integer page = 1;
    /** 每页条数 */
    private Integer limit = 20;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getUserRankId() {
        return userRankId;
    }

    public void setUserRankId(Long userRankId) {
        this.userRankId = userRankId;
    }

    public String getBeginTotalAmount() {
        return beginTotalAmount;
    }

    public void setBeginTotalAmount(String beginTotalAmount) {
        this.beginTotalAmount = beginTotalAmount;
    }

    public String getEndTotalAmount() {
        return endTotalAmount;
    }

    public void setEndTotalAmount(String endTotalAmount) {
        this.endTotalAmount = endTotalAmount;
    }

    public String getHighLevelName() {
        return highLevelName;
    }

    public void setHighLevelName(String highLevelName) {
        this.highLevelName = highLevelName;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegResource() {
        return regResource;
    }

    public void setRegResource(String regResource) {
        this.regResource = regResource;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getDoMain() {
        return doMain;
    }

    public void setDoMain(String doMain) {
        this.doMain = doMain;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getGroupId() {
        return groupId;
    }

    public String getRebate0() {
        return rebate0;
    }

    public void setRebate0(String rebate0) {
        this.rebate0 = rebate0;
    }

    public String getRebate1() {
        return rebate1;
    }

    public void setRebate1(String rebate1) {
        this.rebate1 = rebate1;
    }

    public String getRebate2() {
        return rebate2;
    }

    public void setRebate2(String rebate2) {
        this.rebate2 = rebate2;
    }

    public String getRebate3() {
        return rebate3;
    }

    public void setRebate3(String rebate3) {
        this.rebate3 = rebate3;
    }

    public String getRebate4() {
        return rebate4;
    }

    public void setRebate4(String rebate4) {
        this.rebate4 = rebate4;
    }

    public String getRebate5() {
        return rebate5;
    }

    public void setRebate5(String rebate5) {
        this.rebate5 = rebate5;
    }

    public String getRebate6() {
        return rebate6;
    }

    public void setRebate6(String rebate6) {
        this.rebate6 = rebate6;
    }

    public String getRebate7() {
        return rebate7;
    }

    public void setRebate7(String rebate7) {
        this.rebate7 = rebate7;
    }

    public String getRebate8() {
        return rebate8;
    }

    public void setRebate8(String rebate8) {
        this.rebate8 = rebate8;
    }

    public String getRebate9() {
        return rebate9;
    }

    public void setRebate9(String rebate9) {
        this.rebate9 = rebate9;
    }

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    public Integer getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Integer isDemo) {
        this.isDemo = isDemo;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCanAmount() {
        return canAmount;
    }

    public void setCanAmount(String canAmount) {
        this.canAmount = canAmount;
    }

    public String getIncomeNum() {
        return incomeNum;
    }

    public void setIncomeNum(String incomeNum) {
        this.incomeNum = incomeNum;
    }

    public String getIncomeMoney() {
        return incomeMoney;
    }

    public void setIncomeMoney(String incomeMoney) {
        this.incomeMoney = incomeMoney;
    }

    public String getPayNum() {
        return payNum;
    }

    public void setPayNum(String payNum) {
        this.payNum = payNum;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
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

    @Override
    public String toString() {
        return "TestUserDTO{" +
                "status=" + status +
                ", searchType='" + searchType + '\'' +
                ", searchName='" + searchName + '\'' +
                ", level=" + level +
                ", userRankId=" + userRankId +
                ", beginTotalAmount='" + beginTotalAmount + '\'' +
                ", endTotalAmount='" + endTotalAmount + '\'' +
                ", highLevelName='" + highLevelName + '\'' +
                ", highLevelId=" + highLevelId +
                ", isProxy=" + isProxy +
                ", ip='" + ip + '\'' +
                ", regResource='" + regResource + '\'' +
                ", dateType='" + dateType + '\'' +
                ", beginTime='" + beginTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", sort='" + sort + '\'' +
                ", doMain='" + doMain + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", rebate0='" + rebate0 + '\'' +
                ", rebate1='" + rebate1 + '\'' +
                ", rebate2='" + rebate2 + '\'' +
                ", rebate3='" + rebate3 + '\'' +
                ", rebate4='" + rebate4 + '\'' +
                ", rebate5='" + rebate5 + '\'' +
                ", rebate6='" + rebate6 + '\'' +
                ", rebate7='" + rebate7 + '\'' +
                ", rebate8='" + rebate8 + '\'' +
                ", rebate9='" + rebate9 + '\'' +
                ", controlStatus='" + controlStatus + '\'' +
                ", isDemo=" + isDemo +
                ", sex='" + sex + '\'' +
                ", weChat='" + weChat + '\'' +
                ", qq='" + qq + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", canAmount='" + canAmount + '\'' +
                ", incomeNum='" + incomeNum + '\'' +
                ", incomeMoney='" + incomeMoney + '\'' +
                ", payNum='" + payNum + '\'' +
                ", payMoney='" + payMoney + '\'' +
                ", id=" + id +
                ", createTime='" + createTime + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", groupId=" + groupId +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
