package com.ztravel.paygate.web.processor;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.api.alipay.AlipayParser;
import com.ztravel.paygate.api.alipay.model.RefundQueryResultModel;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PayState;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.enums.RefundState;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.Alipay;
import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundQueryResponse;
import com.ztravel.paygate.thrift.model.RefundRequest;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.web.dto.middlebean.QueryProcessBean;
import com.ztravel.paygate.web.dto.middlebean.RefundConfirmBean;
import com.ztravel.paygate.web.dto.middlebean.RefundQueryProcessBean;
import com.ztravel.paygate.web.dto.request.RequestPayBean;
import com.ztravel.paygate.web.dto.request.RequestRefundBean;
import com.ztravel.paygate.web.modelbuilder.AlipayModelBuilder;
import com.ztravel.paygate.web.service.IAlipayService;

/**
 * 支付宝用于支付请求和信息反馈的处理器
 *
 * @author dingguangxian
 *
 */
@Component("paygate_alipay_request_processor")
public class AlipayRequestProcessor extends RequestProcessor {
	@Resource
	RequestProcessorFactory processorFactory;

	@Resource
	IAlipayService alipayService;

	private AlipayModelBuilder modelBuilder = new AlipayModelBuilder();

	@PostConstruct
	public void init(){
		processorFactory.registProcessor(this);
	}

	@Override
	public GateType supportsGateType() {
		return GateType.AliPay;
	}

