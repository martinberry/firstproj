package com.ztravel.member.front.event;

import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableSet;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.ztravel.common.event.AbstractEventListener;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.common.payment.AccountCreateBean;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.member.client.service.ICouponClientService;
import com.ztravel.payment.service.IAccountService;
import com.ztravel.reuse.member.event.MemberEvent;

/**
 * @author haofan.wan
 *
 */
@Component
public class MemberEventListener extends AbstractEventListener {
	private final static Logger logger = LoggerFactory.getLogger(MemberEventListener.class);

	@Resource(name="memberEventBus")
    private EventBus memberEventBus;

	@Resource
	private IAccountService accountService ;

	@Resource
	private ICouponClientService couponClientService ;

	@Override
    protected Set<EventBus> getEventBusToRegister() {
        return ImmutableSet.of(memberEventBus);
    }

	@Subscribe
	public void eventHandler(MemberEvent event) {
		TokenHolder.set(event.getToken());
		switch (event.getType()) {
		case LOGIN:
			login(event) ;
			break;
		case REGISTER:
			register(event) ;
			break;
		case RANDL:
			if(register(event)){
				login(event) ;
			}
			break;
		default:
			break;
		}
	}

	public boolean register(MemberEvent event) {
		String memberId = event.getMemberId();
		logger.info("用户注册生成账户,memberId: {}", memberId);
		AccountCreateBean bean = new AccountCreateBean() ;
		bean.setMemberId(memberId);
		CommonResponse response = new CommonResponse() ;
		response.setSuccess(false);
		try{
			response = accountService.createAccount(bean) ;
		}catch(Exception e){
			logger.error("IAccountService error", e);
		}
		logger.info("用户注册生成账户结果: {}", response.isSuccess() ? "成功" : response.getErrMsg());
		return response.isSuccess() ;
	}

	public void login(MemberEvent event) {
		String memberId = event.getMemberId();
		couponClientService.grantCoupon(memberId);
	}

}
