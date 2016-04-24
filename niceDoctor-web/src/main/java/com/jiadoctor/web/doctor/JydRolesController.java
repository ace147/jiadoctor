/**
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.doctor;

import java.util.List;

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
import com.jiadoctor.entity.doctor.JydAuthorities;
import com.jiadoctor.entity.doctor.JydRoles;
import com.jiadoctor.service.doctor.JydRolesService;
import com.jiadoctor.vo.JydRolesVo;


/**
 * 前端用户权限管理
 * 
 */
@Controller
@RequestMapping("/jydRoles")
public class JydRolesController {

	@Autowired
	@Qualifier("jydRolesService")
	protected JydRolesService jydRolesService;

	/**
	 * 根据条件获取角色列表(分页)
	 */
	@RequestMapping(value = "/getJydRolesPager", method = RequestMethod.GET)
	@ResponseBody
	public Object getJydRolesPager(int pageSize, int pageNo,JydRoles jydRoles) {
		try {
			Pager pager = jydRolesService.getJydRolesPager(pageSize, pageNo, jydRoles);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,pager);
		} catch (Exception e) {
			LogUtil.logException("JydRolesController.getJydRolesPager", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	
	/**
	 * 根据条件获取角色列表(不分页)
	 */
	@RequestMapping(value = "/getJydRolesByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Object getJydRolesByCondition(JydRoles jydRoles) {
		try {
			List<JydRoles> jydRolesList = jydRolesService.getJydRolesByCondition(jydRoles);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jydRolesList);
		} catch (Exception e) {
			LogUtil.logException("JydRolesController.getJydRolesByCondition", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 根据ID获取角色信息，并且获取权限列表（提供给前端调用）
	 */
	@RequestMapping(value = "/getJydRolesAndJydAuthorities", method = RequestMethod.GET)
	@ResponseBody
	public Object getJydRolesAndJydAuthorities(Long id,JydAuthorities jydAuthorities) {
		try {
			JydRolesVo jydRolesVo = new JydRolesVo();
			if(id != null){
				JydRoles jydRoles = jydRolesService.getJydRoles(id);
				jydRolesVo.setJydRoles(jydRoles);
			}
			List<JydAuthorities> jydAuthoritiesList = jydRolesService.getJydAuthoritiesByCondition(jydAuthorities);
			jydRolesVo.setJydAuthorities(jydAuthoritiesList);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jydRolesVo);
		} catch (Exception e) {
			LogUtil.logException("JydRolesController.getJydRolesAndJydAuthorities", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 根据ID获取角色信息
	 */
	@RequestMapping(value = "/getJydRoles/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getJydRoles(@PathVariable Long id) {
		try {
			JydRoles jydRoles = jydRolesService.getJydRoles(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jydRoles);
		} catch (Exception e) {
			LogUtil.logException("JydRolesController.getJydRoles", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 根据条件，获取权限列表
	 */
	@RequestMapping(value = "/getJydAuthoritiesByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Object getJydAuthoritiesByCondition(JydAuthorities jydAuthorities) {
		try {
			List<JydAuthorities> jydAuthoritiesList = jydRolesService.getJydAuthoritiesByCondition(jydAuthorities);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jydAuthoritiesList);
		} catch (Exception e) {
			LogUtil.logException("JydRolesController.getJydAuthoritiesByCondition", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 保存或更新JydRoles
	 */
	@RequestMapping(value = "/addOrModifyJydRoles", method = RequestMethod.POST)
	@ResponseBody
	public Object addOrModifyJydRoles(JydRoles jydRoles,String jydAuthoritiesIds) {
		try {
			if(jydRolesService.addOrModify(jydRoles, jydAuthoritiesIds) != null){
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
			}else{
				return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
			}
		} catch (Exception e) {
			LogUtil.logException("JydRolesController.addOrModifyJydRoles", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}
	
}