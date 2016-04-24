/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.service;

import java.util.List;

/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface BaseService {

	public Object retrieveObject(Class c, Long id);

	public List retrieveAllObjects(Class c);
	
	public List retrieveByExample(Object object);

	public void addObject(Object object);

	public void modifyObject(Object object);

	public void removeObject(Class c, Long id);

	public void removeObject(Object object);

}
