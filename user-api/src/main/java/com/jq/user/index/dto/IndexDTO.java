package com.jq.user.index.dto;

import java.io.Serializable;

public class IndexDTO implements Serializable{
    // 开始日期
    private String startDate;
    // 结束日期
    private String endDate;
    // 站点id
    private Long siteId;
    //站点code
    private String siteCode;
    private Integer type;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IndexDTO{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", type=" + type +
                '}';
    }
}
