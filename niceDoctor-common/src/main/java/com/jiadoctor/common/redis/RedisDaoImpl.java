/*
 * Copyright (c) 2015 Messcat. All rights reserved.
 * 
 */
package com.jiadoctor.common.redis;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import com.jiadoctor.common.util.SerializeUtil;


/**
 * @author Microcat
 * @version 1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RedisDaoImpl implements RedisDao {

	@Autowired
	protected RedisTemplate redisTemplate;

	@Override
	public Object getSession(final String keyValue) {
		return redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] key = redisTemplate.getStringSerializer().serialize(keyValue);
				if (connection.exists(key)) {
					byte[] value = connection.get(key);
					return SerializeUtil.unserialize(value);
				}
				return null;
			}
		});

	}

	@Override
	public void putSession(final String key, final Object value) {
		redisTemplate.execute(new RedisCallback<Object>() {
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(redisTemplate.getStringSerializer().serialize(key), SerializeUtil.serialize(value));
				return null;
			}
		});
	}

	@Override
	public void putListSession(final String key, final Object value) {
		redisTemplate.opsForList().leftPush(key, value);
	}

	@Override
	public List getListSession(final String keyValue) {
		final Long listSize = redisTemplate.opsForList().size(keyValue);
		List list = redisTemplate.opsForList().range(keyValue, 0, listSize);
		return list;
	}

	@Override
	public void popListSession(final String keyValue) {
		final Long listSize = redisTemplate.opsForList().size(keyValue);
		for (int i = 0; i < listSize; i++) {
			redisTemplate.opsForList().leftPop(keyValue);
		}
	}

	@Override
	public void setListSession(final String key, final int index, final Object value) {
		redisTemplate.opsForList().set(key, index, value);
	}

	@Override
	public Long listSessionSize(final String key) {
		return redisTemplate.opsForList().size(key);
	}

	@Override
	public void removeListSession(final String key, final long index, final Object value) {
		redisTemplate.opsForList().remove(key, index, value);
	}

	@Override
	public void putHashSession(final String key, final Map map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	@Override
	public Map getHashSession(final String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	@Override
	public void deleteSession(final String key) {
		this.redisTemplate.delete(key);
	}

}
