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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.OrderConstans;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.order.back.service.IOrderCommentService;
import com.ztravel.order.back.vo.OrderCommentVO;

/**
 * 后台评价管理
 * @author MH
 */
@Controller
@RequestMapping("/comment")
public class OrderCommentController {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCommentController.class);

	@Resource
	private IOrderCommentService commentService;

	@RequestMapping("/list")
	public String gotoCommentListPage(){
		return "order/back/commentManage/commentList";
	}

	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String searchComments(@RequestBody OrderCommentSearchEntity searchEntity, Model model){
		try {
			List<OrderCommentVO> comments = commentService.searchOrderComment(searchEntity);
			Long totalNum = commentService.countComments(searchEntity);

			model.addAttribute("commentList", comments);
			model.addAttribute("totalItemCount", totalNum);
			if( totalNum != 0 ){
				model.addAttribute("pageNo", searchEntity.getPageNo());
			}else{
				model.addAttribute("pageNo", 1);
			}
			model.addAttribute("pageSize",searchEntity.getPageSize());
			int totalPageCount = (int) Math.ceil( (double)totalNum/searchEntity.getPageSize() );
			totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
			model.addAttribute("totalPageCount", totalPageCount);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "order/back/commentManage/commentListData";
	}

	@RequestMapping("/detail/{commentId}")
	public String gotoCommentDetailPage(@PathVariable String commentId, Model model){
		try {
			OrderCommentVO comment = commentService.getCommentDetail(commentId);
			model.addAttribute("comment", comment);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return "order/back/commentManage/commentDetail";
	}

	@RequestMapping("/audit")
	@ResponseBody
	public AjaxResponse auditComment(String commentId, Integer auditResult){
		AjaxResponse ajaxResponse = null;
		try {
			ajaxResponse = commentService.audit(commentId, auditResult);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(OrderConstans.ORDER_COMMENT_AUDIT_ERROR_CODE, "");
		}
		return ajaxResponse;
	}

}
