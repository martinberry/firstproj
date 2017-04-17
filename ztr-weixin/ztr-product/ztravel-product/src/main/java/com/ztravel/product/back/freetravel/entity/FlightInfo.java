package com.ztravel.product.back.freetravel.entity;

import com.ztravel.product.back.freetravel.enums.AirRange;


/**
 * @author wanhaofan
 * 航班信息
 * */
public class FlightInfo{

	//第几天
	private Integer offsetDays ;

	//航空公司
	private String airLine ;

	//航班号
	private String flightNo ;

	//舱位
	private String cabin ;

	//机型
	private String flightModel ;

	//出发机场
	private String fromAirPort ;

	//出发城市
	private String fromCity ;

	//出发时间
	private String departureTime ;

	//到达机场
	private String toAirPort ;

	//到达城市
	private String toCity ;

	//到达时间
	private String arrivalTime ;

	//到达偏移天数
	private Integer addDays ;

	//航程信息
	private AirRange airRange ;

	//经停信息
	private String stop ;

	/**
	 * 航程排序------为减少复杂度,在此做约定
	 * 如果是去程/返程,则按照普通方式排序
	 * 如果是中间程
	 * 第一程:1000+index = airRangeIndex
	 * 第二程:2000+index = airRangeIndex
	 * 如第一程第一二次中转分别为1001,1002
	 * 第二程第一二次中转分别为2001,2002
	 * 中转和经停信息
	 * 去程1,2,3
	 * 返程1,2,3
	 * */
	private Integer airRangeIndex ;

	public Integer getOffsetDays() {
		return offsetDays;
	}

	public void setOffsetDays(Integer offsetDays) {
		this.offsetDays = offsetDays;
	}

	public String getAirLine() {
		return airLine;
	}

	public void setAirLine(String airLine) {
		this.airLine = airLine;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getFlightModel() {
		return flightModel;
	}

	public void setFlightModel(String flightModel) {
		this.flightModel = flightModel;
	}

	public String getFromAirPort() {
		return fromAirPort;
	}

	public void setFromAirPort(String fromAirPort) {
		this.fromAirPort = fromAirPort;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getToAirPort() {
		return toAirPort;
	}

	public void setToAirPort(String toAirPort) {
		this.toAirPort = toAirPort;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getAddDays() {
		return addDays;
	}

	public void setAddDays(Integer addDays) {
		this.addDays = addDays;
	}

	public AirRange getAirRange() {
		return airRange;
	}

	public void setAirRange(AirRange airRange) {
		this.airRange = airRange;
	}

	public Integer getAirRangeIndex() {
		return airRangeIndex;
	}

	public void setAirRangeIndex(Integer airRangeIndex) {
		this.airRangeIndex = airRangeIndex;
	}

	public String getFromCity() {
		return fromCity;
	}

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

}
