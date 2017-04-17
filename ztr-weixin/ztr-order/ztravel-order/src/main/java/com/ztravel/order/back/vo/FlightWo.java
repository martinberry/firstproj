package com.ztravel.order.back.vo;

import java.util.List;
import java.util.Map;

import com.ztravel.reuse.product.entity.ProductFlightInfo;


public class FlightWo {

    private String from;

    private List<ProductFlightInfo> go ;

    private List<ProductFlightInfo> back ;

    private Map<String, List<ProductFlightInfo>> middle ;

    public List<ProductFlightInfo> getGo() {
        return go;
    }

    public void setGo(List<ProductFlightInfo> go) {
        this.go = go;
    }

    public List<ProductFlightInfo> getBack() {
        return back;
    }

    public void setBack(List<ProductFlightInfo> back) {
        this.back = back;
    }

    public Map<String, List<ProductFlightInfo>> getMiddle() {
        return middle;
    }

    public void setMiddle(Map<String, List<ProductFlightInfo>> middle) {
        this.middle = middle;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

}
