package com.ztravel.order.back.vo;

import java.util.List;

import com.ztravel.common.entity.AdditionalProduct;
import com.ztravel.common.enums.CommonOrderStatus;

/**
 * 用于后台自由行订单详情页面展示
 * @author MH
 */
public class OrderDetailVO {
	/**
	 * 订单id
	 */
	private String orderId;
	/**
	 * 订单号
	 */
	private String orderNo;
	/**
	 * 订单状态(中文)
	 */
	private String status;
	/**
	 * 订单状态枚举值
	 */
	private String statusEnum;
	/**
	 * 订单类型(国内产品订单/国际产品订单)
	 */
	private String type;
	/**
	 * 订单类型(枚举值)
	 */
	private String typeEnum;
	/**
	 * 供应商
	 */
	private String supplier;
	/**
	 * 需求备注
	 */
	private String requirementNotes;
	/**
	 * 订单总额
	 */
	private String orderTotalPrice;
	/**
	 * 联系人信息(目前一个订单只有一个联系人)
	 */
	private List<ContactorVO> contactors;
	/**
	 * 旅客信息
	 */
	private List<TravellerVO> travellers;
	/**
	 * 产品信息(第一期一个订单只包含一个产品)
	 */
	private List<OrderProductVO> products;
	/**
	 * 费用包含
	 */
	private String feesInclude;
	/**
	 * 费用不含
	 */
	private String feesExclude;
	/**
	 * 赠送项目
	 */
	private String giftItems;
	/**
	 * 退改政策
	 */
	private String refundPolicy;

	/**
	 * 费用详情
	 */
	private FeesDetailVO feesDetail;
	/**
	 * 操作日志
	 */
	private List<OrderLogVO> operationLogs;

	/**
	 * 原订单总价
	 */
	private String originalTotalPrice;
	/**
	 * 差价=原订单总价-现订单总价
	 */
	private String minusedTotalPrice;
	/**
	 * 退/补款单状态description
	 */
	private CommonOrderStatus commonOrderStatus;
	/**
	 * 退补款单类型:退款/补款/等价
	 */
	private String commonOrderType;
	/**
	 *
	 */
	private String commonOrderRemark;
	/**
	 * 附加产品
	 */
	private List<AdditionalProduct> additionalProducts;
    /**
     * 建单会员mid
     */
    private String creatorMid;
    /**
     * 
     */
    private String productNature;

	public String getProductNature() {
		return productNature;
	}

	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(String statusEnum) {
		this.statusEnum = statusEnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(String typeEnum) {
		this.typeEnum = typeEnum;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getRequirementNotes() {
		return requirementNotes;
	}

	public void setRequirementNotes(String requirementNotes) {
		this.requirementNotes = requirementNotes;
	}

	public List<ContactorVO> getContactors() {
		return contactors;
	}

	public void setContactors(List<ContactorVO> contactors) {
		this.contactors = contactors;
	}

	public List<TravellerVO> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<TravellerVO> travellers) {
		this.travellers = travellers;
	}

	public List<OrderProductVO> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProductVO> products) {
		this.products = products;
	}

	public List<OrderLogVO> getOperationLogs() {
		return operationLogs;
	}

	public void setOperationLogs(List<OrderLogVO> operationLogs) {
		this.operationLogs = operationLogs;
	}

	public String getFeesInclude() {
		return feesInclude;
	}

	public void setFeesInclude(String feesInclude) {
		this.feesInclude = feesInclude;
	}

	public String getFeesExclude() {
		return feesExclude;
	}

	public void setFeesExclude(String feesExclude) {
		this.feesExclude = feesExclude;
	}

	public String getGiftItems() {
		return giftItems;
	}

	public void setGiftItems(String giftItems) {
		this.giftItems = giftItems;
	}

	public FeesDetailVO getFeesDetail() {
		return feesDetail;
	}

	public void setFeesDetail(FeesDetailVO feesDetail) {
		this.feesDetail = feesDetail;
	}

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getRefundPolicy() {
		return refundPolicy;
	}

	public void setRefundPolicy(String refundPolicy) {
		this.refundPolicy = refundPolicy;
	}

	public String getOriginalTotalPrice() {
		return originalTotalPrice;
	}

	public void setOriginalTotalPrice(String originalTotalPrice) {
		this.originalTotalPrice = originalTotalPrice;
	}

	public String getMinusedTotalPrice() {
		return minusedTotalPrice;
	}

	public void setMinusedTotalPrice(String minusedTotalPrice) {
		this.minusedTotalPrice = minusedTotalPrice;
	}

	public CommonOrderStatus getCommonOrderStatus() {
		return commonOrderStatus;
	}

	public void setCommonOrderStatus(CommonOrderStatus commonOrderStatus) {
		this.commonOrderStatus = commonOrderStatus;
	}

	public String getCommonOrderType() {
		return commonOrderType;
	}

	public void setCommonOrderType(String commonOrderType) {
		this.commonOrderType = commonOrderType;
	}

	public List<AdditionalProduct> getAdditionalProducts() {
		return additionalProducts;
	}

	public void setAdditionalProducts(List<AdditionalProduct> additionalProducts) {
		this.additionalProducts = additionalProducts;
	}

	public String getCommonOrderRemark() {
		return commonOrderRemark;
	}

	public void setCommonOrderRemark(String commonOrderRemark) {
		this.commonOrderRemark = commonOrderRemark;
	}

    public String getCreatorMid() {
        return creatorMid;
    }

    public void setCreatorMid(String creatorMid) {
        this.creatorMid = creatorMid;
    }

}
