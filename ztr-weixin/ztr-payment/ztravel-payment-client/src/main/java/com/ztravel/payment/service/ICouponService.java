/**
 *
 */
package com.ztravel.payment.service;

import java.util.List;
import java.util.Map;

import com.travelzen.framework.core.wrapper.Pagination;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.common.payment.CouponCountBean;
import com.ztravel.common.payment.CouponGrantBean;
import com.ztravel.common.payment.CouponItemBean;
import com.ztravel.common.payment.CouponItemQueryBean;
import com.ztravel.common.payment.CouponSumBean;
import com.ztravel.common.rpc.CommonResponse;

/**
 * @author zuoning.shen
 *
 */
public interface ICouponService {
    CommonResponse grant(CouponGrantBean bean);

    CommonResponse transfer(String couponItemId, String destMemberId);

    CommonResponse freeze(String couponItemId);

    CommonResponse unfreeze(String couponItemId);

    CouponItemBean getCouponItem(String couponItemId);

    List<CouponItemBean> getAvailableCouponItems(String memberId);

    Pagination<CouponItemBean> getCouponItems(CouponItemQueryBean bean);

    CouponSumBean sumAmount(String couponCode);

    CouponCountBean countByCode(String couponCode);

    int countByStatusListAndMemberId(List<CouponItemStatus> statusList, String memberId);

	Pagination<CouponItemBean> getCouponItemsNew(CouponItemQueryBean couponItemQuery);

	CommonResponse invalid(String couponItemId) ;

	int countCouponNumWithoutShared(String memberId, String couponId);

	List<String> selectCouponByMap(Map map);
}
