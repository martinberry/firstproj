package com.ztravel.member.opera.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.areasearch.entity.ProvCityArea;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ResponseConstBackMemb;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.util.TokenUtil;
import com.ztravel.member.opera.entity.MemberSearchCriteria;
import com.ztravel.member.opera.service.IMemberService;
import com.ztravel.member.opera.service.IProvCityAreaService;
import com.ztravel.member.opera.util.ValidationUtils;
import com.ztravel.member.opera.vo.MemberVO;

/**
 * @author
 * @description 后台系统 会员维护Controller
 */

@Controller
@RequestMapping("/member/memberMaintain")
public class MemberMaintainController {

	private static final Logger LOGGER = RequestIdentityLogger.getLogger(MemberMaintainController.class);

	@Resource
	private IMemberService memberService;
	@Resource
	private IProvCityAreaService provCityAreaService;

	@RequestMapping("/list")
	public String showMemberList(Model model, HttpServletRequest request){
		List<ProvCityArea> provList = provCityAreaService.getTopLevelRegionList();
		model.addAttribute("provList", provList);
		return "member/opera/memberManage/memberMaintain/main";
	}

	@RequestMapping("/loadCity")
	public String loadCity(@RequestBody String regionNo, Model model){
		if( StringUtils.isNotBlank(regionNo) ){
			List<ProvCityArea> secLevelRegion = provCityAreaService.getSecondLevelRegion(regionNo);
			model.addAttribute("cityList", secLevelRegion);
		}
		return "member/opera/memberManage/memberMaintain/cityDropDownMenu";
	}

	@RequestMapping(value="/searchMember",method=RequestMethod.POST)
	public String searchMember(@RequestBody MemberSearchCriteria searchCriteria, Model model){
		//搜索条件格式校验，若有任意一项格式错误，不查询数据库，返回空列表
		if( !ValidationUtils.isMemberSearchCriteriaValid(searchCriteria) ){
			return "member/opera/memberManage/memberMaintain/memberTable";
		}

		Integer memNum = null;
		List<MemberVO> memList = null;
		try{
			memNum = memberService.countMembers(searchCriteria);
			memList = memberService.searchMembers(searchCriteria);
		} catch(ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
		} catch(Exception e) {
			LOGGER.error(e.getMessage(), e);
		}

		model.addAttribute("memberList", memList);
		if( memNum != 0 ){
			model.addAttribute("pageNo", searchCriteria.getPageNo());
		}else{
			model.addAttribute("pageNo", 1);
		}
		model.addAttribute("pageSize", searchCriteria.getPageSize());
		model.addAttribute("totalItemCount", memNum);
		Integer totalPageCount = (int) Math.ceil( (double)memNum/searchCriteria.getPageSize() );
		totalPageCount = totalPageCount == 0 ? 1 : totalPageCount ;
		model.addAttribute("totalPageCount", totalPageCount);
		return "member/opera/memberManage/memberMaintain/memberTable";
	}

	@RequestMapping("/detail/{id}")
	public String gotoMemberDetail(@PathVariable("id") String id, Model model, HttpServletRequest request){
	    MemberVO member = null;
	    try {
			member = memberService.searchMemberDetailById(id);
		} catch (ZtrBizException e) {
			LOGGER.error(e.getRetMsg(), e);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		model.addAttribute("member", member);
		String wxNickName = memberService.getWxNickNameById(id);
        if (wxNickName != null) {
            model.addAttribute("wxNickName", wxNickName);
        }
		return "member/opera/memberManage/memberMaintain/memberDetail";
	}

	@RequestMapping(value="/suspend/{operationType}", method=RequestMethod.POST)
	@ResponseBody
	public AjaxResponse modifyMemberStatus(@PathVariable("operationType") String type, @RequestBody String strIds){
		try{
			List<String> idList = new ArrayList<String>();
			String[] idArray = strIds.split(" ");
			for(String id : idArray){
				idList.add(id);
			}
			if( type.equals("lock") ){               //挂起
				memberService.modifyMemberStatus(idList, false);
				if(idList != null && idList.size() > 0){
					for(String id : idList){
						TokenUtil.kickToken(id);
					}
				}
			}else if( type.equals("unlock") ){    //解挂
				memberService.modifyMemberStatus(idList, true);
				if(idList != null && idList.size() > 0){
					for(String id : idList){
						TokenUtil.addToken(id);
					}
				}
			}
			return AjaxResponse.instance(ResponseConstBackMemb.MEMB_MODIFY_MEMBER_STATUS_SUCCESS_CODE,
					                                            ResponseConstBackMemb.MEMB_MODIFY_MEMBER_STATUS_SUCCESS_MSG);
		} catch (SQLException e){
			LOGGER.error("修改会员状态 数据库操作异常", e);
			return AjaxResponse.instance(ResponseConstBackMemb.MEMB_MODIFY_MEMBER_STATUS_ERROR_CODE,
					                                            ResponseConstBackMemb.MEMB_MODIFY_MEMBER_STATUS_ERROR_MSG);
		} catch (Exception e){
			return AjaxResponse.instance(ResponseConstBackMemb.MEMB_MODIFY_MEMBER_STATUS_ERROR_CODE,
					                                            ResponseConstBackMemb.MEMB_MODIFY_MEMBER_STATUS_ERROR_MSG);
		}
	}

}