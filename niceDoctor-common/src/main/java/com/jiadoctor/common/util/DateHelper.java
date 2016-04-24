/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Microcat
 * @version 1.0
 */
public class DateHelper {

	/**
	 * 通用日期格式
	 */
	public static final String DATE_COMMON_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";

	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
	
	public static Date getCurrentDate() {
		Calendar calender = Calendar.getInstance();
		return calender.getTime();
	}


	/**
	 * 获取一个日期，该日期是在date的days天之前(days是负数)或之后(days是正数)
	 * 
	 * @param date
	 * @param days
	 * @return Date
	 */
	public static Date getDateByCalculateDays(Date date, int days) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.DATE, days);
		return calender.getTime();
	}

	/**
	 * 获取一个日期，该日期是在date的seconds秒之前(seconds是负数)或之后(seconds是正数)
	 * 
	 * @param date
	 * @param days
	 * @return Date
	 */
	public static Date getDateByCalculateSeconds(Date date, int seconds) {
		Calendar calender = Calendar.getInstance();
		calender.setTime(date);
		calender.add(Calendar.SECOND, seconds);
		return calender.getTime();
	}

	/**
	 * 把日期按照某个格式转换成字符串
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dataToString(Object date, String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(date);
	}

	/**
	 * 把字符串按照某个格式转换成日期类型
	 * 
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date stringToDate(String strDate, String format) throws Exception {
		DateFormat df = new SimpleDateFormat(format);
		Date date = null;
		if (strDate != null && !"".equals(strDate)) {
			date = df.parse((String) strDate);
		}
		return date;
	}
	
	public static String getRandomNum(){
		StringBuffer sb = new StringBuffer();
		sb.append(dataToString(new java.util.Date(),DATE_FORMAT_YYYYMMDDHHMM));
		sb.append(String.valueOf(Math.round(Math.random() * 8999 + 1000)));
		return sb.toString();
	}
	
}
