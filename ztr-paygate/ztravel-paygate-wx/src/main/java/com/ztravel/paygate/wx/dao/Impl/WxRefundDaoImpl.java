package com.ztravel.paygate.wx.dao.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.paygate.wx.client.entity.RefundQueryResponseEntity;
import com.ztravel.paygate.wx.dao.IWxRefundDao;

@Repository
public class WxRefundDaoImpl extends BaseDaoImpl<RefundQueryResponseEntity> implements IWxRefundDao {

    private static final String UPDATE_RECORD_STATUS = ".updateRecordStatus";
    
    private static final String SEARCH_UNPROCEED_REFUND_RECORDS = ".searchUnProceedRefundRecord";
    
	@Override
	public void lock(RefundQueryResponseEntity refundQueryResponse) {
		session.selectOne(nameSpace + ".lock", refundQueryResponse);

	}

	@Override
	public Integer count(RefundQueryResponseEntity refundQueryResponse) {
		return session.selectOne(nameSpace + ".count", refundQueryResponse);
	}

    @Override
    public List<RefundQueryResponseEntity> selectByTransactionId(String transactionId) {
        Map<String,String> params = new HashMap<String, String>();
        params.put("transaction_id", transactionId);
        return session.selectList(nameSpace + ".selectByTransactionId", params);
    }

    @Override
    public List<RefundQueryResponseEntity> selectByOutTradeNo(String outTradeNo) {
        Map<String,String> params = new HashMap<String, String>();
        params.put("out_trade_no", outTradeNo);
        return session.selectList(nameSpace + ".selectByOutTradeNo", params);
    }
    
    @Override
    public RefundQueryResponseEntity selectByRefundId(String refundId) {
        return session.selectOne(nameSpace + ".selectByRefundId", refundId);
    }

    @Override
    public boolean updateRecordStatus(RefundQueryResponseEntity refundQueryResponse) {
        return session.update(nameSpace + UPDATE_RECORD_STATUS, refundQueryResponse) == 1;
    }
    
    @Override
    public List<RefundQueryResponseEntity> searchUnProceedRefundRecord() {
        return session.selectList(nameSpace + SEARCH_UNPROCEED_REFUND_RECORDS);
    }

}
