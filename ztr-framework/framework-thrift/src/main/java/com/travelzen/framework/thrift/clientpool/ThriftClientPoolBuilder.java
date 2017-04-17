//package com.travelzen.framework.thrift.clientpool;
//
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//
//import com.google.common.base.Preconditions;
//import com.travelzen.framework.rabbitmq.thrift.ThriftClientPoolMQListenerContainer;
//
//public class ThriftClientPoolBuilder<T> {
//	// thrift iface的类名
//	private Class<T> serviceIface;
//
//	// 在zookeeper上注册rpc服务时使用的名字
//	private String rpcServiceName;
//
//	// 是否使用framedTransport
//	private boolean framedTransport;
//
//	// socket超时时间， 单位秒
//	private int socketTimeout = 60;
//
//	// 每个后端最大的连接数
//	private int maxActivePerBackend = 30;
//
//	// 每个后端最大的空闲连接数
//	private int maxIdlePerBackend = 10;
//
//	// 方法调用超时时间
//	private long invokeTimeout = 5;
//
//	// 命名服务前缀
//	private String basePath;
//
//	//初始的后端服务地址
//	private List<BackendRpcAddress> fallbackBackendRpcAddresses;
//
//	//是否使用curator的服务发现， 默认使用公司自己写的服务发现
//	private boolean useCuratorServiceDiscovery;
//
//	private ThriftClientPoolMQListenerContainer listenerContainer = null;
//
//	public ThriftClientPoolBuilder<T> serviceIface(Class<T> serviceIface) {
//		this.serviceIface = serviceIface;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> rpcServiceName(String rpcServiceName) {
//		this.rpcServiceName = rpcServiceName;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> framedTransport(boolean framedTransport) {
//		this.framedTransport = framedTransport;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> socketTimeout(int socketTimeout) {
//		this.socketTimeout = socketTimeout;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> maxActivePerBackend(int maxActivePerBackend) {
//		this.maxActivePerBackend = maxActivePerBackend;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> maxIdlePerBackend(int maxIdlePerBackend) {
//		this.maxIdlePerBackend = maxIdlePerBackend;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> nameSpacePrefix(String nameSpacePrefix) {
//		this.basePath = nameSpacePrefix;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> fallbackBackendRpcAddresses(List<BackendRpcAddress> fallbackBackendRpcAddresses) {
//		this.fallbackBackendRpcAddresses = fallbackBackendRpcAddresses;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> useCuratorServiceDiscovery(boolean useCuratorServiceDiscovery) {
//		this.useCuratorServiceDiscovery = useCuratorServiceDiscovery;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> invokeTimeout(long invokeTimeout) {
//		this.invokeTimeout = invokeTimeout;
//		return this;
//	}
//
//	public ThriftClientPoolBuilder<T> registerThriftMQListener(ThriftClientPoolMQListenerContainer listenerContainer) {
//		this.listenerContainer = listenerContainer;
//		return this;
//	}
//
//	public ThriftClientPool<T> build() throws Exception {
//		Preconditions.checkNotNull(serviceIface, "serviceIface不能为null");
//		Preconditions.checkArgument(StringUtils.isNotEmpty(rpcServiceName), "rpcServiceName不能为空");
//		Preconditions.checkArgument(StringUtils.isNotEmpty(basePath), "nameSpacePrefix不能为空");
//		Preconditions.checkArgument(socketTimeout > 0, "socketTimetout必须为正数");
//		Preconditions.checkArgument(maxActivePerBackend > 0, "maxActivePerBackend必须为正数");
//		Preconditions.checkArgument(maxIdlePerBackend > 0, "maxIdlePerBackend必须为正数");
//		Preconditions.checkArgument(invokeTimeout > 0, "invokeTimeout必须为正数");
//		Preconditions.checkArgument(CollectionUtils.isNotEmpty(fallbackBackendRpcAddresses), "initBackendRpcAddresses不能为空");
//		ThriftClientPool<T> pool = new ThriftClientPool<>();
//		pool.setServiceIface(serviceIface);
//		pool.setRpcServiceName(rpcServiceName);
//		pool.setBasePath(basePath);
//		pool.setFramedTransport(framedTransport);
//		pool.setSocketTimeout(socketTimeout);
//		pool.setMaxActivePerBackend(maxActivePerBackend);
//		pool.setMaxIdlePerBackend(maxIdlePerBackend);
//		pool.setFallbackBackendRpcAddresses(fallbackBackendRpcAddresses);
//		pool.setUseCuratorServiceDiscovery(useCuratorServiceDiscovery);
//		pool.setInvokeTimeout(invokeTimeout);
//		if (listenerContainer != null)
//			pool.registerThriftMQListener(listenerContainer);
//		pool.init();
//		return pool;
//	}
//
//	public ThriftClientPoolBuilder<T> clear() {
//		this.rpcServiceName = null;
//		this.basePath = null;
//		this.fallbackBackendRpcAddresses = null;
//		this.framedTransport = false;
//		return this;
//	}
//
//}
