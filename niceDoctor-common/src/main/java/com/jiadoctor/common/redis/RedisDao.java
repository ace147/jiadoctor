/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.redis;

import java.util.List;
import java.util.Map;

/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface RedisDao {

	public void putSession(String key, Object value);

	public Object getSession(String key);

	public void putListSession(String key, Object value);

	public List getListSession(String key);

	public void popListSession(String key);

	public void setListSession(final String key, final int index, final Object value);

	public Long listSessionSize(final String key);

	public void removeListSession(final String key, final long index, final Object value);

	public void putHashSession(final String key, final Map map);

	public Map getHashSession(final String key);

	public void deleteSession(final String key);
}
