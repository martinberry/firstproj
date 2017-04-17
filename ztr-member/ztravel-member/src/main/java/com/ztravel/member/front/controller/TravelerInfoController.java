/**
 *
 */
package com.ztravel.member.front.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.Const;
import com.ztravel.common.constants.ProductBookConstans;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;
import com.ztravel.member.client.service.TravelerInfoClientService;
import com.ztravel.member.front.service.MemberService;
import com.ztravel.member.front.service.TravelerInfoService;
import com.ztravel.member.front.util.TravelerInfoValidation;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.member.po.TravelerEntity.Credentials;
import com.ztravel.sraech.client.service.IAutoComplete;

/**
 * @author zuoning.shen
 *
 */
@Controller
@RequestMapping("/travelerInfo")
public class TravelerInfoController {

    private static Logger logger = LoggerFactory.getLogger(TravelerInfoController.class);

	@Resource(name="tThriftAutoComplete")
	IAutoComplete autoComplete;

    @Resource
    private TravelerInfoService travlerInfoService;

    @Resource
    private TravelerInfoClientService travlerInfoClientService;

    @Resource
    private MemberService memberService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView showPage(HttpServletRequest request, Model model) {
        try {
    		String memberId =SSOUtil.getMemberId();
            List<SimpleTravelerEntity> simpleTravelers = travlerInfoService.getTravelerInfo(memberId);
            model.addAttribute("travelerList", simpleTravelers);
        } catch (Exception e) {
            logger.error("Failed to load data", e);
        }
        return new ModelAndView("member/front/travelerInfo");
    }

