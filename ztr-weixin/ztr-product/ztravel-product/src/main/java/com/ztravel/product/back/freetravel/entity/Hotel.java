package com.ztravel.product.back.freetravel.entity;

import java.util.List;

import com.ztravel.product.back.freetravel.enums.BedType;
import com.ztravel.product.back.freetravel.enums.BreakFestType;

/**
 * @author wanhaofan
 * 真旅行产品酒店信息
 * */
public class Hotel {

	//酒店资源ID
	private String id ;

	//入住偏移时间
	private List<Integer> checkinDays ;

	//供应商
	private String supplier ;

	//酒店卖点
	private String highLights ;

	//房型名称
	private String roomType ;

	//床型名称
	private BedType bedType ;

	//早餐
	private BreakFestType breakFestType ;

	//酒店备注
	private String hotelRemark ;

	//内部备注
	private String innerRemark ;

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

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hotel other = (Hotel) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
