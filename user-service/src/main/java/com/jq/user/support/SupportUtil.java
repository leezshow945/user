package com.jq.user.support;

import java.math.BigDecimal;

/**
 * @Author: levi
 * @Descript: 项目支持工具类集合
 * @Date: 2018/5/30
 */
public class SupportUtil {

    public static Long doubleToLong(Double amount) {
        if (amount == null) {
            return null;
        }
        BigDecimal unit = new BigDecimal(100);
        return new BigDecimal(amount).multiply(unit).longValue();
    }

    public static BigDecimal longTo(Long amount) {
        if (amount == null) {
            return null;
        }
        BigDecimal unit = new BigDecimal(100);
        return new BigDecimal(amount).divide(unit);
    }

    public static void main(String[] args) {
        Double d = 3.4;
        Long aLong = doubleToLong(d);
        System.out.println(aLong);
    }


    /**
     * 根据层级组装返回设定等级图标名
     * @param n
     * @return
     */
    public static String getRankImgByLevel(int n){
        return "rank_img/vip"+n+".png";
    }


}
