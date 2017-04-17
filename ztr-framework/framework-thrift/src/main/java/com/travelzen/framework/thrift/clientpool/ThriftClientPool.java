//package com.travelzen.framework.thrift.clientpool;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.Callable;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.FutureTask;
//import java.util.concurrent.TimeUnit;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang.exception.ExceptionUtils;
//import org.apache.commons.lang3.time.DateUtils;
//import org.apache.commons.pool.KeyedPoolableObjectFactory;
//import org.apache.commons.pool.impl.GenericKeyedObjectPool;
//import org.apache.thrift.TServiceClient;
//import org.apache.thrift.protocol.TProtocol;
//import org.apache.thrift.transport.TFramedTransport;
//import org.apache.thrift.transport.TSocket;
//import org.apache.thrift.transport.TTransport;
//import org.apache.thrift.transport.TTransportException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.batch.retry.RetryCallback;
//import org.springframework.batch.retry.RetryContext;
//import org.springframework.batch.retry.RetryPolicy;
//import org.springframework.batch.retry.backoff.FixedBackOffPolicy;
//import org.springframework.batch.retry.policy.ExceptionClassifierRetryPolicy;
//import org.springframework.batch.retry.policy.SimpleRetryPolicy;
//import org.springframework.batch.retry.support.RetryTemplate;
//
//import com.travelzen.framework.config.tops.zk.TopsServiceDiscovery;
//import com.travelzen.framework.config.tops.zk.TopsZookeeperBalancer;
//import com.travelzen.framework.core.util.CollectionUtil;
//import com.travelzen.framework.rabbitmq.thrift.ThriftClientPoolMQListenerContainer;
//import com.travelzen.framework.rabbitmq.thrift.ThriftRpcServiceRefreshUtil;
//import com.travelzen.framework.thrift.invocationhandler.HystrixCommandInvocationHandler;
//import com.travelzen.framework.thrift.invocationhandler.HystrixCommandSetterBuilder;
//import com.travelzen.framework.thrift.protocol.RIThriftProtocol;
//import com.travelzen.framework.thrift.util.ThriftServicesUtil;
//
//public class ThriftClientPool<T> implements ThriftRpcServiceRefreshUtil {
//
//	//	private Logger logger = LoggerFactory.getLogger(this.getClass());
//
//	// thrift iface的类名
//	private Class<T> serviceIface;
//
//	// thrift client的类名
//	@SuppressWarnings("rawtypes")
//	private Class serviceClient;
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
//	private FailRetryInvocationHandler invocationHandler = new FailRetryInvocationHandler();
//
//	// 对象池
//	private GenericKeyedObjectPool<BackendRpcAddress, T> clientPool;
//
//	//所有可用的后端服务池
//	private BackendRpcAddressPool backendRpcAddressPool = new BackendRpcAddressPool();
//
//	//初始的服务地址
//	private List<BackendRpcAddress> fallbackBackendRpcAddresses;
//
//	//	private TreeCache treeCache;
//
//	private volatile CountDownLatch asyncRefreshServerClusterLatch = new CountDownLatch(1);
//
//	//是否使用curator的服务发现， 默认使用公司自己写的服务发现
//	private boolean useCuratorServiceDiscovery;
//
//	/**
//	 * 初始化
//	 * @throws Exception
//	 */
//	public void init() throws Exception {
//		initClientPool();
//		backendRpcAddressPool.startBackgroundRefresh();
//		listenRpcServerClusterChange();
//	}
//
//	/**
//	 * 停止服务
//	 * @throws Exception
//	 */
//	public void stop() throws Exception {
//		clientPool.close();
//	}
//
//	/**
//	 * 监听后端服务集群的变化
//	 */
//	private void listenRpcServerClusterChange() throws Exception {
//		//		backendRpcAddressPool.refresh();
//	}
//
//	/**
//	 * 初始化连接池
//	 */
//	private void initClientPool() {
//		GenericKeyedObjectPool.Config config = new GenericKeyedObjectPool.Config();
//		config.lifo = true;
//		config.maxActive = maxActivePerBackend;
//		config.maxWait = 10 * DateUtils.MILLIS_PER_SECOND;
//		config.maxIdle = maxIdlePerBackend;
//		config.testOnBorrow = true;
//		config.testWhileIdle = true;
//		config.timeBetweenEvictionRunsMillis = 3 * 1000;
//		KeyedPoolableObjectFactory<BackendRpcAddress, T> objectFactory = new KeyedPoolableObjectFactory<BackendRpcAddress, T>() {
//			private Logger objectFactoryLogger = LoggerFactory.getLogger(this.getClass());
//
//			@SuppressWarnings({ "unchecked", "rawtypes" })
//			@Override
//			public T makeObject(BackendRpcAddress key) throws Exception {
//				TTransport transport = new TSocket(key.getHost(), key.getPort(), socketTimeout * 1000);
//				if (framedTransport)
//					transport = new TFramedTransport(transport);
//				transport.open();
//				TProtocol protocol = new RIThriftProtocol(transport); // call info is integrated in this protocol
//				//	TProtocol protocol = new TBinaryProtocol(transport);
//				Class serviceClient = ThriftClientPool.this.getServiceClientClass();
//				return (T) serviceClient.getConstructor(TProtocol.class).newInstance(protocol);
//			}
//
//			@Override
//			public void destroyObject(BackendRpcAddress key, T obj) throws Exception {
//				if (obj != null) {
//					TServiceClient serviceClient = (TServiceClient) obj;
//					try {
//						serviceClient.getInputProtocol().getTransport().close();
//					} catch (Exception e) {
//						objectFactoryLogger.error("关闭Tsocket异常", e);
//					}
//				}
//			}
//
//			@Override
//			public boolean validateObject(BackendRpcAddress key, T obj) {
//				try {
//					TServiceClient serviceClient = (TServiceClient) obj;
//					TTransport transport = serviceClient.getInputProtocol().getTransport();
//					return transport.isOpen();
//				} catch (Exception e) {
//					objectFactoryLogger.error("验证对象有效性时出现异常", e);
//					return false;
//				}
//			}
//
//			@Override
//			public void activateObject(BackendRpcAddress key, T obj) throws Exception {
//
//			}
//
//			@Override
//			public void passivateObject(BackendRpcAddress key, T obj) throws Exception {
//
//			}
//
//		};
//		clientPool = new GenericKeyedObjectPool<BackendRpcAddress, T>(objectFactory, config);
//	}
//
//	private class FailRetryInvocationHandler implements InvocationHandler {
//
//		private Logger handlerLogger = LoggerFactory.getLogger(FailRetryInvocationHandler.class);
//
//		private RetryTemplate retryTemplate;
//
//		public FailRetryInvocationHandler() {
//			retryTemplate = createRetryTemplate();
//		}
//
//		/**
//		 *  创建重试模板
//		 * @return
//		 */
//		private RetryTemplate createRetryTemplate() {
//			ExceptionClassifierRetryPolicy retryPolicy = new ExceptionClassifierRetryPolicy();
//			Map<Class<? extends Throwable>, RetryPolicy> policyMap = new HashMap<>();
//			SimpleRetryPolicy maxAttemptRetryPolicy = new SimpleRetryPolicy();
//			// Try twice at present. If the first time failed, refresh and try the second time
//			maxAttemptRetryPolicy.setMaxAttempts(2);
//			policyMap.put(Exception.class, maxAttemptRetryPolicy);
//			retryPolicy.setPolicyMap(policyMap);
//			FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
//			backOffPolicy.setBackOffPeriod(100);
//			RetryTemplate retryTemplate = new RetryTemplate();
//			retryTemplate.setRetryPolicy(retryPolicy);
//			retryTemplate.setBackOffPolicy(backOffPolicy);
//			return retryTemplate;
//		}
//
//		@Override
//		public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {
//			ExecutorService executor = Executors.newSingleThreadExecutor();
//			FutureTask<Object> future = new FutureTask<Object>(new Callable<Object>() {//使用Callable接口作为构造参数
//						@Override
//						public Object call() throws Exception {
//							//真正的任务在这里执行，这里的返回值类型为Object，可以为任意类型
//							RetryCallback<Object> worker = new RetryCallback<Object>() {
//								public Object doWithRetry(RetryContext retryContext) throws Exception {
//									BackendRpcAddress address = null;
//									T serviceClient = null;
//									try {
//										address = ThriftClientPool.this.backendRpcAddressPool.next();
//										if (address == null)
//											throw new Exception("没有可用的后端服务, backendRpcAddressPool is empty");
//										handlerLogger.info("当前使用的rpc地址:" + address);
//										serviceClient = ThriftClientPool.this.clientPool.borrowObject(address);
//										if (serviceClient == null)
//											throw new Exception("没有可用的后端服务, serviceClient is null");
//										return method.invoke(serviceClient, args);
//									} catch (Exception e) {
//										handlerLogger.error("", e);
//										if (ExceptionUtils.indexOfThrowable(e, TTransportException.class) != -1) {
//											ThriftClientPool.this.clientPool.invalidateObject(address, serviceClient);
//											asyncRefreshServerClusterLatch.countDown();
//										} else
//											retryContext.setExhaustedOnly();
//										throw e;
//									} finally {
//										if (address != null && serviceClient != null)
//											ThriftClientPool.this.clientPool.returnObject(address, serviceClient);
//									}
//								}
//							};
//							return retryTemplate.execute(worker);
//						}
//					});
//			executor.execute(future);
//
//			Object result = null;
//			try {
//				result = future.get(invokeTimeout * DateUtils.MILLIS_PER_SECOND, TimeUnit.MILLISECONDS); //在invokeTimeout之内取得结果，否则抛异常
//			} catch (Exception e) {
//				asyncRefreshServerClusterLatch.countDown();
//				throw e;
//			} finally {
//				future.cancel(true);
//				executor.shutdown();
//			}
//			return result;
//		}
//	}
//
//	/**
//	 * 获取经过FailRetry代理过的Client
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public T getFailRetryClient() throws Exception {
//		return (T) Proxy.newProxyInstance(serviceIface.getClassLoader(), new Class[] { serviceIface }, invocationHandler);
//	}
//	
//	/**
//	 * 获取经过Hystrix和FailRetry代理过的Client
//	 * @return
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	public T getClient() throws Exception {
//		HystrixCommandSetterBuilder builder = new HystrixCommandSetterBuilder();
//		String simpleName = ThriftServicesUtil.getSimpleServiceName(getServiceIface());
//		builder.commandGroup(simpleName);
//		builder.commandKey(simpleName);
////		InvocationHandler handler = new HystrixCommandInvocationHandler(invocationHandler, new HystrixCommandSetterBuilder());
//		InvocationHandler handler = new HystrixCommandInvocationHandler(invocationHandler, builder);
//		return (T) Proxy.newProxyInstance(serviceIface.getClassLoader(), new Class[] { serviceIface }, handler);
//	}
//
//	@SuppressWarnings("rawtypes")
//	private Class getServiceClientClass() throws Exception {
//		if (serviceClient != null)
//			return serviceClient;
//		String serviceIfaceClassName = serviceIface.getName();
//		String serviceClientClassName = serviceIfaceClassName.substring(0, serviceIfaceClassName.lastIndexOf("$")) + "$Client";
//		serviceClient = Class.forName(serviceClientClassName);
//		return serviceClient;
//	}
//
//	/**
//	 *  后端服务池
//	 * @author renshui
//	 *
//	 */
//	private class BackendRpcAddressPool {
//		private Logger poolLogger = LoggerFactory.getLogger(BackendRpcAddressPool.class);
//		private List<BackendRpcAddress> addresses;
//		private Iterator<BackendRpcAddress> iter;
//
//		public void startBackgroundRefresh() {
//			Thread asyncRefreshThread = new Thread() {
//
//				public void run() {
//					while (true) {
//						try {
//							System.out.println("---------------Count down and refresh--------------");
//							System.out.println(asyncRefreshServerClusterLatch.hashCode());
//							refresh();
//							System.out.println(addresses);
//							asyncRefreshServerClusterLatch = new CountDownLatch(1);
//							asyncRefreshServerClusterLatch.await(30, TimeUnit.MINUTES);
//						} catch (Exception e) {
//							poolLogger.error("", e);
//							try {
//								Thread.sleep(3 * 1000);
//							} catch (InterruptedException e1) {
//								poolLogger.error("", e);
//							}
//						}
//					}
//				}
//			};
//			asyncRefreshThread.setDaemon(true);
//			asyncRefreshThread.start();
//		}
//
//		/**
//		 * 获取下一个服务地址
//		 * @return
//		 */
//		public synchronized BackendRpcAddress next() {
//			if (CollectionUtils.isEmpty(addresses)) {
//				poolLogger.info("后端服务地址为空， 使用初始服务地址:" + fallbackBackendRpcAddresses);
//				addresses = fallbackBackendRpcAddresses;
//			}
//			if (iter == null || !iter.hasNext())
//				iter = addresses.iterator();
//			return iter.next();
//		}
//
//		private synchronized void refresh() {
//			List<BackendRpcAddress> newAddresses = getBackendRpcAddressListFromZK();
//			if (!CollectionUtil.isEqualCollection(newAddresses, addresses)) {
//				poolLogger.info(String.format("后端服务节点发生了变化， %s->%s", addresses, newAddresses));
//				addresses = newAddresses;
//			} else
//				poolLogger.info("后端服务节点没有变化");
//		}
//
//		/**
//		 * 从zookeeper读取后端服务地址
//		 * @return
//		 */
//		private List<BackendRpcAddress> getBackendRpcAddressListFromZK() {
//			List<BackendRpcAddress> backendRpcAddressList = new ArrayList<>();
//			List<String> addresses = null;
//			if (useCuratorServiceDiscovery) {
//				try {
//					addresses = TopsServiceDiscovery.getRpcAddress(basePath, rpcServiceName);
//				} catch (Exception e) {
//					poolLogger.error("", e);
//				}
//			} else {
//				addresses = TopsZookeeperBalancer.getRpcAddress(rpcServiceName, basePath, false);
//			}
//			if (CollectionUtils.isNotEmpty(addresses)) {
//				for (String address : addresses) {
//					backendRpcAddressList.addAll(BackendRpcAddress.asList(address));
//				}
//			}
//			return backendRpcAddressList;
//		}
//
//	}
//
//	public Class<T> getServiceIface() {
//		return serviceIface;
//	}
//
//	public void setServiceIface(Class<T> serviceIface) {
//		this.serviceIface = serviceIface;
//	}
//
//	public boolean isFramedTransport() {
//		return framedTransport;
//	}
//
//	public void setFramedTransport(boolean framedTransport) {
//		this.framedTransport = framedTransport;
//	}
//
//	public int getSocketTimeout() {
//		return socketTimeout;
//	}
//
//	public void setSocketTimeout(int socketTimeout) {
//		this.socketTimeout = socketTimeout;
//	}
//
//	public int getMaxActivePerBackend() {
//		return maxActivePerBackend;
//	}
//
//	public void setMaxActivePerBackend(int maxActivePerBackend) {
//		this.maxActivePerBackend = maxActivePerBackend;
//	}
//
//	public int getMaxIdlePerBackend() {
//		return maxIdlePerBackend;
//	}
//
//	public void setMaxIdlePerBackend(int maxIdlePerBackend) {
//		this.maxIdlePerBackend = maxIdlePerBackend;
//	}
//
//	public String getRpcServiceName() {
//		return rpcServiceName;
//	}
//
//	public void setRpcServiceName(String rpcServiceName) {
//		this.rpcServiceName = rpcServiceName;
//	}
//
//	public String getBasePath() {
//		return basePath;
//	}
//
//	public void setBasePath(String basePath) {
//		this.basePath = basePath;
//	}
//
//	public boolean isUseCuratorServiceDiscovery() {
//		return useCuratorServiceDiscovery;
//	}
//
//	public void setUseCuratorServiceDiscovery(boolean useCuratorServiceDiscovery) {
//		this.useCuratorServiceDiscovery = useCuratorServiceDiscovery;
//	}
//
//	public List<BackendRpcAddress> getFallbackBackendRpcAddresses() {
//		return fallbackBackendRpcAddresses;
//	}
//
//	public void setFallbackBackendRpcAddresses(List<BackendRpcAddress> fallbackBackendRpcAddresses) {
//		this.fallbackBackendRpcAddresses = fallbackBackendRpcAddresses;
//	}
//
//	public long getInvokeTimeout() {
//		return invokeTimeout;
//	}
//
//	public void setInvokeTimeout(long invokeTimeout) {
//		this.invokeTimeout = invokeTimeout;
//	}
//
//	// after registration, thriftMQListener will trigger the count down after receiving the message from MQ to update ZK service
//	public void registerThriftMQListener(ThriftClientPoolMQListenerContainer thriftClientPoolListenerContainer) {
//		thriftClientPoolListenerContainer.registerThriftMQListener(rpcServiceName, basePath, this);
//	}
//
//	@Override
//	public void refreshServices() {
//		asyncRefreshServerClusterLatch.countDown();
//	}
//
//}
