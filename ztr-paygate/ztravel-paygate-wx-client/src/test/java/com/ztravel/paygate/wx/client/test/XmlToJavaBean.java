package com.ztravel.paygate.wx.client.test;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XmlFriendlyReplacer;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.ztravel.paygate.wx.client.entity.UnifieldOrderRequest;

public class XmlToJavaBean {
	
	@Test
	public void reuqestToXml() {
		
		UnifieldOrderRequest request = new UnifieldOrderRequest();
		request.setAppid("appid");
		request.setBody("body");
		request.setMch_id("mch_id");
		
		XStream xstream = new XStream(new XppDriver(new XmlFriendlyReplacer("_-", "_")));
		xstream.alias("xml", UnifieldOrderRequest.class);
		String xml = xstream.toXML(request);
		
		System.out.println(xml);
		
		XStream xstreamTo = new XStream();
		xstreamTo.alias("xml", UnifieldOrderRequest.class);
		UnifieldOrderRequest entity = (UnifieldOrderRequest) xstreamTo.fromXML(xml);
		System.out.println(JSONObject.toJSON(entity).toString());
		
	}

}
