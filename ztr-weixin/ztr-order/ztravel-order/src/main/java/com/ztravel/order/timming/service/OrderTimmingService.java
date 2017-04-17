package com.ztravel.order.timming.service;



public interface OrderTimmingService {

	/**
	 * 将订单刷新为出行中状态
	 * 定时器在出行日当天的凌晨00:00对已发出出行通知的订单进行订单刷新
	 * */
	void setAutoTravelling();

	/**
	 * 将订单状态刷新为已完成状态
	 * 定时器在归来日的24:00对出行中的订单状态进行更新
	 *
	 * */
	void setAutoCompleted();

	/**
	 *在归来日的44小时后发送评价提醒，即backDate后第二天20:00
	 */
	void setAutoNotice();
	/*
	 * 订单状态设置为取消,并记录操作记录
	 */
	void cancelOrder() throws Exception;

	void sendBackMsgGift()throws Exception;

	void sendBackMsgOutNotice()throws Exception;
	
	void cancelVoucherOrder() throws Exception;

}
