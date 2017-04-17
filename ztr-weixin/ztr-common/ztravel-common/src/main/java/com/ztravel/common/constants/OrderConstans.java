package com.ztravel.common.constants;

/**
 * 订单模块常量
 * @author liuzhuo
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * ORDR--order
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 */
public class OrderConstans {

	/**
	 * 订单创建成功
	 */
	public static final String ORDER_CREATE_SUCCESS = "SF_ORDR_1001";

	/**
	 * 查不到订单
	 */
	public static final String ORDER_ORDER_NOT_EXIST_CODE = "FF_ORDR_1002";
	public static final String ORDER_ORDER_NOT_EXIST_MSG = "订单不存在";

	/**
	 * 订单编号前缀
	 */
	public static final String ORDER_NUM_PREFIX = "ZTO";


	/**
	 * 订单发放礼盒失败
	 */
	public static final String ORDER_SEND_GIFT_FAILED = "FO_ORDR_1002";
	public static final String ORDER_SEND_GIFT_SUCCESS = "SO_ORDR_1003";

	/**
	 * 订单OP确认失败
	 */
	public static final String ORDER_OP_CONFIRM_FAILED = "FO_ORDR_1004";
	public static final String ORDER_OP_CONFIRM_SUCCESS = "SO_ORDR_1005";

	/**
	 * 订单出行通知
	 */
	public static final String ORDER_OUTNOTICE_FAILED = "FO_ORDR_1006";
	public static final String ORDER_OUTNOTICE_SUCCESS = "SO_ORDR_1007";

	/**
	 * 取消订单
	 */
	public static final String ORDER_CANCLE_FAILED = "FO_ORDR_1008";
	public static final String ORDER_CANCLE_SUCCESS = "SO_ORDR_1009";


	/**
	 * ------------------××××××后台订单管理模块×××××××----------------------
	 * */
	public static final String ORDER_SAVE_REMARKS_FAILED_CODE = "FO_ORDR_1010";
	public static final String ORDER_SAVE_REMARKS_FAILED_MSG = "保存操作备注失败";
	public static final String ORDER_SAVE_REMARKS_SUCCESS_CODE = "SO_ORDR_1011";
	public static final String ORDER_SAVE_REMARKS_SUCCESS_MSG = "保存操作备注成功";

	public static final String ORDER_GET_ORDER_BY_ID_FAILED_CODE = "FO_ORDR_1012";
	public static final String ORDER_GET_ORDER_BY_ID_FAILED_MSG = "查询不到订单信息";

	public static final String ORDER_GET_ORDER_PRODUCT_FAILED_CODE = "FO_ORDR_1013";
	public static final String ORDER_GET_ORDER_PRODUCT_FAILED_MSG = "查询不到与订单相关联的产品";

	public static final String ORDER_GET_ORDER_CONTACTOR_FAILED_CODE = "FO_ORDR_1014";
	public static final String ORDER_GET_ORDER_CONTACTOR_FAILED_MSG = "查询不到订单联系人信息";

	public static final String ORDER_GET_ORDER_TRAVELLERS_FAILED_CODE = "FO_ORDR_1015";
	public static final String ORDER_GET_ORDER_TRAVELLERS_FAILED_MSG = "查询订单旅客信息失败";

	public static final String ORDER_GET_ORDER_LOGS_FAILED_CODE = "FO_ORDR_1016";
	public static final String ORDER_GET_ORDER_LOGS_FAILED_MSG = "查询不到订单操作日志";

	public static final String ORDER_GET_ORIGINAL_ORDER_FAILED_CODE = "FO_ORDR_1017";
	public static final String ORDER_GET_ORIGINAL_ORDER_FAILED_MSG = "查询不到原订单";




	/**************************订单评价模块常量*******************************/
	/**
	 * 前台发表评论成功
	 */
	public static final String ORDER_COMMENT_INSERT_SUCCESS_CODE = "SF_ORDR_2001";

	/**
	 * 前台发表评论 验证失败
	 */
	public static final String ORDER_COMMENT_INSERT_VALID_ERR = "FF_ORDR_2002";

	/**
	 * 后台评论审核成功
	 */
	public static final String ORDER_COMMENT_AUDIT_SUCCESS_CODE = "SO_ORDR_2003";

	/**
	 * 后台评论审核失败
	 */
	public static final String ORDER_COMMENT_AUDIT_ERROR_CODE = "FO_ORDR_2004";

	/**
	 * 前台发表评论失败
	 */
	public static final String ORDER_COMMENT_INSERT_ERR_CODE = "FF_ORDR_2005";

	/**
	 * 前台发表评论失败
	 */
	public static final String ORDER_COMMENT_INSERT_ERR_MSG = "前台发表评论失败";

	/**
	 * 无权评价
	 */
	public static final String ORDER_COMMENT_HAVE_NO_RIGHT_TO_COMMENT_ERROR_CODE = "FF_ORDR_2006";
	public static final String ORDER_COMMENT_HAVE_NO_RIGHT_TO_COMMENT_ERROR_MSG = "该订单不属于当前用户,不能进行评价";

	public static final String ORDER_COMMENT_ALREADY_COMMENTED_ERROR_CODE = "FF_ORDR_2007";
	public static final String ORDER_COMMENT_ALREADY_COMMENTED_ERROR_MSG = "订单已评价，不能再进行评价";

}
