package com.ztravel.product.weixin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
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
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.common.enums.PieceType;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.front.book.controller.ProductBookController;
import com.ztravel.product.front.book.validate.ProductBookValidate;
import com.ztravel.product.weixin.service.IWxProductBookService;
import com.ztravel.product.weixin.vo.PriceCretria;
import com.ztravel.resue.order.validate.DetailToOrderValidate;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.product.entity.ProductBookVo;

/**
 *
 * @author zhoubo
 *
 */

@Controller
@RequestMapping("/book/weixin/")
public class WxProductBookController {

	@Resource
	IWxProductBookService wxProductBookServiceImpl;

	@Resource
	private ICouponClientService couponClientService ;

	private static Logger LOGGER = RequestIdentityLogger.getLogger(ProductBookController.class);

	/**
	 * 套餐预订
	 * @param packageId
	 * @param bookDate
	 * @param productId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tobook/{packageId}/{bookDate}/{productId}")
    public String packageToBookPage(@PathVariable String packageId,@PathVariable String bookDate,@PathVariable String productId, Model model) throws Exception{
        couponClientService.grantCoupon(SSOUtil.getMemberId());
        DetailToOrderCriteria criteria = buildPackageCriteria(bookDate,productId,packageId);
        LOGGER.info("预订的产品信息[{}]",TZBeanUtils.describe(criteria));
        Boolean isLogin = wxProductBookServiceImpl.getLoginIngo();
        ProductBookVo productBookInfo = wxProductBookServiceImpl.product2BookVo(criteria);
        model.addAttribute("productBookInfo", productBookInfo);
        model.addAttribute("isLogin", isLogin);
        return "product/weixin/book/bookPage";
    }

	/**
	 * 非套餐预订
	 * @param adultNum
	 * @param childNum
	 * @param bookDate
	 * @param productId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/tobook/{adultNum}/{childNum}/{bookDate}/{productId}/{costPriceId}/{productNature}")
	public String toBookPage(@PathVariable Integer adultNum,@PathVariable Integer childNum,@PathVariable String bookDate,@PathVariable String productId,@PathVariable String costPriceId,@PathVariable String productNature, Model model) throws Exception{
		couponClientService.grantCoupon(SSOUtil.getMemberId());
		DetailToOrderCriteria criteria = buildCriteria(adultNum,childNum,bookDate,productId,costPriceId,productNature);
		LOGGER.info("预订的产品信息[{}]",TZBeanUtils.describe(criteria));
		Assert.isTrue(wxProductBookServiceImpl.isReleased(criteria.getProductId(), criteria.getProductNature()), "产品["+productId+"非发布状态]");
		DetailToOrderValidate.validate(criteria);
		Boolean isLogin = wxProductBookServiceImpl.getLoginIngo();
		ProductBookVo productBookInfo = wxProductBookServiceImpl.product2BookVo(criteria);
		model.addAttribute("productBookInfo", productBookInfo);
		model.addAttribute("isLogin", isLogin);
		return "product/weixin/book/bookPage";
	}
	
	@RequestMapping(value="/apply", method={RequestMethod.POST})
	@ResponseBody
	public AjaxResponse apply(@RequestBody ProductBookItem productBookItem) {
		AjaxResponse applyResult = null;
		try {
			ProductBookValidate.validate(productBookItem);// 参数校验
			Assert.isTrue(wxProductBookServiceImpl.isReleased(productBookItem.getProductId(), productBookItem.getNature()), "产品["+productBookItem.getProductId()+"非发布状态]");
			applyResult = wxProductBookServiceImpl.applyOrder(productBookItem);
		} catch (ZtrBizException ze) {
			LOGGER.error(TZMarkers.p2, "建单失败", ze);
			if (ze.getRetCode().equals(ProductBookConstans.PRODUCT_STOCK_NOT_ENOUGH)) {// 库存不足
				AjaxResponse applyOrderResult = new AjaxResponse();
				applyOrderResult.setRes_code(ProductBookConstans.PRODUCT_STOCK_NOT_ENOUGH);
				return applyOrderResult;
			} else if (ze.getRetCode().equals(
				ActivityConstants.COUPONUSED_TO_APPLY_ORDER_FAILED)) {AjaxResponse applyOrderResult = new AjaxResponse();
				applyOrderResult.setRes_code(ActivityConstants.COUPONUSED_TO_APPLY_ORDER_FAILED);
				applyOrderResult.setRes_msg("代金券已使用,请刷新页面");
				return applyOrderResult;
			}
		} catch (Exception e) {
			LOGGER.error(TZMarkers.p2, "提交订单异常", e);
			applyResult = AjaxResponse.instance(ProductBookConstans.APPLY_ORDER_FAILURE, "");
		}
		return applyResult;
	}

	/**
	 * 产品售完时，跳转到指定提示页面
	 * */
	@RequestMapping(value = "/noproduct/{productNo}")
	public ModelAndView toNoProductPage(@PathVariable String productNo) {
		ModelAndView view = new ModelAndView();
		view.addObject("productNo", productNo);
		view.setViewName("product/front/book/productOver");
		return view;
	}

