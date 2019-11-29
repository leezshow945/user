package com.jq.user.oldbonus.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import com.jq.user.support.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

@TableName("user_bonus_main")
public class UserBonusMainEntity extends BaseEntity implements Serializable{


    private static final long serialVersionUID = 1L;

    private  Long id;
	/** 游戏ID  **/
	private String  gameId;
	
	/** 游戏类型  **/
	private String  gameCategoryId;
	
	/** LHC玩法组合(取字典表对应数字)  **/
	private String  playType;
	
	/** 用户ID  **/
	private Long  userId;
	
	/** 层级  **/
	private Integer  rebateLevel;
	
	/** 分红规则(0:盈亏，1:日均量，2:总量)  **/
	private Integer  bonusRule;
	
	/** 分红策略(0:盈不累计结算，1:盈永久累计，2:盈累计上一期，3:盈下半月累计)  **/
	private Integer  bonusStrategy;
	
	/** 分红周期(0:每月1期，1:每月2期，2:每月3期)  **/
	private Integer  bonusCycle;
	
	/** 自动派发(0:否，1:是)  **/
	private Integer  distribute;
	
	/** 设置类型(0:分红设置,1:契约分红设置)  **/
	private Integer  settingType;
	
	/** 站点id  **/
	private Long  siteId;
	
	/** 契约签订状态(0:未签订，1:同意，2:拒绝) **/
	private Integer  signStatus;
	
	/** 契约人ID **/
	private Long  toUserId;

    /**		站点编码**/
	private String siteCode;

    /**  签订时间*/
    private Date signTime;

    /** 结算时间*/
    private Date settleTime;

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public String getGameId() {
        return StringUtils.isBlank(gameId) ? gameId : gameId.trim();
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    
    
    public String getGameCategoryId() {
        return StringUtils.isBlank(gameCategoryId) ? gameCategoryId : gameCategoryId.trim();
    }
    public void setGameCategoryId(String gameCategoryId) {
        this.gameCategoryId = gameCategoryId;
    }
    
    
    public String getPlayType() {
        return StringUtils.isBlank(playType) ? playType : playType.trim();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getSignStatus() {
        return signStatus;
    }
    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    @Override
    public String toString() {
        return "UserBonusMainEntity{" +
                "id=" + id +
                ", gameId='" + gameId + '\'' +
                ", gameCategoryId='" + gameCategoryId + '\'' +
                ", playType='" + playType + '\'' +
                ", userId=" + userId +
                ", rebateLevel=" + rebateLevel +
                ", bonusRule=" + bonusRule +
                ", bonusStrategy=" + bonusStrategy +
                ", bonusCycle=" + bonusCycle +
                ", distribute=" + distribute +
                ", settingType=" + settingType +
                ", siteId=" + siteId +
                ", signStatus=" + signStatus +
                ", toUserId=" + toUserId +
                ", siteCode='" + siteCode + '\'' +
                ", signTime=" + signTime +
                ", settleTime=" + settleTime +
                '}';
    }
}