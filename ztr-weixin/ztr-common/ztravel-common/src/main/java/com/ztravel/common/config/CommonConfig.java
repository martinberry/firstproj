package com.ztravel.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.common.bean.CaptchaProducer;
import com.ztravel.common.bean.DataSourceAopConfig;
import com.ztravel.common.bean.ErrPageBean;
import com.ztravel.common.bean.SequenceGeneratorConfig;


@Configuration
@Import({
	CaptchaProducer.class,
	DataSourceAopConfig.class,
	ErrPageBean.class,
	SequenceGeneratorConfig.class,
	CommonBeanConfig.class
	})
public class CommonConfig {

}
