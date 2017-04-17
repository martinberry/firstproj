package com.ztravel.order.dao.impl;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.IOrderDao;
import com.ztravel.order.po.Order;


@Repository
public class OrderDaoImpl extends BaseDaoImpl<Order> implements IOrderDao{

	private static final String UPDATE_STATUS = ".updateStatus";
	private static final String ORDER_COUNT = ".orderCount";
	public static final String SELECT_BY_PAGE = ".selectByPage";
	public static final String REFUND_UPDATE = ".refundUpdate";
	public static final String SELECT_BY_COUPONITEM=".selectBycouponitem";
	public static final String TEMP_ORDER_UPDATE=".tempOrderUpdate";
	private static final String UPDATE_TO_TRAVELLING = ".updateToTravelling";
	private static final String UPDATE_TO_COMPLETED = ".updateToCompleted";
	private static final String COUNT_COMPLETED = ".countCompleted";
	private static final String SELECT_AUTO_TRAVELLING = ".selectAutoTravelling";
	private static final String SELECT_AUTO_COMPLETED = ".selectAutoCompleted";
	private static final String SELECT_AUTO_NOTICE = ".selectAutoNotice";


	@Override
	public Order selectBycouponItem(String couponitem)throws SQLException{
		Map<String,String> params = new HashMap<String, String>();
		params.put("discountCouponId", couponitem);
		return session.selectOne(nameSpace + SELECT_BY_COUPONITEM, params);
	}

	@Override
	public List<Order> selectByCreator(String mid) throws SQLException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("creator", mid);
		return session.selectList(nameSpace + ".selectByCreator", params);
	}

	@Override
	public Boolean updateStatus(Map<String,String> paramMap) throws SQLException{
		return session.update(nameSpace + UPDATE_STATUS, paramMap) == 1;
	}

	@Override
	public Boolean updateOrder(Order order) throws SQLException {
		order.setUpdateDate(new Date());
		return session.update(nameSpace + UPDATE , order) == 1;
	}

	@Override
	public Boolean refundUpdate(Map<String,Object> paramsMap) throws SQLException {
		return session.update(nameSpace + REFUND_UPDATE , paramsMap) == 1;
	}

	@Override
	public List<Order> selectByOrderCode(String orderCode) throws SQLException {
		Map<String,String> params = new HashMap<String, String>();
		params.put("orderNo", orderCode);
		return session.selectList(nameSpace + ".selectByNo", params);
	}

	@Override
	public int countOrder(Map<String,String> params) throws SQLException {
		return session.selectOne(nameSpace + ORDER_COUNT, params);
	}

	@Override
	public List<Order> selectByPage(Map<String,Object> params)
			throws SQLException {
		return session.selectList(nameSpace + SELECT_BY_PAGE, params);
	}

    @Override
    public Boolean tempOrderUpdate(Map<String, Object> params)
            throws SQLException {
        return session.update(nameSpace + TEMP_ORDER_UPDATE , params) == 1;
    }
    

	@Override
	public int updateToTravelling(String bookDate) throws SQLException {
		return session.update(nameSpace + UPDATE_TO_TRAVELLING, bookDate);
	}

	@Override
	public int updateToCompleted(String backDate) throws SQLException {
		return session.update(nameSpace + UPDATE_TO_COMPLETED, backDate);
	}

	public int countCompleted(String creator)throws SQLException{
		return session.selectOne(nameSpace + COUNT_COMPLETED, creator);
	}
	
	@Override
	public List<Order> selectAutoTravelling(Map params){
		return session.selectList(nameSpace + SELECT_AUTO_TRAVELLING, params) ;
	}
	
	@Override
	public List<Order> selectAutoCompleted(Map params){
		return session.selectList(nameSpace + SELECT_AUTO_COMPLETED, params) ;
	}
	
	@Override
	public List<Order> selectAutoNotice(Map params){
		return session.selectList(nameSpace + SELECT_AUTO_NOTICE, params) ;
	}

}
