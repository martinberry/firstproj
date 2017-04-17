package com.ztravel.member.opera.wo;

import com.ztravel.common.entity.PaginationEntity;

public class WishDetailPageWo extends PaginationEntity {

	/**
	 * 产品id主键
	 */
	private String productId ;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

}
