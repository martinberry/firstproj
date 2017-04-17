package com.ztravel.paygate.web.dto.request;

/**
 * 退款查询请求
 * 
 * @author dingguangxian
 * 
 */
public class RequestRefundQueryBean extends AbstractRequestBean{
	private static final long serialVersionUID = -549525815907402766L;
	/**
	 * 退款标识号
	 */
	private String refundNum;

	public String getRefundNum() {
		return refundNum;
	}

	public void setRefundNum(String refundNum) {
		this.refundNum = refundNum;
	}

}
