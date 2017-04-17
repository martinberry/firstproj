package com.ztravel.paygate.core.po.gen;

import java.io.Serializable;

/**
 * 订单材料联系人
 */
public class OrderMateriaContactor implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 联系人姓名
     */
    private String contactor;

    /**
     * 订单Id
     */
    private String orderId;

    /**
     * 联系人手机号
     */
    private String phone;

    /**
     * 联系人email
     */
    private String email;

    /**
     * 联系人所在省
     */
    private String province;

    /**
     * 联系人所在市
     */
    private String city;

    /**
     * 联系人所在县
     */
    private String county;

    /**
     * 联系人详细地址
     */
    private String address;

    /**
     * @return 联系人姓名
     */
    public String getContactor() {
        return contactor;
    }

    /**
     * @param contactor 联系人姓名
     */
    public void setContactor(String contactor) {
        this.contactor = contactor == null ? null : contactor.trim();
    }

    /**
     * @return 订单Id
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * @param orderId 订单Id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * @return 联系人手机号
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone 联系人手机号
     */
    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    /**
     * @return 联系人email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email 联系人email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * @return 联系人所在省
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province 联系人所在省
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * @return 联系人所在市
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city 联系人所在市
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * @return 联系人所在县
     */
    public String getCounty() {
        return county;
    }

    /**
     * @param county 联系人所在县
     */
    public void setCounty(String county) {
        this.county = county == null ? null : county.trim();
    }

    /**
     * @return 联系人详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address 联系人详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        OrderMateriaContactor other = (OrderMateriaContactor) that;
        return (this.getContactor() == null ? other.getContactor() == null : this.getContactor().equals(other.getContactor()))
            && (this.getOrderId() == null ? other.getOrderId() == null : this.getOrderId().equals(other.getOrderId()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getProvince() == null ? other.getProvince() == null : this.getProvince().equals(other.getProvince()))
            && (this.getCity() == null ? other.getCity() == null : this.getCity().equals(other.getCity()))
            && (this.getCounty() == null ? other.getCounty() == null : this.getCounty().equals(other.getCounty()))
            && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getContactor() == null) ? 0 : getContactor().hashCode());
        result = prime * result + ((getOrderId() == null) ? 0 : getOrderId().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getProvince() == null) ? 0 : getProvince().hashCode());
        result = prime * result + ((getCity() == null) ? 0 : getCity().hashCode());
        result = prime * result + ((getCounty() == null) ? 0 : getCounty().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        return result;
    }
}