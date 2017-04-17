package com.ztravel.common.entity;

public class PaginationEntity {
	/** 总页数 */
    private int totalPageCount;

    /** 当前页码 */
    private int pageNo;

	/** 页面大小（一页当中的数据条目数量） */
    private int pageSize;

    /** 总数据条目数 */
    private int totalItemCount;

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}

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

	public int getTotalItemCount() {
		return totalItemCount;
	}

	public void setTotalItemCount(int totalItemCount) {
		this.totalItemCount = totalItemCount;
	}

}
