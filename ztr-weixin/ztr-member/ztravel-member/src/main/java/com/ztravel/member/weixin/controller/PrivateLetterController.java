package com.ztravel.member.weixin.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.client.service.IPrivateLetterClientService;
import com.ztravel.member.front.vo.MsgEntity;
import com.ztravel.reuse.member.entity.PrivateLetterVo;
import com.ztravel.member.po.PrivateLetter;
import com.ztravel.member.weixin.service.IWxMemberService;
@Controller
@RequestMapping("/privateletter")
public class PrivateLetterController {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(PrivateLetterController.class);
	@Resource
	private IPrivateLetterClientService privateLetterClientService;
	@Resource
	private IWxMemberService wxMemberService;

	@RequestMapping("/list")
	public String list(Model model){
		List<PrivateLetterVo> dataList = null ;
		dataList = (List<PrivateLetterVo>)privateLetterClientService.list(SSOUtil.getMemberId(), 1, 10).getData();
		if(null == dataList || dataList.size() == 0){
			return "member/weixin/letter/no_letter";
		}
		return "member/weixin/letter/letter_list";
	}

	@RequestMapping("/countunread")
	@ResponseBody
	public AjaxResponse countUnread(){
		if(SSOUtil.getMemberId() == null){
			return AjaxResponse.instance("success", 0+"");
		}
		long unNum = privateLetterClientService.count(SSOUtil.getMemberId());
		return AjaxResponse.instance("success", unNum+"");
	}

	@RequestMapping("/talk/{anotherId}")
	public String read(@PathVariable String anotherId,Model model){
		try {
			String authorId = SSOUtil.getMemberId();
			PrivateLetter letter = privateLetterClientService.detail(authorId, anotherId);
			model.addAttribute("letter", letter);
			model.addAttribute("author", wxMemberService.getMemberById(authorId));
			model.addAttribute("another", wxMemberService.getMemberById(anotherId));
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("获取私信详情出错：", e);
		}
		return "member/weixin/letter/letter_detail";
	}

	@RequestMapping("/addmsg")
	@ResponseBody
	public AjaxResponse addMsg(@RequestBody MsgEntity msg){
		boolean result = privateLetterClientService.addMsg(SSOUtil.getMemberId(), msg.getAnotherId(), msg.getContent());
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

	@RequestMapping("/load")
	public String load(Integer pageNo, Integer size, Model model){
		List<PrivateLetterVo> dataList = null ;
		try {
			dataList = (List<PrivateLetterVo>)privateLetterClientService.list(SSOUtil.getMemberId(), pageNo, size).getData();
			model.addAttribute("dataList",dataList);
		} catch (Exception e) {
			LOGGER.error("获取私信JSON数据出错：", e);
		}
		return "member/weixin/letter/private_letter_tr";
	}

	@RequestMapping("/deleteletter/{letterId}")
	@ResponseBody
	public AjaxResponse deleteLetter(@PathVariable String letterId){
		boolean result = privateLetterClientService.deleteAll(letterId);
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

	@RequestMapping("/deletemsg/{letterId}/{msgId}")
	@ResponseBody
	public AjaxResponse deleteMsg(@PathVariable String letterId, @PathVariable String msgId){
		boolean result = privateLetterClientService.deleteOne(letterId, msgId);
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

}
