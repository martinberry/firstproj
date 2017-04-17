package com.ztravel.paygate.wx.service;

public interface IWxPayNotifySyncRetryService {
	
	/**
	 * 新增任务(方法内处理异常  不要抛出)
	 */
	public void insertTask();
	
	/**
	 * 查询待执行任务
	 */
	public void searchToProceedTask();
	
	/**
	 * 执行任务
	 */
	public void proceedTask();

}
