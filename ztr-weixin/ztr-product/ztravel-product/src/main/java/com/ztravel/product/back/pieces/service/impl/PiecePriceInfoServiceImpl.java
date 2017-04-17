package com.ztravel.product.back.pieces.service.impl;

import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.pieces.convert.PiecePriceInfoConvert;
import com.ztravel.product.back.pieces.service.IPiecePriceInfoService;
import com.ztravel.product.back.pieces.vo.PiecePriceInfoVo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.utils.MongoSequenceUtil;

@Service
public class PiecePriceInfoServiceImpl extends PieceServiceImpl implements IPiecePriceInfoService {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PiecePriceInfoServiceImpl.class) ;

    @Override
    public PiecePriceInfoVo queryPiecePriceInfoVoById(String id, String nature) throws Exception {
        PieceProduct pieceProduct = pieceProductDaoImpl.queryPieceProductById(id, nature);
        if (pieceProduct == null) {
            throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
        }
        PiecePriceInfoVo basicInfo = PiecePriceInfoConvert.entity2Vo(pieceProduct);
        return basicInfo;
    }

    @Override
    public void save(PiecePriceInfoVo piecePriceInfoVo) throws Exception {

        PieceProduct pieceProduct = buildPieceProduct(piecePriceInfoVo);

        int result = pieceProductDaoImpl.updatePiecePublicInfo(pieceProduct);
        if (result <=0) {
            throw ZtrBizException.instance(ProductCons.PROD_UPDATE_FAIL_CODE, ProductCons.PROD_UPDATE_FAIL_MSG);
        } else if (result == 1) {
            LOGGER.info(ProductCons.PROD_UPDATE_SUCCESS_MSG);
        } else {
            throw ZtrBizException.instance(ProductCons.PROD_UPDATE_MULT_ERROR_CODE, ProductCons.PROD_UPDATE_MULT_ERROR_MSG);
        }
    }

    /**
     * 价格信息修改时，部分数据会获取原有数据，故作此转换
     * @param pieceProduct
     * @param piecePriceInfoVo
     */
    private PieceProduct buildPieceProduct(PiecePriceInfoVo piecePriceInfoVo) {

        PieceProduct pieceProduct = PiecePriceInfoConvert.vo2Entity(piecePriceInfoVo);
        Map<String, PriceInfo> priceInfoMap = PiecePriceInfoConvert.convertPriceInfoListToMap(pieceProduct.getPriceInfos());
        if (piecePriceInfoVo.getWithNext() && "cost".equals(piecePriceInfoVo.getPriceType()) && piecePriceInfoVo.getProgress() < 3) {
            pieceProduct.setProgress(3);
        } else if (piecePriceInfoVo.getWithNext() && "sale".equals(piecePriceInfoVo.getPriceType()) && piecePriceInfoVo.getProgress() < 4) {
            pieceProduct.setProgress(4);
        }

        Map<String, PriceInfo> oldPriceInfoMap = null;
        PieceProduct oldPieceProduct = pieceProductDaoImpl.queryPieceProductById(piecePriceInfoVo.getId(), piecePriceInfoVo.getNature());
        if (oldPieceProduct != null && CollectionUtils.isNotEmpty(oldPieceProduct.getPriceInfos())) {
            oldPriceInfoMap = PiecePriceInfoConvert.convertPriceInfoListToMap(oldPieceProduct.getPriceInfos());
        }

        if (pieceProduct.getPriceInfos() != null) {
            if ("cost".equals(piecePriceInfoVo.getPriceType())) {
                for (PriceInfo priceInfo : pieceProduct.getPriceInfos()) {
                    if (StringUtils.isEmpty(priceInfo.getId())) {
                        priceInfo.setId(MongoSequenceUtil.generateLongId(PriceInfo.class).toString());
                    } else if (oldPriceInfoMap != null && oldPriceInfoMap.get(priceInfo.getId()) != null) {
                        PriceInfo oldPriceInfo = oldPriceInfoMap.get(priceInfo.getId());
                        priceInfo.setAdultPrice(oldPriceInfo.getAdultPrice());
                        priceInfo.setChildPrice(oldPriceInfo.getChildPrice());
                    }
                }
            } else if ("sale".equals(piecePriceInfoVo.getPriceType())) {
                if (oldPieceProduct != null && CollectionUtils.isNotEmpty(oldPieceProduct.getPriceInfos())) {
                    for (PriceInfo oldPriceInfo : oldPieceProduct.getPriceInfos()) {
                        if (priceInfoMap != null && priceInfoMap.get(oldPriceInfo.getId()) != null) {
                            PriceInfo priceInfo = priceInfoMap.get(oldPriceInfo.getId());
                            oldPriceInfo.setAdultPrice(priceInfo.getAdultPrice());
                            oldPriceInfo.setChildPrice(priceInfo.getChildPrice());
                        }
                    }
                    oldPieceProduct.setProgress(pieceProduct.getProgress());
                    pieceProduct = oldPieceProduct;
                }
            }
        }

        return pieceProduct;
    }

}
