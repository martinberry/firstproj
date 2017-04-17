package com.ztravel.common.util;

import com.travelzen.framework.core.util.MoneyUtil;

public class SmsContentUtil {

	/**
	 * 建单成功后的短信通知内容
	 * */
	public static String getCreateOrderSmsContent(String orderId){
		return "hi，旅行家，您的订单"+orderId+"已提交成功，请在30分钟内完成支付，否则订单将被取消。如有问题可随时咨询我们的微信客服zhenlx2.";
	}

	/**
	 *支付成功后短信内容
	 * */
	public static String getPayOrderSmsContent(String productTitle){
		return "预订成功：您订购的\""+productTitle+"\"已支付成功，产品确认后会短信通知。如有任何行程相关问题，可随时咨询您的微信客服zhenlx2.";
	}

	/**
	 *补单成功后短信内容
	 * */
	public static String getPayOpConfirmOrderSmsContent(String productTitle){
		return "补款成功：您订购的\""+productTitle+"\"补款成功，真旅行将尽快为您确认订单。如有任何问题，请联系真旅行微信zhenlx2.";
	}

	/**
     *代金券支付成功后短信内容
     * */
    public static String getPayVoucherOrderMobileSmsContent(String mobile, String password){
        return "邀请券购买成功咯，已发送至个人电子钱包。您可通过微信或手机号：" + mobile + "+初始密码：" + password + "登录真旅行zhenlx.com查收。";
    }

    /**
     *代金券支付成功后短信内容
     * */
    public static String getPayVoucherOrderSmsContent(){
        return "购买成功咯，邀请券已发送至个人电子钱包，请登录网站查收。";
    }

	public static String getOpConfirmSmsContent(String productTitle){
		return "产品已确认：您订购的\""+productTitle+"\"已确认，请登录网站或个人邮件查看产品确认信息。距出行前两周我们将给您发送行程手册和福袋。如有任何行程相关问题，可随时咨询您的微信客服zhenlx2.";
	}

	public static String getVisaOpConfirmSmsContent(String productTitle){
		return "产品已确认：您订购的\""+productTitle+"\"已确认，您可登陆网站查看签证办理进度;如有任何签证相关问题,可随时咨询您的微信客服zhenlx2.";
	}
	
	public static String getLocalTravelOpConfirmSmsContent(String productTitle){
		return "产品已确认：您订购的\""+productTitle+"\"已确认，您可登录网站查看订单进度；如有任何相关问题，可随时咨询您的微信客服zhenlx2.";
	}


	public static String get44NoticeContent(){
		return "hi，旅行家，游玩归来您可登陆网站对我们的服务进行评价。喜欢真旅行的玩乐安排吗？一路上的趣事不少吧？期待您的分享和对我们服务的回馈。在您探索这个美丽世界的路途中，真旅行始终与您相伴。";
	}

    /**
     *订单补款短信内容
     * */
    public static String getPayBackSmsContent(String orderId, Long amount){
    	String payAmount = MoneyUtil.cent2Yuan(amount);
        return "hi，旅行家，您的订单"+orderId+"已重新计价，需要补款"+payAmount+"元，请在24小时登录网站或者服务号“我的订单”完成支付，以免耽误您的出行。";
    }

}

