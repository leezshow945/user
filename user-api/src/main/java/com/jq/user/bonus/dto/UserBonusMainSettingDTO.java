package com.jq.user.bonus.dto;

import com.jq.user.common.BaseDTO;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public class UserBonusMainSettingDTO extends BaseDTO implements Serializable {
    /** 有效会员  **/
    private String[] validMember;

    /** 团队日量  **/
    private String[] teamDailyAmount;

    /** 团队实际亏损  **/
    private String[] teamActualLose;

    /** 奖金金额  **/
    private String[] bonus;

    /** 日量比例  **/
    private String[]  daysPer;

    /** 盈亏比例  **/
    private String[]  ykPer;

    /** 自动派发(0:否，1:是)  **/
    private Integer  distribute;

    /**
     * 分红周期(0:每月1期，1:每月2期，2:每月3期)
     **/
    private Integer bonusCycle;

    /** 设置类型(0:分红设置,1:契约分红设置,2:日工资设置,3:契约日工资设置)  **/
    private Integer  settingType;

    /** 游戏类型  **/
    private String  gameCategoryId;

    /** LHC玩法组合(取字典表对应数字)  **/
    private String  playType;

    /** 层级  **/
    private Integer  rebateLevel;

    /** 主单id  **/
    public String id;

    /** 子单id  **/
    private String[]  sonId;

    /** 返点要求  **/
    private String[]  rebate;

    /** 奖金模式(0:日量比例,1:盈亏比例)  **/
    private String[]  bonusMode;

    private Long siteId;

    /** 契约人ID **/
    private Long  toUserId;

    /** 用户ID  **/
    private Long  userId;

    private String siteCode;

    private Long sysUserId;
//    private String sysUserName;
    /* 未选中的游戏列表*/
    private String[] gameList;

    /** 结算时间*/
    private Date settleTime;

    /**  签订时间*/
    private Date signTime;

    /** 契约签订状态(0:未签订，1:同意，2:拒绝) **/
    private Integer  signStatus;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }
//
//    public String getSysUserName() {
//        return sysUserName;
//    }
//
//    public void setSysUserName(String sysUserName) {
//        this.sysUserName = sysUserName;
//    }

    public String[] getValidMember() {
        return validMember;
    }

    public void setValidMember(String[] validMember) {
        this.validMember = validMember;
    }

    public String[] getTeamDailyAmount() {
        return teamDailyAmount;
    }

    public void setTeamDailyAmount(String[] teamDailyAmount) {
        this.teamDailyAmount = teamDailyAmount;
    }

    public String[] getTeamActualLose() {
        return teamActualLose;
    }

    public void setTeamActualLose(String[] teamActualLose) {
        this.teamActualLose = teamActualLose;
    }

    public String[] getBonus() {
        return bonus;
    }

    public void setBonus(String[] bonus) {
        this.bonus = bonus;
    }

    public String[] getDaysPer() {
        return daysPer;
    }

    public void setDaysPer(String[] daysPer) {
        this.daysPer = daysPer;
    }

    public String[] getYkPer() {
        return ykPer;
    }

    public void setYkPer(String[] ykPer) {
        this.ykPer = ykPer;
    }

    public Integer getDistribute() {
        return distribute;
    }

    public void setDistribute(Integer distribute) {
        this.distribute = distribute;
    }

    public Integer getBonusCycle() {
        return bonusCycle;
    }

    public void setBonusCycle(Integer bonusCycle) {
        this.bonusCycle = bonusCycle;
    }

    public Integer getSettingType() {
        return settingType;
    }

    public void setSettingType(Integer settingType) {
        this.settingType = settingType;
    }

    public String getGameCategoryId() {
        return gameCategoryId;
    }

    public void setGameCategoryId(String gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }

    public String getPlayType() {
        return playType;
    }

    public void setPlayType(String playType) {
        this.playType = playType;
    }

    public Integer getRebateLevel() {
        return rebateLevel;
    }

    public void setRebateLevel(Integer rebateLevel) {
        this.rebateLevel = rebateLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getSonId() {
        return sonId;
    }

    public void setSonId(String[] sonId) {
        this.sonId = sonId;
    }

    public String[] getRebate() {
        return rebate;
    }

    public void setRebate(String[] rebate) {
        this.rebate = rebate;
    }

    public String[] getBonusMode() {
        return bonusMode;
    }

    public void setBonusMode(String[] bonusMode) {
        this.bonusMode = bonusMode;
    }

    public String[] getGameList() {
        return gameList;
    }

    public void setGameList(String[] gameList) {
        this.gameList = gameList;
    }


    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    @Override
    public String toString() {
        return "UserBonusMainSettingDTO{" +
                "validMember=" + Arrays.toString(validMember) +
                ", teamDailyAmount=" + Arrays.toString(teamDailyAmount) +
                ", teamActualLose=" + Arrays.toString(teamActualLose) +
                ", bonus=" + Arrays.toString(bonus) +
                ", daysPer=" + Arrays.toString(daysPer) +
                ", ykPer=" + Arrays.toString(ykPer) +
                ", distribute=" + distribute +
                ", bonusCycle=" + bonusCycle +
                ", settingType=" + settingType +
                ", gameCategoryId='" + gameCategoryId + '\'' +
                ", playType='" + playType + '\'' +
                ", rebateLevel=" + rebateLevel +
                ", id='" + id + '\'' +
                ", sonId=" + Arrays.toString(sonId) +
                ", rebate=" + Arrays.toString(rebate) +
                ", bonusMode=" + Arrays.toString(bonusMode) +
                ", siteId=" + siteId +
                ", toUserId=" + toUserId +
                ", userId=" + userId +
                ", siteCode='" + siteCode + '\'' +
                ", sysUserId=" + sysUserId +
                ", gameList=" + Arrays.toString(gameList) +
                ", settleTime=" + settleTime +
                ", signTime=" + signTime +
                ", signStatus=" + signStatus +
                '}';
    }
}
