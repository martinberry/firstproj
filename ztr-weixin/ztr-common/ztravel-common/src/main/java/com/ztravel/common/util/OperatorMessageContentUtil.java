package com.ztravel.common.util;

import org.codehaus.plexus.util.StringUtils;

import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.Nature;

public class OperatorMessageContentUtil {
	public static final String OPERA_SERVER = WebEnv.get("server.path.operaServer") ;
	public static final String BACK_ORDER_DETAIL = OPERA_SERVER + "/order/freetravel/detail/";
	public static final String BACK_VISA_ORDER_DETAIL = OPERA_SERVER + "/visa/detail/";
	public static final String BACK_LOCAL_ORDER_DETAIL = OPERA_SERVER + "/localorder/travel/detail/";
	public static final String BACK_COMMENT_DETAIL = OPERA_SERVER + "/comment/detail/";
	public static String getContent(MessageTitleType title, String mid, String productName){
		switch(title){
		case PAYSUCCESS:
			return "用户"+mid+"["+productName+"]订单支付成功，请速op确认";
		case CANCLE:
			return "用户"+mid+"["+productName+"]订单被取消，请查看";
		case GIFTBOX:
			return "用户"+mid+"["+productName+"]快到礼盒发放时间，请速操作";
		case OUTNOTICE:
			return "用户"+mid+"["+productName+"]快到出行通知时间，请速操作";
		case EVALATE:
			return "用户"+mid+"["+productName+"]做了评价，请速审核";
		case REFUNDED:
			return "用户"+mid+"["+productName+"]订单退款成功";
		case REFUNDFAILED:
			return "用户"+mid+"["+productName+"]订单退款失败";
		default:
			return "";
		}
	}

	public static String getOrderUrl(String orderId,String productNature){
		if(StringUtils.isNotBlank(productNature)){
			if(productNature.equals(Nature.COMBINATION.name()) || productNature.equals(Nature.PACKAGE.name())){
				return BACK_ORDER_DETAIL + orderId;
			}else{
				if(productNature.equals(Nature.VISA.name())){
					return BACK_VISA_ORDER_DETAIL + orderId;
				}else{
					return BACK_LOCAL_ORDER_DETAIL + orderId;
				}
				
			}
		}else{
			return BACK_ORDER_DETAIL + orderId;
		}
		
	}
	public static String getCommentUrl(String commentId){
		return BACK_COMMENT_DETAIL + commentId;
	}
}
