package com.ztravel.product.weixin.service;

import java.util.List;

import com.ztravel.product.weixin.entity.ProductSearchCriteria;
import com.ztravel.product.weixin.vo.ProductVO;

/**
 * C端产品列表Service
 * @author MH
 */
public interface IProductListService {
	public List<ProductVO> searchProducts(ProductSearchCriteria criteria) throws Exception;
}
