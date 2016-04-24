/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.system;

import java.io.File;

/**
 * 系统接口 数据备份
 * 
 * @author Microcat
 * @version 1.0
 */
public interface SystemService {

	public File backupData() throws Exception;

}
