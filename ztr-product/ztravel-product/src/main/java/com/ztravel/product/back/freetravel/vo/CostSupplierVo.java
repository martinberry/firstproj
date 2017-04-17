package com.ztravel.product.back.freetravel.vo;



/**
 * @author wanhaofan
 * 供应商
 * */
public class CostSupplierVo {
	private Integer supplierId ;
	
	private String supplierName ;
	
	private String supplierNameTransfer ;
	
	private Double cost ;

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public String getSupplierNameTransfer() {
		return supplierNameTransfer;
	}

	public void setSupplierNameTransfer(String supplierNameTransfer) {
		this.supplierNameTransfer = supplierNameTransfer;
	}


}
