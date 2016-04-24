/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.dao.BaseDao;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.util.CollectionUtil;


/**
 * 公共接口开放
 * 
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
@Service("baseService")
public class BaseServiceImpl implements BaseService {

	@Autowired
	@Qualifier("baseDao")
	private BaseDao baseDao;

	@Override
	public void addObject(Object object) {
		baseDao.save(object);
	}

	@Override
	public void modifyObject(Object object) {
		baseDao.update(object);
	}

	@Override
	public void removeObject(Class c, Long id) {
		baseDao.deleteById(c, id);
	}

	@Override
	public void removeObject(Object object) {
		baseDao.delete(object);
	}

	@Override
	public List retrieveAllObjects(Class c) {
		return baseDao.getAll(c);
	}

	@Override
	public Object retrieveObject(Class c, Long id) {
		return baseDao.get(c, id);
	}
	
	@SuppressWarnings("unchecked")
	protected Pager getPager(Integer pageNo, Integer pageSize,List list,Integer rowCount) {
		try {
			checkPageValue(pageNo, pageSize);
		} catch (NullPointerException e) {
			pageNo = CommonConstant.CONSTANT_PAGER_NO;
			pageSize = CommonConstant.CONSTANT_PAGER_SIZE;
		}
		if (CollectionUtil.isListEmpty(list))
			return new Pager(pageSize, pageNo, 0, list);
		
		List resultList = new ArrayList();
		
		if(null==rowCount){
			
			rowCount = list.size();
			int fromIndex = (pageNo.intValue() - 1) * pageSize.intValue();
			int toIndex = fromIndex + pageSize.intValue();
			toIndex = toIndex > rowCount ? rowCount : toIndex;
			
			resultList.addAll(list.subList(fromIndex, toIndex));
		}else{
			resultList.addAll(list);
		}
		
		return new Pager(pageSize, pageNo, rowCount, resultList);
	}


	private static void checkPageValue(Integer... page) {
		for (Integer no : page) {
			if (null == no || no.intValue() < 0)
				throw new NullPointerException();
		}
	}
	
	@Override
	public List retrieveByExample(Object object) {
		return baseDao.findByExample(object);
	}

}
