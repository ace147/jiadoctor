package com.jiadoctor.entity.doctor;

import java.io.Serializable;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@SuppressWarnings("serial")
@Entity
@Table(name = "JYD_ROLES")
public class JydRoles implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "NAME", length = 100)
	private String name;

	@Column(name = "STATE", length = 1)
	private String state;

	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"}) 
	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "JYD_ROLESAUTHORITIES", joinColumns = @JoinColumn(name = "ROLE_ID") , inverseJoinColumns = @JoinColumn(name = "AUTHORITIES_ID") )
	private Set<JydAuthorities> jydAuthorities;
	
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Set<JydAuthorities> getJydAuthorities() {
		return jydAuthorities;
	}

	public void setJydAuthorities(Set<JydAuthorities> jydAuthorities) {
		this.jydAuthorities = jydAuthorities;
	}

}
