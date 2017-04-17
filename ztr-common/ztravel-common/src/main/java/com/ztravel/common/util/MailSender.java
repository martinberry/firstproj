package com.ztravel.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.travelzen.framework.config.tops.TopsConfEnum.ConfScope;
import com.travelzen.framework.config.tops.TopsConfReader;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.entity.Attachment;
import com.ztravel.common.mail.MailEntity;
import com.ztravel.common.mail.MailEnums.BizType;
import com.ztravel.common.mail.MailEnums.ContentType;
import com.ztravel.common.mail.MailUtil;

/**
 * @author wanhaofan
 * */
public class MailSender {

	private final static Logger logger = LoggerFactory.getLogger(MailSender.class);
	private static final ExecutorService exec = Executors.newFixedThreadPool(10) ;
	private static final RedisClient redisClient = RedisClient.getInstance() ;

	private static String from; // 发件人
	private static String host; // smtp主机
	private static String username ;
	private static String password ;
	private static String feedbackReceiver;

	static{
		try {
			Properties mailProp = TopsConfReader.getConfProperties("properties/mail.properties", ConfScope.R) ;
			from = mailProp.getProperty("mail.from", "") ;
			host = mailProp.getProperty("mail.smtp", "") ;
			username = mailProp.getProperty("mail.auth.username", "") ;
			password = mailProp.getProperty("mail.auth.password", "") ;
			feedbackReceiver = mailProp.getProperty("mail.feedbackReceiver","linlin.tong@travelzen.com");
		} catch (Exception e) {
			logger.error("properties file read error when send mail...", e);
		}
	}