    @RequestMapping("/edit")
    public String edit(Model model, String id) {
        TravelerEntity traveler = null;
        try {
            traveler = travlerInfoService.getById(id);
        } catch (Exception e) {
            logger.error("Failed to get traveler", e);
        }
        model.addAttribute("travelerInfo", traveler);
        return "member/front/travelerInfoEdit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public AjaxResponse save(@RequestBody TravelerEntity traveler, HttpServletRequest request) throws Exception{
		AjaxResponse response	= new AjaxResponse();
	    String phoneNum	=  traveler.getPhoneNum();
	    String nationality	  =   traveler.getNationality();
	    String email =  traveler.getEmail();
	    List<Credentials> credentialsList = traveler.getCredentials();

	    Map<String,	Object> repeateNameMap = Maps.newHashMap();

//	    MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean() ;

//    	if(memberSessionBean.getIsLogin() == false || StringUtils.isBlank(memberSessionBean.getMid()) || memberSessionBean.getIsActive() ==false){
//    		return AjaxResponse.instance("MEMBERINFOERROR","账号异常，请联系客服");
//		}
	    String wholeEnNameReg = "^[a-zA-z]{1,60}$";
		String wholeChNameReg = "^[\\u4E00-\\u9FA5]{1,40}$";
	    try{
	    	if(StringUtils.isNotEmpty(traveler.getPageStoreId())){
	    		TravelerEntity travelerEntity = travlerInfoService.getById(traveler.getPageStoreId());
	    		if(null !=	travelerEntity){


	    			String name = traveler.getFirstNameCn() + traveler.getLastNameCn();

	    			if(!name.matches(wholeEnNameReg) && !name.matches(wholeChNameReg)){
	    				return  AjaxResponse.instance(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE, "旅客姓名不正确");
	    			}

	    			if(!travelerEntity.getTravelerNameCn().equals(traveler.getTravelerNameCn())){
	    				repeateNameMap	=	travlerInfoService.checkRepeateName(traveler.getTravelerNameCn(),	traveler.getMemberId());
	    		    	if(repeateNameMap.get("repeate").equals("repeated")){
	    		    		return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_REPEATENAME_ILLEGE_CODE, ResponseConstants.TRAVELERINFO_REPEATENAME_ILLEGE_MSG);
	    		    	}
	    			}
	    		}

	    	}else{
    			String name = traveler.getFirstNameCn() + traveler.getLastNameCn();

    			if(!name.matches(wholeEnNameReg) && !name.matches(wholeChNameReg)){
    				return  AjaxResponse.instance(ProductBookConstans.PASSENGER_NAME_ERROE_ORDER_FAILURE, "旅客姓名不正确");
    			}

	    		repeateNameMap	=	travlerInfoService.checkRepeateName(traveler.getTravelerNameCn(),traveler.getMemberId());
		    	if(repeateNameMap.get("repeate").equals("repeated")){
		    		return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_REPEATENAME_ILLEGE_CODE, ResponseConstants.TRAVELERINFO_REPEATENAME_ILLEGE_MSG);
		    	}
	    	}
	    }catch(Exception e){
	    	 logger.error("Failed to check repeateName", e);
	    }

	    //校验号码
	    if(StringUtils.isNotEmpty(phoneNum)){
	    	try {
				TravelerInfoValidation.validate(phoneNum, TravelerInfoValidation.MOBILE);
			} catch (Exception e1) {
				return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_MOBILE_ILLEGE_CODE
						, ResponseConstants.TRAVELERINFO_MOBILE_ILLEGE_MSG) ;
			}
	    }

	  //校验邮箱
	  	if(StringUtils.isNotEmpty(email)){
		  	try {
				TravelerInfoValidation.validate(email, TravelerInfoValidation.EMAIL);
			} catch (Exception e1) {
				return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_EMAIL_ILLEGE_CODE
						, ResponseConstants.TRAVELERINFO_EMAIL_ILLEGE_MSG) ;
			}
	  	}

	  	if(StringUtils.isNotEmpty(nationality)){
	  		try{
	  			if(!isExistNationalName(nationality)){
	  				return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_NATIONALITY_ILLEGE_CODE, ResponseConstants.TRAVELERINFO_NATIONALITY_ILLEGE_MSG);
	  			}
	  		}catch(Exception e){
	  			logger.info("调用自动补全获取国籍列表失败："+e.getMessage());
	  		}
	  	}

	  	//校验证件号码
	  	try {
	  		if(credentialsList.size()>3){
	  			return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_CREDITSIZE_ILLEGE_CODE
							, ResponseConstants.TRAVELERINFO_CREDITSIZE_ILLEGE_MSG) ;
	  		}
	  		String credentialtype = "";
	  		String credentialNum="";
	  		String credentialValidateType="";
	  		for(Credentials credential :credentialsList ){
	  			credentialtype	=	credential.getType();
	  			credentialNum	= 	credential.getNumber();
		  			if(TravelerInfoValidation.CREDIT_CN.equals(credentialtype)){
		  				try{
		  					credentialValidateType = TravelerInfoValidation.CREDIT;
		  					TravelerInfoValidation.validate(credentialNum,credentialValidateType);
		  				}catch(Exception e){
		  					return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_CREDIT_ILLEGE_CODE
		  							, ResponseConstants.TRAVELERINFO_CREDIT_ILLEGE_MSG) ;
		  				}
		  			}else if(TravelerInfoValidation.PASSPORT_CN.equals(credentialtype)){
		  				try{
		  					credentialValidateType = TravelerInfoValidation.PASSPORT;
		  					TravelerInfoValidation.validate(credentialNum, credentialValidateType);
		  				}catch(Exception e){
		  					return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_PASSPORT_ILLEGE_CODE
		  							, ResponseConstants.TRAVELERINFO_PASSPORT_ILLEGE_MSG) ;
		  				}
		  			}else if(TravelerInfoValidation.GANGAOPASS_CN.equals(credentialtype)){
		  				try{
			  				credentialValidateType = TravelerInfoValidation.GANGAOPASS;
			  				TravelerInfoValidation.validate(credentialNum, credentialValidateType);
		  				}catch(Exception e){
		  					return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_GANGAOPASS_ILLEGE_CODE
		  							, ResponseConstants.TRAVELERINFO_GANGAOPASS_ILLEGE_MSG) ;
		  				}
		  			}

	  		}
		} catch (Exception e1) {
			return AjaxResponse.instance(ResponseConstants.TRAVELERINFO_CRENDITIALS_ILLEGE_CODE
					, ResponseConstants.TRAVELERINFO_CRENDITIALS_ILLEGE_MSG) ;
		}

        try {
        	String memberId = memberService.getMemberFromRedisBySID().getMemberId() ;
        	if(null==traveler.getNationality() || ""==traveler.getNationality()){
        		traveler.setNationality(Const.DEFAULT_COUNTRY);
        	}
        	traveler.setMemberId(memberId);
            traveler.setIsActive(true);
            travlerInfoService.save(traveler);
            response.setRes_code("0");
            response.setRes_msg("保存成功");
        } catch (Exception e) {
            logger.error("Failed to save traveler", e);
            response.setRes_code("100003");
            response.setRes_msg("保存失败");
        }
        return response;
    }


    public boolean  isExistNationalName(String nationalName) throws Exception {
    	boolean isExist = false;
		List<String> nationalitysSearchFromLucene = autoComplete.countryAutoComplete(nationalName, 5);
		@SuppressWarnings("rawtypes")
		Map nationalMap = Maps.newHashMap();
     	String nationalNameSearchFromLucene	= "";
 	   	for(int i=0;i<nationalitysSearchFromLucene.size();i++){
     	   	nationalMap =  JSON.parseObject(nationalitysSearchFromLucene.get(i));
     	   nationalNameSearchFromLucene=(String) nationalMap.get("nameCn");
     	    if(StringUtils.isNotBlank(nationalNameSearchFromLucene)){
     	    	if(nationalNameSearchFromLucene.equals(nationalName)){
     	    		isExist = true;
     	    		break;
     	    	}
     	    }
     	}
    	return isExist;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResponse delete(String id){
        AjaxResponse response = new AjaxResponse();

        try {
            travlerInfoService.deleteById(id);
            response.setRes_code("0");
            response.setRes_msg("删除成功");
        } catch (Exception e) {
            logger.error("Failed to delete traveler", e);
            response.setRes_code("100004");
            response.setRes_msg("删除失败");
        }
        return response;
    }

	@RequestMapping("/countryAutoComplete")
	@ResponseBody
	public String[] countryAutoComplete(String query){
		 try {
	         	return getCountryNamesByQuery(query);
			}catch (Exception e) {
	            logger.error("Failed to country autoComplete", e);
			}
		return null;
	}

	@RequestMapping("/checkRepeateName" )
	@ResponseBody
	public Map<String,	Object> checkRepeateName(String travelerNameCn){
		 try {
			 	String memberId = SSOUtil.getMemberId();
	         	return travlerInfoService.checkRepeateName(travelerNameCn,	memberId );
			}catch (Exception e) {
	            logger.error("Failed to checkRepeateName", e);
			}
		return null;
	}


	public String[] getCountryNamesByQuery(String query)	throws Exception{
	  	List<String> countrys = autoComplete.countryAutoComplete(query, 5);
     	return getCountryNamesFromCountrys(countrys);

	}

	public String[] getCountryNamesFromCountrys(List<String> countrys)	throws Exception{
		@SuppressWarnings("rawtypes")
		Map countryMap = Maps.newHashMap();
     	int size = countrys.size();
     	String[] countryNames =new String[size];
 	   	for(int i=0;i<countrys.size();i++){
 	   		countryMap =  JSON.parseObject(countrys.get(i));
 	   		countryNames[i]=(String) countryMap.get("nameCn");
     	}
		return countryNames;
	}


	@RequestMapping(value = "/isEmailAlreadyExists", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResponse isEmailAlreadyExists(String email){
		try{
			email = TravelerInfoValidation.validate(email, TravelerInfoValidation.EMAIL);
		}catch(Exception e){
			return AjaxResponse.instance(e.getMessage(), e.getMessage()) ;
		}
		try{
			if(memberService.isEmailAlreadyExists(email)){
				return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_CODE
						, ResponseConstants.MEMB_EMAIL_ALREADYEXISTS_MSG) ;
			}else{
				return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_SUCCESS_CODE
						, ResponseConstants.MEMB_EMAIL_SUCCESS_MSG) ;
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return AjaxResponse.instance(ResponseConstants.MEMB_EMAIL_PROGRAM_ERROR_CODE
					, ResponseConstants.MEMB_EMAIL_PROGRAM_ERROR_CODE) ;
		}
	}

	@RequestMapping(value="/membersIsActive",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object>  membersIsActive() throws Exception{
		Map<String,Object> map = Maps.newHashMap();
		String isActive = "no";
		MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
		if(memberSessionBean !=null && memberSessionBean.getIsActive()) {
			isActive = "yes";
		}
		map.put("isActive", isActive);
		return map;
	}

}