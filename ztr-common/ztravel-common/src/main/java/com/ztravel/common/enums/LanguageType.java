package com.ztravel.common.enums;

public enum LanguageType {
	CHINESE("中文"),
	ENGLISH("英语"),
	FRENCH("法语"),
	PORTUGUESE("葡萄牙语"),
	SPANISH("西班牙语"),
	JAPANESE("日语"),
	KOREAN("韩语"),
	VIETNAMESE("越南语"),
	THAI("泰语");

	private final String description;

	LanguageType(String description) {
        this.description = description;
    }

	public String getDescription() {
		return description;
	}

}
