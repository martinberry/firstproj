package com.ztravel.common.adapter.sms;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.logger.core.TZMarkers;
import com.ztravel.common.util.SimpleHttpClient;


/**
 * 短信适配
 * @author liuzhuo
 *
 */
public class SmsAdapter {

	private static final Logger LOG = LoggerFactory.getLogger(SmsAdapter.class);

	/**
	 * 发送短信
	 * @param mobileCaptchaEntity
	 */
	public static void sendMessage(MobileCaptchaEntity mobileCaptchaEntity) {

//		SmsMessageEntity smsMessageEntity = new SmsMessageEntity();
//		buildSmsMessageEntity(smsMessageEntity,mobileCaptchaEntity);
		try {
//			AdapterBeanFactory.getBean(SmsSender.class).sendSms(smsMessageEntity);
			String result = sendYPSms(mobileCaptchaEntity);
			LOG.info(result);
		} catch (Exception e) {
			LOG.error(TZMarkers.p2, " 调用短信服务发送短信异常", e);
		}

	}

	public static String sendYPSms(MobileCaptchaEntity mobileCaptchaEntity){
		return sendYPSms(mobileCaptchaEntity.getContent(), mobileCaptchaEntity.getMobileNum());
	}

	public static String sendYPSms(String content, String mobile){
		NameValuePair[] nameValuePairs = new NameValuePair[3];
		nameValuePairs[0] = new BasicNameValuePair("apikey", YunPianSmsConfig.ALI_TUNNEL_API_KEY);
		nameValuePairs[1] = new BasicNameValuePair("text",  buildPostfix() + content);
		nameValuePairs[2] = new BasicNameValuePair("mobile", mobile);
		return SimpleHttpClient.post(YunPianSmsConfig.SEND_SMS_URL, nameValuePairs);
	}

	private static String buildPostfix(){
		if(StringUtils.isNotBlank(YunPianSmsConfig.SMS_POSTFIX)){
			return "【"+YunPianSmsConfig.SMS_POSTFIX+"】";
		}else{
			return "";//没有postfix系统会默认加上"【不夜城】"
		}
	}

	/**
	 * 类型转换
	 * @param smsMessageEntity
	 * @param mobileCaptchaEntity
	 */
//	@SuppressWarnings("unused")
//	private static void buildSmsMessageEntity(SmsMessageEntity smsMessageEntity,MobileCaptchaEntity mobileCaptchaEntity) {
//		smsMessageEntity.setSmsPostfix("真旅行");
//		smsMessageEntity.setMobiles(mobileCaptchaEntity.getMobileNum());
//		smsMessageEntity.setSmsContent(mobileCaptchaEntity.getContent());
//	}

}
