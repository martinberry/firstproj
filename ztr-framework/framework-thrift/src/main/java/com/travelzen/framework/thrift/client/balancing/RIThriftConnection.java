package com.travelzen.framework.thrift.client.balancing;

import java.lang.reflect.Constructor;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.thrift.protocol.RIThriftProtocol;

public class RIThriftConnection<T> extends Connection<T> {

	private Logger LOG = LoggerFactory.getLogger(RIThriftConnection.class);

	private int SO_TIMEOUT = 100000;
	private TTransport transport;
	private TProtocol protocol;
	private boolean didFailConnect = false;

	private T client;

	public RIThriftConnection(String host, int port, boolean framed, Class<? extends T> clazz) throws Exception {
		this.host = host;
		this.port = port;
		TSocket socket = new TSocket(host, port, SO_TIMEOUT);
		if (framed) {
			transport = new TFramedTransport(socket);
		} else {
			transport = socket;
		}
		protocol = new RIThriftProtocol(transport);
		Constructor<? extends T> constructor = clazz.getDeclaredConstructor(TProtocol.class);
		client = constructor.newInstance(protocol);
	}

	@Override
	public T getClient() throws Exception {
		LOG.info("get client object::{}", client);
		return client;
	}

	@Override
	public void ensureOpen() {
		if (transport.isOpen()){
			LOG.info("transport is opened:{}, {}", host,port);
			return;
		}
		try {
			LOG.info("try open transport:{},{}", host,port);
			transport.open();
		} catch (TTransportException e) {
			didFailConnect = true;
			LOG.error("an error occured when ensure transport is open.", e);
		}
	}

	@Override
	public void teardown() {
		try {
			LOG.info("try tear down transport:{},{}", host,port);
			transport.close();
		} catch (Exception e) {
			LOG.error("fail to tear down.", e);
		}
		protocol.getTransport().close();
	}

	@Override
	public void flush() {
	}

	@Override
	public boolean isHealthy() {
		boolean healthy = !didFailConnect && super.isHealthy();
		LOG.info("check transport healthy:{},{}, healthy:{}", host,port, healthy);
		return healthy;
	}

	@Override
	public String toString() {
		return "RIThriftConnection [SO_TIMEOUT=" + SO_TIMEOUT + ", transport="
				+ transport + ", protocol=" + protocol + ", didFailConnect="
				+ didFailConnect + ", client=" + client + ", host=" + host
				+ ", port=" + port + ", didFail=" + didFail + "]";
	}
	

}
