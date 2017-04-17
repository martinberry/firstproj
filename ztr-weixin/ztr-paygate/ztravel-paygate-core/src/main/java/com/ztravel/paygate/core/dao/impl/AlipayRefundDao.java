package com.ztravel.paygate.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.paygate.core.dao.IAlipayRefundDao;
import com.ztravel.paygate.core.dao.gen.AlipayRefundDAOImpl;

/**
 * 支付宝退款数据访问
 * 
 * @author dingguangxian
 * 
 */
@Repository("paygate_alipay_refund_dao")
public class AlipayRefundDao extends AlipayRefundDAOImpl implements IAlipayRefundDao {

}
