package com.jq.user.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 公共page参数集合类
 */

public class InitParameters implements Serializable {

    private Map siteMap;//站点集合
    private Map userStatusMap;//用户状态集合
    private Map scoreTypeMap;//积分动作集合
    private List rankLevelList;//会员等级列表
    private List rebateLevelList;//代理层级列表
    private Map sysDeptMap;//管理员部门集合
    private Map userRankMap;//rankId与rankLevel对应Map

    public Map getSiteMap() {
        return siteMap;
    }

    public void setSiteMap(Map siteMap) {
        this.siteMap = siteMap;
    }

    public Map getUserStatusMap() {
        return userStatusMap;
    }

    public void setUserStatusMap(Map userStatusMap) {
        this.userStatusMap = userStatusMap;
    }

    public Map getScoreTypeMap() {
        return scoreTypeMap;
    }

    public void setScoreTypeMap(Map scoreTypeMap) {
        this.scoreTypeMap = scoreTypeMap;
    }

    public List getRankLevelList() {
        return rankLevelList;
    }

    public void setRankLevelList(List rankLevelList) {
        this.rankLevelList = rankLevelList;
    }

    public List getRebateLevelList() {
        return rebateLevelList;
    }

    public void setRebateLevelList(List rebateLevelList) {
        this.rebateLevelList = rebateLevelList;
    }

    public Map getSysDeptMap() {
        return sysDeptMap;
    }

    public void setSysDeptMap(Map sysDeptMap) {
        this.sysDeptMap = sysDeptMap;
    }

    public Map getUserRankMap() {
        return userRankMap;
    }

    public void setUserRankMap(Map userRankMap) {
        this.userRankMap = userRankMap;
    }

    @Override
    public String toString() {
        return "InitParameters{" +
                "siteMap=" + siteMap +
                ", userStatusMap=" + userStatusMap +
                ", scoreTypeMap=" + scoreTypeMap +
                ", rankLevelList=" + rankLevelList +
                ", rebateLevelList=" + rebateLevelList +
                ", sysDeptMap=" + sysDeptMap +
                ", userRankMap=" + userRankMap +
                '}';
    }
}
