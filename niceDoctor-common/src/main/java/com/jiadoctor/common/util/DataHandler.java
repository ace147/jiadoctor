/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import java.io.InputStream;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.JDBCSettingBean;


/**
 * @author Panda
 * @version 1.0
 */
public class DataHandler {
	
	/**
	 * 数据脚本备份
	 * 
	 * @param jdbcBean
	 * @return
	 * @throws Exception
	 * 
	 * @author Panda
	 */
	public static String backupDatabaseScript(JDBCSettingBean jdbcBean) throws Exception{
		
		String fileName = "数据备份_"+FileHandler.createFileNameByTimeRole(CommonConstant.DATA_BACKUP_SUFFIX);
		
		StringBuffer targetFilePath = new StringBuffer();
		targetFilePath.append(jdbcBean.getDataPath());
		targetFilePath.append(CommonConstant.SYMBOL_SLASH);
		targetFilePath.append(fileName);
		
		// 使用mysqldump cmd 备份功能，读取需要备份
		String cmd = getBackupDatabaseCMD(fileName, false,jdbcBean);
		Runtime rt = Runtime.getRuntime();
		Process process = rt.exec(cmd);
		InputStream in = process.getInputStream();
		FileHandler.createFile(in, targetFilePath.toString());
		
		return targetFilePath.toString();
	}

	/**
	 * 获取数据备份cmd
	 * 
	 * @param fileName
	 * @param isOnlyStruct
	 * @param jdbcBean
	 * @return
	 * @throws Exception
	 * 
	 * @author Panda
	 */
	public static String getBackupDatabaseCMD(String fileName,boolean isOnlyStruct,JDBCSettingBean jdbcBean) throws Exception{
		StringBuffer cmd = new StringBuffer();
		cmd.append(jdbcBean.getMysqldump());	// 	C:\Program Files\MySQL\MySQL Server 5.5\bin
		cmd.append(" -h");						//	-h
		cmd.append(jdbcBean.getHost());			// 	127.0.0.1
		cmd.append(" -u");						//	-u
		cmd.append(jdbcBean.getUserName());		// 	root
		cmd.append(" -p");						//	-p
		cmd.append(jdbcBean.getPassword());		// 	123456
		if(isOnlyStruct)
			cmd.append(" --no-data");			//	–databases
		cmd.append(" ");						//	 
		cmd.append(jdbcBean.getDatabaseName());	// 	mysql
		return cmd.toString();
	}
	
	
}
