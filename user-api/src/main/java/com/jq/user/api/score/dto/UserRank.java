package com.jq.user.api.score.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import java.io.Serializable;

public class UserRank implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long siteId;
    private String rankName;
    private String rankLevel;
    private String rankImg;
    private Integer maxScore;


    @TableField(exist = false)
    private String timeStr;//查询字段 时间关键词 格式：yyyy-MM-dd


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(String rankLevel) {
        this.rankLevel = rankLevel;
    }

    public String getRankImg() {
        return rankImg;
    }

    public void setRankImg(String rankImg) {
        this.rankImg = rankImg;
    }

    public Integer getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

}
