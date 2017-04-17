package com.ztravel.product.back.pieces.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.pieces.convert.PieceAdditionalInfoConvert;
import com.ztravel.product.back.pieces.service.IPieceAdditionalInfoService;
import com.ztravel.product.back.pieces.vo.UnVisaAdditionalInfoVo;
import com.ztravel.product.back.pieces.vo.VisaAdditionalInfoVo;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Service
public class PieceAdditionalInfoServiceImpl extends PieceServiceImpl implements IPieceAdditionalInfoService {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceAdditionalInfoServiceImpl.class) ;

    @Resource
    private IUnVisaProductDao unVisaProductDaoImpl;

    @Resource
    private IVisaProductDao visaProductDaoImpl;

    @Override
    public VisaAdditionalInfoVo queryVisaAdditionalInfoVoById(String id) throws Exception {
        VisaProduct visaProduct = pieceProductDaoImpl.queryVisaProductById(id);
        if (visaProduct == null) {
            throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
        }
        VisaAdditionalInfoVo visaAdditionalInfoVo = PieceAdditionalInfoConvert.entity2VisaVo(visaProduct);
        return visaAdditionalInfoVo;
    }

    @Override
    public UnVisaAdditionalInfoVo queryUnVisaAdditionalInfoVoById(String id) throws Exception {
        UnVisaProduct unVisaProduct = pieceProductDaoImpl.queryUnVisaProductById(id);
        if (unVisaProduct == null) {
            throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
        }
        UnVisaAdditionalInfoVo unVisaAdditionalInfoVo = PieceAdditionalInfoConvert.entity2UnVisaVo(unVisaProduct);
        return unVisaAdditionalInfoVo;
    }

    @Override
    public void save(UnVisaAdditionalInfoVo unVisaAdditionalInfoVo) throws Exception {
        UnVisaProduct unVisaProduct = PieceAdditionalInfoConvert.vo2EntityForUnVisa(unVisaAdditionalInfoVo);
        if (unVisaAdditionalInfoVo.getProgress() < 5) {
            unVisaProduct.setProgress(5);
        }
        int result = unVisaProductDaoImpl.updateUnVisaInfo(unVisaProduct);
        if (result <=0) {
            throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
        } else if (result == 1) {
            LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
        } else {
            throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
        }
    }

    @Override
    public void save(VisaAdditionalInfoVo visaAdditionalInfoVo) throws Exception {
        VisaProduct visaProduct = PieceAdditionalInfoConvert.vo2EntityForVisa(visaAdditionalInfoVo);
        if (visaAdditionalInfoVo.getProgress() < 5) {
            visaProduct.setProgress(5);
        }
        int result = visaProductDaoImpl.updateVisaInfo(visaProduct);
        if (result <=0) {
            throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
        } else if (result == 1) {
            LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
        } else {
            throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
        }
    }

}
