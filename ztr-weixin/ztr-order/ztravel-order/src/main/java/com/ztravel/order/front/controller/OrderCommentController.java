package com.ztravel.order.front.controller;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.TZMarkers;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.OrderComment;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.OrderOperate;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.sensitive.SensitiveValidator;
import com.ztravel.common.util.OperatorMessageContentUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.IOperatorMessageClientService;
import com.ztravel.order.front.service.IOrderProductService;
import com.ztravel.order.front.service.IOrderService;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.order.service.IOrderCommentReuseService;
import com.ztravel.reuse.order.service.IOrderLogReuseService;
import com.ztravel.reuse.order.service.IOrderReuseService;

/**
 * 前台订单评价
 */
@Controller
@RequestMapping("/order/comment")
public class OrderCommentController {

	private static Logger LOG = LoggerFactory.getLogger(OrderCommentController.class);

	@Resource
	SensitiveValidator sensitiveValidator ;
	
	@Resource
	IOrderCommentReuseService orderCommentReuseService;

	@Resource
	IOrderProductService orderProductService;

	@Resource
	IOrderService orderService;

	@Resource
	IOrderLogReuseService orderLogReuseService;

	@Resource
	IOrderReuseService orderReuseService;

	@Resource
	IOperatorMessageClientService operatorMessageClientServiceImpl;

	@RequestMapping("/edit/{orderId}")
	public String gotoEditCommentPage(@PathVariable String orderId, Model model){
		try {
			if( SSOUtil.getMemberSessionBean() != null ){
				String currentMem = SSOUtil.getMemberSessionBean().getMid();
				String orderCreator = orderReuseService.getCreatorByOrderId(orderId);
				//检查是否是当前用户的订单
				if( currentMem.equals(orderCreator) ){
					OrderProduct orderProduct = orderProductService.selectOrderProductByOrderId(orderId);
					model.addAttribute("orderId", orderProduct.getOrderId());
					model.addAttribute("productTitle", orderProduct.getProductTitle());
					model.addAttribute("imageId", orderProduct.getImageId());
					return "order/front/orderComment/editComment";
				}else{  //不是该用户订单
					throw ZtrBizException.instance(OrderConstans.ORDER_COMMENT_HAVE_NO_RIGHT_TO_COMMENT_ERROR_CODE, OrderConstans.ORDER_COMMENT_HAVE_NO_RIGHT_TO_COMMENT_ERROR_MSG);
				}
			}
		} catch(ZtrBizException e){
			LOG.error(e.getRetMsg(), e);
			throw e;
		} catch(Exception e) {
			LOG.error(e.getMessage(), e);
		}
		return "order/front/orderComment/editComment";
	}

	@RequestMapping(value="/submit",method={RequestMethod.POST})
	@ResponseBody
	public AjaxResponse submitComment(@RequestBody OrderComment comment) {
		AjaxResponse ajaxResponse = null;
		
		boolean hasSenstiveWord = sensitiveValidator.hasSensitiveWord(comment.getComment()) ;
		if(hasSenstiveWord){
			return AjaxResponse.instance(ResponseConstants.SENSITIVE_WORD, ResponseConstants.SENSITIVE_WORD);
		}
		
		try {
			Boolean isCommented = orderCommentReuseService.isOrderAlreadyCommented(comment.getOrderId());
			if( isCommented ){
				return AjaxResponse.instance(OrderConstans.ORDER_COMMENT_ALREADY_COMMENTED_ERROR_CODE, OrderConstans.ORDER_COMMENT_ALREADY_COMMENTED_ERROR_MSG);
			}

			ajaxResponse = orderCommentReuseService.insert(comment);//orderCommentReuse.insert
			if( ajaxResponse.getRes_code().equals(OrderConstans.ORDER_COMMENT_INSERT_SUCCESS_CODE) ){
				if( SSOUtil.getMemberSessionBean() != null ){
					String currentMid = SSOUtil.getMemberSessionBean().getMid();
					if( StringUtils.isNotBlank(currentMid) ){
						orderLogReuseService.save(comment.getOrderId(), currentMid, "用户发表评价", "");
					}
					//发送后台消息
					operatorMessageClientServiceImpl.add(MessageTitleType.EVALATE, currentMid, comment.getProductTitle(), OperatorMessageContentUtil.getCommentUrl(comment.getId().toString()));
				}
				orderReuseService.updateOperateRecord(comment.getOrderId(), OrderOperate.EVALATE.getCode());
			}
			return ajaxResponse;
		} catch(Exception e) {
			LOG.error(TZMarkers.p2, OrderConstans.ORDER_COMMENT_INSERT_ERR_MSG, e);
			return AjaxResponse.instance(OrderConstans.ORDER_COMMENT_INSERT_ERR_CODE, "");
		}
	}

}
