package com.ztravel.reuse.order.converter;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.ztravel.common.enums.PaymentType;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.reuse.order.entity.OrderPayFormBean;
import com.ztravel.reuse.order.entity.OrderPayVo;

public class Convert2OrderPayBean {


	public static OrderPayBean convert2OrderPayBean(OrderPayFormBean s,	HttpServletRequest request)	throws Exception{
		OrderPayBean  t = new OrderPayBean();
		String orderId = "";
		if(s.getOrderNo() != null){
			orderId = s.getOrderNo();
		}
		t.setOrderId(orderId);

		t.setFgNotifyUrl(s.getFgNotifyUrl());

		String memberId = "";
		if(SSOUtil.getMemberId() != null){
			memberId = SSOUtil.getMemberId();
		}
		t.setMemberId(memberId);

		PaymentType paymentType = PaymentType.Alipay;
		if(s.getPaymentType() != null){
			paymentType = s.getPaymentType();
		}
		t.setPaymentType(paymentType);

		t.setComment(s.getComment());
		boolean isMobile = isMoblile(request);
		t.setMobile(isMobile);
		String fgNotifyUrl = getFgNotifyUrl();
		t.setFgNotifyUrl(fgNotifyUrl);

		t.setPayAmount(s.getPayAmount());

		String couponItemId = "";
		if(s.getCouponItemId() != null ){
			couponItemId = s.getCouponItemId();
		}
		t.setCouponItemId(couponItemId);

		t.setUseCoupon(s.getUseCoupon());

		t.setOrderAmount(s.getOrderAmount());

		t.setUseRewardPoint(s.getUseRewardPoint());

		t.setProductType(s.getProductType());
		return t;
	}

	private static boolean isMoblile(HttpServletRequest request) {
		@SuppressWarnings("rawtypes")
		Enumeration headerNames = request.getHeaderNames(); //先获取头信息
		while(headerNames.hasMoreElements()) { //如果有头的话
			String headerName = (String)headerNames.nextElement();//获取首个头元素
			if (headerName.equals("x-up-calling-line-id")) {//如果头信息是x-up-calling-line-id那基本上可以确定是手机了
				String temvit=request.getHeader(headerName);//再进一步确认
				if (temvit.substring(0,3).trim().equals("861") || temvit.substring(0,2).trim().equals("13")) {
					return true;
				}
			}
		}
		return false;
	}

	private static String getFgNotifyUrl() {
		String fgNotifyUrl = WebEnv.get("server.path.memberServer", "") //服务器地址
                + "/orderPay/payResult" ;    //请求页面或其他地址
		return fgNotifyUrl;
	}

	public static OrderPayBean buildOrderPayBeanByOrderPayVo(OrderPayVo	orderPayVo) throws Exception{
		OrderPayBean orderPayBean = new OrderPayBean();
		String memberId = "";
		orderPayBean.setOrderId(orderPayVo.getOrderCode());
		if(SSOUtil.getMemberId() !=null){
			memberId = SSOUtil.getMemberId();
		}
		orderPayBean.setMemberId(memberId);
		orderPayBean.setUseCoupon(orderPayVo.getDiscountCoupon() == null ? 0l : orderPayVo.getDiscountCoupon());
		orderPayBean.setPayAmount(orderPayVo.getPayAmount() ==null ? 0l : orderPayVo.getPayAmount());
		String couponItemId = "";
		if(orderPayVo.getCouponItemId() != null){
			couponItemId = orderPayVo.getCouponItemId();
		}
		orderPayBean.setCouponItemId(couponItemId);

		orderPayBean.setUseRewardPoint(orderPayVo.getUseRewardPoint() ==null ? 0l :orderPayVo.getUseRewardPoint());
		orderPayBean.setOrderAmount(orderPayVo.getTotalPrice() == null ? 0l : orderPayVo.getTotalPrice());
		orderPayBean.setProductType(ProductType.valueOf(orderPayVo.getProductType()));
		return orderPayBean;
	}

}
