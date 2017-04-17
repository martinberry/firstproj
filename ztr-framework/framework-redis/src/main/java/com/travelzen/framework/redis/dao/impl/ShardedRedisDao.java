/**
 *
 */
package com.travelzen.framework.redis.dao.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisException;

import com.travelzen.framework.redis.dao.RedisDao;

/**
 * @author zuoning.shen
 *
 */
@Repository("shardedRedisDao")
public class ShardedRedisDao implements RedisDao {
    private static Logger logger = LoggerFactory.getLogger(ShardedRedisDao.class);
    public final static int DEFAULT_EXPIRE_TIME = 1800;

    @Resource
    private ShardedJedisPool shardedJedisPool;

    @Override
    public void set(String key, String value) {
        set(key, value, DEFAULT_EXPIRE_TIME);
    }

    @Override
    public String get(String key) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.get(key);
        }catch(Exception e){
            logger.error("Failed to get key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public void delete(String key) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            jedis.del(key);
        } catch (Exception e) {
            logger.error("Failed to delete key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public void set(String key, String value, int expireTime) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            jedis.setex(key, expireTime, value);
        }catch(Exception e){
            logger.error("Failed to setex key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

	@Override
	public Boolean exists(String key) {
		ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.exists(key);
        }catch(Exception e){
            logger.error("Failed to judge key exists: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
	}

	@Override
	public void persist(String key) {
		ShardedJedis shardedJedis = null;
        try{
        	shardedJedis = shardedJedisPool.getResource();
            shardedJedis.getShard(key).persist(key);
        } catch (Exception e) {
            logger.error("Failed to persist key: {}", key, e);
            shardedJedisPool.returnBrokenResource(shardedJedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(shardedJedis);
        }		
	}

    @Override
    public void lpush(String key, String... strings) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            jedis.lpush(key, strings);
        }catch(Exception e){
            logger.error("Failed to lpush key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public String rpop(String key) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.rpop(key);
        }catch(Exception e){
            logger.error("Failed to lpush key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
    }

    @Override
    public long llen(String key) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.llen(key);
        }catch(Exception e){
            logger.error("Failed to llen key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
    }

	@Override
	public long incr(String key) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.incr(key);
        }catch(Exception e){
            logger.error("Failed to incr key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
	}
	
	@Override
	public long decr(String key) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.decr(key);
        }catch(Exception e){
            logger.error("Failed to decr key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
	}

	@Override
	public Long setnx(String key, String value) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            return jedis.setnx(key, value);
        }catch(Exception e){
            logger.error("Failed to setnx key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
	}

	@Override
	public void expire(String key, int expireTime) {
        ShardedJedis jedis = null;
        try{
            jedis = shardedJedisPool.getResource();
            jedis.expire(key, expireTime);
        }catch(Exception e){
            logger.error("Failed to expire key: {}", key, e);
            shardedJedisPool.returnBrokenResource(jedis);
            throw new JedisException(e);
        }finally{
            shardedJedisPool.returnResource(jedis);
        }
	}
    

}
