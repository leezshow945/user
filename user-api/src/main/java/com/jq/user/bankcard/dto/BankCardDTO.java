package com.jq.user.bankcard.dto;

import java.io.Serializable;
import java.util.Date;

public class BankCardDTO implements Serializable {
    /** 主键id */
    private Long id;
    private Long userBankId;
    /** 用户id */
    private Long userId;
    /** 省份id */
    private Long provinceId;
    /** 省份 */
    private String province;
    /** 城市id */
    private Long cityId;
    /** 城市 */
    private String city;
    /** 身份证号码 */
    private String cardNo;
    /** 银行id */
    private Long bankId;
    /** 银行名称 */
    private String bankName;
    /** 网点id */
    private Long netAddrId;
    /** 银行网点 */
    private String netAddr;
    /** 是否启用 */
    private Integer isEnable;
    /** 是否默认 */
    private Integer isDefault;
    private Date createTime;
    private String createBy;
    private String updateBy;
    private Date updateTime;
    /** 备注 */
    private String remark;
    /** 是否删除 */
    private Integer isDel;
    /** 持卡人真实姓名 */
    private String cardUserName;
    private String branchName;
    private String banLogo;
    private Long siteId;
    private String manUserName;

    public String getManUserName() {
        return manUserName;
    }

    public void setManUserName(String manUserName) {
        this.manUserName = manUserName;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public Long getUserBankId() {
        return userBankId;
    }

    public void setUserBankId(Long userBankId) {
        this.userBankId = userBankId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Long getNetAddrId() {
        return netAddrId;
    }

    public void setNetAddrId(Long netAddrId) {
        this.netAddrId = netAddrId;
    }

    public String getNetAddr() {
        return netAddr;
    }

    public void setNetAddr(String netAddr) {
        this.netAddr = netAddr;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getCardUserName() {
        return cardUserName;
    }

    public void setCardUserName(String cardUserName) {
        this.cardUserName = cardUserName;
    }

    public String getBanLogo() {
        return banLogo;
    }

    public void setBanLogo(String banLogo) {
        this.banLogo = banLogo;
    }

    @Override
    public String toString() {
        return "BankCardDTO{" +
                "id=" + id +
                ", userBankId=" + userBankId +
                ", userId=" + userId +
                ", provinceId=" + provinceId +
                ", province='" + province + '\'' +
                ", cityId=" + cityId +
                ", city='" + city + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", bankId=" + bankId +
                ", bankName='" + bankName + '\'' +
                ", netAddrId=" + netAddrId +
                ", netAddr='" + netAddr + '\'' +
                ", isEnable=" + isEnable +
                ", isDefault=" + isDefault +
                ", createTime=" + createTime +
                ", createBy='" + createBy + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", isDel=" + isDel +
                ", cardUserName='" + cardUserName + '\'' +
                ", branchName='" + branchName + '\'' +
                ", banLogo='" + banLogo + '\'' +
                '}';
    }
}
