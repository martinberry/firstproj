package com.ztravel.reuse.order.service.impl;

import java.sql.SQLException;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ztravel.common.util.SSOUtil;
import com.ztravel.order.dao.IOrderComContactorDao;
import com.ztravel.order.dao.IOrderContactorDao;
import com.ztravel.order.dao.IOrderMaterialDao;
import com.ztravel.order.po.MaterialContactor;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.order.po.OrderContactor;
import com.ztravel.order.po.OrderContactorPoMaterial;
import com.ztravel.reuse.order.service.IOrderContactorReuseService;

@Service
public class OrderContactorReuseService implements IOrderContactorReuseService {

	@Resource
	IOrderContactorDao orderContactorDaoImpl;

	@Resource
	IOrderComContactorDao orderComContactorDaoImpl;
	
	@Resource
	IOrderMaterialDao materialContactorDao;
	

	@Override
	public OrderContactor getOrderContactor(String orderId) throws SQLException{
      OrderContactor orderContactor= orderContactorDaoImpl.selectContactorByOrderId(orderId);
       return orderContactor;
	}
	
	@Override
	public OrderContactorPoMaterial getOrderContactorsById(String orderId) throws SQLException{
		OrderContactor orderContactor = orderContactorDaoImpl.selectContactorByOrderId(orderId);
		MaterialContactor materialContactor=materialContactorDao.selectContactorByOrderId(orderId);
		OrderContactorPoMaterial orderContactorPo=new OrderContactorPoMaterial();
		orderContactorPo.setOrderId(orderContactor.getOrderId());
		orderContactorPo.setContactor(orderContactor.getContactor());
		orderContactorPo.setPhone(orderContactor.getPhone());
		orderContactorPo.setEmail(orderContactor.getEmail());
		orderContactorPo.setProvince(orderContactor.getProvince());
		orderContactorPo.setCity(orderContactor.getCity());
		orderContactorPo.setCounty(orderContactor.getCounty());
		orderContactorPo.setAddress(orderContactor.getAddress());
		if(materialContactor!=null){
			orderContactorPo.setMaterialContactor(materialContactor.getContactor());
			orderContactorPo.setMaterialPhone(materialContactor.getPhone());
			orderContactorPo.setMaterialEmail(materialContactor.getEmail());
			orderContactorPo.setMaterialAddress(materialContactor.getAddress());
		}

	    return orderContactorPo;
	}

	@Override
	public void updateOrderContactor(OrderContactor contactor, String memberId) throws Exception{				
			orderContactorDaoImpl.update(contactor);
//			List<OrderComContactor> com = orderComContactorDaoImpl.selectByMemberIds(memberId);
//			OrderComContactor contactorChange=new OrderComContactor();
//			int flag=0;
//			if(com != null ){
//				for(OrderComContactor tmp:com){
//					if(tmp.getContactor().equals(contactor.getContactor())){
//						flag=1;
//						contactorChange=convertContactor2Com(contactor,tmp);	
//						contactorChange=tmp;
//					}
//				}
//				if (flag==0){
//					contactorChange=convertContactor2Com(contactor,contactorChange);	
//					orderComContactorDaoImpl.insert(contactorChange);
//				}else{
//					orderComContactorDaoImpl.update(contactorChange);
//				}
//
//			}else{
//				contactorChange=convertContactor2Com(contactor,contactorChange);
//				orderComContactorDaoImpl.insert(contactorChange);
//			}
		
	}
	
	private OrderComContactor convertContactor2Com(OrderContactor contactor,OrderComContactor com){
		com.setMemberId(SSOUtil.getMemberSessionBean().getMemberId());
		com.setId(UUID.randomUUID().toString());
		com.setContactor(contactor.getContactor());
		com.setAddress(contactor.getAddress());
		com.setCity(contactor.getCity());
		com.setCounty(contactor.getCounty());
		com.setEmail(contactor.getEmail());
		com.setPhone(contactor.getPhone());
		com.setProvince(contactor.getProvince());
		return com;
	}
	
	

}
