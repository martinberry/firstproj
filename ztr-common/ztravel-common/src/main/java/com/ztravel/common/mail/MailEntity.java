package com.ztravel.common.mail;

import java.util.List;
import java.util.Map;

import com.ztravel.common.entity.Attachment;

/**
 * @author wanhaofan
 * */
public class MailEntity {
	private String to ;
	private String content ;
	private MailEnums.ContentType contentType ;
	private Map<String, String> params ;
	private MailEnums.BizType bizType ;
	
	private List<Attachment> attachments ;

	public MailEntity(){} ;

	public MailEntity(String to, String content, MailEnums.ContentType contentType, Map<String, String> params, MailEnums.BizType bizType){
		this.to = to ;
		this.content = content ;
		this.contentType = contentType ;
		this.params = params ;
		this.bizType = bizType ;
	}
	
	public MailEntity(String to, String content, MailEnums.ContentType contentType, Map<String, String> params, MailEnums.BizType bizType, List<Attachment> attachments){
		this(to, content, contentType, params, bizType) ;
		this.attachments = attachments ;
	}

	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public MailEnums.ContentType getContentType() {
		return contentType;
	}
	public void setContentType(MailEnums.ContentType contentType) {
		this.contentType = contentType;
	}
	public Map<String, String> getParams() {
		return params;
	}
	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	public MailEnums.BizType getBizType() {
		return bizType;
	}
	public void setBizType(MailEnums.BizType bizType) {
		this.bizType = bizType;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}


}
