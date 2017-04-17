package com.ztravel.order.back.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ztravel.common.enums.Nature;
import com.ztravel.order.back.convertor.OrderConvertor;
import com.ztravel.order.back.criteria.LocalOrderSearchCriteria;
import com.ztravel.order.back.service.IUnvisaOrderService;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.order.dao.IOrderOrderProductDao;
import com.ztravel.order.po.OrderPO;


@Service
public class UnvisaOrderServiceImpl implements IUnvisaOrderService{
	
	@Resource
	private IOrderOrderProductDao orderOrderProductDao;

	private static final Logger LOGGER=LoggerFactory.getLogger(VisaOrderServiceImpl.class);
	


	@Override
	public List<OrderListVO> searchLocalOrder(LocalOrderSearchCriteria criteria)throws Exception{
		Map paramsMap = convertLocalOrderSearchCriteria(criteria);
		paramsMap.put("offset", (criteria.getPageNo() - 1) * criteria.getPageSize());
		paramsMap.put("limit", criteria.getPageSize());
		List<OrderPO> orderList = orderOrderProductDao.selectByUnvisaOrderId(paramsMap);
		List<OrderListVO> localorderVoList = OrderConvertor.convertPiecePOList2VOList(orderList);
		return localorderVoList;
	}
	
	

	  @Override
		public Integer countLocalOrders(LocalOrderSearchCriteria criteria) throws Exception {
			Map<String, Object> paramsMap = convertLocalOrderSearchCriteria(criteria);
			return orderOrderProductDao.countUnvisa(paramsMap);
		}
	
	
	
	private Map convertLocalOrderSearchCriteria(LocalOrderSearchCriteria criteria){
		Map map = new HashMap();
		map.put("payStatus", "PAID");
		map.put("paymentType", "Coupon");
		if(StringUtils.isNotBlank(criteria.getOrderNo())){
			map.put("orderNo", criteria.getOrderNo());
		}
		if(StringUtils.isNotBlank(criteria.getProductNature())){
			map.put("productNature", criteria.getProductNature());
		}
		// 产品标题 模糊查询
		if (StringUtils.isNotBlank(criteria.getProductTitle())) {
			String productTitle;
			if (criteria.getProductTitle().contains("%")) {
				productTitle = criteria.getProductTitle().replace("%", "\\%");
			} else {
				productTitle = criteria.getProductTitle();
			}
			map.put("productTitle", "%" + productTitle + "%");
		}
		if (StringUtils.isNotBlank(criteria.getTravellerNames())) {
			String traveller;
			if (criteria.getTravellerNames().contains("%")) {
				traveller = criteria.getTravellerNames().replace("%", "\\%");
			} else {
				traveller = criteria.getTravellerNames();
			}
			map.put("travellerNames", "%" + traveller + "%");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (StringUtils.isNotBlank(criteria.getPurchaseTimeFrom())) {
				map.put("purchaseDateFrom", format.parse(criteria.getPurchaseTimeFrom() + " 00:00:00"));
			}
			if (StringUtils.isNotBlank(criteria.getPurchaseTimeTo())) {
				map.put("purchaseDateTo", format.parse(criteria.getPurchaseTimeTo() + " 23:59:59"));
			}
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		if (StringUtils.isNotBlank(criteria.getStatus())){
			map.put("backState", criteria.getStatus());
		}		
        return map;
	}
	
	

}
