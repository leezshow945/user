package com.jq.user.customer.vo;

public class UserVO {
    // siteId 用于前端分页查询
    private Long siteId;
    private String status;
    private String searchType;
    private String searchName;
    private String level;
    private String rankId;
    private String beginTotalAmount;
    private String endTotalAmount;
    private String highLevelName;
    private String highLevelId;
    private String isProxy;
    private String ip;
    private String regSource;
    private String dateType;
    private String beginTime;
    private String endTime;
    private String orderBy;
    private String sort;
    private String doMain;
    private String loginName;
    private String loginPwd;
    private String nickName;
    private Integer rankLevel;
    private String regIp;
    private String lastLoginIp;



/*    @RequestParam(value = "userName", required = false) String userName,
    @RequestParam(value = "orderId", required = false) Long orderId,
    @RequestParam(value = "gameName", required = false) String gameName,
    @RequestParam(value = "playName", required = false) String playName,
    @RequestParam(value = "gameNo", required = false) String gameNo,
    @RequestParam(value = "status", required = false) String status,
    @RequestParam(value = "startTime", required = false) String startTime,
    @RequestParam(value = "endTime", required = false) String endTime,*/

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRankId() {
        return rankId;
    }

    public void setRankId(String rankId) {
        this.rankId = rankId;
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

    public String getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(String highLevelId) {
        this.highLevelId = highLevelId;
    }

    public String getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(String isProxy) {
        this.isProxy = isProxy;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}
