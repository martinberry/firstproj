package com.ztravel.product.weixin.convertor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.ztravel.common.constants.Const;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.product.utils.PieceProductUtils;
import com.ztravel.product.weixin.entity.ProductSearchCriteria;
import com.ztravel.product.weixin.vo.VisaProductListVo;

public class VisaProductListConvertor {
	
	public static Map<String,String> paramsConvert(ProductSearchCriteria criteria){
		Map<String,String> map = new HashMap<String,String>();
		if( StringUtils.isNotBlank(criteria.getDestination()) && !criteria.getDestination().equals(Const.DESTINATION_PLACEHOLDER) ){
			switch(criteria.getDestLevel()){
			case 1:
				map.put("toContinent", criteria.getDestination());
				break;
			case 2:
				map.put("toCountry", criteria.getDestination());
				break;
			}
		}
		map.put("status", ProductStatus.RELEASED.name());
		return map;
	}
	
	public static List<VisaProductListVo> listConvert(List<VisaProduct> visaProducts) throws Exception{
		List<VisaProductListVo> products = new ArrayList<>();
		DecimalFormat df =new DecimalFormat("#"); 
		if(CollectionUtils.isNotEmpty(visaProducts)){
			for(VisaProduct visa : visaProducts){
				VisaProductListVo vo = new VisaProductListVo();
				PropertyUtils.copyProperties(vo, visa);
				if(null != visa.getDetailInfo() && CollectionUtils.isNotEmpty(visa.getDetailInfo().getImages())){
					vo.setImage(visa.getDetailInfo().getImages().get(0));
				}
				if(CollectionUtils.isNotEmpty(visa.getPriceInfos())){
					vo.setLowest(df.format(PieceProductUtils.getLowestPrice(visa.getPid(), visa.getPriceInfos())));
				}
				if(null != visa.getBasicInfo() && CollectionUtils.isNotEmpty(visa.getBasicInfo().getToCountry())){
					vo.setToCountry(visa.getBasicInfo().getToCountry().toString());
				}
				products.add(vo);
			}
		}
		return products;
	}
	
}
