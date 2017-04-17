package com.ztravel.product.back.freetravel.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.freetravel.convert.RecommendTripConvert;
import com.ztravel.product.back.freetravel.dao.IRecommendTripDao;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.service.IRecommendTripService;
import com.ztravel.product.back.freetravel.vo.RecommendTripVo;

@Service
public class RecommendTripServiceImpl implements IRecommendTripService {
	@Resource
	private IRecommendTripDao recommendTripDaoImpl;

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(RecommendTripServiceImpl.class) ;

	@Override
	public RecommendTripVo queryByid(String id) throws Exception {
		Product product = recommendTripDaoImpl.queryRTById(id);
		if(product == null){
			LOGGER.error(ProductCons.PROD_QUERY_ERROR_MSG + id);
			throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
		}
		return RecommendTripConvert.entiry2Vo(product);
	}

	@Override
	public void save(RecommendTripVo vo) throws Exception {
		Product product = RecommendTripConvert.vo2Entity(vo);
		if(vo.getWithNext() && vo.getProgress() <=3){
			product.setProgress(4);
		}
		int result = recommendTripDaoImpl.updateRT(product);
		if(result <=0){
			throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
		}else if(result == 1){
			LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
		}else{
			throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
		}
	}

}
