package com.jiadoctor.service.doctor;

import java.util.List;

import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.entity.doctor.JydAuthorities;
import com.jiadoctor.entity.doctor.JydRoles;


public interface JydRolesService extends BaseService {

	/**
	 * 根据条件获取角色列表(分页)
	 */
	public Pager getJydRolesPager(int pageSize, int pageNo,JydRoles jydRoles);
	
	/**
	 * 根据条件获取角色列表(不分页)
	 */
	public List<JydRoles> getJydRolesByCondition(JydRoles jydRoles);
	
	/**
	 * 根据ID获取角色
	 */
	public JydRoles getJydRoles(Long id);
	
	/**
	 * 保存或更新JydRoles
	 */
	public JydRoles addOrModify(JydRoles jydRoles,String jydAuthoritiesIds);
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	public List<JydAuthorities> getJydAuthoritiesByCondition(JydAuthorities jydAuthorities);
}
