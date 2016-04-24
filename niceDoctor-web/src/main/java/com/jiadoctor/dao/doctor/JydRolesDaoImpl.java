/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.doctor;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jiadoctor.common.dao.BaseHibernateDaoUtil;
import com.jiadoctor.common.util.StringUtil;
import com.jiadoctor.entity.doctor.JydAuthorities;
import com.jiadoctor.entity.doctor.JydRoles;


/**
 * @author Ace
 * @version 1.0
 */
@Repository("jydRolesDao")
public class JydRolesDaoImpl extends BaseHibernateDaoUtil implements JydRolesDao {

	/**
	 * 根据条件获取角色列表(不分页)
	 */
	@SuppressWarnings("unchecked")
	public List<JydRoles> getJydRolesByCondition(JydRoles jydRoles){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JydRoles.class);
		//条件
		if(jydRoles != null){
			if(StringUtil.isNotBlank(jydRoles.getName())){
				criteria.add(Restrictions.like("name", jydRoles.getName().trim(), MatchMode.ANYWHERE));
			}
			if(StringUtil.isNotBlank(jydRoles.getState())){
				criteria.add(Restrictions.eq("state", jydRoles.getState().trim()));
			}
		}
		List<JydRoles> result = criteria.list();
		return result;
	}
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	@SuppressWarnings("unchecked")
	public List<JydAuthorities> getJydAuthoritiesByCondition(JydAuthorities jydAuthorities){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JydAuthorities.class);
		//条件
		if(jydAuthorities != null){
			if(StringUtil.isNotBlank(jydAuthorities.getAuthoritiesType())){
				criteria.add(Restrictions.eq("authoritiesType", jydAuthorities.getAuthoritiesType().trim()));
			}
			if(StringUtil.isNotBlank(jydAuthorities.getName())){
				criteria.add(Restrictions.like("name", jydAuthorities.getName(), MatchMode.ANYWHERE));
			}
		}
		List<JydAuthorities> result = criteria.list();
		return result;
	}
	
	public void updateJydRoles(JydRoles jydRoles){
		Session session = getCurrentSession();
		session.merge(jydRoles);
	}
}
