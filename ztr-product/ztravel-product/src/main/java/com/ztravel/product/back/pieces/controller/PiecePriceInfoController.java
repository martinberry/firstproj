package com.ztravel.product.back.pieces.controller;

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
import com.ztravel.product.back.pieces.service.IPiecePriceInfoService;
import com.ztravel.product.back.pieces.utils.PiecesValidator;
import com.ztravel.product.back.pieces.vo.PiecePriceInfoVo;

@Controller
@RequestMapping("/pieces/priceInfo")
public class PiecePriceInfoController {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PiecePriceInfoController.class);

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    private IPiecePriceInfoService piecePriceInfoServiceImpl;

    @RequestMapping("/edit/{priceType}/{nature}/{id}")
    public String editCost(Model model, @PathVariable String priceType, @PathVariable String nature, @PathVariable String id) {
        String url = "";
        PiecePriceInfoVo priceInfo = null;
        try {
            priceInfo = piecePriceInfoServiceImpl.queryPiecePriceInfoVoById(id, nature);
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询价格信息出错：" + e.getMessage(), e);
        }

        if ("cost".equals(priceType)) {
            priceInfo.setPriceType("cost");
            url = "product/back/pieces/piece_price_cost";
        } else if ("sale".equals(priceType)) {
            priceInfo.setPriceType("sale");
            url = "product/back/pieces/piece_price_sale";
        }

        model.addAttribute("priceInfo", priceInfo);
        model.addAttribute("pieceMenuVo", piecePriceInfoServiceImpl.getPieceMenuVo(id, nature)) ;
        model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
        model.addAttribute("mode", "edit");

        return url;
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResponse save(@RequestBody PiecePriceInfoVo piecePriceInfoVo) {
        try {
            if (piecePriceInfoVo.getWithNext()) {
                PiecesValidator.AssertPriceInfo(piecePriceInfoVo);
            } else {
                PiecesValidator.AssertPriceInfoWithoutNext(piecePriceInfoVo);
            }
            piecePriceInfoServiceImpl.save(piecePriceInfoVo);

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
        } catch (Exception e) {
            LOGGER.error("保存价格信息失败:"+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存价格信息出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存价格信息成功");
    }

}
