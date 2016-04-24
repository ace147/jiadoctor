package com.jiadoctor.dao.doctor;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jiadoctor.common.dao.BaseHibernateDaoUtil;



@Repository("jydLoginRecordDao")
public class JydLoginRecordDaoImpl extends BaseHibernateDaoUtil implements JydLoginRecordDao {

//	根据用户id查询该病人用户的最新的20条登陆记录
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getJydLoginRecordById(Long jydUserId) {
		String hql = " from JydLoginRecord lr where 1=1 and lr.memberId.id = :jydUserId order by lr.id desc ";
		Session session = this.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("jydUserId", jydUserId);
		query.setFirstResult(0);    
	    query.setMaxResults(20); 
		List<Object> list = query.list();
		
		return list;
	}
}
