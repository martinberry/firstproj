package com.ztravel.order.back.convertor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.travelzen.framework.core.time.DateTimeUtil;
import com.travelzen.framework.core.util.MoneyUtil;
import com.ztravel.common.entity.Attachment;
import com.ztravel.common.enums.OrderFrom;
import com.ztravel.common.enums.ZtOrderStatus;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums;
import com.ztravel.common.util.WebEnv;
import com.ztravel.order.back.vo.OrderListVO;
import com.ztravel.order.po.Order;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderPO;
import com.ztravel.order.po.OrderProduct;
import com.ztravel.reuse.product.entity.ProductBookVo;
/**
 * @author MH
 */

public class OrderConvertor {
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderConvertor.class);

	private static String opconfirmPdfAttachUrl = WebEnv.get("server.path.media") + "imageservice?mediaType=download&mediaImageId=" ;

	public static List<OrderListVO> convertPOList2VOList(List<OrderPO> orderList){
		List<OrderListVO> orderVoList = new ArrayList<OrderListVO>();
		for(OrderPO order : orderList){
			OrderListVO orderVo = convertPO2VO(order);
			orderVoList.add(orderVo);
		}
		return orderVoList;
	}
	
	public static List<OrderListVO> convertPiecePOList2VOList(List<OrderPO> orderList){
		List<OrderListVO> orderVoList = new ArrayList<OrderListVO>();
		for(OrderPO order : orderList){
			OrderListVO orderVo = convertPieceOrderPO2VO(order);
			orderVoList.add(orderVo);
		}
		return orderVoList;
	}

	private static OrderListVO convertPO2VO(OrderPO order){
		OrderListVO orderVO = new OrderListVO();
		orderVO.setOrderId(order.getOrderId());
		orderVO.setOrderNo(order.getOrderNo());

		String status = ZtOrderStatus.getDescByCode(order.getBackState());
		if( StringUtils.isNotBlank(status) ){
			orderVO.setStatus(status);
		}else{
			LOGGER.error("状态字符串转换错误");
		}

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderVO.setCreateDate(format.format(order.getCreateDate()));

		orderVO.setCreatorMid(order.getCreator());
		orderVO.setTravellerNames(order.getTravellerNames());

		if( StringUtils.isNotBlank(order.getOrderFrom()) ){
			orderVO.setSource(OrderFrom.valueOf(order.getOrderFrom()).getDescription());
		}
		if(StringUtils.isNotBlank(order.getPackageName())){
			String packageName=order.getPackageName();
			if(packageName.length()>10){
				packageName=packageName.substring(0,7)+"...";
			}
			orderVO.setPackageName(packageName);
		}
		orderVO.setOrderTotalPrice(MoneyUtil.cent2Yuan(order.getTotalPrice()));
		orderVO.setLastOperator(order.getOperator());
		orderVO.setProductNo(order.getProductNo());
		orderVO.setProductTitle(order.getProductTitle());
		if (order.getCommonOrderStatus() == null) {
		    orderVO.setCommonOrderStatus("--- ---");
		} else {
		    orderVO.setCommonOrderStatus(order.getCommonOrderStatus().getDescription());
		}
		return orderVO;
	}
	
	
	private static OrderListVO convertPieceOrderPO2VO(OrderPO order){
		OrderListVO orderVO = new OrderListVO();
		orderVO.setOrderId(order.getOrderId());
		orderVO.setOrderNo(order.getOrderNo());

		String status = ZtOrderStatus.getDescByCode(order.getBackState());
		if( StringUtils.isNotBlank(status) ){
			orderVO.setStatus(status);
		}else{
			LOGGER.error("状态字符串转换错误");
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		orderVO.setTravellerNames(order.getTravellerNames());		
		orderVO.setCreateDate(format.format(order.getCreateDate()));
		if(order.getProductSnapshot()!=null){
			ProductBookVo productBook = JSON.parseObject(order.getProductSnapshot(), ProductBookVo.class);
			orderVO.setCostPrice(productBook.getCostPriceName());			
		}
			orderVO.setProductTitle(order.getProductTitle());	
			
			if(order.getContactorName()!=null){
				orderVO.setContactorName(order.getContactorName());	
			}
			
		return orderVO;
	}

	public static MailEntity buildMailEntity(final Order order,final OrderContactor orderContactor, final OrderProduct orderProduct, List<Attachment> attachments){
		Map<String, String> params = new HashMap<String, String>() ;
		String mediaUrl = WebEnv.get("server.path.media", "")+"imageservice?mediaImageId=";
		String memberServer = WebEnv.get("server.path.memberServer", "");
		DateTime bookDateTime = DateTimeUtil.getDate(orderProduct.getBookDate().getTime());
		String detailUrl = WebEnv.get("server.path.memberServer") + "/order/front/detail/" + order.getOrderId();
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd"));
		params.put("CONTACTORNAME", orderContactor.getContactor());
		params.put("URL", mediaUrl + orderProduct.getImageId());
		params.put("DETAILURL",detailUrl);
		params.put("ORDERNO", order.getOrderNo());
		params.put("PRODUCTNAME", orderProduct.getProductTitle());
		params.put("CREATETIME",DateTimeUtil.convertDateToString("yyyy-MM-dd", order.getCreateDate()));
		params.put("OFFTIME", bookDateTime.toString("yyyy-MM-dd"));
		params.put("TOTALPRICE", String.valueOf(order.getTotalPrice()/100));
		params.put("PAYMENT", String.valueOf(order.getPayAmount()/100));
		Long couponSub = order.getCouponSub() == null ? 0l : order.getCouponSub();
		params.put("DISCOUNT", String.valueOf(couponSub/100));
		params.put("HOMEPAGE", memberServer) ;
		params.put("MEMBERINFO", memberServer + "/member/info") ;
		if(attachments != null && attachments.size() > 0){
			for(Attachment attach:attachments){
				attach.setMediaId(opconfirmPdfAttachUrl + attach.getMediaId()) ;
			}
		}
		return new MailEntity(orderContactor.getEmail(),null,MailEnums.ContentType.CONFIRMHTML,params,MailEnums.BizType.CONFIRM, attachments);
	}



	public static MailEntity buildPieceMailEntity(final Order order,final OrderContactor orderContactor, final OrderProduct orderProduct, List<Attachment> attachments){
		Map<String, String> params = new HashMap<String, String>() ;
		String mediaUrl = WebEnv.get("server.path.media", "")+"imageservice?mediaImageId=";
		String memberServer = WebEnv.get("server.path.memberServer", "");
		DateTime bookDateTime = DateTimeUtil.getDate(orderProduct.getBookDate().getTime());
		String detailUrl = WebEnv.get("server.path.memberServer") + "/order/front/detail/" + order.getOrderId();
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd"));
		params.put("CONTACTORNAME", orderContactor.getContactor());
		params.put("URL", mediaUrl + orderProduct.getImageId());
		params.put("DETAILURL",detailUrl);
		params.put("ORDERNO", order.getOrderNo());
		params.put("PRODUCTNAME", orderProduct.getProductTitle());
		params.put("CREATETIME",DateTimeUtil.convertDateToString("yyyy-MM-dd", order.getCreateDate()));
		params.put("OFFTIME", bookDateTime.toString("yyyy-MM-dd"));
		params.put("TOTALPRICE", String.valueOf(order.getTotalPrice()/100));
		params.put("PAYMENT", String.valueOf(order.getPayAmount()/100));
		Long couponSub = order.getCouponSub() == null ? 0l : order.getCouponSub();
		params.put("DISCOUNT", String.valueOf(couponSub/100));
		params.put("HOMEPAGE", memberServer) ;
		params.put("MEMBERINFO", memberServer + "/member/info") ;
		if(attachments != null && attachments.size() > 0){
			for(Attachment attach:attachments){
				attach.setMediaId(opconfirmPdfAttachUrl + attach.getMediaId()) ;
			}
		}
		return new MailEntity(orderContactor.getEmail(),null,MailEnums.ContentType.PIECECONFIRMHTML,params,MailEnums.BizType.PIECECONFIRM, attachments);
	}
}
