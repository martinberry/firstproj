package com.ztravel.product.front.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.common.enums.OrderCommentStatus;
import com.ztravel.common.enums.SEOEnums;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.util.SEOUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.reuse.member.entity.WorkPlatFormVo;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.member.client.service.IWishListClientService;
import com.ztravel.order.client.service.IOrderCommentClientService;
import com.ztravel.order.client.vo.OrderCommentClientVO;
import com.ztravel.product.common.service.ProductDetailService;
import com.ztravel.product.front.wo.CalendarDataWo;
import com.ztravel.product.front.wo.ProductWo;

/**
 * 前台产品详情
 * @author tengmeilin
 *
 */
@Controller
@RequestMapping("/localproduct/front")
public class LocalTravelDetailController {

	private static Logger logger = RequestIdentityLogger.getLogger(ProductDetailController.class);

	@Resource
	private ProductDetailService productDetailServiceImpl ;

	@Resource
	private IWishListClientService wishListClientServiceImpl;

	@Resource
	private IOrderCommentClientService commentClientService;

	@Resource
	private FrontCommonService frontCommonService;

	private final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping(value = "/detail/{productPid}", method = RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable("productPid") String productPid,
			@RequestParam(required=false) String selectedDay,
			@RequestParam(required=false) Integer adultNum,
			@RequestParam(required=false) Integer childNum,
			@RequestParam(required=false) String costPriceId, Model model,HttpServletRequest request) throws Exception {

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;
		ProductWo  product = new ProductWo();
		WorkPlatFormVo wpfv = new WorkPlatFormVo();
		Boolean isWish = false;

		if(StringUtils.isBlank(productPid)){
	    	logger.error("failed to get Pid from list");
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001) ;
	    }

	    try {
	    	product = productDetailServiceImpl.getUnvisaProductByPid(productPid);
	    	if(memberSessionBean != null && StringUtils.isNotBlank(memberSessionBean.getMemberId()) && memberSessionBean.getIsLogin() == true){
	    		isWish = wishListClientServiceImpl.isWishorNot(memberSessionBean.getMemberId(), product.getId());
			}
	    	wpfv = frontCommonService.getWorkPlatFormVo(request);

	    }catch (ZtrBizException ze) {
	    	throw ze ;
		}catch (Exception e) {
	    	logger.error("failed to get product", e);
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002) ;
		}

	    model.addAttribute("wpfv", wpfv) ;
		model.addAttribute("product", product);
		model.addAttribute("isWish", isWish);
		model.addAttribute("selectedDay", selectedDay);
		model.addAttribute("adultNum", adultNum);
		model.addAttribute("childNum", childNum);
		model.addAttribute("costPriceId", costPriceId);
		model.addAttribute("title", product.getpName());
		String pre = null;
		String pos = null;
		List<String> pids = redisClient.get(TokenHolder.get() + ":pids", ArrayList.class);
		if(pids != null){
			for(int i=0; i < pids.size(); i++){
				if(pids.get(i).equals(productPid)){
					if(i > 0){
						pre = pids.get(i-1);
					}else{
						pre = pids.get(pids.size()-1);
					}
					if(i < pids.size()-1){
						pos = pids.get(i+1);
					}else{
						pos = pids.get(0);
					}
					redisClient.expire(TokenHolder.get() + ":pids", 60 * 60);
					break;
				}
			}
		}
		model.addAttribute("prePid", pre);
		model.addAttribute("posPid", pos);

		//用户评价
		OrderCommentSearchEntity searchEntity = new OrderCommentSearchEntity();
		searchEntity.setPid(productPid);
		searchEntity.setStatus(OrderCommentStatus.PUBLISHED);
		List<OrderCommentClientVO> commentList = commentClientService.searchOrderComment(searchEntity);
		model.addAttribute("commentList", commentList);
		Long commentNum = commentClientService.countOrderComment(searchEntity);
		model.addAttribute("commentNum", commentNum);
		return new ModelAndView("product/front/detail/local_detail_main") ;
	}



	@RequestMapping(value = "/detail/getCalendarData/{productPid}", method = RequestMethod.POST)
	@ResponseBody
	public CalendarDataWo getCalendarData(@PathVariable("productPid") String productPid) throws Exception {
		if(StringUtils.isBlank(productPid)){
	    	logger.error("failed to get pid from list");
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001) ;
	    }
		CalendarDataWo data = new CalendarDataWo();
	    try {
	    	data = productDetailServiceImpl.getCalDataByPid(productPid);
	    }catch (ZtrBizException ze) {
	    	throw ze ;
		}catch (Exception e) {
	    	logger.error("failed to get product", e);
	    	throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002) ;
		}
		return data;
	}

}
