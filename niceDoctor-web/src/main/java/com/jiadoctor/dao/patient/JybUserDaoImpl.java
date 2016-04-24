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
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.entity.patient.JybUser;


/**
 * @author Ace
 * @version 1.0
 */
@Repository("jybUserDao")
public class JybUserDaoImpl extends BaseHibernateDaoUtil implements JybUserDao {

	/**
	 * @author Ace
	 * @since 2016-02-24
	 * 根据手机查看是否存在用户
	 */
	public JybUser isExistJybUserByMobile(String mobile) {
		Criteria criteria = super.getCurrentSession().createCriteria(JybUser.class);
		criteria.add(Restrictions.eq("mobile", mobile));
		criteria.add(Restrictions.eq("status","1" ));		
		@SuppressWarnings("unchecked")
		List<JybUser> list = criteria.list();
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
	public JybUser isExistJybUserByName(String username) {
		Criteria criteria = super.getCurrentSession().createCriteria(JybUser.class);
		criteria.add(Restrictions.eq("account", username));
		List<JybUser> list = criteria.list();
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
	public List retrieveJybUserAll(JybUser jybUser) {
		Session session = getCurrentSession();
		Criteria criteria = session.createCriteria(JybUser.class);
		//条件
		if(jybUser != null){
			if(BeanUtil.isValid(jybUser.getAccount())){
				criteria.add(Restrictions.like("username", jybUser.getAccount().trim(), MatchMode.ANYWHERE));
			}
			if(BeanUtil.isValid(jybUser.getMobile())){
				criteria.add(Restrictions.like("mobile", jybUser.getMobile().trim(), MatchMode.ANYWHERE));
			}
			if(BeanUtil.isValid(jybUser.getNickname())){
				criteria.add(Restrictions.like("nickname", jybUser.getNickname().trim(), MatchMode.ANYWHERE));
			}
			if(BeanUtil.isValid(jybUser.getIsBlock())){
				criteria.add(Restrictions.eq("isBlock",jybUser.getIsBlock()));
			}
		}
		List<JybUser> result = criteria.list();
		return result;
	}

	/**
	 * 查看昵称是否存在
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JybUser isExistJybUserByNickname(String nickname) {
		Criteria criteria = super.getCurrentSession().createCriteria(JybUser.class);
		criteria.add(Restrictions.eq("nickname", nickname));
		List<JybUser> list = criteria.list();
		if (CollectionUtil.isListNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

}
