package com.ztravel.product.weixin.wo;

import java.util.List;

public class WxCalendarDataWo {

    private String date;

    private List<WxDayWo> wxDayWoList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<WxDayWo> getWxDayWoList() {
        return wxDayWoList;
    }

    public void setWxDayWoList(List<WxDayWo> wxDayWoList) {
        this.wxDayWoList = wxDayWoList;
    }

}
