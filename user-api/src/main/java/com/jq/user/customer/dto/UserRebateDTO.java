package com.jq.user.customer.dto;


import java.io.Serializable;
import java.util.Date;

public class UserRebateDTO implements Serializable{

    /** 主键id */
    private Long id;

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
    // --------------------------------------------
    private Long userId;
    private String password;
    private String realName;
    private String loginName;
    private String status;
    private Integer isProxy;
    private Integer subCount;
    private String highAccount;
    private Integer level;
    private Integer rankLevel;
    private Integer loginCount;
    // 登录天数
    private Integer loginCountByDay;
    private String lastLoginIp;
    private Integer regSource;
    private String regIp;
    private Date createTime;
    private String remark;
    private Long rankId;
    private Long siteId;
    private Long highId;
    private String path;
    private Long  highLevelId;
    private String mobile;
    /** 冻结余额，单位：分 **/
    private Long freezeAmount;
    /** 可用余额，单位：分 **/
    private Long canAmount;
    /** 总资产 **/
    private Long totalAmount;
    // 累计分红
    private long bonusTotal;
    // 累计契约分红
    private long contractBonusTotal;
    // 累计日工资
    private long wagesTotal;
    // 累计契约日工资
    private long contractWagesTotal;
    private long totalMakeWater;
    private String controlStatus;
    private String userName;
    private Date lastLoginTime;
    // 性别 1-男,0女
    private Integer sex;
    private Date birthDay;
    // 身份证号
    private String cardNo;
    private String siteCode;

    private String defaultBankName;
    private String defaultBankNo;
    private String regUrl;
    // 是否在线 1-在线,0-不在线
    private Integer isOnline;
    private String rankName;
    private Date banRebateTime;
    /**是否禁止返点 */
    private boolean isBanRebate;
    // 是否为直属下级 1:是
    private Integer directUser;
    private Integer isDemo;
    private Integer isValid;

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
    /**用户迁移时间*/
    private Date transferTime;

    public Integer getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Integer isDemo) {
        this.isDemo = isDemo;
    }

    public Integer getDirectUser() {
        return directUser;
    }

    public void setDirectUser(Integer directUser) {
        this.directUser = directUser;
    }

    public boolean isBanRebate() {
        return isBanRebate;
    }

    public void setBanRebate(boolean banRebate) {
        isBanRebate = banRebate;
    }

    public Date getBanRebateTime() {
        return banRebateTime;
    }

    public void setBanRebateTime(Date banRebateTime) {
        this.banRebateTime = banRebateTime;
    }

    public Integer getLoginCountByDay() {
        return loginCountByDay;
    }

    public void setLoginCountByDay(Integer loginCountByDay) {
        this.loginCountByDay = loginCountByDay;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public long getBonusTotal() {
        return bonusTotal;
    }

    public void setBonusTotal(long bonusTotal) {
        this.bonusTotal = bonusTotal;
    }

    public long getContractBonusTotal() {
        return contractBonusTotal;
    }

    public void setContractBonusTotal(long contractBonusTotal) {
        this.contractBonusTotal = contractBonusTotal;
    }

    public long getWagesTotal() {
        return wagesTotal;
    }

    public void setWagesTotal(long wagesTotal) {
        this.wagesTotal = wagesTotal;
    }

    public long getContractWagesTotal() {
        return contractWagesTotal;
    }

    public void setContractWagesTotal(long contractWagesTotal) {
        this.contractWagesTotal = contractWagesTotal;
    }

    public Integer getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(Integer isOnline) {
        this.isOnline = isOnline;
    }

    public String getDefaultBankName() {
        return defaultBankName;
    }

    public void setDefaultBankName(String defaultBankName) {
        this.defaultBankName = defaultBankName;
    }

    public String getDefaultBankNo() {
        return defaultBankNo;
    }

    public void setDefaultBankNo(String defaultBankNo) {
        this.defaultBankNo = defaultBankNo;
    }

    public String getRegUrl() {
        return regUrl;
    }

    public void setRegUrl(String regUrl) {
        this.regUrl = regUrl;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    public Long getFreezeAmount() {
        return freezeAmount;
    }

    public void setFreezeAmount(Long freezeAmount) {
        this.freezeAmount = freezeAmount;
    }

    public Long getCanAmount() {
        return canAmount;
    }

    public void setCanAmount(Long canAmount) {
        this.canAmount = canAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
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

    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
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

    public Integer getRegSource() {
        return regSource;
    }

    public void setRegSource(Integer regSource) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public long getTotalMakeWater() {
        return totalMakeWater;
    }

    public void setTotalMakeWater(long totalMakeWater) {
        this.totalMakeWater = totalMakeWater;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    @Override
    public String toString() {
        return "UserRebateDTO{" +
                "id=" + id +
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
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", loginName='" + loginName + '\'' +
                ", status='" + status + '\'' +
                ", isProxy=" + isProxy +
                ", subCount=" + subCount +
                ", highAccount='" + highAccount + '\'' +
                ", level=" + level +
                ", rankLevel=" + rankLevel +
                ", loginCount=" + loginCount +
                ", loginCountByDay=" + loginCountByDay +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", regSource=" + regSource +
                ", regIp='" + regIp + '\'' +
                ", createTime=" + createTime +
                ", remark='" + remark + '\'' +
                ", rankId=" + rankId +
                ", siteId=" + siteId +
                ", highId=" + highId +
                ", path='" + path + '\'' +
                ", highLevelId=" + highLevelId +
                ", mobile='" + mobile + '\'' +
                ", freezeAmount=" + freezeAmount +
                ", canAmount=" + canAmount +
                ", totalAmount=" + totalAmount +
                ", bonusTotal=" + bonusTotal +
                ", contractBonusTotal=" + contractBonusTotal +
                ", wagesTotal=" + wagesTotal +
                ", contractWagesTotal=" + contractWagesTotal +
                ", totalMakeWater=" + totalMakeWater +
                ", controlStatus='" + controlStatus + '\'' +
                ", userName='" + userName + '\'' +
                ", lastLoginTime=" + lastLoginTime +
                ", sex=" + sex +
                ", birthDay=" + birthDay +
                ", cardNo='" + cardNo + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", defaultBankName='" + defaultBankName + '\'' +
                ", defaultBankNo='" + defaultBankNo + '\'' +
                ", regUrl='" + regUrl + '\'' +
                ", isOnline=" + isOnline +
                ", rankName='" + rankName + '\'' +
                ", banRebateTime=" + banRebateTime +
                ", isBanRebate=" + isBanRebate +
                ", directUser=" + directUser +
                ", isDemo=" + isDemo +
                ", isValid=" + isValid +
                ", transferTime=" + transferTime +
                '}';
    }
}
