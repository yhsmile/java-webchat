package com.smile.webchat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smile.webchat.cache.RedisManager;
import com.smile.webchat.util.ListTranscoder;

@Service("redisService")
public class RedisService{
	
	private int expire = 0;
	
	@Autowired
	private RedisManager redisManager;
//	
//	@Autowired
//	private StringRedis stringRedis;
//	
//	@Autowired
//	private HashMapRedis hashMapRedis;

	public boolean exists(String key) {
		redisManager.initalPool();
		return redisManager.exists(key);
	}

	public boolean exists(byte[] key) {
		redisManager.initalPool();
		return redisManager.exists(key);
	}

	public String get(String key) {
		redisManager.initalPool();
		return redisManager.get(key);
	}

	public byte[] get(byte[] key) {
		redisManager.initalPool();
		return redisManager.get(key);
	}

	public List<String> mget(String... keys) {
		redisManager.initalPool();
		return redisManager.mget(keys);
	}

	public List<byte[]> mget(byte[]... keys) {
		redisManager.initalPool();
		return redisManager.mget(keys);
	}

	public Long del(String key) {
		redisManager.initalPool();
		return redisManager.del(key);
	}
	
	public Long del(byte[] key) {
		redisManager.initalPool();
		return redisManager.del(key);
	}

	public Long del(String[] keys) {
		redisManager.initalPool();
		return redisManager.del(keys);
	}

	public byte[] dump(String key) {
		redisManager.initalPool();
		return redisManager.dump(key);
	}

	
	public Long persist(String key) {
		redisManager.initalPool();
		return redisManager.persist(key);
	}

	
	public Long persist(byte[] key) {
		redisManager.initalPool();
		return redisManager.persist(key);
	}

	
	public Long pttl(String key) {
		redisManager.initalPool();
		return redisManager.pttl(key);
		
	}

	
	public Long pttl(byte[] key) {
		redisManager.initalPool();
		return redisManager.pttl(key);
	}

	
	public Long ttl(String key) {
		redisManager.initalPool();
		return redisManager.ttl(key);
	}

	
	public Long ttl(byte[] key) {
		redisManager.initalPool();
		return redisManager.ttl(key);
	}

	
	public String set(String key, String value) {
		redisManager.initalPool();
		return redisManager.set(key, value, expire);
	}

	
	public String set(byte[] key, byte[] value) {
		redisManager.initalPool();
		return redisManager.set(key, value, expire);
	}

	
	public Long append(String key, String value) {
		redisManager.initalPool();
		return redisManager.append(key, value);
	}

	
	public String getSet(String key, String newValue) {
		redisManager.initalPool();
		return redisManager.getSet(key, newValue);
	}

	
	public byte[] getSet(byte[] key, byte[] newValue) {
		redisManager.initalPool();
		return redisManager.getSet(key, newValue);
	}

	
	public Long incrBy(String key, long inc) {
		redisManager.initalPool();
		return redisManager.incrBy(key, inc);
	}

	
	public Long decrBy(String key, long decr) {
		redisManager.initalPool();
		return redisManager.decrBy(key, decr);
	}

	
	public String setBytes(String key, Object obj) {
		redisManager.initalPool();
		return redisManager.set(key.getBytes(), ListTranscoder.serialize(obj), expire);
	}
	
	
	public byte[] getBytes(String key) {
		redisManager.initalPool();
		return redisManager.get(key.getBytes());
	}

	
	public Long expire(String key, int time) {
		redisManager.initalPool();
		return redisManager.exprie(key, time);
	}
	
	
	public Long expire(byte[] key, int time) {
		redisManager.initalPool();
		return redisManager.exprie(key, time);
	}

	
//	public void setKey(String key, String value) {
//		stringRedis.setKey(key, value);
//	}
//
//	
//	public void setKey(String key, String value, long timeout) {
//		stringRedis.setKey(key, value, timeout);
//	}
//
//	
//	public String getKey(String key) {
//		return stringRedis.getKey(key);
//	}
//
//	
//	public void put(String key, Object hashKey, Object value) {
//		hashMapRedis.put(key, hashKey, value);
//	}
//
//	
//	public Object get(String key, Object hashKey) {
//		return hashMapRedis.get(key, hashKey);
//	}

	
	public Object getObject(String key) {
		byte[] bytes = this.getBytes(key);
		return ListTranscoder.deserialize(bytes);
	}

}
