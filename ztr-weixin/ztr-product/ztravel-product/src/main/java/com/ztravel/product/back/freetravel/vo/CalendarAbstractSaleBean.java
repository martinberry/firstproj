package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.SaleUnit;


/**
 * @author wanhaofan
 * @see CalendarBatchSaveBean CalendarSingleSaveBean
 * 批量/单个修改新增用
 * */
public class CalendarAbstractSaleBean {
	//产品ID
	protected String id ;
	
	//销售单位
	protected SaleUnit saleUnit ;		
	//成人价格 		
	protected Double adultPrice ;		
	//成人是否含税 		
	protected Boolean isAdultPriceHasTax ;		
	//是否含有儿童价格 		
	protected Boolean hasChildPrice ;		
	public Boolean getHasChildPrice() {		
		return hasChildPrice;		
	}		
	public void setHasChildPrice(Boolean hasChildPrice) {		
		this.hasChildPrice = hasChildPrice;		
	}		
	//儿童价格 		
	protected Double childPrice ;		
	//儿童是否含税 		
	protected Boolean isChildPriceHasTax ;		
	//单房差 		
	protected Double singleRoomPrice ;
	
	protected List<SalesPackage> salesPackages ;

	//可用库存
	protected Integer stock ;

	//市场价
	protected Double marketPrice ;

	//提前几天预订
	protected Integer inAdvanceDays ;

	//提前几天的几点前可预订
	protected String inAdvanceHours ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<SalesPackage> getSalesPackages() {
		return salesPackages;
	}

	public void setSalesPackages(List<SalesPackage> salesPackages) {
		this.salesPackages = salesPackages;
	}
	
	public SaleUnit getSaleUnit() {	
		return saleUnit;
	}
	public void setSaleUnit(SaleUnit saleUnit) {
		this.saleUnit = saleUnit;
	}		
	public Double getAdultPrice() {		
		return adultPrice;		
	}		
	public void setAdultPrice(Double adultPrice) {		
		this.adultPrice = adultPrice;		
	}		
	public Boolean getIsAdultPriceHasTax() {		
		return isAdultPriceHasTax;		
	}		
	public void setIsAdultPriceHasTax(Boolean isAdultPriceHasTax) {		
		this.isAdultPriceHasTax = isAdultPriceHasTax;		
	}		
	public Double getChildPrice() {		
		return childPrice;		
	}		
	public void setChildPrice(Double childPrice) {		
		this.childPrice = childPrice;		
	}		
	public Boolean getIsChildPriceHasTax() {		
		return isChildPriceHasTax;		
	}		
	public void setIsChildPriceHasTax(Boolean isChildPriceHasTax) {		
		this.isChildPriceHasTax = isChildPriceHasTax;		
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

}
