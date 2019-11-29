package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/23
 */
public class QuerySysUserDTO implements Serializable{

    private Long id;
    private String userName;
    private String realName;
    private String password;
    private Long deptId;
    private Integer sex;
    private Integer isEnable;
    private String email;
    private String mobile;
    private Date birthday;
    private Long siteId;
    private String siteCode;
    private String key;
    private String allowIp;
    private String remark;
    private String createTime;
    private String loginName;
    /**排序字段,默认sort_id**/
    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    /** 当前页 */
    private Integer page = 1;
    /** 每页条数 */
    private Integer limit = 20;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAllowIp() {
        return allowIp;
    }

    public void setAllowIp(String allowIp) {
        this.allowIp = allowIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "QuerySysUserDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", realName='" + realName + '\'' +
                ", password='" + password + '\'' +
                ", deptId=" + deptId +
                ", sex=" + sex +
                ", isEnable=" + isEnable +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", birthday=" + birthday +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", key='" + key + '\'' +
                ", allowIp='" + allowIp + '\'' +
                ", remark='" + remark + '\'' +
                ", createTime='" + createTime + '\'' +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
