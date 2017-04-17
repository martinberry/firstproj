package com.ztravel.member.weixin.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.travelzen.framework.core.util.StringUtil;
import com.travelzen.framework.logger.core.TZMarkers;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.travelzen.framework.redis.client.RedisClient;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.CouponBookConstants;
import com.ztravel.common.constants.ResponseConstants;
import com.ztravel.common.entity.CouponBookVo;
import com.ztravel.common.entity.MemberSessionBean;
import com.ztravel.common.enums.ActivityStatus;
import com.ztravel.common.enums.ActivityType;
import com.ztravel.common.enums.UserRangeType;
import com.ztravel.common.exception.ZtrBizException;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.common.util.WebEnv;
import com.ztravel.reuse.member.entity.MemberConstants;
import com.ztravel.reuse.member.validation.MemberRegisterValidation;
import com.ztravel.member.weixin.service.activity.IWxVoucherService;
import com.ztravel.member.validation.VoucherBuyValidate;
import com.ztravel.payment.service.ICouponService;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.sso.client.service.SSOClientService;
import com.ztravel.sso.po.SSOBasicEntity;

/**
 * 代金券
 * @author tian1.xu
 */
@Controller
@RequestMapping("/activity/coupon/weixin")
public class ActivityCouponController {

    private static Logger LOGGER = RequestIdentityLogger.getLogger(ActivityCouponController.class);

    private static final RedisClient redisClient = RedisClient.getInstance();

    private static final String wxServer = WebEnv.get("server.path.wxServer", "");

    private static final String activityProcessUrl = "http://static.zhenlx.com/mstatic/special/goumailiucheng_dn.html";

    @Resource
    IWxVoucherService wxVoucherService;

    @Resource
    ICouponService couponService;

    @Resource
    IActivityClientService activityClientService;

    @Resource
    SSOClientService ssoClientService;

    @RequestMapping(value="/couponList",method=RequestMethod.GET)
    public String  search(Model model) throws Exception{
        List<CouponBookVo> couponBookVoList = activityClientService.getDNActivityList(getAvailableActivityParams());
        model.addAttribute("couponList", couponBookVoList);
        return "member/weixin/activity/couponList";
    }

