/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.patient;

import java.util.List;

import com.jiadoctor.entity.patient.JybUser;


/**
 * @author Ace
 * @version 1.0
 */
public interface JybUserDao {
	
	public JybUser isExistJybUserByMobile(String mobile);

	public JybUser isExistJybUserByName(String username);
	
	public JybUser isExistJybUserByNickname(String nickname);

	@SuppressWarnings("rawtypes")
	public List retrieveJybUserAll(JybUser jybUser);
	
	
}