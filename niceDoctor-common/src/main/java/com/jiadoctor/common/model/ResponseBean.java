/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.jiadoctor.common.model.views.Views;



/**
 * 响应json数据结构模型
 * @author Panda
 * @version 1.0
 */
public class ResponseBean {
	
	@JsonView(Views.class)
	private String message;
	
	@JsonView(Views.class)
	private String status;
	
	@JsonView(Views.class)
	private Object result;
	
	public ResponseBean(String status){
		this.status = status;
	}
	
	public ResponseBean(String status,String message){
		this.status = status;
		this.message = message;
	}
	
	public ResponseBean(String status,String message,Object result){
		this.status = status;
		this.message = message;
		this.result = result;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
