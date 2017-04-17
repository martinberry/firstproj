/**
 * 
 */
package com.ztravel.common.cache;

import com.travelzen.framework.redis.client.RedisClient;

/**
 * @author zuoning.shen
 *
 */
public class RedisCache {
    private static final RedisClient redisClient = RedisClient.getInstance();
    
    public static void set(CacheMapper object){
        redisClient.set(object.getRedisKey(), object);
    }
    
    public static <T extends CacheMapper> CacheMapper get(String key, Class<T> clazz){
        return redisClient.get(key, clazz);
    }
}
