package com.ztravel.weixin.template.pojo;

import java.util.Map;

public class Template {

	private String template_id;

    private String touser;

    private String url;

    private String topcolor;

    private Map<String, TemplateData> data;

	public Template() {
		super();
	}

	public Template(String template_id, String touser, String url,
			String topcolor) {
		super();
		this.template_id = template_id;
		this.touser = touser;
		this.url = url;
		this.topcolor = topcolor;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}

	public Map<String, TemplateData> getData() {
		return data;
	}

	public void setData(Map<String, TemplateData> data) {
		this.data = data;
	}

}
