package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import javax.validation.constraints.Pattern;

import com.ztravel.product.back.freetravel.entity.FlightInfo;


/**
 * @author wanhaofan
 * 机票信息保存bean
 * */
public class FlightSaveReqBean {

	private String id ;

	private List<FlightInfo> gos ;

	private List<FlightInfo> middles ;

	private List<FlightInfo> backs ;

	@Pattern(regexp = "^[\\S ]{0,60}$",message="")
	private String innerRemark ;

	private String airRangeRemark ;

	private String supplier ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<FlightInfo> getGos() {
		return gos;
	}

	public void setGos(List<FlightInfo> gos) {
		this.gos = gos;
	}

	public List<FlightInfo> getMiddles() {
		return middles;
	}

	public void setMiddles(List<FlightInfo> middles) {
		this.middles = middles;
	}

	public List<FlightInfo> getBacks() {
		return backs;
	}

	public void setBacks(List<FlightInfo> backs) {
		this.backs = backs;
	}

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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}


}
