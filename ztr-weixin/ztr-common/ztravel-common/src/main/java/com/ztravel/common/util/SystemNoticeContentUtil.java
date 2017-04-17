package com.ztravel.common.util;

import org.codehaus.plexus.util.StringUtils;

import com.ztravel.common.enums.Nature;

public class SystemNoticeContentUtil {
	public static final String MEMBER_SERVER = getServerUrl();
	public static final String ORDER_DETAIL_URL = "order/front/detail/";
	public static final String VISA_ORDER_DETAIL_URL = "visaorder/front/detail/";
	public static final String LOCAL_ORDER_DETAIL_URL = "localorder/front/detail/";
	public static final String COMMENT_URL = "order/comment/edit/";
	public static final String COUPON_LIST_URL = "electronicWallet/coupon/index";
	public static final String ACCOUNT_BALANCE_URL = "electronicWallet/accountBalance/index";
	/**
	 *
	 * @param type 1>待支付；2>支付成功已确认；3>已完成状态后44小时（归来后第二天20点）
	 * @param nickName　用户昵称
	 * @param 订单id
	 * @return
	 */
	public static String getOdContent(int type,String nickName,String id,String productNature){
		switch(type){
		case 1:
			return "亲爱的“"+ nickName +"”，您的订单已提交，请在半小时内完成支付；<a target='_blank' href='" +getOdUrl(id,productNature)+ "'>查看订单</a>。";
		case 2:
			return "亲爱的“"+ nickName +"”，您的订单已支付成功！<a target='_blank' href='"+ getOdUrl(id,productNature) +"'>查看订单</a>。";
		case 3:
			return "亲爱的“"+ nickName +"”，喜欢真旅行的玩乐安排吗？一路上的趣事不少吧？真诚期待您的分享和对我们服务的回馈。<a target='_blank' href='"+ getComment(id) +"'>去评价</a>。";
		case 4:
			return "亲爱的“"+ nickName +"”，您的订单已确认，出游倒计时，现在开始！<a target='_blank' href='"+  getOdUrl(id,productNature) +"'>查看订单</a>。";
		default:
			return null;
		}
	}
	/**
	 * 系统送代金券
	 * @return
	 */
	public static String getCpContent(String couponName){
		return "新代金券“"+couponName+"”袭来,快去查看<a target='_blank' href='"+ getCpUrl() +"'>我的钱包</a>。";
	}
	/**
	 * 购买代金券
	 * @return
	 */
	public static String getVoucherContent(String couponName){
		return "代金券“"+couponName+"”已添加到<a target='_blank' href='"+ getCpUrl() +"'>我的钱包</a>，请查收。";
	}
	/**
	 * 好友送代金券
	 * @param nickName
	 * @param value
	 * @return
	 */
	public static String getCpContent(String nickName, double value){
		return "您的朋友“"+ nickName +"”给您邮寄了一张价值"+ value +"元的代金券,快去查看<a target='_blank' href='"+ getCpUrl() +"'>我的钱包</a>。";
	}
	/**
	 * 推荐好友注册成功
	 * @return
	 */
	public static String getAbContent(String nickName, double value){
		
		return "您的朋友“"+ nickName +"”注册成功,"+ value +"话费充值卡已在碗里!“"+nickName+"”出游,您的话费即可兑换!快去查看<a target='_blank' href='"+ getAbUrl() +"'>我的钱包</a>。";
	}
	/**
	 * 推荐好友出行
	 * @return
	 */
	public static String getAbContent(){
		return "100元话费充值可兑换咯,快去<a target='_blank' href='"+ getAbUrl() +"'>我的钱包</a>兑换充值卡吧!";
	}


	private static String getOdUrl(String orderId,String productNature){
		if(StringUtils.isNotBlank(productNature)){
			if(productNature.equals(Nature.COMBINATION.name()) || productNature.equals(Nature.PACKAGE.name())){
				return MEMBER_SERVER + ORDER_DETAIL_URL + orderId;
			}else{
				if(productNature.equals(Nature.VISA.name())){
					return MEMBER_SERVER + VISA_ORDER_DETAIL_URL + orderId;
				}else{
					return MEMBER_SERVER + LOCAL_ORDER_DETAIL_URL + orderId;
				}
				
			}
		}else{
			return MEMBER_SERVER + ORDER_DETAIL_URL + orderId;
		}
	}
	
	private static String getComment(String id){
		return MEMBER_SERVER + COMMENT_URL + id;
	}
	private static String getCpUrl(){
		return MEMBER_SERVER + COUPON_LIST_URL ;
	}
	private static String getAbUrl(){
		return MEMBER_SERVER + ACCOUNT_BALANCE_URL;
	}
	private static String getServerUrl(){
		String url = WebEnv.get("server.path.memberServer");
		if(url.lastIndexOf("/") == url.length()-1){
			return url;
		}else{
			return url + "/";
		}
	}

	public static void main(String[] args) {
		System.out.println(getServerUrl());
	}
}
