package com.ztravel.order.wx.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.yaml.snakeyaml.util.UriEncoder;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.util.TZBeanUtils;
import com.ztravel.common.entity.CouponSnapshot;
import com.ztravel.common.enums.OrderPayStatus;
import com.ztravel.common.enums.ProductType;
import com.ztravel.common.enums.VoucherOrderStatus;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.payment.OrderPayBean;
import com.ztravel.common.payment.OrderPayInfoBean;
import com.ztravel.common.payment.PaymentResponse;
import com.ztravel.common.payment.WxPaymentResponse;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.MoneyCalculator;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.common.util.WeixinSignUtil;
import com.ztravel.member.client.service.IMemberClientService;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.order.po.VoucherCombineOrder;
import com.ztravel.order.po.VoucherOrder;
import com.ztravel.payment.service.IOrderPaymentService;
import com.ztravel.reuse.order.converter.Convert2OrderPayBean;
import com.ztravel.reuse.order.entity.OrderPayFormBean;
import com.ztravel.reuse.order.entity.OrderPayResultFormBean;
import com.ztravel.reuse.order.entity.VoucherOrderPayVo;
import com.ztravel.reuse.order.service.IOrderOpenIdReuseService;
import com.ztravel.reuse.order.service.IOrderPayReuseService;

@Controller
@RequestMapping("/weixin/voucherOrderPay")
public class VoucherOrderPayController {

    private static Logger logger = RequestIdentityLogger.getLogger(VoucherOrderPayController.class);

    private static final String CLIENT_PATH = "ztr-payment/payment-server.properties";

    private static final String wxServer = WebEnv.get("server.path.wxServer", "");

    @Resource
    IOrderPaymentService orderPaymentService;

    @Resource
    IOrderOpenIdReuseService orderOpenIdReuseService;

    @Resource
    IVoucherOrderClientService voucherOrderClientService;

    @Resource
    IMemberClientService memberClientServiceImpl;

    @Resource
    IOrderPayReuseService orderPayReuseService;

    @RequestMapping(value = "/test")
    public String test(Model model) throws Exception {

        return "order/weixin/activity/selectvoucherpaytype";
    }

    @RequestMapping(value="/searchCouponList",method=RequestMethod.GET)
    public String  searchCouponList(String combineOrderId, String state, Model model) throws Exception{
        if (!StringUtil.isEmpty(combineOrderId) && !StringUtil.isEmpty(state) && "back".equals(state)) {
            try {
                voucherOrderClientService.cancelVoucherCombineOrder(combineOrderId);
            } catch (Exception e) {
                logger.info("按订单号取消订单异常，订单号为：[{}]", combineOrderId);
            }
        }
        return "redirect:" + WebEnv.get("server.path.wxServer", "") + "/activity/coupon/weixin/couponList";
    }

