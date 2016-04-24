/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import java.util.List;

/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class CollectionUtil {

	public static boolean isListEmpty(List list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isListNotEmpty(List list) {
		if (list != null && !list.isEmpty()) {
			return true;
		}
		return false;
	}
	
	public static boolean isMapEmpty(java.util.Map map){
		if(null==map || map.isEmpty())
			return true;
		return false;
	}
	
	public static boolean isSetEmpty(java.util.Set set){
		if(null==set || set.isEmpty())
			return true;
		return false;
	}

}
