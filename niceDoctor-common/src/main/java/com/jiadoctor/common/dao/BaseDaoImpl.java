/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.dao;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;
import org.springframework.stereotype.Repository;

import com.jiadoctor.common.dao.BaseHibernateDaoUtil;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.common.util.HQLUtil;


/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Repository("baseDao")
public class BaseDaoImpl extends BaseHibernateDaoUtil implements BaseDao {
	
	public final Object get(Class clazz, final long id) {
		return getCurrentSession().get(clazz, id);
	}

	public final List getAll(Class clazz) {
		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}

	public final void save(final Object entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	public final Object update(final Object entity) {
		getCurrentSession().update(entity);
		return entity;
	}

	public final void delete(final Object entity) {
		getCurrentSession().delete(entity);
	}

	public final void deleteById(Class clazz, final long entityId) {
		final Object entity = this.get(clazz, entityId);
		delete(entity);
	}
	
	// TODO
	/**
	 * 更新对象中非空属性 , 默认条件是Id
	 * 
	 * @param entity
	 * @author Panda
	 */
	public final void updateWithoutNull(Object entity) {
		java.util.Map<String, Object> attrs = BeanUtil.parserToMap(entity, true);
		java.util.Set<String> changeAttrNames = attrs.keySet();
		changeAttrNames.remove("id");
		java.util.Set<String> conditionAttrNames = new HashSet<String>();
		conditionAttrNames.add("id");
		String sql = HQLUtil.createUpdateHQL(entity.getClass().getName(), changeAttrNames, conditionAttrNames);
		executeHQLUpdate(sql, attrs);
	}

	/**
	 * 执行HQL查询语句
	 * 
	 * @param sql
	 * @param attrs
	 * @return
	 * @author Panda
	 */
	public final Object executeHQLQuery(String sql, java.util.Map<String, Object> attrs) {
		org.hibernate.Query sqlQuery = setSQLParameter(sql, getCurrentSession().createQuery(sql), attrs);
		return sqlQuery.list();
	}

	/**
	 * 执行HQL非查询语句
	 * 
	 * @param sql
	 *            eg.from User as o where o.id=:id
	 * @param attrs
	 * @return
	 * @author Panda
	 */
	public final Object executeHQLUpdate(String sql, java.util.Map<String, Object> attrs) {
		org.hibernate.Query sqlQuery = setSQLParameter(sql, getCurrentSession().createQuery(sql), attrs);
		return sqlQuery.executeUpdate();
	}

	/**
	 * 执行SQL查询语句
	 * 
	 * @param sql
	 * @param attrs
	 * @return
	 * @author Panda
	 */
	public final Object executeSQLQuery(String sql, java.util.Map<String, Object> attrs) {
		org.hibernate.SQLQuery sqlQuery = setSQLParameter(sql, getCurrentSession().createSQLQuery(sql), attrs);
		return sqlQuery.list();
	}

	/**
	 * 执行SQL非查询语句
	 * 
	 * @param sql
	 * @param attrs
	 * @return
	 * @author Panda
	 */
	public final Object executeSQLUpdate(String sql, java.util.Map<String, Object> attrs) {
		org.hibernate.SQLQuery sqlQuery = setSQLParameter(sql, getCurrentSession().createSQLQuery(sql), attrs);
		return sqlQuery.executeUpdate();
	}

	/**
	 * 构造SQLQuery对象,并赋值
	 * 
	 * @param sql
	 * @param sqlQuery
	 * @param attrs
	 * @return
	 * @author Panda
	 */
	private org.hibernate.SQLQuery setSQLParameter(String sql, org.hibernate.SQLQuery sqlQuery,
		java.util.Map<String, Object> attrs) {
		if (!CollectionUtil.isMapEmpty(attrs)) {
			if (!sql.contains("?")) {
				for (Entry<String, Object> attr : attrs.entrySet()) {
					sqlQuery.setParameter(attr.getKey(), attr.getValue());
				}
			} else {
				int i = 0;
				for (Entry<String, Object> attr : attrs.entrySet()) {
					sqlQuery.setParameter(i, attr.getValue());
					i++;
				}
			}
		}
		return sqlQuery;
	}

	/**
	 * 构造SQLQuery对象,并赋值
	 * 
	 * @param sql
	 * @param sqlQuery
	 * @param attrs
	 * @return
	 * @author Panda
	 */
	private org.hibernate.Query setSQLParameter(String sql, org.hibernate.Query hqlQuery, java.util.Map<String, Object> attrs) {
		if (!CollectionUtil.isMapEmpty(attrs)) {
			if (!sql.contains("?")) {
				for (Entry<String, Object> attr : attrs.entrySet()) {
					hqlQuery.setParameter(attr.getKey(), attr.getValue());
				}
			} else {
				int i = 0;
				for (Entry<String, Object> attr : attrs.entrySet()) {
					hqlQuery.setParameter(i, attr.getValue());
					i++;
				}
			}
		}
		return hqlQuery;
	}
	@Override
	public List<Object> findByExample(Object entity) {
		Example example = Example.create(entity)   
			  .excludeZeroes()        //排除值为0的属性    
			  .excludeProperty( "serialVersionUID" );  //排除 属性    
			List results = getCurrentSession().createCriteria(entity.getClass())   
			  .add(example)   
			  .list();   
			return results;
	}

	/**
	 * hql查询数量
	 * 
	 * @param sql
	 * @param conditions
	 * @return
	 */
	public int queryCount(final String hql, final Object... conditions) {
		Query query = getCurrentSession().createQuery(hql);
		if (conditions != null) {
			for (int i = 0; i < conditions.length; i++) {
				query.setParameter(i, conditions[i]);
			}
		}
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	public int queryCount(final String hql, final List conditions) {
		Query query = getCurrentSession().createQuery(hql);
		if (conditions != null) {
			for (int i = 0; i < conditions.size(); i++) {
				query.setParameter(i, conditions.get(i));
			}
		}
		Long count = (Long) query.uniqueResult();
		return count.intValue();
	}

	public int queryCountBySql(final String sql, final Object... conditions) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		if (conditions != null) {
			for (int i = 0; i < conditions.length; i++) {
				query.setParameter(i, conditions[i]);
			}
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}
	
	public int queryCountBySql2(final String sql, final List list) {
		SQLQuery query = getCurrentSession().createSQLQuery(sql);
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				query.setParameter(i, list.get(i));
			}
		}
		BigInteger count = (BigInteger) query.uniqueResult();
		return count.intValue();
	}
	
