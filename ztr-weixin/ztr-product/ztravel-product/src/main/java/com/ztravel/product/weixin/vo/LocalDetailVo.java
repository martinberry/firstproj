package com.ztravel.product.weixin.vo;

import com.ztravel.product.po.pieces.unvisa.UnVisaDetailInfo;

public class LocalDetailVo extends PieceProductDetailVo {
	
	private UnVisaDetailInfo detailInfo;

	public UnVisaDetailInfo getDetailInfo() {
		return detailInfo;
	}

	public void setDetailInfo(UnVisaDetailInfo detailInfo) {
		this.detailInfo = detailInfo;
	}
}
