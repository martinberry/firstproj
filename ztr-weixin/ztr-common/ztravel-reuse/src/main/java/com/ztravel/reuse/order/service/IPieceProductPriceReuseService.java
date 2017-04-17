package com.ztravel.reuse.order.service;

public interface IPieceProductPriceReuseService {
	
	Double[] getVisaProductPriceInfo(String productId, String priceId)throws Exception;
	
	Double[] getUnVisaProductPriceInfo(String productId, String priceId)throws Exception;
	
	Double getPieceProductTotalPrice(String productId, String priceId,Integer adultNum, Integer childNum,String productNature) throws Exception;
	
	String getPidById(String productId,String type) throws Exception;
	
	Boolean isReleased(String productNature,String productId);
}
