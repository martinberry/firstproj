package com.ztravel.order.client.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.order.po.OrderComContactor;



/**
 * order client 提供出去的接口
 * @author liuzhuo
 *
 */
public interface IOrderClientService {

	/**
	 * 提交订单
	 * @param productBookItem
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> applyOrder(ProductBookItem productBookItem) throws Exception;

	/**
	 *根据会员mid和产品id判断该会员是否已游玩该订单产品
	 * @param mid
	 * @param productId
	 * @return
	 * @throws SQLException
	 */
	Boolean orderProductIsGone(String mid, String productId) throws SQLException;

	ContactorInfo getContactorInfoByMemId(String memberId) throws SQLException;

	void sendWeiXinPayMsg(String orderId);

	void sendWeiXinSubmitMsg(String orderId);

	void sendTravelEndMsg(String orderId);

	CommonResponse cancleOrder(String orderId, String operator)   throws Exception;

	public Map<String,String> OrderSearchBycouponItem(String couponItem)throws Exception;

	List<OrderComContactor> getContactorsByMemId(String memberId)throws SQLException;
}
