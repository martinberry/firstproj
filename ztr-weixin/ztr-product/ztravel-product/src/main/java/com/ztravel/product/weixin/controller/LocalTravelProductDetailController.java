package com.ztravel.product.weixin.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

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
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.constants.Const;
import com.ztravel.common.entity.OrderCommentSearchEntity;
import com.ztravel.common.enums.OrderCommentStatus;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.holder.TokenHolder;
import com.ztravel.member.client.service.FrontCommonService;
import com.ztravel.member.client.service.IWishListClientService;
import com.ztravel.order.client.service.IOrderCommentClientService;
import com.ztravel.order.client.vo.OrderCommentClientVO;
import com.ztravel.product.back.pieces.convert.PiecePrevDetailConvert;
import com.ztravel.product.front.wo.ProductWo;
import com.ztravel.product.po.pieces.common.PriceInfo;
import com.ztravel.product.po.pieces.unvisa.UnVisaProduct;
import com.ztravel.product.weixin.convertor.WxPieceProductDetailConvertor;
import com.ztravel.product.weixin.vo.PieceProductDetailVo;
import com.ztravel.reuse.product.service.IUnvisaProductReuseService;

/**
 * 前台产品详情
 *
 * @author nyn
 *
 */
@Controller
@RequestMapping("/local/product/weixin")
public class LocalTravelProductDetailController {

	private static Logger LOGGER = RequestIdentityLogger.getLogger(LocalTravelProductDetailController.class);

    @Resource
    private IUnvisaProductReuseService unvisaProductReuseService;

	@Resource
	private IWishListClientService wishListClientServiceImpl;

	@Resource
	private IOrderCommentClientService commentClientService;

	@Resource
	private FrontCommonService frontCommonService;

	private final RedisClient redisClient = RedisClient.getInstance();

	@RequestMapping(value = "/detail/{productPid}", method = RequestMethod.GET)
	public ModelAndView getProductDetail(@PathVariable("productPid") String productPid, Model model) throws Exception {

		ProductWo product = new ProductWo();

		if (StringUtils.isBlank(productPid)) {
		    LOGGER.error("failed to get Pid from list");
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001);
		}

		try {
            UnVisaProduct unVisaProduct = unvisaProductReuseService.selectByPid(productPid);
            product = PiecePrevDetailConvert.product2UnVisaVo(unVisaProduct);
		} catch (ZtrBizException ze) {
			throw ze;
		} catch (Exception e) {
		    LOGGER.error("failed to get product", e);
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002);
		}

		model.addAttribute("product", product);

		String pre = null;
		String pos = null;
		@SuppressWarnings("unchecked")
		List<String> pids = redisClient.get(TokenHolder.get() + ":pids", ArrayList.class);
		if (pids != null) {
			for (int i = 0; i < pids.size(); i++) {
				if (pids.get(i).equals(productPid)) {
					if (i > 0) {
						pre = pids.get(i - 1);
					} else {
						pre = pids.get(pids.size() - 1);
					}
					if (i < pids.size() - 1) {
						pos = pids.get(i + 1);
					} else {
						pos = pids.get(0);
					}
					redisClient.expire(TokenHolder.get() + ":pids", 60 * 60);
					break;
				}
			}
		}
		model.addAttribute("prePid", pre);
		model.addAttribute("posPid", pos);

		// 用户评价
		OrderCommentSearchEntity searchEntity = new OrderCommentSearchEntity();
		searchEntity.setPid(productPid);
		searchEntity.setStatus(OrderCommentStatus.PUBLISHED);
		//List<OrderCommentClientVO> commentList = commentClientService.searchOrderComment(searchEntity);
		//Long commentNum = commentClientService.countOrderComment(searchEntity);
		Long commentNum=null;
		List<OrderCommentClientVO> commentList=null ;
		model.addAttribute("commentNum", commentNum);
        model.addAttribute("commentList", commentList);
		return new ModelAndView("product/weixin/detail/unvisa/local_travel_detail");
	}

    @RequestMapping("/chooseTrip/{pid}")
    public String toDaySelect(@PathVariable String pid, Model model) throws Exception {
        LOGGER.info("微信端非签证产品[{}]选择出发日期开始",pid);
        UnVisaProduct unVisaProduct = unvisaProductReuseService.selectByPid(pid);
        PieceProductDetailVo pieceProductVo = WxPieceProductDetailConvertor.product2Vo(unVisaProduct);
        model.addAttribute("product", pieceProductVo);
        LOGGER.info("微信端非签证产品[{}]选择出发日期结束", pid);
        return "product/weixin/detail/unvisa/local_chooseTrip";
    }

    @RequestMapping("/getPrice/{pid}/{priceId}")
    @ResponseBody
    public JSONObject getPrice(@PathVariable String pid, @PathVariable String priceId) {
        JSONObject response = new JSONObject();
        PriceInfo price = null;
        try {
            price = unvisaProductReuseService.getPriceByPidAndPriceId(pid, priceId);
            response.put("status", "SUCCESS");
            response.put("price",price);
        } catch (Exception e) {
            LOGGER.error("查询非签证产品价格类型信息错误:[{}]", e);
            response.put("status", "FAILED");
        }
        return response;
    }

}
