package com.ztravel.order.back.vo;

import java.util.List;

public class HotelWo {

    private String hotelId;

    /**
     * 入住日期
     */
    private String checkInDate;

    /**
     * 离店日期
     */
    private String checkOutDate;

    /**
     * 酒店名称
     */
    private String hotelName;
    /**
     * 房型名称
     * */
    private String roomType;

    /**
     * 酒店类型
     */
    private String hotelType;

    private Integer tripNights;

  //入住偏移时间
    private String checkinDaysStr ;

    //入住偏移时间
    private List<Integer> checkinDays ;

    public String getCheckinDaysStr() {
        return checkinDaysStr;
    }

    public void setCheckinDaysStr(String checkinDaysStr) {
        this.checkinDaysStr = checkinDaysStr;
    }

    public List<Integer> getCheckinDays() {
        return checkinDays;
    }

    public void setCheckinDays(List<Integer> checkinDays) {
        this.checkinDays = checkinDays;
    }

    public String getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(String checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(String checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getHotelType() {
        return hotelType;
    }

    public void setHotelType(String hotelType) {
        this.hotelType = hotelType;
    }

    public Integer getTripNights() {
        return tripNights;
    }

    public void setTripNights(Integer tripNights) {
        this.tripNights = tripNights;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

}
