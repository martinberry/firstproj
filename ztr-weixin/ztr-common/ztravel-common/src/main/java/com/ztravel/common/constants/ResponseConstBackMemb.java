package com.ztravel.common.constants;

/**
 * @author meihua.piao
 * 处理AJAX的返回值
 * 异常信息常量
 *
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 * */
public class ResponseConstBackMemb {
	/**
	 * ------------------××××××后台会员管理a×××××××----------------------
	 * */
	public static final String MEMB_MODIFY_MEMBER_STATUS_SUCCESS_CODE = "SO_MEMB_1001";
	public static final String MEMB_MODIFY_MEMBER_STATUS_SUCCESS_MSG = "修改会员状态成功";

	public static final String MEMB_MODIFY_MEMBER_STATUS_ERROR_CODE = "FO_MEMB_1001";
	public static final String MEMB_MODIFY_MEMBER_STATUS_ERROR_MSG = "修改会员状态失败";

	public static final String MEMB_SEARCH_MEMBER_ERROR_CODE = "FO_MEMB_1002";
	public static final String MEMB_SEARCH_MEMBER_ERROR_MSG = "搜索会员失败";

	public static final String MEMB_MODIFY_DH_STATUS_SUCCESS_CODE="SO_MEMBDH_1003";
    public static final String MEMB_MODIFY_DH_STATUS_SUCCESS_MSG = "修改兑换状态成功";

    public static final String MEMB_MODIFY_DH_STATUS_ERRROR_CODE="FO_MEMBDH_1003";
    public static final String MEMB_MODIFY_DH_STATUS_ERROR_MSG="修改兑换状态失败";

	public static final String MEMB_INSERT_DH_STATUS_SUCCESS_CODE="SO_MEMBDH_1001";
	public static final String MEMB_INSERT_DH_STATUS_SUCCESS_MSG="兑换申请成功";


	public static final String MEMB_INSERT_DH_ERROR_CODE="FO_MEMBDH_1002";
	public static final String MEMB_INSERT_DH_ERROR_MSG="兑换申请失败";

	public static final String MEMB_DHVIEW_SUCCESS_CODE="SO_DHVIEW_1001";
	public static final String MEMB_DHVIEW_SUCCESS_MSG="查看兑换单详情成功";

	public static final String MEMB_GET_MEMBER_DETAIL_ERROR_CODE = "FO_MEMB_1003";
	public static final String MEMB_GET_MEMBER_DETAIL_ERROR_MSG = "获取会员详情失败";





	public static final String MEMB_DHVIEW_FAILED_CODE= "FO_MEMB_1003";
	public static final String MEMB_DHVIEW_FAILED_MSG = "查看兑换单详情失败";


	public static final String GET_ACCOUNT_MONEY_FAILED_CODE= "FO_MEMB_1004";
	public static final String GET_ACCOUNT_MONEY_FAILED_MSG = "查询账户余额错误";

	public static final String GET_ACCOUNT_MONEY_SUCCESS_CODE= "SO_MEMB_1005";
	public static final String GET_ACCOUNT_MONEY_SUCCESS_MSG = "查询账户余额成功";
}
