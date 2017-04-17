package com.ztravel.member.po;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

import com.github.jmkgreen.morphia.annotations.Entity;
import com.github.jmkgreen.morphia.annotations.Id;

@Entity(value="wish", noClassnameStored=true)
public class WishEntity {

	@Id
	private ObjectId id;

	/**
	 * 用户id主键
	 */
	private String memberId;

	/**
	 * 产品id主键
	 */
	private String productId ;

	/**
	 * 产品业务ID
	 */
	private String pid ;

	/**
	 * 产品名称
	 */
	private String pName ;

	private DateTime createdTime ;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public DateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(DateTime createdTime) {
		this.createdTime = createdTime;
	}


}
