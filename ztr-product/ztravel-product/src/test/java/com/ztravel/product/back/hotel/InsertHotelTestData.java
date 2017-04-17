/**
 *
 */
package com.ztravel.product.back.hotel;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.jmkgreen.morphia.Datastore;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.enums.HotelRate;
import com.ztravel.product.back.hotel.enums.HotelType;

/**
 * @author
 * MongoDB酒店资源数据库插入测试数据
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/ztravel-datastore.xml"})
public class InsertHotelTestData {
	@Resource
	private Datastore datastore;

	@Test
	public void InsertTestData(){
		HotelEntity hotel = new HotelEntity();
		hotel.setShowId("J22233");
		hotel.setHotelNameCn("假日酒店");
		hotel.setHotelNameEn("Holiday Inn");
		hotel.setType(HotelType.TYPE_3);
		hotel.setRating(HotelRate.TWO_STAR);
		hotel.setContinent("欧洲");
		hotel.setNation("法国");
		hotel.setCity("巴黎");
		hotel.setAddress("呼兰西路123号4号楼");
		hotel.setPhone("18622334587");
		hotel.setAdvantage("早餐好/免费机场接送怕啥有可能比较多会回行");
		hotel.setIsComplete(false);
//		hotel.setCreateTime(new Date());
		datastore.save(hotel);
	}

}
