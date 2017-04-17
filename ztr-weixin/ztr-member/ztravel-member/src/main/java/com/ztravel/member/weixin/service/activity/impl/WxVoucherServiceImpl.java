package com.ztravel.member.weixin.service.activity.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.site.lookup.util.StringUtils;
import com.travelzen.framework.logger.core.ri.RequestIdentityLogger;
import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.constants.CouponBookConstants;
import com.ztravel.common.entity.CouponBookVo;
import com.ztravel.common.entity.CouponSnapshot;
import com.ztravel.common.rpc.CommonResponse;
import com.ztravel.common.util.OpenIdUtil;
import com.ztravel.common.util.SSOUtil;
import com.ztravel.member.weixin.service.activity.IWxVoucherService;
import com.ztravel.order.client.service.IVoucherOrderClientService;
import com.ztravel.product.client.entity.Voucher;
import com.ztravel.product.client.service.IActivityClientService;
import com.ztravel.product.client.service.IVoucherClientService;

@Service
public class WxVoucherServiceImpl implements IWxVoucherService {

    private static Logger LOGGER = RequestIdentityLogger.getLogger(WxVoucherServiceImpl.class);

    @Resource
    IVoucherOrderClientService voucherOrderClientService;

    @Resource
    IActivityClientService activityClientService;

    @Resource
    IVoucherClientService voucherClientService;

    @Override
    public AjaxResponse validate(CouponBookVo couponBookVo) throws Exception {
        AjaxResponse applyVoucherOrderResult = AjaxResponse.instance(CouponBookConstants.BUY_VALIDATION_SUCCESS, "");
        //验证登录用户是否挂起
        applyVoucherOrderResult = getCheckActiveResponse(applyVoucherOrderResult);
        if (applyVoucherOrderResult.getRes_code().equals(CouponBookConstants.NOT_ACTIVE_TO_APPLY_ORDER_FAILURE)) {
            return applyVoucherOrderResult;
        }
        //验证用户是否登录
        applyVoucherOrderResult = getLoginResponse(applyVoucherOrderResult);
        if (applyVoucherOrderResult.getRes_code().equals(CouponBookConstants.USER_NOT_LOGIN)) {
            return applyVoucherOrderResult;//用户未登录
        }

        //验证活动
        CommonResponse activityResponse = checkActivity(couponBookVo);
        if(!activityResponse.isSuccess()) {
            applyVoucherOrderResult.setRes_code(CouponBookConstants.ACTIVITY_CHECK_FAILURE);
            applyVoucherOrderResult.setRes_msg(activityResponse.getErrMsg());
            return applyVoucherOrderResult;
        }

        //验证每人限购
        if (!checkMemberLimitNum(couponBookVo)) {
            applyVoucherOrderResult.setRes_code(CouponBookConstants.BUY_AMOUNT_MORE_THAN_UNIT);
            applyVoucherOrderResult.setRes_msg("每用户限购" + couponBookVo.getUnit() + "份");
            return applyVoucherOrderResult;
        }

      //校验代金券库存
        if(!checkVoucherStock(couponBookVo)) {
            applyVoucherOrderResult.setRes_code(CouponBookConstants.COUPON_STOCK_NOT_ENOUGH);
            applyVoucherOrderResult.setRes_msg("仓库断货，筹货中……");
            return applyVoucherOrderResult;
        }
        return applyVoucherOrderResult;
    }

    @Override
    public AjaxResponse applyOrderWithoutValidation(CouponBookVo couponBookVo) throws Exception {

        AjaxResponse applyVoucherOrderResult = AjaxResponse.instance(CouponBookConstants.APPLY_ORDER_FAILURE, "");

        //选择并锁定需要购买的代金券
        if (!lockVouchersByCouponId(couponBookVo)) {
            applyVoucherOrderResult.setRes_code(CouponBookConstants.APPLY_ORDER_FAILURE);
            return applyVoucherOrderResult;
        }

//        //构建订单产品快照
//        CouponSnapshot css =  buidCouponSnapshot(couponBookVo);
//        couponBookVo.setCouponSnapshot(JSONObject.toJSONString(css));

        //开始建单
        Map<String, Object> applyResult = voucherOrderClientService.applyVoucherOrder(couponBookVo);
        applyVoucherOrderResult = (AjaxResponse) applyResult.get("result");
        //返回建单结果
        return applyVoucherOrderResult;
    }

    @Override
    public AjaxResponse applyOrder(CouponBookVo couponBookVo) throws Exception {

        AjaxResponse applyVoucherOrderResult = validate(couponBookVo);
        if (!applyVoucherOrderResult.getRes_code().equals(CouponBookConstants.BUY_VALIDATION_SUCCESS)) {
            return applyVoucherOrderResult;
        }

        applyVoucherOrderResult = AjaxResponse.instance(CouponBookConstants.APPLY_ORDER_FAILURE, "");

        //选择并锁定需要购买的代金券
        if (!lockVouchersByCouponId(couponBookVo)) {
            applyVoucherOrderResult.setRes_code(CouponBookConstants.APPLY_ORDER_FAILURE);
            return applyVoucherOrderResult;
        }

//        //构建订单产品快照(在校验活动时已构建产品快照)
//        CouponSnapshot css =  buidCouponSnapshot(couponBookVo);
//        couponBookVo.setCouponSnapshot(JSONObject.toJSONString(css));

        //开始建单
        Map<String, Object> applyResult = voucherOrderClientService.applyVoucherOrder(couponBookVo);
        applyVoucherOrderResult = (AjaxResponse) applyResult.get("result");
        //返回建单结果
        return applyVoucherOrderResult;
    }

