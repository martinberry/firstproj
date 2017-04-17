/**
 *
 */
package com.ztravel.payment.dao;

import java.util.List;
import java.util.Map;

import com.ztravel.common.dao.BaseDao;
import com.ztravel.payment.po.CouponItem;

/**
 * @author zuoning.shen
 *
 */
public interface CouponItemDao extends BaseDao<CouponItem> {
    CouponItem selectItem(String memberId, String couponCode);

    List<CouponItem> selectAvailableItems(String memberId);

    void updateStatus(String couponItemId, String status);

    List<CouponItem> selectCouponItems(Map params);

    int countCouponItems(Map params);

    List<String> selectItemsForExpire();

    long sumGrantedAmount(String couponCode);

    long sumUsedAmount(String couponCode);

    int countGranted(String couponCode);

    int countUsed(String couponCode);

    List<CouponItem> selectCouponItemsNew(Map params);
}
