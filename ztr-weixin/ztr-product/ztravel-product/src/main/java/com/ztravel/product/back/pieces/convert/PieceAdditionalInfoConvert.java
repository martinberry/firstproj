package com.ztravel.product.back.pieces.convert;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.product.back.pieces.vo.UnVisaAdditionalInfoVo;
import com.ztravel.product.back.pieces.vo.VisaAdditionalInfoVo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class PieceAdditionalInfoConvert {

    public static UnVisaProduct vo2EntityForUnVisa(UnVisaAdditionalInfoVo unVisaAdditionalInfoVo) {

        UnVisaProduct unVisaProduct = new UnVisaProduct();

        if (StringUtils.isNotBlank(unVisaAdditionalInfoVo.getId())) {
            unVisaProduct.setId(new ObjectId(unVisaAdditionalInfoVo.getId()));
        }

        if (StringUtils.isNotBlank(unVisaAdditionalInfoVo.getPid())) {
            unVisaProduct.setPid(unVisaAdditionalInfoVo.getPid());
        }

        if (unVisaAdditionalInfoVo.getProgress() != null) {
            unVisaProduct.setProgress(unVisaAdditionalInfoVo.getProgress());
        }

        Map<String, String> additionalInfoVos = unVisaAdditionalInfoVo.getAdditionalInfos();
        if (additionalInfoVos != null) {
            Map<AdditionalRule, String> additionalInfos = new HashMap<AdditionalRule, String>();
            for (String additionalKey : additionalInfoVos.keySet()) {
                if (AdditionalRule.valueOf(additionalKey) != null) {
                    additionalInfos.put(AdditionalRule.valueOf(additionalKey), additionalInfoVos.get(additionalKey));
                }
            }
            unVisaProduct.setAdditionalInfos(additionalInfos);
        }

        return unVisaProduct;
    }

    public static VisaProduct vo2EntityForVisa(VisaAdditionalInfoVo visaAdditionalInfoVo) {

        VisaProduct visaProduct = new VisaProduct();

        if (StringUtils.isNotBlank(visaAdditionalInfoVo.getId())) {
            visaProduct.setId(new ObjectId(visaAdditionalInfoVo.getId()));
        }

        if (StringUtils.isNotBlank(visaAdditionalInfoVo.getPid())) {
            visaProduct.setPid(visaAdditionalInfoVo.getPid());
        }

        if (visaAdditionalInfoVo.getProgress() != null) {
            visaProduct.setProgress(visaAdditionalInfoVo.getProgress());
        }

        Map<String, String> additionalInfoVos = visaAdditionalInfoVo.getAdditionalInfos();
        if (additionalInfoVos != null) {
            Map<AdditionalRule, String> additionalInfos = new HashMap<AdditionalRule, String>();
            for (String additionalKey : additionalInfoVos.keySet()) {
                if (AdditionalRule.valueOf(additionalKey) != null) {
                    additionalInfos.put(AdditionalRule.valueOf(additionalKey), additionalInfoVos.get(additionalKey));
                }
            }
            visaProduct.setAdditionalInfos(additionalInfos);
        }

        if (visaAdditionalInfoVo.getMaterias() != null) {
            visaProduct.setMaterias(visaAdditionalInfoVo.getMaterias());
        }

        if (CollectionUtils.isNotEmpty(visaAdditionalInfoVo.getProcesses())) {
            visaProduct.setProcesses(visaAdditionalInfoVo.getProcesses());
        }

        return visaProduct;
    }

    public static UnVisaAdditionalInfoVo entity2UnVisaVo(UnVisaProduct unVisaProduct) {

        UnVisaAdditionalInfoVo unVisaAdditionalInfoVo = new UnVisaAdditionalInfoVo();

        if (unVisaProduct.getId() != null) {
            unVisaAdditionalInfoVo.setId(unVisaProduct.getId().toString());
        }

        if (StringUtils.isNotBlank(unVisaProduct.getPid())) {
            unVisaAdditionalInfoVo.setPid(unVisaProduct.getPid());
        }

        if (unVisaProduct.getProgress() != null) {
            unVisaAdditionalInfoVo.setProgress(unVisaProduct.getProgress());
        }

        Map<AdditionalRule, String> additionalInfos = unVisaProduct.getAdditionalInfos();
        if (additionalInfos != null) {
            Map<String, String> additionalInfoVos = new HashMap<String, String>();
            for (AdditionalRule additionalRule : additionalInfos.keySet()) {
                additionalInfoVos.put(additionalRule.name(), additionalInfos.get(additionalRule));
            }
            unVisaAdditionalInfoVo.setAdditionalInfos(additionalInfoVos);
        }

        return unVisaAdditionalInfoVo;
    }

    public static VisaAdditionalInfoVo entity2VisaVo(VisaProduct visaProduct) {

        VisaAdditionalInfoVo visaAdditionalInfoVo = new VisaAdditionalInfoVo();

        if (visaProduct.getId() != null) {
            visaAdditionalInfoVo.setId(visaProduct.getId().toString());
        }

        if (StringUtils.isNotBlank(visaProduct.getPid())) {
            visaAdditionalInfoVo.setPid(visaProduct.getPid());
        }

        if (visaProduct.getProgress() != null) {
            visaAdditionalInfoVo.setProgress(visaProduct.getProgress());
        }

        Map<AdditionalRule, String> additionalInfos = visaProduct.getAdditionalInfos();
        if (additionalInfos != null) {
            Map<String, String> additionalInfoVos = new HashMap<String, String>();
            for (AdditionalRule additionalRule : additionalInfos.keySet()) {
                additionalInfoVos.put(additionalRule.name(), additionalInfos.get(additionalRule));
            }
            visaAdditionalInfoVo.setAdditionalInfos(additionalInfoVos);
        }

        if (visaProduct.getMaterias() != null) {
            visaAdditionalInfoVo.setMaterias(visaProduct.getMaterias());
        }

        if (CollectionUtils.isNotEmpty(visaProduct.getProcesses())) {
            visaAdditionalInfoVo.setProcesses(visaProduct.getProcesses());
        }

        return visaAdditionalInfoVo;
    }

}
