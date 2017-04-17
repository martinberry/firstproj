package com.ztravel.paygate.web.dto.request;

import java.io.Serializable;

import com.ztravel.paygate.core.enums.GateType;

/**
 * 接收客户端请求的数据模型
 * 
 * @author dingguangxian
 * 
 */
public abstract class AbstractRequestBean implements Serializable {
	private static final long serialVersionUID = 4202288140133564468L;
	private String clientId;// 请求来源
	private String gateType;// 网关类型
	private String sign;// 签约串，用于对客户端请求数据有效性进行校验

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getGateType() {
		return gateType;
	}

	public void setGateType(String gateType) {
		this.gateType = gateType;
	}
	/**
	 * 获取对应的网支类型
	 * @return
	 */
	public GateType gateType(){
		for(GateType type : GateType.values()){
			if(type.code.equals(this.getGateType())){
				return type;
			}
		}
		return null;
	}

}
