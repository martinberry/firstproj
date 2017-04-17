package com.ztravel.product.back.pieces.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.pieces.convert.PieceBasicInfoConvert;
import com.ztravel.product.back.pieces.service.IPieceBasicInfoService;
import com.ztravel.product.back.pieces.vo.PieceBasicInfoVo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.utils.MongoSequenceUtil;

@Service
public class PieceBasicInfoServiceImpl extends PieceServiceImpl implements IPieceBasicInfoService {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceBasicInfoServiceImpl.class) ;

    @Override
    public String save(PieceBasicInfoVo pieceBasicInfoVo) throws Exception {

        String result = pieceBasicInfoVo.getId();
        PieceProduct pieceProduct = PieceBasicInfoConvert.vo2Entity(pieceBasicInfoVo);
        if (pieceBasicInfoVo.getWithNext() && (pieceBasicInfoVo.getProgress() == null || pieceBasicInfoVo.getProgress() == 0)) {
            pieceProduct.setProgress(1);
        }
        if (StringUtils.isNotBlank(pieceBasicInfoVo.getId())) {

            int count = pieceProductDaoImpl.updatePiecePublicInfo(pieceProduct);
            if (count <= 0) {
                throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
            } else if (count == 1) {
                LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
            } else {
                throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
            }
            LOGGER.info("更新碎片产品["+result+"]基本信息成功");
        } else {
            pieceProduct.setPid(MongoSequenceUtil.generatePieceProductEntityPid());
            pieceProduct.setStatus(ProductStatus.NEW);
            result  = pieceProductDaoImpl.insertPieceBasicInfo(pieceProduct);
            LOGGER.info("成功新建一条碎片产品[" + result + "]");
        }
        return result;
    }

    @Override
    public PieceBasicInfoVo queryPieceBasicInfoVoById(String id, String nature) throws Exception {

        PieceProduct pieceProduct = pieceProductDaoImpl.queryPieceProductById(id, nature);
        if (pieceProduct == null) {
            throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
        }
        PieceBasicInfoVo basicInfo = PieceBasicInfoConvert.entity2Vo(pieceProduct);
        return basicInfo;
    }

}
