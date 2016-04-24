/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.doctor;


import java.util.List;

import com.jiadoctor.entity.doctor.JydAuthorities;
import com.jiadoctor.entity.doctor.JydRoles;


/**
 * @author Ace
 * @version 1.0
 */
public interface JydRolesDao {

	/**
	 * 根据条件获取角色列表(不分页)
	 */
	public List<JydRoles> getJydRolesByCondition(JydRoles jydRoles);
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	public List<JydAuthorities> getJydAuthoritiesByCondition(JydAuthorities jydAuthorities);
	
	public void updateJydRoles(JydRoles jydRoles);
}
