package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/18
 */
public class UserDTO implements Serializable{

    private static final long serialVersionUID = 1L;
    /** 主键*/
    private Long id;
    /** 站点id*/
    private Long siteId;
    /** siteCode */
    private String siteCode;
    /** 会员等级id*/
    private Long userRankId;
    /** 连续签到次数*/
    private Integer signCount;
    /** 登陆次数*/
    private Integer loginCount;
    /** 登录密码连续错误次数*/
    private Integer loginErrCount;
    /** 用户名*/
    private String userName;
    /** 密码*/
    private String password;
    /** 创建时间*/
    private Date createTime;
    /** 最后更新时间*/
    private Date updateTime;
    /** 创建人*/
    private String createBy;
    /** 最后更新人*/
    private String updateBy;
    /** 用户状态 1：删除，0：正常*/
    private Integer isDel;
    /** googleAuthenticator秘钥*/
    private String key;
    /** 上次签到时间*/
    private Date lastSignTime;
    /** 上次登录时间*/
    private Date lastLoginTime;
    /** 最后一次修改密码的时间*/
    private Date lastChangePwdTime;
    /** 上次登录ip*/
    private String lastLoginIp;
    /** 上次登录ip*/
    private String lastLoginAddress;
    /** 修改密码错误次数*/
    private Integer changePwdErrNum;
    /** 修改支付密码错误次数*/
    private Integer changePayPwdErrNum;
    /** 冻结积分*/
    private Integer freezeScore;
    /** 可用积分*/
    private Integer canScore;
    /** 积分值*/
    private Integer score;
    /** 用户状态 10 启用，11 停用，21 暂停投注，31 冻结，41 拉黑*/
    private Integer status;
    /** 取款密码*/
    private String payPwd;
    /** 是否是试玩账号：1是，0否  2:测试用户*/
    private Integer isDemo ;
    /** 迁移时间*/
    private Date transferTime;
    private Integer random;
    /** 备注*/
    private String remark;
    /** 最后游戏时间*/
    private Date lastPlaytime;
    /** 最后游戏种类*/
    private String lastCategory;
    /**排序字段,默认sort_id**/
    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    /** 当前页 */
    private Integer page = 1;
    /** 每页条数 */
    private Integer limit = 20;
    /** 最大层级 */
    private Integer maxLevel;
    /** 最大等级 */
    private Integer maxRank;
    /** 是否绑定密保问题 **/
    private boolean isBindEncrypted;
    /** 是否拥有银行卡 */
    private boolean isBindBankCard;
    /** 是否绑定邮箱 */
    private boolean isBindEmail;
    /** 是否绑定手机号码 */
    private boolean isBindMobile;
    /** 是否绑定真实姓名 */
    private boolean isBindRealName;
    /** 是否已签到 */
    private boolean isSignIn;
    /** 是否禁止返点 */
    private boolean isBanRebate;
    /** 是否设置取款密码 */
    private boolean hasPayPwd;
    /** 等级图片url */
    private String rankImage;
    /** 等级名称 */
    private String rankName;
    /** 等级阶级 */
    private Integer rankLevel;
    /** 下一级最大积分 */
    private Integer nextRankScore;
    /** 充值1元兑换积分值 */
    private String rankRechargeRatio;
    /** 用户头像 */
    private String photo;
    /** 真实姓名 */
    private String realName;
    /** 用户id */
    private Long userId;
    /** 是否是试玩账号*/
    private boolean isInDemo;
    /** 用户类型：1-代理，0-会员 */
    private Integer isProxy;
    /** 高频彩返点*/
    private Long gpcRebate;
    /** 棋牌返点 */
    private Long flcRebate;
    /** 体育彩票返点 */
    private Long tycpRebate;
    /** 其他返点 */
    private Long qtRebate;
    /** 体育返点 */
    private Long tyRebate;
    /** 低频彩返点 */
    private Long dpcRebate;
    /** 六合彩0 */
    private Long lhcRebate0;
    /** 六合彩1 */
    private Long lhcRebate1;
    /** 六合彩2 */
    private Long lhcRebate2;
    /** 六合彩3 */
    private Long lhcRebate3;
    private Long amount;
    private String path;
    private String token;



