package com.ztravel.product.client.entity;

public class ProductBasicDetail {

	/**
	 * 产品id主键
	 */
	private String productId ;

	/**
	 * 产品业务ID
	 */
	private String pid ;

	/**
	 * 产品名称
	 */
	private String pName ;

	/**
	 * 产品显示图
	 */
	private String image ;

	/**
	 * 目的地区域
	 */
	private String toContinent ;

	/**
	 * 产品状态
	 */
	private String status ;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getToContinent() {
		return toContinent;
	}

	public void setToContinent(String toContinent) {
		this.toContinent = toContinent;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
