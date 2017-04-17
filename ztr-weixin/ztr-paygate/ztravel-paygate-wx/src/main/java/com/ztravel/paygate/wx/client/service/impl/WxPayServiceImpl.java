package com.ztravel.paygate.wx.client.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.annotation.Resource;
import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.swordfish.thrift.client.annotation.ThriftServiceEndpoint;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.HttpUtil;
import com.ztravel.paygate.wx.client.entity.CloseOrderRequest;
import com.ztravel.paygate.wx.client.entity.CloseOrderResponse;
import com.ztravel.paygate.wx.client.entity.QueryOrderRequest;
import com.ztravel.paygate.wx.client.entity.QueryOrderResponse;
import com.ztravel.paygate.wx.client.entity.RefundOrderRequestEntity;
import com.ztravel.paygate.wx.client.entity.RefundOrderResponseEntity;
import com.ztravel.paygate.wx.client.entity.RefundQueryResponseEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderNotifyEntity;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderRequest;
import com.ztravel.paygate.wx.client.entity.WxPayResponse;
import com.ztravel.paygate.wx.client.service.IWxPayService;
import com.ztravel.paygate.wx.converter.WxPayParmConvert;
import com.ztravel.paygate.wx.dao.IWxPayNotifyDao;
import com.ztravel.paygate.wx.dao.IWxRefundDao;
import com.ztravel.paygate.wx.reslover.UnifieldOrderReponseReslover;

@Service("WxPayServiceImpl")
@ThriftServiceEndpoint
public class WxPayServiceImpl implements IWxPayService{

	private static final Logger LOG = LoggerFactory.getLogger(WxPayServiceImpl.class);

	@Resource
	IWxPayNotifyDao wxPayNotifyDao;

	@Resource
	IWxRefundDao wxRefundDao;


	private static final String WX_CLOSE_ORDER_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "close_order_url", ConfScope.R) ;

	private static final String WX_UNIFIED_ORDER_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "place_order_url", ConfScope.R) ;

	private static final String WX_QUERY_ORDER_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "query_order_url", ConfScope.R) ;

	private static final String WX_REFUND_ORDER_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "refund_order_url", ConfScope.R) ;

