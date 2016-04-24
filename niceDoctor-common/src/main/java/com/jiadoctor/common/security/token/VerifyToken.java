/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.security.token;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.util.DateHelper;

/**
 * @author Microcat
 * @version 1.0
 */
public class VerifyToken {

	/**
	 * 请求延迟时间（10秒）
	 */
	private final static int REQUEST_DELAY_TIME = 10;

	/**
	 * Token定义组合：messcat2015+接口名+相关参数值+时间（yyyyMMddHHmm），再MD5加密
	 * 
	 * Token包括自定义token常量值、其他密钥信息（数组由第一个开始遍历拼接）和时间（yyyyMMddHHmm）
	 * ，鉴于接口调用延迟性，规定10秒内属于正常访问范畴。例如，在2015/8/20 23:59:59发过来的接口，
	 * 获取到的时间有可能会由201508202359变成201508210002
	 * ，这样就需要把201508210000减去10秒得到201508202359再去校验
	 * 
	 * @param token
	 * @return
	 */
	public static boolean verify(String token, String[] otherTokenDetail) {

		StringBuilder tokenWithNoMd5 = new StringBuilder();

		tokenWithNoMd5.append(CommonConstant.CUSTOM_TOKEN_KEY_CONSTANT);

		if (otherTokenDetail != null) {
			for (int i = 0; i < otherTokenDetail.length; i++) {
				tokenWithNoMd5.append(otherTokenDetail[i]);
			}
		}

		String dateTime = getDateTime();
		String dateTimeBeforeTenSeconds = getDateTimeBeforeTenSeconds();
		String newToken1 = MD5.getMD5Code(tokenWithNoMd5.toString() + dateTime);
		String newToken2 = MD5.getMD5Code(tokenWithNoMd5.toString() + dateTimeBeforeTenSeconds);

		if (newToken1.equals(token) || newToken2.equals(token)) {
			return true;
		}
		return false;
	}

	public static String getToken(String[] otherTokenDetail) {

		StringBuilder tokenWithNoMd5 = new StringBuilder();

		tokenWithNoMd5.append(CommonConstant.CUSTOM_TOKEN_KEY_CONSTANT);

		for (int i = 0; i < otherTokenDetail.length; i++) {
			tokenWithNoMd5.append(otherTokenDetail[i]);
		}

		String dateTime = getDateTime();
		String newToken1 = MD5.getMD5Code(tokenWithNoMd5.toString() + dateTime);
		return newToken1;
	}

	public static String getTokenWhitoutTime(String[] otherTokenDetail) {
		StringBuilder tokenWithNoMd5 = new StringBuilder();
		tokenWithNoMd5.append(CommonConstant.CUSTOM_TOKEN_KEY_CONSTANT);
		for (int i = 0; i < otherTokenDetail.length; i++) {
			tokenWithNoMd5.append(otherTokenDetail[i]);
		}
		String newToken1 = MD5.getMD5Code(tokenWithNoMd5.toString());
		return newToken1;
	}

	private static String getDateTimeBeforeTenSeconds() {
		String dateTimeBeforeTenSeconds = DateHelper.dataToString(
			DateHelper.getDateByCalculateSeconds(DateHelper.getCurrentDate(), -REQUEST_DELAY_TIME),
			DateHelper.DATE_FORMAT_YYYYMMDDHHMM);
		return dateTimeBeforeTenSeconds;
	}

	private static String getDateTime() {
		String dateTime = DateHelper.dataToString(DateHelper.getCurrentDate(), DateHelper.DATE_FORMAT_YYYYMMDDHHMM);
		return dateTime;
	}

	public static void main(String[] args) {
		try {
			String date = DateHelper.dataToString(DateHelper
				.getDateByCalculateSeconds(DateHelper.stringToDate("20150820000010", "yyyyMMddHHmmss"), -REQUEST_DELAY_TIME),
				DateHelper.DATE_FORMAT_YYYYMMDDHHMM);
			System.out.println(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] d = { "michael", "machine" };
		System.out.println(verify("dfd4752132a0023190f70cb81e6de032", d));
	}
}
