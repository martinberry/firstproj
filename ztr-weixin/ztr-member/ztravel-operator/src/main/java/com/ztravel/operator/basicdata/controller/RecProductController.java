package com.ztravel.operator.basicdata.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoException;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.enums.ProductType;
import com.ztravel.operator.basicdata.entity.AdSpotEntity;
import com.ztravel.operator.basicdata.entity.RecProductEntity;
import com.ztravel.operator.basicdata.service.IAdSpotService;
import com.ztravel.operator.basicdata.service.IPieceProductService;
import com.ztravel.operator.basicdata.service.IRecProductService;
import com.ztravel.product.client.entity.ProductHomePageEntity;
import com.ztravel.product.client.service.IProductClientService;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;

@Controller
@RequestMapping("/recProduct")
public class RecProductController {

	@Resource
	private IRecProductService recProductService;

	@Resource
	private IAdSpotService adSpotService;

	@Resource
	private IProductClientService ProductClientService ;

	@Resource
	IPieceProductService pieceProductService;

	//每次打开从数据库读取初始页面
	@RequestMapping(value="/index")
	public String showRecProduct(Model model) throws MongoException{
			 List<RecProductEntity> recproductlist=recProductService.searchRecProduct();
		        model.addAttribute("recproductlist",recproductlist);
		return "operator/basicData/recProduct";
	}



	//预览
	@RequestMapping(value="/preview",method=RequestMethod.POST)
	public ModelAndView previewRecProductList(HttpServletRequest request,Model model) throws Exception{
		 List <AdSpotEntity> adspotlist=adSpotService.getAdSpotList();
		 List <String> imageIds=new ArrayList<String>();
		for(int i=0;i<adspotlist.size();i++){
			String temp=adspotlist.get(i).getImageId();
			imageIds.add(temp);
		}

		 model.addAttribute("imageIds",imageIds);



		String recproductlist = request.getParameter("recproductlist");
		JSONArray recarray=JSON.parseArray(recproductlist);
		List<RecProductEntity> recproduct=new ArrayList<RecProductEntity>();

       for(int i=0;i<recarray.size();i++){
    	   JSONObject temp=recarray.getJSONObject(i);
    	   RecProductEntity tempmodel=new RecProductEntity();
    	   tempmodel.setMainTitle(temp.getString("mainTitle"));
    	   tempmodel.setViceTitle(temp.getString("viceTitle"));
    	   tempmodel.setPictureId(temp.getString("pictureId"));
    	   try{
    		   ProductHomePageEntity hpentity =ProductClientService.getProductHPById(temp.getString("productId"));

    			   tempmodel.setTags(hpentity.getTags());
            	   tempmodel.setHighLights(hpentity.getHighLights());
            	   tempmodel.setLowestPrice(hpentity.getLowestPrice());


    	   }catch(Exception e){
    	   }
    	   recproduct.add(tempmodel);
       }
	    model.addAttribute("recproductlist",recproduct);
		return new ModelAndView("operator/basicData/recproductPreview");
	}



//保存
	@RequestMapping(value="/save",method=RequestMethod.POST)
	@ResponseBody
	public String recProductSave(@RequestBody List<RecProductEntity> recproductlist){
		JSONObject jsonobj=new JSONObject();
		try{
			for(int i=0;i<recproductlist.size();i++){
				RecProductEntity recproduct=recproductlist.get(i);
				String productId=recproduct.getProductId();
				productId=productId.toUpperCase();
				recproduct.setProductId(productId);
			}
			recProductService.saveRecProduct(recproductlist);
			jsonobj.put("res_code","success");
		}catch(MongoException e){
			jsonobj.put("res_code","fail");
		}
		return JSONObject.toJSONString(jsonobj);
	}



	//搜索productId
	@RequestMapping(value="/searchproductId",method=RequestMethod.POST)
	@ResponseBody
	public String searchProductId(@RequestBody List<RecProductEntity> productIds) {
		JSONObject jsonobj = new JSONObject();
		try {
			for (int i = 0; i < productIds.size(); i++) {
				RecProductEntity temp = productIds.get(i);
                String productType = temp.getProductType();
				String productId = temp.getProductId().toUpperCase();

				if (ProductType.TRAVEL.name().equals(productType)) {
				    ProductHomePageEntity hpentity = ProductClientService.getProductHPById(productId);
				    String status = ProductClientService.getProductstatusbyid(productId);
                    if (hpentity == null || !(status.equals("released"))) {
                        jsonobj.put("res_code", "empty");
                    }
				} else if (ProductType.VISA.name().equals(productType)) {
				    VisaProduct visaProduct = pieceProductService.queryVisaProductByPid(productId);
				    if (visaProduct == null || visaProduct.getStatus() == null || !ProductStatus.RELEASED.equals(visaProduct.getStatus())) {
				        jsonobj.put("res_code", "empty");
				    }
				} else if (ProductType.UNVISA.name().equals(productType)) {
				    UnVisaProduct unVisaProduct = pieceProductService.queryUnVisaProductByPid(productId);
                    if (unVisaProduct == null || unVisaProduct.getStatus() == null || !ProductStatus.RELEASED.equals(unVisaProduct.getStatus())) {
                        jsonobj.put("res_code", "empty");
                    }
                }
			}
			if (jsonobj.size() == 0) {
				jsonobj.put("res_code", "notempty");
			}
			return JSONObject.toJSONString(jsonobj);
		} catch (Exception e) {
			jsonobj.put("res_code","fail");
			return JSONObject.toJSONString(jsonobj);

		}
	}

	@RequestMapping(value="/error")
	public ModelAndView errorPage(){
		return new ModelAndView("operator/basicData/error");
	}




}
