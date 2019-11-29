package com.jq.user.score.dto;

import java.io.Serializable;
import java.util.Date;

public class UserSignDTO implements Serializable {

    private Long userId;
    private Long siteId;
    private Date signTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    @Override
    public String toString() {
        return "UserSignDTO{" +
         ", userId=" + userId +
         ", siteId=" + siteId +
         ", signTime=" + signTime +
         '}';
    }
}
