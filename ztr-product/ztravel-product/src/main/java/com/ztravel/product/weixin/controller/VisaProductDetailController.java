package com.ztravel.product.weixin.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.enums.CareerType;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.visa.VisaMaterial;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.product.weixin.convertor.WxPieceProductDetailConvertor;
import com.ztravel.product.weixin.vo.PieceProductDetailVo;
import com.ztravel.reuse.product.service.IVisaProductReuseService;

@Controller
@RequestMapping("/weixin/product/visa")
public class VisaProductDetailController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(VisaProductDetailController.class);

	@Resource
	private IVisaProductReuseService visaProductReuseService;

	@RequestMapping("/detail/{pid}")
	public String toDetail(@PathVariable String pid,Model model)throws Exception{
		LOGGER.info("微信端开始查看签证产品[{}]详情",pid);
		VisaProduct visaProduct = visaProductReuseService.selectByPid(pid);
		PieceProductDetailVo pieceProductVo =  WxPieceProductDetailConvertor.product2Vo(visaProduct);
		model.addAttribute("product", pieceProductVo);
		LOGGER.info("微信端查看签证产品[{}]详情结束",pid);
		return "product/weixin/detail/visa/main";
	}

	@RequestMapping("/daySelect/{pid}")
	public String toDaySelect(@PathVariable String pid,Model model)throws Exception{
		LOGGER.info("微信端签证产品[{}]选择出发日期开始",pid);
		VisaProduct visaProduct = visaProductReuseService.selectByPid(pid);
		PieceProductDetailVo pieceProductVo =  WxPieceProductDetailConvertor.product2Vo(visaProduct);
		model.addAttribute("product", pieceProductVo);
		LOGGER.info("微信端签证产品[{}]选择出发日期结束",pid);
		return "product/weixin/detail/visa/daySelect";
	}

	@RequestMapping("/getPrice/{pid}/{priceId}")
	@ResponseBody
	public JSONObject getPrice(@PathVariable String pid,@PathVariable String priceId){
		JSONObject response = new JSONObject();
		PriceInfo price = null;
		try {
			price = visaProductReuseService.getPriceByPidAndPriceId(pid, priceId);
			response.put("status", "SUCCESS");
			response.put("price",price);
		} catch (Exception e) {
			LOGGER.error("查询签证产品价格类型信息错误:[{}]", e);
			response.put("status", "FAILED");
		}
		return response;
	}
	
	@RequestMapping("/getMateria/{materiaType}/{pid}")
	public String getMateria(@PathVariable String materiaType,@PathVariable String pid,Model model) throws Exception{
		Assert.hasText(materiaType, "签证材料类型为空");
		VisaProduct product = visaProductReuseService.selectByPid(pid);
		Assert.isTrue(null != product, "签证产品["+pid+"]不存在");
		List<VisaMaterial> materias = null;
		if(!CollectionUtils.isEmpty(product.getMaterias())){
			Map<String,List<VisaMaterial>> allMaterias = product.getMaterias();
			materias = allMaterias.get(materiaType.trim());
			String title = "";
			if(materiaType.trim().equals(CareerType.STUDENT.name())){
				title = "在校学生签证材料";
			}else if(materiaType.trim().equals(CareerType.CHILD.name())){
				title = "学龄前儿童签证材料";
			}else if(materiaType.trim().equals(CareerType.EMPLOYED.name())){
				title = "在职签证材料";
			}else if(materiaType.trim().equals(CareerType.FREE.name())){
				title = "自由职业者签证材料";
			}else if(materiaType.trim().equals(CareerType.RETIRE.name())){
				title = "退休人员签证材料";
			}
			model.addAttribute("title",title);
		}
		model.addAttribute("pid", pid);
		model.addAttribute("mases", materias);
		model.addAttribute("materiaType", materiaType);
		return "product/weixin/detail/visa/materia";
	}
}
