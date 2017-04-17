package com.ztravel.member.opera.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.member.opera.service.IOperatorMessageService;
import com.ztravel.member.opera.vo.OperatorMessageVo;

@Controller
@RequestMapping("/operation/message")
public class OperatorMessageController {
	private static Logger logger = RequestIdentityLogger.getLogger(OperatorMessageController.class);
	@Resource
	IOperatorMessageService operatorMessageServiceImpl;

	@RequestMapping("/list")
	public String list(Model model){
		return "member/opera/operation/operator_message";
	}
	@RequestMapping("/load")
	public String load(Integer pageNo, Integer pageSize, Model model){
		Pagination<OperatorMessageVo> page = new Pagination<OperatorMessageVo>();
		try {
			pageNo = pageNo == null?1: pageNo;
			pageSize = pageSize == null? 10: pageSize;
			page = operatorMessageServiceImpl.getPage(pageNo, pageSize);
			model.addAttribute("dataList", page.getData());
			model.addAttribute("totalItemCount", page.getTotalItemCount());
			model.addAttribute("pageNo", page.getPageNo());
			model.addAttribute("pageSize", page.getPageSize());
			model.addAttribute("totalPageCount", page.getTotalPageCount());
		} catch (Exception e) {
			logger.error("获取后台消息列表出错:", e);
		}
		return "member/opera/operation/operator_message_tr";
	}
	@ResponseBody
	@RequestMapping("/read/{id}")
	public AjaxResponse read(@PathVariable String id){
		try {
			operatorMessageServiceImpl.readOne(id);
		} catch (Exception e) {
			logger.error("标记["+id+"]为已读出错：", e);
			return AjaxResponse.instance("fail", "");
		}
		return AjaxResponse.instance("success", "");
	}
	@ResponseBody
	@RequestMapping("/delete/{id}")
	public AjaxResponse delete(@PathVariable String id){
		try {
			operatorMessageServiceImpl.deleteOne(id);
		} catch (Exception e) {
			logger.error("删除["+id+"]出错：", e);
			return AjaxResponse.instance("fail", "");
		}
		return AjaxResponse.instance("success", "");
	}

	@ResponseBody
	@RequestMapping("/countUnread")
	public AjaxResponse countUnread(){
		int unreadNum = 0;
		try {
			unreadNum = operatorMessageServiceImpl.countUnread();
		} catch (Exception e) {
			logger.error("查询后台消息未读数出错：", e);
			return AjaxResponse.instance("fail", ""+unreadNum);
		}
		return AjaxResponse.instance("success", ""+unreadNum);
	}
}
