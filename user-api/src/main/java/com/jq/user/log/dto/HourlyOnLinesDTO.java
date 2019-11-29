package com.jq.user.log.dto;

import java.io.Serializable;

public class HourlyOnLinesDTO implements Serializable{
    // 时间 例如:x=7 则对应时间段为7:00-7:59
    private Integer x;
    // 在线会员数量
    private int y;

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "HourlyOnLinesDTO{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
