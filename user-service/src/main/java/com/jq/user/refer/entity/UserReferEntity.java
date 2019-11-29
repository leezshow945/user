package com.jq.user.refer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jq.user.support.BaseEntity;

/**
 * 〈推广链接〉
 *
 * @author Json
 * @create 2018/4/25
 */
@TableName("user_refer")
public class UserReferEntity extends BaseEntity {

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
    private Long  flcRebate;

    /** 高频彩返点  **/
    private Long  gpcRebate;

    /** 体育彩票返点  **/
    private Long  tycpRebate;

    /** 其他返点  **/
    private Long  qtRebate;

    /** 体育返点  **/
    private Long  tyRebate;

    /** 六合彩返点0，单位：万  **/
    private Long  lhcRebate0;

    /** 六合彩返点1，单位：万  **/
    private Long  lhcRebate1;

    /** 六合彩返点2，单位：万  **/
    private Long  lhcRebate2;

    /** 六合彩返点3，单位：万  **/
    private Long  lhcRebate3;

    /** 低频彩返点，单位：万  **/
    private Long  dpcRebate;

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


    public Long getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(Long flcRebate) {
        this.flcRebate = flcRebate;
    }

    public Long getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Long gpcRebate) {
        this.gpcRebate = gpcRebate;
    }

    public Long getTycpRebate() {
        return tycpRebate;
    }

    public void setTycpRebate(Long tycpRebate) {
        this.tycpRebate = tycpRebate;
    }

    public Long getQtRebate() {
        return qtRebate;
    }

    public void setQtRebate(Long qtRebate) {
        this.qtRebate = qtRebate;
    }

    public Long getTyRebate() {
        return tyRebate;
    }

    public void setTyRebate(Long tyRebate) {
        this.tyRebate = tyRebate;
    }

    public Long getLhcRebate0() {
        return lhcRebate0;
    }

    public void setLhcRebate0(Long lhcRebate0) {
        this.lhcRebate0 = lhcRebate0;
    }

    public Long getLhcRebate1() {
        return lhcRebate1;
    }

    public void setLhcRebate1(Long lhcRebate1) {
        this.lhcRebate1 = lhcRebate1;
    }

    public Long getLhcRebate2() {
        return lhcRebate2;
    }

    public void setLhcRebate2(Long lhcRebate2) {
        this.lhcRebate2 = lhcRebate2;
    }

    public Long getLhcRebate3() {
        return lhcRebate3;
    }

    public void setLhcRebate3(Long lhcRebate3) {
        this.lhcRebate3 = lhcRebate3;
    }

    public Long getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Long dpcRebate) {
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

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getLinkType() {
        return linkType;
    }

    public void setLinkType(Integer linkType) {
        this.linkType = linkType;
    }

    public Integer getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        this.useCount = useCount;
    }
}
