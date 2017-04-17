package com.ztravel.product.back.pieces.convert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.common.enums.Nature;
import com.ztravel.product.back.pieces.vo.PiecePriceInfoVo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class PiecePriceInfoConvert {

    public static PieceProduct vo2Entity(PiecePriceInfoVo piecePriceInfoVo) {

        PieceProduct pieceProduct = null;

        if (piecePriceInfoVo.getNature() != null && Nature.VISA.name().equals(piecePriceInfoVo.getNature())) {
            pieceProduct = new VisaProduct();
        } else {
            pieceProduct = new UnVisaProduct();
        }

        if (StringUtils.isNotBlank(piecePriceInfoVo.getId())) {
            pieceProduct.setId(new ObjectId(piecePriceInfoVo.getId()));
        }

        if (StringUtils.isNotBlank(piecePriceInfoVo.getPid())) {
            pieceProduct.setPid(piecePriceInfoVo.getPid());
        }

        if (StringUtils.isNotBlank(piecePriceInfoVo.getNature())) {
            pieceProduct.setNature(Nature.valueOf(piecePriceInfoVo.getNature()));
        }

        if (piecePriceInfoVo.getProgress() != null) {
            pieceProduct.setProgress(piecePriceInfoVo.getProgress());
        }

        if (CollectionUtils.isNotEmpty(piecePriceInfoVo.getPriceInfos())) {
            pieceProduct.setPriceInfos(piecePriceInfoVo.getPriceInfos());
        }

        return pieceProduct;
    }

    public static PiecePriceInfoVo entity2Vo(PieceProduct pieceProduct) {

        PiecePriceInfoVo piecePriceInfoVo = new PiecePriceInfoVo();

        if (pieceProduct.getId() != null) {
            piecePriceInfoVo.setId(pieceProduct.getId().toString());
        }

        if (StringUtils.isNotBlank(pieceProduct.getPid())) {
            piecePriceInfoVo.setPid(pieceProduct.getPid());
        }

        if (pieceProduct.getNature() != null) {
            piecePriceInfoVo.setNature(pieceProduct.getNature().name());
        }

        if (pieceProduct.getProgress() != null) {
            piecePriceInfoVo.setProgress(pieceProduct.getProgress());
        }

        if (pieceProduct.getBasicInfo() != null) {
            piecePriceInfoVo.setPriceInfos(pieceProduct.getPriceInfos());
        }

        return piecePriceInfoVo;
    }



    public static Map<String, PriceInfo> convertPriceInfoListToMap(List<PriceInfo> priceInfos) {

        if (priceInfos == null) return null;

        Map<String, PriceInfo> priceInfoMap = new HashMap<String, PriceInfo>();
        for (PriceInfo priceInfo : priceInfos) {
            if (StringUtils.isNotBlank(priceInfo.getId())) {
                priceInfoMap.put(priceInfo.getId(), priceInfo);
            }
        }
        return priceInfoMap;
    }

}
