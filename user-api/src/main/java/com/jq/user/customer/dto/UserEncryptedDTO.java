package com.jq.user.customer.dto;

import java.io.Serializable;

public class UserEncryptedDTO implements Serializable{
	/** 主键id **/
	private  Long id;
	/** 问题   **/
	private String question ;
	/** 答案   **/
	private  String result;
	/** 用户id **/
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
	@Override
	public String toString() {
		return "UserEncryptedDTO [id=" + id + ", question=" + question
				+ ", result=" + result + ", userId=" + userId + "]";
	}
	
	

}
