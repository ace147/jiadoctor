package com.jiadoctor.web.filter;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import org.hibernate.metamodel.source.hbm.Helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.jiadoctor.common.util.DateHelper;
import com.jiadoctor.entity.doctor.JydUser;
import com.jiadoctor.entity.filter.FilterFilterOutAllExcept;
import com.jiadoctor.entity.patient.JybUser;


public class JsonFiltermmm {

	
	/**
	 * 病人登录记陆列表过滤
	 * @param obj
	 * @return
	 */
	public String jsonJybLoginRecordInfoFilter(List obj){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).setTimeZone(TimeZone.getTimeZone("GMT+8"));
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			set2.add("id");
			set2.add("username");
			set2.add("nickname");
		    
		    FilterProvider filters = new SimpleFilterProvider().addFilter("jsonSerializeAllExceptFilter",SimpleBeanPropertyFilter.serializeAllExcept(set))
		    		.addFilter("jsonFilterOutAllExceptFilter",SimpleBeanPropertyFilter.filterOutAllExcept(set2));  
		    objectMapper.addMixIn(JybUser.class,FilterFilterOutAllExcept.class);
		    
		    String jsonStr = objectMapper.writer(filters).writeValueAsString(obj);  
		    return jsonStr;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 医生登录记陆列表过滤
	 * @param obj
	 * @return
	 */
	public String jsonJydLoginRecordInfoFilter(List obj){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).setTimeZone(TimeZone.getTimeZone("GMT+8"));
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			set2.add("id");
			set2.add("username");
			set2.add("nickname");
		    
		    FilterProvider filters = new SimpleFilterProvider().addFilter("jsonSerializeAllExceptFilter",SimpleBeanPropertyFilter.serializeAllExcept(set))
		    		.addFilter("jsonFilterOutAllExceptFilter",SimpleBeanPropertyFilter.filterOutAllExcept(set2));  
		    objectMapper.addMixIn(JydUser.class,FilterFilterOutAllExcept.class);
		    
		    String jsonStr = objectMapper.writer(filters).writeValueAsString(obj);  
		    return jsonStr;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 病人/医生列表过滤
	 * @param obj
	 * @return
	 */
	public String jsonJybUserInfoInfoFilter(Object obj){
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
			Set<String> set = new HashSet<String>();
			Set<String> set2 = new HashSet<String>();
			
		    
		    FilterProvider filters = new SimpleFilterProvider().addFilter("jsonSerializeAllExceptFilter",SimpleBeanPropertyFilter.serializeAllExcept(set))
		    		.addFilter("jsonFilterOutAllExceptFilter",SimpleBeanPropertyFilter.filterOutAllExcept(set2));  
		    //objectMapper.addMixIn(JybUser.class,FilterFilterOutAllExcept.class);
		    
		    String jsonStr = objectMapper.writer(filters).writeValueAsString(obj);  
		    return jsonStr;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
}
