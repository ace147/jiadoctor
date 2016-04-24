/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.dao.system.PermissionDao;
import com.jiadoctor.dao.system.RoleDao;
import com.jiadoctor.entity.system.Permission;
import com.jiadoctor.entity.system.Role;


/**
 * @author Panda
 * @version 1.0
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl implements RoleService {

	@Autowired
	@Qualifier("roleDao")
	RoleDao roleDao;

	@Autowired
	@Qualifier("permissionDao")
	PermissionDao permissionDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.RoleService#addRole(cc.messcat.entity.system.
	 * Role)
	 */
	@Override
	public void addRole(Role role, Long[] permissions) throws Exception {
		// 无权限
		List<Permission> permissionList = permissionDao.find(permissions);
		if (CollectionUtil.isListNotEmpty(permissionList)) {
			java.util.Set<Permission> permissionSet = new java.util.HashSet<Permission>(permissionList);
			role.setPermissions(permissionSet);
		}
		this.addObject(role);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.RoleService#modifyRole(cc.messcat.entity.system
	 * .Role)
	 */
	@Override
	public void modifyRole(Role role, Long[] permissions) throws Exception {
		Role role0 = this.retrieveRoleById(role.getId());
		if (null == role0)
			throw new Exception(CommonConstant.MSG_ROLE_EXCEPTION);

		List<Permission> permissionList = permissionDao.find(permissions);

		if (CollectionUtil.isListNotEmpty(permissionList)) {
			java.util.Set<Permission> permissionSet = new java.util.HashSet<Permission>(permissionList);
			role0.setPermissions(permissionSet);
		}

		role0.setDescription(role.getDescription());
		role0.setName(role.getName());
		this.modifyObject(role0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cc.messcat.service.system.RoleService#removeRole(java.lang.Long)
	 */
	@Override
	public void removeRole(Long id) {
		this.removeObject(Role.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.RoleService#retrieveRoleById(java.lang.Long)
	 */
	@Override
	public Role retrieveRoleById(Long id) {
		return (Role) this.retrieveObject(Role.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.RoleService#retrieveRoleAll(java.lang.Integer,
	 * java.lang.Integer)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Pager retrieveRoleAll(Integer pageNo, Integer pageSize) {
		List list = retrieveRoleAll();
		return getPager(pageNo, pageSize, list, null);
	}

	@SuppressWarnings("rawtypes")
	private java.util.List retrieveRoleAll() {
		return this.retrieveAllObjects(Role.class);
	}

}
