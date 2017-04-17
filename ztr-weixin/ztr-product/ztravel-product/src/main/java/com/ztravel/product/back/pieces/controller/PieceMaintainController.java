package com.ztravel.product.back.pieces.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.pieces.bean.PieceResponseBean;
import com.ztravel.product.back.pieces.entity.PieceSearchCriteria;
import com.ztravel.product.back.pieces.service.IPieceService;
import com.ztravel.product.back.pieces.vo.PieceProductVo;

@Controller
@RequestMapping("/pieces/maintain")
public class PieceMaintainController {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceMaintainController.class) ;

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    IPieceService pieceServiceImpl;

    @RequestMapping("/index")
    public String showPieceList(Model model, HttpServletRequest request) throws Exception{
        model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
        model.addAttribute("ADDRESS", redisClient.get(Const.CONTINENT_NATION_CITY_KEY).replace("\"", "'"));

        return "product/back/pieces/piece_list";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value="/search",method=RequestMethod.POST)
    public String  search(@RequestBody PieceSearchCriteria searchCriteria, Model model) {

        Map<String, Object> searchResultMap = Maps.newHashMap();
        int pageNo = 0;
        int pageSize = 0;
        Long totalItemCount = 0L;
        Integer totalPageCount = 0;
        List<PieceProductVo> pieceProductVoList = Lists.newArrayList();

        try {
            searchResultMap =  pieceServiceImpl.search(searchCriteria);
        } catch (Exception e) {
            LOGGER.info("产品列表查询失败：" + e.getMessage());
        }
        if (null != searchResultMap) {
            pieceProductVoList = (List<PieceProductVo>) searchResultMap.get("pieceProductVoList");
            pageNo = (int) searchResultMap.get("pageNo");
            pageSize = (int) searchResultMap.get("pageSize");
            totalItemCount = (Long) searchResultMap.get("totalItemCount");
            totalPageCount = (Integer) searchResultMap.get("totalPageCount");
        }

        model.addAttribute("pieceProductVoList", pieceProductVoList);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("totalItemCount", totalItemCount);
        model.addAttribute("totalPageCount", totalPageCount);

        return "product/back/pieces/piece_list_table";
    }

    @RequestMapping(value = "/online/{nature}/{id}")
    @ResponseBody
    public AjaxResponse online(@PathVariable String nature, @PathVariable String id) {
        try {
            PieceResponseBean bean = pieceServiceImpl.updatePieceStatus(id, Nature.valueOf(nature), ProductStatus.RELEASED);
            if (!bean.getFlag()) {
                return AjaxResponse.instance(ProductCons.PROD_PRODUCT_ONLINE_ERROR_CODE, bean.getMsg());
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_PRODUCT_ONLINE_ERROR_CODE, ProductCons.PROD_PRODUCT_ONLINE_ERROR_MSG);
        }
        return AjaxResponse.instance(ProductCons.PROD_PRODUCT_ONLINE_SUCCESS_CODE, ProductCons.PROD_PRODUCT_ONLINE_SUCCESS_MSG);
    }

    @RequestMapping(value = "/close/{nature}/{id}")
    @ResponseBody
    public AjaxResponse close(@PathVariable String nature, @PathVariable String id) {
        try {
            PieceResponseBean bean = pieceServiceImpl.updatePieceStatus(id, Nature.valueOf(nature), ProductStatus.OFFLINE);
            if (!bean.getFlag()) {
                return AjaxResponse.instance(ProductCons.PROD_PRODUCT_CLOSE_ERROR_CODE, ProductCons.PROD_PRODUCT_CLOSE_ERROR_MSG);
            }
        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_PRODUCT_CLOSE_ERROR_CODE, ProductCons.PROD_PRODUCT_CLOSE_ERROR_MSG);
        }
        return AjaxResponse.instance(ProductCons.PROD_PRODUCT_CLOSE_SUCCESS_CODE, ProductCons.PROD_PRODUCT_CLOSE_SUCCESS_MSG);
    }

    @RequestMapping(value = "/del/{nature}/{id}")
    @ResponseBody
    public AjaxResponse del(@PathVariable String nature, @PathVariable String id) {
        try {

            if (!pieceServiceImpl.deletePieceProductByNatureAndId(Nature.valueOf(nature), id)) {
                return AjaxResponse.instance(ProductCons.PROD_PRODUCT_DEL_ERROR_CODE, ProductCons.PROD_PRODUCT_DEL_ERROR_MSG);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_PRODUCT_DEL_ERROR_CODE, ProductCons.PROD_PRODUCT_DEL_ERROR_MSG);
        }
        return AjaxResponse.instance(ProductCons.PROD_PRODUCT_DEL_SUCCESS_CODE, ProductCons.PROD_PRODUCT_DEL_SUCCESS_MSG);
    }

}
