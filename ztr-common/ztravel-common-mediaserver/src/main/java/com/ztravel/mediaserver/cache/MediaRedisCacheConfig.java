/**
 * 
 */
package com.ztravel.mediaserver.cache;

/**
 * @author zuoning.shen
 *
 */
public class MediaRedisCacheConfig {
	
    private long capacity;
    
    private long timeToLiveSeconds;
    
    public void setCapacity(long capacity) {
		this.capacity = capacity;
	}

    public long getCapacity() {
        return capacity;
    }
    
	public void setTimeToLiveSeconds(long timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
	}
	
    public long getTimeToLiveSeconds() {
        return timeToLiveSeconds;
    }

	public MediaRedisCacheConfig() {
    	
    }
    
    public MediaRedisCacheConfig(long capacity, long timeToLiveSeconds){
        this.capacity = capacity;
        this.timeToLiveSeconds = timeToLiveSeconds;
    }
}
