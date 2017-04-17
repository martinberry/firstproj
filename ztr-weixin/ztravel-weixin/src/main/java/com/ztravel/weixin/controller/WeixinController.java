package com.ztravel.weixin.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.ParseException;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.weixin.notice.GiftBoxSendNotice;
import com.ztravel.common.weixin.notice.InviteRegisNotice;
import com.ztravel.common.weixin.notice.InviteSuccessNotice;
import com.ztravel.common.weixin.notice.OpConfirmNotice;
import com.ztravel.common.weixin.notice.OrderSubmitNotice;
import com.ztravel.common.weixin.notice.OuttingNotice;
import com.ztravel.common.weixin.notice.PaySuccessNotice;
import com.ztravel.common.weixin.notice.ReceiveCouponNotice;
import com.ztravel.common.weixin.notice.TravelEndNotice;
import com.ztravel.weixin.constant.TemplateMessage;
import com.ztravel.weixin.constant.ViewState;
import com.ztravel.weixin.operate.OpenIdOperator;
import com.ztravel.weixin.operate.TemplateOperator;
import com.ztravel.weixin.servlet.AccessTokenThread;
import com.ztravel.weixin.template.pojo.Template;
import com.ztravel.weixin.template.pojo.TemplateData;

@Controller
@RequestMapping("/weixinController")
public class WeixinController {

	private static Logger logger = RequestIdentityLogger.getLogger(WeixinController.class);

	@RequestMapping(value = "/getOpenid", method = RequestMethod.GET)
	public String getOpenIdFromWeixin(HttpServletRequest request, String code, String state) throws ParseException, IOException {

		logger.info("请求的参数code为：" + code + ",state为：" + state + ".");

		JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);

		//获取得到openid
		String openid = oAuthAccessToken.getString("openid");
		logger.info("获得的openid为：" + openid);

		//将获取到的openid保存到redis中
		OpenIdUtil.setOpenId(openid);

