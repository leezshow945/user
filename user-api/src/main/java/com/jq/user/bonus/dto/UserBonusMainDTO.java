package com.jq.user.bonus.dto;

import com.jq.user.common.BaseDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 〈奖金设置主表〉
 *
 * @author Json
 * @create 2018/6/19
 */
public class UserBonusMainDTO  extends BaseDTO implements Serializable{

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

    /** 签订时间 */
    private Date signTime;
    /** 结算时间*/
    private Date settleTime;

    private String startTime;
    private String endTime;
    private String toUserName;
    private String userName;


    /** 分红子单列表 **/
    private List<UserBonusSonDTO> bonusSonList;

    /** 日工资子单列表 **/
    private List<UserBonusSettingDTO> bonusSettingList;

    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    private Integer page=1;
    private Integer limit=10;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
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

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Integer getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(Integer signStatus) {
        this.signStatus = signStatus;
    }

    public Long getToUserId() {
        return toUserId;
    }

    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    public List<UserBonusSonDTO> getBonusSonList() {
        return bonusSonList;
    }

    public void setBonusSonList(List<UserBonusSonDTO> bonusSonList) {
        this.bonusSonList = bonusSonList;
    }

    public List<UserBonusSettingDTO> getBonusSettingList() {
        return bonusSettingList;
    }

    public void setBonusSettingList(List<UserBonusSettingDTO> bonusSettingList) {
        this.bonusSettingList = bonusSettingList;
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
        return "UserBonusMainDTO{" +
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
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", userName='" + userName + '\'' +
                ", bonusSonList=" + bonusSonList +
                ", bonusSettingList=" + bonusSettingList +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
