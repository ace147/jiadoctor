/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.entity.doctor;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import com.jiadoctor.common.model.views.Views;

/**
 * @author Ace
 * @version 1.0
 */
@Entity
@Table(name = "JYD_USER")
public class JydUser implements Serializable {

	private static final long serialVersionUID = 2219160238586496748L;

	public interface WithoutPasswordView extends Views {
	};

	@Id
	@JsonView(WithoutPasswordView.class)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@JsonView(WithoutPasswordView.class)
	@Column(name = "ACCOUNT ", length = 20)
	private String account; // 帐号

	@JsonIgnore
	@Column(name = "PASSWORD", length = 100)
	private String password; // 密码
	
	@JsonView(WithoutPasswordView.class)
	@Column(name = "REALNAME", length = 100)
	private String realName; // 真实名称
	
	@JsonView(WithoutPasswordView.class)
	@Column(name = "CARDTYPE", length = 2)
	private String cardType; // 证件类型  ‘1’= 身份证；‘2’= 护照;‘3’= 港澳通行证；‘4’= 军官证；
    
	@JsonView(WithoutPasswordView.class)
	@Column(name = "CARDNUM", length = 2)
	private String cardNum; // 证件号码
	
	@JsonView(WithoutPasswordView.class)
	@Column(name = "NICKNAME", length = 40)
	private String nickname; // 昵称

	@JsonView(WithoutPasswordView.class)
	@Column(name = "MOBILE", length = 20)
	private String mobile; // 手机

	@JsonView(WithoutPasswordView.class)
	@Column(name = "SEX", length = 1)
	private String sex; // 性别 ‘M’=男；‘F’=女

	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonView(WithoutPasswordView.class)
	@Column(name = "BIRTHDAY")
	private Date birthday; // 生日

	@JsonView(WithoutPasswordView.class)
	@Column(name = "ADDRESS", length = 300)
	private String address; // 地址
	
	@JsonView(WithoutPasswordView.class)
	@Column(name = "AREA", length = 300)
	private String area; // 地区  例如：广州海珠区

	@JsonView(WithoutPasswordView.class)
	@Column(name = "REGISTIME")
	private Date regisTime; // 创建时间，注册时间

	@JsonView(WithoutPasswordView.class)
	@Column(name = "USERHEAD", length = 100)
	private String userHead; // 头像

	@JsonView(WithoutPasswordView.class)
	@Column(name = "LASTVISITTIME", length = 100)
	private Date lastVisitTime; // 最后访问时间

	@JsonView(WithoutPasswordView.class)
	@Column(name = "LOGINNUMS", length = 20)
	private Integer loginNums; // 登陆次数

	@JsonView(WithoutPasswordView.class)
	@Column(name = "STATUS", length = 2)
	private String status; // 状态 ‘0’=禁用;‘1’=启用;‘2’注销

	@JsonView(WithoutPasswordView.class)
	@Column(name = "REGISFROM", length = 2)
	private String regisFrom; // 会员注册方式 ‘1’=平台;‘2’=微信;‘3’=QQ;‘4’=新浪‘1’= QQ；‘2’= 新浪微博；‘3’= 微信；‘4’= 平台；


	@JsonView(WithoutPasswordView.class)
	@Column(name = "ISBLOCK", length = 2)
	private String isBlock; // 是否拉黑 ‘Y’=是;‘F’=否

	
	@JsonManagedReference
	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name = "JYD_USERROLES", joinColumns = @JoinColumn(name = "USER_ID") , inverseJoinColumns = @JoinColumn(name = "ROLES_ID") )
	private Set<JydRoles> JydRoles;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getRegisTime() {
		return regisTime;
	}

	public void setRegisTime(Date regisTime) {
		this.regisTime = regisTime;
	}

	public String getUserHead() {
		return userHead;
	}

	public void setUserHead(String userHead) {
		this.userHead = userHead;
	}

	public Date getLastVisitTime() {
		return lastVisitTime;
	}

	public void setLastVisitTime(Date lastVisitTime) {
		this.lastVisitTime = lastVisitTime;
	}

	public Integer getLoginNums() {
		return loginNums;
	}

	public void setLoginNums(Integer loginNums) {
		this.loginNums = loginNums;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRegisFrom() {
		return regisFrom;
	}

	public void setRegisFrom(String regisFrom) {
		this.regisFrom = regisFrom;
	}

	public String getIsBlock() {
		return isBlock;
	}

	public void setIsBlock(String isBlock) {
		this.isBlock = isBlock;
	}

	public Set<JydRoles> getJydRoles() {
		return JydRoles;
	}

	public void setJydRoles(Set<JydRoles> jydRoles) {
		JydRoles = jydRoles;
	}


}
