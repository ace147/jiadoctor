/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.patient;

import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.entity.patient.JybUser;


/**
 * @author Ace
 * @version 1.0
 */
public interface JybUserService extends BaseService {

	public void addJybUser(JybUser jybUser,String ntRolesIds) throws Exception; //用户名密码注册
	
	public void addJybUserByMobile(JybUser jybUser) throws Exception; //手机验证码注册
	
	public void addJybUserByMobilePwd(JybUser jybUser) throws Exception; //手机密码注册

	public void modifyJybUser(JybUser jybUser,String ntRolesIds) throws Exception; //修改信息
	
	public void modifyBlock(String ids)throws Exception;//快速拉黑
	
	public void modifyPasswordJybUser(JybUser jybUser, String password) throws Exception; //修改密码
	
	public boolean verifyOldPassword(String username, String oldPassword); //旧密码验证
	
	public Pager retrieveJybUserList(JybUser jybUser, Integer pageNo, Integer pageSize); //查询列表（有无参数）
	
	public Pager retrieveJybUserList( Integer pageNo, Integer pageSize); //查询所有

	public JybUser retrieveJybUserByName(String username); //用户名查找
	
	public String loginByUsername(JybUser jybUser);  //用户名登录
	
	public String loginByMobile(JybUser jybUser);  //手机登录
	
	public JybUser isExistJybUserByMobile(String Mobile);
	
	public  JybUser retrieveJybUserById(Long id); //id查找用户
	
	public void removeJybUser(Long id);
	
	public JybUser isExistJybUserByNickname(String nickname);
	

}
