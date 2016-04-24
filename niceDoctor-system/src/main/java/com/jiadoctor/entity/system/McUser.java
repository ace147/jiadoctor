/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.entity.system;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.jiadoctor.common.model.views.Views;
import com.jiadoctor.common.util.DateHelper;


/**
 * 用户实体
 * 
 * @author Microcat
 * @version 1.0
 */
@Entity
@Table(name = "MC_USER")
@SuppressWarnings("serial")
public class McUser implements java.io.Serializable {

	public interface WithoutPasswordView extends Views {
	};

	@JsonView(WithoutPasswordView.class)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@JsonView(WithoutPasswordView.class)
	@Column(name = "USERNAME", length = 50)
	private String username;

	@JsonIgnore
	@Column(name = "PASSWORD", length = 50)
	private String password;

	@JsonView(WithoutPasswordView.class)
	@Column(name = "NICK_NAME", length = 20)
	private String nickName;

	@JsonView(WithoutPasswordView.class)
	@Column(name = "MOBILE_PHONE", length = 20)
	private String mobilePhone;

	@Column(name = "REAL_NAME", length = 20)
	private String realName;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Max(value = 20)
	@Column(name = "LOGIN_COUNT")
	private int loginCount;

	@DateTimeFormat(pattern = DateHelper.DATE_COMMON_FORMAT)
	@Column(name = "LAST_LOGIN")
	private java.util.Date lastLogin;

	@DateTimeFormat(pattern = DateHelper.DATE_COMMON_FORMAT)
	@Column(name = "EDIT_TIME")
	private java.util.Date editTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EDITOR")
	@JsonIgnore
	private McUser editor;

	@JsonView(WithoutPasswordView.class)
	@Column(name = "STATUS", length = 1)
	private String status;

	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "USER_ID") , inverseJoinColumns = @JoinColumn(name = "ROLE_ID") )
	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public java.util.Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(java.util.Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public java.util.Date getEditTime() {
		return editTime;
	}

	public void setEditTime(java.util.Date editTime) {
		this.editTime = editTime;
	}

	public McUser getEditor() {
		return editor;
	}

	public void setEditor(McUser editor) {
		this.editor = editor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
