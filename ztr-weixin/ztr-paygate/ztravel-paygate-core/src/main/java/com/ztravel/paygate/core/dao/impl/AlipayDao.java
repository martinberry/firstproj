package com.ztravel.paygate.core.dao.impl;

import org.springframework.stereotype.Repository;

import com.ztravel.paygate.core.dao.IAlipayDao;
import com.ztravel.paygate.core.dao.gen.AlipayDAOImpl;

/**
 * 支付宝数据访问
 * 
 * @author dingguangxian
 * 
 */
@Repository("paygate_alipay_dao")
public class AlipayDao extends AlipayDAOImpl implements IAlipayDao {

}
