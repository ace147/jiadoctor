/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.redis;

import org.apache.log4j.Logger;

/**
 * @author Microcat
 * @version 1.0
 */
public class RedisLoader {

	private static final Logger logger = Logger.getLogger(RedisLoader.class);

	public static Object loadObject(RedisDao redisSession, String key, RedisDoCallBack callBack) {
		Object object = null;

		// Get from redis
		try {
			object = redisSession.getSession(key);
		} catch (Exception e) {
			StringBuilder builder = new StringBuilder();
			builder.append("RedisLoader.loadObject getSession key=");
			builder.append(key);
			builder.append(" exception = ");
			builder.append(e.toString());
			logger.error(builder.toString());
			object = callBack.doGetData();
			return object;
		}

		// It's not in redis then get from database
		if (object == null) {
			object = callBack.doGetData();

			// Put the new data into redis
			try {
				redisSession.putSession(key, object);
			} catch (Exception e) {
				StringBuilder builder = new StringBuilder();
				builder.append("RedisLoader.loadObject putSession key=");
				builder.append(key);
				builder.append(" exception = ");
				builder.append(e.toString());
				logger.error(builder.toString());
			}
		}

		return object;
	}

	public static void doPutRedis(RedisDao redisSession, Object obj, String function, String key, Logger logger) {
		try {
			redisSession.putSession(key, obj);
		} catch (Exception e) {
			StringBuilder builder = new StringBuilder(function);
			builder.append(" redis put session key=").append(key).append(" exception = ").append(e.toString());
			logger.error(builder.toString());
		}
	}

	public static void doDelRedis(RedisDao redisSession, String function, String key, Logger logger) {
		try {
			redisSession.deleteSession(key);
		} catch (Exception e) {
			StringBuilder builder = new StringBuilder(function);
			builder.append(" redis delete session key=").append(key).append(" exception = ").append(e.toString());
			logger.error(builder.toString());
		}
	}

}
