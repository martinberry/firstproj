/**
 * 
 */
package com.ztravel.mediaserver.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Component;

import com.travelzen.framework.redis.client.RedisClient;

/**
 * @author zuoning.shen
 *
 */
@Component
public class InvalidImageIdCache {
    private static final String INVALID_IMAGE_ID_CACHE_KEY = "INVALID_IMAGE_ID_CACHE";

    private static final String MEDIA_CACHE_POLICY_REDIS = "redis";

    private static final String MEDIA_CACHE_POLICY_EHCACHE = "ehcache";

    private String mediaCachePolicy;

    private RedisClient redisClient;

    private MediaRedisCacheConfig invalidImageIdCacheConfig;

    private Cache invalidImageIdCache;
    
    public InvalidImageIdCache() {
    	
    }
    
    public InvalidImageIdCache(CacheManager cacheManager){
        this.mediaCachePolicy = MEDIA_CACHE_POLICY_EHCACHE;
        this.invalidImageIdCache = cacheManager.getCache("invalidImageIdCache");
    }
    
    public InvalidImageIdCache(MediaRedisCacheConfig invalidImageIdCacheConfig){
        this.mediaCachePolicy = MEDIA_CACHE_POLICY_REDIS;
        redisClient = RedisClient.getInstance();
        this.invalidImageIdCacheConfig = invalidImageIdCacheConfig;
    }

    public void set(String imageId) {
        if(MEDIA_CACHE_POLICY_REDIS.equals(mediaCachePolicy)) setRedisCache(imageId);
        if(MEDIA_CACHE_POLICY_EHCACHE.equals(mediaCachePolicy)) setEhcache(imageId);
    }
    
    private void setEhcache(String imageId) {
        invalidImageIdCache.put(new Element(imageId, "1"));
    }

    private synchronized void setRedisCache(String imageId) {
        long size = redisClient.llen(INVALID_IMAGE_ID_CACHE_KEY);
        redisClient.lpush(INVALID_IMAGE_ID_CACHE_KEY, imageId);
        if(size >= invalidImageIdCacheConfig.getCapacity()){
            redisClient.rpop(INVALID_IMAGE_ID_CACHE_KEY);
        }
    }

    public Object get(String imageId){
        if(MEDIA_CACHE_POLICY_REDIS.equals(mediaCachePolicy)) return getRedisCache(imageId);
        if(MEDIA_CACHE_POLICY_EHCACHE.equals(mediaCachePolicy)) return getEhcache(imageId);
        return null;
    }

    private Object getEhcache(String imageId) {
        return invalidImageIdCache.get(imageId).getObjectValue();
    }

    private Object getRedisCache(String imageId) {
        return null;
    }
}
