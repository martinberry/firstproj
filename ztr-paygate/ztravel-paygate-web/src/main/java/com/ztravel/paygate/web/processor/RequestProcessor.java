package com.ztravel.paygate.web.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PayState;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.enums.RefundState;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.thrift.model.PaySignRequest;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryRequest;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundQueryRequest;
import com.ztravel.paygate.thrift.model.RefundQueryResponse;
import com.ztravel.paygate.thrift.model.RefundRequest;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.thrift.model.RefundShareProfitModel;
import com.ztravel.paygate.thrift.model.RefundValSignRequest;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.thrift.model.ValSignRequest;
import com.ztravel.paygate.thrift.model.ValSignResponse;
import com.ztravel.paygate.web.dto.middlebean.PayConfirmBean;
import com.ztravel.paygate.web.dto.middlebean.QueryProcessBean;
import com.ztravel.paygate.web.dto.middlebean.RefundConfirmBean;
import com.ztravel.paygate.web.dto.middlebean.RefundQueryProcessBean;
import com.ztravel.paygate.web.dto.middlebean.RefundResponseConfirmBean;
import com.ztravel.paygate.web.dto.middlebean.ResponseConfirmBean;
import com.ztravel.paygate.web.dto.request.RequestPayBean;
import com.ztravel.paygate.web.dto.request.RequestQueryBean;
import com.ztravel.paygate.web.dto.request.RequestRefundBean;
import com.ztravel.paygate.web.dto.request.RequestRefundQueryBean;
import com.ztravel.paygate.web.dto.response.BankPayResponse;
import com.ztravel.paygate.web.dto.result.PayResult;
import com.ztravel.paygate.web.dto.result.RefundResult;
import com.ztravel.paygate.web.util.EnvArgs;
import com.ztravel.paygate.web.util.EnvArgs.Const;
import com.ztravel.paygate.web.util.PaygateServiceClient;
import com.ztravel.paygate.web.util.PaygateUtil;

/**
 * 支付请求或信息返回的数据处理器
 *
 * @author dingguangxian
 */
public abstract class RequestProcessor {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 支持的支付平台
	 *
	 * @return
	 */
	public abstract GateType supportsGateType();

	/**
	 * 处理支付请求
	 *
	 * @param payRequest
	 * @param req
	 * @return
	 */
	public PayConfirmBean processRequest(RequestPayBean payRequest, HttpServletRequest req) {
		logger.info("处理支付请求. start...bean:{}", TZBeanUtils.describe(payRequest));
		if (!supportsGateType().code.equals(payRequest.getGateType())) {
			logger.error("不受支持的网支类型:{}", payRequest.getGateType());
			PayConfirmBean result = new PayConfirmBean();
			result.setOrderNum(payRequest.getOrderNum());
			result.setRetCode(PaygateError.E203_GATETYPE_NOT_SUPPORT.code());
			result.setRetMsg(PaygateError.E203_GATETYPE_NOT_SUPPORT.name());
			return result;
		}
		PayConfirmBean bean = new PayConfirmBean();
		bean.setOrderNum(payRequest.getOrderNum());
		// 1. 记录当前的请求信息
		String entityId = null;
		try {
			logger.info("记录支付请求信息到数据库...");
			entityId = recordPayRequest(payRequest);
		} catch (Exception e1) {
			logger.error("记录支付请求信息到数据库时出现异常.", e1);
			PaygateUtil.processException(bean, e1);
			return bean;
		}
		if (entityId == null) {
			bean.setRetCode(PaygateError.E100_ERROR.code());
			bean.setRetMsg(PaygateError.E100_ERROR.desc());
			return bean;
		}

		// 2. 对请求进行签名
		try {
			logger.info("请求签名及获取支付URL... entityId:{}", entityId);
			PaySignResponse response = signPayment(entityId, payRequest, req);
			// 将签名结果进行记录
			logger.info("记录请求结果到数据库.retCode:{},retMsg:{},payUrl:{},extraInfos:{}", response.getRetCode(), response.getRetMsg(),
					response.getBankPayUrl(), response.getExtraInfos());
			recordSignResult(entityId, response);
			bean.setRetCode(response.getRetCode());
			bean.setRetMsg(response.getRetMsg());
			bean.setPayURL(response.getBankPayUrl());
		} catch (Exception e) {
			logger.error("请求签名出现异常.", e);
			PaygateUtil.processException(bean, e);
			return bean;
		}
		logger.info("处理支付请求. end");
		return bean;
	}

