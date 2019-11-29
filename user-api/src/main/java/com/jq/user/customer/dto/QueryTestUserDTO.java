package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/13
 */
public class QueryTestUserDTO implements Serializable{


    private String gameCode;  //游戏类型
    private String userName;//用户名
    private Long id;//用户id
    private Integer status;//用户状态
    private Integer loginCount;//登录次数
    private Date createTime;//创建时间
    private String realName;//真实姓名
    private Long userRankId;//等级
    private Integer isProxy;//是否代理.
    private Long siteId;//站点id
    private String path;//层级列表
    private String lastLoginIp;//上次登录ip
    private String remark;//备注
    private String regIp;//注册时Ip
    private String regSource;//注册来源
    private Integer subLevelCount;//下级数量
    private String highLevelAccount;//上次帐户
    private Long highLevelId;//上级用户id
    private Integer level;//层级数量
    private Long totalAmount;//总资产
    private Long canAmount;//可用余额，单位：分
    private Long freezeAmount;//冻结余额，单位：分
    //前台控制弹窗 - 控制按钮选中 0表示正常/1表示异常
    private String controlStatus;

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getUserRankId() {
        return userRankId;
    }

    public void setUserRankId(Long userRankId) {
        this.userRankId = userRankId;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getRegSource() {
        return regSource;
    }

    public void setRegSource(String regSource) {
        this.regSource = regSource;
    }

    public Integer getSubLevelCount() {
        return subLevelCount;
    }

    public void setSubLevelCount(Integer subLevelCount) {
        this.subLevelCount = subLevelCount;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(String controlStatus) {
        this.controlStatus = controlStatus;
    }

    @Override
    public String toString() {
        return "QueryTestUserDTO{" +
                "gameCode='" + gameCode + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                ", status=" + status +
                ", loginCount=" + loginCount +
                ", createTime=" + createTime +
                ", realName='" + realName + '\'' +
                ", userRankId=" + userRankId +
                ", isProxy=" + isProxy +
                ", siteId=" + siteId +
                ", path='" + path + '\'' +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", remark='" + remark + '\'' +
                ", regIp='" + regIp + '\'' +
                ", regSource='" + regSource + '\'' +
                ", subLevelCount=" + subLevelCount +
                ", highLevelAccount='" + highLevelAccount + '\'' +
                ", highLevelId=" + highLevelId +
                ", level=" + level +
                ", totalAmount=" + totalAmount +
                ", canAmount=" + canAmount +
                ", freezeAmount=" + freezeAmount +
                ", controlStatus='" + controlStatus + '\'' +
                '}';
    }
}
