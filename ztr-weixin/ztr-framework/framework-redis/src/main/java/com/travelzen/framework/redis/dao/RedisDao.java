/**
 *
 */
package com.travelzen.framework.redis.dao;

import redis.clients.jedis.ShardedJedisPool;


/**
 * @author zuoning.shen
 *
 */
public interface RedisDao {
    /**
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * @param <T>
     * @param key
     * @return
     */
    String get(String key);

    /**
     * @param key
     */
    void delete(String key);

    /**
     * save expire
     * 其中expireTime以秒为单位
     */
    void set(String key, String value, int expireTime);

    /**
     * key是否存在
     */
    Boolean exists(String key);

    /**
     * Retrieve sharded jedis pool
     * @return
     */
    ShardedJedisPool getShardedJedisPool();
    
    /**
     * 设置key 永不过期
     * @param key
     */
    void persist(String key);
    
    void lpush(String key, String... strings);
    
    String rpop(String key);
    
    long llen(String key);
    
    /**
     * 自增操作,用于实现分布式自增id
     * @param key
     * @return
     */
    long incr(String key);
    
    long decr(String key) ;
    
    /**
     * set if not exist
     * @param key
     * @return
     */
    Long setnx(String key, String value);
    
    /**
     * 设置超时时间
     * @param key
     * @param expireTime
     */
    void expire(String key, int expireTime);
    
}
