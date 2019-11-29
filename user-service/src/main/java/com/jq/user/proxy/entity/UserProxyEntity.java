package com.jq.user.proxy.entity;


import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName("user_proxy")
public class UserProxyEntity {
    private Long id;
    private Long userId;
    private Long highUserId;
    private String userName;
    private String highUserName;
    private Integer level;
    private Long siteId;
    private String siteCode;
    private Date createTime;
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHighUserId() {
        return highUserId;
    }

    public void setHighUserId(Long highUserId) {
        this.highUserId = highUserId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHighUserName() {
        return highUserName;
    }

    public void setHighUserName(String highUserName) {
        this.highUserName = highUserName;
    }

    @Override
    public String toString() {
        return "UserProxyEntity{" +
                "id=" + id +
                ", userId=" + userId +
                ", highUserId=" + highUserId +
                ", userName='" + userName + '\'' +
                ", highUserName='" + highUserName + '\'' +
                ", level=" + level +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
