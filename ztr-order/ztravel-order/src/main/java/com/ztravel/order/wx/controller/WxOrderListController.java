package com.ztravel.order.wx.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.order.client.service.ICommonOrderClientService;
import com.ztravel.order.po.Order;
import com.ztravel.order.wx.convertor.OrderConvert;
import com.ztravel.order.wx.service.IWxOrderListService;
import com.ztravel.order.wx.service.IWxOrderService;
import com.ztravel.order.wx.vo.OrderListVo;

@Controller
@RequestMapping("/order/weixin")
public class WxOrderListController {

	public static Integer pageSize = 10;

	@Resource
	IWxOrderListService wxOrderListServiceImpl;

	@Resource
	IWxOrderService wxOrderServiceImpl;
	
	@Resource
	ICommonOrderClientService commonOrderClientServiceImpl;

	private static Logger LOGGER = RequestIdentityLogger.getLogger(WxOrderListController.class);

	@RequestMapping(value="/list")
	public String index(Model model){
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		String mid = memberSessionBean.getMid();
		OrderListVo orderListVo = new OrderListVo();
		Map<String,String> totalParams = new OrderConvert().getCountParams(mid,null);
		Map<String,String> totalUnpayParams = new OrderConvert().getCountParams(mid,"UNPAY");
		Map<String,String> totalUnRecomParams =new OrderConvert().getCountParams(mid, "COMPLETED");
		try {
			Integer totalUnpayNum = wxOrderServiceImpl.getOrderCount(totalUnpayParams);
			Integer unpayNumConfirmOrder = commonOrderClientServiceImpl.getUnpayConfirmOrderNum(mid);
			totalUnpayNum += unpayNumConfirmOrder;
			
			Integer totalNum = wxOrderServiceImpl.getOrderCount(totalParams);
			Integer totalUnRecomNum = wxOrderServiceImpl.getUnCommentOrderCount(totalUnRecomParams);
			orderListVo.setTotalNum(totalNum);
			orderListVo.setTotalUnpayNum(totalUnpayNum);
			orderListVo.setTotalUnRecNum(totalUnRecomNum);
			orderListVo.setTotalPage((totalNum + pageSize - 1) / pageSize);
			model.addAttribute("orderListVo", orderListVo);
		} catch (Exception e) {
			LOGGER.error("", e);
		}
		return "order/weixin/orderList/list";
	}

	@RequestMapping(value="/load")
	public String loadData(Integer startNum,Model model){
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		OrderListVo orderListVo = null;
		List<Order> orderList = null;
		if(null != memberSessionBean){
			String mid = memberSessionBean.getMid();
			Map<String,Object> params = OrderConvert.getParams(mid, startNum,pageSize);
			try {
				orderList = wxOrderServiceImpl.getOrdersByMid(params);
				orderListVo = wxOrderListServiceImpl.orderList2Vo(orderList);
				orderListVo.setSuccess(true);
				orderListVo.setPageNum(startNum);
				model.addAttribute("orderListVo", orderListVo);
			} catch (Exception e) {
				OrderListVo orderListVoError = new OrderListVo();
				orderListVoError.setSuccess(false);
				LOGGER.error("微信端查询用户订单列表异常:",e);
				model.addAttribute("orderListVo", orderListVoError);
				return "order/weixin/orderList/orderTemplate";
			}
		}
		return "order/weixin/orderList/orderTemplate";
	}


	@RequestMapping(value="/noOrder")
	public String noOrder(){
		return "order/weixin/orderList/noOrder";
	}

}
