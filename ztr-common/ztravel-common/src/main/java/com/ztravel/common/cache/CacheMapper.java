/**
 *
 */
package com.ztravel.common.cache;

/**
 * Cache mapper used for redis sharding. 
 * @author zuoning.shen
 *
 */
public interface CacheMapper {
    
    String getRedisKey();
}
