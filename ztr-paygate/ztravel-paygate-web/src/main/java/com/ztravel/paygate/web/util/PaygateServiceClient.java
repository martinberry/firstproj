package com.ztravel.paygate.web.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PaygateError;
import com.ztravel.paygate.core.exception.PaygateException;
import com.ztravel.paygate.thrift.model.PaySignRequest;
import com.ztravel.paygate.thrift.model.PaySignResponse;
import com.ztravel.paygate.thrift.model.QueryRequest;
import com.ztravel.paygate.thrift.model.QueryResponse;
import com.ztravel.paygate.thrift.model.RefundQueryRequest;
import com.ztravel.paygate.thrift.model.RefundQueryResponse;
import com.ztravel.paygate.thrift.model.RefundRequest;
import com.ztravel.paygate.thrift.model.RefundResponse;
import com.ztravel.paygate.thrift.model.RefundValSignRequest;
import com.ztravel.paygate.thrift.model.RefundValSignResponse;
import com.ztravel.paygate.thrift.model.ValSignRequest;
import com.ztravel.paygate.thrift.model.ValSignResponse;
import com.ztravel.paygate.thrift.service.PaygateService;
import com.ztravel.paygate.web.thrift.PaygateThriftBalancingClientFactory;
import com.ztravel.paygate.web.util.EnvArgs.Const;

/**
 * paygate thrift service client
 *
 * @author dingguangxian
 */
public class PaygateServiceClient {
	private static Logger logger = LoggerFactory.getLogger(PaygateServiceClient.class);
	// 请求超时时间,60s
	public static final int SHORT_TIMEOUT = 60 * 1000;
	// 请求超时时间,10min
	public static final int LONG_TIMEOUT = 10 * 60 * 1000;

	private static Map<GateType, PaygateThriftBalancingClientFactory> clientMap = new HashMap<>();

	/**
	 * thrift服务提供的方法
	 */
	public static enum ServiceMethod {
		paySign(new Class[]{PaySignRequest.class}), // 支付请求
		payValSign(new Class[]{ValSignRequest.class}), // 支付结果验签
		refund(new Class[]{RefundRequest.class}), // 退单请求
		refundValSign(new Class[]{RefundValSignRequest.class}), // 退单验签
		query(new Class[]{QueryRequest.class}), // 查询
		refundQuery(new Class[]{RefundQueryRequest.class}), // 退单查询
		;
		Class<?>[] parameterTypes;

		ServiceMethod(Class<?>[] parameterTypes) {
			this.parameterTypes = parameterTypes;
		}
	}

	static {
		for (GateType gateType : Lists.newArrayList(GateType.AliPay)) {
			List<String> server = formedServerBeanConfig(gateType);
			String serviceName = EnvArgs.getArgs(gateType.name + Const.ZK_SERVICE_NAME_SUFFIX);
			String zkRegistPath = null;
			if (StringUtils.isNotBlank(serviceName)) {
				zkRegistPath = EnvArgs.getArgs(Const.ZK_ZKNS);
			}
			try {
				clientMap.put(gateType, new PaygateThriftBalancingClientFactory(server, zkRegistPath, serviceName));
			} catch (Exception e) {
				logger.error(TZMarkers.p1, "网关\"" + gateType + "\"创建zookeeper代理服务失败.", e);
			}
		}
	}

	/**
	 * 支付请求签名
	 */
	public static PaySignResponse signPayment(PaySignRequest signRequest, GateType gateType) {
		return serviceHandle(ServiceMethod.paySign, gateType, SHORT_TIMEOUT, signRequest);
	}

	/**
	 * 对支付的处理结果进行有效性校验
	 */
	public static ValSignResponse payValSign(ValSignRequest request, GateType gateType) {
		return serviceHandle(ServiceMethod.payValSign, gateType, LONG_TIMEOUT, request);
	}

	/**
	 * 发起退单请求
	 */
	public static RefundResponse requestRefund(RefundRequest refundRequest, GateType gateType) {
		return serviceHandle(ServiceMethod.refund, gateType, LONG_TIMEOUT, refundRequest);
	}

	/**
	 * 退单验签
	 */
	public static RefundValSignResponse refundValSign(RefundValSignRequest valSignRequest, GateType gateType) {
		return serviceHandle(ServiceMethod.refundValSign, gateType, LONG_TIMEOUT, valSignRequest);
	}

	/**
	 * 查询请求
	 */
	public static QueryResponse query(QueryRequest queryRequest, GateType gateType) {
		return serviceHandle(ServiceMethod.query, gateType, SHORT_TIMEOUT, queryRequest);
	}

	/**
	 * 退单查询
	 */
	public static RefundQueryResponse refundQuery(RefundQueryRequest queryRequest, GateType gateType) {
		return serviceHandle(ServiceMethod.refundQuery, gateType, SHORT_TIMEOUT, queryRequest);
	}

