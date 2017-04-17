/**
 *
 */
package com.ztravel.payment.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import com.ztravel.common.dao.BaseDaoImpl;
import com.ztravel.common.enums.CouponItemStatus;
import com.ztravel.payment.dao.CouponItemDao;
import com.ztravel.payment.po.CouponItem;

/**
 * @author zuoning.shen
 *
 */
@Repository
public class CouponItemDaoImpl extends BaseDaoImpl<CouponItem> implements CouponItemDao {

    @Override
    public CouponItem selectItem(String memberId, String couponCode) {
        Map params = new HashMap();
        params.put("memberId", memberId);
        params.put("couponCode", couponCode);
        return session.selectOne(nameSpace + ".selectItem", params);
    }

    @Override
    public List<CouponItem> selectAvailableItems(String memberId) {
        return session.selectList(nameSpace + ".selectAvailableItems", memberId);
    }

    @Override
    public void updateStatus(String couponItemId, String status) {
        Map params = new HashMap();
        params.put("couponItemId", couponItemId);
        params.put("status", status);
        DateTime now = DateTime.now();
        if(CouponItemStatus.USED.name().equals(status) || CouponItemStatus.FROZEN.name().equals(status)){
            params.put("useDate", now);
        }else{
        	params.put("useDate", null);
        }
        params.put("updated", now);
        session.update(nameSpace + ".updateStatus", params);
    }

    @Override
    public List<CouponItem> selectCouponItems(Map params) {
        return session.selectList(nameSpace + ".selectCouponItems", params);
    }

    @Override
    public int countCouponItems(Map params) {
        return session.selectOne(nameSpace + ".countCouponItems", params);
    }

    @Override
    public List<String> selectItemsForExpire() {
        return session.selectList(nameSpace + ".selectItemsForExpire");
    }

    @Override
    public long sumGrantedAmount(String couponCode) {
        return session.selectOne(nameSpace + ".sumGrantedAmount", couponCode);
    }

    @Override
    public long sumUsedAmount(String couponCode) {
        return session.selectOne(nameSpace + ".sumUsedAmount", couponCode);
    }

    @Override
    public int countGranted(String couponCode) {
        return session.selectOne(nameSpace + ".countGranted", couponCode);
    }

    @Override
    public int countUsed(String couponCode) {
        return session.selectOne(nameSpace + ".countUsed", couponCode);
    }

    @Override
    public List<CouponItem> selectCouponItemsNew(Map params) {
        return session.selectList(nameSpace + ".selectCouponItemsNew", params);
    }

}
