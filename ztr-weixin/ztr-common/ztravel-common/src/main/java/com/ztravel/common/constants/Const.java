package com.ztravel.common.constants;

/**
 * @author tengmeilin
 * 处理AJAX的返回值, 以及异常信息
 *
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 * */
public final class Const {

	//后台用户登录生成的验证码, 保存在redis中的键值
	public final static String ZTRAVEL_OPERATOR_ADMIN = "ZTRAVEL_OPERATOR_ADMIN";

	//后台用户登录生成的验证码, 保存在redis中的键值
	public final static String KAPTCHA_OPER_LOGIN_KEY = "KAPTCHA_OPER_LOGIN_KEY";

	//后台用户登录密码加密的密钥
	public final static String ZTRAVEL_OPERATOR_USER = "ztravel.operator.user";

	//后台用户被删除时找到当前登录会话id，在redis中的键值
    public final static String ZTRAVEL_OPERATOR_USER_KEY = ":forUserChanged";

	//后台用户登录在redis中设置自动登录的键值
	public final static String ZTRAVEL_OPERATOR_AUTOLOGIN_KEY = ":autoLogin";

	//大洲区域-国家-城市 对应关系保存在redis中的键值
	public final static String CONTINENT_NATION_CITY_KEY = "internationalDestinationKey";

	public static final String SO_OPER_CODE_1001 = "SO_OPER_1001";
    public static final String SO_MEMB_CODE_1001 = "SO_MEMB_1001";
    public static final String SF_MEMB_CODE_1001 = "SF_MEMB_1001";
    public static final String SF_MEMB_CODE_1002 = "SF_MEMB_1002";
    public static final String SF_MEMB_CODE_1003 = "SF_MEMB_1003";
    public static final String SO_PROD_CODE_1001 = "SO_PROD_1001";
    public static final String SO_PROD_CODE_1002 = "SO_PROD_1002";
    public static final String SF_ORDE_CODE_1001 = "SF_ORDE_1001";
    public static final String SF_ORDE_CODE_1002 = "SF_ORDE_1002";
    public static final String SF_ORDE_CODE_1003 = "SF_ORDE_1003";
    public static final String SO_RBAC_CODE_1001 = "SO_RBAC_1001";
    public static final String SO_RBAC_CODE_1002 = "SO_RBAC_1002";
    public static final String SO_RBAC_CODE_1003 = "SO_RBAC_1003";
    public static final String SO_RBAC_CODE_1004 = "SO_RBAC_1004";
    public static final String SO_RBAC_CODE_1005 = "SO_RBAC_1005";


    public static final String SUCCESS = "成功";


