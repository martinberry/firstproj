package com.ztravel.product.back.activity.validate;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.enums.ProductRangeType;
import com.ztravel.product.back.activity.vo.CouponVo;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.reuse.product.service.IUnvisaProductReuseService;
import com.ztravel.reuse.product.service.IVisaProductReuseService;

@Component
public class CouponValidate {

	@Resource
	private  ProductService productServiceImpl;
	
	@Resource
	private IVisaProductReuseService visaProductReuseService;
	
	@Resource
	private IUnvisaProductReuseService unvisaProductReuseService;

	public   AjaxResponse couponValidate(CouponVo couponVo){
		AjaxResponse response = AjaxResponse.instance(ActivityConstants.COUPON_VALIDATE_FAILED,"");
		if(couponVo.getProductRangeType().equals(ProductRangeType.MANUALADD.name())){
			if(StringUtils.isBlank(couponVo.getProductRange())){
				response.setRes_msg("产品范围必填");
				return response;
			}else{
				List<String> productList = Arrays.asList(couponVo.getProductRange().trim().split(","));
				if(CollectionUtils.isNotEmpty(productList)){
					if(productList.size() > 100){
						response.setRes_msg("最多添加100个产品");
						return response;
					}
					for(String productCode : productList){
						try {
							if(!productServiceImpl.isProductExistByCode(productCode) && null == visaProductReuseService.selectByPid(productCode) && null == unvisaProductReuseService.selectByPid(productCode)){
								response.setRes_msg("产品"+productCode+"不存在");
								return response;
							}
						} catch (Exception e) {
							response.setRes_msg("查询产品"+productCode+"错误");
							return response;
						}
						if(productList.indexOf(productCode.trim()) != productList.lastIndexOf(productCode.trim())){
							response.setRes_msg("重复添加产品"+productCode);
							return response;
						}
					}
				}
			}
		}
		response.setRes_code(ActivityConstants.COUPON_VALIDATE_SUCCESS);
		return response;
	}
}
