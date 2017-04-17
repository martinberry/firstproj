package com.travelzen.swordfish.thrift.common;

public class ThriftServerPro {
	
	/**
	 * server ip
	 */
	private String server_ip;
	
	/**
	 * server port
	 */
	private String server_port;
	
	/**
	 * sharedid 
	 */
	private String server_zookeeper_shardid;
	
	/**
	 * replicaid
	 */
	private String server_zookeeper_replicaid;
	
	/**
	 * zk service name
	 */
	private String client_zookeeper_servicename;
	
	/**
	 * zk prefix
	 */
	private String client_zookeeper_prefix;
	
	
	public String getServer_ip() {
		return server_ip;
	}
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public String getServer_port() {
		return server_port;
	}
	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}
	public String getServer_zookeeper_shardid() {
		return server_zookeeper_shardid;
	}
	public void setServer_zookeeper_shardid(String server_zookeeper_shardid) {
		this.server_zookeeper_shardid = server_zookeeper_shardid;
	}
	public String getServer_zookeeper_replicaid() {
		return server_zookeeper_replicaid;
	}
	public void setServer_zookeeper_replicaid(String server_zookeeper_replicaid) {
		this.server_zookeeper_replicaid = server_zookeeper_replicaid;
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
	
	public ThriftServerPro(){
		
	}
}