	@Override
	protected String recordPayRequest(RequestPayBean payRequest) {
		Alipay entity = null;
		try {
			entity = alipayService.createEntity();
			entity = AlipayModelBuilder.buildFromPayRequest(entity.getAlipayId(), payRequest);
			alipayService.insertSelective(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		return entity.getAlipayId();
	}

	@Override
	protected void recordSignResult(String entityId, PaySignResponse response) {
		Alipay record = AlipayModelBuilder.buildFromPaySignResponse(entityId, response);
		try {
			alipayService.updateSelectiveById(record);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}

	}

	@Override
	protected RefundConfirmBean validateAndRecordRefundRequest(RequestRefundBean refundRequest) throws PaygateException {
		RefundConfirmBean bean = new RefundConfirmBean();
		String orderNum = refundRequest.getOrderNum();
		String traceNum = refundRequest.getTraceNum();
		try {
			Alipay alipay = alipayService.searchByOrderNumAndTraceNum(orderNum, traceNum);
			// 执行数据校验
			if (alipay == null) {
				throw new PaygateException(PaygateError.E202_ORDER_INVALID);
			}
			if (!StringUtils.equals(alipay.getPayState(), PayState.SUCCESS.name())) {
				// 支付未成功的订单
				throw new PaygateException(PaygateError.E222_PAY_STATE_NOT_SUPPORT_REFUND);
			}
			// TODO 不再判断客户端是否确认成功
			// if(!StringUtils.equalsIgnoreCase(alipay.getConfirmResult(), Const.CLIENT_CONFIRM_SUCCESS_FLAG)){
			// //终端未确认的订单
			// throw new PaygateException(PaygateError.E222_PAY_STATE_NOT_SUPPORT_REFUND);
			// }
			if (!StringUtils.equals(alipay.getClientId(), refundRequest.getClientId())) {
				throw new PaygateException(PaygateError.E221_CLIENTID_NOT_MATCHED);
			}
			if (refundRequest.getTransAmount() != alipay.getTransAmount()) {
				throw new PaygateException(PaygateError.E210_AMOUNT_NOT_MATCHED);
			}
			// 记录该笔退款请求
			AlipayRefund entity = null;
			try {
				entity = alipayService.createRefundEntity();
				entity = AlipayModelBuilder.buildRefundModelFromRefundRequest(entity.getAlipayRefundId(), alipay.getAlipayId(),
						refundRequest);
				alipayService.insertSelective(entity);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new PaygateException(PaygateError.E101_DB_ERROR);
			}
			bean.setEntityId(alipay.getAlipayId());
			bean.setRefundEntityId(entity.getAlipayRefundId());
			bean.setPartner(alipay.getPartner());
			return bean;
		} catch (SQLException e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
	}

	@Override
	protected void recordRefundRequestResult(String entityId, RefundResponse response) {
		// 记录该笔退款请求
		AlipayRefund record = AlipayModelBuilder.buildRefundModelFromRefundResponse(entityId, response);
		try {
			alipayService.updateSelectiveById(record);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}

	}

	/**
	 * 构建退款请求对象
	 *
	 * @param bean
	 * @param refundBean
	 * @param req
	 * @return
	 */
	@Override
	protected RefundRequest buildRefundRequest(RefundConfirmBean bean, RequestRefundBean refundBean, HttpServletRequest req) {
		RefundRequest refundRequest = super.buildRefundRequest(bean, refundBean, req);
		refundRequest
				.setComment(String.format("[%s]%s", refundRequest.getRefundNum(), StringUtils.trimToEmpty(refundRequest.getComment())));
		return refundRequest;
	}

	@Override
	public QueryProcessBean queryRequestData(String orderNum) throws Exception {
		Alipay alipay = alipayService.searchPaySuccessRecord(orderNum);
		if (alipay == null) {
			alipay = alipayService.searchByOrderNum(orderNum);
		}
		if (alipay != null) {
			QueryProcessBean bean = new QueryProcessBean();
			bean.setPayId(alipay.getAlipayId());
			bean.setPartner(alipay.getPartner());
			bean.setClientId(alipay.getClientId());
			bean.setOrderNum(alipay.getOrderNum());
			bean.setPayState(alipay.getPayState());
			// bean.setPayType(alipay.getPayType());
			bean.setTraceNum(alipay.getTraceNum());
			bean.setTransAmount(alipay.getTransAmount());
			bean.setTransDate(alipay.getTransDate());
			bean.setTransTime(alipay.getTransTime());
			bean.setGateType(GateType.AliPay.code);
			// bean.setSignRetCode(alipay.getSignRetCode());
			return bean;
		}
		return null;
	}

	@Override
	protected void recordQueryResult(String entityId, QueryResponse response) {
		logger.info("start 查询结果更新到支付请求中.{},{}", entityId, response);
		try {
			String orderNum = response.getOrderNum();
			Alipay alipay = alipayService.searchPaySuccessRecord(orderNum);
			if (alipay != null) {
				logger.warn("支付成功记录已经存在，不需要处理.{},{}", orderNum, alipay.getAlipayId());
				return;
			}
			Alipay record = AlipayModelBuilder.buildFromQueryResponse(entityId, response);
			alipayService.updateSelectiveById(record);
			logger.info("end 查询结果更新到支付请求中.{},{}", entityId, response);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
	}

	@Override
	public RefundQueryProcessBean queryRefundRequestData(String refundNum) throws Exception {
		AlipayRefund entity = alipayService.searchRefundSuccessRecord(refundNum);
		if (entity == null) {
			entity = alipayService.searchByRefundNum(refundNum);
		}
		if (entity != null) {
			RefundQueryProcessBean bean = new RefundQueryProcessBean();
			bean.setRefundId(entity.getAlipayRefundId());
			bean.setPartner(entity.getPartner());
			bean.setClientId(entity.getClientId());
			bean.setRefundNum(entity.getRefundNum());
			bean.setOrderNum(entity.getOrderNum());
			bean.setTraceNum(entity.getTraceNum());
			bean.setTransAmount(entity.getTransAmount());
			bean.setRefundAmount(entity.getRefundAmount());
			bean.setReqRetCode(entity.getReqRetCode());
			bean.setReqRetMsg(entity.getReqRetMsg());
			bean.setGateType(GateType.AliPay);
			if (entity.getRefundState() != null) {
				bean.setRefundState(RefundState.valueOf(entity.getRefundState()));
			}
			return bean;
		}
		return null;
	}

	@Override
	protected void recordRefundQueryResponse(RefundQueryProcessBean processBean, RefundQueryResponse queryResponse) throws Exception {
		logger.info("start 查询结果更新到支付请求中.{},{}", TZBeanUtils.describe(processBean), TZBeanUtils.describe(queryResponse));
		try {
			String refundNum = processBean.getRefundNum();
			AlipayRefund entity = alipayService.searchRefundSuccessRecord(refundNum);
			if (entity != null) {
				processBean.setRefundId(entity.getAlipayRefundId());
				processBean.setRefundState(RefundState.valueOf(entity.getRefundState()));
				logger.warn("退单成功记录已经存在，不需要处理.{},{}", refundNum, entity.getAlipayRefundId());
				return;
			}

			String refundDetails = queryResponse.getExtraInfos().get("result_details");
			List<RefundQueryResultModel> list = AlipayParser.parseRefundQueryResultDetails(refundDetails);
			RefundQueryResultModel model = null;
			if (list != null && list.size() == 1) {
				model = list.get(0);
			}

			boolean refundSuccess = model != null && model.getAmount() == processBean.getRefundAmount()
					&& "SUCCESS".equalsIgnoreCase(model.getTransRetMsg()) /*&& "SUCCESS".equalsIgnoreCase(model.getRefundRetMsg())*/;

			processBean.setRefundState(refundSuccess ? RefundState.SUCCESS : RefundState.FAIL);

			// 在客户端未确认成功的情况下，无论是否已经接收过该消息，都需要进行根据返回信息更新数据库
			AlipayRefund record = new AlipayRefund();
			record.setAlipayRefundId(processBean.getRefundId());
			record.setTransRetMsg(model == null ? null : model.getTransRetMsg());
			record.setRefundRetMsg(model == null ? null : model.getRefundRetMsg());
			record.setSuccessNum((short) (refundSuccess ? 1 : 0));
			record.setResultDetails(refundDetails);
			record.setRespTime(new Date());
			record.setValsignRetCode(PaygateError.SUCCESS.code());
			record.setValsignRetMsg(PaygateError.SUCCESS.desc());
			record.setRefundState(processBean.getRefundState().name());
			alipayService.updateSelectiveById(record);
			logger.info("end 查询结果更新到退单请求中.{},{}", processBean.getRefundId(), TZBeanUtils.describe(queryResponse));
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
	}
}
