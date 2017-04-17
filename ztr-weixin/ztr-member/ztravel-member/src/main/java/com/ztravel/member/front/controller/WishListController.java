package com.ztravel.member.front.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.front.vo.AddToWishRequest;
import com.ztravel.member.front.vo.WishDetailResponse;
import com.ztravel.member.front.service.IWishListService;

@Controller
@RequestMapping("/member/wish")
public class WishListController {

	private static Logger logger = RequestIdentityLogger.getLogger(WishListController.class);

	@Resource
	private IWishListService wishListFrontServiceimpl;

	@RequestMapping(value="/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse add2WishList(@RequestBody AddToWishRequest product, Model model) throws Exception{

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

		if(memberSessionBean == null || StringUtils.isBlank(memberSessionBean.getMemberId())
				|| memberSessionBean.getIsLogin() == false || StringUtils.isBlank(memberSessionBean.getMid())){
			return AjaxResponse.instance(Const.FF_MEMB_CODE_1001, Const.FF_MEMB_REASON_1001);
		}

		if( StringUtils.isBlank(product.getProductId())){
			return AjaxResponse.instance(Const.FF_MEMB_CODE_1002, Const.FF_MEMB_REASON_1002);
		}

		try{
			wishListFrontServiceimpl.add2WishList(memberSessionBean.getMemberId(), memberSessionBean.getMid(), product);
		} catch(SQLException se){
	    	logger.error(se.getMessage(), se);
	    	return AjaxResponse.instance(Const.FF_MEMB_CODE_1004, Const.FF_MEMB_REASON_1004) ;
	    }catch(ZtrBizException ze){
	    	logger.error("the wish has been in DB", ze);
	    	return AjaxResponse.instance(Const.FF_MEMB_CODE_1003, Const.FF_MEMB_REASON_1003);
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FF_MEMB_CODE_1005, Const.FF_MEMB_REASON_1005);
		}

		return AjaxResponse.instance(Const.SF_MEMB_CODE_1001, Const.SUCCESS);

	}

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public String getWishListByMemberId(Model model) throws Exception{

		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

		if(memberSessionBean == null || StringUtils.isBlank(memberSessionBean.getMemberId()) || memberSessionBean.getIsLogin() == false){
			throw ZtrBizException.instance(Const.FF_MEMB_CODE_1001, Const.FF_MEMB_REASON_1001) ;
		}

		List<WishDetailResponse> wishs = null;
		try{
			wishs = wishListFrontServiceimpl.searchWishsDetailByMemberId(memberSessionBean.getMemberId(), memberSessionBean.getMid());
		} catch(SQLException ex){
	    	logger.error("failed to get wishList detail from DB", ex);
	    	throw ZtrBizException.instance(Const.FF_MEMB_CODE_1006, Const.FF_MEMB_REASON_1006) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FF_MEMB_CODE_1007, Const.FF_MEMB_REASON_1007) ;
		}

		model.addAttribute("wishs", wishs);

		return "member/front/wishList/wish_list";

	}

	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse deleteWish(@RequestParam(value="wishId") String wishId) throws Exception{

		if( StringUtils.isBlank(wishId)){
			return AjaxResponse.instance(Const.FF_MEMB_CODE_1008, Const.FF_MEMB_REASON_1008);
		}

		try{
			wishListFrontServiceimpl.deleteWish(wishId);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(Const.FF_MEMB_CODE_1009, Const.FF_MEMB_REASON_1009);
		}

		return AjaxResponse.instance(Const.SF_MEMB_CODE_1002, Const.SUCCESS);

	}

	@RequestMapping(value="/query", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse getProductIdsByMemberId() throws Exception{

		String str = "";
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

		if(memberSessionBean == null || StringUtils.isBlank(memberSessionBean.getMemberId()) || memberSessionBean.getIsLogin() == false){
			return AjaxResponse.instance(Const.FF_MEMB_CODE_1001, Const.FF_MEMB_REASON_1001) ;
		}

		try{
			str = wishListFrontServiceimpl.getProductIdsByMemberId(memberSessionBean.getMemberId());
		} catch(SQLException ex){
	    	logger.error("failed to get wishList from DB", ex);
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
		}

		return AjaxResponse.instance(Const.SF_MEMB_CODE_1003, str);

	}


}
