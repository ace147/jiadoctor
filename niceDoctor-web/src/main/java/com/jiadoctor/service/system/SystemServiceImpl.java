/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.system;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.model.JDBCSettingBean;
import com.jiadoctor.common.util.DataHandler;


/**
 * @author Panda
 * @version 1.0
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService {

	@Autowired
	@Qualifier("jdbcSettingBean")
	JDBCSettingBean jdbcSettingBean;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cc.messcat.service.system.SystemService#backupData()
	 */
	@Override
	public File backupData() throws Exception {
		String targetFilePath = DataHandler.backupDatabaseScript(jdbcSettingBean);
		return new File(targetFilePath);
	}

}
