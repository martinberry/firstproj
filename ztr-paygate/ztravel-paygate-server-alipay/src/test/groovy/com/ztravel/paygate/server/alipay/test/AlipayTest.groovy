package com.ztravel.paygate.server.alipay.test;

import org.apache.commons.io.IOUtils
import org.joda.time.DateTime
import org.junit.Test

import com.travelzen.framework.core.time.DateTimeUtil
import com.travelzen.framework.core.util.RandomUtil
import com.ztravel.paygate.api.alipay.model.QueryResultModel
import com.ztravel.paygate.server.alipay.AlipayEnv
import com.ztravel.paygate.thrift.model.ValSignRequest
/**
 * 支付宝接口测试
 * @author dingguangxian
 */
public class AlipayTest {
	static AlipayEnv alipayEnv = AlipayEnv.instance();
	String partner = "2088101964737404";
	String signKey = "kqyvoebvumo8jcar2zzo2qifipe20k9j";
	def alipayUrl = "https://mapi.alipay.com/gateway.do?_input_charset=utf-8";
	
	/** 请求支付宝 */
	def requestAlipay(Map<String,String> params){
		if(!params.containsKey("partner")){
			params.put("partner", partner);
		}
		if(!params.containsKey("_input_charset")){
			params.put("_input_charset", Args.CHARSET_UTF8);
		}
		def linkStr = AlipayCore.createLinkString(params);
		def sign = AlipaySignMD5.sign(linkStr, signKey, Args.CHARSET_UTF8);
		params.put("sign", sign);
		params.put("sign_type", "MD5");
		println "请求支付宝参数:$params";
		return AlipayCore.reqAlipay(alipayUrl, params, Args.CHARSET_UTF8);
	}
	@Test
	public void testRefund() {
		String params = "sign=b614db449ac38c607b2846a1b3b7325e, _input_charset=utf-8, sign_type=MD5, detail_data=2013091141206212^0.01, service=refund_fastpay_by_platform_pwd, notify_url=http://180.169.46.150:8280/ztravel-paygate-web/demo/bgNotify.jsp, partner=2088101964737404, seller_email=bycoa@10106266.com, batch_num=1, batch_no=201309130002, refund_date=2013-09-13 13:13:12";
		String values[] = params.split(",");
		ValSignRequest request = new ValSignRequest();
		Map<String, String> map = new HashMap<>();
		request.setBankResponseData(map);
		for (String value : values) {
			String[] pair = value.split("=");
			String k = pair[0].trim();
			String v = "";
			if (pair.length > 1) {
				v = pair[1].trim();
			}
			map.put(k, v);
		}
		System.out.println("处理结果数据:" + map);
		// String refundUrl = AlipayEnv.getArgs(Args.URL_REFUND);
		String response = AlipayCore.reqAlipay("https://mapi.alipay.com/gateway.do?_input_charset=utf-8", map, "utf-8");
		System.out.println(response);
	}

	/**
	 * 单笔交易查询接口
	 */
	@Test
	public void testQuery() {
		def params = [
			"service" : Args.TT_QUERY,
			"partner" : partner,
			"_input_charset" : Args.CHARSET_UTF8,
			"out_trade_no" : "RC140305155401003505",
			
			];
		// params.put("trade_no", "2014030512748312");
		String response = requestAlipay(params);
		println "查询订单支付返回结果:$response";

		Map<String, String> responseData = new HashMap<String, String>();
		QueryResultModel model = AlipayParser.parseQueryResult(response);
		responseData.putAll(model.requestDatas());
		responseData.putAll(model.tradeDatas());
		responseData.put("is_success", model.is_success);
		responseData.put("sign", model.sign);
		responseData.put("sign_type", model.sign_type);
		System.out.println(AlipayCore.createLinkString(AlipayCore.paraFilter(model.tradeDatas())));
		boolean verify = AlipaySignMD5.verify(AlipayCore.createLinkString(AlipayCore.paraFilter(model.tradeDatas())), model.sign, signKey,
				Args.CHARSET_UTF8);
		if (verify) {// 验签成功
			System.out.println("验签成功");
		} else {
			System.out.println("验签失败");
		}
	}

