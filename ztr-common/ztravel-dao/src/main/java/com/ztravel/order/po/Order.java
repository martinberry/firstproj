package com.ztravel.order.po;

import java.util.Date;

import com.ztravel.common.enums.Nature;

/**
 * 订单
 * @author pengfei.zhao
 *
 */
public class Order {

	/***
	 * 订单ID
	 * */
	private String orderId;

	/***
	 * 订单编号
	 * */
    private String orderNo;

    /**
     * 标识订单是否跳转到支付
     * */
    private Boolean isToPay;

    /***
	 * 订单类型:国际产品订单/国内产品订单
	 * */
    private String orderType;

    /**
     * 订单来源
     * */
    private String orderFrom;

    /***
	 *建单时间
	 * */
    private Date createDate;

    /***
	 *建单人:member ID
	 * */
    private String creator;

    /***
	 *最近操作时间
	 * */
    private Date updateDate;

    /***
	 *最近操作人:member ID
	 * */
    private String operator;

    /***
	 *状态变更历史
	 * */
    private String stateChangeHistory;

    /***
	 *订单处理进度
	 * */
    private Integer progress;

    /***
	 *代金券ID
	 * */
    private String discountCouponId;

    /***
	 *使用的积分数目
	 * */
    private Integer integral;

    /***
	 *订单总价
	 * */
    private Long totalPrice;

    /***
	 *订单应付金额
	 * */
    private Long payAmount;

    /***
	 *订单前端状态
	 * */
    private String frontState;

    /***
	 *订单后台装填
	 * */
    private String backState;

    /**
     * 使用代金券抵消的金额
     * */
    private Long couponSub;

    /***
   	 *使用积分抵消的金额
   	 * */
    private Long integralSub;
    /**
     * 支付类型
     * */
    private String payType;
    /**
     * 支付流水号
     * */
    private String paySerialNum;
    /**
     * 酒店床型偏好
     * */
    private String bedPrefer;
    /**
     * 操作记录
     * */
    private String operateRecord;
    /**
     * 订单需求备注
     * */
    private String remark;
    /**
     * 原订单JSON
     */
    private String originalOrder;
    /**
     * 编辑行程确认单缓存JSON
     */
    private String tempOrder;
    
    /**
     * {@link Nature}
     * */
    private String productNature;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public String getStateChangeHistory() {
        return stateChangeHistory;
    }

    public void setStateChangeHistory(String stateChangeHistory) {
        this.stateChangeHistory = stateChangeHistory == null ? null : stateChangeHistory.trim();
    }

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }

    public String getDiscountCouponId() {
        return discountCouponId;
    }

    public void setDiscountCouponId(String discountCouponId) {
        this.discountCouponId = discountCouponId == null ? null : discountCouponId.trim();
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

	public Long getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Long payAmount) {
		this.payAmount = payAmount;
	}

	public String getFrontState() {
		return frontState;
	}

	public void setFrontState(String frontState) {
		this.frontState = frontState;
	}

	public String getBackState() {
		return backState;
	}

	public void setBackState(String backState) {
		this.backState = backState;
	}

	public Long getCouponSub() {
		return couponSub;
	}

	public void setCouponSub(Long couponSub) {
		this.couponSub = couponSub;
	}

	public Long getIntegralSub() {
		return integralSub;
	}

	public void setIntegralSub(Long integralSub) {
		this.integralSub = integralSub;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getPaySerialNum() {
		return paySerialNum;
	}

	public void setPaySerialNum(String paySerialNum) {
		this.paySerialNum = paySerialNum;
	}

	public String getBedPrefer() {
		return bedPrefer;
	}

	public void setBedPrefer(String bedPrefer) {
		this.bedPrefer = bedPrefer;
	}

	public String getOperateRecord() {
		return operateRecord;
	}

	public void setOperateRecord(String operateRecord) {
		this.operateRecord = operateRecord;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getIsToPay() {
		return isToPay;
	}

	public void setIsToPay(Boolean isToPay) {
		this.isToPay = isToPay;
	}

	public String getOrderFrom() {
		return orderFrom;
	}

	public void setOrderFrom(String orderFrom) {
		this.orderFrom = orderFrom;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderNo=" + orderNo
				+ ", isToPay=" + isToPay + ", orderType=" + orderType
				+ ", discountCouponId=" + discountCouponId + ", payAmount="
				+ payAmount + ", frontState=" + frontState + ", backState="
				+ backState + ", couponSub=" + couponSub + ", payType="
				+ payType + "]";
	}

	public String getOriginalOrder() {
		return originalOrder;
	}

	public void setOriginalOrder(String originalOrder) {
		this.originalOrder = originalOrder;
	}

	public String getTempOrder() {
		return tempOrder;
	}

	public void setTempOrder(String tempOrder) {
		this.tempOrder = tempOrder;
	}

	public String getProductNature() {
		return productNature;
	}

	public void setProductNature(String productNature) {
		this.productNature = productNature;
	}



}