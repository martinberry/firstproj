package com.ztravel.product.back.freetravel.entity;

import com.github.jmkgreen.morphia.annotations.Entity;

/**
 * 产品销售套餐
 * @author xutian
 *
 */
@Entity(noClassnameStored = true)
public class SalesPackage {

    /**
     * 套餐id
     */
    private String pkgId;

    /**
     * 套餐名称
     */
    private String name;

    /**
     * 套餐说明
     */
    private String desc;

    /**
     * 套餐包含成人数
     */
    private Integer adultNum;

    /**
     * 套餐包含儿童数
     */
    private Integer childrenNum;

    /**
     * 套餐包含总人数
     */
    private Integer sumNum;

    /**
     * 套餐价格
     */
    private Double price;

    /**
     * 套餐人均价格
     */
    private Double perPrice;

    /**
     * 是否含税
     */
    private Boolean isPriceHasTax ;

    public Integer getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(Integer adultNum) {
        this.adultNum = adultNum;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getSumNum() {
        return sumNum;
    }

    public void setSumNum(Integer sumNum) {
        this.sumNum = sumNum;
    }

    public Boolean getIsPriceHasTax() {
        return isPriceHasTax;
    }

    public void setIsPriceHasTax(Boolean isPriceHasTax) {
        this.isPriceHasTax = isPriceHasTax;
    }

	public String getPkgId() {
		return pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getPerPrice() {
		return perPrice;
	}

	public void setPerPrice(Double perPrice) {
		this.perPrice = perPrice;
	}

    
    
}
