package com.jq.user.customer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.io.Serializable;
import java.util.Date;


/**
 * @author Created by ZhangCong on 2018/4/4.
 */

@TableName("user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 站点id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long siteId;
    /**
     * siteCode
     */
    private String siteCode;
    /**
     * 会员等级id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userRankId;
    /**
     * 连续签到次数
     */
    private Integer signCount;
    /**
     * 登陆次数
     */
    private Integer loginCount;
    /**
     * 登录密码连续错误次数
     */
    private Integer loginErrCount;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 密码
     */
    @TableField(value = "`password`")
    private String password;
    /**
     * 创建时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    /**
     * 最后更新时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date updateTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 最后更新人
     */
    private String updateBy;
    /**
     * 用户状态 1：删除，0：正常
     */
    private Integer isDel;
    /**
     * googleAuthenticator秘钥
     */
    @TableField(value = "`key`")
    private String key;
    /**
     * 上次签到时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date lastSignTime;
    /**
     * 上次登录时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date lastLoginTime;
    /**
     * 最后一次修改密码的时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date lastChangePwdTime;
    /**
     * 上次登录ip
     */
    private String lastLoginIp;
    /**
     * 修改密码错误次数
     */
    private Integer changePwdErrNum;
    /**
     * 修改支付密码错误次数
     */
    private Integer changePayPwdErrNum;
    /**
     * 冻结积分
     */
    private Integer freezeScore;
    /**
     * 可用积分
     */
    private Integer canScore;
    /**
     * 积分值
     */
    private Integer score;
    /**
     * 用户状态 10 启用，11 停用，21 暂停投注，31 冻结，41 拉黑
     */
    @TableField(value = "`status`")
    private Integer status;
    /**
     * 取款密码
     */
    private String payPwd;
    /**
     * 是否是试玩账号：1是，0否  2:测试用户
     */
    private Integer isDemo;
    /**
     * 迁移时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date transferTime;
    private Integer random;
    /**
     * 备注
     */
    private String remark;
    /**
     * 最后游戏时间
     */
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date lastPlaytime;
    /**
     * 最后游戏种类
     */
    private String lastCategory;

    private Long dailyBonus;//用户每日加奖金额
    private Long upgradeBonus;//用户晋级加奖金额
    private Integer isConvert;//用户额度转换开启状态 0-未开启 1-开启

    public Integer getIsConvert() {
        return isConvert;
    }

    public void setIsConvert(Integer isConvert) {
        this.isConvert = isConvert;
    }

    public Long getDailyBonus() {
        return dailyBonus;
    }

    public void setDailyBonus(Long dailyBonus) {
        this.dailyBonus = dailyBonus;
    }

    public Long getUpgradeBonus() {
        return upgradeBonus;
    }

    public void setUpgradeBonus(Long upgradeBonus) {
        this.upgradeBonus = upgradeBonus;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getUserRankId() {
        return userRankId;
    }

    public void setUserRankId(Long userRankId) {
        this.userRankId = userRankId;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getLoginErrCount() {
        return loginErrCount;
    }

    public void setLoginErrCount(Integer loginErrCount) {
        this.loginErrCount = loginErrCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(Date lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastChangePwdTime() {
        return lastChangePwdTime;
    }

    public void setLastChangePwdTime(Date lastChangePwdTime) {
        this.lastChangePwdTime = lastChangePwdTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getChangePwdErrNum() {
        return changePwdErrNum;
    }

    public void setChangePwdErrNum(Integer changePwdErrNum) {
        this.changePwdErrNum = changePwdErrNum;
    }

    public Integer getChangePayPwdErrNum() {
        return changePayPwdErrNum;
    }

    public void setChangePayPwdErrNum(Integer changePayPwdErrNum) {
        this.changePayPwdErrNum = changePayPwdErrNum;
    }

    public Integer getFreezeScore() {
        return freezeScore;
    }

    public void setFreezeScore(Integer freezeScore) {
        this.freezeScore = freezeScore;
    }

    public Integer getCanScore() {
        return canScore;
    }

    public void setCanScore(Integer canScore) {
        this.canScore = canScore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public Integer getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Integer isDemo) {
        this.isDemo = isDemo;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastPlaytime() {
        return lastPlaytime;
    }

    public void setLastPlaytime(Date lastPlaytime) {
        this.lastPlaytime = lastPlaytime;
    }

    public String getLastCategory() {
        return lastCategory;
    }

    public void setLastCategory(String lastCategory) {
        this.lastCategory = lastCategory;
    }


    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", userRankId=" + userRankId +
                ", signCount=" + signCount +
                ", loginCount=" + loginCount +
                ", loginErrCount=" + loginErrCount +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", isDel=" + isDel +
                ", key='" + key + '\'' +
                ", lastSignTime=" + lastSignTime +
                ", lastLoginTime=" + lastLoginTime +
                ", lastChangePwdTime=" + lastChangePwdTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", changePwdErrNum=" + changePwdErrNum +
                ", changePayPwdErrNum=" + changePayPwdErrNum +
                ", freezeScore=" + freezeScore +
                ", canScore=" + canScore +
                ", score=" + score +
                ", status=" + status +
                ", payPwd='" + payPwd + '\'' +
                ", isDemo=" + isDemo +
                ", transferTime=" + transferTime +
                ", random=" + random +
                ", remark='" + remark + '\'' +
                ", lastPlaytime=" + lastPlaytime +
                ", lastCategory='" + lastCategory + '\'' +
                '}';
    }
}