    /** 上周投注金额 */
    private String preWeekBetMoney="0";
    /** 本周投注金额 */
    private String curWeekBetMoney="0";
    /** 是否显示分红 */
    private String isShow;
    // 余额
    private long canAmount;
    // 存款次数
    private long depositNum;
    // 存款总额
    private long depositAmount;
    // 取款次数
    private long withdrawNum;
    // 取款总额
    private long withdrawAmount;
    private String highLevelAccount;
    private String weChat;
    private String qq;
    private String mobile;
    private String email;
    // 注册IP
    private String regIp;
    /** 最近存款时间 YYYY-MM-DD */
    private String depositTime;
    /** 最近取款时间 YYYY-MM-DD */
    private String withDrawTime;
    private Integer integralMall;//是否显示积分商城  0 不显示  1 显示
    /** 昵称*/
    private String nickName;

    private Integer isConvert;//用户额度转换开启状态 0-未开启 1-开启

    public Integer getIsConvert() {
        return isConvert;
    }

    public void setIsConvert(Integer isConvert) {
        this.isConvert = isConvert;
    }


    public Integer getRankLevel() {
        return rankLevel;
    }

    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    public Integer getNextRankScore() {
        return nextRankScore;
    }

    public void setNextRankScore(Integer nextRankScore) {
        this.nextRankScore = nextRankScore;
    }

    public String getRankRechargeRatio() {
        return rankRechargeRatio;
    }

    public void setRankRechargeRatio(String rankRechargeRatio) {
        this.rankRechargeRatio = rankRechargeRatio;
    }

    public boolean isBanRebate() {
        return isBanRebate;
    }

    public void setBanRebate(boolean banRebate) {
        isBanRebate = banRebate;
    }

    public String getRegIp() {
        return regIp;
    }

    public void setRegIp(String regIp) {
        this.regIp = regIp;
    }

    public String getDepositTime() {
        return depositTime;
    }

    public void setDepositTime(String depositTime) {
        this.depositTime = depositTime;
    }

    public String getWithDrawTime() {
        return withDrawTime;
    }

