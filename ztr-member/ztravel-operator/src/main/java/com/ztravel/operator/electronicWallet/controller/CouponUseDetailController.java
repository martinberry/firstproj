package com.ztravel.operator.electronicWallet.controller;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.enums.VoucherStatus;
import com.ztravel.common.enums.VoucherType;
import com.ztravel.common.payment.CouponCountBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.CouponItemQueryBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.operator.electronicWallet.entity.Convert2CouponUseDetail;
import com.ztravel.operator.electronicWallet.entity.CouponSearchCriteria;
import com.ztravel.operator.electronicWallet.entity.CouponUseDetail;
import com.ztravel.operator.electronicWallet.entity.DnCouponSearchCriteria;
import com.ztravel.operator.service.IDnChangeStatusService;
import com.ztravel.operator.service.IDnSearchService;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.product.client.entity.ActivityClientEntity;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.client.service.IVoucherClientService;


@Controller
@RequestMapping("/operator/couponUserDetail")
public class CouponUseDetailController {

	private static Logger logger = RequestIdentityLogger.getLogger(CouponUseDetailController.class);

	@Resource
	IActivityClientService activityClientService;

	@Resource
	private ICouponService couponService;

	@Resource
	private IVoucherClientService voucherClientServiceImpl;

	@Resource
	IMemberClientService  memberClientService;

   @Resource
	private IDnSearchService dnSearchService;

   @Resource
   private IDnChangeStatusService dnChangeStatusService;

	@Resource
	private IOrderPaymentService orderPaymentService;


	@RequestMapping("/index/{activityId}/{couponCode}/{systemSendCount}")
	public ModelAndView index(@PathVariable String activityId, @PathVariable String couponCode,	@PathVariable String systemSendCount,	Model model) {
		CouponClientEntity couponItemVo = activityClientService.getCouponById(activityId, couponCode);
		CouponCountBean couponCount = couponService.countByCode(couponCode);
		model.addAttribute("couponItemVo", couponItemVo);
		model.addAttribute("couponCount", couponCount);
		model.addAttribute("couponCode", couponCode);
		model.addAttribute("systemSendCount", couponItemVo.getTotalNum());
		model.addAttribute("activityId", activityId);
		return new ModelAndView("operator/electronicWallet/couponUserDetail_index") ;
	}


	//大宁活动代金券具体信息展示
	@RequestMapping("/dnindex/{activityId}/{couponCode}/{systemSendCount}")
	public String dncouponindex(@PathVariable String activityId, @PathVariable String couponCode,	@PathVariable String systemSendCount,	Model model){

		CouponClientEntity couponItemVo = activityClientService.getCouponById(activityId, couponCode);
		CouponCountBean couponCount=null;
		try{
			 couponCount = couponService.countByCode(couponCode);
		}
		catch(Exception e){

		}

		int totalnum=couponItemVo.getTotalNum();//coupon totalnum
		model.addAttribute("couponItemVo", couponItemVo);
		model.addAttribute("couponCount", couponCount);
		model.addAttribute("couponCode", couponCode);
		model.addAttribute("systemSendCount", totalnum);
		model.addAttribute("activityId", activityId);
		long price = couponItemVo.getPrice();
		model.addAttribute("price",price);
		boolean isActivityExpired= true;
		String isAllSystem = "yes";
		try{
			isActivityExpired = isActivityExpired(activityId);
			logger.info("isActivityExpired: [{}]", isActivityExpired);
			String  activityStatus = activityClientService.getActivityStatusById(activityId);
			model.addAttribute("activityStatus", activityStatus);
			Map<String, Object> map = Maps.newHashMap();
			map.put("voucherType", VoucherType.NORMAL);
			map.put("couponId", couponCode);
			ImmutableList<VoucherStatus> statusList = ImmutableList.of(VoucherStatus.INIT);
			map.put("statusList", statusList);
			int normalVoucherStatusCount = voucherClientServiceImpl.countByMap(map);
			if(normalVoucherStatusCount > 0){
				isAllSystem = "no";
			}
			model.addAttribute("isAllSystem", isAllSystem);
		}catch(Exception e){
			logger.error(e.toString());
		}
		model.addAttribute("isActivityExpired",isActivityExpired);
		return "operator/electronicWallet/dncouponUserDetail_index";
	}

	private boolean isActivityExpired(String activityId) throws Exception{
		DateTime endTime = activityClientService.getActivityEndTimeById(activityId);
		return endTime.getMillis() > new Date().getTime() ? false:true;
	}

