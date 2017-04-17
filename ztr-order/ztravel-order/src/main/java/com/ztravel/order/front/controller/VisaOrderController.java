package com.ztravel.order.front.controller;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.areasearch.service.IAddressService;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.order.front.service.IOrderService;
import com.ztravel.order.front.validate.OrderValidation;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.service.IProductDetailService;
import com.ztravel.reuse.order.converter.OrderReuseConverter;
import com.ztravel.reuse.order.entity.OrderDetailWo;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
import com.ztravel.reuse.order.service.IOrderCommentReuseService;
import com.ztravel.reuse.order.service.IOrderContactorReuseService;
import com.ztravel.reuse.order.service.IOrderPassengerReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;
import com.ztravel.sraech.client.service.IAutoComplete;

@Controller
@RequestMapping("/visaorder/front")
public class VisaOrderController {

	private static Logger logger = LoggerFactory.getLogger(VisaOrderController.class);

	@Resource(name="tThriftAutoComplete")
	IAutoComplete autoComplete;

	@Resource
	IOrderService  orderService;

	@Resource
	IAddressService  addressServiceImpl;

	@Resource
	IProductDetailService productClientDetailServiceImpl;

	@Resource
	ICouponService couponService;
	
	@Resource
	IOrderReuseService orderReuseService;
	
	@Resource
	private IOrderContactorReuseService orderContactorReuseService;
	
	@Resource
	private IOrderPassengerReuseService orderPassengerReuseService;
	
	@Resource
	private IOrderCommentReuseService orderCommentReuseService;
	
	@Resource
	private ICommonOrderReuseService commonOrderReuseService;


	@RequestMapping(value="/detail/{orderId}", method = RequestMethod.GET)
	 public ModelAndView  getOrderDetail(@PathVariable String orderId, Model model) {

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

		if(memberSessionBean == null || StringUtils.isBlank(memberSessionBean.getMemberId())
				|| memberSessionBean.getIsLogin() == false || StringUtils.isBlank(memberSessionBean.getMid())){
			throw ZtrBizException.instance(Const.FF_MEMB_CODE_1001, Const.FF_MEMB_REASON_1001) ;
		}

	    try{

		    OrderDetailWo orderDetail =  orderService.selectOrderById(orderId, memberSessionBean.getMid());
			CouponItemBean couponItem = new CouponItemBean();
			if(StringUtils.isNotBlank(orderDetail.getPrice().getCouponId())){
				couponItem = couponService.getCouponItem(orderDetail.getPrice().getCouponId());
			}

			OrderReuseConverter.setOrderProductAndCoupon(couponItem, orderDetail);

			//订单是否可评价
			Boolean canComment = isOrderCanComment(orderId, orderDetail.getProduct().getBackDate());
			orderDetail.getOrder().setCanComment(canComment);
			if(orderDetail.getPrice() != null){
				//int priceRowSpan = getPriceRowSpanByPrice(orderDetail.getPrice());
				int priceRowSpan=0;
				if(orderDetail.getPrice().getChildNum()!=0){
					priceRowSpan=2;
				}else{
					priceRowSpan=1;
				}
				model.addAttribute("priceRowSpan", priceRowSpan);
			}
			
		    model.addAttribute("orderDetail", orderDetail);

	    }catch(ZtrBizException ze){
	    	logger.error("this order is not the login memberId", ze);
	    	throw ze ;
	    }catch(SQLException ex){
	    	logger.error("failed to get order detail from DB", ex);
	    	throw ZtrBizException.instance(Const.FF_ORDE_CODE_1003, Const.FF_ORDE_REASON_1003) ;
	    }catch(Exception e){
	    	logger.error("failed to get order detail by orderId", e);
	    	throw ZtrBizException.instance(Const.FF_ORDE_CODE_1004, Const.FF_ORDE_REASON_1004) ;
	    }

	    return new ModelAndView("/order/front/orderDetail/visadetailMain") ;
	}
	


	@RequestMapping(value = "/loadProvince", method = RequestMethod.POST)
	public ModelAndView loadProvince(Model model){

		List<ProvCityArea> provinceList = addressServiceImpl.getAddressByNameAndLevel(null, "1");
		model.addAttribute("provinceList", provinceList);
		return new ModelAndView("/order/front/orderDetail/provinceDropDownMenu") ;
	}

