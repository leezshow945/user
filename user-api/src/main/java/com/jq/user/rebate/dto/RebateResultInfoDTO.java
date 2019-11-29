package com.jq.user.rebate.dto;

import java.io.Serializable;
import java.util.List;

public class RebateResultInfoDTO implements Serializable{

    private Long id;
    private Long userId;
    private String userName;
    private String highLevelAccount;//上级用户名
    private Long resultId;//返水结果表id
    private Long effectiveBets;//总有效投注
    private Integer status;//状态0已处理1已冲销
    private Long allRebates;//返水总金额

    //投注金额
    private Long gpcBets;
    private Long dpcBets;
    private Long flcBets;
    private Long tycpBets;
    private Long qtBets;
    private Long tyBets;
    private Long lhc0Bets;
    private Long lhc1Bets;
    private Long lhc2Bets;
    private Long lhc3Bets;

    //返水金额
    private Long gpcRebate=0l;
    private Long dpcRebate=0l;
    private Long flcRebate=0l;
    private Long tycpRebate=0l;
    private Long qtRebate=0l;
    private Long tyRebate=0l;
    private Long lhc0Rebate=0l;
    private Long lhc1Rebate=0l;
    private Long lhc2Rebate=0l;
    private Long lhc3Rebate=0l;

    private List<RebateVideoResultInfoDTO> videoResultInfoDTOS;//真人游戏 用户投注与返水

    private String orderField="id";
    private String orderDirection = "desc";
    private Integer page = 1;
    private Integer limit = 10;


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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public Long getEffectiveBets() {
        return effectiveBets;
    }

    public void setEffectiveBets(Long effectiveBets) {
        this.effectiveBets = effectiveBets;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getAllRebates() {
        return allRebates;
    }

    public void setAllRebates(Long allRebates) {
        this.allRebates = allRebates;
    }

    public Long getGpcBets() {
        return gpcBets;
    }

    public void setGpcBets(Long gpcBets) {
        this.gpcBets = gpcBets;
    }

    public Long getDpcBets() {
        return dpcBets;
    }

    public void setDpcBets(Long dpcBets) {
        this.dpcBets = dpcBets;
    }

    public Long getFlcBets() {
        return flcBets;
    }

    public void setFlcBets(Long flcBets) {
        this.flcBets = flcBets;
    }

    public Long getTycpBets() {
        return tycpBets;
    }

    public void setTycpBets(Long tycpBets) {
        this.tycpBets = tycpBets;
    }

    public Long getQtBets() {
        return qtBets;
    }

    public void setQtBets(Long qtBets) {
        this.qtBets = qtBets;
    }

    public Long getTyBets() {
        return tyBets;
    }

    public void setTyBets(Long tyBets) {
        this.tyBets = tyBets;
    }

    public Long getLhc0Bets() {
        return lhc0Bets;
    }

    public void setLhc0Bets(Long lhc0Bets) {
        this.lhc0Bets = lhc0Bets;
    }

    public Long getLhc1Bets() {
        return lhc1Bets;
    }

    public void setLhc1Bets(Long lhc1Bets) {
        this.lhc1Bets = lhc1Bets;
    }

    public Long getLhc2Bets() {
        return lhc2Bets;
    }

    public void setLhc2Bets(Long lhc2Bets) {
        this.lhc2Bets = lhc2Bets;
    }

    public Long getLhc3Bets() {
        return lhc3Bets;
    }

    public void setLhc3Bets(Long lhc3Bets) {
        this.lhc3Bets = lhc3Bets;
    }

    public Long getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Long gpcRebate) {
        this.gpcRebate = gpcRebate;
    }

    public Long getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Long dpcRebate) {
        this.dpcRebate = dpcRebate;
    }

    public Long getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(Long flcRebate) {
        this.flcRebate = flcRebate;
    }

    public Long getTycpRebate() {
        return tycpRebate;
    }

    public void setTycpRebate(Long tycpRebate) {
        this.tycpRebate = tycpRebate;
    }

    public Long getQtRebate() {
        return qtRebate;
    }

    public void setQtRebate(Long qtRebate) {
        this.qtRebate = qtRebate;
    }

    public Long getTyRebate() {
        return tyRebate;
    }

    public void setTyRebate(Long tyRebate) {
        this.tyRebate = tyRebate;
    }

    public Long getLhc0Rebate() {
        return lhc0Rebate;
    }

    public void setLhc0Rebate(Long lhc0Rebate) {
        this.lhc0Rebate = lhc0Rebate;
    }

    public Long getLhc1Rebate() {
        return lhc1Rebate;
    }

    public void setLhc1Rebate(Long lhc1Rebate) {
        this.lhc1Rebate = lhc1Rebate;
    }

    public Long getLhc2Rebate() {
        return lhc2Rebate;
    }

    public void setLhc2Rebate(Long lhc2Rebate) {
        this.lhc2Rebate = lhc2Rebate;
    }

    public Long getLhc3Rebate() {
        return lhc3Rebate;
    }

    public void setLhc3Rebate(Long lhc3Rebate) {
        this.lhc3Rebate = lhc3Rebate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public List<RebateVideoResultInfoDTO> getVideoResultInfoDTOS() {
        return videoResultInfoDTOS;
    }

    public void setVideoResultInfoDTOS(List<RebateVideoResultInfoDTO> videoResultInfoDTOS) {
        this.videoResultInfoDTOS = videoResultInfoDTOS;
    }

    @Override
    public String toString() {
        return "RebateResultInfoDTO{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", highLevelAccount='" + highLevelAccount + '\'' +
                ", resultId=" + resultId +
                ", effectiveBets=" + effectiveBets +
                ", status=" + status +
                ", allRebates=" + allRebates +
                ", gpcBets=" + gpcBets +
                ", dpcBets=" + dpcBets +
                ", flcBets=" + flcBets +
                ", tycpBets=" + tycpBets +
                ", qtBets=" + qtBets +
                ", tyBets=" + tyBets +
                ", lhc0Bets=" + lhc0Bets +
                ", lhc1Bets=" + lhc1Bets +
                ", lhc2Bets=" + lhc2Bets +
                ", lhc3Bets=" + lhc3Bets +
                ", gpcRebate=" + gpcRebate +
                ", dpcRebate=" + dpcRebate +
                ", flcRebate=" + flcRebate +
                ", tycpRebate=" + tycpRebate +
                ", qtRebate=" + qtRebate +
                ", tyRebate=" + tyRebate +
                ", lhc0Rebate=" + lhc0Rebate +
                ", lhc1Rebate=" + lhc1Rebate +
                ", lhc2Rebate=" + lhc2Rebate +
                ", lhc3Rebate=" + lhc3Rebate +
                ", videoResultInfoDTOS=" + videoResultInfoDTOS +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
