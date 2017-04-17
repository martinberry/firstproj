package com.ztravel.paygate.wx.dao;

import java.util.List;

import com.ztravel.paygate.wx.client.entity.RefundQueryResponseEntity;

public interface IWxRefundDao {

	/**
	 * 保存微信退款记录
	 * @param refundQueryResponse
	 * @return
	 * @throws Exception
	 */
	public void insert(RefundQueryResponseEntity refundQueryResponse);

	/**
	 * 修改微信退款记录
	 * @param refundQueryResponse
	 * @return
	 * @throws Exception
	 */
	public void update(RefundQueryResponseEntity refundQueryResponse);

	/**
	 * lock
	 * @param refundQueryResponse
	 * @return
	 * @throws Exception
	 */
	public void lock(RefundQueryResponseEntity refundQueryResponse);

	/**
	 * count
	 * @param refundQueryResponse
	 * @return
	 * @throws Exception
	 */
	public Integer count(RefundQueryResponseEntity refundQueryResponse);

	/**
	 * 根据transaction_id查询退款结果记录
	 * @param transactionId
	 * @return
	 */
	public List<RefundQueryResponseEntity> selectByTransactionId(String transactionId);

    /**
     * 根据out_trade_no查询退款结果记录
     * @param out_trade_no
     * @return
     */
    public List<RefundQueryResponseEntity> selectByOutTradeNo(String outTradeNo);

    /**
     * 修改微信退款记录状态
     * @param refundQueryResponse
     * @return
     * @throws Exception
     */
    public boolean updateRecordStatus(RefundQueryResponseEntity refundQueryResponse);
    
    List<RefundQueryResponseEntity> searchUnProceedRefundRecord() ;

	RefundQueryResponseEntity selectByRefundId(String refundId);

}
