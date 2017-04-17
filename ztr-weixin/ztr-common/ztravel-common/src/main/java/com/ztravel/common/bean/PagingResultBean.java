package com.ztravel.common.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页查询结果封装
 *
 * @author zuoning.shen
 *
 * @param <T>
 */
public class PagingResultBean<T> {
	private int pageIndex = 1;
	private int pageSize = 10;
	private long total;
	private Collection<T> data = new ArrayList<T>();
    private Map<String,Object> sumMap=new HashMap<String,Object>();
	private String errors;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if (pageIndex < 1) {
			pageIndex = 1;
		}
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		if (pageSize < 0) {
			pageSize = 10;
		}
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public Map<String,Object> getSumMap() {
		return sumMap;
	}

	public void setSumMap(Map<String,Object> sumMap) {
		this.sumMap = sumMap;
	}

}
