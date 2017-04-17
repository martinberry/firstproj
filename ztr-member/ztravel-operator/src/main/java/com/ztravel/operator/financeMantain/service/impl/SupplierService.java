package com.ztravel.operator.financeMantain.service.impl;

import java.io.OutputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.poi.util.ExcelHelper;
import com.ztravel.common.enums.SettlementPeriod;
import com.ztravel.operator.financeMantain.dao.ISupplierDao;
import com.ztravel.operator.financeMantain.po.Supplier;
import com.ztravel.operator.financeMantain.service.ISupplierService;
import com.ztravel.operator.financeMantain.util.ExcelHeadFactoryUtil;
import com.ztravel.operator.financeMantain.util.FileNameUtil;
import com.ztravel.operator.financeMantain.vo.SupplierQuery;
@Service("SupplierService")
public class SupplierService implements ISupplierService{
	private static Logger logger = RequestIdentityLogger.getLogger(SupplierService.class);

	@Resource
	ISupplierDao supplierDaoImpl;

	@Override
	public Pagination<Supplier> search(SupplierQuery supplierQuery) throws Exception {
		Pagination<Supplier> searchResult = new Pagination<Supplier>();
		Map<String,	Object> searchParams = Maps.newHashMap();
		int pageNo =0;
		int totalItemCount=0;
		int totalPageCount=0;
		 List<Supplier> searchList = Lists.newArrayList();
		 try{
			 searchParams  = getSearchParamsByQuery(supplierQuery);
			 searchList = supplierDaoImpl.select(searchParams);
			 totalItemCount = supplierDaoImpl.count(searchParams);
			 totalPageCount = (int) Math.ceil(new Double(totalItemCount)/supplierQuery.getPageSize() );

			 if( searchList.size() == 0){
			 		pageNo = 1;
					totalItemCount = 0;
					totalPageCount=1;
			 }

			 searchResult.setData(searchList);
			 searchResult.setTotalItemCount(totalItemCount);
			 searchResult.setPageSize(supplierQuery.getPageSize());
			 searchResult.setTotalPageCount(totalPageCount);
			 searchResult.setPageNo(pageNo);
		 }catch(Exception e){
			 logger.error("查询失败", e);
		 }
		return searchResult;
	}


	private Map<String, Object> getSearchParamsByQuery(SupplierQuery supplierQuery) {
		Map<String,	Object> searchParams = Maps.newHashMap();
		if(StringUtils.isNotEmpty(supplierQuery.getSupplierName())){
			searchParams.put("supplierName", supplierQuery.getSupplierName());
		}
	  if (supplierQuery.getPageNo() != -1 && supplierQuery.getPageSize() != -1) {
		  searchParams.put("offset", supplierQuery.getPageSize() * (supplierQuery.getPageNo() - 1));
		  searchParams.put("limit", supplierQuery.getPageSize());
	    }

		return searchParams;
	}




	@Override
	public void add(Supplier supplier) throws SQLException {
		if(null != supplier){
			String settlementPeriod = supplier.getSettlementPeriod();
			settlementPeriod = convertSettlementPeriod(settlementPeriod);
			supplier.setSettlementPeriod(settlementPeriod);
			supplier.setCreateTime(new Date());
			supplier.setUpdateTime(new Date());
		}
		supplierDaoImpl.insert(supplier);
	}

	@Override
	public void update(Supplier supplier) throws SQLException {
		if(null != supplier){
			String settlementPeriod = supplier.getSettlementPeriod();
			settlementPeriod = convertSettlementPeriod(settlementPeriod);
			supplier.setSettlementPeriod(settlementPeriod);
			supplier.setUpdateTime(new Date());
		}
		supplierDaoImpl.update(supplier);
	}

	private String convertSettlementPeriod(String settlementPeriod){
		if(StringUtils.isNotEmpty(settlementPeriod)){
			if(settlementPeriod.equals("一单一结")){
				settlementPeriod = SettlementPeriod.SINGLE.toString();
			}else if(settlementPeriod.equals("周结")){
				settlementPeriod = SettlementPeriod.WEEK.toString();
			}else if(settlementPeriod.equals("半月结")){
				settlementPeriod = SettlementPeriod.HALFMONTH.toString();
			}else if(settlementPeriod.equals("月结")){
				settlementPeriod = SettlementPeriod.MONTH.toString();
			}
		}
		return settlementPeriod;
	}


	@Override
	public void delete(Supplier supplier) throws SQLException {
//		mapper.deleteByPrimaryKey(supplier.getSupplierId());
	}

	@Override
	public void exportExcel(WebRequest request, HttpServletResponse response) throws Exception {
		SupplierQuery supplierQuery = this.buildQueryBeanByRequest(request);
		//强制设置
		StringBuffer fileName = new StringBuffer();
		fileName.append("供应商信息");
		fileName.append("_");
		fileName.append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		fileName.append(".xls");
		OutputStream os = response.getOutputStream();
		String returnFileName = FileNameUtil.converterFileName(fileName.toString(), request);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment;filename=" + returnFileName);
		List<Supplier> suppliers  = Lists.newArrayList();
		Pagination<Supplier>  pagination =  this.search(supplierQuery);
		if(null!= pagination.getData()) {
			suppliers = (List<Supplier>) pagination.getData();
		}
		Class<Supplier> clazz = Supplier.class;
		ExcelHelper.getInstanse().exportToOS(os, ExcelHeadFactoryUtil.getSupplierHead(), suppliers, clazz);
	}

	private SupplierQuery buildQueryBeanByRequest(WebRequest request) {
		SupplierQuery supplierQuery = new SupplierQuery();
		String supplierName = request.getParameter("supplierName");
		if(StringUtils.isNotEmpty(supplierName)){
			supplierQuery.setSupplierName(supplierName);
		}
		return supplierQuery;
	}


	@Override
	public Supplier getSupplierById(Integer supplierId) throws SQLException {
		Map<String,	Object> searchParams = Maps.newHashMap();
		List<Supplier> suppliers = Lists.newArrayList();
		searchParams.put("supplierId", supplierId);
		suppliers = supplierDaoImpl.select(searchParams);
		return suppliers==null ?	null	:	suppliers.get(0);
	}


	@Override
	public List<Supplier> getSupplierBySupplierName(String supplierName) throws SQLException{
		Map<String,	Object> searchParams = Maps.newHashMap();
		List<Supplier> suppliers = Lists.newArrayList();
		searchParams.put("supplierName", supplierName);
		suppliers = supplierDaoImpl.select(searchParams);
		return suppliers==null ?	null	:	suppliers;
	}


	@Override
	public String[] getSupplierNamesByQuery(String supplierName) throws Exception{
		List<Supplier> suppliers = supplierDaoImpl.selectBySupplierName(supplierName);
		return this.getSupplierNamesFromSuppliers(suppliers);
	}

	private String[] getSupplierNamesFromSuppliers(List<Supplier> suppliers)	throws Exception{
     	int size = suppliers.size();
     	String[] supplierNames =new String[size];
 	   	for(int i=0;i<suppliers.size();i++){
 	   		supplierNames[i]=(String) suppliers.get(i).getSupplierName();
     	}
		return supplierNames;
	}
}
