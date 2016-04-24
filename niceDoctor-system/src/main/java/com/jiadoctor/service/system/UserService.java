/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.system;

import com.jiadoctor.common.model.Pager;
import com.jiadoctor.entity.system.McUser;


/**
 * 用户接口
 * 
 * @author Microcat
 * @version 1.0
 */
public interface UserService {

	public McUser retrieveUserById(Long id);

	public McUser retrieveUserByIdWithoutPermission(Long id);

	public McUser retrieveUserByUsernameIgnorePassword(String username);

	public McUser retrieveUserByUsername(String username);
	
	public McUser retrieveUserWithRoleByUsernames(String username);
	
	/**
	 * 登录
	 * @param userName
	 * @throws Exception
	 * @author Panda
	 */
	public void modifyForLogin(String userName) throws Exception;

	public void addUser(McUser user, Long roleId) throws Exception;

	public void modifyUser(McUser user, Long roleId) throws Exception;

	public void modifyPasswordUser(McUser user, String authPassword) throws Exception;

	public void removeUser(Long id);

	public boolean verifyOldPassword(String username, String oldPassword);

	public Pager retrieveUserAll(Integer pageNo, Integer pageSize);
}
