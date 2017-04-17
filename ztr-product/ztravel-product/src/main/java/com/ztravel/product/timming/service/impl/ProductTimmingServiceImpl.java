package com.ztravel.product.timming.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.product.back.freetravel.entity.Day;
import com.ztravel.product.back.freetravel.entity.Product;
import com.ztravel.product.timming.dao.IProductTimmingDao;
import com.ztravel.product.timming.service.IProductTimmingService;

@Service
public class ProductTimmingServiceImpl implements IProductTimmingService {

	private final Logger logger = RequestIdentityLogger.getLogger(ProductTimmingServiceImpl.class);

	@Resource
	private IProductTimmingDao productTimmingDaoImpl;

	@Override
	public void setAutoExpired() throws Exception {

		StopWatch watch = new StopWatch();

		watch.start("获取上线的产品");
		List<Product> allProducts = productTimmingDaoImpl.getAllProduct();
		watch.stop();

		watch.start("获取需要设置为过期的产品id");
		List<ObjectId> ids = new LinkedList<>();
		if(! allProducts.isEmpty()){
			ids = getExpiredIds(allProducts);
		}
		watch.stop();

		watch.start("更新产品状态为过期");
		if(! ids.isEmpty()){
			logger.info("需要设置为过期的产品为" + StringUtils.join(ids, ","));
			productTimmingDaoImpl.updateProductStatus(ids);
		}
		watch.stop();

		logger.info("本次定时任务执行情况" + watch.prettyPrint());

	}

	/**
	 * 获取需要下线的产品id
	 * @param allProducts
	 * @return
	 */
	private List<ObjectId> getExpiredIds(List<Product> allProducts) {

		List<ObjectId> ids = new LinkedList<ObjectId>();
		DateTime now = new DateTime() ;

		for(Product product : allProducts) {

			if(null == product.getCalendar() || product.getCalendar().isEmpty()) {
				logger.info("编号为--"+ product.getId().toString() + "--的产品未设置时间价格,不处理");
				continue;
			}

			//判断产品是否过期
			if(isExpired(product.getCalendar(), now)){
				ids.add(product.getId());
			}

		}

		return ids;
	}

	private Boolean isExpired(Map<String, Day> calendar, DateTime now) {

		Integer year = now.getYear() ;
		Integer month = now.getMonthOfYear() ;
		Integer day = now.getDayOfMonth();

		for(String key : calendar.keySet()){
			DateTime date = DateTime.parse(key) ;
			if(date.getYear() > year){
				return false ;
			}else if(date.getYear() == year && date.getMonthOfYear() > month){
				return false ;
			}else if(date.getYear() == year && date.getMonthOfYear() == month && date.getDayOfMonth() >= day){
				return false ;
			}
		}
		return true ;
	}

}
