/*
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
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.model.ResponseBean;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.entity.system.Role;
import com.jiadoctor.service.system.RoleService;


/**
 * 角色模块
 * 
 * @author Panda
 * @version 1.0
 */
@Controller
@RequestMapping("/role")
public class RoleController {

	@Autowired
	@Qualifier("roleService")
	protected RoleService roleService;

	@RequestMapping(value = "getRoleAll", method = RequestMethod.GET)
	@ResponseBody
	public Object getRoleAll(Integer pageNo, Integer pageSize) {
		try {
			Pager pager = roleService.retrieveRoleAll(pageNo, pageSize);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, pager);
		} catch (Exception e) {
			LogUtil.logException("RoleController.getRoleAll", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "getRole/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getRole(@PathVariable Long id) {
		try {
			Role role = roleService.retrieveRoleById(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, role);
		} catch (Exception e) {
			LogUtil.logException("RoleController.getRole param:" + String.valueOf(id), e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public Object addRole(Role role, Long[] permissionss) {
		try {
			roleService.addRole(role, permissionss);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
		} catch (Exception e) {
			this.getClass();
			LogUtil.logException("RoleController.addRole", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}

	@RequestMapping(value = "delRole/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Object delRole(@PathVariable Long id) {
		try {
			roleService.removeRole(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("RoleController.delRole", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
		}
	}

	@RequestMapping(value = "updateRole", method = RequestMethod.POST)
	@ResponseBody
	public Object updateRole(Role role, Long[] permissionss) {
		try {
			roleService.modifyRole(role, permissionss);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			LogUtil.logException("RoleController.updateRole", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_FAIL);
		}
	}

}