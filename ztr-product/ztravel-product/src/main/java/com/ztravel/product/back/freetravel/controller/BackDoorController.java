package com.ztravel.product.back.freetravel.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.google.common.collect.Lists;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.enums.SaleStatus;
import com.ztravel.product.client.service.IVoucherClientService;

@Controller
@RequestMapping("/backdoor")
public class BackDoorController {
	
	@Resource
	private Datastore datastore ;
	
	@Resource
	private IVoucherClientService voucherClientService ;
	
	private static final Logger logger = RequestIdentityLogger.getLogger(BackDoorController.class) ;
	
	
	private static final String BACK_DOOR_KEY=TopsConfReader.getConfProperties("properties/backdoor.properties", ConfScope.R).getProperty("key", "") ;

	@RequestMapping("/saleStatus/{id}/{key}")
	public void saleStatus(@PathVariable String id, @PathVariable String key, HttpServletResponse response) throws Exception{
		if(checkKeyWrapperResponse(key, response)){
			Query<Product> q = datastore.createQuery(Product.class) ;
			if(!"all".equals(id)){
				q.filter("_id", new ObjectId(id)) ;
			}
			List<Product> products = q.asList() ;
			logger.info("update product saleStatus...");
			if(!CollectionUtils.isEmpty(products)){
				for(Product product : products){
					try{
						Map<String, Day> days = product.getCalendar() ;
						if(!CollectionUtils.isEmpty(days)){
							logger.info("update saleStatus start:::{}", product.getId());
							Query<Product> q1 = datastore.createQuery(Product.class).filter("_id", product.getId()) ;
							UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
							boolean needModify = false ;
							Iterator<Entry<String, Day>> iter = days.entrySet().iterator() ;
							while(iter.hasNext()){
								Entry<String, Day> entry = iter.next() ;
								Day day = entry.getValue() ;
								if(day.getSale() != null){
									ops.set("calendar." + entry.getKey() + ".sale.saleStatus", SaleStatus.RELEASED) ;
									needModify = true ;
								}
							}
							logger.info("update saleStatus end, response:::{}", needModify ? datastore.findAndModify(q1, ops) != null : "no need");
						}
					}catch(Exception e){
						logger.error("update saleStatus error:::{}", product.getId().toString(), e);
					}
				}
			}
		}
	}
	
	@RequestMapping("/deleteSupplier/{id}/{key}")
	public void deleteSupplier(@PathVariable String id, @PathVariable String key, HttpServletResponse response) throws Exception{
		if(checkKeyWrapperResponse(key, response)){
			Query<Product> q = datastore.createQuery(Product.class) ;
			if(!"all".equals(id)){
				q.filter("_id", new ObjectId(id)) ;
			}
			List<Product> products = q.asList() ;
			logger.info("update product deleteSupplier...");
			if(!CollectionUtils.isEmpty(products)){
				for(Product product : products){
					try{
						Query<Product> q1 = datastore.createQuery(Product.class).filter("_id", product.getId()) ;
						UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
						ops.unset("packageSupplier").unset("shuttleSupplier").unset("zenbookSupplier").unset("wifiSupplier")
							.unset("otherSupplier").unset("flightSupplier");
						List<Hotel> hotels = product.getHotels() ;
						boolean needModify = false ;
						if(!CollectionUtils.isEmpty(hotels)){
							for(Hotel hotel:hotels){
								needModify = true ;
								hotel.setSupplier(null);
							}
						}
						if(needModify){
							ops.set("hotels", hotels) ;
						}
						logger.info("update deleteSupplier end, response:::{}",datastore.findAndModify(q1, ops) != null);
					}catch(Exception e){
						logger.error("update deleteSupplier error:::{}", product.getId().toString(), e);
					}
				}
			}
		}
	}
	
	@RequestMapping("/voucher/code/helper/{couponId}/{key}")
	public void voucherCodeHelper(@PathVariable String couponId, @PathVariable String key, HttpServletResponse response) throws Exception{
		if(checkKeyWrapperResponse(key, response)){
			ClassPathResource resource = new ClassPathResource("voucher_codes") ;
			BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream())) ;
			String tmp = "" ;
			List<String> codes = Lists.newArrayList() ;
			while((tmp = reader.readLine()) != null){
				codes.add(tmp.trim()) ;
		    }
			logger.info("new voucher codes:::{}", codes);
			boolean ret = voucherClientService.updateVoucher4BackDoor(codes, couponId) ;
			logger.info("new voucher codes update end, response:::{}", ret);
		}
	}
	
	private boolean checkKeyWrapperResponse(String key, HttpServletResponse response){
		boolean ret = false ;
		try {
			if(!BACK_DOOR_KEY.equals(key)){
				response.getWriter().write("key error...");
			}else{
				ret = true ;
			}
		} catch (IOException e) {
			logger.error("backdoor response getwriter error...", e);
		} finally {
			if(!ret){
				try {
					response.getWriter().flush();
					response.getWriter().close();
				} catch (IOException e) {
					logger.error("backdoor response printwriter flush/close error...", e);
				}
			}
		}
		return ret ;
	}
	
}
