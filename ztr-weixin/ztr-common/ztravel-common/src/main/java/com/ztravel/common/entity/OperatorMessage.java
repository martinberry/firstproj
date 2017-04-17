package com.ztravel.common.entity;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.common.enums.MessageTitleType;
import com.ztravel.common.enums.MessageType;

@Entity(value="operatorMessage", noClassnameStored=true)
public class OperatorMessage {
	@Id
	private ObjectId id;
	private MessageType type;
	private MessageTitleType title;
	private String content;
	private String link;
	private DateTime time;
	private boolean hasRead;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public DateTime getTime() {
		return time;
	}
	public void setTime(DateTime time) {
		this.time = time;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
	public MessageTitleType getTitle() {
		return title;
	}
	public void setTitle(MessageTitleType title) {
		this.title = title;
	}
}