    @RequestMapping(value = "/selectPayType")
    public ModelAndView selectPayType(String combineOrderId, HttpServletRequest request) throws Exception {
        logger.info("=======微信订单{}跳转选择支付页面开始=======", combineOrderId);
        ModelAndView model = new ModelAndView();
        String checkSum = "";
        VoucherOrderPayVo orderPayVo = new VoucherOrderPayVo();
        String openId = null ;
        try {
            VoucherCombineOrder voucherCombineOrder = voucherOrderClientService.selectCombineOrderByCombineOrderId(combineOrderId);

            openId = orderOpenIdReuseService.getOpenIdByOrderId(combineOrderId) ;
            logger.info("==get openId:::{}", openId);
            String orderStatus = "";
            if (voucherCombineOrder != null) {
                orderStatus = voucherCombineOrder.getStatus().getCode();
                if (orderStatus.equals(VoucherOrderStatus.PAYED.getCode())) {
                    model.addObject("realOrderId", combineOrderId);
                    String payAmount ="";
                    if (voucherCombineOrder.getPayAmount() > 0L) {
                        MoneyCalculator money = new MoneyCalculator(voucherCombineOrder.getPayAmount());
                        payAmount = money.fenToYuan().toString();
                    }
                    model.addObject("payAmount", payAmount);
                    model.addObject("num", voucherCombineOrder.getNum());
                    model.addObject("orderId", voucherCombineOrder.getCombineOrderId());
                    model.addObject("orderStatus", orderStatus);
                    if (voucherCombineOrder.getPayType() != null) {
                        model.addObject("payType", voucherCombineOrder.getPayType().getDescription());
                    }
                    List<VoucherOrder> voucherOrderList = voucherOrderClientService.selectByCombineOrderId(combineOrderId);
                    if (voucherOrderList.size() > 0) {
                        VoucherOrder voucherOrder = voucherOrderList.get(0);
                        CouponSnapshot couponSnapshot = JSONObject.toJavaObject(JSONObject.parseObject(voucherOrder.getCouponSnapshot()), CouponSnapshot.class) ;
                        couponSnapshot.setValidDateFrom(couponSnapshot.getValidDateFrom().substring(0, 10).replace("-", "."));
                        couponSnapshot.setValidDateTo(couponSnapshot.getValidDateTo().substring(0, 10).replace("-", "."));
                        model.addObject("couponSnap", couponSnapshot);
                    }
                    model.setViewName("order/weixin/activity/paysuccessed");
                    return model;

                }else if(orderStatus.equals(VoucherOrderStatus.UNPAY.getCode())){

                    OrderPayBean orderPayBean = new OrderPayBean();
                    orderPayVo = buildOrderPayVo(voucherCombineOrder);
                    if(orderPayVo != null){
                        String payAmountConfig = TopsConfReader.getConfContent(CLIENT_PATH, "ztr.payment.payAmount", ConfScope.R);
                        if(StringUtils.isNotEmpty(payAmountConfig)){
                            long payAmount = Long.parseLong(payAmountConfig);
                            if(payAmount !=0l){
                                orderPayVo.setPayAmount(payAmount);
                            }
                        }
                        orderPayVo.setItemPrice(voucherCombineOrder.getItemPrice());
                        orderPayVo.setNum(voucherCombineOrder.getNum());

                        orderPayBean = Convert2OrderPayBean.buildOrderPayBeanByOrderPayVo(orderPayVo);
                        if(StringUtil.isEmpty(orderPayBean.getMemberId()) && openId != null) {
                            String memberId = memberClientServiceImpl.selectMemberIdByOpenId(openId);
                            if (memberId != null) {
                                orderPayBean.setMemberId(memberId);
                            }
                        }

                        List<VoucherOrder> voucherOrderList = voucherOrderClientService.selectByCombineOrderId(combineOrderId);
                        if (voucherOrderList.size() > 0) {
                            VoucherOrder voucherOrder = voucherOrderList.get(0);
                            CouponSnapshot couponSnapshot = JSONObject.toJavaObject(JSONObject.parseObject(voucherOrder.getCouponSnapshot()), CouponSnapshot.class) ;
                            couponSnapshot.setValidDateFrom(couponSnapshot.getValidDateFrom().substring(0, 10).replace("-", "."));
                            couponSnapshot.setValidDateTo(couponSnapshot.getValidDateTo().substring(0, 10).replace("-", "."));
                            orderPayVo.setCouponSnapShot(couponSnapshot);
                        }
                    }

                    logger.info("orderPayBean .. "+TZBeanUtils.describe(orderPayBean));
                    logger.info("orderPayVo .. "+TZBeanUtils.describe(orderPayVo));

                    if (orderPayBean.getMemberId().equals(orderPayVo.getMemberId())) {
                        checkSum = WeixinSignUtil.sign(getParams(orderPayBean)) ;
                    } else {
                        logger.info("======跳转到选择支付方式页面失败!订单{}支付人不是订单创建人", combineOrderId);
                        throw new Exception();
                    }
                }else{
                    model.setViewName("redirect:"+wxServer+"activity/coupon/weixin/couponList");
                    return model;
                }
            }else{
                logger.info("====跳转到无订单页面==");
                model.setViewName("redirect:"+wxServer+"activity/coupon/weixin/couponList");
                return model;
            }
        } catch (Exception e) {
            logger.info("======跳转到选择支付方式页面失败！=====:", e);
            throw new Exception(e);
        }

        model.addObject("checkSum", checkSum);
        model.addObject("orderPayVo", orderPayVo);
        model.addObject("openId", openId) ;
        logger.info("=======订单{}跳转选择支付页面成功=======", combineOrderId);
        model.setViewName("order/weixin/activity/selectvoucherpaytype");
        return model;
    }



    public VoucherOrderPayVo buildOrderPayVo(VoucherCombineOrder voucherCombineOrder) {
        VoucherOrderPayVo payVo = new VoucherOrderPayVo();
        payVo.setProductType(ProductType.VOUCHER.name());
        payVo.setMemberId(voucherCombineOrder.getCreatedBy());
        payVo.setPayAmount(voucherCombineOrder.getPayAmount());
        payVo.setTitle(voucherCombineOrder.getCombineOrderId());
        payVo.setOrderId(voucherCombineOrder.getCombineOrderId());
        payVo.setOrderCode(voucherCombineOrder.getCombineOrderId());
        payVo.setTotalPrice(voucherCombineOrder.getPayAmount());
        payVo.setUseRewardPoint(0L);
        payVo.setDiscountCoupon(0L);
        return payVo;
    }

