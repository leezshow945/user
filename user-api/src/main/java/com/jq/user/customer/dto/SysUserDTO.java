package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Brady
 *         Date: 2018/5/10
 */
public class SysUserDTO implements Serializable{

    /** 主键*/
    private Long id;
    /**  真实姓名*/
    private String realName;
    /**  性别*/
    private Integer sex;
    /**  邮箱*/
    private String email;
    /**  生日*/
    private Date birthday;
    /** 站点id*/
    private Long siteId;
    /** siteCode */
    private String siteCode;
    /** 部门id*/
    private Long deptId;
    /** 登录次数*/
    private Integer loginCount;
    /** 登录密码错误次数*/
    private Integer loginPwdErrCount;
    /** 帐号是否可用*/
    private Integer isEnable;
    /** 是否开启动态口令*/
    private Integer openCommand;
    /** 动态口令*/
    private String command;
    /** 允许登录的ip*/
    private String allowIp;
    /** 帐号*/
    private String userName;
    /** 密码*/
    private String password;
    /** 是否删除*/
    private Integer isDel;
    /**排序字段,默认sort_id**/
    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    /** 当前页 */
    private Integer page = 1;
    /** 每页条数 */
    private Integer limit = 20;
    /** googleAuthenticator秘钥*/
    private String key;
    /** googleAuthenticator秘钥 错误次数 最多5次 */
    private Integer keyErrorCount;
    /** 创建时间*/
    private Date createTime;
    /** 最后更新时间*/
    private Date updateTime;
    /** 创建人*/
    private String createBy;
    /** 最后更新人*/
    private String updateBy;
    /** 手机*/
    private String mobile;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "SysUserDTO{" +
                "id=" + id +
                ", realName='" + realName + '\'' +
                ", sex=" + sex +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", deptId=" + deptId +
                ", loginCount=" + loginCount +
                ", loginPwdErrCount=" + loginPwdErrCount +
                ", isEnable=" + isEnable +
                ", openCommand=" + openCommand +
                ", command='" + command + '\'' +
                ", allowIp='" + allowIp + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", isDel=" + isDel +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", key='" + key + '\'' +
                ", keyErrorCount=" + keyErrorCount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", mobile='" + mobile + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
