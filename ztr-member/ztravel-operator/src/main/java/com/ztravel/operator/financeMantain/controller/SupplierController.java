package com.ztravel.operator.financeMantain.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.core.wrapper.Pagination;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.util.WebEnv;
import com.ztravel.operator.financeMantain.po.Supplier;
import com.ztravel.operator.financeMantain.service.ISupplierService;
import com.ztravel.operator.financeMantain.vo.SupplierQuery;

@Controller
@RequestMapping("/financeMantain/opera/supplier")
public class SupplierController {
	private static Logger logger = RequestIdentityLogger.getLogger(SupplierController.class);

	private static String operaServer = WebEnv.get("server.path.operaServer", "");
	@Resource
	ISupplierService supplierService;
	

	@RequestMapping("/index")
	public String showList(Model model, HttpServletRequest request){
		return "operator/financeMantain/supplier_index";
	}

	@RequestMapping(value="/add",	method=RequestMethod.POST)
	public String add(Supplier supplier) {
		
		try{
				supplierService.add(supplier);
		}catch(Exception e){
			logger.error("新增供应商失败",	e);
		}
		return "redirect:"+operaServer+"/financeMantain/opera/supplier/index";
	}

	@RequestMapping(value="/edit",	method= RequestMethod.GET)
	@ResponseBody
	public Map<String,	Object> edit(Integer supplierId, Model model,	HttpServletRequest request){
		Map<String,	Object> map = Maps.newHashMap();
		Supplier supplier = new Supplier();
		String result = "failed";
		try{
			supplier =supplierService.getSupplierById(supplierId);
			result	= "successed";
		}catch(Exception e){
			logger.error("编辑供应商失败{}"+e,supplierId);
		}
		map.put("supplier", supplier);
		map.put("result", result);
		return map;
	}

	@RequestMapping(value="/update",	method=RequestMethod.POST)
	public String update(Supplier supplier){
		try{
			supplierService.update(supplier);
	}catch(Exception e){
		logger.error("新增供应商失败",	e);
	}
		return "redirect:"+operaServer+"/financeMantain/opera/supplier/index";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView getSearchList(@RequestBody SupplierQuery queryBean ,HttpServletRequest request, Model model) throws Exception {
		 Pagination<Supplier> searchResult = new Pagination<Supplier>();
		 List<Supplier> supplierList = Lists.newArrayList();
			int pageNo =1;
			long totalItemCount=0l;
			long totalPageCount=1l;
		try{
			searchResult = supplierService.search(queryBean);
			supplierList = (List<Supplier>) searchResult.getData();
			if(null != searchResult && null!=supplierList && supplierList.size()>0){

				pageNo = queryBean.getPageNo();
				totalItemCount = searchResult.getTotalItemCount();
				totalPageCount = searchResult.getTotalPageCount();
			}

		}catch(Exception e){
			e.printStackTrace();
			logger.error("=====查询列表失败 :",e);
		}


		model.addAttribute("searchList", supplierList);
		model.addAttribute("pageNo", pageNo);
		model.addAttribute("pageSize", queryBean.getPageSize());
		model.addAttribute("totalItemCount",	totalItemCount);
		model.addAttribute("totalPageCount", totalPageCount);

		return new ModelAndView("operator/financeMantain/supplierTable");
	}

	@RequestMapping(value = "/exportExcel", method = RequestMethod.POST)
	public void exportExcel(WebRequest request,  HttpServletResponse response) {
		try {
			supplierService.exportExcel(request,response);
		} catch (Exception e) {
			logger.error("=====导出供应商excel失败 :",e);
		}
	}

	@RequestMapping(value="/isSupplierNameExisted",	method= RequestMethod.GET)
	@ResponseBody
	public Map<String,	Object> isSupplierNameExisted(String supplierName, Model model,	HttpServletRequest request){
		Map<String,	Object> map = Maps.newHashMap();
		List<Supplier> suppliers = Lists.newArrayList();
		String result = "failed";
		String msg = "notExisted";
		try{
			suppliers =supplierService.getSupplierBySupplierName(supplierName);
			if(null != suppliers && suppliers.size() >=1){
				result	= "successed";
				msg	=	"existed";
			}
		}catch(Exception e){
			logger.error("判断供应商名字{}是否重复失败"+e,		supplierName);
		}
		map.put("msg", msg);
		map.put("result", result);
		return map;
	}

	@RequestMapping("/supplierNameAutoComplete")
	@ResponseBody
	public String[] supplierNameAutoComplete(String query){
		 try {
			 	if(StringUtils.isNotEmpty(query)){
			     	return supplierService.getSupplierNamesByQuery(query);
			 	}
			}catch (Exception e) {
	            logger.error("Failed to supplierName autoComplete", e);
			}
		return null;
	}

}
