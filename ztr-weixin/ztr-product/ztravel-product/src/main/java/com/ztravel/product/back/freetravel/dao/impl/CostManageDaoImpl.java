package com.ztravel.product.back.freetravel.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.site.lookup.util.StringUtils;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.product.back.freetravel.dao.CostManageDao;
import com.ztravel.product.back.freetravel.entity.Cost;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.dao.ProductDao;

@Repository
public class CostManageDaoImpl implements CostManageDao{

	@Resource
	private Datastore datastore ;

	@Resource
	private ProductDao productDao ;
	
	@Resource
	private IdGeneratorUtil idGeneratorUtil ;
	
	@Resource
	private Map<String, String> cityPySx ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@Override
	public Boolean saveFlight(String id, Flight flight) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.set("flight", flight) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Flight getFlight(String id) {
		return productDao.getProductById(id).getFlight() ;
	}

	@Override
	public Boolean deleteFlight(String id) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.unset("flight") ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Boolean saveHotel(String id, List<Hotel> hotels) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.set("hotels", hotels) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Boolean removeCost(String id, Map<String, Cost> costs) {
		Iterator<Entry<String, Cost>> iter = costs.entrySet().iterator() ;

		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		while(iter.hasNext()){
			Entry<String, Cost> entry = iter.next() ;
			if(calendar.get(entry.getKey()) != null){
				if(calendar.get(entry.getKey()).getSale() != null){
					ops.unset("calendar."+entry.getKey()+".cost") ;
				}else{
					ops.unset("calendar."+entry.getKey()) ;
				}
//				ops.unset("calendar."+entry.getKey()+".cost") ;
			}
		}
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Boolean addCost(String id, Map<String, Cost> costs) throws Exception {
		Iterator<Entry<String, Cost>> iter = costs.entrySet().iterator() ;

		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		Day day = null ;
		String sx = cityPySx.get(product.getTo().get(product.getTo().size() - 1)) == null ? "" : cityPySx.get(product.getTo().get(product.getTo().size() - 1)) ;
		while(iter.hasNext()){
			Entry<String, Cost> entry = iter.next() ;
			if(calendar.get(entry.getKey()) == null){
				day = new Day() ;
				day.setDay(entry.getKey());
				day.setCost(entry.getValue());
				day.setTeamNum(idGeneratorUtil.getTeamNum(sx));
				ops.set("calendar."+entry.getKey(), day) ;
			}else{
				day = calendar.get(entry.getKey()) ;
				if(StringUtils.isEmpty(day.getTeamNum())){
					ops.set("calendar."+entry.getKey()+".teamNum", idGeneratorUtil.getTeamNum(sx)) ;
				}
				if(entry.getValue().getPackageAdultCost() != null){
					ops.set("calendar."+entry.getKey()+".cost.packageAdultCost", entry.getValue().getPackageAdultCost()) ;
				}
				if(entry.getValue().getPackageChildCost() != null){
					ops.set("calendar."+entry.getKey()+".cost.packageChildCost", entry.getValue().getPackageChildCost()) ;
				}
			}
		}
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Boolean addHotelCost(String id, Map<String, Cost> costs) throws Exception {
		Iterator<Entry<String, Cost>> iter = costs.entrySet().iterator() ;

		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		Day day = null ;
		String sx = cityPySx.get(product.getTo().get(product.getTo().size() - 1)) == null ? "" : cityPySx.get(product.getTo().get(product.getTo().size() - 1)) ;
		while(iter.hasNext()){
			Entry<String, Cost> entry = iter.next() ;
			if(calendar.get(entry.getKey()) == null){
				day = new Day() ;
				day.setDay(entry.getKey());
				day.setCost(entry.getValue());
				day.setTeamNum(idGeneratorUtil.getTeamNum(sx));
				ops.set("calendar."+entry.getKey(), day) ;
			}else{
				day = calendar.get(entry.getKey()) ;
				if(StringUtils.isEmpty(day.getTeamNum())){
					ops.set("calendar."+entry.getKey()+".teamNum", idGeneratorUtil.getTeamNum(sx)) ;
				}
				if(entry.getValue().getHotelRoomCost() != null){
					ops.set("calendar."+entry.getKey()+".cost.hotelRoomCost", entry.getValue().getHotelRoomCost()) ;
				}
			}
		}
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}
	@Override
	public Boolean removeHotelCost(String id, Map<String, Cost> costs) {
		Iterator<Entry<String, Cost>> iter = costs.entrySet().iterator() ;

		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		while(iter.hasNext()){
			Entry<String, Cost> entry = iter.next() ;
			if(calendar.get(entry.getKey()) != null){
				if(calendar.get(entry.getKey()).getCost() != null){
					if(calendar.get(entry.getKey()).getCost().getFlightAdultCost() != null || calendar.get(entry.getKey()).getCost().getFlightChildCost() != null){
						ops.unset("calendar."+entry.getKey()+".cost.hotelRoomCost") ;
					}else if(calendar.get(entry.getKey()).getSale() != null){
						ops.unset("calendar."+entry.getKey()+".cost") ;
					}else{
						ops.unset("calendar."+entry.getKey()) ;
					}
				}
//				ops.unset("calendar."+entry.getKey()+".cost.hotelRoomCost") ;
			}
		}
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}
	@Override
	public Boolean addFlightCost(String id, Map<String, Cost> costs) throws Exception {
		Iterator<Entry<String, Cost>> iter = costs.entrySet().iterator() ;

		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		Day day = null ;
		String sx = cityPySx.get(product.getTo().get(product.getTo().size() - 1)) == null ? "" : cityPySx.get(product.getTo().get(product.getTo().size() - 1)) ;
		while(iter.hasNext()){
			Entry<String, Cost> entry = iter.next() ;
			if(calendar.get(entry.getKey()) == null){
				day = new Day() ;
				day.setDay(entry.getKey());
				day.setCost(entry.getValue());
				day.setTeamNum(idGeneratorUtil.getTeamNum(sx));
				ops.set("calendar."+entry.getKey(), day) ;
			}else{
				day = calendar.get(entry.getKey()) ;
				if(StringUtils.isEmpty(day.getTeamNum())){
					ops.set("calendar."+entry.getKey()+".teamNum", idGeneratorUtil.getTeamNum(sx)) ;
				}
				if(entry.getValue().getFlightAdultCost() != null){
					ops.set("calendar."+entry.getKey()+".cost.flightAdultCost", entry.getValue().getFlightAdultCost()) ;
				}
				if(entry.getValue().getFlightChildCost() != null){
					ops.set("calendar."+entry.getKey()+".cost.flightChildCost", entry.getValue().getFlightChildCost()) ;
				}
			}
		}
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}
	@Override
	public Boolean removeFlightCost(String id, Map<String, Cost> costs) {
		Iterator<Entry<String, Cost>> iter = costs.entrySet().iterator() ;

		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		Product product = q.get() ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		Map<String, Day> calendar = product.getCalendar() == null ? new HashMap<String, Day>() : product.getCalendar() ;
		while(iter.hasNext()){
			Entry<String, Cost> entry = iter.next() ;
			if(calendar.get(entry.getKey()) != null){
				if(calendar.get(entry.getKey()).getCost() != null){
					if(calendar.get(entry.getKey()).getCost().getHotelRoomCost() != null){
						ops.unset("calendar."+entry.getKey()+".cost.flightAdultCost") ;
						ops.unset("calendar."+entry.getKey()+".cost.flightChildCost") ;
					}else if(calendar.get(entry.getKey()).getSale() != null){
						ops.unset("calendar."+entry.getKey()+".cost") ;
					}else{
						ops.unset("calendar."+entry.getKey()) ;
					}
				}
//				ops.unset("calendar."+entry.getKey()+".cost.flightAdultCost") ;
//				ops.unset("calendar."+entry.getKey()+".cost.flightChildCost") ;
			}
		}
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}

	@Override
	public Boolean saveCheck(String id, String supplier) {
		if(!"".equals(supplier)){
			Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
			UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class).set("packageSupplier", supplier) ;
			ops.set("updatedTime", DateTime.now()) ;
			ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
			return datastore.findAndModify(q, ops) != null ;
		}else{
			return true ;
		}
	}

	@Override
	public Boolean saveCombinationCheck(String id, String flightSupplier,
			String hotelSupplier) {
		if("".equals(flightSupplier) && "".equals(hotelSupplier)){
			return true ;
		}else{
			Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
			UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
			if(flightSupplier != null){
				ops.set("flightSupplier", flightSupplier) ;
			}
			if(hotelSupplier != null){
				ops.set("hotelSupplier", hotelSupplier) ;
			}
			ops.set("updatedTime", DateTime.now()) ;
			ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
			return datastore.findAndModify(q, ops) != null ;
		}
	}

	@Override
	public Boolean deleteHotel(String id) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.unset("hotels") ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q, ops) != null ;
	}
	
	@Override
	public Boolean deleteSingleHotel(String id, String hotelId) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		List<Hotel> hotels = q.get().getHotels() ;
		if(hotels == null){
			return true ;
		}
		List<Hotel> newHotels = new ArrayList<Hotel>() ;
		newHotels.addAll(hotels) ;
		for(Hotel h : hotels){
			if(h.getId().equals(hotelId)){
				newHotels.remove(h);
			}
		}
		Query<Product> q2 = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)).filter("hotels", hotels) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class).set("hotels", newHotels) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		return datastore.findAndModify(q2, ops) != null ;
	}

	@Override
	public Boolean saveAdditionCostSupplier(String id, String sid, Double cost, String type) {
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId(id)) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		ops.set("updatedTime", DateTime.now()) ;
		ops.set("updateBy", redisClient.get(OperatorSidHolder.get())) ;
		ops.set(type + "Supplier", sid) ;
		ops.set(type + "Cost", cost) ;
		return datastore.findAndModify(q, ops) != null ;
	}

}