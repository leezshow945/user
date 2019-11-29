package com.jq.user.customer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

public class UserWrapper {

    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String loginName;
    private String realName;
    private String status;
    private Integer isProxy;
    private Integer subCount;
    private String highAccount;
    private Integer level;
    private Long rankLevel;
    private Integer loginCount;
    private String lastLoginIp;
    private String regSource;
    private String regIp;
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    private String remark;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long rankId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long highId;

    private Long userId;
    private String userName;
    private String rankName;
    private Long highLevelId;
    private String highLevelAccount;
    private Integer isOnline;
    private Date accessExpire;
    private Long totalAmount=0L;
    private Long canAmount=0L;
    private Long freezeAmount=0L;
    private String path;
    private Long amount=0L;
    private Date lastLoginTime;
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
    private String siteCode;
    // 是否为直属下级 1:是
    private Integer directUser;
    private Integer isValid;

    public Integer getIsOnline() {
        if (accessExpire==null){
            this.isOnline=0;
            return isOnline;
        }
        this.isOnline = accessExpire.after(new Date())?1:0;
        return isOnline;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Date getAccessExpire() {
        return accessExpire;
    }

    public void setAccessExpire(Date accessExpire) {
        this.accessExpire = accessExpire;
    }

    public void setIsOnline(Integer isOnLine) {
        this.isOnline = isOnLine;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Integer getDirectUser() {
        return directUser;
    }

    public void setDirectUser(Integer directUser) {
        this.directUser = directUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public String getHighAccount() {
        return highAccount;
    }

    public void setHighAccount(String highAccount) {
        this.highAccount = highAccount;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Long rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getHighId() {
        return highId;
    }

    public void setHighId(Long highId) {
        this.highId = highId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getCanAmount() {
        return canAmount;
    }

    public void setCanAmount(Long canAmount) {
        this.canAmount = canAmount;
    }

    public Long getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(Long freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
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

    @Override
    public String toString() {
        return "UserWrapper{" +
                "id=" + id +
                ", loginName='" + loginName + '\'' +
                ", realName='" + realName + '\'' +
                ", status='" + status + '\'' +
                ", isProxy='" + isProxy + '\'' +
                ", subCount='" + subCount + '\'' +
                ", highAccount='" + highAccount + '\'' +
                ", level=" + level +
                ", rankLevel=" + rankLevel +
                ", loginCount=" + loginCount +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", regSource='" + regSource + '\'' +
                ", regIp='" + regIp + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", rankId=" + rankId +
                ", siteId=" + siteId +
                ", highId=" + highId +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", rankName='" + rankName + '\'' +
                ", highLevelId=" + highLevelId +
                ", highLevelAccount='" + highLevelAccount + '\'' +
                ", isOnline=" + isOnline +
                ", accessExpire=" + accessExpire +
                ", totalAmount=" + totalAmount +
                ", canAmount=" + canAmount +
                ", freezeAmount=" + freezeAmount +
                ", path='" + path + '\'' +
                ", amount=" + amount +
                ", lastLoginTime=" + lastLoginTime +
                ", gpcRebate=" + gpcRebate +
                ", flcRebate=" + flcRebate +
                ", tycpRebate=" + tycpRebate +
                ", qtRebate=" + qtRebate +
                ", tyRebate=" + tyRebate +
                ", dpcRebate=" + dpcRebate +
                ", lhcRebate0=" + lhcRebate0 +
                ", lhcRebate1=" + lhcRebate1 +
                ", lhcRebate2=" + lhcRebate2 +
                ", lhcRebate3=" + lhcRebate3 +
                ", siteCode='" + siteCode + '\'' +
                ", directUser=" + directUser +
                ", isValid=" + isValid +
                '}';
    }
}
