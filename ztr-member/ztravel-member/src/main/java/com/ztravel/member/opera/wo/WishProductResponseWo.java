package com.ztravel.member.opera.wo;

public class WishProductResponseWo {

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
	 * 目的地区域
	 */
	private String toContinent ;

	/**
	 * 产品状态
	 */
	private String status ;

	/**
	 * 最近收藏时间
	 */
	private String recentTime ;

	/**
	 * 收藏数量
	 */
	private Double count ;

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

	public String getRecentTime() {
		return recentTime;
	}

	public void setRecentTime(String recentTime) {
		this.recentTime = recentTime;
	}

	public Double getCount() {
		return count;
	}

	public void setCount(Double count) {
		this.count = count;
	}

}
