package com.ztravel.weixin.activity.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.ActivityAwardConstants;
import com.ztravel.common.entity.AwardWeight;
import com.ztravel.common.enums.ActivityAwardType;
import com.ztravel.common.holder.WechatAccountHolder;
import com.ztravel.common.util.IdGeneratorUtil;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.weixin.activity.enums.ActivityRecordStatus;
import com.ztravel.weixin.activity.service.INewYearActivityUserService;
import com.ztravel.weixin.activity.service.INewYearAwardStockService;
import com.ztravel.weixin.activity.service.INewYearUserAwardRecordService;
import com.ztravel.weixin.activity.vo.ActivityAwardOptionsVo;
import com.ztravel.weixin.activity.vo.ActivityUserVo;
import com.ztravel.weixin.activity.vo.AwardRecordVo;
import com.ztravel.weixin.activity.vo.AwardSettingsVo;
import com.ztravel.weixin.entity.WxUserEntity;
import com.ztravel.weixin.operate.OpenIdOperator;
import com.ztravel.weixin.user.service.IWxUserService;


@Controller
@RequestMapping("/happy2016")
public class NewYearActivityController {

    private static Logger logger = RequestIdentityLogger.getLogger(NewYearActivityController.class);

    @Resource
    private IdGeneratorUtil idGeneratorUtil ;

    @Resource
    private INewYearActivityUserService newYearActivityUserService;

    @Resource
    private INewYearAwardStockService newYearAwardStockService;

    @Resource
    private INewYearUserAwardRecordService newYearUserAwardRecordService;


    @Resource
    private IWxUserService wxUserService;



    @RequestMapping(value="/achieveAward", method={RequestMethod.POST})
    @ResponseBody
    public AjaxResponse achieveAward(@RequestBody ActivityUserVo activityUserVo) {
        AjaxResponse applyResult = null;
        try {

            //用户校验并保存
            boolean activityUserFlag = newYearActivityUserService.validateAndSaveActivityUser(activityUserVo);
            if (!activityUserFlag) {
                //返回用户信息异常
                applyResult = AjaxResponse.instance(ActivityAwardConstants.AWARD_ACHIEVE_FAIL, "");
                return applyResult;
            }

            //查询奖励并领取
            applyResult = newYearUserAwardRecordService.randomCreateAwardRecordByOpenId(activityUserVo.getOpenId());

        }catch (Exception e) {
            logger.error(TZMarkers.p2, "获取奖励异常", e);
            applyResult = AjaxResponse.instance(ActivityAwardConstants.AWARD_ACHIEVE_FAIL, "");
        }
        return applyResult;
    }

    @ResponseBody
    @RequestMapping(value = "/goToGame", method = RequestMethod.GET)
    public ModelAndView goToGame(HttpServletRequest request, Model model, String code, String state) {

        dealWxUserInfo (code, state, model);

        String openId = OpenIdUtil.getOpenId();
        if (!model.containsAttribute("activityUser") && openId != null) {
            WxUserEntity wxUser = wxUserService.selectWxUserInfoByOpenId(openId);
            if (wxUser != null) {
                ActivityUserVo activityUserVo = new ActivityUserVo();
                activityUserVo.setOpenId(wxUser.getOpenid());
                activityUserVo.setNickName(wxUser.getNickname());
                activityUserVo.setHeadImageUrl(wxUser.getHeadimgurl());
                model.addAttribute("activityUser", activityUserVo);
            }
        }
        List<AwardRecordVo> recordList = newYearUserAwardRecordService.searchAwardRecordList();
        model.addAttribute("records", recordList);
        model.addAttribute("appId", WechatAccountHolder.APP_ID);
        return new ModelAndView("/activity/newYear/newYearGame");
    }

    @RequestMapping(value = "/goToGameResult", method = RequestMethod.POST)
    public String goToGameResult(Model model, ActivityUserVo activityUserVo) {

        return "activity/newYear/activityResult";
    }

