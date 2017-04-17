package com.ztravel.product.back.freetravel.vo;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.FlightInfo;


/**
 * @author wanhaofan
 * 真旅行产品机票信息打包低价维护
 * */
public class CostFlightVo {

	private List<FlightInfo> go ;

	private List<FlightInfo> back ;

	private String from ;

	private Integer goNum ;

	private Integer backNum ;

	private Integer middleNum ;

	private Map<String, List<FlightInfo>> middle ;

	//航程备注
	private String airRangeRemark ;

	//内部备注
	private String innerRemark ;

	public String getInnerRemark() {
		return innerRemark;
	}

	public void setInnerRemark(String innerRemark) {
		this.innerRemark = innerRemark;
	}

	public String getAirRangeRemark() {
		return airRangeRemark;
	}

	public void setAirRangeRemark(String airRangeRemark) {
		this.airRangeRemark = airRangeRemark;
	}

	public List<FlightInfo> getGo() {
		return go;
	}

	public void setGo(List<FlightInfo> go) {
		this.go = go;
	}

	public List<FlightInfo> getBack() {
		return back;
	}

	public void setBack(List<FlightInfo> back) {
		this.back = back;
	}

	public Integer getGoNum() {
		return goNum;
	}

	public void setGoNum(Integer goNum) {
		this.goNum = goNum;
	}

	public Integer getBackNum() {
		return backNum;
	}

	public void setBackNum(Integer backNum) {
		this.backNum = backNum;
	}

	public Integer getMiddleNum() {
		return middleNum;
	}

	public void setMiddleNum(Integer middleNum) {
		this.middleNum = middleNum;
	}

	public Map<String, List<FlightInfo>> getMiddle() {
		return middle;
	}

	public void setMiddle(Map<String, List<FlightInfo>> middle) {
		this.middle = middle;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


}
