package com.ztravel.mediaserver.client;

public enum CompressType {

	NoCompress("no","不压缩"),Normal("s1","标准1"),Cut("s2","剪切压缩");
	
	private final String value;
	
	private final String desc;

	private CompressType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}
	


}
