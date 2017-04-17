package com.ztravel.adapter.common.adapter.test.sms;

import org.junit.Test;

import com.ztravel.common.adapter.sms.MobileCaptchaEntity;
import com.ztravel.common.adapter.sms.SmsAdapter;

/**
 * 测试短息发送适配功能
 * @author liuzhuo
 *
 */
public class SmsTest {

	@Test
	public void sendRegiestCapthca() {
		MobileCaptchaEntity mobileCaptchaEntity = new MobileCaptchaEntity();
		mobileCaptchaEntity.setMobileNum("13661832763");
		mobileCaptchaEntity.setContent("您注册真旅行账号的验证码是345678");
		SmsAdapter.sendMessage(mobileCaptchaEntity);
	}
	@Test
	public void sendYPSms(){
		System.out.println(SmsAdapter.sendYPSms("wanhaofan is a big <strong>S_B</strong>", "13661832763"));
	}

}
