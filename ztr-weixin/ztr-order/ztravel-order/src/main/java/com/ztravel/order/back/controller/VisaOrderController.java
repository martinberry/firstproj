package com.ztravel.order.back.controller;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.order.back.criteria.VisaOrderSearchCriteria;
import com.ztravel.order.back.service.IOrderService;
import com.ztravel.order.back.service.IVisaOrderService;
import com.ztravel.order.back.vo.OrderContactMessageVO;
import com.ztravel.order.back.vo.OrderDetailVO;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.order.po.MaterialContactor;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.reuse.order.service.IOrderContactorReuseService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

@Controller
@RequestMapping("/visa")
public class VisaOrderController {

	@Resource
    private IVisaOrderService visaorderServiceImpl;
	
	@Resource
	private IOrderReuseService orderReuseService;
	
	@Resource
	private IOrderService orderServiceImpl;
	
	@Resource
	private IOrderLogReuseService orderLogReuseService;
	
	@Resource
	private IOrderContactorReuseService orderContactorReuseService;
	

	private static Logger LOGGER  = RequestIdentityLogger.getLogger(VisaOrderController.class);
	
	private final RedisClient redisClient = RedisClient.getInstance();
	
	@RequestMapping("/list")
	public String showOrderListPage(){
		return "order/back/visa/visaList";                            
	}
	
	//签证订单详情
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String searchOrders(@RequestBody VisaOrderSearchCriteria criteria, Model model){
		//搜索条件格式校验，若校验失败，返回空列表
		ValidResult result = BasicValidator.valid(criteria);
		if( !result.isSuccess() ){
			return "order/back/visa/visaListData";
		}
		List<OrderListVO> visaOrderList = null;
		int totalNum = 0;
		try{
			visaOrderList = visaorderServiceImpl.searchVisaOrder(criteria);
			totalNum = visaorderServiceImpl.countVisaOrders(criteria);
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("visaOrderList", visaOrderList);
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
		return "order/back/visa/visaListData";                //刷新页面数据列表
	}
	
	
	
	
	//显示详情
	@RequestMapping("/detail/{visaOrderId}")
	public String showVisaOrderDetail(@PathVariable String visaOrderId,Model model){
		try{
			OrderDetailVO orderDetailVo = orderServiceImpl.getOrderDetailByOrderId(visaOrderId);
			model.addAttribute("visaDetail",orderDetailVo);
			int priceRow=2;
//			if(orderDetailVo.getFeesDetail().getCouponName()!=null){
//				priceRow=3;
//			}else{
//				priceRow=2;
//			}
			model.addAttribute("priceRow", priceRow);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return "order/back/visa/visaDetail";
		}

	//取消订单
	@ResponseBody
	@RequestMapping("/cancelOrder")
	public AjaxResponse cancelOrder(@RequestParam String visaOrderId){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			ajaxResponse = orderServiceImpl.cancleOrder(visaOrderId);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code(OrderConstans.ORDER_CANCLE_FAILED);
		}
		return ajaxResponse;
	}
	
	
	//OP确认
	@ResponseBody
	@RequestMapping("/opConfirm")
	public AjaxResponse opConfirm(@RequestParam String visaOrderId){
		AjaxResponse ajaxResponse=AjaxResponse.instance("","");
		try{
			ajaxResponse=orderServiceImpl.confirmOrder(visaOrderId);	
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code(OrderConstans.ORDER_OP_CONFIRM_FAILED);
		}
		return ajaxResponse;
	}
	
	////添加订单备注信息
	@ResponseBody
	@RequestMapping("/saveLog")
	public AjaxResponse save(@RequestParam String visaOrderId,@RequestParam String remark){
		AjaxResponse ajaxResponse = null;
		try {
			String operatorName = redisClient.get(OperatorSidHolder.get());
			orderLogReuseService.save(visaOrderId, operatorName, "查看", remark);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SAVE_REMARKS_SUCCESS_CODE, OrderConstans.ORDER_SAVE_REMARKS_SUCCESS_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SAVE_REMARKS_FAILED_CODE, OrderConstans.ORDER_SAVE_REMARKS_FAILED_MSG);
		}
		return ajaxResponse;
	}
	
	
	////制作完成更新订单状态
	@ResponseBody
	@RequestMapping("/maked")
	public AjaxResponse maked(@RequestParam String visaOrderId){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			orderReuseService.updateStatus(ZtOrderStatus.MAKED.getCode(), ZtOrderStatus.MAKED.getCode(),visaOrderId);
		    orderReuseService.updateOperateRecord(visaOrderId,OrderOperate.MAKED.getCode());
		    String operatorName = redisClient.get(OperatorSidHolder.get());
		    orderLogReuseService.save(visaOrderId, operatorName, "制作完成", "");
			ajaxResponse.setRes_code("MAKEDSUCCESS");
		}catch(Exception e){
			ajaxResponse.setRes_code("MAKEDFAIL");
			LOGGER.error(e.getMessage(), e);
		}
		return ajaxResponse;
	
	}
	
	//材料送回时联系人信息回填
	@ResponseBody
	@RequestMapping("/materialshow")
	public String materialShowContactor(@RequestParam String visaOrderId){
		JSONObject jsonObj = new JSONObject();
		try{
			OrderContactor orderContactor=orderContactorReuseService.getOrderContactor(visaOrderId);
			String address="";
			if(!StringUtils.isEmpty(orderContactor.getProvince())){
				address+=orderContactor.getProvince();
			}
			if(!StringUtils.isEmpty(orderContactor.getCity())){
				address+=orderContactor.getCity();
			}
			if(!StringUtils.isEmpty(orderContactor.getCounty())){
				address+=orderContactor.getCounty();
			}
			if(!StringUtils.isEmpty(orderContactor.getAddress())){
				address+=orderContactor.getAddress();
			}
			orderContactor.setAddress(address);
			jsonObj.put("res_code", "SUCCESS");
			jsonObj.put("res_msg", orderContactor);
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			jsonObj.put("res_code", "FAIL");
		}
		return JSONObject.toJSONString(jsonObj);
	}
	
	//材料送回发送信息插入新联系人
	@ResponseBody
	@RequestMapping("/materialsend")
	public AjaxResponse materialSend(@RequestBody OrderContactMessageVO contactorAndMessage){
		AjaxResponse ajaxResponse=new AjaxResponse();
		try{
			String message=contactorAndMessage.getMessage();
			MaterialContactor orderContactor=new MaterialContactor();
			orderContactor.setOrderId(contactorAndMessage.getOrderId());
			orderContactor.setId(UUID.randomUUID().toString());
			orderContactor.setContactor(contactorAndMessage.getContactor());
			orderContactor.setPhone(contactorAndMessage.getPhone());
			orderContactor.setEmail(contactorAndMessage.getEmail());
			orderContactor.setAddress(contactorAndMessage.getAddress()+"（后台人员添加）");
			ajaxResponse=visaorderServiceImpl.materialSend(orderContactor, message);
			orderReuseService.updateOperateRecord(contactorAndMessage.getOrderId(), OrderOperate.MATERIALSEND.getCode());		
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			ajaxResponse.setRes_code("MaterialSendFAIL");
		}
	
		return ajaxResponse;	
	}










}
