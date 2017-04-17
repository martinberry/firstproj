package com.ztravel.member.opera.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.constants.Const;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.member.opera.service.TravellerService;
import com.ztravel.member.opera.util.TravellerUtils;
import com.ztravel.member.opera.wo.TravellerRequestInfo;
import com.ztravel.member.opera.wo.TravellerResponseInfo;
import com.ztravel.member.opera.wo.TravellerResponseInfoDetail;
import com.ztravel.member.opera.wo.TravellerSearchCriteria;

/**
 *
 * @author tengmeilin
 *
 */
@Controller
@RequestMapping("/member/opera/traveller")
public class TravellerController {

	private static Logger logger = RequestIdentityLogger.getLogger(TravellerController.class);

	@Resource
	private TravellerService travellerServiceImpl;

	@RequestMapping(value="/list/{memberId}", method = RequestMethod.GET)
	public ModelAndView getTravellerList(@PathVariable("memberId") String memberId, Model model, HttpServletRequest request) {

		TravellerSearchCriteria criteria = new TravellerSearchCriteria();

		if(StringUtils.isBlank(memberId)){
			logger.error("not get memberId from session ! ");
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1002, Const.FO_MEMB_REASON_1002) ;
		}
		criteria.setMemberId(memberId);

		List<TravellerResponseInfo> list = new ArrayList<>();
		try {

			list = travellerServiceImpl.getTravellersOneMember(criteria);

        } catch (Exception e) {
            logger.error("failed to get traveller list !", e);
            throw ZtrBizException.instance(Const.FO_MEMB_CODE_1003, Const.FO_MEMB_REASON_1003) ;
        }

		model.addAttribute("list", list);
		model.addAttribute("memberId", memberId); //左侧菜单放会员主键id

		return new ModelAndView("member/opera/memberManage/traveller/travellersList");
	}


	@RequestMapping(value="/detail", method = RequestMethod.POST)
	public ModelAndView getTravellerDtail(@RequestParam(value="travelerId") String travelerId, Model model) throws Exception{

		TravellerResponseInfoDetail detail = new TravellerResponseInfoDetail();

		try {
			if(StringUtils.isNotBlank(travelerId)){
				detail = travellerServiceImpl.getTravellersDetail(travelerId);
			}
        } catch (Exception e) {
            logger.error("failed to get traveller detail ! ", e);
            throw ZtrBizException.instance(Const.FO_MEMB_CODE_1001, Const.FO_MEMB_REASON_1001) ;
        }

		model.addAttribute("detail", detail);

		return new ModelAndView("member/opera/memberManage/traveller/travellerDetail");
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView getTravellersOneMember(@RequestBody TravellerRequestInfo searchCriteria, HttpServletRequest request, Model model) throws Exception {

		TravellerSearchCriteria criteria = new TravellerSearchCriteria();

		if(StringUtils.isBlank(searchCriteria.getMemberId())){
			logger.error("not get memberId from session ! ");
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1002, Const.FO_MEMB_REASON_1002) ;
		}
		criteria.setMemberId(searchCriteria.getMemberId());

		if( !TravellerUtils.validateTravellerRequest(searchCriteria, criteria)){
			logger.info("name or phon error ! ");
			throw ZtrBizException.instance(Const.FO_MEMB_CODE_1004, Const.FO_MEMB_REASON_1004) ;
		}

		List<TravellerResponseInfo> list = new ArrayList<>();
		try {

			list = travellerServiceImpl.getTravellersOneMember(criteria);

        } catch (Exception e) {
            logger.error("failed to get traveller search list !", e);
            throw ZtrBizException.instance(Const.FO_MEMB_CODE_1003, Const.FO_MEMB_REASON_1003) ;
        }

		model.addAttribute("searchList", list);
		model.addAttribute("size", list.size());

		return new ModelAndView("member/opera/memberManage/traveller/travellerTbody");

	}

}