    public void setWithDrawTime(String withDrawTime) {
        this.withDrawTime = withDrawTime;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getHighLevelAccount() {
        return highLevelAccount;
    }

    public void setHighLevelAccount(String highLevelAccount) {
        this.highLevelAccount = highLevelAccount;
    }

    public long getCanAmount() {
        return canAmount;
    }

    public void setCanAmount(long canAmount) {
        this.canAmount = canAmount;
    }

    public long getDepositNum() {
        return depositNum;
    }

    public void setDepositNum(long depositNum) {
        this.depositNum = depositNum;
    }

    public long getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(long depositAmount) {
        this.depositAmount = depositAmount;
    }

    public long getWithdrawNum() {
        return withdrawNum;
    }

    public void setWithdrawNum(long withdrawNum) {
        this.withdrawNum = withdrawNum;
    }

    public long getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(long withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getPreWeekBetMoney() {
        return preWeekBetMoney;
    }

    public void setPreWeekBetMoney(String preWeekBetMoney) {
        this.preWeekBetMoney = preWeekBetMoney;
    }

    public String getCurWeekBetMoney() {
        return curWeekBetMoney;
    }

    public void setCurWeekBetMoney(String curWeekBetMoney) {
        this.curWeekBetMoney = curWeekBetMoney;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getGpcRebate() {
        return gpcRebate;
    }

    public void setGpcRebate(Long gpcRebate) {
        this.gpcRebate = gpcRebate;
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

    public Long getDpcRebate() {
        return dpcRebate;
    }

    public void setDpcRebate(Long dpcRebate) {
        this.dpcRebate = dpcRebate;
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

    public Integer getIsProxy() {
        return isProxy;
    }

    public void setIsProxy(Integer isProxy) {
        this.isProxy = isProxy;
    }

    public boolean isInDemo() {
        return isInDemo;
    }

    public void setInDemo(boolean inDemo) {
        isInDemo = inDemo;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public boolean isBindBankCard() {
        return isBindBankCard;
    }

    public void setBindBankCard(boolean bindBankCard) {
        isBindBankCard = bindBankCard;
    }

    public boolean isBindEmail() {
        return isBindEmail;
    }

    public void setBindEmail(boolean bindEmail) {
        isBindEmail = bindEmail;
    }

    public boolean isBindMobile() {
        return isBindMobile;
    }

    public void setBindMobile(boolean bindMobile) {
        isBindMobile = bindMobile;
    }

    public boolean isBindRealName() {
        return isBindRealName;
    }

    public void setBindRealName(boolean bindRealName) {
        isBindRealName = bindRealName;
    }

    public boolean isSignIn() {
        return isSignIn;
    }

    public void setSignIn(boolean signIn) {
        isSignIn = signIn;
    }

    public boolean isHasPayPwd() {
        return hasPayPwd;
    }

    public void setHasPayPwd(boolean hasPayPwd) {
        this.hasPayPwd = hasPayPwd;
    }

    public String getRankImage() {
        return rankImage;
    }

    public void setRankImage(String rankImage) {
        this.rankImage = rankImage;
    }

    public String getRankName() {
        return rankName;
    }

    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }


    public Integer getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(Integer maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Integer getMaxRank() {
        return maxRank;
    }

    public void setMaxRank(Integer maxRank) {
        this.maxRank = maxRank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public Long getUserRankId() {
        return userRankId;
    }

    public void setUserRankId(Long userRankId) {
        this.userRankId = userRankId;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public Integer getLoginCount() {
        return loginCount;
    }

    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    public Integer getLoginErrCount() {
        return loginErrCount;
    }

    public void setLoginErrCount(Integer loginErrCount) {
        this.loginErrCount = loginErrCount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getLastSignTime() {
        return lastSignTime;
    }

    public void setLastSignTime(Date lastSignTime) {
        this.lastSignTime = lastSignTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastChangePwdTime() {
        return lastChangePwdTime;
    }

    public void setLastChangePwdTime(Date lastChangePwdTime) {
        this.lastChangePwdTime = lastChangePwdTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public Integer getChangePwdErrNum() {
        return changePwdErrNum;
    }

    public void setChangePwdErrNum(Integer changePwdErrNum) {
        this.changePwdErrNum = changePwdErrNum;
    }

    public Integer getChangePayPwdErrNum() {
        return changePayPwdErrNum;
    }

    public void setChangePayPwdErrNum(Integer changePayPwdErrNum) {
        this.changePayPwdErrNum = changePayPwdErrNum;
    }

    public Integer getFreezeScore() {
        return freezeScore;
    }

    public void setFreezeScore(Integer freezeScore) {
        this.freezeScore = freezeScore;
    }

    public Integer getCanScore() {
        return canScore;
    }

    public void setCanScore(Integer canScore) {
        this.canScore = canScore;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPayPwd() {
        return payPwd;
    }

    public void setPayPwd(String payPwd) {
        this.payPwd = payPwd;
    }

    public Integer getIsDemo() {
        return isDemo;
    }

    public void setIsDemo(Integer isDemo) {
        this.isDemo = isDemo;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(Date transferTime) {
        this.transferTime = transferTime;
    }

    public Integer getRandom() {
        return random;
    }

    public void setRandom(Integer random) {
        this.random = random;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getLastPlaytime() {
        return lastPlaytime;
    }

    public void setLastPlaytime(Date lastPlaytime) {
        this.lastPlaytime = lastPlaytime;
    }

    public String getLastCategory() {
        return lastCategory;
    }

    public void setLastCategory(String lastCategory) {
        this.lastCategory = lastCategory;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getIntegralMall() {
        return integralMall;
    }

    public void setIntegralMall(Integer integralMall) {
        this.integralMall = integralMall;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public boolean isBindEncrypted() {
		return isBindEncrypted;
	}

	public void setBindEncrypted(boolean isBindEncrypted) {
		this.isBindEncrypted = isBindEncrypted;
	}
    public String getLastLoginAddress() {
        return lastLoginAddress;
    }

    public void setLastLoginAddress(String lastLoginAddress) {
        this.lastLoginAddress = lastLoginAddress;
    }
    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", userRankId=" + userRankId +
                ", signCount=" + signCount +
                ", loginCount=" + loginCount +
                ", loginErrCount=" + loginErrCount +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", isDel=" + isDel +
                ", key='" + key + '\'' +
                ", lastSignTime=" + lastSignTime +
                ", lastLoginTime=" + lastLoginTime +
                ", lastChangePwdTime=" + lastChangePwdTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", lastLoginAddress='" + lastLoginAddress + '\'' +
                ", changePwdErrNum=" + changePwdErrNum +
                ", changePayPwdErrNum=" + changePayPwdErrNum +
                ", freezeScore=" + freezeScore +
                ", canScore=" + canScore +
                ", score=" + score +
                ", status=" + status +
                ", payPwd='" + payPwd + '\'' +
                ", isDemo=" + isDemo +
                ", transferTime=" + transferTime +
                ", random=" + random +
                ", remark='" + remark + '\'' +
                ", lastPlaytime=" + lastPlaytime +
                ", lastCategory='" + lastCategory + '\'' +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                ", maxLevel=" + maxLevel +
                ", maxRank=" + maxRank +
                ", isBindBankCard=" + isBindBankCard +
                ", isBindEmail=" + isBindEmail +
                ", isBindMobile=" + isBindMobile +
                ", isBindRealName=" + isBindRealName +
                ", isSignIn=" + isSignIn +
                ", isBanRebate=" + isBanRebate +
                ", hasPayPwd=" + hasPayPwd +
                ", rankImage='" + rankImage + '\'' +
                ", rankName='" + rankName + '\'' +
                ", rankLevel=" + rankLevel +
                ", nextRankScore=" + nextRankScore +
                ", rankRechargeRatio='" + rankRechargeRatio + '\'' +
                ", photo='" + photo + '\'' +
                ", realName='" + realName + '\'' +
                ", userId=" + userId +
                ", isInDemo=" + isInDemo +
                ", isProxy=" + isProxy +
                ", gpcRebate=" + gpcRebate +
                ", flcRebate=" + flcRebate +
                ", tycpRebate=" + tycpRebate +
                ", qtRebate=" + qtRebate +
                ", tyRebate=" + tyRebate +
                ", dpcRebate=" + dpcRebate +
                ", lhcRebate0=" + lhcRebate0 +
                ", lhcRebate1=" + lhcRebate1 +
                ", lhcRebate2=" + lhcRebate2 +
                ", lhcRebate3=" + lhcRebate3 +
                ", amount=" + amount +
                ", path='" + path + '\'' +
                ", token='" + token + '\'' +
                ", preWeekBetMoney='" + preWeekBetMoney + '\'' +
                ", curWeekBetMoney='" + curWeekBetMoney + '\'' +
                ", isShow='" + isShow + '\'' +
                ", canAmount=" + canAmount +
                ", depositNum=" + depositNum +
                ", depositAmount=" + depositAmount +
                ", withdrawNum=" + withdrawNum +
                ", withdrawAmount=" + withdrawAmount +
                ", highLevelAccount='" + highLevelAccount + '\'' +
                ", weChat='" + weChat + '\'' +
                ", qq='" + qq + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", regIp='" + regIp + '\'' +
                ", depositTime='" + depositTime + '\'' +
                ", withDrawTime='" + withDrawTime + '\'' +
                ", integralMall=" + integralMall +
                ", nickName='" + nickName + '\'' +
                ", isBindEncrypted='" + isBindEncrypted + '\'' +
                '}';
    }

	
}
