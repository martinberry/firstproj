package com.ztravel.product.weixin.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.constants.Const;
import com.ztravel.member.client.service.IWishListClientService;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.dao.IProductListDao;
import com.ztravel.product.weixin.convertor.ProductConvertor;
import com.ztravel.product.weixin.entity.ProductSearchCriteria;
import com.ztravel.product.weixin.service.IProductListService;
import com.ztravel.product.weixin.vo.ProductVO;
import com.ztravel.weixin.client.service.IWxTopicClientService;
import com.ztravel.weixin.po.WeixinTopic;

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
	
	@Resource
	private IWxTopicClientService wxTopicClientService;
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

	@Override
	public List<ProductVO> searchProducts(ProductSearchCriteria criteria)throws Exception {
		Map paramMap = convertProductSearchCriteria(criteria);
		LOGGER.info("查询微信产品列表开始，参数[{}]",TZBeanUtils.describe(paramMap));
		List<Product> productList = productListDao.searchProducts(paramMap);
		LOGGER.info("查询微信产品列表完成");
		List<ProductVO> prodVoList = new ArrayList<ProductVO>();
		for(Product prod : productList){
			List<WeixinTopic> topics = wxTopicClientService.getByProductId(prod.getPid());
			ProductVO prodVo = ProductConvertor.convertEntityToVO(prod);
			prodVo.setTopics(topics);
			prodVoList.add(prodVo);
		}
		return prodVoList;
	}

}
