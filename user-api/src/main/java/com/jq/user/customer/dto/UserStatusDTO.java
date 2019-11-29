package com.jq.user.customer.dto;

import java.io.Serializable;

/**
 * @author Brady
 *         Date: 2018/5/10
 */
public class UserStatusDTO implements Serializable{

    /** 用户id */
    private Long id;
    /** 用户名 */
    private String userName;
    /** 站点code */
    private String siteCode;
    /** 是否是试玩账号 */
    private Integer isDemo;
    /**用户状态*/
    private Integer status;

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

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Integer isDemo) {
        this.isDemo = isDemo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserStatusDTO{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", isDemo=" + isDemo +
                ", status=" + status +
                '}';
    }
}