    public Map<String,String> getAvailableActivityParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("status", ActivityStatus.EFFECTIVE.name());
        params.put("userRangType", UserRangeType.DN.name());
        params.put("type", ActivityType.CUSTOMERGET.name());
        return params;
    }

    @RequestMapping(value = "/buy")
    @ResponseBody
    public AjaxResponse buy(String activityId, String couponId, Integer amount, Model model){

        AjaxResponse applyResult = new AjaxResponse();
        if (StringUtil.isEmpty(activityId) || StringUtil.isEmpty(couponId) || amount == null ||amount <= 0) {
            applyResult.setRes_code(CouponBookConstants.PARAMS_NOT_CORRECT);
            applyResult.setRes_msg("请选定所需购买的代金券以及所需数量");
            return applyResult;
        }

        if (SSOUtil.isMemberExists() && SSOUtil.isLogin()) {
            applyResult.setRes_code(CouponBookConstants.MEMBER_IS_LOGINED);
            CouponBookVo couponBookVo = new CouponBookVo();
            couponBookVo.setActivityId(activityId);
            couponBookVo.setCouponId(couponId);
            couponBookVo.setBookAmount(amount);
            try {
                applyResult = wxVoucherService.validate(couponBookVo);
            } catch (Exception e) {
                applyResult.setRes_code(CouponBookConstants.BUY_VALIDATION_FAIL);
                applyResult.setRes_msg("代金券库存校验失败");
            }
            try {
                String mobile = getMemberMobile();
                if (applyResult.getRes_code().equals(CouponBookConstants.BUY_VALIDATION_SUCCESS) && !StringUtil.isEmpty(mobile)) {
                    applyResult = wxVoucherService.applyOrderWithoutValidation(couponBookVo);
                }
            }catch(ZtrBizException ze){
                LOGGER.error(TZMarkers.p2, "建单失败", ze);
                if(ze.getRetCode().equals(CouponBookConstants.COUPON_STOCK_NOT_ENOUGH)){//库存不足
                    AjaxResponse applyOrderResult = new AjaxResponse();
                    applyOrderResult.setRes_code(CouponBookConstants.COUPON_STOCK_NOT_ENOUGH);
                    return applyOrderResult;
                }
            }catch (Exception e) {
                LOGGER.error(TZMarkers.p2, "提交订单异常", e);
                applyResult = AjaxResponse.instance(CouponBookConstants.APPLY_ORDER_FAILURE, "");
            }
        } else if (OpenIdUtil.getOpenId() != null) {
            applyResult.setRes_code(CouponBookConstants.USER_NOT_LOGIN);
            String backUrl = wxServer +"/activity/coupon/weixin/couponList";
            try {
                backUrl = URLEncoder.encode(backUrl, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            applyResult.setRes_msg(wxServer + "/rl/torl?backUrl=" + backUrl);
        } else {
            applyResult.setRes_code(CouponBookConstants.USER_NOT_LOGIN);
            applyResult.setRes_msg(activityProcessUrl);
        }

        return applyResult;
    }

    //获取用户电话号码
    private String getMemberMobile() {
        MemberSessionBean memberSessionBean = SSOUtil.getMemberSessionBean();
        String memberId = memberSessionBean.getMemberId();
        String mobile = memberSessionBean.getMobile();
        if (StringUtil.isEmpty(mobile)) {
            SSOBasicEntity entity = ssoClientService.selectMemberByMemberId(memberId);
            if (entity != null && !StringUtil.isEmpty(entity.getMobile())) {
                mobile = entity.getMobile();
                memberSessionBean.setMobile(entity.getMobile());
                SSOUtil.refreshMemberSessionBean(memberSessionBean);
            }
        }
        return mobile;
    }

    @RequestMapping(value="/apply", method={RequestMethod.POST})
    @ResponseBody
    public AjaxResponse apply(@RequestBody CouponBookVo couponBookVo, Model model) {
        AjaxResponse applyResult = null;

        String mobile = couponBookVo.getMobile();
        String verifyCode = couponBookVo.getVerifyCode();

        try{
            VoucherBuyValidate.validate(couponBookVo);
            MemberRegisterValidation.validateVerifyCode(verifyCode);
            String serverVerfyCode = redisClient.get(String.format("%s:%s", MemberConstants.VERIFYCODE_COMMON, mobile)) ;
            if(!verifyCode.equals(serverVerfyCode)){
                return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_CODE
                        , ResponseConstants.MEMB_VERIFYCODE_VERIFY_FAIL_MSG);
            }
        }catch(Exception e){
            LOGGER.error(e.getMessage(), e);
            return AjaxResponse.instance(ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_CODE
                    , ResponseConstants.MEMB_VERIFYCODE_VERIFY_ERROR_MSG);
        }

        try {
            applyResult = wxVoucherService.applyOrder(couponBookVo);
        }catch(ZtrBizException ze){
            LOGGER.error(TZMarkers.p2, "建单失败", ze);
            if(ze.getRetCode().equals(CouponBookConstants.COUPON_STOCK_NOT_ENOUGH)){//库存不足
                AjaxResponse applyOrderResult = new AjaxResponse();
                applyOrderResult.setRes_code(CouponBookConstants.COUPON_STOCK_NOT_ENOUGH);
                return applyOrderResult;
            }
        }catch (Exception e) {
            LOGGER.error(TZMarkers.p2, "提交订单异常", e);
            applyResult = AjaxResponse.instance(CouponBookConstants.APPLY_ORDER_FAILURE, "");
        }
        return applyResult;
    }

}
