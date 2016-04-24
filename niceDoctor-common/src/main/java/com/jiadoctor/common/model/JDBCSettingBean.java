/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.model;

/**
 * 数据备份，自定义备份地址
 * @author Panda
 * @version 1.0
 */
public class JDBCSettingBean {

	private String mysqldump;
	private String host;
	private String userName;
	private String password;
	private String databaseName;
	private String dataPath;

	public JDBCSettingBean() {
	};

	public JDBCSettingBean(String mysqldump, String host, String userName, String password, String databaseName, String dataPath) {
		this.mysqldump = mysqldump;
		this.host = host;
		this.userName = userName;
		this.password = password;
		this.databaseName = databaseName;
		this.dataPath = dataPath;
	};

	public String getMysqldump() {
		return mysqldump;
	}

	public void setMysqldump(String mysqldump) {
		this.mysqldump = mysqldump;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getDataPath() {
		return dataPath;
	}

	public void setDataPath(String dataPath) {
		this.dataPath = dataPath;
	}
}
