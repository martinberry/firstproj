package com.ztravel.product.timming.service;

public interface IActivityTimmingService {

	public void setActivityAutoExpired() throws Exception;

	public void setCouponAutoExpired() throws Exception;

	public void setSendingToFinished() throws Exception;

}
