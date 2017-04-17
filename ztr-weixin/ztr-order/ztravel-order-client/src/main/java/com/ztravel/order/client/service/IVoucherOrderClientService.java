package com.ztravel.order.client.service;

import java.util.List;
import java.util.Map;

import com.ztravel.common.entity.CouponBookVo;
import com.ztravel.common.entity.VoucherCombineOrderInfo;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.order.po.VoucherCombineOrder;
import com.ztravel.order.po.VoucherOrder;


/**
 * order client 提供出去的接口
 * @author haofan.wan
 *
 */
public interface IVoucherOrderClientService {

	boolean cancelVoucherOrder(VoucherOrder voucherOrder) ;

	List<VoucherOrder> selectVoucherOrders4Cancel() ;

	boolean applyRefund(String couponItemId) throws Exception;

	boolean updateVoucherOrder4Paid(OrderPaidBean orderPaidBean) throws Exception;

	VoucherOrder selectById(String voucherOrderId) throws Exception;

	boolean updateVoucherOrder4Refunding(String voucherOrderId);

	List<VoucherOrder> selectVoucherOrderByVoucherCode(String voucherCode) throws Exception;

	boolean updateVoucherOrder4RefundFail(String voucherOrderId)
			throws Exception;

	List<VoucherOrder> selectByCombineOrderId(String combineOrderId) throws Exception;

	boolean updateCombineVoucherOrder4Refunding(String combineVoucherId,
			String voucherOrderId) throws Exception;

	boolean updateCombineVoucherOrder4RefundFail(String combineOrderId)
			throws Exception;

	boolean isVoucherOrderRefunded(String voucherOrderId) throws Exception;

	boolean canBuy(String memberId, String couponId, int limtNum, int orderNum);

    List<String> selectVoucherCodeByMap(Map map) throws Exception;

	List<VoucherCombineOrder> selectVoucherCombineOrders4Cancel();

	boolean cancelVoucherCombineOrder(String voucherCombineOrderId) throws Exception;

	long getCouponAmountByVoucherOrderId(String voucherOrderId) throws Exception;

	Map<String,Object> applyVoucherOrder(CouponBookVo couponBookVo) throws Exception;

	boolean insertVoucherCombineOrder(VoucherCombineOrder voucherCombineOrder) throws Exception;

    VoucherCombineOrder selectCombineOrderByCombineOrderId(String voucherOrderId) throws Exception;

    boolean updateVoucherOrder4Refunded(String voucherOrderId, long refundAmount)
			throws Exception;

	boolean updateCombineVoucherOrder4Refunded(String combineVoucherId,
			long refundAmount) throws Exception;

	VoucherCombineOrder selectCombineOrderById(String combineOrderId)
			throws Exception;

    VoucherCombineOrderInfo selectCombineOrderInfoByCombineOrderId(String voucherOrderId) throws Exception;

}
