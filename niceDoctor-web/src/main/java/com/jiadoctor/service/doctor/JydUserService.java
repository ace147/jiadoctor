/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.service.doctor;

import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.service.BaseService;
import com.jiadoctor.entity.doctor.JydUser;


/**
 * @author Ace
 * @version 1.0
 */
public interface JydUserService extends BaseService {

	public void addJydUser(JydUser jydUser,String ntRolesIds) throws Exception; //用户名密码注册
	
	public void addJydUserByMobile(JydUser jydUser) throws Exception; //手机验证码注册
	
	public void addJydUserByMobilePwd(JydUser jydUser) throws Exception; //手机密码注册

	public void modifyJydUser(JydUser jydUser,String ntRolesIds) throws Exception; //修改信息
	
	public void modifydlock(String ids)throws Exception;//快速拉黑
	
	public void modifyPasswordJydUser(JydUser jydUser, String password) throws Exception; //修改密码
	
	public boolean verifyOldPassword(String username, String oldPassword); //旧密码验证
	
	public Pager retrieveJydUserList(JydUser jydUser, Integer pageNo, Integer pageSize); //查询列表（有无参数）
	
	public Pager retrieveJydUserList( Integer pageNo, Integer pageSize); //查询所有

	public JydUser retrieveJydUserByName(String username); //用户名查找
	
	public String loginByUsername(JydUser jydUser);  //用户名登录
	
	public String loginByMobile(JydUser jydUser);  //手机登录
	
	public JydUser isExistJydUserByMobile(String Mobile);
	
	public  JydUser retrieveJydUserById(Long id); //id查找用户
	
	public void removeJydUser(Long id);
	
	public JydUser isExistJydUserByNickname(String nickname);
	

}
