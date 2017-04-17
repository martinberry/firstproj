package com.ztravel.product.front.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.order.client.service.IOrderCommentClientService;
import com.ztravel.order.client.vo.OrderCommentClientVO;
import com.ztravel.product.front.convertor.VisaProductDetailConvertor;
import com.ztravel.product.front.vo.VisaProductDetailVo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;
import com.ztravel.reuse.product.service.IVisaProductReuseService;


@Controller
@RequestMapping("/product/visa")
public class VisaProductDetailController {
	
	@Resource
	private IVisaProductReuseService visaProductReuseService;
	
	@Resource
	private FrontCommonService frontCommonService;
	
	@Resource
	private IOrderCommentClientService commentClientService;
	
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaProductDetailController.class);
	
	@RequestMapping(value="/detail/{pid}")
	public String detail(@PathVariable String pid,@RequestParam(required=false) String selectedDay,
			@RequestParam(required=false) Integer adultNum,
			@RequestParam(required=false) Integer childNum,
			@RequestParam(required=false) String costPriceId,Model model,HttpServletRequest request) throws Exception{
		WorkPlatFormVo wpfv = frontCommonService.getWorkPlatFormVo(request);
		VisaProduct visaProduct = visaProductReuseService.selectByPid(pid);
		VisaProductDetailVo visaProductVo =  VisaProductDetailConvertor.product2Vo(visaProduct);
		model.addAttribute("wpfv", wpfv);
		model.addAttribute("product", visaProductVo);
		//用户评价
		List<OrderCommentClientVO> commentList = commentClientService.searchOrderCommentByPid(pid);
		model.addAttribute("commentList", commentList);
		Long commentNum = commentClientService.countOrderCommentByPid(pid);
		model.addAttribute("commentNum", commentNum);
		model.addAttribute("selectedDay", selectedDay);
		model.addAttribute("adultNum", adultNum);
		model.addAttribute("childNum", childNum);
		model.addAttribute("costPriceId", costPriceId);
		return "product/front/detail/visa/main";
	}
	
	@RequestMapping("/detail/getPrice/{pid}/{priceId}")
	@ResponseBody
	public JSONObject getPrice(@PathVariable String pid,@PathVariable String priceId){
		JSONObject response = new JSONObject();
		PriceInfo price = null;
		try {
			price = visaProductReuseService.getPriceByPidAndPriceId(pid, priceId);
			response.put("status", "SUCCESS");
			response.put("price",price);
		} catch (Exception e) {
			LOGGER.error("查询签证产品价格类型信息错误:[{}]", e);
			response.put("status", "FAILED");
		}
		return response;
	}
	
	
	
}
