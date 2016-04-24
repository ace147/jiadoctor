package com.jiadoctor.service.patient;

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
import com.jiadoctor.dao.patient.JybRolesDao;
import com.jiadoctor.entity.patient.JybAuthorities;
import com.jiadoctor.entity.patient.JybRoles;


@Service("jybRolesService")
public class JybRolesServiceImpl  extends BaseServiceImpl implements JybRolesService {

	@Autowired
	@Qualifier("jybRolesDao")
	private JybRolesDao jybRolesDao;
	
	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

	/**
	 * 根据条件获取角色列表(分页)
	 */
	public Pager getJybRolesPager(int pageSize, int pageNo,JybRoles jybRoles){
		List<JybRoles> jybRoles2 = jybRolesDao.getJybRolesByCondition(jybRoles);
		return getPager(pageNo, pageSize, jybRoles2, null);
	}
	
	/**
	 * 根据条件获取角色列表(不分页)
	 */
	public List<JybRoles> getJybRolesByCondition(JybRoles jybRoles){
		return jybRolesDao.getJybRolesByCondition(jybRoles);
	}
	
	/**
	 * 根据ID获取角色
	 */
	public JybRoles getJybRoles(Long id){
		JybRoles jybRoles = (JybRoles)baseDao.get(JybRoles.class, id);
		return jybRoles;
	}
	
	/**
	 * 保存或更新JybRoles
	 */
	public JybRoles addOrModify(JybRoles jybRoles,String jybAuthoritiesIds){
		if(BeanUtil.isValid(jybRoles)){
			//校验角色信息，名称不能为空且不能重复
			if(StringUtil.isNotBlank(jybRoles.getName())){
				List<JybRoles> jybRoles2 = jybRolesDao.getJybRolesByCondition(jybRoles);
				if(BeanUtil.isValid(jybRoles2)){
					//新增校验
					if(jybRoles.getId() == null){
						for(JybRoles ntRol : jybRoles2){
							if(jybRoles.getName().equals(ntRol.getName())){
								return null;
							}
						}
					}else{
						//编辑校验
						for(JybRoles ntRol : jybRoles2){
							if(jybRoles.getName().equals(ntRol.getName()) && jybRoles.getId().longValue() != ntRol.getId().longValue()){
								return null;
							}
						}
					}
				}
			}else{
				return null;
			}
			Set<JybAuthorities> set = new HashSet<JybAuthorities>();
			if(StringUtil.isNotBlank(jybAuthoritiesIds)){
				for(String id : jybAuthoritiesIds.split(",")){
					JybAuthorities jybAuthorities = (JybAuthorities)baseDao.get(JybAuthorities.class,Long.parseLong(id));
					if(BeanUtil.isValid(jybAuthorities)){
						set.add(jybAuthorities);
					}
				}
				jybRoles.setJybAuthorities(set);
			}
			if(BeanUtil.isValid(jybRoles.getId())){
				jybRolesDao.updateJybRoles(jybRoles);
			}else{
				baseDao.save(jybRoles);
			}
			return jybRoles;
		}
		return null;
	}
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	public List<JybAuthorities> getJybAuthoritiesByCondition(JybAuthorities jybAuthorities){
		return jybRolesDao.getJybAuthoritiesByCondition(jybAuthorities);
	}

}