	public PayResult paymentQuery(RequestQueryBean requestQueryBean, HttpServletRequest req) {
		logger.debug("处理查询请求. patch...bean:{}", TZBeanUtils.describe(requestQueryBean));
		PayResult bean = new PayResult();
		String orderNum = requestQueryBean.getOrderNum();
		bean.setOrderNum(orderNum);

		try {
			QueryProcessBean queryBean = this.queryRequestData(orderNum);
			logger.info("从本地数据库获取到的查询结果:{}", TZBeanUtils.describe(queryBean));
			if (queryBean == null) {
				throw new PaygateException(PaygateError.E313_PATCH_NOT_FIND_PAYGATE_DATA);
			}

			if (PayState.SUCCESS.name().equals(queryBean.getPayState())) {// 支付成功
				logger.info("本地数据检索成功，直接返回.{}", TZBeanUtils.describe(queryBean));
				bean.setClientId(queryBean.getClientId());
				bean.setAmount(queryBean.getTransAmount());
				// bean.setBankPaymentTime(resultBean.get);
				bean.setGateType(queryBean.getGateType());
				bean.setOrderNum(queryBean.getOrderNum());
				bean.setTraceNum(queryBean.getTraceNum());
				bean.setPayState(PayState.valueOf(queryBean.getPayState()));
				bean.setRetCode(PaygateError.SUCCESS.code());
				bean.setRetMsg(PaygateError.SUCCESS.desc());
			} else {// 支付结果未知，进行远程查询
				logger.info("本地数据库未检索到相应的数据，请求远程检索.{}", queryBean.getOrderNum());
				bean.setClientId(queryBean.getClientId());
				// queryRequest.setTraceNum(patchBean.getTraceNum());
				QueryResponse queryResponse = signQuery(queryBean, orderNum, req);
				if (PaygateError.SUCCESS.code().equals(queryResponse.getRetCode())) {
					if (PayState.valueOf(queryResponse.getPayState()) == PayState.SUCCESS) {
						bean.setRetCode(PaygateError.SUCCESS.code());
						bean.setRetMsg(PaygateError.SUCCESS.desc());
						if (queryResponse.getAmount() == queryBean.getTransAmount()) {
							bean.setAmount(queryResponse.getAmount());
							bean.setOrderNum(orderNum);
							bean.setTraceNum(queryResponse.getTraceNum());
							bean.setPayState(PayState.valueOf(queryResponse.getPayState()));
							// 修改网关记录为成功
							recordQueryResult(queryBean.getPayId(), queryResponse);
						} else {
							/*PaygateMonitor.sendMessage(getClass(), "调用网关查询接口结果中订单支付金额与请求支付金额不匹配.", null, orderNum,
									queryBean.getTransAmount(), queryResponse.getAmount());*/
							bean.setRetCode(PaygateError.E210_AMOUNT_NOT_MATCHED.code());
							bean.setRetMsg(PaygateError.E210_AMOUNT_NOT_MATCHED.desc());
						}
					}
				} else {
					bean.setRetCode(queryResponse.retCode);
					bean.setRetMsg(queryResponse.retMsg);
				}
			}
		} catch (Exception e) {
			logger.error("查询支付结果出现异常.", e);
			PaygateUtil.processException(bean, e);
			return bean;
		}
		return bean;
	}

	/**
	 * 查询请求数据
	 *
	 * @return
	 */
	public abstract QueryProcessBean queryRequestData(String orderNum) throws Exception;

	// 在签名之前对支付的请求进行记录,并返回其ID
	protected abstract String recordPayRequest(RequestPayBean payRequest);

	// 签名结束之后的处理
	protected abstract void recordSignResult(String entityId, PaySignResponse response);

	protected abstract void recordQueryResult(String entityId, QueryResponse queryResponse);

