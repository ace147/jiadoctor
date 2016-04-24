package com.jiadoctor.entity.doctor;

import java.io.Serializable;
import java.util.Set;

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

@SuppressWarnings("serial")
@Entity
@Table(name = "JYD_AUTHORITIES")
public class JydAuthorities implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "DISPLAY_NAME")
	private Long displayName;
	
	@Column(name = "AUTHORITIES_TYPE", length = 2)
	private String authoritiesType;
	
	@Column(name = "AUTHORITIES_ID")
	private Long authoritiesId;

	@JsonBackReference
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "JYD_ROLESAUTHORITIES", joinColumns = @JoinColumn(name = "AUTHORITIES_ID") , inverseJoinColumns = @JoinColumn(name = "ROLE_ID") )
	private Set<JydRoles> jydRoles;
	
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

	public Long getDisplayName() {
		return displayName;
	}

	public void setDisplayName(Long displayName) {
		this.displayName = displayName;
	}

	public String getAuthoritiesType() {
		return authoritiesType;
	}

	public void setAuthoritiesType(String authoritiesType) {
		this.authoritiesType = authoritiesType;
	}

	public Long getAuthoritiesId() {
		return authoritiesId;
	}

	public void setAuthoritiesId(Long authoritiesId) {
		this.authoritiesId = authoritiesId;
	}

	public Set<JydRoles> getJydRoles() {
		return jydRoles;
	}

	public void setJydRoles(Set<JydRoles> jydRoles) {
		this.jydRoles = jydRoles;
	}


}
