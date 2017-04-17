package com.ztravel.common.constants;

/**
 * @author wanhaofan
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
public class ResponseConstants {
	public static final String MEMB_SUSPEND_ERROR_CODE = "EF_MEMB_1020" ;
	public static final String MEMB_SUSPEND_ERROR_MSG = "会员已挂起" ;


	/**
	 * ------------------××××××C端注册流程业务字典表×××××××----------------------
	 * */
	public static final String MEMB_VERIFYMOBILE_SUCCESS_CODE = "SF_MEMB_1001" ;
	public static final String MEMB_VERIFYMOBILE_SUCCESS_MSG = "手机号码校验成功" ;

	public static final String MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_CODE = "EF_MEMB_1002" ;
	public static final String MEMB_VERIFYMOBILE_ALREADYEXISTS_ERROR_MSG = "手机号已被注册" ;

	public static final String MEMB_VERIFYMOBILE_PROGRAM_ERROR_CODE = "EF_MEMB_1003" ;
	public static final String MEMB_VERIFYMOBILE_PROGRAM_ERROR_MSG = "C端手机号码校验失败,程序错误" ;

	public static final String MEMB_REGISTER_SUCCESS_CODE = "SF_MEMB_1000" ;
	public static final String MEMB_REGISTER_SUCCESS_MSG = "注册成功" ;

	public static final String MEMB_REGISTER_ERROR_CODE = "EF_MEMB_1000" ;
	public static final String MEMB_REGISTER_ERROR_MSG = "C端用户注册失败" ;

	public static final String MEMB_VERIFYCODE_SENDSMS_ERROR_CODE = "EF_MEMB_1010" ;
	public static final String MEMB_VERIFYCODE_SENDSMS_ERROR_MSG = "发送短信验证码失败" ;

	public static final String MEMB_VERIFYCODE_SENDSMS_SUCCESS_CODE = "SF_MEMB_1010" ;
	public static final String MEMB_VERIFYCODE_SENDSMS_SUCCESS_MSG = "发送短信验证码成功" ;

	public static final String MEMB_VERIFYCODE_VERIFY_FAIL_CODE = "EF_MEMB_1011" ;
	public static final String MEMB_VERIFYCODE_VERIFY_FAIL_MSG = "输入的验证码有误" ;

	public static final String MEMB_VERIFYCODE_VERIFY_ERROR_CODE = "EF_MEMB_1012" ;
	public static final String MEMB_VERIFYCODE_VERIFY_ERROR_MSG = "验证码校验过程错误" ;

	public static final String MEMB_IMAGE_TOOLARGE_ERROR_CODE = "EF_MEMB_1013" ;
	public static final String MEMB_IMAGE_TOOLARGE_ERROR_MSG = "用户头像图片大于2M" ;

	public static final String MEMB_IMAGE_WRONGTYPE_ERROR_CODE = "EF_MEMB_1014" ;
	public static final String MEMB_IMAGE_WRONGTYPE_ERROR_MSG = "用户头像图片类型不合法" ;

	public static final String MEMB_NOTFOUND_ERROR_CODE = "EF_MEMB_1015" ;
	public static final String MEMB_NOTFOUND_ERROR_MSG = "没找到用户" ;

	public static final String MEMB_IMAGE_UPDATE_ERROR_CODE = "EF_MEMB_1016" ;
	public static final String MEMB_IMAGE_UPDATE_ERROR_MSG = "用户头像更新失败" ;

	public static final String MEMB_IMAGE_UPDATE_SUCCESS_CODE = "SF_MEMB_1002" ;
	public static final String MEMB_IMAGE_UPDATE_SUCCESS_MSG = "用户头像更新成功" ;

	public static final String MEMB_MOBILE_ILLEGE_CODE = "EF_MEMB_1017" ;
	public static final String MEMB_MOBILE_ILLEGE_MSG = "用户手机号码不合法" ;

	public static final String MEMB_EMAIL_ILLEGE_CODE = "EF_MEMB_1018" ;
	public static final String MEMB_EMAIL_ILLEGE_MSG = "用户邮箱不合法" ;

	public static final String MEMB_EMAIL_ALREADYEXISTS_CODE = "EF_MEMB_1020" ;
	public static final String MEMB_EMAIL_ALREADYEXISTS_MSG = "用户邮箱已存在" ;

	public static final String MEMB_NICKNAME_ILLEGE_CODE = "EF_MEMB_1019" ;
	public static final String MEMB_NICKNAME_ILLEGE_MSG = "用户昵称不合法" ;

	public static final String MEMB_NICKNAME_ALREADYEXISTS_CODE = "EF_MEMB_1021" ;
	public static final String MEMB_NICKNAME_ALREADYEXISTS_MSG = "用户昵称已存在" ;

	public static final String MEMB_NICKNAME_SUCCESS_CODE = "SF_MEMB_1003" ;
	public static final String MEMB_NICKNAME_SUCCESS_MSG = "用户昵称校验通过" ;

	public static final String MEMB_EMAIL_SUCCESS_CODE = "SF_MEMB_1004" ;
	public static final String MEMB_EMAIL_SUCCESS_MSG = "用户邮箱校验通过" ;

	public static final String MEMB_NICKNAME_PROGRAM_ERROR_CODE = "EF_MEMB_1022" ;
	public static final String MEMB_NICKNAME_PROGRAM_ERROR_MSG = "用户昵称校验失败" ;

	public static final String MEMB_EMAIL_PROGRAM_ERROR_CODE = "EF_MEMB_1023" ;
	public static final String MEMB_EMAIL_PROGRAM_ERROR_MSG = "用户邮箱校验失败" ;

	public static final String MEMB_PASSWORD_ERROR_CODE = "EF_MEMB_1024" ;
	public static final String MEMB_PASSWORD_ERROR_MSG = "用户密码不合法" ;

	public static final String MEMB_IMPROVEDATA_ERROR_CODE = "EF_MEMB_1025" ;
	public static final String MEMB_IMPROVEDATA_ERROR_MSG = "用户完善资料失败" ;

	public static final String MEMB_CANTRECSELF_ERROR_CODE = "EF_MEMB_1040" ;
	public static final String MEMB_CANTRECSELF_ERROR_MSG = "不能推荐自己" ;


	public static final String MEMB_TAKEMEBACK_CODE = "TAKE_ME_BACK" ;

	public static final String MEMB_LOGIN_FAIL_CODE = "EF_MEMB_1026" ;

	public static final String MEMB_NOT_ACTIVE_CODE = "EF_MEMB_10261" ;
    public static final String MEMB_NOT_ACTIVE_MSG = "用户已锁定" ;

	public static final String MEMB_LOGIN_SUCCESS_CODE = "SF_MEMB_1005" ;
	public static final String MEMB_LOGIN_SUCCESS_MSG = "用户登陆成功" ;

    public static final String MEMB_BINDED_OPENID_CODE = "SF_MEMB_1050" ;
    public static final String MEMB_BINDED_OPENID_MSG = "用户已绑定微信账号" ;

    public static final String OPENID_REGISTERED_CODE = "SF_MEMB_1051" ;
    public static final String OPENID_REGISTERED_MSG = "该微信号已经注册啦" ;

    public static final String OPENID_BINDED_MOBILE_CODE = "SF_MEMB_1052" ;
    public static final String OPENID_BINDED_MOBILE_MSG = "该微信号已经绑定手机号啦" ;

    public static final String ACCOUNT_PASSWORD_IS_NULL_CODE = "SF_MEMB_1053" ;
    public static final String ACCOUNT_PASSWORD_IS_NULL_MSG = "输入账号或密码不能为空" ;

    public static final String GET_OPENID_FAIL_CODE = "SF_MEMB_1060" ;
    public static final String GET_OPENID_FAIL_MSG = "获取OPENID失败" ;

    public static final String INSERT_WX_USER_FAIL_CODE = "SF_MEMB_1061" ;
    public static final String INSERT_WX_USER_FAIL_MSG = "插入微信用户信息失败" ;

    public static final String REDIRECT_WX_BIND_PAGE_CODE = "SF_MEMB_1062" ;
    public static final String REDIRECT_WX_BIND_PAGE_MSG = "转入微信绑定页面" ;

	public static final String MEMB_LOGIN_VERIFYCODEERROR_CODE = "EF_MEMB_1027" ;

	public static final String MEMB_LOGIN_VERIFYCODEERROR_MSG = "用户登陆验证码验证失败" ;


	public static final String MEMB_SAME_MEMBER_ACCOUNT = "SAME_MEMBER_ACCOUNT" ;

	public static final String MEMB_LOGIN_ERROR_CODE = "EF_MEMB_1028" ;
	public static final String MEMB_LOGIN_ERROR_MSG = "用户登录过程异常" ;


	public static final String MEMB_UPDATE_LAST_LOGIN_DATE_ERROR_CODE = "EF_MEMB_1029" ;


	public static final String MEMB_FINDPASSWORD_BYMOBILE_SUCCESS_CODE = "SF_MEMB_1006" ;
	public static final String MEMB_FINDPASSWORD_BYMOBILE_SUCCESS_MSG = "用户找回密码验证手机验证码成功" ;

	public static final String MEMB_RESET_PASSWORD_BYMOBILE_ERROR_CODE = "EF_MEMB_1030" ;
	public static final String MEMB_RESET_PASSWORD_BYMOBILE_ERROR_MSG = "用户手机重置密码失败" ;

	public static final String MEMB_RESET_PASSWORD_BYMOBILE_SUCCESS_CODE = "SF_MEMB_1007" ;
	public static final String MEMB_RESET_PASSWORD_BYMOBILE_SUCCESS_MSG = "用户手机重置密码成功" ;

	public static final String MEMB_RESET_PASSWORD_BYEMAIL_SUCCESS_CODE = "SF_MEMB_1008" ;
	public static final String MEMB_RESET_PASSWORD_BYEMAIL_SUCCESS_MSG = "用户邮箱重置密码成功" ;

	public static final String MEMB_PASSWORD_NOTSAME_CODE = "EF_MEMB_1031" ;
	public static final String MEMB_PASSWORD_NOTSAME_MSG = "重置密码不一样" ;

	public static final String MEMB_PASSWORD_ILLEGE_ERROR_CODE = "EF_MEMB_1032" ;
	public static final String MEMB_PASSWORD_ILLEGE_ERROR_MSG = "重置密码新密码不合法" ;

	public static final String MEMB_MAIL_NOT_FOUND_ERROR_CODE = "EF_MEMB_1033" ;
	public static final String MEMB_MAIL_NOT_FOUND_ERROR_MSG = "邮箱没有找到" ;

	public static final String MEMB_RESET_PASSWORD_BYEMAIL_ERROR_CODE = "EF_MEMB_1034" ;
	public static final String MEMB_RESET_PASSWORD_BYEMAIL_ERROR_MSG = "用户邮箱重置密码失败" ;

	public static final String MEMB_SKIP_IMPROVE_PERSONAL_DATA_ERROR_CODE = "EF_MEMB_1035" ;
	public static final String MEMB_SKIP_IMPROVE_PERSONAL_DATA_ERROR_MSG = "跳过补全个人信息异常" ;

	public static final String MEMB_SKIP_IMPROVE_PERSONAL_DATA_SUCCESS_CODE = "SF_MEMB_1009" ;
	public static final String MEMB_SKIP_IMPROVE_PERSONAL_DATA_SUCCESS_MSG = "跳过补全个人信息成功" ;

	public static final String MEMB_MAIL_SEND_SUCCESS_CODE = "SF_MEMB_1008" ;
	public static final String MEMB_MAIL_SEND_SUCCESS_MSG = "找回密码发送邮件成功" ;

	public static final String MEMB_MAIL_ALREADY_SEND_CODE = "EF_MEMB_1036" ;
	public static final String MEMB_MAIL_ALREADY_SEND_MSG = "邮件已经发送" ;

	public static final String MEMB_MOBILE_NOT_FOUND_ERROR_CODE = "EF_MEMB_1036" ;
	public static final String MEMB_MOBILE_NOT_FOUND_ERROR_MSG = "手机号码未注册" ;

	//常旅客
	public static final String TRAVELERINFO_REPEATENAME_ILLEGE_CODE = "FF_TRAVLERINFO_1015" ;
	public static final String TRAVELERINFO_REPEATENAME_ILLEGE_MSG = "旅客重名" ;

	public static final String TRAVELERINFO_MOBILE_ILLEGE_CODE = "FF_TRAVLERINFO_1001" ;
	public static final String TRAVELERINFO_MOBILE_ILLEGE_MSG = "用户手机号码不合法" ;

	public static final String TRAVELERINFO_EMAIL_ILLEGE_CODE = "FF_TRAVLERINFO_1002" ;
	public static final String TRAVELERINFO_EMAIL_ILLEGE_MSG = "用户邮箱不合法" ;

	public static final String TRAVELERINFO_FIRSTNAME_ILLEGE_CODE = "FF_TRAVLERINFO_1003" ;
	public static final String TRAVELERINFO_FIRSTNAME_ILLEGE_MSG = " 请输入0~20个拼音或中文！" ;

	public static final String TRAVELERINFO_LASTNAME_ILLEGE_CODE = "FF_TRAVLERINFO_1004" ;
	public static final String TRAVELERINFO_LASTNAME_ILLEGE_MSG = " 请输入0~20个拼音或中文！" ;

	public static final String TRAVELERINFO_CREDIT_ILLEGE_CODE = "FF_TRAVLERINFO_1005" ;
	public static final String TRAVELERINFO_CREDIT_ILLEGE_MSG = "身份证件号码不合法" ;

	public static final String TRAVELERINFO_PASSPORT_ILLEGE_CODE = "FF_TRAVLERINFO_1006" ;
	public static final String TRAVELERINFO_PASSPORT_ILLEGE_MSG = "护照号码不合法" ;

	public static final String TRAVELERINFO_GANGAOPASS_ILLEGE_CODE = "FF_TRAVLERINFO_1007" ;
	public static final String TRAVELERINFO_GANGAOPASS_ILLEGE_MSG = "港澳通行证号码不合法" ;

	public static final String TRAVELERINFO_CRENDITIALS_ILLEGE_CODE = "FF_TRAVLERINFO_1008" ;
	public static final String TRAVELERINFO_CRENDITIALS_ILLEGE_MSG = "证件不合法" ;

	public static final String TRAVELERINFO_NAMEEMPTY_ILLEGE_CODE = "FF_TRAVLERINFO_1009";
	public static final String TRAVELERINFO_NAMEEMPTY_ILLEGE_MSG = "中英文名字二选一" ;

	public static final String TRAVELERINFO_CREDITSIZE_ILLEGE_CODE = "FF_TRAVLERINFO_1010" ;
	public static final String TRAVELERINFO_CREDITSIZE_ILLEGE_MSG = "证件数量不能大于三" ;

	public static final String TRAVELERINFO_FIRSTENNAME_ILLEGE_CODE = "FF_TRAVLERINFO_1011" ;
	public static final String TRAVELERINFO_FIRSTENNAME_ILLEGE_MSG = " 请输入姓氏：0~20个英文字符" ;

	public static final String TRAVELERINFO_LASTENNAME_ILLEGE_CODE = "FF_TRAVLERINFO_1012" ;
	public static final String TRAVELERINFO_LASTENNAME_ILLEGE_MSG = " 请输入名字：0~20个英文字符" ;

	public static final String TRAVELERINFO_NATIONALITY_ILLEGE_CODE = "FF_TRAVLERINFO_1013" ;
	public static final String TRAVELERINFO_NATIONALITY_ILLEGE_MSG = "不存在所填国籍" ;

	public static final String TRAVELERINFO_SAVE_SUCCESS_CODE = "SF_TRAVLERINFO_1000" ;
	public static final String TRAVELERINFO_SAVE_SUCCESS_MSG = "保存成功" ;
	/**
	 * ------------------××××××××××××××××××××××××××××××××××----------------------
	 * */


	/***
	 *
	 * 微信端余额兑换字段表
	 * */

	public static final String WX_ACCOUNT_CONVERT_SUCCESS_CODE = "SF_WXACCOUNT_CONVERT_0001" ;
	public static final String WX_ACCOUNT_CONVERT_SUCCESS_MSG = "兑换成功" ;

	public static final String WX_ACCOUNT_CONVERT_FAILED_CODE = "FF_WXACCOUNT_CONVERT_0002" ;
	public static final String WX_ACCOUNT_CONVERT_FAILED_MSG = "兑换失败" ;

	public static final String WX_CONVERT_MOBILE_EMPTY_CODE = "FF_WXACCOUNT_CONVERT_0003" ;
	public static final String WX_CONVERT_MOBILE_EMPTY_MSG = "手机号为空" ;

	public static final String WX_CONVERT_AMOUNT_EMPTY_CODE = "FF_WXACCOUNT_CONVERT_0004" ;
	public static final String WX_CONVERT_AMOUNT_EMPTY_MSG = "兑换金额为空" ;

	public static final String WX_CONVERT_AMOUNT_LESS_ZERO_CODE = "FF_WXACCOUNT_CONVERT_0005" ;
	public static final String WX_CONVERT_AMOUNT_LESS_ZERO_MSG = "兑换金额为负数" ;

	/***
	 *
	 * Web端余额兑换字段表
	 * */

	public static final String WEB_CONVERT_MOBILE_EMPTY_CODE = "FF_WEBACCOUNT_CONVERT_0003" ;
	public static final String WEB_CONVERT_MOBILE_EMPTY_MSG = "手机号为空" ;

	public static final String WEB_CONVERT_AMOUNT_EMPTY_CODE = "FF_	WEBACCOUNT_CONVERT_0004" ;
	public static final String WEB_CONVERT_AMOUNT_EMPTY_MSG = "兑换金额为空" ;

	public static final String WEB_CONVERT_AMOUNT_LESS_ZERO_CODE = "FF_WEBACCOUNT_CONVERT_0005" ;
	public static final String WEB_CONVERT_AMOUNT_LESS_ZERO_MSG = "兑换金额为负数" ;

	/**
	 * 长连接响应常量定义
	 * */
	public static final String WEB_LONG_POLL_OLD_USER_LOGIN_CODE = "SF_LONG_POLL_OLD_USER_LOGIN_001";
	public static final String WEB_LONG_POLL_OLD_USER_LOGIN_MSG = "已有真旅行账户扫码";

	public static final String WEB_LONG_POLL_NEW_USER_LOGIN_CODE = "SF_LONG_POLL_NEW_USER_LOGIN_001";
	public static final String WEB_LONG_POLL_NEW_USER_LOGIN_MSG = "新用户扫码" ;

	public static final String WEB_LONG_POLL_USER_LOGINED_CODE = "SF_LONG_POLL_USER_LOGINED_001";
    public static final String WEB_LONG_POLL_USER_LOGINED_MSG = "用户已登陆" ;

	public static final String WEB_LONG_POLL_FAILED_CODE = "FF_LONG_POLL_FAILED_001";
	public static final String WEB_LONG_POLL_FAILED_MSG = "长连接请求失败" ;

	/**
	 *  ------------------××××××填坑专用数据字段,请勿在此提交×××××××----------------------
	 */

	public static final int OPERA_AJAX_CALL_TIME_OUT_STATUS = 901;
	public static final int MEMBE_AJAX_CALL_TIME_OUT_STATUS = 902;

	/**
	 *  ------------------××××××填坑专用数据字段,请勿在此提交×××××××----------------------
	 */
	
	public static final String SENSITIVE_WORD = "SENSITIVE_WORD" ;
	
}