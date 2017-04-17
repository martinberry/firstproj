package com.ztravel.product.front.book.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.front.book.service.IProductBookService;
import com.ztravel.product.front.book.validate.ProductBookValidate;
import com.ztravel.resue.order.validate.DetailToOrderValidate;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.product.entity.ProductBookVo;

/**
 * 产品预订
 * @author liuzhuo
 *
 */

@Controller
@RequestMapping("/product/book")
public class ProductBookController {

	private static Logger LOGGER = RequestIdentityLogger.getLogger(ProductBookController.class);

	@Resource
	IProductBookService productBookService;

	@Resource
	private FrontCommonService frontCommonService;

	@Resource
	private ICouponClientService couponClientService ;

	@RequestMapping(value="/tobookpage")
	public String bookView(DetailToOrderCriteria criteria, Model model,HttpServletRequest request) throws Exception {
		couponClientService.grantCoupon(SSOUtil.getMemberId());
		DetailToOrderValidate.validate(criteria);
		Assert.isTrue(productBookService.isReleased(criteria.getProductId(), criteria.getProductNature()), "产品["+criteria.getProductId()+"]非发布状态");
		Boolean login = productBookService.getLoginIngo();
		if (criteria.getPackageId() != null && "undefined".equals(criteria.getPackageId())) {
		    criteria.setPackageId(null);
		}
		ProductBookVo productBookInfo = productBookService.product2BookVo(criteria);
		model.addAttribute("productBookInfo", productBookInfo);
		model.addAttribute("login", login);
		WorkPlatFormVo wpfv = frontCommonService.getWorkPlatFormVo(request);
		model.addAttribute("wpfv", wpfv) ;
		return "product/front/book/productBook";
	}

	@RequestMapping(value="/apply", method={RequestMethod.POST})
	@ResponseBody
	public AjaxResponse apply(@RequestBody ProductBookItem productBookItem) {
		AjaxResponse applyResult = null;
		try {
			ProductBookValidate.validate(productBookItem);//参数校验
			Assert.isTrue(productBookService.isReleased(productBookItem.getProductId(), productBookItem.getNature()), "产品["+productBookItem.getProductId()+"非发布状态]");
			applyResult = productBookService.applyOrder(productBookItem);
		}catch(ZtrBizException ze){
			LOGGER.error(TZMarkers.p2, "建单失败", ze);
			if(ze.getRetCode().equals(ProductBookConstans.PRODUCT_STOCK_NOT_ENOUGH)){//库存不足
				AjaxResponse applyOrderResult = new AjaxResponse();
				applyOrderResult.setRes_code(ProductBookConstans.PRODUCT_STOCK_NOT_ENOUGH);
				return applyOrderResult;
			}else if(ze.getRetCode().equals(ActivityConstants.COUPONUSED_TO_APPLY_ORDER_FAILED)){
				AjaxResponse applyOrderResult = new AjaxResponse();
				applyOrderResult.setRes_code(ActivityConstants.COUPONUSED_TO_APPLY_ORDER_FAILED);
				applyOrderResult.setRes_msg("代金券已使用,请刷新页面");
				return applyOrderResult;
			}else if(ze.getRetCode().equals(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE)){
				AjaxResponse applyOrderResult = new AjaxResponse();
				applyOrderResult.setRes_code(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE);
				applyOrderResult.setRes_msg("旅客姓名不正确");
				return applyOrderResult;
			}
		}catch (Exception e) {
			LOGGER.error(TZMarkers.p2, "提交订单异常", e);
			applyResult = AjaxResponse.instance(ProductBookConstans.APPLY_ORDER_FAILURE, "");
		}
		return applyResult;
	}

	/**
     * 异步刷新订单预订页的价格信息
     * */
    @RequestMapping(value="/adultChildPriceInfo/{productId}/{bookDate}",method={RequestMethod.POST})
    @ResponseBody
    public Map<String,Object> getAdultChildPriceInfo(@PathVariable String productId, @PathVariable String bookDate) throws Exception{
        Map<String,Object> priceInfo = new HashMap<String,Object>();
        Double[] productPrice = productBookService.getProductPriceInfo(productId, bookDate);
        priceInfo.put("adultPrice", productPrice[0]);
        priceInfo.put("childPrice", productPrice[1]);
        priceInfo.put("singlePrice", productPrice[2]);
        return priceInfo;
    }

