package com.ztravel.member.front.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.po.SystemNotice;
import com.ztravel.member.front.vo.MsgRequest;
import com.ztravel.member.front.service.ISystemNoticeService;
import com.ztravel.member.front.vo.SystemNoticeVo;

@Controller
@RequestMapping("/systemnotice")
public class SystemNoticeController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(SystemNoticeController.class);

	@Resource
	ISystemNoticeService systemNoticeServiceImpl;

	@RequestMapping("/list")
	public String list(Model model){
		return "member/front/message/system_notice";
	}

	@RequestMapping("/search")
	public String search(@RequestBody MsgRequest request, Model model){
		Pagination<SystemNotice> page = new Pagination<SystemNotice>();
		try {
			request.setMemberId(SSOUtil.getMemberId());
			page = systemNoticeServiceImpl.list(request);
		} catch (Exception e) {
			LOGGER.error("获取提醒列表出错：", e);
		}
		model.addAttribute("dataList", page.getData());
		model.addAttribute("totalItemCount", page.getTotalItemCount());
		model.addAttribute("pageNo", page.getPageNo());
		model.addAttribute("pageSize", page.getPageSize());
		model.addAttribute("totalPageCount", page.getTotalPageCount());
		return "member/front/message/system_notice_tr";
	}

	@ResponseBody
	@RequestMapping("/countunread")
	public AjaxResponse count(Model model){
		long result = 0l;
		try {
			result =  systemNoticeServiceImpl.countUnread(SSOUtil.getMemberId());
		} catch (Exception e) {
			return AjaxResponse.instance("error", e.getMessage());
		}
		return AjaxResponse.instance("success", result+"");
	}

	@ResponseBody
	@RequestMapping("/read")
	public AjaxResponse read(@RequestBody List<String> noticeIds, Model model){
		boolean result  = false;
		try {
			result =  systemNoticeServiceImpl.batchRead(noticeIds);
		} catch (Exception e) {
			return AjaxResponse.instance("error", e.getMessage());
		}
		if(result){
			return AjaxResponse.instance("success", "");
		}else{
			return AjaxResponse.instance("error", "");
		}
	}

	@RequestMapping("/load")
	@ResponseBody
	public List<SystemNoticeVo> load(Integer size){
		List<SystemNotice> list = null;
		List<SystemNoticeVo> listVo= new ArrayList<SystemNoticeVo>();
		try {
			list = systemNoticeServiceImpl.list(SSOUtil.getMemberId(),size);
			if(!CollectionUtils.isEmpty(list)){
				for(SystemNotice sn:list){
					SystemNoticeVo vo = new SystemNoticeVo() ;
					vo.setId(sn.getId().toString());
					vo.setContent(sn.getContent());
					vo.setDateTime(sn.getDateTime());
					vo.setHasRead(sn.isHasRead());
					vo.setMemberId(sn.getMemberId());
					vo.setType(sn.getType());
					listVo.add(vo) ;
				}
			}
		} catch (Exception e) {
			LOGGER.error("获取提醒JSON出错：", e);
		}
		return listVo;
	}
}
