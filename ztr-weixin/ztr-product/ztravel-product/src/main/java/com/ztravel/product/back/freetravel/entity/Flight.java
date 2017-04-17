package com.ztravel.product.back.freetravel.entity;

import java.util.List;


/**
 * @author wanhaofan
 * 航班信息
 * */
public class Flight {

	private List<FlightInfo> infos ;

	//内部备注
	private String innerRemark ;

	//航程备注
	private String airRangeRemark ;

	public String getInnerRemark() {
		return innerRemark;
	}

	public void setInnerRemark(String innerRemark) {
		this.innerRemark = innerRemark;
	}

	public List<FlightInfo> getInfos() {
		return infos;
	}

	public void setInfos(List<FlightInfo> infos) {
		this.infos = infos;
	}

	public String getAirRangeRemark() {
		return airRangeRemark;
	}

	public void setAirRangeRemark(String airRangeRemark) {
		this.airRangeRemark = airRangeRemark;
	}


}
