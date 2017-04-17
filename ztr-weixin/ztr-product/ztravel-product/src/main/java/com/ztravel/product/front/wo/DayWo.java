package com.ztravel.product.front.wo;

import java.util.List;

import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.SaleStatus;
import com.ztravel.product.weixin.wo.WxDayWo;

public class DayWo implements Comparable<DayWo>{
	//@link SaleUnit
	private Double adultPrice ;
	private Double childPrice ;
	private Double singleRoomPrice ;
	private Integer stock ;
	private Integer usedStock ;
	private Double marketPrice ;
	private Integer inAdvanceDays ;
	private String inAdvanceHours ;
	private SaleStatus saleStatus ;
	private Boolean hasChildPrice;
	private boolean isBefore ;
	//日期
	private String day ;

	private List<SalesPackage> salesPackages;

	public List<SalesPackage> getSalesPackages() {
		return salesPackages;
	}
	public void setSalesPackages(List<SalesPackage> salesPackages) {
		this.salesPackages = salesPackages;
	}
	public Double getAdultPrice() {
		return adultPrice;
	}
	public void setAdultPrice(Double adultPrice) {
		this.adultPrice = adultPrice;
	}
	public Double getChildPrice() {
		return childPrice;
	}
	public void setChildPrice(Double childPrice) {
		this.childPrice = childPrice;
	}
	public Double getSingleRoomPrice() {
		return singleRoomPrice;
	}
	public void setSingleRoomPrice(Double singleRoomPrice) {
		this.singleRoomPrice = singleRoomPrice;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Integer getUsedStock() {
		return usedStock;
	}
	public void setUsedStock(Integer usedStock) {
		this.usedStock = usedStock;
	}
	public Double getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(Double marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getInAdvanceDays() {
		return inAdvanceDays;
	}
	public void setInAdvanceDays(Integer inAdvanceDays) {
		this.inAdvanceDays = inAdvanceDays;
	}
	public String getInAdvanceHours() {
		return inAdvanceHours;
	}
	public void setInAdvanceHours(String inAdvanceHours) {
		this.inAdvanceHours = inAdvanceHours;
	}
	public Boolean getHasChildPrice() {
		return hasChildPrice;
	}
	public void setHasChildPrice(Boolean hasChildPrice) {
		this.hasChildPrice = hasChildPrice;
	}
	public SaleStatus getSaleStatus() {
		return saleStatus;
	}
	public void setSaleStatus(SaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}
	public boolean isBefore() {
		return isBefore;
	}
	public void setBefore(boolean isBefore) {
		this.isBefore = isBefore;
	}

	@Override
	public int compareTo(DayWo o) {
		 return this.getDay().compareTo(o.getDay());
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
}
