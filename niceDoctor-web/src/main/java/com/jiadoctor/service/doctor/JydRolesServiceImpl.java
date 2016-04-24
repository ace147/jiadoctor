package com.jiadoctor.service.doctor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.dao.BaseDao;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.StringUtil;
import com.jiadoctor.dao.doctor.JydRolesDao;
import com.jiadoctor.entity.doctor.JydAuthorities;
import com.jiadoctor.entity.doctor.JydRoles;


@Service("jydRolesService")
public class JydRolesServiceImpl  extends BaseServiceImpl implements JydRolesService {

	@Autowired
	@Qualifier("jydRolesDao")
	private JydRolesDao jydRolesDao;
	
	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

	/**
	 * 根据条件获取角色列表(分页)
	 */
	public Pager getJydRolesPager(int pageSize, int pageNo,JydRoles jydRoles){
		List<JydRoles> jydRoles2 = jydRolesDao.getJydRolesByCondition(jydRoles);
		return getPager(pageNo, pageSize, jydRoles2, null);
	}
	
	/**
	 * 根据条件获取角色列表(不分页)
	 */
	public List<JydRoles> getJydRolesByCondition(JydRoles jydRoles){
		return jydRolesDao.getJydRolesByCondition(jydRoles);
	}
	
	/**
	 * 根据ID获取角色
	 */
	public JydRoles getJydRoles(Long id){
		JydRoles jydRoles = (JydRoles)baseDao.get(JydRoles.class, id);
		return jydRoles;
	}
	
	/**
	 * 保存或更新JydRoles
	 */
	public JydRoles addOrModify(JydRoles jydRoles,String jydAuthoritiesIds){
		if(BeanUtil.isValid(jydRoles)){
			//校验角色信息，名称不能为空且不能重复
			if(StringUtil.isNotBlank(jydRoles.getName())){
				List<JydRoles> jydRoles2 = jydRolesDao.getJydRolesByCondition(jydRoles);
				if(BeanUtil.isValid(jydRoles2)){
					//新增校验
					if(jydRoles.getId() == null){
						for(JydRoles ntRol : jydRoles2){
							if(jydRoles.getName().equals(ntRol.getName())){
								return null;
							}
						}
					}else{
						//编辑校验
						for(JydRoles ntRol : jydRoles2){
							if(jydRoles.getName().equals(ntRol.getName()) && jydRoles.getId().longValue() != ntRol.getId().longValue()){
								return null;
							}
						}
					}
				}
			}else{
				return null;
			}
			Set<JydAuthorities> set = new HashSet<JydAuthorities>();
			if(StringUtil.isNotBlank(jydAuthoritiesIds)){
				for(String id : jydAuthoritiesIds.split(",")){
					JydAuthorities jydAuthorities = (JydAuthorities)baseDao.get(JydAuthorities.class,Long.parseLong(id));
					if(BeanUtil.isValid(jydAuthorities)){
						set.add(jydAuthorities);
					}
				}
				jydRoles.setJydAuthorities(set);
			}
			if(BeanUtil.isValid(jydRoles.getId())){
				jydRolesDao.updateJydRoles(jydRoles);
			}else{
				baseDao.save(jydRoles);
			}
			return jydRoles;
		}
		return null;
	}
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	public List<JydAuthorities> getJydAuthoritiesByCondition(JydAuthorities jydAuthorities){
		return jydRolesDao.getJydAuthoritiesByCondition(jydAuthorities);
	}

}
