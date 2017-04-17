package com.travelzen.framework.redis.client;

import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.ShardedJedisPool;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.travelzen.framework.redis.converter.DateTimeSerializer;
import com.travelzen.framework.redis.dao.RedisDao;

public class RedisClient {
	private ApplicationContext app;
	private RedisDao shardedRedisDao;
	private Gson gson;

	// singleton
	private static class SingletonHolder {
		static final RedisClient INSTANCE = new RedisClient();
		static {
			INSTANCE.init();
		}
	}

	public static RedisClient getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void init() {
		app = new ClassPathXmlApplicationContext("spring/redis.xml");
		shardedRedisDao = (RedisDao) app.getBean("shardedRedisDao");
		final GsonBuilder builder = new GsonBuilder()
	       .registerTypeAdapter(DateTime.class, new DateTimeSerializer());
	    this.gson = builder.create();
	}

	public void set(String key, Object object) {
	    String json = gson.toJson(object);
	    shardedRedisDao.set(key, json);
	}

	public void set(String key, Object object, int expireTime) {
	    String json = gson.toJson(object);
	    shardedRedisDao.set(key, json, expireTime);
	}

	public void set(String key, String object, int expireTime) {
	    shardedRedisDao.set(key, object, expireTime);
	}

	public void setNoJSON(String key, Object o) {
	    shardedRedisDao.set(key, o.toString());
	}

	public void setNoJSON(String key, Object o, int expireTime) {
	    shardedRedisDao.set(key, o.toString(), expireTime);
	}

	public <T> T get(String key, Class<T> clazz) {
	    String json = shardedRedisDao.get(key);
	    if(StringUtils.isNotBlank(json)) return gson.fromJson(json, clazz);
		return null;
	}

	public String get(String key) {
	    return shardedRedisDao.get(key);
    }

	public void delete(String key) {
	    shardedRedisDao.delete(key);
	}

	public Boolean exists(String key) {
	    return shardedRedisDao.exists(key);
	}

	public ShardedJedisPool getShardedJedisPool(){
		return shardedRedisDao.getShardedJedisPool();
	}

    public void persist(String key){
    	shardedRedisDao.persist(key);
    }

    public void lpush(String key, String... strings){
        shardedRedisDao.lpush(key, strings);
    }

    public String rpop(String key){
        return shardedRedisDao.rpop(key);
    }

    public long llen(String key){
        return shardedRedisDao.llen(key);
    }

    public long incr(String key) {
    	return shardedRedisDao.incr(key);
    }
    
    public long decr(String key) {
    	return shardedRedisDao.decr(key);
    }

    public Long setnx(String key, String value) {
    	return shardedRedisDao.setnx(key, value);
    }

    public void expire(String key , int expireTime) {
    	shardedRedisDao.expire(key, expireTime);
    }
}
