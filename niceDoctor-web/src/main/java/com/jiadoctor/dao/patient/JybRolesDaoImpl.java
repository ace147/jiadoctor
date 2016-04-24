/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.patient;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jiadoctor.common.dao.BaseHibernateDaoUtil;
import com.jiadoctor.common.util.StringUtil;
import com.jiadoctor.entity.patient.JybAuthorities;
import com.jiadoctor.entity.patient.JybRoles;


/**
 * @author Ace
 * @version 1.0
 */
@Repository("jybRolesDao")
public class JybRolesDaoImpl extends BaseHibernateDaoUtil implements JybRolesDao {

	/**
	 * 根据条件获取角色列表(不分页)
	 */
	@SuppressWarnings("unchecked")
	public List<JybRoles> getJybRolesByCondition(JybRoles jybRoles){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JybRoles.class);
		//条件
		if(jybRoles != null){
			if(StringUtil.isNotBlank(jybRoles.getName())){
				criteria.add(Restrictions.like("name", jybRoles.getName().trim(), MatchMode.ANYWHERE));
			}
			if(StringUtil.isNotBlank(jybRoles.getState())){
				criteria.add(Restrictions.eq("state", jybRoles.getState().trim()));
			}
		}
		List<JybRoles> result = criteria.list();
		return result;
	}
	
	/**
	 * 根据条件获取权限列表(不分页)
	 */
	@SuppressWarnings("unchecked")
	public List<JybAuthorities> getJybAuthoritiesByCondition(JybAuthorities jybAuthorities){
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JybAuthorities.class);
		//条件
		if(jybAuthorities != null){
			if(StringUtil.isNotBlank(jybAuthorities.getAuthoritiesType())){
				criteria.add(Restrictions.eq("authoritiesType", jybAuthorities.getAuthoritiesType().trim()));
			}
			if(StringUtil.isNotBlank(jybAuthorities.getName())){
				criteria.add(Restrictions.like("name", jybAuthorities.getName(), MatchMode.ANYWHERE));
			}
		}
		List<JybAuthorities> result = criteria.list();
		return result;
	}
	
	public void updateJybRoles(JybRoles jybRoles){
		Session session = getCurrentSession();
		session.merge(jybRoles);
	}
}
