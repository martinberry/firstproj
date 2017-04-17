package com.ztravel.product.weixin.wo;

import java.util.List;
import java.util.Map;

import com.ztravel.product.client.wo.FlightInfoWo;

public class FlightWo {

	private List<FlightInfoWo> go ;

	private List<FlightInfoWo> back ;

	private Integer goNum ;

	private Integer backNum ;

	private Integer middleNum ;

	private Map<String, List<FlightInfoWo>> middle ;

	//页面展示用
	private List<FlightInfoWo> main ;

	//航程备注
	private String airRangeRemark ;

	public List<FlightInfoWo> getGo() {
		return go;
	}

	public void setGo(List<FlightInfoWo> go) {
		this.go = go;
	}

	public List<FlightInfoWo> getBack() {
		return back;
	}

	public void setBack(List<FlightInfoWo> back) {
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

	public Map<String, List<FlightInfoWo>> getMiddle() {
		return middle;
	}

	public void setMiddle(Map<String, List<FlightInfoWo>> middle) {
		this.middle = middle;
	}

	public List<FlightInfoWo> getMain() {
		return main;
	}

	public void setMain(List<FlightInfoWo> main) {
		this.main = main;
	}

	public String getAirRangeRemark() {
		return airRangeRemark;
	}

	public void setAirRangeRemark(String airRangeRemark) {
		this.airRangeRemark = airRangeRemark;
	}


}
