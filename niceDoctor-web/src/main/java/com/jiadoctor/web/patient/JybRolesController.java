/**
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.patient;

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
import com.jiadoctor.entity.patient.JybAuthorities;
import com.jiadoctor.entity.patient.JybRoles;
import com.jiadoctor.service.patient.JybRolesService;
import com.jiadoctor.vo.JybRolesVo;


/**
 * 前端用户权限管理
 * 
 */
@Controller
@RequestMapping("/jybRoles")
public class JybRolesController {

	@Autowired
	@Qualifier("jybRolesService")
	protected JybRolesService jybRolesService;

	/**
	 * 根据条件获取角色列表(分页)
	 */
	@RequestMapping(value = "/getJybRolesPager", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybRolesPager(int pageSize, int pageNo,JybRoles jybRoles) {
		try {
			Pager pager = jybRolesService.getJybRolesPager(pageSize, pageNo, jybRoles);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,pager);
		} catch (Exception e) {
			LogUtil.logException("JybRolesController.getJybRolesPager", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}

	
	/**
	 * 根据条件获取角色列表(不分页)
	 */
	@RequestMapping(value = "/getJybRolesByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybRolesByCondition(JybRoles jybRoles) {
		try {
			List<JybRoles> jybRolesList = jybRolesService.getJybRolesByCondition(jybRoles);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jybRolesList);
		} catch (Exception e) {
			LogUtil.logException("JybRolesController.getJybRolesByCondition", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 根据ID获取角色信息，并且获取权限列表（提供给前端调用）
	 */
	@RequestMapping(value = "/getJybRolesAndJybAuthorities", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybRolesAndJybAuthorities(Long id,JybAuthorities jybAuthorities) {
		try {
			JybRolesVo jybRolesVo = new JybRolesVo();
			if(id != null){
				JybRoles jybRoles = jybRolesService.getJybRoles(id);
				jybRolesVo.setJybRoles(jybRoles);
			}
			List<JybAuthorities> jybAuthoritiesList = jybRolesService.getJybAuthoritiesByCondition(jybAuthorities);
			jybRolesVo.setJybAuthorities(jybAuthoritiesList);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jybRolesVo);
		} catch (Exception e) {
			LogUtil.logException("JybRolesController.getJybRolesAndJybAuthorities", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 根据ID获取角色信息
	 */
	@RequestMapping(value = "/getJybRoles/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybRoles(@PathVariable Long id) {
		try {
			JybRoles jybRoles = jybRolesService.getJybRoles(id);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jybRoles);
		} catch (Exception e) {
			LogUtil.logException("JybRolesController.getJybRoles", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 根据条件，获取权限列表
	 */
	@RequestMapping(value = "/getJybAuthoritiesByCondition", method = RequestMethod.GET)
	@ResponseBody
	public Object getJybAuthoritiesByCondition(JybAuthorities jybAuthorities) {
		try {
			List<JybAuthorities> jybAuthoritiesList = jybRolesService.getJybAuthoritiesByCondition(jybAuthorities);
			return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_FIND_SUCCESS,jybAuthoritiesList);
		} catch (Exception e) {
			LogUtil.logException("JybRolesController.getJybAuthoritiesByCondition", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_FIND_FAIL);
		}
	}
	
	/**
	 * 保存或更新JybRoles
	 */
	@RequestMapping(value = "/addOrModifyJybRoles", method = RequestMethod.POST)
	@ResponseBody
	public Object addOrModifyJybRoles(JybRoles jybRoles,String jybAuthoritiesIds) {
		try {
			if(jybRolesService.addOrModify(jybRoles, jybAuthoritiesIds) != null){
				return new ResponseBean(CommonConstant.SUCCESS_CODE_200, CommonConstant.MSG_ADD_SUCCESS);
			}else{
				return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
			}
		} catch (Exception e) {
			LogUtil.logException("JybRolesController.addOrModifyJybRoles", e);
			return new ResponseBean(CommonConstant.EXCEPTION_CODE_500, CommonConstant.MSG_ADD_FAIL);
		}
	}
	
}