package com.ztravel.paygate.wx.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.enums.GateType;
import com.ztravel.common.order.OrderPaidBean;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyResponse;
import com.ztravel.paygate.wx.converter.WxPayParmConvert;
import com.ztravel.paygate.wx.dao.IWxPayNotifyDao;
import com.ztravel.paygate.wx.service.IWxPayNotifyService;
import com.ztravel.paygate.wx.service.IWxPayNotifySyncRetryService;
import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.service.IThirdPaymentThriftService;


/**
 * 微信通知支付通知处理实现 需要保证
 *<p> 1 通知处理的幂等性
 *<p> 2 支付结果 与 订单状态之间的数据一致性
 * @author haofna.wan
 *
 */
@Service
public class WxPayNotifyServiceImpl implements IWxPayNotifyService{

	private static final Logger logger = LoggerFactory.getLogger(WxPayNotifyServiceImpl.class);

	@Resource
	IWxPayNotifyDao wxPayNotifyDao;

	@Resource(name="orderThriftClientServiceImpl")
	private IOrderThriftClientService orderThriftClientServiceImpl;

	@Resource
	private IThirdPaymentThriftService thirdPaymentThriftService;

	@Resource
	IWxPayNotifySyncRetryService wxPayNotifySyncRetryService;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor=RuntimeException.class)
	public UnifieldOrderNotifyResponse proceed(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		logger.info("wechat order pay notify:\n" + JSONObject.toJSONString(unifieldOrderNotifyEntity));

		UnifieldOrderNotifyResponse unifieldOrderNotifyResponse = null;

		boolean needNotify = preproceed(unifieldOrderNotifyEntity) ;

		if(needNotify){
			if(unifieldOrderNotifyEntity.getReturn_code().equals("SUCCESS")
					&& unifieldOrderNotifyEntity.getResult_code().equals("SUCCESS")) {
				try {
					wxPayNotifyDao.lock(unifieldOrderNotifyEntity);
					OrderPaidBean orderPayedBean = WxPayParmConvert.buildOrderPaidBean(unifieldOrderNotifyEntity);
					PayResultBean payResult = new PayResultBean() ;
					payResult.setOrderNum(orderPayedBean.getOrderId());
					payResult.setTraceNum(orderPayedBean.getTraceNum());
					payResult.setBankPaymentTime(orderPayedBean.getBankPaymentTime());
					payResult.setGateType(GateType.WeChatpay.getGateCode());
					if(unifieldOrderNotifyEntity.getTotal_fee() != 0){		
						payResult.setAmount(unifieldOrderNotifyEntity.getTotal_fee());		
					}
					logger.info("notify order start, request: {}", TZBeanUtils.describe(payResult));
					boolean result = thirdPaymentThriftService.notifyOrderPay(payResult);
					logger.info("notify order end, response: {}", result);
					if(result){
						unifieldOrderNotifyEntity.setConfirm_result("T");
						logger.info("update record confirm status, request: {}", TZBeanUtils.describe(unifieldOrderNotifyEntity));
						boolean upt = updateConfirmResult(unifieldOrderNotifyEntity) ;
						logger.info("update record confirm status, response: {}", upt);
						unifieldOrderNotifyResponse = UnifieldOrderNotifyResponse.instance("SUCCESS", "OK");
					}else{
						unifieldOrderNotifyResponse = UnifieldOrderNotifyResponse.instance("FAIL", "notify order fail");
					}
				} catch (Exception e) {
					logger.error(TZMarkers.p2, "wechat pay notify error", e);
					unifieldOrderNotifyResponse = UnifieldOrderNotifyResponse.instance("FAIL", "wechat pay notify error");
				}
			}else {
				unifieldOrderNotifyResponse = UnifieldOrderNotifyResponse.instance("SUCCESS", "wechat pay fail,query DB transaction_id="+unifieldOrderNotifyEntity.getTransaction_id());
			}
		}else{
			unifieldOrderNotifyResponse = UnifieldOrderNotifyResponse.instance("SUCCESS", "OK") ;
		}
		logger.info("wechat order pay notify end, result:::{}", TZBeanUtils.describe(unifieldOrderNotifyResponse));
		return unifieldOrderNotifyResponse ;

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED, rollbackFor=RuntimeException.class)
	public boolean preproceed(UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		boolean needNotify = false ;
		if(isProceed(unifieldOrderNotifyEntity)){
			logger.info("order:::{} already processed, skip...", unifieldOrderNotifyEntity.getOut_trade_no());
		}else{
			needNotify = true ;
			int count = wxPayNotifyDao.count(unifieldOrderNotifyEntity) ;
			if(count == 0){
				try{
					unifieldOrderNotifyEntity.setConfirm_result("F");
					wxPayNotifyDao.insert(unifieldOrderNotifyEntity) ;
				}catch(Exception e){
					logger.error("record wechat notify into DB fail:::{}", unifieldOrderNotifyEntity.getOut_trade_no(), e);
				}
			}else{
				logger.info("wechat order notify already record...");
			}
		}
		return needNotify;
	}

	@Override
	public boolean updateConfirmResult(
			UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		return wxPayNotifyDao.updateConfirmResult(unifieldOrderNotifyEntity) == 1;
	}
	
	@Override
	public boolean isProceed(
			UnifieldOrderNotifyEntity unifieldOrderNotifyEntity) {
		unifieldOrderNotifyEntity.setConfirm_result("T");
		return wxPayNotifyDao.searchCountByTidCfs(unifieldOrderNotifyEntity) == 1;
	}
	
	@Override
	public List<UnifieldOrderNotifyEntity> searchUnProceedRecords() {
		return wxPayNotifyDao.searchUnProceedRecords();
	}

}
