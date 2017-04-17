package com.ztravel.product.back.freetravel.vo;


public class SupplierSaveBean {
	//产品Id
	private String id ;

	private String flightSupplier ;

	private String hotelSupplier ;

	private String supplier ;

	private Boolean isNextStep ;



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getFlightSupplier() {
		return flightSupplier;
	}

	public void setFlightSupplier(String flightSupplier) {
		this.flightSupplier = flightSupplier;
	}

	public String getHotelSupplier() {
		return hotelSupplier;
	}

	public void setHotelSupplier(String hotelSupplier) {
		this.hotelSupplier = hotelSupplier;
	}

	public Boolean getIsNextStep() {
		return isNextStep;
	}

	public void setIsNextStep(Boolean isNextStep) {
		this.isNextStep = isNextStep;
	}

}