	/**
	 * 异步刷新订单预订页的价格信息
	 * */
	@RequestMapping(value="/packagePriceInfo/{productId}/{bookDate}/{packageId}",method={RequestMethod.POST})
	@ResponseBody
	public Map<String,Object> getPackagePriceInfo(@PathVariable String productId, @PathVariable String bookDate, @PathVariable String packageId) throws Exception{
		Map<String,Object> priceInfo = new HashMap<String,Object>();
		Double packagePrice = productBookService.getProductPackagePrice(productId, bookDate, packageId);
		priceInfo.put("packagePrice", packagePrice / 100);
		return priceInfo;
	}

	/**
	 * 产品售完时，跳转到指定提示页面
	 * */
	@RequestMapping(value="/noproduct/{productNo}")
	public ModelAndView toNoProductPage(@PathVariable String productNo){
		ModelAndView view = new ModelAndView();
		view.addObject("productNo", productNo);
		view.setViewName("product/front/book/productOver");
		return view;
	}

	@RequestMapping(value="/travelist/{travellerType}")
	@ResponseBody
	public JSONObject getTravelList(@PathVariable String travellerType){
		JSONObject jsonObj = new JSONObject();
		List<TravelerEntity> travellers = productBookService.getTravelList(travellerType);
		if(null != travellers){
			jsonObj.put("selectResult", true);
		}else{
			jsonObj.put("selectResult", false);
		}
		jsonObj.put("travellers", travellers);
		return jsonObj;
	}

	/**
	 * 刷新可选代金券
	 * */
	@RequestMapping(value="/refreshCoupons",method=RequestMethod.POST)
	public String refreshCoupons(@RequestBody DetailToOrderCriteria criteria,Model model){
		try {
			List<CouponClientEntity> couponList = null;
			if(productBookService.checkIsActive()){//未挂起用户
				Boolean login = productBookService.getLoginIngo();
				if(login){
				    if (StringUtil.isEmpty(criteria.getPackageId())) {
				        couponList = productBookService.getAllCoupons(criteria.getProductId(), criteria.getBookDate(), criteria.getAdultNum(), criteria.getChildNum(),criteria.getProductNature(),criteria.getCostPriceId());
				    } else {
				        couponList = productBookService.getAllCoupons(criteria.getProductId(), criteria.getBookDate(), criteria.getPackageId());
				    }
					couponList = productBookService.couponsSort(couponList);
				}
			}
			model.addAttribute("couponList", couponList);
		} catch (Exception e) {
			LOGGER.error("查询可用代金券信息出现异常",e);
		}
		return "product/front/book/discountDetail";
	}

	/**
	 * 获取联系人信息
	 * */
	@RequestMapping(value="/getContactor",method=RequestMethod.POST)
	public String getContactor(Model model){
		Boolean login = false;
		try {
			if(productBookService.checkIsActive()){//未挂起用户
				login = productBookService.getLoginIngo();
				ContactorInfo contactorInfo = null;
				if(login){
					contactorInfo = productBookService.getContactorInfo();
					model.addAttribute("contactorInfo", contactorInfo);
				}
			}
		} catch (Exception e) {
			LOGGER.error("查询联系人信息出现异常[{}]",e);
		}
		model.addAttribute("login", login);
		return "product/front/book/contactorInfo";
	}
	
	/**
	 * 获取常用联系人
	 * */
	@RequestMapping(value="/contactors")
	@ResponseBody
	public JSONObject getComContactors(){
		JSONObject jsonObj = new JSONObject();
		List<OrderComContactor> contactors = null;
		try {
			contactors = productBookService.getComContactors();
			jsonObj.put("selectResult", true);
		} catch (Exception e) {
			jsonObj.put("selectResult", false);
			LOGGER.error("查询常用联系人信息错误[{}]",e);
		}
		jsonObj.put("contactors", contactors);
		return jsonObj;
	}

}
