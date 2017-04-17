package com.ztravel.paygate.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.exception.BizException;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.web.dto.middlebean.RefundConfirmBean;
import com.ztravel.paygate.web.dto.request.RequestRefundBean;
import com.ztravel.paygate.web.dto.result.CommonSyncResult;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.util.PaygateUtil;

/**
 * 退款请求
 * 
 * @author dingguangxian
 * 
 */
@Controller
@RequestMapping("/paygate/")
public class RefundController extends PaygateController{
	private static Logger logger = LoggerFactory.getLogger(RefundController.class);

	/**
	 * 请求退款
	 * 
	 * @param model
	 * @param req
	 * @return
	 */
	@RequestMapping("reqRefund")
	@ResponseBody
	public CommonSyncResult reqRefund(Model model, HttpServletRequest req, HttpServletResponse res) {
		try {
			// 0. 将客户端的请求进行封装
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			res.setContentType("text/html;charset=utf-8");
			RequestRefundBean requestRefundBean = new RequestRefundBean();
			TZBeanUtils.setProperties(requestRefundBean, req);
			logger.info("请求退款报文:" + TZBeanUtils.describe(requestRefundBean));
			// 1. 校验客户端是否为正确的请求
			validator.validate(requestRefundBean);

			// 2. 处理请求
			// 2.1 需要根据不同的gateType选择处理程序
			GateType gateType = requestRefundBean.gateType();
			RequestProcessor processor = null;
			if ((gateType == null) || (processor = processorFactory.getProcessor(gateType)) == null) {
				// 支付类型不受支持,直接返回
				logger.warn("请求的网支类型不受支持.{}", gateType);
				return buildRefundResult(PaygateError.E203_GATETYPE_NOT_SUPPORT);
			}
			// 2.2 处理请求
			RefundConfirmBean confirmBean = processor.processRefundRequest(requestRefundBean, req);
			// 3. 判断处理结果是否有效
			valiateConfirmRefundBean(confirmBean);
			logger.info("请求退款的处理结果:" + TZBeanUtils.describe(confirmBean));
			CommonSyncResult result = new CommonSyncResult();
			result.setRetCode(confirmBean.getRetCode());
			result.setRetMsg(StringUtils.trimToEmpty(confirmBean.getRetMsg()));
			paygateUtil.signResultBean(requestRefundBean.getClientId(), result);
			return result;
		} catch (Throwable thr) {
			logger.error("退款请求出现异常.", thr);

			CommonSyncResult result = new CommonSyncResult();
			PaygateUtil.processException(result, thr);
			return result;
		}
	}

	private void valiateConfirmRefundBean(RefundConfirmBean confirmBean) {
		// TODO
	}

	private CommonSyncResult buildRefundResult(PaygateError ex) {
		CommonSyncResult ret = new CommonSyncResult();
		ret.setRetCode(ex.code());
		ret.setRetMsg(ex.desc());
		return ret;
	}
}
