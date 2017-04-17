package com.ztravel.paygate.web.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ztravel.paygate.api.alipay.AlipayParser;
import com.ztravel.paygate.api.alipay.model.RefundProfitResultModel;
import com.ztravel.paygate.api.alipay.model.RefundResultModel;
import com.ztravel.paygate.api.alipay.model.RefundUnfreezedModel;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.enums.RefundState;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.core.po.gen.AlipayRefund;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.web.dto.middlebean.RefundResponseConfirmBean;
import com.ztravel.paygate.web.processor.AlipayRequestProcessor;
import com.ztravel.paygate.web.processor.RequestProcessor;
import com.ztravel.paygate.web.service.IAlipayService;
import com.ztravel.paygate.web.util.EnvArgs.Const;

@Controller
@RequestMapping("/paygate/alipay/")
public class AlipayRefundNotifyController extends RefundNotifyController {

	private static final String ACK_CONTENT_SUCCESS = "success";
	@Resource
	AlipayRequestProcessor requestProcessor;
	@Resource
	IAlipayService alipayService;

	public AlipayRefundNotifyController() {
		super(GateType.AliPay);
	}

	@Override
	protected RefundResponseConfirmBean buildConfirmBeanFromResponse(String entityId, Map<String, String> responseData) {

		AlipayRefund entity;
		try {
			entity = alipayService.findByRefundId(entityId);
		} catch (SQLException e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
		// if (entity == null
		// || !PaygateError.SUCCESS.code().equals(entity.getReqRetCode())) {
		// // 保证是请求成功的记录.
		if (entity == null) {// bugfix:请求时由于server未返回，导致未记录到请求结果
			logger.warn("接收到的退款信息(id={})在数据库中未找到对应的记录,丢弃.", entityId);
			return null;
		}

		RefundResponseConfirmBean bean = new RefundResponseConfirmBean();
		bean.setEntityId(entity.getAlipayRefundId());
		bean.setClientId(entity.getClientId());
		bean.setPartner(entity.getPartner());
		bean.setRefundNum(entity.getRefundNum());
		bean.setOrderNum(entity.getOrderNum());
		bean.setTraceNum(entity.getTraceNum());
		bean.setRefundAmount(entity.getRefundAmount());
		// bean.setPayType(entity.getPayType());

		bean.setAckContent(ACK_CONTENT_SUCCESS);
		String refundDetails = responseData.get("result_details");
		List<RefundResultModel> list = AlipayParser.parserRefundResultDetails(refundDetails);
		RefundResultModel model = null;
		if (list != null && list.size() == 1) {
			model = list.get(0);
		}
		//退单返回的退分润信息
		StringBuilder refundProfits = new StringBuilder();
		for(RefundProfitResultModel m : model.getRefundProfits()){
			refundProfits.append(m.getPartnerAccount()).append("|").append(m.getShareAccount()).
				append("|").append(m.getAmount()).append("|").append(m.getStatus());
		}
		if(StringUtils.isNotBlank(refundProfits)){
			bean.setRefundShareProfits(refundProfits.toString());
		}
		//退单返回的解冻信息
		String unfreezedDetails = responseData.get("unfreezed_details");
		if(StringUtils.isNotBlank(unfreezedDetails)){

			RefundUnfreezedModel um = AlipayParser.parseRefundUnfreezedModel(unfreezedDetails);
			if(um != null){
				StringBuilder detail = new StringBuilder();
				detail.append(um.getUnfreezeNum());
				detail.append("|").append(um.getFreezeNum());
				detail.append("|").append(um.getUnfreezeAmount());
				detail.append("|").append(um.getTraceNum());
				detail.append("|").append(um.getTransTime());
				detail.append("|").append(um.getState());
				detail.append("|").append(um.getMsg());
				bean.setUnfreezeDetails(detail.toString());
			}
		}

		boolean refundSuccess = model != null && model.getAmount() == bean.getRefundAmount()
				&& "SUCCESS".equalsIgnoreCase(model.getTransRetMsg()) /*&& "SUCCESS".equalsIgnoreCase(model.getRefundRetMsg())*/;

		bean.setRefundState(refundSuccess && "1".equals(responseData.get("success_num")) ? RefundState.SUCCESS : RefundState.FAIL);

		if (RefundState.SUCCESS.name().equalsIgnoreCase(entity.getRefundState())
				&& Const.CLIENT_CONFIRM_SUCCESS_FLAG.equalsIgnoreCase(entity.getConfirmResult())) {
			// 客户端已经确认成功，可能是重复返回的信息
			logger.warn("接收到的退款信息(id={})已经处理过,丢弃.", entityId);
			bean.setProcessed(true);
		} else {
			// 在客户端未确认成功的情况下，无论是否已经接收过该消息，都需要进行根据返回信息更新数据库
			try {
				alipayService.recordRefundNotify(entity.getAlipayRefundId(), responseData);
			} catch (Exception e) {
				logger.error("", e);
				throw new PaygateException(PaygateError.E101_DB_ERROR);
			}
		}
		return bean;
	}

	@Override
	protected void processValSignResult(RefundValSignResponse valSignResponse, RefundResponseConfirmBean confirmBean) {
		AlipayRefund record = new AlipayRefund();
		record.setAlipayRefundId(confirmBean.getEntityId());
		record.setValsignRetCode(valSignResponse.getRetCode());
		record.setValsignRetMsg(valSignResponse.getRetMsg());
		if (confirmBean.getRefundState() != null) {
			record.setRefundState(confirmBean.getRefundState().name());
		}
		try {
			alipayService.updateSelectiveById(record);
		} catch (SQLException e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
	}

	@Override
	protected void recordConfirmResult(RefundResponseConfirmBean confirmBean, String clientConfirmResult) {
		AlipayRefund record = new AlipayRefund();
		record.setAlipayRefundId(confirmBean.getEntityId());
		record.setAckContent(confirmBean.getAckContent());
		record.setConfirmResult(clientConfirmResult);
		record.setCompleteTime(new Date());
		try {
			alipayService.updateSelectiveById(record);
		} catch (SQLException e) {
			logger.error("", e);
			throw new PaygateException(PaygateError.E101_DB_ERROR);
		}
	}

	@Override
	public RequestProcessor getRequestProcessor() {
		return requestProcessor;
	}

}
