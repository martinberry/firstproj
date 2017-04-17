package com.travelzen.framework.thrift.util;

public class ThriftServiceBean implements Comparable<ThriftServiceBean> {

	private String serviceName;
	
	private int shardId;
	
	private int replicaId;

	private String connectionString;
	
	private String zkConnectionString;
	
	private boolean offline;
	
	private String basepath;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int getShardId() {
		return shardId;
	}
	
	public void setShardId(int shardId) {
		this.shardId = shardId;
	}

	public int getReplicaId() {
		return replicaId;
	}

	public void setReplicaId(int replicaId) {
		this.replicaId = replicaId;
	}

	public String getConnectionString() {
		return connectionString;
	}

	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	public String getZkConnectionString() {
		return zkConnectionString;
	}

	public void setZkConnectionString(String zkConnectionString) {
		this.zkConnectionString = zkConnectionString;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public String getBasepath() {
		return basepath;
	}

	public void setBasepath(String basepath) {
		this.basepath = basepath;
	}

	@Override
	public String toString() {
		return "ThriftServiceBean [serviceName=" + serviceName + ", shardId=" + shardId + ", replicaId=" + replicaId
				+ ", connectionString=" + connectionString + ", zkConnectionString=" + zkConnectionString
				+ ", offline=" + offline + ", basepath=" + basepath + "]";
	}

	@Override
	public int compareTo(ThriftServiceBean o) {
		int v1 = this.serviceName.compareTo(o.getServiceName());
		if (v1 != 0) {
			return v1;
		}
		return Boolean.compare(this.offline, o.offline);
	}
	
}
