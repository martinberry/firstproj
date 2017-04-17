package com.ztravel.product.weixin.convertor;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.product.utils.PieceProductUtils;
import com.ztravel.product.weixin.vo.LocalDetailVo;
import com.ztravel.product.weixin.vo.PieceProductDetailVo;
import com.ztravel.product.weixin.vo.VisaProductDetailVo;

public class WxPieceProductDetailConvertor {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WxPieceProductDetailConvertor.class);

	public static VisaProductDetailVo product2Vo(VisaProduct visaProduct) throws Exception {
		VisaProductDetailVo vo = null;
		DecimalFormat df =new DecimalFormat("#"); 
		if (null != visaProduct && visaProduct.getStatus().equals(ProductStatus.RELEASED)) {
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
				vo.setNatureType(visaProduct.getBasicInfo().getType().name());
				if(CollectionUtils.isNotEmpty(visaProduct.getPriceInfos())){
					vo.setLowest(df.format(PieceProductUtils.getLowestPrice(visaProduct.getPid(), visaProduct.getPriceInfos())));
				}
				filterPrice(vo.getPriceInfos(),vo);
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
			if (null == visaProduct) {
				LOGGER.error("产品不存在");
				throw new RuntimeException("产品不存在");
			} else if (!visaProduct.getStatus().equals(ProductStatus.RELEASED)) {
				LOGGER.error("产品:[{}]状态:[{}],非[{}]状态", visaProduct.getPid(), visaProduct.getStatus().name(), ProductStatus.RELEASED.name());
				throw new RuntimeException("产品:[" + visaProduct.getPid() + "]状态:[" + visaProduct.getStatus().name() + "],非[" + ProductStatus.RELEASED.name() + "]状态");
			}
		}
		return vo;
	}

    public static LocalDetailVo product2Vo(UnVisaProduct unVisaProduct) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	LocalDetailVo vo = null;
        if (null != unVisaProduct && unVisaProduct.getStatus().equals(ProductStatus.RELEASED)) {
            vo = new LocalDetailVo();
            try {
                PropertyUtils.copyProperties(vo, unVisaProduct);
                if (null != unVisaProduct.getDetailInfo() && CollectionUtils.isNotEmpty(unVisaProduct.getDetailInfo().getImages())) {
                    vo.setFirstImage(unVisaProduct.getDetailInfo().getImages().get(0));
                }
                if (unVisaProduct.getAdditionalInfos() != null && !unVisaProduct.getAdditionalInfos().isEmpty()) {
                    setAdditional(vo, unVisaProduct.getAdditionalInfos());
                }
                vo.setProId(unVisaProduct.getId().toString());
                vo.setNatureType(unVisaProduct.getBasicInfo().getType().name());
                filterPrice(vo.getPriceInfos(),vo);
            } catch (IllegalAccessException e) {
                LOGGER.error("非签证产品详情转换错误:[]",e);
                throw e;
            } catch (InvocationTargetException e) {
                LOGGER.error("非签证产品详情转换错误:[]",e);
                throw e;
            } catch (NoSuchMethodException e) {
                LOGGER.error("非签证产品详情转换错误:[]",e);
                throw e;
            }
        } else {
            if (null == unVisaProduct) {
                LOGGER.error("产品不存在");
                throw new RuntimeException("产品不存在");
            } else if (!unVisaProduct.getStatus().equals(ProductStatus.RELEASED)) {
                LOGGER.error("产品:[{}]状态:[{}],非[{}]状态", unVisaProduct.getPid(), unVisaProduct.getStatus().name(), ProductStatus.RELEASED.name());
                throw new RuntimeException("产品:[" + unVisaProduct.getPid() + "]状态:[" + unVisaProduct.getStatus().name() + "],非[" + ProductStatus.RELEASED.name() + "]状态");
            }
        }
        return vo;
    }

	private static void setAdditional(PieceProductDetailVo visaVo, Map<AdditionalRule, String> additionalInfos) {
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

	private static void filterPrice(List<PriceInfo> prices, PieceProductDetailVo vo) {
		if (CollectionUtils.isNotEmpty(prices)) {
			Iterator<PriceInfo> iterators = prices.iterator();
			while (iterators.hasNext()) {
				PriceInfo price = iterators.next();
				if (null == price.getAdultPrice() || price.getAdultPrice() <= 0) {
					iterators.remove();
				}
			}
			vo.setPriceInfos(prices);
		}
	}
}
