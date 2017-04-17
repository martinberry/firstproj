package com.ztravel.reuse.product.entity;


/**
 * 产品航程信息
 * @author liuzhuo
 *
 */
public class ProductFlightInfo {

	/**
	 * 航程批次(第一程，第二程)
	 */
	private String flightIndex;

	/**
	 * 出发时间
	 */
	private String departDate;

	/**
	 * 出发机场
	 */
	private String departAirPort;

	/**
	 * 出发航站楼
	 */
	private String departTerminal;

	/**
	 * 到达时间
	 */
	private String departTime;

	/**
	 * 天数偏移
	 * */
	private Integer addDays;

	/**
	 * 航司名
	 */
	private String airLineName;

	/**
	 * 航班号
	 */
	private String flightNum;


	/**
	 * 机型
	 */
	private String planeCode;

	/**
	 * 舱位
	 */
	private String cabin;

	/**
	 * 到达机场
	 */
	private String arriveAirPort;

	/**
	 * 到达航站楼
	 */
	private String arriveTerminal;

	/**
	 * 经停信息
	 * */
	private String stop;

	/**
	 * 到达时间
	 */
	private String arriveTime;

	/**
	 * 航程耗时
	 */
	private String flightTimeCost;

	private String airRangeIndex;
	/**
	 * 出发城市
	 * */
	private String fromCity;
	/**
	 * 到达城市
	 * */
	private String toCity;

	private Integer departDayIndex;
	
	private Integer offsetDays;

	public String getFlightIndex() {
		return flightIndex;
	}

	public void setFlightIndex(String flightIndex) {
		this.flightIndex = flightIndex;
	}


	public Integer getAddDays() {
		return addDays;
	}

	public void setAddDays(Integer addDays) {
		this.addDays = addDays;
	}

	public String getDepartAirPort() {
		return departAirPort;
	}

	public void setDepartAirPort(String departAirPort) {
		this.departAirPort = departAirPort;
	}

	public String getDepartTime() {
		return departTime;
	}

	public void setDepartTime(String departTime) {
		this.departTime = departTime;
	}


	public String getAirLineName() {
		return airLineName;
	}

	public void setAirLineName(String airLineName) {
		this.airLineName = airLineName;
	}

	public String getFlightNum() {
		return flightNum;
	}

	public void setFlightNum(String flightNum) {
		this.flightNum = flightNum;
	}

	public String getPlaneCode() {
		return planeCode;
	}

	public void setPlaneCode(String planeCode) {
		this.planeCode = planeCode;
	}

	public String getCabin() {
		return cabin;
	}

	public void setCabin(String cabin) {
		this.cabin = cabin;
	}

	public String getArriveAirPort() {
		return arriveAirPort;
	}

	public void setArriveAirPort(String arriveAirPort) {
		this.arriveAirPort = arriveAirPort;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getFlightTimeCost() {
		return flightTimeCost;
	}

	public void setFlightTimeCost(String flightTimeCost) {
		this.flightTimeCost = flightTimeCost;
	}

	public String getDepartTerminal() {
		return departTerminal;
	}

	public void setDepartTerminal(String departTerminal) {
		this.departTerminal = departTerminal;
	}

	public String getArriveTerminal() {
		return arriveTerminal;
	}

	public void setArriveTerminal(String arriveTerminal) {
		this.arriveTerminal = arriveTerminal;
	}

	public String getStop() {
		return stop;
	}

	public void setStop(String stop) {
		this.stop = stop;
	}

	public String getDepartDate() {
		return departDate;
	}

	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}

	public String getAirRangeIndex() {
		return airRangeIndex;
	}

	public void setAirRangeIndex(String airRangeIndex) {
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

	public Integer getDepartDayIndex() {
		return departDayIndex;
	}

	public void setDepartDayIndex(Integer departDayIndex) {
		this.departDayIndex = departDayIndex;
	}

	public Integer getOffsetDays() {
		return offsetDays;
	}

	public void setOffsetDays(Integer offsetDays) {
		this.offsetDays = offsetDays;
	}

}
