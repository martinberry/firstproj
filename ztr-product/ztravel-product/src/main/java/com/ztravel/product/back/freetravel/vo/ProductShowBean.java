package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.product.back.freetravel.enums.Status;

public class ProductShowBean {
	@Id
	private ObjectId id;

	//业务ID
	private String pid ;

	//产品名称
	private String pName ;

	private String theme ;

	private List<String> tags ;

	private String from ;

	private List<String> to ;

	//产品状态
	private Status status ;

	private DateTime createdTime ;

	private DateTime updatedTime ;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}


	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public DateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(DateTime createdTime) {
		this.createdTime = createdTime;
	}

	public DateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(DateTime updatedTime) {
		this.updatedTime = updatedTime;
	}
}
