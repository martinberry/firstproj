package com.ztravel.member.weixin.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.po.BalanceDetailEntity;
import com.ztravel.member.client.service.IBalanceDetailClientService;
import com.ztravel.member.po.Member;
import com.ztravel.member.weixin.service.IWxMemberService;
import com.ztravel.payment.service.IAccountService;

/**
 * C端首页
 * @author bzhou
 */

@Controller
@RequestMapping("/accountBalance")
public class AccountBalanceController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(AccountBalanceController.class);

	@Resource
	private IWxMemberService wxMemberServiceImpl ;

	@Resource
	private IBalanceDetailClientService balanceDetailClientService ;

	@Resource
	private IAccountService accountService ;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, Model model) {
		String memberId = SSOUtil.getMemberId();
		long amount = 0l;
		int totalPage = 0;
		AccountInfoBean accountInfoBean = new AccountInfoBean();
		try{
			if(StringUtils.isNotEmpty(memberId)){
				accountInfoBean =	accountService.getAccountInfo(memberId);
				if(null != accountInfoBean){
					amount = accountInfoBean.getAmount();
				}
			}
			int totalNum = balanceDetailClientService.countByMemberId(memberId);
			totalPage = (totalNum + 10 - 1) / 10;

		}catch(Exception e){
			LOGGER.info("跳转账户余额页面失败",e);
		}

		model.addAttribute("account", amount);
		model.addAttribute("totalPage", totalPage);
		return new ModelAndView("member/weixin/electronicWallet/accountBalance_index");
	}


	@RequestMapping(value="/load")
	public String loadData(Integer pageNo, Integer size,Model model) throws Exception{
		Pagination<BalanceDetailEntity> pagination = new Pagination<BalanceDetailEntity>();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(size);
		String memberId = SSOUtil.getMemberId();
		pagination = balanceDetailClientService.getBalanceDetailsByMemberid(memberId, pagination);
		model.addAttribute("pageNum", pageNo);
		List<BalanceDetailEntity> balanceDetailList = (List<BalanceDetailEntity>) pagination.getData();
		String mobile = "";
		String friendId="";
		Member friend = new Member();
		for(BalanceDetailEntity balanceDetailEntity : balanceDetailList){
			if(null != balanceDetailEntity.getBonus() ){
				MoneyCalculator money = new MoneyCalculator(balanceDetailEntity.getBonus());
				balanceDetailEntity.setBonusStr(money.fenToYuan().toString());
			}
			friendId = balanceDetailEntity.getFriend();
			if(StringUtils.isNotEmpty(friendId)){
				 friend = wxMemberServiceImpl.getMemberById(friendId);
				if(null != friend){
					mobile = friend.getMobile();
					if(StringUtils.isNotEmpty(mobile)){
						balanceDetailEntity.setFriend(mobile);
					}
				}
			}
		}
		model.addAttribute("balanceDetailList",balanceDetailList);
		return "member/weixin/electronicWallet/accountBalance_table";
	}

}
