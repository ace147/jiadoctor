package com.jiadoctor.service.doctor;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.dao.doctor.JydLoginRecordDao;
import com.jiadoctor.entity.doctor.JydLoginRecord;


/**
 * @author Panda
 * @version 1.0
 */
@Service("jydLoginRecordService")
public class JydLoginRecordServiceImpl extends BaseServiceImpl implements JydLoginRecordService {
    

	@Autowired
	@Qualifier("jydLoginRecordDao")
	private JydLoginRecordDao loginRecordDao;
	
	@Override
	public void addJydLoginRecord(JydLoginRecord ntLoginRecord) {
			ntLoginRecord.setLoginTime(new Date());
			this.addObject(ntLoginRecord);
	}

	@Override
	public void deleteJydLoginRecord(Long id) throws Exception {
		JydLoginRecord jydLoginRecord0=(JydLoginRecord) this.retrieveObject(JydLoginRecord.class, id);	
         if(BeanUtil.isValid(jydLoginRecord0))
        	 throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);
         this.removeObject(jydLoginRecord0);
	}

//	根据用户id查询该用户的最新20条登陆记录
	@SuppressWarnings("unchecked")
	public List<JydLoginRecord> getLoginRecordById(Long memberId) {
		return loginRecordDao.getJydLoginRecordById(memberId);
	}

}
