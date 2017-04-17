package com.ztravel.member.weixin.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.AccountConvertContent;
import com.ztravel.common.enums.AccountCovertStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.member.dao.IOrderDhDao;
import com.ztravel.member.po.Dhpo;
import com.ztravel.member.weixin.service.IWxAccountConvertService;
import com.ztravel.member.weixin.service.IWxMemberService;
import com.ztravel.payment.service.IAccountService;

@Service
public class WxAccountConvertServiceImpl implements IWxAccountConvertService {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(WxAccountConvertServiceImpl.class);

	@Resource
	private IAccountService accountService ;

	@Resource
	private IWxMemberService wxMemberServiceImpl ;

	@Resource
	private IOrderDhDao orderDhDaoImpl;

	@Resource
	private IdGeneratorUtil idGeneratorUtil;


	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public AjaxResponse convert(String mobile, Long amount) {
		AjaxResponse response = AjaxResponse.instance(ResponseConstants.WX_ACCOUNT_CONVERT_FAILED_CODE, ResponseConstants.WX_ACCOUNT_CONVERT_FAILED_MSG);
		try {
			MemberSessionBean memberBean = wxMemberServiceImpl.getMemberSessionBean();
			AccountInfoBean accountInfo = accountService.getAccountInfo(memberBean.getMemberId());
			if(null != accountInfo){
				Long leftAmount = accountInfo.getAmount();
				if(leftAmount < amount){
					LOGGER.error("账户{}余额不足,账户余额{},兑换金额{}", memberBean.getMemberId(),leftAmount,amount);
				}else{
					CommonResponse convertResponse = accountService.addAmount(memberBean.getMemberId(),-amount);
					Dhpo dhpo = buildDhpo(mobile, amount,memberBean);
					String dhpoId = idGeneratorUtil.getAccountConvertId();
					dhpo.setDhId(dhpoId);
					orderDhDaoImpl.insert(dhpo);
					if(convertResponse.isSuccess()){
						response.setRes_code(ResponseConstants.WX_ACCOUNT_CONVERT_SUCCESS_CODE);
						response.setRes_msg(ResponseConstants.WX_ACCOUNT_CONVERT_SUCCESS_MSG);
					}else{
						LOGGER.error("账户金额扣除错误", convertResponse.getErrMsg());
						throw ZtrBizException.instance(ResponseConstants.WX_ACCOUNT_CONVERT_FAILED_CODE, convertResponse.getErrMsg());
					}
				}
			}else{
				LOGGER.error("兑换账户不存在", memberBean.getMemberId());
			}
		} catch (Exception e) {
			LOGGER.error("微信端余额兑换错误", e.getMessage());
		}
		return response;
	}

	public Dhpo buildDhpo(String mobile,Long convertAmount,MemberSessionBean memberBean){
		Dhpo dhpo = new Dhpo();
		dhpo.setDhPhonenum(mobile);
		dhpo.setMemberName(memberBean.getNickName());
		dhpo.setMemberId(memberBean.getMid());
		dhpo.setMemberName(memberBean.getNickName());
		dhpo.setPledhTime(new Date());
		dhpo.setDhMoney(convertAmount);
		dhpo.setDhStatus(AccountCovertStatus.UNCONVERT.name());
		dhpo.setDhContent(AccountConvertContent.MOBILECARD.name());
		dhpo.setLastOperator(memberBean.getMid());
		return dhpo;
	}

}
