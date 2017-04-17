package com.travelzen.swordfish.thrift.common;

public class ThriftClientPro {
	
	/**
	 * client ip
	 */
	private String client_ip;
	
	/**
	 * clent port
	 */
	private String client_port;

	/**
	 * zk service name
	 */
	private String client_zookeeper_servicename;
	
	/**
	 * zk prefix
	 */
	private String client_zookeeper_prefix;

	public String getClient_ip() {
		return client_ip;
	}

	public void setClient_ip(String client_ip) {
		this.client_ip = client_ip;
	}

	public String getClient_port() {
		return client_port;
	}

	public void setClient_port(String client_port) {
		this.client_port = client_port;
	}

	public String getClient_zookeeper_servicename() {
		return client_zookeeper_servicename;
	}

	public void setClient_zookeeper_servicename(String client_zookeeper_servicename) {
		this.client_zookeeper_servicename = client_zookeeper_servicename;
	}

	public String getClient_zookeeper_prefix() {
		return client_zookeeper_prefix;
	}

	public void setClient_zookeeper_prefix(String client_zookeeper_prefix) {
		this.client_zookeeper_prefix = client_zookeeper_prefix;
	}
	
	public ThriftClientPro() {
		
	}
	
	
	public ThriftClientPro(String client_ip, String client_port, String client_zookeeper_servicename,String client_zookeeper_prefix) {
		this.client_ip = client_ip;
		this.client_port = client_port;
		this.client_zookeeper_servicename = client_zookeeper_servicename;
		this.client_zookeeper_prefix = client_zookeeper_prefix;
	}
	

}
