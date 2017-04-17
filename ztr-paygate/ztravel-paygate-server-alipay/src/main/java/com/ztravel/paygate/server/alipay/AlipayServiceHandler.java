package com.ztravel.paygate.server.alipay;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.ztravel.paygate.thrift.model.PaySignRequest;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryRequest;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundQueryRequest;
import com.ztravel.paygate.thrift.model.RefundQueryResponse;
import com.ztravel.paygate.thrift.model.RefundRequest;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.thrift.model.RefundValSignRequest;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.thrift.model.ValSignRequest;
import com.ztravel.paygate.thrift.model.ValSignResponse;
import com.ztravel.paygate.thrift.service.PaygateService;;

/**
 * 支付宝支付服务
 * 
 * @author dingguangxian
 * 
 */
@Service("paygate_alipay_service_handler")
public class AlipayServiceHandler implements PaygateService.Iface {

	@Resource
	AlipayMobileServiceHandler payServiceHandler;
	
	@Override
	public PaySignResponse paySign(PaySignRequest signRequest)
			throws TException {
		return payServiceHandler.paySign(signRequest);
	}

	@Override
	public ValSignResponse payValSign(ValSignRequest valSignRequest)
			throws TException {
		return payServiceHandler.payValSign(valSignRequest);
	}

	@Override
	public QueryResponse query(QueryRequest queryRequest) throws TException {
		return payServiceHandler.query(queryRequest);
	}

	@Override
	public RefundResponse refund(RefundRequest refundRequest) throws TException {
		return payServiceHandler.refund(refundRequest);
	}

	@Override
	public RefundQueryResponse refundQuery(RefundQueryRequest refundQueryRequest)
			throws TException {
		return payServiceHandler.refundQuery(refundQueryRequest);
	}

	@Override
	public RefundValSignResponse refundValSign(
			RefundValSignRequest valSignRequest) throws TException {
		return payServiceHandler.refundValSign(valSignRequest);
	}

}
