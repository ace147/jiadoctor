/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.system;

import java.util.List;

import com.jiadoctor.entity.system.Permission;


/**
 * @author Panda
 * @version 1.0
 */
public interface PermissionDao {

	public java.util.List<Permission> find(Long[] permissions);
	
	public List<Permission> findMenu();
	
}
