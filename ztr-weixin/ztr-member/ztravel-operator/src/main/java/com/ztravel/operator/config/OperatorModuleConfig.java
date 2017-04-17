package com.ztravel.operator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.ztravel.rbac.config.RbacBeanConfig;
import com.ztravel.operator.bean.MultiFileResloverBean;
import com.ztravel.payment.config.PaymentClientConfig;

@Configuration

@Import({
	PaymentClientConfig.class,
	OperatorBeanConfig.class,
	MultiFileResloverBean.class,
	RbacBeanConfig.class
})
public class OperatorModuleConfig {

}
