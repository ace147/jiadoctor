package com.jiadoctor.entity.patient;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Ace
 * 病人登陆记录
 */
@Entity
@Table(name = "JYB_LOGINRECORD")
public class JybLoginRecord  implements Serializable{
    
	
	private static final long serialVersionUID = 1693067634317687859L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "LOGINTIME")
	private Date loginTime; // 登陆时间
	
	@Column(name = "TYPE")
	private String type; // 登陆方式   ‘1’= APP ；‘2’= PC ；‘3’= 其他；

	@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler"})    
	@ManyToOne(cascade=CascadeType.PERSIST,fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private JybUser jybUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JybUser getJybUser() {
		return jybUser;
	}

	public void setJybUser(JybUser jybUser) {
		this.jybUser = jybUser;
	}

	
}
