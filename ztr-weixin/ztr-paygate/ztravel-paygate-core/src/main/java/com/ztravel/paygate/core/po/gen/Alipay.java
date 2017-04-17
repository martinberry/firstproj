package com.ztravel.paygate.core.po.gen;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付宝支付记录表
 */
public class Alipay implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付ID
     */
    private String alipayId;

    /**
     * 终端标识
     */
    private String clientId;

    /**
     * 订单号
     */
    private String orderNum;

    /**
     * 订单类型
     */
    private String payType;

    /**
     * 交易金额
     */
    private Long transAmount;

    /**
     * 订单描述信息
     */
    private String transComment;

    /**
     * 支付手机号
     */
    private String mobile;

    /**
     * 前台回调URL
     */
    private String fgNotifyUrl;

    /**
     * 合作商户号
     */
    private String partner;

    /**
     * 卖方email
     */
    private String sellerEmail;

    /**
     * 卖方email
     */
    private String sellerId;

    /**
     * 交易日期(yyyyMMdd)
     */
    private String transDate;

    /**
     * 交易时间
     */
    private String transTime;

    /**
     * 签名结果
     */
    private String signRetCode;

    /**
     * 签名结果信息
     */
    private String signRetMsg;

    /**
     * 流水号
     */
    private String traceNum;

    /**
     * 买方email
     */
    private String buyerEmail;

    /**
     * 买方id
     */
    private String buyerId;

    /**
     * 交易状态
     */
    private String tradeStatus;

    /**
     * 交易创建时间
     */
    private String gmtCreate;

    /**
     * 交易付款时间
     */
    private String gmtPayment;

    /**
     * 通知时间
     */
    private String notifyTime;

    /**
     * 返回的额外信息
     */
    private String extraInfos;

    /**
     * 验签结果
     */
    private String valsignRetCode;

    /**
     * 验签结果信息
     */
    private String valsignRetMsg;

    /**
     * success-成功,fail-失败
     */
    private String payState;

    /**
     * 返回给支付宝的信息
     */
    private String ackContent;

    /**
     * 终端确认结果
     */
    private String confirmResult;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 结果返回时间
     */
    private Date respTime;

    /**
     * 完成时间
     */
    private Date completeTime;

    /**
     * @return 支付ID
     */
    public String getAlipayId() {
        return alipayId;
    }

    /**
     * @param alipayId 支付ID
     */
    public void setAlipayId(String alipayId) {
        this.alipayId = alipayId == null ? null : alipayId.trim();
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
     * @return 订单号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum 订单号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    /**
     * @return 订单类型
     */
    public String getPayType() {
        return payType;
    }

    /**
     * @param payType 订单类型
     */
    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    /**
     * @return 交易金额
     */
    public Long getTransAmount() {
        return transAmount;
    }

    /**
     * @param transAmount 交易金额
     */
    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * @return 订单描述信息
     */
    public String getTransComment() {
        return transComment;
    }

    /**
     * @param transComment 订单描述信息
     */
    public void setTransComment(String transComment) {
        this.transComment = transComment == null ? null : transComment.trim();
    }

    /**
     * @return 支付手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile 支付手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * @return 前台回调URL
     */
    public String getFgNotifyUrl() {
        return fgNotifyUrl;
    }

    /**
     * @param fgNotifyUrl 前台回调URL
     */
    public void setFgNotifyUrl(String fgNotifyUrl) {
        this.fgNotifyUrl = fgNotifyUrl == null ? null : fgNotifyUrl.trim();
    }

    /**
     * @return 合作商户号
     */
    public String getPartner() {
        return partner;
    }

    /**
     * @param partner 合作商户号
     */
    public void setPartner(String partner) {
        this.partner = partner == null ? null : partner.trim();
    }

    /**
     * @return 卖方email
     */
    public String getSellerEmail() {
        return sellerEmail;
    }

    /**
     * @param sellerEmail 卖方email
     */
    public void setSellerEmail(String sellerEmail) {
        this.sellerEmail = sellerEmail == null ? null : sellerEmail.trim();
    }

    /**
     * @return 卖方email
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId 卖方email
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * @return 交易日期(yyyyMMdd)
     */
    public String getTransDate() {
        return transDate;
    }

    /**
     * @param transDate 交易日期(yyyyMMdd)
     */
    public void setTransDate(String transDate) {
        this.transDate = transDate == null ? null : transDate.trim();
    }

    /**
     * @return 交易时间
     */
    public String getTransTime() {
        return transTime;
    }

    /**
     * @param transTime 交易时间
     */
    public void setTransTime(String transTime) {
        this.transTime = transTime == null ? null : transTime.trim();
    }

    /**
     * @return 签名结果
     */
    public String getSignRetCode() {
        return signRetCode;
    }

    /**
     * @param signRetCode 签名结果
     */
    public void setSignRetCode(String signRetCode) {
        this.signRetCode = signRetCode == null ? null : signRetCode.trim();
    }

    /**
     * @return 签名结果信息
     */
    public String getSignRetMsg() {
        return signRetMsg;
    }

    /**
     * @param signRetMsg 签名结果信息
     */
    public void setSignRetMsg(String signRetMsg) {
        this.signRetMsg = signRetMsg == null ? null : signRetMsg.trim();
    }

    /**
     * @return 流水号
     */
    public String getTraceNum() {
        return traceNum;
    }

    /**
     * @param traceNum 流水号
     */
    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum == null ? null : traceNum.trim();
    }

    /**
     * @return 买方email
     */
    public String getBuyerEmail() {
        return buyerEmail;
    }

    /**
     * @param buyerEmail 买方email
     */
    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail == null ? null : buyerEmail.trim();
    }

    /**
     * @return 买方id
     */
    public String getBuyerId() {
        return buyerId;
    }

    /**
     * @param buyerId 买方id
     */
    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId == null ? null : buyerId.trim();
    }

    /**
     * @return 交易状态
     */
    public String getTradeStatus() {
        return tradeStatus;
    }

    /**
     * @param tradeStatus 交易状态
     */
    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus == null ? null : tradeStatus.trim();
    }

    /**
     * @return 交易创建时间
     */
    public String getGmtCreate() {
        return gmtCreate;
    }

    /**
     * @param gmtCreate 交易创建时间
     */
    public void setGmtCreate(String gmtCreate) {
        this.gmtCreate = gmtCreate == null ? null : gmtCreate.trim();
    }

    /**
     * @return 交易付款时间
     */
    public String getGmtPayment() {
        return gmtPayment;
    }

    /**
     * @param gmtPayment 交易付款时间
     */
    public void setGmtPayment(String gmtPayment) {
        this.gmtPayment = gmtPayment == null ? null : gmtPayment.trim();
    }

    /**
     * @return 通知时间
     */
    public String getNotifyTime() {
        return notifyTime;
    }

    /**
     * @param notifyTime 通知时间
     */
    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime == null ? null : notifyTime.trim();
    }

    /**
     * @return 返回的额外信息
     */
    public String getExtraInfos() {
        return extraInfos;
    }

    /**
     * @param extraInfos 返回的额外信息
     */
    public void setExtraInfos(String extraInfos) {
        this.extraInfos = extraInfos == null ? null : extraInfos.trim();
    }

    /**
     * @return 验签结果
     */
    public String getValsignRetCode() {
        return valsignRetCode;
    }

    /**
     * @param valsignRetCode 验签结果
     */
    public void setValsignRetCode(String valsignRetCode) {
        this.valsignRetCode = valsignRetCode == null ? null : valsignRetCode.trim();
    }

    /**
     * @return 验签结果信息
     */
    public String getValsignRetMsg() {
        return valsignRetMsg;
    }

    /**
     * @param valsignRetMsg 验签结果信息
     */
    public void setValsignRetMsg(String valsignRetMsg) {
        this.valsignRetMsg = valsignRetMsg == null ? null : valsignRetMsg.trim();
    }

    /**
     * @return success-成功,fail-失败
     */
    public String getPayState() {
        return payState;
    }

    /**
     * @param payState success-成功,fail-失败
     */
    public void setPayState(String payState) {
        this.payState = payState == null ? null : payState.trim();
    }

    /**
     * @return 返回给支付宝的信息
     */
    public String getAckContent() {
        return ackContent;
    }

    /**
     * @param ackContent 返回给支付宝的信息
     */
    public void setAckContent(String ackContent) {
        this.ackContent = ackContent == null ? null : ackContent.trim();
    }

    /**
     * @return 终端确认结果
     */
    public String getConfirmResult() {
        return confirmResult;
    }

    /**
     * @param confirmResult 终端确认结果
     */
    public void setConfirmResult(String confirmResult) {
        this.confirmResult = confirmResult == null ? null : confirmResult.trim();
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
     * @return 结果返回时间
     */
    public Date getRespTime() {
        return respTime;
    }

    /**
     * @param respTime 结果返回时间
     */
    public void setRespTime(Date respTime) {
        this.respTime = respTime;
    }

    /**
     * @return 完成时间
     */
    public Date getCompleteTime() {
        return completeTime;
    }

    /**
     * @param completeTime 完成时间
     */
    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
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
        Alipay other = (Alipay) that;
        return (this.getAlipayId() == null ? other.getAlipayId() == null : this.getAlipayId().equals(other.getAlipayId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getPayType() == null ? other.getPayType() == null : this.getPayType().equals(other.getPayType()))
            && (this.getTransAmount() == null ? other.getTransAmount() == null : this.getTransAmount().equals(other.getTransAmount()))
            && (this.getTransComment() == null ? other.getTransComment() == null : this.getTransComment().equals(other.getTransComment()))
            && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
            && (this.getFgNotifyUrl() == null ? other.getFgNotifyUrl() == null : this.getFgNotifyUrl().equals(other.getFgNotifyUrl()))
            && (this.getPartner() == null ? other.getPartner() == null : this.getPartner().equals(other.getPartner()))
            && (this.getSellerEmail() == null ? other.getSellerEmail() == null : this.getSellerEmail().equals(other.getSellerEmail()))
            && (this.getSellerId() == null ? other.getSellerId() == null : this.getSellerId().equals(other.getSellerId()))
            && (this.getTransDate() == null ? other.getTransDate() == null : this.getTransDate().equals(other.getTransDate()))
            && (this.getTransTime() == null ? other.getTransTime() == null : this.getTransTime().equals(other.getTransTime()))
            && (this.getSignRetCode() == null ? other.getSignRetCode() == null : this.getSignRetCode().equals(other.getSignRetCode()))
            && (this.getSignRetMsg() == null ? other.getSignRetMsg() == null : this.getSignRetMsg().equals(other.getSignRetMsg()))
            && (this.getTraceNum() == null ? other.getTraceNum() == null : this.getTraceNum().equals(other.getTraceNum()))
            && (this.getBuyerEmail() == null ? other.getBuyerEmail() == null : this.getBuyerEmail().equals(other.getBuyerEmail()))
            && (this.getBuyerId() == null ? other.getBuyerId() == null : this.getBuyerId().equals(other.getBuyerId()))
            && (this.getTradeStatus() == null ? other.getTradeStatus() == null : this.getTradeStatus().equals(other.getTradeStatus()))
            && (this.getGmtCreate() == null ? other.getGmtCreate() == null : this.getGmtCreate().equals(other.getGmtCreate()))
            && (this.getGmtPayment() == null ? other.getGmtPayment() == null : this.getGmtPayment().equals(other.getGmtPayment()))
            && (this.getNotifyTime() == null ? other.getNotifyTime() == null : this.getNotifyTime().equals(other.getNotifyTime()))
            && (this.getExtraInfos() == null ? other.getExtraInfos() == null : this.getExtraInfos().equals(other.getExtraInfos()))
            && (this.getValsignRetCode() == null ? other.getValsignRetCode() == null : this.getValsignRetCode().equals(other.getValsignRetCode()))
            && (this.getValsignRetMsg() == null ? other.getValsignRetMsg() == null : this.getValsignRetMsg().equals(other.getValsignRetMsg()))
            && (this.getPayState() == null ? other.getPayState() == null : this.getPayState().equals(other.getPayState()))
            && (this.getAckContent() == null ? other.getAckContent() == null : this.getAckContent().equals(other.getAckContent()))
            && (this.getConfirmResult() == null ? other.getConfirmResult() == null : this.getConfirmResult().equals(other.getConfirmResult()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRespTime() == null ? other.getRespTime() == null : this.getRespTime().equals(other.getRespTime()))
            && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAlipayId() == null) ? 0 : getAlipayId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getPayType() == null) ? 0 : getPayType().hashCode());
        result = prime * result + ((getTransAmount() == null) ? 0 : getTransAmount().hashCode());
        result = prime * result + ((getTransComment() == null) ? 0 : getTransComment().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getFgNotifyUrl() == null) ? 0 : getFgNotifyUrl().hashCode());
        result = prime * result + ((getPartner() == null) ? 0 : getPartner().hashCode());
        result = prime * result + ((getSellerEmail() == null) ? 0 : getSellerEmail().hashCode());
        result = prime * result + ((getSellerId() == null) ? 0 : getSellerId().hashCode());
        result = prime * result + ((getTransDate() == null) ? 0 : getTransDate().hashCode());
        result = prime * result + ((getTransTime() == null) ? 0 : getTransTime().hashCode());
        result = prime * result + ((getSignRetCode() == null) ? 0 : getSignRetCode().hashCode());
        result = prime * result + ((getSignRetMsg() == null) ? 0 : getSignRetMsg().hashCode());
        result = prime * result + ((getTraceNum() == null) ? 0 : getTraceNum().hashCode());
        result = prime * result + ((getBuyerEmail() == null) ? 0 : getBuyerEmail().hashCode());
        result = prime * result + ((getBuyerId() == null) ? 0 : getBuyerId().hashCode());
        result = prime * result + ((getTradeStatus() == null) ? 0 : getTradeStatus().hashCode());
        result = prime * result + ((getGmtCreate() == null) ? 0 : getGmtCreate().hashCode());
        result = prime * result + ((getGmtPayment() == null) ? 0 : getGmtPayment().hashCode());
        result = prime * result + ((getNotifyTime() == null) ? 0 : getNotifyTime().hashCode());
        result = prime * result + ((getExtraInfos() == null) ? 0 : getExtraInfos().hashCode());
        result = prime * result + ((getValsignRetCode() == null) ? 0 : getValsignRetCode().hashCode());
        result = prime * result + ((getValsignRetMsg() == null) ? 0 : getValsignRetMsg().hashCode());
        result = prime * result + ((getPayState() == null) ? 0 : getPayState().hashCode());
        result = prime * result + ((getAckContent() == null) ? 0 : getAckContent().hashCode());
        result = prime * result + ((getConfirmResult() == null) ? 0 : getConfirmResult().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRespTime() == null) ? 0 : getRespTime().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        return result;
    }

	@Override
	public String toString() {
		return "Alipay [alipayId=" + alipayId + ", orderNum=" + orderNum
				+ ", payType=" + payType + ", signRetCode=" + signRetCode
				+ ", signRetMsg=" + signRetMsg + ", valsignRetCode="
				+ valsignRetCode + ", valsignRetMsg=" + valsignRetMsg
				+ ", payState=" + payState + ", ackContent=" + ackContent
				+ ", confirmResult=" + confirmResult + "]";
	}
    
    
}