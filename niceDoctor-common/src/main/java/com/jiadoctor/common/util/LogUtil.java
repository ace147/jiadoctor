/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.util;

import java.util.Map;

import org.apache.log4j.Logger;

import com.jiadoctor.common.constant.CommonConstant;


/**
 * @author Microcat
 * @version 1.0
 */
public class LogUtil {

	/**
	 * 日志实例
	 */
	private static final Logger logger = Logger.getLogger(LogUtil.class);

	public static void logException(String errorTitle, Exception e) {
		StringBuilder errorBuilder = new StringBuilder();
		errorBuilder.append(errorTitle).append(" : ").append(e);
		logger.error(errorBuilder.toString());
	}

	public static void logException(String errorTitle, Exception e, Map<String, Object> defualtJsonResponse) {
		logException(errorTitle, e);
		defualtJsonResponse.put("status", CommonConstant.EXCEPTION_CODE_500);
		defualtJsonResponse.put("message", CommonConstant.MSG_EXCEPTION_POPUP);
	}
}