		return ViewState.viewUrlMap.get(state);

	}

	/**
	 * 订单支付成功
	 * @param request
	 * @param paySuccessNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/paySuccess", method = RequestMethod.POST)
	public String sendPaySuccessTemplateMsg(HttpServletRequest request) throws Exception {

		PaySuccessNotice paySuccessNotice = (PaySuccessNotice) getNotice(request, PaySuccessNotice.class);

		logger.info("订单支付成功推送消息为：" + paySuccessNotice);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = paySuccessNotice.getOpenId();
		String url = paySuccessNotice.getDetailUrl();

		Template template = new Template(TemplateMessage.PAY_SUCCESS_ID, openId, url, "#000000");

		String userName = paySuccessNotice.getUserName();
		String orderCode = paySuccessNotice.getOrderCode();
		String productName = paySuccessNotice.getProductContent();
		String payAmount = paySuccessNotice.getPayAmount();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "你好，" + userName + " 您已支付成功"));
        data.put("keyword1", new TemplateData("#000000", orderCode));
        data.put("keyword2", new TemplateData("#000000", productName));
        data.put("keyword3", new TemplateData("#000000", payAmount + "元"));
        data.put("remark", new TemplateData("#000000", "感谢您的支持"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

	/**
	 * 订单确认通知
	 * @param request
	 * @param paySuccessNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/opConfirm", method = RequestMethod.POST)
	public String sendOpConfirmTemplateMsg(HttpServletRequest request) throws Exception {

		OpConfirmNotice paySuccessNotice = (OpConfirmNotice) getNotice(request, OpConfirmNotice.class);

		logger.info("订单确认通知推送消息为：" + paySuccessNotice);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = paySuccessNotice.getOpenId();

		Template template = new Template(TemplateMessage.OP_CONFIRM_ID, openId, "", "#000000");

		String orderCode = paySuccessNotice.getOrderCode();
		String payAmount = paySuccessNotice.getPayAmount();
		String createDate = paySuccessNotice.getCreateDate();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "您的订单已经确认，请登录网站或邮件查看产品确认信息"));
        data.put("keyword1", new TemplateData("#000000", orderCode));
        data.put("keyword2", new TemplateData("#000000", payAmount + "元"));
        data.put("keyword3", new TemplateData("#000000", createDate));
        data.put("remark", new TemplateData("#000000", "如果您还有任何疑问，请直接联系真旅管家回复您的问题即可，或电话联系：400-620-6266 转6。祝您行程愉快！"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

	/**
	 * 行程结束
	 * @param request
	 * @param travelEndNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/travelEnd", method = RequestMethod.POST)
	public String sendTravelEndTemplateMsg(HttpServletRequest request) throws Exception {

	    TravelEndNotice travelEndNotice = (TravelEndNotice) getNotice(request, TravelEndNotice.class);

	    String accessToken = AccessTokenThread.getAccessToken();

		String openId = travelEndNotice.getOpenId();
		String url = travelEndNotice.getDetailUtl();

		Template template = new Template(TemplateMessage.TRAVEL_END_ID, openId, url , "#000000");

		String productName = travelEndNotice.getProductContent();
		String endDate = travelEndNotice.getEndDate();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "尊敬的用户，您的行程已结束，谢谢"));
        data.put("keyword1", new TemplateData("#000000", productName));
        data.put("keyword2", new TemplateData("#000000", endDate));
        data.put("remark", new TemplateData("#000000", "欢迎下次光临"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

	@SuppressWarnings("unchecked")
	private Object getNotice(HttpServletRequest request, @SuppressWarnings("rawtypes") Class cl) throws IOException{

		//从request中取得输入流
		InputStream inputStream = request.getInputStream();

	    InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

		StringBuffer buffer = new StringBuffer();
		String str = null;
		while ((str = bufferedReader.readLine()) != null) {
			buffer.append(str);
		}
		return JSON.parseObject(buffer.toString(), cl);
	}

	/**
	 * 订单提交成功
	 * @param request
	 * @param orderSubmitNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/orderSubmit", method = RequestMethod.POST)
	public String sendOrderSubmitTemplateMsg(HttpServletRequest request) throws Exception {

		OrderSubmitNotice orderSubmitNotice = (OrderSubmitNotice) getNotice(request, OrderSubmitNotice.class);

		logger.info("订单提交成功推送消息为：" + orderSubmitNotice);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = orderSubmitNotice.getOpenId();
		String url = orderSubmitNotice.getPayUrl();

		Template template = new Template(TemplateMessage.ORDER_SUBMIT_ID, openId, url , "#000000");
		String userName = orderSubmitNotice.getUserName();
		String orderId = orderSubmitNotice.getOrderId();
		String payAmount = orderSubmitNotice.getPayAmount();
		String productName = orderSubmitNotice.getProductContent();
		String bookDate = orderSubmitNotice.getBookDate();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "亲爱的“" + userName + "”，您的订单已提交成功！"));
        data.put("keyword1", new TemplateData("#000000", orderId));
        data.put("keyword2", new TemplateData("#000000", payAmount + "元"));
        data.put("keyword3", new TemplateData("#000000", productName));
        data.put("keyword4", new TemplateData("#000000", bookDate));
        data.put("remark", new TemplateData("#000000", "请在半小时内完成支付，点击“详情”去支付。"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
		String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

	   return returnMsg;

	}

	/**
	 * 行程手册发放
	 * @param request
	 * @param giftBoxSendNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/giftBoxSend", method = RequestMethod.POST)
	public String sendGiftBoxSendTemplateMsg(HttpServletRequest request) throws Exception {

		GiftBoxSendNotice giftBoxSendNotice = (GiftBoxSendNotice) getNotice(request, GiftBoxSendNotice.class);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = giftBoxSendNotice.getOpenId();

		Template template = new Template(TemplateMessage.GIFT_BOX_SEND_ID, openId, "" , "#000000");
		String userName = giftBoxSendNotice.getUserName();
		String logisticsType = giftBoxSendNotice.getLogisticsType();
		String logisticsCode = giftBoxSendNotice.getLogisticsCode();
		String receiver = giftBoxSendNotice.getReceiver();
		String phone = giftBoxSendNotice.getPhone();
		String receiveAddress = giftBoxSendNotice.getReceiveAddress();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "亲爱的“" + userName + "”，您的行程手册已发放，注意查收"));
        data.put("keyword1", new TemplateData("#000000", logisticsType));
        data.put("keyword2", new TemplateData("#000000", logisticsCode));
        data.put("keyword3", new TemplateData("#000000", receiver));
        data.put("keyword4", new TemplateData("#000000", phone));
        data.put("keyword5", new TemplateData("#000000", receiveAddress));
        data.put("remark", new TemplateData("#000000", "请耐心等候。"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

	/**
	 * 出行
	 * @param request
	 * @param outtingNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/outting", method = RequestMethod.POST)
	public String sendOuttingTemplateMsg(HttpServletRequest request) throws Exception {

		OuttingNotice outtingNotice = (OuttingNotice) getNotice(request, OuttingNotice.class);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = outtingNotice.getOpenId();

		Template template = new Template(TemplateMessage.OUTTING_ID, openId, "" , "#000000");
		String userName = outtingNotice.getUserName();
		String productContent = outtingNotice.getProductContent();
		String bookDate = outtingNotice.getBookDate();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "亲爱的“" + userName + "”，您的行程即将出发。"));
        data.put("keyword1", new TemplateData("#000000", productContent));
        data.put("keyword2", new TemplateData("#000000", bookDate));
        data.put("remark", new TemplateData("#000000", "如果您还有任何疑问，请直接联系xx回复您的问题即可，或电话联系：400-88888888。祝您行程愉快！"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

	/**
	 * 邀请好友注册成功
	 * @param request
	 * @param inviteRegisNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/inviteRegis", method = RequestMethod.POST)
	public String sendInviteRegisTemplateMsg(HttpServletRequest request) throws Exception {

		InviteRegisNotice inviteRegisNotice = (InviteRegisNotice) getNotice(request, InviteRegisNotice.class);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = inviteRegisNotice.getOpenId();

		Template template = new Template(TemplateMessage.INVITE_REGIS_ID, openId, "" , "#000000");
		String friendName = inviteRegisNotice.getFriendName();
		String regisTime = inviteRegisNotice.getRegisTime();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "邀请成功"));
        data.put("keyword1", new TemplateData("#000000", friendName));
        data.put("keyword2", new TemplateData("#000000", regisTime));
        data.put("remark", new TemplateData("#000000", "您的好友”" + friendName + "“已经接受邀请，恭喜获得10个积分"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}


	/**
	 * 邀请好友注册成功
	 * @param request
	 * @param inviteSuccessNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/inviteSuccess", method = RequestMethod.POST)
	public String sendInviteSuccessTemplateMsg(HttpServletRequest request) throws Exception {

		InviteSuccessNotice inviteSuccessNotice = (InviteSuccessNotice) getNotice(request, InviteSuccessNotice.class);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = inviteSuccessNotice.getOpenId();

		Template template = new Template(TemplateMessage.INVITE_SUCCESS_ID, openId, "" , "#000000");
		String userName = inviteSuccessNotice.getUserName();
		String friendName = inviteSuccessNotice.getFriendName();
		String productContent = inviteSuccessNotice.getProductContent();
		String endDate = inviteSuccessNotice.getEndDate();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "亲爱的“" + userName + "”，您的朋友成功出行，准备金可以体现咯！"));
        data.put("keyword1", new TemplateData("#000000", friendName));
        data.put("keyword2", new TemplateData("#000000", productContent));
        data.put("keyword3", new TemplateData("#000000", endDate));
        data.put("remark", new TemplateData("#000000", "点击“详情”去电子钱包查看。"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

	/**
	 * 获得代金券
	 * @param request
	 * @param inviteRegisNotice
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/receiveCoupon", method = RequestMethod.POST)
	public String sendReceiveCouponTemplateMsg(HttpServletRequest request) throws Exception {

		ReceiveCouponNotice receiveCouponNotice = (ReceiveCouponNotice) getNotice(request, ReceiveCouponNotice.class);

		String accessToken = AccessTokenThread.getAccessToken();

		String openId = receiveCouponNotice.getOpenId();

		Template template = new Template(TemplateMessage.RECEIVE_COUPON_ID, openId, "" , "#000000");
		String couponAmount = receiveCouponNotice.getCouponAmount();
		String validateDateInfo = receiveCouponNotice.getValidateDateInfo();

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
        data.put("first", new TemplateData("#000000", "尊敬的客户，您已获得唯品会10元代金券"));
        data.put("keyword1", new TemplateData("#000000", couponAmount + "元"));
        data.put("keyword2", new TemplateData("#000000", validateDateInfo));
        data.put("remark", new TemplateData("#000000", "如有疑问，请拨打咨询热线4006789888。"));

        template.setData(data);

        String jsonStr = JSONObject.toJSONString(template).toString();
        String returnMsg = TemplateOperator.sendTemplateMessage(jsonStr, accessToken);

		return returnMsg;

	}

}
