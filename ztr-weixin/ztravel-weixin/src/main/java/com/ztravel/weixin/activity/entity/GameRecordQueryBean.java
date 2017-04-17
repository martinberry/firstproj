package com.ztravel.weixin.activity.entity;

/**
 *
 * @author zhoubo
 *
 */
public class GameRecordQueryBean {
//	/**
//	 * 活动名称
//	 */
//	private String activityName;
	/**
	 * 页数
	 */
	private int pageNo = -1;
	/**
	 * 每页大小
	 */
	private int pageSize = -1;

//	public String getActivityName() {
//		return activityName;
//	}
//
//	public void setActivityName(String activityName) {
//		this.activityName = activityName;
//	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
