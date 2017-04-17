package com.ztravel.product.timming.service;

public interface IProductTimmingService {

	/**
	 * 真旅行产品过期定时任务
	 * @throws Exception
	 */
	public void setAutoExpired() throws Exception;

}
