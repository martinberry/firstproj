package com.ztravel.paygate.web.dto.request;

/**
 * 查询请求
 * 
 * @author dingguangxian
 * 
 */
public class RequestQueryBean extends AbstractRequestBean{
	private static final long serialVersionUID = -4791396338598275382L;

	/**
	 * 订单号
	 */
	private String orderNum;

	public String getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

}
