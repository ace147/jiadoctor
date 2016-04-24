/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import com.jiadoctor.common.constant.CommonConstant;

/**
 * @author Microcat
 * @version 1.0
 */
public class StringUtil {

	public static boolean isBlank(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static String getGetterMethodName(String fieldName) {
		String getter = CommonConstant.CONSTANT_STRING_GET + toUpperCaseFirstLetter(fieldName);
		return getter;
	}

	public static String getSetterMethodName(String fieldName) {
		String setter = CommonConstant.CONSTANT_STRING_SET + toUpperCaseFirstLetter(fieldName);
		return setter;
	}

	public static String toUpperCaseFirstLetter(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	public static String toLowerCaseFirstLetter(String str) {
		return str.substring(0, 1).toLowerCase() + str.substring(1);
	}

}
