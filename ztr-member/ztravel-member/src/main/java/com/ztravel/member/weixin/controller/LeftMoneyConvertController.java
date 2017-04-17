package com.ztravel.member.weixin.controller;

import javax.annotation.Resource;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.valid.CommonValidateUtil;
import com.ztravel.member.weixin.service.IWxAccountConvertService;
import com.ztravel.member.weixin.service.IWxMemberService;
import com.ztravel.member.validation.AccountConvertValidate;
import com.ztravel.payment.service.IAccountService;


@Controller
@RequestMapping("/account/weixin")
public class LeftMoneyConvertController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(LeftMoneyConvertController.class);

	@Resource
	private IAccountService accountService ;

	@Resource
	private IWxMemberService wxMemberServiceImpl ;

	@Resource
	private IWxAccountConvertService wxAccountConvertServiceImpl;

	@RequestMapping("/toconvert")
	public String toConvertPage(Model model){
		String viewUrl = null;
		try {
			MemberSessionBean memberBean = wxMemberServiceImpl.getMemberSessionBean();
			AccountInfoBean accountInfo = accountService.getAccountInfo(memberBean.getMemberId());
			if(null != accountInfo){
				Long accountLeft = accountInfo.getAmount();
				if(accountLeft >= 1000){
					model.addAttribute("amount", accountLeft/100);
					model.addAttribute("mobile", memberBean.getMobile());
					viewUrl = "member/weixin/electronicWallet/convert";
				}else{
					LOGGER.info("账户{}余额不足10元,为{}", memberBean.getNickName(),accountLeft/100);
				}
			}
		} catch (Exception e) {
			LOGGER.error("跳转到订单兑换页错误", e);
		}
		return viewUrl;
	}

	@RequestMapping("/convert")
	@ResponseBody
	public AjaxResponse convert(String mobile,String amount){
		AjaxResponse response = AjaxResponse.instance(ResponseConstants.WX_ACCOUNT_CONVERT_FAILED_CODE, ResponseConstants.WX_ACCOUNT_CONVERT_FAILED_MSG);
		if(StringUtils.isNotBlank(mobile)){
			try {
				mobile = CommonValidateUtil.validateMobile(mobile);
				Long convertAmount = AccountConvertValidate.validateAmount(amount);
				response = wxAccountConvertServiceImpl.convert(mobile, convertAmount);
			} catch (Exception e) {
				response.setRes_code(e.getMessage());
			}
		}else{
			response.setRes_code(ResponseConstants.WX_CONVERT_MOBILE_EMPTY_CODE);
			response.setRes_code(ResponseConstants.WX_CONVERT_MOBILE_EMPTY_MSG);
		}
		return response;
	}
}
