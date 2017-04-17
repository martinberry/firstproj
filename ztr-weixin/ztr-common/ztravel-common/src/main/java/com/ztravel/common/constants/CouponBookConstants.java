package com.ztravel.common.constants;

/**
 * 代金券预订常量
 * @author tian1.xu
 * 规范
 * 1.第1位 S-->成功  F-->失败
 * 2.第2位 F-->C端   O-->后台
 * 3.第3,4,5,6位 表示模块
 * 4.最后4位   表示状态码
 *
 * CPBK--coupon book
 * 如--> SF_MEMB_0001 表示 C端用户模块某一业务成功
 */
public class CouponBookConstants {

    /**
     * 代金券库存不足
     */
    public static final String COUPON_STOCK_NOT_ENOUGH = "EF_CPBK_1001";

    /**
     * 用户未登录
     */
    public static final String USER_NOT_LOGIN = "EF_CPBK_1002";

    /**
     * 当前累计购买数量超过每人限购数量
     */
    public static final String BUY_AMOUNT_MORE_THAN_UNIT = "EF_CPBK_1003";

    /**
     * 活动核查失败
     */
    public static final String ACTIVITY_CHECK_FAILURE = "EF_CPBK_1004";

    /**
     * 提交订单失败
     */
    public static final String APPLY_ORDER_FAILURE = "EF_CPBK_1005";

    /**
     * 代金券提交订单成功
     */
    public static final String COUPON_APPLY_ORDER_SUCCESS = "EF_CPBK_1006";

    /**
     * 用户挂起建单失败
     */
    public static final String NOT_ACTIVE_TO_APPLY_ORDER_FAILURE = "EF_CPBK_1007";

    /**
     * 产品快照key
     */
    public static final String COUPON_SNAPSHOT_KEY = "ps_key";

    /**
     * 参数异常
     */
    public static final String PARAMS_NOT_CORRECT = "EF_CPBK_1011";

    /**
     * 用户已登陆
     */
    public static final String MEMBER_IS_LOGINED = "EF_CPBK_1012";

    /**
     * 校验成功
     */
    public static final String BUY_VALIDATION_SUCCESS = "EF_CPBK_1015";
    /**
     * 校验失败
     */
    public static final String BUY_VALIDATION_FAIL = "EF_CPBK_1016";

}
