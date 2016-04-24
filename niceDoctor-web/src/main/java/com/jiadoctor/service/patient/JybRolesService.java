package com.jiadoctor.service.patient;

import java.util.List;

import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.entity.patient.JybAuthorities;
import com.jiadoctor.entity.patient.JybRoles;


public interface JybRolesService extends BaseService {

	/**
	 * 根据条件获取角色列表(分页)
	 */
	public Pager getJybRolesPager(int pageSize, int pageNo,JybRoles jybRoles);
	
	/**
	 * 根据条件获取角色列表(不分页)
	 */
	public List<JybRoles> getJybRolesByCondition(JybRoles jybRoles);
	
	/**
	 * 根据ID获取角色
	 */
	public JybRoles getJybRoles(Long id);
	
	/**
	 * 保存或更新JybRoles
	 */
	public JybRoles addOrModify(JybRoles jybRoles,String jybAuthoritiesIds);
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	public List<JybAuthorities> getJybAuthoritiesByCondition(JybAuthorities jybAuthorities);
}