	/**
	 * 对查询请求进行签名
	 *
	 * @param entityId
	 * @param orderNum
	 * @param req
	 * @return
	 */
	protected QueryResponse signQuery(QueryProcessBean processBean, String orderNum, HttpServletRequest req) {
		logger.info("生成签名请求...processBean:{},orderNum:{}", TZBeanUtils.describe(processBean), orderNum);
		QueryRequest queryRequest = new QueryRequest();
		queryRequest.setPartner(processBean.getPartner());
		queryRequest.setOrderNum(orderNum);

		logger.info("执行签名请求...signRequest:{}", TZBeanUtils.describe(queryRequest));
		return PaygateServiceClient.query(queryRequest, supportsGateType());
	}

	/**
	 * 支付请求签名
	 *
	 * @param entityId
	 * @param payRequest
	 * @param req
	 * @return
	 */
	protected PaySignResponse signPayment(String entityId, RequestPayBean payRequest, HttpServletRequest req) {
		logger.info("生成签名请求...entityId:{}, payRequest:{}", entityId, TZBeanUtils.describe(payRequest));
		PaySignRequest signRequest = buildSignRequest(entityId, payRequest, req);
		logger.info("执行签名请求...signRequest:{}", TZBeanUtils.describe(signRequest));
		return PaygateServiceClient.signPayment(signRequest, supportsGateType());
	}

	/**
	 * 构造签名请求
	 *
	 * @param entityId
	 * @param payRequest
	 * @param req
	 * @return
	 */
	protected PaySignRequest buildSignRequest(String entityId, RequestPayBean payRequest, HttpServletRequest req) {
		PaySignRequest signRequest = new PaySignRequest();
		signRequest.setPartner(payRequest.getPartner());
		signRequest.setOrderNum(payRequest.getOrderNum());
		signRequest.setAmount(payRequest.getAmount());
		signRequest.setComment(payRequest.getComment());
		// 前后台通知的地址都用中间件的地址进行替换,以便拦截结果
//		signRequest.setFgNotifyUrl(PropertiesUtil.getProperty(Const.CONFIG_FILE, supportsGateType().name + Const.FG_NOTIFY_URL_SUFFIX)
//				+ entityId);
//		signRequest.setBgNotifyUrl(PropertiesUtil.getProperty(Const.CONFIG_FILE, supportsGateType().name + Const.BG_NOTIFY_URL_SUFFIX)
//				+ entityId);

		signRequest.setFgNotifyUrl(EnvArgs.getArgs(supportsGateType().name + Const.FG_NOTIFY_URL_SUFFIX)
				+ entityId);
		signRequest.setBgNotifyUrl(EnvArgs.getArgs(supportsGateType().name + Const.BG_NOTIFY_URL_SUFFIX)
				+ entityId);
		boolean mobilePay = payRequest.getMobilePay() != null ? payRequest.getMobilePay().booleanValue() : false;
		if (mobilePay) {
			signRequest.setIsMobilePay(true);
			signRequest.setBgNotifyUrl(EnvArgs.getArgs(supportsGateType().name + Const.MOBILE_NOTIFY_URL_SUFFIX)
					+ entityId);
		}
		return signRequest;
	}

	/**
	 * 对信息的处理结果进行有效性校验
	 *
	 * @param response
	 * @return
	 */
	public ValSignResponse payValSign(ResponseConfirmBean confirmBean, BankPayResponse response) {
		ValSignRequest request = new ValSignRequest();
		request.setFgNotify(response.isFgNotify());
		request.setBankResponseData(response.getFormData());
		request.setIsMobilePay(response.isMobilePay());
		//TODO partner
		request.setPartner(confirmBean.getPartner());
		return PaygateServiceClient.payValSign(request, supportsGateType());
	}

	// //////////////////////////退款///////////////////////////////

