package com.jq.user.refer.dto;

import com.jq.user.common.BaseDTO;

import java.io.Serializable;

/**
 * 〈〉
 *
 * @author Json
 * @create 2018/4/25
 */
public class UserReferDTO extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    /**主键*/
    private Long id;

    /** 用户ID  **/
    private Long  userId;

    /**账户名**/
    private String userName;

    /** 站点id  **/
    private Long  siteId;

    /**站点code**/
    private String siteCode;

    /** 推广代码  **/
    private String  referCode;

    /** 推广地址url  **/
    private String  referUrl;

    /** 动态域名  **/
    private String  domainUrl;

    /**自定义地址**/
    private String  domainUrl1;

    /** 棋牌返点  **/
    private Double  flcRebate;

    /** 高频彩返点  **/
    private Double  gpcRebate;

    /** 体育彩票返点  **/
    private Double  tycpRebate;

    /** 其他返点  **/
    private Double  qtRebate;

    /** 体育返点  **/
    private Double  tyRebate;

    /** 六合彩返点0，单位：万  **/
    private Double  lhcRebate0;

    /** 六合彩返点1，单位：万  **/
    private Double  lhcRebate1;

    /** 六合彩返点2，单位：万  **/
    private Double  lhcRebate2;

    /** 六合彩返点3，单位：万  **/
    private Double  lhcRebate3;

    /** 低频彩返点，单位：万  **/
    private Double  dpcRebate;

    /** 注册会员是否是代理  **/
    private Integer  isProxy;

    /** 是否是手机端 T :app端，F: pc端  **/
    private Integer  isApp;

    /** 是否启用T是，F否  **/
    private Integer  isEnable;

    /**链接类型  0 站内  1 自定义域名*/
    private Integer linkType;
    /**推广链接使用次数*/
    private Integer useCount;

    /**
     * 1 六合彩组0返点
     * 2 六合彩组1返点
     * 3 六合彩组2返点
     * 4 六合彩组3返点
     * 5 福利彩票返点
     * 6 高频彩返点
     * 7 体育彩票返点
     * 8 其他返点
     * 9 体育返点
     * 10 低频彩返点
     */
    private Integer rebateType;

    /** 返点开始值 */
    private Long startRebate;

    /** 返点结束值*/
    private Long endRebate;

    /**开始时间*/
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String startDate;

    /**结束时间*/
//    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String endDate;

    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    private Integer page=1;
    private Integer limit=20;

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

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getRebateType() {
        return rebateType;
    }

    public void setRebateType(Integer rebateType) {
        this.rebateType = rebateType;
    }

    public Long getStartRebate() {
        return startRebate;
    }

    public void setStartRebate(Long startRebate) {
        this.startRebate = startRebate;
    }

    public Long getEndRebate() {
        return endRebate;
    }

    public void setEndRebate(Long endRebate) {
        this.endRebate = endRebate;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public Double getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(Double flcRebate) {
        this.flcRebate = flcRebate;
    }

    public Double getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Double gpcRebate) {
        this.gpcRebate = gpcRebate;
    }

    public Double getTycpRebate() {
        return tycpRebate;
    }

    public void setTycpRebate(Double tycpRebate) {
        this.tycpRebate = tycpRebate;
    }

    public Double getQtRebate() {
        return qtRebate;
    }

    public void setQtRebate(Double qtRebate) {
        this.qtRebate = qtRebate;
    }

    public Double getTyRebate() {
        return tyRebate;
    }

    public void setTyRebate(Double tyRebate) {
        this.tyRebate = tyRebate;
    }

    public Double getLhcRebate0() {
        return lhcRebate0;
    }

    public void setLhcRebate0(Double lhcRebate0) {
        this.lhcRebate0 = lhcRebate0;
    }

    public Double getLhcRebate1() {
        return lhcRebate1;
    }

    public void setLhcRebate1(Double lhcRebate1) {
        this.lhcRebate1 = lhcRebate1;
    }

    public Double getLhcRebate2() {
        return lhcRebate2;
    }

    public void setLhcRebate2(Double lhcRebate2) {
        this.lhcRebate2 = lhcRebate2;
    }

    public Double getLhcRebate3() {
        return lhcRebate3;
    }

    public void setLhcRebate3(Double lhcRebate3) {
        this.lhcRebate3 = lhcRebate3;
    }

    public Double getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Double dpcRebate) {
        this.dpcRebate = dpcRebate;
    }

    public Integer getIsApp() {
        return isApp;
    }

    public void setIsApp(Integer isApp) {
        this.isApp = isApp;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDomainUrl1() {
        return domainUrl1;
    }

    public void setDomainUrl1(String domainUrl1) {
        this.domainUrl1 = domainUrl1;
    }


    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }


    @Override
    public String toString() {
        return "UserReferDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", referCode='" + referCode + '\'' +
                ", referUrl='" + referUrl + '\'' +
                ", domainUrl='" + domainUrl + '\'' +
                ", domainUrl1='" + domainUrl1 + '\'' +
                ", flcRebate=" + flcRebate +
                ", gpcRebate=" + gpcRebate +
                ", tycpRebate=" + tycpRebate +
                ", qtRebate=" + qtRebate +
                ", tyRebate=" + tyRebate +
                ", lhcRebate0=" + lhcRebate0 +
                ", lhcRebate1=" + lhcRebate1 +
                ", lhcRebate2=" + lhcRebate2 +
                ", lhcRebate3=" + lhcRebate3 +
                ", dpcRebate=" + dpcRebate +
                ", isProxy=" + isProxy +
                ", isApp=" + isApp +
                ", isEnable=" + isEnable +
                ", linkType=" + linkType +
                ", useCount=" + useCount +
                ", rebateType=" + rebateType +
                ", startRebate=" + startRebate +
                ", endRebate=" + endRebate +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
