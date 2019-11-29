package com.jq.user.oldbonus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.jq.user.support.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;




@TableName("user_bonus")
public class UserBonusEntity extends BaseEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	/** 游戏ID  **/
	private String  gameId;
	
	/** 游戏类型  **/
	private String  gameCategoryId;
	
	/** LHC玩法组合(取字典表对应信息)  **/
	private String  playType;
	
	/** 用户ID  **/
	private Long  userId;
	
 
	
	/** 期数  **/
	private String  periods;
	
	/** 总投注额  **/
	@TableField("all_betting_amount")
	private Long  allBetAmount;
	
	/** 总投注数  **/
	@TableField("all_betting_number")
	private Integer  allBetNumber;
	
	/** 返点  **/
	private Long  rebate;
	
	/** 审核状态(0:临时，1:锁定，2:已审核)  **/
	private Integer  examineState;
	
	/** 派彩金额  **/
	private Long  winAmount;
	
	/** 团队赚水  **/
	private Long  teamMakeWater;
	
	/** 盈亏  **/
	private Long  winLose;
	
	/** 活动  **/
	private Long  activity;
	
	/** 实际盈亏  **/
	private Long  actualWinLose;
	
	/** 日工资/分红  **/
	private Long  bonus;
	
	
	/** 审核人ID  **/
	private Long  auditorId;
	
	/** 数据类型(0:分红,1:契约分红,2:日工资,3:契约日工资)  **/
	private Integer  dataType;
 
	/**		契约用户ID**/
	private Long toUserId;
	
	/**		设置ID**/
	private Long mainId;
	
	/**		站点ID**/
	private Long siteId;

    /**		站点编码**/
    private String siteCode;

    /** 分红周期 **/
    private String bonusCycle;
	
	public UserBonusEntity() {
	}

    public UserBonusEntity(Long id, String gameId,
                           String gameCategoryId,
                           String playType, Long userId,
                           String periods, Long allBetAmount,
                           Integer allBetNumber, Long rebate,
                           Integer examineState, Long winAmount,
                           Long teamMakeWater, Long winLose,
                           Long activity, Long actualWinLose,
                           Long bonus, Long auditorId,
                           Integer dataType, Long toUserId,
                           Long mainId, Long siteId,
                           String siteCode, String bonusCycle) {
        this.id = id;
        this.gameId = gameId;
        this.gameCategoryId = gameCategoryId;
        this.playType = playType;
        this.userId = userId;
        this.periods = periods;
        this.allBetAmount = allBetAmount;
        this.allBetNumber = allBetNumber;
        this.rebate = rebate;
        this.examineState = examineState;
        this.winAmount = winAmount;
        this.teamMakeWater = teamMakeWater;
        this.winLose = winLose;
        this.activity = activity;
        this.actualWinLose = actualWinLose;
        this.bonus = bonus;
        this.auditorId = auditorId;
        this.dataType = dataType;
        this.toUserId = toUserId;
        this.mainId = mainId;
        this.siteId = siteId;
        this.siteCode = siteCode;
        this.bonusCycle = bonusCycle;
    }

    /*
	 * 构造方法
	 *
	 */
	public UserBonusEntity(String gameId, String gameCategoryId,
                           String playType, Long userId, String periods, Long allBetAmount,
                           Integer allBetNumber, Long rebate, Integer examineState,
                           Long winAmount, Long teamMakeWater, Long winLose, Long activity,
                           Long actualWinLose, Long bonus, Long auditorId, Integer dataType,
                           Long toUserId, Long mainId, Long siteId) {
		super();
		this.gameId = gameId;
		this.gameCategoryId = gameCategoryId;
		this.playType = playType;
		this.userId = userId;
		this.periods = periods;
		this.allBetAmount = allBetAmount;
		this.allBetNumber = allBetNumber;
		this.rebate = rebate;
		this.examineState = examineState;
		this.winAmount = winAmount;
		this.teamMakeWater = teamMakeWater;
		this.winLose = winLose;
		this.activity = activity;
		this.actualWinLose = actualWinLose;
		this.bonus = bonus;
		this.auditorId = auditorId;
		this.dataType = dataType;
		this.toUserId = toUserId;
		this.mainId = mainId;
		this.siteId = siteId;
	}


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getMainId() {
        return mainId;
    }

    public void setMainId(Long mainId) {
        this.mainId = mainId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
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
    
    

    
    public String getPeriods() {
        return StringUtils.isBlank(periods) ? periods : periods.trim();
    }
    public void setPeriods(String periods) {
        this.periods = periods;
    }
    
    
    public Long getAllBetAmount() {
        return allBetAmount;
    }
    public void setAllBetAmount(Long allBetAmount) {
        this.allBetAmount = allBetAmount;
    }

    
    public Integer getAllBetNumber() {
        return allBetNumber;
    }
    public void setAllBetNumber(Integer allBetNumber) {
        this.allBetNumber = allBetNumber;
    }

    
    public Long getRebate() {
        return rebate;
    }
    public void setRebate(Long rebate) {
        this.rebate = rebate;
    }

    
    public Integer getExamineState() {
        return examineState;
    }
    public void setExamineState(Integer examineState) {
        this.examineState = examineState;
    }

    
    public Long getWinAmount() {
        return winAmount;
    }
    public void setWinAmount(Long winAmount) {
        this.winAmount = winAmount;
    }

    
    public Long getTeamMakeWater() {
        return teamMakeWater;
    }
    public void setTeamMakeWater(Long teamMakeWater) {
        this.teamMakeWater = teamMakeWater;
    }

    
    public Long getWinLose() {
        return winLose;
    }
    public void setWinLose(Long winLose) {
        this.winLose = winLose;
    }

    
    public Long getActivity() {
        return activity;
    }
    public void setActivity(Long activity) {
        this.activity = activity;
    }

    
    public Long getActualWinLose() {
        return actualWinLose;
    }
    public void setActualWinLose(Long actualWinLose) {
        this.actualWinLose = actualWinLose;
    }

    
    public Long getBonus() {
        return bonus;
    }
    public void setBonus(Long bonus) {
        this.bonus = bonus;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDataType() {
        return dataType;
    }
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getBonusCycle() {
        return bonusCycle;
    }

    public void setBonusCycle(String bonusCycle) {
        this.bonusCycle = bonusCycle;
    }
}