package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import com.ztravel.product.back.freetravel.enums.BedType;
import com.ztravel.product.back.freetravel.enums.BreakFestType;

/**
 * @author wanhaofan
 * 真旅行产品酒店信息打包低价维护VO
 * */
public class CostHotelVo {

	//酒店资源ID
	private String id ;

	//酒店名称
	private String name ;

	//入住偏移时间
	private String checkinDaysStr ;

	//入住偏移时间
	private List<Integer> checkinDays ;

	//酒店卖点
	private String highLights ;

	//酒店星级
	private String rate ;

	//酒店目的地
	private String dest ;

	//房型名称
	private String roomType ;

	//床型名称
	private BedType bedType ;

	//床型名称
	private String bedTypeCN ;

	//早餐
	private BreakFestType breakFestType ;

	//早餐
	private String breakFestTypeCN ;

	//酒店备注
	private String hotelRemark ;

	//内部备注
	private String innerRemark ;

	private CostSupplierVo supplier ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Integer> getCheckinDays() {
		return checkinDays;
	}

	public void setCheckinDays(List<Integer> checkinDays) {
		this.checkinDays = checkinDays;
	}

	public String getHighLights() {
		return highLights;
	}

	public void setHighLights(String highLights) {
		this.highLights = highLights;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public BedType getBedType() {
		return bedType;
	}

	public void setBedType(BedType bedType) {
		this.bedType = bedType;
	}

	public BreakFestType getBreakFestType() {
		return breakFestType;
	}

	public void setBreakFestType(BreakFestType breakFestType) {
		this.breakFestType = breakFestType;
	}

	public String getHotelRemark() {
		return hotelRemark;
	}

	public void setHotelRemark(String hotelRemark) {
		this.hotelRemark = hotelRemark;
	}

	public String getInnerRemark() {
		return innerRemark;
	}

	public void setInnerRemark(String innerRemark) {
		this.innerRemark = innerRemark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getDest() {
		return dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

	public String getBedTypeCN() {
		return bedTypeCN;
	}

	public void setBedTypeCN(String bedTypeCN) {
		this.bedTypeCN = bedTypeCN;
	}

	public String getBreakFestTypeCN() {
		return breakFestTypeCN;
	}

	public void setBreakFestTypeCN(String breakFestTypeCN) {
		this.breakFestTypeCN = breakFestTypeCN;
	}

	public String getCheckinDaysStr() {
		return checkinDaysStr;
	}

	public void setCheckinDaysStr(String checkinDaysStr) {
		this.checkinDaysStr = checkinDaysStr;
	}

	public CostSupplierVo getSupplier() {
		return supplier;
	}

	public void setSupplier(CostSupplierVo supplier) {
		this.supplier = supplier;
	}

}
