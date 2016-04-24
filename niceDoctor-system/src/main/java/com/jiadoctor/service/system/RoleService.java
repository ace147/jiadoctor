/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.system;

import com.jiadoctor.common.model.Pager;
import com.jiadoctor.entity.system.Role;


/**
 * 角色接口
 * 
 * @author Panda
 * @version 1.0
 */
public interface RoleService {

	public void addRole(Role role, Long[] permissions) throws Exception;

	public void modifyRole(Role role, Long[] permissions) throws Exception;

	public void removeRole(Long id);

	public Role retrieveRoleById(Long id);

	public Pager retrieveRoleAll(Integer pageNo, Integer pageSize);

}
