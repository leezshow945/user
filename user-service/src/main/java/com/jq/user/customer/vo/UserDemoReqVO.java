package com.jq.user.customer.vo;

import java.io.Serializable;

/**
 * @author Brady
 *         Descript:试玩账户请求参数VO
 *         Date: 2018/4/27
 */
public class UserDemoReqVO implements Serializable {

    private String loginBeginTime;  //登录时间开始

    private String loginEndTime;  //登录时间结束

    private String orderBeginTime;  //游戏时间开始

    private String orderEndTime;  //游戏时间结束

    private String gameCode;  //游戏类型

    public String getLoginBeginTime() {
        return loginBeginTime;
    }

    public void setLoginBeginTime(String loginBeginTime) {
        this.loginBeginTime = loginBeginTime;
    }

    public String getLoginEndTime() {
        return loginEndTime;
    }

    public void setLoginEndTime(String loginEndTime) {
        this.loginEndTime = loginEndTime;
    }

    public String getOrderBeginTime() {
        return orderBeginTime;
    }

    public void setOrderBeginTime(String orderBeginTime) {
        this.orderBeginTime = orderBeginTime;
    }

    public String getOrderEndTime() {
        return orderEndTime;
    }

    public void setOrderEndTime(String orderEndTime) {
        this.orderEndTime = orderEndTime;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    @Override
    public String toString() {
        return "UserDemoReqVO{" +
                "loginBeginTime='" + loginBeginTime + '\'' +
                ", loginEndTime='" + loginEndTime + '\'' +
                ", orderBeginTime='" + orderBeginTime + '\'' +
                ", orderEndTime='" + orderEndTime + '\'' +
                ", gameCode='" + gameCode + '\'' +
                '}';
    }
}
