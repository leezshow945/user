package com.jq.user.bonus.dto;

import java.io.Serializable;
import java.util.Date;

public class UserBonusSettingDTO implements Serializable {
    /** id */
    private Long id;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private Integer isDel;
    private String remark;
    /** 返点要求 */
    private Long rebate;
    /** 奖金模式(0:日量比例，1:盈亏比例 */
    private Integer bonusMode;
    /** 有效会员 */
    private Integer validMember;
    /** 团队日量 */
    private Long teamDailyAmount;
    /** 团队实际亏损 */
    private Long teamActualLose;
    /** 日工资:上限金额 /契约日工资 奖励金额 */
    private Integer bonus;
    /** 日量比例 */
    private Integer daysPer;
    /** 盈亏比例 */
    private Integer ykPer;
    /** 主单id */
    private Long mainId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getRebate() {
        return rebate;
    }

    public void setRebate(Long rebate) {
        this.rebate = rebate;
    }

    public Integer getBonusMode() {
        return bonusMode;
    }

    public void setBonusMode(Integer bonusMode) {
        this.bonusMode = bonusMode;
    }

    public Integer getValidMember() {
        return validMember;
    }

    public void setValidMember(Integer validMember) {
        this.validMember = validMember;
    }

    public Long getTeamDailyAmount() {
        return teamDailyAmount;
    }

    public void setTeamDailyAmount(Long teamDailyAmount) {
        this.teamDailyAmount = teamDailyAmount;
    }

    public Long getTeamActualLose() {
        return teamActualLose;
    }

    public void setTeamActualLose(Long teamActualLose) {
        this.teamActualLose = teamActualLose;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getDaysPer() {
        return daysPer;
    }

    public void setDaysPer(Integer daysPer) {
        this.daysPer = daysPer;
    }

    public Integer getYkPer() {
        return ykPer;
    }

    public void setYkPer(Integer ykPer) {
        this.ykPer = ykPer;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    @Override
    public String toString() {
        return "UserBonusSettingDTO{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateTime=" + updateTime +
                ", updateBy='" + updateBy + '\'' +
                ", isDel=" + isDel +
                ", remark='" + remark + '\'' +
                ", rebate=" + rebate +
                ", bonusMode=" + bonusMode +
                ", validMember=" + validMember +
                ", teamDailyAmount=" + teamDailyAmount +
                ", teamActualLose=" + teamActualLose +
                ", bonus=" + bonus +
                ", daysPer=" + daysPer +
                ", ykPer=" + ykPer +
                ", mainId=" + mainId +
                '}';
    }
}
