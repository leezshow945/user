package com.jq.user.customer.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/5/21
 */
public class SysDeptDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    /**  主键 */
    private Long id;
    /**  部门名称 */
    private String deptName;
    /**  部门电话 */
    private String deptPhone;
    /**  部门负责人 */
    private String contact;
    /**  部门负责人手机 */
    private String contactMobile;
    /**  创建人 */
    private String createBy;
    /**  创建时间 */
    private Date createTime;
    /**  最后更新人 */
    private String updateBy;
    /**  最后更新时间 */
    private Date updateTime;
    /**  备注 */
    private String remark;
    /**  部门是否启用 */
    private Integer isEnable;
    /**  站点id */
    private Long siteId;
    /** siteCode */
    private String siteCode;
    /**  部门状态 */
    private Integer isDel;
    /**  是否开启动态口令 */
    private Integer openCommand;
    /**排序字段,默认sort_id**/
    private String orderField = "id";
    /**排序方向,默认降序**/
    private String orderDirection = "desc";
    /** 当前页 */
    private Integer page = 1;
    /** 每页条数 */
    private Integer limit = 20;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptPhone() {
        return deptPhone;
    }

    public void setDeptPhone(String deptPhone) {
        this.deptPhone = deptPhone;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
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

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Integer getOpenCommand() {
        return openCommand;
    }

    public void setOpenCommand(Integer openCommand) {
        this.openCommand = openCommand;
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

    @Override
    public String toString() {
        return "SysDeptDTO{" +
                "id=" + id +
                ", deptName='" + deptName + '\'' +
                ", deptPhone='" + deptPhone + '\'' +
                ", contact='" + contact + '\'' +
                ", contactMobile='" + contactMobile + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createTime=" + createTime +
                ", updateBy='" + updateBy + '\'' +
                ", updateTime=" + updateTime +
                ", remark='" + remark + '\'' +
                ", isEnable=" + isEnable +
                ", siteId=" + siteId +
                ", siteCode='" + siteCode + '\'' +
                ", isDel=" + isDel +
                ", openCommand=" + openCommand +
                ", orderField='" + orderField + '\'' +
                ", orderDirection='" + orderDirection + '\'' +
                ", page=" + page +
                ", limit=" + limit +
                '}';
    }
}
