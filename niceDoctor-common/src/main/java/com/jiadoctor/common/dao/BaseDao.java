/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.dao;

import java.util.List;

/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface BaseDao{
	
	Object get(Class clazz, final long id);

	List<Object> getAll(Class clazz);

	void save(final Object entity);

	Object update(final Object entity);

	void delete(final Object entity);

	void deleteById(Class clazz, final long entityId);
	
	/**
	 * @author Sherry
	 */
	List<Object> findByExample(Object entity);
}
