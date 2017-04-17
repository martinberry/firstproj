package com.ztravel.order.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderPO;

/**
 * t_order与t_order_product连接表dao
 * @author MH
 */
public interface IOrderOrderProductDao extends BaseDao<OrderPO> {
	public  List<OrderPO>  selectByVisaOrderId(Map map) ;
	public  List<OrderPO>  selectByUnvisaOrderId(Map map);
	public Integer countVisa(Map params);
	public Integer countUnvisa(Map params) ;

}
