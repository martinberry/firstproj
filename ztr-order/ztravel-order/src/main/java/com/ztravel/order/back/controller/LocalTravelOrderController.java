package com.ztravel.order.back.controller;

import java.util.List;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.order.back.criteria.LocalOrderSearchCriteria;
import com.ztravel.order.back.criteria.VisaOrderSearchCriteria;
import com.ztravel.order.back.service.IOrderService;
import com.ztravel.order.back.service.IUnvisaOrderService;
import com.ztravel.order.back.vo.OrderDetailVO;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

@Controller
@RequestMapping("/localorder/travel")
public class LocalTravelOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);
	
	private final RedisClient redisClient = RedisClient.getInstance();
	
	@Resource
	private IOrderService orderServiceImpl;
	
	@Resource
	private IOrderLogReuseService orderLogReuseService;
	
	@Resource
	private IOrderReuseService orderReuseService;
	
	@Resource
	private IUnvisaOrderService unvisaorderServiceImpl;
	
	@RequestMapping("/list")
	public String showOrderListPage(){
		return "order/back/localtravel/localList";                            
	}
	
	//订单详情
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String searchOrders(@RequestBody LocalOrderSearchCriteria criteria, Model model){
		//搜索条件格式校验，若校验失败，返回空列表
		ValidResult result = BasicValidator.valid(criteria);
		if( !result.isSuccess() ){
			return "order/back/localtravel/localListData";
		}

		List<OrderListVO> localOrderList = null;
		int totalNum = 0;
		try{
			localOrderList = unvisaorderServiceImpl.searchLocalOrder(criteria);
			totalNum = unvisaorderServiceImpl.countLocalOrders(criteria);
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("localOrderList", localOrderList);
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
		return "order/back/localtravel/localListData";                //刷新页面数据列表
	}
	
	
	//显示详情
	@RequestMapping("/detail/{orderId}")
	public String showLocalOrderDetail(@PathVariable String orderId,Model model){
		try{
			OrderDetailVO localDetailVo = orderServiceImpl.getOrderDetailByOrderId(orderId);
			model.addAttribute("localDetail",localDetailVo);
			int priceRow=2;
			model.addAttribute("priceRow", priceRow);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return "order/back/localtravel/localDetail";
		}
	
    //取消订单
	@ResponseBody
	@RequestMapping("/cancelOrder")
	public AjaxResponse cancelOrder(@RequestParam String orderId){
		AjaxResponse ajaxResponse=AjaxResponse.instance("", "");
		try{
			ajaxResponse = orderServiceImpl.cancleOrder(orderId);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code(OrderConstans.ORDER_CANCLE_FAILED);
		}
		return ajaxResponse;
	}
	
	
	
	//OP确认
	@ResponseBody
	@RequestMapping("/opConfirm")
	public AjaxResponse opConfirm(@RequestParam String orderId){
		AjaxResponse ajaxResponse=AjaxResponse.instance("","");
		try{
			ajaxResponse = orderServiceImpl.confirmOrder(orderId);
			orderReuseService.updateOperateRecord(orderId, OrderOperate.LOCALCONFIRM.getCode());
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse.setRes_code(OrderConstans.ORDER_OP_CONFIRM_FAILED);
		}
		return ajaxResponse;
	}
	
	////添加订单备注信息
	@ResponseBody
	@RequestMapping("/saveLog")
	public AjaxResponse save(@RequestParam String orderId,@RequestParam String remark){
		AjaxResponse ajaxResponse = null;
		try {
			String operatorName = redisClient.get(OperatorSidHolder.get());
			orderLogReuseService.save(orderId, operatorName, "查看", remark);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SAVE_REMARKS_SUCCESS_CODE, OrderConstans.ORDER_SAVE_REMARKS_SUCCESS_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SAVE_REMARKS_FAILED_CODE, OrderConstans.ORDER_SAVE_REMARKS_FAILED_MSG);
		}
		return ajaxResponse;
	}
	
	
}
