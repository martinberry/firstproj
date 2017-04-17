package com.ztravel.product.front.service;

import java.util.List;

import com.ztravel.product.front.entity.ProductSearchCriteria;
import com.ztravel.product.front.vo.ProductVO;

/**
 * C端产品列表Service
 * @author MH
 */
public interface IProductListService {
	public List<ProductVO> searchProducts(ProductSearchCriteria criteria) throws Exception;
}
