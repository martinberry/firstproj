package com.ztravel.product.client.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.enums.AdditionalRule;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.operator.client.finance.service.ISupplierClientService;
import com.ztravel.operator.financeMantain.po.Supplier;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.client.entity.ProductClientEntity;
import com.ztravel.product.client.entity.ProductHomePageEntity;
import com.ztravel.product.client.entity.TopicRelatedProductEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.product.dao.ProductDao;

@Service
public class ProductClientServiceImpl implements IProductClientService {

	private static Logger LOGGER = RequestIdentityLogger.getLogger(ProductClientServiceImpl.class);

	@Resource
	ProductDao productDao;

	@Resource
	private ISupplierClientService supplierClientService ;

	public Integer getProductStockByIdAndBookDate(Product product,String bookDate) throws Exception{
		Assert.isTrue(product != null, "产品{"+product.getId().toString()+"}不存在");
		Assert.isTrue(product.getCalendar() != null, "产品{"+product.getId().toString()+"}未设置价格日历");
		Assert.isTrue(product.getCalendar().get(bookDate) != null, "产品{"+product.getId().toString()+"}未设置{"+bookDate+"}的价格信息");
		Sale sale = product.getCalendar().get(bookDate).getSale();
		return sale == null ? 0 : sale.getStock();
	}

	@Override
	public Boolean updateAndModifyStock(String productId, String bookDate, Integer usedStock) throws Exception {
		Product product = productDao.getProductById(productId);
		Integer stock = getProductStockByIdAndBookDate(product,bookDate);
		Integer oldUsedStock = getProductUsedStockByIdAndBookDate(product,bookDate);
		Boolean updateResult = productDao.updateAndModifyStock(productId, bookDate, (oldUsedStock + usedStock) < 0 ? 0 : (oldUsedStock + usedStock), stock - usedStock) ;
		if(!updateResult){
			LOGGER.debug("扣除产品{}在{}日的库存失败,产品剩余库存为{},占用库存为{}",productId,bookDate,stock,usedStock);
			throw new RuntimeException("扣除产品库存失败");
		}
		return updateResult;
	}

	//获取产品已经使用的库存
	public Integer getProductUsedStockByIdAndBookDate(Product product,String bookDate) {
		Assert.isTrue(product != null, "产品{"+product.getId().toString()+"}不存在");
		Assert.isTrue(product.getCalendar() != null, "产品{"+product.getId().toString()+"}未设置价格日历");
		Assert.isTrue(product.getCalendar().get(bookDate) != null, "产品{"+product.getId().toString()+"}未设置{"+bookDate+"}的价格信息");
		Sale sale = product.getCalendar().get(bookDate).getSale();
		return sale == null ? 0 : sale.getUsedStock() == null ? 0 : sale.getUsedStock();
	}

	@Override
	public Integer getStock(String productId, String bookDate) throws Exception {
		Product product = productDao.getProductById(productId);
		return getProductStockByIdAndBookDate(product,bookDate) ;
	}

	@Override
	public ProductClientEntity getProductById(String id) throws Exception {
		ProductClientEntity prodClientEntity = new ProductClientEntity();

		Product product = productDao.getProductById(id);
		if( product == null ){
			throw ZtrBizException.instance(ProductCons.PROD_QUERY_ERROR_CODE, ProductCons.PROD_QUERY_ERROR_MSG);
		}
		JSONObject providerInfo = new JSONObject();
		if( product.getNature() == Nature.PACKAGE ){
			Supplier supplier = supplierClientService.getSupplierById(product.getPackageSupplier()) ;
			JSONObject json = new JSONObject() ;
			json.put("name", supplier.getSupplierName()) ;
			providerInfo.put("packageSupplier", json);
		}else if( product.getNature() == Nature.COMBINATION ){
			if(product.isContainFlight()){
				Supplier supplier = supplierClientService.getSupplierById(product.getFlightSupplier()) ;
				JSONObject json = new JSONObject() ;
				json.put("name", supplier.getSupplierName()) ;
				providerInfo.put("flightSupplier", json);
			}
			if(product.isContainHotel()){
				String hotelProviderInfo = "" ;
				if(!CollectionUtils.isEmpty(product.getHotels())){
					for(Hotel hotel : product.getHotels()){
						if(StringUtils.isNotBlank(hotel.getSupplier())){
							hotelProviderInfo += supplierClientService.getSupplierById(hotel.getSupplier()).getSupplierName() + "," ;
						}
					}
				}
				hotelProviderInfo = hotelProviderInfo.length() > 0 ? hotelProviderInfo.substring(0, hotelProviderInfo.length() - 1) : hotelProviderInfo ;
				providerInfo.put("hotelSupplier", hotelProviderInfo);
			}
		}
		prodClientEntity.setToCountry(product.getToCountry());
		prodClientEntity.setProductCode(product.getPid());
		prodClientEntity.setProviderInfo(providerInfo.toJSONString());
		prodClientEntity.setTripDays(product.getTripDays());
		Map<AdditionalRule, String> additionalInfoMap = product.getAdditionalInfos();
		if( additionalInfoMap != null ){
			Map<String, String> additionalInfos = new HashMap<String, String>();
			for(AdditionalRule rule : additionalInfoMap.keySet()){
				additionalInfos.put(rule.toString(), additionalInfoMap.get(rule));
			}
			prodClientEntity.setAdditionalInfos(additionalInfos);
		}
		return prodClientEntity;
	}

	@Override
	public ProductHomePageEntity getProductHPById(String id) throws Exception {
		ProductHomePageEntity entity = new ProductHomePageEntity() ;
		Product product = productDao.getProductByPid(id);
		if(!CollectionUtils.isEmpty(product.getHighLights())){
			List<String> hlts = new ArrayList<String>() ;
			int index = 4 ;
			for(String hlt : product.getHighLights()){
				if(index < 0) break ;
				hlts.add(hlt) ;
				index -- ;
			}
			entity.setHighLights(hlts);
		}
		if(!CollectionUtils.isEmpty(product.getTags())){
			List<String> tags = new ArrayList<String>() ;
			for(String tag : product.getTags()){
				tags.add(tag) ;
			}
			entity.setTags(tags);
		}
		entity.setLowestPrice(product.getLowestPrice());
		return entity;
	}


	@Override
	public String getProductstatusbyid(String id) throws Exception {
		Product product = productDao.getProductByPid(id);
		Status state=product.getStatus();
        String status =state.getName();
		return status;
	}
	
	@Override
	public List<TopicRelatedProductEntity> getReleasedProduct() throws Exception{
		List<Product> productList=productDao.getAllReleasedProducts();
		List<TopicRelatedProductEntity> RelatedProductList=new ArrayList<TopicRelatedProductEntity>();
		for(Product producttmp:productList){
			TopicRelatedProductEntity RelatedProduct=new TopicRelatedProductEntity();
			RelatedProduct.setPid(producttmp.getPid());
			RelatedProduct.setpName(producttmp.getpName());
			RelatedProduct.setCombinetitle(producttmp.getPid()+','+producttmp.getpName());
			RelatedProductList.add(RelatedProduct);
		}
		return RelatedProductList;
	}
	
	

}
