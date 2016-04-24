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
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.entity.doctor.JydUser;


/**
 * @author Ace
 * @version 1.0
 */
@Repository("jydUserDao")
public class JydUserDaoImpl extends BaseHibernateDaoUtil implements JydUserDao {

	/**
	 * @author Ace
	 * @since 2016-02-24
	 * 根据手机查看是否存在用户
	 */
	public JydUser isExistJydUserByMobile(String mobile) {
		Criteria criteria = super.getCurrentSession().createCriteria(JydUser.class);
		criteria.add(Restrictions.eq("mobile", mobile));
		criteria.add(Restrictions.eq("status","1" ));		
		@SuppressWarnings("unchecked")
		List<JydUser> list = criteria.list();
		if (CollectionUtil.isListNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * @author Ace
	 * @since 2016-02-25
	 * 根据用户名查看是否存在用户
	 */
	@SuppressWarnings("unchecked")
	public JydUser isExistJydUserByName(String username) {
		Criteria criteria = super.getCurrentSession().createCriteria(JydUser.class);
		criteria.add(Restrictions.eq("account", username));
		List<JydUser> list = criteria.list();
		if (CollectionUtil.isListNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

     
    
	/**
	 * 查询用户  参数为空默认查询全部
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List retrieveJydUserAll(JydUser jydUser) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JydUser.class);
		//条件
		if(jydUser != null){
			if(BeanUtil.isValid(jydUser.getAccount())){
				criteria.add(Restrictions.like("username", jydUser.getAccount().trim(), MatchMode.ANYWHERE));
			}
			if(BeanUtil.isValid(jydUser.getMobile())){
				criteria.add(Restrictions.like("mobile", jydUser.getMobile().trim(), MatchMode.ANYWHERE));
			}
			if(BeanUtil.isValid(jydUser.getNickname())){
				criteria.add(Restrictions.like("nickname", jydUser.getNickname().trim(), MatchMode.ANYWHERE));
			}
			if(BeanUtil.isValid(jydUser.getIsBlock())){
				criteria.add(Restrictions.eq("isBlock",jydUser.getIsBlock()));
			}
		}
		List<JydUser> result = criteria.list();
		return result;
	}

	/**
	 * 查看昵称是否存在
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JydUser isExistJydUserByNickname(String nickname) {
		Criteria criteria = super.getCurrentSession().createCriteria(JydUser.class);
		criteria.add(Restrictions.eq("nickname", nickname));
		List<JydUser> list = criteria.list();
		if (CollectionUtil.isListNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

}
