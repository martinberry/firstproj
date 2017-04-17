package com.ztravel.operator.financeMantain.service;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.request.WebRequest;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.operator.financeMantain.po.Supplier;
import com.ztravel.operator.financeMantain.vo.SupplierQuery;

public interface ISupplierService {

	void exportExcel(WebRequest request, HttpServletResponse response) throws Exception;

	Pagination<Supplier> search(SupplierQuery supplierQuery) throws Exception;

	void add(Supplier supplier) throws SQLException;

	void update(Supplier supplier) throws SQLException;

	void delete(Supplier supplier) throws SQLException;

	Supplier getSupplierById(Integer supplierId)	throws SQLException;

	List<Supplier> getSupplierBySupplierName(String supplierName) throws SQLException;

	String[] getSupplierNamesByQuery(String query) throws Exception;

}
