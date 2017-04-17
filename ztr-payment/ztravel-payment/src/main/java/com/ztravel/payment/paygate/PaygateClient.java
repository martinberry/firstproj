package com.ztravel.payment.paygate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.RandomUtil;
import com.ztravel.common.enums.GateType;
import com.ztravel.payment.http.HttpRequest;
import com.ztravel.payment.http.HttpRequestType;
import com.ztravel.payment.paygate.model.RefundRequestBean;
import com.ztravel.payment.paygate.model.RequestBean;
import com.ztravel.payment.paygate.model.ResponseBean;
import com.ztravel.payment.po.Trade;
/**
 * @author zuoning.shen
 *
 */
@Component
public class PaygateClient {
	private static Logger logger = LoggerFactory.getLogger(PaygateClient.class);
	/**
	 * 请求地址
	 */
	private static Map<Service,String> requestUrls = new HashMap<>();
	@Value("#{paygateProps.clientId}")
	private String clientId;//客户端标识
	@Value("#{paygateProps.signKey}")
	private String signKey;//验签key
	
	@Resource(name="paygateProps")
	private Properties properties;
	/**
	 * 网关提供的服务
	 * @author dingguangxian
	 *
	 */
	public static enum Service{
		PAY("付款"),REFUND("退款"),MOBILE_PAY("手机支付"),
		QUERY_PAY("支付查询"),QUERY_REFUND("退款查询"),
		;
		private String description;
		private Service(String description){
			this.description = description;
		}
		public String getDescription(){
			return description;
		}
	}
	/**
	 * 发送请求
	 * @param service
	 * @param request
	 * @return
	 */
	public <T> T sendRequest(Service service, RequestBean request, Class<T> clazz){
		String responseText = sendRequest(service, request);
		return new Gson().fromJson(responseText, clazz);
	}
	
	/**
	 * 发送请求
	 * @param service
	 * @param request
	 * @return
	 */
	public String sendRequest(Service service, RequestBean request){
		Map<String,String> params = BeanMapUtil.mapBean(request);
		return sendRequest(service, params);
	}
	
	/**
	 * 对请求参数进行签名
	 * @param params
	 * @return
	 */
	public String getSignStr(Map<String, String> params) {
		return PaygateEncryptUtil.getSignStr(params, signKey);
	}
	
	/**
	 * 发送请求
	 * @see PaygateClient#sendRequest(Service, com.travelzen.tfc.base.paygate.model.RequestBean)
	 * @see PaygateClient#sendRequest(Service, com.travelzen.tfc.base.paygate.model.RequestBean, Class)
	 * @param service
	 * @param request
	 * @return
	 */
	public String sendRequest(Service service, Map<String,String> params){
		params.put("clientId", clientId);
		String sign = PaygateEncryptUtil.getSignStr(params, signKey);
		params.put("sign", sign);
		String requestUrl = requestUrls.get(service);
		try {
			logger.info("发送网关请求:Service:{}, requestUrl:{}, params:{}",service,requestUrl,params);
			String response = HttpRequest.sendRequest(requestUrl, params, HttpRequestType.POST);
			if(StringUtils.isNotBlank(response)){
				logger.info("请求网关\"{}\"返回的结果\n:{}", service, response.length() > 500 ? response.substring(0,500)+"..." : response);
			} else {
				logger.warn("网关\"{}\"(url:{})无返回结果.",service, requestUrl);
			}
			return response;
		} catch (Exception e) {
			logger.error("请求网关\"{}\"(url:{})出现异常", service, requestUrl);
			logger.error("请求网关出现异常.", e);
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String args[]){
		Map<String,String> params = Maps.newHashMap() ;
		
		RefundRequestBean refundBean = new RefundRequestBean();
	      refundBean.setGateType(GateType.Alipay.getGateCode());
	      refundBean.setOrderNum("aaaaaa");
	      refundBean.setRefundAmount(1);
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      String refundTime = sdf.format(new Date());
	      refundBean.setRefundTime(refundTime);
	      refundBean.setRefundNum(DateTimeUtil.datetime14() + RandomUtil.getRandomStr(4));
	      String traceNum = "2015120100001000620065005450";
	      refundBean.setTraceNum(traceNum);
	      refundBean.setTransAmount(1);
	      params = BeanMapUtil.mapBean(refundBean);
	      params.put("clientId", "900001");
			String sign = PaygateEncryptUtil.getSignStr(params, "44598166726f4eb5a222a1e90d717415");
			params.put("sign", sign);
			String requestUrl = "http://192.168.164.135:8580/ztravel-paygate-web/paygate/reqRefund";
			try {
				String response = HttpRequest.sendRequest(requestUrl, params, HttpRequestType.POST);
				if(StringUtils.isNotBlank(response)){
					logger.info("请求网关\"{}\"返回的结果\n:{}",  response.length() > 500 ? response.substring(0,500)+"..." : response);
				} else {
					logger.warn("网关\"{}\"(url:{})无返回结果.", requestUrl);
				}
				System.out.println(response);
			} catch (Exception e) {
				logger.error("请求网关\"{}\"(url:{})出现异常", requestUrl);
				logger.error("请求网关出现异常.", e);
				throw new RuntimeException(e);
			}
	}
	
	/**
	 * 对返回结果进行验签
	 * @param response
	 * @return
	 */
	public boolean verifyResponse(ResponseBean response){
		Map<String,String> params = BeanMapUtil.mapBean(response);
		return PaygateEncryptUtil.verifySignStr(params, response.getSign(), signKey);
	}

	/**
	 * 对返回结果进行验签
	 * @param response
	 * @return
	 */
	public boolean verifyResponse(Map<String,String> response){
		return PaygateEncryptUtil.verifySignStr(response, response.get("sign"), signKey);
	}
	
	@PostConstruct
	public void init(){
		if(StringUtils.isBlank(clientId) || StringUtils.isBlank(signKey)){
			logger.error("参数配置出错,clientId或signKey未配置.");
			throw new RuntimeException("参数配置出错,clientId或signKey未配置.");
		}
		requestUrls.put(Service.PAY, StringUtils.trimToNull(properties.getProperty("payUrl")));//支付
		requestUrls.put(Service.REFUND, StringUtils.trimToNull(properties.getProperty("refundUrl")));//退款
		requestUrls.put(Service.MOBILE_PAY, StringUtils.trimToNull(properties.getProperty("mobilePayUrl")));//手机支付
		
		requestUrls.put(Service.QUERY_PAY, StringUtils.trimToNull(properties.getProperty("queryUrl")));//支付查询
		requestUrls.put(Service.QUERY_REFUND, StringUtils.trimToNull(properties.getProperty("refundQueryUrl")));//退款查询
		
		for(Service service : Service.values()){
			if(StringUtils.isBlank(requestUrls.get(service))){
				logger.error("参数配置出错,服务({})请求地址未配置.", service);
				throw new RuntimeException(String.format("参数配置出错,服务(%s)请求地址未配置.", service.name()));
			}
			logger.info("网关服务({})地址:{}", service, requestUrls.get(service));
		}
	}
}
