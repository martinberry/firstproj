package com.ztravel.weixin.template.pojo;

public class TemplateData {

	private String value;

    private String color;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

	public TemplateData() {
		super();
	}

	public TemplateData(String color, String value) {
		super();
		this.value = value;
		this.color = color;
	}

}
