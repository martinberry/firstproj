package com.ztravel.product.po.pieces.common;

import java.util.List;

import com.ztravel.common.enums.PieceType;

public class BasicInfo {

	private String pname;

	private PieceType type;

	private List<String> toContinent;//洲

	private List<String> toCountry;
	private List<String> toCity;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public List<String> getToContinent() {
		return toContinent;
	}

	public void setToContinent(List<String> toContinent) {
		this.toContinent = toContinent;
	}

	public List<String> getToCountry() {
		return toCountry;
	}

	public void setToCountry(List<String> toCountry) {
		this.toCountry = toCountry;
	}

	public List<String> getToCity() {
		return toCity;
	}

	public void setToCity(List<String> toCity) {
		this.toCity = toCity;
	}

    public PieceType getType() {
        return type;
    }

    public void setType(PieceType type) {
        this.type = type;
    }
}
