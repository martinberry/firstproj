package com.ztravel.member.front.vo;

public class AddToWishRequest {

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


}
