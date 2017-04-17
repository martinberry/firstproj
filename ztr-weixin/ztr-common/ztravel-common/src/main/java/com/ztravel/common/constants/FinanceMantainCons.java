package com.ztravel.common.constants;

/**
 * @author junhui.xu
 * 处理AJAX的返回值
 *
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 * */
public class FinanceMantainCons {
	/**
	 * ------------------××××××后台产品×××××××----------------------
	 * */
	public static final String COUPONACCOUNT_AJAX_SUCCESS_CODE = "success";
	public static final String COUPONACCOUNT_AJAX_ERROR_CODE = "error";

	public static final String COUPONACCOUNT_FROZEN_ERROR_CODE = "FO_FROZEN_1001";
	public static final String COUPONACCOUNT_FROZEN_ERROR_MSG = "冻结账户失败";

	public static final String COUPONACCOUNT_FROZEN_SUCESS_CODE = "SO_FROZEN_1001";
	public static final String COUPONACCOUNT_FROZEN_SUCESS_MSG = "冻结账户成功";

	public static final String COUPONACCOUNT_UNFROZEN_ERROR_CODE = "FO_UNFROZEN_1002";
	public static final String COUPONACCOUNT_UNFROZEN_ERROR_MSG = "解冻账户失败";

	public static final String COUPONACCOUNT_UNFROZEN_SUCESS_CODE = "SO_UNFROZEN_1002";
	public static final String COUPONACCOUNT_UNFROZEN_SUCESS_MSG = "解冻账户成功";

}
