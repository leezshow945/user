package com.jq.user.log.dto;

import java.io.Serializable;
import java.util.Date;

public class LogUserDTO implements Serializable {

    private Long id;
    private Integer plat;
    private String siteCode;
    private String userName;
    private String content;
    private Long userId;
    private String loginIp;
    private Date loginTime;
    private String loginArea;
    private Integer isDiffAreaLogin;
    private String type;
    private String accountType;
    private Long siteId;
    private Integer isDel;
    private String flagType;
    private String loginUrl;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPlat() {
        return plat;
    }

    public void setPlat(Integer plat) {
        this.plat = plat;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
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

    @Override
    public String toString() {
        return "LogUserDTO{" +
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
                '}';
    }
}
