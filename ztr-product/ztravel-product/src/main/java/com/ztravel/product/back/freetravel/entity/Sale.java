package com.ztravel.product.back.freetravel.entity;

import java.util.List;

import com.ztravel.product.back.freetravel.enums.SaleStatus;
import com.ztravel.product.back.freetravel.enums.SaleUnit;

/**
 * @author wanhaofan
 * 自由行产品某天的销售规则及情况
 * */
public class Sale {

    //成人价格
    private Double adultPrice ;

    //成人是否含税
    private Boolean isAdultPriceHasTax ;

    private Boolean hasChildPrice ;
    
    private SaleUnit saleUnit ;

    //儿童价格
    private Double childPrice ;

    //儿童是否含税
    private Boolean isChildPriceHasTax ;

    //单房差
    private Double singleRoomPrice ;

	//可用库存
	private Integer stock ;

	//已用库存
	private Integer usedStock ;

	//市场价
	private Double marketPrice ;

	//提前几天预订
	private Integer inAdvanceDays ;

	//提前几天的几点前可预订 XX
	private String inAdvanceHours ;

	//是否关闭
	private SaleStatus saleStatus ;

	//销售套餐
	private List<SalesPackage> salesPackages;

	public Double getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(Double adultPrice) {
        this.adultPrice = adultPrice;
    }

    public Boolean isAdultPriceHasTax() {
        return isAdultPriceHasTax;
    }

    public void setAdultPriceTax(Boolean isAdultPriceHasTax) {
        this.isAdultPriceHasTax = isAdultPriceHasTax;
    }

    public Boolean getHasChildPrice() {
        return hasChildPrice;
    }

    public void setHasChildPrice(Boolean hasChildPrice) {
        this.hasChildPrice = hasChildPrice;
    }

    public Double getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(Double childPrice) {
        this.childPrice = childPrice;
    }

    public Boolean isChildPriceHasTax() {
        return isChildPriceHasTax;
    }

    public void setChildPriceTax(Boolean isChildPriceHasTax) {
        this.isChildPriceHasTax = isChildPriceHasTax;
    }

    public Double getSingleRoomPrice() {
        return singleRoomPrice;
    }

    public void setSingleRoomPrice(Double singleRoomPrice) {
        this.singleRoomPrice = singleRoomPrice;
    }

    public Boolean existsSalesPackage() {
	    return this.salesPackages != null;
	}

	public Boolean isClosed() {
		return this.saleStatus == SaleStatus.CLOSED;
	}

	public Boolean canScheduled() {
		return this.saleStatus != SaleStatus.NOT_SCHEDULED;
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

	public SaleStatus getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(SaleStatus saleStatus) {
		this.saleStatus = saleStatus;
	}

    public List<SalesPackage> getSalesPackages() {
        return salesPackages;
    }

    public void setSalesPackages(List<SalesPackage> salesPackages) {
        this.salesPackages = salesPackages;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((inAdvanceDays == null) ? 0 : inAdvanceDays.hashCode());
		result = prime * result
				+ ((inAdvanceHours == null) ? 0 : inAdvanceHours.hashCode());
		result = prime * result
				+ ((marketPrice == null) ? 0 : marketPrice.hashCode());
		result = prime * result
				+ ((saleStatus == null) ? 0 : saleStatus.hashCode());
		result = prime * result
				+ ((salesPackages == null) ? 0 : salesPackages.hashCode());
		result = prime * result + ((stock == null) ? 0 : stock.hashCode());
		result = prime * result
				+ ((usedStock == null) ? 0 : usedStock.hashCode());
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
		Sale other = (Sale) obj;
		if (inAdvanceDays == null) {
			if (other.inAdvanceDays != null)
				return false;
		} else if (!inAdvanceDays.equals(other.inAdvanceDays))
			return false;
		if (inAdvanceHours == null) {
			if (other.inAdvanceHours != null)
				return false;
		} else if (!inAdvanceHours.equals(other.inAdvanceHours))
			return false;
		if (marketPrice == null) {
			if (other.marketPrice != null)
				return false;
		} else if (!marketPrice.equals(other.marketPrice))
			return false;
		if (saleStatus != other.saleStatus)
			return false;
		if (salesPackages == null) {
			if (other.salesPackages != null)
				return false;
		} else if (!salesPackages.equals(other.salesPackages))
			return false;
		if (stock == null) {
			if (other.stock != null)
				return false;
		} else if (!stock.equals(other.stock))
			return false;
		if (usedStock == null) {
			if (other.usedStock != null)
				return false;
		} else if (!usedStock.equals(other.usedStock))
			return false;
		return true;
	}

	public SaleUnit getSaleUnit() {
		return saleUnit;
	}

	public void setSaleUnit(SaleUnit saleUnit) {
		this.saleUnit = saleUnit;
	}



}
