package com.ztravel.product.back.freetravel.entity;


/**
 * @author wanhaofan
 * 自由行产品成本
 * */
public class Cost {
	//机票成人成本
	private Double flightAdultCost ;

	//机票儿童成本
	private Double flightChildCost ;

	//酒店房间成本
	private Double[] hotelRoomCost ;

	//打包产品成人成本
	private Double packageAdultCost ;

	//打包产品儿童成本
	private Double packageChildCost ;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((flightAdultCost == null) ? 0 : flightAdultCost.hashCode());
		result = prime * result
				+ ((flightChildCost == null) ? 0 : flightChildCost.hashCode());
		result = prime * result
				+ ((hotelRoomCost == null) ? 0 : hotelRoomCost.hashCode());
		result = prime
				* result
				+ ((packageAdultCost == null) ? 0 : packageAdultCost.hashCode());
		result = prime
				* result
				+ ((packageChildCost == null) ? 0 : packageChildCost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cost other = (Cost) obj;
		if (flightAdultCost == null) {
			if (other.flightAdultCost != null)
				return false;
		} else if (!flightAdultCost.equals(other.flightAdultCost))
			return false;
		if (flightChildCost == null) {
			if (other.flightChildCost != null)
				return false;
		} else if (!flightChildCost.equals(other.flightChildCost))
			return false;
		if (hotelRoomCost == null) {
			if (other.hotelRoomCost != null)
				return false;
		} else if (!hotelRoomCost.equals(other.hotelRoomCost))
			return false;
		if (packageAdultCost == null) {
			if (other.packageAdultCost != null)
				return false;
		} else if (!packageAdultCost.equals(other.packageAdultCost))
			return false;
		if (packageChildCost == null) {
			if (other.packageChildCost != null)
				return false;
		} else if (!packageChildCost.equals(other.packageChildCost))
			return false;
		return true;
	}

	public Boolean isContainFlightCost(Product p){
		if(p.isContainFlight()){
			return this.flightAdultCost != null || this.packageAdultCost != null ;
		}else{
			return false ;
		}
	}

	public Boolean isContainHotelCost(Product p){
		if(p.isContainHotel()){
			return this.hotelRoomCost != null || this.packageAdultCost != null ;
		}else{
			return false ;
		}
	}

	public Double getFlightAdultCost() {
		return flightAdultCost;
	}

	public void setFlightAdultCost(Double flightAdultCost) {
		this.flightAdultCost = flightAdultCost;
	}

	public Double getFlightChildCost() {
		return flightChildCost;
	}

	public void setFlightChildCost(Double flightChildCost) {
		this.flightChildCost = flightChildCost;
	}

	public Double[] getHotelRoomCost() {
		return hotelRoomCost;
	}

	public void setHotelRoomCost(Double[] hotelRoomCost) {
		this.hotelRoomCost = hotelRoomCost;
	}

	public Double getPackageAdultCost() {
		return packageAdultCost;
	}

	public void setPackageAdultCost(Double packageAdultCost) {
		this.packageAdultCost = packageAdultCost;
	}

	public Double getPackageChildCost() {
		return packageChildCost;
	}

	public void setPackageChildCost(Double packageChildCost) {
		this.packageChildCost = packageChildCost;
	}



}
