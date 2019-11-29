package com.jq.user.customer.vo;

import java.io.Serializable;

/**
 * 用户注册/登录参数扩展
 * Created by ZhangCong on 2018/4/17.
 */
public class UserModelVO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;//用户id
    private String referCode; //推广码
    private String userName;//登录帐号
    private String password;//登录密码
    private String newPwd;//新密码
    private String verifyCode;//验证码
    private String verId;//验证码id
    private Integer platformType;//"平台，1->pc，2->app"
    private String dmicCode; //动态口令
    private String payPwd;   //用户支付密码

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

    public String getVerId() {
        return verId;
    }

    public void setVerId(String verId) {
        this.verId = verId;
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

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    @Override
    public String toString() {
        return "UserModelVO{" +
                "id='" + id + '\'' +
                ", referCode='" + referCode + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", newPwd='" + newPwd + '\'' +
                ", verifyCode='" + verifyCode + '\'' +
                ", verId='" + verId + '\'' +
                ", platformType='" + platformType + '\'' +
                ", dmicCode='" + dmicCode + '\'' +
                ", payPwd='" + payPwd + '\'' +
                '}';
    }
}
