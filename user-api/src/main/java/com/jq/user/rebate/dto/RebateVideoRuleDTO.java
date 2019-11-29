package com.jq.user.rebate.dto;

import java.io.Serializable;

/**
 * @Auther: Lee
 * @Date: 2019/2/11 15:12
 */
public class RebateVideoRuleDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long ruleInfoId;//返水规则详情表Id
    private String gameCode;//游戏code
    private String gameName;//游戏名称
    private Long gameRebate;//真人游戏返点

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRuleInfoId() {
        return ruleInfoId;
    }

    public void setRuleInfoId(Long ruleInfoId) {
        this.ruleInfoId = ruleInfoId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Long getGameRebate() {
        return gameRebate;
    }

    public void setGameRebate(Long gameRebate) {
        this.gameRebate = gameRebate;
    }

    @Override
    public String toString() {
        return "RebateVideoRuleDTO{" +
                "id=" + id +
                ", ruleInfoId=" + ruleInfoId +
                ", gameCode='" + gameCode + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameRebate=" + gameRebate +
                '}';
    }
}
