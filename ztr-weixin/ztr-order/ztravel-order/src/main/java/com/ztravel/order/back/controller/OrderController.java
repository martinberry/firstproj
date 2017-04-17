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
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.order.back.criteria.OrderSearchCriteria;
import com.ztravel.order.back.service.IOrderService;
import com.ztravel.order.back.vo.OrderDetailVO;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.reuse.order.service.IOrderLogReuseService;

/**
 * @author MH
 */
@Controller
@RequestMapping("/order/freetravel")
public class OrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

	private final RedisClient redisClient = RedisClient.getInstance();

	@Resource
	private IOrderService orderServiceImpl;

	@Resource
	private IOrderLogReuseService orderLogReuseService;

	@RequestMapping("/list")
	public String showOrderListPage() {
		return "order/back/freetravel/orderList";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String searchOrders(@RequestBody OrderSearchCriteria criteria, Model model) {
		// 搜索条件格式校验，若校验失败，返回空列表
		ValidResult result = BasicValidator.valid(criteria);
		if (!result.isSuccess()) {
			return "order/back/freetravel/orderListData";
		}

		List<OrderListVO> orderList = null;
		int totalNum = 0;
		try {
			orderList = orderServiceImpl.searchOrders(criteria);
			totalNum = orderServiceImpl.countOrders(criteria);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("orderList", orderList);
		model.addAttribute("totalItemCount", totalNum);
		if (totalNum != 0) {
			model.addAttribute("pageNo", criteria.getPageNo());
		} else {
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize", criteria.getPageSize());
		int totalPageCount = (int) Math.ceil((double) totalNum / criteria.getPageSize());
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount;
		model.addAttribute("totalPageCount", totalPageCount);
		return "order/back/freetravel/orderListData";
	}

	@RequestMapping("/detail/{orderId}")
	public String gotoOrderDetailPage(@PathVariable String orderId, Model model) {
		try {
			OrderDetailVO orderDetail = orderServiceImpl.getOrderDetailByOrderId(orderId);
			model.addAttribute("orderDetail", orderDetail);
			model.addAttribute("title", "订单-" + orderDetail.getProducts().get(0).getProductTitle()); // 页面title
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "order/back/freetravel/orderDetail";
	}

	@RequestMapping("/originalOrder/{orderId}")
	public String gotoOriginalOrder(@PathVariable String orderId, Model model) {
		try {
			OrderDetailVO orderDetail = orderServiceImpl.getOriginalOrder(orderId);
			model.addAttribute("orderDetail", orderDetail);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
			throw e;
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "order/back/freetravel/originalOrder";
	}

	@RequestMapping("/cancelOrder")
	@ResponseBody
	public AjaxResponse cancelOrder(@RequestBody String orderId) {
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = orderServiceImpl.cancleOrder(orderId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_CANCLE_FAILED, "");
		}
		return ajaxResponse;
	}

	@RequestMapping("/refundOpConfirmOrder")
	@ResponseBody
	public AjaxResponse refundOpConfirmOrder(@RequestBody String orderId) {
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = orderServiceImpl.refundOpConfirmOrder(orderId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_CANCLE_FAILED, "");
		}
		return ajaxResponse;
	}

	@RequestMapping("/opConfirm")
	@ResponseBody
	public AjaxResponse opConfirm(@RequestBody String orderId) {
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = orderServiceImpl.confirmOrder(orderId);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_OP_CONFIRM_FAILED, "");
		}
		return ajaxResponse;
	}

	@RequestMapping("/sendGiftBox")
	@ResponseBody
	public AjaxResponse sendGiftBox(@RequestParam String orderId, @RequestParam String giftSendMessage) {
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = orderServiceImpl.sendGiftBox(orderId, giftSendMessage);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SEND_GIFT_FAILED, "");
		}
		return ajaxResponse;
	}

	@RequestMapping("/sendOutingNotice")
	@ResponseBody
	public AjaxResponse sendOutingNotice(@RequestParam String orderId, @RequestParam String outNoticeMessage) {
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = orderServiceImpl.sendOutNotice(orderId, outNoticeMessage);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_OUTNOTICE_FAILED, "");
		}
		return ajaxResponse;
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResponse saveRemarks(@RequestParam String orderId, @RequestParam String remarks) {
		AjaxResponse ajaxResponse = null;
		try {
			String operatorName = redisClient.get(OperatorSidHolder.get());
			orderLogReuseService.save(orderId, operatorName, "查看", remarks);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SAVE_REMARKS_SUCCESS_CODE, OrderConstans.ORDER_SAVE_REMARKS_SUCCESS_MSG);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_SAVE_REMARKS_FAILED_CODE, OrderConstans.ORDER_SAVE_REMARKS_FAILED_MSG);
		}
		return ajaxResponse;
	}

}