	@RequestMapping(value = "/loadCity", method = RequestMethod.POST)
	public ModelAndView loadCity(@RequestBody String provinceName, Model model){

		if(StringUtils.isNotBlank(provinceName)){
			List<ProvCityArea> cityList = addressServiceImpl.getAddressByNameAndLevel(provinceName, "2");
			model.addAttribute("cityList", cityList);
		}
		return new ModelAndView("/order/front/orderDetail/cityDropDownMenu") ;
	}

	@RequestMapping(value = "/loadCounty", method = RequestMethod.POST)
	public ModelAndView loadCounty(@RequestBody String cityName, Model model){

		if(StringUtils.isNotBlank(cityName)){
			List<ProvCityArea> countyList = addressServiceImpl.getAddressByNameAndLevel(cityName, "3");
			model.addAttribute("countyList", countyList);
		}
		return new ModelAndView("/order/front/orderDetail/countyDropDownMenu") ;
	}

	@RequestMapping(value="/contactor/update", method = RequestMethod.POST)
	@ResponseBody
	 public AjaxResponse updateOrderContactor(@RequestBody OrderContactor contactor) {

		try{

			if(contactor == null || !OrderValidation.validateOrderContactor(contactor)){
				return AjaxResponse.instance(Const.FF_ORDE_CODE_1005, Const.FF_ORDE_REASON_1005);
			}
			orderContactorReuseService.updateOrderContactor(contactor, SSOUtil.getMemberId());
	    }catch(SQLException ex){
	    	logger.error("failed to update orderContactor from DB", ex);
	    	return AjaxResponse.instance(Const.FF_ORDE_CODE_1006, Const.FF_ORDE_REASON_1006);
	    }catch(Exception e){
	    	logger.error("failed to update orderContactor", e);
	    	return AjaxResponse.instance(Const.FF_ORDE_CODE_1007, Const.FF_ORDE_REASON_1007);
	    }

		return AjaxResponse.instance(Const.SF_ORDE_CODE_1001, Const.SUCCESS);

	}

	@RequestMapping(value="/contactor/list", method = RequestMethod.POST)
	 public ModelAndView getOrderContactInfo(@RequestBody String orderId, Model model) {

		try{

			OrderContactor contactor = orderContactorReuseService.getOrderContactor(orderId);
			model.addAttribute("contactor", contactor);

	    }catch(SQLException ex){
	    	logger.error("failed to get orderContactor from DB", ex);
	    	throw ZtrBizException.instance(Const.FF_ORDE_CODE_1008, Const.FF_ORDE_REASON_1008) ;
	    }catch(Exception e){
	    	logger.error("failed to get orderContactor", e);
	    	throw ZtrBizException.instance(Const.FF_ORDE_CODE_1009, Const.FF_ORDE_REASON_1009) ;
	    }

		return new ModelAndView("/order/front/orderDetail/contactInfoTab") ;

	}


	@RequestMapping(value="/countryAutoComplete", method = RequestMethod.POST)
	@ResponseBody
	public String[] countryAutoComplete(String query){
		 try {
	         	List<String> result = autoComplete.countryAutoComplete(query, 5);
				@SuppressWarnings("rawtypes")
				Map map = new HashMap<String,String>();
	         	int size = result.size();
	         	String[] countryArr =new String[size];
	         	for(int i=0;i<result.size();i++){
	         		map =  JSON.parseObject(result.get(i));
	         		countryArr[i]=(String) map.get("nameCn");
	         	}
	         	return countryArr;
			}catch (Exception e) {
	            logger.error("Failed to country autoComplete", e);
			}
		return null;
	}

	

    /**
     * 判断订单是否可评价
     * @param orderId
     * @param backDate 返程日期
     * @return
     * @throws Exception
     */
    private Boolean isOrderCanComment(String orderId, String strBackDate) throws Exception{
    	//是否已评价
    	Boolean isCommented = orderCommentReuseService.isOrderAlreadyCommented(orderId);
    	//是否在订单完成的44小时以后
		Boolean hasPass44Hours;
		DateTime backDate = DateTimeUtil.getEndDate(strBackDate); //返程当天24:00,订单状态改为已完成
		DateTime commentOpenTime = backDate.plusHours(44);  //评价入口开放时间为返程日期后第二天20:00
		if( Minutes.minutesBetween(commentOpenTime, DateTime.now()).getMinutes() >= 0 )
			hasPass44Hours = true;
		else
			hasPass44Hours = false;
		return hasPass44Hours && !isCommented;
    }

}
