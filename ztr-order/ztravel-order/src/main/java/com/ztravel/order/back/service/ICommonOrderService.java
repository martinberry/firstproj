package com.ztravel.order.back.service;

import java.util.List;

import com.ztravel.order.back.vo.CommonOrderListVo;
import com.ztravel.order.po.CommonOrder;
import com.ztravel.order.po.CommonOrderSearchCriteria;


public interface ICommonOrderService {
	public List<CommonOrderListVo> searchCO(CommonOrderSearchCriteria criteria) ;
	public Integer countCOs(CommonOrderSearchCriteria criteria) throws Exception;
	public CommonOrder search(String orderNoOrigin)throws Exception;
	public void updateAndinsertlog(CommonOrder commonordertmp)throws Exception;
	public CommonOrder searchByOrderNoVice(String orderNoVice) throws Exception;
	public String convertstateChangeHistory(String statechangepo);

}
