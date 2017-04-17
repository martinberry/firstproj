package com.ztravel.operator.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Configuration
public class MultiFileResloverBean {
	
//	<!-- upload文件设置 -->
//	<bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
//		<!--2M-->
//		<property name="maxUploadSize" value="2097152" />
//	</bean>
	
	@Bean(name="multipartResolver")
	CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(2097152);
		return resolver;
	}
	

}
