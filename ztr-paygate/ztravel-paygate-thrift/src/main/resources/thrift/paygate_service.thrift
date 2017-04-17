namespace java com.ztravel.paygate.thrift.service
include "paygate_model.thrift"
/**
 * 支付网关的thrift服务,目前可以满足汇付天下，财付通，支付宝三种支持方式
 */
service PaygateService{
	/** 单笔支付订单签名 */
	paygate_model.PaySignResponse paySign(1: paygate_model.PaySignRequest signRequest),
	/** 单笔支付订单结果验签 */
	paygate_model.ValSignResponse payValSign(1: paygate_model.ValSignRequest valSignRequest),
	/** 支付查询 */
	paygate_model.QueryResponse query(1: paygate_model.QueryRequest queryRequest),
	
	/** 订单退款 */
	paygate_model.RefundResponse refund(1: paygate_model.RefundRequest refundRequest),
	/** 退单查询 */
	paygate_model.RefundQueryResponse refundQuery(1: paygate_model.RefundQueryRequest refundQueryRequest),
	/** 订单退款结果验签 */
	paygate_model.RefundValSignResponse refundValSign(1: paygate_model.RefundValSignRequest valSignRequest),
}

