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
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.utils.PieceProductUtils;
import com.ztravel.product.weixin.entity.ProductSearchCriteria;
import com.ztravel.product.weixin.vo.LocaltListVo;

public class LocalListConvertor {
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
	
	public static List<LocaltListVo> listConvert(List<UnVisaProduct> unVisaProducts) throws Exception{
		List<LocaltListVo> products = new ArrayList<>();
		DecimalFormat df =new DecimalFormat("#"); 
		if(CollectionUtils.isNotEmpty(unVisaProducts)){
			for(UnVisaProduct product : unVisaProducts){
				LocaltListVo vo = new LocaltListVo();
				PropertyUtils.copyProperties(vo, product);
				if(null != product.getDetailInfo() && CollectionUtils.isNotEmpty(product.getDetailInfo().getImages())){
					vo.setImage(product.getDetailInfo().getImages().get(0));
				}
				if(CollectionUtils.isNotEmpty(product.getPriceInfos())){
					vo.setLowest(df.format(PieceProductUtils.getLowestPrice(product.getPid(), product.getPriceInfos())));
				}
				vo.setTypeDesc(product.getBasicInfo().getType().getDesc());
				products.add(vo);
			}
		}
		return products;
	}
}
