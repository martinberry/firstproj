package com.ztravel.order.back.controller;

import java.util.List;

import javax.annotation.Resource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.enums.CommonOrderStatus;
import com.ztravel.common.valid.BasicValidator;
import com.ztravel.common.valid.ValidResult;
import com.ztravel.order.back.service.ICommonOrderService;
import com.ztravel.order.back.vo.CommonOrderListVo;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.CommonOrderSearchCriteria;
import com.ztravel.reuse.order.service.ICommonOrderReuseService;
@Controller
@RequestMapping("/commonorder/opconfirm")
public class CommonOrderController {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonOrderController.class);
	
	@Resource
	private ICommonOrderService commonOrderServiceImpl;
	
	@Resource
	private ICommonOrderReuseService commonOrderReuseService;
	
	@Resource
	private com.ztravel.order.back.service.IOrderService orderCommonService;


	@RequestMapping("/list")
	public String showOrderListPage(){
		return "order/back/commonorder/List";                          //刷新页面上半部分
	}

	@RequestMapping(value="/search", method=RequestMethod.POST)
	public String searchOrders(@RequestBody CommonOrderSearchCriteria criteria, Model model){
		//搜索条件格式校验，若校验失败，返回空列表
		ValidResult result = BasicValidator.valid(criteria);
		if( !result.isSuccess() ){
			return "order/back/commonorder/ListData"; 
		}
		List<CommonOrderListVo> CoList = null;
		int totalNum = 0;
		try{
			CoList = commonOrderServiceImpl.searchCO(criteria);
			totalNum = commonOrderServiceImpl.countCOs(criteria);
		} catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("CoList", CoList);
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
		return "order/back/commonorder/ListData"; 

}
	
	
	
	//审核更改状态并存储备注
			@RequestMapping("/changestatus")
			@ResponseBody
			public AjaxResponse changestatus(@RequestBody CommonOrder commonorderchange,Model model){
				AjaxResponse ajaxResponse =  AjaxResponse.instance("","");	
				int changeflag=0;
				CommonOrder commonordertmp = commonOrderReuseService.selectByOrderId(commonorderchange.getOrderId());
				try {				
					//CommonOrder commonordertmp = commonOrderServiceImpl.selectByOrderId(commonorderchange.getOrderId());			
					CommonOrderStatus currentstatus=commonordertmp.getStatus();
					if(currentstatus!=CommonOrderStatus.INIT){
						ajaxResponse.setRes_code("OrderCHANGED");
					}else{
						if(commonorderchange.getStatus()==CommonOrderStatus.AUDIT_UNPASS&&commonorderchange.getRemark()!=null){
		                    commonordertmp.setRemark(commonorderchange.getRemark());
		                    commonordertmp.setStateChangeHistory(commonordertmp.getStatus() + "-->" + CommonOrderStatus.AUDIT_UNPASS.toString());
							}		
						commonordertmp.setStatus(commonorderchange.getStatus());
						commonordertmp.setUpdateDate(new DateTime());	                              
	                    commonOrderServiceImpl.updateAndinsertlog(commonordertmp);
	                    changeflag=1;
	                    //ajaxResponse.setRes_code("ChangeStatusSuccess");
	                    if(commonorderchange.getStatus()==CommonOrderStatus.REFUNDING){
	                    	//调用退款接口
	                    	orderCommonService.refundOpConfirmOrder(commonordertmp.getOrderId());	                    			                    	
	                    }	
	                    CommonOrder order4detailstatus=commonOrderReuseService.selectByOrderId(commonordertmp.getOrderId());
	                    CommonOrderStatus detailcommonstatus=order4detailstatus.getStatus();
	                    String detailstatus=detailcommonstatus.getDescription();
	                    String statechangehistory=order4detailstatus.getStateChangeHistory();
	                    statechangehistory=commonOrderServiceImpl.convertstateChangeHistory(statechangehistory);
	                    ajaxResponse.setRes_code(detailstatus);
	                    ajaxResponse.setRes_msg(statechangehistory);
					}					
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
					if(changeflag==1){					
						    CommonOrder order4detailstatus=commonOrderReuseService.selectByOrderId(commonordertmp.getOrderId());
		                    CommonOrderStatus detailcommonstatus=order4detailstatus.getStatus();
		                    String detailstatus=detailcommonstatus.getDescription();
		                    String statechangehistory=order4detailstatus.getStateChangeHistory();
		                    statechangehistory=commonOrderServiceImpl.convertstateChangeHistory(statechangehistory);
		                    ajaxResponse.setRes_code(detailstatus);
		                    ajaxResponse.setRes_msg(statechangehistory);
					}else{
						ajaxResponse.setRes_code("ChangeStatusFail");
					}
	               
				}
				return ajaxResponse;
			}
						
	
}


