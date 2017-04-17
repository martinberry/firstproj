package com.ztravel.member.front.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.util.MailSender;
@Controller
@RequestMapping("/feedback")
public class FeedbackController {
	private final static Logger logger = LoggerFactory.getLogger(FeedbackController.class);

	@RequestMapping("/send")
	@ResponseBody
	public AjaxResponse sendFeedback(String content, String contact){
		try {
			MailSender.sendFeedback("用户反馈：" +contact, content);
		} catch (Exception e) {
			logger.error("发送用户反馈邮件失败", e);
			return AjaxResponse.instance("error", "");
		}
		return AjaxResponse.instance("success", "");
	}
}
