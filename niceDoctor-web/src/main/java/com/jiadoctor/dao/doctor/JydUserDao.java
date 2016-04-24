/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.doctor;

import java.util.List;

import com.jiadoctor.entity.doctor.JydUser;



/**
 * @author Ace
 * @version 1.0
 */
public interface JydUserDao {
	
	public JydUser isExistJydUserByMobile(String mobile);

	public JydUser isExistJydUserByName(String username);
	
	public JydUser isExistJydUserByNickname(String nickname);

	@SuppressWarnings("rawtypes")
	public List retrieveJydUserAll(JydUser jydUser);
	
	
}