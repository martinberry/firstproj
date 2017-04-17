package com.ztravel.order.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderOrderProductDao;
import com.ztravel.order.po.OrderPO;

/**
 * t_order与t_order_product连接表dao
 * @author MH
 */
@Repository
public class OrderOrderProductDaoImpl extends BaseDaoImpl<OrderPO> implements IOrderOrderProductDao {
	private static final String SELECT_BY_VISA_ORDER_ID =".selectByVisaorderId";
    private static final String SELECT_BY_UNVISA_ORDRE_ID=".selectByUnvisaorderId";
    private static final String COUNT_VISA_ORDER=".countVisaOrder";
    private static final String COUNT_UNVISA_ORDER=".countUnvisaOrder";
	@Override
	public  List<OrderPO>  selectByVisaOrderId(Map map) {
		return session.selectList(nameSpace + SELECT_BY_VISA_ORDER_ID, map) ;
	}
	@Override
	public  List<OrderPO>  selectByUnvisaOrderId(Map map) {
		return session.selectList(nameSpace + SELECT_BY_UNVISA_ORDRE_ID, map) ;
	}
	@Override
    public Integer countVisa(Map params) {
	    return session.selectOne(nameSpace + COUNT_VISA_ORDER, params);
	 }
	@Override
    public Integer countUnvisa(Map params) {
	    return session.selectOne(nameSpace + COUNT_UNVISA_ORDER, params);
	 }

}
