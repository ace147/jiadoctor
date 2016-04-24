/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.system;

import com.jiadoctor.entity.system.McUser;

/**
 * @author Microcat
 * @version 1.0
 */
public interface UserDao {

	public McUser getUserIgnorePassword(Long id);

	public McUser getUserByUsername(String username);
	
	public McUser getCurrentMcUser();

}
