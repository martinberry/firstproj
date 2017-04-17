package com.ztravel.product.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.product.po.pieces.visa.VisaProduct;

public interface IVisaProductDao extends IPieceProductDao{

	List<VisaProduct> selectByMap(Map<String,String> map) throws Exception;

	List<VisaProduct> selectByMapAndPage(Map<String,String> map,Integer pageNum,Integer pageSize) throws Exception;

	VisaProduct getProductById(String id) throws Exception;

	VisaProduct getProductByPid(String id) throws Exception;

    int updateVisaInfo(VisaProduct visaProduct);
    
    List<VisaProduct> search(Map<String,String> params) throws Exception;
}
