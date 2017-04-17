package com.ztravel.order.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.OrderComContactor;

public interface IOrderComContactorDao extends BaseDao<OrderComContactor>{
	/**
	 * 根据订单id查询对应的订单联系人信息
	 * @param orderId
	 * @return
	 */
	OrderComContactor  selectByMemberId(String memberId);

	Boolean isContactorExist(String memberId) throws Exception;
	
	List<OrderComContactor> selectContactorsByMemberId(String memberId);
	
	OrderComContactor selectByMemberIdAndName(String memberId,String contactorName);
	public List<OrderComContactor> selectByMemberIds(String memberId) ;
}
