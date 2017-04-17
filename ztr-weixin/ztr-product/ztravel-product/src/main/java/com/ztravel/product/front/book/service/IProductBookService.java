package com.ztravel.product.front.book.service;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.common.entity.ContactorInfo;
import com.ztravel.common.entity.ProductBookItem;
import com.ztravel.member.po.TravelerEntity;
import com.ztravel.order.po.OrderComContactor;
import com.ztravel.product.client.entity.CouponClientEntity;
import com.ztravel.reuse.order.entity.DetailToOrderCriteria;
import com.ztravel.reuse.product.entity.ProductBookVo;

public interface IProductBookService {

	/**
	 * 获取产品预订详情
	 * @throws Exception
	 */
	ProductBookVo product2BookVo(DetailToOrderCriteria criteria)throws Exception;
	
	ProductBookVo freeTravelProduct2BookVo(DetailToOrderCriteria criteria)throws Exception;

	/**
	 * 产品提交订单
	 * @return
	 * @throws Exception
	 */
	public AjaxResponse applyOrder(ProductBookItem productBookItem)  throws Exception;

	Boolean getLoginIngo();

	/**
     * 查询产品价格信息
     * @param productId--产品id
     * @param bookDate--预订日期(格式yyyy-MM-dd)
     * @return {成人价，儿童价，单房差}
     * @throws Exception
     */
    public Double[] getProductPriceInfo(String productId, String bookDate) throws Exception;

	/**
     * 查询产品价格信息
     * @param productId--产品id
     * @param bookDate--预订日期(格式yyyy-MM-dd)
     * @param packageId--套餐id
     * @return--套餐价
     * @throws Exception
     */
    public Double getProductPackagePrice(String productId, String bookDate, String packageId) throws Exception;

    public Double getTotalPriceByProductBookItem(ProductBookItem productBookItem) throws Exception;

    List<CouponClientEntity> getAllCoupons(String productId,String bookDate,Integer adultNum, Integer childNum,String productNature,String priceId);

    List<CouponClientEntity> getAllCoupons(String productId,String bookDate, String packageId);

	public List<TravelerEntity> getTravelList(String travellerType);

	public List<CouponClientEntity> couponsSort(List<CouponClientEntity> couponList);

	ContactorInfo getContactorInfo() throws Exception;

	Boolean checkIsActive();
	
	List<OrderComContactor> getComContactors() throws Exception;
	
	Boolean isReleased(String productId,String productNature);

}
