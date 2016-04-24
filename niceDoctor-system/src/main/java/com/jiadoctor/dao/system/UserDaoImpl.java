/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.dao.system;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.jiadoctor.common.constant.CommonRedisConstant;
import com.jiadoctor.common.dao.BaseHibernateDaoUtil;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.entity.system.McUser;


/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings("unchecked")
@Repository("userDao")
public class UserDaoImpl extends BaseHibernateDaoUtil implements UserDao {

	@Override
	public McUser getUserIgnorePassword(Long id) {
		McUser user = (McUser) getCurrentSession().get(McUser.class, id);
		user.setPassword(null);
		return user;
	}

	@Override
	public McUser getUserByUsername(String username) {
		Criteria criteria = super.getCurrentSession().createCriteria(McUser.class);
		criteria.add(Restrictions.eq("username", username));
		List<McUser> list = criteria.list();
		if (CollectionUtil.isListNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	public McUser getCurrentMcUser(){
		String username = (String) SecurityUtils.getSubject().getSession().getAttribute(CommonRedisConstant.REDIS_KEY_LOGIN_USER);
		return getUserByUsername(username);
	}

}