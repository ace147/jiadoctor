/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Microcat
 * @version 1.0
 */
public class BeanUtil {

	/**
	 * 把obj1里的值赋给obj0，然后返回obj0，excludeFields是不需要赋值的属性组合
	 * 
	 * @param obj0
	 * @param obj1
	 * @param excludeFields
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object setBeanByOtherBean(Object obj0, Object obj1, String[] excludeFields) throws Exception {

		// 把排除属性放入set里
		Set excludeFieldsSet = new HashSet();
		if (excludeFields != null && excludeFields.length > 0) {
			for (String ef : excludeFields) {
				excludeFieldsSet.add(ef);
			}
		}

		// 把未排除的属性放入list里
		List<String> fieldList = new ArrayList<String>();
		Field[] fields = obj0.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String fieldName = field.getName();
			if (!excludeFieldsSet.contains(fieldName)) {
				fieldList.add(fieldName);
			}

		}

		// 循环属性list进行赋值
		int size = fieldList.size();
		for (int i = 0; i < size; i++) {
			String fieldName = fieldList.get(i);
			Method getterMethod = obj1.getClass().getMethod(StringUtil.getGetterMethodName(fieldName), new Class[] {});
			Method setterMethod = obj0.getClass().getMethod(StringUtil.getSetterMethodName(fieldName),
				getterMethod.getReturnType());
			Object value = getterMethod.invoke(obj1, new Object[] {});
			setterMethod.invoke(obj0, new Object[] { value });
		}

		return obj0;
	}

	/**
	 * 把obj1里的值（非空）赋给obj0，然后返回obj0
	 * 
	 * @param obj0
	 * @param obj1
	 * @return
	 * @throws Exception
	 * @author Panda
	 * @param <T>
	 * @return
	 */
	public static <T> T setBeanByOtherBeanWithoutNull(T obj0, T obj1) throws Exception {

		Field[] fields = obj0.getClass().getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i].getName();
			if ("serialVersionUID".equals(fieldName))
				continue;
			Method getterMethod = obj1.getClass().getMethod(StringUtil.getGetterMethodName(fieldName), new Class[] {});
			Method setterMethod = obj0.getClass().getMethod(StringUtil.getSetterMethodName(fieldName),
				getterMethod.getReturnType());
			Object value = getterMethod.invoke(obj1, new Object[] {});
			if (null == value)
				continue;
			setterMethod.invoke(obj0, new Object[] { value });
		}

		return obj0;
	}

	/**
	 * map to bean
	 * 
	 * @param map
	 * @param obj
	 * @author Panda
	 */
	public static <T> T parserToBean(Map<String, Object> map, Class<T> objClass) {
		T obj = null;
		try {
			obj = objClass.newInstance();
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if (map.containsKey(key)) {

					Object value = map.get(key);
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}

		} catch (Exception e) {
			System.out.println("transMap2Bean Error " + e);
		}

		return obj;
	}

	/**
	 * @param obj 
	 * @param filterAttributeNames 过滤属性名集合
	 * @param isFilterNull	是否过滤空值
	 * @return
	 * @author Panda
	 */
	public static Map<String, Object> parserToMap(Object obj, boolean isFilterNull, String ... filterAttributeNames) {

		if (obj == null) {
			return null;
		}
		
		boolean isFilter = false;
		List<String> attrList = null;
		if(null != filterAttributeNames && filterAttributeNames.length>0){
			isFilter = true;
			attrList = java.util.Arrays.asList(filterAttributeNames);
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				if("serialVersionUID".equals(key))
					continue;
				if (isFilter) {
					if (attrList.contains(key))
						continue;
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (isFilterNull && (null == value || "".equals(value)))
						continue;
					map.put(key, value);
				} else if (!key.equals("class")) {// 过滤class属性
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					if (isFilterNull && (null == value || "".equals(value)))
						continue;
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;

	}
	
	/**
	 * @param objs
	 * @return
	 */
	public static boolean isValid(Object... objs) {
		boolean isValid = true;
		if (null != objs) {
			for (int i = 0; i < objs.length; i++) {
				if(null == objs[i]  ) {
					isValid = false;
				}
				if(objs[i] instanceof String) {
					String key = (String)objs[i];
					if(key.trim().equals("")) {
						isValid = false;
					}
					if(key.trim().equals("-1")) {
						isValid = false;
					}
				} else if(objs[i] instanceof Integer) {
					Integer key = (Integer)objs[i];
					if(key.intValue()<=0) {
						isValid = false;
					}
				} else if(objs[i] instanceof Double) {
					Double key = (Double)objs[i];
					if(key.doubleValue()<=0) {
						isValid = false;
					}
				} else if(objs[i] instanceof Byte) {
					Byte key = (Byte)objs[i];
					if(key.byteValue() < 0) {
						isValid = false;
					}
				}else if (objs[i] instanceof Long) {
					Long key = (Long)objs[i];
					if(key.longValue() <= 0) {
						isValid = false;
					}
				}else if (objs[i] instanceof List<?>) {
					List<?> key = (List<?>)objs[i];
					if(key.size() <= 0) {
						isValid = false;
					}
				}else if (objs[i] instanceof JSONArray) {
					JSONArray key = (JSONArray)objs[i];
					if(key.size() <= 0) {
						isValid = false;
					}
				}else if (objs[i] instanceof JSONObject) {
					JSONObject key = (JSONObject)objs[i];
					if(key.size() <= 0) {
						isValid = false;
					}
				}
			}
		}else {
			isValid = false;
		}
		return isValid;
	}
	
	/**
	 * 将对象数组集合转为Map集合
	 * 如：objs=[{1,"sherry"}],names=["id","name"] 可以转换为list=[{"id":1,"name":"sherry"}]
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Map> changeObjectArrayToMaps(List<Object[]> objs, String[] names){
		List<Map> maps = new ArrayList<Map>();
		if(null == names || names.length == 0)
			return maps;
		for (int i = 0; i < objs.size(); i++) {
			//将对象数组转化成map对象
			maps.add(changeObjectArrayToMap(objs.get(i), names));
		}
		return maps;
	}
	
	/**
	 * 将对象数组转为Map
	 * 如：obj={1,"sherry"} 可以转换为result={"id":1,"name":"sherry"}
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map changeObjectArrayToMap(Object[] obj, String[] names){
		Map map = new HashMap();
		for (int j = 0; j < names.length; j++) {
			map.put(names[j], obj[j]);
		}
		return map;
	}

}
