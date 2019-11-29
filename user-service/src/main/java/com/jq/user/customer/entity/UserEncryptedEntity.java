package com.jq.user.customer.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@TableName("user_encrypted")
public class UserEncryptedEntity implements Serializable{

	 /**
     * 主键
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
	/** 问题   **/
	private String question ;
	/** 答案   **/
	private  String result;
	/** 用户id **/
	@JsonSerialize(using = ToStringSerializer.class)	
	private Long userId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	

}