	/**
	 * hql分页查询
	 */
	public Pager getPagerByHql(int pageNo, int pageSize, String hql, Object... attrs) {
		List resultList = this.findPageObjects(hql, pageNo, pageSize, attrs);
		int rowCount = this.queryCount("select count(*) " + hql, attrs);
		return new Pager(pageSize, pageNo, rowCount, resultList);
	}

	public Pager getPagerByHql(int pageNo, int pageSize, String hql, List attrs) {
		List resultList = this.findPageObjects(hql, pageNo, pageSize, attrs);
		int rowCount = this.queryCount("select count(*) " + hql, attrs);
		return new Pager(pageSize, pageNo, rowCount, resultList);
	}

	/**
	 * hql分页查询
	 * 
	 * @return
	 */
	public <T> List<T> findPageObjects(String hql, int pageNo, int pageSize, final Object... conditions) {
		int pageStart = (pageNo - 1) * pageSize;
		Query query = getCurrentSession().createQuery(hql);
		if (null != conditions) {
			for (int i = 0; i < conditions.length; i++) {
				query.setParameter(i, conditions[i]);
			}
		}
		query.setFirstResult(pageStart);
		query.setMaxResults(pageSize);
		return query.list();
	}

	public <T> List<T> findPageObjects(String hql, int pageNo, int pageSize, final List conditions) {
		int pageStart = (pageNo - 1) * pageSize;
		Query query = getCurrentSession().createQuery(hql);
		if (null != conditions) {
			for (int i = 0; i < conditions.size(); i++) {
				query.setParameter(i, conditions.get(i));
			}
		}
		query.setFirstResult(pageStart);
		query.setMaxResults(pageSize);
		return query.list();
	}
}