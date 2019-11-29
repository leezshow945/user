package com.jq.user.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Created by ZhangCong on 2018/4/13.
 */

@TableName("sys_user")
public class SysUserEntity implements Serializable{
    private static final long serialVersionUID = 1L;

    /** 主键*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**  真实姓名*/
    private String realName;
    /**  性别*/
    private Integer sex;
    /**  邮箱*/
    private String email;
    /**  生日*/
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date birthday;
    /** 手机*/
    private String mobile;
    /** 站点id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteId;
    /** siteCode */
    private String siteCode;
    /** 部门id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long deptId;
    /** 登录次数*/
    private Integer loginCount;
    /** 登录密码错误次数*/
    private Integer loginPwdErrCount;
    /** 帐号是否可用*/
    private Integer isEnable;
    /** 允许登录的ip*/
    private String allowIp;
    /** 帐号*/
    private String userName;
    /** 密码*/
    private String password;
    /** 创建时间*/
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    /** 最后更新时间*/
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;
    /** 创建人*/
    private String createBy;
    /** 最后更新人*/
    private String updateBy;
    /** 帐号状态 1：删除，0：正常*/
    private Integer isDel;
    /** googleAuthenticator秘钥*/
    @TableField(value = "`key`")
    private String key;
    /** 谷歌秘钥验证错误次数 */
    private Integer keyErrorCount;
    /** 备注*/
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getLoginPwdErrCount() {
        return loginPwdErrCount;
    }

    public void setLoginPwdErrCount(Integer loginPwdErrCount) {
        this.loginPwdErrCount = loginPwdErrCount;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public String getAllowIp() {
        return allowIp;
    }

    public void setAllowIp(String allowIp) {
        this.allowIp = allowIp;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getKey() {
        return key;
    }

    public Integer getKeyErrorCount() {
        return keyErrorCount;
    }

    public void setKeyErrorCount(Integer keyErrorCount) {
        this.keyErrorCount = keyErrorCount;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysUserEntity{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", mobile='" + mobile + '\'' +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", deptId=" + deptId +
                ", loginCount=" + loginCount +
                ", loginPwdErrCount=" + loginPwdErrCount +
                ", isEnable=" + isEnable +
                ", allowIp='" + allowIp + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", isDel=" + isDel +
                ", key='" + key + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}