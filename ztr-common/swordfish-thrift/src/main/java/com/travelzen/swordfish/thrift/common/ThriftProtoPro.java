package com.travelzen.swordfish.thrift.common;

/**
 * thrift协议 相关参数
 * @author liuzhuo
 *
 */
public class ThriftProtoPro {
	
	/**
	 * 线程数量
	 */
	private Integer threadCount;
	
	/**
	 * readbuffer大小
	 */
	private Long maxReadBufferBytes;

	public Integer getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(Integer threadCount) {
		this.threadCount = threadCount;
	}

	public Long getMaxReadBufferBytes() {
		return maxReadBufferBytes;
	}

	public void setMaxReadBufferBytes(Long maxReadBufferBytes) {
		this.maxReadBufferBytes = maxReadBufferBytes;
	}
	
	
	

}
