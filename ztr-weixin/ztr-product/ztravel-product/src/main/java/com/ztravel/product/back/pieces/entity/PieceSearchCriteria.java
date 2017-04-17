package com.ztravel.product.back.pieces.entity;

import com.ztravel.common.entity.PaginationEntity;

public class PieceSearchCriteria extends PaginationEntity {

    private String pid;

    private String pname;

    private String type;

    private String status;

    private String toCountry;

    private String toContinent;

    private String toCity;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

}
