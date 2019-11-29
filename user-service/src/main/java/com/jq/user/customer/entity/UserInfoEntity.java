package com.jq.user.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @Author: levi
 * @Descript:
 * @Date: 2018/4/11
 */
@TableName(value = "user_info")
public class UserInfoEntity {
    /** 主键id */
    private Long id;
    /** 照片 */
    private String photo;
    /** 性别 */
    private Integer sex;
    /** 生日 */
    private Date birthday;
    /** 身份id */
    private Long provinceId;
    /** 区域id */
    private Long regionId;
    /** 城市id */
    private Long cityId;
    /** 地址 */
    private String address;
    /** 身份证号 */
    private String cardNo;
    /** qq */
    private String qq;
    /** 微信 */
    private String weChat;
    /** 介绍人 */
    private Long inviter;
    /** 注册ip */
    private String regIp;
    /** 注册Url */
    private String regUrl;
    /** 注册员 */
    private Integer regSource;
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
    private String email;
    /** 手机号码 */
    private String mobile;
    /** 真实姓名 */
    private String realName;
    /** 昵称 */
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
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

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
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
}
