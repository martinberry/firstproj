package com.ztravel.product.back.freetravel.controller;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.common.service.ProductDetailService;
import com.ztravel.product.front.wo.CalendarDataWo;
import com.ztravel.product.front.wo.ProductWo;

/**
 * 前台产品详情
 * @author junhui.xu
 *
 */
@Controller
@RequestMapping("/product/back")
public class ProductPrevDetailController {

	private static Logger logger = RequestIdentityLogger.getLogger(ProductPrevDetailController.class);
	@Resource
	private ProductDetailService productDetailServiceImpl ;

	@RequestMapping(value = "/prevDetail/{productId}", method = RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable("productId") String productId, Model model) throws Exception {
		ProductWo  product = new ProductWo();
		if(StringUtils.isBlank(productId)){
	    	logger.error("failed to get productId from list");
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001) ;
	    }
	    try {
	    	product = productDetailServiceImpl.getProductById(productId);
	    }catch (ZtrBizException ze) {
	    	throw ze ;
		}catch (Exception e) {
	    	logger.error("failed to get product", e);
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002) ;
		}

		model.addAttribute("product", product);
		model.addAttribute("isPreview", true);//使页面不加载member
		return new ModelAndView("product/back/prevDetail/detail_main") ;
	}



	@RequestMapping(value = "/prevDetail/getCalendarData/{productId}", method = RequestMethod.POST)
	@ResponseBody
	public CalendarDataWo getCalendarData(@PathVariable("productId") String productId) throws Exception {
		if(StringUtils.isBlank(productId)){
	    	logger.error("failed to get productId from list");
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001) ;
	    }
		CalendarDataWo data = new CalendarDataWo();
	    try {
	    	data = productDetailServiceImpl.getCalDataById(productId);
	    }catch (ZtrBizException ze) {
	    	throw ze ;
		}catch (Exception e) {
	    	logger.error("failed to get product", e);
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002) ;
		}
		return data;
	}

}
