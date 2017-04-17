package com.ztravel.product.back.freetravel.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.freetravel.convert.AdditionalInfoConvert;
import com.ztravel.product.back.freetravel.dao.IAdditionalInfoDao;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.service.IAdditinalInfoService;
import com.ztravel.product.back.freetravel.vo.AdditionalInfoVo;

@Service
public class AdditinalInfoServiceImpl implements IAdditinalInfoService {
	@Resource
	private IAdditionalInfoDao additionalInfoDaoImpl;

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(AdditinalInfoServiceImpl.class) ;

	@Override
	public AdditionalInfoVo queryAIById(String id) throws Exception {
		Product product = additionalInfoDaoImpl.queryAIById(id);
		if(product == null){
			throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
		}
		return AdditionalInfoConvert.entity2Vo(product);
	}

	@Override
	public void save(AdditionalInfoVo addInfo) throws Exception {
		Product product = AdditionalInfoConvert.vo2Entity(addInfo);
		if(addInfo.getProgress() < 5){
			product.setProgress(5);
		}
		int result = additionalInfoDaoImpl.updateAI(product);
		if(result <=0){
			throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
		}else if(result == 1){
			LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
		}else{
			throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
		}
	}

	@Override
	public void release(String id) throws Exception {
		int result = additionalInfoDaoImpl.release(id);
		if(result <=0){
			throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
		}else if(result == 1){
			LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
		}else{
			throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
		}
	}
}