	/**
	 * 调用thrift服务接口
	 *
	 * @param serviceMethod 方法名称
	 * @param gateType      网关类型
	 * @param timeout       请求超时时间
	 * @param params        请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T serviceHandle(ServiceMethod serviceMethod, GateType gateType, int timeout, Object... params) {
		logger.info("调用网关接口\"{}\", gateType:{}, timeout:{}", serviceMethod, gateType, timeout);
		ArrayList<Class<?>> parameterTypes = new ArrayList<>();
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				logger.info("参数{}:{}", i, TZBeanUtils.describe(params[i]));
				parameterTypes.add(params[i].getClass());
			}
		}
		Method method = null;
		try {
			method = PaygateService.Iface.class.getMethod(serviceMethod.name(), serviceMethod.parameterTypes);
		} catch (Exception e) {
			logger.error(TZMarkers.p1, "方法\"" + serviceMethod.name() + "\"调用不存在.", e);
			throw new PaygateException(PaygateError.E100_ERROR);
		}
		PaygateThriftBalancingClientFactory clientFactory = clientMap.get(gateType);
		PaygateService.Iface client = null;
		try {
			client = clientFactory != null ? clientFactory.getClient() : null;
		} catch (Exception e1) {
			logger.error("get client from client factory failed", e1);
			client = null;
		}
		if(client != null){
			logger.info("使用集群client调用服务:{}", gateType);
			try {
				return (T) method.invoke(client, params);
			} catch (Exception e) {
				logger.error(TZMarkers.p2, "方法\"" + serviceMethod.name() + "\"调用出现异常.", e);
				throw new PaygateException(PaygateError.E100_ERROR);
			}
		}

		logger.info("集群client未获取到，使用原服务接口调用:{}", gateType);
		ServerBeanIterator iterator = parseServerBeanConfig(gateType);
		while (iterator.hasNextServer()) {
			TTransport transport = null;
			try {
				transport = createTransport(iterator.nextServer(), gateType, timeout);
				TProtocol protocol = new TBinaryProtocol(transport);
				client = new PaygateService.Client(protocol);
				transport.open();
				return (T) method.invoke(client, params);
			} catch (Exception e) {
				logger.error(TZMarkers.p2, "调用网关接口\"{}\"出现异常, gateType:{}, timeout:{}", serviceMethod, gateType, timeout);
				// throw new RuntimeException(e);
			} finally {
				try {
					transport.close();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		throw new RuntimeException("调用网关接口\"" + serviceMethod + "\"出现异常");
	}

	// 创建TTransport交由统一的函数处理
	private static class ServerBean {
		private String address;
		private int port;

		public ServerBean(String address, int port) {
			this.address = address;
			this.port = port;
		}

		public String toString() {
			return this.address + ":" + this.port;
		}
	}

	private static class ServerBeanIterator {
		private Iterator<ServerBean> iterator;

		public ServerBeanIterator(List<ServerBean> serverList) {
			if (serverList != null) {
				iterator = serverList.iterator();
			}
		}

		public ServerBean nextServer() {
			if (hasNextServer()) {
				return iterator.next();
			} else {
				return null;
			}
		}

		public boolean hasNextServer() {
			return iterator != null && iterator.hasNext();
		}
	}

	// server地址配置,address1:port1;address2:port2
	private static ServerBeanIterator parseServerBeanConfig(GateType gateType) {
//		String addressConfig = PropertiesUtil.getProperty(Const.CONFIG_FILE, gateType.name + Const.THRIFT_SERVICE_ADDRESS_SUFFIX);
		String addressConfig = EnvArgs.getArgs(gateType.name + Const.THRIFT_SERVICE_ADDRESS_SUFFIX);
		if (StringUtils.isBlank(addressConfig)) {
			throw new RuntimeException("配置参数缺失.");
		}
		String[] addressArr = addressConfig.split(";");
		String[] address;
		List<ServerBean> serverList = new ArrayList<>();
		for (String add : addressArr) {
			if ((address = add.split(":")).length == 2) {
				serverList.add(new ServerBean(address[0], Integer.parseInt(address[1])));
			}
		}
		return new ServerBeanIterator(serverList);
	}

	private static List<String> formedServerBeanConfig(GateType gateType) {
		ServerBeanIterator iterator = parseServerBeanConfig(gateType);
		List<String> result = new ArrayList<String>();
		while(iterator.hasNextServer()){
			result.add(iterator.nextServer().toString());
		}
		return result;
	}

	private static TTransport createTransport(ServerBean serverBean, GateType gateType, int timeout) {
		logger.info("create transport ,address : {}, port : {} ,gateType : {}", serverBean.address, serverBean.port, gateType);
		TTransport transport = new TFramedTransport(new TSocket(serverBean.address, serverBean.port, timeout));
		return transport;
	}

}
