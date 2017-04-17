/**
 *
 */
package com.ztravel.product.back.freetravel.entity;

import com.ztravel.common.entity.PaginationEntity;

/**
 * @author
 *
 */
public class ProductSearchCriteria extends PaginationEntity {


	private String pid;


	private	String from;

	private String theme;

	private String status ;

	private String pName ;


	private String toCountry;

	private String toContinent;

	private String to;

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}



	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}



	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getToCountry() {
		return toCountry;
	}

	public void setToCountry(String toCountry) {
		this.toCountry = toCountry;
	}

	public String getToContinent() {
		return toContinent;
	}

	public void setToContinent(String toContinent) {
		this.toContinent = toContinent;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}


}
