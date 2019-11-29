package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

public class UserInfoDTO implements Serializable {
    /** 用户id */
    private Long userId;
    /** 性别 1-男，0：女 */
    private Integer sex;
    /** 交易密码 */
    private String payPwd;
    /** 生日 */
    private String birthday;
    /** 身份证号 */
    private String cardNo;
    private String email;
    /** */
    private String qq;
    /** */
    private String weChat;
    /** 手机号码 */
    private String mobile;
    /** 省份id */
    private Long provinceId;
    /** 城市do */
    private Long cityId;
    /** 备注 */
    private String remark;
    /** 真实姓名 */
    private String realName;
    /** 上次登录ip */
    private String lastLoginIp;
    /** 城市 */
    private String cityName;
    /** 用户名 */
    private String userName;
    /** 省份 */
    private String provinceName;
    /** 照片 */
    private String photo;
    /** 区域id */
    private Long regionId;
    /** 地址 */
    private String address;
    /** 介绍人 */
    private Long inviter;
    /** 注册ip */
    private String regIp;
    /** 注册Url */
    private String regUrl;
    /** 注册员 */
    private Integer regSource;

    private Date createTime;

    private Date updateTime;
    /** 昵称 */
    private String nickName;

    private Integer rankLevel;

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getInviter() {
        return inviter;
    }

    public void setInviter(Long inviter) {
        this.inviter = inviter;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getRegUrl() {
        return regUrl;
    }

    public void setRegUrl(String regUrl) {
        this.regUrl = regUrl;
    }

    public Integer getRegSource() {
        return regSource;
    }

    public void setRegSource(Integer regSource) {
        this.regSource = regSource;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "UserInfoDTO{" +
                "userId=" + userId +
                ", sex=" + sex +
                ", payPwd='" + payPwd + '\'' +
                ", birthday='" + birthday + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", email='" + email + '\'' +
                ", qq='" + qq + '\'' +
                ", weChat='" + weChat + '\'' +
                ", mobile='" + mobile + '\'' +
                ", provinceId=" + provinceId +
                ", cityId=" + cityId +
                ", remark='" + remark + '\'' +
                ", realName='" + realName + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", cityName='" + cityName + '\'' +
                ", userName='" + userName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", photo='" + photo + '\'' +
                ", regionId=" + regionId +
                ", address='" + address + '\'' +
                ", inviter=" + inviter +
                ", regIp='" + regIp + '\'' +
                ", regUrl='" + regUrl + '\'' +
                ", regSource=" + regSource +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", nickName='" + nickName + '\'' +
                ", rankLevel=" + rankLevel +
                '}';
    }
}
