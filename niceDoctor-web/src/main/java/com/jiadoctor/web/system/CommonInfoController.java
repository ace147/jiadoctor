/**
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.ResponseBean;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.entity.system.CommonInfo;
import com.jiadoctor.service.system.ComminInfoService;


/**
 * 用户功能处理
 * 
 * @author Panda
 * @version 1.0
 */
@Controller
@RequestMapping("/common")
public class CommonInfoController {

	@Autowired
	@Qualifier("comminInfoService")
	protected ComminInfoService comminInfoService;

	@RequestMapping(value = "addCommonInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object addCommonInfo(CommonInfo CommonInfo) {
		try {
			comminInfoService.addComminInfo(CommonInfo);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("CommonInfoController.addCommonInfo", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}

	@RequestMapping(value = "delCommonInfo/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object delCommonInfo(@PathVariable Long id) {
		try {
			comminInfoService.removeComminInfo(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("CommonInfoController.delCommonInfo", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
		}
	}

	@RequestMapping(value = "updateCommonInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object updateCommonInfo(CommonInfo CommonInfo) {
		try {
			comminInfoService.modifyComminInfo(CommonInfo);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("CommonInfoController.updateCommonInfo", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	@RequestMapping(value = "getCommonInfo", method = RequestMethod.GET)
	@ResponseBody
	public Object getCommonInfo() {
		try {
			@SuppressWarnings("unchecked")
			java.util.List<CommonInfo> resultList = comminInfoService.retrieveComminInfoAll();
			CommonInfo CommonInfo = null;
			if (CollectionUtil.isListNotEmpty(resultList))
				for (CommonInfo CommonInfo0 : resultList) {
					if (null != CommonInfo0.getStatus() && CommonConstant.STATUS_ONE.equals(CommonInfo0.getStatus()))
						CommonInfo = CommonInfo0;
				}
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, CommonInfo);
		} catch (Exception e) {
			LogUtil.logException("CommonInfoController.getCommonInfo", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "getCommonInfoAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getCommonInfoAll() {
		try {
			@SuppressWarnings("unchecked")
			java.util.List<CommonInfo> resultList = comminInfoService.retrieveComminInfoAll();
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, resultList);
		} catch (Exception e) {
			LogUtil.logException("CommonInfoController.getCommonInfo", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

}