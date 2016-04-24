/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.patient;


import java.util.List;

import com.jiadoctor.entity.patient.JybAuthorities;
import com.jiadoctor.entity.patient.JybRoles;


/**
 * @author Ace
 * @version 1.0
 */
public interface JybRolesDao {

	/**
	 * 根据条件获取角色列表(不分页)
	 */
	public List<JybRoles> getJybRolesByCondition(JybRoles jybRoles);
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	public List<JybAuthorities> getJybAuthoritiesByCondition(JybAuthorities jybAuthorities);
	
	public void updateJybRoles(JybRoles jybRoles);
}
