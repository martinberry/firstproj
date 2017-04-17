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
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.pieces.service.IPieceDetailInfoService;
import com.ztravel.product.back.pieces.utils.PiecesValidator;
import com.ztravel.product.back.pieces.vo.UnVisaDetailInfoVo;
import com.ztravel.product.back.pieces.vo.VisaDetailInfoVo;

@Controller
@RequestMapping("/pieces/detailInfo")
public class PieceDetailInfoController {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceDetailInfoController.class) ;

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    private IPieceDetailInfoService pieceDetailInfoServiceImpl;

    @RequestMapping("/edit/{nature}/{id}")
    public String edit(Model model, @PathVariable String nature, @PathVariable String id) {
        String url = "";
        try {
            if (Nature.VISA.name().equals(nature)) {
                url = "product/back/pieces/visa_detail";
                VisaDetailInfoVo visaDetailInfoVo = pieceDetailInfoServiceImpl.queryVisaDetailInfoVoById(id);
                model.addAttribute("detailInfo", visaDetailInfoVo);
            } else if (Nature.UNVISA.name().equals(nature)) {
                url = "product/back/pieces/unvisa_detail";
                UnVisaDetailInfoVo unVisaDetailInfoVo = pieceDetailInfoServiceImpl.queryUnVisaDetailInfoVoById(id);
                model.addAttribute("detailInfo", unVisaDetailInfoVo);
            } else {
                throw new IllegalArgumentException("产品性质不正确");
            }
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询详情信息出错：" + e.getMessage(), e);
        }
        model.addAttribute("pieceMenuVo", pieceDetailInfoServiceImpl.getPieceMenuVo(id, nature)) ;
        model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
        model.addAttribute("mode", "edit");
        return url;
    }

    @RequestMapping("/save/unvisa")
    @ResponseBody
    public AjaxResponse save(@RequestBody UnVisaDetailInfoVo detailInfoVo){
        try {
            if (detailInfoVo.getWithNext()) {
                PiecesValidator.AssertUnVisaDetailInfo(detailInfoVo);
            } else {
                PiecesValidator.AssertUnVisaDetailInfoWithoutNext(detailInfoVo);
            }
            pieceDetailInfoServiceImpl.save(detailInfoVo);

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
        } catch(ZtrBizException e){
            LOGGER.error(e.getRetMsg(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
        } catch(Exception e){
            LOGGER.error("保存详情信息出错："+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存详情信息出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存详情信息成功");
    }

    @RequestMapping("/save/visa")
    @ResponseBody
    public AjaxResponse save(@RequestBody VisaDetailInfoVo detailInfoVo){
        try {
            if (detailInfoVo.getWithNext()) {
                PiecesValidator.AssertVisaDetailInfo(detailInfoVo);
            } else {
                PiecesValidator.AssertVisaDetailInfoWithoutNext(detailInfoVo);
            }
            pieceDetailInfoServiceImpl.save(detailInfoVo);

        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
        } catch(ZtrBizException e){
            LOGGER.error(e.getRetMsg(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
        } catch(Exception e){
            LOGGER.error("保存详情信息出错："+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存详情信息出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, "保存详情信息成功");
    }

}
