package com.ztravel.product.back.pieces.convert;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.product.back.pieces.vo.UnVisaDetailInfoVo;
import com.ztravel.product.back.pieces.vo.VisaDetailInfoVo;
import com.ztravel.product.po.pieces.unvisa.UnVisaDetailInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaDetailInfo;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class PieceDetailInfoConvert {

    public static UnVisaProduct vo2EntityForUnVisa(UnVisaDetailInfoVo unVisaDetailInfoVo) {

        UnVisaProduct unVisaProduct = new UnVisaProduct();

        if (StringUtils.isNotBlank(unVisaDetailInfoVo.getId())) {
            unVisaProduct.setId(new ObjectId(unVisaDetailInfoVo.getId()));
        }

        if (StringUtils.isNotBlank(unVisaDetailInfoVo.getPid())) {
            unVisaProduct.setPid(unVisaDetailInfoVo.getPid());
        }

        if (unVisaDetailInfoVo.getProgress() != null) {
            unVisaProduct.setProgress(unVisaDetailInfoVo.getProgress());
        }

        UnVisaDetailInfo unVisaDetailInfo = new UnVisaDetailInfo();
        if (StringUtils.isNotBlank(unVisaDetailInfoVo.getLanguageType())) {
            unVisaDetailInfo.setLanguageType(unVisaDetailInfoVo.getLanguageType());
        }
        if (StringUtils.isNotBlank(unVisaDetailInfoVo.getServiceTime())) {
            unVisaDetailInfo.setServiceTime(unVisaDetailInfoVo.getServiceTime());
        }

        if (unVisaDetailInfoVo.getImages() != null) {
            unVisaDetailInfo.setImages(unVisaDetailInfoVo.getImages());
        }
        unVisaProduct.setDetailInfo(unVisaDetailInfo);

        return unVisaProduct;
    }

    public static VisaProduct vo2EntityForVisa(VisaDetailInfoVo visaDetailInfoVo) {

        VisaProduct visaProduct = new VisaProduct();

        if (StringUtils.isNotBlank(visaDetailInfoVo.getId())) {
            visaProduct.setId(new ObjectId(visaDetailInfoVo.getId()));
        }

        if (StringUtils.isNotBlank(visaDetailInfoVo.getPid())) {
            visaProduct.setPid(visaDetailInfoVo.getPid());
        }

        if (visaDetailInfoVo.getProgress() != null) {
            visaProduct.setProgress(visaDetailInfoVo.getProgress());
        }

        VisaDetailInfo visaDetailInfo = new VisaDetailInfo();
        if (visaDetailInfoVo.getIsInterview() != null) {
            visaDetailInfo.setIsInterview(visaDetailInfoVo.getIsInterview());
        }
        if (StringUtils.isNotBlank(visaDetailInfoVo.getValidate())) {
            visaDetailInfo.setValidate(visaDetailInfoVo.getValidate());
        }
        if (StringUtils.isNotBlank(visaDetailInfoVo.getStayTime())) {
            visaDetailInfo.setStayTime(visaDetailInfoVo.getStayTime());
        }
        if (StringUtils.isNotBlank(visaDetailInfoVo.getScope())) {
            visaDetailInfo.setScope(visaDetailInfoVo.getScope());
        }
        if (StringUtils.isNotBlank(visaDetailInfoVo.getTimes())) {
            visaDetailInfo.setTimes(visaDetailInfoVo.getTimes());
        }
        if (visaDetailInfoVo.getImages() != null) {
            visaDetailInfo.setImages(visaDetailInfoVo.getImages());
        }
        visaProduct.setDetailInfo(visaDetailInfo);

        return visaProduct;
    }


    public static UnVisaDetailInfoVo entity2UnVisaVo(UnVisaProduct unVisaProduct) {

        UnVisaDetailInfoVo unVisaDetailInfoVo = new UnVisaDetailInfoVo();

        if (unVisaProduct.getId() != null) {
            unVisaDetailInfoVo.setId(unVisaProduct.getId().toString());
        }

        if (StringUtils.isNotBlank(unVisaProduct.getPid())) {
            unVisaDetailInfoVo.setPid(unVisaProduct.getPid());
        }

        if (unVisaProduct.getProgress() != null) {
            unVisaDetailInfoVo.setProgress(unVisaProduct.getProgress());
        }

        if (unVisaProduct.getDetailInfo() != null) {

            UnVisaDetailInfo unVisaDetailInfo = unVisaProduct.getDetailInfo();
            if (StringUtils.isNotBlank(unVisaDetailInfo.getLanguageType())) {
                unVisaDetailInfoVo.setLanguageType(unVisaDetailInfo.getLanguageType());
            }
            if (unVisaDetailInfo.getServiceTime() != null) {
                unVisaDetailInfoVo.setServiceTime(unVisaDetailInfo.getServiceTime());
            }

            if (unVisaDetailInfo.getImages() != null) {
                unVisaDetailInfoVo.setImages(unVisaDetailInfo.getImages());
            }
        }

        return unVisaDetailInfoVo;
    }

    public static VisaDetailInfoVo entity2VisaVo(VisaProduct visaProduct) {

        VisaDetailInfoVo visaDetailInfoVo = new VisaDetailInfoVo();

        if (visaProduct.getId() != null) {
            visaDetailInfoVo.setId(visaProduct.getId().toString());
        }

        if (StringUtils.isNotBlank(visaProduct.getPid())) {
            visaDetailInfoVo.setPid(visaProduct.getPid());
        }

        if (visaProduct.getProgress() != null) {
            visaDetailInfoVo.setProgress(visaProduct.getProgress());
        }

        if (visaProduct.getDetailInfo() != null) {

            VisaDetailInfo visaDetailInfo = visaProduct.getDetailInfo();

            if (visaDetailInfo.getIsInterview() != null) {
                visaDetailInfoVo.setIsInterview(visaDetailInfo.getIsInterview());
            }

            if (StringUtils.isNotBlank(visaDetailInfo.getValidate())) {
                visaDetailInfoVo.setValidate(visaDetailInfo.getValidate());
            }

            if (StringUtils.isNotBlank(visaDetailInfo.getStayTime())) {
                visaDetailInfoVo.setStayTime(visaDetailInfo.getStayTime());
            }

            if (StringUtils.isNotBlank(visaDetailInfo.getScope())) {
                visaDetailInfoVo.setScope(visaDetailInfo.getScope());
            }

            if (StringUtils.isNotBlank(visaDetailInfo.getTimes())) {
                visaDetailInfoVo.setTimes(visaDetailInfo.getTimes());
            }

            if (visaDetailInfo.getImages() != null) {
                visaDetailInfoVo.setImages(visaDetailInfo.getImages());
            }
        }

        return visaDetailInfoVo;
    }

}
