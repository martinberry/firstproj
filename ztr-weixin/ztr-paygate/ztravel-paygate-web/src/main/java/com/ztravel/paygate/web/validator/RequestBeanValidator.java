package com.ztravel.paygate.web.validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
/**
 * 对请求数据进行校验
 * @author dingguangxian
 *
 */
/**
 * 对请求数据进行校验
 * @author dingguangxian
 *
 */
@Component("paygate_request_bean_validator")
public class RequestBeanValidator implements ApplicationContextAware{
	private static Logger logger = LoggerFactory.getLogger(RequestBeanValidator.class);
	private Map<Class<?>,SortedMethod> methods = new HashMap<>();
	private List<Entry<Class<?>,SortedMethod>> sortedMethods;
	//暂存数据校验方法以及定义该方法的对象
	private static class SortedMethod implements Comparable<SortedMethod>{
		private Object validator;
		private Method method;
		private Integer order;
		public SortedMethod(Object object, Method method, Integer order) {
			super();
			this.validator = object;
			this.method = method;
			this.order = order;
		}

		@Override
		public int compareTo(SortedMethod other) {
			return this.order.compareTo(other.order);
		}
	}
	/**
	 * 进行数据校验
	 * @param requestBean
	 */
	public void validate(Object requestBean){
		if(requestBean == null){
			return;
		}
		logger.info("执行数据校验.bean : {}", TZBeanUtils.describe(requestBean));
		SortedMethod method;
		for(Entry<Class<?>,SortedMethod> entry : getSortedMethods()){
			if(entry.getKey().isInstance(requestBean)){
				method = entry.getValue();
				try {
					logger.info("执行数据校验,validator:{}, method:{}, validateBean:{}",method.validator, method.method.getName(), TZBeanUtils.describe(requestBean));
					method.method.invoke(method.validator, requestBean);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					if(e.getCause() instanceof PaygateException){
						throw (PaygateException)e.getCause();
					}
					logger.error("数据校验出现未知异常.", e);
					throw new PaygateException(PaygateError.E100_ERROR);
				}
			}
		}
		logger.info("数据校验成功.bean : {}", TZBeanUtils.describe(requestBean));
	}
	
	private void initValidator(String name, Object validator){
		logger.info("解析数据校验器:{},class:{}", name, validator.getClass().getName());
		for(Method method : validator.getClass().getDeclaredMethods()){
			BeanValidator anno = null; 
			if((anno = method.getAnnotation(BeanValidator.class))!=null
					&& (method.getReturnType() == Void.class || method.getReturnType() == void.class) 
					&& method.getParameterTypes().length ==1){
				logger.info("注册数据校验器:{}#{}",validator.getClass().getName(),method.getName());
				Class supportClass = method.getParameterTypes()[0];
				methods.put(supportClass, new SortedMethod(validator, method, anno.order()));
			}
		}
	}
	
	private List<Entry<Class<?>,SortedMethod>> getSortedMethods(){
		if(sortedMethods == null){
			sortedMethods = new ArrayList<>();
			sortedMethods.addAll(methods.entrySet());
			Collections.sort(sortedMethods, new Comparator<Entry<Class<?>,SortedMethod>>() {
				@Override
				public int compare(Entry<Class<?>, SortedMethod> o1,
						Entry<Class<?>, SortedMethod> o2) {
					return o1.getValue().compareTo(o2.getValue());
				}
				
			});
		}
		return sortedMethods;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		Map<String,Object> validators = applicationContext.getBeansWithAnnotation(BeanValidator.class);
		for(Entry<String,Object> entry : validators.entrySet()){
			initValidator(entry.getKey(), entry.getValue());
		}
	}
}
