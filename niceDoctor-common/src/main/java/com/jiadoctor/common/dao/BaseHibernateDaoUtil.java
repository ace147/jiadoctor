/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.dao;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * 封装hibernate操作
 * 
 * @author Microcat
 * @version 1.0
 * @param <T>
 */
public class BaseHibernateDaoUtil{

	@Resource(name = "sessionFactory")
	private SessionFactory sessionFactory;

	/**
	 * Method of getCurrentSession will close session automatically and it's
	 * using current session transaction
	 */
	protected final Session getCurrentSession() {
		if (this.sessionFactory == null) {
			throw new HibernateException("Session Create Fail,SessionFactory is null!");
		}
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * Method of openSession need to close session and open another new session
	 */
	protected final Session getNewSession() {
		return this.sessionFactory.openSession();
	}
	
}
