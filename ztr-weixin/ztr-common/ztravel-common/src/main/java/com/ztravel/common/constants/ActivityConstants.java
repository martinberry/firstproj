package com.ztravel.common.constants;

public class ActivityConstants {

	/**
	 * 订单id自动生成序列名
	 */
	public static final String COUPON_ID_GEN_SEQ_NAME = "ztr_coupon";
	/**
	 * 活动模块常量
	 * @author pengfei.zhao
	 * 规范
	 * 1.第1位 S-->成功  F-->失败
	 * 2.第2位 F-->C端   O-->后台
	 * 3.第3,4,5,6位 表示模块
	 * 4.最后4位   表示状态码
	 */

	/**
	 * 创建活动
	 * */
	public static final String ACTIVITY_CREATE_SUCCESS = "SO_ACTIVITY_1001";
	public static final String ACTIVITY_CREATE_FAILED = "FO_ACTIVITY_1002";
	public static final String ACTIVITY_CREATE_FAILED_MSG = "创建活动失败";
	/**
	 * 活动生效
	 * */
	public static final String ACTIVITY_EFFEC_SUCCESS = "SO_ACTIVITY_1003";
	public static final String ACTIVITY_EFFEC_FAILED = "FO_ACTIVITY_1004";
	public static final String ACTIVITY_EFFEC_FAILED_MSG = "活动生效操作失败";
	/**
	 * 活动终止
	 * */
	public static final String ACTIVITY_TERMINATE_SUCCESS = "SO_ACTIVITY_1005";
	public static final String ACTIVITY_TERMINATE_FAILED = "FO_ACTIVITY_1006";
	public static final String ACTIVITY_TERMINATE_FAILED_MSG = "终止活动操作失败";

	/**
	 * 活动加券
	 * */
	public static final String ACTIVITY_ADDCOUPON_SUCCESS = "SO_ACTIVITY_1007";
	public static final String ACTIVITY_ADDCOUPON_FAILED = "FO_ACTIVITY_1008";
	public static final String ACTIVITY_ADDCOUPON_FAILED_MSG = "活动添加代金券失败";

	/**
	 * 活动删券
	 * */
	public static final String ACTIVITY_DELETECOUPON_SUCCESS = "SO_ACTIVITY_1009";
	public static final String ACTIVITY_DELETECOUPON_FAILED = "FO_ACTIVITY_1010";
	public static final String ACTIVITY_DELETECOUPON_FAILED_MSG = "活动删除代金券失败";

	/**
	 * 活动改券
	 * */
	public static final String ACTIVITY_EDITCOUPON_SUCCESS = "SO_ACTIVITY_1011";
	public static final String ACTIVITY_EDITCOUPON_FAILED = "FO_ACTIVITY_1012";
	public static final String ACTIVITY_EDITCOUPON_FAILED_MSG = "活动编辑代金券失败";

	/**
	 * 活动查券
	 * */
	public static final String ACTIVITY_SELECTCOUPON_SUCCESS = "SO_ACTIVITY_1013";
	public static final String ACTIVITY_SELECTCOUPON_FAILED = "FO_ACTIVITY_1014";
	public static final String ACTIVITY_SELECTCOUPON_FAILED_MSG = "查看券信息失败";

	/**
	 * 活动强行终止券
	 * */
	public static final String COUPON_TERMINATE_SUCCESS = "SO_ACTIVITY_1015";
	public static final String COUPON_TERMINATE_FAILED = "FO_ACTIVITY_1016";
	public static final String COUPON_TERMINATE_FAILED_MSG = "强行终止券操作失败";

	/**
	 * 活动送券
	 * */
	public static final String COUPON_SENDDING_SUCCESS = "SO_ACTIVITY_1017";
	public static final String COUPON_SENDDING_FAILED = "FO_ACTIVITY_1018";
	public static final String COUPON_SENDDING_FAILED_MSG = "发券失败";
	public static final String COUPON_VALIADTIME_ERROR_MSG = "代金券有效期错误";

	/**
	 * 大宁活动券系统状态更改
	 * */
	public static final String CHANEGE_VOUCHERTYPE_SUCCESS="SO_VOUCHER_1001";
	public static final String CHANEGE_VOUCHERTYPE_FAILED="FO_VOUCHER_1002";
	public static final String CHANEGE_VOUCHERTYPE_SUCCESS_MSG="修改系统占用状态成功";
	public static final String CHANEGE_VOUCHERTYPE_FAILED_MSG="修改系统占用状态失败";

	/**
	 * 大宁活动截止时间
	 *
	 * */
	public static final String DN_ACTIVITY_END="SO_DNEND_1001";
	public static final String DN_ACTIVITY_END_SUCCESS_MSG="大宁活动有效期截止";


	/**
	 * 活动送券
	 * */
	public static final String ACTIVITY_CONFIGURE_USER_SUCCESS = "SO_ACTIVITY_1019";
	public static final String ACTIVITY_CONFIGURE_USER_FAILED = "FO_ACTIVITY_1020";
	public static final String ACTIVITY_CONFIGURE_USER_FAILED_MSG = "用户信息错误";

	/**
	 * 活动参数校验失败
	 * */
	public static final String ACTIVITY_VALIDATE_SUCCESS = "SO_ACTIVITY_1021";
	public static final String ACTIVITY_VALIDATE_FAILED = "FO_ACTIVITY_1022";

	public static final String COUPON_VALIDATE_SUCCESS = "SO_ACTIVITY_1023";
	public static final String COUPON_VALIDATE_FAILED = "FO_ACTIVITY_1024";

	public static final String COUPONSHARED_TO_APPLY_ORDER_FAILED = "FO_ACTIVITY_1025";
	public static final String COUPONSHARED_TO_APPLY_ORDER_FAILED_MSG = "该代金券已失效,请重新选择";

	public static final String COUPONUSED_TO_APPLY_ORDER_FAILED = "FO_ACTIVITY_1026";
	public static final String COUPONUSED_TO_APPLY_ORDER_FAILED_MSG = "该代金券已使用,请重新选择";

	public static final String COUPONPRICECHANGED_TO_APPLY_ORDER_FAILED = "FO_ACTIVITY_1027";
	public static final String COUPONPRICECHANGED_TO_APPLY_ORDER_FAILED_MSG = "代金券优惠价格不一致,请重新选择";

}
