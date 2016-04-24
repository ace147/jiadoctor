/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.system;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonView;
import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.model.ResponseBean;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.entity.system.McUser;
import com.jiadoctor.entity.system.McUser.WithoutPasswordView;
import com.jiadoctor.service.system.RoleService;
import com.jiadoctor.service.system.UserService;


/**
 * 用户功能处理
 * 
 * @author Microcat
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	@Qualifier("userService")
	protected UserService userService;

	@Autowired
	@Qualifier("roleService")
	protected RoleService roleService;

	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	public Object addUser(McUser user, Long roleId) {
		try {
			userService.addUser(user, roleId);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("UserController.addUser", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}

	@RequestMapping(value = "delUser/{id}", method = RequestMethod.DELETE)
	public Object delUser(@PathVariable Long id) {
		try {
			userService.removeUser(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_DELETE_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("UserController.delUser", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_DELETE_FAIL);
		}
	}

	@RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
	public Object updateUserInfo(McUser user, Long roleId) {
		try {
			userService.modifyUser(user, roleId);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("UserController.updateUserInfo", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FAIL);
		}
	}

	@RequestMapping(value = "updateUserSafe", method = RequestMethod.POST)
	public Object updateUserSafe(McUser user, @RequestParam String authPassword) {
		try {
			userService.modifyPasswordUser(user, authPassword);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_UPDATE_PASSWORD_SUCCESS);
		} catch (Exception e) {
			LogUtil.logException("UserController.updateUserSafe", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_UPDATE_PASSWORD_FAIL);
		}
	}

	@RequestMapping(value = "getUser/{id}", method = RequestMethod.GET)
	@JsonView(WithoutPasswordView.class)
	public Object getUser(@PathVariable Long id) {
		try {
			McUser user = userService.retrieveUserById(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, user);
		} catch (Exception e) {
			LogUtil.logException("UserController.getUser", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "editUser/{id}", method = RequestMethod.GET)
	@JsonFilter(value = "McFilter")
	public Object editUser(@PathVariable Long id, Integer pageNo, Integer pageSize) {
		try {
			McUser user = userService.retrieveUserByIdWithoutPermission(id);
			Pager page = roleService.retrieveRoleAll(pageNo, pageSize);
			java.util.Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("user", user);
			resultMap.put("role", page);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, resultMap);
		} catch (Exception e) {
			LogUtil.logException("UserController.getUser", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	@RequestMapping(value = "getUserAll", method = RequestMethod.GET)
	@JsonView(WithoutPasswordView.class)
	public Object getUserAll(Integer pageNo, Integer pageSize) {
		try {
			Pager pager = userService.retrieveUserAll(pageNo, pageSize);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS, pager);
		} catch (Exception e) {
			LogUtil.logException("UserController.getUserAll", e);
			e.printStackTrace();
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

}