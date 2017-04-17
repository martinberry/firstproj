package com.ztravel.common.constants;

/**
 * 微信用户模块常量
 * @author MH
 *
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台  W-->微信
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 */
public class WxMembConst {

	public static final String REDIS_KEY_VERIFYCODE_CHANGE_MOBILE = "CHANGE_MOBILE_VERIFYCODE_KEY";

	public static final String MEMB_DELETE_TRAVELLER_SUCCESS_CODE = "SW_MEMB_0001";
	public static final String MEMB_DELETE_TRAVELLER_SUCCESS_MSG = "删除常旅客成功";

	public static final String MEMB_DELETE_TRAVELLER_ERROR_CODE = "FW_MEMB_0002";
	public static final String MEMB_DELETE_TRAVELLER_ERROR_MSG = "删除常旅客失败";

	public static final String MEMB_GET_TRAVELLER_BY_ID_ERROR_CODE = "FW_MEMB_0003";
	public static final String MEMB_GET_TRAVELLER_BY_ID_ERROR_MSG = "根据id查不到常旅客";

	public static final String MEMB_INVALID_MOBILE_NUMBER_ERROR_CODE = "FW_MEMB_0004";
	public static final String MEMB_INVALID_MOBILE_NUMBER_ERROR_MSG = "手机号格式错误";

	public static final String MEMB_MOBILE_ALREADY_EXISTS_ERROR_CODE = "FW_MEMB_0005";
	public static final String MEMB_MOBILE_ALREADY_EXISTS_ERROR_MSG = "该手机号已经注册";

	public static final String MEMB_SEND_VERIFICATION_CODE_SUCCESS_CODE = "SW_MEMB_0006";
	public static final String MEMB_SEND_VERIFICATION_CODE_SUCCESS_MSG = "发送验证码成功";

	public static final String MEMB_SEND_VERIFICATION_CODE_ERROR_CODE = "FW_MEMB_0006";
	public static final String MEMB_SEND_VERIFICATION_CODE_ERROR_MSG = "发送验证码失败";

	public static final String MEMB_MODIFY_MEMBER_INFO_VALIDATION_ERROR_CODE = "FW_MEMB_0007";
	public static final String MEMB_MODIFY_MEMBER_INFO_VALIDATION_ERROR_MSG = "修改会员信息格式校验错误";

	public static final String MEMB_VERIFICATION_CODE_VERIFY_ERROR_CODE = "FW_MEMB_0008";
	public static final String MEMB_VERIFICATION_CODE_VERIFY_ERROR_MSG = "验证码校验失败";

	public static final String MEMB_UPDATE_MEMBER_INFO_SUCCESS_CODE = "SW_MEMB_0009";
	public static final String MEMB_UPDATE_MEMBER_INFO_SUCCESS_MSG = "修改会员信息成功";

	public static final String MEMB_UPDATE_MEMBER_INFO_ERROR_CODE = "FW_MEMB_0010";
	public static final String MEMB_UPDATE_MEMBER_INFO_ERROR_MSG = "修改会员信息失败";

	public static final String MEMB_UPDATE_TRAVELLER_INFO_SUCCESS_CODE = "SW_MEMB_0011";
	public static final String MEMB_UPDATE_TRAVELLER_INFO_SUCCESS_MSG = "修改常旅客信息成功";

	public static final String MEMB_UPDATE_TRAVELLER_INFO_ERROR_CODE = "FW_MEMB_1012";
	public static final String MEMB_UPDATE_TRAVELLER_INFO_ERROR_MSG = "修改常旅客信息失败";

	public static final String MEMB_MODIFY_TRAVELLER_INFO_VALIDATION_ERROR_CODE = "FW_MEMB_1013";
	public static final String MEMB_MODIFY_TRAVELLER_INFO_VALIDATION_ERROR_MSG = "修改常旅客信息校验失败";

	public static final String MEMB_CHANGE_PASSWORD_OLDPWD_WRONG_ERROR_CODE = "FW_MEMB_1014";
	public static final String MEMB_CHANGE_PASSWORD_OLDPWD_WRONG_ERROR_MSG = "原密码输入错误";

	public static final String MEMB_CHANGE_PASSWORD_OLDPWD_CHECK_FAILED_CODE = "FW_MEMB_1015";
	public static final String MEMB_CHANGE_PASSWORD_OLDPWD_CHECK_FAILED_MSG = "原密码校验异常";

	public static final String MEMB_CHANGE_PASSWORD_NEWPWD_INVALID_ERROR_CODE = "FW_MEMB_1016";
	public static final String MEMB_CHANGE_PASSWORD_NEWPWD_INVALID_ERROR_MSG = "新密码格式不合法";

	public static final String MEMB_CHANGE_PASSWORD_NEWPWD_NOT_EQUAL_ERROR_CODE = "FW_MEMB_1017";
	public static final String MEMB_CHANGE_PASSWORD_NEWPWD_NOT_EQUAL_ERROR_MSG = "新密码两次输入不一致";

	public static final String MEMB_CHANGE_PASSWORD_SUCCESS_CODE = "SW_MEMB_1018";
	public static final String MEMB_CHANGE_PASSWORD_SUCCESS_MSG = "修改密码成功";

	public static final String MEMB_CHANGE_PASSWORD_FAILED_CODE = "FW_MEMB_1019";
	public static final String MEMB_CHANGE_PASSWORD_FAILED_MSG = "修改密码失败";

	public static final String MEMB_EMAIL_ALREADY_EXISTS_ERROR_CODE = "FW_MEMB_1020";
	public static final String MEMB_EMAIL_ALREADY_EXISTS_ERROR_MSG = "该邮箱已存在";

}
