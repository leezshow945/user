package com.jq.user.customer.vo;

public class UserQryParamVO {
    /** 站点code[必填] **/
//    private String siteCode;
    /** 会员id[必填] **/
//    private String userId;
    /** 会员账号 **/
    private String userName;
    /** 注单号 **/
    private String orderNo;
    /** 开始时间,格式:YYYY-MM-DD[必填] **/
    private String startTime;
    /** 结束时间,格式:YYYY-MM-DD[必填] **/
    private String endTime;
    /** 一级玩法[必填] **/
    private String pid;
    /** 二级玩法[必填] **/
    private String playId;
    /** 彩种[必填] **/
    private String gameCode;
    /** 订单状态: 撤销、派彩中、和局、中奖异常、未中奖、已中奖、未派彩 **/
    private String status;
    /** 页码[必填] **/
//    private String pageNo;
    /** 页数 [必填] **/
//    private String pageSize;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPlayId() {
        return playId;
    }

    public void setPlayId(String playId) {
        this.playId = playId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
