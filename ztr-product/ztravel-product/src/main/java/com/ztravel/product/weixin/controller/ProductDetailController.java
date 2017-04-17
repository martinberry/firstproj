package com.ztravel.product.weixin.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.google.common.collect.Lists;
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
import com.ztravel.product.back.freetravel.entity.SalesPackage;
import com.ztravel.product.common.service.ProductDetailService;
import com.ztravel.product.front.wo.ProductWo;
import com.ztravel.product.weixin.wo.PriceTabDataWo;
import com.ztravel.product.weixin.wo.WxCalendarDataWo;
import com.ztravel.product.weixin.wo.WxDayWo;

/**
 * 前台产品详情
 *
 * @author tengmeilin
 *
 */
@Controller
@RequestMapping("/product/weixin")
public class ProductDetailController {

	private static Logger logger = RequestIdentityLogger.getLogger(ProductDetailController.class);

	@Resource
	private ProductDetailService productDetailServiceImpl;

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
			logger.error("failed to get Pid from list");
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001);
		}

		try {
			product = productDetailServiceImpl.getProductByPid(productPid);

		} catch (ZtrBizException ze) {
			throw ze;
		} catch (Exception e) {
			logger.error("failed to get product", e);
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
		List<OrderCommentClientVO> commentList = commentClientService.searchOrderComment(searchEntity);
		Long commentNum = commentClientService.countOrderComment(searchEntity);
		model.addAttribute("commentNum", commentNum);
        model.addAttribute("commentList", commentList);
//		if (null != commentList && commentList.size() >= 3) {
//			model.addAttribute("commentList", commentList.subList(0, 3));
//		} else {
//			model.addAttribute("commentList", commentList);
//		}

		return new ModelAndView("product/weixin/detail/detail");
	}

	@RequestMapping(value = "/detail/journeyRecommend/{productPid}", method = RequestMethod.GET)
    public ModelAndView getJourneyRecommend(@PathVariable("productPid") String productPid, Model model) throws Exception {

        ProductWo product = new ProductWo();

        if (StringUtils.isBlank(productPid)) {
            logger.error("failed to get Pid from list");
            throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001);
        }

        try {
            product = productDetailServiceImpl.getProductByPid(productPid);

        } catch (ZtrBizException ze) {
            throw ze;
        } catch (Exception e) {
            logger.error("failed to get product", e);
            throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002);
        }

        model.addAttribute("product", product);

        return new ModelAndView("product/weixin/detail/journey_recommend");
    }

	@RequestMapping(value = "/detail/getCalendarData/{productPid}", method = RequestMethod.POST)
	@ResponseBody
	public PriceTabDataWo getPriceTabData(@PathVariable("productPid") String productPid) throws Exception {
		if (StringUtils.isBlank(productPid)) {
			logger.error("failed to get pid from list");
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001);
		}
		PriceTabDataWo data = new PriceTabDataWo();
		try {
			data = productDetailServiceImpl.getTabDataByPid(productPid);
		} catch (ZtrBizException ze) {
			throw ze;
		} catch (Exception e) {
			logger.error("failed to get product", e);
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002);
		}
		return data;
	}

	@RequestMapping(value = "/detail/queryPackageInfo", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView queryPackageInfo(String pid, String chooseDay, Model model) throws Exception {
		logger.info("queryPackageInfo start: pid= {}, chooseDay={}", pid, chooseDay);
		try {
			List<SalesPackage> salesPackageList = Lists.newArrayList();
			salesPackageList = productDetailServiceImpl.getPackageListByPidAndDate(pid, chooseDay);
			model.addAttribute("searchList", salesPackageList);
			int stock = productDetailServiceImpl.getStockByPidAndDate(pid, chooseDay);
			model.addAttribute("stock", stock);
			logger.info("queryPackageInfo success");
		} catch (Exception e) {
			logger.error("failed to queryPackageInfo:", e);
		}

		return new ModelAndView("product/weixin/detail/packageInfoTab");
	}

	@RequestMapping(value = "/detail/chooseTrip/{pid}", method = RequestMethod.GET)
	public ModelAndView chooseTrip(@PathVariable String pid, HttpServletRequest request, Model model) throws Exception {
		if (StringUtils.isBlank(pid)) {
			logger.error("failed to get pid from list");
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001);
		}

		ProductWo product = productDetailServiceImpl.getProductByPid(pid);
		model.addAttribute("product", product);
		return new ModelAndView("product/weixin/detail/chooseTrip");
	}

	@RequestMapping(value = "/detail/getWxCalendarData/{productPid}", method = RequestMethod.POST)
    @ResponseBody
    public List<WxCalendarDataWo> getWxCalendarData(@PathVariable("productPid") String productPid) throws Exception {
        if (StringUtils.isBlank(productPid)) {
            logger.error("failed to get pid from list");
            throw ZtrBizException.instance(Const.FF_PROD_CODE_1001, Const.FF_PROD_REASON_1001);
        }
        Map<String, List<WxDayWo>> wxDayWoMap = productDetailServiceImpl.getWxDayMapByPid(productPid);
        List<WxCalendarDataWo> wxCalendarDataWoList = convertWxDayWoList2CalendarData(wxDayWoMap);

        return wxCalendarDataWoList;
    }

	private List<WxCalendarDataWo> convertWxDayWoList2CalendarData(Map<String, List<WxDayWo>> wxDayWoMap) throws Exception {
	    List<WxCalendarDataWo> wxCalendarDataWoList = Lists.newArrayList();
	    Iterator it = wxDayWoMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            WxCalendarDataWo wxCalendarDataWo = new WxCalendarDataWo();
            wxCalendarDataWo.setDate(key);
            wxCalendarDataWo.setWxDayWoList(wxDayWoMap.get(key));
            wxCalendarDataWoList.add(wxCalendarDataWo);
        }
        return wxCalendarDataWoList;
    }

	@RequestMapping(value = "/detail/getAllComment/{productPid}", method = RequestMethod.GET)
	public ModelAndView getMoreComment(@PathVariable("productPid") String productPid, Model model) {
		OrderCommentSearchEntity searchEntity = new OrderCommentSearchEntity();
		searchEntity.setPid(productPid);
		searchEntity.setStatus(OrderCommentStatus.PUBLISHED);
		try {
			List<OrderCommentClientVO> commentList = commentClientService.searchOrderComment(searchEntity);
			Long commentNum = commentClientService.countOrderComment(searchEntity);
			model.addAttribute("commentNum", commentNum);
			model.addAttribute("commentList", commentList);
			model.addAttribute("productPid", productPid);
		} catch (Exception e) {
			logger.error("failed to get comment", e);
			throw ZtrBizException.instance(Const.FF_PROD_CODE_1002, Const.FF_PROD_REASON_1002);
		}

		return new ModelAndView("product/weixin/detail/user_evaluation_more");
	}
}
