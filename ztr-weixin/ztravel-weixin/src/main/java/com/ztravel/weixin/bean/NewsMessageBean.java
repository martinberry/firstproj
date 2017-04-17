package com.ztravel.weixin.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class NewsMessageBean extends BaseMessageBean {

	private int articleCount;

	 private List<NewsItem> items;

	public int getArticleCount() {
		return articleCount;
	}

	public void setArticleCount(int articleCount) {
		this.articleCount = articleCount;
	}

	public List<NewsItem> getItems() {
		return items;
	}

	public void setItems(List<NewsItem> items) {
		this.items = items;
	}

	public NewsMessageBean() {
		super();
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "news";
	}

	public NewsMessageBean(NewsItem item) {
		super();
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "news";
		this.articleCount = 1;
		this.items = new ArrayList<NewsItem>();
		this.items.add(item);
	}

	public NewsMessageBean(List<NewsItem> items) {
		super();
		this.createTime = Calendar.getInstance().getTimeInMillis() /1000;
		this.msgType = "news";
		this.articleCount = items.size();
		this.items = items;
	}

	public String buildXmlToWeixin(){

		StringBuffer xmlBuffer = new StringBuffer();

		xmlBuffer.append("<xml>");
		xmlBuffer.append("<ToUserName><![CDATA[").append(toUserName).append("]]></ToUserName>");
		xmlBuffer.append("<FromUserName><![CDATA[").append(fromUserName).append("]]></FromUserName>");
		xmlBuffer.append("<CreateTime>").append(createTime).append("</CreateTime>");
		xmlBuffer.append("<MsgType><![CDATA[").append(msgType).append("]]></MsgType>");
		xmlBuffer.append("<ArticleCount><![CDATA[").append(articleCount).append("]]></ArticleCount>");
		xmlBuffer.append("<Articles>");
		for ( NewsItem item : items) {
			xmlBuffer.append("<item>");
			xmlBuffer.append("<Title><![CDATA[").append(item.getTitle()).append("]]></Title>");
			xmlBuffer.append("<Description><![CDATA[").append(item.getDescription()).append("]]></Description>");
			xmlBuffer.append("<PicUrl><![CDATA[").append(item.getPicUrl()).append("]]></PicUrl>");
			xmlBuffer.append("<Url><![CDATA[").append(item.getUrl()).append("]]></Url>");
			xmlBuffer.append("</item>");
		}
		xmlBuffer.append("</Articles>");
		xmlBuffer.append("</xml>");

		return xmlBuffer.toString();

	}

}