	/**
	 * 处理退款请求
	 *
	 * @param refundRequest
	 * @param req
	 * @return
	 */
	public RefundConfirmBean processRefundRequest(RequestRefundBean refundRequest, HttpServletRequest req) {
		logger.info("处理退款请求...start. refundBean:{}", TZBeanUtils.describe(refundRequest));
		if (!supportsGateType().code.equals(refundRequest.getGateType())) {
			logger.warn("不受支持的网支类型.{}" + refundRequest.getGateType());
			RefundConfirmBean result = new RefundConfirmBean();
			result.setRetCode(PaygateError.E203_GATETYPE_NOT_SUPPORT.code());
			result.setRetMsg(PaygateError.E203_GATETYPE_NOT_SUPPORT.name());
			return result;
		}
		RefundConfirmBean bean = null;
		// 1. 记录当前的请求信息
		try {
			logger.info("记录退款请求到数据库.");
			bean = validateAndRecordRefundRequest(refundRequest);
		} catch (Exception e1) {
			logger.error("记录退款请求到数据库出现异常", e1);
			bean = new RefundConfirmBean();
			PaygateUtil.processException(bean, e1);
			return bean;
		}
		if (bean == null) {
			bean = new RefundConfirmBean();
			logger.error("记录退款请求到数据库出现异常,entityId为null");
			bean.setRetCode(PaygateError.E100_ERROR.code());
			bean.setRetMsg(PaygateError.E100_ERROR.desc());
			return bean;
		}

		// 2. 发起请求
		try {
			logger.info("发起退款请求.entityId:{},refundEntityId:{}, partner:{}", bean.getEntityId(), bean.getRefundEntityId(), bean.getPartner());
			RefundResponse response = requestRefund(bean, refundRequest, req);
			logger.info("退款请求结果返回:{}", TZBeanUtils.describe(response));
			// 记录请求结果
			logger.info("记录退款请求结果到数据库.retCode:{},retMsg:{},extraInfos:{}", response.getRetCode(), response.getRetMsg(),
					response.getExtraInfos());
			recordRefundRequestResult(bean.getRefundEntityId(), response);
			bean.setRetCode(response.getRetCode());
			bean.setRetMsg(response.getRetMsg());
		} catch (Exception e) {
			logger.error("退款请求失败.", e);
			PaygateUtil.processException(bean, e);
			return bean;
		}
		return bean;
	}

	/**
	 * 校验并且记录下本次的退款请求
	 *
	 * @param refundRequest
	 * @return 返回记录在数据库中的退款实体ID
	 * @throws PaygateException
	 */
	protected abstract RefundConfirmBean validateAndRecordRefundRequest(RequestRefundBean refundRequest) throws PaygateException;

	/**
	 * 记录退款请求返回的结果
	 *
	 * @param entityId
	 * @param response
	 */
	protected abstract void recordRefundRequestResult(String entityId, RefundResponse response);

	// 发起退款请求
	protected RefundResponse requestRefund(RefundConfirmBean bean, RequestRefundBean refundBean, HttpServletRequest req) {
		RefundRequest refundRequest = buildRefundRequest(bean, refundBean, req);
		return PaygateServiceClient.requestRefund(refundRequest, supportsGateType());
	}

	/**
	 * 构建退款请求对象
	 *
	 * @param entityId
	 * @param refundBean
	 * @param req
	 * @return
	 */
	protected RefundRequest buildRefundRequest(RefundConfirmBean bean, RequestRefundBean refundBean, HttpServletRequest req) {
		/**
		 * 退款标识 1: required string refundNum, //原支付交易的订单号 2: required string orderNum, //原支付交易流水号 3: required string traceNum, //本次退款金额 4: required i64
		 * refundAmount, //原始交易金额 5: required i64 transAmount, //备注信息 6: optional string comment, //交易结果通知地址 7: optional string notifyUrl, //退款时间(yyyy-MM-dd
		 * HH:mm:ss) 8: optional string refundTime,
		 **/
		RefundRequest request = new RefundRequest();
		request.setRefundNum(refundBean.getRefundNum());
		request.setOrderNum(refundBean.getOrderNum());
		request.setTraceNum(refundBean.getTraceNum());
		request.setRefundAmount(refundBean.getRefundAmount());
		request.setTransAmount(refundBean.getTransAmount());
		request.setComment(refundBean.getComment());
		request.setRefundTime(refundBean.getRefundTime());

		String refundProfits = refundBean.getRefundProfitDetails();
		if (StringUtils.isNotBlank(refundProfits)) {
			List<RefundShareProfitModel> list = new ArrayList<>();
			String[] str = StringUtils.splitPreserveAllTokens(refundProfits.trim(), "\\|");//refundProfits.trim().split("\\|");
			RefundShareProfitModel model = new RefundShareProfitModel();
			model.setRefundAccount(str[0]);
			model.setAmount(Long.valueOf(str[1]));
			model.setComment(StringUtils.trimToEmpty(str[2]));
			list.add(model);
			request.setRefundProfitDetails(list);
		}

		// 通知地址
		String notifyUrlKey = supportsGateType().name + Const.REFUND_NOTIFY_URL_SUFFIX;
		logger.info("refund notify url key :: {}", notifyUrlKey);
//		request.setNotifyUrl(PropertiesUtil.getProperty(Const.CONFIG_FILE, notifyUrlKey) + bean.getRefundEntityId());
		request.setNotifyUrl(EnvArgs.getArgs(notifyUrlKey) + bean.getRefundEntityId());
		request.setPartner(bean.getPartner());
		return request;
	}

