package com.jiadoctor.service.patient;

import java.util.List;

import com.jiadoctor.entity.patient.JybLoginRecord;


public interface JybLoginRecordService {
    
	public void addJybLoginRecord(JybLoginRecord ntLoginRecord);
	
	public void deleteJybLoginRecord(Long id) throws Exception;
	
	public List<JybLoginRecord> getLoginRecordById(Long userId);
}
