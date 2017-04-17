package com.ztravel.member.front.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.travelzen.framework.core.util.MoneyUtil;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstBackMemb;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.valid.CommonValidateUtil;
import com.ztravel.member.po.BalanceDetailEntity;
import com.ztravel.member.client.service.IBalanceDetailClientService;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.member.po.Dhpo;
import com.ztravel.member.po.Member;
import com.ztravel.member.front.service.IDhService;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.member.validation.AccountConvertValidate;
import com.ztravel.payment.service.IAccountService;

/**
 * C端首页
 * @author haofan.wan
 */

@Controller
@RequestMapping("/electronicWallet/accountBalance")
public class AccountBalanceController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(AccountBalanceController.class);

	@Resource
	private MemberService memberServiceImpl ;

	@Resource
	private IBalanceDetailClientService balanceDetailClientService ;

	@Resource
	private   IAccountService accountService ;

	@Resource
	private IMemberClientService memberClientServiceImpl ;

	@Resource
	private IDhService dhService ;



	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, Model model) {
		String memberId =SSOUtil.getMemberId();

		String headImageId ="";
		String nickname="";
		String phonenumber="";
		long amount = 0l;
		AccountInfoBean accountInfoBean = new AccountInfoBean();
		try{
			if(StringUtils.isNotEmpty(memberId)){
				accountInfoBean =	accountService.getAccountInfo(memberId);
				if(null != accountInfoBean){
					amount = accountInfoBean.getAmount();
				}
				Member currentMember =  memberServiceImpl.getMemberById(memberId);
				if(null!= currentMember){
					headImageId = currentMember.getHeadImageId();

					nickname = currentMember.getNickName();//获取会员昵称
					phonenumber=currentMember.getMobile();
				}

			}
		}catch(Exception e){
			LOGGER.info("跳转账户余额页面失败",e);
		}
		model.addAttribute("account", amount/100);
		model.addAttribute("headImageId", headImageId);
		model.addAttribute("memberId",memberId);//传递会员Id到页面
		model.addAttribute("nickname",nickname);//传递会员昵称到页面
		model.addAttribute("phonenumber",phonenumber);//传递会员昵称到页面

		return new ModelAndView("member/front/electronicWallet/accountBalance/index") ;
	}

//存储兑换数据
	@RequestMapping(value="/dh",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse transferdh(Dhpo dh){
		AjaxResponse ajaxResponse =  AjaxResponse.instance("","");
		try{
			String mobile = CommonValidateUtil.validateMobile(dh.getDhPhonenum());
			Long convertAmount = AccountConvertValidate.validateAmount(dh.getDhMoney());
			dh.setDhMoney(convertAmount);
			dh.setDhPhonenum(mobile);
			ajaxResponse=dhService.adddh(dh);
			return ajaxResponse;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_CODE, ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_MSG);//
			return ajaxResponse;
		}
	}

	//存储兑换数据
		@RequestMapping(value="/getAccountMoney",method=RequestMethod.POST)
		@ResponseBody
		public AjaxResponse getAccountMoney(){
			AjaxResponse ajaxResponse =  AjaxResponse.instance(ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_CODE,ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_MSG);
			long amount = 0l;
			try{
				String memberId =SSOUtil.getMemberId();
				if(StringUtils.isNotEmpty(memberId)){
					AccountInfoBean accountInfoBean =	accountService.getAccountInfo(memberId);
					if(null != accountInfoBean){
						amount = accountInfoBean.getAmount();
						ajaxResponse.setRes_code(ResponseConstBackMemb.GET_ACCOUNT_MONEY_SUCCESS_CODE);
						ajaxResponse.setRes_msg(MoneyUtil.cent2Yuan(amount));
					}
				}
				return ajaxResponse;
			}catch(Exception e){
				LOGGER.error(e.getMessage(), e);
				ajaxResponse = AjaxResponse.instance(ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_CODE, ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_MSG);//
				return ajaxResponse;
			}
		}

///////////////////////////////////////////




	@RequestMapping(value="/search",method=RequestMethod.POST)
	public String  search(@RequestBody	Pagination<BalanceDetailEntity> pagination , Model model){
		String memberId = "";
		int totalItemCount = 0;
		int totalPageCount = 0;
		int pageNo =pagination.getPageNo();
		int pageSize = pagination.getPageSize();

		try{
			memberId = memberServiceImpl.getMemberFromRedisBySID().getMemberId() ;
			if(StringUtils.isNotEmpty(memberId)){
				pagination = balanceDetailClientService.getBalanceDetailsByMemberid(memberId,	pagination);
				totalItemCount = balanceDetailClientService.countByMemberId(memberId);
				if(pagination.getPageSize() != 0){
					totalPageCount = (int) Math.ceil(new Double(totalItemCount)/pagination.getPageSize() );
				}
			}
		}catch(Exception e){
			LOGGER.info("根据memberId 查询帮赚钱好友列表失败:",e);
		}

		if(0==totalItemCount){
			 pageNo = 1;
			 totalItemCount = 0;
			 totalPageCount=1;
		}

		model.addAttribute("makeMoneyFriends", pagination.getData());
		model.addAttribute("pageNo",pageNo);
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("totalItemCount", totalItemCount);
		model.addAttribute("totalPageCount", totalPageCount);
		return "member/front/electronicWallet/accountBalance/makeMoneyFriends_table";
	}


/////////////////////////////////////////////////////////判断用户是否挂起
	@RequestMapping(value="/membersIsActive",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  membersIsActive(String destMembeId) throws Exception{
		Map<String, Object> resultMap = Maps.newHashMap();
		String isActive = "no";
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		boolean destIsActive = true;
		if(memberSessionBean !=null && memberSessionBean.getIsActive() && destIsActive) {
			isActive = "yes";
		}
		resultMap.put("isActive", isActive);
		return resultMap;
	}

/////////////////////////////////////////////////////////////获得默认兑换号码
	@RequestMapping(value="/getphonenum",method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse getPhonenum(){
		AjaxResponse ajaxResponse =  AjaxResponse.instance(ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_CODE,ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_MSG);
		String memberId =SSOUtil.getMemberId();
		String phonenumber="";
		try{
					Member currentMember =  memberServiceImpl.getMemberById(memberId);
					phonenumber=currentMember.getMobile();
					ajaxResponse.setRes_code(ResponseConstBackMemb.GET_ACCOUNT_MONEY_SUCCESS_CODE);
					ajaxResponse.setRes_msg(phonenumber);
					return ajaxResponse;
		}
			catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			ajaxResponse = AjaxResponse.instance(ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_CODE, ResponseConstBackMemb.GET_ACCOUNT_MONEY_FAILED_MSG);//
			return ajaxResponse;
		}
	}

	/////////////////////////////登陆判断
	@RequestMapping(value="/getlogin",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getLoginIngo() {
		Map<String, Object> resultMap = Maps.newHashMap();
		String login="out";
		try{
		boolean isLogin = memberClientServiceImpl.getMemberFromRedisBySID().getIsLogin();
		if(isLogin==true) {
			login="in";
		}
		}catch(Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
		resultMap.put("login", login);
		return resultMap;
	}

}
