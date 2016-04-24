/**
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.system;

import java.io.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.common.util.ResponseHandler;
import com.jiadoctor.service.system.SystemService;


/**
 * 系统功能处理
 * 
 * @author Panda
 * @version 1.0
 */
@Controller
public class SystemController {

	@Autowired
	@Qualifier("systemService")
	protected SystemService systemService;

	@RequestMapping("/download")
	public ResponseEntity<byte[]> download() {
		try {
			File file = systemService.backupData();
			return ResponseHandler.downloadFile(file);
		} catch (Exception e) {
			LogUtil.logException("SystemController.download", e);
			return null;
		}
	}

}