	@RequestMapping(value = "/travelist/{travellerType}")
	@ResponseBody
	public JSONObject getTravelList(@PathVariable String travellerType) {
		JSONObject jsonObj = new JSONObject();
		List<TravelerEntity> travellers = wxProductBookServiceImpl.getTravelList(travellerType);
		if (null != travellers) {
			jsonObj.put("selectResult", true);
		} else {
			jsonObj.put("selectResult", false);
		}
		jsonObj.put("travellers", travellers);
		return jsonObj;
	}

	/**
	 * 获取联系人信息
	 * */
	@RequestMapping(value = "/getContactors", method = RequestMethod.POST)
    @ResponseBody
	public JSONObject getContactor() {
	    JSONObject jsonObj = new JSONObject();
		try {
			if (wxProductBookServiceImpl.checkIsActive()) {// 未挂起用户
				Boolean login = wxProductBookServiceImpl.getLoginIngo();
				if (login) {
				    List<OrderComContactor> contactors = wxProductBookServiceImpl.getContactors();
			        jsonObj.put("contactors", contactors);
				}
			}
		} catch (Exception e) {
			LOGGER.error("微信建單查询联系人信息出现异常", e);
		}
		return jsonObj;
	}

	/**
	 * 刷新可选代金券
	 * */
	@RequestMapping(value = "/refreshCoupons", method = RequestMethod.POST)
	public String refreshCoupons(@RequestBody DetailToOrderCriteria criteria,Model model) {
		try {
			List<CouponClientEntity> couponList = null;
			if (wxProductBookServiceImpl.checkIsActive()) {// 未挂起用户
				Boolean login = wxProductBookServiceImpl.getLoginIngo();
				if (login) {
					couponList = wxProductBookServiceImpl.getAllCoupons(criteria.getProductId(), criteria.getBookDate(), criteria.getPackageId(), criteria.getAdultNum(), criteria.getChildNum(),criteria.getProductNature(),criteria.getCostPriceId());
					couponList = wxProductBookServiceImpl.couponsSort(couponList);
				}
			}
			model.addAttribute("couponList", couponList);
		} catch (Exception e) {
			LOGGER.error("微信建單查询可用代金券信息出现异常", e);
		}
		return "product/weixin/book/coupon";
	}

	@RequestMapping(value = "/getCoupons")
	@ResponseBody
	public JSONObject getCoupons(@RequestBody DetailToOrderCriteria criteria) {
		JSONObject jsonObj = new JSONObject();
		try {
			List<CouponClientEntity> couponList = null;
			if (wxProductBookServiceImpl.checkIsActive()) {// 未挂起用户
				Boolean login = wxProductBookServiceImpl.getLoginIngo();
				if (login) {
					couponList = wxProductBookServiceImpl.getAllCoupons(criteria.getProductId(), criteria.getBookDate(), criteria.getPackageId(), criteria.getAdultNum(), criteria.getChildNum(),criteria.getProductNature(),criteria.getCostPriceId());
					couponList = wxProductBookServiceImpl.couponsSort(couponList);
				}
			jsonObj.put("couponList", couponList);
			jsonObj.put("selectResult", true);
			}
		} catch (Exception e) {
			jsonObj.put("selectResult", false);
			LOGGER.error("微信建單查询可用代金券信息出现异常", e);
		}
		return jsonObj;
	}

