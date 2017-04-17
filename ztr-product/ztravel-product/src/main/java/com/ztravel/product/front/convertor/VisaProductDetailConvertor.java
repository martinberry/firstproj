package com.ztravel.product.front.convertor;

import java.lang.reflect.InvocationTargetException;
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
import com.ztravel.product.front.vo.VisaProductDetailVo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.visa.VisaProduct;

public class VisaProductDetailConvertor {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaProductDetailConvertor.class);
	
	public static VisaProductDetailVo product2Vo(VisaProduct visaProduct) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		VisaProductDetailVo vo = null;
		if(null != visaProduct && visaProduct.getStatus().equals(ProductStatus.RELEASED)){
			vo = new VisaProductDetailVo();
				try {
					PropertyUtils.copyProperties(vo, visaProduct);
					if(null != visaProduct.getDetailInfo() && CollectionUtils.isNotEmpty(visaProduct.getDetailInfo().getImages())){
						vo.setFirstImage(visaProduct.getDetailInfo().getImages().get(0));
					}
					if (visaProduct.getAdditionalInfos() != null && !visaProduct.getAdditionalInfos().isEmpty()) {
						setAdditional(vo, visaProduct.getAdditionalInfos());
					}
					vo.setProId(visaProduct.getId().toString());
					vo.setNatureType(visaProduct.getBasicInfo().getType().name());
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
		}else{
			if(null == visaProduct){
				LOGGER.error("产品不存在");
				throw new RuntimeException("产品不存在");
			}else if(!visaProduct.getStatus().equals(ProductStatus.RELEASED)){
				LOGGER.error("产品:[{}]状态:[{}],非[{}]状态",visaProduct.getPid(),visaProduct.getStatus().name(),ProductStatus.RELEASED.name());
				throw new RuntimeException("产品:["+visaProduct.getPid()+"]状态:["+visaProduct.getStatus().name()+"],非["+ProductStatus.RELEASED.name()+"]状态");
			}
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
	
	private static void filterPrice(List<PriceInfo> prices,VisaProductDetailVo vo){
		if(CollectionUtils.isNotEmpty(prices)){
			Iterator<PriceInfo> iterators = prices.iterator();
			while(iterators.hasNext()){
				PriceInfo price = iterators.next();
				if(null == price.getAdultPrice() || price.getAdultPrice() <= 0){
					iterators.remove();				
				}
			}
			vo.setPriceInfos(prices);
		}
	}
}
