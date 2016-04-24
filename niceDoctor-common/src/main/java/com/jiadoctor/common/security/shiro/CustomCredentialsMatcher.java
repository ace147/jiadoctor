/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.security.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.jiadoctor.common.security.token.SecurityHandler;
import com.jiadoctor.common.util.StringUtil;


/**
 * @author Microcat
 * @version 1.0
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

	@Override
	public boolean doCredentialsMatch(AuthenticationToken authcToken, AuthenticationInfo info) {
		String username = getUsername(authcToken);
		String inputPassword = getInputPassword(authcToken);
		String credentials = getUserPassword(info);

		if (StringUtil.isNotBlank(username) && StringUtil.isNotBlank(inputPassword) && StringUtil.isNotBlank(credentials)) {
			return SecurityHandler.verifyPassword(username, inputPassword, credentials);
		}
		return false;
	}

	private String getUsername(AuthenticationToken authcToken) {
		String username = String.valueOf(authcToken.getPrincipal());
		return username;
	}

	private String getInputPassword(AuthenticationToken authcToken) {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		return String.valueOf(token.getPassword());
	}

	private String getUserPassword(AuthenticationInfo info) {
		String credentials = String.valueOf(getCredentials(info));
		return credentials;
	}

}
