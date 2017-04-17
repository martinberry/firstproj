package com.ztravel.common.exception;

/**
 * 真旅行统一业务异常处理类
 * @author liuzhuo
 *
 */

public class ZtrBizException extends RuntimeException{

	/**
	 * gen serialVersionUID
	 */
	private static final long serialVersionUID = -697086171597616584L;
	
	private String retCode;
	
	private String retMsg;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	
	public ZtrBizException() {
		
	}
	
	private ZtrBizException(String retCode, String retMsg) {
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
	
	private ZtrBizException(String retCode, String retMsg, Exception e) {
		super(e);
		this.retCode = retCode;
		this.retMsg = retMsg;
	}
	
	public static ZtrBizException instance(String retCode, String retMsg) {
		return new ZtrBizException(retCode, retMsg);
	}
	
	public static ZtrBizException instance(String retCode, String retMsg, Exception e) {
		return new ZtrBizException(retCode, retMsg, e);
	}
	

}
