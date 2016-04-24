/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.model;

import java.io.InputStream;

import com.jiadoctor.common.constant.SystemConstant;
import com.jiadoctor.common.util.ContainerUtil;
import com.jiadoctor.common.util.FileHandler;


/**
 * 
 * @author Panda
 * @version 1.0
 */
public class FilesUploadModel {

	private String originFileName;
	private String newFileName;
	private InputStream fileInputStream;
	private String base64FileContent;
	private String fileRealPath;
	private String fileUri;
	private String projectName;
	private String folderName;
	private boolean isFileExists = false;

	public FilesUploadModel(String projectName, String fileUri) {
		this.fileRealPath = ContainerUtil.getContainerRealPath(projectName,
			fileUri.replace("/", System.getProperty(SystemConstant.FILE_SEPARATOR)));
	}
	
	public FilesUploadModel(String originFileName, String base64FileContent, String projectName, String folderName) {
		this.originFileName = originFileName;
		this.base64FileContent = base64FileContent;
		this.projectName = projectName;
		this.folderName = folderName;
		this.newFileName = FileHandler.createFileNameByTimeRole(originFileName);
	}

	public FilesUploadModel(String originFileName, InputStream fileInputStream, String projectName, String folderName) {
		this.originFileName = originFileName;
		this.fileInputStream = fileInputStream;
		this.projectName = projectName;
		this.folderName = folderName;
		this.newFileName = FileHandler.createFileNameByTimeRole(originFileName);
	}

	public FilesUploadModel(InputStream fileInputStream, String projectName, String folderName, String newFileName) {
		this.newFileName = newFileName;
		this.fileInputStream = fileInputStream;
		this.projectName = projectName;
		this.folderName = folderName;
	}

	public void writeFileByBinary() throws Exception {
		this.fileRealPath = ContainerUtil.getContainerRealPath(projectName, folderName, newFileName);
		this.isFileExists = FileHandler.createFileByBinary(this.fileInputStream, fileRealPath);
		this.fileUri = FileHandler.getFileUri(folderName, newFileName);
	}
	
	public void writeFileByBase64() throws Exception {
		this.fileRealPath = ContainerUtil.getContainerRealPath(projectName, folderName, newFileName);
		this.isFileExists = FileHandler.createFileByBase64(base64FileContent, fileRealPath);
		this.fileUri = FileHandler.getFileUri(folderName, newFileName);
	}

	public void deleteFile() {
		new java.io.File(this.fileRealPath).deleteOnExit();
	}

	public String getOriginFileName() {
		return originFileName;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public String getFileRealPath() {
		return fileRealPath;
	}

	public String getFileUri() {
		return fileUri;
	}

	public boolean isFileExists() {
		return isFileExists;
	}

}
