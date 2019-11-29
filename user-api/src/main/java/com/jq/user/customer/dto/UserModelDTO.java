package com.jq.user.customer.dto;

import java.io.Serializable;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/18
 */
public class UserModelDTO extends UserRegConfigDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    private Long id;//用户id
    private String referCode; //推广码
    private String userName;//登录帐号
    private String password;//登录密码
    private String newPwd;//新密码
    private String verifyCode;//验证码
    private Integer platformType;//"平台，1->pc，2->app"
    private String dmicCode; //动态口令
    private Long siteId;   //站点id
    private String siteCode;//siteCode
    private String currTime;
    private String payPwd;   //用户支付密码
    private String createByUserName;
    private String ip;
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
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

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    public String getDmicCode() {
        return dmicCode;
    }

    public void setDmicCode(String dmicCode) {
        this.dmicCode = dmicCode;
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

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getCurrTime() {
        return currTime;
    }

    public void setCurrTime(String currTime) {
        this.currTime = currTime;
    }

    public String getCreateByUserName() {
        return createByUserName;
    }

    public void setCreateByUserName(String createByUserName) {
        this.createByUserName = createByUserName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "UserModelDTO{" +
                "id=" + id +
                ", referCode='" + referCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", platformType=" + platformType +
                ", dmicCode='" + dmicCode + '\'' +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", currTime='" + currTime + '\'' +
                ", payPwd='" + payPwd + '\'' +
                ", createByUserName='" + createByUserName + '\'' +
                ", ip='" + ip + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
