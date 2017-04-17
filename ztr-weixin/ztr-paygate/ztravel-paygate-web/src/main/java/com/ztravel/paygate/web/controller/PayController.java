package com.ztravel.paygate.web.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.common.ReturnCode;
import com.travelzen.framework.core.exception.BizException;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.PaygateClient;
import com.ztravel.paygate.core.util.BeanMapUtil;
import com.ztravel.paygate.core.util.PaygateEncryptUtil;
import com.ztravel.paygate.web.dto.middlebean.PayConfirmBean;
import com.ztravel.paygate.web.dto.request.RequestPayBean;
import com.ztravel.paygate.web.dto.result.PayResult;
import com.ztravel.paygate.web.dto.result.RequestPayResult;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.util.EnvArgs;
import com.ztravel.paygate.web.util.EnvArgs.Const;
import com.ztravel.paygate.web.util.PaygateUtil;

/**
 * 请求支付
 *
 * @author dingguangxian
 */
@Controller
@RequestMapping("/paygate")
public class PayController extends PaygateController {
	private static Logger logger = LoggerFactory.getLogger(PayController.class);

	//	private static String MOBILE_PARTNER = PropertiesUtil.getProperty(Const.CONFIG_FILE, Const.PARTNER_MOBILE_PAY);
	private static String MOBILE_PARTNER = EnvArgs.getArgs(Const.PARTNER_MOBILE_PAY);

	/**
	 * 请求支付(后台调用方式)
	 *
	 * @param model
	 * @param req
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("reqPay")
	@ResponseBody
	public RequestPayResult reqPay(Model model, HttpServletRequest req) {
		RequestPayResult response = new RequestPayResult();
		try {
			// 0. 将客户端的请求进行封装
			req.setCharacterEncoding("UTF-8");
			RequestPayBean requestPayBean = new RequestPayBean();
			TZBeanUtils.setProperties(requestPayBean, req);
			logger.info("请求支付报文:" + TZBeanUtils.describe(requestPayBean));
			// 1. 校验客户端是否为正确的请求
			validator.validate(requestPayBean);

			// 2. 处理请求
			return processRequest(requestPayBean, req);
		} catch (Throwable thr) {
			logger.error("", thr);
			if (thr instanceof PaygateException) {
				response.setRetCode(((PaygateException) thr).getError().code());
				response.setRetMsg(((PaygateException) thr).getError().desc());
			} else {
				response.setRetCode(PaygateError.E100_ERROR.code());
				response.setRetMsg(PaygateError.E100_ERROR.desc());
			}
			return response;
		}
	}

	/**
	 * 处理支付请求
	 *
	 * @param requestPayBean
	 * @param req
	 * @return
	 * @throws Exception
	 */
	private RequestPayResult processRequest(RequestPayBean requestPayBean, HttpServletRequest req) throws Exception {
		RequestPayResult response = new RequestPayResult();

		// 3. 处理请求
		BeanUtils.copyProperties(response, requestPayBean);
		// 3.1 需要根据不同的gateType选择处理程序
		String gateTypeCode = requestPayBean.getGateType();
		GateType gateType = GateType.getByCode(gateTypeCode);
		RequestProcessor processor = null;
		if ((gateType == null) || (processor = processorFactory.getProcessor(gateType)) == null) {
			// 支付类型不受支持,直接返回
			throw new PaygateException(PaygateError.E203_GATETYPE_NOT_SUPPORT);
		}
		// 3.2 处理请求
		PayConfirmBean confirmBean = processor.processRequest(requestPayBean, req);
		// 4. 判断处理结果是否有效
		valiateConfirmPayBean(confirmBean);
		// 5. 根据处理结果进行判断
		// 5.1 处理无误，进入银行支付
		if (PaygateError.SUCCESS.code().equals(confirmBean.getRetCode())) {
			// 签名成功
			logger.info("请求签名成功，返回签名请求结果.{}", TZBeanUtils.describe(confirmBean));
			response.setRetCode(PaygateError.SUCCESS.code());
			response.setRetMsg(PaygateError.SUCCESS.desc());
			response.setPayUrl(confirmBean.getPayURL());
			response.setSign(signResponse(requestPayBean.getClientId(), response));
			return response;
		}
		// 5.2 处理失败,返回失败信息
		response.setRetCode(confirmBean.getRetCode());
		response.setRetMsg(confirmBean.getRetMsg());
		return response;
	}

	//支付请求结果进行签名
	private String signResponse(String clientId, RequestPayResult response) throws Exception {
		PaygateClient client = paygateClientService.findByClientId(clientId);
		return PaygateEncryptUtil.getSignStr(BeanMapUtil.mapBean(response), client.getSignKey());
	}


