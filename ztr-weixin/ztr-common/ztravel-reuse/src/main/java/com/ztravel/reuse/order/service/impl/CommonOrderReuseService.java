package com.ztravel.reuse.order.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.enums.CommonOrderType;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.MailSender;
import com.ztravel.common.util.SmsContentUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderContactorReuseService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;

@Service
public class CommonOrderReuseService implements ICommonOrderReuseService {
	
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(CommonOrderReuseService.class);
	
	private final RedisClient redisClient = RedisClient.getInstance();
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil;
	
	@Resource
	ICommonOrderDao commonOrderDaoImpl;
	
	@Resource
	private IOrderDao orderDao;
	
	@Resource
	private IOrderLogReuseService orderLogReuseService;
	
	@Resource
	IOrderContactorReuseService orderContactorReuseService;
	
	

	@Override
	public CommonOrder getRepairCommonOrderByOrderId(String orderId) throws Exception {
		return commonOrderDaoImpl.selectRepairByOriginOrderId(orderId);
	}
	
	
	@Override
	public CommonOrder selectByOrderId(String orderId){
		return commonOrderDaoImpl.selectById(orderId) ;
	}


	@Override
	public CommonResponse createCommonOrder(Order order, long price, String productName) throws Exception {
		LOGGER.info(TZBeanUtils.describe(order));
		CommonResponse commonResponse = new CommonResponse();
		commonResponse.setSuccess(true);
		try {
			CommonOrder commonOrder = createRepairOrderByOrder(order, price);
			if (commonOrder != null) {
			    String operatorName = redisClient.get(OperatorSidHolder.get());
			    String remark = "生成等价单";
			    if (price > 0) {
			        remark = "生成补款单";
			    } else if (price < 0) {
			        remark = "生成退款单";
			    }
			    orderLogReuseService.save(commonOrder.getOrderIdOrigin(), operatorName, remark, "");

                if (price > 0) {
                    try {
                        final String orderId = commonOrder.getOrderId();
                        String orderIdOrigin = commonOrder.getOrderIdOrigin();
                        String orderNoOrigin = commonOrder.getOrderNoOrigin();
                        OrderContactor orderContactor = orderContactorReuseService.getOrderContactor(orderIdOrigin);
                        Preconditions.checkNotNull(orderContactor);
                        final String email = orderContactor.getEmail();
                        final Long amount = commonOrder.getPrice();
                        final String mobile = orderContactor.getPhone();

                        sendBackPayEmail(email, orderId, productName);
                        sendSms(mobile, orderNoOrigin, amount);

                    } catch (Exception e) {
                        LOGGER.error(e.getMessage(), e);
                    }
                }

			} else {
	            commonResponse.setSuccess(false);
	            commonResponse.setErrMsg("创建commonOrder失败：" + TZBeanUtils.describe(order));
	            return commonResponse;
			}
		} catch (Exception e) {
			LOGGER.error("创建commonOrder失败",e);
			commonResponse.setSuccess(false);
			commonResponse.setErrMsg("创建commonOrder失败：" + TZBeanUtils.describe(order));
			return commonResponse;
		}
		return commonResponse;
	}
		
	private CommonOrder createRepairOrderByOrder(Order order, long price) throws Exception {
		CommonOrder commonOrder = new CommonOrder();
		String commonOrderId = idGeneratorUtil.getCommonOrderId(order.getOrderNo());
		commonOrder.setOrderId(commonOrderId);
        commonOrder.setOrderNoVice(commonOrderId);
		commonOrder.setOrderIdOrigin(order.getOrderId());
		commonOrder.setOrderNoOrigin(order.getOrderNo());
		commonOrder.setMemberId(order.getCreator());
		commonOrder.setPrice(price);
		if (price == 0) {
		    commonOrder.setRemark("等价单");
	        commonOrder.setType(CommonOrderType.OP_CONFIRM_EQUAL);
		} else if (price > 0) {
		    commonOrder.setRemark("补款单");
	        commonOrder.setType(CommonOrderType.OP_CONFIRM_REPAIR);
	        commonOrder.setStatus(CommonOrderStatus.UNPAY);
	        commonOrder.setStateChangeHistory(CommonOrderStatus.UNPAY.toString());
		} else {
		    commonOrder.setPrice(0 - price);
		    //commonOrder.setRemark("退款单");
	        commonOrder.setType(CommonOrderType.OP_CONFIRM_REFUND);
	        commonOrder.setStatus(CommonOrderStatus.INIT);
	        commonOrder.setStateChangeHistory(CommonOrderStatus.INIT.toString());
	        Order orderOrig = orderDao.selectById(order.getOrderId());
	        commonOrder.setPaySerialNum(orderOrig.getPaySerialNum());
	        if (orderOrig.getPayType() != null) {
	            commonOrder.setPayType(PaymentType.valueOf(orderOrig.getPayType()));
	        }
		}
		DateTime now = new DateTime();
		commonOrder.setCreateDate(now);
		commonOrder.setUpdateDate(now);

		try {
            commonOrderDaoImpl.insert(commonOrder);

        } catch (Exception e) {
            LOGGER.error("生成commonOrder失败：orderId:" + order.getOrderId(), e);
            return null;
        }
		return commonOrder;
	}
	
	public void sendBackPayEmail(String email, String orderId, String productName) throws Exception {
		String serverPath = WebEnv.get("server.path.memberServer");
		String url = serverPath + "/orderPay/selectPayType/" + orderId;
		Map<String, String> params = new HashMap<String, String>();
		params.put("URL", url);
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd"));
		params.put("HOMEPAGE", WebEnv.get("server.path.memberServer", ""));
		params.put("MEMBERINFO", WebEnv.get("server.path.memberServer", "") + "/home");
		params.put("PRODUCTNAME", productName);
		MailEntity entity = new MailEntity(email, null, MailEnums.ContentType.BACKPAYHTML, params, MailEnums.BizType.BACKPAY);
		MailSender.send(entity);
	}
	
	private void sendSms(final String mobile, final String orderId, final Long amount) {
	    new Thread(new Runnable() {
            public void run() {
                try {
                    sendBackPaySms(mobile, orderId, amount);
                } catch (Exception e) {
                    LOGGER.error("发送补款短信失败：orderNo:" + orderId, e);
                }
            }
        }).start();
    }
	
	public void sendBackPaySms(String mobile, String orderId, Long amount) throws Exception {
		MobileCaptchaEntity mobileCapEntity = buildSmsEntity(mobile, SmsContentUtil.getPayBackSmsContent(orderId, amount));
		SmsAdapter.sendMessage(mobileCapEntity);
	}
	
	private MobileCaptchaEntity buildSmsEntity(String mobilPhone, String smsContent) {
		MobileCaptchaEntity mobileCapEntity = new MobileCaptchaEntity();
		mobileCapEntity.setMobileNum(mobilPhone);
		mobileCapEntity.setContent(smsContent);
		return mobileCapEntity;
	}

}
