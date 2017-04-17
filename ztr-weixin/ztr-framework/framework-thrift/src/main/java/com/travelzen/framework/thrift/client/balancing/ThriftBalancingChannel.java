package com.travelzen.framework.thrift.client.balancing;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class ThriftBalancingChannel<T, C extends T> extends LoadBalancingChannelNoProperty<T> {

	private Class<C> clazz;

	public ThriftBalancingChannel(List<String> list, String prefix, String serviceName, boolean needCache) {
		super(list, prefix, serviceName, needCache);
	}

	@Override
	public Client<T> createThriftCient(String ip, int port) {
		ThriftBalancingClient<T> client = new ThriftBalancingClient<T>(ip, port, true, getClientClass());
		return client;
	}

	@SuppressWarnings("unchecked")
	public Class<C> getClientClass() {
		if (clazz != null) {
			return clazz;
		}
		Type superclass = this.getClass().getGenericSuperclass();
		if (superclass instanceof Class) {
			throw new RuntimeException("Missing type parameter.");
		}
		ParameterizedType parameterized = (ParameterizedType) superclass;
		clazz = (Class<C>) parameterized.getActualTypeArguments()[1];
		return clazz;
	}

}
