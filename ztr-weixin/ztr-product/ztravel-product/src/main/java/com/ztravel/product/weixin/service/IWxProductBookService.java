package com.ztravel.product.weixin.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.product.weixin.vo.PriceCretria;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.product.entity.ProductBookVo;

public interface IWxProductBookService {

	ProductBookVo product2BookVo(DetailToOrderCriteria criteria)throws Exception;

	public List<TravelerEntity> getTravelList(String travellerType);

	public List<CouponClientEntity> couponsSort(List<CouponClientEntity> couponList);

	List<OrderComContactor> getContactors() throws Exception;

	Boolean checkIsActive();

	Boolean getLoginIngo();

	AjaxResponse applyOrder(ProductBookItem productBookItem)  throws Exception;

	PriceCretria getOrderPriceInfo(PriceCretria criteria);

	List<CouponClientEntity> getAllCoupons(String productId, String bookDate, String packageId, Integer adultNum, Integer childNum,String productNature,String priceId) throws Exception;

	Boolean isReleased(String productId,String productNature);
}
