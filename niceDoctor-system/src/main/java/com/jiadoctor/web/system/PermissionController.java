/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.system;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.jiadoctor.entity.system.Permission;
import com.jiadoctor.service.system.PermissionService;


/**
 * 权限模块
 * 
 * @author Panda
 * @version 1.0
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Autowired
	@Qualifier("permissionService")
	protected PermissionService permissionService;

	@RequestMapping(value = "getPermissionAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getPermissionAll(Integer pageNo, Integer pageSize) {
		try {
			@SuppressWarnings("unchecked")
			java.util.List<Permission> list = (java.util.List<Permission>) permissionService.retrievePermissionAll();
			java.util.Map<String, java.util.List<Permission>> resultMap = new HashMap<String, java.util.List<Permission>>();

			if (CollectionUtil.isListEmpty(list))
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, resultMap);

			// 封装数据,按需要进行数据重组！当接口被多次访问时，则不建议进行数据重组--by Panda
			java.util.List<Permission> menu = new ArrayList<Permission>();
			java.util.List<Permission> front = new ArrayList<Permission>();
			java.util.List<Permission> opt = new ArrayList<Permission>();

			for (Permission p : list) {
				if (p.getPermissionType().equals(CommonConstant.PERMISSION_TYPE_MENU)) {
					menu.add(p);
					continue;
				}
				if (p.getPermissionType().equals(CommonConstant.PERMISSION_TYPE_FRONT)) {
					front.add(p);
					continue;
				}
				if (p.getPermissionType().equals(CommonConstant.PERMISSION_TYPE_OPT))
					opt.add(p);
			}

			resultMap.put("menu", menu);
			resultMap.put("front", front);
			resultMap.put("opt", opt);

			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, resultMap);
		} catch (Exception e) {
			LogUtil.logException("PermissionController.getPermissionAll", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "getPermission/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getPermission(@PathVariable Long id) {
		try {
			Permission Permission = permissionService.retrievePermissionById(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, Permission);
		} catch (Exception e) {
			LogUtil.logException("PermissionController.getPermission param:" + String.valueOf(id), e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "addPermission", method = RequestMethod.POST)
	@ResponseBody
	public Object addPermission(Permission permission) {
		try {
			permissionService.addPermission(permission);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.logException("PermissionController.addPermission", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}

	@RequestMapping(value = "delPermission/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object delPermission(@PathVariable Long id) {
		try {
			permissionService.removePermission(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("PermissionController.delPermission", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
		}
	}

	@RequestMapping(value = "updatePermission", method = RequestMethod.POST)
	@ResponseBody
	public Object updatePermission(Permission permission) {
		try {
			permissionService.modifyPermission(permission);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("PermissionController.updatePermission", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_FAIL);
		}
	}

	@RequestMapping(value = "menu", method = RequestMethod.GET)
	@ResponseBody
	public Object getMenu() {
		try {
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS,
				permissionService.retrievePermissionMenu());
		} catch (Exception e) {
			LogUtil.logException("PermissionController.updatePermission", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_FAIL);
		}
	}
}