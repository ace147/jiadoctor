package com.jiadoctor.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 公告信息，如公司电话、地址、邮箱、logo等
 * 
 * @author Panda
 * @version 1.0
 */
@Entity
@Table(name = "COMMON_INFO")
public class CommonInfo implements java.io.Serializable {

	private static final long serialVersionUID = 5563101165996844119L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "COMPANY", length = 100)
	private String company;

	@Column(name = "COMPANY_EN", length = 20)
	private String companyEn;

	@Column(name = "COMPANY_ADDR", length = 200)
	private String companyAddr;

	@Column(name = "COMPANY_PHONE", length = 20)
	private String companyPhone;

	@Column(name = "COMPANY_EMAIL", length = 100)
	private String companyEmail;

	@Column(name = "COMPANY_LOG", length = 100)
	private String companyLog;

	@Column(name = "COMPANY_BACKGROUND", length = 100)
	private String companyBackground;

	@Column(name = "STATUS", length = 1)
	private String status;

	public CommonInfo() {
	}

	public CommonInfo(String company, String companyEn, String companyAddr, String companyPhone, String companyEmail,
		String companyLog, String companyBackground, String state) {
		this.company = company;
		this.companyEn = companyEn;
		this.companyAddr = companyAddr;
		this.companyPhone = companyPhone;
		this.companyEmail = companyEmail;
		this.companyLog = companyLog;
		this.companyBackground = companyBackground;
		this.status = state;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getCompanyEn() {
		return this.companyEn;
	}

	public void setCompanyEn(String companyEn) {
		this.companyEn = companyEn;
	}

	public String getCompanyAddr() {
		return this.companyAddr;
	}

	public void setCompanyAddr(String companyAddr) {
		this.companyAddr = companyAddr;
	}

	public String getCompanyPhone() {
		return this.companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getCompanyEmail() {
		return this.companyEmail;
	}

	public void setCompanyEmail(String companyEmail) {
		this.companyEmail = companyEmail;
	}

	public String getCompanyLog() {
		return this.companyLog;
	}

	public void setCompanyLog(String companyLog) {
		this.companyLog = companyLog;
	}

	public String getCompanyBackground() {
		return this.companyBackground;
	}

	public void setCompanyBackground(String companyBackground) {
		this.companyBackground = companyBackground;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String state) {
		this.status = state;
	}

}