	/**
	 * 退款查询
	 */
	@Test
	public void testQueryRefund() {
		def params=[
			"service": "refund_fastpay_query",
			"partner": partner,
			"_input_charset": Args.CHARSET_UTF8,
//			"trade_no": "2014032638780412",
//			"batch_no": "20140331284",//退交易+退分润,失败
//			"batch_no": "20140331223",//退分润不退交易,失败
			"batch_no": "20140331057",//退分润不退交易,成功
			];
		// params.put("trade_no", "2013091142096412");
		String response = requestAlipay(params);
		println("退款查询结果:" + response);
	}

	/**
	 * 对账文件下载
	 */
	@Test
	public void testDownloadBills() throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("service", "account.page.query");
		params.put("partner", alipayEnv.getPartnerArgs(partner, Args.PATNER));
		params.put("_input_charset", Args.CHARSET_UTF8);

		params.put("page_no", "1");
		params.put("gmt_start_time", "2013-12-03 21:45:00");
		params.put("gmt_end_time", "2013-12-03 21:59:00");
		// params.put("trans_code", "5103");

		// 分页大小
		// params.put("page_size", "5000");

		String response = requestAlipay(params);
		System.out.println("支付宝对账单下载接口返回结果.....");
		// System.out.println(response);
		IOUtils.copy(new StringReader(response), new FileOutputStream("alipay_bills_refund.txt"));

	}
 	/**
 	 * 签约
 	 */
	@Test
	public void testSignPartner() throws Exception {
		def params = [
			"service":"sign_protocol_with_partner",
			"partner":partner,
			"_input_charset":Args.CHARSET_UTF8,
			"email":"jscs1@10106266.com",
//			"sign_channel":"normal"//normal不显示自动支付按钮
		];

		String linkStr = AlipayCore.createLinkString(params);
		String sign = AlipaySignMD5.sign(linkStr, signKey, Args.CHARSET_UTF8);
		params.put("sign", sign);
		params.put("sign_type", alipayEnv.getPartnerArgs(partner, Args.SIGN_TYPE));

		StringBuilder url = new StringBuilder("https://mapi.alipay.com/gateway.do?");
		params.each{k,v->
			url.append("$k=$v&");
		}
		System.out.println("签约URL:::" + url);
	}
	
	/**
	 * 解除签约
	 */
   @Test
   public void testUnsignPartner() throws Exception {
	   def params = [
		   "service":"customer_unsign",
		   "partner":partner,
		   "_input_charset":Args.CHARSET_UTF8,
		   "user_email":"jscs1@10106266.com",
		   "biz_type":"10004",
	   ];

	   def response = AlipayCore.reqAlipay(alipayUrl,params,Args.CHARSET_UTF8);
	   println "支付宝解约返回结果：$response";

   }
 	/**
 	 * 签约查询
 	 */
 	@Test
 	public void testSignQuery() throws Exception {
		def params = [
			"service":"query_customer_protocol",
			"partner":partner,
			"_input_charset":Args.CHARSET_UTF8,
			"biz_type":"10004",
			"user_email":"jscs1@10106266.com"
		];
		String response = requestAlipay(params);
 		println("签约查询返回结果:::" + response);
 	}
	 
	 /***
	  * 分润请求
	  * */
	@Test
	 void shareProfit() {
		 def params = [
			 "service":"distribute_royalty",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "royalty_type":"10",
			 "out_bill_no":RandomUtil.getRandomStr(10),
			 "royalty_parameters":"jscs1@10106266.com^0.04^",
			 "trade_no":"2014032638780412",
			 "out_trade_no":"20140326205335448436",
			 ]
		 String response = requestAlipay(params);
		 println "分润请求返回结果:$response";
	 }
	 
	 /**
	  * 交易退款委托冻结
	  * */
	 @Test
	 void refundFreeze() {
		 def params = [
			 "service":"air_trade_refund_freeze",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "notify_url":"http://test.tdxinfo.com/notify_freeze_result.jsp",
			 "trade_no":"2014032638780412",//支付宝交易号
			 "freeze_details":"freeze0002^jscs1@10106266.com^^0.04",
			 ];
		 
		 String response = requestAlipay(params);
		 println "退款冻结请求返回结果:$response";
	 }
 
	 /***
	  * 委托冻结查询类
	  * */
	 @Test
	 void refundFreezeQuery() {
		 def params = [
			 "service":"air_trade_refund_freeze_query",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "freeze_order_no":"freeze0001",//冻结请求单号
			 ];
		 String response = requestAlipay(params);
		 println "退款冻结查询返回结果:$response";
	 }
 
	 /***
	  * 交易委托解冻接口类
	  * */
	 @Test
	 void refundUnfreeze() {
		 def params = [
			 "service":"air_trade_refund_unfreeze",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "unfreeze_details":"unfreeze001^freeze0001^0.01",//
			 "notify_url":"http://test.tdxinfo.com/notify_unfreeze_result.jsp",//支付宝交易号
			 ];
		 String response = requestAlipay(params);
		 println "退款解冻请求返回结果:$response";
	 }
 
	 /***
	  * 交易委托解冻查询接口类
	  * */
	 @Test
	 void refundUnfreezeQuery() {
		 def params = [
			 "service":"air_trade_refund_unfreeze_query",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "unfreeze_order_no":"unfreeze001",//解冻请求单号
			 ];
		 String response = requestAlipay(params);
		 println "退款解冻查询返回结果:$response";
	 }
	 @Test
	 public void testRefundWithFreeze() {
		 def params = [
			 	"service":"refund_fastpay_by_platform_nopwd",
			 	"detail_data":'2014032638780412^0.02^测试冻结转退款$^2088101964737404^0.02^测试退收费|^2088101964737404^jscs1@10106266.com^^0.02^测试退分润',
				"notify_url":"http://180.169.46.150:8280/ztravel-paygate-web/demo/bgNotify.jsp",
				"batch_no":new DateTime().toString("yyyyMMdd")+RandomUtil.getRandomStr(3),
				"batch_num":"1",
				"refund_date":new DateTime().toString("yyyy-MM-dd HH:mm:ss"),
				"use_freeze_amount":"Y"
			 ];
		 def response = requestAlipay(params);
	  	 println "退款（优先使用冻结）请求返回结果:$response";
	 }
	 /**
	  * 单独退分润
	  */
	 @Test
	 public void testRefundProfit() {
		 def params = [
				 "service":"refund_fastpay_by_platform_nopwd",
				 "detail_data":'2014032638780412^0^测试单独退分润|jscs1@10106266.com^^^2088101964737404^0.02^测试单独退分润',
				"notify_url":"http://180.169.46.150:8280/ztravel-paygate-web/demo/bgNotify.jsp",
				"batch_no":new DateTime().toString("yyyyMMdd")+RandomUtil.getRandomStr(3),
				"batch_num":"1",
				"refund_date":new DateTime().toString("yyyy-MM-dd HH:mm:ss"),
				"use_freeze_amount":"Y"
			 ];
		 def response = requestAlipay(params);
		   println "退款（优先使用冻结）请求返回结果:$response";
	 }
	 /***
	  * CAE代扣
	  * */
	@Test
	 void caeChargeAgent() {
		 String outOrderNO = RandomUtil.getRandomStr(10); 
		 def params = [
			 "service":"cae_charge_agent",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "notify_url":"http://180.169.46.150:8280/ztravel-paygate-web/paygate/alipay/payResult/0/" + outOrderNO,
			 "out_order_no":outOrderNO,
			 "amount":"0.03",
			 "subject":"测试代扣功能",
			 "trans_account_out":"jscs1@10106266.com",
			 "trans_account_in":"bycoa@10106266.com",
			 "charge_type":"trade",
			 "type_code":partner+"1000310004",
			 "royalty_type":"10",
			 "royalty_parameters":"jscs1@10106266.com^0.01^CAE代扣分润",
			 "gmt_out_order_create":DateTimeUtil.datetime14Readable(),
			 ]
		 String response = requestAlipay(params);
		 println "CAE代扣请求返回结果:$response";
	 }
	 /***
	  * 批量付款到支付宝账户(无密接口)
	  * 
	  */
	@Test
	 void accountDirectPay() {
		 String outOrderNO = RandomUtil.getRandomStr(10);
		 def params = [
			 "service":"batch_trans_notify_no_pwd",
			 "partner":partner,
			 "_input_charset":Args.CHARSET_UTF8,
			 "notify_url":"http://180.169.46.150:8280/ztravel-paygate-web/paygate/alipay/payResult/0/" + outOrderNO,
			 "account_name":"上海不夜城国际旅行社有限公司",
			 "batch_no":"ADP"+RandomUtil.getRandomStr(10),
			 "detail_data":RandomUtil.getRandomStr(6)+'^jscs1@10106266.com^上海不夜城国际旅行社有限公司^0.01^测试批量付款接口',
			 "batch_num":"1",
			 "batch_fee":"0.01",
			 "pay_date":DateTime.now().toString("yyyyMMdd"),
			 "email":"bycoa@10106266.com",
			 "buyer_account_name":"bycoa@10106266.com",
//			 "extend_param":"",//业务扩展参数
			 ]
		 String response = requestAlipay(params);
		 println "批量付款到支付宝账户(无密接口)请求返回结果:$response";
	 }
	/**
	 * 手机支付签名 
	 */
	@Test
	void mobileSign(){
		
		def params=[
			"partner":"2088111008275759",
			"seller_id":"2088111008275759",
			"out_trade_no":RandomUtil.getRandomStr(10),
			"subject":"《暗黑破坏神3:凯恩之书》",
//			"body":"暴雪唯一官方授权中文版!玩家必藏!附赠暗黑精致手绘地图!绝不仅仅是一本暗黑的故事或画册，而是一个栩栩如生的游戏再现。是游戏玩家珍藏的首选。",
			"total_fee":"0.01",
			"notify_url":"http://notify.java.jpxx.org/index.jsp",
			"service":"mobile.securitypay.pay",
			"payment_type":"1",
			"_input_charset":Args.CHARSET_UTF8,
			"it_b_pay":"30m",
			]
		def presign = new StringBuilder();
		params.eachWithIndex {k,v,i->
			if(i!=0){
				presign.append("&");
			}
			presign.append("$k=\"$v\"");
		}
		def privateKey = 
			["MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL9/WV+0MvUE3AxW",
			"EYs3iVJ5kbewhoHdFfe5uJtHTD6mnsPoJSdu5dLLU5gA+p/PIPNyJn3cMZgsFjEj",
			"5ukD3aC+DXE25nCRw1+laFZ1a1ZXi/UKV4T5O8/rNEXoq1cqclgm29nTXCrRAXQK",
			"0Uw74V706c1Dpr4JrDf0YQJlZn2xAgMBAAECgYEAtLHmE3hl3O2I9dsBEuyEl14/",
			"MaVQRpnncKm+mEbemv9+MD2NUXhDRHS3nGbQFpvL6+t6hqiP45c2GYhXCuiq5q/X",
			"WbGoH0AkZoBjED5aVDs1opd3ZEIVhYuo0tJQjVqNrZ1djSUl7MTIS9dtbbVq/Z1A",
			"ZGe1y5T1Pd2zmmwyVUECQQDq/9T1rEKqzHKUc1KBdSpSPlGjqfzvx6F7k1DNUsop",
			"KxyMKJXnrlWMsmYkVrM3ZM3ca8ya1P6oTxpiySILCEoDAkEA0JxO67W0ZGY9hcG/",
			"IORHFozFD4lzZgKwQKyv+iXhzn8JFlDLwiL+4nVFAmRSRCtIiKMWatjSNoGIhttn",
			"JJklOwJAcN5hdYHAH3LE0aI2DXGt2wSNh5Mcpjn2yHiwBX3vFSjctEDGBxnHgyUo",
			"Q+nUeBWVfnnUMsV8bnBX1vyNukjH6wJAG+9Sgi3q4ibuxiKTvYvUGSjwmowFb/uc",
			"LozxY8X+3vAT/3ECWBTnnNunShK1EXvaUrHEJrqyK6pc5fIrdbMsUQJBAOCFgQNS",
			"qH5MTV51hSPoGPHKCZ5Z7XkW2deSvcGMZW+3i66C6obIvhM9D+0TdcPXLRdxHVi6",
			"nPi/6NJEX/TyCg0="].join("");
		println "预签名串:$presign"
		def sign = Rsa.sign(presign.toString(), privateKey);
		sign = URLEncoder.encode(sign,"UTF-8");
		presign += "&sign=\"$sign\"&sign_type=\"RSA\"";
		println "支付URL:$presign"
	}
}
