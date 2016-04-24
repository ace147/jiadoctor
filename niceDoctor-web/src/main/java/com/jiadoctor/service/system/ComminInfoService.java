/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.system;

import com.jiadoctor.entity.system.CommonInfo;

/**
 * 角色接口
 * 
 * @author Panda
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public interface ComminInfoService {

	public void addComminInfo(CommonInfo commonInfo);

	public void modifyComminInfo(CommonInfo commonInfo)throws Exception;

	public void removeComminInfo(Long id);

	public CommonInfo retrieveComminInfoById(Long id);

	public java.util.List retrieveComminInfoAll();

}
