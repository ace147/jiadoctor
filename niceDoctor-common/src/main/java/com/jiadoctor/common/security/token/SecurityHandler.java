/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 */
package com.jiadoctor.common.security.token;

import java.security.MessageDigest;
import java.util.Random;

import org.apache.shiro.codec.Hex;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author Microcat
 * @version 1.0
 */
public class SecurityHandler {

	public static final int ENCODE_PASSWORD_TIMES = 222;

	/**
	 * 判断该密码是否符合加盐的逻辑
	 * 
	 * @param username
	 *            用户名
	 * @param password
	 *            密码（表现层传入的密码）
	 * @param passwordByMd5AddSalt
	 *            加密为MD5后再加盐的密码（持久层存储的加MD5加盐的密码）
	 * @return
	 */
	public static boolean verifyPassword(String username, String password, String passwordByMd5AddSalt) {
		String passwordByMd5 = encodePasswordToMD5(password, username);
		return verify(passwordByMd5, passwordByMd5AddSalt);
	}

	/**
	 * 先重复生成MD5多次，再给密码加盐（加MD5加盐的密码需存入持久层）
	 * 
	 * @param pwd
	 * @param user
	 * @param count
	 * @return
	 */
	public static String encodePassword(String pwd, String user) {

		// 重复加密
		String newPwd = encodePasswordToMD5(pwd, user);

		// 加盐
		newPwd = addSalt(newPwd);

		return newPwd;
	}

	/**
	 * （MD5加密）可针对一个密码重复生成MD5多次，提高彩虹表破解的难度，建议count小于5000
	 * 
	 * @param pwd
	 * @param user
	 * @return
	 */
	private static String encodePasswordToMD5(String pwd, String user) {
		return new Md5Hash(pwd, user, ENCODE_PASSWORD_TIMES).toString();
	}

	/**
	 * 构造随机加盐的密码
	 */
	private static String addSalt(String password) {
		Random r = new Random();
		StringBuilder sb = new StringBuilder(16);
		sb.append(r.nextInt(99999999)).append(r.nextInt(99999999));
		int len = sb.length();
		if (len < 16) {
			for (int i = 0; i < 16 - len; i++) {
				sb.append("0");
			}
		}
		String salt = sb.toString();
		password = md5Hex(password + salt);
		char[] cs = new char[48];
		for (int i = 0; i < 48; i += 3) {
			cs[i] = password.charAt(i / 3 * 2);
			char c = salt.charAt(i / 3);
			cs[i + 1] = c;
			cs[i + 2] = password.charAt(i / 3 * 2 + 1);
		}
		return new String(cs);
	}

	/**
	 * 获取十六进制字符串形式的MD5摘要
	 */
	@SuppressWarnings("static-access")
	private static String md5Hex(String src) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bs = md5.digest(src.getBytes());
			return new String(new Hex().encode(bs));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 校验密码是否正确
	 * 
	 */
	private static boolean verify(String passwordByMd5, String passwordByMd5AddSalt) {
		char[] cs1 = new char[32];
		char[] cs2 = new char[16];
		for (int i = 0; i < 48; i += 3) {
			cs1[i / 3 * 2] = passwordByMd5AddSalt.charAt(i);
			cs1[i / 3 * 2 + 1] = passwordByMd5AddSalt.charAt(i + 2);
			cs2[i / 3] = passwordByMd5AddSalt.charAt(i + 1);
		}
		String salt = String.valueOf(cs2);
		return md5Hex(passwordByMd5 + salt).equals(String.valueOf(cs1));
	}

	public static void main(String[] args) {
		String user = "microcat";
		String password1 = "888888";
		String password2 = "123456";

		String passwordMd5Salt1 = SecurityHandler.encodePassword(password1, user);
		String passwordMd5Salt11 = SecurityHandler.encodePassword(password1, user);
		String passwordMd5Salt2 = SecurityHandler.encodePassword(password2, user);
		String passwordMd5Salt22 = SecurityHandler.encodePassword(password2, user);

		System.out.println("passwordMd5Salt1: " + passwordMd5Salt1);
		System.out.println("passwordMd5Salt2: " + passwordMd5Salt2);

		System.out.println("password1 vs passwordMd5Salt1: " + SecurityHandler.verifyPassword(user, password1, passwordMd5Salt1));
		System.out.println("password1 vs passwordMd5Salt2: " + SecurityHandler.verifyPassword(user, password1, passwordMd5Salt2));
		System.out.println("password2 vs passwordMd5Salt1: " + SecurityHandler.verifyPassword(user, password2, passwordMd5Salt1));
		System.out.println("password2 vs passwordMd5Salt2: " + SecurityHandler.verifyPassword(user, password2, passwordMd5Salt2));
		System.out.println("password1 vs passwordMd5Salt11: " + SecurityHandler.verifyPassword(user, password1, passwordMd5Salt11));
		System.out.println("password1 vs passwordMd5Salt22: " + SecurityHandler.verifyPassword(user, password1, passwordMd5Salt22));
		System.out.println("password2 vs passwordMd5Salt11: " + SecurityHandler.verifyPassword(user, password2, passwordMd5Salt11));
		System.out.println("password2 vs passwordMd5Salt22: " + SecurityHandler.verifyPassword(user, password2, passwordMd5Salt22));
	}
}
