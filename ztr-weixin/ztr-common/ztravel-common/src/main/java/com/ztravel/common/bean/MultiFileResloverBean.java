package com.ztravel.common.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


//@Configuration
public class MultiFileResloverBean {

	@Bean(name="multipartResolver")
	CommonsMultipartResolver commonsMultipartResolver() {
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setMaxUploadSize(2097152);
		return resolver;
	}


}
