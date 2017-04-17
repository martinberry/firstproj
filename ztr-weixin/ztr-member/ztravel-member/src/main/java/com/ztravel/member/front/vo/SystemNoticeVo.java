package com.ztravel.member.front.vo;

import org.joda.time.DateTime;

import com.ztravel.common.enums.NoticeType;

public class SystemNoticeVo {
	private String id;
	private String memberId;
	private NoticeType type;
	private String content;
	private DateTime dateTime;
	private boolean hasRead;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public NoticeType getType() {
		return type;
	}
	public void setType(NoticeType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public DateTime getDateTime() {
		return dateTime;
	}
	public void setDateTime(DateTime dateTime) {
		this.dateTime = dateTime;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
}