	public static final String FO_OPER_CODE_1001 = "FO_OPER_1001";
    public static final String FO_OPER_CODE_1002 = "FO_OPER_1002";
    public static final String FO_OPER_CODE_1003 = "FO_OPER_1003";
    public static final String FO_OPER_CODE_1004 = "FO_OPER_1004";
    public static final String FO_OPER_CODE_1005 = "FO_OPER_1005";
    public static final String FO_MEMB_CODE_1001 = "FO_MEMB_1001";
    public static final String FO_MEMB_CODE_1002 = "FO_MEMB_1002";
    public static final String FO_MEMB_CODE_1003 = "FO_MEMB_1003";
    public static final String FO_MEMB_CODE_1004 = "FO_MEMB_1004";
    public static final String FO_MEMB_CODE_1005 = "FO_MEMB_1005";
    public static final String FO_MEMB_CODE_1006 = "FO_MEMB_1006";
    public static final String FO_MEMB_CODE_1007 = "FO_MEMB_1007";
    public static final String FO_MEMB_CODE_1008 = "FO_MEMB_1008";
    public static final String FO_MEMB_CODE_1009 = "FO_MEMB_1009";
    public static final String FO_MEMB_CODE_1010 = "FO_MEMB_1010";
    public static final String FF_MEMB_CODE_1001 = "FF_MEMB_1001";
    public static final String FF_MEMB_CODE_1002 = "FF_MEMB_1002";
    public static final String FF_MEMB_CODE_1003 = "FF_MEMB_1003";
    public static final String FF_MEMB_CODE_1004 = "FF_MEMB_1004";
    public static final String FF_MEMB_CODE_1005 = "FF_MEMB_1005";
    public static final String FF_MEMB_CODE_1006 = "FF_MEMB_1006";
    public static final String FF_MEMB_CODE_1007 = "FF_MEMB_1007";
    public static final String FF_MEMB_CODE_1008 = "FF_MEMB_1008";
    public static final String FF_MEMB_CODE_1009 = "FF_MEMB_1009";
    public static final String FF_MEMB_CODE_1010 = "FF_MEMB_1010";
    public static final String FO_PROD_CODE_1001 = "FO_PROD_1001";
    public static final String FO_PROD_CODE_1002 = "FO_PROD_1002";
    public static final String FO_PROD_CODE_1003 = "FO_PROD_1003";
    public static final String FO_PROD_CODE_1004 = "FO_PROD_1004";
    public static final String FO_PROD_CODE_1005 = "FO_PROD_1005";
    public static final String FO_PROD_CODE_1006 = "FO_PROD_1006";
    public static final String FO_PROD_CODE_1007 = "FO_PROD_1007";
    public static final String FO_PROD_CODE_1008 = "FO_PROD_1008";
    public static final String FF_PROD_CODE_1001 = "FF_PROD_1001";
    public static final String FF_PROD_CODE_1002 = "FF_PROD_1002";
    public static final String FF_ORDE_CODE_1001 = "FF_ORDE_1001";
    public static final String FF_ORDE_CODE_1002 = "FF_ORDE_1002";
    public static final String FF_ORDE_CODE_1003 = "FF_ORDE_1003";
    public static final String FF_ORDE_CODE_1004 = "FF_ORDE_1004";
    public static final String FF_ORDE_CODE_1005 = "FF_ORDE_1005";
    public static final String FF_ORDE_CODE_1006 = "FF_ORDE_1006";
    public static final String FF_ORDE_CODE_1007 = "FF_ORDE_1007";
    public static final String FF_ORDE_CODE_1008 = "FF_ORDE_1008";
    public static final String FF_ORDE_CODE_1009 = "FF_ORDE_1009";
    public static final String FF_ORDE_CODE_1010 = "FF_ORDE_1010";
    public static final String FF_ORDE_CODE_1011 = "FF_ORDE_1011";
    public static final String FF_ORDE_CODE_1012 = "FF_ORDE_1012";
    public static final String FF_ORDE_CODE_1013 = "FF_ORDE_1013";
    public static final String FF_ORDE_CODE_1014 = "FF_ORDE_1014";
    public static final String FF_ORDE_CODE_1015 = "FF_ORDE_1015";
    public static final String FF_ORDE_CODE_1016 = "FF_ORDE_1016";
    public static final String FF_ORDE_CODE_1017 = "FF_ORDE_1017";
    public static final String FF_ORDE_CODE_1018 = "FF_ORDE_1018";
    public static final String FF_ORDE_CODE_1019 = "FF_ORDE_1019";
    public static final String FO_ORDE_CODE_1020 = "FF_ORDE_1020";
    public static final String FO_RBAC_CODE_1001 = "FO_RBAC_1001";
    public static final String FO_RBAC_CODE_1002 = "FO_RBAC_1002";
    public static final String FO_RBAC_CODE_1003 = "FO_RBAC_1003";
    public static final String FO_RBAC_CODE_1004 = "FO_RBAC_1004";
    public static final String FO_RBAC_CODE_1005 = "FO_RBAC_1005";
    public static final String FO_RBAC_CODE_1006 = "FO_RBAC_1006";
    public static final String FO_RBAC_CODE_1007 = "FO_RBAC_1007";
    public static final String FO_RBAC_CODE_1008 = "FO_RBAC_1008";
    public static final String FO_RBAC_CODE_1009 = "FO_RBAC_1009";
    public static final String FO_RBAC_CODE_1010 = "FO_RBAC_1010";
    public static final String FO_RBAC_CODE_1011 = "FO_RBAC_1011";
    public static final String FO_RBAC_CODE_1012 = "FO_RBAC_1012";
    public static final String FO_RBAC_CODE_1013 = "FO_RBAC_1013";
    public static final String FO_RBAC_CODE_1014 = "FO_RBAC_1014";
    public static final String FO_RBAC_CODE_1015 = "FO_RBAC_1015";
    public static final String FO_RBAC_CODE_1016 = "FO_RBAC_1016";
    public static final String FO_RBAC_CODE_1017 = "FO_RBAC_1017";
    public static final String FO_RBAC_CODE_1018 = "FO_RBAC_1018";
    public static final String FO_RBAC_CODE_1019 = "FO_RBAC_1019";



