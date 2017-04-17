package com.ztravel.operator.client.basicdata.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;
import com.ztravel.common.enums.ProductType;
import com.ztravel.operator.basicdata.dao.IRecProductDAO;
import com.ztravel.operator.basicdata.entity.RecProductEntity;
import com.ztravel.operator.basicdata.entity.RecProductHomePageEntity;
import com.ztravel.operator.basicdata.service.IRecProductClientService;
import com.ztravel.product.client.entity.ProductHomePageEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.product.dao.IUnVisaProductDao;
import com.ztravel.product.dao.IVisaProductDao;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Service
public class RecProductClientServiceImpl implements IRecProductClientService{
	@Resource
	private IRecProductDAO recProductDao;

    @Resource
    IVisaProductDao visaProductDao;

    @Resource
    IUnVisaProductDao unVisaProductDao;

	@Resource
	private IProductClientService productClientService;

	public void saveRecProduct(List<RecProductEntity> recproductlist) throws MongoException{

		recProductDao.deleteRecProductCollection();
		recProductDao.insertRecProductList(recproductlist);

	}

	public List<RecProductHomePageEntity> searchRecProduct() throws Exception{
		List<RecProductHomePageEntity> results = new ArrayList<RecProductHomePageEntity>() ;
		List<RecProductEntity> entitys = recProductDao.searchRecProduct() ;
		if(CollectionUtils.isNotEmpty(entitys)){
			for(RecProductEntity entity : entitys){
				RecProductHomePageEntity recProductHomePageEntity = new RecProductHomePageEntity() ;
				recProductHomePageEntity.setMainTitle(entity.getMainTitle()) ;
				recProductHomePageEntity.setPictureId(entity.getPictureId()) ;
                recProductHomePageEntity.setProductType(entity.getProductType()) ;
				recProductHomePageEntity.setProductId(entity.getProductId()) ;
				recProductHomePageEntity.setViceTitle(entity.getViceTitle()) ;
				if (ProductType.TRAVEL.name().equals(entity.getProductType())) {
	                ProductHomePageEntity hpentity = productClientService.getProductHPById(entity.getProductId()) ;
	                recProductHomePageEntity.setLowestPrice(hpentity.getLowestPrice());
	                recProductHomePageEntity.setTags(hpentity.getTags());
	                recProductHomePageEntity.setHighLights(hpentity.getHighLights());
				} else if (ProductType.VISA.name().equals(entity.getProductType())) {
				    VisaProduct visaProduct = visaProductDao.getProductByPid(entity.getProductId());
				    Double lowestPrice = null;
				    for (PriceInfo priceInfo : visaProduct.getPriceInfos()) {
				        if (lowestPrice == null || lowestPrice > priceInfo.getAdultPrice()) {
				            lowestPrice = priceInfo.getAdultPrice();
				        }
				        recProductHomePageEntity.setLowestPrice(lowestPrice);
				    }
                }  else if (ProductType.UNVISA.name().equals(entity.getProductType())) {
                    UnVisaProduct unVisaProduct = unVisaProductDao.getUnvisaProductByPid(entity.getProductId());
                    Double lowestPrice = null;
                    for (PriceInfo priceInfo : unVisaProduct.getPriceInfos()) {
                        if (lowestPrice == null || lowestPrice > priceInfo.getAdultPrice()) {
                            lowestPrice = priceInfo.getAdultPrice();
                        }
                        recProductHomePageEntity.setLowestPrice(lowestPrice);
                    }
                }
				results.add(recProductHomePageEntity) ;
			}
		}
		return results;
	}


}
