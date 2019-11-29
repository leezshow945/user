package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/22
 */
public class UserDemoReqDTO implements Serializable {

    private String loginBeginTime;  //登录时间开始
    private String loginEndTime;  //登录时间结束
    private String orderBeginTime;  //游戏时间开始
    private String orderEndTime;  //游戏时间结束
    private String gameCode;  //游戏类型
    private String userName;//用户名
    private Long id;//用户id
    private Integer status;//用户状态
    private Integer loginCount;//登录次数
    private Date createTime;//创建时间
    private String realName;//真实姓名
    private Integer rankLevel;//等级
    private Integer isProxy;//是否代理
    private Long userRankId;//用户等级id
    private Long totalCount;//用户总资产

    /**
     * 排序字段,默认sort_id
     **/
    private String orderField = "id";
    /**
     * 排序方向,默认降序
     **/
    private String orderDirection = "desc";
    /**
     * 当前页
     */
    private Integer page = 1;
    /**
     * 每页条数
     */
    private Integer limit = 20;

    public String getLoginBeginTime() {
        return loginBeginTime;
    }

    public void setLoginBeginTime(String loginBeginTime) {
        this.loginBeginTime = loginBeginTime;
    }

    public String getLoginEndTime() {
        return loginEndTime;
    }

    public void setLoginEndTime(String loginEndTime) {
        this.loginEndTime = loginEndTime;
    }

    public String getOrderBeginTime() {
        return orderBeginTime;
    }

    public void setOrderBeginTime(String orderBeginTime) {
        this.orderBeginTime = orderBeginTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String uerName) {
        this.userName = uerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
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

    public Long getUserRankId() {
        return userRankId;
    }

    public void setUserRankId(Long userRankId) {
        this.userRankId = userRankId;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "UserDemoReqDTO{" +
                "loginBeginTime='" + loginBeginTime + '\'' +
                ", loginEndTime='" + loginEndTime + '\'' +
                ", orderBeginTime='" + orderBeginTime + '\'' +
                ", orderEndTime='" + orderEndTime + '\'' +
                ", gameCode='" + gameCode + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", loginCount=" + loginCount +
                ", createTime=" + createTime +
                ", realName='" + realName + '\'' +
                ", rankLevel=" + rankLevel +
                ", isProxy=" + isProxy +
                ", userRankId=" + userRankId +
                ", totalCount=" + totalCount +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
