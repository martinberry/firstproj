/**
 * 
 */
package com.ztravel.mediaserver.cache;

import java.util.Arrays;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.mediaserver.db.projo.Image;
import com.ztravel.mediaserver.db.projo.Media;

/**
 * @author zuoning.shen
 *
 */
public class ImageCache {
    private static final String IMAGE_ID_CACHE_KEY = "IMAGE_ID_CACHE";
    
    private static final String IMAGE_CACHE_PREFIX = "IMAGE_CACHE_";
    
    private static final String MEDIA_CACHE_POLICY_REDIS = "redis";
    
    private static final String MEDIA_CACHE_POLICY_EHCACHE = "ehcache";
    
    private String mediaCachePolicy;
    
    private RedisClient redisClient;
    
    private MediaRedisCacheConfig imageCacheConfig;
    
    private Cache imageCache;
    
    public ImageCache(CacheManager cacheManager){
        this.mediaCachePolicy = MEDIA_CACHE_POLICY_EHCACHE;
        this.imageCache = cacheManager.getCache("imageCache");
    }
    
    public ImageCache(MediaRedisCacheConfig imageCacheConfig){
        this.mediaCachePolicy = MEDIA_CACHE_POLICY_REDIS;
        redisClient = RedisClient.getInstance();
        this.imageCacheConfig = imageCacheConfig;
    }
    
    public void set(String imageId, SimpleMedia image){
        if(MEDIA_CACHE_POLICY_REDIS.equals(mediaCachePolicy)) setRedisCache(imageId, image);
        if(MEDIA_CACHE_POLICY_EHCACHE.equals(mediaCachePolicy)) setEhcache(imageId, image);
    }
    
    public SimpleMedia get(String imageId){
        if(MEDIA_CACHE_POLICY_REDIS.equals(mediaCachePolicy)) return getRedisCache(imageId);
        if(MEDIA_CACHE_POLICY_EHCACHE.equals(mediaCachePolicy)) return getEhcache(imageId);
        return null;
    }
    
    private synchronized void setRedisCache(String imageId, SimpleMedia image){
        long size = redisClient.llen(IMAGE_ID_CACHE_KEY);
        redisClient.lpush(IMAGE_ID_CACHE_KEY, imageId);
        redisClient.set(IMAGE_CACHE_PREFIX + imageId, image);
        if(size >= imageCacheConfig.getCapacity()){
            String imageIdToBeRemoved = redisClient.rpop(IMAGE_ID_CACHE_KEY);
            redisClient.delete(IMAGE_CACHE_PREFIX + imageIdToBeRemoved);
        }
    }
    
    private void setEhcache(String imageId, SimpleMedia image){
        imageCache.put(new Element(imageId, image));
    }
    
    private SimpleMedia getRedisCache(String imageId){
        SimpleMedia image = redisClient.get(IMAGE_CACHE_PREFIX + imageId, SimpleMedia.class);
        setRedisCache(imageId, image);
        return image;
    }
    
    private SimpleMedia getEhcache(String imageId){
    	
    	if(null == imageCache.get(imageId)) {
    		return null;
    	}
        return (SimpleMedia) imageCache.get(imageId).getObjectValue();
    }
}
