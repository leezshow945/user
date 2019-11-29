package com.jq.user.score.vo;

import com.jq.user.score.entity.UserScoreRecordEntity;

import java.io.Serializable;

/**
 * 用户积分模板VO类
 */
public class UserScoreVO extends UserScoreRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String isProxy;
    private String status;
    private String level;
    private String rankLevel;
    private String scoreStart;//最小积分查询
    private String scoreEnd;//最大积分查询

    private String rankName;
    private String subLevelCount;
    private String highLevelAccount;

    public String getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(String isProxy) {
        this.isProxy = isProxy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubLevelCount() {
        return subLevelCount;
    }

    public void setSubLevelCount(String subLevelCount) {
        this.subLevelCount = subLevelCount;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(String rankLevel) {
        this.rankLevel = rankLevel;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getScoreStart() {
        return scoreStart;
    }

    public void setScoreStart(String scoreStart) {
        this.scoreStart = scoreStart;
    }

    public String getScoreEnd() {
        return scoreEnd;
    }

    public void setScoreEnd(String scoreEnd) {
        this.scoreEnd = scoreEnd;
    }
}
