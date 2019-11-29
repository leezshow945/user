package com.jq.user.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import java.util.Date;

/**
 * @author Brady
 *         Descript:
 *         Date: 2018/6/28
 */
@TableName("user_token")
public class UserTokenEntity {
    /** 用户id*/
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id ;
    /** token key*/
    private String secretToken;
    /** token*/
    private String accessToken;
    /** 过期时间*/
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date accessExpire;
    /** 创建时间*/
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date createTime;
    /** 平台类型*/
    private Integer platformType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Date getAccessExpire() {
        return accessExpire;
    }

    public void setAccessExpire(Date accessExpire) {
        this.accessExpire = accessExpire;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }

    @Override
    public String toString() {
        return "UserTokenEntity{" +
                "id=" + id +
                ", secretToken='" + secretToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", accessExpire=" + accessExpire +
                ", createTime=" + createTime +
                ", platformType=" + platformType +
                '}';
    }
}
