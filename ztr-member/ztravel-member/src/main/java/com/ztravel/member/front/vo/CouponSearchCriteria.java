package com.ztravel.member.front.vo;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.enums.CouponItemStatus;

public class CouponSearchCriteria extends Pagination{


	private CouponItemStatus status;

	public CouponItemStatus getStatus() {
		return status;
	}

	public void setStatus(CouponItemStatus status) {
		this.status = status;
	}

}
