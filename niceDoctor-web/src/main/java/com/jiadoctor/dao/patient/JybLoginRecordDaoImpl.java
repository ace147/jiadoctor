package com.jiadoctor.dao.patient;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.jiadoctor.common.dao.BaseHibernateDaoUtil;



@Repository("jybLoginRecordDao")
public class JybLoginRecordDaoImpl extends BaseHibernateDaoUtil implements JybLoginRecordDao {

//	根据用户id查询该病人用户的最新的20条登陆记录
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getJybLoginRecordById(Long jybUserId) {
		String hql = " from JybLoginRecord lr where 1=1 and lr.memberId.id = :jybUserId order by lr.id desc ";
		Session session = this.getCurrentSession();
		Query query = session.createQuery(hql);
		query.setParameter("jybUserId", jybUserId);
		query.setFirstResult(0);    
	    query.setMaxResults(20); 
		List<Object> list = query.list();
		
		return list;
	}
}
