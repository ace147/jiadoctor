package com.jiadoctor.service.patient;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.dao.patient.JybLoginRecordDao;
import com.jiadoctor.entity.patient.JybLoginRecord;


/**
 * @author Panda
 * @version 1.0
 */
@Service("jybLoginRecordService")
public class JybLoginRecordServiceImpl extends BaseServiceImpl implements JybLoginRecordService {
    

	@Autowired
	@Qualifier("jybLoginRecordDao")
	private JybLoginRecordDao loginRecordDao;
	
	@Override
	public void addJybLoginRecord(JybLoginRecord ntLoginRecord) {
			ntLoginRecord.setLoginTime(new Date());
			this.addObject(ntLoginRecord);
	}

	@Override
	public void deleteJybLoginRecord(Long id) throws Exception {
		JybLoginRecord jybLoginRecord0=(JybLoginRecord) this.retrieveObject(JybLoginRecord.class, id);	
         if(BeanUtil.isValid(jybLoginRecord0))
        	 throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
         this.removeObject(jybLoginRecord0);
	}

//	根据用户id查询该用户的最新20条登陆记录
	@SuppressWarnings("unchecked")
	public List<JybLoginRecord> getLoginRecordById(Long memberId) {
		return loginRecordDao.getJybLoginRecordById(memberId);
	}

}
