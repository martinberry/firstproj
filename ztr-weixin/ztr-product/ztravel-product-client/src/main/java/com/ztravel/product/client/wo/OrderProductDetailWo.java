package com.ztravel.product.client.wo;

import java.util.List;
import java.util.Map;

public class OrderProductDetailWo {

	private String id;

	//业务ID
	private String pid ;

	//产品名称
	private String pName ;

	private List<String> images ;

    /**
     * 预订日期
     * */
    private String bookDate;

	private OrderFlightWo flight ;

	private List<OrderHotelWo> hotels ;

	private Map<String, String> additionalInfos ;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public String getBookDate() {
		return bookDate;
	}

	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}

	public OrderFlightWo getFlight() {
		return flight;
	}

	public void setFlight(OrderFlightWo flight) {
		this.flight = flight;
	}

	public List<OrderHotelWo> getHotels() {
		return hotels;
	}

	public void setHotels(List<OrderHotelWo> hotels) {
		this.hotels = hotels;
	}

	public Map<String, String> getAdditionalInfos() {
		return additionalInfos;
	}

	public void setAdditionalInfos(Map<String, String> additionalInfos) {
		this.additionalInfos = additionalInfos;
	}

}
