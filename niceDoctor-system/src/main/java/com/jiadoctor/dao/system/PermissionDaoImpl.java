/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.system;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jiadoctor.common.dao.BaseHibernateDaoUtil;
import com.jiadoctor.entity.system.Permission;


/**
 * @author Panda
 * @version 1.0
 */
@Repository("permissionDao")
public class PermissionDaoImpl extends BaseHibernateDaoUtil implements PermissionDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cc.messcat.dao.system.PermissionDao#find(java.lang.Long[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Permission> find(Long[] permissions) {
		if (null == permissions || permissions.length < 1)
			return null;
		StringBuffer hql = new StringBuffer();
		hql.append("from Permission as model where");
		for (int i = 0; i < permissions.length; i++) {
			hql.append(" model.id=");
			hql.append(permissions[i].intValue());
			hql.append(" or");
		}
		hql.delete(hql.length() - 2, hql.length());
		return (List<Permission>) super.getCurrentSession().createQuery(hql.toString()).list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Permission> findMenu(){
		String hql = "from Permission as model where model.permissionType=0 order by model.id asc";
		return (List<Permission>) super.getCurrentSession().createQuery(hql).list();
	}

}