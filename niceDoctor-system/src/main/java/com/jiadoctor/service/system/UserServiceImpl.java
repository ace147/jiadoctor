/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.system;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jiadoctor.common.constant.CommonConstant;
import com.jiadoctor.common.model.Pager;
import com.jiadoctor.common.security.token.SecurityHandler;
import com.jiadoctor.common.service.BaseServiceImpl;
import com.jiadoctor.common.util.CollectionUtil;
import com.jiadoctor.common.util.StringUtil;
import com.jiadoctor.dao.system.RoleDao;
import com.jiadoctor.dao.system.UserDao;
import com.jiadoctor.entity.system.McUser;
import com.jiadoctor.entity.system.Permission;
import com.jiadoctor.entity.system.Role;


/**
 * 用户接口实现
 * 
 * @author Microcat
 * @version 1.0
 */
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;

	@Autowired
	@Qualifier("roleDao")
	RoleDao roleDao;

	public McUser retrieveUserById(Long id) {
		McUser user = (McUser) this.retrieveObject(McUser.class, id);
		if (user != null) {
			user.setPassword(null);
			user.setRoles(null);
		}
		return user;
	}

	public McUser retrieveUserByIdWithoutPermission(Long id) {
		McUser user = (McUser) this.retrieveObject(McUser.class, id);
		if (user != null) {
			user.setPassword(null);
			Set<Role> role = user.getRoles();
			if (null != role) {
				Iterator<Role> iterator = role.iterator();
				while (iterator.hasNext()) {
					iterator.next().setPermissions(null);
				}
			}
		}
		return user;
	}

	@Override
	public McUser retrieveUserByUsernameIgnorePassword(String username) {
		McUser user = userDao.getUserByUsername(username);
		if (user != null) {
			user.setPassword(null);
		}
		return user;
	}

	@Override
	public McUser retrieveUserByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
	
	/**
	 * 专用于ShiroRealm中获取用户，角色，权限
	 * @param username
	 * @return
	 */
	public McUser retrieveUserWithRoleByUsernames(String username) {
		McUser mcUser = userDao.getUserByUsername(username);
		Set<Role> roleSet = mcUser.getRoles();
		if(!CollectionUtil.isSetEmpty(roleSet)){
			for(Role role:roleSet){
				Set<Permission> permissionSet = role.getPermissions();
				if(!CollectionUtil.isSetEmpty(permissionSet))
					role.setPermissions(permissionSet);
			}
			mcUser.setRoles(roleSet);
		}
		return mcUser;
	}

	@Override
	public void addUser(McUser user, Long roleId) throws Exception {
		String encodePassword = SecurityHandler.encodePassword(user.getPassword(), user.getUsername());
		user.setPassword(encodePassword);
		if (StringUtil.isBlank(user.getStatus()))
			user.setStatus(CommonConstant.STATUS_ONE);

		Set<Role> roleSet = new HashSet<Role>();
		Role role1 = (Role) this.retrieveObject(Role.class, roleId);
		if (null == role1)
			throw new Exception(CommonConstant.MSG_ROLE_EXCEPTION);
		roleSet.add(role1);
		user.setRoles(roleSet);
		user.setLoginCount(0);
		this.addObject(user);
	}

	@Override
	public void modifyUser(McUser user, Long roleId) throws Exception {
		// 在同一个业务中（同个session），不能存在同ID实体进行更新操作！
		McUser user0 = (McUser) this.retrieveObject(McUser.class, user.getId());

		Set<Role> role = user0.getRoles();
		if (null != role) {
			Iterator<Role> iterator = role.iterator();
			if (!iterator.hasNext() || iterator.next().getId().intValue() != roleId.intValue()) {
				Set<Role> roleSet = new HashSet<Role>();
				Role role1 = (Role) this.retrieveObject(Role.class, roleId);
				if (null == role1)
					throw new NullPointerException(CommonConstant.MSG_ROLE_EXCEPTION);
				roleSet.add(role1);
				user0.setRoles(roleSet);
			}
		} else {
			Set<Role> roleSet = new HashSet<Role>();
			Role role1 = (Role) this.retrieveObject(Role.class, roleId);
			if (null == role1)
				throw new Exception(CommonConstant.MSG_ROLE_EXCEPTION);
			roleSet.add(role1);
			user0.setRoles(roleSet);
		}

		user0.setMobilePhone(user.getMobilePhone());
		user0.setNickName(user.getNickName());
		user0.setStatus(user.getStatus());
		user0.setEditor(userDao.getCurrentMcUser());
		user0.setEditTime(new Date());

		this.modifyObject(user0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cc.messcat.service.system.UserService#modifyPasswordUser(cc.messcat.
	 * entity.system.McUser, java.lang.String)
	 */
	@Override
	public void modifyPasswordUser(McUser user, String authPassword) throws Exception {

		McUser user0 = (McUser) this.retrieveObject(McUser.class, user.getId());

		if (null == user0)
			throw new Exception(CommonConstant.MSG_USER_NOEXIST_ERROR);

		if (StringUtil.isBlank(user.getPassword()))
			throw new Exception(CommonConstant.MSG_PASSWORD_FORMAT_ERROR);

		String encodePassword = SecurityHandler.encodePassword(user.getPassword(), user.getUsername());
		user0.setPassword(encodePassword);
		user0.setEditor(userDao.getCurrentMcUser());
		user0.setEditTime(new Date());

		this.modifyObject(user0);
	}

	@Override
	public void removeUser(Long id) {
		this.removeObject(McUser.class, id);
	}

	@Override
	public boolean verifyOldPassword(String username, String oldPassword) {

		if (StringUtil.isBlank(username)) {
			return false;
		}

		if (StringUtil.isBlank(oldPassword)) {
			return false;
		}

		McUser user = retrieveUserByUsername(username);
		if (user == null) {
			return false;
		}

		if (StringUtil.isBlank(user.getPassword())) {
			return false;
		}

		return SecurityHandler.verifyPassword(username, oldPassword, user.getPassword());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected List<McUser> retrieveUserAll() {
		List resultList = retrieveAllObjects(McUser.class);
		return (List<McUser>) resultList;
	}

	@SuppressWarnings("rawtypes")
	public Pager retrieveUserAll(Integer pageNo, Integer pageSize) {
		List list = retrieveUserAll();
		return getPager(pageNo, pageSize, list, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see cc.messcat.service.system.UserService#login()
	 */
	@Override
	public void modifyForLogin(String username) throws Exception {
		McUser mcUser = userDao.getUserByUsername(username);
		mcUser.setLastLogin(new Date());
		mcUser.setLoginCount(mcUser.getLoginCount() + 1);
		super.modifyObject(mcUser);
	}

}
