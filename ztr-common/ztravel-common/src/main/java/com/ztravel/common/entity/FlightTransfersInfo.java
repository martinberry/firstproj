package com.ztravel.common.entity;

/**
 * 产品航程中转(经停)信息
 * @author liuzhuo
 *
 */
public class FlightTransfersInfo {
	
	/**
	 * 中转 transfer/经停stop （暂时没有中转，只有经停）
	 */
	private String transfersType;
	
	/**
	 * 中转/经停 机场
	 */
	private String transfersAirPort;
	
	/**
	 * 到达时间
	 */
	private String arriveTime;
	
	/**
	 * 出发时间
	 */
	private String departTime;
	
	/**
	 * 停留时间
	 */
	private String stayTime;

	public String getTransfersType() {
		return transfersType;
	}

	public void setTransfersType(String transfersType) {
		this.transfersType = transfersType;
	}

	public String getTransfersAirPort() {
		return transfersAirPort;
	}

	public void setTransfersAirPort(String transfersAirPort) {
		this.transfersAirPort = transfersAirPort;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}

	public String getStayTime() {
		return stayTime;
	}

	public void setStayTime(String stayTime) {
		this.stayTime = stayTime;
	}
	
	

}