    @RequestMapping("/jumpToWechatPay")
    @ResponseBody
    public Map<String, Object> jumpToPay(OrderPayFormBean orderPayFormBean,
            HttpServletRequest request) {
        logger.info("===========微信支付跳转第三方支付页面开始==============="+TZBeanUtils.describe(orderPayFormBean));
        Map<String, Object> map = Maps.newHashMap();
        Boolean verfyChecksumResult = true;
        WxPaymentResponse paymentResponse = new WxPaymentResponse();
        try {

            OrderPayBean orderPayBean = Convert2OrderPayBean.convert2OrderPayBean(orderPayFormBean, request);
            CommonResponse resCheck = checkVoucherPay(orderPayFormBean, request) ;
            paymentResponse.setSuccess(resCheck.isSuccess());
            paymentResponse.setErrMsg(resCheck.getErrMsg());
            if(!paymentResponse.isSuccess()){
                map.put("paymentResponse", paymentResponse) ;
                return map ;
            }
            orderPayBean.setFgNotifyUrl(wxServer + "/weixin/voucherOrderPay/payResult");
            // 校验checkSum
            logger.info("orderPay verfyChecksumResult start .."+TZBeanUtils.describe(orderPayBean));
            verfyChecksumResult = WeixinSignUtil.verify(getParams(orderPayBean), orderPayFormBean.getCheckSum());

            if (verfyChecksumResult) {
                logger.info("==verfyChecksumResult is true");
                logger.info("orderPay placeOrder start.. "+TZBeanUtils.describe(orderPayBean));
                orderPayBean.setTradeType("JSAPI");
                String openId = orderOpenIdReuseService.getOpenIdByOrderId(orderPayFormBean.getOrderId()) ;
                logger.info("==get openId:::{}", openId);
                orderPayBean.setOpenId(openId);
                paymentResponse = orderPaymentService.placeWxOrder(orderPayBean);
                logger.info("orderPay placeOrder end .. "+TZBeanUtils.describe(paymentResponse));
            }else{
                logger.info("=======订单跳转支付页面失败======  :校验verifyCheckSum 不匹配");
                paymentResponse.setErrMsg("订单跳转支付页面失败");
                paymentResponse.setSuccess(false);
                map.put("paymentResponse", paymentResponse) ;
                return map ;
            }
        } catch (Exception e) {
            logger.info("=======订单跳转支付页面失败======  :" + e);
            paymentResponse.setErrMsg("订单跳转支付页面失败!");
            paymentResponse.setSuccess(false);
            map.put("paymentResponse", paymentResponse) ;
            return map ;
        }
        String timeStamp = Integer.parseInt(new Date().getTime()/1000 + "") + "" ;
        String nonceStr = UUID.randomUUID().toString().replaceAll("-", "") ;
        String paySign = "" ;
        map.put("paymentResponse", paymentResponse) ;
        if(paymentResponse.isSuccess()){
            Map<String, String> params = new HashMap<String, String>() ;
            params.put("appId", WechatAccountHolder.APP_ID) ;
            params.put("timeStamp", timeStamp) ;
            params.put("nonceStr", nonceStr) ;
            params.put("signType", "MD5") ;
            params.put("package", "prepay_id=" + paymentResponse.getValue()) ;
            paySign = WeixinSignUtil.sign(params) ;
            map.put("appId", WechatAccountHolder.APP_ID) ;
            map.put("timeStamp", timeStamp) ;
            map.put("nonceStr", nonceStr) ;
            map.put("signType", "MD5") ;
            map.put("pkg", "prepay_id=" + paymentResponse.getValue()) ;
            map.put("paySign", paySign) ;
        }
        logger.info("===========跳转第三方支付页面结束===============");
        return map;
    }

