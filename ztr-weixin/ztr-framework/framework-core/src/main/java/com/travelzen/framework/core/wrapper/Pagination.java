package com.travelzen.framework.core.wrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 8692496180734037486L;

    /** 总页数 */
    private int totalPageCount;

    /** 当前页码 */
    private int pageNo;

	/** 页面大小（一页当中的数据条目数量） */
    private int pageSize;

    /** 总数据条目数 */
    private int totalItemCount;

    /** 源数据 */
    private Map<String, Object> meta;

    private Collection<T> data;

    public Pagination() {
        this.pageSize = 10;
        this.pageNo = 1;
        this.totalPageCount = 1;
    }



    public Map<String, Object> getMeta() {
		return meta;
	}



	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}

    public Pagination(int pageNo, int pageSize) {
        this.pageNo = pageNo;;
        this.pageSize = pageSize;
    }

    public void setTotalItemCount(int itemCount) {
        this.totalItemCount = itemCount;
        this.totalPageCount = (itemCount + pageSize - 1) / pageSize;
        this.totalPageCount = totalPageCount <= 0 ? 1 : totalPageCount;
    }

    @JsonIgnore
    public int getStart() {
        return pageSize * (pageNo - 1);
    }

    public long getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount < 1 ? 1 : totalPageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo != null) {
            this.pageNo = pageNo < 1 ? 1 : pageNo;
        }
    }

	public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize != null) {
            this.pageSize = pageSize;
        }
    }

    public long getTotalItemCount() {
        return totalItemCount;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }

    public void addMeta(String key, Object obj) {
        if (meta == null) {
            meta = new HashMap<String, Object>();
        }
        meta.put(key, obj);
    }

    public Object getMeta(String key) {
        if (meta == null) {
            return null;
        }
        return meta.get(key);
    }

    public Set<String> getMetaKeys() {
        if (meta == null) {
            return null;
        }
        return meta.keySet();
    }

    public Object removeMeta(String key) {
        if (meta == null) {
            return null;
        }
        return meta.remove(key);
    }

}
