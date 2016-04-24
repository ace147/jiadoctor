/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.FilesUploadModel;
import com.jiadoctor.common.model.ResponseBean;
import com.jiadoctor.common.util.FileHandler;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.common.util.StringUtil;


/**
 * 公共接口 文件上传，文件下载，文件删除
 * 
 * @author Panda
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/base")
public class ActionController {

	@Value("#{ configProperties['web.name'] }")
	private String projectName;

	@Value("#{ configProperties['upload.image.path'] }")
	private String imageFolderName;

	@Value("#{ configProperties['upload.file.path'] }")
	private String fileFolderName;

	@Value("#{ configProperties['upload.file.maxUploadSize'] }")
	private Long fileMaxSize;

	@RequestMapping(value = "/imageUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object pullImageFile(MultipartFile file) {
		try {
			FilesUploadModel fileModel = null;
			if (!file.isEmpty()) {
				fileModel = new FilesUploadModel(file.getOriginalFilename(), file.getInputStream(), projectName, imageFolderName);
				fileModel.writeFileByBinary();
			}
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS, fileModel.getFileUri());
		} catch (Exception e) {
			LogUtil.logException("ActionController pullImageFile has exception:", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	@RequestMapping(value = "/imageUploadByBase64", method = RequestMethod.POST)
	@ResponseBody
	public Object pullImageFileByBase64(@RequestParam String fileName, @RequestParam String fileContent) {
		try {
			FilesUploadModel fileModel = null;
			if (StringUtil.isNotBlank(fileName) && StringUtil.isNotBlank(fileContent)) {
				fileModel = new FilesUploadModel(fileName, fileContent, projectName, imageFolderName);
				fileModel.writeFileByBase64();
			}
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS, fileModel.getFileUri());
		} catch (Exception e) {
			LogUtil.logException("ActionController pullImageFile has exception:", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public Object pullFile(MultipartFile file) {
		try {
			FilesUploadModel fileModel = null;
			if (!file.isEmpty()) {
				fileModel = new FilesUploadModel(file.getOriginalFilename(), file.getInputStream(), projectName, fileFolderName);
				fileModel.writeFileByBinary();
			}
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS, fileModel.getFileUri());
		} catch (Exception e) {
			LogUtil.logException("ActionController pullFile has exception:", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	@RequestMapping(value = "/fileRemove", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteFile(String uri) {
		try {
			FileHandler.deleteFile(uri, null, projectName);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.logException("ActionController pullFile has exception:", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
		}
	}

}