		@RequestMapping(value="/dn/changeVoucherTypeInit", method=RequestMethod.POST)
		@ResponseBody
		public AjaxResponse changeVoucherTypeInit(String voucherCode,String activityId) throws Exception{
			AjaxResponse ajaxResponse = new AjaxResponse();
			logger.info("changeVoucheTypeInit  voucherCode: [{}] ,activity: [{}]", voucherCode, activityId);
			try{
				Voucher voucher = voucherClientServiceImpl.getVoucherByCode(voucherCode);
				if(voucher != null){
					VoucherStatus status = voucher.getStatus();
					if(status != null && !status.equals(VoucherStatus.INIT)){
						ajaxResponse.setRes_code("voucherRecieved");
						return ajaxResponse;
					}
				}
				ajaxResponse=dnChangeStatusService.changeVoucherTypeInit(voucherCode,activityId);
			}catch(Exception e){
				ajaxResponse.setRes_code("failed");
				ajaxResponse.setRes_msg("操作失败");
				logger.error(e.toString());
			}
			logger.info("changeVoucheTypeInit response: [{}]", TZBeanUtils.describe(ajaxResponse));
              return ajaxResponse;
		}

		@RequestMapping(value="/dn/changeVoucherTypeAll", method=RequestMethod.POST)
		@ResponseBody
		public AjaxResponse changeVoucherTypeAll(String activityId, String couponId, VoucherType voucherType) {
			AjaxResponse ajaxResponse = new AjaxResponse();
			logger.info("changeVoucheTypeAll  ,activity: [{}], couponId: [{}], voucherType : [{}]", activityId, couponId, voucherType.toString());
			try{
				ajaxResponse= dnChangeStatusService.changeVoucherTypeAll(activityId , couponId, voucherType);
			}catch(Exception e){
				ajaxResponse.setRes_code("failed");
				ajaxResponse.setRes_msg("操作失败");
				logger.error(e.toString());
			}
			logger.info("changeVoucheTypeAll response: [{}]", TZBeanUtils.describe(ajaxResponse));
              return ajaxResponse;

		}

		@RequestMapping("/changeAuditStatus")
		@ResponseBody
		public AjaxResponse changeAuditStatus(@RequestBody String voucherCode){
			AjaxResponse ajaxresponse=dnSearchService.ChangeAuditStatusrByVoucherCode(voucherCode);
			return ajaxresponse;
		}

		@RequestMapping(value="/dn/search",method=RequestMethod.POST)
		public String  dnSearch(@RequestBody DnCouponSearchCriteria criteria,	 Model model) throws Exception{
			//获取tab 名称，根据tab名称　调用不同的查询方法，　返回不同的查询页面
			String searchTab = criteria.getSearchTab();
			String dataListFtlUrl = "";
			List dataList = Lists.newArrayList();
			int totalNum =0;
			if(StringUtils.isNotEmpty(searchTab)){
				if(searchTab.equals(VoucherStatus.INIT.toString())){
					dataListFtlUrl = "operator/electronicWallet/systemLockTabdatalist";
					String activityId = criteria.getActivityId();
					if(StringUtils.isNotEmpty(activityId)){
						boolean isActivityExpired = isActivityExpired(activityId);
						logger.info("isActivityExpired: [{}]", isActivityExpired);
						model.addAttribute("isActivityExpired", isActivityExpired);
						String  activityStatus = activityClientService.getActivityStatusById(activityId);
						model.addAttribute("activityStatus", activityStatus);
					}
				}else if(searchTab.equals(VoucherStatus.RECIEVED.toString())){
					dataListFtlUrl = "operator/electronicWallet/recievedTabdatalist";
				}else if(searchTab.equals(VoucherOrderStatus.AUDITING.toString())){
					dataListFtlUrl = "operator/electronicWallet/unrefundedTabdatalist";
				}else if(searchTab.equals(VoucherOrderStatus.REFUNDED.toString())){
					dataListFtlUrl = "operator/electronicWallet/refundedTabdatalist";
				}
			}else{
				throw new Exception();
			}
			try{
				logger.info(TZBeanUtils.describe(criteria));
				dataList =  dnSearchService.searchByCriteria(criteria);
				totalNum = dnSearchService.countByCriteria(criteria);
				model.addAttribute("dataList", dataList);
			}catch(Exception e){
				logger.error(e.toString());
			}
			model.addAttribute("dataList", dataList);
			model.addAttribute("totalItemCount", totalNum);
			if( totalNum != 0 ){
				model.addAttribute("pageNo", criteria.getPageNo());
			}else{
				model.addAttribute("pageNo", 1);
			}
			model.addAttribute("pageSize",criteria.getPageSize());
			int totalPageCount=0;
			if (totalNum ==0){
				totalPageCount=1;
			}
			else{
			totalPageCount = (int) Math.ceil( (double)totalNum/criteria.getPageSize());
			}
			model.addAttribute("totalPageCount", totalPageCount);
			return dataListFtlUrl;
		}

	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String  search(@RequestBody CouponSearchCriteria couponSearchCriteria,	 Model model) throws Exception{
		List<CouponUseDetail> couponUseDetails =  Lists.newArrayList();
		CouponItemQueryBean couponItemQuery = buildCouponItemQuery(couponSearchCriteria);

		 Pagination<CouponItemBean> paginationResult = couponService.getCouponItems(couponItemQuery);
		 List<CouponItemBean> couponItems = (List<CouponItemBean>) paginationResult.getData();

		 if(null != couponItems && couponItems.size()>0){
			 CouponUseDetail  couponUseDetail = new CouponUseDetail();
			 for(CouponItemBean couponItemBean : couponItems){
				 couponUseDetail =  Convert2CouponUseDetail.couponItemBean2CouponUseDetail(couponItemBean);
				 couponUseDetail.setMid(convertMemberIdToMobile(couponItemBean.getMemberId()));
				 couponUseDetails.add(couponUseDetail);
			 }
		 }

		 long totalItemCount = paginationResult.getTotalItemCount();
		 Integer totalPageCount = (int) Math.ceil(new Double(totalItemCount)/couponSearchCriteria.getPageSize() );
		 int pageNo = couponSearchCriteria.getPageNo();

		 if( couponItems.size() == 0){
		 		pageNo = 1;
				totalItemCount = 0;
				totalPageCount=1;
		 }

 		model.addAttribute("searchList", couponUseDetails);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", couponSearchCriteria.getPageSize());
		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalPageCount", totalPageCount);

		return "operator/electronicWallet/couponUserDetail_table";
	}

