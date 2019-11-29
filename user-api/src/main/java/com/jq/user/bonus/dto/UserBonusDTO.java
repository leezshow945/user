package com.jq.user.bonus.dto;

import com.jq.user.common.BaseDTO;

import java.io.Serializable;
import java.util.Date;

/**
 * 〈奖金记录表〉
 *
 * @author Json
 * @create 2018/6/19
 */
public class UserBonusDTO  extends BaseDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long id;
    /** 游戏ID  **/
    private String  gameId;

    /** 游戏类型  **/
    private String  gameCategoryId;

    /** 游戏名称  **/
    private String categoryName;

    /** LHC玩法组合(取字典表对应信息)  **/
    private String  playType;

    /** 用户ID  **/
    private Long  userId;

    /** 游戏类型 **/
    private String categoryId;

    /** 期数  **/
    private String  periods;

    /** 总投注额  **/
    private Long  allBettingAmount;

    /** 总投注数  **/
    private Integer  allBettingNumber;

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

    /** 日工资 **/
    private Long dailyWage;

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

    private String apperName;
    /**		分红策略**/
    private String bonusStartegy;
    /**		分红周期**/
    private String bonusCycle;
    /** 层级**/
    private Integer level;
    /** 发起人**/
    private String createName;
    /** 接收人**/
    private String contractName;
    /** 站点编码 **/
    private String siteCode;
    /** 结算时间*/
    private Date settleTime;



    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    private Integer page=1;
    private Integer limit=10;

    /** 开始期数 */
    private String beginPeriods;
    /** 结束期数 */
    private String endPeriods;


    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
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

    public Long getAuditorId() {
        return auditorId;
    }

    public void setAuditorId(Long auditorId) {
        this.auditorId = auditorId;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public Long getAllBettingAmount() {
        return allBettingAmount;
    }

    public void setAllBettingAmount(Long allBettingAmount) {
        this.allBettingAmount = allBettingAmount;
    }

    public Integer getAllBettingNumber() {
        return allBettingNumber;
    }

    public void setAllBettingNumber(Integer allBettingNumber) {
        this.allBettingNumber = allBettingNumber;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getBonusStartegy() {
        return bonusStartegy;
    }

    public void setBonusStartegy(String bonusStartegy) {
        this.bonusStartegy = bonusStartegy;
    }

    public String getBonusCycle() {
        return bonusCycle;
    }

    public void setBonusCycle(String bonusCycle) {
        this.bonusCycle = bonusCycle;
    }


    public String getApperName() {
        return apperName;
    }

    public void setApperName(String apperName) {
        this.apperName = apperName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getDailyWage() {
        return dailyWage;
    }

    public void setDailyWage(Long dailyWage) {
        this.dailyWage = dailyWage;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getBeginPeriods() {
        return beginPeriods;
    }

    public void setBeginPeriods(String beginPeriods) {
        this.beginPeriods = beginPeriods;
    }

    public String getEndPeriods() {
        return endPeriods;
    }

    public void setEndPeriods(String endPeriods) {
        this.endPeriods = endPeriods;
    }

    @Override
    public String toString() {
        return "UserBonusDTO{" +
                "id=" + id +
                ", gameId='" + gameId + '\'' +
                ", gameCategoryId='" + gameCategoryId + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", playType='" + playType + '\'' +
                ", userId=" + userId +
                ", categoryId='" + categoryId + '\'' +
                ", periods='" + periods + '\'' +
                ", allBettingAmount=" + allBettingAmount +
                ", allBettingNumber=" + allBettingNumber +
                ", rebate=" + rebate +
                ", examineState=" + examineState +
                ", winAmount=" + winAmount +
                ", teamMakeWater=" + teamMakeWater +
                ", winLose=" + winLose +
                ", activity=" + activity +
                ", dailyWage=" + dailyWage +
                ", actualWinLose=" + actualWinLose +
                ", bonus=" + bonus +
                ", auditorId=" + auditorId +
                ", dataType=" + dataType +
                ", toUserId=" + toUserId +
                ", mainId=" + mainId +
                ", siteId=" + siteId +
                ", apperName='" + apperName + '\'' +
                ", bonusStartegy='" + bonusStartegy + '\'' +
                ", bonusCycle='" + bonusCycle + '\'' +
                ", level=" + level +
                ", createName='" + createName + '\'' +
                ", contractName='" + contractName + '\'' +
                ", siteCode='" + siteCode + '\'' +
                ", settleTime=" + settleTime +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", beginPeriods='" + beginPeriods + '\'' +
                ", endPeriods='" + endPeriods + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
