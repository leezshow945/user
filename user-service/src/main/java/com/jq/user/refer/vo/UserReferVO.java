package com.jq.user.refer.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈推广链接〉
 *
 * @author Json
 * @create 2018/4/25
 */
public class UserReferVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**主键*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /** 用户ID  **/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  userId;

    /** 站点id  **/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long  siteId;

    /**站点code**/
    private String siteCode;

    /** 推广代码  **/
    private String  referCode;

    /** 推广地址url  **/
    private String  referUrl;

    /** 动态域名  **/
    private String  domainUrl;

    /** 棋牌返点  **/
    private Double  flcRebate=0d;

    /** 高频彩返点  **/
    private Double  gpcRebate=0d;

    /** 体育彩票返点  **/
    private Double  tycpRebate=0d;

    /** 其他返点  **/
    private Double  qtRebate=0d;

    /** 体育返点  **/
    private Double  tyRebate=0d;

    /** 六合彩返点0，单位：万  **/
    private Double  lhcRebate0=0d;

    /** 六合彩返点1，单位：万  **/
    private Double  lhcRebate1=0d;

    /** 六合彩返点2，单位：万  **/
    private Double  lhcRebate2=0d;

    /** 六合彩返点3，单位：万  **/
    private Double  lhcRebate3=0d;

    /** 低频彩返点，单位：万  **/
    private Double  dpcRebate=0d;

    /** 注册会员是否是代理  **/
    private Integer  isProxy;

    /** 是否是手机端 T :app端，F: pc端  **/
    private Integer  isApp;

    /** 是否启用T是，F否  **/
    private Integer  isEnable;
    /**链接类型  0 站内  1 外链*/
    private Integer linkType;
    /**
     * 0 六合彩组0返点
     * 1 六合彩组1返点
     * 2 六合彩组2返点
     * 3 六合彩组3返点
     * 4 福利彩票返点
     * 5 高频彩返点
     * 6 体育彩票返点
     * 7 其他返点
     * 8 体育返点
     * 9 低频彩返点
     */
    private Integer rebateType;

    /** 返点开始值 */
    private Long startRebate;

    /** 返点结束值*/
    private Long endRebate;

    /**开始时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    /**结束时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public String getDomainUrl() {
        return domainUrl;
    }

    public void setDomainUrl(String domainUrl) {
        this.domainUrl = domainUrl;
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

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
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
}
