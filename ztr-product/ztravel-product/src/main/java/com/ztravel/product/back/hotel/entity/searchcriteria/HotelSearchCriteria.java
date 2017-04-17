/**
 *
 */
package com.ztravel.product.back.hotel.entity.searchcriteria;

import javax.validation.constraints.Pattern;

import com.ztravel.common.entity.PaginationEntity;

/**
 * @author
 * 产品管理-->酒店资源 酒店查询条件
 */
public class HotelSearchCriteria extends PaginationEntity {
	/**
	 * 酒店中文名称
	 */
//	@Pattern(regexp = "^([a-zA-Z\u4E00-\u9FA5\\d\\,\\.\\;\\:\\，\\。\\、\\；\\：\\“\\”\\’\\（\\）\\【\\】\\——\\/\\]{0,60})$")
	@Pattern(regexp = "^[\\S ]{0,60}$")
	private String hotelNameCn;
	/**
	 * 目的地 国家
	 */
	private String destCountry;
	/**
	 * 目的地 城市/景点
	 */
	private String destCityOrAttraction;
	/**
	 * 状态
	 */
	private String status;

	public String getHotelNameCn() {
		return hotelNameCn;
	}
	public void setHotelNameCn(String hotelNameCn) {
		this.hotelNameCn = hotelNameCn;
	}
	public String getDestCountry() {
		return destCountry;
	}
	public void setDestCountry(String destCountry) {
		this.destCountry = destCountry;
	}
	public String getDestCityOrAttraction() {
		return destCityOrAttraction;
	}
	public void setDestCityOrAttraction(String destCityOrAttraction) {
		this.destCityOrAttraction = destCityOrAttraction;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
