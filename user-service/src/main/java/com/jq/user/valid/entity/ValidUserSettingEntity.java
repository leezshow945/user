package com.jq.user.valid.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jq.user.support.BaseEntity;

import java.io.Serializable;

@TableName("valid_user_setting")
public class ValidUserSettingEntity extends BaseEntity implements Serializable{
    // 站点id
    @TableId
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

    @Override
    public String toString() {
        return "ValidUserSettingEntity{" +
                "siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", rechargeAmount=" + rechargeAmount +
                ", withdrawAmount=" + withdrawAmount +
                ", validBetAmount=" + validBetAmount +
                ", loginCountNum=" + loginCountNum +
                ", validOrderNum=" + validOrderNum +
                ", registerDays=" + registerDays +
                ", loginDays=" + loginDays +
                ", hasRealName=" + hasRealName +
                ", isRepeat=" + isRepeat +
                '}';
    }
}