    @RequestMapping("/jumpToAliPay")
    @ResponseBody
    public Map<String, Object> jumpToAliPay(OrderPayFormBean orderPayFormBean,
            HttpServletRequest request) {
        logger.info("===========支付宝跳转第三方支付页面开始==============="+TZBeanUtils.describe(orderPayFormBean));
        Map<String, Object> map = Maps.newHashMap();
        Boolean verfyChecksumResult = true;
        PaymentResponse paymentResponse = new PaymentResponse();
        try {
            OrderPayBean orderPayBean = Convert2OrderPayBean.convert2OrderPayBean(orderPayFormBean, request);
            CommonResponse resCheck = checkVoucherPay(orderPayFormBean, request) ;
            paymentResponse.setSuccess(resCheck.isSuccess());
            paymentResponse.setErrMsg(resCheck.getErrMsg());
            if(!paymentResponse.isSuccess()){
                map.put("paymentResponse", paymentResponse) ;
                return map ;
            }

            orderPayBean.setFgNotifyUrl(wxServer + "/weixin/voucherOrderPay/payResult");
            // 校验checkSum
            logger.info("orderPay verfyChecksumResult start .."+TZBeanUtils.describe(orderPayBean));
            verfyChecksumResult = WeixinSignUtil.verify(getParams(orderPayBean), orderPayFormBean.getCheckSum());

            if (verfyChecksumResult) {
                logger.info("==verfyChecksumResult is true");
                logger.info("orderPay placeOrder start.. "+TZBeanUtils.describe(orderPayBean));
                orderPayBean.setProductType(ProductType.VOUCHER);
                paymentResponse = orderPaymentService.placeOrder(orderPayBean);
                logger.info("orderPay placeOrder end .. "+TZBeanUtils.describe(paymentResponse));
            }else{
                logger.info("=======订单跳转支付页面失败======  :校验verifyCheckSum 不匹配");
                paymentResponse.setErrMsg("订单跳转支付页面失败");
                paymentResponse.setSuccess(false);
                map.put("paymentResponse", paymentResponse) ;
                return map ;
            }
        } catch (Exception e) {
            logger.info("=======订单跳转支付页面失败======  :" + e);
            paymentResponse.setErrMsg("订单跳转支付页面失败!");
            paymentResponse.setSuccess(false);
            map.put("paymentResponse", paymentResponse) ;
            return map ;
        }
        map.put("paymentResponse", paymentResponse) ;
        logger.info("===========支付宝跳转第三方支付页面结束===============");
        return map;
    }

    private Map<String, String> getParams(OrderPayBean orderPayBean){
        Map<String, String> params = new HashMap<String, String>() ;
        params.put("memberId", orderPayBean.getMemberId()) ;
        params.put("orderId", orderPayBean.getOrderId()) ;
        params.put("orderAmount", orderPayBean.getOrderAmount() + "") ;
        params.put("useRewardPoint", orderPayBean.getUseRewardPoint() + "") ;
        params.put("useCoupon", orderPayBean.getUseCoupon() + "") ;
        params.put("couponItemId", orderPayBean.getCouponItemId() + "") ;
        params.put("payAmount", orderPayBean.getPayAmount() + "") ;
        return params ;
    }

    private CommonResponse checkVoucherPay(OrderPayFormBean orderPayFormBean, HttpServletRequest request) throws Exception{
        CommonResponse paymentResponse = new CommonResponse() ;
        paymentResponse.setSuccess(true);

        // 判断订单在支付系统是否已支付成功
        OrderPayInfoBean orderPayInfoBean = orderPaymentService.getOrderPayInfo(orderPayFormBean.getOrderNo());
        logger.info("========orderPayInfoBean======="+TZBeanUtils.describe(orderPayInfoBean));
        VoucherCombineOrder voucherCombineOrder = voucherOrderClientService.selectCombineOrderByCombineOrderId(orderPayFormBean.getOrderId());

        if(!voucherCombineOrder.getStatus().equals(VoucherOrderStatus.UNPAY)){
            logger.info("=======订单{}已支付或已取消======  :", orderPayFormBean.getOrderId());
            paymentResponse.setErrMsg("订单已支付或已取消!");
            paymentResponse.setSuccess(false);
            return paymentResponse ;
        }

        if (null != orderPayInfoBean ) {
            OrderPayStatus orderPayStatus = orderPayInfoBean.getOrderPayStatus();
            if (!orderPayStatus.equals(OrderPayStatus.AWAIT)) {
                logger.info("=======订单{}在payment已支付或已取消======  :", orderPayFormBean.getOrderId());
                paymentResponse.setErrMsg("订单在支付系统中已支付或已取消!");
                paymentResponse.setSuccess(false);
                return paymentResponse ;
            }
        }
        return paymentResponse ;
    }

