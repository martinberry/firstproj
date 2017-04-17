package com.ztravel.reuse.member.entity;


public class PrivateLetterVo {
	private String id;
	private String authorId;
	private String anotherId;
	/*yyyy-MM-dd HH:mm*/
	private String updateTime;
	private boolean hasRead;
	private String msgContent;
	private String anotherNickname;
	private String anotherHead;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAuthorId() {
		return authorId;
	}
	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}
	public String getAnotherId() {
		return anotherId;
	}
	public void setAnotherId(String anotherId) {
		this.anotherId = anotherId;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	public String getMsgContent() {
		return msgContent;
	}
	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}
	public String getAnotherNickname() {
		return anotherNickname;
	}
	public void setAnotherNickname(String anotherNickname) {
		this.anotherNickname = anotherNickname;
	}
	public String getAnotherHead() {
		return anotherHead;
	}
	public void setAnotherHead(String anotherHead) {
		this.anotherHead = anotherHead;
	}
}
