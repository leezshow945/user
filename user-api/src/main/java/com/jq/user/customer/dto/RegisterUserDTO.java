package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author jason
 * @Date 2019/4/8 19:00
 * @Version 1.0
 **/
public class RegisterUserDTO implements Serializable {

    private static final long serialVersionUID = -6022888976790084311L;

    private Long id;
    private String userName;
    private Date lastLoginTime;
    private String lastLoginIP;
    private Integer status;
    private String siteCode;
    private String realName;
    private String highLevelAccount;
    private Integer isProx;
    private Long totalAmount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public Integer getIsProx() {
        return isProx;
    }

    public void setIsProx(Integer isProx) {
        this.isProx = isProx;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "RegisterUserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIP='" + lastLoginIP + '\'' +
                ", status=" + status +
                ", siteCode='" + siteCode + '\'' +
                ", realName='" + realName + '\'' +
                ", highLevelAccount='" + highLevelAccount + '\'' +
                ", isProx=" + isProx +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
