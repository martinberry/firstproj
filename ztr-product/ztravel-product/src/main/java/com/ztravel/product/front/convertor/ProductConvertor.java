package com.ztravel.product.front.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.front.vo.ProductVO;

/**
 * C端产品列表数据结构转换
 * @author MH
 */
public class ProductConvertor {

	public static ProductVO convertEntityToVO(Product prod){
		ProductVO prodVO = new ProductVO();
		prodVO.setId(prod.getId().toString());
		prodVO.setPid(prod.getPid());
		prodVO.setpName(prod.getpName());
		//标签取5个
		if( prod.getTags() != null ){
			if( prod.getTags().size() > 5 ){
				prodVO.setTags(prod.getTags().subList(0, 5));
			}else{
				prodVO.setTags(prod.getTags());
			}
		}
		//亮点截取前30个字
		if( prod.getHighLights() != null ){
			List<String> highLights = new ArrayList<String>();
			for(String highLight : prod.getHighLights()){
				if( highLight.length() > 30 ){
					highLights.add(highLight.substring(0,30));
				}else{
					highLights.add(highLight);
				}
			}
			prodVO.setHighLights(highLights);
		}

		if( prod.getImages() != null && prod.getImages().size() != 0 ){
			prodVO.setImageId(prod.getImages().get(0));
		}
		if( prod.getTitleImages() != null && prod.getTitleImages().size() != 0 ){
			prodVO.setTitleImageId(prod.getTitleImages().get(0));
		}
		return prodVO;
	}

}
