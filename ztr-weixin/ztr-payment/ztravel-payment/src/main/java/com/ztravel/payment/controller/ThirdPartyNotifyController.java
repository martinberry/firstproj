/**
 *
 */
package com.ztravel.payment.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.order.client.service.IOrderThriftClientService;
import com.ztravel.payment.paygate.BeanMapUtil;
import com.ztravel.payment.paygate.model.PayResultBean;
import com.ztravel.payment.paygate.model.RefundResultBean;
import com.ztravel.payment.service.ThirdPaymentService;

/**
 * @author zuoning.shen
 *
 */
@Controller
@RequestMapping("/notify/")
public class ThirdPartyNotifyController {
	private static Logger logger = LoggerFactory.getLogger(ThirdPartyNotifyController.class);
	@Resource
	protected ThirdPaymentService thirdPaymentService;

	@Resource
	private IOrderThriftClientService orderThriftClientServiceImpl;

	@RequestMapping(value = "payNotify")
	@ResponseBody
	public String payNotify(@ModelAttribute PayResultBean payResult) throws Exception {
		logger.info("Process payment result start, {}", TZBeanUtils.describe(payResult));
		Map<String, String> params = BeanMapUtil.mapBean(payResult);
		if (!thirdPaymentService.verifySignStr(params, params.get("sign"))) {
			logger.error("Verify sign error, params={}", params);
			return "SF";
		}

		String retCode = payResult.getRetCode();
		String payState = payResult.getPayState();
		if ("0000".equals(retCode) && "SUCCESS".equalsIgnoreCase(payState)) {
			try {
				thirdPaymentService.notifyOrderPay(payResult);
			} catch (Exception e) {
				logger.error("Processing payment result error", e);
				return "EF";
			}
		}
		logger.info("Process payment result end, {}", TZBeanUtils.describe(payResult));
		return "T";
	}

	/**
	 * 退款通知
	 *
	 * @param formBean
	 * @return
	 */
	@RequestMapping(value = "refundNotify")
	@ResponseBody
	public String refundNotify(@ModelAttribute RefundResultBean refundResult) {
		logger.info("Process refund result start, {}", TZBeanUtils.describe(refundResult));
		Map<String, String> params = BeanMapUtil.mapBean(refundResult);
		if (!thirdPaymentService.verifySignStr(params, params.get("sign"))) {
			logger.error("Verify sign error, params={}", params);
			return "SF";
		}
		String retCode = refundResult.getRetCode();
		String refundState = refundResult.getRefundState();
		if ("0000".equals(retCode) && "SUCCESS".equalsIgnoreCase(refundState)) {
			try {
				thirdPaymentService.notifyOrderRefund(refundResult);
			} catch (Exception e) {
				logger.error("Processing refund result error", e);
				return "EF";
			}
		}else{
			try{
				orderThriftClientServiceImpl.updateOrderToRefundFailed(refundResult.getOrderNum());
			}catch(Exception e){
				logger.info("调用orderThriftClientServiceImpl.updateOrderToRefundFailed({}) 更改订单状态为取消失败 失败:"+e, refundResult.getOrderNum());
			}
		}
		logger.info("Process refund result end, {}", TZBeanUtils.describe(refundResult));
		return "T";
	}

}
