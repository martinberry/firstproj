package com.travelzen.swordfish.thrift.client.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.SmartFactoryBean;

import com.incubate.framework.thrift.ThriftRequest;
import com.incubate.framework.thrift.ThriftResponse;
import com.incubate.framework.thrift.ThriftService;
import com.travelzen.framework.core.util.TZUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.swordfish.thrift.util.JacksonUtil;
import com.travelzen.swordfish.thrift.util.ThriftReflectionUtil;


public class ThriftClientServiceProxy implements SmartFactoryBean<Object>{

	private static Logger logger = RequestIdentityLogger.getLogger(ThriftClientServiceProxy.class);


	private ThriftClient thriftClient;

	private Class<?> clazz;

	private String implClazz;


	public ThriftClient getThriftClient() {
		return thriftClient;
	}

	public void setThriftClient(ThriftClient thriftClient) {
		this.thriftClient = thriftClient;
	}

	public void setInterface(Class<?> clazz) {
		this.clazz = clazz;
	}

	public void setImplClazz(String implClazz) {
		this.implClazz = implClazz;
	}


	@Override
	public Object getObject() throws Exception {

		Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class<?>[]{clazz}, new InvocationHandler() {

			@Override
			public Object invoke(Object paramObject, Method paramMethod, Object[] paramArrayOfObject) throws Throwable {


				ThriftService.Iface client = null;

				try{
					client = thriftClient.getObject().proxy();

					ThriftRequest request = new ThriftRequest();
					request.setDeclaredInterface(paramMethod.getDeclaringClass().getName());
					request.setMethod(ThriftReflectionUtil.getSimpleMethodStr(paramMethod));

					if(!TZUtil.isEmpty(paramArrayOfObject)) {
						request.setParameters(JacksonUtil.obj2json(paramArrayOfObject));
					}
					request.setImplClass(implClazz);
					ThriftResponse response = client.request(request);

					if(null == request || null == response || void.class == paramMethod.getReturnType()) {
						return null;
					}else {
						Type returnType =  paramMethod.getGenericReturnType() ;
						Class<?> clazz = paramMethod.getReturnType() ;
						if(returnType  instanceof ParameterizedType)/**//* 如果是泛型类型 */{
							Type[] types = ((ParameterizedType) returnType).getActualTypeArguments() ;
							if(types != null){
								Class<?>[] clazzs = new Class<?>[types.length] ;
								for(int i=0;i<types.length;i++){
									clazzs[i] = (Class<?>)types[i] ;
								}
								return JacksonUtil.json2pojo(response.getResult(), clazz, clazzs);
							}else{
								return null ;
							}
						}else{
							return JacksonUtil.json2pojo(response.getResult(), clazz);
						}
					}

				}catch(Throwable e) {

					logger.error(TZMarkers.p2, "请求thrift服务异常,方法 " + paramMethod.getDeclaringClass().getName() + "\n" + ThriftReflectionUtil.getSimpleMethodStr(paramMethod), e);
					throw e;
				}

			}
		});
		return proxy;
	}

	@Override
	public Class<?> getObjectType() {
		return this.getClass();
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public boolean isPrototype() {
		return false;
	}

	@Override
	public boolean isEagerInit() {
		return true;
	}

}
