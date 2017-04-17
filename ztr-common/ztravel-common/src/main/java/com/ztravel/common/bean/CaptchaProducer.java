package com.ztravel.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Properties;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;


/**
 * CaptchaProducer 生成登录验证码,长度为4位
 * @author liuzhuo
 *
 */

@Configuration
public class CaptchaProducer {
	
	@Bean(name="captchaProducer")
	public DefaultKaptcha buildDefaultKaptcha() {
		
		Properties properties = new Properties();
		
		properties.put("kaptcha.border", "no");
		properties.put("kaptcha.textproducer.font.color", "88,105,147");
		properties.put("kaptcha.textproducer.char.length", "4");
		properties.put("kaptcha.background.clear.from", "white");
		properties.put("kaptcha.background.clear.to", "white");
		
		Config config = new Config(properties);
		
		DefaultKaptcha kaptcha = new DefaultKaptcha();
		kaptcha.setConfig(config);
		
		return kaptcha;
	}

}
