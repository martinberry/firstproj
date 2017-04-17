package com.ztravel.member.front.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.member.front.vo.MsgEntity;
import com.ztravel.member.front.vo.MsgRequest;
import com.ztravel.member.front.service.IPrivateLetterService;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.reuse.member.entity.PrivateLetterVo;
@Controller
@RequestMapping("/privateletter")
public class PrivateLetterController {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(PrivateLetterController.class);
	@Resource
	private IPrivateLetterService privateLetterServiceImpl;
	@Resource
	private MemberService memberServiceImpl;

	@RequestMapping("/list")
	public String list(Model model){
		return "member/front/message/private_letter";
	}

	@RequestMapping("/search")
	public String search(@RequestBody MsgRequest request,Model model){
		Pagination<PrivateLetterVo> page = new Pagination<PrivateLetterVo>();
		try {
			page = privateLetterServiceImpl.list(SSOUtil.getMemberId(), request.getPageNo(), request.getPageSize());
			model.addAttribute("dataList", page.getData());
			model.addAttribute("totalItemCount", page.getTotalItemCount());
			model.addAttribute("pageNo", page.getPageNo());
			model.addAttribute("pageSize", page.getPageSize());
			model.addAttribute("totalPageCount", page.getTotalPageCount());
		} catch (Exception e) {
			LOGGER.error("获取私信列表出错：", e);
		}
		return "member/front/message/private_letter_tr";
	}

	@RequestMapping("/countunread")
	@ResponseBody
	public AjaxResponse countUnread(){
		long unNum = privateLetterServiceImpl.count(SSOUtil.getMemberId());
		return AjaxResponse.instance("success", unNum+"");
	}

	@RequestMapping("/talk/{anotherId}")
	public String read(@PathVariable String anotherId,Model model){
		try {
			String authorId = SSOUtil.getMemberId();
			PrivateLetter letter = privateLetterServiceImpl.detail(authorId, anotherId);
			model.addAttribute("letter", letter);
			model.addAttribute("author", memberServiceImpl.getMemberById(authorId));
			model.addAttribute("another", memberServiceImpl.getMemberById(anotherId));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取私信详情出错：", e);
		}
		return "member/front/message/private_letter_detail";
	}

	@RequestMapping("/addmsg")
	@ResponseBody
	public AjaxResponse addMsg(@RequestBody MsgEntity msg){
		boolean result = privateLetterServiceImpl.addMsg(SSOUtil.getMemberId(), msg.getAnotherId(), msg.getContent());
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

	@RequestMapping("/deleteletter/{letterId}")
	@ResponseBody
	public AjaxResponse deleteLetter(@PathVariable String letterId){
		boolean result = privateLetterServiceImpl.deleteAll(letterId);
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

	@RequestMapping("/deletemsg/{letterId}/{msgId}")
	@ResponseBody
	public AjaxResponse deleteMsg(@PathVariable String letterId, @PathVariable String msgId){
		boolean result = privateLetterServiceImpl.deleteOne(letterId, msgId);
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

	@RequestMapping("/load")
	@ResponseBody
	public List<PrivateLetterVo> load(Integer size){
		List<PrivateLetterVo> dataList = null ;
		try {
			dataList = (List<PrivateLetterVo>)privateLetterServiceImpl.list(SSOUtil.getMemberId(), 1, size).getData();
		} catch (Exception e) {
			LOGGER.error("获取私信JSON数据出错：", e);
		}
		return dataList == null ? new ArrayList<PrivateLetterVo>() : dataList;
	}

}
