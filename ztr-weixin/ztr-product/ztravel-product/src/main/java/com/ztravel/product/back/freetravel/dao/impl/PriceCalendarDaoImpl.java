package com.ztravel.product.back.freetravel.dao.impl;

/**
 * @author wanhaofan
 * */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.dao.PriceCalendarDao;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.SaleStatus;
import com.ztravel.product.dao.ProductDao;

@Repository
public class PriceCalendarDaoImpl implements PriceCalendarDao{

	@Resource
	private ProductDao productDao ;

	@Resource
	private Datastore datastore ;
	
	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Map<String, Day> getCalendar(String id) {

		return productDao.getProductById(id).getCalendar() ;
	}

	@Override
	public Boolean updateSale(String id, String day, Sale sale) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		if(q.get() == null || q.get().getCalendar() == null
				|| q.get().getCalendar().get(day) == null || q.get().getCalendar().get(day).getSale() == null){
			sale.setUsedStock(0);
		}
		ops = resetSale(sale, ops, day, false, true) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Sale getSale(String id, String day) {
		return getCalendar(id).get(day).getSale() ;
	}

	@Override
	public boolean updateSaleStatus(String id, String day, SaleStatus saleStatus) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.set("calendar." + day + ".sale.saleStatus", saleStatus) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}
	
	@Override
	public Boolean deleteSale(String id, String day) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		if(calendar.get(day) != null){
			if(calendar.get(day).getCost() != null){
				ops.unset("calendar."+day+".sale") ;
			}else{
				ops.unset("calendar."+day) ;
			}
		}
//		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
//				.unset("calendar." + day + ".sale") ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public boolean deletePkg(String id, String effectDay, String pkgId) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		SalesPackage sp = new SalesPackage() ;
		sp.setPkgId(pkgId);
		ops.removeAll("calendar."+effectDay+".sale.salesPackages", sp) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}
	
	@Override
	public SalesPackage getPkg(String id, String effectDay, String pkgId) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product p = q.get() ;
		if(!CollectionUtils.isEmpty(p.getCalendar())){
			Map<String, Day> days = p.getCalendar() ;
			Day day = days.get(effectDay) ;
			if(day != null){
				Sale sale = day.getSale() ;
				if(sale != null){
					List<SalesPackage> pkgs = sale.getSalesPackages() ;
					if(!CollectionUtils.isEmpty(pkgs)){
						for(SalesPackage pkg:pkgs){
							if(pkg.getPkgId().equals(pkgId)){
								return pkg ;
							}
						}
					}
				}
			}
		}
		return null ;
	}
	
	@Override
	public Boolean updateSaleBatch(String id, List<String> days, Sale sale, boolean needUpdPrice) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		for(String day:days){
			if(q.get() == null || q.get().getCalendar() == null
					|| q.get().getCalendar().get(day) == null || q.get().getCalendar().get(day).getSale() == null){
				sale.setUsedStock(0);
				if(needUpdPrice){
					if(!CollectionUtils.isEmpty(sale.getSalesPackages())){
						ops.set("calendar." + day + ".sale.salesPackages", sale.getSalesPackages()) ;
					}
				}
			}else{
				sale.setUsedStock(null);
				if(!CollectionUtils.isEmpty(sale.getSalesPackages())){
					List<SalesPackage> pkgs = q.get().getCalendar().get(day).getSale().getSalesPackages() ;
					pkgs = pkgs == null ? new ArrayList<SalesPackage>() : pkgs ;
					pkgs.addAll(sale.getSalesPackages()) ;
					ops.set("calendar." + day + ".sale.salesPackages", pkgs) ;
				}
			}
			ops = resetSale(sale, ops, day, true, needUpdPrice) ;
		}
		return datastore.findAndModify(q, ops) != null ;
	}

	private UpdateOperations<Product> resetSale(Sale sale, UpdateOperations<Product> ops, String day, boolean isBatchSave, boolean needUpdPrice){
		if(needUpdPrice){
			if(sale.getAdultPrice() != null){		
				ops.set("calendar." + day + ".sale.adultPrice", sale.getAdultPrice()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.adultPrice") ;
			}		
			if(sale.getSaleUnit() != null){		
				ops.set("calendar." + day + ".sale.saleUnit", sale.getSaleUnit()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.saleUnit") ;
			}		
			if(sale.isAdultPriceHasTax() != null){		
				ops.set("calendar." + day + ".sale.isAdultPriceHasTax", sale.isAdultPriceHasTax()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.isAdultPriceHasTax") ;
			}	
			if(sale.isChildPriceHasTax() != null){		
				ops.set("calendar." + day + ".sale.isChildPriceHasTax", sale.isChildPriceHasTax()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.isChildPriceHasTax") ;
			}	
			if(sale.getHasChildPrice() != null){		
				ops.set("calendar." + day + ".sale.hasChildPrice", sale.getHasChildPrice()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.hasChildPrice") ;
			}	
			if(sale.getChildPrice() != null){		
				ops.set("calendar." + day + ".sale.childPrice", sale.getChildPrice()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.childPrice") ;
			}		
			if(sale.getSingleRoomPrice() != null){		
				ops.set("calendar." + day + ".sale.singleRoomPrice", sale.getSingleRoomPrice()) ;		
			}else{
				ops.unset("calendar." + day + ".sale.singleRoomPrice") ;	
			}
			if(sale.getStock() != null){
				ops.set("calendar." + day + ".sale.stock", sale.getStock()) ;
			}else{
				ops.unset("calendar." + day + ".sale.stock") ;
			}
			if(sale.getUsedStock() != null){
				ops.set("calendar." + day + ".sale.usedStock", sale.getUsedStock()) ;
			}
			if(sale.getMarketPrice() != null){
				ops.set("calendar." + day + ".sale.marketPrice", sale.getMarketPrice()) ;
			}else{
				ops.unset("calendar." + day + ".sale.marketPrice") ;
			}
			if(sale.getInAdvanceDays() != null){
				ops.set("calendar." + day + ".sale.inAdvanceDays", sale.getInAdvanceDays()) ;
			}else{
				ops.unset("calendar." + day + ".sale.inAdvanceDays") ;
			}
			if(sale.getInAdvanceHours() != null){
				ops.set("calendar." + day + ".sale.inAdvanceHours", sale.getInAdvanceHours()) ;
			}else{
				ops.unset("calendar." + day + ".sale.inAdvanceHours") ;
			}
			if(sale.getSaleStatus() != null){
				ops.set("calendar." + day + ".sale.saleStatus", sale.getSaleStatus()) ;
			}else{
				ops.unset("calendar." + day + ".sale.saleStatus") ;
			}
		}
		if(!isBatchSave){
			if(!CollectionUtils.isEmpty(sale.getSalesPackages())){
				ops.set("calendar." + day + ".sale.salesPackages", sale.getSalesPackages()) ;
			}else{
				ops.unset("calendar." + day + ".sale.salesPackages") ;
			}
		}
		return ops ;
	}

}
