package com.ztravel.product.back.pieces.convert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.LanguageType;
import com.ztravel.product.front.vo.VisaProductDetailVo;
import com.ztravel.product.front.wo.ProductWo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class PiecePrevDetailConvert {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PiecePrevDetailConvert.class);

    public static VisaProductDetailVo product2VisaVo(VisaProduct visaProduct) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        VisaProductDetailVo vo = null;
        if (null != visaProduct) {
            vo = new VisaProductDetailVo();
                try {
                    PropertyUtils.copyProperties(vo, visaProduct);
                    if (null != visaProduct.getDetailInfo() && CollectionUtils.isNotEmpty(visaProduct.getDetailInfo().getImages())) {
                        vo.setFirstImage(visaProduct.getDetailInfo().getImages().get(0));
                    }
                    if (visaProduct.getAdditionalInfos() != null && !visaProduct.getAdditionalInfos().isEmpty()) {
                        setAdditional(vo, visaProduct.getAdditionalInfos());
                    }
                    vo.setProId(visaProduct.getId().toString());
                    vo.setNatureType(visaProduct.getNature().getName());
                } catch (IllegalAccessException e) {
                    LOGGER.error("签证产品详情转换错误:[]",e);
                    throw e;
                } catch (InvocationTargetException e) {
                    LOGGER.error("签证产品详情转换错误:[]",e);
                    throw e;
                } catch (NoSuchMethodException e) {
                    LOGGER.error("签证产品详情转换错误:[]",e);
                    throw e;
                }
        } else {
            LOGGER.error("产品不存在");
            throw new RuntimeException("产品不存在");
        }
        return vo;
    }

    private static void setAdditional(VisaProductDetailVo visaVo, Map<AdditionalRule, String> additionalInfos) {
        Map<String, String> infos = new HashMap<>();
        if (additionalInfos != null && additionalInfos.keySet() != null) {
            Iterator<AdditionalRule> it = additionalInfos.keySet().iterator();
            while (it.hasNext()) {
                AdditionalRule rule = it.next();
                infos.put(rule.toString(), additionalInfos.get(rule));
            }
        }
        visaVo.setAdditional(infos);
    }

    public static ProductWo product2UnVisaVo(UnVisaProduct product) {
        ProductWo productWo = new ProductWo();
        if (product.getId() != null) {
            productWo.setId(product.getId().toString());
        }
        productWo.setPid(product.getPid());
        productWo.setpName(product.getBasicInfo().getPname());
        productWo.setTo(product.getBasicInfo().getToCity());
        productWo.setToCountry(product.getBasicInfo().getToCountry());
        productWo.setImages(product.getDetailInfo().getImages());
        setCostPriceForPieceProduct(productWo, product.getPriceInfos());
        productWo.setLanguage(LanguageType.valueOf(product.getDetailInfo().getLanguageType()).getDescription());
        productWo.setServiceTime(product.getDetailInfo().getServiceTime());
        productWo.setPieceType(product.getBasicInfo().getType().getDesc());
        setPrice(productWo, product.getPriceInfos());
        productWo.setProductNature(product.getNature().getName());
        if (product.getAdditionalInfos() != null && !product.getAdditionalInfos().isEmpty()) {
            // 转换附加信息, 包含费用说明和预订须知
            setAdditionalInfosForProduct(productWo, product.getAdditionalInfos());
        }
        return productWo;
    }

    private static void setCostPriceForPieceProduct(ProductWo productWo, List<PriceInfo> priceInfo) {
        List<String> costPriceId = new ArrayList<String>();
        List<String> costPrice = new ArrayList<String>();
        for (PriceInfo tmp : priceInfo) {
            if (tmp != null) {
                costPrice.add(tmp.getName());
                costPriceId.add(tmp.getId());
            }
        }
        productWo.setCostPrice(costPrice);
        productWo.setCostPriceId(costPriceId);
    }

    private static void setPrice(ProductWo productWo, List<PriceInfo> priceInfo) {
        List<Double> adultPrice = new ArrayList<Double>();
        List<Double> childPrice = new ArrayList<Double>();
        Double lowestPrice = null;
        for (PriceInfo tmp : priceInfo) {
            if (tmp != null) {
                if (tmp.getAdultPrice() != null && tmp.getAdultPrice() > 0) {
                    adultPrice.add(tmp.getAdultPrice());
                    childPrice.add(tmp.getChildPrice());
                    if (lowestPrice == null || lowestPrice > tmp.getAdultPrice()) {
                        lowestPrice = tmp.getAdultPrice();
                    }
                } else {
                    int exludeindex = adultPrice.size();
                    productWo.getCostPrice().remove(exludeindex);
                    productWo.getCostPriceId().remove(exludeindex);
                }
            }
        }
        productWo.setAdultPrice(adultPrice);
        productWo.setChildPrice(childPrice);
        productWo.setLowestPrice(lowestPrice);
    }

    private static void setAdditionalInfosForProduct(ProductWo productWo, Map<AdditionalRule, String> additionalInfos) {
        Map<String, String> infos = new HashMap<>();
        if (additionalInfos != null && additionalInfos.keySet() != null) {
            Iterator<AdditionalRule> it = additionalInfos.keySet().iterator();
            while (it.hasNext()) {
                AdditionalRule rule = it.next();
                infos.put(rule.toString(), additionalInfos.get(rule));
            }
        }
        productWo.setAdditionalInfos(infos);
    }

}
