package com.ztravel.product.front.wo;

import java.util.List;

import com.ztravel.product.back.freetravel.entity.DescribeItem;

public class TripWo {

	//标题
	private String title ;

	//内容
	private String content ;

	private List<DescribeItem> desItems;

	//航班
//	private List<FlightInfo> flightInfos ;

	//酒店
	private HotelInfo hotelInfo ;

	//早餐
	private String breakfest ;

	//中餐
	private String lunch ;

	//晚餐
	private String dinner ;

	//排序
	private Integer index ;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}

	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}

	public String getBreakfest() {
		return breakfest;
	}

	public void setBreakfest(String breakfest) {
		this.breakfest = breakfest;
	}

	public String getLunch() {
		return lunch;
	}

	public void setLunch(String lunch) {
		this.lunch = lunch;
	}

	public String getDinner() {
		return dinner;
	}

	public void setDinner(String dinner) {
		this.dinner = dinner;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public List<DescribeItem> getDesItems() {
		return desItems;
	}

	public void setDesItems(List<DescribeItem> desItems) {
		this.desItems = desItems;
	}


}
