/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.system;

import com.jiadoctor.entity.system.Permission;

/**
 * 角色接口
 * 
 * @author Panda
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface PermissionService {

	public void addPermission(Permission permission);

	public void modifyPermission(Permission permission);

	public void removePermission(Long id);

	public Permission retrievePermissionById(Long id);

	public java.util.List retrievePermissionAll();
	
	public java.util.List retrievePermissionMenu();

}
