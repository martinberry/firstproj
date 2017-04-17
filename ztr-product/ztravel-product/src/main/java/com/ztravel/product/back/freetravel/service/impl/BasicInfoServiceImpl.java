package com.ztravel.product.back.freetravel.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.freetravel.convert.BasicInfoConvert;
import com.ztravel.product.back.freetravel.dao.IBasicInfoDao;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.service.IBasicInfoService;
import com.ztravel.product.back.freetravel.vo.BasicInfoVo;
import com.ztravel.product.utils.MongoSequenceUtil;
@Service
public class BasicInfoServiceImpl implements IBasicInfoService {

	@Resource
	private IBasicInfoDao basicInfoDaoImpl;

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(BasicInfoServiceImpl.class) ;

	@Override
	public BasicInfoVo queryById(String id) throws Exception {
		Product product = basicInfoDaoImpl.queryBasicInfoById(id);
		if(product == null){
			throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
		}
		BasicInfoVo basicInfo = BasicInfoConvert.entity2Vo(product);
		return basicInfo;
	}

	@Override
	public String save(BasicInfoVo basicInfo) throws Exception {
		String result = basicInfo.getId();
		Product product = BasicInfoConvert.vo2Entity(basicInfo);
		if(basicInfo.getWithNext() && (product.getProgress() == null || product.getProgress() == 0)){
			product.setProgress(1);
		}
		if(StringUtils.isNotBlank(basicInfo.getId())){
			int count = basicInfoDaoImpl.updateBasicInfo(product);
			if(count <= 0){
				throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
			}else if(count == 1){
				LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
			}else{
				throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
			}
			LOGGER.info("更新产品["+result+"]基本信息成功");
		}else{
			product.setPid(MongoSequenceUtil.generateProductEntityPid());
			product.setStatus(Status.NEW);
			result  = basicInfoDaoImpl.insertProduct(product);
			LOGGER.info("成功新建一条产品["+result+"]");
		}
		return result;
	}
}
