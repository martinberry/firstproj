package com.ztravel.paygate.core.po.gen;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付宝退款记录表
 */
public class AlipayRefund implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 退款ID
     */
    private String alipayRefundId;

    /**
     * 原支付ID
     */
    private String alipayId;

    /**
     * 终端标识
     */
    private String clientId;

    /**
     * 退款标识号
     */
    private String refundNum;

    /**
     * 原订单号
     */
    private String orderNum;

    /**
     * 原流水号
     */
    private String traceNum;

    /**
     * 原交易金额
     */
    private Long transAmount;

    /**
     * 本次退款金额
     */
    private Long refundAmount;

    /**
     * 退款备注
     */
    private String refundComment;

    /**
     * 合作商户号
     */
    private String partner;

    /**
     * 卖方email
     */
    private String sellerEmail;

    /**
     * 卖方id
     */
    private String sellerId;

    /**
     * 退款日期
     */
    private String refundDate;

    /**
     * 退款详细信息
     */
    private String detailData;

    /**
     * 请求返回码
     */
    private String reqRetCode;

    /**
     * 请求返回信息
     */
    private String reqRetMsg;

    /**
     * 成功处理交易笔数
     */
    private Short successNum;

    /**
     * 结果详细信息
     */
    private String resultDetails;

    /**
     * 交易返回信息
     */
    private String transRetMsg;

    /**
     * 退款返回信息
     */
    private String refundRetMsg;

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
    private String refundState;

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
     * 分润请求信息
     */
    private String refundProfitDetails;

    /**
     * 解冻返回结果
     */
    private String unfreezeDetails;

    /**
     * @return 退款ID
     */
    public String getAlipayRefundId() {
        return alipayRefundId;
    }

    /**
     * @param alipayRefundId 退款ID
     */
    public void setAlipayRefundId(String alipayRefundId) {
        this.alipayRefundId = alipayRefundId == null ? null : alipayRefundId.trim();
    }

    /**
     * @return 原支付ID
     */
    public String getAlipayId() {
        return alipayId;
    }

    /**
     * @param alipayId 原支付ID
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
     * @return 退款标识号
     */
    public String getRefundNum() {
        return refundNum;
    }

    /**
     * @param refundNum 退款标识号
     */
    public void setRefundNum(String refundNum) {
        this.refundNum = refundNum == null ? null : refundNum.trim();
    }

    /**
     * @return 原订单号
     */
    public String getOrderNum() {
        return orderNum;
    }

    /**
     * @param orderNum 原订单号
     */
    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    /**
     * @return 原流水号
     */
    public String getTraceNum() {
        return traceNum;
    }

    /**
     * @param traceNum 原流水号
     */
    public void setTraceNum(String traceNum) {
        this.traceNum = traceNum == null ? null : traceNum.trim();
    }

    /**
     * @return 原交易金额
     */
    public Long getTransAmount() {
        return transAmount;
    }

    /**
     * @param transAmount 原交易金额
     */
    public void setTransAmount(Long transAmount) {
        this.transAmount = transAmount;
    }

    /**
     * @return 本次退款金额
     */
    public Long getRefundAmount() {
        return refundAmount;
    }

    /**
     * @param refundAmount 本次退款金额
     */
    public void setRefundAmount(Long refundAmount) {
        this.refundAmount = refundAmount;
    }

    /**
     * @return 退款备注
     */
    public String getRefundComment() {
        return refundComment;
    }

    /**
     * @param refundComment 退款备注
     */
    public void setRefundComment(String refundComment) {
        this.refundComment = refundComment == null ? null : refundComment.trim();
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
     * @return 卖方id
     */
    public String getSellerId() {
        return sellerId;
    }

    /**
     * @param sellerId 卖方id
     */
    public void setSellerId(String sellerId) {
        this.sellerId = sellerId == null ? null : sellerId.trim();
    }

    /**
     * @return 退款日期
     */
    public String getRefundDate() {
        return refundDate;
    }

    /**
     * @param refundDate 退款日期
     */
    public void setRefundDate(String refundDate) {
        this.refundDate = refundDate == null ? null : refundDate.trim();
    }

    /**
     * @return 退款详细信息
     */
    public String getDetailData() {
        return detailData;
    }

    /**
     * @param detailData 退款详细信息
     */
    public void setDetailData(String detailData) {
        this.detailData = detailData == null ? null : detailData.trim();
    }

    /**
     * @return 请求返回码
     */
    public String getReqRetCode() {
        return reqRetCode;
    }

    /**
     * @param reqRetCode 请求返回码
     */
    public void setReqRetCode(String reqRetCode) {
        this.reqRetCode = reqRetCode == null ? null : reqRetCode.trim();
    }

    /**
     * @return 请求返回信息
     */
    public String getReqRetMsg() {
        return reqRetMsg;
    }

    /**
     * @param reqRetMsg 请求返回信息
     */
    public void setReqRetMsg(String reqRetMsg) {
        this.reqRetMsg = reqRetMsg == null ? null : reqRetMsg.trim();
    }

    /**
     * @return 成功处理交易笔数
     */
    public Short getSuccessNum() {
        return successNum;
    }

    /**
     * @param successNum 成功处理交易笔数
     */
    public void setSuccessNum(Short successNum) {
        this.successNum = successNum;
    }

    /**
     * @return 结果详细信息
     */
    public String getResultDetails() {
        return resultDetails;
    }

    /**
     * @param resultDetails 结果详细信息
     */
    public void setResultDetails(String resultDetails) {
        this.resultDetails = resultDetails == null ? null : resultDetails.trim();
    }

    /**
     * @return 交易返回信息
     */
    public String getTransRetMsg() {
        return transRetMsg;
    }

    /**
     * @param transRetMsg 交易返回信息
     */
    public void setTransRetMsg(String transRetMsg) {
        this.transRetMsg = transRetMsg == null ? null : transRetMsg.trim();
    }

    /**
     * @return 退款返回信息
     */
    public String getRefundRetMsg() {
        return refundRetMsg;
    }

    /**
     * @param refundRetMsg 退款返回信息
     */
    public void setRefundRetMsg(String refundRetMsg) {
        this.refundRetMsg = refundRetMsg == null ? null : refundRetMsg.trim();
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
    public String getRefundState() {
        return refundState;
    }

    /**
     * @param refundState success-成功,fail-失败
     */
    public void setRefundState(String refundState) {
        this.refundState = refundState == null ? null : refundState.trim();
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

    /**
     * @return 分润请求信息
     */
    public String getRefundProfitDetails() {
        return refundProfitDetails;
    }

    /**
     * @param refundProfitDetails 分润请求信息
     */
    public void setRefundProfitDetails(String refundProfitDetails) {
        this.refundProfitDetails = refundProfitDetails == null ? null : refundProfitDetails.trim();
    }

    /**
     * @return 解冻返回结果
     */
    public String getUnfreezeDetails() {
        return unfreezeDetails;
    }

    /**
     * @param unfreezeDetails 解冻返回结果
     */
    public void setUnfreezeDetails(String unfreezeDetails) {
        this.unfreezeDetails = unfreezeDetails == null ? null : unfreezeDetails.trim();
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
        AlipayRefund other = (AlipayRefund) that;
        return (this.getAlipayRefundId() == null ? other.getAlipayRefundId() == null : this.getAlipayRefundId().equals(other.getAlipayRefundId()))
            && (this.getAlipayId() == null ? other.getAlipayId() == null : this.getAlipayId().equals(other.getAlipayId()))
            && (this.getClientId() == null ? other.getClientId() == null : this.getClientId().equals(other.getClientId()))
            && (this.getRefundNum() == null ? other.getRefundNum() == null : this.getRefundNum().equals(other.getRefundNum()))
            && (this.getOrderNum() == null ? other.getOrderNum() == null : this.getOrderNum().equals(other.getOrderNum()))
            && (this.getTraceNum() == null ? other.getTraceNum() == null : this.getTraceNum().equals(other.getTraceNum()))
            && (this.getTransAmount() == null ? other.getTransAmount() == null : this.getTransAmount().equals(other.getTransAmount()))
            && (this.getRefundAmount() == null ? other.getRefundAmount() == null : this.getRefundAmount().equals(other.getRefundAmount()))
            && (this.getRefundComment() == null ? other.getRefundComment() == null : this.getRefundComment().equals(other.getRefundComment()))
            && (this.getPartner() == null ? other.getPartner() == null : this.getPartner().equals(other.getPartner()))
            && (this.getSellerEmail() == null ? other.getSellerEmail() == null : this.getSellerEmail().equals(other.getSellerEmail()))
            && (this.getSellerId() == null ? other.getSellerId() == null : this.getSellerId().equals(other.getSellerId()))
            && (this.getRefundDate() == null ? other.getRefundDate() == null : this.getRefundDate().equals(other.getRefundDate()))
            && (this.getDetailData() == null ? other.getDetailData() == null : this.getDetailData().equals(other.getDetailData()))
            && (this.getReqRetCode() == null ? other.getReqRetCode() == null : this.getReqRetCode().equals(other.getReqRetCode()))
            && (this.getReqRetMsg() == null ? other.getReqRetMsg() == null : this.getReqRetMsg().equals(other.getReqRetMsg()))
            && (this.getSuccessNum() == null ? other.getSuccessNum() == null : this.getSuccessNum().equals(other.getSuccessNum()))
            && (this.getResultDetails() == null ? other.getResultDetails() == null : this.getResultDetails().equals(other.getResultDetails()))
            && (this.getTransRetMsg() == null ? other.getTransRetMsg() == null : this.getTransRetMsg().equals(other.getTransRetMsg()))
            && (this.getRefundRetMsg() == null ? other.getRefundRetMsg() == null : this.getRefundRetMsg().equals(other.getRefundRetMsg()))
            && (this.getValsignRetCode() == null ? other.getValsignRetCode() == null : this.getValsignRetCode().equals(other.getValsignRetCode()))
            && (this.getValsignRetMsg() == null ? other.getValsignRetMsg() == null : this.getValsignRetMsg().equals(other.getValsignRetMsg()))
            && (this.getRefundState() == null ? other.getRefundState() == null : this.getRefundState().equals(other.getRefundState()))
            && (this.getAckContent() == null ? other.getAckContent() == null : this.getAckContent().equals(other.getAckContent()))
            && (this.getConfirmResult() == null ? other.getConfirmResult() == null : this.getConfirmResult().equals(other.getConfirmResult()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRespTime() == null ? other.getRespTime() == null : this.getRespTime().equals(other.getRespTime()))
            && (this.getCompleteTime() == null ? other.getCompleteTime() == null : this.getCompleteTime().equals(other.getCompleteTime()))
            && (this.getRefundProfitDetails() == null ? other.getRefundProfitDetails() == null : this.getRefundProfitDetails().equals(other.getRefundProfitDetails()))
            && (this.getUnfreezeDetails() == null ? other.getUnfreezeDetails() == null : this.getUnfreezeDetails().equals(other.getUnfreezeDetails()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getAlipayRefundId() == null) ? 0 : getAlipayRefundId().hashCode());
        result = prime * result + ((getAlipayId() == null) ? 0 : getAlipayId().hashCode());
        result = prime * result + ((getClientId() == null) ? 0 : getClientId().hashCode());
        result = prime * result + ((getRefundNum() == null) ? 0 : getRefundNum().hashCode());
        result = prime * result + ((getOrderNum() == null) ? 0 : getOrderNum().hashCode());
        result = prime * result + ((getTraceNum() == null) ? 0 : getTraceNum().hashCode());
        result = prime * result + ((getTransAmount() == null) ? 0 : getTransAmount().hashCode());
        result = prime * result + ((getRefundAmount() == null) ? 0 : getRefundAmount().hashCode());
        result = prime * result + ((getRefundComment() == null) ? 0 : getRefundComment().hashCode());
        result = prime * result + ((getPartner() == null) ? 0 : getPartner().hashCode());
        result = prime * result + ((getSellerEmail() == null) ? 0 : getSellerEmail().hashCode());
        result = prime * result + ((getSellerId() == null) ? 0 : getSellerId().hashCode());
        result = prime * result + ((getRefundDate() == null) ? 0 : getRefundDate().hashCode());
        result = prime * result + ((getDetailData() == null) ? 0 : getDetailData().hashCode());
        result = prime * result + ((getReqRetCode() == null) ? 0 : getReqRetCode().hashCode());
        result = prime * result + ((getReqRetMsg() == null) ? 0 : getReqRetMsg().hashCode());
        result = prime * result + ((getSuccessNum() == null) ? 0 : getSuccessNum().hashCode());
        result = prime * result + ((getResultDetails() == null) ? 0 : getResultDetails().hashCode());
        result = prime * result + ((getTransRetMsg() == null) ? 0 : getTransRetMsg().hashCode());
        result = prime * result + ((getRefundRetMsg() == null) ? 0 : getRefundRetMsg().hashCode());
        result = prime * result + ((getValsignRetCode() == null) ? 0 : getValsignRetCode().hashCode());
        result = prime * result + ((getValsignRetMsg() == null) ? 0 : getValsignRetMsg().hashCode());
        result = prime * result + ((getRefundState() == null) ? 0 : getRefundState().hashCode());
        result = prime * result + ((getAckContent() == null) ? 0 : getAckContent().hashCode());
        result = prime * result + ((getConfirmResult() == null) ? 0 : getConfirmResult().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRespTime() == null) ? 0 : getRespTime().hashCode());
        result = prime * result + ((getCompleteTime() == null) ? 0 : getCompleteTime().hashCode());
        result = prime * result + ((getRefundProfitDetails() == null) ? 0 : getRefundProfitDetails().hashCode());
        result = prime * result + ((getUnfreezeDetails() == null) ? 0 : getUnfreezeDetails().hashCode());
        return result;
    }
}