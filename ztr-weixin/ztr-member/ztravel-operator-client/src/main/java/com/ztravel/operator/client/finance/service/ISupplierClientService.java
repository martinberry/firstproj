package com.ztravel.operator.client.finance.service;

import java.util.List;
import java.util.Map;

import com.ztravel.operator.financeMantain.po.Supplier;

public interface ISupplierClientService {
	List<Supplier> getSuppliers(Map params) ;
	
	Supplier getSupplierById(String id) ;
}