//	private static final String WX_REFUND_QUERY_URL = TopsConfReader.getConfContent("properties/wx-pay.properties", "refund_query_url", ConfScope.R) ;

	private static final String  APICLIENT_CERT_DIR = TopsConfReader.getConfContent("properties/wx-pay.properties", "apiclient_cert_dir", ConfScope.R) ;

	@Override
	public WxPayResponse unifiedOrder(String requestParms) throws Exception {

		WxPayResponse result = null ;

		try {
			UnifieldOrderRequest wxPayEntity = WxPayParmConvert.buildWxUnifiedlOrderParam(requestParms);

			String wxPayEntityXml = WxPayParmConvert.buildXMlParamByEntity(wxPayEntity, UnifieldOrderRequest.class, "xml");

			String response = HttpUtil.postXml(WX_UNIFIED_ORDER_URL, wxPayEntityXml);

			JSONObject json = JSONObject.parseObject(requestParms) ;

			String wxPayTradeType = json.containsKey("tradeType") ? json.getString("tradeType") : "NATIVE" ;

			result = UnifieldOrderReponseReslover.proceed(response, wxPayTradeType);
		} catch (Exception e) {
			LOG.error(TZMarkers.p2, "微信支付统一下单异常", e);
		}
		if(result == null || result.getResult_code().equals("FAIL")){
			throw new Exception("微信支付统一下单异常") ;
		}else{
			return result;
		}
	}

	@Override
	public boolean closeOrder(String orderId) throws Exception {
		LOG.info("wechat order close ---> orderId:{}", orderId);
		boolean ret = false ;
		try{
			CloseOrderRequest request = WxPayParmConvert.buildWxCloseOrderParam(orderId) ;
			String xml = WxPayParmConvert.buildXMlParamByEntity(request, CloseOrderRequest.class, "xml");
			String xmlResponse = HttpUtil.postXml(WX_CLOSE_ORDER_URL, xml);
			LOG.info("wechat order close response ---> {}", xmlResponse);
			CloseOrderResponse response = WxPayParmConvert.getEntityByXml(xmlResponse, CloseOrderResponse.class);
			String return_code = response.getReturn_code() ;
			String result_code = response.getResult_code() ;
			if(return_code.equals("SUCCESS") && result_code.equals("SUCCESS")){
				ret = true ;
				LOG.info("close order orderId:{} success", orderId);
			}else{
				LOG.info("close order orderId:{} fail", orderId);
			}
		}catch(Exception e){
			LOG.error(TZMarkers.p2, "微信支付关闭订单异常", e);
		}
		return ret ;
	}

	@Override
	public void placePayNotify(
			UnifieldOrderNotifyEntity unifieldOrderNotifyEntity)
			throws Exception {
		try {
			LOG.info("placePayNotify orderId:::{} start", unifieldOrderNotifyEntity.getOut_trade_no());
			wxPayNotifyDao.insert(unifieldOrderNotifyEntity);
			LOG.info("placePayNotify orderId:::{} end", unifieldOrderNotifyEntity.getOut_trade_no());
		} catch (Exception e) {
			LOG.error(TZMarkers.p2, "placePayNotify orderId:::{} error", unifieldOrderNotifyEntity.getOut_trade_no());
		}
	}

	@Override
	public String orderTradeStateQuery(String orderId) throws Exception {
		// TODO Auto-generated method stub
		LOG.info("wechat order query ---> orderId:{}", orderId);
		String tradeState = "";
		try{
			QueryOrderRequest request = WxPayParmConvert.buildWxOrderQueryParam(orderId) ;
			String xml = WxPayParmConvert.buildXMlParamByEntity(request, QueryOrderRequest.class, "xml");
			String xmlResponse = HttpUtil.postXml(WX_QUERY_ORDER_URL, xml);
			LOG.info("wechat order query response ---> {}", xmlResponse);
			QueryOrderResponse response = WxPayParmConvert.getEntityByXml(xmlResponse, QueryOrderResponse.class);
			String return_code = response.getReturn_code() ;
			String result_code = response.getResult_code() ;
			if(return_code.equals("SUCCESS") && result_code.equals("SUCCESS")){
				tradeState = response.getTrade_state();
				LOG.info("query order orderId:{} success", orderId);
			}else{
				LOG.info("query order orderId:{} fail", orderId);
			}
		}catch(Exception e){

			LOG.error(TZMarkers.p2, "微信订单查询异常orderId: "+orderId, e);
		}
		return tradeState;
	}

	@Override
	public WxPayResponse refundOrder(String requestParms) throws Exception {
		WxPayResponse refundResponse = null ;

		try {
			LOG.info("微信退款输入参数：[{}]",requestParms);
			//生成申请退款接口的请求参数
			RefundOrderRequestEntity refundOrder = WxPayParmConvert.buildWxRefundOrderParam(requestParms);
			String refundOrderXml = WxPayParmConvert.buildXMlParamByEntity(refundOrder, RefundOrderRequestEntity.class, "xml");
			LOG.info("微信退款请求报文信息：[{}]",refundOrderXml);
			//调用微信退款接口
			String response = postXml(WX_REFUND_ORDER_URL, refundOrderXml);
			LOG.info("微信申请退款响应http报文:  "+ response);

	        if (StringUtils.isBlank(response)) {
	            refundResponse =  WxPayResponse.instance("FAIL", "http请求无结果");
	        } else {
	            RefundOrderResponseEntity refundOrderResponse = WxPayParmConvert.getEntityByXml(response, RefundOrderResponseEntity.class);

	            if (null != refundOrderResponse && refundOrderResponse.getReturn_code().equals("SUCCESS")) {
	                if (refundOrderResponse.getResult_code().equals("SUCCESS")) {

	                    //申请退款成功，向退款记录表中插入一条记录
	                    recordWxRefundRecord(refundOrderResponse);

	                    refundResponse =  WxPayResponse.instance("SUCCESS", "SUCCESS");

	                } else {
	                    refundResponse = WxPayResponse.instance("FAIL", "微信申请退款业务失败,检查通信报文");
	                }
	            } else {
	                refundResponse = WxPayResponse.instance("FAIL", "微信申请退款通信失败,检查通信报文");
	            }
	        }
	        LOG.info("报文解析结果:  "+ refundResponse.toString());

		} catch (Exception e) {
			LOG.error(TZMarkers.p2, "微信退款申请异常", e);
		}

		if (refundResponse == null || refundResponse.getResult_code().equals("FAIL")) {
			throw new Exception("微信退款申请异常") ;
		}

		return refundResponse;

	}

	private void recordWxRefundRecord(RefundOrderResponseEntity refundOrderResponse) throws Exception {
	    RefundQueryResponseEntity refundQueryResponse = new RefundQueryResponseEntity();
        refundQueryResponse.setAppid(refundOrderResponse.getAppid());
        refundQueryResponse.setMch_id(refundOrderResponse.getMch_id());
        refundQueryResponse.setTransaction_id(refundOrderResponse.getTransaction_id());
        refundQueryResponse.setOut_trade_no(refundOrderResponse.getOut_trade_no());
        refundQueryResponse.setOut_refund_no(refundOrderResponse.getOut_refund_no());
        refundQueryResponse.setRefund_id(refundOrderResponse.getRefund_id());
        refundQueryResponse.setRefund_fee(refundOrderResponse.getRefund_fee());
        refundQueryResponse.setTotal_fee(refundOrderResponse.getTotal_fee());
        refundQueryResponse.setRefund_status("INITIAL");
        refundQueryResponse.setRecord_status("F");

        DateTime currentDateTime = new DateTime();
        refundQueryResponse.setLast_modify_time(currentDateTime);

        int count = wxRefundDao.count(refundQueryResponse);
        if (count == 0) {
            refundQueryResponse.setCreate_time(currentDateTime);
            wxRefundDao.insert(refundQueryResponse);
        } else {
            wxRefundDao.update(refundQueryResponse);
        }

	}

	private String postXml(String postUrl, String reuqestXml) throws Exception {

		InputStream in;
		String result = "";

		KeyStore keyStore  = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(APICLIENT_CERT_DIR));
        try {
            keyStore.load(instream, WechatAccountHolder.MCH_ID.toCharArray());
        } finally {
            instream.close();
        }

        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, WechatAccountHolder.MCH_ID.toCharArray()).build();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[] { "TLSv1" },
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        try {
        	HttpPost httpPost = new HttpPost(postUrl);

            System.out.println("executing request" + httpPost.getRequestLine());
            StringEntity  reqEntity  = new StringEntity(reuqestXml);
            reqEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(reqEntity);

            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
    			in = response.getEntity().getContent();
    			if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK ){
    				result = IOUtils.toString(in);
    			}
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }

        return result;
	}

