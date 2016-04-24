/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.service.base;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.jiadoctor.common.security.shiro.CustomCredentialsMatcher;
import com.jiadoctor.common.util.StringUtil;
import com.jiadoctor.entity.system.McUser;
import com.jiadoctor.entity.system.Permission;
import com.jiadoctor.entity.system.Role;
import com.jiadoctor.service.system.UserService;


/**
 * @author Microcat
 * @version 1.0
 */
public class ShiroRealm extends AuthorizingRealm {

	@Autowired
	@Qualifier("userService")
	protected UserService userService;

	/**
	 * 授权信息
	 */
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		String username = (String) principals.getPrimaryPrincipal();
		McUser user = userService.retrieveUserWithRoleByUsernames(username);
		if (user == null) {
			return new SimpleAuthorizationInfo();
		}

		// 角色名、权限名的集合
		Set<String> roles = new HashSet<String>();
		Set<String> permissions = new HashSet<String>();

		// 将角色名和权限名放入shiro里
		for (Iterator<Role> roleItr = user.getRoles().iterator(); roleItr.hasNext();) {
			Role role = roleItr.next();
			if (role != null) {
				roles.add(role.getName());
				for (Iterator<Permission> perItr = role.getPermissions().iterator(); perItr.hasNext();) {
					Permission per = perItr.next();
					if (per != null) {
						permissions.add(per.getName());
					}
				}
			}
		}

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		authorizationInfo.addRoles(roles);
		authorizationInfo.addStringPermissions(permissions);
		return authorizationInfo;
	}

	/**
	 * 认证信息
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String username = token.getUsername();

		if (StringUtil.isBlank(username)) {
			throw new AuthenticationException("username is null");
		}

		McUser user = userService.retrieveUserByUsername(username);
		if (user == null) {
			throw new AuthenticationException("user is null");
		}

		if (StringUtil.isBlank(user.getUsername())) {
			throw new AuthenticationException("user's name is null");
		}

		if (StringUtil.isBlank(user.getPassword())) {
			throw new AuthenticationException("user's password is null");
		}

		if (!username.equals(user.getUsername())) {
			throw new AuthenticationException("user's name is not as the same as username that input");
		}

		return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
	}

	@PostConstruct
	public void initCredentialsMatcher() {

		// 该句作用是重写shiro的密码验证，让shiro使用自定义验证
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}
}