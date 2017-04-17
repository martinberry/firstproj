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
import com.ztravel.common.enums.Nature;
import com.ztravel.common.enums.ProductStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.pieces.bean.PieceResponseBean;
import com.ztravel.product.back.pieces.service.IPieceAdditionalInfoService;
import com.ztravel.product.back.pieces.utils.PiecesValidator;
import com.ztravel.product.back.pieces.vo.UnVisaAdditionalInfoVo;
import com.ztravel.product.back.pieces.vo.VisaAdditionalInfoVo;

@Controller
@RequestMapping("/pieces/additionalInfo")
public class PieceAdditionalInfoController {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceAdditionalInfoController.class) ;

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    IPieceAdditionalInfoService pieceAdditionalInfoServiceImpl;

    @RequestMapping("/edit/{nature}/{id}")
    public String edit(Model model, @PathVariable String nature, @PathVariable String id) {
        String url = "";
        try {
            if (Nature.VISA.name().equals(nature)) {
                url = "product/back/pieces/visa_additional";
                VisaAdditionalInfoVo visaAdditionalInfoVo = pieceAdditionalInfoServiceImpl.queryVisaAdditionalInfoVoById(id);
                model.addAttribute("additionalInfo", visaAdditionalInfoVo);
            } else if (Nature.UNVISA.name().equals(nature)) {
                url = "product/back/pieces/unvisa_additional";
                UnVisaAdditionalInfoVo unVisaAdditionalInfoVo = pieceAdditionalInfoServiceImpl.queryUnVisaAdditionalInfoVoById(id);
                model.addAttribute("additionalInfo", unVisaAdditionalInfoVo);
            } else {
                throw new IllegalArgumentException("产品性质不正确");
            }
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询附加信息出错：" + e.getMessage(), e);
        }
        model.addAttribute("pieceMenuVo", pieceAdditionalInfoServiceImpl.getPieceMenuVo(id, nature)) ;
        model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
        model.addAttribute("mode", "edit");
        return url;
    }

    @RequestMapping("/save/visa")
    @ResponseBody
    public AjaxResponse save(@RequestBody VisaAdditionalInfoVo additionalInfoVo) {
        try {
            PiecesValidator.AssertVisaAdditionalInfoWithoutNext(additionalInfoVo);
            pieceAdditionalInfoServiceImpl.save(additionalInfoVo);

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
        } catch (Exception e) {
            LOGGER.error("保存附加信息出错："+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存附加信息出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存附加信息成功");
    }

    @RequestMapping("/save/unvisa")
    @ResponseBody
    public AjaxResponse save(@RequestBody UnVisaAdditionalInfoVo additionalInfoVo) {
        try {
            PiecesValidator.AssertUnVisaAdditionalInfoWithoutNext(additionalInfoVo);
            pieceAdditionalInfoServiceImpl.save(additionalInfoVo);

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
        } catch (Exception e) {
            LOGGER.error("保存附加信息出错："+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存附加信息出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存附加信息成功");
    }

    @RequestMapping("/release/{nature}/{id}")
    @ResponseBody
    public AjaxResponse release(@PathVariable String nature, @PathVariable String id) {
        try {
            PieceResponseBean bean = pieceAdditionalInfoServiceImpl.updatePieceStatus(id, Nature.valueOf(nature), ProductStatus.RELEASED);
            if (!bean.getFlag()) {
                return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, bean.getMsg());
            }
        } catch(Exception e) {
            LOGGER.error("发布产品出错："+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "发布产品出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "发布产品成功");
    }

}
