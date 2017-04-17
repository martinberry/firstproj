package com.ztravel.paygate.web.dto.bean;

import java.util.Date;
import java.util.Map;

import com.ztravel.paygate.core.enums.GateType;
import com.ztravel.paygate.core.enums.PayState;
/**
 * 
 * @author dingguangxian
 *
 */
public class PayBean<T> {
	private String payId;
	private String clientId;
	private String orderNum;
	private long amount;
	private String transComment;
	private GateType gateType;
	private String fgNotifyUrl;
	private String partner;
	private String sellerId;
	private String sellerAccount;
	private String traceNum;
	private String buyerAccount;
	private String buyerPartner;
	private Date transTime;
	private Date createTime;
	private Date respTime;
	private Date completeTime;
	private PayState payState;
	private String ackContent;
	private String confirmResult;
	private Date paymentTime;
	private String reqRetCode;
	private String reqRetMsg;
	private String valsignRetCode;
	private String valsignRetMsg;
	private Map<String,String> requestExtraInfos;
	private Map<String,String> responseExtraInfos;
	private String serviceName;
	private String returnUrl;
	private String notifyUrl;
	private String originTransState;
	
	private T payEntity;
	
	protected void transferFromEntity(T entity){
		
	}

	protected T transferToEntity(T entity){
		payEntity = entity;
		return payEntity;
	}
	
}
