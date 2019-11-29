package com.jq.user.customer.dto;

import java.io.Serializable;

public class UserRebateUpdateDTO implements Serializable{
    /** 高频彩返点*/
    private String gpcRebate;
    /** 棋牌返点 */
    private String flcRebate;
    /** 体育彩票返点 */
    private String tycpRebate;
    /** 其他返点 */
    private String qtRebate;
    /** 体育返点 */
    private String tyRebate;
    /** 低频彩返点 */
    private String dpcRebate;
    /** 六合彩0 */
    private String lhcRebate0;
    /** 六合彩1 */
    private String lhcRebate1;
    /** 六合彩2 */
    private String lhcRebate2;
    /** 六合彩3 */
    private String lhcRebate3;
    // --------------------------------------------
    private Long userId;
    private String password;
    private String realName;
    private String mobile;
    private String userName;
    private String payPwd;

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public String getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(String gpcRebate) {
        this.gpcRebate = gpcRebate;
    }

    public String getFlcRebate() {
        return flcRebate;
    }

    public void setFlcRebate(String flcRebate) {
        this.flcRebate = flcRebate;
    }

    public String getTycpRebate() {
        return tycpRebate;
    }

    public void setTycpRebate(String tycpRebate) {
        this.tycpRebate = tycpRebate;
    }

    public String getQtRebate() {
        return qtRebate;
    }

    public void setQtRebate(String qtRebate) {
        this.qtRebate = qtRebate;
    }

    public String getTyRebate() {
        return tyRebate;
    }

    public void setTyRebate(String tyRebate) {
        this.tyRebate = tyRebate;
    }

    public String getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(String dpcRebate) {
        this.dpcRebate = dpcRebate;
    }

    public String getLhcRebate0() {
        return lhcRebate0;
    }

    public void setLhcRebate0(String lhcRebate0) {
        this.lhcRebate0 = lhcRebate0;
    }

    public String getLhcRebate1() {
        return lhcRebate1;
    }

    public void setLhcRebate1(String lhcRebate1) {
        this.lhcRebate1 = lhcRebate1;
    }

    public String getLhcRebate2() {
        return lhcRebate2;
    }

    public void setLhcRebate2(String lhcRebate2) {
        this.lhcRebate2 = lhcRebate2;
    }

    public String getLhcRebate3() {
        return lhcRebate3;
    }

    public void setLhcRebate3(String lhcRebate3) {
        this.lhcRebate3 = lhcRebate3;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "UserRebateUpdateDTO{" +
                "gpcRebate='" + gpcRebate + '\'' +
                ", flcRebate='" + flcRebate + '\'' +
                ", tycpRebate='" + tycpRebate + '\'' +
                ", qtRebate='" + qtRebate + '\'' +
                ", tyRebate='" + tyRebate + '\'' +
                ", dpcRebate='" + dpcRebate + '\'' +
                ", lhcRebate0='" + lhcRebate0 + '\'' +
                ", lhcRebate1='" + lhcRebate1 + '\'' +
                ", lhcRebate2='" + lhcRebate2 + '\'' +
                ", lhcRebate3='" + lhcRebate3 + '\'' +
                ", userId=" + userId +
                ", password='" + password + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", userName='" + userName + '\'' +
                ", payPwd='" + payPwd + '\'' +
                '}';
    }
}