//    @Override
//    @Transactional(propagation=Propagation.REQUIRED, isolation=Isolation.READ_COMMITTED, rollbackFor=RuntimeException.class)
//    public CommonResponse proceedForRefund(String orderId) throws Exception {
//
//        CommonResponse commonResponse = new CommonResponse();
//
//        RefundQueryResponseEntity oldRefundQueryResponse = null ;//preproceedForRefund(orderId);
//        if (oldRefundQueryResponse != null && oldRefundQueryResponse.getRecord_status() != null && oldRefundQueryResponse.getRecord_status().equals("T")) {
//            LOG.info("order:::{} already cancelled, skip...", orderId);
//            commonResponse.setSuccess(true);
//            commonResponse.setErrMsg("该订单已经取消成功");
//            return commonResponse;
//        }
//
//        //主动向微信发送查询退款接口请求
//        RefundQueryResponseEntity refundQueryResponse = queryRefund(orderId);
//        LOG.info("wechat query refund result:\n" + JSONObject.toJSONString(refundQueryResponse));
//
//       // recordQueryRefundResult(oldRefundQueryResponse, refundQueryResponse);
//
////        boolean needNotify = preproceedForRefund(refundQueryResponse) ;
//
////        if (needNotify) {
//            String return_code = refundQueryResponse.getReturn_code();
//            String result_code = refundQueryResponse.getResult_code();
//            if (return_code.equals("SUCCESS") && result_code.equals("SUCCESS")) {
//                String refund_status = refundQueryResponse.getRefund_status();
//                if (refund_status.equals("SUCCESS")) {
//                    try {
//                        wxRefundDao.lock(refundQueryResponse);
//                        int paymentNotifyResult = 0;//thirdPaymentThriftService.processRefundResult(orderId);
//                        LOG.info("notify refund end, response: {}", paymentNotifyResult);
//                        if (paymentNotifyResult != 2) {
//                            //修改订单状态
//                            commonResponse =  null;//orderThriftClientServiceImpl.updateOrderToCancled(orderId);
//                            LOG.info("notifyOrder end, response: {}", TZBeanUtils.describe(commonResponse));
//                            if (commonResponse.isSuccess()) {
//                                String transaction_id = refundQueryResponse.getTransaction_id();//微信订单号
//                                //记录退款结果
//                                //thirdPaymentThriftService.recordWxRefundTradeResult(orderId, PaymentType.WeChatpay, transaction_id, DateTimeUtil.formatDate(new Date()));
//                                //修改微信退款记录状态:record_status = T
//                                refundQueryResponse.setRecord_status("T");
//                                wxRefundDao.updateRecordStatus(refundQueryResponse);
//                            }
//                        } else {
//                            LOG.info("notify refund fail...");
//                            commonResponse.setSuccess(false);
//                            commonResponse.setErrMsg("微信退款成功,但业务更改出现异常");
//                        }
//                    } catch (Exception e) {
//                        LOG.error(TZMarkers.p2, "wechat refund error", e);
//                        commonResponse.setSuccess(false);
//                        commonResponse.setErrMsg("微信退款成功,但业务更改出现异常");
//                    }
//                } else if (refund_status.equals("FAIL")) {
//                    commonResponse.setSuccess(false);
//                    commonResponse.setErrMsg("退款失败");
//                } else if (refund_status.equals("PROCESSING")) {
//                    commonResponse.setSuccess(false);
//                    commonResponse.setErrMsg("退款处理中");
//                } else if (refund_status.equals("NOTSURE")) {
//                    commonResponse.setSuccess(false);
//                    commonResponse.setErrMsg("未确定，需要商户原退款单号重新发起");
//                } else if (refund_status.equals("CHANGE")) {
//                    commonResponse.setSuccess(false);
//                    commonResponse.setErrMsg("转入代发，退款到银行发现用户的卡作废或者冻结了，导致原路退款银行卡失败，资金回流到商户的现金帐号，需要商户人工干预，通过线下或者财付通转账的方式进行退款");
//                }
//
//            } else {
//                commonResponse.setSuccess(false);
//                commonResponse.setErrMsg("微信查询退款接口请求结果返回失败, transaction_id=" + refundQueryResponse.getTransaction_id());
//            }
////        } else {
////            commonResponse.setSuccess(true);
////            commonResponse.setErrMsg("OK");
////        }
//        LOG.info("wechat refund end, result:::{}", TZBeanUtils.describe(commonResponse));
//        return commonResponse ;
//    }

