package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;

public interface IUnVisaProductDao extends IPieceProductDao{

	List<UnVisaProduct> selectByMap(Map<String,String> map) throws Exception;

	UnVisaProduct getProductById(String id);
	
	public UnVisaProduct getUnvisaProductByPid(String pid) ;

    int updateUnVisaInfo(UnVisaProduct unVisaProduct);

}
