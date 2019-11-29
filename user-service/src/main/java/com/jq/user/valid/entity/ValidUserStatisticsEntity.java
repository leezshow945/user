package com.jq.user.valid.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName("valid_user_statistics")
public class ValidUserStatisticsEntity implements Serializable {
    // 用户id
    @TableId
    private Long userId;
    // 站点id
    private Long siteId;
    // 站点code
    private String siteCode;
    // 充值金额
    private Long rechargeAmount;
    // 提现金额
    private Long withdrawAmount;
    // 有效投金额
    private Long validBetAmount;
    // 登录次数
    private Integer loginCountNum;
    // 有效注单量
    private Integer validOrderNum;
    // 注册天数
    private Integer registerDays;
    // 登录天数
    private Integer loginDays;
    // 是否绑定真实姓名
    private Integer hasRealName;
    // 是否校验正式姓名重复
    private Integer isRepeat;
    // 是否为有效会员
    private Integer isValid;
    private Integer isDel;
    private Date createTime;
    private Date updateTime;

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

    public Long getRechargeAmount() {
        return rechargeAmount;
    }

    public void setRechargeAmount(Long rechargeAmount) {
        this.rechargeAmount = rechargeAmount;
    }

    public Long getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Long withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Long getValidBetAmount() {
        return validBetAmount;
    }

    public void setValidBetAmount(Long validBetAmount) {
        this.validBetAmount = validBetAmount;
    }

    public Integer getLoginCountNum() {
        return loginCountNum;
    }

    public void setLoginCountNum(Integer loginCountNum) {
        this.loginCountNum = loginCountNum;
    }

    public Integer getValidOrderNum() {
        return validOrderNum;
    }

    public void setValidOrderNum(Integer validOrderNum) {
        this.validOrderNum = validOrderNum;
    }

    public Integer getRegisterDays() {
        return registerDays;
    }

    public void setRegisterDays(Integer registerDays) {
        this.registerDays = registerDays;
    }

    public Integer getLoginDays() {
        return loginDays;
    }

    public void setLoginDays(Integer loginDays) {
        this.loginDays = loginDays;
    }

    public Integer getHasRealName() {
        return hasRealName;
    }

    public void setHasRealName(Integer hasRealName) {
        this.hasRealName = hasRealName;
    }

    public Integer getIsRepeat() {
        return isRepeat;
    }

    public void setIsRepeat(Integer isRepeat) {
        this.isRepeat = isRepeat;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
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
