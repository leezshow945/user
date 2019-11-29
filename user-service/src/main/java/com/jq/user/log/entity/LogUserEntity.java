package com.jq.user.log.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

@TableName("log_user")
public class LogUserEntity  {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private Integer plat;
    private String siteCode;
    private String userName;
    private String content;
    private Long userId;
    private String loginIp;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date loginTime;
    private String loginArea;
    private Integer isDiffAreaLogin;
    private String type;
    private String accountType;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteId;
    private Integer isDel;
    private String flagType;
    private String loginUrl;

    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;
    @TableField(exist = false)
    private String startTime;
    @TableField(exist = false)
    private String endTime;

    @TableField(exist = false)
    private String orderByClause;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getPlat() {
        return plat;
    }

    public void setPlat(Integer plat) {
        this.plat = plat;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginArea() {
        return loginArea;
    }

    public void setLoginArea(String loginArea) {
        this.loginArea = loginArea;
    }

    public Integer getIsDiffAreaLogin() {
        return isDiffAreaLogin;
    }

    public void setIsDiffAreaLogin(Integer isDiffAreaLogin) {
        this.isDiffAreaLogin = isDiffAreaLogin;
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

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getFlagType() {
        return flagType;
    }

    public void setFlagType(String flagType) {
        this.flagType = flagType;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    @Override
    public String toString() {
        return "LogUserEntity{" +
                "id=" + id +
                ", plat=" + plat +
                ", siteCode='" + siteCode + '\'' +
                ", userName='" + userName + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", loginIp='" + loginIp + '\'' +
                ", loginTime=" + loginTime +
                ", loginArea='" + loginArea + '\'' +
                ", isDiffAreaLogin=" + isDiffAreaLogin +
                ", type='" + type + '\'' +
                ", accountType='" + accountType + '\'' +
                ", siteId=" + siteId +
                ", isDel=" + isDel +
                ", flagType='" + flagType + '\'' +
                ", loginUrl='" + loginUrl + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", orderByClause='" + orderByClause + '\'' +
                '}';
    }
}
