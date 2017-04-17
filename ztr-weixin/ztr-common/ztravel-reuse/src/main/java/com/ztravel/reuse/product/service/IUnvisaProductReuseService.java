package com.ztravel.reuse.product.service;

import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;

public interface IUnvisaProductReuseService {
	public UnVisaProduct selectByPid(String pid) throws Exception ;

	public PriceInfo getPriceByPidAndPriceId(String pid, String priceId) throws Exception;

    public UnVisaProduct selectById(String id) throws Exception ;

}
