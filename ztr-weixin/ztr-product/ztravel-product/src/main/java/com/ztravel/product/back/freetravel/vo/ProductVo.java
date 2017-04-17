package com.ztravel.product.back.freetravel.vo;

import java.util.List;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Id;
import com.ztravel.product.back.freetravel.enums.Status;

public class ProductVo {
	@Id
	private ObjectId id;

	//业务ID
	private String pid ;

	//产品名称
	private String pName ;

	private String theme ;

	private List<String> tags ;

	private String from ;

	private String to ;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	//产品状态
	private String status ;

	private String createdTime ;

	private String updatedTime ;

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



	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(String updatedTime) {
		this.updatedTime = updatedTime;
	}
}