//    @Override
//    @Transactional(propagation=Propagation.REQUIRES_NEW, isolation=Isolation.READ_COMMITTED, rollbackFor=RuntimeException.class)
//    public boolean preproceedForRefund(RefundQueryResponseEntity refundQueryResponse) {
//
//        boolean needNotify = false ;
//
//        String transactionId = refundQueryResponse.getTransaction_id();
//
//        List<RefundQueryResponseEntity> list = wxRefundDao.selectByTransactionId(transactionId);
//        int count = list.size();
//        if (count == 0) {
//            //退款记录表中没有数据记录时插入一条记录
//            try {
//                wxRefundDao.insert(refundQueryResponse) ;
//            } catch (Exception e) {
//                LOG.error("insert wechat refund record into DB fail:::{}", transactionId, e);
//            }
//            needNotify = true;
//        } else if (count == 1) {
//            //退款记录表中有一条数据记录时判断记录状态,"T"表示该订单已退款成功,"F"表示该订单正在退款处理中
//            String record_status = list.get(0).getRecord_status();
//            if (record_status.equals("T")) {
//                LOG.info("order:::{} already cancelled, skip...", transactionId);
//            } else  if (record_status.equals("F")) {
//                LOG.info("wechat refund already record, so update...");
//                try {
//                    wxRefundDao.update(refundQueryResponse) ;
//                } catch (Exception e) {
//                    LOG.error("update wechat refund record into DB fail:::{}", transactionId, e);
//                }
//                needNotify = true ;
//            } else {
//                LOG.warn("获取退款微信订单号[{}]退款记录时记录状态异常", transactionId);
//            }
//
//        } else {
//            //退款记录表中数据记录多于一条时,警告日志(一般不会出现此情况,查询条件transaction_id字段为主键约束字段)
//            LOG.warn("wechat refund already more than one record, order:::{}", transactionId);
//        }
//
//        return needNotify;
//    }

}