	/**
	 * 退单验签
	 *
	 * @param bean
	 * @param response
	 * @return
	 */
	public RefundValSignResponse refundValSign(RefundResponseConfirmBean bean, Map<String, String> response) {
		RefundValSignRequest request = new RefundValSignRequest();
		request.setPartner(bean.getPartner());
		request.setRefundResponse(response);
		return PaygateServiceClient.refundValSign(request, supportsGateType());
	}

	/**
	 * 退单查询
	 *
	 * @param requestQueryBean
	 * @param req
	 * @return
	 */
	public RefundResult refundQuery(RequestRefundQueryBean requestQueryBean, HttpServletRequest req) {

		logger.debug("处理退款查询请求. bean:{}", TZBeanUtils.describe(requestQueryBean));
		RefundResult bean = new RefundResult();
		String refundNum = requestQueryBean.getRefundNum();
		bean.setOrderNum(refundNum);

		try {
			RefundQueryProcessBean processBean = this.queryRefundRequestData(refundNum);
			logger.info("从本地数据库获取到的查询结果:{}", TZBeanUtils.describe(processBean));
			if (processBean == null) {
				throw new PaygateException(PaygateError.E313_PATCH_NOT_FIND_PAYGATE_DATA);
			}

			if (RefundState.SUCCESS == processBean.getRefundState()) {// 支付成功
				logger.info("本地数据检索成功，直接返回.{}", TZBeanUtils.describe(processBean));
			} else if (PaygateError.SUCCESS.code().equals(processBean.getReqRetCode())) {// 请求成功，但未返回结果
				logger.info("本地数据库未检索到相应的数据，请求远程检索.{}", processBean.getOrderNum());
				RefundQueryResponse queryResponse = remoteRefundQuery(processBean, req);
				if (PaygateError.SUCCESS.code().equals(queryResponse.getRetCode())) {
					processBean.setReqRetCode(PaygateError.SUCCESS.code());
					processBean.setReqRetMsg(PaygateError.SUCCESS.desc());
					recordRefundQueryResponse(processBean, queryResponse);
				} else {
					logger.info("远程查询出现问题,{}", queryResponse);
					processBean.setReqRetCode(queryResponse.retCode);
					processBean.setReqRetMsg(queryResponse.retMsg);
				}
			} else {
				// 请求失败,直接返回失败
				processBean.setRefundState(RefundState.FAIL);
			}
			bean.setClientId(processBean.getClientId());
			bean.setRefundAmount(processBean.getRefundAmount());
			bean.setGateType(processBean.getGateType().code);
			bean.setRefundNum(processBean.getRefundNum());
			bean.setOrderNum(processBean.getOrderNum());
			bean.setTraceNum(processBean.getTraceNum());
			bean.setRefundState(processBean.getRefundState());
			bean.setRetCode(processBean.getReqRetCode());
			bean.setRetMsg(processBean.getReqRetMsg());
		} catch (Exception e) {
			logger.error("查询退款结果出现异常.", e);
			PaygateUtil.processException(bean, e);
			return bean;
		}
		return bean;
	}

	// 记录退单查询结果
	protected abstract void recordRefundQueryResponse(RefundQueryProcessBean processBean, RefundQueryResponse queryResponse)
			throws Exception;

	protected RefundQueryResponse remoteRefundQuery(RefundQueryProcessBean queryBean, HttpServletRequest req) {
		RefundQueryRequest request = new RefundQueryRequest(queryBean.getRefundNum());
		request.setPartner(queryBean.getPartner());
		return PaygateServiceClient.refundQuery(request, supportsGateType());
	}

	// 检查退单记录
	protected abstract RefundQueryProcessBean queryRefundRequestData(String refundNum) throws Exception;

}
