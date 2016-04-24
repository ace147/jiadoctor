/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.constant.SystemConstant;
import com.jiadoctor.common.model.FilesUploadModel;
import com.jiadoctor.common.model.decoder.BASE64Decoder;


/**
 * @author Microcat
 * @version 1.0
 */
public class FileHandler {

	public final static String SYMBOL_SLASH = "\\";

	/**
	 * 新建文件 文件内容写入成功，那么返回true；否则，如果文件存在删除文件，返回false
	 * 
	 * @param inputStr
	 * @param targetFilePath
	 * @return
	 * @author Panda
	 */
	public static boolean createFile(final String inputStr, final String targetFilePath) {
		try {
			createFolderIfNotExists(targetFilePath);
			writeFile(targetFilePath, inputStr, CommonConstant.ENCORD_UTF8);
			return true;
		} catch (Exception e) {
			File file = new File(targetFilePath);
			file.deleteOnExit();
			return false;
		}
	}

	/**
	 * 新建文件 文件内容写入成功，那么返回true；否则，如果文件存在删除文件，返回false
	 * 
	 * @param in
	 * @param targetFilePath
	 * @return
	 * @author Panda
	 */
	public static boolean createFile(final InputStream in, final String targetFilePath) {
		try {
			createFolderIfNotExists(targetFilePath);
			writeFile(in, targetFilePath, CommonConstant.ENCORD_UTF8);
			return true;
		} catch (Exception e) {
			File file = new File(targetFilePath);
			file.deleteOnExit();
			return false;
		}
	}

	/**
	 * 创建图片文件，文件内容写入成功，那么返回true；否则，如果文件存在删除文件，返回false
	 * 
	 * @param in
	 * @param targetFilePath
	 * @return
	 * @author Panda
	 */
	public static boolean createFileByBinary(final InputStream in, final String targetFilePath) {
		try {
			createFolderIfNotExists(targetFilePath);
			writeBinaryFile(in, targetFilePath);
			return true;
		} catch (Exception e) {
			File file = new File(targetFilePath);
			file.deleteOnExit();
			return false;
		}
	}

	/**
	 * 创建图片文件，文件内容写入成功，那么返回true；否则，如果文件存在删除文件，返回false
	 * 
	 * @param in
	 * @param targetFilePath
	 * @return
	 * @author Panda
	 */
	public static boolean createFileByBase64(final String base64FileContent, final String targetFilePath) {
		try {
			createFolderIfNotExists(targetFilePath);
			writeBase64File(base64FileContent, targetFilePath);
			return true;
		} catch (Exception e) {
			File file = new File(targetFilePath);
			file.deleteOnExit();
			return false;
		}
	}

	/**
	 * 将字符流写入文件 @param outFilePath @param outStr @param enCode @throws
	 * Exception @author Panda @throws IOException @throws
	 */
	public static void writeFile(final String outFilePath, final String outStr, final String enCode) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFilePath), enCode);
		writer.write(outStr);
		writer.flush();
		writer.close();
	}

	public static void writeFile(final InputStream in, final String outFilePath, final String enCode) throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(outFilePath), enCode);

		final InputStreamReader reader = new InputStreamReader(in, enCode);
		int i = 0;
		String inStr;
		final BufferedReader br = new BufferedReader(reader);
		while ((inStr = br.readLine()) != null) {
			writer.write(inStr);
			if (i > 0) {
				writer.write("\r\n");
			}
			i++;
		}
		reader.close();
		writer.flush();
		writer.close();
	}

	/**
	 * 字节流写入
	 * 
	 * @param in
	 * @param outFilePath
	 * @throws IOException
	 * @author Panda
	 */
	public static void writeBinaryFile(final InputStream in, final String outFilePath) throws IOException {
		byte[] dataBuf = new byte[2048];
		BufferedInputStream bis = new BufferedInputStream(in, 8192);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outFilePath), 8192);
		int count = 0;
		while ((count = bis.read(dataBuf)) != -1) {
			bos.write(dataBuf, 0, count);
		}
		bos.flush();
		bos.close();
	}

	/**
	 * Base64字符流
	 * 
	 * @param in
	 * @param outFilePath
	 * @throws IOException
	 * @author Panda
	 */
	public static void writeBase64File(final String base64FileContent, final String outFilePath) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		// Base64解码
		byte[] b = decoder.decodeBuffer(base64FileContent);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		// 生成jpeg图片 UUID.randomUUID().toString() +
		// String name = UUID.randomUUID().toString() + ".jpg";
		OutputStream out = new FileOutputStream(outFilePath);
		out.write(b);
		out.flush();
		out.close();

	}

	/**
	 * 输入流转换成字符串,应用于图片写入
	 * 
	 * @param in
	 * @param enCode
	 * @return
	 * @throws Exception
	 * @author Panda
	 */
	public static String convertInputStreamToString(final InputStream in, final String enCode) throws Exception {

		final InputStreamReader reader = new InputStreamReader(in, enCode);
		final StringBuffer sb = new StringBuffer();
		String inStr;
		final BufferedReader br = new BufferedReader(reader);
		int i = 0;
		while ((inStr = br.readLine()) != null) {
			sb.append(inStr);
			if (i > 0) {
				sb.append("\r\n");
			}
			i++;
		}

		reader.close();
		br.close();

		return sb.toString();

	}

	/**
	 * 根据时间规则生成文件名
	 * 
	 * @param originFile
	 * @return
	 * @author Panda
	 */
	public static String createFileNameByTimeRole(String originFile) {
		int dotIndex = originFile.lastIndexOf(".");
		String suffix;
		if (dotIndex >= 0) {
			suffix = originFile.substring(dotIndex);
		} else {
			suffix = ".txt";
		}
		StringBuffer newFileName = new StringBuffer();
		newFileName.append(DateHelper.getRandomNum()).append(suffix);
		return newFileName.toString();
	}

	/**
	 * 返回文件相对网络地址
	 * 
	 * @param fileNameList
	 * @return /{param1}/{param2}/param3...
	 * @author Panda
	 */
	public static String getFileUri(String... fileNameList) {
		StringBuffer fileUri = new StringBuffer();
		if (null != fileNameList && fileNameList.length > 0)
			for (String fileName : fileNameList) {
				fileUri.append("/");
				fileUri.append(fileName.replace(System.getProperty(SystemConstant.FILE_SEPARATOR), "/"));
			}
		return fileUri.toString();
	}

	public static void deleteFile(String oldFileUri, String fileUri, String projectName) {
		if (StringUtil.isNotBlank(oldFileUri)) {// 不是空的删除文件
			if (StringUtil.isNotBlank(fileUri) && oldFileUri.equals(fileUri))
				return;
			FilesUploadModel fileModel = new FilesUploadModel(projectName, oldFileUri);
			fileModel.deleteFile();
		}
	}

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 * @author Panda
	 */
	private static void createFolderIfNotExists(String filePath) {
		String folderPath = filePath.substring(0, filePath.lastIndexOf(CommonConstant.SYMBOL_SLASH));
		File target = new File(folderPath);
		if (!target.exists()) {
			String fatherPath = folderPath.substring(0, folderPath.lastIndexOf(CommonConstant.SYMBOL_SLASH));
			createFolderIfNotExists(fatherPath);
			target.mkdirs();
		}
	}

}
