package com.ztravel.common.constants;

/**
 * @author meihua.piao
 * C端产品模块常量
 *
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 */
public class ProductConsFront {
	/**
	 * ------------------××××××C端产品列表×××××××----------------------
	 * */
	public static final String PROD_SEARCH_PRODUCT_LIST_ERROR_CODE = "FF_PROD_0001";
	public static final String PROD_SEARCH_PRODUCT_LIST_ERROR_MSG = "查询产品列表失败";
}
