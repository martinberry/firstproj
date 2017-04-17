package com.ztravel.common.constants;

/**
 * 产品预订常量
 * @author liuzhuo
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * PDBK--product book
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 */
public class ProductBookConstans {

	/**
	 * 产品库存不足
	 */
	public static final String PRODUCT_STOCK_NOT_ENOUGH = "EF_PDBK_1001";

	/**
	 * 产品金额变化
	 */
	public static final String PRODUCT_PRICE_CHANGED = "EF_PDBK_1002";

	/**
	 * 用户未注册
	 */
	public static final String USER_NOT_REGISTER = "EF_PDBK_1003";

	/**
	 * 用户未登录
	 */
	public static final String USER_NOT_LOGIN = "EF_PDBK_1004";

	/**
	 * 提交订单失败
	 */
	public static final String APPLY_ORDER_FAILURE = "EF_PDBK_1005";

	/**
	 * 订单金额不一致
	 */
	public static final String PRODUCT_PRICE_NOT_EQUAL = "EF_PDBK_1006";

	/**
	 * 产品提交订单成功
	 */
	public static final String PRODUCT_APPLY_ORDER_SUCCESS = "SF_PDBK_1007";

	/**
	 *用户登录失败
	 */
	public static final String LOGIN_FAILURE = "EF_PDBK_1008";

	/**
	 * 用户挂起建单失败
	 */
	public static final String NOT_ACTIVE_TO_APPLY_ORDER_FAILURE = "EF_PDBK_1009";

	/**
	 * 旅客姓名格式错误
	 */
	public static final String PASSENGER_NAME_ERROE_ORDER_FAILURE = "EF_PDBK_1010";

	/**
	 * 产品快照key
	 */
	public static final String PRODUCT_SNAPSHOT_KEY = "ps_key";

}
