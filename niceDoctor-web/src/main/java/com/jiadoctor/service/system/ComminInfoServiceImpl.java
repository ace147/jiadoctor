/**
 * 
 * @Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.system;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.dao.BaseDao;
import com.jiadoctor.common.util.BeanUtil;
import com.jiadoctor.common.util.FileHandler;
import com.jiadoctor.entity.system.CommonInfo;


/**
 * @author Panda
 * @version 1.0
 */
@Service("comminInfoService")
public class ComminInfoServiceImpl implements ComminInfoService {

	@Autowired
	@Qualifier("baseDao")
	BaseDao baseDao;

	@Value("#{ configProperties['web.name'] }")
	private String projectName;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.ComminInfoService#addComminInfo(cc.messcat.
	 * entity.system.CommonInfo)
	 */
	@Override
	public void addComminInfo(CommonInfo commonInfo) {
		baseDao.save(commonInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.ComminInfoService#modifyComminInfo(cc.messcat.
	 * entity.system.CommonInfo)
	 */
	@Override
	public void modifyComminInfo(CommonInfo commonInfo) throws Exception {

		CommonInfo commonInfo0 = (CommonInfo) baseDao.get(CommonInfo.class, commonInfo.getId());

		FileHandler.deleteFile(commonInfo0.getCompanyLog(), commonInfo.getCompanyLog(), projectName);
		FileHandler.deleteFile(commonInfo0.getCompanyBackground(), commonInfo.getCompanyBackground(), projectName);

		commonInfo0 = BeanUtil.setBeanByOtherBeanWithoutNull(commonInfo0, commonInfo);

		baseDao.update(commonInfo0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.ComminInfoService#removeComminInfo(java.lang.
	 * Long)
	 */
	@Override
	public void removeComminInfo(Long id) {
		CommonInfo commonInfo = (CommonInfo) baseDao.get(CommonInfo.class, id);
		FileHandler.deleteFile(commonInfo.getCompanyLog(), null, projectName);
		FileHandler.deleteFile(commonInfo.getCompanyBackground(), null, projectName);
		baseDao.delete(commonInfo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cc.messcat.service.system.ComminInfoService#retrieveComminInfoById(java.
	 * lang.Long)
	 */
	@Override
	public CommonInfo retrieveComminInfoById(Long id) {
		return (CommonInfo) baseDao.get(CommonInfo.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cc.messcat.service.system.ComminInfoService#retrieveComminInfoAll()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List retrieveComminInfoAll() {
		return baseDao.getAll(CommonInfo.class);
	}

}
