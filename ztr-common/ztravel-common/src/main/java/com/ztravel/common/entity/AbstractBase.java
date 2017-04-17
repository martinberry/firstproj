package com.ztravel.common.entity;

import org.joda.time.DateTime;

public class AbstractBase{

	private String createdby;

	private String updatedby;

	private DateTime updated;

	private DateTime created;

	public AbstractBase() {}

	public String getCreatedby(){
		return createdby;
	}

	public void setCreatedby(String createdby){
		this.createdby=createdby;
	}

	public String getUpdatedby(){
		return updatedby;
	}

	public void setUpdatedby(String updatedby){
		this.updatedby=updatedby;
	}

	public DateTime getUpdated(){
		return updated;
	}

	public void setUpdated(DateTime updated){
		this.updated=updated;
	}

	public DateTime getCreated(){
		return created;
	}

	public void setCreated(DateTime created){
		this.created=created;
	}

}
