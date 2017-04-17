package com.ztravel.paygate.wx.service;

import java.util.List;

import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.paygate.wx.client.entity.RefundQueryResponseEntity;

public interface IWxRefundService {

    /**
     * 微信查询退款接口
     * @param requestParms
     * @return
     */
    public RefundQueryResponseEntity queryRefund(String orderId) throws Exception;

    /**
     * 查询退款结果处理
     * @param refundQueryResponse
     * @return
     */
    public CommonResponse proceedForRefund(String orderId) throws Exception;

    /**
     * 查询退款结果预处理
     * @param refundQueryResponse
     * @return
     */
    public RefundQueryResponseEntity preproceedForRefund(String orderId);
    
    List<RefundQueryResponseEntity> searchUnProceedRefundRecord() ;

}
