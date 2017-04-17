package com.ztravel.member.po;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

@Entity(value="privateLetter", noClassnameStored=true)
public class PrivateLetter {
	@Id
	private ObjectId id;
	private String authorId;
	private String anotherId;
	private DateTime updateTime;
	private boolean hasRead;
	private List<PrivateMsg> msgs;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
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
	public DateTime getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(DateTime updateTime) {
		this.updateTime = updateTime;
	}
	public List<PrivateMsg> getMsgs() {
		return msgs;
	}
	public void setMsgs(List<PrivateMsg> msgs) {
		this.msgs = msgs;
	}
	public boolean isHasRead() {
		return hasRead;
	}
	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}
}
