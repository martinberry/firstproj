package com.ztravel.product.back.pieces.convert;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.common.enums.DestinationType;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.PieceType;
import com.ztravel.product.back.pieces.vo.PieceBasicInfoVo;
import com.ztravel.product.po.pieces.common.BasicInfo;
import com.ztravel.product.po.pieces.common.PieceProduct;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class PieceBasicInfoConvert {

    public static PieceProduct vo2Entity(PieceBasicInfoVo pieceBasicInfoVo) {

        PieceProduct pieceProduct = null;

        if (pieceBasicInfoVo.getType() != null && PieceType.VISA.name().equals(pieceBasicInfoVo.getType())) {
            pieceProduct = new VisaProduct();
            pieceProduct.setNature(Nature.VISA);
        } else {
            pieceProduct = new UnVisaProduct();
            pieceProduct.setNature(Nature.UNVISA);
        }

        if (StringUtils.isNotBlank(pieceBasicInfoVo.getId())) {
            pieceProduct.setId(new ObjectId(pieceBasicInfoVo.getId()));
        }

        if (StringUtils.isNotBlank(pieceBasicInfoVo.getPid())) {
            pieceProduct.setPid(pieceBasicInfoVo.getPid());
        }

        if (pieceBasicInfoVo.getProgress() != null) {
            pieceProduct.setProgress(pieceBasicInfoVo.getProgress());
        }

        BasicInfo basicInfo = new BasicInfo();
        if (StringUtils.isNotBlank(pieceBasicInfoVo.getPname())) {
            basicInfo.setPname(pieceBasicInfoVo.getPname());
        }

        if (pieceBasicInfoVo.getToCity() != null) {
            basicInfo.setToCity(pieceBasicInfoVo.getToCity());
        }

        if (pieceBasicInfoVo.getToCountry() != null) {
            basicInfo.setToCountry(pieceBasicInfoVo.getToCountry());
        }

        if (pieceBasicInfoVo.getToContinent() != null) {
            basicInfo.setToContinent(pieceBasicInfoVo.getToContinent());
        }

        if (pieceBasicInfoVo.getType() != null) {
            basicInfo.setType(PieceType.valueOf(pieceBasicInfoVo.getType()));

            if (PieceType.VISA.name().equals(pieceBasicInfoVo.getType())) {
                pieceProduct.setDestinationType(DestinationType.INTERNATIONAL);
            } else {
                pieceProduct.setDestinationType(DestinationType.DOMESTIC);
            }
        }
        pieceProduct.setBasicInfo(basicInfo);
        return pieceProduct;
    }

    public static PieceBasicInfoVo entity2Vo(PieceProduct pieceProduct) {

        PieceBasicInfoVo pieceBasicInfoVo = new PieceBasicInfoVo();

        if (pieceProduct.getId() != null) {
            pieceBasicInfoVo.setId(pieceProduct.getId().toString());
        }

        if (StringUtils.isNotBlank(pieceProduct.getPid())) {
            pieceBasicInfoVo.setPid(pieceProduct.getPid());
        }

        if (pieceProduct.getNature() != null) {
            pieceBasicInfoVo.setNature(pieceProduct.getNature().name());
        }

        if (pieceProduct.getProgress() != null) {
            pieceBasicInfoVo.setProgress(pieceProduct.getProgress());
        }

        if (pieceProduct.getBasicInfo() != null) {

            BasicInfo basicInfo = pieceProduct.getBasicInfo();

            if (StringUtils.isNotBlank(basicInfo.getPname())) {
                pieceBasicInfoVo.setPname(basicInfo.getPname());
            }

            if (basicInfo.getToCity() != null) {
                pieceBasicInfoVo.setToCity(basicInfo.getToCity());
            }

            if (basicInfo.getToCountry() != null) {
                pieceBasicInfoVo.setToCountry(basicInfo.getToCountry());
            }

            if (basicInfo.getToContinent() != null) {
                pieceBasicInfoVo.setToContinent(basicInfo.getToContinent());
            }

            if (basicInfo.getType() != null) {
                pieceBasicInfoVo.setType(basicInfo.getType().name());
            }
        }

        return pieceBasicInfoVo;
    }

}
