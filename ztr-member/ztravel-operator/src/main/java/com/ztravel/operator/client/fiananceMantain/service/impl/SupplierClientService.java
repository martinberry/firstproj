package com.ztravel.operator.client.fiananceMantain.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ztravel.operator.client.finance.service.ISupplierClientService;
import com.ztravel.operator.financeMantain.dao.ISupplierDao;
import com.ztravel.operator.financeMantain.po.Supplier;


/**
 * 
 * @author wanhaofan
 *
 */
@Service
public class SupplierClientService implements ISupplierClientService{
	
	@Resource
	ISupplierDao supplierDaoImpl;
	
	@Override
	public List<Supplier> getSuppliers(Map params) {
		List<Supplier> suppliers = null ;
		suppliers = supplierDaoImpl.select(params) ;
		return suppliers == null ? new ArrayList<Supplier>() : suppliers ;
	}
	
	@Override
	public Supplier getSupplierById(String id) {
		Map<String,	Object> searchParams = Maps.newHashMap();
		List<Supplier> suppliers = Lists.newArrayList();
		if(StringUtils.isNotBlank(id)){
			searchParams.put("supplierId", Integer.valueOf(id));
			suppliers = supplierDaoImpl.select(searchParams);
		}
		if(suppliers == null || suppliers.size() == 0){
			Supplier supplier = new Supplier() ;
			supplier.setSupplierName("");
			return supplier ;
		}else{
			return suppliers.get(0) ;
		}
	}

}