	/**
	 * 请求支付(手机支付方式)
	 *
	 * @param model
	 * @param req
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("reqMobilePay")
	@ResponseBody
	public RequestPayResult reqMobilePay(Model model, HttpServletRequest req) {
		RequestPayResult response = new RequestPayResult();
		try {
			// 0. 将客户端的请求进行封装
			req.setCharacterEncoding("UTF-8");
			RequestPayBean requestPayBean = new RequestPayBean();
			TZBeanUtils.setProperties(requestPayBean, req);
			logger.info("请求支付报文:" + TZBeanUtils.describe(requestPayBean));
			if (!GateType.AliPay.code.equals(requestPayBean.getGateType())) {
				throw new PaygateException(PaygateError.E203_GATETYPE_NOT_SUPPORT);
			}
			// 1. 校验客户端是否为正确的请求
			validator.validate(requestPayBean);
			requestPayBean.setPartner(MOBILE_PARTNER);
			requestPayBean.setMobilePay(true);
			// 2. 处理请求
			return processRequest(requestPayBean, req);
		} catch (Throwable thr) {
			logger.error("", thr);
			if (thr instanceof PaygateException) {
				response.setRetCode(((PaygateException) thr).getError().code());
				response.setRetMsg(((PaygateException) thr).getError().desc());
			} else {
				response.setRetCode(PaygateError.E100_ERROR.code());
				response.setRetMsg(PaygateError.E100_ERROR.desc());
			}
			return response;
		}
	}

	/**
	 * 请求支付(页面跳转方式)
	 *
	 * @param model
	 * @param req
	 * @return
	 * @throws BizException
	 */
	@RequestMapping("reqPayD")
	public String reqPayD(Model model, HttpServletRequest req) throws BizException {
		try {
			// 0. 将客户端的请求进行封装
			req.setCharacterEncoding("UTF-8");
			RequestPayBean requestPayBean = new RequestPayBean();
			TZBeanUtils.setProperties(requestPayBean, req);
			logger.info("请求支付报文:" + TZBeanUtils.describe(requestPayBean));
			// 1. 校验客户端是否为正确的请求
			try {
				validator.validate(requestPayBean);
			} catch (Exception e) {
				PayResult result = new PayResult();
				BeanUtils.copyProperties(result, requestPayBean);
				if (e instanceof PaygateException) {
					result.setRetCode(((PaygateException) e).getError().code());
					result.setRetMsg(((PaygateException) e).getError().desc());
				} else {
					result.setRetCode(PaygateError.E100_ERROR.code());
					result.setRetMsg(PaygateError.E100_ERROR.desc());
				}
				model.addAttribute("resultBean", result);
				model.addAttribute("fgNotifyUrl", requestPayBean.getFgNotifyUrl());
				logger.error("接收到错误的客户端请求,请求支付报文:{}.校验结果：{}", TZBeanUtils.describe(requestPayBean), TZBeanUtils.describe(result));
				logger.error(e.getMessage(), e);
				return "payment/jumpToFgNotify";
			}

			// 3. 处理请求
			// 3.1 需要根据不同的gateType选择处理程序
			GateType gateType = requestPayBean.gateType();
			RequestProcessor processor = null;
			if ((gateType == null) || (processor = processorFactory.getProcessor(gateType)) == null) {
				// 支付类型不受支持,跳转到前台页面
				PayResult result = new PayResult();
				BeanUtils.copyProperties(result, requestPayBean);
				result.setRetCode(PaygateError.E203_GATETYPE_NOT_SUPPORT.code());
				result.setRetMsg(PaygateError.E203_GATETYPE_NOT_SUPPORT.desc());
				model.addAttribute("resultBean", result);
				model.addAttribute("fgNotifyUrl", requestPayBean.getFgNotifyUrl());
				return "payment/jumpToFgNotify";
			}
			// 3.2 处理请求
			PayConfirmBean confirmBean = processor.processRequest(requestPayBean, req);
			// 4. 判断处理结果是否有效
			valiateConfirmPayBean(confirmBean);
			// 5. 根据处理结果进行页面跳转
			// 5.1 处理无误，进入银行支付页面
			if (PaygateError.SUCCESS.code().equals(confirmBean.getRetCode()) && StringUtils.isNotBlank(confirmBean.getPayURL())) {
				// 签名成功,跳转到银行支付页面
				logger.info("请求签名成功，跳转银行支付页面.");
				model.addAttribute("confirmBean", confirmBean);
				return "payment/jumpToBank";
			}
			// 5.2 处理失败或POS支付,跳转到前台页面
			PayResult result = new PayResult();
			BeanUtils.copyProperties(result, requestPayBean);
			result.setRetCode(confirmBean.getRetCode());
			result.setRetMsg(confirmBean.getRetMsg());
			model.addAttribute("resultBean", result);
			model.addAttribute("fgNotifyUrl", requestPayBean.getFgNotifyUrl());
			return "payment/jumpToFgNotify";
		} catch (BizException be) {
			logger.error("", be);
			throw be;
		} catch (Throwable thr) {
			logger.error("", thr);
			throw BizException.instance(ReturnCode.ERROR, "未知错误", thr);
		}
	}

	/**
	 * 对处理结果进行校验
	 *
	 * @param confirmBean
	 */
	protected void valiateConfirmPayBean(PayConfirmBean confirmBean) throws PaygateException {
	}
}
