/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.web.system;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.jiadoctor.common.constant.CommonRedisConstant;
import com.jiadoctor.common.util.LogUtil;
import com.jiadoctor.service.system.UserService;


/**
 * 登录功能处理
 * 
 * @author Microcat
 * @version 1.0
 */
@Controller
public class LoginController {

	@Autowired
	@Qualifier("userService")
	protected UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/goLoginning", method = RequestMethod.POST)
	public String goLoginning(Map<String, Object> map, Model model, @RequestParam("mc_username") String username,
		@RequestParam("mc_password") String password) throws Exception {

		try {
			UsernamePasswordToken token = new UsernamePasswordToken(username, password);
			Subject subject = SecurityUtils.getSubject();
			subject.login(token);
			subject.getSession().setAttribute(CommonRedisConstant.REDIS_KEY_LOGIN_USER, username);
			userService.modifyForLogin(username);
			
		} catch (Exception e) {
			LogUtil.logException("LoginController.goLoginning", e);
			return "login";
		}

		return "index";
	}

}