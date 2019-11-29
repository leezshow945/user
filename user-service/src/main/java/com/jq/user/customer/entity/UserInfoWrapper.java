package com.jq.user.customer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserInfoWrapper {
    private String realName;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date birthday;
    private Integer sex;
    private String tradeCode;
    private String cardNo;
    private String mobile;
    private String email;
    private String qq;
    private String province;
    private String city;
    private Long cityId;
    private String address;
    private String loginName;
    private String weChat;
    private String remark;

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "UserInfoWrapper{" +
                "realName='" + realName + '\'' +
                ", birthday=" + birthday +
                ", sex=" + sex +
                ", tradeCode='" + tradeCode + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", cityId=" + cityId +
                ", address='" + address + '\'' +
                ", loginName='" + loginName + '\'' +
                ", weChat='" + weChat + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
