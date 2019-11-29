package com.jq.user.customer.vo;

import java.io.Serializable;
import java.util.Map;

/**
 * 保存用户登录成功信息扩展
 * Created by ZhangCong on 2018/4/23.
 */
public class UserLoginSuccessInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long userId; //用户id
    private String userName;//账号名称
    private String token;//token登录标识
    private String canAmount;//可用金额
    private Map<String, Object> isPopupNotice;//弹窗公告信息
    private Integer isProxy;//是否是代理用户,1:是/0:否
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCanAmount() {
        return canAmount;
    }

    public void setCanAmount(String canAmount) {
        this.canAmount = canAmount;
    }

    public Map<String, Object> getIsPopupNotice() {
        return isPopupNotice;
    }

    public void setIsPopupNotice(Map<String, Object> isPopupNotice) {
        this.isPopupNotice = isPopupNotice;
    }

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    @Override
    public String toString() {
        return "UserLoginSuccessInfoVO [userId=" + userId + ", userName=" + userName + ", token=" + token
                + ", canAmount=" + canAmount + ", isPopupNotice=" + isPopupNotice + ", isProxy=" + isProxy + "]";
    }

}