    private void dealWxUserInfo (String code, String state, Model model) {
        logger.info("goToGame请求的参数code为：" + code + ",state为：" + state + ".");
        if (!StringUtil.isEmpty(code) && !StringUtil.isEmpty(state) && "happy2016".equals(state)) {
            try {
                JSONObject oAuthAccessToken = OpenIdOperator.oAuthAccessToken(code);
                if (oAuthAccessToken.getString("errcode") == null) {
                    //获取得到openid
                    String openid = oAuthAccessToken.getString("openid");
                    String access_token = oAuthAccessToken.getString("access_token");
                    JSONObject userBasicInfo = OpenIdOperator.getUserBasicInfo(access_token, openid);
                    if (userBasicInfo.getString("errcode") == null) {
                        String nickName = userBasicInfo.getString("nickname");
                        String headimgurl = userBasicInfo.getString("headimgurl");
                        ActivityUserVo activityUserVo = new ActivityUserVo();
                        activityUserVo.setOpenId(openid);
                        activityUserVo.setNickName(nickName);
                        activityUserVo.setHeadImageUrl(headimgurl);
                        model.addAttribute("activityUser", activityUserVo);

                        WxUserEntity entity = wxUserService.selectWxUserInfoByOpenId(openid);
                        if (entity == null) {
                            WxUserEntity wxUser = JSONObject.toJavaObject(userBasicInfo, WxUserEntity.class);
                            wxUser.setLastModifyTime(new DateTime());
                            wxUser.setMpId(WechatAccountHolder.APP_ID);
                            //插入微信用户表
                            wxUser.setId(idGeneratorUtil.getWxUserId());
                            wxUserService.insertWxUserInfo(wxUser);
                        }
                    }
                    logger.info("获得的openid为：" + openid);

                    OpenIdUtil.setOpenId(openid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getActivityAwardOptions", method = RequestMethod.GET)
    public ModelAndView getActivityAwardOptions(HttpServletRequest request, Model model){

        Map<String, ActivityAwardOptionsVo> recordList = newYearAwardStockService.getActivityAwardOptions();
        model.addAttribute("records", recordList);
        return new ModelAndView("/activity/newYear/activityAwardSettings");
    }

    @ResponseBody
    @RequestMapping(value="/modifyActivityAwardWeight", method = RequestMethod.POST)
    public AjaxResponse modifyActivityAwardWeight(@RequestBody AwardSettingsVo awardSettingsVo) {

        AjaxResponse response =  AjaxResponse.instance("FAIL", "操作异常");

        try {
            if (awardSettingsVo.getBagChangeNum() != null && awardSettingsVo.getBagChangeNum() != 0) {
                newYearAwardStockService.updateStockByAwardCodeAndChangeNum(ActivityAwardType.BAG.name(), awardSettingsVo.getBagChangeNum());
            }

            if (awardSettingsVo.getCalendaryChangeNum() != null && awardSettingsVo.getCalendaryChangeNum() != 0) {
                newYearAwardStockService.updateStockByAwardCodeAndChangeNum(ActivityAwardType.CALENDARY.name(), awardSettingsVo.getCalendaryChangeNum());
            }
            AwardWeight awardWeight = buildAwardWeightByAwardSettingsVo(awardSettingsVo);
            newYearAwardStockService.buildAwardWeight(awardWeight);
            response.setRes_code("SUCCESS");
        } catch (Exception e) {
            logger.error(TZMarkers.p2, "修改权重信息或库存余量异常", e);
            response.setRes_msg("修改权重信息或库存余量异常");
        }

        return response;
    }

    private AwardWeight buildAwardWeightByAwardSettingsVo(AwardSettingsVo awardSettingsVo) {
        AwardWeight awardWeight = new AwardWeight();
        awardWeight.setBag(awardSettingsVo.getBagWeight());
        awardWeight.setCalendary(awardSettingsVo.getCalendaryWeight());
        awardWeight.setCoupon1(awardSettingsVo.getCoupon1Weight());
        awardWeight.setCoupon2(awardSettingsVo.getCoupon2Weight());
        awardWeight.setCoupon3(awardSettingsVo.getCoupon3Weight());
        return awardWeight;
    }

	@ResponseBody
	@RequestMapping(value = "/saveGuestinfo", method = RequestMethod.POST)
	public AjaxResponse SaveInfo(@RequestBody ActivityUserVo activityUserVo){
       AjaxResponse ajaxResponse=AjaxResponse.instance("savefail", "保存失败");
       try{
    	   ActivityRecordStatus status=newYearUserAwardRecordService.selectAwardStatus(activityUserVo);
          if(status.equals(ActivityRecordStatus.RELEASED)){
        	   ajaxResponse.setRes_code("outtime");
        	   ajaxResponse.setRes_msg("超时提交");

          }else{
       	   newYearActivityUserService.saveGuestInfo(activityUserVo);
    	   ajaxResponse.setRes_code("savesuccess");
    	   ajaxResponse.setRes_msg("保存成功");
          }
    	   return ajaxResponse;
       }catch(Exception e){
           logger.error(e.getMessage(), e);
    	   ajaxResponse.setRes_code("savefail");
    	   ajaxResponse.setRes_msg("保存失败");
    	  return ajaxResponse;
       }
	}

	@RequestMapping(value = "/error")
	public String error(){
		 return "activity/newYear/error";
	}






}
