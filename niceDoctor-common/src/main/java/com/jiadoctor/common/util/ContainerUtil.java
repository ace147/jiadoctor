/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import com.jiadoctor.common.constant.SystemConstant;

/**
 * 容器工具
 * 
 * @author Panda
 * @version 1.0
 */
public class ContainerUtil {
	
	/**
	 * 获取容器真实路径 eg.{D:\work\tomcat8.0\webapps}\{projectName}\{fileName}
	 * 
	 * @param projectName
	 * @param folderName
	 * @return
	 * @throws Exception
	 * @author Panda
	 */
	public static String getContainerRealPath(String projectName, String folderName,String fileName) throws Exception {
		StringBuffer filePath = new StringBuffer();
		filePath.append(getContainerRealPath(projectName, folderName));
		if (StringUtil.isBlank(fileName)) 
			throw new NullPointerException("fileName is null!");
		filePath.append(System.getProperty(SystemConstant.FILE_SEPARATOR));
		filePath.append(fileName);
		return filePath.toString();
	}
	
	/**
	 * 获取容器真实路径 eg.{D:\work\tomcat8.0\webapps}\{projectName}\{folderName}
	 * 
	 * @param projectName
	 * @param folderName
	 * @return
	 * @throws Exception
	 * @author Panda
	 */
	public static String getContainerRealPath(String projectName, String folderName){
		StringBuffer folderPath = new StringBuffer();

		folderPath.append(getContainerRealPath(projectName));

		if (StringUtil.isBlank(folderName)) 
			throw new NullPointerException("folderName is null");
		
		folderPath.append(System.getProperty(SystemConstant.FILE_SEPARATOR));
		
		folderPath.append(folderName);

		return folderPath.toString();
	}

	/**
	 * 获取容器真实路径 eg.{D:\work\tomcat8.0\webapps}\{projectName}
	 * 
	 * @param projectName
	 * @param folderName
	 * @return
	 * @throws Exception
	 * @author Panda
	 */
	public static String getContainerRealPath(String projectName){
		StringBuffer folderPath = new StringBuffer();
		// webroot real path
		String wwwRoot = System.getProperty(SystemConstant.WTP_DEPLOY);
		if (StringUtil.isBlank(wwwRoot))
			throw new NullPointerException("don`t exist webroot real path in system property!");
		
		folderPath.append(wwwRoot);
		
		if (StringUtil.isBlank(projectName)) 
			throw new NullPointerException("projectName is null!");
		
		folderPath.append(System.getProperty(SystemConstant.FILE_SEPARATOR));
		folderPath.append(projectName);

		return folderPath.toString();
	}
	

}
