package com.jiadoctor.service.doctor;

import java.util.List;

import com.jiadoctor.entity.doctor.JydLoginRecord;



public interface JydLoginRecordService {
    
	public void addJydLoginRecord(JydLoginRecord ntLoginRecord);
	
	public void deleteJydLoginRecord(Long id) throws Exception;
	
	public List<JydLoginRecord> getLoginRecordById(Long userId);
}