    @RequestMapping(value = "/payResult", method = RequestMethod.POST)
    public ModelAndView payResult(@ModelAttribute OrderPayResultFormBean orderPayResultFormBean)
            throws Exception {
        logger.info("==订单在第三方支付成功返回=="+TZBeanUtils.describe(orderPayResultFormBean));
        ModelAndView model = new ModelAndView();
        VoucherCombineOrder voucherCombineOrder = voucherOrderClientService.selectCombineOrderByCombineOrderId(orderPayResultFormBean.getOrderNum());
        if (StringUtils.isEmpty(SSOUtil.getMemberId())) {
            logger.info("====跳转到微信列表页==");
            return new ModelAndView("redirect:"+wxServer+"/weixin/product/list");
        }else if(StringUtils.isNotEmpty(SSOUtil.getMemberId()) && !SSOUtil.getMemberId().equals(voucherCombineOrder.getCreatedBy())){
            throw new Exception();
        }

        String payAmount ="";
        if(voucherCombineOrder.getPayAmount() > 0l){
            MoneyCalculator money = new MoneyCalculator(voucherCombineOrder.getPayAmount());
            payAmount = money.fenToYuan().toString();
        }
        model.addObject("payAmount", payAmount);
        model.addObject("num", voucherCombineOrder.getNum());
        List<VoucherOrder> voucherOrderList = voucherOrderClientService.selectByCombineOrderId(voucherCombineOrder.getCombineOrderId());
        if (voucherOrderList.size() > 0) {
            VoucherOrder voucherOrder = voucherOrderList.get(0);
            CouponSnapshot couponSnapshot = JSONObject.toJavaObject(JSONObject.parseObject(voucherOrder.getCouponSnapshot()), CouponSnapshot.class) ;
            couponSnapshot.setValidDateFrom(couponSnapshot.getValidDateFrom().substring(0, 10).replace("-", "."));
            couponSnapshot.setValidDateTo(couponSnapshot.getValidDateTo().substring(0, 10).replace("-", "."));
            model.addObject("couponSnap", couponSnapshot);
        }
        model.setViewName("order/weixin/activity/paysuccessed");
        return model;
    }

    @RequestMapping(value = "/blank")
    public ModelAndView blank(String url) throws Exception {
        String decodeURL = UriEncoder.decode(url) ;
        String param = UriEncoder.encode(decodeURL.split("[?]")[1]) ;
        String path = UriEncoder.encode(decodeURL.split("[?]")[0]) ;
        Map<String, String> params = Maps.newHashMap();
        params.put("url", path + "?" + param) ;
        return new ModelAndView("order/weixin/activity/blank", params);
    }

    @RequestMapping(value = "/payfailed")
    public ModelAndView payfailed(String voucherOrderId) throws Exception {
        Map<String, String> params = Maps.newHashMap();
        params.put("voucherOrderId", voucherOrderId) ;
        return new ModelAndView("order/weixin/activity/payfailed", params);
    }

    @RequestMapping(value = "/test1")
    public ModelAndView test1() throws Exception {
        ModelAndView model = new ModelAndView();
        if (StringUtils.isEmpty(SSOUtil.getMemberId())) {
            logger.info("====跳转到微信列表页==");
            return new ModelAndView("redirect:"+wxServer+"/weixin/product/list");
        }

        model.addObject("realOrderId", "ere");
        String payAmount ="";
            MoneyCalculator money = new MoneyCalculator(1200l);
            payAmount = money.fenToYuan().toString();
        model.addObject("payAmount", payAmount);
        model.addObject("num", 2);
        model.addObject("orderId", "ere");
        model.addObject("orderStatus", "ss");
        try{
            model.addObject("payType", "VOUCHER");
        }catch(Exception e){
            logger.error("order.getPayType() not define...", e);
        }
        List<VoucherOrder> voucherOrderList = voucherOrderClientService.selectByCombineOrderId("DJCB151207185920002503");
        if (voucherOrderList.size() > 0) {
            VoucherOrder voucherOrder = voucherOrderList.get(0);
            CouponSnapshot couponSnapshot = JSONObject.toJavaObject(JSONObject.parseObject(voucherOrder.getCouponSnapshot()), CouponSnapshot.class) ;
            couponSnapshot.setValidDateFrom(couponSnapshot.getValidDateFrom().substring(0, 10).replace("-", "."));
            couponSnapshot.setValidDateTo(couponSnapshot.getValidDateTo().substring(0, 10).replace("-", "."));
            model.addObject("couponSnap", couponSnapshot);
        }
        model.setViewName("order/weixin/activity/paysuccessed");
        return model;
    }

}