    private CouponSnapshot buidCouponSnapshot(CouponBookVo couponBookVo) {
        CouponSnapshot css = new CouponSnapshot() ;
        css.setActivityId(couponBookVo.getActivityId());
        css.setAmount(couponBookVo.getAmount());
        css.setCouponId(couponBookVo.getCouponId());
        css.setName(couponBookVo.getCouponName());
        css.setValidDateFrom(couponBookVo.getValidTimeFrom().toString("yyyy-MM-dd HH:mm:ss"));
        css.setValidDateTo(couponBookVo.getValidTimeTo().toString("yyyy-MM-dd HH:mm:ss"));
        css.setDescription(couponBookVo.getDescription());
        return css;
    }

    @Transactional(value = "ztravel-txManager", isolation = Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, rollbackFor = Throwable.class)
    public boolean lockVouchersByCouponId(CouponBookVo couponBookVo) throws Exception {

        String couponId = couponBookVo.getCouponId();
        int amount = couponBookVo.getBookAmount();

        //锁定需要购买的类型的代金券
        voucherClientService.selectVoucherLockForUpdateByCouponId(couponId);

        List<Voucher> voucherList = voucherClientService.selectBuyAvailableByCouponIdAndNum(couponId, amount);
        if (voucherList.size() < amount) {
            return false;
        }
        List<String> voucherIdList = getVoucherIdListByVoucherList(voucherList);
        boolean flag = voucherClientService.updateVoucher4ApplyLock(voucherIdList);
        if (flag) {
            //代金券锁定成功后，将取到的voucherIdList存放到couponBookVo中
            couponBookVo.setVoucherIdList(voucherIdList);
        }
        return flag;
    }

    private List<String> getVoucherIdListByVoucherList(List<Voucher> voucherList) {
        List<String> voucherIdList = new ArrayList<String>();
        for (Voucher voucher : voucherList) {
            voucherIdList.add(voucher.getVoucherId());
        }
        return voucherIdList;
    }

    /**
     * 用户是否被挂起
     * */
    private AjaxResponse getCheckActiveResponse(AjaxResponse applyVoucherOrderResult){
        Boolean isActive = true;
        if(null != SSOUtil.getMemberSessionBean() && StringUtils.isNotEmpty(SSOUtil.getMemberSessionBean().getMemberId())){//用户被挂起
            isActive = SSOUtil.getMemberSessionBean().getIsActive();
        }
        if(!isActive){//用户被挂起
            LOGGER.info("用户[{}]处于挂起状态,不能建单",SSOUtil.getMemberSessionBean().getMobile());
            applyVoucherOrderResult.setRes_code(CouponBookConstants.NOT_ACTIVE_TO_APPLY_ORDER_FAILURE);
            applyVoucherOrderResult.setRes_msg("咦?账号异常,请与客服联系~");
        }
        return applyVoucherOrderResult;
    }

    /**
     * 用户是否登录
     * */
    private AjaxResponse getLoginResponse(AjaxResponse applyVoucherOrderResult) throws Exception{
        if(!SSOUtil.isLogin() || SSOUtil.getMemberId() == null) {//未登录
            applyVoucherOrderResult.setRes_code(CouponBookConstants.USER_NOT_LOGIN);
            applyVoucherOrderResult.setRes_msg("/rl/torl");
            if (OpenIdUtil.getOpenId() != null) {
                applyVoucherOrderResult.setRes_msg("/weixin/product/list");
            }
        }
        return applyVoucherOrderResult;
    }

    public boolean checkMemberLimitNum(CouponBookVo couponBookVo) throws Exception {
        String memberId = SSOUtil.getMemberId();
        String couponId = couponBookVo.getCouponId();
        int limtNum = couponBookVo.getUnit();
        int orderNum = couponBookVo.getBookAmount();
        return voucherOrderClientService.canBuy(memberId, couponId, limtNum, orderNum);
    }

    /**
     * 检验活动
     * @param productBookItem
     * @return
     * @throws Exception
     */
    public CommonResponse checkActivity(CouponBookVo couponBookVo) throws Exception {
        CommonResponse response = activityClientService.validateDNActivityAvailable(couponBookVo);
        return response;
    }

    /**
     * 检验活动
     * @param productBookItem
     * @return
     * @throws Exception
     */
    public Boolean checkVoucherStock(CouponBookVo couponBookVo) throws Exception {
        int availableNum = voucherClientService.countBuyAvailableByCouponId(couponBookVo.getCouponId());
        if (availableNum < couponBookVo.getBookAmount()) {
            return false;
        }
        return true;
    }

}