    public static final String FO_OPER_REASON_1001 = "用户名或密码输入格式错误";
    public static final String FO_OPER_REASON_1002 = "验证码错误";
    public static final String FO_OPER_REASON_1003 = "用户名或密码错误";
    public static final String FO_OPER_REASON_1004 = "登录异常,请稍后重试";
    public static final String FO_OPER_REASON_1005 = "生成验证码失败或验证码已过期";
    public static final String FO_MEMB_REASON_1001 = "获取会员常旅客详情失败";
    public static final String FO_MEMB_REASON_1002 = "与会员关联失败";
    public static final String FO_MEMB_REASON_1003 = "获取会员常旅客列表失败";
    public static final String FO_MEMB_REASON_1004 = "旅客姓名或手机号格式输入错误";
    public static final String FO_MEMB_REASON_1005 = "获取单个会员心愿清单失败";
    public static final String FO_MEMB_REASON_1006 = "查询会员心愿清单失败";
    public static final String FO_MEMB_REASON_1007 = "获取会员心愿清单详情中产品信息失败";
    public static final String FO_MEMB_REASON_1008 = "获取会员心愿清单详情中会员信息失败";
    public static final String FO_MEMB_REASON_1009 = "获取会员心愿清单详情失败";
    public static final String FF_MEMB_REASON_1001 = "该会员未登录，请先登录";
    public static final String FF_MEMB_REASON_1002 = "该产品发生异常";
    public static final String FF_MEMB_REASON_1003 = "数据库中已存在该心愿单";
    public static final String FF_MEMB_REASON_1004 = "查询订单数据库失败";
    public static final String FF_MEMB_REASON_1005 = "数据库中插入心愿单失败";
    public static final String FF_MEMB_REASON_1006 = "获取会员心愿清单失败";
    public static final String FF_MEMB_REASON_1007 = "网络异常，获取会员心愿清单失败";
    public static final String FF_MEMB_REASON_1008 = "该心愿清单发生异常";
    public static final String FF_MEMB_REASON_1009 = "网络异常，删除会员心愿清单失败";
    public static final String FO_PROD_REASON_1001 = "关联酒店列表失败";
    public static final String FO_PROD_REASON_1002 = "获取酒店资源失败";
    public static final String FO_PROD_REASON_1003 = "上传图片不能超过2M";
    public static final String FO_PROD_REASON_1004 = "只能上传jpg，png , jpeg格式图片";
    public static final String FO_PROD_REASON_1005 = "加载图片失败";
    public static final String FO_PROD_REASON_1006 = "输入格式有误，请重新输入";
    public static final String FO_PROD_REASON_1007 = "中文名称、目的地、酒店地址、酒店类型、酒店星级、卖点、酒店描述完成后才能发布";
    public static final String FO_PROD_REASON_1008 = "保存酒店资源失败";
    public static final String FF_PROD_REASON_1001 = "关联产品列表失败";
    public static final String FF_PROD_REASON_1002 = "获取产品资源失败";
    public static final String FF_ORDE_REASON_1001 = "获取会员订单列表失败";
    public static final String FF_ORDE_REASON_1002 = "获取订单产品信息失败";
    public static final String FF_ORDE_REASON_1003 = "获取数据库订单详情失败";
    public static final String FF_ORDE_REASON_1004 = "网络异常，获取订单详情失败";
    public static final String FF_ORDE_REASON_1005 = "订单联系人输入信息格式不正确";
    public static final String FF_ORDE_REASON_1006 = "更新订单联系人失败";
    public static final String FF_ORDE_REASON_1007 = "网络异常，更新订单联系人失败";
    public static final String FF_ORDE_REASON_1008 = "获取订单联系人失败";
    public static final String FF_ORDE_REASON_1009 = "网络异常，获取订单联系人失败";
    public static final String FF_ORDE_REASON_1010 = "订单旅客输入信息格式不正确";
    public static final String FF_ORDE_REASON_1011 = "更新订单旅客信息失败";
    public static final String FF_ORDE_REASON_1012 = "网络异常，更新旅客信息失败";
    public static final String FF_ORDE_REASON_1013 = "获取订单旅客信息失败";
    public static final String FF_ORDE_REASON_1014 = "网络异常，获取订单旅客信息失败";
    public static final String FF_ORDE_REASON_1015 = "订单异常";
    public static final String FF_ORDE_REASON_1016 = "更新订单产品信息失败";
    public static final String FF_ORDE_REASON_1017 = "网络异常，更新订单产品信息失败";
    public static final String FF_ORDE_REASON_1018 = "该会员无权查看该订单";
    public static final String FF_ORDE_REASON_1019 = "订单旅客信息不能为空";
    public static final String FF_ORDE_REASON_1020 = "该订单不能编辑";
    public static final String FO_RBAC_REASON_1001 = "获取角色信息失败";
    public static final String FO_RBAC_REASON_1002 = "网络异常，获取角色信息失败";
    public static final String FO_RBAC_REASON_1003 = "该角色发生异常";
    public static final String FO_RBAC_REASON_1004 = "网络异常，删除角色失败";
    public static final String FO_RBAC_REASON_1005 = "获取角色和权限详情失败";
    public static final String FO_RBAC_REASON_1006 = "网络异常，获取角色和权限详情失败";
    public static final String FO_RBAC_REASON_1007 = "该角色名已存在";
    public static final String FO_RBAC_REASON_1008 = "保存角色失败";
    public static final String FO_RBAC_REASON_1009 = "网络异常，保存角色失败";
    public static final String FO_RBAC_REASON_1010 = "获取用户信息失败";
    public static final String FO_RBAC_REASON_1011 = "网络异常，获取用户信息失败";
    public static final String FO_RBAC_REASON_1012 = "该用户发生异常";
    public static final String FO_RBAC_REASON_1013 = "网络异常，删除用户失败";
    public static final String FO_RBAC_REASON_1014 = "修改用户状态失败";
    public static final String FO_RBAC_REASON_1015 = "网络异常，修改用户状态失败";
    public static final String FO_RBAC_REASON_1016 = "保存用户失败";
    public static final String FO_RBAC_REASON_1017 = "网络异常，保存用户失败";
    public static final String FO_RBAC_REASON_1018 = "该用户名已存在";
    public static final String FO_RBAC_REASON_1019 = "该用户未登录，请先登录";

    public static final String DEFAULT_COUNTRY = "中国";

    //首页目的地，后台未设置默认目的地时的值
    public static final String DESTINATION_PLACEHOLDER = "世界";
}
