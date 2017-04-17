package com.ztravel.member.front.vo;

import java.util.List;

public class WishDetailResponse {

	private String id ;

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

	/**
	 * 产品显示图
	 */
	private String image ;

	/**
	 * 产品状态
	 */
	private String status ;

	/**
	 * 是否已游玩
	 */
	private Boolean hasGo ;

	/**
	 * 收藏时间
	 */
	private String createdTime ;

	/**
	 * 收藏数量
	 */
	private long count ;

	/**
	 * 收藏该产品的所有会员
	 */
	private List<MemberResponse> members;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getHasGo() {
		return hasGo;
	}

	public void setHasGo(Boolean hasGo) {
		this.hasGo = hasGo;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}

	public List<MemberResponse> getMembers() {
		return members;
	}

	public void setMembers(List<MemberResponse> members) {
		this.members = members;
	}


}
