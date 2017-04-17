package com.ztravel.product.po.pieces.unvisa;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.ztravel.product.po.pieces.common.PieceProduct;

@Entity(value="UnVisaProduct", noClassnameStored=true)
public class UnVisaProduct extends PieceProduct{

	private UnVisaDetailInfo detailInfo;

    public UnVisaDetailInfo getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(UnVisaDetailInfo detailInfo) {
        this.detailInfo = detailInfo;
    }
}
