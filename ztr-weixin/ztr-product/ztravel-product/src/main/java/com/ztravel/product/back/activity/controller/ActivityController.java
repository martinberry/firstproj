package com.ztravel.product.back.activity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityConstants;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.ActivityType;
import com.ztravel.common.security.RandomId;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.product.back.activity.convert.ActivityConvert;
import com.ztravel.product.back.activity.convert.CouponConvert;
import com.ztravel.product.back.activity.entity.Activity;
import com.ztravel.product.back.activity.entity.Coupon;
import com.ztravel.product.back.activity.service.IActivityService;
import com.ztravel.product.back.activity.service.IVoucherService;
import com.ztravel.product.back.activity.validate.ActivityValidate;
import com.ztravel.product.back.activity.validate.CouponValidate;
import com.ztravel.product.back.activity.vo.ActivityCriteria;
import com.ztravel.product.back.activity.vo.ActivityVo;
import com.ztravel.product.back.activity.vo.CouponVo;
import com.ztravel.product.dao.IActivityDao;


@Controller
@RequestMapping(value="/activity")
public class ActivityController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(ActivityController.class);
	@Resource
	IActivityService activityServiceImpl;

	@Resource
	CouponValidate couponValidate;

	@Resource
	private IdGeneratorUtil idGeneratorUtil;

	@Resource
	IVoucherService voucherServiceImpl;

	   @Resource
	   IActivityDao activitydao;

	@RequestMapping("/list")
	public String main(){
		return "product/back/activity/list";
	}

	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String search(@RequestBody ActivityCriteria criteria,Model model){
		LOGGER.debug("活动查询参数{}",TZBeanUtils.describe(criteria));
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			Map<String,String> params = ActivityConvert.criteriaToParams(criteria);
			result = activityServiceImpl.searchActivities(params,criteria.getPageNo(),criteria.getPageSize());
			model.addAttribute("totalItemCount", result.get("totalItemCount"));
			model.addAttribute("totalPageCount",result.get("totalPageCount"));
			model.addAttribute("pageNo", result.get("pageNo"));
			model.addAttribute("pageSize",result.get("pageSize"));
			model.addAttribute("result", result);
		} catch (Exception e) {
			result.put("errorMsg", "查询列表失败");
			LOGGER.error("查询网站活动异常",e);
		}
		return "product/back/activity/table";
	}

	@RequestMapping(value="/getCoupons",method=RequestMethod.POST)
	public String getCoupons(@RequestBody String activityId,Model model){
		try {
			Map<String,Coupon> coupons = activityServiceImpl.getCouponsByActivityId(activityId);
			List<CouponVo> couponVoList = CouponConvert.coupons2Vo(coupons);
			model.addAttribute("success", "true");
			model.addAttribute("coupons", couponVoList);
			model.addAttribute("activityId", activityId);
		} catch (Exception e) {
			model.addAttribute("success", "false");
			LOGGER.error("查询[{}]活动所有代金券失败",e);
		}
		return "product/back/activity/couponTable";
	}

	@RequestMapping(value="/add/{type}")
	public String toAdd(@PathVariable String type,Model model){
		String activityType = type.equals("customerGet") ? ActivityType.CUSTOMERGET.name() : ActivityType.SYSTEMSEND.name();
		model.addAttribute("type", activityType);
		return "product/back/activity/add";
	}

	@RequestMapping(value="/save")
	@ResponseBody
	public AjaxResponse save(@RequestBody ActivityVo activityVo){
		AjaxResponse response = new AjaxResponse();
		response.setRes_code(ActivityConstants.ACTIVITY_CREATE_FAILED);
		try {
			AjaxResponse validateResponse = ActivityValidate.activityValidate(activityVo,ActivityStatus.DRAFT.name());
			if(validateResponse.getRes_code().equals(ActivityConstants.ACTIVITY_VALIDATE_SUCCESS)){
				String  activityId = activityServiceImpl.saveActivity(activityVo);
				if(StringUtils.isNoneBlank(activityId)){
					response.setRes_code(ActivityConstants.ACTIVITY_CREATE_SUCCESS);
					response.setRes_msg(activityId);
				}
			}else{
				return validateResponse;
			}
		} catch (Exception e) {
			response.setRes_msg(ActivityConstants.ACTIVITY_CREATE_FAILED_MSG);
			LOGGER.error("活动创建/更新失败",e);
		}
		return response;
	}

	@RequestMapping(value="/edit/{activityId}")
	public String toEdit(@PathVariable String activityId,Model model){
		Activity activity = null;
		ActivityVo activityVo = null;
		try {
			activity = activityServiceImpl.getActivityById(activityId);
			activityVo = activityServiceImpl.convert2Vo(activity);
			model.addAttribute("activity", activityVo);
		} catch (Exception e) {
			LOGGER.error("获取活动{}信息失败",activityId,e);
		}
		return "product/back/activity/edit";
	}

	@RequestMapping(value="/editCoupon/{activityId}/{couponItemId}")
	@ResponseBody
	public AjaxResponse toCouponEdit(@PathVariable String activityId,@PathVariable String couponItemId){
		CouponVo couponVo = null;
		AjaxResponse response = new AjaxResponse();
		try {
			Coupon coupon = activityServiceImpl.getCoupon(activityId, couponItemId);
			couponVo = CouponConvert.coupon2Vo(coupon);
			if(null != couponVo){
				response.setRes_code(ActivityConstants.ACTIVITY_SELECTCOUPON_SUCCESS);
				response.setRes_msg(JSONObject.toJSONString(couponVo));
			}
		} catch (Exception e) {
			response.setRes_msg(ActivityConstants.ACTIVITY_SELECTCOUPON_FAILED);
			response.setRes_code(ActivityConstants.ACTIVITY_SELECTCOUPON_FAILED_MSG);
			LOGGER.error("获取活动{}的代金券{}信息失败",activityId,couponItemId,e);
		}
		return response;
	}

	@RequestMapping(value="/addCoupon")
	@ResponseBody
	public AjaxResponse addCoupon(@RequestBody CouponVo couponVo){
		List<Coupon> coupons  = new ArrayList<Coupon>();
		AjaxResponse response = null;
		try {
			AjaxResponse validateResponse = couponValidate.couponValidate(couponVo);
				if(validateResponse.getRes_code().equals(ActivityConstants.COUPON_VALIDATE_SUCCESS)){
					couponVo.setCouponId(idGeneratorUtil.getCouponId());
					couponVo.setCouponCode("J"+RandomId.randomCouponIdWithoutKeyAndSeed(Integer.parseInt(idGeneratorUtil.getCouponCode())));
					Coupon coupon  = CouponConvert.vo2Coupon(couponVo);
					LOGGER.info("添加的券信息[{}]",TZBeanUtils.describe(coupon));
					coupons.add(coupon);
						response  = activityServiceImpl.addCoupons(couponVo.getActivityId(), coupons);
				}else{
					return validateResponse;
				}


		} catch (Exception e) {
			LOGGER.error("活动{}添加代金券操作失败",couponVo.getActivityId(),e);
			response = AjaxResponse.instance(ActivityConstants.ACTIVITY_ADDCOUPON_FAILED, ActivityConstants.ACTIVITY_ADDCOUPON_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/updateCouponNum")
	@ResponseBody
	public AjaxResponse editCouponNum(@RequestBody CouponVo couponVo){
		AjaxResponse response = null;
		try {
			LOGGER.info("修改的券信息[{}]",TZBeanUtils.describe(couponVo));
			response  = activityServiceImpl.updateCouponNum(couponVo.getActivityId(), couponVo.getCouponId(), couponVo.getTotalNum(), couponVo.getUnit());
		} catch (Exception e) {
			LOGGER.error("编辑活动[{}]代金券[{}]操作失败",couponVo.getActivityId(),couponVo.getCouponCode(),e);
			response = AjaxResponse.instance(ActivityConstants.ACTIVITY_EDITCOUPON_FAILED, ActivityConstants.ACTIVITY_EDITCOUPON_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/terminateCoupon/{activityId}/{couponItemId}")
	@ResponseBody
	public AjaxResponse teminateCoupon(@PathVariable String activityId,@PathVariable String couponItemId){
		AjaxResponse response = null;
		try {
			response  = activityServiceImpl.teminateCoupon(activityId, couponItemId);
			LOGGER.info("已强行终止活动[{}]的代金券{}",activityId,couponItemId);
		} catch (Exception e) {
			LOGGER.error("强行终止活动[{}]的代金券{}操作失败",activityId,couponItemId,e);
			response = AjaxResponse.instance(ActivityConstants.COUPON_TERMINATE_FAILED, ActivityConstants.COUPON_TERMINATE_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/deleteCoupon/{activityId}/{couponItemId}")
	@ResponseBody
	public AjaxResponse deleteCoupon(@PathVariable String activityId,@PathVariable String couponItemId){
		AjaxResponse response = null;
		try {
			response  = activityServiceImpl.removeCoupon(couponItemId, activityId);
		} catch (Exception e) {
			LOGGER.error("删除活动{}的代金券{}操作失败",activityId,couponItemId,e);
			response = AjaxResponse.instance(ActivityConstants.ACTIVITY_DELETECOUPON_FAILED, ActivityConstants.ACTIVITY_DELETECOUPON_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/grantCoupon/{activityId}")
	@ResponseBody
	public AjaxResponse sendCoupon(@PathVariable String activityId,CouponVo couponVo){
		AjaxResponse response = null;
		try {
			response  = activityServiceImpl.sendCoupon(activityId);
		} catch (Exception e) {
			LOGGER.error("活动{}送券操作失败",activityId,e);
			response = AjaxResponse.instance(ActivityConstants.COUPON_SENDDING_FAILED, ActivityConstants.COUPON_SENDDING_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/effectitve")
	@ResponseBody
	public AjaxResponse effectitve(@RequestBody ActivityVo activityVo){
		AjaxResponse response = null;
		String activityId = null;
		try {
			AjaxResponse validateResponse = ActivityValidate.activityValidate(activityVo,ActivityStatus.EFFECTIVE.name());
			if(validateResponse.getRes_code().equals(ActivityConstants.ACTIVITY_VALIDATE_SUCCESS)){
				activityId = activityVo.getId();
				if(StringUtils.isBlank(activityId)){//活动新增页,点击活动生效
					activityId = activityServiceImpl.saveActivity(activityVo);
					activityVo.setId(activityId);
				}
				response  = activityServiceImpl.toEffect(activityVo);
			}else{
				return validateResponse;
			}
		} catch (Exception e) {
			LOGGER.error("活动{}的生效操作失败",activityId,e);
			response = AjaxResponse.instance(ActivityConstants.ACTIVITY_EFFEC_FAILED, ActivityConstants.ACTIVITY_EFFEC_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/terminate")
	@ResponseBody
	public AjaxResponse terminate(@RequestBody String activityId){
		AjaxResponse response = null;
		try {
			response  = activityServiceImpl.toTerminate(activityId);
		} catch (Exception e) {
			LOGGER.error("活动{}强行终止操作失败",activityId,e);
			response = AjaxResponse.instance(ActivityConstants.ACTIVITY_TERMINATE_FAILED, ActivityConstants.ACTIVITY_TERMINATE_FAILED_MSG);
		}
		return response;
	}

	@RequestMapping(value="/checkMobile")
	@ResponseBody
	public AjaxResponse checkMobile(@RequestBody String mobiles){
		AjaxResponse response = null;
		try {
			response  = activityServiceImpl.checkMobiles(mobiles);
		} catch (Exception e) {
			LOGGER.error("用户信息错误",e);
			response = AjaxResponse.instance(ActivityConstants.ACTIVITY_CONFIGURE_USER_FAILED, ActivityConstants.ACTIVITY_CONFIGURE_USER_FAILED_MSG);
		}
		return response;
	}
}
