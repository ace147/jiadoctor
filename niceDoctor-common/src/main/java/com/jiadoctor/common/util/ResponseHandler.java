/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.Pager;


/**
 * 处理响应，
 * 下载文件的响应
 * 
 * @author Panda
 * @version 1.0
 */
public class ResponseHandler {
	
	public static ResponseEntity<byte[]> downloadFile(File file) throws IOException{
		
		// 转码，处理文件名乱码
		String fileName = new String(file.getName().getBytes(CommonConstant.ENCORD_UTF8), CommonConstant.ENCORD_ISO_8859_1);
		
		// 消息头
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment",fileName);
		
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Pager getPager(Integer pageNo, Integer pageSize,List list,Integer rowCount) {
		try {
			if(!BeanUtil.isValid(pageNo, pageSize))
				throw new NullPointerException();
			
		} catch (NullPointerException e) {
			pageNo = CommonConstant.CONSTANT_PAGER_NO;
			pageSize = CommonConstant.CONSTANT_PAGER_SIZE;
		}
		if (CollectionUtil.isListEmpty(list))
			return new Pager(pageSize, pageNo, 0, list);
		
		List resultList = new java.util.ArrayList();
		
		if(null==rowCount){
			
			rowCount = list.size();
			int fromIndex = (pageNo.intValue() - 1) * pageSize.intValue();
			int toIndex = fromIndex + pageSize.intValue();
			toIndex = toIndex > rowCount ? rowCount : toIndex;
			
			resultList.addAll(list.subList(fromIndex, toIndex));
		}else{
			resultList.addAll(list);
		}
		
		return new Pager(pageSize, pageNo, rowCount, resultList);
	}
	
}
