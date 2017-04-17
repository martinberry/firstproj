package com.ztravel.common.weixin.notice;

public class TravelEndNotice {

	private String userName;

	private String productContent;

	private String couponUrl;//评价送券链接

	private String endDate;

	private String detailUtl;

	private String openId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProductContent() {
		return productContent;
	}

	public void setProductContent(String productContent) {
		this.productContent = productContent;
	}

	public String getCouponUrl() {
		return couponUrl;
	}

	public void setCouponUrl(String couponUrl) {
		this.couponUrl = couponUrl;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDetailUtl() {
		return detailUtl;
	}

	public void setDetailUtl(String detailUtl) {
		this.detailUtl = detailUtl;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
}
