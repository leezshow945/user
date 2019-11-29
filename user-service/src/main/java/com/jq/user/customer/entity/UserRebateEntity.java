package com.jq.user.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

@TableName(value = "user_rebate")
public class UserRebateEntity {
    /** 主键id */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /** 站点id */
    private Long siteId;
    private String siteCode;
    /** 上级id */
//    @JsonSerialize(using = ToStringSerializer.class)
//    private Long highLevelId;
    /** 上级代理账号 */
//    private String highLevelAccount;
    /** 下级会员数量 */
//    private Integer subLevelCount;
    /** 层级 */
//    private Integer level;
    /** 层级路径 */
//    private String path;
    /** 是否代理*/
    private Integer isProxy;
    /** 推广链接 */
    private String referUrl;
    /** 高频彩返点*/
    private Long gpcRebate;
    /** 棋牌返点 */
    private Long flcRebate;
    /** 体育彩票返点 */
    private Long tycpRebate;
    /** 其他返点 */
    private Long qtRebate;
    /** 体育返点 */
    private Long tyRebate;
    /** 低频彩返点 */
    private Long dpcRebate;
    /** 六合彩0 */
    private Long lhcRebate0;
    /** 六合彩1 */
    private Long lhcRebate1;
    /** 六合彩2 */
    private Long lhcRebate2;
    /** 六合彩3 */
    private Long lhcRebate3;
    /** 禁止返点时间*/
    private Date banRebateTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;

    public Date getBanRebateTime() {
        return banRebateTime;
    }

    public void setBanRebateTime(Date banRebateTime) {
        this.banRebateTime = banRebateTime;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }
//
//    public Long getHighLevelId() {
//        return highLevelId;
//    }
//
//    public void setHighLevelId(Long highLevelId) {
//        this.highLevelId = highLevelId;
//    }
//
//    public String getHighLevelAccount() {
//        return highLevelAccount;
//    }
//
//    public void setHighLevelAccount(String highLevelAccount) {
//        this.highLevelAccount = highLevelAccount;
//    }
//
//    public Integer getSubLevelCount() {
//        return subLevelCount;
//    }
//
//    public void setSubLevelCount(Integer subLevelCount) {
//        this.subLevelCount = subLevelCount;
//    }
//
//    public Integer getLevel() {
//        return level;
//    }
//
//    public void setLevel(Integer level) {
//        this.level = level;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public String getReferUrl() {
        return referUrl;
    }

    public void setReferUrl(String referUrl) {
        this.referUrl = referUrl;
    }

    public Long getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Long gpcRebate) {
        this.gpcRebate = gpcRebate;
    }

    public Long getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(Long flcRebate) {
        this.flcRebate = flcRebate;
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

    public Long getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Long dpcRebate) {
        this.dpcRebate = dpcRebate;
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
}
