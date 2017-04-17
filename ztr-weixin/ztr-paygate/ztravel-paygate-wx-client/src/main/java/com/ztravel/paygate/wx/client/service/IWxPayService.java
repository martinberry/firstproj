package com.ztravel.paygate.wx.client.service;

import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.WxPayResponse;

public interface IWxPayService {

	/**
	 * 微信统一下单接口
	 * @param requestParms
	 * @return
	 */
	public WxPayResponse unifiedOrder(String requestParms) throws Exception;

	/**
	 * 微信关闭订单
	 * @param orderId
	 * @return
	 */
	public boolean closeOrder(String orderId) throws Exception;

	/**
	 * 记录微信支付结果
	 * @param UnifieldOrderNotifyEntity
	 * @return
	 */
	public void placePayNotify(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) throws Exception;


	/**
	 * 查询订单是否支付成功接口
	 * @param requestParms
	 * @return trade_state
	 */
	public String  orderTradeStateQuery(String requestParms) throws Exception;

	/**
	 * 微信申请退款接口
	 * @param requestParms
	 * @return
	 */
	public WxPayResponse refundOrder(String requestParms) throws Exception;

}
