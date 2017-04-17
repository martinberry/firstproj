package com.ztravel.product.back.hotel.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.hotel.convertor.HotelEntityConvertor;
import com.ztravel.product.dao.IHotelDao;
import com.ztravel.product.back.hotel.entity.HotelEntity;
import com.ztravel.product.back.hotel.entity.searchcriteria.HotelSearchCriteria;
import com.ztravel.product.back.hotel.service.IHotelService;
import com.ztravel.product.back.hotel.vo.HotelVO;

/**
 * @author
 * 酒店列表查询Service
 */
@Service
public class HotelServiceImpl implements IHotelService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(HotelServiceImpl.class);

	@Resource
	private IHotelDao hotelDao;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<HotelVO> searchHotels(HotelSearchCriteria criteria) throws Exception {
		Map params = new HashMap();
		convertHotelSearchCriteria(params, criteria);
		params.put("offset", (criteria.getPageNo()-1)*criteria.getPageSize());
		params.put("limit", criteria.getPageSize());

		List<HotelEntity> hotelEntities = hotelDao.searchHotelsWithPaging(params);
		if( hotelEntities == null ){
			LOGGER.error("酒店搜索失败");
			throw ZtrBizException.instance(ProductCons.PROD_HOTEL_SEARCH_ERROR_CODE, ProductCons.PROD_HOTEL_SEARCH_ERROR_MSG);
		}
		List<HotelVO> hotelList = HotelEntityConvertor.convertEntityListToVOList(hotelEntities);
		return hotelList;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int countHotels(HotelSearchCriteria criteria) throws Exception {
		Map params = new HashMap();
		convertHotelSearchCriteria(params, criteria);
		return hotelDao.countHotels(params);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void convertHotelSearchCriteria(Map params, HotelSearchCriteria criteria){
		if( StringUtils.isNotBlank(criteria.getHotelNameCn()) ){
			params.put("hotelNameCn", criteria.getHotelNameCn());
		}
		if( StringUtils.isNotBlank(criteria.getDestCountry()) && !criteria.getDestCountry().equals("不限") ){
			params.put("nation", criteria.getDestCountry());
		}
		if( StringUtils.isNotBlank(criteria.getDestCityOrAttraction()) && !criteria.getDestCityOrAttraction().equals("不限") ){
			params.put("city", criteria.getDestCityOrAttraction());
		}
		if( StringUtils.isNotBlank(criteria.getStatus()) ){
			if( criteria.getStatus().equals("complete") ){
				params.put("isComplete", true);
			}else if( criteria.getStatus().equals("draft") ){
				params.put("isComplete", false);
			}
		}
	}

	@Override
	public void deleteHotelById(String id) throws Exception {
		int nRow = hotelDao.deleteById(id);
		if( nRow != 1 ){
			throw ZtrBizException.instance(ProductCons.PROD_HOTEL_DELETE_ERROR_CODE, ProductCons.PROD_HOTEL_DELETE_ERROR_MSG);
		}
	}

	@Override
	public HotelEntity getHotelById(String hotelId) {
		return hotelDao.getHotelById(hotelId) ;
	}

}
