package com.ztravel.product.timming.service;

public interface IDestinationTimmingService {

	/**
	 * 更新redis中的可用目的地
	 * @throws Exception
	 */
	public void updateAvailableDestination() throws Exception;
	//更新签证产品可用目的地
	void updateVisaAvailableDestination() throws Exception;
	//更新当地游产品可用目的地
	void updateLocalAvailableDestination() throws Exception;

}
