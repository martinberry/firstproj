package com.ztravel.member.front.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstBackMemb;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.AccountConvertContent;
import com.ztravel.common.enums.AccountCovertStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.payment.AccountInfoBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.dao.IOrderDhDao;
import com.ztravel.member.po.Dhpo;
import com.ztravel.member.front.service.IDhService;
import com.ztravel.payment.service.IAccountService;

@Service
public class DhServiceImpl implements IDhService {

	@Resource
	private IOrderDhDao orderdhdao;

	@Resource
	private IdGeneratorUtil idGeneratorUtil ;

	@Resource
	private IAccountService accountService ;

	private static final Logger LOGGER = LoggerFactory.getLogger(DhServiceImpl.class);
	//存储兑换信息
	@Override
	@Transactional(value = "ztravel-txManager",isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED, rollbackFor=RuntimeException.class)
	public AjaxResponse adddh(Dhpo dh ) throws Exception{
		MemberSessionBean memberBean =  SSOUtil.getMemberSessionBean();
		AjaxResponse ajaxResponse = AjaxResponse.instance(ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_CODE,ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_MSG);
		try{
			AccountInfoBean accountInfo = accountService.getAccountInfo(memberBean.getMemberId());
			if(null != accountInfo){
				Long leftAmount = accountInfo.getAmount();
				if(leftAmount < dh.getDhMoney()){
					LOGGER.error("账户{}余额不足,账户余额{},兑换金额{}", memberBean.getMemberId(),leftAmount,dh.getDhMoney());
				}else{
					CommonResponse convertResponse = accountService.addAmount(memberBean.getMemberId(),-dh.getDhMoney());
					Dhpo dhpo = buildDhpo(dh.getDhPhonenum(), dh.getDhMoney(),memberBean);
					String dhpoId = idGeneratorUtil.getAccountConvertId();
					dhpo.setDhId(dhpoId);
					orderdhdao.insert(dhpo);
					if(convertResponse.isSuccess()){
						ajaxResponse.setRes_code(ResponseConstBackMemb.MEMB_INSERT_DH_STATUS_SUCCESS_CODE);
						ajaxResponse.setRes_msg(ResponseConstBackMemb.MEMB_INSERT_DH_STATUS_SUCCESS_MSG);
					}else{
						LOGGER.error("账户金额扣除错误", convertResponse.getErrMsg());
						throw ZtrBizException.instance(ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_CODE,ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_MSG);
					}
				}
			}else{
				LOGGER.error("兑换账户不存在", memberBean.getMemberId());
			}
		}catch(Exception e){
			LOGGER.error("服务层存储兑换数据错误", e);
			ajaxResponse.setRes_code(ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_CODE);
			ajaxResponse.setRes_msg(ResponseConstBackMemb.MEMB_INSERT_DH_ERROR_MSG);
		}
		return ajaxResponse;
	}


	public Dhpo buildDhpo(String mobile,Long convertAmount,MemberSessionBean memberBean){
		Dhpo dhpo = new Dhpo();
		dhpo.setDhPhonenum(mobile);
		dhpo.setMemberName(memberBean.getNickName());
		dhpo.setMemberId(memberBean.getMid());
		dhpo.setPledhTime(new Date());
		dhpo.setDhMoney(convertAmount);
		dhpo.setDhStatus(AccountCovertStatus.UNCONVERT.name());
		dhpo.setDhContent(AccountConvertContent.MOBILECARD.name());
		dhpo.setLastOperator(memberBean.getMid());
		return dhpo;
	}

}
