package com.ztravel.product.back.freetravel.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.enums.Status;
import com.ztravel.product.back.freetravel.service.IAdditinalInfoService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.utils.ProductValidator;
import com.ztravel.product.back.freetravel.vo.AdditionalInfoVo;
import com.ztravel.product.back.freetravel.vo.ProductCheckRespBean;

@Controller
@RequestMapping("/product/additionalInfo")
public class AdditionalInfoController {
	@Resource
	private IAdditinalInfoService additionalInfoServiceImpl;

	@Resource
	private ProductService productService ;

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(AdditionalInfoController.class) ;

	private static final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping("/{accessType}/{id}")
	public String view(Model model, @PathVariable String accessType, @PathVariable String id){
		try {
			AdditionalInfoVo data = additionalInfoServiceImpl.queryAIById(id);
			model.addAttribute("data", data);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
			throw e;
		}catch (Exception e) {
			LOGGER.error("查询附加信息出错："+e.getMessage(), e);
		}
		model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
		model.addAttribute("mode", accessType);
		return "product/back/additional_info";
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResponse save(@RequestBody AdditionalInfoVo addInfo){
		try {
			ProductValidator.AssertAdditionalInfo(addInfo);
			additionalInfoServiceImpl.save(addInfo);
		} catch (IllegalArgumentException e) {
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
		} catch(ZtrBizException e){
			LOGGER.error(e.getRetMsg(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
		} catch(Exception e){
			LOGGER.error("保存附加信息出错："+e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存附加信息出错");
		}
		return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存附加信息成功");
	}

	@RequestMapping("/release/{id}")
	@ResponseBody
	public AjaxResponse release(@PathVariable String id){
		try {
			ProductCheckRespBean bean = productService.changeProductStatus(id, Status.RELEASED) ;
			if(!bean.getFlag()){
				return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, bean.getMsg());
			}
		} catch(Exception e){
			LOGGER.error("发布产品出错："+e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "发布产品出错");
		}
		return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "发布产品成功");
	}
}
