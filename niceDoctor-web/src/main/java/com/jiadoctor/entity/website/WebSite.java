/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.entity.website;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 站点配置
 * 
 * @author Panda
 * @version 1.0
 */
@Entity
@Table(name = "web_site")
public class WebSite implements java.io.Serializable {

	private static final long serialVersionUID = 1896394745146145592L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "SITE_NAME", length = 50)
	private String siteName;

	@Column(name = "SITE_TITLE", length = 50)
	private String siteTitle;

	@Column(name = "SITE_KEYWORD", length = 200)
	private String siteKeyword;

	@Column(name = "SITE_DOMAIN", length = 200)
	private String siteDomain;

	@Column(name = "SITE_PORT", length = 10)
	private String sitePort;

	@Column(name = "SITE_COPYRIGHT", length = 200)
	private String siteCopyright;

	@Column(name = "SITE_RECODE", length = 200)
	private String siteRecode;

	@Column(name = "SITE_LOGO", length = 200)
	private String siteLogo;

	@Column(name = "SITE_DESCRIBE", length = 255)
	private String siteDescribe;

	@Column(name = "SITE_STATUS", length = 1)
	private String siteStatus;

	public WebSite() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSiteName() {
		return this.siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteTitle() {
		return this.siteTitle;
	}

	public void setSiteTitle(String siteTitle) {
		this.siteTitle = siteTitle;
	}

	public String getSiteKeyword() {
		return this.siteKeyword;
	}

	public void setSiteKeyword(String siteKeyword) {
		this.siteKeyword = siteKeyword;
	}

	public String getSiteDomain() {
		return this.siteDomain;
	}

	public void setSiteDomain(String siteDomain) {
		this.siteDomain = siteDomain;
	}

	public String getSitePort() {
		return this.sitePort;
	}

	public void setSitePort(String sitePort) {
		this.sitePort = sitePort;
	}

	public String getSiteCopyright() {
		return this.siteCopyright;
	}

	public void setSiteCopyright(String siteCopyright) {
		this.siteCopyright = siteCopyright;
	}

	public String getSiteRecode() {
		return this.siteRecode;
	}

	public void setSiteRecode(String siteRecode) {
		this.siteRecode = siteRecode;
	}

	public String getSiteLogo() {
		return this.siteLogo;
	}

	public void setSiteLogo(String siteLogo) {
		this.siteLogo = siteLogo;
	}

	public String getSiteDescribe() {
		return this.siteDescribe;
	}

	public void setSiteDescribe(String siteDescribe) {
		this.siteDescribe = siteDescribe;
	}

	public String getSiteStatus() {
		return siteStatus;
	}

	public void setSiteStatus(String siteStatus) {
		this.siteStatus = siteStatus;
	}

}
