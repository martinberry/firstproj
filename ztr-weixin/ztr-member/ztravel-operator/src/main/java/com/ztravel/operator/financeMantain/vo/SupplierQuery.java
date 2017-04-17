package com.ztravel.operator.financeMantain.vo;

import com.ztravel.operator.financeMantain.po.Supplier;

public class SupplierQuery extends Supplier{
    private int pageNo = -1;

    private int pageSize = -1;

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
