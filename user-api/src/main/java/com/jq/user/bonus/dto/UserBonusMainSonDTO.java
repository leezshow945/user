package com.jq.user.bonus.dto;


import com.jq.user.common.BaseDTO;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 〈设置主单子单融合DTO〉
 *
 * @author Json
 * @create 2018/6/26
 */
public class UserBonusMainSonDTO extends BaseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 游戏类型
     **/
    private String gameCategoryId;

    /**
     * LHC玩法组合(取字典表对应数字)
     **/
    private String playType;

    /**
     * 用户ID
     **/
    private String userId;

    /**
     * 层级
     **/
    private Integer rebateLevel;


    /**
     * 分红规则(0:盈亏，1:日均量，2:总量)
     **/
    private Integer bonusRule;

    /**
     * 分红策略(0:盈不累计结算，1:盈永久累计，2:盈累计上一期，3:盈下半月累计)
     **/
    private Integer bonusStrategy;

    /**
     * 有效会员
     **/
    private String[] validMember;

    /**
     * 分红周期(0:每月1期，1:每月2期，2:每月3期)
     **/
    private Integer bonusCycle;

    /**
     * 自动派发(0:否，1:是)
     **/
    private Integer distribute;

    /**
     * 设置类型(0:分红设置,1:契约分红设置)
     **/
    private Integer settingType;

    /**
     * 主表id
     **/
    private String mainId;

    /**
     * 金额
     **/
    private String[] amount;

    /**
     * 返点要求
     **/
    private String[] rebate;

    /**
     * 奖金模式(0:奖金比例,1:奖金金额)
     **/
    private String[] bonusMode;

    /**
     * 奖金金额
     **/
    private String[] bonusAmount;

    /**
     * 上限金额
     **/
    private String[] limitAmount;

    /**
     * 奖金比例
     **/
    private String[] bonusPer;

    /**
     * 实际盈亏金额
     */
    private String[] actualWinLose;

    /**
     * 未选中的游戏
     */
    private String [] gameList;

    /**
     * 主单id
     **/
    public String id;

    /**
     * 子单id
     **/
    private String[] sonId;

    /**
     * 站点 id
     **/
    private Long siteId;

    /**
     * 站点编码
     **/
    private String siteCode;
    /**
     * 操作人
     **/
    private String sysUserName;

    /**
     * 操作人
     **/
    private Long sysUserId;


    public Long getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Long sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getSysUserName() {
        return sysUserName;
    }

    public void setSysUserName(String sysUserName) {
        this.sysUserName = sysUserName;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getRebateLevel() {
        return rebateLevel;
    }

    public void setRebateLevel(Integer rebateLevel) {
        this.rebateLevel = rebateLevel;
    }

    public Integer getBonusRule() {
        return bonusRule;
    }

    public void setBonusRule(Integer bonusRule) {
        this.bonusRule = bonusRule;
    }

    public Integer getBonusStrategy() {
        return bonusStrategy;
    }

    public void setBonusStrategy(Integer bonusStrategy) {
        this.bonusStrategy = bonusStrategy;
    }

    public String[] getValidMember() {
        return validMember;
    }

    public void setValidMember(String[] validMember) {
        this.validMember = validMember;
    }

    public Integer getBonusCycle() {
        return bonusCycle;
    }

    public void setBonusCycle(Integer bonusCycle) {
        this.bonusCycle = bonusCycle;
    }

    public Integer getDistribute() {
        return distribute;
    }

    public void setDistribute(Integer distribute) {
        this.distribute = distribute;
    }

    public Integer getSettingType() {
        return settingType;
    }

    public void setSettingType(Integer settingType) {
        this.settingType = settingType;
    }

    public String getMainId() {
        return mainId;
    }

    public void setMainId(String mainId) {
        this.mainId = mainId;
    }

    public String[] getAmount() {
        return amount;
    }

    public void setAmount(String[] amount) {
        this.amount = amount;
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

    public String[] getBonusAmount() {
        return bonusAmount;
    }

    public void setBonusAmount(String[] bonusAmount) {
        this.bonusAmount = bonusAmount;
    }

    public String[] getLimitAmount() {
        return limitAmount;
    }

    public void setLimitAmount(String[] limitAmount) {
        this.limitAmount = limitAmount;
    }

    public String[] getBonusPer() {
        return bonusPer;
    }

    public void setBonusPer(String[] bonusPer) {
        this.bonusPer = bonusPer;
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

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String[] getActualWinLose() {
        return actualWinLose;
    }

    public void setActualWinLose(String[] actualWinLose) {
        this.actualWinLose = actualWinLose;
    }

    public String[] getGameList() {
        return gameList;
    }

    public void setGameList(String[] gameList) {
        this.gameList = gameList;
    }

    @Override
    public String toString() {
        return "UserBonusMainSonDTO{" +
                "gameCategoryId='" + gameCategoryId + '\'' +
                ", playType='" + playType + '\'' +
                ", userId='" + userId + '\'' +
                ", rebateLevel=" + rebateLevel +
                ", bonusRule=" + bonusRule +
                ", bonusStrategy=" + bonusStrategy +
                ", validMember=" + Arrays.toString(validMember) +
                ", bonusCycle=" + bonusCycle +
                ", distribute=" + distribute +
                ", settingType=" + settingType +
                ", mainId='" + mainId + '\'' +
                ", amount=" + Arrays.toString(amount) +
                ", rebate=" + Arrays.toString(rebate) +
                ", bonusMode=" + Arrays.toString(bonusMode) +
                ", bonusAmount=" + Arrays.toString(bonusAmount) +
                ", limitAmount=" + Arrays.toString(limitAmount) +
                ", bonusPer=" + Arrays.toString(bonusPer) +
                ", actualWinLose=" + Arrays.toString(actualWinLose) +
                ", gameList=" + Arrays.toString(gameList) +
                ", id='" + id + '\'' +
                ", sonId=" + Arrays.toString(sonId) +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", sysUserName='" + sysUserName + '\'' +
                ", sysUserId=" + sysUserId +
                '}';
    }
}
