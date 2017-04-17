package com.ztravel.product.back.freetravel.service;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.ProductSearchCriteria;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.vo.ProductCheckRespBean;
import com.ztravel.product.back.freetravel.vo.ProductMenuVo;



/**
 * @author wanhaofan
 * */
public interface ProductService {

	Boolean updateProgress(String id, Integer progress) ;

	List<Product> findByConditions(Map<String, String> map, int page, int pageSize) throws Exception;

	Long count();

	Long getCountByConditions(Map<String, String> map);

	Product getProductById(String id) ;


	ProductMenuVo getProductMenuVo(String id) ;

	Map<String,Object>  search(ProductSearchCriteria criteria);

	ProductCheckRespBean changeProductStatus(String id, Status status) ;

	Boolean deleteProductAndWishList(String id) throws Exception;

	ProductCheckRespBean checkProduct(String id) ;

	Boolean updateLowestPrice(String id) ;

	List<String> getAllProductIds()throws Exception;

	Boolean isProductExistByCode(String productCode);

	/**
	 * 判断某个酒店是否已被添加到产品中
	 * @param hotelId
	 * @return
	 */
	Boolean isHotelUsedByProduct(String hotelId);

}
