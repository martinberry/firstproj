package com.ztravel.operator.financeMantain.dao;

import java.util.List;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.operator.financeMantain.po.Supplier;

public interface ISupplierDao extends BaseDao<Supplier> {

	List<Supplier> selectBySupplierName(String query);

}