	/**
	 * 获取订单价格信息
	 * */
	@RequestMapping(value = "/getPriceInfo", method = RequestMethod.POST)
	public String getPriceInfo(@RequestBody PriceCretria criteria,Model model) {
		PriceCretria criteriaInfo = wxProductBookServiceImpl.getOrderPriceInfo(criteria);
		model.addAttribute("priceInfo", criteriaInfo);
		return "product/weixin/book/price";
	}

	public DetailToOrderCriteria buildPackageCriteria(String bookDate,String productId, String packageId) throws Exception{
		DetailToOrderCriteria criteria = new DetailToOrderCriteria();
		String reg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//日期正则表达
		if(StringUtils.isBlank(bookDate) || !bookDate.matches(reg)){
			LOGGER.error("微信端预订产品日期为[{}]",bookDate);
			throw ZtrBizException.instance("", "预订日期不符合yyyy-mm-dd格式");
		}
		if(StringUtils.isBlank(productId)){
			LOGGER.error("微信端预订产品ID为[{}]",productId);
			throw ZtrBizException.instance("", "预订产品Id为空");
		}
		if(StringUtils.isBlank(packageId)){
            LOGGER.error("微信端预订产品的套餐ID为[{}]",packageId);
            throw ZtrBizException.instance("", "预订产品的套餐Id为空");
        }
		criteria.setBookDate(bookDate);
		criteria.setProductId(productId);
		criteria.setPackageId(packageId);
		return criteria;
	}

	public DetailToOrderCriteria buildCriteria(Integer adultNum,Integer childNum,String bookDate,String productId,String costPriceId,String productNature) throws Exception{

 		DetailToOrderCriteria criteria = new DetailToOrderCriteria();
 		String reg = "[0-9]{4}-[0-9]{2}-[0-9]{2}";//日期正则表达
		if(adultNum <= 0 || childNum < 0){
			LOGGER.error("成人数[{}],儿童数[{}]",adultNum,childNum);
			throw ZtrBizException.instance("", "成人数或者儿童数小于0");
		}
 		if(StringUtils.isBlank(bookDate) || !bookDate.matches(reg)){
 			LOGGER.error("微信端预订产品日期为[{}]",bookDate);
 			throw ZtrBizException.instance("", "预订日期不符合yyyy-mm-dd格式");
 		}
 		if(StringUtils.isBlank(productId)){
			LOGGER.error("微信端预订产品ID为[{}]",productId);
			throw ZtrBizException.instance("", "预订产品Id为空");//
		}
 		if(StringUtils.isBlank(productNature)){
			LOGGER.error("产品类型为[{}]",productId);
			throw ZtrBizException.instance("", "预订产品类型为空");//
		}
 		if(PieceType.VISA.name().equals(productNature) 
 				|| PieceType.HOTELUP.name().equals(productNature)
 				|| PieceType.INTELTAXI.name().equals(productNature)
 				|| PieceType.LOCAL.name().equals(productNature)
 				|| PieceType.TICKET.name().equals(productNature)
 				|| PieceType.TRAFFIC.name().equals(productNature)
 				|| PieceType.WIFI.name().equals(productNature)
 				|| PieceType.CHARTER.name().equals(productNature)){
 			if(StringUtils.isBlank(costPriceId)){
 				LOGGER.error("微信端预订产品价格ID为[{}]",costPriceId);
 				throw ZtrBizException.instance("", "微信端预订产品价格ID为空");//
 			}
 		}

		criteria.setAdultNum(adultNum);
		criteria.setChildNum(childNum);

 		criteria.setBookDate(bookDate);
 		criteria.setProductId(productId);
 		criteria.setProductNature(productNature);
 		criteria.setCostPriceId(costPriceId);

 		return criteria;
 	}

}
