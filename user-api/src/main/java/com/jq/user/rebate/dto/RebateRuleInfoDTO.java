package com.jq.user.rebate.dto;

import java.io.Serializable;
import java.util.List;

public class RebateRuleInfoDTO implements Serializable {

    private Long id;
    private Long ruleId;//规则组id
    private Long rebateMost;//返水金额上限
    private Long effectiveBets;//总有效投注金额

    /** 棋牌返点  **/
    private Long  flcRebate;

    /** 高频彩返点  **/
    private Long  gpcRebate;

    /** 体育彩票返点  **/
    private Long  tycpRebate;

    /** 其他返点  **/
    private Long  qtRebate;

    /** 体育返点  **/
    private Long  tyRebate;

    /** 六合彩返点0，单位：万  **/
    private Long  lhcRebate0;

    /** 六合彩返点1，单位：万  **/
    private Long  lhcRebate1;

    /** 六合彩返点2，单位：万  **/
    private Long  lhcRebate2;

    /** 六合彩返点3，单位：万  **/
    private Long  lhcRebate3;

    /** 低频彩返点，单位：万  **/
    private Long  dpcRebate;

    private String createBy;
    private String updateBy;

    private List<RebateVideoRuleDTO> videoRuleDTOList;//真人返水值

    private String orderField="effective_bets";
    private String orderDirection = "desc";
    private Integer page = 1;
    private Integer limit = 10;

    public List<RebateVideoRuleDTO> getVideoRuleDTOList() {
        return videoRuleDTOList;
    }

    public void setVideoRuleDTOList(List<RebateVideoRuleDTO> videoRuleDTOList) {
        this.videoRuleDTOList = videoRuleDTOList;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
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

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getRebateMost() {
        return rebateMost;
    }

    public void setRebateMost(Long rebateMost) {
        this.rebateMost = rebateMost;
    }

    public Long getEffectiveBets() {
        return effectiveBets;
    }

    public void setEffectiveBets(Long effectiveBets) {
        this.effectiveBets = effectiveBets;
    }

    public Long getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(Long flcRebate) {
        this.flcRebate = flcRebate;
    }

    public Long getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Long gpcRebate) {
        this.gpcRebate = gpcRebate;
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

    public Long getLhcRebate0() {
        return lhcRebate0;
    }

    public void setLhcRebate0(Long lhcRebate0) {
        this.lhcRebate0 = lhcRebate0;
    }

    public Long getLhcRebate1() {
        return lhcRebate1;
    }

    public void setLhcRebate1(Long lhcRebate1) {
        this.lhcRebate1 = lhcRebate1;
    }

    public Long getLhcRebate2() {
        return lhcRebate2;
    }

    public void setLhcRebate2(Long lhcRebate2) {
        this.lhcRebate2 = lhcRebate2;
    }

    public Long getLhcRebate3() {
        return lhcRebate3;
    }

    public void setLhcRebate3(Long lhcRebate3) {
        this.lhcRebate3 = lhcRebate3;
    }

    public Long getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Long dpcRebate) {
        this.dpcRebate = dpcRebate;
    }

    @Override
    public String toString() {
        return "RebateRuleInfoDTO{" +
                "id=" + id +
                ", ruleId=" + ruleId +
                ", rebateMost=" + rebateMost +
                ", effectiveBets=" + effectiveBets +
                ", flcRebate=" + flcRebate +
                ", gpcRebate=" + gpcRebate +
                ", tycpRebate=" + tycpRebate +
                ", qtRebate=" + qtRebate +
                ", tyRebate=" + tyRebate +
                ", lhcRebate0=" + lhcRebate0 +
                ", lhcRebate1=" + lhcRebate1 +
                ", lhcRebate2=" + lhcRebate2 +
                ", lhcRebate3=" + lhcRebate3 +
                ", dpcRebate=" + dpcRebate +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", videoRuleDTOList=" + videoRuleDTOList +
                '}';
    }
}
