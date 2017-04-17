package com.ztravel.common.retry;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.batch.retry.policy.SimpleRetryPolicy;
import org.springframework.batch.retry.support.RetryTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ztravel.common.exception.ShouldRetryException;

/**
 * @author zuoning.shen
 *
 */
@Configuration
public class RetryConfig {

	@Bean
	public RetryTemplate retryTemplate(RetryOperationsInterceptor retryInterceptor) {
		RetryTemplate retryTemplate = new RetryTemplate();
		Map<Class<? extends Throwable>, Boolean> map = new HashMap<>();
		map.put(ShouldRetryException.class, true);
		SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(10, map);
		retryInterceptor.setRetryOperations(retryTemplate);
		retryTemplate.setRetryPolicy(retryPolicy);
		return retryTemplate;
	}

	@Bean
	public RetryOperationsInterceptor retryInterceptor() {
		return new RetryOperationsInterceptor();
	}
}
