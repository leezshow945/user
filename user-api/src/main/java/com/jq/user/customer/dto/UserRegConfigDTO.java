package com.jq.user.customer.dto;

import java.io.Serializable;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/21
 */
public class UserRegConfigDTO implements Serializable {

    //真实姓名
    private String realName;
    //手机号码
    private String mobile;
    //邮箱
    private String email;
    //支付密码
    private String payPassword;
    //介绍人
    private String inviter;
    //家庭住址
    private String address;
    //生日
    private String birthDate;
    //qq号码
    private String qq;
    //微信号码
    private String weChat;
    //身份证号码
    private String cartNo;
    //备注
    private String remark;
    //最后登录ip
    private String lastLoginIp;
    /**昵称*/
    private String nickName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
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

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getCartNo() {
        return cartNo;
    }

    public void setCartNo(String cartNo) {
        this.cartNo = cartNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "UserRegConfigDTO{" +
                "realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", payPassword='" + payPassword + '\'' +
                ", inviter='" + inviter + '\'' +
                ", address='" + address + '\'' +
                ", birthDate='" + birthDate + '\'' +
                ", qq='" + qq + '\'' +
                ", weChat='" + weChat + '\'' +
                ", cartNo='" + cartNo + '\'' +
                ", remark='" + remark + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
