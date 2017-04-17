package com.travelzen.framework.thrift.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.core.dict.DataDict;
import com.travelzen.framework.core.util.TZUtil;

public class ThriftServicesUtil {

	private static Logger log = LoggerFactory.getLogger(ThriftServicesUtil.class);

	private static Map<String, CuratorFramework> clientMap = new ConcurrentHashMap<>();

	private static RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);

	/**
	 * @throws RuntimeException
	 * in order to give failure to MainController
	 */
	public static List<ThriftServiceBean> getServiceBeans(String connectionString, String basepath) {
		List<ThriftServiceBean> result = new ArrayList<>();
		if (TZUtil.isEmpty(connectionString))
			throw new IllegalStateException("connectionString is null");
		CuratorFramework client = null;
		if (clientMap.containsKey(connectionString)) {
			client = clientMap.get(connectionString);
		} else {
			// construct CuratorFramework;
			client = CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
			client.start();
			clientMap.put(connectionString, client);
		}
		try {
			Map<String, ThriftServiceBean> serviceMap = new HashMap<String, ThriftServiceBean>();

			List<Node> nodes = getAllLeafNodes(client, basepath);
			convertToServiceBeans(connectionString, basepath, nodes, serviceMap);
			try {
				List<Node> offlineNodes = getAllLeafNodes(client, "/OFFLINE" + basepath);
				convertToServiceBeans(connectionString, basepath, offlineNodes, serviceMap);
			} catch (Exception e) {
				log.warn("/OFFLINE" + basepath + " does not exist.", e);
			}
			result.addAll(serviceMap.values());
			Collections.sort(result);
		} catch (Exception e) {
			log.error("", e);
			throw new RuntimeException(e);
		}
		return result;
	}

	private static void convertToServiceBeans(String zkConnStr, String basepath, List<Node> nodes, Map<String, ThriftServiceBean> result) {
		if (nodes != null && nodes.size() > 0) {
			for (Node node : nodes) {
				ThriftServiceBean bean = new ThriftServiceBean();
				if (null == node.getData()) {
					continue;
				}
				// result.add(bean);
				bean.setConnectionString(node.getData());
				bean.setZkConnectionString(zkConnStr);
				bean.setBasepath(basepath);
				String path = node.getPath();
				if (path.contains("/rpc")) {
					if (!path.endsWith("/rpc")) {
						bean.setReplicaId(Integer.parseInt(path.substring(path.lastIndexOf("/") + 1)));
					}
					path = path.substring(0, path.lastIndexOf("/rpc"));
					bean.setShardId(Integer.parseInt(path.substring(path.lastIndexOf("/") + 1)));
					path = path.substring(0, path.lastIndexOf("/"));
				}
				path = path.substring(path.lastIndexOf("/") + 1);
				if (path.contains("_offline")) {
					bean.setOffline(true);
					path = path.substring(0, path.lastIndexOf("_offline"));
				}
				bean.setServiceName(path);
				String key = bean.getServiceName() + bean.getShardId() + bean.getReplicaId();
				result.put(key, bean);
			}
		}
	}

	private static List<Node> getAllLeafNodes(CuratorFramework client, String basepath) throws Exception {
		List<Node> allNodes = new ArrayList<>();
		Stack<Node> stack = new Stack<>();
		Node parentNode = new Node(client, basepath);
		stack.push(parentNode);
		while (!stack.isEmpty()) {
			Node pNode = stack.pop();
			for (Node childNode : pNode.getChildNodes()) {
				if (childNode.isLeafNode()) {
					allNodes.add(childNode);
				} else {
					stack.push(childNode);
				}
			}
		}
		return allNodes;
	}

	private static String getDataForString(CuratorFramework client, String path) throws Exception {
		if (TZUtil.isEmpty(client))
			throw new IllegalStateException("没有可用的curatorFramework");
		if (TZUtil.isEmpty(path))
			throw new IllegalStateException("path is null");
		byte[] data = client.getData().forPath(path);
		if (!TZUtil.isEmpty(data)) {
			return new String(data, DataDict.CHARACTER_SET_ENCODING_UTF8);
		}
		return null;
	}

	private static void assertClient(CuratorFramework client) {
		if (TZUtil.isEmpty(client))
			throw new IllegalStateException("没有可用的curatorFramework");
	}

	public static List<String> listPaths(CuratorFramework client, String zkPath) throws Exception {
		assertClient(client);
		if (TZUtil.isEmpty(zkPath))
			throw new IllegalStateException("zkPath is null");
		if (zkPath.length() > 2 && zkPath.endsWith("/")) {
			zkPath = zkPath.substring(0, zkPath.length() - 1);
		}
		List<String> list = client.getChildren().forPath(zkPath);
		if (!TZUtil.isEmpty(list)) {
			Collections.sort(list, new Comparator<String>() {
				@Override
				public int compare(String str1, String str2) {
					return str1.compareTo(str2);
				}
			});
		}
		return list;
	}

	private static class Node {
		private String path;
		private String data;
		private List<Node> childNodes;
		private CuratorFramework client;

		public Node(CuratorFramework client, String path) throws Exception {
			this.path = path;
			this.client = client;
			data = getDataForString(client, path);
		}

		public String getPath() {
			return path;
		}

		public String getData() {
			return data;
		}

		/**
		 * 获取所有的直接子节点
		 *
		 * @return
		 * @throws Exception
		 */
		public List<Node> getChildNodes() throws Exception {
			if (childNodes != null)
				return childNodes;
			childNodes = new ArrayList<>();
			for (String childNodePath : listPaths(client, path)) {
				childNodes.add(new Node(client, path + "/" + childNodePath));
			}
			return childNodes;
		}

		/**
		 * 当前节点是否是子节点
		 *
		 * @return
		 * @throws Exception
		 */
		public boolean isLeafNode() throws Exception {
			return getChildNodes().size() == 0;
		}
	}

	public static void makeThriftServiceOffline(ThriftServiceBean bean) {
		assertThriftServiceBean(bean);
		String rpcNodePath = "/OFFLINE" + bean.getBasepath().toUpperCase() + "/" + bean.getServiceName() + "_offline/" + bean.getShardId() + "/rpc/"
				+ bean.getReplicaId();
		CuratorFramework client = clientMap.get(bean.getZkConnectionString());
		assertClient(client);
		try {
			deleteNode(client, rpcNodePath);
			createPermentNode(client, rpcNodePath, bean.getConnectionString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void makeThriftServiceOnline(ThriftServiceBean bean) {
		assertThriftServiceBean(bean);
		String rpcNodePath = "/OFFLINE" + bean.getBasepath().toUpperCase() + "/" + bean.getServiceName() + "_offline/" + bean.getShardId() + "/rpc/"
				+ bean.getReplicaId();
		CuratorFramework client = clientMap.get(bean.getZkConnectionString());
		assertClient(client);
		try {
			deleteNode(client, rpcNodePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void deleteNode(CuratorFramework client, String nodepath) throws Exception {
		assertClient(client);
		if (TZUtil.isEmpty(nodepath))
			throw new IllegalStateException("monitorNodePath is null");
		Stat stat = checkNodePath(client, nodepath, null);
		if (stat != null) {
			log.info("delete -- > {}", nodepath);
			client.delete().forPath(nodepath);
		}
	}

	public static void createPermentNode(CuratorFramework client, String nodepath, String nodeValue) throws Exception {
		assertClient(client);
		if (TZUtil.isEmpty(nodepath)) {
			throw new IllegalStateException("nodepath is null");
		}
		if (TZUtil.isEmpty(nodeValue)) {
			throw new IllegalStateException("nodeValue is null");
		}
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(nodepath, nodeValue.getBytes(DataDict.CHARACTER_SET_ENCODING_UTF8));
	}

	private static Stat checkNodePath(CuratorFramework client, String nodepath, CuratorWatcher curatorWatcher) throws Exception {
		assertClient(client);
		if (TZUtil.isEmpty(nodepath))
			throw new IllegalStateException("nodepath is null");
		if (TZUtil.isEmpty(curatorWatcher)) {
			return client.checkExists().forPath(nodepath);

		} else {
			return client.checkExists().usingWatcher(curatorWatcher).forPath(nodepath);
		}
	}

	private static void assertThriftServiceBean(ThriftServiceBean bean) {
		if (TZUtil.isEmpty(bean))
			throw new IllegalStateException("ThriftServiceBean 不能为null");
		if (TZUtil.isEmpty(bean.getBasepath()))
			throw new IllegalStateException("ThriftServiceBean[basepath] 不能为null");
		if (TZUtil.isEmpty(bean.getServiceName()))
			throw new IllegalStateException("ThriftServiceBean[serviceName] 不能为null");
		if (TZUtil.isEmpty(bean.getShardId()))
			throw new IllegalStateException("ThriftServiceBean[shardId] 不能为null");
		if (TZUtil.isEmpty(bean.getReplicaId()))
			throw new IllegalStateException("ThriftServiceBean[replicaId] 不能为null");
	}
	
	public static String getSimpleServiceName(Class<?> clz) {
		String serviceName = clz.getName();
		return serviceName.substring(serviceName.lastIndexOf(".") + 1, serviceName.lastIndexOf("$"));
	}

}
