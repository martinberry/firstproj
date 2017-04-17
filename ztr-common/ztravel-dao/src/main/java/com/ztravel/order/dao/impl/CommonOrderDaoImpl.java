package com.ztravel.order.dao.impl;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.order.dao.ICommonOrderDao;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.CommonOrderPo;

@Repository
public class CommonOrderDaoImpl extends BaseDaoImpl<CommonOrder> implements ICommonOrderDao{

	
	private static final String SELECT_BY_ORIGIN_ORDERNO = ".selectByOriginOrderNo" ;
	private static final String SELECT_BY_ORDERVICE=".selectByOrderNoVice";
	private static final String UPDATE_BY_MAP = ".updateByMap" ;
	private static final String SELECT_FOR_UPDATE_BY_ORIGINORDERNO = ".select4UpdateByOriginOrderNo" ;
	
	private static final String SELECTREPAIR_BY_ORIGINORDERID = ".selectrRepairByOriginOrderId" ;
	private static final String SELECT_COMBINE_ORDER=".selectcombineorder";
	
	
	@Override
	public List<CommonOrderPo> selectCombineOrder(Map params){
		return session.selectList(nameSpace + SELECT_COMBINE_ORDER, params);
	}
	
	@Override
	public int updateByMap(Map<String, Object> parameter) throws Exception {
		int count = session.update(nameSpace + UPDATE_BY_MAP, parameter) ;
		if(count > 1){
			throw new Exception("updateByMap:update more then one record by params:::" + parameter) ;
		}
		return count ;
	}
	
	@Override
	public CommonOrder selectByorderVice(String orderNoVice) {
		return session.selectOne(nameSpace + SELECT_BY_ORDERVICE, orderNoVice);
	}
	
	@Override
    public CommonOrder selectByOriginOrderNo(String orderNo) {
		List<CommonOrder> commonOrders = session.selectList(nameSpace + SELECT_BY_ORIGIN_ORDERNO, orderNo) ;
		if(!CollectionUtils.isEmpty(commonOrders)){
			return commonOrders.get(0) ;
		}
		return null ;
    }
	
	@Override
    public CommonOrder select4UpdateByOriginOrderNo(String originOrderNo) {
		List<CommonOrder> commonOrders = session.selectList(nameSpace + SELECT_FOR_UPDATE_BY_ORIGINORDERNO, originOrderNo) ;
		if(!CollectionUtils.isEmpty(commonOrders)){
			return commonOrders.get(0) ;
		}
		return null ;
    }

	@Override
	public CommonOrder selectRepairByOriginOrderId(String orderIdOrigin) throws Exception {
		return session.selectOne(nameSpace + SELECTREPAIR_BY_ORIGINORDERID, orderIdOrigin);
	}
	
}




