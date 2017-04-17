package com.ztravel.product.back.freetravel.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;
import com.ztravel.member.client.service.IWishListClientService;
import com.ztravel.product.back.freetravel.controller.ProductMaintainController;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Hotel;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.back.freetravel.entity.ProductSearchCriteria;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.utils.Converter;
import com.ztravel.product.back.freetravel.vo.ProductCheckRespBean;
import com.ztravel.product.back.freetravel.vo.ProductMenuVo;
import com.ztravel.product.back.freetravel.vo.ProductVo;
import com.ztravel.product.dao.ProductDao;

/**
 * @author wanhaofan
 * */
@Service
public class ProductServiceImpl implements ProductService{

	private static final Logger logger = LoggerFactory.getLogger(ProductMaintainController.class);

	@Resource
	private ProductDao productDao ;
	@Resource
	private IWishListClientService wishListClientService;

	//更新产品进度
	@Override
	public Boolean updateProgress(String id, Integer progress) {
		Integer oldProgress = productDao.getProductById(id).getProgress() ;
		oldProgress = oldProgress == null ? 0 : oldProgress ;
		Boolean isOk = true ;
		if(progress - 1 == oldProgress){
			isOk = productDao.updateProgress(id, progress) ;
		}
		return isOk ;
	}

	@Override
	public Long count() {
		return productDao.getCount();
	}

	@Override
	public List<Product> findByConditions(Map<String, String> map, int page, int pageSize) throws Exception {
		return productDao.findByConditions(map, page, pageSize);
	}

	@Override
	public Long getCountByConditions(Map<String, String> map) {
		return productDao.getCountByConditions(map);
	}

	@Override
	public Product getProductById(String id) {
		return productDao.getProductById(id) ;
	}

