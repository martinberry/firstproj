package com.travelzen.framework.thrift.client.balancing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThriftBalancingClient<T> extends PooledClient<T> {

	private static final Logger LOG = LoggerFactory.getLogger(ThriftBalancingClient.class);

	private boolean framed = false;
	private String host = null;
	private int port = -1;

	public ThriftBalancingClient(String host, int port, boolean framed, Class<? extends T> clazz) {
		super(clazz);
		this.framed = framed;
		this.host = host;
		this.port = port;
	}

	@Override
	public Connection<T> createConnection() {
		try {
			return new RIThriftConnection<T>(host, port, framed, type);
		} catch (Exception ex) {
			LOG.error("create connection failed.", ex);
		}
		return null;
	}

	@Override
	public String toString() {
		return "ThriftBalancingClient [framed=" + framed + ", host=" + host + ", port=" + port + "]";
	}

}
