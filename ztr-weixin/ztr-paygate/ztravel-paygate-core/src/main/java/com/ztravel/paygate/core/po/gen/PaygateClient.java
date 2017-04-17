package com.ztravel.paygate.core.po.gen;

import java.io.Serializable;
import java.util.Date;

/**
 * 网关终端信息表
 */
public class PaygateClient implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String paygateClientId;

    /**
     * 终端标识
     */
    private String clientId;

    /**
     * 签名key
     */
    private String signKey;

    /**
     * 是否支持签名
     */
    private Boolean supportsEncrypt;

    /**
     * 描述信息
     */
    private String describe;

    /**
     * 是否支持支付
     */
    private Boolean supportsPayment;

    /**
     * 是否支持退款
     */
    private Boolean supportsRefund;

    /**
     * 是否支持查询
     */
    private Boolean supportsQuery;

    /**
     * 支持的网关类型,多个以逗号分隔
     */
    private String supportsGatetype;

    /**
     * 支付成功通知地址
     */
    private String paymentNotifyUrl;

    /**
     * 退款成功通知地址
     */
    private String refundNotifyUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * @return ID
     */
    public String getPaygateClientId() {
        return paygateClientId;
    }

    /**
     * @param paygateClientId ID
     */
    public void setPaygateClientId(String paygateClientId) {
        this.paygateClientId = paygateClientId == null ? null : paygateClientId.trim();
    }

    /**
     * @return 终端标识
     */
    public String getClientId() {
        return clientId;
    }

    /**
     * @param clientId 终端标识
     */
    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    /**
     * @return 签名key
     */
    public String getSignKey() {
        return signKey;
    }

    /**
     * @param signKey 签名key
     */
    public void setSignKey(String signKey) {
        this.signKey = signKey == null ? null : signKey.trim();
    }

    /**
     * @return 是否支持签名
     */
    public Boolean getSupportsEncrypt() {
        return supportsEncrypt;
    }

    /**
     * @param supportsEncrypt 是否支持签名
     */
    public void setSupportsEncrypt(Boolean supportsEncrypt) {
        this.supportsEncrypt = supportsEncrypt;
    }

    /**
     * @return 描述信息
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * @param describe 描述信息
     */
    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    /**
     * @return 是否支持支付
     */
    public Boolean getSupportsPayment() {
        return supportsPayment;
    }

    /**
     * @param supportsPayment 是否支持支付
     */
    public void setSupportsPayment(Boolean supportsPayment) {
        this.supportsPayment = supportsPayment;
    }

    /**
     * @return 是否支持退款
     */
    public Boolean getSupportsRefund() {
        return supportsRefund;
    }

    /**
     * @param supportsRefund 是否支持退款
     */
    public void setSupportsRefund(Boolean supportsRefund) {
        this.supportsRefund = supportsRefund;
    }

    /**
     * @return 是否支持查询
     */
    public Boolean getSupportsQuery() {
        return supportsQuery;
    }

    /**
     * @param supportsQuery 是否支持查询
     */
    public void setSupportsQuery(Boolean supportsQuery) {
        this.supportsQuery = supportsQuery;
    }

    /**
     * @return 支持的网关类型,多个以逗号分隔
     */
    public String getSupportsGatetype() {
        return supportsGatetype;
    }

    /**
     * @param supportsGatetype 支持的网关类型,多个以逗号分隔
     */
    public void setSupportsGatetype(String supportsGatetype) {
        this.supportsGatetype = supportsGatetype == null ? null : supportsGatetype.trim();
    }

    /**
     * @return 支付成功通知地址
     */
    public String getPaymentNotifyUrl() {
        return paymentNotifyUrl;
    }

    /**
     * @param paymentNotifyUrl 支付成功通知地址
     */
    public void setPaymentNotifyUrl(String paymentNotifyUrl) {
        this.paymentNotifyUrl = paymentNotifyUrl == null ? null : paymentNotifyUrl.trim();
    }

    /**
     * @return 退款成功通知地址
     */
    public String getRefundNotifyUrl() {
        return refundNotifyUrl;
    }

    /**
     * @param refundNotifyUrl 退款成功通知地址
     */
    public void setRefundNotifyUrl(String refundNotifyUrl) {
        this.refundNotifyUrl = refundNotifyUrl == null ? null : refundNotifyUrl.trim();
    }

    /**
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        PaygateClient other = (PaygateClient) that;
        return (this.getPaygateClientId() == null ? other.getPaygateClientId() == null : this.getPaygateClientId().equals(other.getPaygateClientId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getSignKey() == null ? other.getSignKey() == null : this.getSignKey().equals(other.getSignKey()))
            && (this.getSupportsEncrypt() == null ? other.getSupportsEncrypt() == null : this.getSupportsEncrypt().equals(other.getSupportsEncrypt()))
            && (this.getDescribe() == null ? other.getDescribe() == null : this.getDescribe().equals(other.getDescribe()))
            && (this.getSupportsPayment() == null ? other.getSupportsPayment() == null : this.getSupportsPayment().equals(other.getSupportsPayment()))
            && (this.getSupportsRefund() == null ? other.getSupportsRefund() == null : this.getSupportsRefund().equals(other.getSupportsRefund()))
            && (this.getSupportsQuery() == null ? other.getSupportsQuery() == null : this.getSupportsQuery().equals(other.getSupportsQuery()))
            && (this.getSupportsGatetype() == null ? other.getSupportsGatetype() == null : this.getSupportsGatetype().equals(other.getSupportsGatetype()))
            && (this.getPaymentNotifyUrl() == null ? other.getPaymentNotifyUrl() == null : this.getPaymentNotifyUrl().equals(other.getPaymentNotifyUrl()))
            && (this.getRefundNotifyUrl() == null ? other.getRefundNotifyUrl() == null : this.getRefundNotifyUrl().equals(other.getRefundNotifyUrl()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaygateClientId() == null) ? 0 : getPaygateClientId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getSignKey() == null) ? 0 : getSignKey().hashCode());
        result = prime * result + ((getSupportsEncrypt() == null) ? 0 : getSupportsEncrypt().hashCode());
        result = prime * result + ((getDescribe() == null) ? 0 : getDescribe().hashCode());
        result = prime * result + ((getSupportsPayment() == null) ? 0 : getSupportsPayment().hashCode());
        result = prime * result + ((getSupportsRefund() == null) ? 0 : getSupportsRefund().hashCode());
        result = prime * result + ((getSupportsQuery() == null) ? 0 : getSupportsQuery().hashCode());
        result = prime * result + ((getSupportsGatetype() == null) ? 0 : getSupportsGatetype().hashCode());
        result = prime * result + ((getPaymentNotifyUrl() == null) ? 0 : getPaymentNotifyUrl().hashCode());
        result = prime * result + ((getRefundNotifyUrl() == null) ? 0 : getRefundNotifyUrl().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }
}