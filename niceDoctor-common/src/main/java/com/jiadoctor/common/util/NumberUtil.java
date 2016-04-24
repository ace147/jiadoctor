package com.jiadoctor.common.util;

/**
 * @author Sherry
 * @version 1.0
 */
public class NumberUtil {

	public static boolean isIntegerZero(Integer num) {
		if (null == num || num.intValue() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isIntegerNotZero(Integer num) {
		if (null != num && num.intValue() != 0) {
			return true;
		}
		return false;
	}
	
	public static boolean isLongZero(Long num) {
		if (null == num || num.intValue() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isLongNotZero(Long num) {
		if (null != num && num.intValue() != 0) {
			return true;
		}
		return false;
	}
}
