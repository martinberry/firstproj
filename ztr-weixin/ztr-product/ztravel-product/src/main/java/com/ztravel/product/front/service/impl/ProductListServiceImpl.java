package com.ztravel.product.front.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.IWishListClientService;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.dao.IProductListDao;
import com.ztravel.product.front.convertor.ProductConvertor;
import com.ztravel.product.front.entity.ProductSearchCriteria;
import com.ztravel.product.front.service.IProductListService;
import com.ztravel.product.front.vo.ProductVO;

/**
 * @author MH
 */
@Service
public class ProductListServiceImpl implements IProductListService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductListServiceImpl.class);

	@Resource
	private IProductListDao productListDao;

	@Resource
	private IWishListClientService wishListClientServiceImpl;

	@Override
	public List<ProductVO> searchProducts(ProductSearchCriteria criteria) throws Exception {

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

		Map paramMap = convertProductSearchCriteria(criteria);
		List<Product> productList = productListDao.searchProducts(paramMap);

		List<ProductVO> prodVoList = new ArrayList<ProductVO>();
		Boolean isWish = false;
		for(Product prod : productList){
			ProductVO prodVo = ProductConvertor.convertEntityToVO(prod);
			if(memberSessionBean != null && StringUtils.isNotBlank(memberSessionBean.getMemberId()) && memberSessionBean.getIsLogin() == true){
				isWish = wishListClientServiceImpl.isWishorNot(memberSessionBean.getMemberId(), prod.getId().toString());
			}
			prodVo.setIsWish(isWish);
			prodVoList.add(prodVo);
		}
		return prodVoList;
	}

	/**
	 * 产品搜索条件ProductSearchCriteria转换成Map
	 * @param ProductSearchCriteria
	 * @return Map
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map convertProductSearchCriteria(ProductSearchCriteria criteria){
		Map map = new HashMap();
		if( StringUtils.isNotBlank(criteria.getDeparture()) ){
			map.put("from", criteria.getDeparture());
		}
		if( StringUtils.isNotBlank(criteria.getDestination()) && !criteria.getDestination().equals(Const.DESTINATION_PLACEHOLDER) ){
			switch(criteria.getDestLevel()){
			case 1:
				map.put("toContinent", criteria.getDestination());
				break;
			case 2:
				map.put("toCountry", criteria.getDestination());
				break;
			}
		}
		return map;
	}

}
