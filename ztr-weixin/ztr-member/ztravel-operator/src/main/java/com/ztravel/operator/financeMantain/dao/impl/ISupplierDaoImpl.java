package com.ztravel.operator.financeMantain.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.operator.financeMantain.dao.ISupplierDao;
import com.ztravel.operator.financeMantain.po.Supplier;

@Repository
public class ISupplierDaoImpl extends BaseDaoImpl<Supplier> implements ISupplierDao {

	@Override
	public List<Supplier> selectBySupplierName(String supplierName) {
		 return session.selectList(nameSpace + ".selectBySupplierName", supplierName);
	}

}
