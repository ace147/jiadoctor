/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.dao.system.PermissionDao;
import com.jiadoctor.entity.system.Permission;


/**
 * @author Panda
 * @version 1.0
 */
@Service("permissionService")
public class PermissionServiceImpl extends BaseServiceImpl implements PermissionService {

	@Autowired
	@Qualifier("permissionDao")
	PermissionDao permissionDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.PermissionService#addPermission(cc.messcat.
	 * entity.system.Permission)
	 */
	@Override
	public void addPermission(Permission permission) {
		this.addObject(permission);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.PermissionService#modifyPermission(cc.messcat.
	 * entity.system.Permission)
	 */
	@Override
	public void modifyPermission(Permission permission) {
		this.modifyObject(permission);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.PermissionService#removePermission(java.lang.
	 * Long)
	 */
	@Override
	public void removePermission(Long id) {
		this.removeObject(Permission.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.PermissionService#retrievePermissionById(java.
	 * lang.Long)
	 */
	@Override
	public Permission retrievePermissionById(Long id) {
		return (Permission) this.retrieveObject(Permission.class, id);
	}

	@SuppressWarnings("rawtypes")
	public java.util.List retrievePermissionAll() {
		return this.retrieveAllObjects(Permission.class);
	}

	/* (non-Javadoc)
	 * @see cc.messcat.service.system.PermissionService#retrievePermissionMenu()
	 */
	@Override
	public java.util.List<Permission> retrievePermissionMenu() {
		return permissionDao.findMenu();
	}

}
