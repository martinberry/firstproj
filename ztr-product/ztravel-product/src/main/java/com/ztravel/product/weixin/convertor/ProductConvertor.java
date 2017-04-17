package com.ztravel.product.weixin.convertor;

import java.text.DecimalFormat;

import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.weixin.vo.ProductVO;


/**
 * weixin端产品列表数据结构转换
 * @author zhiwen.hu
 */
public class ProductConvertor {

	public static ProductVO convertEntityToVO(Product prod){
		ProductVO prodVO = new ProductVO();
		DecimalFormat df = new DecimalFormat("0.##"); 
		prodVO.setTheme(prod.getTheme());
		prodVO.setSubName(prod.getSubName());
		prodVO.setRecommendWords(prod.getRecommendWords());
		prodVO.setLowestPrice(df.format(prod.getLowestPrice()));
		prodVO.setId(prod.getId().toString());
		prodVO.setPid(prod.getPid());
		prodVO.setpName(prod.getpName());
		if( prod.getImages() != null && prod.getImages().size() != 0 ){
			prodVO.setImageId(prod.getImages().get(0));
		}
		return prodVO;
	}

}
