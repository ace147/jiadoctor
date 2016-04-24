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
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.entity.website.WebSite;


/**
 * 用户功能处理
 * 
 * @author Panda
 * @version 1.0
 */
@Controller
@RequestMapping("/site")
public class SiteController {

	@Autowired
	@Qualifier("baseService")
	protected BaseService baseService;

	@RequestMapping(value = "addWebSite", method = RequestMethod.POST)
	@ResponseBody
	public Object addWebSite(WebSite webSite) {
		try {
			baseService.addObject(webSite);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("SiteController.addWebSite", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}

	@RequestMapping(value = "delWebSite/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object delWebSite(@PathVariable Long id) {
		try {
			baseService.removeObject(WebSite.class, id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("SiteController.delWebSite", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
		}
	}

	@RequestMapping(value = "updateWebSite", method = RequestMethod.POST)
	@ResponseBody
	public Object updateWebSite(WebSite webSite) {
		try {
			baseService.modifyObject(webSite);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("SiteController.updateWebSite", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	@RequestMapping(value = "getWebSite", method = RequestMethod.GET)
	@ResponseBody
	public Object getWebSite() {
		try {
			@SuppressWarnings("unchecked")
			java.util.List<WebSite> resultList = baseService.retrieveAllObjects(WebSite.class);
			WebSite webSite = null;
			if (CollectionUtil.isListNotEmpty(resultList))
				for (WebSite webSite0 : resultList) {
					if (null != webSite0.getSiteStatus() && CommonConstant.STATUS_ONE.equals(webSite0.getSiteStatus()))
						webSite = webSite0;
				}
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, webSite);
		} catch (Exception e) {
			LogUtil.logException("SiteController.getWebSite", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "getWebSiteAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getWebSiteAll() {
		try {
			@SuppressWarnings("unchecked")
			java.util.List<WebSite> resultList = baseService.retrieveAllObjects(WebSite.class);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, resultList);
		} catch (Exception e) {
			LogUtil.logException("SiteController.getWebSite", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

}