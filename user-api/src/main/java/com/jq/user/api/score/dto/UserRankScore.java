package com.jq.user.api.score.dto;
import java.io.Serializable;

public class UserRankScore implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private Long rankId;
    private String scoreName;
    private String scoreCode;
    private String scoreVal;
    private String isEnable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getScoreName() {
        return scoreName;
    }

    public void setScoreName(String scoreName) {
        this.scoreName = scoreName;
    }

    public String getScoreCode() {
        return scoreCode;
    }

    public void setScoreCode(String scoreCode) {
        this.scoreCode = scoreCode;
    }

    public String getScoreVal() {
        return scoreVal;
    }

    public void setScoreVal(String scoreVal) {
        this.scoreVal = scoreVal;
    }

    public String getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(String isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "UserRankScore{" +
                "id=" + id +
                ", rankId=" + rankId +
                ", scoreName='" + scoreName + '\'' +
                ", scoreCode='" + scoreCode + '\'' +
                ", scoreVal='" + scoreVal + '\'' +
                ", isEnable='" + isEnable + '\'' +
                '}';
    }
}
