package com.jq.user.common;

/**
 * 〈基础DTO〉
 *
 * @author Json
 * @create 2018/7/11
 */
public class BaseDTO {

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private String updateTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否删除 默认
     */
    private Integer isDel;

    /**
     * ip
     **/
    private String ip;

    /**
     * ip归属地
     **/
    private String ipAttribution;

    /**
     * 操作人类型 (系统用户，站点用户，用户，试玩用户，测试用户)
     */
    private String OperatorType;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIpAttribution() {
        return ipAttribution;
    }

    public void setIpAttribution(String ipAttribution) {
        this.ipAttribution = ipAttribution;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
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

    public String getOperatorType() {
        return OperatorType;
    }

    public void setOperatorType(String operatorType) {
        OperatorType = operatorType;
    }
}
