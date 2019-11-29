package com.jq.user.oldbonus.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jq.user.support.BaseEntity;

import java.io.Serializable;

@TableName("user_bonus_setting")
public class UserBonusSettingEntity extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	private Long id;
	/**		返点要求**/
	private Long rebate;
	
	/**		有效会员**/
	private Integer validMember;

	/**		团队日量**/
	private Long teamDailyAmount;
	
	/**		团队实际亏损**/
	private Long teamActualLose;
	
	/**		奖金金额**/
	private Integer bonus;
	
	/**		日量比例**/
	private Integer daysPer;
	

	/**		盈亏比例**/
	private Integer ykPer;

	/**		总单id**/
	private Long mainId;
	
	/** 奖金模式(0:日量比例,1:盈亏比例)  **/
	private Integer  bonusMode;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMainId() {
		return mainId;
	}

	public void setMainId(Long mainId) {
		this.mainId = mainId;
	}

	public Long getRebate() {
		return rebate;
	}
	public void setRebate(Long rebate) {
		this.rebate = rebate;
	}
	
	public Integer getValidMember() {
		return validMember;
	}
	public void setValidMember(Integer valid_member) {
		this.validMember = valid_member;
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
	
    public Integer getBonusMode() {
        return bonusMode;
    }
    public void setBonusMode(Integer bonusMode) {
        this.bonusMode = bonusMode;
    }
}