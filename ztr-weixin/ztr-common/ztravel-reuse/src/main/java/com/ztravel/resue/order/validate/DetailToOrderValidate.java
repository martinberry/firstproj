package com.ztravel.resue.order.validate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.springframework.util.Assert;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.Nature;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;

public class DetailToOrderValidate {
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(DetailToOrderValidate.class);
	
	public static void validate(DetailToOrderCriteria criteria) throws Exception{
		Assert.isTrue(null != criteria, "跳转失败,跳转信息为空");
		Assert.hasText(criteria.getProductId(), "产品ID为空,跳到预定页失败");
		Assert.hasText(criteria.getBookDate(), "预定日期为空，跳转至预定页失败");
		String reg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//日期正则表达
		Assert.isTrue(criteria.getBookDate().matches(reg), "预订日期不符合yyyy-mm-dd格式，跳转至预定页失败");
		Assert.isTrue(isAfterNow(criteria.getBookDate()),"预订日期早于当前日期，跳转至预定页失败");
		Assert.isTrue(null != criteria.getAdultNum() && criteria.getAdultNum() > 0, "成人数为空或小于等于0，跳转至预定页失败");
		if(null != criteria.getChildNum()){
			Assert.isTrue(criteria.getChildNum() >= 0, "儿童数小于0，跳转至预定页失败");
		}
		Assert.hasText(criteria.getProductNature(), "产品类型为空,跳转至预定页失败");
		if(criteria.getProductNature().trim().equals(Nature.COMBINATION.name())|| criteria.getProductNature().trim().equals(Nature.PACKAGE.name())){
			//自由行的预定限制
		}else{
			Assert.hasText(criteria.getCostPriceId(), "碎片化产品价格ID为空,跳转至预定也失败");
		}
	}
	
	
	public static Boolean isAfterNow(String bookDate){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(sdf.parse(bookDate));
		} catch (ParseException e) {
			LOGGER.error("日期比较错误[{}]", e);
			return false;
		}
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime().after(new Date());
	}
}
