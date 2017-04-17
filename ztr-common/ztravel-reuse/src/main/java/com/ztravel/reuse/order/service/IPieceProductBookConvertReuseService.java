package com.ztravel.reuse.order.service;

import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.product.entity.ProductBookVo;

public interface IPieceProductBookConvertReuseService {
	
	ProductBookVo pieceProduct2BookVo(DetailToOrderCriteria criteria)throws Exception;
	
	DetailToOrderCriteria getDetailToOrderCriteria(ProductBookItem productBookItem);
	
}
