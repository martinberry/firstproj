package com.ztravel.reuse.order.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.reuse.order.service.IPieceProductPriceReuseService;

@Service
public class PieceProductPriceReuseService implements IPieceProductPriceReuseService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceProductPriceReuseService.class);

	@Resource
	private IVisaProductDao visaProductDaoImpl;

	@Resource
	private IUnVisaProductDao unVisaProductDaoImpl;

	@Override
	public Double getPieceProductTotalPrice(String productId, String priceId,Integer adultNum, Integer childNum,String productNature) throws Exception {
		Assert.hasText(productId, "计算碎片产品总价时，产品ID为空");
		Assert.hasText(priceId, "计算碎片产品总价时，产品价格ID为空");
		Assert.hasText(priceId, "计算碎片产品总价时，产品性质为空");
		LOGGER.info("开始计算订单总价，参数:产品ID:[],产品类型：[],成人数:[],儿童数：[],价格ID:[]",productId,productNature,adultNum,childNum,priceId);
		Double total = null;
		Double priceArray[] = null;
		if(PieceType.VISA.name().equals(productNature)){
			priceArray = getVisaProductPriceInfo(productId, priceId);
		}else{
			priceArray = getUnVisaProductPriceInfo(productId, priceId);
		}
		total = getPieceProductTotalPrice(productId, priceId, adultNum, childNum,priceArray);
		LOGGER.info("订单总价计算结束,计算结果：[]",total);
		return total;
	}
	public Double getPieceProductTotalPrice(String productId, String priceId,Integer adultNum, Integer childNum,Double[] priceArray) throws Exception {
		Assert.isTrue((null != priceArray && priceArray.length > 0),"产品["+productId+"]ID为["+priceId+"]的价格信息设置错误");
		Double totalPrice = 0D ;
        if (priceArray[0] != null && adultNum != null) {
            totalPrice += priceArray[0] * adultNum ;
        }
        if(priceArray[1] != null && childNum != null){
            totalPrice += priceArray[1] * childNum ;
        }
        if(priceArray[2] != null && adultNum % 2 == 1){
            totalPrice += priceArray[2] ;
        }
        totalPrice = totalPrice * 100 ;
        return totalPrice;
    }

    public Double[] getVisaProductPriceInfo(String productId, String priceId)throws Exception {
        Double[] ret = new Double[3] ;
        VisaProduct product = visaProductDaoImpl.getProductById(productId) ;
        List<PriceInfo> prices = product.getPriceInfos();
        if(!CollectionUtils.isEmpty(prices)){
        	PriceInfo price = null;
        	for(PriceInfo priceInfo : prices){
        		if(priceInfo.getId().equals(priceId)){
        			price = priceInfo;
        		}
        	}
        	Assert.isTrue(null != price, "签证产品["+productId+"]未设置ID为["+priceId+"]的价格信息");
        	ret[0] = price.getAdultPrice();
        	if(price.getHasChildPrice()){
        		ret[1] = price.getChildPrice();
        	}else{
        		ret[1] = null;
        	}
        	ret[2] = null;
        }
        return ret ;
    }

    public Double[] getUnVisaProductPriceInfo(String productId, String priceId)throws Exception {
    	 Double[] ret = new Double[3] ;
         UnVisaProduct product = unVisaProductDaoImpl.getProductById(productId) ;
         List<PriceInfo> prices = product.getPriceInfos();
         if(!CollectionUtils.isEmpty(prices)){
        	PriceInfo price = null;
         	for(PriceInfo priceInfo : prices){
        		if(priceInfo.getId().equals(priceId)){
        			price = priceInfo;
        		}
        	}
         	Assert.isTrue(null != price, "非签证产品["+productId+"]未设置ID为["+priceId+"]的价格信息");
         	ret[0] = price.getAdultPrice();
         	if(price.getHasChildPrice()){
         		ret[1] = price.getChildPrice();
         	}else{
         		ret[1] = null;
         	}
         	ret[2] = null;
         }
         return ret ;
    }

	@Override
	public String getPidById(String productId, String type) throws Exception {
		LOGGER.info("查询碎片产品[{}]编号",productId);
		Assert.hasText(productId, "查询碎片产品编号时，产品ID为空");
		Assert.hasText(type, "查询碎片产品["+productId+"]编号时，产品类型为空");
		if(type.trim().equals(PieceType.VISA.name())){
			VisaProduct visa =  visaProductDaoImpl.getProductById(productId);
			Assert.isTrue(null != visa, "签证产品["+productId+"不存在]");
			return visa.getPid();
		}else{
			UnVisaProduct unVisa = unVisaProductDaoImpl.getProductById(productId);
			Assert.isTrue(null != unVisa, "非签证产品["+productId+"不存在]");
			return unVisa.getPid();
		}
	}
	@Override
	public Boolean isReleased(String productNature, String productId) {
		Boolean released = false;
		try {
			if(productNature.equals(PieceType.VISA.name())){
					VisaProduct pro = visaProductDaoImpl.getProductById(productId);
					released = null == pro ? false : pro.getStatus().equals(ProductStatus.RELEASED);
			}else{
				UnVisaProduct pro = unVisaProductDaoImpl.getProductById(productId);
				released = null == pro ? false : pro.getStatus().equals(ProductStatus.RELEASED);
			}
		} catch (Exception e) {
			LOGGER.error("查询产品[{}]错误:[{}]", productId,e);
		}
		return released;
	}

}
