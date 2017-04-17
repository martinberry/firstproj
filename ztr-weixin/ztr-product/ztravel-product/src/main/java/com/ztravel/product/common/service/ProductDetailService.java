package com.ztravel.product.common.service;

import java.util.List;
import java.util.Map;

import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.front.wo.CalendarDataWo;
import com.ztravel.product.front.wo.ProductWo;
import com.ztravel.product.weixin.wo.PriceTabDataWo;
import com.ztravel.product.weixin.wo.WxDayWo;

public interface ProductDetailService {

	/**
	 * 根据产品PID查询产品信息
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductWo getProductByPid(String productPid) throws Exception;

	/**
	 * 根据产品ID查询产品信息
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public ProductWo getProductById(String productId) throws Exception;

	/**
	 * 获取日历需要json
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	CalendarDataWo getCalDataByPid(String pid)throws Exception;

	/**
	 * 获取weixin 价格tab需要json
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public PriceTabDataWo getTabDataByPid(String pid)throws Exception;
	/**
	 * 获取日历需要json
	 * @param id
	 * @return
	 * @throws Exception
	 */
	CalendarDataWo getCalDataById(String id)throws Exception;


	/**
	 * 获取weixin价格tab需要Map<month,List<WxDayWo>>
	 * @param pid
	 * @return
	 * @throws Exception
	 */
	public Map<String, List<WxDayWo>> getWxDayMapByPid(String pid) throws Exception;

	public List<WxDayWo> getWxDayListByPid(String pid) throws Exception;

	/**
	 * 通过产品pid 和所选择的日期获取对应的套餐信息
	 * @param pid, chooseDay
	 * @return
	 * @throws Exception
	 */
	public List<SalesPackage> getPackageListByPidAndDate(String pid, String chooseDay) throws Exception;

	/**
	 * 通过产品pid 和所选择的日期获取库存
	 * @param pid, chooseDay
	 * @return
	 * @throws Exception
	 */
	public int getStockByPidAndDate(String pid, String chooseDay) throws Exception;
	
	
	public ProductWo getUnvisaProductByPid(String productPid)throws Exception;
}
