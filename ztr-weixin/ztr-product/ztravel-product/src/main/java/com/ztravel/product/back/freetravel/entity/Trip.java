package com.ztravel.product.back.freetravel.entity;

import java.util.List;

/**
 * @author wanhaofan
 * 自由行产品行程
 * */
public class Trip {
	//标题
	private String title ;

	//内容
	private String content ;

	private List<DescribeItem> desItems;

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
