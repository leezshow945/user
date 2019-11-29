package com.jq.user.rebate.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 真人返水 返水结果数据DTO对象
 * @Auther: Lee
 * @Date: 2018/12/4 13:56
 */
public class RebateVideoResultInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long resultInfoId;//返水结果用户详情Id
    private String gameCode;//游戏code
    private String gameName;//游戏名称
    private Long gameBet=0l;//游戏总投注
    private Long gameRebate=0l;//游戏总返水

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResultInfoId() {
        return resultInfoId;
    }

    public void setResultInfoId(Long resultInfoId) {
        this.resultInfoId = resultInfoId;
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

    public Long getGameBet() {
        return gameBet;
    }

    public void setGameBet(Long gameBet) {
        this.gameBet = gameBet;
    }

    public Long getGameRebate() {
        return gameRebate;
    }

    public void setGameRebate(Long gameRebate) {
        this.gameRebate = gameRebate;
    }

    @Override
    public String toString() {
        return "RebateVideoResultInfoDTO{" +
                "id=" + id +
                ", resultInfoId=" + resultInfoId +
                ", gameCode='" + gameCode + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameBet=" + gameBet +
                ", gameRebate=" + gameRebate +
                '}';
    }
}