	public static void main(String args[]){
		redisClient.delete("template:mail:findpassword");
		String url = WebEnv.get("server.host.ssoServer", "") + "/sso/findPasswordNewPasswordEmail?sid=12312312" ;
		Map<String, String> params = new HashMap<String, String>() ;
		params.put("URL", url) ;
		params.put("DATE", DateTime.now().toString("yyyy-MM-dd")) ;
		params.put("HOMEPAGE", WebEnv.get("server.path.memberServer", "")) ;
		params.put("MEMBERINFO", WebEnv.get("server.path.memberServer", "") + "/member/info") ;
		List<Attachment> attachments = new ArrayList<Attachment>() ;
		Attachment attachment = new Attachment() ;
		attachment.setMediaId("http://192.168.161.121:8980/ztravel-common-mediaserver/imageservice?mediaImageId=56ceb259d4c61a8c2adf0acd&mediaType=download");
		attachment.setName("行程确认单.pdf");
		attachments.add(attachment) ;
		MailEntity entity = new MailEntity("piaokcd@163.com",null,ContentType.HTML,params,BizType.FINDPASSWORD, attachments) ;
		try {
			send(entity) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void send(MailEntity entity) throws Exception{

		String content = "" ;
		String contentType = "text/html; charset=utf-8" ;
		String subject = "" ;

		switch(entity.getBizType()){
		case FINDPASSWORD :
			if(!redisClient.exists("template:mail:findpassword")){
				byte[] bytes = TopsConfReader.getConfContent("template/mail/find-password.html", ConfScope.R) ;
				redisClient.setNoJSON("template:mail:findpassword", new String(bytes, "utf-8")) ;
				redisClient.persist("template:mail:findpassword") ;
			}
			subject = "真旅行-密码找回" ;
			break ;
		case UNPAY :
			if(!redisClient.exists("template:mail:unpay")){
				byte[] bytes = TopsConfReader.getConfContent("template/mail/unpay-mail.html", ConfScope.R) ;
				redisClient.setNoJSON("template:mail:unpay", new String(bytes, "utf-8")) ;
				redisClient.persist("template:mail:unpay") ;
			}
			subject = "真旅行-订单提交成功" ;
			break;
		case PAYED :
			if(!redisClient.exists("template:mail:payed")){
				byte[] bytes = TopsConfReader.getConfContent("template/mail/payed-mail.html", ConfScope.R) ;
				redisClient.setNoJSON("template:mail:payed", new String(bytes, "utf-8")) ;
				redisClient.persist("template:mail:payed") ;
			}
			subject = "真旅行-订单支付成功" ;
			break;
		case CONFIRM :
			if(!redisClient.exists("template:mail:confirm")){
				byte[] bytes = TopsConfReader.getConfContent("template/mail/confirm-mail.html", ConfScope.R) ;
				redisClient.setNoJSON("template:mail:confirm", new String(bytes, "utf-8")) ;
				redisClient.persist("template:mail:confirm") ;
			}
			subject = "真旅行-订单确认书" ;
			break;
		case PIECECONFIRM :
			if(!redisClient.exists("template:mail:confirm")){
				byte[] bytes = TopsConfReader.getConfContent("template/mail/piece-confirm-mail.html", ConfScope.R) ;
				redisClient.setNoJSON("template:mail:piececofirm", new String(bytes, "utf-8")) ;
				redisClient.persist("template:mail:piececonfirm") ;
			}
			subject = "真旅行-碎片化产品订单确认书" ;
			break;
		case BACKPAY :
			if(!redisClient.exists("template:mail:backpay")){
				byte[] bytes = TopsConfReader.getConfContent("template/mail/backpay.html", ConfScope.R) ;
				redisClient.setNoJSON("template:mail:backpay", new String(bytes, "utf-8")) ;
				redisClient.persist("template:mail:backpay") ;
			}
			subject = "真旅行-补款申请" ;
			break ;
		default : break ;
		}

		switch(entity.getContentType()){
		case HTML :
			content = redisClient.get("template:mail:findpassword") ;
			if(entity.getParams() != null){
				content = content.replaceAll("%URL%", entity.getParams().get("URL")) ;
				content = content.replaceAll("%DATE%", entity.getParams().get("DATE")) ;
				content = content.replaceAll("%HOMEPAGE%", entity.getParams().get("HOMEPAGE")) ;
				content = content.replaceAll("%MEMBERINFO%", entity.getParams().get("MEMBERINFO")) ;
			}
			contentType = "text/html;charset=utf-8" ;
			break ;
		case UNPAYHTML :
			content = redisClient.get("template:mail:unpay") ;
			if(entity.getParams() != null){
				content = content.replaceAll("%URL%", entity.getParams().get("URL")) ;
				content = content.replaceAll("%PAYURL%", entity.getParams().get("PAYURL")) ;
				content = content.replaceAll("%DETAILURL%", entity.getParams().get("DETAILURL")) ;
				content = content.replaceAll("%DATE%", entity.getParams().get("DATE")) ;
				content = content.replaceAll("%ORDERNO%", entity.getParams().get("ORDERNO")) ;
				content = content.replaceAll("%PRODUCTNAME%", entity.getParams().get("PRODUCTNAME")) ;
				content = content.replaceAll("%CREATETIME%", entity.getParams().get("CREATETIME")) ;
				content = content.replaceAll("%OFFTIME%", entity.getParams().get("OFFTIME")) ;
				content = content.replaceAll("%TOTALPRICE%", entity.getParams().get("TOTALPRICE")) ;
				content = content.replaceAll("%PAYMENT%", entity.getParams().get("PAYMENT")) ;
				content = content.replaceAll("%DISCOUNT%", entity.getParams().get("DISCOUNT")) ;
				content = content.replaceAll("%HOMEPAGE%", entity.getParams().get("HOMEPAGE")) ;
				content = content.replaceAll("%MEMBERINFO%", entity.getParams().get("MEMBERINFO")) ;
				subject += "【" + entity.getParams().get("PRODUCTNAME") + "】" ;
			}
			contentType = "text/html;charset=utf-8" ;
			break ;
		case PAYEDHTML :
			content = redisClient.get("template:mail:payed") ;
			if(entity.getParams() != null){
				content = content.replaceAll("%URL%", entity.getParams().get("URL")) ;
				content = content.replaceAll("%DETAILURL%", entity.getParams().get("DETAILURL")) ;
				content = content.replaceAll("%DATE%", entity.getParams().get("DATE")) ;
				content = content.replaceAll("%ORDERNO%", entity.getParams().get("ORDERNO")) ;
				content = content.replaceAll("%PRODUCTNAME%", entity.getParams().get("PRODUCTNAME")) ;
				content = content.replaceAll("%CREATETIME%", entity.getParams().get("CREATETIME")) ;
				content = content.replaceAll("%OFFTIME%", entity.getParams().get("OFFTIME")) ;
				content = content.replaceAll("%TOTALPRICE%", entity.getParams().get("TOTALPRICE")) ;
				content = content.replaceAll("%PAYMENT%", entity.getParams().get("PAYMENT")) ;
				content = content.replaceAll("%DISCOUNT%", entity.getParams().get("DISCOUNT")) ;
				content = content.replaceAll("%HOMEPAGE%", entity.getParams().get("HOMEPAGE")) ;
				content = content.replaceAll("%MEMBERINFO%", entity.getParams().get("MEMBERINFO")) ;
				subject += "【" + entity.getParams().get("PRODUCTNAME") + "】" ;
			}
			contentType = "text/html;charset=utf-8" ;
			break ;
		case CONFIRMHTML :
			content = redisClient.get("template:mail:confirm") ;
			if(entity.getParams() != null){
				content = content.replaceAll("%CONTACTORNAME%", entity.getParams().get("CONTACTORNAME")) ;
				content = content.replaceAll("%URL%", entity.getParams().get("URL")) ;
				content = content.replaceAll("%DETAILURL%", entity.getParams().get("DETAILURL")) ;
				content = content.replaceAll("%DATE%", entity.getParams().get("DATE")) ;
				content = content.replaceAll("%ORDERNO%", entity.getParams().get("ORDERNO")) ;
				content = content.replaceAll("%PRODUCTNAME%", entity.getParams().get("PRODUCTNAME")) ;
				content = content.replaceAll("%CREATETIME%", entity.getParams().get("CREATETIME")) ;
				content = content.replaceAll("%OFFTIME%", entity.getParams().get("OFFTIME")) ;
				content = content.replaceAll("%TOTALPRICE%", entity.getParams().get("TOTALPRICE")) ;
				content = content.replaceAll("%PAYMENT%", entity.getParams().get("PAYMENT")) ;
				content = content.replaceAll("%DISCOUNT%", entity.getParams().get("DISCOUNT")) ;
				content = content.replaceAll("%HOMEPAGE%", entity.getParams().get("HOMEPAGE")) ;
				content = content.replaceAll("%MEMBERINFO%", entity.getParams().get("MEMBERINFO")) ;
				subject += "【" + entity.getParams().get("PRODUCTNAME") + "】" ;
			}
			contentType = "text/html;charset=utf-8" ;
			break ;
		case STRING :
			content = entity.getContent() ;
			contentType = "text/plain;charset=utf-8" ;
			break ;
		case BACKPAYHTML :
			content = redisClient.get("template:mail:backpay") ;
			if(entity.getParams() != null){
				content = content.replaceAll("%URL%", entity.getParams().get("URL")) ;
				content = content.replaceAll("%DATE%", entity.getParams().get("DATE")) ;
				content = content.replaceAll("%HOMEPAGE%", entity.getParams().get("HOMEPAGE")) ;
				content = content.replaceAll("%MEMBERINFO%", entity.getParams().get("MEMBERINFO")) ;
				subject += "【" + entity.getParams().get("PRODUCTNAME") + "】" ;
			}
			contentType = "text/html;charset=utf-8" ;
			break ;
		default : break ;
		}
		final MailUtil mailUtil = new MailUtil() ;
		mailUtil.setFrom(from);
		mailUtil.setTo(entity.getTo());
		mailUtil.setContent(content);
		mailUtil.setHost(host);
		mailUtil.setContentType(contentType);
		mailUtil.setUsername(username);
		mailUtil.setPassword(password);
		mailUtil.setSubject(subject);
		mailUtil.setAttachments(entity.getAttachments());
		exec.execute(new Runnable() {
			@Override
			public void run() {
				mailUtil.sendMail() ;
			}
		});
	}
	public static void sendFeedback(String subject, String content) throws Exception{
		final MailUtil mailUtil = new MailUtil();
		mailUtil.setFrom(from);
		mailUtil.setTo(feedbackReceiver);
		mailUtil.setSubject(subject);
		mailUtil.setContent(content);
		mailUtil.setHost(host);
		mailUtil.setContentType("text/plain;charset=utf-8");
		mailUtil.setUsername(username);
		mailUtil.setPassword(password);
		exec.execute(new Runnable() {
			@Override
			public void run() {
				mailUtil.sendMail() ;
			}
		});
	}
}
