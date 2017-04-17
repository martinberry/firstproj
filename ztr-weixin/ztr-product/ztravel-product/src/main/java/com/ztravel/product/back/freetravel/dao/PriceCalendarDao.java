package com.ztravel.product.back.freetravel.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Sale;
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.back.freetravel.enums.SaleStatus;

/**
 * @author wanhaofan
 * */
public interface PriceCalendarDao {

	Map<String, Day> getCalendar(String id) ;

	Boolean updateSale(String id, String day, Sale sale) ;

	Sale getSale(String id, String day) ;

	Boolean deleteSale(String id, String day) ;

	boolean deletePkg(String id, String effectDay, String pkgId);

	SalesPackage getPkg(String id, String effectDay, String pkgId);

	Boolean updateSaleBatch(String id, List<String> days, Sale sale,
			boolean needUpdPrice);

	boolean updateSaleStatus(String id, String effectDay, SaleStatus closed);
	
}
