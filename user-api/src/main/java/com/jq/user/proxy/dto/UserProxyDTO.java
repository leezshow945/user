package com.jq.user.proxy.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class UserProxyDTO implements Serializable {
    private Long id;
    // 用户id
    private Long userId;
    // 上级用户id
    private Long highUserId;
    // 用户名
    private String userName;
    // 上级用户名
    private String highUserName;
    // 间隔层级(若level=1,则为直属关系)
    private Integer level;
    // 站点id
    private Long siteId;
    // 站点code
    private String siteCode;
    private Date createTime;
    private Date updateTime;

    private List<Long> idList;
    // 1-直属上级，2-直属下级，3-所有下级,4-所有上级
    private Integer proxyRelation;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Integer getProxyRelation() {
        return proxyRelation;
    }

    public void setProxyRelation(Integer proxyRelation) {
        this.proxyRelation = proxyRelation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getHighUserId() {
        return highUserId;
    }

    public void setHighUserId(Long highUserId) {
        this.highUserId = highUserId;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    @Override
    public String toString() {
        return "UserProxyDTO{" +
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
                ", idList=" + idList +
                ", proxyRelation=" + proxyRelation +
                '}';
    }
}
