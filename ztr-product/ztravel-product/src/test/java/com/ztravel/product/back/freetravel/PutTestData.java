package com.ztravel.product.back.freetravel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.EnumUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jmkgreen.morphia.Datastore;
import com.github.jmkgreen.morphia.query.Query;
import com.github.jmkgreen.morphia.query.UpdateOperations;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.entity.Cost;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Flight;
import com.ztravel.product.back.freetravel.entity.FlightInfo;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.AirRange;
import com.ztravel.product.back.freetravel.enums.BedType;
import com.ztravel.product.back.freetravel.enums.BreakFestType;
import com.ztravel.product.back.freetravel.enums.Content;
import com.ztravel.product.back.freetravel.enums.SaleUnit;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/ztravel-datastore.xml"})
public class PutTestData {

	@Resource
	private Datastore datastore;

	@Test
	public void input(){
		Product product = new Product() ;
		product.setpName("测试底价维护03");
		product.setPid("D01002");

		product.setNature(Nature.PACKAGE);

		List<Content> contents = new ArrayList<Content>() ;
		contents.add(Content.FLIGHT) ;
		contents.add(Content.HOTEL) ;
		product.setContents(contents);

		Flight flight = new Flight() ;

		List<FlightInfo> flightInfos = new ArrayList<FlightInfo>() ;
		FlightInfo flightInfo = new FlightInfo() ;
		flightInfo.setAddDays(0);
		flightInfo.setAirLine("东方航空");
		flightInfo.setAirRange(AirRange.GO);
		flightInfo.setAirRangeIndex(1);
		flightInfo.setArrivalTime("2015-05-04 12:00");
		flightInfo.setDepartureTime("2015-05-02 12:00");
		flightInfo.setCabin("cabin");
		flightInfos.add(flightInfo) ;
		flight.setInfos(flightInfos);
		product.setFlight(flight);

		List<Hotel> hotels = new ArrayList<Hotel>() ;
		Hotel hotel = new Hotel() ;
		hotel.setId("AAAAAAAAAAAAAAAAA");
		List<Integer> checkinDays = new ArrayList<Integer>() ;
		checkinDays.add(4) ;
		checkinDays.add(3) ;
		checkinDays.add(1) ;
		checkinDays.add(2) ;
		hotel.setCheckinDays(checkinDays);
		hotel.setBedType(BedType.DOUBLE);
		hotel.setBreakFestType(BreakFestType.DOUBLE);
		hotel.setRoomType("标间");
		hotels.add(hotel) ;
		product.setHotels(hotels);

		Map<String, Day> calendar = new HashMap<String, Day>() ;
		DateTime time = new DateTime() ;
		Cost cost = new Cost();
		cost.setPackageAdultCost(600D);
		cost.setPackageChildCost(400D);

		Sale sale = new Sale();
//		sale.setAdultPrice(1000D);
//		sale.setChildPrice(600D);
//		sale.setClosed(false);
		sale.setStock(10);
		sale.setUsedStock(5);

		Sale sale2 = new Sale();
//		sale2.setAdultPrice(1200D);
//		sale2.setChildPrice(400D);
//		sale2.setClosed(false);
		sale2.setStock(6);
		sale2.setUsedStock(2);

		for(int i=0; i< 10; i++){
			time = time.plusDays(i) ;
			Day day = new Day();
			day.setDay(time.toString("yyyy-MM-dd"));
			day.setCost(cost);
			if(i<5){
				day.setSale(sale2);
			}else{
				day.setSale(sale);
			}
			calendar.put(day.getDay(), day) ;
		}

		for(int i=0; i< 30; i++){
			time = time.plusDays(i) ;
			Day day = new Day();
			day.setDay(time.toString("yyyy-MM-dd"));
			day.setCost(cost);
			calendar.put(day.getDay(), day) ;
		}

		product.setCalendar(calendar);

		datastore.save(product) ;

	}

	@Test
	public void delete(){

		System.out.println(datastore.createQuery(Product.class)
		.filter("_id", new ObjectId("553ddb8fe4b03d17781e0710")).get().getId()) ;

		datastore.delete(datastore.createQuery(Product.class)
				.filter("_id", new ObjectId("553ddb8fe4b03d17781e0710"))) ;
	}



	@Test
	public void update(){
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId("554895a4e4b0bf5d2cbdc5d6")) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class)
				.unset("calendar");
		datastore.findAndModify(q, ops) ;
	}

	@Test
	public void ttres(){
		System.out.println(EnumUtils.isValidEnum(SaleUnit.class, "SINGLE")) ;
	}
	
	@Test
	public void deletePkg(){
		Query<Product> q = datastore.createQuery(Product.class).filter("_id", new ObjectId("55f127ece4b0705b9411a746")) ;
		UpdateOperations<Product> ops = datastore.createUpdateOperations(Product.class) ;
		SalesPackage sp = new SalesPackage() ;
		sp.setPkgId("PKG151217110026000204");
		ops.removeAll("calendar.2015-12-24.sale.salesPackages", sp) ;
		Product product = datastore.findAndModify(q, ops) ;
		System.out.println(datastore.findAndModify(q, ops)) ;
	}

}
