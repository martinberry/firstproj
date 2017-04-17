package com.ztravel.order.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.order.po.Order;

public interface IOrderDao extends BaseDao<Order>{

	/**
	 * 根据会员mid查询对应的所有订单
	 * @param mid
	 * @return
	 * @throws SQLException
	 */
	public List<Order> selectByCreator(String mid) throws SQLException;

	public List<Order> selectByOrderCode(String orderCode) throws SQLException;

	/**
	 * 根据订单ID更新客户端订单状态
	 * @param paramMap key:订单ID value:订单更新后的状态
	 * */
	Boolean updateStatus(Map<String,String> paramMap) throws SQLException;
	/**
	 * 更新整个订单
	 * @param orderId : 订单ID
	 * */
	Boolean updateOrder(Order order) throws SQLException;

	int countOrder(Map<String,String> params)throws SQLException;

	public List<Order> selectByPage(Map<String,Object> params) throws SQLException;

	Boolean refundUpdate(Map<String,Object> paramsMap) throws SQLException;

	public Order selectBycouponItem(String couponitem)throws SQLException;

	Boolean tempOrderUpdate(Map<String,Object> params) throws SQLException;
	
	/**
	 * 将订单更行为出行中状态
	 * */
	int updateToTravelling(String bookDate) throws SQLException;
	/**
	 * 将订单更新为已完成状态
	 * */
	int updateToCompleted(String backDate) throws SQLException;

	int countCompleted(String memberId)throws SQLException;
	
	List<Order> selectAutoTravelling(Map params) ;
	
	List<Order> selectAutoCompleted(Map params) ;
	
	List<Order> selectAutoNotice(Map params) ;

}
