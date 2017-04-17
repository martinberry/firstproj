package com.ztravel.product.back.freetravel.controller;

import java.util.List;

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
import com.ztravel.product.back.freetravel.entity.DescribeItem;
import com.ztravel.product.back.freetravel.entity.Trip;
import com.ztravel.product.back.freetravel.service.IRecommendTripService;
import com.ztravel.product.back.freetravel.service.ProductService;
import com.ztravel.product.back.freetravel.utils.ProductValidator;
import com.ztravel.product.back.freetravel.vo.RecommendTripVo;

@Controller
@RequestMapping("/product/recommendTrip")
public class RecommendTripController {
	private static final Logger LOGGER = RequestIdentityLogger.getLogger(RecommendTripController.class) ;

	private static final RedisClient redisClient = RedisClient.getInstance();
	@Resource
	private IRecommendTripService recommendTripServiceImpl;

	@Resource
	private ProductService productService ;

	@RequestMapping("/{accessType}/{id}")
	public String add(Model model, @PathVariable String accessType, @PathVariable String id){
		try {
			model.addAttribute("mode", accessType);
			RecommendTripVo vo = recommendTripServiceImpl.queryByid(id);
			model.addAttribute("data", vo);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
			throw e;
		} catch (Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
		model.addAttribute("productMenuVo", productService.getProductMenuVo(id)) ;
		return "product/back/recommend_trip";
	}

	@RequestMapping("/save")
	@ResponseBody
	public AjaxResponse save(@RequestBody RecommendTripVo tripVo){
		try{
			if(tripVo.getWithNext()){
				ProductValidator.AssertRecommendTrip(tripVo);
			}
			
			
			List<Trip> triplist=tripVo.getRecommendTrips();
			for(Trip triptemp:triplist){
				List<DescribeItem> describelist=triptemp.getDesItems();
				String content=triptemp.getContent();
				if(content!=null){
					content=content.replaceAll("\n\t","");
					content=content.replaceAll("</p>\n<p>","</p><p>");
					content=content.replaceAll("<br />","");
					triptemp.setContent(content);
				}
				for(DescribeItem describetemp:describelist){
					String changedesc=describetemp.getDescribe();
					if(changedesc!=null){
						changedesc=changedesc.replaceAll("\n\t","");
						changedesc=changedesc.replaceAll("</p>\n<p>","</p><p>");
						changedesc=changedesc.replaceAll("<br />","");
						describetemp.setDescribe(changedesc);
					}
				
				}
			}
			
			
			
			
			
			
			
			
			recommendTripServiceImpl.save(tripVo);
		}catch(IllegalArgumentException e){
			LOGGER.error(e.getMessage(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
		}catch(ZtrBizException e){
			LOGGER.error(e.getRetMsg(), e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
		}catch(Exception e){
			LOGGER.error("保存推荐行程失败", e);
			return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存推荐行程失败");
		}
		return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存推荐行程成功");
	}
}
