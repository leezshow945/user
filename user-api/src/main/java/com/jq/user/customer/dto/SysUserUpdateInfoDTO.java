package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/23
 */
public class SysUserUpdateInfoDTO implements Serializable {

    private Long id;
    private Long siteId;
    private Long deptId;
    private Integer loginCount;
    private Integer loginPwdErrCount;
    private Integer isEnable;
    private Integer openCommand;
    private String command;
    private String allowIp;
    private String userName;
    private String password;
    private Date createTime;
    private Date updateTime;
    private String createBy;
    private String updateBy;
    private Integer isDel;
    private String key;
    private Integer keyErrorCount;
    private String remark;
    private Integer sex;
    private String realName;
    private String email;
    private String mobile;
    private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
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

    public Integer getOpenCommand() {
        return openCommand;
    }

    public void setOpenCommand(Integer openCommand) {
        this.openCommand = openCommand;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
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

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getKeyErrorCount() {
        return keyErrorCount;
    }

    public void setKeyErrorCount(Integer keyErrorCount) {
        this.keyErrorCount = keyErrorCount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "SysUserUpdateInfoDTO{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", deptId=" + deptId +
                ", loginCount=" + loginCount +
                ", loginPwdErrCount=" + loginPwdErrCount +
                ", isEnable=" + isEnable +
                ", openCommand=" + openCommand +
                ", command='" + command + '\'' +
                ", allowIp='" + allowIp + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", isDel=" + isDel +
                ", key='" + key + '\'' +
                ", keyErrorCount='" + keyErrorCount + '\'' +
                ", remark='" + remark + '\'' +
                ", sex=" + sex +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
