package com.ztravel.order.client.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.CommonOrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.order.client.service.ICommonOrderClientService;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.entity.OrderCancelNeedToDoEntity;
import com.ztravel.order.po.CommonOrder;


/**
 *
 * @author wanhaofan
 *
 */
@Service
public class CommonOrderClientService implements ICommonOrderClientService{
	
	private static long payAmount = 0l ;
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(CommonOrderClientService.class);
	@Resource
	ICommonOrderDao commonOrderDao ;
	
	static{
		String payAmountConfig = TopsConfReader.getConfContent("ztr-payment/payment-server.properties", "ztr.payment.payAmount", ConfScope.R) ;
		if(StringUtils.isNotEmpty(payAmountConfig)){
			payAmount = Long.parseLong(payAmountConfig);
		}
	}
	
	@Override
	public void updateCommonOrder4Refunded(String commonOrderId, String paySerialNum) throws Exception {
		Map<String, Object> param = Maps.newHashMap() ;
		CommonOrder commonOrder = commonOrderDao.selectById(commonOrderId) ;
		param.put("wOrderId", commonOrderId) ;
		param.put("paySerialNum", paySerialNum) ;
		param.put("status", CommonOrderStatus.REFUNDED.toString()) ;
		param.put("stateChangeHistory", commonOrder.getStateChangeHistory() + "-->" + CommonOrderStatus.REFUNDED.toString()) ;
		param.put("wStatus", CommonOrderStatus.REFUNDING.toString()) ;
		int count = commonOrderDao.updateByMap(param) ;
		if(count != 1){
			throw new Exception("#updateCommonOrder4Refunded:can't update commonOrder by params:::" + param) ;
		}
	}

	@Override
	public void updateCommonOrder4Paid(String commonOrderId, PaymentType paymentType, String paySerialNum) throws Exception {
		Map<String, Object> param = Maps.newHashMap() ;
		CommonOrder commonOrder = commonOrderDao.selectById(commonOrderId) ;
		param.put("wOrderId", commonOrderId) ;
		param.put("status", CommonOrderStatus.PAID.toString()) ;
//		param.put("wStatus", CommonOrderStatus.UNPAY.toString()) ;
		param.put("stateChangeHistory", commonOrder.getStateChangeHistory() + "-->" + CommonOrderStatus.PAID.toString()) ;
		param.put("payType", paymentType.toString()) ;
		param.put("paySerialNum", paySerialNum) ;
		int count = commonOrderDao.updateByMap(param) ;
		if(count != 1){
			throw new Exception("#updateCommonOrder4Paid:can't update commonOrder by params:::" + param) ;
		}
	}
	
	/**
	 * @param originOrderNo
	 * @return
	 * @throws Exception
	 */
	@Override
	public CommonOrder selectByOriginOrderNo(String originOrderNo) throws Exception {
		return commonOrderDao.selectByOriginOrderNo(originOrderNo) ;
	}

	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public OrderCancelNeedToDoEntity orderCancelNeedToDo(String originOrderNo) throws Exception {
		OrderCancelNeedToDoEntity entity = new OrderCancelNeedToDoEntity() ;
		CommonOrder commonOrder = commonOrderDao.select4UpdateByOriginOrderNo(originOrderNo) ;
		LOGGER.info("orderCancelNeedToDo, commonOrder:::{}", TZBeanUtils.describe(commonOrder));
		if(commonOrder == null || CommonOrderStatus.CANCELED == commonOrder.getStatus()){
			return entity ;
		}
		CommonOrderType orderType = commonOrder.getType() ;
		CommonOrderStatus orderStatus = commonOrder.getStatus() ;

		if(orderType == CommonOrderType.OP_CONFIRM_REFUND && orderStatus == CommonOrderStatus.REFUNDED){
			entity.setAlreadyRefundPrice(payAmount != 0l ? payAmount : commonOrder.getPrice()) ;
			entity.setNeedTodo(true);
		}else if(orderType == CommonOrderType.OP_CONFIRM_REPAIR && orderStatus == CommonOrderStatus.PAID){
			entity.setNeedRefundTraceNum(commonOrder.getPaySerialNum());
			entity.setPaymentType(commonOrder.getPayType().toString()) ;
			entity.setNeedTodo(true);
		}
		Map<String, Object> param = Maps.newHashMap() ;
		param.put("wOrderId", commonOrder.getOrderId()) ;
		param.put("status", CommonOrderStatus.CANCELED.toString()) ;
		param.put("stateChangeHistory", commonOrder.getStateChangeHistory() + "-->" + CommonOrderStatus.CANCELED.toString()) ;
//		List<String> wStatusNotIn = new ArrayList<String>() ;
//		wStatusNotIn.add(CommonOrderStatus.AUDIT_UNPASS.toString()) ;
//		wStatusNotIn.add(CommonOrderStatus.REFUNDING.toString()) ;
//		wStatusNotIn.add(CommonOrderStatus.REFUNDED.toString()) ;
//		param.put("wStatusNotIn", wStatusNotIn) ;
		try{
			commonOrderDao.updateByMap(param) ;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return entity ;
	}

	@Override
	public int getUnpayConfirmOrderNum(String mid) throws Exception {
		Map<String, Object> params = Maps.newHashMap(); 
		params.put("memberId", mid);
		ImmutableList<CommonOrderStatus> statusList = ImmutableList.of(CommonOrderStatus.UNPAY);
		params.put("statuslist", statusList);
		return commonOrderDao.count(params);
	}
}
