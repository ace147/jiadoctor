/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.jiadoctor.entity.system.McUser;

/**
 * @author Microcat
 * @version 1.0
 */
@Entity
@Table(name = "ROLE")
@SuppressWarnings("serial")
public class Role implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", length = 50)
	private String name;

	@Column(name = "description", length = 100)
	private String description;

	@Column(name = "STATUS", length = 1)
	private String status;

	@JsonBackReference
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "USERS_ROLES", joinColumns = @JoinColumn(name = "ROLE_ID") , inverseJoinColumns = @JoinColumn(name = "USER_ID") )
	private Set<McUser> users;

	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "ROLES_PERMISSIONS", joinColumns = @JoinColumn(name = "ROLE_ID") , inverseJoinColumns = @JoinColumn(name = "PERMISSION_ID") )
	private Set<Permission> permissions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<McUser> getUsers() {
		return users;
	}

	public void setUsers(Set<McUser> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
