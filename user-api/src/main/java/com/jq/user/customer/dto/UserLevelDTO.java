package com.jq.user.customer.dto;

import java.io.Serializable;

public class UserLevelDTO implements Serializable {
    /** 会员id */
    private Long userId;
    /** 用户名 */
    private String userName;
    /** 等级id */
    private Long rankId;
    /** 等级名称 */
    private String rankName;
    /** 上级代理 */
    private Long highLevelId;
    /**  上级用户名 */
    private String highLevelName;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getRankId() {
        return rankId;
    }

    public void setRankId(Long rankId) {
        this.rankId = rankId;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public Long getHighLevelId() {
        return highLevelId;
    }

    public void setHighLevelId(Long highLevelId) {
        this.highLevelId = highLevelId;
    }

    public String getHighLevelName() {
        return highLevelName;
    }

    public void setHighLevelName(String highLevelName) {
        this.highLevelName = highLevelName;
    }

    @Override
    public String toString() {
        return "UserLevelDTO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", rankId=" + rankId +
                ", rankName='" + rankName + '\'' +
                ", highLevelId=" + highLevelId +
                ", highLevelName='" + highLevelName + '\'' +
                '}';
    }
}