	public CouponItemQueryBean  buildCouponItemQuery(CouponSearchCriteria searchCriteria) throws Exception{
		CouponItemQueryBean couponItemQuery = new CouponItemQueryBean();
		 DateTimeFormatter format = DateTimeFormat .forPattern("yyyy-MM-dd HH:mm:ss");

		String memberId = "";
		if(StringUtils.isNotBlank(searchCriteria.getMid())){
			memberId = memberClientService.getMemberIdByMid(searchCriteria.getMid());
			couponItemQuery.setMemberId(memberId);
		}

		if(StringUtils.isNotEmpty(searchCriteria.getUseTimeFrom())){
			couponItemQuery.setConditionUseDateFrom(DateTime.parse(searchCriteria.getUseTimeFrom()+" 00:00:00", format));
		}

		if(StringUtils.isNotEmpty(searchCriteria.getUseTimeTo())){

			String conditionOperateTo = searchCriteria.getUseTimeTo();
			if(null != conditionOperateTo){
				conditionOperateTo = conditionOperateTo+" 23:59:59";
			}
			couponItemQuery.setConditionUseDateTo( DateTime.parse(conditionOperateTo, format));
		}

		ImmutableList<CouponItemStatus> statusList = ImmutableList.of(CouponItemStatus.AVAILABLE,	CouponItemStatus.EXPIRED,CouponItemStatus.FROZEN,	CouponItemStatus.USED);
		couponItemQuery.setStatusList(statusList);
		couponItemQuery.setCouponCode(searchCriteria.getCouponCode());
		couponItemQuery.setPageNo(searchCriteria.getPageNo());
		couponItemQuery.setPageSize(searchCriteria.getPageSize());
		return couponItemQuery;
	}

	public String convertMemberIdToMobile(String memberId) {
		String memberInfo = "" ;
		String mid = "";
		if(StringUtils.isNotEmpty(memberId)){
			try{
				memberInfo = memberClientService.getMinMemberById(memberId);
			}catch(Exception e){
			}
			if(StringUtils.startsWith(memberInfo, "{")) {
				JSONObject json = JSONObject.parseObject(memberInfo);
				if(json.containsKey("mid")) {
					return json.getString("mid");
				}
			}
		}
		return mid;
	}

	@RequestMapping(value="/dn/passAudit", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse passAudit(String voucherCode) {
		logger.info("passAudit  ,voucherCode: [{}]", voucherCode);
		AjaxResponse ajaxResponse = new AjaxResponse();
		ajaxResponse.setRes_code("success");
		try{
			ajaxResponse = dnSearchService.ChangeAuditStatusrByVoucherCode(voucherCode);
		}catch(Exception e){
			ajaxResponse.setRes_code("failed");
			ajaxResponse.setRes_msg("操作失败");
			logger.error(e.toString());
		}
		logger.info("passAudit response: [{}]", TZBeanUtils.describe(ajaxResponse));
          return ajaxResponse;

	}

}
