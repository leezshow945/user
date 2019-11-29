package com.jq.user.customer.dto;

import java.io.Serializable;

public class AddUserDTO implements Serializable {
    private String sysUserName;
    private Long siteId;
    private String highLevelName;
    private Integer isProxy;
    private String userName;
    private String password;
    private String realName;
    private Double gpcRebate;
    private Double flcRebate;
    private Double tycpRebate;
    private Double qtRebate;
    private Double tyRebate;
    private Double dpcRebate;
    private Double lhcRebate0;
    private Double lhcRebate1;
    private Double lhcRebate2;
    private Double lhcRebate3;
    private String siteCode;
    private Integer isDemo;
    // 交易密码
    private String payPwd;
    //注册id与注册区域
    private String regIP;
    // 注册来源 1-电脑端,2-手机端
    private Integer platformType;
    private String regUrl;

    public String getRegUrl() {
        return regUrl;
    }

    public void setRegUrl(String regUrl) {
        this.regUrl = regUrl;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getRegIP() {
        return regIP;
    }

    public void setRegIP(String regIP) {
        this.regIP = regIP;
    }
    

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getHighLevelName() {
        return highLevelName;
    }

    public void setHighLevelName(String highLevelName) {
        this.highLevelName = highLevelName;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Double getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Double gpcRebate) {
        this.gpcRebate = gpcRebate;
    }

    public Double getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(Double flcRebate) {
        this.flcRebate = flcRebate;
    }

    public Double getTycpRebate() {
        return tycpRebate;
    }

    public void setTycpRebate(Double tycpRebate) {
        this.tycpRebate = tycpRebate;
    }

    public Double getQtRebate() {
        return qtRebate;
    }

    public void setQtRebate(Double qtRebate) {
        this.qtRebate = qtRebate;
    }

    public Double getTyRebate() {
        return tyRebate;
    }

    public void setTyRebate(Double tyRebate) {
        this.tyRebate = tyRebate;
    }

    public Double getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Double dpcRebate) {
        this.dpcRebate = dpcRebate;
    }

    public Double getLhcRebate0() {
        return lhcRebate0;
    }

    public void setLhcRebate0(Double lhcRebate0) {
        this.lhcRebate0 = lhcRebate0;
    }

    public Double getLhcRebate1() {
        return lhcRebate1;
    }

    public void setLhcRebate1(Double lhcRebate1) {
        this.lhcRebate1 = lhcRebate1;
    }

    public Double getLhcRebate2() {
        return lhcRebate2;
    }

    public void setLhcRebate2(Double lhcRebate2) {
        this.lhcRebate2 = lhcRebate2;
    }

    public Double getLhcRebate3() {
        return lhcRebate3;
    }

    public void setLhcRebate3(Double lhcRebate3) {
        this.lhcRebate3 = lhcRebate3;
    }

    public Integer getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Integer isDemo) {
        this.isDemo = isDemo;
    }

    @Override
    public String toString() {
        return "AddUserDTO{" +
                "sysUserName='" + sysUserName + '\'' +
                ", siteId=" + siteId +
                ", highLevelName='" + highLevelName + '\'' +
                ", isProxy=" + isProxy +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", gpcRebate=" + gpcRebate +
                ", flcRebate=" + flcRebate +
                ", tycpRebate=" + tycpRebate +
                ", qtRebate=" + qtRebate +
                ", tyRebate=" + tyRebate +
                ", dpcRebate=" + dpcRebate +
                ", lhcRebate0=" + lhcRebate0 +
                ", lhcRebate1=" + lhcRebate1 +
                ", lhcRebate2=" + lhcRebate2 +
                ", lhcRebate3=" + lhcRebate3 +
                ", siteCode='" + siteCode + '\'' +
                ", isDemo=" + isDemo +
                ", payPwd='" + payPwd + '\'' +
                ", regIP='" + regIP + '\'' +
                ", platformType=" + platformType +
                ", regUrl='" + regUrl + '\'' +
                '}';
    }
}