	@Override
	public ProductMenuVo getProductMenuVo(String id) {
		ProductMenuVo resp = new ProductMenuVo() ;
		Product product = productDao.getProductById(id) ;
		resp.setCode(product.getPid());
		resp.setDescribe(product.getTheme());
		resp.setName(product.getpName());
		resp.setType("自由行");
		resp.setNature(product.getNature());
		resp.setPid(product.getId().toString());
		resp.setProgress(product.getProgress() == null ? 0 : product.getProgress());
		return resp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> search(ProductSearchCriteria searchCriteria) {
		Map<String,Object>  resultMap = new HashMap<String,Object>();
		Map<String,String> searchMap = new HashMap<String,String>();
		int pageNo=searchCriteria.getPageNo();
		int pageSize =searchCriteria.getPageSize();

		searchMap =convert2serachParam(searchCriteria);

		List<Product> productList = new ArrayList<Product>();
			//查询数据
		productList =productDao.findByConditions(searchMap, pageNo, pageSize);

		List<ProductVo> productVoList = Converter.convert2ProductVoList(productList);

		resultMap.put("productVoList", productVoList);

		if(0==pageNo){
			pageNo=1;
		}
		if(0==pageSize){
			pageSize=10;
		}

		Long totalItemCount = productDao.getCountByConditions(searchMap);
		Integer totalPageCount = (int) Math.ceil(new Double(totalItemCount)/searchCriteria.getPageSize() );

		if(totalItemCount<=0){
			 totalPageCount=1;
			 pageNo=1;
		}

		resultMap.put("pageNo", pageNo);
		resultMap.put("pageSize", pageSize);

		resultMap.put("totalItemCount", totalItemCount);
		resultMap.put("totalPageCount", totalPageCount);
		return resultMap;
	}


	@SuppressWarnings("rawtypes")
	public  Map convert2serachParam(ProductSearchCriteria searchCriteria){
		Map<String,String> searchPrams = Maps.newHashMap();
		searchPrams.put("pid", searchCriteria.getPid());
		searchPrams.put("from", searchCriteria.getFrom());
		searchPrams.put("theme", searchCriteria.getTheme());
		searchPrams.put("pName", searchCriteria.getpName());
		searchPrams.put("toContinent", searchCriteria.getToContinent());
		searchPrams.put("toCountry", searchCriteria.getToCountry());
		searchPrams.put("to", searchCriteria.getTo());
		searchPrams.put("status", getStatusFromSearchCriteria(searchCriteria));

		return searchPrams;
	}

	public String getStatusFromSearchCriteria(ProductSearchCriteria searchCriteria){
		String status =	"";
		String searchStatus = searchCriteria.getStatus();
		if(StringUtils.isNotEmpty(searchStatus)){
			if(Status.RELEASED.getDesc().equals(searchStatus)){
				status	=	Status.RELEASED.toString();
			}else if(Status.NEW.getDesc().equals(searchStatus)){
				status	=	Status.NEW.toString();
			}else if(Status.OFFLINE.getDesc().equals(searchStatus)){
				status	=	Status.OFFLINE.toString();
			}else if(Status.EXPIRED.getDesc().equals(searchStatus)){
				status	=	Status.EXPIRED.toString();
			}
		}

		return status;
	}

	@Override
	public ProductCheckRespBean changeProductStatus(String id, Status status) {
		ProductCheckRespBean bean = new ProductCheckRespBean() ;
		bean.setFlag(true);
		if(status == Status.RELEASED){
			bean = checkProduct(id);
		}
		if(bean.getFlag()){
			bean.setFlag(productDao.changeProductStatus(id, status)) ;
		}
		return bean ;
	}

	@Override
	@Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED,  propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
	public Boolean deleteProductAndWishList(String id) throws Exception{
		try {
			wishListClientService.deleteWishByProductId(id);
			logger.info("删除产品关联心愿清单成功，产品id: {} ",id);
		} catch (Exception e) {
			logger.info("删除产品关联心愿清单失败，产品id: {} "+e,id);
			throw new Exception("心愿清单删除失败 id: %s "+id);
		}
		return productDao.deleteProduct(id) ;
	}

	@Override
	public ProductCheckRespBean checkProduct(String id) {
		Product product = productDao.getProductById(id) ;
		ProductCheckRespBean bean = new ProductCheckRespBean() ;
		bean.setFlag(true);
		StringBuffer msg = new StringBuffer() ;
		if(product.isContainHotel()){
			if(!CollectionUtils.isEmpty(product.getHotels())){
				if(product.getHotels().size() > product.getTripNights()){
					msg.append("酒店数量最多不得超过行程晚数;") ;
					bean.setFlag(false);
				}
			}else{
				msg.append("该产品内容包含酒店,请添加酒店信息;") ;
				bean.setFlag(false);
			}
		}
		if(product.isContainFlight()){
			if(product.getFlight() == null || CollectionUtils.isEmpty(product.getFlight().getInfos())){
				msg.append("该产品内容包含机票,请添加机票信息;") ;
				bean.setFlag(false);
			}
		}
		if(!CollectionUtils.isEmpty(product.getCalendar())){
			boolean flight_flag = true ;
			boolean hotel_flag = true ;
			boolean flag = true ;
			boolean flag2 = false ;
			Map<String, Day> calendar = product.getCalendar() ;
			DateTime now = new DateTime(new DateTime().toString("yyyy-MM-dd")) ;
			Iterator<Entry<String, Day>> iter = calendar.entrySet().iterator() ;
			StringBuffer flightErrorMsg = new StringBuffer() ;
			StringBuffer hotelErrorMsg = new StringBuffer() ;
			StringBuffer errorMsg = new StringBuffer() ;
			while(iter.hasNext()){
				Entry<String, Day> entry = iter.next() ;
				DateTime current = new DateTime(entry.getKey()) ;
				Day day = entry.getValue() ;
				if(day.getSale() != null && !day.getSale().isClosed()){
					flag2 = true ;
				}
				if((now.isBefore(current) || now.isEqual(current)) && day != null && day.getSale() != null && !day.getSale().isClosed()){
					if(day.getCost() == null){
						errorMsg.append(day.getDay() + ",") ;
						flag = false ;
					}else{
						if(product.isContainFlight() && !day.getCost().isContainFlightCost(product)){
							flightErrorMsg.append(day.getDay() + ",") ;
							flight_flag = false ;
						}
						if(product.isContainHotel() && !day.getCost().isContainHotelCost(product)){
							hotelErrorMsg.append(day.getDay() + ",") ;
							hotel_flag = false ;
						}
					}
				}
			}
			if(!flag){
				msg = new StringBuffer(errorMsg.substring(0, errorMsg.length() - 1)) ;
				msg.append("请添加对应成本信息;") ;
				bean.setFlag(false);
			}
			if(!flight_flag){
				msg = new StringBuffer(flightErrorMsg.substring(0, flightErrorMsg.length() - 1)) ;
				msg.append("请添加对应机票成本信息;") ;
				bean.setFlag(false);
			}
			if(!hotel_flag){
				msg = new StringBuffer(hotelErrorMsg.substring(0, hotelErrorMsg.length() - 1)) ;
				msg.append("请添加对应酒店成本信息;") ;
				bean.setFlag(false);
			}
			if(product.isContainOther() && (product.getOtherCost() == null || StringUtils.isEmpty(product.getOtherSupplier()))){
				msg.append("请添加对应其他成本信息;") ;
				bean.setFlag(false);
			}
			if(product.isContainWifi() && (product.getWifiCost() == null || StringUtils.isEmpty(product.getWifiSupplier()))){
				msg.append("请添加对应WIFI成本信息;") ;
				bean.setFlag(false);
			}
			if(product.isContainZenbook() && (product.getZenbookCost() == null || StringUtils.isEmpty(product.getZenbookSupplier()))){
				msg.append("请添加对应真旅本子成本信息;") ;
				bean.setFlag(false);
			}
			if(product.isContainShuttle() && (product.getShuttleCost() == null || StringUtils.isEmpty(product.getShuttleSupplier()))){
				msg.append("请添加对应接送机成本信息;") ;
				bean.setFlag(false);
			}
			if(!flag2){
				msg.append("请添加至少一天可用(日期在今日之后且未关闭)的售价信息;") ;
				bean.setFlag(false);
			}
		}else{
			msg.append("请添加至少一天的成本信息") ;
			bean.setFlag(false);
		}
		if(!CollectionUtils.isEmpty(product.getRecommendTrips())){
			if((product.getRecommendTrips().size() - 1) != product.getTripDays()){
				msg.append("推荐行程天数与行程天数需保持一致") ;
				bean.setFlag(false);
			}
		}else{
			msg.append("请添加至少一天的推荐行程") ;
			bean.setFlag(false);
		}
		bean.setMsg(msg.toString());
		return bean ;
	}

	@Override
	public Boolean updateLowestPrice(String id) {
		Product product = productDao.getProductById(id) ;
		//sale == null 再删除的情况下发生
		DateTime now = DateTime.parse(DateTime.now().toString("yyyy-MM-dd")) ;
		//其实只有等于
		Double minAdultPrice = -1D ;
		Iterator<Entry<String, Day>> iter = product.getCalendar().entrySet().iterator() ;
		while(iter.hasNext()){
			Entry<String, Day> entry = iter.next() ;
			Day oday = entry.getValue() ;
			DateTime effectDay = DateTime.parse(oday.getDay()) ;
			if(now.isBefore(effectDay) || now.isEqual(effectDay)){
				if(oday.getSale() != null && !oday.getSale().isClosed() && oday.getSale().getAdultPrice() != null){
					if(minAdultPrice == -1){
						minAdultPrice = oday.getSale().getAdultPrice() ;
					}else{
						if(oday.getSale().getAdultPrice() < minAdultPrice){
							minAdultPrice = oday.getSale().getAdultPrice() ;
						}
					}
				}
			}
		}
		return productDao.updateLowestPrice(id, minAdultPrice == -1 ? null : minAdultPrice, product.getLowestPrice()) ;
	}
	
	@Override
	public List<String> getAllProductIds() throws Exception {
		return null;
	}

	@Override
	public Boolean isProductExistByCode(String productCode){
		Map<String,String> paramsMap = new HashMap<>();
		paramsMap.put("pid", productCode);
		return productDao.findByConditions(paramsMap, 1, 1).size() > 0 ;
	}

	@Override
	public Boolean isHotelUsedByProduct(String hotelId) {
		Boolean isHotelUsed = false;
		List<Product> products = productDao.getAllProducts();
		for(Product prod : products){
			if( prod.getHotels() != null ){
				for(Hotel hotel : prod.getHotels()){
					if( hotel.getId().equals(hotelId) ){
						isHotelUsed = true;
						break;
					}
				}
			}
		}
		return isHotelUsed;
	}


}
