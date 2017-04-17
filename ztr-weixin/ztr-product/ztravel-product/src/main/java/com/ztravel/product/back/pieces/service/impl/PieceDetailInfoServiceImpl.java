package com.ztravel.product.back.pieces.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.pieces.convert.PieceDetailInfoConvert;
import com.ztravel.product.back.pieces.service.IPieceDetailInfoService;
import com.ztravel.product.back.pieces.vo.UnVisaDetailInfoVo;
import com.ztravel.product.back.pieces.vo.VisaDetailInfoVo;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Service
public class PieceDetailInfoServiceImpl extends PieceServiceImpl implements IPieceDetailInfoService {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceDetailInfoServiceImpl.class) ;

    @Resource
    private IUnVisaProductDao unVisaProductDaoImpl;

    @Resource
    private IVisaProductDao visaProductDaoImpl;

    @Override
    public VisaDetailInfoVo queryVisaDetailInfoVoById(String id) throws Exception {
        VisaProduct visaProduct = pieceProductDaoImpl.queryVisaProductById(id);
        if (visaProduct == null) {
            throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
        }
        VisaDetailInfoVo visaDetailInfoVo = PieceDetailInfoConvert.entity2VisaVo(visaProduct);
        return visaDetailInfoVo;
    }

    @Override
    public UnVisaDetailInfoVo queryUnVisaDetailInfoVoById(String id) throws Exception {
        UnVisaProduct unVisaProduct = pieceProductDaoImpl.queryUnVisaProductById(id);
        if (unVisaProduct == null) {
            throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
        }
        UnVisaDetailInfoVo unVisaDetailInfoVo = PieceDetailInfoConvert.entity2UnVisaVo(unVisaProduct);
        return unVisaDetailInfoVo;
    }

    @Override
    public void save(UnVisaDetailInfoVo unVisaDetailInfoVo) throws Exception {

        UnVisaProduct unVisaProduct = PieceDetailInfoConvert.vo2EntityForUnVisa(unVisaDetailInfoVo);
        if (unVisaDetailInfoVo.getWithNext() && unVisaDetailInfoVo.getProgress() < 2) {
            unVisaProduct.setProgress(2);
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
    public void save(VisaDetailInfoVo visaDetailInfoVo) throws Exception {
        VisaProduct visaProduct = PieceDetailInfoConvert.vo2EntityForVisa(visaDetailInfoVo);
        if (visaDetailInfoVo.getWithNext() && visaDetailInfoVo.getProgress() < 2) {
            visaProduct.setProgress(2);
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
