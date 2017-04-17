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
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.ProductCons;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.OperatorSidHolder;
import com.ztravel.product.back.freetravel.vo.ProductMenuVo;
import com.ztravel.product.back.pieces.service.IPieceBasicInfoService;
import com.ztravel.product.back.pieces.utils.PiecesValidator;
import com.ztravel.product.back.pieces.vo.PieceBasicInfoVo;

@Controller
@RequestMapping("/pieces/basicInfo")
public class PieceBasicInfoController {

    private static final Logger LOGGER = RequestIdentityLogger.getLogger(PieceBasicInfoController.class) ;

    private static final RedisClient redisClient = RedisClient.getInstance();

    @Resource
    private IPieceBasicInfoService pieceBasicInfoServiceImpl;

    @RequestMapping("/add")
    public String add(Model model) {
        model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
        model.addAttribute("mode", "edit");
        ProductMenuVo productMenuVo = new ProductMenuVo();
        productMenuVo.setPid("");
        productMenuVo.setProgress(0);
        model.addAttribute("pieceMenuVo", productMenuVo) ;
        model.addAttribute("ADDRESS", redisClient.get(Const.CONTINENT_NATION_CITY_KEY).replace("\"", "'"));

        return "product/back/pieces/piece_base";
    }

    @RequestMapping("/edit/{nature}/{id}")
    public String edit(Model model, @PathVariable String nature, @PathVariable String id) throws Exception{
        PieceBasicInfoVo basicInfo = null;
        try {
            basicInfo = pieceBasicInfoServiceImpl.queryPieceBasicInfoVoById(id, nature);
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询基本信息出错"+e.getMessage(), e);
        }
        model.addAttribute("pieceMenuVo", pieceBasicInfoServiceImpl.getPieceMenuVo(id, basicInfo.getNature())) ;
        model.addAttribute("userName", redisClient.get(OperatorSidHolder.get()));
        model.addAttribute("basicInfo", basicInfo);
        model.addAttribute("mode", "edit");
        model.addAttribute("ADDRESS", redisClient.get(Const.CONTINENT_NATION_CITY_KEY).replace("\"", "'"));

        return "product/back/pieces/piece_base";
    }

    @RequestMapping("/save")
    @ResponseBody
    public AjaxResponse save(@RequestBody PieceBasicInfoVo pieceBasicInfoVo) {
        String id = pieceBasicInfoVo.getId();
        try {
            if (pieceBasicInfoVo.getWithNext()) {
                PiecesValidator.AssertBasicInfo(pieceBasicInfoVo);
            } else {
                PiecesValidator.AssertBasicInfoWithoutNext(pieceBasicInfoVo);
            }

            id = pieceBasicInfoServiceImpl.save(pieceBasicInfoVo);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getMessage());
        } catch (ZtrBizException e) {
            LOGGER.error(e.getRetMsg(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, e.getRetMsg());
        } catch (Exception e) {
            LOGGER.error("保存基本信息失败:"+e.getMessage(), e);
            return AjaxResponse.instance(ProductCons.PROD_AJAX_ERROR_CODE, "保存基本信息出错");
        }
        return AjaxResponse.instance(ProductCons.PROD_AJAX_SUCCESS_CODE, id);
    }

}
