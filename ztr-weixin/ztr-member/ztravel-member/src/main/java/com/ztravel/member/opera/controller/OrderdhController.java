package com.ztravel.member.opera.controller;
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

import com.alibaba.fastjson.JSONObject;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstBackMemb;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.member.po.Dhpo;
import com.ztravel.member.opera.entity.DHSearchCriteria;
import com.ztravel.member.opera.service.IOrderDhService;
import com.ztravel.member.opera.vo.DHOrderDetailVO;
import com.ztravel.member.opera.vo.DHOrderListVO;

@Controller
@RequestMapping("/order/convert")
	public class OrderdhController {
		private static final Logger LOGGER = LoggerFactory.getLogger(OrderdhController.class);

		@Resource
		private IOrderDhService orderdhService;


		@RequestMapping("/list")
		public String showOrderListPage(){
			return "member/opera/dh/dhList";                          //刷新页面上半部分
		}

		@RequestMapping(value="/search", method=RequestMethod.POST)
		public String searchOrders(@RequestBody DHSearchCriteria criteria, Model model){
			//搜索条件格式校验，若校验失败，返回空列表
			ValidResult result = BasicValidator.valid(criteria);
			if( !result.isSuccess() ){
				return "member/opera/dh/dhListData";
			}

			List<DHOrderListVO> DHList = null;
			int totalNum = 0;
			try{
				DHList = orderdhService.searchDH(criteria);
				totalNum = orderdhService.countDHs(criteria);
			} catch(Exception e){
				LOGGER.error(e.getMessage(), e);
			}
			model.addAttribute("DHList", DHList);
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
			return "member/opera/dh/dhListData";                       //刷新页面数据列表
		}

		@RequestMapping("/detail/{dhId}")
		@ResponseBody
		public String gotoOrderDetailPage(@PathVariable String dhId, Model model){
			JSONObject jsonObj = new JSONObject();
			try {
				DHOrderDetailVO dhDetail = orderdhService.getDHDetailByDHId(dhId);
				jsonObj.put("res_code", ResponseConstBackMemb.MEMB_DHVIEW_SUCCESS_CODE);
				jsonObj.put("res_msg",dhDetail);
			} catch (ZtrBizException e) {
				jsonObj.put("res_code", ResponseConstBackMemb.MEMB_DHVIEW_FAILED_CODE);
				LOGGER.error(e.getRetMsg(), e);
			} catch (Exception e) {
				jsonObj.put("res_code", ResponseConstBackMemb.MEMB_DHVIEW_FAILED_CODE);
				LOGGER.error(e.getMessage(), e);
			}
			return JSONObject.toJSONString(jsonObj);
		}
		////////////////////////////////////////////////
		//更改状态
		@RequestMapping("/changestatus/{dhId}")
		@ResponseBody
		public AjaxResponse changestatus(@PathVariable String dhId,Model model){
			AjaxResponse ajaxResponse =  AjaxResponse.instance("","");
			try {
				Dhpo dh = orderdhService.search(dhId);
				ajaxResponse=orderdhService.update(dh);
				return ajaxResponse;
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
               ajaxResponse.setRes_code(ResponseConstBackMemb.MEMB_MODIFY_DH_STATUS_ERRROR_CODE);
               return ajaxResponse;
			}
		}
}
