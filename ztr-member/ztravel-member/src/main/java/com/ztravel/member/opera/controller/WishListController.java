package com.ztravel.member.opera.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.member.po.WishEntity;
import com.ztravel.member.opera.service.IWishListService;
import com.ztravel.member.opera.wo.WishDetailPageWo;
import com.ztravel.member.opera.wo.WishListSearchWo;
import com.ztravel.member.opera.wo.WishMemberWo;
import com.ztravel.member.opera.wo.WishProductResponseWo;
import com.ztravel.product.client.entity.ProductBasicDetail;
import com.ztravel.product.client.service.IProductDetailService;

@Controller
@RequestMapping("/member/opera/wish")
public class WishListController {

	private static Logger logger = RequestIdentityLogger.getLogger(WishListController.class);

	@Resource
	private IWishListService wishListServiceimpl;

	@Resource
	private IProductDetailService productClientDetailServiceImpl;

	@RequestMapping(value="/list/{memberId}", method = RequestMethod.GET)
	public String getWishListByOne(@PathVariable("memberId") String memberId, Model model) throws Exception{

		if( StringUtils.isBlank(memberId)){
			return "member/opera/memberManage/wishList/wish_list_one";
		}

		List<WishEntity> wishs = null;
		try{
			wishs = wishListServiceimpl.searchWishsByMemberId(memberId);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1005, Const.FO_MEMB_REASON_1005) ;
		}

		model.addAttribute("memberId", memberId);
		model.addAttribute("wishs", wishs);

		return "member/opera/memberManage/wishList/wish_list_one";

	}

	@RequestMapping(value="/list/all", method = RequestMethod.GET)
	public String getAllWishList(Model model, HttpServletRequest request){

		return "member/opera/memberManage/wishList/wish_list_all";

	}

	@RequestMapping(value="/search", method = RequestMethod.POST)
	public String getWishListBySearch(@RequestBody WishListSearchWo searchWo, Model model) throws Exception{

		if(StringUtils.isNotBlank(searchWo.getpName()) && searchWo.getpName().length() > 30){
			return "member/opera/memberManage/wishList/list_all_tbody";
		}

		List<WishProductResponseWo> products = new ArrayList<>();
		long productNum = 0;
		try{
			List<WishProductResponseWo> response = new ArrayList<>();
			productNum = wishListServiceimpl.searchWishMembers(searchWo, response);
			for(WishProductResponseWo product : response){
				ProductBasicDetail entity = productClientDetailServiceImpl.getProductById(product.getProductId());
				if(entity != null){
					product.setPid(entity.getPid());
					product.setpName(entity.getpName());
					product.setToContinent(entity.getToContinent());
					product.setStatus(entity.getStatus());
					products.add(product);
				}
			}

		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1006, Const.FO_MEMB_REASON_1006) ;
		}
		model.addAttribute("products", products);

		if( productNum != 0 ){
			model.addAttribute("pageNo", searchWo.getPageNo());
		}else{
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize", searchWo.getPageSize());
		model.addAttribute("totalItemCount", productNum);
		Integer totalPageCount = (int) Math.ceil( (double)productNum/searchWo.getPageSize() );
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
		model.addAttribute("totalPageCount", totalPageCount);
		return "member/opera/memberManage/wishList/list_all_tbody";

	}

	@RequestMapping(value="/detail/{productId}", method = RequestMethod.GET)
	public String getWishProductDetail(@PathVariable("productId") String productId, Model model) throws Exception{
		ProductBasicDetail entity = new ProductBasicDetail();
	    try {
	    	entity = productClientDetailServiceImpl.getProductById(productId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1007, Const.FO_MEMB_REASON_1007) ;
		}
		model.addAttribute("product", entity);
		return "member/opera/memberManage/wishList/wish_detail";
	}

	@RequestMapping(value="/detail/page", method = RequestMethod.POST)
	public String getWishMemberDetail(@RequestBody WishDetailPageWo pageWo, Model model) throws Exception{

		if(StringUtils.isBlank(pageWo.getProductId())){
			return "member/opera/memberManage/wishList/detail_tbody";
		}

		List<WishMemberWo> members = new ArrayList<>();
		long memberNum = 0;
		try{
			memberNum = wishListServiceimpl.searchWishsByProductId(pageWo, members);
		} catch(SQLException ex){
	    	logger.error("failed to get wishList detail from DB", ex);
	    	throw ZtrBizException.instance(Const.FO_MEMB_CODE_1008, Const.FO_MEMB_REASON_1008) ;
	    }catch(Exception e) {
			logger.error(e.getMessage(), e);
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1009, Const.FO_MEMB_REASON_1009) ;
		}
		model.addAttribute("members", members);

		if( memberNum != 0 ){
			model.addAttribute("pageNo", pageWo.getPageNo());
		}else{
			model.addAttribute("pageNo", 0);
		}
		model.addAttribute("pageSize", pageWo.getPageSize());
		model.addAttribute("totalItemCount", memberNum);
		Integer totalPageCount = (int) Math.ceil( (double)memberNum/pageWo.getPageSize() );
		model.addAttribute("totalPageCount", totalPageCount);
		return "member/opera/memberManage/wishList/detail_tbody";

	}

}
