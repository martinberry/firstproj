package com.ztravel.product.back.pieces.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.product.back.pieces.convert.PiecePrevDetailConvert;
import com.ztravel.product.common.service.ProductDetailService;
import com.ztravel.product.front.vo.VisaProductDetailVo;
import com.ztravel.product.front.wo.ProductWo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.po.pieces.visa.VisaProduct;
import com.ztravel.reuse.product.service.IUnvisaProductReuseService;
import com.ztravel.reuse.product.service.IVisaProductReuseService;

@Controller
@RequestMapping("/piece/prevDetail")
public class PiecePrevDetailController {

    @Resource
    private IVisaProductReuseService visaProductReuseService;

    @Resource
    private IUnvisaProductReuseService unvisaProductReuseService;

    @Resource
    private ProductDetailService productDetailServiceImpl ;

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PiecePrevDetailController.class);

    @RequestMapping(value="/visa/{id}")
    public String detail(@PathVariable String id,Model model,HttpServletRequest request) throws Exception {
        VisaProduct visaProduct = visaProductReuseService.selectById(id);
        VisaProductDetailVo visaProductVo =  PiecePrevDetailConvert.product2VisaVo(visaProduct);
        model.addAttribute("product", visaProductVo);
        return "product/back/pieces/prevDetail/visa/main";
    }

    @RequestMapping("/visa/getPrice/{pid}/{priceId}")
    @ResponseBody
    public JSONObject getPrice(@PathVariable String pid,@PathVariable String priceId) {
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

    @RequestMapping(value = "/unvisa/{id}", method = RequestMethod.GET)
    public ModelAndView getProductDetail(@PathVariable String id, Model model,HttpServletRequest request) throws Exception {

        ProductWo  product = new ProductWo();

        if (StringUtils.isBlank(id)) {
            LOGGER.error("failed to get id from list");
            throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001) ;
        }

        try {
            UnVisaProduct unVisaProduct = unvisaProductReuseService.selectById(id);
            product = PiecePrevDetailConvert.product2UnVisaVo(unVisaProduct);
        } catch (ZtrBizException ze) {
            throw ze ;
        } catch (Exception e) {
            LOGGER.error("failed to get product", e);
            throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002) ;
        }

        model.addAttribute("product", product);
        model.addAttribute("title", product.getpName());

        return new ModelAndView("product/back/pieces/prevDetail/unvisa/local_detail_main") ;
    }

}
